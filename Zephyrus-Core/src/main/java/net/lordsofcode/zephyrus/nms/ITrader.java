package net.lordsofcode.zephyrus.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ITrader {

	/**
	 * Adds a new trade to the merchants
	 * 
	 * @param i1
	 *            Input one
	 * @param i2
	 *            Input two
	 * @param i3
	 *            Result
	 */
	public void addOffer(ItemStack input1, ItemStack input2, ItemStack output);
	
	/**
	 * Opens the trade for the given player
	 * 
	 * @param player
	 *            The player to send the trade to
	 */
	public void openTrade(Player player);

	/**
	 * Gets the first input for the trade
	 * 
	 * @return The first input itemstack
	 */
	public ItemStack getInput1();
	
	/**
	 * Gets the second input for the trade
	 * 
	 * @return The second input itemstack
	 */
	public ItemStack getInput2();
	
	/**
	 * Gets the output input for the trade
	 * 
	 * @return The output itemstack
	 */
	public ItemStack getOutput();
	
	/**
	 * Clones the merchant and returns a new instance with the same trades
	 */
	public ITrader clone();
	
}
