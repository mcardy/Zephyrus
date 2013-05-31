package minnymin3.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Dig extends Spell {

	public Dig(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "dig";
	}

	@Override
	public String bookText() {
		return "Digs for you. Max range of 12 blocks";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 1;
	}

	@Override
	public void run(Player player, String[] args) {
		player.getTargetBlock(null, 12).breakNaturally(
				new ItemStack(Material.DIAMOND_PICKAXE));
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (player.getTargetBlock(null, 12).getType() != Material.BEDROCK) {
			if (PluginHook.canBuild(player, player.getTargetBlock(null, 12))
					&& player.getTargetBlock(null, 12).getType() != Material.AIR) {
				return true;
			} else if (player.getTargetBlock(null, 12).getType() != Material.AIR) {
				player.sendMessage(ChatColor.DARK_RED
						+ "You don't have permission for this area");
				return false;
			}
			player.sendMessage(ChatColor.GRAY + "That block is out of range!");
			return false;
		} else {
			player.sendMessage(ChatColor.GRAY + "You can't break bedrock!");
			return false;
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_PICKAXE));
		return i;
	}

}
