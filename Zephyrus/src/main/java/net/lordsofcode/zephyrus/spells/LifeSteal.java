package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LifeSteal extends Spell {

	public LifeSteal() {
		Lang.add("spells.lifesteal.fail", "No one to suck the life out of...");
	}

	@Override
	public String getName() {
		return "lifesteal";
	}

	@Override
	public String getDesc() {
		return "This spell will damage your enemy and give you their health";
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
	public boolean run(Player player, String[] args) {
		Entity e = getTarget(player);
		if (e == null || !(e instanceof Creature)) {
			Lang.errMsg("spells.lifesteal.fail", player);
			return false;
		}
		Creature en = (Creature) getTarget(player);
		en.damage(2);
		try {
			player.setHealth(player.getMaxHealth() + 2);
		} catch (Exception ex) {
			player.setHealth(player.getMaxHealth());
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GHAST_TEAR, 2));
		return s;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		if (rand.nextInt(2) == 0) {
			Creature en = (Creature) getTarget(player);
			try {
				en.setHealth(en.getHealth() + 2);
			} catch (Exception e) {
				en.setHealth(en.getMaxHealth());
			}
			player.damage(2, en);
			return true;
		}
		return false;

	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

}
