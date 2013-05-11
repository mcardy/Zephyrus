package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.spells.Spell;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Wand extends CustomItem {

	LevelManager lvl;

	public Wand(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
	}

	@Override
	public String name() {
		return "¤6Wand";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.STICK);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Default wand used to learn spells");
		m.setLore(lore);
		i.setItemMeta(m);
		setGlow(i);
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

	@EventHandler(priority = EventPriority.LOW)
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType() == Material.BOOKSHELF
				&& checkName(e.getPlayer().getItemInHand(), this.name())
				&& e.getItem().getItemMeta().getLore().get(0)
						.contains("Default wand")) {
			Location loc = e.getClickedBlock().getLocation();
			loc.setY(loc.getY() + 1);
			Entity[] entitys = getNearbyEntities(loc, 1);
			if (!getItems(entitys).isEmpty()) {
				Set<ItemStack> i = getItems(entitys);
				if (plugin.spellCraftMap.containsKey(i)) {
					Spell s = plugin.spellCraftMap.get(i);
					if (s.reqSpell() != null) {
						if (s.isLearned(e.getPlayer(), s.reqSpell().name())) {
							if (!(s.getLevel(e.getPlayer()) < s.reqLevel())) {
								for (Item item : getItemEntity(entitys)) {
									item.remove();
								}
								s.dropSpell(e.getClickedBlock(), s.name(),
										s.bookText());
							} else {
								e.getPlayer().sendMessage(
										"This spell requires level "
												+ s.reqLevel());
							}
						} else {
							e.getPlayer().sendMessage(
									"This spell requires the knowledge of "
											+ ChatColor.GOLD
											+ s.reqSpell().name());
						}
					} else {
						if (!(s.getLevel(e.getPlayer()) < s.reqLevel())) {
							for (Item item : getItemEntity(entitys)) {
								item.remove();
							}
							s.dropSpell(e.getClickedBlock(), s.name(),
									s.bookText());
						} else {
							e.getPlayer()
									.sendMessage(
											"This spell requires level "
													+ s.reqLevel());
							;
						}
					}
				} else {
					e.getPlayer().sendMessage("Spell recipe not found");
				}
			} else {
				e.getPlayer().sendMessage("Spell recipe not found");
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
							&& e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
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
	public void onWand(PlayerInteractEvent e) {
		if (checkName(e.getItem(), this.name())) {
			ItemStack i = e.getItem();
			String s = i
					.getItemMeta()
					.getLore()
					.get(0)
					.replace(
							ChatColor.GRAY + "Bound spell: "
									+ ChatColor.DARK_GRAY, "");
			if (plugin.spellMap.containsKey(s)) {
				Spell spell = plugin.spellMap.get(s);
				Player player = e.getPlayer();
				if (!(lvl.getMana(player) < spell.manaCost())) {
					if (spell.canRun(player)) {
						spell.run(player);
						spell.drainMana(player, spell.manaCost() * plugin.getConfig().getInt("ManaMultiplier"));
					} else {
						if (spell.failMessage() != "") {
							player.sendMessage(spell.failMessage());
						}
					}
				} else {
					player.sendMessage("Not enough mana!");
				}
			}
		}
	}

}
