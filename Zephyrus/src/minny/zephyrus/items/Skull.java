package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class Skull extends Item{
	
	public Skull(Zephyrus plugin) {
		super(plugin);
	}

	public void craftItem(ItemStack i){
		setItemName(i, "Skull", "8");
		setItemLevel(i, 1);
	}
	
	 public Recipe recipe() {
         ItemStack skull = new ItemStack(Material.SKULL_ITEM); 
         craftItem(skull);
        
         ShapedRecipe recipe = new ShapedRecipe(skull);
         recipe.shape("AAA", "AAA", "AAA");
         recipe.setIngredient('A', Material.SKULL_ITEM);
         return recipe;
     }
}
