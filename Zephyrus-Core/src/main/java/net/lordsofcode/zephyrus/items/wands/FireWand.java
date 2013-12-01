package net.lordsofcode.zephyrus.items.wands;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class FireWand extends Wand {

	@Override
	public int getPower(ISpell spell) {
		return spell.getElementType() == Element.FIRE ? 3 : 1;
	}

	@Override
	public int getManaDiscount(ISpell spell) {
		return spell.getElementType() == Element.FIRE ? 15 : 0;
	}

	@Override
	public boolean getCanBind(ISpell spell) {
		return spell.getElementType() == Element.FIRE;
	}

	@Override
	public String getName() {
		return ChatColor.RED + "Fire Wand";
	}

	@Override
	public Recipe getRecipe() {
		ShapedRecipe recipe = new ShapedRecipe(getItem());
		recipe.shape("DDC", "DBD", "ADD");
		recipe.setIngredient('C', Material.GLOWSTONE_DUST);
		recipe.setIngredient('B', Material.STICK);
		recipe.setIngredient('A', Material.GOLD_INGOT);
		recipe.setIngredient('D', Material.BLAZE_POWDER);
		return recipe;
	}
	
	@Override
	public List<String> getBoundLore(ISpell spell) {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY + WordUtils.capitalize(spell.getDisplayName()));
		list.add(ChatColor.GRAY + "Mana cost " + ChatColor.GREEN + "-" + getManaDiscount(spell) + "%");
		list.add(ChatColor.GRAY + "Power " + ChatColor.GREEN + "+" + getPower(spell));
		return list;
	}

	@Override
	public List<String> getDefaultLore() {
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.GRAY + "Upgraded wand:" + ChatColor.RED + " Fire");
		list.add(ChatColor.GRAY + "Mana cost " + ChatColor.GREEN + "-0% (-15% Fire)");
		list.add(ChatColor.GRAY + "Power " + ChatColor.GREEN + "+1 (+3 Fire)");
		return list;
	}

	@Override
	public String getBoundName(ISpell spell) {
		return getDisplayName() + " [" + ChatColor.DARK_RED
				+ WordUtils.capitalizeFully(spell.getDisplayName()) + ChatColor.RED + "]";
	}

	@Override
	public int getReqLevel() {
		return 6;
	}

	@Override
	public String getPerm() {
		return "wand.advanced";
	}

	@Override
	public String getSpell(ItemStack i) {
		return i.getItemMeta().getLore().get(0).replace(ChatColor.GRAY + "Bound spell: " + ChatColor.DARK_GRAY, "").toLowerCase();
	}

}
