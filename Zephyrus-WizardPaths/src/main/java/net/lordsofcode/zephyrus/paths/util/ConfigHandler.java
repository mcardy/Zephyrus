package net.lordsofcode.zephyrus.paths.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import net.lordsofcode.zephyrus.paths.WizardPaths;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Zephyrus WizardPaths
 * 
 * @author minnymin3 
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class ConfigHandler {
	
	String fileName;
	WizardPaths plugin;

	File configFile;
	FileConfiguration fileConfiguration;

	public ConfigHandler(WizardPaths plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName + ".yml";
	}

	public void reloadConfig() {
		if (configFile == null) {
			File dataFolder = new File("plugins", "Zephyrus");
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}
			configFile = new File(dataFolder, fileName);
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig() {
		File dataFolder = new File("plugins", "Zephyrus");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		configFile = new File(dataFolder, fileName);
		if (fileConfiguration == null || configFile == null) {
			return;
		} else {
			try {
				getConfig().save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE,
						"Could not save config to " + configFile, ex);
			}
		}
	}

	public void saveDefaultConfig() {
		File dataFolder = new File("plugins", "Zephyrus");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		configFile = new File(dataFolder, fileName);
		if (!configFile.exists()) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = plugin.getResource(fileName);
				out = new FileOutputStream(configFile);
				byte[] buffer = new byte[1024];
				int len = in.read(buffer);
				while (len != -1) {
				    out.write(buffer, 0, len);
				    len = in.read(buffer);
				}
			} catch (Exception e) {} finally {
				try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				} catch (IOException e) {}
			}
		}
	}
}
