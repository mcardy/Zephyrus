package net.lordsofcode.zephyrus.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerCraftSpellEvent;
import net.lordsofcode.zephyrus.hooks.PluginHook;
import net.lordsofcode.zephyrus.player.LevelManager;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

public class Wand extends CustomItem {

	public Wand(Zephyrus plugin) {
		super(plugin);
		Lang.add("wand.enchanter",
				"You have successfully created an #6#lArcane Leveller");
		Lang.add("wand.nospell", "Spell recipe not found!");
		Lang.add("wand.noperm", "You do not have permission to learn [SPELL]");
		Lang.add("wand.reqlevel", "That spell requires level [LEVEL]");
		Lang.add("wand.reqspell", "That spell requires the knowledge of [SPELL]");
	}

	@Override
	public String name() {
		return ChatColor.GOLD + "Wand";
	}

	public static ItemStack getItem() {
		int id = Zephyrus.getInstance().getConfig().getInt("Wand-ID");
		ItemStack i;
		try {
			i = new ItemStack(Material.getMaterial(id));
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
		}
		ItemMeta m;
		try {
			m = i.getItemMeta();
			m.setDisplayName(ChatColor.GOLD + "Wand");
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
			m = i.getItemMeta();
			m.setDisplayName(ChatColor.GOLD + "Wand");
		}
		m.setDisplayName(ChatColor.GOLD + "Wand");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Regular old default wand");
		m.setLore(lore);
		i.setItemMeta(m);
		i.addEnchantment(Zephyrus.getInstance().glow, 1);
		return i;
	}

	@Override
	public ItemStack item() {
		int id = Zephyrus.getInstance().getConfig().getInt("Wand-ID");
		ItemStack i;
		try {
			i = new ItemStack(Material.getMaterial(id));
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
		}
		try {
			setItemName(i, this.name());
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
			setItemName(i, this.name());
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
	public Recipe recipe() {
		ShapedRecipe recipe = new ShapedRecipe(item());
		recipe.shape("  C", " B ", "A  ");
		recipe.setIngredient('C', Material.GLOWSTONE_DUST);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.GOLD_INGOT);
		return recipe;
	}

	@EventHandler
	public void arcaneClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& checkName(e.getItem(), this.name())
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
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType() == Material.BOOKSHELF
				&& checkName(e.getPlayer().getItemInHand(), this.name())
				&& e.getItem().getItemMeta().getLore().get(0).contains("wand")) {
			if (plugin.getConfig().getBoolean("Disable-Spell-Crafting")) {
				return;
			}
			Location loc = e.getClickedBlock().getLocation();
			if (!PluginHook.canBuild(e.getPlayer(), loc)) {
				return;
			}
			loc.setY(loc.getY() + 1);
			Entity[] entitys = getNearbyEntities(loc, 1);
			if (!getItems(entitys).isEmpty()) {
				Set<ItemStack> i = getItems(entitys);
				if (Zephyrus.spellCraftMap.containsKey(i)) {
					Spell s = Zephyrus.spellCraftMap.get(i);
					if (s.isEnabled()) {
						if (e.getPlayer().hasPermission(
								"zephyrus.spell." + s.name().toLowerCase())
								|| e.getPlayer().hasPermission(
										"zephyrus.spell.*")) {
							if (s.reqSpell() != "") {
								if (s.isLearned(e.getPlayer(), Zephyrus.spellMap.get(s.reqSpell().toLowerCase()).getDisplayName())) {
									if (!(LevelManager.getLevel(e.getPlayer()) < s
											.getLevel())) {
										PlayerCraftSpellEvent event = new PlayerCraftSpellEvent(
												e.getPlayer(), s);
										Bukkit.getServer().getPluginManager()
												.callEvent(event);
										if (!event.isCancelled()) {
											for (Item item : getItemEntity(entitys)) {
												item.remove();
											}
											s.dropSpell(e.getClickedBlock(),
													s.getDisplayName().toLowerCase(), s.getDesc(),
													e.getPlayer());
										}
									} else {
										e.getPlayer().sendMessage(ChatColor.RED + Lang.get("wand.reqlevel").replace("[LEVEL]", s.reqLevel() + ""));
									}
								} else {
									e.getPlayer().sendMessage(
											Lang.get("wand.reqspell").replace("[SPELL]", Zephyrus.spellMap.get(s.reqSpell().toLowerCase()).getDisplayName()));
								}
							} else {
								if (!(LevelManager.getLevel(e.getPlayer()) < s
										.getLevel())) {
									for (Item item : getItemEntity(entitys)) {
										item.remove();
									}
									s.dropSpell(e.getClickedBlock(), s.getDisplayName().toLowerCase(),
											s.getDesc(), e.getPlayer());
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + Lang.get("wand.reqlevel").replace("[LEVEL]", s.reqLevel() + ""));
								}
							}
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + Lang.get("wand.noperm").replace("[WAND]", s.getDisplayName()));
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

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
						+ (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius
							&& e.getLocation().getBlock() != l.getBlock()) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public static Set<ItemStack> getItems(Entity[] entitys) {
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
			if (e.getType() == EntityType.DROPPED_ITEM) {
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
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack i = e.getItem();
			if (i != null && i.hasItemMeta()
					&& i.getItemMeta().hasDisplayName()
					&& i.getItemMeta().getDisplayName().contains(this.name())) {
				String s = i
						.getItemMeta()
						.getLore()
						.get(0)
						.replace(
								ChatColor.GRAY + "Bound spell: "
										+ ChatColor.DARK_GRAY, "");
				if (Zephyrus.spellMap.containsKey(s)) {
					Spell spell = Zephyrus.spellMap.get(s);
					Player player = e.getPlayer();
					if (spell.isLearned(player, spell.getDisplayName().toLowerCase())) {
						if (spell.isEnabled()) {
							if (!(LevelManager.getMana(player) < spell
									.getManaCost()
									* plugin.getConfig().getInt(
											"ManaMultiplier"))) {
								if (spell.canRun(player, null)) {
									PlayerCastSpellEvent event = new PlayerCastSpellEvent(
											player, spell, null);
									Bukkit.getServer().getPluginManager()
											.callEvent(event);
									if (!event.isCancelled()) {
										spell.run(player, null);
										LevelManager
												.drainMana(
														player,
														spell.getManaCost()
																* plugin.getConfig()
																		.getInt("ManaMultiplier"));
									}
								} else {
									if (spell.getFailMessage() != "") {
										player.sendMessage(spell.getFailMessage());
									}
								}
							} else {
								Lang.errMsg("nomana", e.getPlayer());
							}
						} else {
							Lang.errMsg("disabled", e.getPlayer());
						}
					} else {
						Lang.errMsg("notlearned", e.getPlayer());
					}
				}
			}
		}
	}

	@Override
	public int maxLevel() {
		return 0;
	}

	@Override
	public String perm() {
		return "wand";
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if (e.getRecipe().getResult() == this.recipe().getResult()
				&& plugin.getConfig().getBoolean("Disable-Wand-Recipe")) {
			e.getInventory().setResult(null);
		}
	}
}
