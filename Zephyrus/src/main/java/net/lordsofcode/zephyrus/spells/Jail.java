package net.lordsofcode.zephyrus.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.BlockData;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Jail extends Spell {

	Set<Map<Location, BlockData>> lmap;

	public Jail() {
		lmap = new HashSet<Map<Location, BlockData>>();
		Lang.add("spells.jail.break", "$7You cannot break jail blocks!");
		Lang.add("spells.jail.fail", "Theres no one to lock up!");
	}

	@Override
	public String getName() {
		return "jail";
	}

	@Override
	public String getDesc() {
		return "Locks your enemy in a jail and throws away the key! They are stuck for a while but the bars then rust and dissppear.";
	}

	@Override
	public int reqLevel() {
		return 10;
	}

	@Override
	public int manaCost() {
		return 500;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int i = getConfig().getInt(getName() + ".duration");
		Entity target = getTarget(player);
		if (target == null) {
			Lang.errMsg("spells.jail.fail", player);
			return false;
		}
		Location loc = target.getLocation();
		Map<Location, BlockData> map = new HashMap<Location, BlockData>();
		List<Block> bars = new ArrayList<Block>();
		List<Block> blocks = new ArrayList<Block>();
		for (int x = loc.getBlockX() - 1; x < loc.getBlockX() + 2; x++) {
			for (int y = loc.getBlockY() - 1; y < loc.getBlockY() + 3; y++) {
				for (int z = loc.getBlockZ() - 1; z < loc.getBlockZ() + 2; z++) {
					if (x == loc.getBlockX() && z == loc.getBlockZ() && y < loc.getBlockY() + 2
							&& y != loc.getBlockY() - 1) {
					} else {
						BlockBreakEvent e = new BlockBreakEvent(new Location(player.getWorld(), x, y, z).getBlock(),
								player);
						Bukkit.getPluginManager().callEvent(e);
						if (e.isCancelled()) {
							return false;
						}
						if (y == loc.getBlockY() - 1 || y == loc.getBlockY() + 2) {
							Location bloc = new Location(loc.getWorld(), x, y, z);
							Block b = bloc.getBlock();
							if (!(b.getType() == Material.CHEST) && !(b.getType() == Material.FURNACE)) {
								map.put(bloc, new BlockData(b));
								blocks.add(b);
							}
						} else {
							Location bloc = new Location(loc.getWorld(), x, y, z);
							Block b = bloc.getBlock();
							if (!(b.getType() == Material.CHEST) && !(b.getType() == Material.FURNACE)) {
								map.put(bloc, new BlockData(b));
								bars.add(b);
							}
						}
					}
				}
			}
		}
		for (Block b : bars) {
			b.setType(Material.IRON_FENCE);
			b.setMetadata("jailblock", new FixedMetadataValue(Zephyrus.getPlugin(), true));
		}
		for (Block b : blocks) {
			b.setType(Material.IRON_BLOCK);
			b.setMetadata("jailblock", new FixedMetadataValue(Zephyrus.getPlugin(), true));
		}
		this.lmap.add(map);
		new Reset(map).runTaskLater(Zephyrus.getPlugin(), i * 20);
		Effects.playEffect(Sound.ANVIL_LAND, loc);
		return true;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 20);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.IRON_FENCE, 64));
		return s;
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

	private class Reset extends BukkitRunnable {

		Map<Location, BlockData> map;

		Reset(Map<Location, BlockData> map2) {
			this.map = map2;
		}

		@Override
		public void run() {
			for (Location loc : map.keySet()) {
				Block b = loc.getWorld().getBlockAt(loc);
				BlockData mat = map.get(loc);
				b.setType(mat.getType());
				b.setData(mat.getData());
			}
		}
	}

	@EventHandler
	public void onBreakJail(BlockBreakEvent e) {
		if (e.getPlayer() != null) {
			Block b = e.getBlock();
			if (b.getType() == Material.IRON_FENCE || b.getType() == Material.IRON_BLOCK) {
				if (b.hasMetadata("jailblock")) {
					e.setCancelled(true);
					Lang.msg("spells.jail.break", e.getPlayer());
				}
			}
		}
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
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
