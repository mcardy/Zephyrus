package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class RodOfFire extends Item{
	
	public RodOfFire(Zephyrus plugin) {
		super(plugin);
	}

	public void craftItem(ItemStack i){
		setItemName(i, "Rod of Fire", "c");
		setItemLevel(i, 1);
		setGlow(i);
	}
	
	 public Recipe recipe() {
         ItemStack fire_rod = new ItemStack(Material.BLAZE_ROD); 
         craftItem(fire_rod);
        
         ShapedRecipe recipe = new ShapedRecipe(fire_rod);
         recipe.shape("BCB", "BAB", "BBB");
         recipe.setIngredient('C', Material.DIAMOND);
         recipe.setIngredient('B', Material.BLAZE_POWDER);
         recipe.setIngredient('A', Material.BLAZE_ROD);
         return recipe;
     }
}
