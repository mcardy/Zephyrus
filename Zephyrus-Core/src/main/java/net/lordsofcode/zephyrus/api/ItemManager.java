package net.lordsofcode.zephyrus.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.items.ItemUtil;
import net.lordsofcode.zephyrus.nms.ITrader;
import net.lordsofcode.zephyrus.nms.NMSHandler;
import net.lordsofcode.zephyrus.utils.ConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ItemManager extends ItemUtil implements Listener {

	private Set<ICustomItem> itemMap;
	private Set<ICustomItemWand> wandMap;
	private Map<String, Map<String, Integer>> itemDelay;
	private ConfigHandler itemConfig;
	private Map<ItemStack, ITrader> merchantMap;
	private Map<String, ITrader> invPlayers;
	
	public static boolean trade = NMSHandler.getTrader() != null;
	
	public ItemManager() {
		this.itemMap = new HashSet<ICustomItem>();
		this.wandMap = new HashSet<ICustomItemWand>();
		this.itemDelay = new HashMap<String, Map<String, Integer>>();
		this.itemConfig = new ConfigHandler("items.yml");
		this.merchantMap = new HashMap<ItemStack, ITrader>();
		this.invPlayers = new HashMap<String, ITrader>();
		if (!trade) {
			Zephyrus.getPlugin().getLogger()
					.warning("This version of Bukkit is unsupported! Some features are disabled.");
		}
	}

	public void addItem(ICustomItem item) {
		if (item instanceof ICustomItemWand) {
			wandMap.add((ICustomItemWand) item);
		}
		itemMap.add(item);
		if (!itemConfig.getConfig().contains(item.getConfigName() + ".enabled")) {
			itemConfig.getConfig().set(item.getConfigName() + ".enabled", true);
			itemConfig.saveConfig();
		}
		if (!itemConfig.getConfig().contains(item.getConfigName() + ".displayname")) {
			itemConfig.getConfig().set(item.getConfigName() + ".displayname",
					item.getName().replace(ChatColor.COLOR_CHAR + "", "$"));
			itemConfig.saveConfig();
		}
		if (itemConfig.getConfig().getBoolean(item.getConfigName() + ".enabled")) {
			if (item.getRecipe() != null) {
				Zephyrus.getPlugin().getServer().addRecipe(item.getRecipe());
			}
			try {
				Zephyrus.getPlugin().getServer().getPluginManager().registerEvents(item, Zephyrus.getPlugin());
			} catch (Exception e) {
			}
			if (trade) {
				if (item.hasLevel()) {
					for (int n = 1; n < item.getMaxLevel(); n++) {
						ItemStack itemstack = item.getItem();
						new ItemUtil().setItemLevel(itemstack, n);
						ItemStack itemstack2 = item.getItem();
						int n2 = n;
						new ItemUtil().setItemLevel(itemstack2, n2 + 1);
						ITrader m = NMSHandler.getTrader();
						m.addOffer(itemstack, new ItemStack(Material.EMERALD, n), itemstack2);
						merchantMap.put(itemstack, m);
					}
				}
			}
		}
	}
	
	/**
	 * Gets the ItemManager's item map
	 * 
	 * @return The Item's name and the CustomItem object
	 */
	public Set<ICustomItem> getItemMap() {
		return this.itemMap;
	}
	
	/**
	 * Gets the item delay map
	 * 
	 * @return
	 */
	public Map<String, Map<String, Integer>> getDelayMap() {
		return itemDelay;
	}
	
	/**
	 * Gets the custom item trading map
	 * 
	 * @return
	 */
	public Map<ItemStack, ITrader> getTradeMap() {
		return merchantMap;
	}

	/**
	 * Gets the custom item merchant map
	 * 
	 * @return
	 */
	public Map<String, ITrader> getMerchantMap() {
		return invPlayers;
	}

	public boolean isLevellableItem(ItemStack i) {
		return getCustomItem(i).hasLevel();
	}
	
	public boolean isCustomItem(ItemStack i) {
		return getCustomItem(i) != null;
	}
	
	public ICustomItem getCustomItem(ItemStack i) {
		for (ICustomItem c : this.itemMap) {
			if (checkItem(c, i)) {
				return c;
			}
		}
		return null;
	}
	
	public boolean checkItem(ICustomItem c, ItemStack i) {
		return checkContainsName(i, c.getItem().getItemMeta().getDisplayName());
	}
	
	public boolean isWand(ItemStack i) {
		return getCustomItem(i) instanceof ICustomItemWand;
	}
	
	public ICustomItemWand getWand(ItemStack i) {
		return (ICustomItemWand) getCustomItem(i);
	}
	
	public Set<ICustomItemWand> getWandMap() {
		return wandMap;
	}
	
	
}
