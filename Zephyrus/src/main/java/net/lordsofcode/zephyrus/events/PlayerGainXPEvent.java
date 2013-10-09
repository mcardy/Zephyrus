package net.lordsofcode.zephyrus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerGainXPEvent extends Event implements Cancellable {

	private boolean cancelled;
	private int amount;
	private Player player;
	
	public PlayerGainXPEvent(Player player, int amount) {
		this.player = player;
		this.amount = amount;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Gets the player who is gaining XP
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets the amount that the player will gain
	 */
	public int getAmount() {
		return amount;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
