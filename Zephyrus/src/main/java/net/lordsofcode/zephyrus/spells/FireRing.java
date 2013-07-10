package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.hooks.PluginHook;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FireRing extends Spell {

	public FireRing(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "firering";
	}

	@Override
	public String bookText() {
		return "Creates a ring of fire around you";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public void run(Player player, String[] args) {
		int radius = getConfig().getInt(this.name() + ".radius");
		final Block block = player.getLocation().getBlock();
		for (int x = -(radius); x <= radius; x++) {
			for (int y = -(radius); y <= radius; y++) {
				for (int z = -(radius); z <= radius; z++) {
					Block b = block.getRelative(x, y, z);
					if (PluginHook.canBuild(player, b)
							&& b.getRelative(BlockFace.DOWN).getType() != Material.AIR
							&& b.getType() == Material.AIR
							&& b.getLocation().distance(block.getLocation()) > 2
							&& b.getLocation().distance(block.getLocation()) < radius) {
						b.setType(Material.FIRE);
					}
				}
			}
		}
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 5);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.FLINT_AND_STEEL));
		s.add(new ItemStack(Material.FIREBALL));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.FIRE;
	}

}
