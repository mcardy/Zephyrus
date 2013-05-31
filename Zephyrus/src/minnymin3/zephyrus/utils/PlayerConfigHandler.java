package minnymin3.zephyrus.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerConfigHandler {

	private static File configFile;
	private static FileConfiguration fileConfiguration;

	/**
	 * Reloads the config from the file
	 * @param plugin Zephyrus main class
	 * @param player The player to get the config for
	 */
	public static void reloadConfig(Zephyrus plugin, Player player) {
		String fileName = player.getName() + ".yml";
		File dataFolder = new File(plugin.getDataFolder(), "Players");
		configFile = new File(dataFolder, fileName);
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
	}

	/**
	 * Gets the config for the specified player
	 * @param plugin Zephyrus main class
	 * @param player The player to get the config for
	 * @return The player's FileConfiguration
	 */
	public static FileConfiguration getConfig(Zephyrus plugin, Player player) {
		if (fileConfiguration == null) {
			PlayerConfigHandler.reloadConfig(plugin, player);
		}
		return fileConfiguration;
	}

	/**
	 * Saves the config file for the specified player
	 * @param plugin Zephyrus main class
	 * @param player The player to save the config for
	 */
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

	/**
	 * Saves a new file for the player
	 * @param plugin Zephyrus main class
	 * @param player The player to save the default config for
	 */
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
