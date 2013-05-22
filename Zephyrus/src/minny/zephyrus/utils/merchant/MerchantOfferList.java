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
package minny.zephyrus.utils.merchant;

import java.util.ArrayList;

import net.minecraft.server.v1_5_R3.MerchantRecipe;
import net.minecraft.server.v1_5_R3.MerchantRecipeList;

public final class MerchantOfferList extends ArrayList<MerchantOffer> {
	private static final long serialVersionUID = 7856998541433225645L;

	/**
	 * Creates a offer list from the origional nms recipe list. (Only used by the api.)
	 */
	protected MerchantOfferList(MerchantRecipeList handle) {
		for (Object r : handle) {
			this.add(new MerchantOffer((MerchantRecipe) r));
		}
	}

	/**
	 * Returns the original recipe list. (Only used by the api.)
	 */
	@SuppressWarnings("unchecked")
	protected MerchantRecipeList getHandle() {
		MerchantRecipeList list = new MerchantRecipeList();

		for (MerchantOffer o : this) {
			list.add(o.getHandle());
		}

		return list;
	}
}