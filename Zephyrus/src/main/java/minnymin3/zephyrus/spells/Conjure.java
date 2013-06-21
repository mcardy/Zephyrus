package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.player.LevelManager;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Conjure extends Spell {

	public Conjure(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "conjure";
	}

	@Override
	public String bookText() {
		return "Conjures the item that you specify! Only supports raw materials. /cast conjure [id] [amount]";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 0;
	}

	@Override
	public void run(Player player, String[] args) {
		int id;
		byte data = 0;
		int amount = 1;
		if (args[1].contains("\\:")) {
			String[] ids = args[1].split("\\:");
			id = Integer.parseInt(ids[0]);
			data = Byte.parseByte(ids[1]);
		} else {
			id = Integer.parseInt(args[1]);
		}
		if (args.length == 3) {
			amount = Integer.parseInt(args[2]);
		}
		ItemStack item = new ItemStack(Material.getMaterial(id), amount, data);
		HashMap<Integer, ItemStack> map = player.getInventory().addItem(item);
		if (!map.isEmpty()) {
			player.sendMessage(ChatColor.GRAY + "Your inventory is full!");
		} else {
			player.sendMessage(ChatColor.GRAY + "You have conjured " + ChatColor.GOLD + amount + " " + WordUtils.capitalizeFully(item.getType().toString().replace("_", " ")));
			LevelManager.drainMana(player, getValue(id) * amount);
		}
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (!(args.length < 2)) {
			int id = -1;
			if (args[1].contains("\\:")) {
				String[] ids = args[1].split("\\:");
				if (ids.length == 2) {
					try {
						id = Integer.parseInt(ids[0]);
						Byte.parseByte(ids[1]);
					} catch (Exception e) {
						player.sendMessage(ChatColor.GRAY + "Invalid id!");
						return false;
					}
				} else {
					player.sendMessage(ChatColor.GRAY + "Invalid id!");
					return false;
				}
			} else {
				try {
					id = Integer.parseInt(args[1]);
				} catch (Exception e) {
					player.sendMessage(ChatColor.GRAY + "Invalid id!");
					return false;
				}
			}
			int amount = 1;
			if (args.length == 3) {
				try {
					amount = Integer.parseInt(args[2]);
				} catch (Exception e) {
					player.sendMessage(ChatColor.GRAY
							+ "Amount must be a number!");
					return false;
				}
			}
			if (getValue(id) == -1) {
				player.sendMessage(ChatColor.GRAY
						+ "That item cannot be conjured!");
				return false;
			}
			if (LevelManager.getMana(player) < getValue(id) * amount) {
				player.sendMessage(ChatColor.GRAY + "You do not have enough mana to conjure that item!");
				return false;
			} else {
				return true;
			}
		} else {
			player.sendMessage(ChatColor.GRAY + "You must specify an item!");
		}
		return false;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.DIAMOND, 4));
		s.add(new ItemStack(Material.GOLD_INGOT, 8));
		s.add(new ItemStack(Material.IRON_INGOT, 16));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.CONJURE;
	}
	
	@Override
	public boolean canBind() {
		return false;
	}

	public int getValue(int id) {
		switch (id) {
		case 1:
			return 8;
		case 2:
			return 32;
		case 3:
			return 1;
		case 4:
			return 1;
		case 5:
			return 8;
		case 6:
			return 8;
		case 12:
			return 2;
		case 13:
			return 2;
		case 14:
			return 2048;
		case 15:
			return 256;
		case 16:
			return 128;
		case 17:
			return 64;
		case 18:
			return 4;
		case 19:
			return 32;
		case 20:
			return 8;
		case 21:
			return 2048;
		case 24:
			return 4;
		case 27:
			return 1024;
		case 28:
			return 320;
		case 29:
			return 512;
		case 30:
			return 16;
		case 33:
			return 256;
		case 35:
			return 64;
		case 45:
			return 64;
		case 47:
			return 256;
		case 48:
			return 128;
		case 49:
			return 128;
		case 50:
			return 24;
		case 73:
			return 192;
		case 78:
			return 32;
		case 79:
			return 32;
		case 81:
			return 32;
		case 82:
			return 64;
		case 86:
			return 64;
		case 98:
			return 8;
		case 102:
			return 128;
		case 112:
			return 64;
		case 121:
			return 32;
		case 129:
			return 4096;
		case 155:
			return 32;
		}
		return -1;
	}

}
