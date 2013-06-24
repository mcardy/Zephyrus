package net.lordsofcode.zephyrus.utils;

import net.lordsofcode.zephyrus.Zephyrus;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Lang {

	/**
	 * Adds a section to the language config if it does not exist. Setting key to null will remove it.
	 * @param key The key to add
	 * @param desc The object to add
	 */
	public static void add(String key, String desc) {
		if (!Zephyrus.getInstance().langCfg.getConfig().contains(key)) { 
			Zephyrus.getInstance().langCfg.getConfig().set(key, desc);
			Zephyrus.getInstance().langCfg.saveConfig();
		}
	}
	
	/**
	 * Gets the specified section from the language config
	 * @param key The object's key
	 * @return The String found at that location
	 */
	public static String get(String key) {
		FileConfiguration cfg = Zephyrus.getInstance().lang;
		return cfg.getString(key).replace("$", ChatColor.COLOR_CHAR + "");
	}
	
	/**
	 * Sends an error message to the specified CommandSender
	 * @param key The key to get
	 * @param sender The CommandSender to send the message to
	 */
	public static void errMsg(String key, CommandSender sender) {
		String txt = get(key);
		sender.sendMessage(ChatColor.RED + txt.replace("$", ChatColor.COLOR_CHAR + ""));
	}
	
	/**
	 * Sends a message to the specified CommandSender
	 * @param key The key to get
	 * @param sender The CommandSender to send the message to
	 */
	public static void msg(String key, CommandSender sender) {
		String txt = get(key);
		sender.sendMessage(txt.replace("$", ChatColor.COLOR_CHAR + ""));
	}
	
	/**
	 * Fully capitalizes the string
	 * @param string The string to change
	 * @return The capitalized version of that string
	 */
	public static String caps(String string) {
		return WordUtils.capitalizeFully(string);
	}
	
}
