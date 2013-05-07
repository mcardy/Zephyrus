package minny.zephyrus.commands;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.CustomItem;
import minny.zephyrus.utils.CommandExceptions;
import minny.zephyrus.utils.ItemUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUpItem extends CommandExceptions implements CommandExecutor {

	ItemUtil item;
	Zephyrus plugin;

	public LevelUpItem(Zephyrus plugin) {
		item = new ItemUtil(plugin);
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("zephyrus.levelup.item") || player.isOp()) {
				if (plugin.itemMap.containsKey(player.getItemInHand()
						.getItemMeta().getDisplayName())) {
					CustomItem i = plugin.itemMap.get(player.getItemInHand()
							.getItemMeta().getDisplayName());
					if (item.getItemLevel(player.getItemInHand()) < i
							.maxLevel()) {
						int current = item.getItemLevel(player.getItemInHand());
						item.setItemLevel(player.getItemInHand(), current + 1);
					} else {
						player.sendMessage("That item cannot be leveled anymore!");
					}
				} else {
					player.sendMessage("The item you are holding cannot be leveled!");
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
}
