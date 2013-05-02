package minny.zephyrus;

import java.lang.reflect.Field;
import java.util.HashSet;

import minny.zephyrus.commands.LevelUp;
import minny.zephyrus.enchantments.GlowEffect;
import minny.zephyrus.enchantments.LifeSuck;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.LifeSuckSword;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	
	public GlowEffect glow = new GlowEffect(120);
	public LifeSuck suck = new LifeSuck(121);
	
	public HashSet<String> fireRod;
	public HashSet<String> lightningGem;
	
	ItemUtil itemUtil = new ItemUtil(this);
	
	public void onEnable() {
		new UpdateChecker(this).run();
		
		addCommands();
		addListeners();
		
		addEnchants();
		addRecipes();
	}

	/*public void onDisable() {
	
	}*/
	
	public void addRecipes() {
		RodOfFire fireRod = new RodOfFire(this);
		getServer().addRecipe(fireRod.recipe());

		GemOfLightning lightningGem = new GemOfLightning(this);
		getServer().addRecipe(lightningGem.recipe());

		HoeOfGrowth hoe = new HoeOfGrowth(this);
		getServer().addRecipe(hoe.recipe());
		
		LifeSuckSword lifesuck = new LifeSuckSword(this);
		getServer().addRecipe(lifesuck.recipe());
	}
	
	public void addEnchants() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
		}
		try {
			Enchantment.registerEnchantment(glow);
			Enchantment.registerEnchantment(suck);
		} catch (IllegalArgumentException e){
			
		}
	}
	
	public void addListeners(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
	}
	
	public void addCommands(){
		getCommand("levelup").setExecutor(new LevelUp(this, itemUtil));
	}
}
