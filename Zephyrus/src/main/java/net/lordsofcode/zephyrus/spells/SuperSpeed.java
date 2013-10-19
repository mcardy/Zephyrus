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

public class SuperSpeed extends Spell {

	public SuperSpeed() {
		Lang.add("spells.superspeed.applied", "You'll run fast as lightning for [TIME] seconds!");
		Lang.add("spells.superspeed.warning", "$7You start to feel slower");
		Lang.add("spells.superspeed.end", "$7You feel much slower now");
	}
	
	@Override
	public String getName() {
		return "superspeed";
	}

	@Override
	public String getDesc() {
		return "Makes you go super fast for a certain amount of time";
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		return map;
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 320;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> set = new HashSet<ItemStack>();
		set.add(new ItemStack(Material.SUGAR, 64));
		return set;
	}

	@Override
	public Type getPrimaryType() {
		return Type.BUFF;
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
	public boolean run(Player player, String[] args) {
		int time = getConfig().getInt(getName() + ".duration");
		IUser user = Zephyrus.getUser(player);
		user.applyEffect(EffectType.SUPERSPEED, time);
		player.sendMessage(Lang.get("spells.superspeed.applied").replace("[TIME]",
				user.getEffectTime(EffectType.SUPERSPEED)/20 + ""));
		return true;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
