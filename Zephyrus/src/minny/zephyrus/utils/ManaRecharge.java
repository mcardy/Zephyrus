package minny.zephyrus.utils;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRecharge extends BukkitRunnable {

	Zephyrus plugin;
	Player player;
	LevelManager lvl;

	public ManaRecharge(Zephyrus plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.lvl = new LevelManager(plugin);
	}

	public void run() {
		if (player.isOnline()) {
			if (lvl.getMana(player) < lvl.getLevel(player) * 100) {
				if (lvl.getMana(player) == 0) {
					plugin.mana.put(player.getName(), 1);
				} else {
					plugin.mana.put(player.getName(), lvl.getMana(player) + 1);
				}
			}
			new ManaRecharge(plugin, player).runTaskLater(plugin, plugin.getConfig().getInt("ManaRegen"));
		}
	}

}
