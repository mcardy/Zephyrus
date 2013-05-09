package minny.zephyrus.commands;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.CommandExceptions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUp extends CommandExceptions implements CommandExecutor{

	Zephyrus plugin;
	LevelManager lvl;
	
	public LevelUp(Zephyrus plugin){
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("zephyrus.levelup") || sender.isOp()){
				Player player = (Player) sender;
				lvl.levelUp(player);
			} else {
				needOp(sender);
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
	
}
