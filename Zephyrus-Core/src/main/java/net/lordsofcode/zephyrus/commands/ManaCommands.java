package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.command.Command;
import net.lordsofcode.zephyrus.utils.command.CommandArgs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ManaCommands {

	public ManaCommands() {
		Lang.add("mana.restored", "Your magical powers feel restored!");
	}

	@Command(name = "mana", permission = "zephyrus.mana", description = "General mana command. Show mana, set hud display and restore mana.", usage = "/mana (restore|display|player)")
	public void manaCommand(CommandArgs cmd) {
		if (cmd.isPlayer()) {
			if (cmd.getArgs().length == 0) {
				Player player = cmd.getPlayer();
				Zephyrus.getUser(player).displayMana(player);
			} else {
				if (cmd.getSender().hasPermission("zephyrus.mana.other") || cmd.getSender().isOp()) {
					if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
						Player target = Bukkit.getServer().getPlayer(cmd.getArgs()[0]);
						Zephyrus.getUser(target).displayMana(cmd.getSender());
					} else {
						Lang.errMsg("notonline", cmd.getSender());
					}
				} else {
					Lang.errMsg("noperm", cmd.getSender());
				}
			}
		} else {
			if (cmd.getArgs().length == 0) {
				cmd.getSender().sendMessage("Specify a target player!");
			} else {
				if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
					Player target = Bukkit.getServer().getPlayer(cmd.getArgs()[0]);
					Zephyrus.getUser(target).displayMana(cmd.getSender());
				} else {
					Lang.errMsg("notonline", cmd.getSender());
				}
			}
		}
	}

	@Command(name = "mana.restore", permission = "zephyrus.mana.restore")
	public void manaRestoreCommand(CommandArgs cmd) {
		if (cmd.getArgs().length < 2) {
			if (cmd.isPlayer()) {
				Player player = cmd.getPlayer();
				Zephyrus.getUser(player).reLoadMana();
				player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
			} else {
				cmd.getSender().sendMessage("Specify a target player!");
			}
		} else {
			if (Bukkit.getPlayer(cmd.getArgs()[1]) != null) {
				if (Bukkit.getPlayer(cmd.getArgs()[1]) != null) {
					Player player = Bukkit.getServer().getPlayer(cmd.getArgs()[1]);
					Zephyrus.getUser(player).reLoadMana();
					player.sendMessage(ChatColor.DARK_AQUA + Lang.get("mana.restored"));
				} else {
					Lang.errMsg("notonline", cmd.getSender());
				}
			} else {
				Lang.errMsg("notonline", cmd.getSender());
			}
		}
	}

	@Command(name = "mana.display")
	public void manaDisplayCommand(CommandArgs cmd) {
		if (!cmd.isPlayer()) {
			Lang.errMsg("ingameonly", cmd.getSender());
			return;
		}
		IUser user = Zephyrus.getUser(cmd.getPlayer());
		user.setDisplayMana(!user.getDisplayMana());
		user.drainMana(0);
	}

}
