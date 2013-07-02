package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

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

	public Detect(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "detect";
	}

	@Override
	public String bookText() {
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
	public void run(Player player, String[] args) {
		int r = getConfig().getInt(name() + ".radius");
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
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("radius", 20);
		return cfg;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.TRIPWIRE_HOOK));
		s.add(new ItemStack(Material.STONE_PLATE));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.OTHER;
	}

}
