package net.lordsofcode.zephyrus.items.wands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class BasicWand extends Wand {

	public BasicWand() {
		Lang.add("wand.enchanter", "You have successfully created an $6$lArcane Leveller");
		Lang.add("wand.nospell", "Spell recipe not found!");
		Lang.add("wand.noperm", "You do not have permission to learn [SPELL]");
		Lang.add("wand.reqlevel", "That spell requires level [LEVEL]");
		Lang.add("wand.reqspell", "That spell requires the knowledge of [SPELL]");
	}

	@Override
	public String getName() {
		return ChatColor.GOLD + "Wand";
	}

	@Override
	public ItemStack getItem() {
		int id = Zephyrus.getConfig().getInt("Wand-ID");
		ItemStack i;
		try {
			i = new ItemStack(Material.getMaterial(id));
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
		}
		try {
			setItemName(i, getDisplayName());
		} catch (Exception e) {
			i = new ItemStack(Material.STICK);
			setItemName(i, getDisplayName());
		}
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Regular old default wand");
		m.setLore(lore);
		i.setItemMeta(m);
		setGlow(i);
		return i;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		recipe.shape("  C", " B ", "A  ");
		recipe.setIngredient('C', Material.GLOWSTONE_DUST);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.GOLD_INGOT);
		return recipe;
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@Override
	public String getPerm() {
		return "wand";
	}

	@Override
	public int getManaDiscount() {
		return 0;
	}

	@Override
	public int getReqLevel() {
		return 0;
	}

	@Override
	public int getPower() {
		return 0;
	}
	
}
