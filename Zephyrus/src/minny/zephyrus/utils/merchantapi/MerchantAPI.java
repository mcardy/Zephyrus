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

import net.minecraft.server.v1_5_R2.EntityVillager;
import net.minecraft.server.v1_5_R2.MerchantRecipeList;
import net.minecraft.server.v1_5_R2.NBTTagCompound;

import org.bukkit.craftbukkit.v1_5_R2.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

public class MerchantAPI extends JavaPlugin {

	/**
	 * Sets all the offers of a merchant to a villager with removing the old ones.
	 * @param villager The villager.
	 * @param merchant The merchant.
	 */
	public static void setToVillager(Villager villager, Merchant merchant) {
		EntityVillager v = ((CraftVillager) villager).getHandle();

		NBTTagCompound t = new NBTTagCompound();
		v.b(t);

		MerchantRecipeList l = merchant.getOffers().getHandle();
		t.setCompound("Offers", l.a());

		v.a(t);
	}
}