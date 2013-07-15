package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
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

public class Detect extends Spell {

	@Override
	public String getName() {
		return "detect";
	}

	@Override
	public String getDesc() {
		return "Look around for enemies nearby...";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int r = getConfig().getInt(getName() + ".radius");
		List<Entity> enList = player.getNearbyEntities(r, r, r);
		player.sendMessage(ChatColor.GRAY + "Nearby mobs:");
		boolean msg = true;
		Map<String, Integer> mobMap = new HashMap<String, Integer>();
		for (Entity en : enList) {
			if (en instanceof LivingEntity) {
				msg = false;
				if (mobMap.containsKey(en.getType().toString())) {
					int i = mobMap.get(en.getType().toString());
					mobMap.put(en.getType().toString(), i+1);
				} else {
					mobMap.put(en.getType().toString(), 1);
				}
			}
		}
		for (String s : mobMap.keySet()) {
			player.sendMessage(ChatColor.GREEN + WordUtils.capitalize(s.toLowerCase().replace(
					"_", " ")) + ": " + mobMap.get(s));
		}
		if (msg) {
			player.sendMessage(ChatColor.GRAY + "None...");
		}
		return true;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("radius", 20);
		return cfg;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.TRIPWIRE_HOOK));
		s.add(new ItemStack(Material.STONE_PLATE));
		return s;
	}
	
	@Override
	public EffectType getPrimaryType() {
		return EffectType.MONITOR;
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
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
