package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

public class MassParalyze extends Spell {

	@Override
	public String getName() {
		return "massparalyze";
	}

	@Override
	public String getDesc() {
		return "Paralyses everyone in the area";
	}

	@Override
	public int reqLevel() {
		return 11;
	}

	@Override
	public int manaCost() {
		return 300;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 10);
		cfg.put("radius", 5);
		return cfg;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int time = getConfig().getInt(getName() + ".duration");
		int radius = getConfig().getInt(getName() + ".radius");
		for (Entity en : player.getNearbyEntities(radius, radius, radius)) {
			if (en instanceof LivingEntity) {
				LivingEntity ln = (LivingEntity) en;
				ln.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 100));
			}
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		// Slowness extended potions
		s.add(new ItemStack(Material.POTION, 1, (short) 8266));
		return s;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("paralyze");
	}

	@Override
	public Type getPrimaryType() {
		return Type.MOVEMENT;
	}

	@Override
	public Element getElementType() {
		return Element.POTION;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
