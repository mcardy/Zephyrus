package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Feed extends Spell{

	public Feed(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "feed";
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
		return 20;
	}

	@Override
	public void run(Player player) {
		player.setFoodLevel(player.getFoodLevel() + 1);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.PORK));
		items.add(new ItemStack(Material.COOKED_BEEF));
		items.add(new ItemStack(Material.COOKED_CHICKEN));
		items.add(new ItemStack(Material.COOKED_FISH));
		return items;
	}

}
