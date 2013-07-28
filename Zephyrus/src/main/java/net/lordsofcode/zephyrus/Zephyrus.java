package net.lordsofcode.zephyrus;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.CustomEnchantment;
import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.api.SpellManager;
import net.lordsofcode.zephyrus.enchantments.GlowEffect;
import net.lordsofcode.zephyrus.loader.SpellLoader;
import net.lordsofcode.zephyrus.player.User;
import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.ItemUtil;
import net.lordsofcode.zephyrus.utils.Merchant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * @version 1.3.0 Beta
 */

public class Zephyrus {

	static Zephyrus instance;
	static SpellManager spellManager;

	SpellLoader loader = new SpellLoader();
	ConfigHandler spellsConfig = new ConfigHandler("spells.yml");
	ConfigHandler enchantmentsConfig = new ConfigHandler("enchantments.yml");
	ConfigHandler langConfig = new ConfigHandler("lang.yml");
	ConfigHandler itemsConfig = new ConfigHandler("items.yml");

	public GlowEffect glow = new GlowEffect(120);

	public String[] updateMsg;

	static Map<String, Map<String, Integer>> itemDelay;
	static Map<String, Merchant> invPlayers;

	static Map<String, Integer> mana;
	static Map<String, ICustomItem> itemMap;
	static Map<ItemStack, Merchant> merchantMap;
	
	int builtInSpells = 0;
	
	public Zephyrus() {
		instance = this;
		spellManager = new SpellManager();
		itemsConfig = new ConfigHandler("items.yml");
		spellsConfig = new ConfigHandler("spells.yml");
		enchantmentsConfig = new ConfigHandler("enchantments.yml");
		langConfig = new ConfigHandler("lang.yml");
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
	 * @return Zephyrus' java plugin
	 */
	public static ZephyrusPlugin getPlugin() {
		return ZephyrusPlugin.getPluginInstance();
	}
	
	/**
	 * Gets the mana map which contains a player's name and their mana value
	 * @return A map with each player's name and each player's mana value
	 */
	public static Map<String, Integer> getManaMap() {
		return mana;
	}
	
	/**
	 * Gets the SpellManager's map of spells
	 * @return
	 */
	public static Map<String, ISpell> getSpellMap() {
		return spellManager.getSpellMap();
	}
	
	/**
	 * Gets the Spellmanager's map of craftable spells
	 * @return The spell's set of items and the spell
	 */
	public static Map<Set<ItemStack>, ISpell> getCraftMap() {
		return spellManager.getCraftMap();
	}
	
	/**
	 * Gets the ItemManager's item map
	 * @return The Item's name and the CustomItem object
	 */
	public static Map<String, ICustomItem> getItemMap() {
		return itemMap;
	}
	
	/**
	 * Gets the custom item trading map
	 * @return
	 */
	public static Map<ItemStack, Merchant> getTradeMap() {
		return merchantMap;
	}
	
	/**
	 * Gets the item delay map
	 * @return
	 */
	public static Map<String, Map<String, Integer>> getDelayMap() {
		return itemDelay;
	}
	
	/**
	 * Gets the custom item merchant map
	 * @return
	 */
	public static Map<String, Merchant> getMerchantMap() {
		return invPlayers;
	}
	
	/**
	 * Gets the SpellManager for this instance of Zephyrus
	 * @return
	 */
	public static SpellManager getSpellManager() {
		return spellManager;
	}
	
	/**
	 * Registers that ISpell
	 * @param spell
	 */
	public static void registerSpell(ISpell spell) {
		getSpellManager().addSpell(spell);
	}
	
	/**
	 * Wraps the user from the specified player
	 * @param player The player to wrap
	 * @return An IUser object of the specified player
	 */
	public static IUser getUser(Player player) {
		return new User(player);
	}
	
	/**
	 * Registers the custom item
	 * @param i
	 */
	public static void registerItem(ICustomItem i) {
		if (i.getRecipe() != null) {
			getPlugin().getServer().addRecipe(i.getRecipe());
		}
		try {
			getPlugin().getServer().getPluginManager().registerEvents(i, getPlugin());
		} catch (Exception e) {}
		if (i.hasLevel() && i.getName() != null) {
			Zephyrus.itemMap.put(i.getName(), i);
		}
		if (i.hasLevel()) {
			for (int n = 1; n < i.getMaxLevel(); n++) {
				ItemStack item = i.getItem();
				new ItemUtil().setItemLevel(item, n);
				ItemStack item2 = i.getItem();
				int n2 = n;
				new ItemUtil().setItemLevel(item2, n2 + 1);
				Merchant m = new Merchant();
				m.addOffer(item, new ItemStack(Material.EMERALD, n),
						item2);
				Zephyrus.merchantMap.put(item, m);
			}
		}
	}
	
	/**
	 * Registers the specified enchantment
	 * @param e
	 */
	public static void registerEnchantment(CustomEnchantment e) {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(e);
		} catch (Exception ex) {
		}
		Bukkit.getPluginManager().registerEvents(e, Zephyrus.getPlugin());
	}
	
}