package minny.zephyrus.utils;

import minny.zephyrus.Zephyrus;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayUtil extends BukkitRunnable{

	Zephyrus plugin;
	ItemUtil util;
	ItemStack item;
	boolean b;
	
	public DelayUtil(Zephyrus plugin, ItemStack i, boolean b){
		this.plugin = plugin;
		this.util = new ItemUtil(plugin);
		this.item = i;
		this.b = b;
	}
	
	@Override
	public void run() {
		plugin.getServer().broadcastMessage("fired");
		util.setRecharging(item, b);
	}

}
