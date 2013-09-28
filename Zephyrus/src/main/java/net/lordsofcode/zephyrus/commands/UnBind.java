package net.lordsofcode.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class UnBind implements CommandExecutor {

	public UnBind() {
		Lang.add("unbind.unbound", "Spell unbound from your wand!");
		Lang.add("unbind.nospell", "There is no spell bound to that wand!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.hasPermission("zephyrus.bind")) {
				if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta()
						&& player.getItemInHand().getItemMeta().hasDisplayName()
						&& player.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Wand")) {
					if (player.getItemInHand().getItemMeta().getLore().get(0) != ChatColor.GRAY
							+ "Regular old default wand") {
						ItemStack i = player.getItemInHand();
						List<String> list = new ArrayList<String>();
						list.add(ChatColor.GRAY + "Regular old default wand");
						ItemMeta m = i.getItemMeta();
						m.setDisplayName(ChatColor.GOLD + "Wand");
						m.setLore(list);
						i.setItemMeta(m);
						Lang.msg("unbind.unbound", sender);
					} else {
						Lang.errMsg("unbind.nospell", sender);
					}
				} else {
					Lang.errMsg("bind.needwand", sender);
				}
			} else {
				Lang.errMsg("noperm", sender);
			}
		} else {
			Lang.errMsg("ingameonly", sender);
		}

		return true;
	}

}
