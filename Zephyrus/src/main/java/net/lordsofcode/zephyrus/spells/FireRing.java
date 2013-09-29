package net.lordsofcode.zephyrus.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FireRing extends Spell {

	@Override
	public String getName() {
		return "firering";
	}

	@Override
	public String getDesc() {
		return "Creates a ring of fire around you";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 150;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int radius = getConfig().getInt(getName() + ".radius");
		final Block block = player.getLocation().getBlock();
		List<Block> bList = new ArrayList<Block>();
		for (int x = -(radius); x <= radius; x++) {
			for (int y = -(radius); y <= radius; y++) {
				for (int z = -(radius); z <= radius; z++) {
					Block b = block.getRelative(x, y, z);
					BlockBreakEvent e = new BlockBreakEvent(b, player);
					Bukkit.getPluginManager().callEvent(e);
					if (e.isCancelled()) {
						return false;
					}
					if (b.getRelative(BlockFace.DOWN).getType() != Material.AIR && b.getType() == Material.AIR
							&& b.getLocation().distance(block.getLocation()) > 2
							&& b.getLocation().distance(block.getLocation()) < radius) {
						bList.add(b);
					}
				}
			}
		}
		for (Block b : bList) {
			b.setType(Material.FIRE);
		}
		return true;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 5);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.FLINT_AND_STEEL));
		s.add(new ItemStack(Material.FIREBALL));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.FIRE;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("flare");
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.getLocation().getBlock().setType(Material.FIRE);
		return false;
	}

}
