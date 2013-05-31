package minnymin3.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.utils.PlayerConfigHandler;

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

public class Bind extends ZephyrusCommand implements CommandExecutor,
		TabCompleter {

	Zephyrus plugin;

	public Bind(Zephyrus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (sender instanceof Player) {
			if (hasPerm(sender, "zephyrus.bind")) {
				if (args.length == 0) {
					sender.sendMessage("Specify a spell to bind!");
				} else {
					if (Zephyrus.spellMap.containsKey(args[0])) {
						Spell spell = Zephyrus.spellMap.get(args[0]);
						Player player = (Player) sender;
						if (spell.isLearned(player, spell.name())
								|| spell.hasPermission(player, spell)) {
							if (player.getItemInHand() != null
									&& player.getItemInHand().hasItemMeta()
									&& player.getItemInHand().getItemMeta().hasDisplayName()
									&& player.getItemInHand().getItemMeta()
											.getDisplayName()
											.contains("¤6Wand")) {
								if (spell.canBind()) {
									ItemStack i = player.getItemInHand();
									List<String> list = new ArrayList<String>();
									list.add(ChatColor.GRAY + "Bound spell: "
											+ ChatColor.DARK_GRAY
											+ spell.name());
									ItemMeta m = i.getItemMeta();
									m.setLore(list);
									i.setItemMeta(m);
									player.sendMessage(ChatColor.GRAY
											+ "Bound " + ChatColor.GOLD
											+ spell.name() + ChatColor.GRAY
											+ " to that wand.");
								} else {
									sender.sendMessage(ChatColor.DARK_RED
											+ "That spell cannot be bound");
								}
							} else {
								sender.sendMessage(ChatColor.DARK_RED
										+ "You need to be holding a wand!");
							}
						} else {
							sender.sendMessage(ChatColor.DARK_RED
									+ "You do not know that spell!");
						}

					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "You do not know that spell!");
					}
				}
			} else {
				needOp(sender);
			}
		} else {
			inGameOnly(sender);
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		return learned(sender);
	}

	public List<String> learned(CommandSender p) {
		Player player = (Player) p;
		return PlayerConfigHandler.getConfig(plugin, player).getStringList(
				"learned");
	}

}
