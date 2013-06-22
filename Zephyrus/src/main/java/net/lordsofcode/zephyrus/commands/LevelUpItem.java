package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.items.CustomItem;
import net.lordsofcode.zephyrus.utils.ItemUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LevelUpItem extends ZephyrusCommand implements CommandExecutor {

	ItemUtil item;
	Zephyrus plugin;

	public LevelUpItem(Zephyrus plugin) {
		item = new ItemUtil(plugin);
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("zephyrus.levelup.item") || player.isOp()) {
				if (Zephyrus.itemMap.containsKey(player.getItemInHand()
						.getItemMeta().getDisplayName())) {
					CustomItem i = Zephyrus.itemMap.get(player.getItemInHand()
							.getItemMeta().getDisplayName());
					if (item.getItemLevel(player.getItemInHand()) < i
							.maxLevel()) {
						int current = item.getItemLevel(player.getItemInHand());
						item.setItemLevel(player.getItemInHand(), current + 1);
						sender.sendMessage("You have leveled up the "
								+ i.name());
					} else {
						player.sendMessage(ChatColor.DARK_RED + "That item cannot be leveled anymore!");
					}
				} else {
					player.sendMessage(ChatColor.DARK_RED + "The item you are holding cannot be leveled!");
				}
			} else {
				needOp(sender);
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
}
