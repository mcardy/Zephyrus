package net.lordsofcode.zephyrus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ManaChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private int amount;

	public ManaChangeEvent(Player player, int amount) {
		this.player = player;
		this.amount = amount;
	}

	/**
	 * Gets the player who's mana was drained
	 * 
	 * @return The player who cast the spell
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets the amount of mana that was drained
	 * 
	 * @return The amount of mana drained
	 */
	public int getAmount() {
		return amount;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
