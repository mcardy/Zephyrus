package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.player.LevelManager;
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

public class ManaCommand extends ZephyrusCommand implements CommandExecutor {

	LevelManager lvl;
	Zephyrus plugin;

	public ManaCommand(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
		Lang.add("mana.restored", "Your magical powers feel restored!");
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
					Lang.errMsg("noperm", sender);
				}
			} else {
				if (args[0].equalsIgnoreCase("restore")) {
					if (args.length < 2) {
						if (hasPerm(sender, "zephyrus.mana.restore")) {
							Player player = (Player) sender;
							LevelManager.resetMana(player);
							player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
						} else {
							Lang.errMsg("noperm", sender);
						}
					} else {
						if (isOnline(args[1])) {
							//TODO restore mana to other players
						} else {
							Lang.errMsg("notonline", sender);
						}
					}
				} else if (hasPerm(sender, "zephyrus.mana.other")) {
					if (isOnline(args[0])) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						lvl.displayMana(target);
					} else {
						Lang.errMsg("notonline", sender);
					}
				} else {
					Lang.errMsg("noperm", sender);
				}
			}
		} else {
			//TODO restore and view player's mana from console
			Lang.errMsg("ingameonly", sender);
		}
		return false;
	}

}
