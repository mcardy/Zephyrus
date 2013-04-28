package minny.zephyrus;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;

public class Hooks {

	Zephyrus plugin;
	
	Hooks(Zephyrus plugin){
		this.plugin = plugin;
	}
	
	public WorldGuardPlugin getWorldGuard() {
	    Plugin worldguard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");

	    if (worldguard == null || !(worldguard instanceof WorldGuardPlugin)) {
	        return null; 
	    }
	 
	    return (WorldGuardPlugin) worldguard;
	}
	
	public boolean isWorldGuard() {
		Plugin worldguard = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		 
	    if (worldguard == null || !(worldguard instanceof WorldGuardPlugin)) {
	        return false; 
	    }
	 
	    return true;
	}
	
}
