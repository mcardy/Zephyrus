package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Grow extends Spell {

	public Grow(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "grow";
	}

	@Override
	public String bookText() {
		return "Grows wheat and Saplings";
	}
	
	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 25;
	}

	@Override
	public void run(Player player) {
		if (player.getTargetBlock(null, 4).getTypeId() == 59) {
			player.getTargetBlock(null, 4).setData((byte) 7);
			Location loc = player.getTargetBlock(null, 4).getLocation();
			loc.setX(loc.getX() + 0.6);
			loc.setZ(loc.getZ() + 0.6);
			loc.setY(loc.getY() + 0.3);
			try {
				ParticleEffects.sendToLocation(ParticleEffects.HAPPY_VILLAGER, loc,
						1, 0, 1, 100, 20);
			} catch (Exception e) {
			}
		}
		if (player.getTargetBlock(null, 4).getType() == Material.SAPLING){
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
				ParticleEffects.sendToLocation(ParticleEffects.HAPPY_VILLAGER,
						loc, 1, 1, 1, 100, 20);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean canRun(Player player) {
		if (player.getTargetBlock(null, 4).getTypeId() == 59
				&& player.getTargetBlock(null, 3).getData() != 7) {
			return true;
		}
		if (player.getTargetBlock(null, 4).getType() == Material.SAPLING){
			return true;
		}
		return false;
	}
	
	@Override
	public String failMessage(){
		return ChatColor.GRAY + "That block cannot be grown";
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
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.SAPLING));
		items.add(new ItemStack(Material.BONE));
		items.add(new ItemStack(Material.SEEDS));
		return items;
	}
}
