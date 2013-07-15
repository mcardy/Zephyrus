package net.lordsofcode.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Bind implements CommandExecutor,
		TabCompleter {

	public Bind() {
		Lang.add("bind.nospell", "Specify a spell to bind!");
		Lang.add("bind.needwand", "You need to be holding a wand!");
		Lang.add("bind.cantbind", "$6[SPELL] cannot be bound!");
		Lang.add("bind.finish", "Bound [SPELL] to your wand");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {
			if (sender.hasPermission("zephyrus.bind") || sender.isOp()) {
				if (args.length == 0) {
					Lang.errMsg("bind.nospell", sender);
				} else {
					if (Zephyrus.getSpellMap().containsKey(args[0])) {
						ISpell spell = Zephyrus.getSpellMap().get(args[0]);
						Player player = (Player) sender;
						IUser user = Zephyrus.getUser(player);
						if (user.isLearned(spell)
								|| user.hasPermission(spell)) {
							if (player.getItemInHand() != null
									&& player.getItemInHand().hasItemMeta()
									&& player.getItemInHand().getItemMeta()
											.hasDisplayName()
									&& player.getItemInHand().getItemMeta()
											.getDisplayName()
											.contains(ChatColor.GOLD + "Wand")) {
								if (spell.canBind()) {
									ItemStack i = player.getItemInHand();
									List<String> list = new ArrayList<String>();
									list.add(ChatColor.GRAY + "Bound spell: "
											+ ChatColor.DARK_GRAY
											+ spell.getDisplayName().toLowerCase());
									ItemMeta m = i.getItemMeta();
									m.setDisplayName(ChatColor.GOLD
											+ "Wand"
											+ ChatColor.DARK_GRAY
											+ " | "
											+ ChatColor.GOLD
											+ WordUtils.capitalizeFully(spell.getDisplayName()));
									m.setLore(list);
									i.setItemMeta(m);
									player.sendMessage(ChatColor.GRAY
											+ Lang.get("bind.finish").replace("[SPELL]",
													WordUtils.capitalizeFully(ChatColor.GOLD
															+ spell.getDisplayName() + ChatColor.GRAY)));
								} else {
									sender.sendMessage(ChatColor.DARK_RED
											+ Lang.get("bind.cantbind")
													.replace("[SPELL]",
															ChatColor.GOLD + WordUtils.capitalizeFully(
																			spell.getDisplayName()) + ChatColor.RED));
								}
							} else {
								Lang.errMsg("bind.needwand", player);
							}
						} else {
							Lang.errMsg("notlearned", sender);
						}

					} else {
						Lang.errMsg("notlearned", sender);
					}
				}
			} else {
				Lang.errMsg("noperm", sender);
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
		return PlayerConfigHandler.getConfig(player).getStringList(
				"learned");
	}

}
