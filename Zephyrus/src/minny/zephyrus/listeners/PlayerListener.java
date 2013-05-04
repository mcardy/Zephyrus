package minny.zephyrus.listeners;

import java.io.File;

import minny.zephyrus.Hooks;
import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.ManaRecharge;
import minny.zephyrus.utils.PlayerConfigHandler;
import minny.zephyrus.utils.merchantapi.Merchant;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends ItemUtil implements Listener {

	LevelManager lvl;

	public PlayerListener(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
	}

	Merchant upgrade = new Merchant();

	PlayerConfigHandler config;
	Hooks wgplugin;

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
		if (!checkPlayer.exists()) {
			config = new PlayerConfigHandler(plugin, e.getPlayer().getName()
					+ ".yml");
			config.saveDefaultConfig();
			config.getConfig().set("Level", 1);
			config.getConfig().set("mana", 100);
			config.saveConfig();

		}
	}

	@EventHandler
	public void setMana(PlayerJoinEvent e) {
		plugin.mana.put(e.getPlayer().getName(), lvl.loadMana(e.getPlayer()));
		new ManaRecharge(plugin, e.getPlayer()).runTaskLater(plugin, 30);
	}
	
	@EventHandler
	public void removeMana(PlayerQuitEvent e) {
		lvl.saveMana(e.getPlayer());		
		plugin.mana.remove(e.getPlayer().getName());
	}

}
