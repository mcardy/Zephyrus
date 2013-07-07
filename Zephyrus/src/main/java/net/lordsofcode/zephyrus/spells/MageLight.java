package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class MageLight extends Spell {

	public MageLight(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "magelight";
	}

	@Override
	public String bookText() {
		return "Makes the world around you glow with the power of the mage!";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 40;
	}

	@Override
	public void run(Player player, String[] args) {
		int time = getConfig().getInt(name() + ".duration");
		playerMap.add(player.getName());
		Location loc = player.getLocation();
		loc.setY(loc.getY() - 1);
		player.sendBlockChange(loc, Material.GLOWSTONE, (byte) 0);
		startDelay(player, time * 20);
	}

	@Override
	public void delayedAction(Player player2) {
		playerMap.remove(player2.getName());
		Player player = Bukkit.getPlayer(player2.getName());
		if (player != null) {
			Location loc = player.getLocation();
			loc.setY(loc.getY() - 1);
			player.sendBlockChange(loc, loc.getBlock().getType(), loc
					.getBlock().getData());
		}
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		return map;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		return true;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GLOWSTONE, 32));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.ILLUSION;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (playerMap.contains(e.getPlayer().getName())) {
			Location loc = e.getTo();
			loc.setY(loc.getY() - 1);
			Material mat = loc.getBlock().getType();
			if (mat != Material.AIR && mat != Material.WATER
					&& mat != Material.STATIONARY_WATER && mat != Material.LAVA
					&& mat != Material.STATIONARY_LAVA) {
				e.getPlayer()
						.sendBlockChange(loc, Material.GLOWSTONE, (byte) 0);
			}
			Location loc2 = e.getFrom();
			loc2.setY(loc2.getY() - 1);
			if (loc.getBlockX() == loc2.getBlockX()
					&& loc.getBlockY() == loc2.getBlockY()
					&& loc.getBlockZ() == loc2.getBlockZ()) {
			} else {
				e.getPlayer().sendBlockChange(loc2, loc2.getBlock().getType(),
						loc2.getBlock().getData());
			}
		}
	}
}