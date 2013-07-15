package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Prospect extends Spell {

	public Prospect() {
		Lang.add("spells.prospect.found", "Found ores: ");
		Lang.add("spells.prospect.none", "none...");
	}

	@Override
	public String getName() {
		return "prospect";
	}

	@Override
	public String getDesc() {
		return "Searches for valuable materials nearby";
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
		int radius = getConfig().getInt(getName() + ".radius");
		final Block block = player.getLocation().getBlock();
		Set<String> s = new HashSet<String>();
		for (int x = -(radius); x <= radius; x++) {
			for (int y = -(radius); y <= radius; y++) {
				for (int z = -(radius); z <= radius; z++) {
					if (block.getRelative(x,y,z).getType() == Material.GOLD_ORE) {
						s.add(ChatColor.GOLD + "Gold");
					}
					if (block.getRelative(x,y,z).getType() == Material.IRON_ORE) {
						s.add(ChatColor.GRAY + "Iron");
					}
					if (block.getRelative(x,y,z).getType() == Material.COAL_ORE) {
						s.add(ChatColor.DARK_GRAY + "Coal");
					}
					if (block.getRelative(x,y,z).getType() == Material.REDSTONE_ORE) {
						s.add(ChatColor.RED + "Redstone");
					}
					if (block.getRelative(x,y,z).getType() == Material.LAPIS_ORE) {
						s.add(ChatColor.BLUE + "Lapis");
					}
					if (block.getRelative(x,y,z).getType() == Material.DIAMOND_ORE) {
						s.add(ChatColor.AQUA + "Diamond");
					}
				}
			}
		}
		StringBuilder msg = new StringBuilder();
		msg.append(Lang.get("spells.prospect.found"));
		for (String str : s) {
			msg.append(str + " ");
		}
		if (s.size() == 0) {
			msg.append(Lang.get("spells.prospect.none"));
		}
		player.sendMessage(msg.toString());
		return true;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 3);
		return map;
	}
	
	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GOLD_INGOT));
		s.add(new ItemStack(Material.DIAMOND));
		s.add(new ItemStack(Material.COAL));
		s.add(new ItemStack(Material.IRON_ORE));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.MONITOR;
	}

	@Override
	public Element getElementType() {
		return Element.EARTH;
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
