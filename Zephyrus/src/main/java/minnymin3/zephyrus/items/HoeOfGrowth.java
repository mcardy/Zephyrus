package minnymin3.zephyrus.items;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class HoeOfGrowth extends CustomItem {

	public HoeOfGrowth(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return ChatColor.getByChar("a") + "Hoe of Growth";
	}

	@Override
	public int maxLevel() {
		return 2;
	}

	@Override
	public Recipe recipe() {
		ItemStack grow_hoe = item();
		ShapedRecipe recipe = new ShapedRecipe(grow_hoe);
		recipe.shape("CBC", "BAB", "CBC");
		recipe.setIngredient('C', Material.SAPLING);
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('A', Material.GOLD_HOE);
		return recipe;
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.GOLD_HOE);
		setItemName(i, this.name());
		setItemLevel(i, 1);
		i.addEnchantment(plugin.glow, 1);
		return i;
	}

	@EventHandler
	public void grow(PlayerInteractEvent e) throws Exception {
		if (e.getClickedBlock() != null
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getClickedBlock().getTypeId() == 59
				&& checkName(e.getPlayer().getItemInHand(), name())
				&& e.getClickedBlock().getData() != 7) {
			e.getClickedBlock().setData((byte) 7);
			Location loc = e.getClickedBlock().getLocation();
			loc.setX(loc.getX() + 0.6);
			loc.setZ(loc.getZ() + 0.6);
			loc.setY(loc.getY() + 0.3);
			ParticleEffects.sendToLocation(ParticleEffects.HAPPY_VILLAGER, loc,
					1, 0, 1, 100, 20);
		}
		if (e.getClickedBlock() != null
				&& e.getClickedBlock().getType() == Material.SAPLING
				&& checkName(e.getPlayer().getItemInHand(), name())
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (getItemLevel(e.getPlayer().getItemInHand()) == 1) {
				Block b = e.getClickedBlock();
				TreeType tt = getTree(b.getData());
				World world = e.getPlayer().getWorld();
				b.setTypeId(0);
				world.generateTree(b.getLocation(), tt);
				Location loc = e.getClickedBlock().getLocation();
				loc.setX(loc.getX() + 0.6);
				loc.setZ(loc.getZ() + 0.6);
				loc.setY(loc.getY() + 0.3);
				ParticleEffects.sendToLocation(ParticleEffects.HAPPY_VILLAGER,
						loc, 1, 1, 1, 100, 20);
			} else {
				Block b = e.getClickedBlock();
				TreeType tt = getGiantTree(b.getData());
				World world = e.getPlayer().getWorld();
				b.setTypeId(0);
				world.generateTree(b.getLocation(), tt);
				Location loc = e.getClickedBlock().getLocation();
				loc.setX(loc.getX() + 0.6);
				loc.setZ(loc.getZ() + 0.6);
				loc.setY(loc.getY() + 0.3);
				ParticleEffects.sendToLocation(ParticleEffects.HAPPY_VILLAGER,
						loc, 1, 1, 1, 100, 20);
			}
		}
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

	public static TreeType getGiantTree(int data) {
		switch (data) {
		case 0:
			return TreeType.BIG_TREE;
		case 1:
			return TreeType.TALL_REDWOOD;
		case 2:
			return TreeType.BIRCH;
		case 3:
			return TreeType.JUNGLE;
		}
		return TreeType.BIG_TREE;
	}

	public static TreeType getTaintedTree(int data) {
		switch (data) {
		case 0:
			return TreeType.SWAMP;
		case 1:
			return TreeType.RED_MUSHROOM;
		case 2:
			return TreeType.BIRCH;
		case 3:
			return TreeType.SMALL_JUNGLE;
		}
		return TreeType.TREE;
	}

	@Override
	public String perm() {
		return "growhoe";
	}
}
