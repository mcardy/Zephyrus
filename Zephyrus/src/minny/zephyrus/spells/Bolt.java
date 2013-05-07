package minny.zephyrus.spells;

import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
	public String bookText() {
		return "";
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

	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

}
