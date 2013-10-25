package net.lordsofcode.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.items.SpellTome;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class SpellTomeCmd implements CommandExecutor, TabCompleter {

	public SpellTomeCmd() {
		Lang.add("spelltomecmd.nospell", "Specify a spell to give!");
		Lang.add("spelltomecmd.noexist", "That spell does not exist!");
		Lang.add("spelltomecmd.usage", "Usage: /spelltome [spell] [player]");
		Lang.add("spelltomecmd.complete", "Gave [TARGET] the [SPELL] spelltome.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("zephyrus.spelltome.give") || sender.isOp()) {
			if (args.length < 1) {
				Lang.errMsg("spelltomecmd", sender);
			} else {
				if (args.length < 2) {
					if (sender instanceof Player) {
						if (Zephyrus.getSpellMap().containsKey(args[0].toLowerCase())) {
							Player player = (Player) sender;
							ISpell spell = Zephyrus.getSpellMap().get(args[0].toLowerCase());
							if (spell.isEnabled()) {
								SpellTome tome = new SpellTome(spell.getDisplayName().toLowerCase(),
										spell.getDisplayDesc());
								player.getInventory().addItem(tome.item());
								sender.sendMessage(Lang.get("spelltomecmd.complete")
										.replace("[TARGET]", sender.getName())
										.replace("[SPELL]", spell.getDisplayName()));
							} else {
								Lang.errMsg("disabled", sender);
							}
						} else {
							Lang.errMsg("spelltomecmd.noexist", sender);
						}
					} else {
						Lang.errMsg("spelltomecmd.usage", sender);
					}
				} else {
					if (Zephyrus.getSpellMap().containsKey(args[0].toLowerCase())) {
						if (isOnline(args[1])) {
							Player player = Bukkit.getPlayer(args[1]);
							ISpell spell = Zephyrus.getSpellMap().get(args[0].toLowerCase());
							if (spell.isEnabled()) {
								SpellTome tome = new SpellTome(spell.getDisplayName().toLowerCase(),
										spell.getDisplayDesc());
								player.getInventory().addItem(tome.item());
								sender.sendMessage(Lang.get("spelltomecmd.complete")
										.replace("[TARGET]", player.getName())
										.replace("[SPELL]", spell.getDisplayName()));
							} else {
								Lang.errMsg("disabled", sender);
							}
						} else {
							Lang.errMsg("notonline", sender);
						}
					} else {
						Lang.errMsg("spelltomecmd.noexist", sender);
					}
				}
			}
		} else {
			Lang.errMsg("noperm", sender);
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Set<String> set = Zephyrus.getSpellMap().keySet();
		List<String> list = new ArrayList<String>();
		for (String s : set) {
			list.add(s);
		}
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

	public boolean isOnline(String player) {
		Player target = (Bukkit.getServer().getPlayer(player));
		if (target == null) {
			return false;
		}
		return true;
	}

}
