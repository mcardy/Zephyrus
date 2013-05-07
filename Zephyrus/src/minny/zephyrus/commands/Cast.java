package minny.zephyrus.commands;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.CommandExceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cast extends CommandExceptions implements CommandExecutor {

	Zephyrus plugin;
	LevelManager lvl;

	public Cast(Zephyrus plugin) {
		this.plugin = plugin;
		this.lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length < 1) {
				sender.sendMessage(ChatColor.GREEN + "Hello!");
			} else {
				if (plugin.spellMap.containsKey(args[0])) {
					Player player = (Player) sender;
					Spell spell = plugin.spellMap.get(args[0].toLowerCase());
					if (spell.isLearned(player, spell.name())) {
						// || spell.hasPermission(player, spell)
						// || player.isOp()
						// || player.hasPermission("zephyrus.cast.*")) {
						if (!(lvl.getMana(player) < spell.manaCost())) {
							if (spell.canRun(player)) {
								spell.run(player);
								spell.drainMana(player, spell.manaCost());
							} else {
								player.sendMessage(spell.failMessage());
							}
						} else {
							player.sendMessage("Not enough mana!");
						}
					} else {
						player.sendMessage("You have not learned that spell yet!");
					}

				} else {
					sender.sendMessage("That spell does not exist!");
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}
