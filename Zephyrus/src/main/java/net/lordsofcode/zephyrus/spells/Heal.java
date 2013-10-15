package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
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

public class Heal extends Spell {

	@Override
	public String getName() {
		return "heal";
	}

	@Override
	public String getDesc() {
		return "Can't you guess what this does by the title?";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 15;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int a = getConfig().getInt(getName() + ".amount");
		if (player.getFoodLevel() + a > 20) {
			player.setHealth(20);
		} else {
			player.setHealth(player.getFoodLevel() + a);
		}
		return true;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 4);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.GOLDEN_APPLE));
		return items;
	}

	@Override
	public Type getPrimaryType() {
		return Type.RESTORE;
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
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
