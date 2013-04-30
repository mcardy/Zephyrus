package minny.zephyrus.listeners;

import java.io.File;

import minny.zephyrus.Hooks;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.SetItem;
import minny.zephyrus.items.RodOfFire;
import minny.zephyrus.utils.DelayUtil;
import minny.zephyrus.utils.ParticleEffects;
import minny.zephyrus.utils.PlayerConfigHandler;
import minny.zephyrus.utils.merchantapi.Merchant;
import minny.zephyrus.utils.merchantapi.MerchantOffer;

import org.bukkit.ChatColor;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener extends SetItem implements Listener {

	public PlayerListener(Zephyrus plugin) {
		super(plugin);
	}

	Merchant upgrade = new Merchant();

	PlayerConfigHandler config;
	Hooks wgplugin;

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
			if (e.getAction() == Action.RIGHT_CLICK_AIR
					&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")
					&& !plugin.fireRod.contains(e.getPlayer().getName())) {
				Player player = e.getPlayer();
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				plugin.fireRod.add(e.getPlayer().getName());
				new DelayUtil(plugin.fireRod, e.getPlayer().getName())
						.runTaskLater(plugin, delayFromLevel(getItemLevel(e
								.getPlayer().getItemInHand())));
			} else if (plugin.fireRod.contains(e.getPlayer().getName())
					&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")) {
				e.getPlayer().sendMessage(
						ChatColor.GRAY + "Your wand is recharging...");
			}
		} catch (NullPointerException exception) {

		}
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		if (!plugin.lightningGem.contains(e.getPlayer().getName())
				&& e.getPlayer().getItemInHand().getType() == Material.EMERALD
				&& e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")) {
			Location loc = e.getPlayer().getTargetBlock(null, 100)
					.getLocation();
			e.getPlayer().getWorld().strikeLightning(loc);
			plugin.lightningGem.add(e.getPlayer().getName());
			new DelayUtil(plugin.lightningGem, e.getPlayer().getName())
					.runTaskLater(plugin, delayFromLevel(getItemLevel(e
							.getPlayer().getItemInHand())));
		} else if (plugin.lightningGem.contains(e.getPlayer().getName())
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")) {
			e.getPlayer().sendMessage(
					ChatColor.GRAY + "Your gem is recharging...");
		}

	}

	@EventHandler
	public void growWheat(PlayerInteractEvent e) throws Exception {
		if (e.getClickedBlock() != null
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getPlayer().getItemInHand().getType() == Material.GOLD_HOE
				&& e.getClickedBlock().getTypeId() == 59
				&& checkName(e.getPlayer().getItemInHand(), "¤aHoe of Growth")
				&& e.getClickedBlock().getData() != 7) {
			e.getClickedBlock().setData((byte) 7);
			ParticleEffects.HAPPY_VILLAGER.sendToPlayer(e.getPlayer(), e.getClickedBlock().getLocation(), 0, 0, 0, 1, 1);
		}
	}

	@EventHandler
	public void growSapling(PlayerInteractEvent e) throws Exception {
		if (e.getClickedBlock() != null
				&& e.getClickedBlock().getType() == Material.SAPLING
				&& checkName(e.getPlayer().getItemInHand(), "¤aHoe of Growth")
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			TreeType tt = getTree(b.getData());
			World world = e.getPlayer().getWorld();
			b.setTypeId(0);
			world.generateTree(b.getLocation(), tt);
			ParticleEffects.HAPPY_VILLAGER.sendToPlayer(e.getPlayer(), e.getClickedBlock().getLocation(), 0, 0, 0, 1, 1);
		}
	}

	//@EventHandler
	public void onUpgrade(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getType() == Material.EMERALD_BLOCK) {
			RodOfFire fire = new RodOfFire(plugin);

			ItemStack fireItem = fire.item();
			ItemStack newFireItem = fire.item();
			fire.setItemLevel(newFireItem, fire.getItemLevel(fireItem) + 1);

			MerchantOffer o1 = new MerchantOffer(fireItem, newFireItem);
			upgrade.addOffer(o1);
			upgrade.openTrading(e.getPlayer());
		}
	}

	@EventHandler
	public void cancellClick(InventoryClickEvent e) {
		e.getWhoClicked().getServer().broadcastMessage("test1");
		if (e.getInventory().getType() == InventoryType.MERCHANT) {

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

	public static int delayFromLevel(int level) {
		switch (level) {
		case 1:
			return 400;
		case 2:
			return 200;
		case 3:
			return 100;
		case 4:
			return 20;
		}
		return 0;
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
