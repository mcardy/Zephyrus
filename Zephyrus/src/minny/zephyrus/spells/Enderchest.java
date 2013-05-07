package minny.zephyrus.spells;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import minny.zephyrus.Zephyrus;

public class Enderchest extends Spell {

	public Enderchest(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
	}

	@Override
	public String name() {
		return "enderchest";
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
		Inventory i = player.getEnderChest();
		player.openInventory(i);
	}

	@Override
	public ItemStack spellItem() {
		return null;
	}
}
