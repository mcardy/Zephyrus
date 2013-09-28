package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Effects;
import net.lordsofcode.zephyrus.utils.Lang;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

	public Conjure() {
		Lang.add("spells.conjure.invfull", "Your inventory is full!");
		Lang.add("spells.conjure.conplete", "You have conjured [AMOUNT] [ITEM]");
		Lang.add("spells.conjure.badid", "Invalid ID");
		Lang.add("spells.conjure.badamount", "Invalid amount");
		Lang.add("spells.conjure.cannot", "That item cannot be conjured");
		Lang.add("spells.conjure.noitem", "Specify an item to conjure");
	}

	@Override
	public String getName() {
		return "conjure";
	}

	@Override
	public String getDesc() {
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
	public boolean run(Player player, String[] args) {
		if (!(args.length < 2)) {
			int id = -1;
			if (args[1].contains("\\:")) {
				String[] ids = args[1].split("\\:");
				if (ids.length == 2) {
					try {
						id = Integer.parseInt(ids[0]);
						Byte.parseByte(ids[1]);
					} catch (Exception e) {
						Lang.errMsg("spells.conjure.badid", player);
						return false;
					}
				} else {
					Lang.errMsg("spells.conjure.badid", player);
					return false;
				}
			} else {
				try {
					id = Integer.parseInt(args[1]);
				} catch (Exception e) {
					Lang.errMsg("spells.conjure.badid", player);
					return false;
				}
			}
			int amount = 1;
			if (args.length == 3) {
				try {
					amount = Integer.parseInt(args[2]);
				} catch (Exception e) {
					Lang.errMsg("spells.conjure.badamount", player);
					return false;
				}
			}
			if (getValue(id) == -1) {
				Lang.errMsg("spells.conjure.cannot", player);
				return false;
			}
			if (Zephyrus.getUser(player).getMana() < getValue(id) * amount) {
				Lang.errMsg("nomana", player);
				return false;
			}
		} else {
			Lang.errMsg("spells.conjure.noitem", player);
		}
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
			Lang.errMsg("spells.conjure.invfull", player);
		} else {
			String itemName = WordUtils.capitalizeFully(item.getType().toString().replace("_", " "));
			player.sendMessage(Lang.get("spells.conjure.complete").replace("[AMOUNT]", ChatColor.GOLD + "" + amount)
					.replace("[ITEM]", itemName));
			Zephyrus.getUser(player).drainMana(getValue(id) * amount);
		}
		Effects.playEffect(Sound.NOTE_PIANO, player.getLocation(), 1, 5);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.DIAMOND, 4));
		s.add(new ItemStack(Material.GOLD_INGOT, 8));
		s.add(new ItemStack(Material.IRON_INGOT, 16));
		return s;
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

	@Override
	public EffectType getPrimaryType() {
		return EffectType.CREATION;
	}

	@Override
	public Element getElementType() {
		return Element.MAGIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
