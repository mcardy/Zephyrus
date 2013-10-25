package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.effects.EffectType;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class Feather extends Spell {

	public Feather() {
		Lang.add("spells.feather.applied", "You'll be light as a feather for [TIME] seconds!");
		Lang.add("spells.feather.warning", "$7You start to feel heavier");
		Lang.add("spells.feather.end", "$7You feel much heavier");
	}

	@Override
	public String getName() {
		return "feather";
	}

	@Override
	public String getDesc() {
		return "Makes you fall like a feather";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public boolean run(Player player, String[] args, int power) {
		int t = getConfig().getInt(getName() + ".duration");
		IUser user = Zephyrus.getUser(player);
		user.applyEffect(EffectType.FEATHER, t*20*power);
		player.sendMessage(Lang.get("spells.feather.applied").replace("[TIME]",
				user.getEffectTime(EffectType.FEATHER)/20 + ""));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.FEATHER, 8));
		return s;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 120);
		return map;
	}

	@Override
	public Type getPrimaryType() {
		return Type.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
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
