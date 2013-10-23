package net.lordsofcode.zephyrus.uninstaller;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Uninstaller extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getLogger()
				.warning(
						"This plugin will remove all Zephyrus related items to allow for the removal of the plugin Zephyrus."
								+ " Do not use this plugin if you do not intend on removing Zephyrus as all players will lose all items added by Zephyrus.");
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		for (ItemStack item : event.getPlayer().getInventory().getContents()) {
			for (Enchantment ench : item.getEnchantments().keySet()) {
				if (ench.getId() >= 120) {
					item.removeEnchantment(ench);
				}
			}
		}
		for (ItemStack item : event.getPlayer().getInventory().getArmorContents()) {
			for (Enchantment ench : item.getEnchantments().keySet()) {
				if (ench.getId() >= 120) {
					item.removeEnchantment(ench);
				}
			}
		}
	}

}
