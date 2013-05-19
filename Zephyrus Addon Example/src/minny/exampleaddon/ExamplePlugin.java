package minny.exampleaddon;

import minny.zephyrus.Zephyrus;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ExamplePlugin extends JavaPlugin {

	public void onEnable() {
		//Initializes Zephyrus as a variable.
		Zephyrus zephyrus = new Zephyrus();
		
		//Calls the constructor of the class Spell adding ExampleSpell to the list of spells.
		new ExampleSpell(zephyrus);
	}
	
}
