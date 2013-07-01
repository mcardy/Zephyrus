package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Arrow extends Spell {

	public Arrow(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "arrow";
	}

	@Override
	public String bookText() {
		return "Shoots an arrow. Simple.";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 2;
	}

	@Override
	public void run(Player player, String[] args) {
		player.launchProjectile(org.bukkit.entity.Arrow.class);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.ARROW, 64));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}

}
