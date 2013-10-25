package net.lordsofcode.zephyrus.events;

import net.lordsofcode.zephyrus.api.ISpell;

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

public class PlayerPostCastSpellEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private ISpell spell;

	public PlayerPostCastSpellEvent(Player player, ISpell spell) {
		this.player = player;
		this.spell = spell;
	}

	/**
	 * Gets the player who cast the spell
	 * 
	 * @return The player who cast the spell
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets the spell that was cast
	 * 
	 * @return The spell that was casts
	 */
	public ISpell getSpell() {
		return spell;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
