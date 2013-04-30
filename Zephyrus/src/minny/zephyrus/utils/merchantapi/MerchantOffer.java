/**
 * 
 * This software is part of the MerchantAPI
 * 
 * @author Seppe Volkaerts (Cybermaxke)
 * 
 * This api allows plugin developers to create on a easy way new
 * merchants they can open without villagers and adding as many 
 * offers they want.
 * 
 * MerchantAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 *  
 * MerchantAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MerchantAPI. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package minny.zephyrus.utils.merchantapi;

import java.io.Serializable;

import net.minecraft.server.v1_5_R2.MerchantRecipe;

import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public final class MerchantOffer implements Serializable {
	private static final long serialVersionUID = -2230848596793706680L;
	private ItemStack[] items = new ItemStack[3];

	/**
	 * Creates a new merchant offer.
	 * @param is1 The first buy item.
	 * @param is2 The second buy item. (Not required.)
	 * @param re The selling item.
	 */
	public MerchantOffer(ItemStack is1, ItemStack is2, ItemStack re) {
		this.items[0] = is1;
		this.items[1] = is2;
		this.items[2] = re;
	}

	/**
	 * Creates a new merchant offer.
	 * @param is1 The first buy item.
	 * @param re The selling item.
	 */
	public MerchantOffer(ItemStack is, ItemStack re) {
		this(is, null, re);
	}

	/**
	 * Create a offer from the origional offer recipe. (Only used by the api.)
	 * @param handle The offer.
	 */
	protected MerchantOffer(MerchantRecipe handle) {
		this.items[0] = CraftItemStack.asBukkitCopy(handle.getBuyItem1());
		this.items[1] = handle.getBuyItem3() == null ? null : CraftItemStack.asBukkitCopy(handle.getBuyItem2());
		this.items[2] = CraftItemStack.asBukkitCopy(handle.getBuyItem3());
	}

	/**
	 * Returns the origional offer recipe. (Only used by the api.)
	 * @return The offer.
	 */
	protected MerchantRecipe getHandle() {
		if (this.items[1] != null) {
			return new MerchantRecipe(CraftItemStack.asNMSCopy(this.items[0]), CraftItemStack.asNMSCopy(this.items[1]), CraftItemStack.asNMSCopy(this.items[2]));
		}

		return new MerchantRecipe(CraftItemStack.asNMSCopy(this.items[0]), CraftItemStack.asNMSCopy(this.items[2]));
	}

	/**
	 * First buy item.
	 * @return The item.
	 */
	public ItemStack getFirstInput() {
		return this.items[0];
	}

	/**
	 * Second buy item.
	 * @return The item.
	 */
	public ItemStack getSecondInput() {
		return this.items[1];
	}

	/**
	 * Selling item.
	 * @return The item.
	 */
	public ItemStack getOutput() {
		return this.items[2];
	}
}