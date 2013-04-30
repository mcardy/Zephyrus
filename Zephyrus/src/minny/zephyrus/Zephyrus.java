package minny.zephyrus;

import java.lang.reflect.Field;
import java.util.HashSet;

import minny.zephyrus.commands.LevelUp;
import minny.zephyrus.enchantments.GlowEffect;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.SetItem;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	
	public GlowEffect glow = new GlowEffect(120);
	
	public HashSet<String> fireRod;
	public HashSet<String> lightningGem;
	
	SetItem item = new SetItem(this);
	
	public void onEnable() {
		new UpdateChecker(this);

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);

		getCommand("levelup").setExecutor(new LevelUp(this, item));
		
		registerSet();
		addEnchants();
		addRecipes();
	}

	public void onDisable() {

	}

	public void registerSet(){
		fireRod = new HashSet<String>();
		lightningGem = new HashSet<String>();
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
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			EnchantmentWrapper.registerEnchantment(glow);
		} catch (IllegalArgumentException e){
			
		}
	}

}
