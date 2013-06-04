package minnymin3.zephyrus.commands;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.player.LevelManager;

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

public class Mana extends ZephyrusCommand implements CommandExecutor {

	LevelManager lvl;
	Zephyrus plugin;

	public Mana(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (hasPerm(sender, "zephyrus.mana") || isOp(sender)) {
					Player player = (Player) sender;
					lvl.displayMana(player);
				} else {
					needOp(sender);
				}
			} else {
				if (hasPerm(sender, "zephyrus.mana.other") || isOp(sender)) {
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
