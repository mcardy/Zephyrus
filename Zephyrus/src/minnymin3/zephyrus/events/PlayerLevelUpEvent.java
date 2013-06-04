package minnymin3.zephyrus.events;

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

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private int level;
	
	public PlayerLevelUpEvent(Player player, int level) {
		this.player = player;
		this.level = level;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLevel() {
		return level;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
