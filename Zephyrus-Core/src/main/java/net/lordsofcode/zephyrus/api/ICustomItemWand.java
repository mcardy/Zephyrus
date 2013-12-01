package net.lordsofcode.zephyrus.api;

import java.util.List;

import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ICustomItemWand extends ICustomItem {

	/**
	 * Gets the power multiplier of the wand
	 * @param spell The spell to get the power for
	 * @return The power (1 is normal)
	 */
	public int getPower(ISpell spell);
	
	/**
	 * Gets the percentage mana discount
	 * @param spell The spell to get the discount for
	 * @return The discount percentage
	 */
	public int getManaDiscount(ISpell spell);
	
	/**
	 * Gets whether or not the specified spell can be bound
	 * @param spell The spell to bind
	 * @return True if the spell can be bound
	 * 
	 */
	public boolean getCanBind(ISpell spell);
	
	/**
	 * Gets the bound spell
	 * @param i The wand to get the bound spell of
	 * @return The display name of the bound spell
	 */
	public String getSpell(ItemStack i);
	
	/**
	 * Gets the default lore of the wand
	 * @return A list of lore
	 */
	public List<String> getDefaultLore();
	
	/**
	 * Gets the lore that appears when a spell is bound
	 * @param spell The bound spell
	 * @return A list of lore
	 */
	public List<String> getBoundLore(ISpell spell);
	
	/**
	 * Gets the name of the item when a spell is bound
	 * @param spell The spell to get the name for
	 * @return The name of the item including the spell's name
	 */
	public String getBoundName(ISpell spell);
	
}
