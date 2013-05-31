package minnymin3.zephyrus.items;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;

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

	PluginHook hook;

	public GemOfLightning(Zephyrus plugin) {
		super(plugin);
		hook = new PluginHook();
	}

	@Override
	public String name() {
		return "¤bGem of Lightning";
	}

	@Override
	public Recipe recipe() {
		ItemStack lightning_gem = item();

		ShapedRecipe recipe = new ShapedRecipe(lightning_gem);
		recipe.shape(" B ", "BAB", " B ");
		recipe.setIngredient('B', Material.FLINT_AND_STEEL);
		recipe.setIngredient('A', Material.EMERALD);
		return recipe;
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.EMERALD);
		setItemName(i, this.name());
		setItemLevel(i, 1);
		setGlow(i);
		return i;
	}

	@Override
	public int maxLevel() {
		return 5;
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		if (!ItemDelay.hasDelay(plugin, e.getPlayer(), this)
				&& e.getPlayer().getItemInHand().getType() == Material.EMERALD
				&& e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")) {
			if (PluginHook.canBuild(e.getPlayer(), e.getPlayer()
					.getTargetBlock(null, 1000))) {
				Location loc = e.getPlayer().getTargetBlock(null, 100)
						.getLocation();
				e.getPlayer().getWorld().strikeLightning(loc);
				int delay = delayFromLevel(getItemLevel(e.getItem()));
				ItemDelay.setDelay(plugin, e.getPlayer(), this, delay);
			} else {
				e.getPlayer().sendMessage(
						ChatColor.DARK_RED
								+ "You don't have permission for this area");
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")
				&& ItemDelay.hasDelay(plugin, e.getPlayer(), this)) {
			int time = ItemDelay.getDelay(plugin, e.getPlayer(), this);
			e.getPlayer().sendMessage(
					ChatColor.GRAY + "The gem still needs " + time
							+ " seconds to recharge!");
		}

	}

	@Override
	public String perm() {
		return "lightninggem";
	}

}
