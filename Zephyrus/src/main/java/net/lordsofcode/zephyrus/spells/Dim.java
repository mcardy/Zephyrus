package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

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

public class Dim extends Spell {

	@Override
	public String getName() {
		return "dim";
	}

	@Override
	public String getDesc() {
		return "Destroy the sun!! Make it night.";
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
		return 240;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.NETHER_BRICK, 8));
		s.add(new ItemStack(Material.WATCH));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.WORLD;
	}

	@Override
	public Element getElementType() {
		return Element.SHADOW;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean run(Player player, String[] args) {
		player.getWorld().setTime(14000);
		return true;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
		return false;
	}
	
	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("shine");
	}

}
