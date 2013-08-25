package net.lordsofcode.zephyrus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;

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
	private static StateFlag flag;

	/**
	 * Determines if WorldGuard is installed
	 * @return True if WorldGuard is installed, false otherwise
	 */
	public static boolean isWorldGuard() {
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
	public static boolean isEconomy() {
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
	
	public static void loadFlags() {
		flag = new StateFlag("allowspells", true);
		PluginHook.addWGFlag(flag);
	}
	
	/**
	 * Gets the allow spells flag.
	 * @return
	 */
	public static StateFlag getFlag() {
		return flag;
	}
	
	/**
	 * Checks if the player can build
	 * @param player The player in question
	 * @param block The block in question
	 * @return True if the player can build, false otherwise
	 */
	public static boolean canBuild(Player player, Block block) {
		if (PluginHook.isWorldGuard()) {
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
		if (PluginHook.isWorldGuard()) {
			PluginHook.hookWG();
			return PluginHook.wg.canBuild(player, loc);
		}
		return true;
	}
	
	public static WorldGuardPlugin getWorldGuard() {
		if (PluginHook.isWorldGuard()) {
			PluginHook.hookWG();
			return PluginHook.wg;
		}
		return null;
	}
	
	public static boolean allowExplosion() {
		if (isWorldGuard()) {
			hookWG();
			return !wg.getConfig().getBoolean("ignition.block-tnt");
		}
		return true;
	}
	
	public static boolean canCast(Player player, Location loc) {
		if (!isWorldGuard()) {
			return true;
		}
		if (getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(loc).allows(getFlag())) {
			return true;
		}
		return false;
	}
	
	public static void addWGFlag(StateFlag flag) {
        try {
            Field flagField = DefaultFlag.class.getField("flagsList");

            Flag<?>[] flags = new Flag<?>[DefaultFlag.flagsList.length + 1];
            System.arraycopy(DefaultFlag.flagsList, 0, flags, 0, DefaultFlag.flagsList.length);

            flags[DefaultFlag.flagsList.length] = flag;

            if(flag == null) {
                throw new RuntimeException("flag is null");
            }

            setStaticValue(flagField, flags);
        }
        catch(Exception ex) {
            Bukkit.getServer().getLogger().log(Level.WARNING, "Could not add flag {0} to WorldGuard", flag.getName());
        }

        for(int i = 0; i < DefaultFlag.getFlags().length; i++) {
            Flag<?> flag1 = DefaultFlag.getFlags()[i];
            if (flag1 == null) {
                throw new RuntimeException("Flag["+i+"] is null");
            }
        }
    }
	
	private static void setStaticValue(Field field, Object value) {
        try {
            Field modifier = Field.class.getDeclaredField("modifiers");

            modifier.setAccessible(true);
            modifier.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, value);
        } catch(Exception ex) { }
    }

}
