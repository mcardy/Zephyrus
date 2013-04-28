package minny.zephyrus.listeners;

import java.io.File;

import minny.zephyrus.Hooks;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	PlayerConfigHandler config;
	Zephyrus plugin;

	Hooks wgplugin;

	public PlayerListener(Zephyrus plugin) {
		this.plugin = plugin;
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
		try {
			if (e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD
					&& e.getAction() == Action.RIGHT_CLICK_AIR
					&& e.getPlayer().getItemInHand().getItemMeta()
							.getDisplayName().equalsIgnoreCase("¤cRod of Fire")) {
				Player player = e.getPlayer();
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
			}
		} catch (NullPointerException exception) {

		}
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().getType() == Material.EMERALD
				&& e.getAction() == Action.RIGHT_CLICK_AIR
				&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("¤bGem of Lightning")) {
			Location loc = e.getPlayer().getTargetBlock(null, 100)
					.getLocation();
			e.getPlayer().getWorld().strikeLightning(loc);
		}
	}

	@EventHandler
	public void growWheat(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().getType() == Material.GOLD_HOE
				&& e.getClickedBlock().getTypeId() == 59
				&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("¤aHoe of Growth")
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.getPlayer().sendMessage("test");
			Block w = e.getClickedBlock();
			w.setData((byte) 7);
		}
	}

	@EventHandler
	public void growSapling(PlayerInteractEvent e) {
		if (e.getClickedBlock().getType() == Material.SAPLING && e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("¤aHoe of Growth")
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			TreeType tt = getTree(b.getData());
			World world = e.getPlayer().getWorld();
			b.setTypeId(0);
			world.generateTree(b.getLocation(), tt);
		}
	}

	// @EventHandler
	public void skeleton(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof LivingEntity) {
			if (e.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM
					&& e.getPlayer().getItemInHand().getItemMeta()
							.getDisplayName().equalsIgnoreCase("¤8Skull")) {
				LivingEntity entity = (LivingEntity) e.getRightClicked();
				Creature skeleton = (Creature) entity.getWorld().spawnEntity(
						entity.getLocation(), EntityType.ZOMBIE);
				skeleton.setCustomName(e.getPlayer().getName() + "skeleton");
			}
		}
	}

	public static TreeType getTree(int data) {
		switch (data) {
		case 0:
			return TreeType.TREE;
		case 1:

			return TreeType.REDWOOD;

		case 2:

			return TreeType.BIRCH;
		case 3:

			return TreeType.SMALL_JUNGLE;

		}
		return TreeType.TREE;
	}
}
