package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

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

public class Butcher extends Spell {

	@Override
	public String getName() {
		return "butcher";
	}

	@Override
	public String getDesc() {
		return "Brutally murders all creatures within a radius";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 40;
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int r = getConfig().getInt(getName() + ".radius");
		List<Entity> e = player.getNearbyEntities(r, r, r);
		for (Entity en : e) {
			if (en instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) en;
				ParticleEffects.sendToLocation(ParticleEffects.CRITICAL_HIT,
						entity.getLocation(), 0.25F, 0.25F, 0.25F, 5, 15);
				if (entity instanceof Player) {
					entity.damage(10, player);
				} else {
					entity.damage(50);
				}
			}
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_SWORD));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 5);
		return map;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		player.damage(rand.nextInt(4));
		return false;
	}
	
	@Override
	public EffectType getPrimaryType() {
		return EffectType.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}
	
	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

}
