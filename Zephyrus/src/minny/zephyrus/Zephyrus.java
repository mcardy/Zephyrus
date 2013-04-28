package minny.zephyrus;

import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.items.Skull;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	
	public void onEnable() {
		new UpdateChecker(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		
		addRecipes();
	}

	public void onDisable() {

	}
	
	 public void addRecipes() {
		 RodOfFire fireRod = new RodOfFire(this);
		 getServer().addRecipe(fireRod.recipe());
		 
		 GemOfLightning lightningGem = new GemOfLightning(this);
		 getServer().addRecipe(lightningGem.recipe());
		 
		 Skull skull = new Skull(this);
		 getServer().addRecipe(skull.recipe());
     }

}
