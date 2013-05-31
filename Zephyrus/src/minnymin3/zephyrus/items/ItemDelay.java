package minnymin3.zephyrus.items;

import java.util.HashMap;
import java.util.Map;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ItemDelay {

	public static void setDelay(Zephyrus plugin, Player player,
			CustomItem item, int delay) {
		if (plugin.itemDelay.containsKey(player.getName())) {
			Map<String, Integer> s = plugin.itemDelay.get(player.getName());
			s.put(item.name(), delay);
			plugin.itemDelay.put(player.getName(), s);
		} else {
			Map<String, Integer> s = new HashMap<String, Integer>();
			s.put(item.name(), delay);
			plugin.itemDelay.put(player.getName(), s);
			new CooldownUtil(plugin, player, item).runTaskLater(plugin, 20);
		}
	}

	public static boolean hasDelay(Zephyrus plugin, Player player,
			CustomItem item) {
		if (plugin.itemDelay.containsKey(player.getName())) {
			Map<String, Integer> s = plugin.itemDelay.get(player.getName());
			if (s.containsKey(item.name())) {
				return true;
			}
		}
		return false;
	}

	public static int getDelay(Zephyrus plugin, Player player, CustomItem item) {
		if (plugin.itemDelay.containsKey(player.getName())) {
			Map<String, Integer> s = plugin.itemDelay.get(player.getName());
			if (s.containsKey(item.name())) {
				int i = s.get(item.name());
				int ret = Math.round(i / 20);
				return ret;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

}

class CooldownUtil extends BukkitRunnable {

	CustomItem item;
	Player player;
	Zephyrus plugin;

	CooldownUtil(Zephyrus plugin, Player player, CustomItem item) {
		this.item = item;
		this.plugin = plugin;
		this.player = player;
	}

	public void run() {
		Map<String, Integer> s = plugin.itemDelay.get(player.getName());
		if (!(s.get(item.name()) <= 0)) {
			int i = s.get(item.name());
			i = i - 20;
			if (i == 0) {
				s.remove(item.name());
			} else {
				s.put(item.name(), i);
				new CooldownUtil(plugin, player, item).runTaskLater(plugin, 20);
			}
			plugin.itemDelay.put(player.getName(), s);
		}
	}
}
