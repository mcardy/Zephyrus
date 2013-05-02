package minny.zephyrus.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import minny.zephyrus.Zephyrus;

public class LifeSuckSword extends Item{

	public LifeSuckSword(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "Diamond Sword of Life";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, "Diamond Sword of Life", "a");
		i.addEnchantment(plugin.suck, 1);
	}

	@Override
	public Recipe recipe() {
		ItemStack i = item();

		ShapedRecipe recipe = new ShapedRecipe(i);
		recipe.shape("BCB", "BCB", "BAB");
		recipe.setIngredient('C', Material.DIAMOND);
		recipe.setIngredient('B', Material.GHAST_TEAR);
		recipe.setIngredient('A', Material.STICK);
		return recipe;
	}

	
	
}
