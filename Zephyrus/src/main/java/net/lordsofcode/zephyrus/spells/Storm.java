package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

	public Storm() {

	}

	@Override
	public String getName() {
		return "storm";
	}

	@Override
	public String getDesc() {
		return "Collect the power of the elements to conjure a storm!";
	}

	@Override
	public int reqLevel() {
		return 9;
	}

	@Override
	public int manaCost() {
		return 300;
	}

	@Override
	public boolean run(Player player, String[] args, int power) {
		player.getWorld().setStorm(true);
		player.getWorld().setThundering(true);
		for (int i = 0; i < 5; i++) {
			Location loc = player.getLocation();
			loc.setX(loc.getX() + new Random().nextInt(20));
			loc.setX(loc.getX() - new Random().nextInt(20));
			loc.setY(loc.getY() + new Random().nextInt(20));
			loc.setY(loc.getY() - new Random().nextInt(20));
			loc.setY(loc.getY() - 20);
			loc.getWorld().strikeLightningEffect(loc);
		}
		Effects.playEffect(Sound.AMBIENCE_THUNDER, player.getLocation());
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.WATER_BUCKET));
		return s;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.getWorld().strikeLightning(player.getLocation());
		return false;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public Type getPrimaryType() {
		return Type.WORLD;
	}

	@Override
	public Element getElementType() {
		return Element.WATER;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}
	
	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("smite");
	}

}
