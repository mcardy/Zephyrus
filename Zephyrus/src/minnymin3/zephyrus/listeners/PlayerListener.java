package minnymin3.zephyrus.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.events.PlayerCraftCustomItemEvent;
import minnymin3.zephyrus.items.CustomItem;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.player.ManaRecharge;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.utils.ItemUtil;
import minnymin3.zephyrus.utils.PlayerConfigHandler;
import minnymin3.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerListener extends ItemUtil implements Listener {

	LevelManager lvl;
	Map<ItemStack, CustomItem> results;

	public PlayerListener(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
		results = new HashMap<ItemStack, CustomItem>();
		for (String s : Zephyrus.itemMap.keySet()) {
			CustomItem item = Zephyrus.itemMap.get(s);
			if (item.recipe() != null) {
				results.put(item.recipe().getResult(), item);
			}
		}
	}

	@EventHandler
	public void onUpdateMessage(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("zephyrus.notify")) {
			Player player = e.getPlayer();
			if (UpdateChecker.isUpdate) {
				player.sendMessage(ChatColor.DARK_RED
						+ "Zephyrus is out of date!");
				player.sendMessage(ChatColor.DARK_RED
						+ "Get the new version at: " + ChatColor.GRAY
						+ "dev.bukkit.org/server-mods/Zephyrus");
				player.sendMessage(ChatColor.DARK_RED + "ChangeLog: "
						+ ChatColor.GRAY + UpdateChecker.changelog);
			}
		}
	}

	@EventHandler
	public void craftingHandler(PrepareItemCraftEvent e) {
		if (results.containsKey(e.getRecipe().getResult())) {
			CustomItem item = results.get(e.getRecipe().getResult());
			List<HumanEntity> player = e.getViewers();
			PlayerCraftCustomItemEvent event = new PlayerCraftCustomItemEvent(
					player, item, e);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				e.getInventory().setResult(null);
			}
			for (HumanEntity en : e.getViewers()) {
				if (!en.hasPermission("zephyrus.craft." + item.perm())
						&& !en.hasPermission("zephyrus.craft.*")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}

	@EventHandler
	public void onSuckHealthEnchant(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (player.getItemInHand().getType() != Material.AIR
					&& checkName(player.getItemInHand(),
							"¤aDiamond Sword of Life")
					&& player.getHealth() != 20) {
				String level = player.getItemInHand().getItemMeta().getLore()
						.get(0);
				if (level.contains("I") && player.getHealth() < 20) {
					player.setHealth(player.getHealth() + 1);
				} else if (level.contains("II") && player.getHealth() < 19) {
					player.setHealth(player.getHealth() + 2);
				} else if (level.contains("III") && player.getHealth() < 17) {
					player.setHealth(player.getHealth() + 4);
				} else {
					player.setHealth(20);
				}
			}
		}
	}

	@EventHandler
	public void playerFile(PlayerJoinEvent e) {
		File playerFiles = new File(plugin.getDataFolder(), "Players");
		File checkPlayer = new File(playerFiles, e.getPlayer().getName()
				+ ".yml");
		Player player = e.getPlayer();
		if (!checkPlayer.exists()) {
			List<String> l = new ArrayList<String>();
			for (Spell spell : Zephyrus.spellMap.values()) {
				if (spell.getLevel() == 1) {
					List<String> learned = PlayerConfigHandler.getConfig(
							plugin, player).getStringList("learned");
					learned.add(spell.name());
					PlayerConfigHandler.getConfig(plugin, player).set(
							"learned", learned);
					PlayerConfigHandler.saveConfig(plugin, player);
					l.add(spell.name());
				}
			}
			PlayerConfigHandler.saveDefaultConfig(plugin, player);
			PlayerConfigHandler.getConfig(plugin, player).set("Level", 1);
			PlayerConfigHandler.getConfig(plugin, player).set("mana", 100);
			PlayerConfigHandler.getConfig(plugin, player).set("learned", l);
			PlayerConfigHandler.getConfig(plugin, player).set("progress", 0);
			PlayerConfigHandler.saveConfig(plugin, player);

		}
	}

	@EventHandler
	public void setMana(PlayerJoinEvent e) {
		Zephyrus.mana.put(e.getPlayer().getName(),
				LevelManager.loadMana(e.getPlayer()));
		new ManaRecharge(plugin, e.getPlayer()).runTaskLater(plugin, 30);
	}

	@EventHandler
	public void removeMana(PlayerQuitEvent e) {
		LevelManager.saveMana(e.getPlayer());
		Zephyrus.mana.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void removeMana(PlayerKickEvent e) {
		LevelManager.saveMana(e.getPlayer());
		Zephyrus.mana.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onMagicArmourLogin(PlayerJoinEvent e) {
		if (e.getPlayer().getInventory().getBoots() != null
				&& e.getPlayer().getInventory().getBoots().hasItemMeta()
				&& e.getPlayer().getInventory().getBoots().getItemMeta()
						.hasDisplayName()
				&& e.getPlayer().getInventory().getBoots().getItemMeta()
						.getDisplayName().equals("¤6Maigc Armour")) {
			e.getPlayer().getInventory().getBoots().setType(Material.AIR);
			e.getPlayer().getInventory().getLeggings().setType(Material.AIR);
			e.getPlayer().getInventory().getChestplate().setType(Material.AIR);
			e.getPlayer().getInventory().getHelmet().setType(Material.AIR);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMagicArmourClick(InventoryClickEvent e) {
		if (e.getInventory().getType() == InventoryType.PLAYER) {
			PlayerInventory inv = (PlayerInventory) e.getInventory();
			ItemStack helm = new ItemStack(Material.GOLD_HELMET);
			ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
			ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
			ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
			ItemMeta meta = helm.getItemMeta();
			meta.setDisplayName("¤6Magic Armour");
			helm.setItemMeta(meta);
			chest.setItemMeta(meta);
			legs.setItemMeta(meta);
			boots.setItemMeta(meta);
			if (e.getRawSlot() == 5 && checkName(inv.getHelmet(), "¤6Magic Armour")) {
				e.setCursor(null);
				e.getWhoClicked().getInventory().setBoots(boots);
				e.getWhoClicked().getInventory().setLeggings(legs);
				e.getWhoClicked().getInventory().setChestplate(chest);
				e.getWhoClicked().getInventory().setHelmet(helm);
				if (e.getWhoClicked() instanceof Player) {
					Player player = (Player) e.getWhoClicked();
					player.updateInventory();
				}
			} else if (e.getRawSlot() == 6 && checkName(inv.getChestplate(), "¤6Magic Armour")) {
				e.setCursor(null);
				e.getWhoClicked().getInventory().setBoots(boots);
				e.getWhoClicked().getInventory().setLeggings(legs);
				e.getWhoClicked().getInventory().setChestplate(chest);
				e.getWhoClicked().getInventory().setHelmet(helm);
				if (e.getWhoClicked() instanceof Player) {
					Player player = (Player) e.getWhoClicked();
					player.updateInventory();
				}
			} else if (e.getRawSlot() == 7 && checkName(inv.getLeggings(), "¤6Magic Armour")) {
				e.setCursor(null);
				e.getWhoClicked().getInventory().setBoots(boots);
				e.getWhoClicked().getInventory().setLeggings(legs);
				e.getWhoClicked().getInventory().setChestplate(chest);
				e.getWhoClicked().getInventory().setHelmet(helm);
				if (e.getWhoClicked() instanceof Player) {
					Player player = (Player) e.getWhoClicked();
					player.updateInventory();
				}
			} else if (e.getRawSlot() == 8 && checkName(inv.getBoots(), "¤6Magic Armour")) {
				e.setCursor(null);
				e.getWhoClicked().getInventory().setBoots(boots);
				e.getWhoClicked().getInventory().setLeggings(legs);
				e.getWhoClicked().getInventory().setChestplate(chest);
				e.getWhoClicked().getInventory().setHelmet(helm);
				if (e.getWhoClicked() instanceof Player) {
					Player player = (Player) e.getWhoClicked();
					player.updateInventory();
				}
			}
			if (e.getCursor() == boots || e.getCursor() == legs || e.getCursor() == chest || e.getCursor() == helm
					|| e.getCurrentItem() == boots || e.getCurrentItem() == legs || e.getCurrentItem() == chest || e.getCurrentItem() == helm) {
				e.setCurrentItem(null);
				e.setCancelled(true);
				e.setCursor(null);
				e.getWhoClicked().getInventory().setBoots(boots);
				e.getWhoClicked().getInventory().setLeggings(legs);
				e.getWhoClicked().getInventory().setChestplate(chest);
				e.getWhoClicked().getInventory().setHelmet(helm);
				if (e.getWhoClicked() instanceof Player) {
					Player player = (Player) e.getWhoClicked();
					player.updateInventory();
				}
				
			}
		}
	}

	@EventHandler
	public void onBreakJail(BlockBreakEvent e) {
		if (e.getPlayer() != null) {
			Block b = e.getBlock();
			if (b.getType() == Material.IRON_FENCE || b.getType() == Material.IRON_BLOCK) {
				if (b.getData() == 12) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.GRAY + "You cannot break jail blocks!");
				}
			}
		}
	}
	
	@EventHandler
	public void onMagicArmour(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (player.getInventory().getBoots() != null
					&& player.getInventory().getBoots().hasItemMeta()
					&& player.getInventory().getBoots().getItemMeta()
							.getDisplayName().equals("¤6Magic Armour")) {
				e.setCancelled(true);
			}
		}
	}
}
