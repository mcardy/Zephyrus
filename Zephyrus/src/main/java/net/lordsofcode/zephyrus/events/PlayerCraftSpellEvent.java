package net.lordsofcode.zephyrus.events;

import net.lordsofcode.zephyrus.api.ISpell;

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

public class PlayerCraftSpellEvent extends Event implements Cancellable {

	private boolean cancelled;
	private Player player;
	private ISpell spell;

	public PlayerCraftSpellEvent(Player player, ISpell spell) {
		this.player = player;
		this.spell = spell;
	}

	/**
	 * Gets the player who is crafting the spell
	 * 
	 * @return The Crafter
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the spell that is being crafted
	 * 
	 * @return The spell
	 */
	public ISpell getSpell() {
		return spell;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
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
