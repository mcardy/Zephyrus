package minny.zephyrus.utils;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.enchantments.GlowEffect;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public Zephyrus plugin;
	GlowEffect glow;

	public ItemUtil(Zephyrus plugin) {
		this.plugin = plugin;
	}

	public void setItemName(ItemStack i, String name, String color) {
		ItemMeta m = i.getItemMeta();
		m.setDisplayName("¤" + color + name);
		i.setItemMeta(m);
	}

	public void setUnsafeGlow(ItemStack i) {
		i.addUnsafeEnchantment(plugin.glow, 1);
	}

	public void setGlow(ItemStack i) {
		i.addEnchantment(plugin.glow, 1);
	}

	public ItemStack setItemLevel(ItemStack i, int level) {
		ItemMeta m = i.getItemMeta();
		List<String> l = m.getLore();
		try {
			l.set(0, "¤7Level: " + level);
		} catch (NullPointerException e) {
			l = new ArrayList<String>();
			l.add(0, "¤7Level: " + level);
		}

		m.setLore(l);
		i.setItemMeta(m);
		return i;
	}

	public boolean checkName(ItemStack i, String name) {
		try {
			if (i.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
				return true;
			}
		} catch (NullPointerException exception) {
		}
		return false;
	}

	public int getItemLevel(ItemStack i) {
		ItemMeta m = i.getItemMeta();
		String data = m.getLore().get(0).replace("¤7Level: ", "");
		return Integer.parseInt(data);
	}

	public boolean isRecharging(ItemStack i) {
		ItemMeta m = i.getItemMeta();
		if (m.hasLore()) {
			List<String> lore = m.getLore();
			if (lore.contains("¤6Recharging")) {
				return true;
			}
		}

		return false;
	}

	public void setRecharging(ItemStack i, boolean b) {
		if (b) {
			ItemMeta m = i.getItemMeta();
			List<String> lore = m.getLore();
			lore.add("¤6Recharging");
			m.setLore(lore);
			i.setItemMeta(m);
		} else {
			ItemMeta m = i.getItemMeta();
			List<String> lore = m.getLore();
			lore.remove("¤6Recharging");
			m.setLore(lore);
			i.setItemMeta(m);
		}
	}

	public static int delayFromLevel(int level) {
		switch (level) {
		case 1:
			return 400;
		case 2:
			return 200;
		case 3:
			return 100;
		case 4:
			return 20;
		case 5:
			return 0;
		case 6:
			return 400;
		case 7:
			return 200;
		case 8:
			return 100;
		case 9:
			return 20;
		case 10:
			return 0;
		}
		return 0;
	}
}
