package minny.zephyrus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import minny.zephyrus.commands.Cast;
import minny.zephyrus.commands.LevelUp;
import minny.zephyrus.commands.LevelUpItem;
import minny.zephyrus.commands.Mana;
import minny.zephyrus.enchantments.GlowEffect;
import minny.zephyrus.enchantments.LifeSuck;
import minny.zephyrus.hooks.PluginHook;
import minny.zephyrus.items.BlinkPearl;
import minny.zephyrus.items.CustomItem;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.LifeSuckSword;
import minny.zephyrus.items.ManaPotion;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.items.Wand;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.spells.Blink;
import minny.zephyrus.spells.Bolt;
import minny.zephyrus.spells.Enderchest;
import minny.zephyrus.spells.Feed;
import minny.zephyrus.spells.Fireball;
import minny.zephyrus.spells.Grow;
import minny.zephyrus.spells.Repair;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.ManaRecharge;
import minny.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);

	LevelManager lvl = new LevelManager(this);

	public GlowEffect glow = new GlowEffect(120);
	public LifeSuck suck = new LifeSuck(121);

	public Map<String, Object> fireRodDelay;
	public Map<String, Object> lightningGemDelay;
	public Map<String, Object> blinkPearlDelay;

	public Map<String, Object> mana;

	public Map<String, Spell> spellMap;
	public Map<Set<ItemStack>, Spell> spellCraftMap;
	public Map<String, CustomItem> itemMap;

	@Override
	public void onEnable() {
		new UpdateChecker(this).run();

		PluginHook hook = new PluginHook();

		if (hook.worldGuard()) {
			getLogger().info("WorldGuard integration implemented!");
			hook.wgHook();
		}
		if (hook.economy()) {
			getLogger().info("Vault integration implemented!");
			hook.econHook();
		}

		fireRodDelay = new HashMap<String, Object>();
		lightningGemDelay = new HashMap<String, Object>();
		blinkPearlDelay = new HashMap<String, Object>();

		mana = new HashMap<String, Object>();

		spellMap = new HashMap<String, Spell>();
		spellCraftMap = new HashMap<Set<ItemStack>, Spell>();
		itemMap = new HashMap<String, CustomItem>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			this.mana.put(p.getName(), lvl.loadMana(p));
			new ManaRecharge(this, p).runTaskLater(this, 30);
		}

		saveDefaultConfig();

		addCommands();
		addListeners();

		addEnchants();
		addItems();

		addSpells();
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			lvl.saveMana(p);
			this.mana.remove(p);
		}
	}

	public void addItems() {
		new BlinkPearl(this);
		new GemOfLightning(this);
		new HoeOfGrowth(this);
		new LifeSuckSword(this);
		new ManaPotion(this);
		new RodOfFire(this);
		new Wand(this);
	}

	public void addSpells() {
		new Fireball(this);
		new Feed(this);
		new Enderchest(this);
		new Repair(this);
		new Blink(this);
		new Bolt(this);
		new Grow(this);
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
	}

	public void addCommands() {
		getCommand("levelup").setExecutor(new LevelUp(this));
		getCommand("levelupitem").setExecutor(new LevelUpItem(this));
		getCommand("cast").setExecutor(new Cast(this));
		getCommand("cast").setTabCompleter(new Cast(this));
		getCommand("mana").setExecutor(new Mana(this));
	}
}
