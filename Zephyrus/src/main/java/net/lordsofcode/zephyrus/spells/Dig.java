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

public class Dig extends Spell {

	@Override
	public String getName() {
		return "dig";
	}

	@Override
	public String getDesc() {
		return "Digs for you. Max range of 12 blocks";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, String[] args) {
		List<Integer> list = getIntList(getConfig().getStringList("dig.blacklist"));
		if (this.blockBreak(player) || list.contains(player.getTargetBlock(null, 12).getTypeId())) {
			return false;
		}
		player.getTargetBlock(null, 12).breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_PICKAXE));
		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean sideEffect(Player player, String[] args) {
		List<Integer> list = getIntList(getConfig().getStringList("dig.blacklist"));
		if (this.blockBreak(player) || list.contains(player.getTargetBlock(null, 12).getTypeId())) {
			return false;
		}
		player.getTargetBlock(null, 12).breakNaturally(null);
		return true;
	}

	@Override
	public Type getPrimaryType() {
		return Type.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.EARTH;
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> blocks = new ArrayList<Integer>();
		blocks.add(7);
		map.put("blacklist", blocks);
		return map;
	}

	private List<Integer> getIntList(List<String> list) {
		List<Integer> intList = new ArrayList<Integer>();
		for (String s : list) {
			try {
				intList.add(Integer.parseInt(s));
			} catch (NumberFormatException ex) {
			}
		}
		return intList;
	}

}
