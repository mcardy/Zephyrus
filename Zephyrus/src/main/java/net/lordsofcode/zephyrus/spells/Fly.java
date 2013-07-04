package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Fly extends Spell {

	Map<String, Integer> list;
	
	public Fly(Zephyrus plugin) {
		super(plugin);
		list = new HashMap<String, Integer>();
		Lang.add("spells.fly.applied", "You can now fly for [TIME] seconds");
		Lang.add("spells.fly.warning", "$7Your wings start to dissappear!");
		Lang.add("spells.fly.end", "$7Your wings were taken!");
	}

	@Override
	public String name() {
		return "fly";
	}

	@Override
	public String bookText() {
		return "Allows you to fly for 30 seconds.";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player, String[] args) {
		int t = getConfig().getInt(this.name() + ".duration");
		if (list.containsKey(player.getName())) {
			list.put(player.getName(), list.get(player.getName()) + t);
			player.sendMessage(Lang.get("spells.fly.applied").replace("[TIME]", list.get(player.getName()) + ""));
			player.setAllowFlight(true);
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		} else {
			list.put(player.getName(), t);
			player.sendMessage(Lang.get("spells.fly.applied").replace("[TIME]", list.get(player.getName()) + ""));
			player.setAllowFlight(true);
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		}
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 120);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();

		i.add(new ItemStack(Material.FEATHER, 32));

		return i;
	}
	
	@Override
	public String reqSpell() {
		return "feather";
	}

	private class FeatherRunnable extends BukkitRunnable {

		Player player;
		
		FeatherRunnable(Player player) {
			this.player = player;
		}
		
		@Override
		public void run() {
			if (list.containsKey(player.getName()) && list.get(player.getName()) > 0) {
				if (list.get(player.getName()) == 5) {
					Lang.msg("spells.fly.warning", player);
				}
				list.put(player.getName(), list.get(player.getName()) - 1);
				new FeatherRunnable(player).runTaskLater(plugin, 20);
			} else {
				list.remove(player.getName());
				player.setAllowFlight(false);
				Lang.msg("spells.fly.end", player);
			}
		}
		
	}

	@Override
	public SpellType type() {
		return SpellType.AIR;
	}
	
}
