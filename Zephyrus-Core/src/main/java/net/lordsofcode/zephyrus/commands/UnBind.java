package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItemWand;
import net.lordsofcode.zephyrus.utils.Lang;

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
				ItemStack item = player.getItemInHand();
				if (Zephyrus.getItemManager().isWand(item)) {
					ICustomItemWand wand = Zephyrus.getItemManager().getWand(item);
					if (wand.getCanBind()) {
						ItemMeta m = item.getItemMeta();
						m.setDisplayName(wand.getDisplayName());
						m.setLore(wand.getDefaultLore());
						item.setItemMeta(m);
						Lang.msg("unbind.unbound", sender);
					} else {
						Lang.msg("unbind.nospell", sender);
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
