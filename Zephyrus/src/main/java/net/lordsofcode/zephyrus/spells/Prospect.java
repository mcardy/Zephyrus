package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
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

	public Prospect(Zephyrus plugin) {
		super(plugin);
		Lang.add("spells.prospect.found", "Found ores: ");
		Lang.add("spells.prospect.none", "none...");
	}

	@Override
	public String name() {
		return "prospect";
	}

	@Override
	public String bookText() {
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
	public void run(Player player, String[] args) {
		int radius = getConfig().getInt(this.name() + ".radius");
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
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 3);
		return map;
	}
	
	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GOLD_INGOT));
		s.add(new ItemStack(Material.DIAMOND));
		s.add(new ItemStack(Material.COAL));
		s.add(new ItemStack(Material.IRON_ORE));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.EARTH;
	}

}
