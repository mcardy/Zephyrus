package minnymin3.zephyrus.events;

import java.util.List;

import minnymin3.zephyrus.items.CustomItem;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerCraftCustomItemEvent extends Event implements Cancellable {

	private boolean cancelled;
	private List<HumanEntity> player;
	private CustomItem item;
	private PrepareItemCraftEvent e;
	
	public PlayerCraftCustomItemEvent(List<HumanEntity> player, CustomItem item, PrepareItemCraftEvent e) {
		this.player = player;
		this.item = item;
		this.e = e;
	}
	
	public List<HumanEntity> getPlayers() {
		return player;
	}
	
	public PrepareItemCraftEvent getPrepareItemCraft() {
		return e;
	}
	
	public CustomItem getItem() {
		return item;
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
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
