package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
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

public class ManaCommand implements CommandExecutor {

	public ManaCommand() {
		Lang.add("mana.restored", "Your magical powers feel restored!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (sender.hasPermission("zephyrus.mana") || sender.isOp()) {
					Player player = (Player) sender;
					Zephyrus.getUser(player).displayMana(player);
				} else {
					Lang.errMsg("noperm", sender);
				}
			} else {
				if (args[0].equalsIgnoreCase("restore")) {
					if (args.length < 2) {
						if (sender.hasPermission("zephyrus.mana.restore") || sender.isOp()) {
							Player player = (Player) sender;
							Zephyrus.getUser(player).reLoadMana();
							player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
						} else {
							Lang.errMsg("noperm", sender);
						}
					} else {
						if (isOnline(args[1])) {
							if (isOnline(args[1])) {
								Player player = Bukkit.getServer().getPlayer(args[1]);
								Zephyrus.getUser(player).reLoadMana();
								player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
							} else {
								Lang.errMsg("notonline", sender);
							}
						} else {
							Lang.errMsg("notonline", sender);
						}
					}
				} else if (args[0].equalsIgnoreCase("display")) {
					IUser user = Zephyrus.getUser((Player)sender);
					user.setDisplayMana(!user.getDisplayMana());
					user.drainMana(0);
				} else if (sender.hasPermission("zephyrus.mana.other") || sender.isOp()) {
					if (isOnline(args[0])) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						Zephyrus.getUser(target).displayMana(sender);
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
			if (args[0].equalsIgnoreCase("restore")) {
				if (args.length < 2) {
					sender.sendMessage("Specify a target player!");
				} else {
					if (isOnline(args[1])) {
						Player player = Bukkit.getServer().getPlayer(args[1]);
						Zephyrus.getUser(player).reLoadMana();
						player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
					} else {
						Lang.errMsg("notonline", sender);
					}
				}
			} else if (sender.hasPermission("zephyrus.mana.other") || sender.isOp()) {
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