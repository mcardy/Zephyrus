package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Feather extends Spell implements Listener {

	Map<String, Integer> list;

	public Feather(Zephyrus plugin) {
		super(plugin);
		list = new HashMap<String, Integer>();
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public String name() {
		return "feather";
	}

	@Override
	public String bookText() {
		return "Makes you fall like a feather";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		int t = getConfig().getInt(this.name() + ".duration");
		if (list.containsKey(player.getName())) {
			list.put(player.getName(), list.get(player.getName()) + t);
			player.sendMessage("You can now float for "
					+ list.get(player.getName()) + " seconds");
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		} else {
			list.put(player.getName(), t);
			player.sendMessage("You can now float for "
					+ list.get(player.getName()) + " seconds");
			new FeatherRunnable(player).runTaskLater(plugin, 20);
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.FEATHER, 8));
		return s;
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 120);
		return map;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (list.containsKey(e.getPlayer().getName())
				&& !e.getPlayer().isFlying()) {
			if (e.getFrom().getY() > e.getTo().getY()) {
				Location loc = e.getPlayer().getLocation();
				loc.setY(loc.getY() - 1);
				if (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
					Vector v = e.getPlayer().getVelocity();
					v.setY(v.getY() / 1.5);
					e.getPlayer().setVelocity(v);
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (list.containsKey(player.getName())
					&& e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}
		}
	}

	private class FeatherRunnable extends BukkitRunnable {

		Player player;

		FeatherRunnable(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (list.get(player.getName()) > 0) {
				if (list.get(player.getName()) == 5) {
					player.sendMessage(ChatColor.GRAY
							+ "You start to feel heavier...");
				}
				list.put(player.getName(), list.get(player.getName()) - 1);
				new FeatherRunnable(player).runTaskLater(plugin, 20);
			} else {
				list.remove(player.getName());
				player.sendMessage(ChatColor.GRAY + "You feel much heavier");
			}
		}

	}

}
