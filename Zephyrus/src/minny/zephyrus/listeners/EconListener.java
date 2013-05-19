package minny.zephyrus.listeners;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;
import minny.zephyrus.items.SpellTome;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.spells.Spell;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class EconListener implements Listener {

	Zephyrus plugin;
	PluginHook hook;

	public EconListener(Zephyrus plugin) {
		this.plugin = plugin;
		hook = new PluginHook();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickSign(PlayerInteractEvent e) {
		if (hook.economy()) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock() != null
						&& e.getClickedBlock().getState() instanceof Sign) {
					Sign s = (Sign) e.getClickedBlock().getState();
					if (s.getLine(0).equals(ChatColor.DARK_AQUA + "[BuySpell]")) {
						if (e.getPlayer().hasPermission("zephyrus.buy")) {
							double cost = Double.parseDouble(s.getLine(1)
									.replace("$", "").replace("¤6", ""));
							if (hook.econ.getBalance(e.getPlayer().getName()) >= cost) {
								Spell spell = Zephyrus.spellMap.get(s
										.getLine(2).toLowerCase()
										.replace("¤4", ""));
								if (e.getPlayer().hasPermission(
										"zephyrus.spell." + spell.name())) {
									if (!(LevelManager.getLevel(e.getPlayer()) < spell
											.reqLevel())) {
										SpellTome tome = new SpellTome(plugin,
												spell.name(), spell.bookText());
										hook.econ.withdrawPlayer(e.getPlayer()
												.getName(), cost);
										e.getPlayer().getInventory()
												.addItem(tome.item());
										e.getPlayer().updateInventory();
										e.getPlayer().sendMessage(
												"You successfully purchased "
														+ spell.name());
									} else {
										e.getPlayer()
												.sendMessage(
														"You are too low a level to purchas that spell!");
									}
								} else {
									e.getPlayer()
											.sendMessage(
													"You do not have permission for that spell!");
								}
							} else {
								e.getPlayer().sendMessage(
										ChatColor.RED + "Insufficient funds!");
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onCreateSign(SignChangeEvent e) {
		if (e.getLine(0).equals("[BuySpell]")) {
			if (e.getPlayer().hasPermission("zephyrus.buy.create")) {
				if (hook.economy()) {
					Player player = e.getPlayer();
					try {
						if (e.getLine(1).startsWith("$")) {
							Double.parseDouble(e.getLine(1).replace("$", ""));
						} else {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException nFE) {
						player.sendMessage("Invalid Cost!");
						return;
					}
					if (!Zephyrus.spellMap.containsKey(e.getLine(2)
							.toLowerCase())) {
						player.sendMessage("Invalid Spell!");
						return;
					}
					e.setLine(0, ChatColor.DARK_AQUA + "[BuySpell]");
					e.setLine(1, "$" + "¤6" + e.getLine(1).replace("$", ""));
					e.setLine(2, "¤4" + e.getLine(2));
					player.sendMessage("Successfully created a BuySpell sign!");

				} else {
					e.getPlayer()
							.sendMessage(
									ChatColor.RED
											+ "Vault not detected! Install vault to use BuySpell signs.");
				}
			}
		}
	}

}
