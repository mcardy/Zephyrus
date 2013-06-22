package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Enderchest extends Spell {

	public Enderchest(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "enderchest";
	}

	@Override
	public String bookText() {
		return "Your very own portable enderchest!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		Inventory i = player.getEnderChest();
		player.openInventory(i);
		player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
		player.sendMessage(ChatColor.DARK_PURPLE + "The inventories of Ender appear at your command");
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ENDER_CHEST));
		return items;
	}

	@Override
	public SpellType type() {
		return SpellType.OTHER;
	}
}
