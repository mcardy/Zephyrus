package net.lordsofcode.zephyrus.commands;

import org.bukkit.Bukkit;
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

}
