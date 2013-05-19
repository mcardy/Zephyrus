package minny.zephyrus.listeners;

import java.io.File;
import java.util.ArrayList;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.player.ManaRecharge;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerListener extends ItemUtil implements Listener {

	LevelManager lvl;

	public PlayerListener(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
	}

	@EventHandler
	public void onUpdateMessage(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("zephyrus.notify")) {
			Player player = e.getPlayer();
			if (plugin.isUpdate) {
				player.sendMessage(ChatColor.RED
						+ "There is a new version of Zephyrus out!");
				player.sendMessage(ChatColor.DARK_AQUA + "Get it at: "
						+ ChatColor.GRAY
						+ "dev.bukkit.org/server-mods/Zephyrus");
				player.sendMessage(ChatColor.DARK_AQUA + "[ChangeLog] "
						+ plugin.changelog);
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

	@EventHandler
	public void onCast(SpellCastEvent e) {
		if (plugin.getConfig().getBoolean("Debug-Mode")) {
			plugin.getLogger().info(
					e.getPlayer().getName() + " casted " + e.getSpell().name()
							+ " at " + e.getPlayer().getLocation());
		}
	}

}
