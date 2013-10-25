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
	 * @return
	 */
	public int getPower();
	
	/**
	 * Gets the percentage mana discount
	 * @return
	 */
	public int getManaDiscount();
	
	public boolean getCanBind();
	
	public String getSpell(ItemStack i);
	
	public List<String> getDefaultLore();
	
	public List<String> getBoundLore(ISpell spell);
	
	public String getBoundName(ISpell spell);
	
}
