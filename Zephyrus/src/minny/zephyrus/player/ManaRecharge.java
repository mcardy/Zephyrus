package minny.zephyrus.player;

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
			if (LevelManager.getMana(player) < LevelManager.getLevel(player) * 100) {
				if (LevelManager.getMana(player) == 0) {
					Zephyrus.mana.put(player.getName(), 1);
				} else {
					Zephyrus.mana.put(player.getName(), LevelManager.getMana(player) + 1);
				}
			}
			new ManaRecharge(plugin, player).runTaskLater(plugin, plugin.getConfig().getInt("ManaRegen"));
		}
	}

}
