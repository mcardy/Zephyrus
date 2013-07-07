package net.lordsofcode.zephyrus.utils;

import net.minecraft.server.v1_6_R1.EntityHuman;
import net.minecraft.server.v1_6_R1.IMerchant;
import net.minecraft.server.v1_6_R1.MerchantRecipe;
import net.minecraft.server.v1_6_R1.MerchantRecipeList;

import org.bukkit.craftbukkit.v1_6_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_6_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Merchant implements IMerchant {

	private MerchantRecipeList recipes = new MerchantRecipeList();
	private transient EntityHuman human;
	private ItemStack[] items = new ItemStack[3];

	/**
	 * Adds a new trade to the merchants
	 * @param i1 Input one
	 * @param i2 Input two
	 * @param i3 Result
	 */
	public void addOffer(ItemStack i1, ItemStack i2, ItemStack i3) {
		items[0] = i1;
		items[1] = i2;
		items[2] = i3;
		a(new MerchantRecipe(CraftItemStack.asNMSCopy(i1),
				CraftItemStack.asNMSCopy(i2), CraftItemStack.asNMSCopy(i3)));
	}
	
	/**
	 * Adds a trade to the merchant
	 * 
	 * @param recipe
	 *            The MerchantRecipe to add
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void a(MerchantRecipe recipe) {
		recipes.add(recipe);
	}

	/**
	 * Gets the list of recipes for the Merchant
	 */
	@Override
	public MerchantRecipeList getOffers(EntityHuman arg0) {
		return recipes;
	}
	
	/**
	 * Sets the offer for the merchant
	 * @param list The MerchantRecipeList to add
	 */
	public void setOffers(MerchantRecipeList list) {
		recipes = list;
	}

	/**
	 * Gets the HumanEntity associated with the merchant
	 */
	@Override
	public EntityHuman m_() {
		return human;
	}

	/**
	 * Opens the trade for the given player
	 * @param player The player to send the trade to
	 */
	public void openTrade(Player player) {
		human = ((CraftPlayer) player).getHandle();
		human.openTrade(this, "Arcane Leveler");
	}

	/**
	 * Opens the trade for the given EntityHuman
	 * @param h The EntityHuman to send the trade to
	 */
	public void openTrade(EntityHuman h) {
		human = h;
		human.openTrade(this, "Arcane Leveler");
	}
	
	/**
	 * Gets the list of items for this merchant
	 * @return An itemstack array containing the trade items
	 */
	public ItemStack[] getItems() {
		return this.items;
	}

	/**
	 * Gets the first input for the trade
	 * @return The first input itemstack
	 */
	public ItemStack getInput1() {
		return items[0];
	}

	/**
	 * Gets the second input for the trade
	 * @return The second input itemstack
	 */
	public ItemStack getInput2() {
		return items[1];
	}

	/**
	 * Gets the output input for the trade
	 * @return The output input itemstack
	 */
	public ItemStack getOutput() {
		return items[2];
	}
	
	/**
	 * Clones the merchant and returns a new instance with the same trades
	 */
	@Override
	public Merchant clone() {
		Merchant mer = new Merchant();
		mer.addOffer(getInput1(), getInput2(), getOutput());
		return mer;
	}

	@Override
	public void a_(EntityHuman arg0) {
		this.human = arg0;
	}

	@Override
	public void a_(net.minecraft.server.v1_6_R1.ItemStack arg0) {
	}

}
