package minny.zephyrus.commands;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUp extends ZephyrusCommand implements CommandExecutor{

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
				if (args.length == 0) {
					Player player = (Player) sender;
					lvl.levelUp(player);
				} else {
					if (isOnline(args[0])){
						Player player = Bukkit.getPlayer(args[0]);
						lvl.levelUp(player);
					} else {
						notOnline(sender);
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
