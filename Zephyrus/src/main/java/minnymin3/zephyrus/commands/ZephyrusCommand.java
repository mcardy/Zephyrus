package minnymin3.zephyrus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ZephyrusCommand {
	
	public boolean hasPerm(CommandSender sender, String perm) {
		if (sender.hasPermission(perm) || sender.isOp()) {
			return true;
		}
		return false;
	}

	public boolean isOp(CommandSender sender) {
		if (sender.isOp()) {
			return true;
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

	public void inGameOnly(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Must be an ingame player!");
	}

	public void needOp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "You do not have permission to use this command");
	}

	public void tooMany(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Too many arguments");
	}

	public void notEnough(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Not enough arguments");
	}

	public void notOnline(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "That player is not online!");
	}

}
