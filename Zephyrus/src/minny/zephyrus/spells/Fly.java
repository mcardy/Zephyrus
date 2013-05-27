package minny.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
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
		if (list.containsKey(player.getName())) {
			list.put(player.getName(), list.get(player.getName()) + 120);
			player.sendMessage(ChatColor.GRAY + "You can now float for " + list.get(player.getName()) + "seconds");
			player.setAllowFlight(true);
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		} else {
			list.put(player.getName(), 120);
			player.sendMessage(ChatColor.GRAY + "You can now float for " + list.get(player.getName()) + " seconds");
			player.setAllowFlight(true);
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();

		i.add(new ItemStack(Material.FEATHER, 32));

		return i;
	}
	
	@Override
	public Spell reqSpell() {
		return Zephyrus.spellMap.get("feather");
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
					player.sendMessage(ChatColor.GRAY + "5 seconds left of flight!");
				}
				list.put(player.getName(), list.get(player.getName()) - 1);
				new FeatherRunnable(player).runTaskLater(plugin, 20);
			} else {
				list.remove(player.getName());
				player.setAllowFlight(false);
				player.sendMessage(ChatColor.GRAY + "Your wings dissappeared!");
			}
		}
		
	}
	
}
