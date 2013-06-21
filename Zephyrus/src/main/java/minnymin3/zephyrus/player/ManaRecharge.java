package minnymin3.zephyrus.player;

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

public class ManaRecharge extends BukkitRunnable {

	Zephyrus plugin;
	Player player;
	LevelManager lvl;

	public ManaRecharge(Zephyrus plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		lvl = new LevelManager(plugin);
	}

	@Override
	public void run() {
		if (player.isOnline()) {
			if (LevelManager.getMana(player) < LevelManager.getLevel(player) * 100) {
				if (LevelManager.getMana(player) == 0) {
					Zephyrus.mana.put(player.getName(), 1);
				} else {
					Zephyrus.mana.put(player.getName(),
							LevelManager.getMana(player) + 1);
				}
			}
			new ManaRecharge(plugin, player).runTaskLater(plugin, plugin
					.getConfig().getInt("ManaRegen"));
		}
	}

}
