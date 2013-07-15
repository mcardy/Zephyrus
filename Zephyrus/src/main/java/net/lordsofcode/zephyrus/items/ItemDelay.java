package net.lordsofcode.zephyrus.items;

import java.util.HashMap;
import java.util.Map;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.ZephyrusPlugin;

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

	public static void setDelay(Player player,
			CustomItem item, int delay) {
		ZephyrusPlugin plugin = Zephyrus.getPlugin();
		if (Zephyrus.getDelayMap().containsKey(player.getName())) {
			Map<String, Integer> s = Zephyrus.getDelayMap().get(player.getName());
			s.put(item.getConfigName(), delay);
			Zephyrus.getDelayMap().put(player.getName(), s);
			new CooldownUtil(player, item).runTaskLater(plugin, 20);
		} else {
			Map<String, Integer> s = new HashMap<String, Integer>();
			s.put(item.getConfigName(), delay);
			Zephyrus.getDelayMap().put(player.getName(), s);
			new CooldownUtil(player, item).runTaskLater(plugin, 20);
		}
	}

	public static boolean hasDelay(Player player,
			CustomItem item) {
		if (Zephyrus.getDelayMap().containsKey(player.getName())) {
			Map<String, Integer> s = Zephyrus.getDelayMap().get(player.getName());
			if (s.containsKey(item.getConfigName())) {
				return true;
			}
		}
		return false;
	}

	public static int getDelay(Player player, CustomItem item) {
		if (Zephyrus.getDelayMap().containsKey(player.getName())) {
			Map<String, Integer> s = Zephyrus.getDelayMap().get(player.getName());
			if (s.containsKey(item.getConfigName())) {
				int i = s.get(item.getConfigName());
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

	CooldownUtil(Player player, CustomItem item) {
		this.item = item;
		this.player = player;
	}

	@Override
	public void run() {
		Map<String, Integer> s = Zephyrus.getDelayMap().get(player.getName());
		int i = s.get(item.getConfigName());
		i = i - 20;
		if (i <= 0) {
			s.remove(item.getConfigName());
		} else {
			s.put(item.getConfigName(), i);
			new CooldownUtil(player, item).runTaskLater(Zephyrus.getPlugin(), 20);
		}
		Zephyrus.getDelayMap().put(player.getName(), s);
	}
}
