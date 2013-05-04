package minny.zephyrus.commands;

import minny.zephyrus.utils.VarChecks;

import org.bukkit.command.CommandSender;

public class ZephyrusCommand extends VarChecks {

	public boolean hasPerm(CommandSender sender, String perm){
		if (sender.hasPermission(perm)){
			return true;
		}
		return false;
	}
	
	public boolean isOp(CommandSender sender){
		if (sender.isOp()){
			return true;
		}
		return false;
	}
	
}
