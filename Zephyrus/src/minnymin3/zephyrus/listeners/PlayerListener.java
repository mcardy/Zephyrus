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
import minnymin3.zephyrus.utils.ItemUtil;
import minnymin3.zephyrus.utils.PlayerConfigHandler;
import minnymin3.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
			PlayerConfigHandler.saveDefaultConfig(plugin, player);
			PlayerConfigHandler.getConfig(plugin, player).set("Level", 1);
			PlayerConfigHandler.getConfig(plugin, player).set("mana", 100);
			PlayerConfigHandler.getConfig(plugin, player).set("learned",
					new ArrayList<String>());
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
	public void onMagicArmourClick(InventoryClickEvent e) {
		if (checkName(e.getCurrentItem(), "¤6Magic Armour")) {
			e.setCancelled(true);
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
