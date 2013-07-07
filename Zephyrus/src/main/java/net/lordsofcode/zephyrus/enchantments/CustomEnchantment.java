package net.lordsofcode.zephyrus.enchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class CustomEnchantment extends Enchantment implements Listener {

	/**
	 * Creates a new Custom Enchantment
	 * 
	 * @param id
	 *            The id of the enchantment. Should be unique
	 */
	public CustomEnchantment(int id) {
		super(id);
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
		}
		try {
			Enchantment.registerEnchantment(this);
		} catch (IllegalArgumentException e) {
		}
		Bukkit.getPluginManager().registerEvents(this, Zephyrus.getInstance());
	}

	/**
	 * The cost of the enchantment per level
	 */
	public abstract int enchantLevelCost();

	/**
	 * The chance of the enchantment being applied to the item when it is
	 * enchanted
	 */
	public abstract int chance();

	/**
	 * Whether or not the enchantment should be applied
	 * 
	 * @param map
	 *            The map of enchantments to be applied
	 * @return True if the enchantment is incompatible and should not be applied
	 */
	public abstract boolean incompatible(Map<Enchantment, Integer> map);

	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		int level = e.getExpLevelCost() / enchantLevelCost();
		int chance = new Random().nextInt(chance());
		if (chance == 0 && level != 0) {
			if (e.getItem().getType() != Material.BOOK
					&& !incompatible(e.getEnchantsToAdd())
					&& canEnchantItem(e.getItem())) {
				if (level > this.getMaxLevel()) {
					level = this.getMaxLevel();
				}
				Random rand = new Random();
				if (rand.nextInt(2) == 0 && level > 1) {
					level = level-1;
				}
				e.getEnchantsToAdd().put(this, level);
				ItemMeta m = e.getItem().getItemMeta();
				List<String> lore;
				if (m.hasLore()) {
					lore = m.getLore();
				} else {
					lore = new ArrayList<String>();
				}
				lore.add(ChatColor.GRAY + this.getName() + " " + numeral(level));
				m.setLore(lore);
				e.getItem().setItemMeta(m);
			}
		}
	}

	/**
	 * Gets the Roman Numeral from the integer
	 * 
	 * @param i
	 *            The int to change
	 * @return The roman numeral versino of that integer
	 */
	public String numeral(int i) {
		switch (i) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		default:
			return "";
		}
	}

	public boolean pick(ItemStack item) {
		if (item.getType() == Material.STONE_PICKAXE
				|| item.getType() == Material.IRON_PICKAXE
				|| item.getType() == Material.GOLD_PICKAXE
				|| item.getType() == Material.DIAMOND_PICKAXE) {
			return true;
		}
		return false;
	}

	public boolean sword(ItemStack item) {
		if (item.getType() == Material.STONE_SWORD
				|| item.getType() == Material.IRON_SWORD
				|| item.getType() == Material.GOLD_SWORD
				|| item.getType() == Material.DIAMOND_SWORD) {
			return true;
		}
		return false;
	}
	
	public boolean hasEnchantment(ItemStack i) {
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasEnchant(this)) {
			return true;
		}
		return false;
	}
	
	public int getEnchantment(ItemStack i) {
		if (hasEnchantment(i)) {
			return i.getItemMeta().getEnchantLevel(this);
		}
		return 0;
	}

}
