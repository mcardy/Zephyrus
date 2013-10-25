package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
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

public class FireShield extends Spell {

	public FireShield() {
		Lang.add("zephyrus.spells.fireshield.warning", "$cYou start to cool down...");
		Lang.add("zephyrus.spells.fireshield.removed", "$7You feel cold again...");
		Lang.add("zephyrus.spells.fireshield.applied", "$cFlames engulf you for TIME seconds");
	}

	@Override
	public String getName() {
		return "fireshield";
	}

	@Override
	public String getDesc() {
		return "Keeps enemies away by burning them if they get too close";
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
		time = time * power;
		Zephyrus.getUser(player).applyEffect(EffectType.FIRESHIELD, time * 20);
		player.sendMessage(Lang.get("zephyrus.spells.fireshield.applied").replace("TIME",
				Zephyrus.getUser(player).getEffectTime(EffectType.FIRESHIELD) + ""));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.LAVA_BUCKET));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		return map;
	}

	@Override
	public Type getPrimaryType() {
		return Type.DESTRUCTION;
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
		player.getLocation().getBlock().setType(Material.FIRE);
		return false;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("firering");
	}

}
