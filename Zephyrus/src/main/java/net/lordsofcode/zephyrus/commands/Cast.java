package net.lordsofcode.zephyrus.commands;

import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.player.LevelManager;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
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

	public Cast(Zephyrus plugin) {
		this.plugin = plugin;
		Lang.add("cast.nospell", "Specify a spell to cast!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length < 1) {
				Lang.errMsg("cast.nospell", sender);
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
								Lang.errMsg("nomana", sender);
							}
						} else {
							Lang.errMsg("disabled", sender);
						}
					} else {
						Lang.errMsg("notlearned", sender);
					}

				} else {
					Lang.errMsg("notlearned", sender);
				}
			}
		} else {
			Lang.errMsg("ingameonly", sender);
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
