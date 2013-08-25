package net.lordsofcode.zephyrus.example;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Zephyrus Example Addon
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ExamplePlugin extends JavaPlugin {

	public void onEnable() {
		// Registers the spell with Zephyrus by creating a new spell instance
		Zephyrus.registerSpell(new ExampleSpell());
		// Registers the enchantment with Zephyrus by creating a new
		// ExampleEnchantment instance. Choose an enchantment ID for this
		// enchantment.
		Zephyrus.registerEnchantment(new ExampleEnchantment(160));
		// Registers the listener the regular bukkit way
		getServer().getPluginManager().registerEvents(
				new ExampleEventHandler(), this);
	}

}
