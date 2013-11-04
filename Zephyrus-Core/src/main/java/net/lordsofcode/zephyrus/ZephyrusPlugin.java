package net.lordsofcode.zephyrus;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellManager;
import net.lordsofcode.zephyrus.commands.Bind;
import net.lordsofcode.zephyrus.commands.Cast;
import net.lordsofcode.zephyrus.commands.EffectsCommand;
import net.lordsofcode.zephyrus.commands.Level;
import net.lordsofcode.zephyrus.commands.LevelUp;
import net.lordsofcode.zephyrus.commands.LevelUpItem;
import net.lordsofcode.zephyrus.commands.ManaCommand;
import net.lordsofcode.zephyrus.commands.SpellTomeCmd;
import net.lordsofcode.zephyrus.commands.UnBind;
import net.lordsofcode.zephyrus.enchantments.BattleAxe;
import net.lordsofcode.zephyrus.enchantments.InstaMine;
import net.lordsofcode.zephyrus.enchantments.LifeSuck;
import net.lordsofcode.zephyrus.enchantments.ToxicStrike;
import net.lordsofcode.zephyrus.items.BlinkPearl;
import net.lordsofcode.zephyrus.items.GemOfLightning;
import net.lordsofcode.zephyrus.items.HoeOfGrowth;
import net.lordsofcode.zephyrus.items.ManaPotion;
import net.lordsofcode.zephyrus.items.RodOfFire;
import net.lordsofcode.zephyrus.items.SpellTome;
import net.lordsofcode.zephyrus.items.wands.BasicWand;
import net.lordsofcode.zephyrus.items.wands.ObsidianWand;
import net.lordsofcode.zephyrus.items.wands.WandListener;
import net.lordsofcode.zephyrus.items.wands.WeakWand;
import net.lordsofcode.zephyrus.listeners.EconListener;
import net.lordsofcode.zephyrus.listeners.LevelingListener;
import net.lordsofcode.zephyrus.listeners.PlayerListener;
import net.lordsofcode.zephyrus.player.ManaBar;
import net.lordsofcode.zephyrus.registry.PlantRegistry;
import net.lordsofcode.zephyrus.spells.Armour;
import net.lordsofcode.zephyrus.spells.Arrow;
import net.lordsofcode.zephyrus.spells.ArrowStorm;
import net.lordsofcode.zephyrus.spells.Bang;
import net.lordsofcode.zephyrus.spells.Blink;
import net.lordsofcode.zephyrus.spells.Bolt;
import net.lordsofcode.zephyrus.spells.Butcher;
import net.lordsofcode.zephyrus.spells.Clock;
import net.lordsofcode.zephyrus.spells.Confuse;
import net.lordsofcode.zephyrus.spells.Conjure;
import net.lordsofcode.zephyrus.spells.Detect;
import net.lordsofcode.zephyrus.spells.Dig;
import net.lordsofcode.zephyrus.spells.Dim;
import net.lordsofcode.zephyrus.spells.Dispel;
import net.lordsofcode.zephyrus.spells.Enderchest;
import net.lordsofcode.zephyrus.spells.Explode;
import net.lordsofcode.zephyrus.spells.Feather;
import net.lordsofcode.zephyrus.spells.Feed;
import net.lordsofcode.zephyrus.spells.FireRing;
import net.lordsofcode.zephyrus.spells.FireShield;
import net.lordsofcode.zephyrus.spells.Fireball;
import net.lordsofcode.zephyrus.spells.FlameStep;
import net.lordsofcode.zephyrus.spells.Flare;
import net.lordsofcode.zephyrus.spells.Fly;
import net.lordsofcode.zephyrus.spells.Frenzy;
import net.lordsofcode.zephyrus.spells.Grenade;
import net.lordsofcode.zephyrus.spells.Grow;
import net.lordsofcode.zephyrus.spells.Heal;
import net.lordsofcode.zephyrus.spells.Home;
import net.lordsofcode.zephyrus.spells.Jail;
import net.lordsofcode.zephyrus.spells.LifeSteal;
import net.lordsofcode.zephyrus.spells.MageLight;
import net.lordsofcode.zephyrus.spells.Mana;
import net.lordsofcode.zephyrus.spells.MassParalyze;
import net.lordsofcode.zephyrus.spells.Paralyze;
import net.lordsofcode.zephyrus.spells.Phase;
import net.lordsofcode.zephyrus.spells.Prospect;
import net.lordsofcode.zephyrus.spells.Punch;
import net.lordsofcode.zephyrus.spells.Repair;
import net.lordsofcode.zephyrus.spells.Satisfy;
import net.lordsofcode.zephyrus.spells.Shield;
import net.lordsofcode.zephyrus.spells.Shine;
import net.lordsofcode.zephyrus.spells.Smite;
import net.lordsofcode.zephyrus.spells.Storm;
import net.lordsofcode.zephyrus.spells.Summon;
import net.lordsofcode.zephyrus.spells.SuperHeat;
import net.lordsofcode.zephyrus.spells.SuperSpeed;
import net.lordsofcode.zephyrus.spells.Vanish;
import net.lordsofcode.zephyrus.spells.Vision;
import net.lordsofcode.zephyrus.spells.Zap;
import net.lordsofcode.zephyrus.spells.Zephyr;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PluginHook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

