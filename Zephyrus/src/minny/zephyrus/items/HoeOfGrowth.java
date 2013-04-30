package minny.zephyrus.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import minny.zephyrus.Zephyrus;

public class HoeOfGrowth extends Item{

	public HoeOfGrowth(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public void createItem(ItemStack i){
		setItemName(i, "Hoe of Growth", "a");
		setItemLevel(i, 1);
		i.addEnchantment(plugin.glow, 1);
	}
	
	@Override
	public Recipe recipe() {
         ItemStack grow_hoe = new ItemStack(Material.GOLD_HOE); 
         createItem(grow_hoe);
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
		createItem(i);
		return i;
	}
}
