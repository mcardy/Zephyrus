package net.lordsofcode.zephyrus.enchantments;

import java.util.Map;
import java.util.Random;

import net.lordsofcode.zephyrus.api.CustomEnchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ToxicStrike extends CustomEnchantment {

	public ToxicStrike(int id) {
		super(id);
	}

	@Override
	public int enchantLevelCost() {
		return 10;
	}

	@Override
	public int chance() {
		return 5;
	}

	@Override
	public boolean incompatible(Map<Enchantment, Integer> map) {
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return isSword(arg0);
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.WEAPON;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String getName() {
		return "ToxicStrike";
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
				int chance = new Random().nextInt(5 - i);
				if (chance == 1) {
					((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 2));
				}
			}
		}
	}

}
