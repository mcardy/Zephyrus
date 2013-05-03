package minny.zephyrus.listeners;

import java.io.File;

import minny.zephyrus.Hooks;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.GemOfLightning;
import minny.zephyrus.items.HoeOfGrowth;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.PlayerConfigHandler;
import minny.zephyrus.utils.merchantapi.Merchant;
import minny.zephyrus.utils.merchantapi.MerchantOffer;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener extends ItemUtil implements Listener {

	public PlayerListener(Zephyrus plugin) {
		super(plugin);
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
				} else if (level.contains("II")
						&& player.getHealth() < 19) {
					player.setHealth(player.getHealth() + 2);
				} else if (level.contains("III")
						&& player.getHealth() < 17) {
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
			config.getConfig().set("level", 0);
			config.saveConfig();

		}
	}

	@EventHandler
	public void fireball(PlayerInteractEvent e) {
		RodOfFire fire = new RodOfFire(plugin);
		fire.fireball(e);
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		GemOfLightning gem = new GemOfLightning(plugin);
		gem.lightning(e);
	}

	@EventHandler
	public void growWheat(PlayerInteractEvent e) throws Exception {
		HoeOfGrowth hoe = new HoeOfGrowth(plugin);
		hoe.grow(e);
	}

}
