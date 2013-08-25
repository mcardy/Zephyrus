package net.lordsofcode.zephyrus.addon.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 *          These spells show how classes can be loaded form jar files in the
 *          Spells folder by Zephyrus
 * 
 */

public class Spell2 extends Spell {

	public Spell2() {
		Lang.add("example2.error", "No target in sight!");
	}
	
	@Override
	public String getName() {
		return "example2";
	}

	@Override
	public String getDesc() {
		return "An example spell. It is example #2";
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.LAVA));
		return i;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.FIRE;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public boolean run(Player player, String[] args) {
		Entity en = getTarget(player);
		if (en != null && en instanceof LivingEntity) {
			((LivingEntity)en).setFireTicks(100);
			return true;
		} else {
			Lang.errMsg("example2.error", player);
			return false;
		}
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
