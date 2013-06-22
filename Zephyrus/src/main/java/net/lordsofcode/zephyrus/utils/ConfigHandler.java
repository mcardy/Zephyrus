package net.lordsofcode.zephyrus.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ConfigHandler {

	String fileName;
	Zephyrus plugin;

	File configFile;
	FileConfiguration fileConfiguration;

	/**
	 * Creates a configuration instance
	 * @param plugin Zephyrus instance
	 * @param fileName The name of the file containing .yml
	 */
	public ConfigHandler(Zephyrus plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
	}

	/**
	 * Reloads the configuration from the file
	 */
	public void reloadConfig() {
		if (configFile == null) {
			File dataFolder = plugin.getDataFolder();
			if (dataFolder == null) {
				throw new IllegalStateException();
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

	/**
	 * Gets the FileConfiguration of the config object
	 * @return A FileConfiguration of the object
	 */
	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	/**
	 * Saves the config from saved instance's FileConfiguration
	 */
	public void saveConfig() {
		File dataFolder = plugin.getDataFolder();
		if (dataFolder == null) {
			throw new IllegalStateException();
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

	/**
	 * Saves a default config from an embedded resource
	 */
	public void saveDefaultConfig() {
		File dataFolder = plugin.getDataFolder();
		if (dataFolder == null) {
			throw new IllegalStateException();
		}
		configFile = new File(dataFolder, fileName);
		if (!configFile.exists()) {
			plugin.saveResource(fileName, false);
		}
	}
}
