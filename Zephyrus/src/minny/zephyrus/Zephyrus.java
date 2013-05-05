package minny.zephyrus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import minny.zephyrus.commands.Cast;
import minny.zephyrus.commands.LevelUp;
import minny.zephyrus.commands.LevelUpItem;
import minny.zephyrus.commands.Mana;
import minny.zephyrus.enchantments.GlowEffect;
import minny.zephyrus.enchantments.LifeSuck;
import minny.zephyrus.items.BlinkPearl;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.LifeSuckSword;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.listeners.CraftingManager;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.spells.Fireball;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	CraftingManager craftManager = new CraftingManager(this);

	public GlowEffect glow = new GlowEffect(120);
	public LifeSuck suck = new LifeSuck(121);

	public Map<String, Object> fireRodDelay;
	public Map<String, Object> lightningGemDelay;
	public Map<String, Object> blinkPearlDelay;
	
	public Map<String, Object> mana;
	
	public Map<String, Spell> spellMap;

	ItemUtil itemUtil = new ItemUtil(this);

	@Override
	public void onEnable() {
		new UpdateChecker(this).run();
		
		fireRodDelay = new HashMap<String, Object>();
		lightningGemDelay = new HashMap<String, Object>();
		blinkPearlDelay = new HashMap<String, Object>();
		mana = new HashMap<String, Object>();
		spellMap = new HashMap<String, Spell>();

		addCommands();
		addListeners();

		addEnchants();
		addItems();
		
		addSpells();
	}

	/*
	 * public void onDisable() {
	 * 
	 * }
	 */
	
	public void addItems(){
		new GemOfLightning(this);
		new HoeOfGrowth(this);
		new LifeSuckSword(this);
		new RodOfFire(this);
		new BlinkPearl(this);
	}
	
	public void addSpells(){
		new Fireball(this);
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
		} catch (IllegalArgumentException e) {

		}
	}

	public void addListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		pm.registerEvents(craftManager, this);
	}

	public void addCommands() {
		getCommand("levelup").setExecutor(new LevelUp(this));
		getCommand("levelupitem").setExecutor(new LevelUpItem(this, itemUtil));
		getCommand("cast").setExecutor(new Cast(this));
		getCommand("mana").setExecutor(new Mana(this));
	}
}
