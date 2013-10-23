package net.lordsofcode.zephyrus.spellbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.PlayerPreCastSpellEvent;
import net.lordsofcode.zephyrus.items.ItemUtil;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellBookItem implements Listener {

	Zephyrus zephyrus;

	public SpellBookItem(SpellBook plugin, Zephyrus zephyrus) {
		this.zephyrus = zephyrus;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack item() {
		ItemStack i;
		try {
			i = new ItemStack(Material.getMaterial(SpellBook.ID));
		} catch (Exception e) {
			i = new ItemStack(Material.BOOK);
		}
		ItemMeta im = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Shift right click to change spell");
		lore.add(ChatColor.GRAY + "Right click to cast the spell");
		lore.add(ChatColor.GRAY + "Bound spell: " + ChatColor.GOLD + "None");
		im.setDisplayName(name());
		im.setLore(lore);
		i.setItemMeta(im);
		i.addEnchantment(Zephyrus.getInstance().glow, 1);
		return i;
	}

	public static String name() {
		return "¤6" + SpellBook.name;
	}

	public static Recipe getRecipe() {
		ShapedRecipe r = new ShapedRecipe(item());
		r.shape("zyz", "zsz", "zyz");
		r.setIngredient('z', Material.PAPER);
		r.setIngredient('y', Material.GLOWSTONE_DUST);
		r.setIngredient('s', Material.BOOK);
		return r;
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if (e.getRecipe().getResult() == getRecipe().getResult()) {
			for (HumanEntity en : e.getViewers()) {
				if (!en.hasPermission("zephyrus.spellbook.craft")) {
					e.getInventory().setResult(null);
					break;
				}
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.PHYSICAL) {
			ItemUtil util = new ItemUtil();
			if (util.checkName(e.getItem(), SpellBookItem.name())) {
				e.setCancelled(true);
				if (SpellBook.getType(e) == SpellBook.backward) {
					List<String> rawSpells = new ArrayList<String>();
					for (String s : Zephyrus.getSpellMap().keySet()) {
						rawSpells.add(s);
					}
					List<String> spells = new ArrayList<String>();
					for (String s : rawSpells) {
						ISpell spell = Zephyrus.getSpellMap().get(s);
						if (Zephyrus.getUser(e.getPlayer()).isLearned(spell) || Zephyrus.getUser(e.getPlayer()).hasPermission(spell)) {
							if (spell.canBind()) {
								spells.add(s);
							}
						}
					}
					if (!spells.isEmpty()) {
						String[] string = e.getItem().getItemMeta().getLore()
								.get(2).split(" ");
						ItemMeta im = e.getItem().getItemMeta();
						List<String> lore = im.getLore();
						int current = spells.indexOf(string[2]
								.replace("¤6", ""));
						if (current == -1) {
							lore.set(2, ChatColor.GRAY + "Bound spell: "
									+ ChatColor.GOLD + spells.get(0));
							e.getPlayer().sendMessage(
									ChatColor.GOLD + "Spell bound: "
											+ spells.get(0));
							im.setLore(lore);
							e.getItem().setItemMeta(im);
						} else {
							if (current != 0) {
								Iterator<String> it = spells
										.listIterator(current - 1);
								if (it.hasNext()) {
									String str = it.next();
									lore.set(2, ChatColor.GRAY
											+ "Bound spell: " + ChatColor.GOLD
											+ str);
									e.getPlayer().sendMessage(
											ChatColor.GOLD + "Spell bound: "
													+ str);
									im.setLore(lore);
									e.getItem().setItemMeta(im);
								} else {
									lore.set(2, ChatColor.GRAY
											+ "Bound spell: " + ChatColor.GOLD
											+ spells.get(0));
									e.getPlayer().sendMessage(
											ChatColor.GOLD + "Spell bound: "
													+ spells.get(0));
									im.setLore(lore);
									e.getItem().setItemMeta(im);
								}
							} else {
								lore.set(
										2,
										ChatColor.GRAY + "Bound spell: "
												+ ChatColor.GOLD
												+ spells.get(spells.size() - 1));
								e.getPlayer()
										.sendMessage(
												ChatColor.GOLD
														+ "Spell bound: "
														+ spells.get(spells
																.size() - 1));
								im.setLore(lore);
								e.getItem().setItemMeta(im);
							}
						}
					} else {
						e.getPlayer().sendMessage(
								ChatColor.RED + "No learned spells found...");
					}
				} else if (SpellBook.getType(e) == SpellBook.forward) {
					List<String> rawSpells = new ArrayList<String>();
					for (String s : Zephyrus.getSpellMap().keySet()) {
						rawSpells.add(s);
					}
					List<String> spells = new ArrayList<String>();
					for (String s : rawSpells) {
						ISpell spell = Zephyrus.getSpellMap().get(s);
						if (Zephyrus.getUser(e.getPlayer()).isLearned(spell) || Zephyrus.getUser(e.getPlayer()).hasPermission(spell)) {
							if (spell.canBind()) {
								spells.add(s);
							}
						}
					}
					if (!spells.isEmpty()) {
						String[] string = e.getItem().getItemMeta().getLore()
								.get(2).split(" ");
						ItemMeta im = e.getItem().getItemMeta();
						List<String> lore = im.getLore();
						int current = spells.indexOf(string[2]
								.replace("¤6", ""));
						if (current == -1) {
							lore.set(2, ChatColor.GRAY + "Bound spell: "
									+ ChatColor.GOLD + spells.get(0));
							e.getPlayer().sendMessage(
									ChatColor.GOLD + "Spell bound: "
											+ spells.get(0));
							im.setLore(lore);
							e.getItem().setItemMeta(im);
						} else {
							Iterator<String> it = spells
									.listIterator(current + 1);
							if (it.hasNext()) {
								String str = it.next();
								lore.set(2, ChatColor.GRAY + "Bound spell: "
										+ ChatColor.GOLD + str);
								e.getPlayer().sendMessage(
										ChatColor.GOLD + "Spell bound: " + str);
								im.setLore(lore);
								e.getItem().setItemMeta(im);
							} else {
								lore.set(2, ChatColor.GRAY + "Bound spell: "
										+ ChatColor.GOLD + spells.get(0));
								e.getPlayer().sendMessage(
										ChatColor.GOLD + "Spell bound: "
												+ spells.get(0));
								im.setLore(lore);
								e.getItem().setItemMeta(im);
							}
						}
					} else {
						e.getPlayer().sendMessage(
								ChatColor.RED + "No learned spells found...");
					}
				} else if (SpellBook.getType(e) == SpellBook.cast) {
					if (!e.getPlayer().isSneaking()) {
						String[] string = e.getItem().getItemMeta().getLore()
								.get(2).split(" ");
						String spellName = string[2].replace("¤6", "");
						if (Zephyrus.getSpellMap().containsKey(spellName)) {
							Player player = e.getPlayer();
							IUser user = Zephyrus.getUser(player);
							ISpell spell = Zephyrus.getSpellMap()
									.get(spellName.toLowerCase());
							if (user.isLearned(spell) || user.hasPermission(spell)) {
								if (user.hasMana(spell.getManaCost())) {
									PlayerPreCastSpellEvent event = new PlayerPreCastSpellEvent(
											player, spell, null);
									Bukkit.getServer().getPluginManager()
											.callEvent(event);
									if (!event.isCancelled()) {
										boolean b = spell.run(player, null, 1);
										if (b) {
											user.drainMana(spell.getManaCost());
										}
									}
								} else {
									Lang.errMsg("nomana", player);
								}
							} else {
								Lang.errMsg("notlearned", player);
							}

						} else {
							Lang.errMsg("notlearned", e.getPlayer());
						}
					}
				}
			}
		}
	}

}
