package minny.zephyrus.listeners;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.CustomItem;
import minny.zephyrus.utils.Merchant;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
										"Saadly, the version of CraftBukkit running on this server is not fully compatible with this version of Zephyrus. This feature has been disabled...");
						return;
					}
					CustomItem customItem = Zephyrus.itemMap.get(i
							.getItemMeta().getDisplayName());
					if (!(customItem.getItemLevel(i) < customItem.maxLevel())) {
						e.getPlayer().sendMessage(
								"That item is already max level!");
						return;
					}
					if (Zephyrus.merchantMap.containsKey(e.getItem())) {
						Merchant mer = Zephyrus.merchantMap.get(e.getItem());
						Merchant m = mer.clone();
						m.openTrade(e.getPlayer());
						plugin.invPlayers.put(e.getPlayer().getName(), m);
					} else {
						e.getPlayer().sendMessage(
								"Something went wrong. Item not found...");
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (plugin.invPlayers.containsKey(e.getPlayer().getName())) {
			plugin.invPlayers.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		if (plugin.invPlayers.containsKey(e.getWhoClicked().getName())) {
			if (e.getInventory().getType() == InventoryType.MERCHANT) {
				Merchant m = plugin.invPlayers.get(e.getWhoClicked().getName());
				ItemStack i = e.getCurrentItem();
				ItemStack i2 = e.getCursor();
				ItemStack mi = m.getInput1();
				ItemStack m2 = m.getOutput();
				if (e.getRawSlot() != 0 && e.getRawSlot() != 1 && i != null
						&& i2 != null && e.getRawSlot() != 2 && !i.equals(mi)
						&& !i.equals(m2) && !i2.equals(mi) && !i2.equals(m2)
						&& i.getType() != Material.EMERALD
						&& i2.getType() != Material.EMERALD) {
					e.setCancelled(true);
				}
				if (i != null && i.getType() == Material.EMERALD || i != null
						&& i2.getType() == Material.EMERALD) {
					if (i.hasItemMeta() || i2.hasItemMeta()) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
