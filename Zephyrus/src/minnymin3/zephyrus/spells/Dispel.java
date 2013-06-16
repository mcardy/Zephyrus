package minnymin3.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Dispel extends Spell {

	public Dispel(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "dispel";
	}

	@Override
	public String bookText() {
		return "This spell will clear all effects from the caster";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public void run(Player player, String[] args) {
		for (PotionEffect pe : player.getActivePotionEffects()) {
			player.removePotionEffect(pe.getType());
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.MILK_BUCKET, 3));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.RESTORE;
	}

}
