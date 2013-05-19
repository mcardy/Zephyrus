package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class Vanish extends Spell {

	public Vanish(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "vanish";
	}

	@Override
	public String bookText() {
		return "Makes you dissappear!";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player) {
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
				600, 1));
	}

	@Override
	public Set<ItemStack> spellItems() {
		// Potion extended Invisibility
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.POTION, 1, (short) 8270));
		return i;
	}

}
