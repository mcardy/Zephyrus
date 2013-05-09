package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class GemOfLightning extends CustomItem {

	PluginHook hook;

	public GemOfLightning(Zephyrus plugin) {
		super(plugin);
		this.hook = new PluginHook();
	}

	@Override
	public String name() {
		return "¤bGem of Lightning";
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		setItemLevel(i, 1);
		setGlow(i);
	}

	@Override
	public Recipe recipe() {
		ItemStack lightning_gem = new ItemStack(Material.EMERALD);
		createItem(lightning_gem);

		ShapedRecipe recipe = new ShapedRecipe(lightning_gem);
		recipe.shape(" B ", "BAB", " B ");
		recipe.setIngredient('B', Material.FLINT_AND_STEEL);
		recipe.setIngredient('A', Material.EMERALD);
		return recipe;
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.EMERALD);
		createItem(i);
		return i;
	}

	@Override
	public int maxLevel() {
		return 5;
	}

	@EventHandler
	public void lightning(PlayerInteractEvent e) {
		if (!plugin.lightningGemDelay.containsKey(e.getPlayer().getName())
				&& e.getPlayer().getItemInHand().getType() == Material.EMERALD
				&& e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")) {
			hook.wgHook();
			if (hook.wg.canBuild(e.getPlayer(),
					e.getPlayer().getTargetBlock(null, 1000))) {
				Location loc = e.getPlayer().getTargetBlock(null, 100)
						.getLocation();
				e.getPlayer().getWorld().strikeLightning(loc);
				delay(plugin.lightningGemDelay, plugin,
						delayFromLevel(getItemLevel(e.getPlayer()
								.getItemInHand())), e.getPlayer().getName());
			} else {
				e.getPlayer().sendMessage(ChatColor.DARK_RED + "You don't have permission for this area");
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")
				&& plugin.lightningGemDelay
						.containsKey(e.getPlayer().getName())) {
			int time = (Integer) plugin.lightningGemDelay.get(e.getPlayer()
					.getName());
			e.getPlayer().sendMessage(
					ChatColor.GRAY + "The gem still needs " + time
							+ " seconds to recharge!");
		}

	}

}
