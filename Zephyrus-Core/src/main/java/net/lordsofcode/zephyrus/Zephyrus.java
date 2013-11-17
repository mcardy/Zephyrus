package net.lordsofcode.zephyrus;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.CustomEnchantment;
import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.api.ItemManager;
import net.lordsofcode.zephyrus.api.SpellManager;
import net.lordsofcode.zephyrus.effects.EffectHandler;
import net.lordsofcode.zephyrus.enchantments.GlowEffect;
import net.lordsofcode.zephyrus.loader.SpellLoader;
import net.lordsofcode.zephyrus.nms.ITrader;
import net.lordsofcode.zephyrus.player.User;
import net.lordsofcode.zephyrus.utils.ConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * @version 1.4.0
 */

public class Zephyrus {

	static Zephyrus instance;
	static SpellManager spellManager;
	static ItemManager itemManager;

	SpellLoader loader = new SpellLoader();
	ConfigHandler enchantmentsConfig = new ConfigHandler("enchantments.yml");
	ConfigHandler langConfig = new ConfigHandler("lang.yml");
	EffectHandler effectHandler;
	
	public GlowEffect glow = new GlowEffect(120);

	static int manaRegenTime;
	
	static Map<String, Map<Integer, Integer>> effectMap;
	static Map<String, Integer> mana;

	int builtInSpells = 0;

	public Zephyrus() {
		instance = this;
		
		spellManager = new SpellManager();
		itemManager = new ItemManager();
		
		enchantmentsConfig = new ConfigHandler("enchantments.yml");
		langConfig = new ConfigHandler("lang.yml");
		manaRegenTime = Zephyrus.getConfig().getInt("ManaRegen");
		effectHandler = new EffectHandler();
	}

	/**
	 * An instance of Zephyrus defined onEnable
	 * 
	 * @return An instance of Zephyrus
	 */
	public static Zephyrus getInstance() {
		return instance;
	}

	/**
	 * Gets the config file of Zephyrus
	 * 
	 * @return The FileConfiguration of Zephyrus' config.yml
	 */
	public static FileConfiguration getConfig() {
		return ZephyrusPlugin.getPluginInstance().getConfig();
	}

	/**
	 * Saves the config of Zephyrus
	 */
	public static void saveConfig() {
		ZephyrusPlugin.getPluginInstance().saveConfig();
	}

	/**
	 * Gets Zephyrus' plugin class
	 * 
	 * @return Zephyrus' java plugin
	 */
	public static ZephyrusPlugin getPlugin() {
		return ZephyrusPlugin.getPluginInstance();
	}

	/**
	 * Gets the mana map which contains a player's name and their mana value
	 * 
	 * @return A map with each player's name and each player's mana value
	 */
	public static Map<String, Integer> getManaMap() {
		return mana;
	}

	/**
	 * Gets the SpellManager's map of spells
	 * 
	 * @return
	 */
	public static Map<String, ISpell> getSpellMap() {
		return spellManager.getSpellMap();
	}

	/**
	 * Gets the Spellmanager's map of craftable spells
	 * 
	 * @return The spell's set of items and the spell
	 */
	public static Map<Set<ItemStack>, ISpell> getCraftMap() {
		return spellManager.getCraftMap();
	}
	
	/**
	 * Gets the custom item trading map
	 * 
	 * @return
	 */
	public static Map<ItemStack, ITrader> getTradeMap() {
		return itemManager.getTradeMap();
	}

	/**
	 * Gets the custom item merchant map
	 * 
	 * @return
	 */
	public static Map<String, ITrader> getMerchantMap() {
		return itemManager.getMerchantMap();
	}

	/**
	 * Gets the SpellManager for this instance of Zephyrus
	 * 
	 * @return
	 */
	public static SpellManager getSpellManager() {
		return spellManager;
	}

	/**
	 * Registers that ISpell
	 * 
	 * @param spell
	 */
	public static void registerSpell(ISpell spell) {
		getSpellManager().addSpell(spell);
	}

	/**
	 * Gets the time between mana regeneration
	 * 
	 * @return
	 */
	public static int getManaRegenTime() {
		return manaRegenTime;
	}
	
	/**
	 * Wraps the user from the specified player
	 * 
	 * @param player
	 *            The player to wrap
	 * @return An IUser object of the specified player
	 */
	public static IUser getUser(Player player) {
		return new User(player);
	}

	/**
	 * Registers the custom item
	 * 
	 * @param i
	 */
	public static void registerItem(ICustomItem i) {
		itemManager.addItem(i);
	}
	
	public static ItemManager getItemManager() {
		return itemManager;
	}

	public static Map<String, Map<Integer, Integer>> getEffectMap() {
		return effectMap;
	}
	
	/**
	 * Registers the specified enchantment
	 * 
	 * @param e
	 */
	public static void registerEnchantment(CustomEnchantment e) {
		if (!instance.enchantmentsConfig.getConfig().contains(e.getName())) {
			instance.enchantmentsConfig.getConfig().set(e.getName(), true);
			instance.enchantmentsConfig.saveConfig();
		}
		if (instance.enchantmentsConfig.getConfig().getBoolean(e.getName())) {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
				Enchantment.registerEnchantment(e);
				Bukkit.getPluginManager().registerEvents(e, getPlugin());
			} catch (Exception ex) {
			}
		}
	}

}