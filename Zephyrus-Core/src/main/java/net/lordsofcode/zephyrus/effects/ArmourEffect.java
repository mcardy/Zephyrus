package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ArmourEffect implements IEffect, Listener {

	public static ItemStack[] armour;
	public final int ID;

	public ArmourEffect(int ID) {
		this.ID = ID;

		ItemStack helm = new ItemStack(Material.GOLD_HELMET);
		ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
		ItemMeta meta = helm.getItemMeta();
		meta.setDisplayName(new ConfigHandler("lang.yml").getConfig().getString("spells.armour.name")
				.replace("$", ChatColor.COLOR_CHAR + ""));
		helm.setItemMeta(meta);
		chest.setItemMeta(meta);
		legs.setItemMeta(meta);
		boots.setItemMeta(meta);
		armour = new ItemStack[] { boots, legs, chest, helm };
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onApplied(Player player) {
		player.getInventory().setArmorContents(armour);
		player.updateInventory();
	}

	@Override
	public void onRemoved(Player player) {
		player.getInventory().setBoots(null);
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
	}

	@Override
	public void onTick(Player player) {
	}

	@Override
	public void onStartup(Player player) {
		if (checkArmour(player)) {
			player.getInventory().setBoots(null);
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
		}
	}

	@Override
	public int getTypeID() {
		return this.ID;
	}

	private boolean checkArmour(Player player) {
		if (player.getInventory().getArmorContents() != null) {
			for (ItemStack i : player.getInventory().getArmorContents()) {
				if (!checkStack(i)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkStack(ItemStack i) {
		return i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName()
				&& i.getItemMeta().getDisplayName().equals(Lang.get("spells.armour.name"));
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.ARMOR
				&& EffectHandler.hasEffect(e.getWhoClicked().getName(), EffectType.ARMOUR)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		for (ItemStack i : e.getDrops()) {
			if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()
					&& i.getItemMeta().getDisplayName().equalsIgnoreCase(Lang.get("spells.armour.name"))) {
				e.getDrops().remove(i);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		try {
			if (e.getEntity() instanceof Player && e.getCause() != DamageCause.VOID) {
				Player player = (Player) e.getEntity();
				if (EffectHandler.hasEffect(player, EffectType.ARMOUR)) {
					e.setDamage(e.getDamage() / 4.0F);
				}
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public void onWarning(Player player) {
	}

	@Override
	public int getTickTime() {
		return 0;
	}

}
