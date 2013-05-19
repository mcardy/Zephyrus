package minny.zephyrus.commands;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.SpellTome;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.spells.Spell;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpellTomeCmd extends ZephyrusCommand implements CommandExecutor {

	Zephyrus plugin;
	LevelManager lvl;

	public SpellTomeCmd(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("zephyrus.spelltome.give")
					|| sender.isOp()) {
				if (args.length < 2) {
					sender.sendMessage("Usage: " + ChatColor.RED
							+ "/spelltome [spell] [player]");
				} else {
					if (Zephyrus.spellMap.containsKey(args[0])) {
						if (isOnline(args[1])) {
							Player player = Bukkit.getPlayer(args[1]);
							Spell spell = Zephyrus.spellMap.get(args[0]
									.toLowerCase());
							SpellTome tome = new SpellTome(plugin,
									spell.name(), spell.bookText());
							player.getInventory().addItem(tome.item());
							sender.sendMessage("Gave " + player.getName()
									+ " the " + ChatColor.GOLD + spell.name()
									+ " spelltome");
						} else {
							notOnline(sender);
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "That spell does not exist");
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

}
