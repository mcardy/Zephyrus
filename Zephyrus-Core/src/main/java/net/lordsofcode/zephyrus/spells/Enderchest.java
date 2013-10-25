package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

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

	@Override
	public String getName() {
		return "enderchest";
	}

	@Override
	public String getDesc() {
		return "Your very own portable enderchest!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public boolean run(Player player, String[] args, int power) {
		Inventory i = player.getEnderChest();
		player.openInventory(i);
		player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
		player.sendMessage(ChatColor.DARK_PURPLE + "The inventories of Ender appear at your command");
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ENDER_CHEST));
		return items;
	}

	@Override
	public Type getPrimaryType() {
		return Type.ILLUSION;
	}

	@Override
	public Element getElementType() {
		return Element.ENDER;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
}
