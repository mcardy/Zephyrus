package net.lordsofcode.zephyrus.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SuperHeat extends Spell {

	public SuperHeat() {
		Lang.add("spells.superheat.fail", "That can't be superheated!");
	}

	@Override
	public String getName() {
		return "superheat";
	}

	@Override
	public String getDesc() {
		return ChatColor.RED + "SuperHeats " + ChatColor.BLACK
				+ "everything you touch! Cooks ores, smelts cobble, and melts sand!"
				+ " It might also work on animals...";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 32;
	}

	@Override
	public boolean run(Player player, String[] args) {
		List<String> list = getConfig().getStringList(getName() + ".ids");
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (String s : list) {
			String[] ids = s.split("-");
			map.put(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
		}
		Block b = player.getTargetBlock(null, 7);
		Material bm = b.getType();
		if (map.containsKey(bm.getId())) {
			BlockBreakEvent e = new BlockBreakEvent(b, player);
			Bukkit.getPluginManager().callEvent(e);
			if (e.isCancelled()) {
				return false;
			}
			int change = map.get(b.getTypeId());
			Location loc = b.getLocation();
			loc.setX(loc.getX() + 0.5);
			loc.setZ(loc.getZ() + 0.5);
			loc.setY(loc.getY() + 0.5);
			if (change < 256) {
				b.setType(Material.getMaterial(change));
			} else {
				b.setType(Material.AIR);
				loc.getWorld().dropItem(loc, new ItemStack(Material.getMaterial(change)));
			}
			Effects.playEffect(ParticleEffects.FIRE, loc, 0.6F, 0.6F, 0.6F, 0, 20);
			return true;
		} else {
			Entity en = getTarget(player);
			if (en != null && en instanceof LivingEntity) {
				en.setFireTicks(100);
				return true;
			} else {
				Lang.errMsg("spells.superheat.fail", player);
				return false;
			}
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
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
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.COAL, 8));
		i.add(new ItemStack(Material.FURNACE));
		return i;
	}

	@Override
	public Type getPrimaryType() {
		return Type.WORLD;
	}

	@Override
	public Element getElementType() {
		return Element.FIRE;
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
