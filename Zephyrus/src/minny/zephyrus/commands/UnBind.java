package minny.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

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

public class UnBind extends ZephyrusCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (hasPerm(sender, "zephyrus.bind")) {
				if (player.getItemInHand() != null
						&& player.getItemInHand().hasItemMeta()
						&& player.getItemInHand().getItemMeta()
								.hasDisplayName()
						&& player.getItemInHand().getItemMeta()
								.getDisplayName().contains("¤6Wand")) {
					if (player.getItemInHand().getItemMeta().getLore().get(0) != ChatColor.GRAY
							+ "Regular old default wand") {
						ItemStack i = player.getItemInHand();
						List<String> list = new ArrayList<String>();
						list.add(ChatColor.GRAY + "Regular old default wand");
						ItemMeta m = i.getItemMeta();
						m.setLore(list);
						i.setItemMeta(m);
						player.sendMessage("Unbound the spell from your wand!");
					} else {
						sender.sendMessage("There is no spell bound to that wand!");
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED
							+ "You need to be holding a wand!");
				}
			} else {
				needOp(sender);
			}
		} else {
			inGameOnly(sender);
		}

		return true;
	}

}
