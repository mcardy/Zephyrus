package minny.zephyrus.commands;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
			if (args.length == 0) {
				sender.sendMessage("Specify a spell to bind!");
			} else {
				if (sender.hasPermission("zephyrus.bind")) {
					if (plugin.spellMap.containsKey(args[0])) {
						Spell spell = plugin.spellMap.get(args[0]);
						Player player = (Player) sender;
						if (spell.isLearned(player, spell.name())) {
							if (player.getItemInHand().getItemMeta()
									.getDisplayName().contains("¤6Wand")) {
								ItemStack i = player.getItemInHand();
								List<String> list = new ArrayList<String>();
								list.add(ChatColor.GRAY + "Bound spell: "
										+ ChatColor.DARK_GRAY + spell.name());
								ItemMeta m = i.getItemMeta();
								m.setLore(list);
								i.setItemMeta(m);
								player.sendMessage("Bound " + ChatColor.GOLD
										+ spell.name() + ChatColor.WHITE
										+ " to that wand.");
							} else {
								sender.sendMessage("You need to be holding a wand!");
							}
						} else {
							sender.sendMessage("You do not know that spell!");
						}
					} else {
						sender.sendMessage("You do not know that spell!");
					}
				}
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
		PlayerConfigHandler config;
		config = new PlayerConfigHandler(plugin, p.getName());
		return config.getConfig().getStringList("learned");
	}

}
