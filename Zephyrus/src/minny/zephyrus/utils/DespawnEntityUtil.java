package minny.zephyrus.utils;

import org.bukkit.entity.Creature;
import org.bukkit.scheduler.BukkitRunnable;

public class DespawnEntityUtil extends BukkitRunnable {

	Creature entity;
	
	public DespawnEntityUtil(Creature e) {
		this.entity = e;
	}
	
	public void run() {
		
		entity.damage(1000);
	}
	
}
