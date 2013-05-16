package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.RemoveFlightUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Fly extends Spell {

	public Fly(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "fly";
	}

	@Override
	public String bookText() {
		return "Allows you to fly for 30 seconds.";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player) {
		player.setAllowFlight(true);
		new RemoveFlightUtil(plugin, player).cancel();
		new RemoveFlightUtil(plugin, player).runTaskLater(plugin, 500);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		
		i.add(new ItemStack(Material.FEATHER, 32));
		
		return i;
	}

}
