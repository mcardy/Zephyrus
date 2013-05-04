package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class GemOfLightning extends Item {

	public GemOfLightning(Zephyrus plugin) {
		super(plugin);
		plugin.getServer().addRecipe(recipe());
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public String name() {
		return "¤bGem of Lightning";
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, "Gem of Lightning", "b");
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
			Location loc = e.getPlayer().getTargetBlock(null, 100)
					.getLocation();
			e.getPlayer().getWorld().strikeLightning(loc);
			delay(plugin.lightningGemDelay, plugin, delayFromLevel(getItemLevel(e
					.getPlayer().getItemInHand())), e.getPlayer().getName());
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(),
						"¤bGem of Lightning")
				&& plugin.lightningGemDelay.containsKey(e.getPlayer().getName())) {
			int time = (Integer) plugin.lightningGemDelay.get(e.getPlayer()
					.getName());
			e.getPlayer().sendMessage(
					ChatColor.GRAY + "The gem still needs " + time
							+ " seconds to recharge!");
		}

	}

}
