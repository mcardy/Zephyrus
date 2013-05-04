package minny.zephyrus.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandExceptions {

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
