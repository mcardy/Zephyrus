package net.lordsofcode.zephyrus.enchantments;

import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class InstaMine extends CustomEnchantment {

	public InstaMine(int id) {
		super(id);
	}

	@Override
	public int enchantLevelCost() {
		return 15;
	}

	@Override
	public int chance() {
		return 7;
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		return pick(item);
	}

	@Override
	public boolean conflictsWith(Enchantment other) {
		if (other.equals(DIG_SPEED)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean incompatible(Map<Enchantment, Integer> map) {
		if (map.containsKey(DIG_SPEED)) {
			map.remove(DIG_SPEED);
		}
		if (map.containsKey(LOOT_BONUS_BLOCKS)) {
			map.remove(LOOT_BONUS_BLOCKS);
		}
		if (map.containsKey(SILK_TOUCH)) {
			return true;
		}
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		return "InstaMine";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK
				&& e.getClickedBlock().getType() != Material.BEDROCK
				&& e.getItem() != null && e.getItem().hasItemMeta()
				&& e.getItem().getItemMeta().hasEnchant(this)) {
			BlockBreakEvent ev = new BlockBreakEvent(e.getClickedBlock(),
					e.getPlayer());
			Bukkit.getPluginManager().callEvent(ev);
			if (!ev.isCancelled()) {
				e.getClickedBlock().breakNaturally(e.getItem());
				if (e.getItem().containsEnchantment(DURABILITY)) {
					if (new Random().nextInt(e.getItem().getEnchantmentLevel(
							DURABILITY)) == 0)
						e.getItem().setDurability(
								(short) (e.getItem().getDurability() + 1));
				} else {
					e.getItem().setDurability(
							(short) (e.getItem().getDurability() + 1));
				}
			}
		}
	}

}
