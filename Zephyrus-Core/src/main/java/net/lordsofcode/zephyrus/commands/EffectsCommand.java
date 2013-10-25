package net.lordsofcode.zephyrus.commands;

import java.util.Iterator;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.effects.EffectType;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class EffectsCommand implements CommandExecutor {

	public EffectsCommand() {
		Lang.add("effects.header", "$b     --==$6[Active Effects]$b==--");
		Lang.add("effects.footer", "$b     ------=========------");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(Lang.get("effects.header"));
			IUser user = Zephyrus.getUser(player);
			Iterator<EffectType> effects = user.getCurrentEffects().iterator();
			while (effects.hasNext()) {
				EffectType type = effects.next();
				player.sendMessage(ChatColor.GRAY + "     - " + type.getName() + " " + ChatColor.GREEN
						+ user.getEffectTime(type)/20);
			}
			player.sendMessage(Lang.get("effects.footer"));
		} else {
			Lang.errMsg("ingameonly", sender);
		}
		return false;
	}

}
