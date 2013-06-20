package minnymin3.zephyrus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.commands.Bind;
import minnymin3.zephyrus.commands.Cast;
import minnymin3.zephyrus.commands.Level;
import minnymin3.zephyrus.commands.LevelUp;
import minnymin3.zephyrus.commands.LevelUpItem;
import minnymin3.zephyrus.commands.ManaCommand;
import minnymin3.zephyrus.commands.SpellTomeCmd;
import minnymin3.zephyrus.commands.UnBind;
import minnymin3.zephyrus.enchantments.GlowEffect;
import minnymin3.zephyrus.enchantments.LifeSuck;
import minnymin3.zephyrus.hooks.PluginHook;
import minnymin3.zephyrus.items.BlinkPearl;
import minnymin3.zephyrus.items.CustomItem;
import minnymin3.zephyrus.items.GemOfLightning;
import minnymin3.zephyrus.items.HoeOfGrowth;
import minnymin3.zephyrus.items.LifeSuckDiamond;
import minnymin3.zephyrus.items.LifeSuckGold;
import minnymin3.zephyrus.items.LifeSuckIron;
import minnymin3.zephyrus.items.ManaPotion;
import minnymin3.zephyrus.items.RodOfFire;
import minnymin3.zephyrus.items.SpellTome;
import minnymin3.zephyrus.items.Wand;
import minnymin3.zephyrus.listeners.EconListener;
import minnymin3.zephyrus.listeners.ItemLevelListener;
import minnymin3.zephyrus.listeners.LevelingListener;
import minnymin3.zephyrus.listeners.PlayerListener;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.player.ManaRecharge;
import minnymin3.zephyrus.spells.Armour;
import minnymin3.zephyrus.spells.Blink;
import minnymin3.zephyrus.spells.Bolt;
import minnymin3.zephyrus.spells.Butcher;
import minnymin3.zephyrus.spells.Confuse;
import minnymin3.zephyrus.spells.Conjure;
import minnymin3.zephyrus.spells.Dig;
import minnymin3.zephyrus.spells.Dispel;
import minnymin3.zephyrus.spells.Enderchest;
import minnymin3.zephyrus.spells.Explode;
import minnymin3.zephyrus.spells.Feather;
import minnymin3.zephyrus.spells.Feed;
import minnymin3.zephyrus.spells.FireRing;
import minnymin3.zephyrus.spells.Fireball;
import minnymin3.zephyrus.spells.FlameStep;
import minnymin3.zephyrus.spells.Flare;
import minnymin3.zephyrus.spells.Fly;
import minnymin3.zephyrus.spells.Frenzy;
import minnymin3.zephyrus.spells.Grow;
import minnymin3.zephyrus.spells.Heal;
import minnymin3.zephyrus.spells.Home;
import minnymin3.zephyrus.spells.Jail;
import minnymin3.zephyrus.spells.LifeSteal;
import minnymin3.zephyrus.spells.Mana;
import minnymin3.zephyrus.spells.Phase;
import minnymin3.zephyrus.spells.Prospect;
import minnymin3.zephyrus.spells.Punch;
import minnymin3.zephyrus.spells.Repair;
import minnymin3.zephyrus.spells.Satisfy;
import minnymin3.zephyrus.spells.Smite;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.spells.SuperHeat;
import minnymin3.zephyrus.spells.Vanish;
import minnymin3.zephyrus.utils.ConfigHandler;
import minnymin3.zephyrus.utils.Merchant;
import minnymin3.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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

	public GlowEffect glow = new GlowEffect(120);
	public static GlowEffect sGlow = new GlowEffect(122);
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
		new Wand(this);
	}

	private void disableSpells() {
		for (Spell spell : spellMap.values()) {
			spell.onDisable();
		}
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
					if (spell instanceof Armour) {
						config.getConfig().set(spell.name() + ".enabled", false);
					} else {
						config.getConfig().set(spell.name() + ".enabled", true);
					}
				}
				if (!config.getConfig().contains(spell.name() + ".mana")) {
					config.getConfig()
							.set(spell.name() + ".mana", spell.manaCost());
				}
				if (!config.getConfig().contains("level")) {
					config.getConfig()
					.set(spell.name() + ".level", spell.reqLevel());
				}
				if (spell.getConfigurations() != null) {
					Map<String, Object> cfg = spell.getConfigurations();
					for (String str : cfg.keySet()) {
						if (!config.getConfig().contains(spell.name() + "." + str)) {
							config.getConfig().set(spell.name() + "." + str, cfg.get(str));
						}
					}
				}
				config.saveConfig();
			}
			
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
