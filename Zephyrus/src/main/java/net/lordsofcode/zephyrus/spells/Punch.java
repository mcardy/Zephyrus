package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Punch extends Spell {

	public Punch() {
		Lang.add("spells.punch.fail", "No one to punch!");
	}

	@Override
	public String getName() {
		return "punch";
	}

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public boolean run(Player player, String[] args) {
		Entity e = getTarget(player);
		int damage = getConfig().getInt(getName() + ".damage");
		if (e == null) {
			Lang.errMsg("spells.punch.fail", player);
			return false;
		}
		EntityDamageEvent event = new EntityDamageEvent(e, DamageCause.ENTITY_ATTACK, (double) damage);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			LivingEntity c = (LivingEntity) getTarget(player);
			c.damage(damage);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("damage", 1);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		// Potion of instant damage 1
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.IRON_SWORD));
		i.add(new ItemStack(Material.POTION, 1, (short) 8204));
		return i;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.damage(1, player);
		return false;
	}

	@Override
	public Type getPrimaryType() {
		return Type.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return null;
	}

}
