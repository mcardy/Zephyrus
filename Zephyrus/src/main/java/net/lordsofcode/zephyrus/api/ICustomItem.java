package net.lordsofcode.zephyrus.api;

import net.lordsofcode.zephyrus.utils.Merchant;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ICustomItem extends Listener {

	public String getName();
	public String getDisplayName();
	
	public ItemStack getItem();
	
	public Recipe getRecipe();
	
	public int getMaxLevel();
	public boolean hasLevel();
	public int getReqLevel();
	
	public String getPerm();
	
	public Merchant getMerchant();
	
}
