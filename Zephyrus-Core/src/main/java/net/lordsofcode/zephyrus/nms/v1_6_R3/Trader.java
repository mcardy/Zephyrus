package net.lordsofcode.zephyrus.nms.v1_6_R3;

import net.lordsofcode.zephyrus.nms.ITrader;
import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.IMerchant;
import net.minecraft.server.v1_6_R3.MerchantRecipe;
import net.minecraft.server.v1_6_R3.MerchantRecipeList;

import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

class Trader implements IMerchant, ITrader {

	private MerchantRecipeList recipes = new MerchantRecipeList();
	private transient EntityHuman human;
	private ItemStack[] items = new ItemStack[3];

	@SuppressWarnings("unchecked")
	@Override
	public void a(MerchantRecipe recipe) {
		recipes.add(recipe);
	}

	@Override
	public MerchantRecipeList getOffers(EntityHuman arg0) {
		return recipes;
	}

	@Override
	public EntityHuman m_() {
		return human;
	}
	
	@Override
	public void a_(EntityHuman arg0) {
		this.human = arg0;
	}

	@Override
	public void a_(net.minecraft.server.v1_6_R3.ItemStack arg0) {
	}

	@Override
	public void addOffer(ItemStack i1, ItemStack i2, ItemStack i3) {
		items[0] = i1;
		items[1] = i2;
		items[2] = i3;
		a(new MerchantRecipe(CraftItemStack.asNMSCopy(i1), CraftItemStack.asNMSCopy(i2), CraftItemStack.asNMSCopy(i3)));
	}
	
	@Override
	public void openTrade(Player player) {
		human = ((CraftPlayer) player).getHandle();
		human.openTrade(this, "Arcane Leveler");
	}
	
	@Override
	public ItemStack getInput1() {
		return items[0];
	}
	
	@Override
	public ItemStack getInput2() {
		return items[1];
	}
	
	@Override
	public ItemStack getOutput() {
		return items[2];
	}
	
	@Override
	public Trader clone() {
		Trader mer = new Trader();
		mer.addOffer(getInput1(), getInput2(), getOutput());
		return mer;
	}
	
}
