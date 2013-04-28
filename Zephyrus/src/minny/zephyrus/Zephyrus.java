package minny.zephyrus;

import minny.zephyrus.enchantments.GlowEffect;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	
	public void onEnable() {
		new UpdateChecker(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		
		//addEnchants();
		addRecipes();
	}

	public void onDisable() {

	}
	
	 public void addRecipes() {
		 RodOfFire fireRod = new RodOfFire(this);
		 getServer().addRecipe(fireRod.recipe());
		 
		 GemOfLightning lightningGem = new GemOfLightning(this);
		 getServer().addRecipe(lightningGem.recipe());
		 
		 HoeOfGrowth hoe = new HoeOfGrowth(this);
		 getServer().addRecipe(hoe.recipe());
	 }
	 
	 public void addEnchants() {
		 GlowEffect glow = new GlowEffect(120);
		 EnchantmentWrapper.registerEnchantment(glow);
	 }

}
