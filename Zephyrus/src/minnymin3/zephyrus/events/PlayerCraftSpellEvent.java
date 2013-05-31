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

public class PlayerCraftSpellEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private Player player;
	private Spell spell;
	
	public PlayerCraftSpellEvent(Player player, Spell spell) {
		this.player = player;
		this.spell = spell;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Spell getSpell() {
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

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
