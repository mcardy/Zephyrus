package net.lordsofcode.zephyrus.enchantments;

import java.util.Map;


import net.lordsofcode.zephyrus.api.CustomEnchantment;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class BattleAxe extends CustomEnchantment {

	public BattleAxe(int id) {
		super(id);
	}

	@Override
	public int enchantLevelCost() {
		return 5;
	}

	@Override
	public int chance() {
		return 3;
	}

	@Override
	public boolean incompatible(Map<Enchantment, Integer> map) {
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		if (item.getType() == Material.DIAMOND_AXE
				|| item.getType() == Material.GOLD_AXE
				|| item.getType() == Material.IRON_AXE
				|| item.getType() == Material.STONE_AXE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public String getName() {
		return "BattleAxe";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if (hasEnchantment(player.getItemInHand())) {
				int i = getEnchantment(player.getItemInHand());
				e.setDamage(e.getDamage() + i);
			}
		}
	}

}
