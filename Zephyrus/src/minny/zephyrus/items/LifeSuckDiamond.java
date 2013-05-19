package minny.zephyrus.items;

import java.util.List;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
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

public class LifeSuckSword extends CustomItem {

	public LifeSuckSword(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "�aDiamond Sword of Life";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		setCustomEnchantment(i, plugin.suck, 1);
	}

	public void createUpgradeItem(ItemStack i) {
		setItemName(i, this.name());
		setCustomEnchantment(i, plugin.suck, 2);
	}

	public ItemStack upgradeItem() {
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		createUpgradeItem(i);
		return i;
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

	public Recipe recipeUpgrade() {
		ItemStack i = upgradeItem();

		ShapedRecipe recipe = new ShapedRecipe(i);
		recipe.shape("BBB", "BAB", "BCB");
		recipe.setIngredient('C', Material.DIAMOND);
		recipe.setIngredient('B', Material.GHAST_TEAR);
		recipe.setIngredient('A', Material.DIAMOND_SWORD);
		return recipe;
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (e.getRecipe() == this.recipe()) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.lifesuck")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}
}