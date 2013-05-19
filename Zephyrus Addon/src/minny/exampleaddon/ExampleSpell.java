package minny.exampleaddon;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExampleSpell extends Spell {

	public ExampleSpell(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String bookText() {
		return "The spell will heal you";
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public String name() {
		return "test";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public void run(Player player) {
		player.setHealth(20);
	}

	@Override
	public boolean canRun(Player player) {
		if (player.getHealth() == 20) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String failMessage() {
		return "You already are healed";
	}
	
	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		ItemStack item = new ItemStack(Material.APPLE);
		items.add(item);
		return items;
	}
	
}
