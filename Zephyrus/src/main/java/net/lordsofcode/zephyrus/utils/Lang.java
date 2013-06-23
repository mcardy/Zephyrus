package net.lordsofcode.zephyrus.utils;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Lang {

	public static void add(String key, String desc) {
		Zephyrus.getInstance().langCfg.getConfig().set(key, desc);
		Zephyrus.getInstance().langCfg.saveConfig();
	}
	
	public static String get(String key) {
		FileConfiguration cfg = Zephyrus.getInstance().lang;
		return cfg.getString(key);
	}
	
}
