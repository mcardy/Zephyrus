package minny.zephyrus.hooks;

import net.milkbowl.vault.Vault;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PluginHook {

	public WorldGuardPlugin wg;
	public Vault vault;

	public boolean worldGuard() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		if (plugin != null) {
			return true;
		}
		return false;
	}

	public boolean economy() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Vault");
		if (plugin != null) {
			return true;
		}
		return false;
	}

	public void econHook() {
		Plugin vaultplugin = Bukkit.getPluginManager().getPlugin("Vault");
		vault = (Vault) vaultplugin;
	}

	public void wgHook() {
		Plugin wgplugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		wg = (WorldGuardPlugin) wgplugin;
	}

}
