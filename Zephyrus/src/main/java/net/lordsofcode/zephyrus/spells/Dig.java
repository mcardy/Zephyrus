package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class Dig extends Spell {

	public Dig(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "dig";
	}

	@Override
	public String bookText() {
		return "Digs for you. Max range of 12 blocks";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 1;
	}

	@Override
	public void run(Player player, String[] args) {
		player.getTargetBlock(null, 12).breakNaturally(
				new ItemStack(Material.DIAMOND_PICKAXE));
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 12), player);
		Bukkit.getPluginManager().callEvent(e);
		return !e.isCancelled();
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_PICKAXE));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.EARTH;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.getTargetBlock(null, 12).breakNaturally(null);
		return true;
	}
	
	@Override
	public Set<SpellType> types() {
		Set<SpellType> t = types();
		t.add(SpellType.FIRE);
		return t;
	}
	
	@Override
	public void comboSpell(Player player, String[] args, SpellType type, int level) {
		Block block = player.getTargetBlock(null, 12);
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		if (block.getType() == Material.STONE) {
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(loc, new ItemStack(Material.STONE, 1));
		} else if (block.getType() == Material.SAND) {
			block.getWorld().dropItemNaturally(loc, new ItemStack(Material.GLASS, 1));
		} else if (block.getType() == Material.CLAY) {
			block.getWorld().dropItemNaturally(loc, new ItemStack(Material.BRICK));
		} else {
			block.breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		}
	}

}
