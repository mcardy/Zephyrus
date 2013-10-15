package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Grenade extends Spell {

	@Override
	public String getName() {
		return "grenade";
	}

	@Override
	public String getDesc() {
		return "Shoots a big explody thing :D";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 210;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (blockBreak(player)) {
			return false;
		}
		player.launchProjectile(WitherSkull.class);
		Effects.playEffect(Sound.WITHER_SHOOT, player.getLocation());
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.getMaterial(397), 3, (short) 1));
		return s;
	}

	@Override
	public Type getPrimaryType() {
		return Type.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.WITHER;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
	
	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("explode");
	}

}
