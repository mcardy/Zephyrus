package minny.zephyrus.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import minny.zephyrus.Zephyrus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfigHandler {

	private final String fileName;
	private final File dataFolder;
	private final Zephyrus plugin;
	
	
	private File configFile;
	private FileConfiguration fileConfiguration;

	public PlayerConfigHandler(Zephyrus plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.dataFolder = new File(plugin.getDataFolder(), "Players");
		dataFolder.mkdirs();
	}
	
	public void reloadConfig() {
		if (configFile == null) {
			if (dataFolder == null)
				throw new IllegalStateException();
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
		if (dataFolder == null)
			throw new IllegalStateException();
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
		if (dataFolder == null)
			throw new IllegalStateException();
		configFile = new File(dataFolder, fileName);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
}
