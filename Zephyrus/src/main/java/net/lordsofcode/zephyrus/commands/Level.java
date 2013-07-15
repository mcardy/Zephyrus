package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
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

public class Level implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (sender.hasPermission("zephyrus.level") || sender.isOp()) {
					Player player = (Player) sender;
					Zephyrus.getUser(player).displayLevel(player);
				} else {
					Lang.errMsg("noperm", sender);
				}
			} else {
				if (sender.hasPermission("zephyrus.level.other") || sender.isOp()) {
					if (isOnline(args[0])) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						Zephyrus.getUser(target).displayLevel(sender);
					} else {
						Lang.errMsg("notonline", sender);
					}
				} else {
					Lang.errMsg("noperm", sender);
				}
			}
		} else {
			if (args.length == 0) {
				sender.sendMessage("Specify a target player!");
				return true;
			}
			if (isOnline(args[0])) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				Zephyrus.getUser(target).displayLevel(sender);
			} else {
				Lang.errMsg("notonline", sender);
			}
		}
		return false;
	}
	
	public boolean isOnline(String player) {
		Player target = (Bukkit.getServer().getPlayer(player));
		if (target == null) {
			return false;
		}
		return true;
	}

}
