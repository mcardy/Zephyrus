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

public class PlayerLevelUpEvent extends Event {
	
	private Player player;
	private int level;
	
	public PlayerLevelUpEvent(Player player, int level) {
		this.player = player;
		this.level = level;
	}
	
	/**
	 * Gets the player who is leveling up
	 * @return The leveling player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets the level that the player is leveling up to
	 * @return An int
	 */
	public int getLevel() {
		return level;
	}

	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
