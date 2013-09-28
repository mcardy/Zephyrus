package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
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
 */

public class Zap extends Spell {

	public Zap() {
		Lang.add("spells.zap.fail", "You don't have a valid target!");
	}

	@Override
	public String getName() {
		return "zap";
	}

	@Override
	public String getDesc() {
		return "Brings down a bolt of lightning upon your enemy that will jump to other nearby enemies";
	}

	@Override
	public int reqLevel() {
		return 12;
	}

	@Override
	public int manaCost() {
		return 200;
	}

	@Override
	public boolean run(Player player, String[] args) {
		Entity e = getTarget(player);
		if (e instanceof LivingEntity) {
			Set<Entity> list = new HashSet<Entity>();
			int r = getConfig().getInt(getName() + ".radius");
			LivingEntity en = (LivingEntity) e;
			en.getWorld().strikeLightning(e.getLocation());
			list.add(e);
			loopThrough(e.getNearbyEntities(r, r, r), player, list);
			return true;
		}
		Lang.errMsg("spells.zap.fail", player);
		return false;
	}

	public void loopThrough(List<Entity> e, Player player, Set<Entity> list) {
		int r = getConfig().getInt(getName() + ".radius");
		int l = getConfig().getInt(getName() + ".limit");
		for (Entity en : e) {
			if (en instanceof LivingEntity && en != player && en.getLocation().distance(player.getLocation()) < l
					&& !list.contains(en)) {
				en.getWorld().strikeLightning(en.getLocation());
				list.add(en);
				loopThrough(en.getNearbyEntities(r, r, r), player, list);
			}
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 8);
		map.put("limit", 50);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FLINT_AND_STEEL));
		i.add(new ItemStack(Material.DIAMOND, 2));
		i.add(new ItemStack(Material.EMERALD, 4));
		return i;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("smite");
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.LIGHT;
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
