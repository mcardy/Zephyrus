package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Enderchest extends Spell {

	public Enderchest(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "enderchest";
	}

	@Override
	public String bookText() {
		return "Your very own portable enderchest!";
	}
	
	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player) {
		Inventory i = player.getEnderChest();
		player.openInventory(i);
		player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ENDER_CHEST));
		return items;
	}
}
