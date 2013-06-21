package minnymin3.zephyrus.events;

import minnymin3.zephyrus.spells.Spell;

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

public class PlayerCastSpellEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private Player player;
	private Spell spell;
	private String[] args;

	public PlayerCastSpellEvent(Player player, Spell spell, String[] args) {
		this.player = player;
		this.spell = spell;
		this.args = args;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Gets the player who cast the spell
	 * @return The player who cast the spell
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the spell that was cast
	 * @return The spell that was cast
	 */
	public Spell getSpell() {
		return spell;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	/**
	 * Gets the arguments used to cast the spell. Null if cast from a wand or a spellbook
	 * @return
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * Gets the cancellation state of this event
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancellation state of this event
	 */
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
