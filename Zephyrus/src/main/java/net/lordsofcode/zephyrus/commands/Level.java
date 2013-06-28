package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.player.LevelManager;
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

	LevelManager lvl;
	Zephyrus plugin;

	public Level(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (sender.hasPermission("zephyrus.level") || sender.isOp()) {
					Player player = (Player) sender;
					lvl.displayLevel(player);
				} else {
					Lang.errMsg("noperm", sender);
				}
			} else {
				if (sender.hasPermission("zephyrus.level.other") || sender.isOp()) {
					if (isOnline(args[0])) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						lvl.displayLevel(target);
					} else {
						Lang.errMsg("notonline", sender);
					}
				} else {
					Lang.errMsg("noperm", sender);
				}
			}
		} else {
			//TODO view player's level from console
			Lang.errMsg("ingameonly", sender);
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
