package minny.zephyrus.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.enchantments.GlowEffect;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public Zephyrus plugin;
	GlowEffect glow;

	public ItemUtil(Zephyrus plugin) {
		this.plugin = plugin;
	}

	public void setItemName(ItemStack i, String name) {
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
	}

	public void setGlow(ItemStack i) {
		i.addEnchantment(plugin.glow, 1);
	}

	public void setCustomEnchantment(ItemStack item, Enchantment enchant,
			int level) {
		item.addEnchantment(enchant, level);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		try {
			lore.add("¤7" + enchant.getName() + " " + enchantLevel(level));
		} catch (Exception e) {
			lore = new ArrayList<String>();
			lore.add("¤7" + enchant.getName() + " " + enchantLevel(level));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public String enchantLevel(int level) {
		switch (level) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		}
		return "";
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

	public void delay(Map<String, Object> map, Zephyrus plugin, int delay,
			String name) {
		int time = delay / 20;
		map.put(name, time);
		new CountdownUtil(map, name, plugin).runTaskLater(plugin, 20);
		new DelayUtil(map, name).runTaskLater(plugin, delay);
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
			return 60;
		case 5:
			return 20;
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
