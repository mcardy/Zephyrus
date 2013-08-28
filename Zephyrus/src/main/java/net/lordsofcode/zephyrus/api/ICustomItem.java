package net.lordsofcode.zephyrus.api;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ICustomItem extends Listener {

	/**
	 * Gets the internal or base name of the item
	 */
	public String getName();
	
	/**
	 * Gets the display name of the item. Usually used to get the name from a config file
	 */
	public String getDisplayName();
	
	/**
	 * Gets the ItemStack with lore, enchantments, etc.
	 */
	public ItemStack getItem();
	
	/**
	 * Gets the item's recipe
	 */
	public Recipe getRecipe();
	
	/**
	 * Gets the maximum allowed level
	 */
	public int getMaxLevel();
	
	/**
	 * Gets whether or not this item should be levellable
	 */
	public boolean hasLevel();
	
	/**
	 * Gets the required level to craft this item
	 */
	public int getReqLevel();
	
	/**
	 * Gets the permission node required to craft this item
	 * @return
	 */
	public String getPerm();
	
	/**
	 * Gets the name of the item used in configs
	 */
	public String getConfigName();
}
