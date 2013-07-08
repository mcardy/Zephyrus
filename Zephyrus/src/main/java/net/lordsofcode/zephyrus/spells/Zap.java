package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

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

	public Zap(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "zap";
	}

	@Override
	public String bookText() {
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
	public void run(Player player, String[] args) {
		Set<Entity> list = new HashSet<Entity>();
		int r = getConfig().getInt(this.name() + ".radius");
		LivingEntity e = (LivingEntity) getTarget(player);
		e.getWorld().strikeLightning(e.getLocation());
		list.add(e);
		loopThrough(e.getNearbyEntities(r, r, r), player, list);
	}

	public void loopThrough(List<Entity> e, Player player, Set<Entity> list) {
		int r = getConfig().getInt(this.name() + ".radius");
		int l = getConfig().getInt(this.name() + ".limit");
		for (Entity en : e) {
			if (en instanceof LivingEntity && en != player && en.getLocation().distance(player.getLocation()) < l && !list.contains(en)) {
				en.getWorld().strikeLightning(en.getLocation());
				list.add(en);
				loopThrough(en.getNearbyEntities(r, r, r), player, list);
			}
		}
	}
	
	@Override
	public boolean canRun(Player player, String[] args) {
		Entity en = getTarget(player);
		return en != null && en instanceof LivingEntity;
	}
	
	@Override
	public String failMessage() {
		return "You do not have a target!";
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 8);
		map.put("limit", 50);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FLINT_AND_STEEL, 8));
		i.add(new ItemStack(Material.DIAMOND, 2));
		return i;
	}

	@Override
	public SpellType type() {
		return null;
	}
	
	@Override
	public String reqSpell() {
		return Spell.getDisplayName("smite");
	}

}
