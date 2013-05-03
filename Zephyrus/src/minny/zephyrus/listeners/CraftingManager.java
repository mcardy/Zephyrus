package minny.zephyrus.listeners;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.LifeSuckSword;
import minny.zephyrus.utils.RecipeUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;

public class CraftingManager extends RecipeUtil implements Listener {
	
	Zephyrus plugin;
	
	public CraftingManager(Zephyrus plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLifeSuckUpgrade(PrepareItemCraftEvent e){
		LifeSuckSword sword = new LifeSuckSword(plugin);
		Recipe r = sword.recipeUpgrade();
		
		if (areEqual(e.getRecipe(), r)){
			
			if (!sword.checkName(e.getInventory().getItem(5), "¤aDiamond Sword of Life")){
				e.getViewers().get(0).getServer().broadcastMessage(e.getInventory().getItem(5).toString());
				e.getInventory().setResult(null);
			}
		}
	}
	
}
