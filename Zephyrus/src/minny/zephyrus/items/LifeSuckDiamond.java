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

public class LifeSuckDiamond extends CustomItem {

	public LifeSuckDiamond(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "¤aDiamond Sword of Life";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		setItemName(i, this.name());
		setCustomEnchantment(i, plugin.suck, 1);
		return i;
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

	@Override
	public boolean hasLevel() {
		return false;
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (checkName(e.getRecipe().getResult(), this.name())) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.lifesuck")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}

	@Override
	public int maxLevel() {
		return 0;
	}
}
