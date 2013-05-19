package minny.exampleaddon;

import minny.zephyrus.Zephyrus;

import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

	public void onEnable() {
		Zephyrus zephyrus = new Zephyrus();
		new ExampleSpell(zephyrus);
	}
	
}
