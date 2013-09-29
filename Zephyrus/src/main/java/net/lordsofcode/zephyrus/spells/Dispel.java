package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.Material;
import org.bukkit.Sound;
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

	@Override
	public String getName() {
		return "dispel";
	}

	@Override
	public String getDesc() {
		return "This spell will clear all effects from the caster";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public boolean run(Player player, String[] args) {
		for (PotionEffect pe : player.getActivePotionEffects()) {
			player.removePotionEffect(pe.getType());
		}
		Effects.playEffect(Sound.WITHER_SHOOT, player.getLocation(), 1, 8);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.MILK_BUCKET));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.RESTORE;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
