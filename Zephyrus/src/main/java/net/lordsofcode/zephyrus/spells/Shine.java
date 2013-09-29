package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

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

public class Shine extends Spell {

	@Override
	public String getName() {
		return "shine";
	}

	@Override
	public String getDesc() {
		return "Let the sun shine";
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 6;
	}

	@Override
	public int manaCost() {
		return 80;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GLOWSTONE, 8));
		s.add(new ItemStack(Material.WATCH));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.WORLD;
	}

	@Override
	public Element getElementType() {
		return Element.LIGHT;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean run(Player player, String[] args) {
		player.getWorld().setTime(1000);
		return true;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.setFireTicks(20);
		return false;
	}

}
