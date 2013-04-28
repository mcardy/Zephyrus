package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

	Zephyrus plugin;
	
	Item(Zephyrus plugin){
		
	}
	
	public void setItemName(ItemStack i, String name, String color){
		ItemMeta m = i.getItemMeta();
		m.setDisplayName("¤" + color + name);
		i.setItemMeta(m);
	}
	
	public void setItemLevel(ItemStack i, int level){
		ItemMeta m = i.getItemMeta();
		List<String> l = m.getLore();
		try {
		l.set(0, "¤7Level: " + level);
		} catch (NullPointerException e){
			l = new ArrayList<String>();
			l.add(0, "¤7Level: " + level);
		}

		m.setLore(l);
		i.setItemMeta(m);
	}
	
	public int getItemLevel(ItemStack i){
		ItemMeta m = i.getItemMeta();
		String data = m.getLore().get(0).replace("¤7Level: ", "");
		return Integer.parseInt(data);
	}
}
