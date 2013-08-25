package net.lordsofcode.zephyrus.example;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.PlayerCraftCustomItemEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 *          This class is an example of how to use some of the other features of
 *          Zephyrus' API. See the events package to see all of the events added
 *          by Zephyrus.
 * 
 */

public class ExampleEventHandler implements Listener {

	@EventHandler
	public void onSpellCast(PlayerCraftCustomItemEvent e) {
		// In this event we are going to drain mana out of the user when they craft a magic item
		// Gets the Zephyrus user of the crafter
		IUser user = Zephyrus.getUser(e.getPlayer());
		// If the user does not have 50 or more mana, then the item cannot be crafted, otherwise the mana is drained.
		if (user.getMana() < 50) {
			e.setCancelled(true);
		} else {
			user.drainMana(50);
		}
	}

}
