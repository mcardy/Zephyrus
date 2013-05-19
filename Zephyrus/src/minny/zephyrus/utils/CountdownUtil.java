package minny.zephyrus.utils;

import java.util.Map;

import minny.zephyrus.Zephyrus;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class CountdownUtil extends BukkitRunnable {

	Map<String, Object> map;
	String string;
	Zephyrus plugin;

	public CountdownUtil(Map<String, Object> map, String string, Zephyrus plugin) {
		this.map = map;
		this.string = string;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (map.containsKey(string)) {
			int time = (Integer) map.get(string);
			if (time > 1) {
				map.put(string, time - 1);
				new CountdownUtil(map, string, plugin).runTaskLater(plugin, 20);
			}
		}
	}

}
