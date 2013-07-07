package net.lordsofcode.zephyrus.spells;

import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Steal extends Spell {

	public Steal(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "steal";
	}

	@Override
	public String bookText() {
		return "Allows you to steal from another player though there is a chance of you getting caught...";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player, String[] args) {
		Player target = (Player) getTarget(player);
		player.openInventory(target.getInventory());
		playerMap.add(player.getName());
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		Entity e = getTarget(player);
		if (e != null && e instanceof Player) {
			return true;
		}
		return false;
	}
	
	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

	@Override
	public SpellType type() {
		return null;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		Player target = (Player) getTarget(player);
		target.sendMessage(ChatColor.RED + player.getName() + " is attempting to pickpocket you!");
		player.sendMessage(ChatColor.RED + "You have been caught pickpocketing!");
		return true;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (playerMap.contains(e.getWhoClicked().getName())) {
			if (e.getInventory().equals(e.getWhoClicked().getInventory())) {
				e.getWhoClicked().closeInventory();
			}
		}
	}

}
