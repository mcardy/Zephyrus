package minny.zephyrus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import minny.zephyrus.commands.Bind;
import minny.zephyrus.commands.Cast;
import minny.zephyrus.commands.Level;
import minny.zephyrus.commands.LevelUp;
import minny.zephyrus.commands.LevelUpItem;
import minny.zephyrus.commands.Mana;
import minny.zephyrus.commands.SpellTomeCmd;
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
import minny.zephyrus.items.SpellTome;
import minny.zephyrus.items.Wand;
import minny.zephyrus.listeners.EconListener;
import minny.zephyrus.listeners.LevelingListener;
import minny.zephyrus.listeners.PlayerListener;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.player.ManaRecharge;
import minny.zephyrus.spells.Armour;
import minny.zephyrus.spells.Blink;
import minny.zephyrus.spells.Bolt;
import minny.zephyrus.spells.Butcher;
import minny.zephyrus.spells.Confuse;
import minny.zephyrus.spells.Dig;
import minny.zephyrus.spells.Enderchest;
import minny.zephyrus.spells.Feed;
import minny.zephyrus.spells.Fireball;
import minny.zephyrus.spells.Fly;
import minny.zephyrus.spells.Frenzy;
import minny.zephyrus.spells.Grow;
import minny.zephyrus.spells.Heal;
import minny.zephyrus.spells.Phase;
import minny.zephyrus.spells.Punch;
import minny.zephyrus.spells.Repair;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.spells.SuperHeat;
import minny.zephyrus.spells.Vanish;
import minny.zephyrus.utils.ConfigHandler;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zephyrus extends JavaPlugin {

	PlayerListener playerListener = new PlayerListener(this);
	LevelingListener levelListener = new LevelingListener(this);

	ConfigHandler config = new ConfigHandler(this, "spellconfig.yml");
	LevelManager lvl = new LevelManager(this);

	public GlowEffect glow = new GlowEffect(120);
	public LifeSuck suck = new LifeSuck(121);

	public boolean isUpdate;
	public String changelog;

	public Map<String, Object> fireRodDelay;
	public Map<String, Object> lightningGemDelay;
	public Map<String, Object> blinkPearlDelay;

	public static Map<String, Object> mana;

	public static Map<String, Spell> spellMap;
	public static Map<Set<ItemStack>, Spell> spellCraftMap;
	public static Map<String, CustomItem> itemMap;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		itemMap = new HashMap<String, CustomItem>();
		spellCraftMap = new HashMap<Set<ItemStack>, Spell>();
		spellMap = new HashMap<String, Spell>();

		new UpdateChecker(this).run();

		PluginHook hook = new PluginHook();

		if (hook.worldGuard()) {
			getLogger().info(
					"WorldGuard found. Integrating WorldGuard protections!");
		}
		if (hook.economy()) {
			getLogger().info("Vault found. Integrating economy!");
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(new EconListener(this), this);
		}

		fireRodDelay = new HashMap<String, Object>();
		lightningGemDelay = new HashMap<String, Object>();
		blinkPearlDelay = new HashMap<String, Object>();

		mana = new HashMap<String, Object>();

		for (Player p : Bukkit.getOnlinePlayers()) {
			Zephyrus.mana.put(p.getName(), LevelManager.loadMana(p));;
			new ManaRecharge(this, p).runTaskLater(this, 30);
		}

		try {
			new CraftLivingEntity(null, null);
		} catch (NoClassDefFoundError err) {
			getLogger()
					.warning(
							"This version of Zephyrus is not fully compatible with your version of CraftBukkit."
									+ " Some features have been disabled!");
		}

		addCommands();
		addListeners();
		addEnchants();
		addItems();
		addSpells();

		getLogger()
				.info("Zephyrus v" + this.getDescription().getVersion()
						+ " Enabled!");
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			LevelManager.saveMana(p);
			PlayerConfigHandler.saveConfig(this, p);
			Zephyrus.mana.remove(p);
		}
	}

	public void addItems() {
		if (!getConfig().getBoolean("Disable-Recipes")) {
			new BlinkPearl(this);
			new GemOfLightning(this);
			new HoeOfGrowth(this);
			new LifeSuckSword(this);
			new ManaPotion(this);
			new RodOfFire(this);
		}
		new Wand(this);
	}

	public void addSpells() {
		new Armour(this);
		new Blink(this);
		new Bolt(this);
		new Butcher(this);
		new Confuse(this);
		new Dig(this);
		new Enderchest(this);
		new Fireball(this);
		new Fly(this);
		new Frenzy(this);
		new Feed(this);
		new Grow(this);
		new Heal(this);
		new Phase(this);
		new Punch(this);
		new Repair(this);
		// new Summon(this);
		new SuperHeat(this);
		new Vanish(this);

		/*
		 * config.saveDefaultConfig(); Set<String> keys =
		 * this.spellMap.keySet(); Object[] string = keys.toArray(); for (Object
		 * s : string){ Spell spell = this.spellMap.get(s); if
		 * (!config.getConfig().contains(spell.name())){
		 * config.getConfig().set(spell.name(), spell.manaCost()); } }
		 * config.saveConfig();
		 */
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
		pm.registerEvents(levelListener, this);
		pm.registerEvents(new SpellTome(this, null, null), this);
	}

	public void addCommands() {
		getCommand("levelup").setExecutor(new LevelUp(this));
		getCommand("levelupitem").setExecutor(new LevelUpItem(this));
		getCommand("cast").setExecutor(new Cast(this));
		getCommand("cast").setTabCompleter(new Cast(this));
		getCommand("mana").setExecutor(new Mana(this));
		getCommand("bind").setExecutor(new Bind(this));
		getCommand("bind").setTabCompleter(new Bind(this));
		getCommand("spelltome").setExecutor(new SpellTomeCmd(this));
		getCommand("Level").setExecutor(new Level(this));
	}

	public void addSpell(Spell spell) {
		if ((spell.getClass().getPackage() == Spell.class.getPackage())) {
			if (spell.name() != null
					&& !Zephyrus.spellMap.containsKey(spell.name())) {
				Zephyrus.spellMap.put(spell.name(), spell);
			}
			if (spell.spellItems() != null
					&& !Zephyrus.spellCraftMap.containsKey(spell.spellItems())) {
				Zephyrus.spellCraftMap.put(spell.spellItems(), spell);

			}
		} else {
			if (spell.name() != null
					&& !Zephyrus.spellMap.containsKey(spell.name())) {
				Zephyrus.spellMap.put(spell.name(), spell);
			}
			if (spell.spellItems() != null
					&& !Zephyrus.spellCraftMap.containsKey(spell.spellItems())) {
				Zephyrus.spellCraftMap.put(spell.spellItems(), spell);
			}
			Bukkit.getLogger().info(
					"[Zephyrus] External spell '" + spell.name()
							+ "' registered!");
		}
	}
}
