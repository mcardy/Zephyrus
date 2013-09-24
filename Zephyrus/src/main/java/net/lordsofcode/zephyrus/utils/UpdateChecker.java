package net.lordsofcode.zephyrus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import net.lordsofcode.zephyrus.Zephyrus;


/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class UpdateChecker implements Runnable {
	
	String checkURL = "https://raw.github.com/minnymin3/Zephyrus/master/version";
	String changelogURL = "https://raw.github.com/minnymin3/Zephyrus/master/Changelog";
	Zephyrus plugin;

	public static boolean isUpdate;
	public static String changelog;

	private String[] result = new String[3];

	Thread thread;

	/**
	 * Checks for an update for Zephyrus
	 * 
	 * @param plugin
	 *            Zephyrus plugin
	 */
	public UpdateChecker(Zephyrus plugin) {
		this.plugin = plugin;
		thread = new Thread(this, "ZephyrusUpdateThread");
		thread.start();
	}

	@Override
	public void run() {
		if (Zephyrus.getConfig().getBoolean("UpdateChecker")) {
			String current = Zephyrus.getPlugin().getDescription().getVersion();
			Logger log = Zephyrus.getPlugin().getLogger();

			try {
				log.info("Starting update checker...");
				URL url = new URL(checkURL);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = br.readLine()) != null) {
					String line = str;

					if (isUpdate(current, line) == -1) {
						result[0] = "New version of Zephyrus available: " + line;
						result[1] = "Get it at: dev.bukkit.org/server-mods/Zephyrus";
						isUpdate = true;
						try {
							URL change = new URL(changelogURL);
							BufferedReader cl = new BufferedReader(
									new InputStreamReader(change.openStream()));
							String stri;
							while ((stri = cl.readLine()) != null) {
								String scl = stri;
								changelog = scl;
								result[2] = "[ChangeLog] " + scl;
							}
						} catch (IOException e) {
							log.severe("Unable to get Changelog");
						}
						break;
					} else if (isUpdate(current, line) == 1) {
						isUpdate = false;
						result[0] = "You are running a developement build of Zephyrus";
						break;
					} else if (isUpdate(current, line) == 0) {
						result[0] = "Zephyrus is up to date!";
						isUpdate = false;
						break;
					}
				}
				br.close();
				plugin.updateMsg = result;
			} catch (IOException e) {
				log.severe("Unable to check for updates!");
			}
		}

	}

	public String[] getResult() {
		return result;
	}

	private int isUpdate(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		while (i < vals1.length && i < vals2.length
				&& vals1[i].equals(vals2[i])) {
			i++;
		}

		if (i < vals1.length && i < vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(
					Integer.valueOf(vals2[i]));
			return diff < 0 ? -1 : diff == 0 ? 0 : 1;
		}

		return vals1.length < vals2.length ? -1
				: vals1.length == vals2.length ? 0 : 1;
	}

}
