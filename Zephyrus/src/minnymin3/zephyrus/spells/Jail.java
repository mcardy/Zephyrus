package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;
import minnymin3.zephyrus.utils.BlockData;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @version 1.0.0
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Jail extends Spell {

	Set<Map<Location, BlockData>> lmap;

	public Jail(Zephyrus plugin) {
		super(plugin);
		lmap = new HashSet<Map<Location, BlockData>>();
	}

	@Override
	public String name() {
		return "jail";
	}

	@Override
	public String bookText() {
		return "Locks your enemy in a jail and throws away the key! They are stuck for a while but the bars then rust and dissppear.";
	}

	@Override
	public int reqLevel() {
		return 10;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player, String[] args) {
		int i = getConfig().getInt(this.name() + ".duration");
		Entity target = getTarget(player);
		Location loc = target.getLocation();
		Map<Location, BlockData> map = new HashMap<Location, BlockData>();
		for (int x = loc.getBlockX() - 1; x < loc.getBlockX() + 2; x++) {
			for (int y = loc.getBlockY() - 1; y < loc.getBlockY() + 3; y++) {
				for (int z = loc.getBlockZ() - 1; z < loc.getBlockZ() + 2; z++) {
					if (x == loc.getBlockX() && z == loc.getBlockZ()
							&& y < loc.getBlockY() + 2 && y != loc.getBlockY()-1) {
					} else {
						if (y == loc.getBlockY() - 1
								|| y == loc.getBlockY() + 2) {
							Location bloc = new Location(loc.getWorld(), x,
									y, z);
							Block b = bloc.getBlock();
							map.put(bloc,
									new BlockData(b.getType(), b.getData()));
							b.setType(Material.IRON_BLOCK);
							b.setData((byte) 12);
						} else {
							Location bloc = new Location(loc.getWorld(), x, y,
									z);
							Block b = bloc.getBlock();
							map.put(bloc,
									new BlockData(b.getType(), b.getData()));
							b.setType(Material.IRON_FENCE);
							b.setData((byte) 12);
						}
					}
				}
			}
		}
		this.lmap.add(map);
		new Reset(map).runTaskLater(plugin, i * 20);
		player.sendMessage("Your target has now been locked up");
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		Entity tar = getTarget(player);
		return tar != null && PluginHook.canBuild(player, tar.getLocation());
	}

	@Override
	public String failMessage() {
		return "No one to jail!";
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 20);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

	@Override
	public void onDisable() {
		Iterator<Map<Location, BlockData>> it = lmap.iterator();
		while (it.hasNext()) {
			Map<Location, BlockData> map = it.next();
			for (Location loc : map.keySet()) {
				Block b = loc.getWorld().getBlockAt(loc);
				BlockData mat = map.get(loc);
				b.setType(mat.getType());
				b.setData(mat.getData());
			}
		}
	}

	public Entity getTarget(Player player) {

		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		Entity target = null;
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++) {
					for (int z = -acc; z < acc; z++) {
						for (int y = -acc; y < acc; y++) {
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) {
								return target = entity;
							}
						}
					}
				}
			}
		}
		return target;
	}

	private class Reset extends BukkitRunnable {

		Map<Location, BlockData> map;

		Reset(Map<Location, BlockData> map2) {
			this.map = map2;
		}

		public void run() {
			for (Location loc : map.keySet()) {
				Block b = loc.getWorld().getBlockAt(loc);
				BlockData mat = map.get(loc);
				b.setType(mat.getType());
				b.setData(mat.getData());
			}
		}
	}

}
