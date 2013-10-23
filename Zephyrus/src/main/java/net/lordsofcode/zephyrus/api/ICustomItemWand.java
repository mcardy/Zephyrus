package net.lordsofcode.zephyrus.api;

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
	 * Gets the mana discount of the wand
	 * @return
	 */
	public int getManaDiscount();
	
}
