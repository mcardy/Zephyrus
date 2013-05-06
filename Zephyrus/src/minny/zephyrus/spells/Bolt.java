package minny.zephyrus.spells;

import minny.zephyrus.Zephyrus;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Bolt extends Spell {

	public Bolt(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
	}

	@Override
	public String name() {
		return "bolt";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player) {
		Location loc = player.getTargetBlock(null, 1000).getLocation();
		player.getWorld().strikeLightning(loc);
	}

}
