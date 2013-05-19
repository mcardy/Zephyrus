package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ItemUtil;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class CustomItem extends ItemUtil implements Listener{

	public CustomItem(Zephyrus plugin) {
		super(plugin);
		if (recipe() != null){
			plugin.getServer().addRecipe(recipe());
		}
		try {
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		} catch (Exception e){
		}
		if (hasLevel() && this.name() != null){
			Zephyrus.itemMap.put(this.name(), this);
		}
	}

	public abstract String name();
	public abstract ItemStack item();
	public abstract void createItem(ItemStack i);
	public abstract Recipe recipe();
	public boolean hasLevel(){
		return true;
	}
	public int maxLevel(){
		return 10;
	}
	public int reqLevel(){
		return 0;
	}
}
