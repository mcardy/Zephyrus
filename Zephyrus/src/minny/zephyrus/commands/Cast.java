package minny.zephyrus.commands;

import java.util.List;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.CommandExceptions;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class Cast extends CommandExceptions implements CommandExecutor, TabCompleter{

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
				sender.sendMessage("Specify a spell to cast!");
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
								if (spell.failMessage() != ""){
									player.sendMessage(spell.failMessage());
								}
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
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		return learned(sender);
	}
	
	public List<String> learned(CommandSender p){
		PlayerConfigHandler config;
		config = new PlayerConfigHandler(plugin, p.getName());
		return config.getConfig().getStringList("learned");
	}
}
