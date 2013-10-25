package net.lordsofcode.zephyrus.items.wands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.utils.Lang;

import org.apache.commons.lang3.text.WordUtils;
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
		return "wand.basic";
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
		return 1;
	}

	@Override
	public String getSpell(ItemStack i) {
		return i.getItemMeta().getLore().get(0)
				.replace(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY, "").toLowerCase();
	}

	@Override
	public List<String> getDefaultLore() {
		List<String> list = new ArrayList<String>();
		list.add("Regular old default wand");
		return list;
	}

	@Override
	public List<String> getBoundLore(ISpell spell) {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY + WordUtils.capitalize(spell.getDisplayName()));
		return list;
	}
	
	@Override
	public String getBoundName(ISpell spell) {
		return getDisplayName() + " " + ChatColor.GRAY + "[" + ChatColor.GOLD
				+ WordUtils.capitalizeFully(spell.getDisplayName()) + ChatColor.GRAY + "]";
	}
	
	@Override
	public boolean getCanBind() {
		return true;
	}
	
}
