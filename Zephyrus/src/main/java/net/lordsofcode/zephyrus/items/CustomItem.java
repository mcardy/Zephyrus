package net.lordsofcode.zephyrus.items;

import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.ItemUtil;
import net.lordsofcode.zephyrus.utils.Merchant;

import org.bukkit.ChatColor;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class CustomItem extends ItemUtil implements ICustomItem {
	
	@Override
	public String getDisplayName() {
		ConfigHandler cfg = new ConfigHandler("items.yml");
		if (cfg.getConfig().contains(getConfigName() + ".displayname")) {
			return cfg.getConfig().getString(getConfigName() + ".displayname");
		} else {
			return getName();
		}
	}
	
	@Override
	public boolean hasLevel() {
		return true;
	}
	
	@Override
	public int getReqLevel() {
		return 0;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	public String getConfigName() {
		return ChatColor.stripColor(getName().replace(" ", "-").toLowerCase());
	}
	
	@Override
	public Merchant getMerchant() {
		return null;
	}
	
}
