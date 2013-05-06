package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.RecipeUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaPotion extends Item {

	LevelManager lvl;
	RecipeUtil util;
	
	public ManaPotion(Zephyrus plugin) {
		super(plugin);
		plugin.getServer().addRecipe(recipe());
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		lvl = new LevelManager(plugin);
		util = new RecipeUtil();
	}

	@Override
	public String name() {
		return "¤bMana Potion";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.POTION);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("¤7Restors Mana");
		m.setLore(lore);
		i.setItemMeta(m);
	}

	@Override
	public Recipe recipe() {
		ItemStack manaPotion = new ItemStack(Material.POTION);
		createItem(manaPotion);
		ShapedRecipe recipe = new ShapedRecipe(manaPotion);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('B', Material.POTION);
		recipe.setIngredient('A', Material.GLOWSTONE_DUST);
		return recipe;
	}
}
