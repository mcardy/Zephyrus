package minny.zephyrus.utils;

import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;

public class DelayUtil extends BukkitRunnable{

	Set<String> map;
	String user;
	
	public DelayUtil(Set<String> map, String user){
		this.map = map;
		this.user = user;
	}
	
	@Override
	public void run() {
		map.remove(user);
	}

}
