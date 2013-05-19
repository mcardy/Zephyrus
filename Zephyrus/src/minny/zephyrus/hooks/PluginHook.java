package minny.zephyrus.hooks;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PluginHook {

	public WorldGuardPlugin wg;
	public Economy econ = null;

	public boolean worldGuard() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		if (plugin != null) {
			return true;
		}
		return false;
	}

	public boolean economy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public void hookEcon() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer()
				.getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
	}

	public void hookWG() {
		Plugin wgplugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		wg = (WorldGuardPlugin) wgplugin;
	}

}
