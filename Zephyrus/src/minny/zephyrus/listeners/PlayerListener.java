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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

	@EventHandler
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
		if (e.getInventory().getType() == InventoryType.MERCHANT
				&& e.getCursor().getType() != Material.AIR
				&& !checkName(e.getCursor(), "¤cRod of Fire")) {
			e.getWhoClicked().getServer().broadcastMessage("test1");
			if (e.getSlotType() == SlotType.CONTAINER) {
				e.setCancelled(true);
				e.getWhoClicked().getServer().broadcastMessage("test2");
			}
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

}
