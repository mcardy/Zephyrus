package minny.zephyrus.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import minny.zephyrus.Zephyrus;

public class GemOfLightning extends Item {

	public GemOfLightning(Zephyrus plugin) {
		super(plugin);
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

}
