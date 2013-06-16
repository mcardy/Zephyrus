package minnymin3.zephyrus.commands;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.player.LevelManager;

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

public class ManaCommand extends ZephyrusCommand implements CommandExecutor {

	LevelManager lvl;
	Zephyrus plugin;

	public ManaCommand(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (hasPerm(sender, "zephyrus.mana")) {
					Player player = (Player) sender;
					lvl.displayMana(player);
				} else {
					needOp(sender);
				}
			} else {
				if (args[0].equalsIgnoreCase("restore")) {
					if (hasPerm(sender, "zephyrus.mana.restore")) {
						Player player = (Player) sender;
						LevelManager.resetMana(player);
						player.sendMessage(ChatColor.DARK_AQUA + "Mana restored!");
					} else {
						needOp(sender);
					}
				} else if (hasPerm(sender, "zephyrus.mana.other")) {
					if (isOnline(args[0])) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						lvl.displayMana(target);
					} else {
						notOnline(sender);
					}
				} else {
					needOp(sender);
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}
