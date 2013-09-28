package net.lordsofcode.zephyrus.player;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.player.mana.DummyDragon;
import net.lordsofcode.zephyrus.utils.ReflectionUtils;

import org.bukkit.Bukkit;
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

	String player;

	public ManaRecharge(String player) {
		this.player = player;
	}

	@Override
	public void run() {
		Player entity = Bukkit.getPlayer(player);
		if (entity != null) {
			IUser user = Zephyrus.getUser(entity);
			user.drainMana(-1);
			new ManaRecharge(player).runTaskLater(Zephyrus.getPlugin(), Zephyrus.getManaRegenTime());
		}
	}
}