public class ZephyrusPlugin extends JavaPlugin {

	static ZephyrusPlugin pluginInstance;

	private Zephyrus zephyrus;

	@Override
	public void onEnable() {
		load();
	}

	@Override
	public void onDisable() {
		unload();
	}

	private void load() {
		pluginInstance = this;
		zephyrus = new Zephyrus();

		setupMaps();
		setupConfigs();
		setupLanguage();
		setupRegistry();

		setupHooks();
		setupVersion();

		addSpells();
		addCommands();
		addEnchants();
		addItems();
		addListeners();

		for (Player p : Bukkit.getOnlinePlayers()) {
			Zephyrus.getUser(p).loadMana();
			zephyrus.effectHandler.onLogin(new PlayerJoinEvent(p, ""));
		}

		getLogger().info(
				"Zephyrus v" + getDescription().getVersion() + " by "
						+ getDescription().getAuthors().toString().replace("[", "").replace("]", "") + " enabled!");

		new PostInit().runTaskAsynchronously(this);
	}

	private void unload() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Zephyrus.getUser(p).unLoadMana();
			zephyrus.effectHandler.onLogout(new PlayerQuitEvent(p, ""));
		}
		for (ISpell spell : Zephyrus.getSpellMap().values()) {
			spell.onDisable();
		}
		Zephyrus.instance = null;
		pluginInstance = null;
	}

	static ZephyrusPlugin getPluginInstance() {
		return pluginInstance;
	}
	
	private void setupHooks() {
		if (PluginHook.isWorldGuard()) {
			getLogger().info("WorldGuard found. Protections integrated");
			PluginHook.loadFlags();
		}
		if (PluginHook.isEconomy()) {
			getLogger().info("Vault found. Integrating economy!");
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(new EconListener(zephyrus), this);
		}
	}

	private void setupConfigs() {
		saveDefaultConfig();
		zephyrus.langConfig.saveDefaultConfig();
		zephyrus.enchantmentsConfig.saveDefaultConfig();
	}

	private void setupLanguage() {
		Lang.add("noperm", "You do not have permission to do that!");
		Lang.add("ingameonly", "You must be an in-game player to perform this command!");
		Lang.add("notonline", "That player is not online!");
		Lang.add("outofdatebukkit",
				"Sadly, the version of Craftbukkit that you are using is out of date... This feature has been disabled.");

		Lang.add("nomana", "Not enough mana!");
		Lang.add("disabled", "That spell has been disabled...");
		Lang.add("notlearned", "You do not know that spell!");
		Lang.add("worldguard", "You do not have permission for this area!");
		Lang.add("nospellzone", "You are in a no spell zone! You can't cast spells!");

		Lang.add("spelltome.learn", "Learn this spell by left clicking this book");
		Lang.add("spelltome.cast", "Cast this spell with $b/cast [SPELL]$0");
		Lang.add("spelltome.nospell", "That spell was not found!");
		Lang.add("spelltome.known", "You already know that spell!");
		Lang.add("spelltome.success", "You have successfully learned $6[SPELL]");
		Lang.add("spelltome.cantlearn", ChatColor.DARK_RED + "You don't have permission to learn [SPELL]");
		Lang.add("spelltome.noperm", ChatColor.DARK_RED + "You don't have permission to use the spelltome!");

		Lang.add("customitem.level", "Level");
	}

	private void setupVersion() {
		try {
			new CraftLivingEntity(null, null);
		} catch (NoClassDefFoundError err) {
			getLogger().warning(
					"This version of Zephyrus is not fully compatible with your version of CraftBukkit."
							+ " Some features have been disabled!");
		}
	}

	private void setupMaps() {
		Zephyrus.mana = new HashMap<String, Integer>();
		Zephyrus.effectMap = new HashMap<String, Map<Integer, Integer>>();
	}

	private void setupRegistry() {
		PlantRegistry.init();
	}
	
	private void addSpells() {
		getLogger().info("Loading spells...");
		// A
		Zephyrus.registerSpell(new Armour());
		Zephyrus.registerSpell(new Arrow());
		Zephyrus.registerSpell(new ArrowStorm());
		// B
		Zephyrus.registerSpell(new Bang());
		Zephyrus.registerSpell(new Blink());
		Zephyrus.registerSpell(new Bolt());
		Zephyrus.registerSpell(new Butcher());
		// C
		Zephyrus.registerSpell(new Clock());
		Zephyrus.registerSpell(new Confuse());
		Zephyrus.registerSpell(new Conjure());
		// D
		Zephyrus.registerSpell(new Detect());
		Zephyrus.registerSpell(new Dig());
		Zephyrus.registerSpell(new Dim());
		Zephyrus.registerSpell(new Dispel());
		// E
		Zephyrus.registerSpell(new Enderchest());
		Zephyrus.registerSpell(new Explode());
		// F
		Zephyrus.registerSpell(new Feather());
		Zephyrus.registerSpell(new Feed());
		Zephyrus.registerSpell(new Fireball());
		Zephyrus.registerSpell(new FireRing());
		Zephyrus.registerSpell(new FireShield());
		Zephyrus.registerSpell(new FlameStep());
		Zephyrus.registerSpell(new Flare());
		Zephyrus.registerSpell(new Fly());
		Zephyrus.registerSpell(new Frenzy());
		// G
		Zephyrus.registerSpell(new Grenade());
		Zephyrus.registerSpell(new Grow());
		// H
		Zephyrus.registerSpell(new Heal());
		Zephyrus.registerSpell(new Home());
		// I

		// J
		Zephyrus.registerSpell(new Jail());
		// K

		// L
		Zephyrus.registerSpell(new LifeSteal());
		// M
		Zephyrus.registerSpell(new MageLight());
		Zephyrus.registerSpell(new Mana());
		Zephyrus.registerSpell(new MassParalyze());
		// N

		// O

		// P
		Zephyrus.registerSpell(new Paralyze());
		Zephyrus.registerSpell(new Phase());
		Zephyrus.registerSpell(new Prospect());
		Zephyrus.registerSpell(new Punch());
		// Q

		// R
		Zephyrus.registerSpell(new Repair());
		// S
		Zephyrus.registerSpell(new Satisfy());
		Zephyrus.registerSpell(new Shield());
		Zephyrus.registerSpell(new Shine());
		Zephyrus.registerSpell(new Smite());
		Zephyrus.registerSpell(new Storm());
		Zephyrus.registerSpell(new Summon());
		Zephyrus.registerSpell(new SuperHeat());
		Zephyrus.registerSpell(new SuperSpeed());
		// T

		// U

		// V
		Zephyrus.registerSpell(new Vanish());
		Zephyrus.registerSpell(new Vision());
		// W

		// X

		// Y

		// Z
		Zephyrus.registerSpell(new Zap());
		Zephyrus.registerSpell(new Zephyr());

		try {
			zephyrus.loader.loadSpells();
		} catch (MalformedURLException e) {
			getLogger().warning("Error loading spells from spells folder: " + e.getMessage());
		}
		zephyrus.loader.registerSpells();
	}

	private void addItems() {
		getLogger().info("Loading items...");
		if (!getConfig().getBoolean("Disable-Recipes")) {
			Zephyrus.registerItem(new BlinkPearl());
			Zephyrus.registerItem(new GemOfLightning());
			Zephyrus.registerItem(new HoeOfGrowth());
			Zephyrus.registerItem(new RodOfFire());
			Zephyrus.registerItem(new ManaPotion());
		}
		Zephyrus.registerItem(new BasicWand());
		Zephyrus.registerItem(new ObsidianWand());
		Zephyrus.registerItem(new WeakWand());
	}

	private void addEnchants() {
		getLogger().info("Loading enchantments...");
		if (getConfig().getBoolean("Enable-Enchantments")) {
			Zephyrus.registerEnchantment(new InstaMine(123));
			Zephyrus.registerEnchantment(new LifeSuck(124));
			Zephyrus.registerEnchantment(new ToxicStrike(125));
			Zephyrus.registerEnchantment(new BattleAxe(126));
		} else if (!getConfig().contains("Enable-Enchantments")) {
			Zephyrus.registerEnchantment(new InstaMine(123));
			Zephyrus.registerEnchantment(new LifeSuck(124));
			Zephyrus.registerEnchantment(new ToxicStrike(125));
			Zephyrus.registerEnchantment(new BattleAxe(126));
		}
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(zephyrus.glow);
		} catch (Exception e) {
		}
	}

	private void addListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new LevelingListener(), this);
		pm.registerEvents(new SpellTome(), this);
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new ManaBar(), this);
		pm.registerEvents(new WandListener(), this);
		pm.registerEvents(zephyrus.effectHandler, this);
	}

	private void addCommands() {
		getCommand("levelup").setExecutor(new LevelUp());
		getCommand("levelupitem").setExecutor(new LevelUpItem());
		getCommand("cast").setExecutor(new Cast());
		getCommand("cast").setTabCompleter(new Cast());
		getCommand("mana").setExecutor(new ManaCommand());
		getCommand("bind").setExecutor(new Bind());
		getCommand("bind").setTabCompleter(new Bind());
		getCommand("spelltome").setExecutor(new SpellTomeCmd());
		getCommand("level").setExecutor(new Level());
		getCommand("unbind").setExecutor(new UnBind());
		getCommand("effects").setExecutor(new EffectsCommand());
	}

	private class PostInit extends BukkitRunnable {
		@Override
		public void run() {
			SpellManager manager = Zephyrus.getSpellManager();
			getLogger().info(
					"Loaded " + manager.getRegisteredSpells() + " spells. " + manager.getBuiltInSpells()
							+ " built in & " + manager.getExternalSpells() + " external spells.");
		}
	}

}
