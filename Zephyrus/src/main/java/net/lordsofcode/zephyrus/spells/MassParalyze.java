package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

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

	public MassParalyze(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "massparalyze";
	}

	@Override
	public String bookText() {
		return "Paralyses everyone in the area";
	}

	@Override
	public int reqLevel() {
		return 11;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 10);
		cfg.put("radius", 5);
		return cfg;
	}
	
	@Override
	public void run(Player player, String[] args) {
		int time = getConfig().getInt(name() + ".duration");
		int radius = getConfig().getInt(name() + ".radius");
		for (Entity en : player.getNearbyEntities(radius, radius, radius)) {
			if (en instanceof LivingEntity) {
				LivingEntity ln = (LivingEntity) en;
				ln.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 100));
			}
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		//Slowness extended potions
		s.add(new ItemStack(Material.POTION, 1, (short) 8266));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.OTHER;
	}
	
	@Override
	public String reqSpell() {
		return Spell.getDisplayName("paralyze");
	}

}
