package minny.zephyrus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import minny.zephyrus.Zephyrus;

public class UpdateChecker implements Runnable {

	// Insert plugin declaration.

	String url = "https://raw.github.com/minnymin3/Simple-Build/master/version";

	public boolean isUpdate;
	Zephyrus plugin;
	
	Thread t;
	
	public UpdateChecker(Zephyrus plugin) {
		this.plugin = plugin;
		t = new Thread(this, "UpdateThread");
	    t.start();
	}
	
	public void run() {
		if (plugin.getConfig().getBoolean("UpdateChecker")) {
			String current = plugin.getDescription().getVersion();
			String readurl = url;
			Logger log = plugin.getLogger();

			try {
				log.info("Checking for a new version...");
				URL url = new URL(readurl);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String str;
				while ((str = br.readLine()) != null) {
					String line = str;

					if (isUpdate(current, line) == -1) {
						log.info("Update Message");
						log.info("URL");
						this.isUpdate = true;
						break;
					} else if (isUpdate(current, line) == 1) {
						this.isUpdate = false;
						log.info("Dev Build");
						break;
					} else if (isUpdate(current, line) == 0) {
						log.info("Up to date");
						this.isUpdate = false;
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
