package net.lordsofcode.zephyrus.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.hooks.PluginHook;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SuperHeat extends Spell {

	public SuperHeat(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "superheat";
	}

	@Override
	public String bookText() {
		return ChatColor.RED
				+ "SuperHeats "
				+ ChatColor.BLACK
				+ "everything you touch! Cooks ores, smelts cobble, and melts sand!"
				+ " It might also work on animals...";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public void run(Player player, String[] args) {
		List<String> list = getConfig().getStringList(this.name() + ".ids");
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (String s : list) {
			String[] ids = s.split("-");
			map.put(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
		}
		Block b = player.getTargetBlock(null, 7);
		if (map.containsKey(b.getTypeId())) {
			int change = map.get(b.getTypeId());
			Location loc = b.getLocation();
			loc.setX(loc.getX() + 0.5);
			loc.setZ(loc.getZ() + 0.5);
			loc.setY(loc.getY() + 0.5);
			if (change < 256) {
				b.setType(Material.getMaterial(change));
			} else {
				b.setType(Material.AIR);
				loc.getWorld().dropItem(loc,
						new ItemStack(Material.getMaterial(change)));
			}
			ParticleEffects.sendToLocation(ParticleEffects.FIRE, loc, 0.6F,
					0.6F, 0.6F, 0, 20);
			return;
		}
		Entity en = getTarget(player);
		if (en != null) {
			en.setFireTicks(100);
			return;
		}
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add("4-1");
		list.add("15-265");
		list.add("14-266");
		list.add("12-20");
		map.put("ids", list);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.COAL, 8));
		i.add(new ItemStack(Material.FURNACE));
		return i;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		List<String> list = getConfig().getStringList(this.name() + ".ids");
		List<Integer> target = new ArrayList<Integer>();
		for (String s : list) {
			String[] str = s.split("-");
			target.add(Integer.parseInt(str[0]));
		}
		Material block = player.getTargetBlock(null, 7).getType();
		if (target.contains(block.getId())) {
			return PluginHook.canBuild(player, player.getTargetBlock(null, 7));
		} else {
			Entity en = getTarget(player);
			if (en != null && en instanceof LivingEntity) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String failMessage() {
		return "You can't superheat that!";
	}

	@Override
	public SpellType type() {
		return SpellType.FIRE;
	}

}
