package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import minny.zephyrus.Zephyrus;

public class Heal extends Spell {

	public Heal(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "heal";
	}

	@Override
	public String bookText() {
		return "Can't you guess what this does by the title?";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 2;
	}

	@Override
	public void run(Player player) {
		player.setHealth(player.getHealth() + 1);
	}

	@Override
	public boolean canRun(Player player) {
		if (player.getHealth() == 20) {
			return false;
		}
		return true;
	}

	@Override
	public String failMessage(){
		return "You are already at max health!";
	}
	
	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.GOLDEN_APPLE));
		return items;
	}

}
