package net.lordsofcode.zephyrus.items.wands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;

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

public class ObsidianWand extends Wand {

	@Override
	public int getManaDiscount() {
		return 5;
	}

	@Override
	public String getName() {
		return ChatColor.DARK_PURPLE + "Obsidian Wand";
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
		m.setLore(getDefaultLore());
		i.setItemMeta(m);
		setGlow(i);
		return i;
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		recipe.shape("OOC", "OBO", "AOO");
		recipe.setIngredient('C', Material.GLOWSTONE_DUST);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.GOLD_INGOT);
		recipe.setIngredient('O', Material.OBSIDIAN);
		return recipe;
	}

	@Override
	public int getReqLevel() {
		return 5;
	}

	@Override
	public String getPerm() {
		return "wand.advanced";
	}

	@Override
	public int getPower() {
		return 2;
	}

	@Override
	public List<String> getBoundLore(ISpell spell) {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY + WordUtils.capitalize(spell.getDisplayName()));
		list.add(ChatColor.GRAY + "Mana cost " + ChatColor.GREEN + "-5%");
		list.add(ChatColor.GRAY + "Power " + ChatColor.GREEN + "+1");
		return list;
	}

	@Override
	public List<String> getDefaultLore() {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Upgraded wand:" + ChatColor.DARK_PURPLE + " Obsidian");
		list.add(ChatColor.GRAY + "Mana cost " + ChatColor.GREEN + "-5%");
		list.add(ChatColor.GRAY + "Power " + ChatColor.GREEN + "+1");
		return list;
	}

	@Override
	public String getSpell(ItemStack i) {
		return i.getItemMeta().getLore().get(0).replace(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY, "").toLowerCase();
	}

	@Override
	public String getBoundName(ISpell spell) {
		return getDisplayName() + " [" + ChatColor.LIGHT_PURPLE
				+ WordUtils.capitalizeFully(spell.getDisplayName()) + ChatColor.DARK_PURPLE + "]";
	}
	
	@Override
	public boolean getCanBind() {
		return true;
	}

}
