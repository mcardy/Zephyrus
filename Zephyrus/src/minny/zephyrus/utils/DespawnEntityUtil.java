package minny.zephyrus.utils;

import org.bukkit.entity.Creature;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class DespawnEntityUtil extends BukkitRunnable {

	Creature entity;

	public DespawnEntityUtil(Creature e) {
		entity = e;
	}

	@Override
	public void run() {

		entity.damage(1000);
	}

}
