package minny.zephyrus.commands;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.CommandExceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cast extends CommandExceptions implements CommandExecutor{

	Zephyrus plugin;
	
	public Cast(Zephyrus plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player){
			if (args.length < 1){
				sender.sendMessage(ChatColor.GREEN + "Hello!");
			} else {
				if (plugin.spellMap.containsKey(args[0])){
					Player player = (Player) sender;
					Spell spell = plugin.spellMap.get(args[0]);
					spell.run(player);
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}
