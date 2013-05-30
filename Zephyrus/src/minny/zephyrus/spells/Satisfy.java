package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import minny.zephyrus.Zephyrus;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Satisfy extends Spell {

	public Satisfy(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "satisfy";
	}

	@Override
	public String bookText() {
		return "You will be completely satisfied!";
	}

	@Override
	public int reqLevel() {
		return 3;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setSaturation(20);
		player.sendMessage("You feel completely satisfied!");
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.COOKED_BEEF, 8));
		s.add(new ItemStack(Material.COOKED_CHICKEN, 8));
		s.add(new ItemStack(Material.GOLDEN_APPLE));
		s.add(new ItemStack(Material.GOLDEN_CARROT));
		return s;
	}

}
