package net.lordsofcode.zephyrus.player;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

class ManaRecharge extends BukkitRunnable {

	Player player;

	public ManaRecharge(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		if (player.isOnline()) {
			IUser user = Zephyrus.getUser(player);
			user.drainMana(-1);
			new ManaRecharge(player).runTaskLater(Zephyrus.getPlugin(), Zephyrus
					.getConfig().getInt("ManaRegen"));
		}
	}

}
