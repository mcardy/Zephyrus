package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class Paralyze extends Spell {

	public Paralyze() {
		Lang.add("spells.paralyze.fail", "You don't have a target!");
	}

	@Override
	public String getName() {
		return "paralyze";
	}

	@Override
	public String getDesc() {
		return "Stop your enemy dead in their tracks!";
	}

	@Override
	public int reqLevel() {
		return 6;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public boolean run(Player player, String[] args) {
		Entity e = getTarget(player);
		if (e == null && !(e instanceof LivingEntity)) {
			Lang.errMsg("spells.paralyze.fail", player);
			return false;
		}
		LivingEntity en = (LivingEntity) e;
		int time = getConfig().getInt(getName() + ".duration");
		en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 100));
		return true;
	}
	
	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 10);
		return cfg;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		//Slowness extended potions
		s.add(new ItemStack(Material.POTION, 1, (short) 8202));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.MOVEMENT;
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
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100));
		return false;
	}

}
