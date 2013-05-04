package minny.zephyrus.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VarChecks extends CommandExceptions{
	
	public int getInt(String string) {
		if (isInt(string)) {
			return Integer.parseInt(string);
		} else {
			return 0;
		}
	}

	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public boolean isOnline(String player) {
		Player target = (Bukkit.getServer().getPlayer(player));
		if (target == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public boolean isItem(ItemStack i) {
		if (i.getTypeId() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean stringNull(String string){
		if (string != null) {
			return true;
			}
		return false;
	}
	
	public boolean stringEmpty(String string){
		if (string != ""){
			return true;
		} else {
			return false;
		}
	}
	
}
