package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ItemUtil;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class Item extends ItemUtil implements Listener{

	public Item(Zephyrus plugin) {
		super(plugin);
	}

	public abstract String name();
	public abstract ItemStack item();
	public abstract void createItem(ItemStack i);
	public abstract Recipe recipe();
	public int maxLevel(){
		return -1;
	}
	public int reqLevel(){
		return 0;
	}
}
