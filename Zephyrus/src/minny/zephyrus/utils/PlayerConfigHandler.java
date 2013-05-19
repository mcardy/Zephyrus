package minny.zephyrus.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import minny.zephyrus.Zephyrus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerConfigHandler {

	private static File configFile;
	private static FileConfiguration fileConfiguration;
	
	public static void reloadConfig(Zephyrus plugin, Player player) {
		String fileName = player.getName() + ".yml";
		File dataFolder = new File(plugin.getDataFolder(), "Players");
		dataFolder.mkdirs();
		if (configFile == null) {
			configFile = new File(dataFolder, fileName);
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		//InputStream defConfigStream = null;
		//if (defConfigStream != null) {
		//	YamlConfiguration defConfig = YamlConfiguration
		//			.loadConfiguration(defConfigStream);
		//	fileConfiguration.setDefaults(defConfig);
		//}
	}

	public static FileConfiguration getConfig(Zephyrus plugin, Player player) {
		if (fileConfiguration == null) {
			String fileName = player.getName() + ".yml";
			File dataFolder = new File(plugin.getDataFolder(), "Players");
			PlayerConfigHandler.reloadConfig(plugin, player);
			configFile = new File(dataFolder, fileName);
			fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
		}
		return fileConfiguration;
	}

	public static void saveConfig(Zephyrus plugin, Player player) {
		String fileName = player.getName() + ".yml";
		File dataFolder = new File(plugin.getDataFolder(), "Players");
		dataFolder.mkdirs();
		configFile = new File(dataFolder, fileName);
		if (fileConfiguration == null || configFile == null) {
			plugin.getLogger().info("Something broke!");
			return;
		} else {
			try {
				getConfig(plugin, player).save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE,
						"Could not save config to " + configFile, ex);
			}
		}
	}

	public static void saveDefaultConfig(Zephyrus plugin, Player player) {
		String fileName = player.getName() + ".yml";
		File dataFolder = new File(plugin.getDataFolder(), "Players");
		dataFolder.mkdirs();
		configFile = new File(dataFolder, fileName);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
}
