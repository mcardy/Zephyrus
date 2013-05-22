package minny.zephyrus.listeners;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.CustomItem;
import minny.zephyrus.utils.merchant.Merchant;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemLevelListener implements Listener {

	Zephyrus plugin;

	public ItemLevelListener(Zephyrus plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClickWithItem(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			byte b = 12;
			if (block.getType() == Material.ENCHANTMENT_TABLE
					&& block.getData() == b) {
				ItemStack i = e.getItem();
				if (i != null
						&& i.hasItemMeta()
						&& i.getItemMeta().hasDisplayName()
						&& Zephyrus.itemMap.containsKey(i.getItemMeta()
								.getDisplayName())) {
					e.setCancelled(true);
					try {
						new CraftLivingEntity(null, null);
					} catch (NoClassDefFoundError err) {
						e.getPlayer()
								.sendMessage(
										"The version of CraftBukkit running on this server is not fully compatible with Zephyrus. This feature has been disabled...");
						return;
					}
					CustomItem customItem = Zephyrus.itemMap.get(i
							.getItemMeta().getDisplayName());
					if (!(customItem.getItemLevel(i) < customItem.maxLevel())) {
						e.getPlayer().sendMessage(
								"That item cannot be leveled anymore!");
						return;
					}
					if (Zephyrus.merchantMap.containsKey(e.getItem())) {
						Merchant m = Zephyrus.merchantMap.get(e.getItem());
						m.openTrading(e.getPlayer());
					} else {
						e.getPlayer().sendMessage("asdf");
					}
				}
			}
		}
	}

	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		//TODO Make custom trade NOT break vanilla trades
		if (e.getInventory().getType() == InventoryType.MERCHANT) {
			ItemStack i = e.getCurrentItem();
			ItemStack i2 = e.getCursor();
			Bukkit.broadcastMessage("" + e.getRawSlot());
			if (e.getRawSlot() != 0 && e.getRawSlot() != 1 && e.getRawSlot() != 2
					&& !Zephyrus.merchantMap.containsKey(i)
					&& !Zephyrus.merchantMap.containsKey(i2) && i != null
					&& i.getType() != Material.EMERALD && i2 != null
					&& i2.getType() != Material.EMERALD) {
				Bukkit.broadcastMessage(e.getInventory().getName());
				e.setCancelled(true);
			}
		}
	}
}
