package net.lordsofcode.zephyrus.items.wands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.events.PlayerCraftSpellEvent;
import net.lordsofcode.zephyrus.utils.Lang;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class WeakWand extends Wand {

	@Override
	public String getName() {
		return ChatColor.GRAY + "Weak Wand";
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		recipe.shape("  C", " B ", "A  ");
		recipe.setIngredient('C', Material.REDSTONE);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.IRON_INGOT);
		return recipe;
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@Override
	public String getPerm() {
		return "wand.basic";
	}

	@Override
	public int getManaDiscount(ISpell spell) {
		return 0;
	}

	@Override
	public int getReqLevel() {
		return 0;
	}

	@Override
	public int getPower(ISpell spell) {
		return 0;
	}

	@Override
	public String getSpell(ItemStack i) {
		return "";
	}

	@Override
	public List<String> getDefaultLore() {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Weak Wand");
		list.add(ChatColor.GRAY + "Unable to craft spells above level 3");
		return list;
	}

	@Override
	public List<String> getBoundLore(ISpell spell) {
		return null;
	}
	
	@Override
	public String getBoundName(ISpell spell) {
		return ChatColor.GOLD + getDisplayName() + " [" + ChatColor.GOLD
				+ WordUtils.capitalizeFully(spell.getDisplayName()) + "]";
	}
	
	@Override
	public boolean getCanBind(ISpell spell) {
		return false;
	}
	
	@Override
	@EventHandler(priority = EventPriority.LOW)
	public void bookShelfClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BOOKSHELF
				&& checkContainsName(e.getItem(), getItem().getItemMeta().getDisplayName())
				&& !Zephyrus.getSpellMap().containsKey(getSpell(e.getItem()))) {
			if (Zephyrus.getConfig().getBoolean("Disable-Spell-Crafting")) {
				return;
			}
			Location loc = e.getClickedBlock().getLocation();
			BlockBreakEvent blockBreakEvent = new BlockBreakEvent(loc.getBlock(), e.getPlayer());
			Bukkit.getPluginManager().callEvent(blockBreakEvent);
			loc.setY(loc.getY() + 1);
			Entity[] entitys = getNearbyEntities(loc, 1);
			if (blockBreakEvent.isCancelled()) {
				return;
			}
			if (!getItems(entitys).isEmpty()) {
				Set<ItemStack> i = getItems(entitys);
				if (Zephyrus.getCraftMap().containsKey(i)) {
					ISpell s = Zephyrus.getCraftMap().get(i);
					if (s.isEnabled()) {
						if (s.getReqLevel() <= 3) {
							if (e.getPlayer().hasPermission("zephyrus.spell." + s.getName().toLowerCase())) {
								if (s.getRequiredSpell() != null) {
									if (Zephyrus.getUser(e.getPlayer()).isLearned(s.getRequiredSpell())) {
										if (Zephyrus.getUser(e.getPlayer()).getLevel() >= s.getReqLevel()) {
											PlayerCraftSpellEvent craftEvent = new PlayerCraftSpellEvent(e.getPlayer(), s);
											Bukkit.getServer().getPluginManager().callEvent(craftEvent);
											if (!craftEvent.isCancelled()) {
												for (Item item : getItemEntity(entitys)) {
													item.remove();
												}
												dropSpell(e.getClickedBlock(), s.getDisplayName().toLowerCase(),
														s.getDesc(), e.getPlayer());
											}
										} else {
											e.getPlayer().sendMessage(
													ChatColor.RED
															+ Lang.get("wand.reqlevel").replace("[LEVEL]",
																	s.getReqLevel() + ""));
										}
									} else {
										e.getPlayer().sendMessage(
												Lang.get("wand.reqspell").replace("[SPELL]",
														s.getRequiredSpell().getDisplayName()));
									}
								} else {
									if (Zephyrus.getUser(e.getPlayer()).getLevel() >= s.getReqLevel()) {
										for (Item item : getItemEntity(entitys)) {
											item.remove();
										}
										dropSpell(e.getClickedBlock(), s.getDisplayName().toLowerCase(), s.getDesc(),
												e.getPlayer());
									} else {
										e.getPlayer().sendMessage(
												ChatColor.RED
														+ Lang.get("wand.reqlevel")
																.replace("[LEVEL]", s.getReqLevel() + ""));
									}
								}
							} else {
								e.getPlayer().sendMessage(
										ChatColor.RED + Lang.get("wand.noperm").replace("[WAND]", s.getDisplayName()));
							}
						} else {
							Lang.errMsg("wand.lowlevelwand", e.getPlayer());
						}
					} else {
						Lang.errMsg("disabled", e.getPlayer());
					}
				} else {
					Lang.errMsg("wand.nospell", e.getPlayer());
				}
			} else {
				Lang.errMsg("wand.nospell", e.getPlayer());
			}

		}
	}
	
}
