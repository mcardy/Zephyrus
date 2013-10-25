package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
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

public class Shield extends Spell {

	public Shield() {
		Lang.add("zephyrus.spells.shield.warning", "$7Your shield starts to dissappear...");
		Lang.add("zephyrus.spells.shield.removed", "$7You are no longer shielded...");
		Lang.add("zephyrus.spells.shield.applied", "$You are now protected for TIME seconds");
	}
	
	@Override
	public String getName() {
		return "shield";
	}

	@Override
	public String getDesc() {
		return "Keeps enemies away by zapping them if they get too close";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 400;
	}

	@Override
	public boolean run(Player player, String[] args, int power) {
		int time = getConfig().getInt(getName() + ".duration");
		Zephyrus.getUser(player).applyEffect(EffectType.SHIELD, time*20*power);
		player.sendMessage(Lang.get("zephyrus.spells.shield.applied").replace("TIME",
				Zephyrus.getUser(player).getEffectTime(EffectType.SHIELD) + ""));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_SWORD));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		map.put("damage", 1);
		return map;
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
		return Priority.MEDIUM;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
