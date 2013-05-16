package minny.zephyrus.utils;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveFlightUtil extends BukkitRunnable {

	Zephyrus plugin;
	Player player;
	
	public RemoveFlightUtil(Zephyrus plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	public void run() {
		player.sendMessage(ChatColor.GRAY + "5 seconds of flight remaining!");
		new RemoveFlight().runTaskLater(plugin, 100);
	}
	
	private class RemoveFlight extends BukkitRunnable {
		public void run() {
			player.setAllowFlight(false);
		}
	}
	
}
