package minnymin3.zephyrus.commands;

import java.util.List;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.events.PlayerCastSpellEvent;
import minnymin3.zephyrus.hooks.PluginHook;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Cast extends ZephyrusCommand implements CommandExecutor,
		TabCompleter {

	Zephyrus plugin;
	LevelManager lvl;
	PluginHook hook;

	public Cast(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
		hook = new PluginHook();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length < 1) {
				sender.sendMessage("Specify a spell to cast!");
			} else {
				if (Zephyrus.spellMap.containsKey(args[0])) {
					Player player = (Player) sender;
					Spell spell = Zephyrus.spellMap.get(args[0].toLowerCase());
					if (spell.isLearned(player, spell.name())
							|| spell.hasPermission(player, spell)) {
						if (spell.isEnabled()) {
							if (!(LevelManager.getMana(player) < spell
									.getManaCost()
									* plugin.getConfig().getInt(
											"ManaMultiplier"))) {
								if (spell.canRun(player, args)) {
									PlayerCastSpellEvent event = new PlayerCastSpellEvent(
											player, spell, args);
									Bukkit.getServer().getPluginManager()
											.callEvent(event);
									if (!event.isCancelled()) {
										spell.run(player, args);
										LevelManager
												.drainMana(
														player,
														spell.getManaCost()
																* plugin.getConfig()
																		.getInt("ManaMultiplier"));
									}
								} else {
									if (spell.failMessage() != "") {
										player.sendMessage(spell.failMessage());
									}
								}
							} else {
								player.sendMessage(ChatColor.DARK_RED
										+ "Not enough mana!");
							}
						} else {
							sender.sendMessage("That spell is disabled!");
						}
					} else {
						player.sendMessage(ChatColor.DARK_RED
								+ "You have not learned that spell!");
					}

				} else {
					sender.sendMessage(ChatColor.DARK_RED
							+ "You have not learned that spell!");
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		return learned(sender);
	}

	public List<String> learned(CommandSender p) {
		Player player = (Player) p;
		return PlayerConfigHandler.getConfig(plugin, player).getStringList(
				"learned");
	}
}
