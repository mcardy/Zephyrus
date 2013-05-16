package minny.zephyrus.spells;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import minny.zephyrus.Zephyrus;

public class Armour extends Spell {

	public Armour(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "armour";
	}

	@Override
	public String bookText() {
		return null;
	}

	@Override
	public int reqLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int manaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<ItemStack> spellItems() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
