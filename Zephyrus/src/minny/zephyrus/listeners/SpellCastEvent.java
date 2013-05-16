package minny.zephyrus.listeners;

import minny.zephyrus.spells.Spell;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
;
 
public class SpellCastEvent extends Event implements Cancellable{
	
    private static final HandlerList handlers = new HandlerList();
 
    private boolean cancelled;
    private Player player;
    private Spell spell;
    
    public SpellCastEvent(Player player, Spell spell) {
    	this.player = player;
    	this.spell = spell;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public Player getPlayer() {
    	return player;
    }
    
    public Spell getSpell() {
    	return spell;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
