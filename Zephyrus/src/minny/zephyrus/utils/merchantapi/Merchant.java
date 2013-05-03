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

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Merchant implements Serializable {
	private static final long serialVersionUID = -7657859047817139872L;
	private NMSMerchant h;
	
	/**
	 * Basicly just creating the nms merchant.
	 */
	public Merchant() {
		this.h = new NMSMerchant();
	}

	/**
	 * Adds a new offer.
	 * @param offer The offer which should be added.
	 */
	public Merchant addOffer(MerchantOffer offer) {
		this.h.a(offer.getHandle());
		return this;
	}

	/**
	 * Adding mutliple offers in one line.
	 * @param offers The offers.
	 */
	public Merchant addOffers(MerchantOffer... offers) {
		for (MerchantOffer o : offers) {
			this.addOffer(o);
		}

		return this;
	}
	
	/**
	 * Setting the new offers.
	 * @param offers The new offers.
	 */	
	public Merchant setOffers(MerchantOfferList offers) {
		this.h.setRecipes(offers.getHandle());
		return this;
	}

	/**
	 * Getting the offers.
	 * @return The offers.
	 */
	public MerchantOfferList getOffers() {
		return new MerchantOfferList(this.h.getOffers(null));
	}

	/**
	 * Is there a customer?
	 * @return If there is a customer.
	 */
	public boolean hasCustomer() {
		return this.h.m_() != null;
	}

	/**
	 * Returns the customer.
	 * @return The current customer.
	 */
	public Player getCustomer() {
		return (Player) (this.h.m_() == null ? null : this.h.m_().getBukkitEntity());
	}

	/**
	 * Sets the customer.
	 * @param player The customer.
	 */
	public Merchant setCustomer(Player player) {
		this.h.a(player == null ? null : ((CraftPlayer) player).getHandle());
		return this;
	}

	/**
	 * Opens the trading gui, but with the given name.
	 * @param player The player who should open the gui.
	 */
	public void openTrading(Player player, String name) {
		this.h.openTrading(((CraftPlayer) player).getHandle(), name);
	}

	/**
	 * Opens the trading gui.
	 * @param player The player who should open the gui.
	 */
	public void openTrading(Player player) {
		this.openTrading(player, "Arcane Leveler");
	}

	/**
	 * Returns the origional merchant. (Only used by the api.)
	 */
	protected NMSMerchant getHandle() {
		return this.h;
	}

	/**
	 * Returns a copy of the merchant.
	 * @return The copy of the merchant.
	 */
	public Merchant clone() {
		return new Merchant().setOffers(this.getOffers()).setCustomer(this.getCustomer());
	}
}