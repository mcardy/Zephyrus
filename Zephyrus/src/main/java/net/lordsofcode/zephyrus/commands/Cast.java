package net.lordsofcode.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Cast implements CommandExecutor, TabCompleter {

	public Cast() {
		Lang.add("cast.nospell", "Specify a spell to cast!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("zephyrus.cast")) {
				Lang.errMsg("noperm", sender);
				return true;
			}
			if (args.length < 1) {
				Lang.errMsg("cast.nospell", sender);
			} else {
				if (Zephyrus.getSpellMap().containsKey(args[0])) {
					Player player = (Player) sender;
					IUser user = Zephyrus.getUser(player);
					ISpell spell = Zephyrus.getSpellMap()
							.get(args[0].toLowerCase());
					if (user.isLearned(spell) || user.hasPermission(spell)) {
						if (user.hasMana(spell.getManaCost())) {
							PlayerCastSpellEvent event = new PlayerCastSpellEvent(
									player, spell, args);
							Bukkit.getServer().getPluginManager()
									.callEvent(event);
							if (!event.isCancelled()) {
								boolean b = spell.run(player, args);
								if (b) {
									user.drainMana(spell.getManaCost());
								}
							}
						} else {
							Lang.errMsg("nomana", sender);
						}
					} else {
						Lang.errMsg("notlearned", sender);
					}

				} else {
					Lang.errMsg("notlearned", sender);
				}
			}
		} else {
			Lang.errMsg("ingameonly", sender);
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		List<String> list = learned(sender);
		if (args.length == 0) {
			return list;
		}
		String cmd = args[0];
		List<String> newList = new ArrayList<String>();
		for (String s : list) {
			if (s.startsWith(cmd.toLowerCase())) {
				newList.add(s);
			}
		}
		return newList;
	}

	public List<String> learned(CommandSender p) {
		Player player = (Player) p;
		return PlayerConfigHandler.getConfig(player).getStringList("learned");
	}
}
