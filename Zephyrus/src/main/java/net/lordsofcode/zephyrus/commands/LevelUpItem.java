package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.items.CustomItem;
import net.lordsofcode.zephyrus.utils.ItemUtil;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class LevelUpItem extends ZephyrusCommand implements CommandExecutor {

	ItemUtil item;
	Zephyrus plugin;

	public LevelUpItem(Zephyrus plugin) {
		item = new ItemUtil(plugin);
		this.plugin = plugin;
		Lang.add("itemlevel.nomore", "That item cannot be leveled any more!");
		Lang.add("itemlevel.cantlevel", "The item you are holding cannot be leveled!");
		Lang.add("itemlevel.complete", "You have leveled up the [ITEMNAME]");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("zephyrus.levelup.item") || player.isOp()) {
				if (Zephyrus.itemMap.containsKey(player.getItemInHand()
						.getItemMeta().getDisplayName())) {
					CustomItem i = Zephyrus.itemMap.get(player.getItemInHand()
							.getItemMeta().getDisplayName());
					if (item.getItemLevel(player.getItemInHand()) < i
							.maxLevel()) {
						int current = item.getItemLevel(player.getItemInHand());
						item.setItemLevel(player.getItemInHand(), current + 1);
						sender.sendMessage(Lang.get("itemlevel.complete").replace("[ITEMNAME]", Lang.caps(i.name())));
					} else {
						Lang.errMsg("itemlevel.nomore", sender);
					}
				} else {
					Lang.errMsg("cantlevel", sender);
				}
			} else {
				Lang.errMsg("noperm", sender);
			}
		} else {
			Lang.errMsg("ingameonly", sender);
		}
		return false;
	}
}
