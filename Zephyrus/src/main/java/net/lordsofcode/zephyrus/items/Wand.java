package net.lordsofcode.zephyrus.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerCraftSpellEvent;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Wand extends CustomItem {

	public Wand() {
		Lang.add("wand.enchanter", "You have successfully created an #6#lArcane Leveller");
		Lang.add("wand.nospell", "Spell recipe not found!");
		Lang.add("wand.noperm", "You do not have permission to learn [SPELL]");
		Lang.add("wand.reqlevel", "That spell requires level [LEVEL]");
		Lang.add("wand.reqspell", "That spell requires the knowledge of [SPELL]");
	}

	@Override
	public String getName() {
		return ChatColor.GOLD + "Wand";
	}

	@Override
	public ItemStack getItem() {
		int id = Zephyrus.getConfig().getInt("Wand-ID");
		ItemStack i;
		try {
			i = new ItemStack(Material.getMaterial(id));
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
		}
		try {
			setItemName(i, getDisplayName());
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
			setItemName(i, getDisplayName());
		}
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Regular old default wand");
		m.setLore(lore);
		i.setItemMeta(m);
		setGlow(i);
		return i;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		recipe.shape("  C", " B ", "A  ");
		recipe.setIngredient('C', Material.GLOWSTONE_DUST);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.GOLD_INGOT);
		return recipe;
	}

	@EventHandler
	public void arcaneClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && checkName(e.getItem(), getDisplayName())
				&& e.getItem().getItemMeta().getLore().get(0).contains("wand")) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.ENCHANTMENT_TABLE && b.getData() != 12) {
				e.setCancelled(true);
				b.setData((byte) 12);
				Lang.msg("wand.enchanter", e.getPlayer());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void bookShelfClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BOOKSHELF
				&& checkName(e.getPlayer().getItemInHand(), getDisplayName())
				&& e.getItem().getItemMeta().getLore().get(0).contains("wand")) {
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

	public Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public void dropSpell(Block bookshelf, String name, String desc, Player player) {
		Random r = new Random();
		bookshelf.setType(Material.AIR);
		SpellTome tome = new SpellTome(name, desc);
		Location loc = bookshelf.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		loc.getWorld().dropItem(loc.add(0, +1, 0), tome.item()).setVelocity(new Vector(0, 0, 0));
		int chance = 1;
		if (Zephyrus.getUser(player).getLevel() < 7) {
			chance = 1;
		} else if (Zephyrus.getUser(player).getLevel() < 15) {
			chance = 2;
		} else {
			chance = 3;
		}
		loc.getWorld().dropItemNaturally(loc.add(0, +0.5, 0), new ItemStack(Material.BOOK, r.nextInt(chance)));
		Effects.playEffect(ParticleEffects.ENCHANTMENT_TABLE, loc, 0, 0, 0, 1, 30);
		Effects.playEffect(Sound.ORB_PICKUP, loc, 3, 12);
	}

	public Set<ItemStack> getItems(Entity[] entitys) {
		Set<ItemStack> items = new HashSet<ItemStack>();
		for (Entity e : entitys) {
			if (e.getType() == EntityType.DROPPED_ITEM) {
				Item ei = (Item) e;
				ItemStack i = ei.getItemStack();
				items.add(i);
			}
		}
		return items;
	}

	public static Set<Item> getItemEntity(Entity[] entitys) {
		Set<Item> l = new HashSet<Item>();
		for (Entity e : entitys) {
			if (e instanceof Item) {
				Item i = (Item) e;
				l.add(i);
			}
		}
		return l;
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@EventHandler
	public void onBoundSpell(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack i = e.getItem();
			if (i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName()
					&& i.getItemMeta().getDisplayName().contains(getDisplayName())) {
				String s = i.getItemMeta().getLore().get(0)
						.replace(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY, "");
				if (Zephyrus.getSpellMap().containsKey(s)) {
					ISpell spell = Zephyrus.getSpellMap().get(s);
					Player player = e.getPlayer();
					IUser user = Zephyrus.getUser(player);
					if (user.isLearned(spell) || user.hasPermission(spell)) {
						if (user.hasMana(spell.getManaCost())) {
							PlayerCastSpellEvent event = new PlayerCastSpellEvent(player, spell, null);
							Bukkit.getServer().getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								boolean b = spell.run(player, null);
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
				}
			}
		}
	}

	@Override
	public String getPerm() {
		return "wand";
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if (e.getRecipe().getResult() == getRecipe().getResult()
				&& Zephyrus.getConfig().getBoolean("Disable-Wand-Recipe")) {
			e.getInventory().setResult(null);
		}
	}
}
