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

public class LevelUp implements CommandExecutor {

	Zephyrus plugin;
	LevelManager lvl;

	public LevelUp(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
		Lang.add("levelupcmd", "You have leveled up [PLAYER]");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("zephyrus.levelup") || sender.isOp()) {
				if (args.length == 0) {
					Player player = (Player) sender;
					lvl.levelUp(player);
				} else {
					if (isOnline(args[0])) {
						Player player = Bukkit.getPlayer(args[0]);
						lvl.levelUp(player);
						sender.sendMessage(Lang.get("levelupcmd").replace("[PLAYER]", player.getName()));
					} else {
						Lang.errMsg("notonline", sender);
					}
				}
			} else {
				Lang.errMsg("noperm", sender);
			}
		} else {
			//TODO level up in-game players from console
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
