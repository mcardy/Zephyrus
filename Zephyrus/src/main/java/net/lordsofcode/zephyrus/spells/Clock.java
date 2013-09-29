package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class Clock extends Spell {

	public Clock() {
		Lang.add("spells.clock.usage", "Usage: /cast time [time]");
		Lang.add("spells.clock.time", "It is [TIME]");
	}
	
	@Override
	public String getName() {
		return "clock";
	}

	@Override
	public String getDesc() {
		return "You have discovered the secrete to time itself. You have gained the ability to control the time of day!"; 
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 18;
	}

	@Override
	public int manaCost() {
		return 250;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.NETHER_BRICK, 8));
		s.add(new ItemStack(Material.GLOWSTONE, 8));
		s.add(new ItemStack(Material.WATCH));
		s.add(new ItemStack(Material.DAYLIGHT_DETECTOR));
		return s;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.WORLD;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (args.length == 1) {
			ChatColor color;
			long time = player.getWorld().getTime();
			if (time > 12500) {
				color = ChatColor.DARK_BLUE;
			} else {
				color = ChatColor.GOLD;
			}
			long hours = time/1000;
			long minutes = Math.round((time-(hours*1000))/16.6666666);
			boolean am = hours < 6 || hours >= 18;
			if (hours >= 18) {
				hours -= 12;
			}
			hours += 6;
			if (hours > 12) {
				hours -= 12;
			}
			if (hours == 0) {
				hours = 12;
			}
			String min = minutes < 10 ? "0" + minutes : minutes + "";
			String s = am ? "AM" : "PM";
			player.sendMessage(color + Lang.get("spells.clock.time").replace("[TIME]", hours + ":" + min + " " + s));
			return false;
		} else {
			if (args[1].equalsIgnoreCase("day")) {
				setTime(player, 1000);
				return true;
			} else if (args[1].equalsIgnoreCase("night")) {
				setTime(player, 14000);
				return true;
			} else if (args[1].equalsIgnoreCase("noon")) {
				setTime(player, 6000);
				return true;
			} else if (args[1].equalsIgnoreCase("midnight")) {
				setTime(player, 18000);
				return true;
			} else {
				try {
					int time = Integer.parseInt(args[1]);
					setTime(player, time);
					return true;
				} catch (Exception ex) {
					Lang.errMsg("spells.clock.usage", player);
					return false;
				}
			}
		}
	}

	private void setTime(Player player, int time) {
		player.getWorld().setTime(time);
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
	
	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("dim");
	}

}
