package net.lordsofcode.zephyrus.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItemWand;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.effects.EffectType;
import net.lordsofcode.zephyrus.events.PlayerPostCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerPreCastSpellEvent;
import net.lordsofcode.zephyrus.items.SpellTome;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;
import net.lordsofcode.zephyrus.utils.command.Command;
import net.lordsofcode.zephyrus.utils.command.CommandArgs;
import net.lordsofcode.zephyrus.utils.command.Completer;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellCommands {

	public SpellCommands() {
		Lang.add("bind.nospell", "Specify a spell to bind!");
		Lang.add("bind.needwand", "You need to be holding a wand!");
		Lang.add("bind.cantbind", "$6[SPELL] cannot be bound!");
		Lang.add("bind.finish", "Bound [SPELL] to your wand");
		Lang.add("bind.cantbindwand", "That wand can't have spells bound to it!");
		Lang.add("cast.nospell", "Specify a spell to cast!");
		Lang.add("unbind.unbound", "Spell unbound from your wand!");
		Lang.add("unbind.nospell", "There is no spell bound to that wand!");
		Lang.add("spelltomecmd.nospell", "Specify a spell to give!");
		Lang.add("spelltomecmd.noexist", "That spell does not exist!");
		Lang.add("spelltomecmd.usage", "Usage: /spelltome [spell] [player]");
		Lang.add("spelltomecmd.complete", "Gave [TARGET] the [SPELL] spelltome.");
		Lang.add("effects.header", "$b     --==$6[Active Effects]$b==--");
		Lang.add("effects.footer", "$b     ------=========------");
	}

	@Command(name = "bind", permission = "zephyrus.bind", description = "Binds a spell to a wand", usage = "/bind (spell)")
	public void bindCommand(CommandArgs cmd) {
		if (!cmd.isPlayer()) {
			Lang.errMsg("ingameonly", cmd.getSender());
			return;
		}
		if (cmd.getArgs().length == 0) {
			Lang.errMsg("bind.nospell", cmd.getSender());
			return;
		}
		if (Zephyrus.getSpellMap().containsKey(cmd.getArgs()[0])) {
			ISpell spell = Zephyrus.getSpellMap().get(cmd.getArgs()[0]);
			Player player = cmd.getPlayer();
			IUser user = Zephyrus.getUser(player);
			ItemStack item = player.getItemInHand();
			if (user.isLearned(spell) || user.hasPermission(spell)) {
				if (Zephyrus.getItemManager().isWand(item)) {
					ICustomItemWand wand = Zephyrus.getItemManager().getWand(item);
					if (wand.getCanBind(spell)) {
						if (spell.canBind()) {
							ItemMeta m = item.getItemMeta();
							m.setDisplayName(wand.getBoundName(spell));
							m.setLore(wand.getBoundLore(spell));
							item.setItemMeta(m);
							player.sendMessage(ChatColor.GRAY
									+ Lang.get("bind.finish").replace(
											"[SPELL]",
											WordUtils.capitalizeFully(ChatColor.GOLD + spell.getDisplayName()
													+ ChatColor.GRAY)));
						} else {
							cmd.getSender().sendMessage(
									ChatColor.DARK_RED
											+ Lang.get("bind.cantbind").replace(
													"[SPELL]",
													ChatColor.GOLD + WordUtils.capitalizeFully(spell.getDisplayName())
															+ ChatColor.RED));
						}
					} else {
						Lang.errMsg("bind.cantbindwand", player);
					}
				} else {
					Lang.errMsg("bind.needwand", player);
				}
			} else {
				Lang.errMsg("notlearned", cmd.getSender());
			}

		} else {
			Lang.errMsg("notlearned", cmd.getSender());
		}
	}

	@Command(name = "effects", description = "Display all active effects", usage = "/effects (player)")
	public void effectsCommand(CommandArgs cmd) {
		Player target = null;
		if (cmd.getArgs().length == 0) {
			if (!cmd.isPlayer()) {
				Lang.errMsg("ingameonly", cmd.getSender());
				return;
			} else {
				target = cmd.getPlayer();
			}
		} else {
			if (Bukkit.getPlayer(cmd.getArgs()[0]) != null) {
				target = Bukkit.getPlayer(cmd.getArgs()[0]);
			} else {
				Lang.errMsg("notonline", cmd.getPlayer());
				return;
			}
		}
		cmd.getSender().sendMessage(Lang.get("effects.header"));
		IUser user = Zephyrus.getUser(target);
		Iterator<EffectType> effects = user.getCurrentEffects().iterator();
		while (effects.hasNext()) {
			EffectType type = effects.next();
			cmd.getSender()
					.sendMessage(
							ChatColor.GRAY + "     - " + type.getName() + " " + ChatColor.GREEN
									+ user.getEffectTime(type) / 20);
		}
		cmd.getSender().sendMessage(Lang.get("effects.footer"));
	}

	@Command(name = "cast", permission = "zephyrus.cast", description = "Casts the specified spell if known", usage = "/cast (spell)")
	public void castCommand(CommandArgs cmd) {
		if (!cmd.isPlayer()) {
			Lang.errMsg("ingameonly", cmd.getSender());
			return;
		}
		if (cmd.getArgs().length < 1) {
			Lang.errMsg("cast.nospell", cmd.getSender());
		} else {
			if (Zephyrus.getSpellMap().containsKey(cmd.getArgs()[0])) {
				Player player = cmd.getPlayer();
				IUser user = Zephyrus.getUser(player);
				ISpell spell = Zephyrus.getSpellMap().get(cmd.getArgs()[0].toLowerCase());
				if (user.isLearned(spell) || user.hasPermission(spell)) {
					if (user.hasMana(spell.getManaCost())) {
						PlayerPreCastSpellEvent event = new PlayerPreCastSpellEvent(player, spell, cmd.getArgs());
						Bukkit.getPluginManager().callEvent(event);
						if (!event.isCancelled()) {
							boolean b = spell.run(player, cmd.getArgs(), 1);
							if (b) {
								user.drainMana(spell.getManaCost());
								player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1f, 1f);
								player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
								PlayerPostCastSpellEvent event2 = new PlayerPostCastSpellEvent(player, spell);
								Bukkit.getPluginManager().callEvent(event2);
							}
						}
					} else {
						Lang.errMsg("nomana", cmd.getSender());
						player.playSound(player.getLocation(), Sound.WITHER_HURT, 1f, 1f);
					}
				} else {
					Lang.errMsg("notlearned", cmd.getSender());
					player.playSound(player.getLocation(), Sound.WITHER_HURT, 1f, 1f);
				}

			} else {
				Lang.errMsg("notlearned", cmd.getSender());
				player.playSound(player.getLocation(), Sound.WITHER_HURT, 1f, 1f);
			}
		}
	}

	@Command(name = "spelltome", permission = "zephyrus.spelltome.give", description = "Gives a spelltome to the designated player", usage = "/spelltome (spell) (player)")
	public void spelltomeCommand(CommandArgs cmd) {
		if (cmd.getArgs().length < 1) {
			Lang.errMsg("spelltomecmd.usage", cmd.getSender());
			return;
		}
		if (cmd.getArgs().length < 2) {
			if (cmd.isPlayer()) {
				if (Zephyrus.getSpellMap().containsKey(cmd.getArgs()[0].toLowerCase())) {
					Player player = cmd.getPlayer();
					ISpell spell = Zephyrus.getSpellMap().get(cmd.getArgs()[0].toLowerCase());
					if (spell.isEnabled()) {
						SpellTome tome = new SpellTome(spell.getDisplayName().toLowerCase(), spell.getDisplayDesc());
						player.getInventory().addItem(tome.item());
						cmd.getSender().sendMessage(
								Lang.get("spelltomecmd.complete").replace("[TARGET]", cmd.getSender().getName())
										.replace("[SPELL]", spell.getDisplayName()));
					} else {
						Lang.errMsg("disabled", cmd.getSender());
					}
				} else {
					Lang.errMsg("spelltomecmd.noexist", cmd.getSender());
				}
			} else {
				Lang.errMsg("spelltomecmd.usage", cmd.getSender());
			}
		} else {
			if (Zephyrus.getSpellMap().containsKey(cmd.getArgs()[0].toLowerCase())) {
				if (Bukkit.getPlayer(cmd.getArgs()[1]) != null) {
					Player player = Bukkit.getPlayer(cmd.getArgs()[1]);
					ISpell spell = Zephyrus.getSpellMap().get(cmd.getArgs()[0].toLowerCase());
					if (spell.isEnabled()) {
						SpellTome tome = new SpellTome(spell.getDisplayName().toLowerCase(), spell.getDisplayDesc());
						player.getInventory().addItem(tome.item());
						cmd.getSender().sendMessage(
								Lang.get("spelltomecmd.complete").replace("[TARGET]", player.getName())
										.replace("[SPELL]", spell.getDisplayName()));
					} else {
						Lang.errMsg("disabled", cmd.getSender());
					}
				} else {
					Lang.errMsg("notonline", cmd.getSender());
				}
			} else {
				Lang.errMsg("spelltomecmd.noexist", cmd.getSender());
			}
		}
	}

	@Command(name = "unbind", aliases = "bind.none", permission = "zephyrus.bind", description = "Unbinds the wand in your hand", usage = "/unbind")
	public void unbindCommand(CommandArgs cmd) {
		if (!cmd.isPlayer()) {
			Lang.errMsg("ingameonly", cmd.getSender());
			return;
		}
		ItemStack item = cmd.getPlayer().getItemInHand();
		if (Zephyrus.getItemManager().isWand(item)) {
			ICustomItemWand wand = Zephyrus.getItemManager().getWand(item);
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(wand.getDisplayName());
			m.setLore(wand.getDefaultLore());
			item.setItemMeta(m);
			Lang.msg("unbind.unbound", cmd.getSender());
		} else {
			Lang.errMsg("bind.needwand", cmd.getSender());
		}
	}

	@Completer(name = "spelltome")
	public List<String> spelltomeComplete(CommandArgs cmd) {
		List<String> list = new ArrayList<String>();
		for (String s : Zephyrus.getSpellMap().keySet()) {
			list.add(s);
		}
		if (cmd.getArgs().length == 0) {
			return list;
		}
		String spell = cmd.getArgs()[0];
		List<String> newList = new ArrayList<String>();
		for (String s : list) {
			if (s.startsWith(spell.toLowerCase())) {
				newList.add(s);
			}
		}
		return newList;
	}

	@Completer(name = "bind", aliases = { "cast" })
	public List<String> bindComplete(CommandArgs cmd) {
		if (cmd.isPlayer()) {
			List<String> list = PlayerConfigHandler.getConfig(cmd.getPlayer()).getStringList("learned");
			if (cmd.getArgs().length == 0) {
				return list;
			}
			String spell = cmd.getArgs()[0];
			List<String> newList = new ArrayList<String>();
			for (String s : list) {
				if (s.startsWith(spell.toLowerCase())) {
					newList.add(s);
				}
			}
			return newList;
		} else {
			return null;
		}
	}

}
