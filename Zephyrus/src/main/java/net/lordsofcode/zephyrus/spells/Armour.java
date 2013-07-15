package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Armour extends Spell {

	public static ItemStack[] armor;

	public Armour() {
		Lang.add("spells.armour.applied",
				"$6Your skin feels hard with magic and gold!");
		Lang.add("spells.armour.name", "$6Magic Armour");

		Lang.add("spells.armour.fail", "You can't be wearing armour!");
		
		ItemStack helm = new ItemStack(Material.GOLD_HELMET);
		ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
		ItemMeta meta = helm.getItemMeta();
		meta.setDisplayName(new ConfigHandler("lang.yml").getConfig()
				.getString("spells.armour.name")
				.replace("$", ChatColor.COLOR_CHAR + ""));
		helm.setItemMeta(meta);
		chest.setItemMeta(meta);
		legs.setItemMeta(meta);
		boots.setItemMeta(meta);
		armor = new ItemStack[] { boots, legs, chest, helm };
	}

	@Override
	public String getName() {
		return "armour";
	}

	@Override
	public String getDesc() {
		return "A set of magical armour that can be called whenever you need it! The armour will block all damage for 30 seconds!";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 60;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, String[] args) {
		if (player.getInventory().getHelmet() == null
				&& player.getInventory().getChestplate() == null
				&& player.getInventory().getLeggings() == null
				&& player.getInventory().getBoots() == null) {
			int time = getConfig().getInt(this.getName() + ".delay");
			player.getInventory().setArmorContents(armor);
			player.updateInventory();
			startDelay(player, time * 20);
			playerMap.add(player.getName());
			Lang.msg("spells.armour.applied", player);
			return true;
		} else {
			Lang.errMsg("spells.armour.fail", player); 
			return false;
		}
	}

	@Override
	public void delayedAction(Player player) {
		player.getInventory().setBoots(null);
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		if (player.isOnline()) {
			playerMap.remove(player.getName());
		}
	}

	@Override
	public void onDisable() {
		for (String s : playerMap) {
			Player player = Bukkit.getPlayer(s);
			delayedAction(player);
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delay", 60);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.GOLD_BOOTS));
		i.add(new ItemStack(Material.GOLD_LEGGINGS));
		i.add(new ItemStack(Material.GOLD_CHESTPLATE));
		i.add(new ItemStack(Material.GOLD_HELMET));
		return i;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.ARMOR
				&& playerMap.contains(e.getWhoClicked().getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (playerMap.contains(e.getPlayer().getName())) {
			delayedAction(e.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		if (playerMap.contains(e.getPlayer().getName())) {
			delayedAction(e.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		for (ItemStack i : e.getDrops()) {
			if (i.hasItemMeta()
					&& i.getItemMeta().hasDisplayName()
					&& i.getItemMeta().getDisplayName()
							.equalsIgnoreCase(Lang.get("spells.armour.name"))) {
				e.getDrops().remove(i);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() != DamageCause.VOID) {
			Player player = (Player) e.getEntity();
			if (player.getInventory().getBoots() != null
					&& player.getInventory().getBoots().hasItemMeta()
					&& player.getInventory().getBoots().getItemMeta()
							.hasDisplayName()
					&& player.getInventory().getBoots().getItemMeta()
							.getDisplayName()
							.equals(Lang.get("spells.armour.name"))) {
				e.setCancelled(true);
			}
		}
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.MAGIC;
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
}
