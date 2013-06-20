package minnymin3.exampleaddon;

import minnymin3.zephyrus.Zephyrus;

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
		//Initializes the plugin Zephyrus as a variable.
		Zephyrus zephyrus = Zephyrus.getInstance();
		
		//Calls the constructor of the class Spell adding ExampleSpell to the list of spells.
		new ExampleSpell(zephyrus);
		
		//Register WorldGuard spells exactly the same way
		new ExampleWorldGuardSpell(zephyrus);
	}
	
}
