package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.items.ItemUtil;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.command.Command;
import net.lordsofcode.zephyrus.utils.command.CommandArgs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LevelCommands extends ItemUtil {

	public LevelCommands() {
		Lang.add("levelupcmd", "You have leveled up [PLAYER]");
		Lang.add("itemlevel.nomore", "That item cannot be leveled any more!");
		Lang.add("itemlevel.cantlevel", "The item you are holding cannot be leveled!");
		Lang.add("itemlevel.complete", "You have leveled up the [ITEMNAME]");
	}

	@Command(name = "level", permission = "zephyrus.level", description = "Display your level and other people's levels", usage = "/level (player)")
	public void levelCommand(CommandArgs cmd) {
		if (cmd.isPlayer()) {
			if (cmd.getArgs().length == 0) {
				Zephyrus.getUser(cmd.getPlayer()).displayLevel(cmd.getSender());
			} else {
				if (cmd.getSender().hasPermission("zephyrus.level.other") || cmd.getSender().isOp()) {
					if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
						Player target = Bukkit.getServer().getPlayer(cmd.getArgs()[0]);
						Zephyrus.getUser(target).displayLevel(cmd.getSender());
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
			} else if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
				Player target = Bukkit.getServer().getPlayer(cmd.getArgs()[0]);
				Zephyrus.getUser(target).displayLevel(cmd.getSender());
			} else {
				Lang.errMsg("notonline", cmd.getSender());
			}
		}
	}

	@Command(name = "levelup", permission = "zephyrus.levelup", description = "Level yourself or other players up", usage = "/levelup (player)")
	public void levelupCommand(CommandArgs cmd) {
		if (cmd.getArgs().length == 0) {
			if (cmd.isPlayer()) {
				Zephyrus.getUser(cmd.getPlayer()).levelUp();
				cmd.getSender().sendMessage(Lang.get("levelupcmd").replace("[PLAYER]", cmd.getSender().getName()));
			} else {
				cmd.getSender().sendMessage("Specify a target player!");
			}
		} else {
			if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
				Player target = Bukkit.getServer().getPlayer(cmd.getArgs()[0]);
				Zephyrus.getUser(target).levelUp();
				cmd.getSender().sendMessage(Lang.get("levelupcmd").replace("[PLAYER]", target.getName()));
			} else {
				Lang.errMsg("notonline", cmd.getSender());
			}
		}
	}

	@Command(name = "levelupitem", aliases = "levelup.item", description = "Level up the item in your hand if possible", usage = "/levelupitem or /levelup item")
	public void levelupItemCommand(CommandArgs cmd) {
		if (!cmd.isPlayer()) {
			Lang.errMsg("ingameonly", cmd.getSender());
			return;
		}
		Player player = cmd.getPlayer();
		if (Zephyrus.getItemManager().isCustomItem(player.getItemInHand())) {
			ICustomItem i = Zephyrus.getItemManager().getCustomItem(player.getItemInHand());
			if (getItemLevel(player.getItemInHand()) < i.getMaxLevel()) {
				int current = getItemLevel(player.getItemInHand());
				setItemLevel(player.getItemInHand(), current + 1);
				player.sendMessage(Lang.get("itemlevel.complete").replace("[ITEMNAME]", Lang.caps(i.getDisplayName())));
			} else {
				Lang.errMsg("itemlevel.nomore", player);
			}
		} else {
			Lang.errMsg("cantlevel", player);
		}
	}

}
