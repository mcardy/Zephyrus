package net.lordsofcode.zephyrus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.commands.Bind;
import net.lordsofcode.zephyrus.commands.Cast;
import net.lordsofcode.zephyrus.commands.Level;
import net.lordsofcode.zephyrus.commands.LevelUp;
import net.lordsofcode.zephyrus.commands.LevelUpItem;
import net.lordsofcode.zephyrus.commands.ManaCommand;
import net.lordsofcode.zephyrus.commands.SpellTomeCmd;
import net.lordsofcode.zephyrus.commands.UnBind;
import net.lordsofcode.zephyrus.enchantments.GlowEffect;
import net.lordsofcode.zephyrus.enchantments.LifeSuck;
import net.lordsofcode.zephyrus.hooks.PluginHook;
import net.lordsofcode.zephyrus.items.BlinkPearl;
import net.lordsofcode.zephyrus.items.CustomItem;
import net.lordsofcode.zephyrus.items.GemOfLightning;
import net.lordsofcode.zephyrus.items.HoeOfGrowth;
import net.lordsofcode.zephyrus.items.LifeSuckDiamond;
import net.lordsofcode.zephyrus.items.LifeSuckGold;
import net.lordsofcode.zephyrus.items.LifeSuckIron;
import net.lordsofcode.zephyrus.items.ManaPotion;
import net.lordsofcode.zephyrus.items.RodOfFire;
import net.lordsofcode.zephyrus.items.SpellTome;
import net.lordsofcode.zephyrus.items.Wand;
import net.lordsofcode.zephyrus.listeners.EconListener;
import net.lordsofcode.zephyrus.listeners.ItemLevelListener;
import net.lordsofcode.zephyrus.listeners.LevelingListener;
import net.lordsofcode.zephyrus.listeners.PlayerListener;
import net.lordsofcode.zephyrus.player.LevelManager;
import net.lordsofcode.zephyrus.player.ManaRecharge;
import net.lordsofcode.zephyrus.spells.Armour;
import net.lordsofcode.zephyrus.spells.Blink;
import net.lordsofcode.zephyrus.spells.Bolt;
import net.lordsofcode.zephyrus.spells.Butcher;
import net.lordsofcode.zephyrus.spells.Confuse;
import net.lordsofcode.zephyrus.spells.Conjure;
import net.lordsofcode.zephyrus.spells.Dig;
import net.lordsofcode.zephyrus.spells.Dispel;
import net.lordsofcode.zephyrus.spells.Enderchest;
import net.lordsofcode.zephyrus.spells.Explode;
import net.lordsofcode.zephyrus.spells.Feather;
import net.lordsofcode.zephyrus.spells.Feed;
import net.lordsofcode.zephyrus.spells.FireRing;
import net.lordsofcode.zephyrus.spells.Fireball;
import net.lordsofcode.zephyrus.spells.FlameStep;
import net.lordsofcode.zephyrus.spells.Flare;
import net.lordsofcode.zephyrus.spells.Fly;
import net.lordsofcode.zephyrus.spells.Frenzy;
import net.lordsofcode.zephyrus.spells.Grow;
import net.lordsofcode.zephyrus.spells.Heal;
import net.lordsofcode.zephyrus.spells.Home;
import net.lordsofcode.zephyrus.spells.Jail;
import net.lordsofcode.zephyrus.spells.LifeSteal;
import net.lordsofcode.zephyrus.spells.Mana;
import net.lordsofcode.zephyrus.spells.Phase;
import net.lordsofcode.zephyrus.spells.Prospect;
import net.lordsofcode.zephyrus.spells.Punch;
import net.lordsofcode.zephyrus.spells.Repair;
import net.lordsofcode.zephyrus.spells.Satisfy;
import net.lordsofcode.zephyrus.spells.Smite;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.spells.SuperHeat;
import net.lordsofcode.zephyrus.spells.Vanish;
import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Merchant;
import net.lordsofcode.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Zephyrus extends JavaPlugin {

	private static Zephyrus instance;

	ConfigHandler config = new ConfigHandler(this, "spells.yml");
	
	public ConfigHandler langCfg = new ConfigHandler(this, "lang.yml");
	
	public FileConfiguration lang;
	public FileConfiguration spells;
	
	public GlowEffect glow = new GlowEffect(120);
	public LifeSuck suck = new LifeSuck(121);

	public String[] update;

	public Map<String, Map<String, Integer>> itemDelay;

	public Map<String, Merchant> invPlayers;

	public static Map<String, Object> mana;

	public static Map<String, Spell> spellMap;
	public static Map<Set<ItemStack>, Spell> spellCraftMap;
	public static Map<String, CustomItem> itemMap;
	public static Map<ItemStack, Merchant> merchantMap;

	private int builtInSpells = 0;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		config.saveDefaultConfig();
		langCfg.saveDefaultConfig();

		itemMap = new HashMap<String, CustomItem>();
		spellCraftMap = new HashMap<Set<ItemStack>, Spell>();
		spellMap = new HashMap<String, Spell>();
		merchantMap = new HashMap<ItemStack, Merchant>();
		invPlayers = new HashMap<String, Merchant>();
		itemDelay = new HashMap<String, Map<String, Integer>>();
		mana = new HashMap<String, Object>();

		new UpdateChecker(this);

		try {
			new CraftLivingEntity(null, null);
		} catch (NoClassDefFoundError err) {
			getLogger()
					.warning(
							"This version of Zephyrus is not fully compatible with your version of CraftBukkit."
									+ " Some features have been disabled!");
		}

		hook();
		addCommands();
		addEnchants();
		addItems();
		addSpells();
		addListeners();

		for (Player p : Bukkit.getOnlinePlayers()) {
			Zephyrus.mana.put(p.getName(), LevelManager.loadMana(p));
			new ManaRecharge(this, p).runTaskLater(this, 30);
		}
		getLogger().info(
				"Zephyrus v"
						+ this.getDescription().getVersion()
						+ " by "
						+ this.getDescription().getAuthors().toString()
								.replace("[", "").replace("]", "")
						+ " Enabled!");
		new PostInit().runTaskAsynchronously(this);
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			LevelManager.saveMana(p);
			Zephyrus.mana.remove(p);
		}
		disableSpells();
	}

	/**
	 * An instance of Zephyrus defined onEnable
	 * 
	 * @return An instance of Zephyrus
	 */
	public static Zephyrus getInstance() {
		return instance;
	}

	private void hook() {
		if (PluginHook.worldGuard()) {
			getLogger().info("WorldGuard found. Protections integrated");
		}
		if (PluginHook.economy()) {
			getLogger().info("Vault found. Integrating economy!");
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(new EconListener(this), this);
		}
	}

	private void addItems() {
		if (!getConfig().getBoolean("Disable-Recipes")) {
			new BlinkPearl(this);
			new GemOfLightning(this);
			new HoeOfGrowth(this);
			new LifeSuckDiamond(this);
			new LifeSuckGold(this);
			new LifeSuckIron(this);
			new ManaPotion(this);
			new RodOfFire(this);
		}
		new ManaPotion(this);
		new Wand(this);
	}

	private void disableSpells() {
		for (Spell spell : spellMap.values()) {
			spell.onDisable();
		}
		instance = null;
	}

	private void addSpells() {
		new Armour(this);
		new Blink(this);
		new Bolt(this);
		new Butcher(this);
		new Confuse(this);
		new Conjure(this);
		new Dig(this);
		new Dispel(this);
		new Enderchest(this);
		new Explode(this);
		new Feather(this);
		new Feed(this);
		new Fireball(this);
		new FireRing(this);
		new FlameStep(this);
		new Flare(this);
		new Fly(this);
		new Frenzy(this);
		new Feed(this);
		new Grow(this);
		new Heal(this);
		new Home(this);
		new Jail(this);
		new LifeSteal(this);
		new Mana(this);
		new Phase(this);
		new Prospect(this);
		new Punch(this);
		new Repair(this);
		new Satisfy(this);
		new Smite(this);
		new SuperHeat(this);
		new Vanish(this);

		builtInSpells = spellMap.size();
	}

	private void addEnchants() {
		//new InstaMine(123);
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

	private void addListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new LevelingListener(this), this);
		pm.registerEvents(new SpellTome(this, null, null), this);
		pm.registerEvents(new ItemLevelListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
	}

	private void addCommands() {
		getCommand("levelup").setExecutor(new LevelUp(this));
		getCommand("levelupitem").setExecutor(new LevelUpItem(this));
		getCommand("cast").setExecutor(new Cast(this));
		getCommand("cast").setTabCompleter(new Cast(this));
		getCommand("mana").setExecutor(new ManaCommand(this));
		getCommand("bind").setExecutor(new Bind(this));
		getCommand("bind").setTabCompleter(new Bind(this));
		getCommand("spelltome").setExecutor(new SpellTomeCmd(this));
		getCommand("level").setExecutor(new Level(this));
		getCommand("unbind").setExecutor(new UnBind());
	}

	/**
	 * Adds the designated spell to Zephyrus Automatically called in the Spell
	 * constructor
	 * 
	 * @param spell
	 *            The spell to add
	 */
	public void addSpell(Spell spell) {
		if ((spell.getClass().getPackage() == Spell.class.getPackage())) {
			if (spell.name() != null
					&& !Zephyrus.spellMap.containsKey(spell.name()
							.toLowerCase())) {
				Zephyrus.spellMap.put(spell.name().toLowerCase(), spell);
			}
			if (spell.spellItems() != null
					&& !Zephyrus.spellCraftMap.containsKey(spell.spellItems())) {
				Zephyrus.spellCraftMap.put(spell.spellItems(), spell);
			}
			if (isListener(spell) && !spell.listenerEnabled) {
				getServer().getPluginManager().registerEvents(spell, this);
				spell.listenerEnabled = true;
			}
		} else {
			if (spell.name() != null
					&& !Zephyrus.spellMap.containsKey(spell.name()
							.toLowerCase())) {
				Zephyrus.spellMap.put(spell.name().toLowerCase(), spell);
			}
			if (spell.spellItems() != null
					&& !Zephyrus.spellCraftMap.containsKey(spell.spellItems())) {
				Zephyrus.spellCraftMap.put(spell.spellItems(), spell);
			}
		}
	}

	private boolean isListener(Spell spell) {
		for (Method m : spell.getClass().getMethods()) {
			if (m.isAnnotationPresent(EventHandler.class)) {
				return true;
			}
		}
		return false;
	}

	private class PostInit extends BukkitRunnable {
		@Override
		public void run() {
			try {
				for (CustomItem ci : Zephyrus.itemMap.values()) {
					if (ci.hasLevel()) {
						for (int i = 1; i < ci.maxLevel(); i++) {
							ItemStack item = ci.item();
							ci.setItemLevel(item, i);
							ItemStack item2 = ci.item();
							int i2 = i;
							ci.setItemLevel(item2, i2 + 1);
							Merchant m = new Merchant();
							m.addOffer(item,
									new ItemStack(Material.EMERALD, i), item2);
							Zephyrus.merchantMap.put(item, m);
						}
					}
				}
			} catch (Exception e) {
				getLogger().warning(e.getMessage());
			}

			for (Spell spell : spellMap.values()) {
				if (!config.getConfig().contains(spell.name() + ".enabled")) {
					config.getConfig().set(spell.name() + ".enabled", true);
				}
				if (!config.getConfig().contains(spell.name() + ".desc")) {
					config.getConfig().set(spell.name() + ".desc", spell.bookText());
				}
				if (!config.getConfig().contains(spell.name() + ".mana")) {
					config.getConfig().set(spell.name() + ".mana",
							spell.manaCost());
				}
				if (!config.getConfig().contains("level")) {
					config.getConfig().set(spell.name() + ".level",
							spell.reqLevel());
				}
				if (spell.getConfigurations() != null) {
					Map<String, Object> cfg = spell.getConfigurations();
					for (String str : cfg.keySet()) {
						if (!config.getConfig().contains(
								spell.name() + "." + str)) {
							config.getConfig().set(spell.name() + "." + str,
									cfg.get(str));
						}
					}
				}
				config.saveConfig();
			}

			lang = langCfg.getConfig();
			spells = config.getConfig();
			
			try {
				for (String s : update) {
					if (s != null) {
						getLogger().info(s);
					}
				}
			} catch (NullPointerException e) {
				getLogger().info("Could not check for updates...");
			}
			String added = "";
			if (!(spellMap.size() == builtInSpells)) {
				int a = spellMap.size() - builtInSpells;
				added = " " + a + " external spells registered. ";
			}

			getLogger().info("Loaded " + spellMap.size() + " spells." + added);
		}
	}

}
