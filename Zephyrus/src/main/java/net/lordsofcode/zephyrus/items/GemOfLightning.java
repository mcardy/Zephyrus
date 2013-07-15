package net.lordsofcode.zephyrus.items;

import net.lordsofcode.zephyrus.PluginHook;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class GemOfLightning extends CustomItem {

	public GemOfLightning() {
		Lang.add("lightninggem.recharge", "The Gem of Lightning still needs [TIME] to recharge...");
	}

	@Override
	public String getName() {
		return ChatColor.getByChar("b") + "Gem of Lightning";
	}

	@Override
	public Recipe getRecipe() {
		ItemStack lightning_gem = getItem();

		ShapedRecipe recipe = new ShapedRecipe(lightning_gem);
		recipe.shape(" B ", "BAB", " B ");
		recipe.setIngredient('B', Material.FLINT_AND_STEEL);
		recipe.setIngredient('A', Material.EMERALD);
		return recipe;
	}

	@Override
	public ItemStack getItem() {
		ItemStack i = new ItemStack(Material.EMERALD);
		setItemName(i, getDisplayName());
		setItemLevel(i, 1);
		setGlow(i);
		return i;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		if (!ItemDelay.hasDelay(e.getPlayer(), this)
				&& e.getPlayer().getItemInHand().getType() == Material.EMERALD
				&& e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						getDisplayName())) {
			if (PluginHook.canBuild(e.getPlayer(), e.getPlayer()
					.getTargetBlock(null, 1000))) {
				Location loc = e.getPlayer().getTargetBlock(null, 100)
						.getLocation();
				e.getPlayer().getWorld().strikeLightning(loc);
				int delay = delayFromLevel(getItemLevel(e.getItem()));
				ItemDelay.setDelay(e.getPlayer(), this, delay);
			} else {
				Lang.errMsg("worldguard", e.getPlayer());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						getDisplayName())
				&& ItemDelay.hasDelay(e.getPlayer(), this)) {
			int time = ItemDelay.getDelay(e.getPlayer(), this);
			e.getPlayer().sendMessage(
					ChatColor.GRAY + Lang.get("lightninggem.recharge").replace("[TIME]", time + ""));
		}

	}

	@Override
	public String getPerm() {
		return "lightninggem";
	}

}
