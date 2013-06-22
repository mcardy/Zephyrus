package net.lordsofcode.zephyrus.hooks;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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

	private static WorldGuardPlugin wg;
	
	public static Economy econ = null;

	/**
	 * Determines if WorldGuard is installed
	 * @return True if WorldGuard is installed, false otherwise
	 */
	public static boolean worldGuard() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		if (plugin != null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if Vault is installed
	 * @return True if Vault is installed, false otherwise
	 */	
	public static boolean economy() {
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

	/**
	 * Hooks into Vault
	 */
	public static void hookEcon() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer()
				.getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
	}

	/**
	 * Hooks into WorldGuard
	 */
	public static void hookWG() {
		Plugin wgplugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		wg = (WorldGuardPlugin) wgplugin;
	}
	
	/**
	 * Checks if the player can build
	 * @param player The player in question
	 * @param block The block in question
	 * @return True if the player can build, false otherwise
	 */
	public static boolean canBuild(Player player, Block block) {
		if (PluginHook.worldGuard()) {
			PluginHook.hookWG();
			if (PluginHook.wg.canBuild(player, block)) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the player can build
	 * @param player The player in question
	 * @param block The location in question
	 * @return True if the player can build, false otherwise
	 */
	public static boolean canBuild(Player player, Location loc) {
		if (PluginHook.worldGuard()) {
			PluginHook.hookWG();
			return PluginHook.wg.canBuild(player, loc);
		}
		return true;
	}
	
	public static boolean allowExplosion() {
		if (worldGuard()) {
			hookWG();
			return !wg.getConfig().getBoolean("ignition.block-tnt");
		}
		return true;
	}

}
