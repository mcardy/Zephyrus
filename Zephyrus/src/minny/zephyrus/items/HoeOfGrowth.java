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

	public void craftItem(ItemStack i){
		setItemName(i, "Hoe of Growth", "a");
		setItemLevel(i, 1);
		i.addEnchantment(plugin.glow, 1);
	}
	
	public Recipe recipe() {
         ItemStack grow_hoe = new ItemStack(Material.GOLD_HOE); 
         craftItem(grow_hoe);
         ShapedRecipe recipe = new ShapedRecipe(grow_hoe);
         recipe.shape("CBC", "BAB", "CBC");
         recipe.setIngredient('C', Material.SAPLING);
         recipe.setIngredient('B', Material.BONE);
         recipe.setIngredient('A', Material.GOLD_HOE);
         return recipe;
     }
}
