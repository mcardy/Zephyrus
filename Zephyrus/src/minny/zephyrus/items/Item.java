package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class Item extends SetItem{

	public Item(Zephyrus plugin) {
		super(plugin);
	}

	public abstract ItemStack item();
	public abstract void createItem(ItemStack i);
	public abstract Recipe recipe();
	
}
