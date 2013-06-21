package minnymin3.zephyrus.items;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.utils.ItemUtil;

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

public abstract class CustomItem extends ItemUtil implements Listener {

	public CustomItem(Zephyrus plugin) {
		super(plugin);
		if (recipe() != null) {
			plugin.getServer().addRecipe(recipe());
		}
		try {
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		} catch (Exception e) {}
		if (hasLevel() && this.name() != null) {
			Zephyrus.itemMap.put(this.name(), this);
		}
	}

	/**
	 * The displayname of the item
	 * @return The displayname of the item
	 */
	public abstract String name();

	/**
	 * The item (with displayname, lore, etc.)
	 * @return The item (with displayname, lore, etc.)
	 */
	public abstract ItemStack item();

	/**
	 * The custom recipe for the item
	 * @return The custom recipe for the item
	 */
	public abstract Recipe recipe();
	
	/**
	 * The maximum level of a custom item
	 * @return
	 */
	public abstract int maxLevel();

	/**
	 * Weather or not the custom item should have a level
	 * @return Weather or not the custom item should have a level
	 */
	public boolean hasLevel() {
		return true;
	}

	/**
	 * The required level of the player to craft the item
	 * @return The required level of the player to craft the item
	 */
	public int reqLevel() {
		return 0;
	}
	
	/**
	 * The permission that this item requires to craft
	 * @return The permission for the item
	 */
	public abstract String perm();
}
