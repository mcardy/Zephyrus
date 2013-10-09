package net.lordsofcode.zephyrus.items;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.registry.PlantRegistry;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

	@Override
	public String getName() {
		return ChatColor.getByChar("a") + "Hoe of Growth";
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public Recipe getRecipe() {
		ItemStack grow_hoe = getItem();
		ShapedRecipe recipe = new ShapedRecipe(grow_hoe);
		recipe.shape("CBC", "BAB", "CBC");
		recipe.setIngredient('C', Material.SAPLING);
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('A', Material.GOLD_HOE);
		return recipe;
	}

	@Override
	public ItemStack getItem() {
		ItemStack i = new ItemStack(Material.GOLD_HOE);
		setItemName(i, getDisplayName());
		setItemLevel(i, 1);
		i.addEnchantment(Zephyrus.getInstance().glow, 1);
		return i;
	}

	@EventHandler
	public void grow(PlayerInteractEvent e) throws Exception {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && checkName(e.getPlayer().getItemInHand(), getDisplayName())) {
			Block target = e.getClickedBlock();
			if (PlantRegistry.grow(target)) {
				Location loc = target.getLocation();
				loc.setX(loc.getX() + 0.6);
				loc.setZ(loc.getZ() + 0.6);
				loc.setY(loc.getY() + 0.3);
				Effects.playEffect(ParticleEffects.GREEN_SPARKLE, loc, (float) 0.25, (float) 0.1, (float) 0.25, 100, 20);
				e.getItem().setDurability((short) (e.getItem().getDurability() + 1));
			}
		}
	}

	@Override
	public String getPerm() {
		return "growhoe";
	}
}
