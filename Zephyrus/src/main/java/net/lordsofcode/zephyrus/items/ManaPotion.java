package net.lordsofcode.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ManaPotion extends CustomItem {

	@Override
	public String getName() {
		return ChatColor.getByChar("b") + "Mana Potion";
	}

	@Override
	public ItemStack getItem() {
		ItemStack i = new ItemStack(Material.POTION);
		setItemName(i, getDisplayName());
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Restores Mana");
		m.setLore(lore);
		i.setItemMeta(m);
		setGlow(i);
		return i;
	}

	@Override
	public Recipe getRecipe() {
		ItemStack manaPotion = getItem();
		ShapedRecipe recipe = new ShapedRecipe(manaPotion);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('B', Material.POTION, 8192);
		recipe.setIngredient('A', Material.GLOWSTONE_DUST);
		return recipe;
	}

	@EventHandler
	public void onManaPotion(PlayerItemConsumeEvent e) {
		if (checkName(e.getItem(), getDisplayName())) {
			Player player = e.getPlayer();
			Zephyrus.getUser(player).drainMana(-100);
		}
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (checkName(e.getRecipe().getResult(), getDisplayName())) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.mana")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}

	@Override
	public String getPerm() {
		return "mana";
	}
}
