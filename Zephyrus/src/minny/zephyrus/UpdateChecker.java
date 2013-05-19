package minny.zephyrus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class UpdateChecker extends BukkitRunnable {

	String checkURL = "https://raw.github.com/minnymin3/Zephyrus/master/version";
	String changelogURL = "https://raw.github.com/minnymin3/Zephyrus/master/Changelog";
	Zephyrus plugin;

	public UpdateChecker(Zephyrus plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (plugin.getConfig().getBoolean("UpdateChecker")) {
			String current = plugin.getDescription().getVersion();
			Logger log = plugin.getLogger();

			try {
				log.info("Checking for a new version...");
				URL url = new URL(checkURL);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = br.readLine()) != null) {
					String line = str;

					if (isUpdate(current, line) == -1) {
						log.warning("Zephyrus is out of date! Get the new version at:");
						log.warning("dev.bukkit.org/server-mods/Zephyrus");
						plugin.isUpdate = true;
						try {
							URL change = new URL(changelogURL);
							BufferedReader cl = new BufferedReader(
									new InputStreamReader(change.openStream()));
							String stri;
							while ((stri = cl.readLine()) != null) {
								String scl = stri;
								log.info("[Changelog] " + scl);
								plugin.changelog = scl;
							}
						} catch (IOException e) {
							log.severe("Unable to get Changelog");
						}
						break;
					} else if (isUpdate(current, line) == 1) {
						plugin.isUpdate = false;
						log.info("You are running a developement build of Zephyrus");
						break;
					} else if (isUpdate(current, line) == 0) {
						log.info("Zephyrus is up to date!");
						plugin.isUpdate = false;
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				log.severe("Was unable to check for update. URL may be invalid");
			}
		}
	}

	public int isUpdate(String str1, String str2) {
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
