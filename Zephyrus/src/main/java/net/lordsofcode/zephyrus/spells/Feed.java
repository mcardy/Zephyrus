package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Feed extends Spell {

	public Feed() {
		Lang.add("spells.feed.applied", "You feel slightly less hungry");
		Lang.add("spells.feed.side", "You feel slightly more hungry...");
	}

	@Override
	public String getName() {
		return "feed";
	}

	@Override
	public String getDesc() {
		return "You hungry? Not anymore!";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int a = getConfig().getInt(getName() + ".amount");
		if (player.getFoodLevel() + a > 20) {
			player.setFoodLevel(20);
		} else {
			player.setFoodLevel(player.getFoodLevel() + a);
		}
		Lang.msg("spells.feed.applied", player);
		Effects.playEffect(Sound.EAT, player.getLocation());
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
		items.add(new ItemStack(Material.COOKED_BEEF));
		items.add(new ItemStack(Material.COOKED_CHICKEN));
		items.add(new ItemStack(Material.COOKED_FISH));
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
		if (player.getFoodLevel() - 2 < 0) {
			player.setFoodLevel(0);
		} else {
			player.setFoodLevel(player.getFoodLevel() - 2);
		}
		Lang.msg("spells.feed.side", player);
		return true;
	}

}
