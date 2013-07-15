package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
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

public class Grow extends Spell {

	public Grow() {
		Lang.add("spells.grow.fail", "That block can't be grown...");
	}
	
	@Override
	public String getName() {
		return "grow";
	}

	@Override
	public String getDesc() {
		return "Grows wheat and Saplings";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (player.getTargetBlock(null, 4).getTypeId() != 59
				&& player.getTargetBlock(null, 4).getType() != Material.SAPLING) {
			Lang.errMsg("spells.grow.fail", player);
			return false;
		}
		// TODO Add support for mushrooms and melon seeds and carrots and potatoes
		if (player.getTargetBlock(null, 4).getTypeId() == 59) {
			player.getTargetBlock(null, 4).setData((byte) 7);
			Location loc = player.getTargetBlock(null, 4).getLocation();
			loc.setX(loc.getX() + 0.6);
			loc.setZ(loc.getZ() + 0.6);
			loc.setY(loc.getY() + 0.3);
			try {
				ParticleEffects.sendToLocation(ParticleEffects.GREEN_SPARKLE,
						loc, (float) 0.25, (float) 0.1, (float) 0.25, 100, 20);
			} catch (Exception e) {
			}
		}
		if (player.getTargetBlock(null, 4).getType() == Material.SAPLING) {
			Block b = player.getTargetBlock(null, 4);
			TreeType tt = getTree(b.getData());
			World world = player.getWorld();
			b.setTypeId(0);
			world.generateTree(b.getLocation(), tt);
			Location loc = player.getTargetBlock(null, 4).getLocation();
			loc.setX(loc.getX() + 0.6);
			loc.setZ(loc.getZ() + 0.6);
			loc.setY(loc.getY() + 0.3);
			try {
				ParticleEffects.sendToLocation(ParticleEffects.GREEN_SPARKLE,
						loc, 1, 1, 1, 100, 20);
			} catch (Exception e) {
			}
		}
		return true;
	}

	public static TreeType getTree(int data) {
		switch (data) {
		case 0:
			return TreeType.TREE;
		case 1:
			return TreeType.REDWOOD;
		case 2:
			return TreeType.BIRCH;
		case 3:
			return TreeType.SMALL_JUNGLE;
		}
		return TreeType.TREE;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.SAPLING));
		items.add(new ItemStack(Material.BONE));
		items.add(new ItemStack(Material.SEEDS));
		return items;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.CREATION;
	}

	@Override
	public Element getElementType() {
		return Element.EARTH;
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
	
	@Override
	public Map<String, Object> getConfiguration() {
		// TODO Add config for growable blocks
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
	
}
