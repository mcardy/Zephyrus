package net.lordsofcode.zephyrus.example;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.lordsofcode.zephyrus.api.CustomEnchantment;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 *          To make a custom enchantment with Zephyrus you first need to extend
 *          CustomEnchantment. This will cover all of the basic things like the
 *          name of the enchantment and the chance of it being applied, etc.
 * 
 *          You will need to handle the effect of the enchantments yourself
 *          though. When the enchantment is registered with Zephyrus it is
 *          automatically registered as an event handler. 
 * 
 */

public class ExampleEnchantment extends CustomEnchantment {

	/**
	 * The default constructor for an enchantment defining the id of the
	 * enchantment
	 * 
	 * @param id
	 */
	public ExampleEnchantment(int id) {
		super(id);
	}
	
	@EventHandler
	public void onAttack() {
		
	}

	/**
	 * Gets the level cost of the enchantment
	 */
	@Override
	public int enchantLevelCost() {
		return 5;
	}

	/**
	 * The chance of the enchantment being placed onto the item. The higher the
	 * number, the lower the chance. Must be above 0.
	 */
	@Override
	public int chance() {
		return 1;
	}

	/**
	 * Whether or not the enchantment is incompatible with any of the
	 * enchantments already being applied.
	 */
	@Override
	public boolean incompatible(Map<Enchantment, Integer> map) {
		return false;
	}

	/**
	 * Whether or not the specified itemstack can be enchanted with the
	 * enchantment
	 */
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return isTool(arg0);
	}

	/**
	 * Whether or not the specified enchantment conflicts with the enchantment
	 */
	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	/**
	 * The enchantment target of the enchantment - Appears to be broken.
	 */
	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	/**
	 * The maximum obtainable level of the enchantment
	 */
	@Override
	public int getMaxLevel() {
		return 3;
	}

	/**
	 * The name of the enchantment
	 */
	@Override
	public String getName() {
		return "FireTool";
	}

	/**
	 * The starting level of the enchantment
	 */
	@Override
	public int getStartLevel() {
		return 1;
	}
	
	/**
	 * Sets the entity on fire if the sword has firetool on it.
	 */
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			ItemStack i = player.getItemInHand();
			if (i != null && i.hasItemMeta() && i.getItemMeta().hasEnchant(this)) {
				int level = i.getItemMeta().getEnchantLevel(this);
				e.getEntity().setFireTicks(level*20);
			}
		}
	}

}
