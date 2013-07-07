package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Location;
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

public class Storm extends Spell {

	public Storm(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "storm";
	}

	@Override
	public String bookText() {
		return "Collect the power of the elements to conjure a storm!";
	}

	@Override
	public int reqLevel() {
		return 9;
	}

	@Override
	public int manaCost() {
		return 120;
	}

	@Override
	public void run(Player player, String[] args) {
		player.getWorld().setStorm(true);
		player.getWorld().setThundering(true);
		Location loc = player.getLocation();
		loc.setX(loc.getX() + new Random().nextInt(20));
		loc.setX(loc.getX() - new Random().nextInt(20));
		loc.setY(loc.getY() + new Random().nextInt(20));
		loc.setY(loc.getY() - new Random().nextInt(20));
		loc.setY(loc.getY() - 20);
		loc.getWorld().strikeLightningEffect(loc);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.WATER_BUCKET, 3));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.ELEMENTAL;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.getWorld().strikeLightning(player.getLocation());
		return false;
	}

}
