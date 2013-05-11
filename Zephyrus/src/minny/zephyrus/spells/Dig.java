package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Dig extends Spell {

	PluginHook hook;

	public Dig(Zephyrus plugin) {
		super(plugin);
		hook = new PluginHook();
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
	public void run(Player player) {
		player.getTargetBlock(null, 12).breakNaturally(
				new ItemStack(Material.DIAMOND_PICKAXE));
	}

	@Override
	public boolean canRun(Player player) {
		if (player.getTargetBlock(null, 12).getType() != Material.BEDROCK) {
			if (hook.worldGuard()) {
				hook.wgHook();
				if (hook.wg.canBuild(player, player.getTargetBlock(null, 12))
						&& player.getTargetBlock(null, 12).getType() != Material.AIR) {
					return true;
				} else if (player.getTargetBlock(null, 12).getType() != Material.AIR) {
					player.sendMessage(ChatColor.DARK_RED
							+ "You don't have permission for this area");
					return false;
				}
				player.sendMessage(ChatColor.GRAY
						+ "That block is out of range!");
				return false;
			}
			if (player.getTargetBlock(null, 12).getType() != Material.AIR) {
				return true;
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
