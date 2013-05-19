package minny.zephyrus.utils;

import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class DelayUtil extends BukkitRunnable {

	Map<String, Object> set;
	String string;

	public DelayUtil(Map<String, Object> set, String string) {
		this.set = set;
		this.string = string;
	}

	@Override
	public void run() {
		set.remove(string);
	}
}
