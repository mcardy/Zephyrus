package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
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

public class FlameStep extends Spell {

	public FlameStep() {
		Lang.add("spells.flamestep.applied", "You will now burn everything in your path for [TIME] seconds");
		Lang.add("spells.flamestep.warning", "$7You start to cool down...");
		Lang.add("spells.flamestep.end", "$7You are cold again...");
	}

	@Override
	public String getName() {
		return "flamestep";
	}

	@Override
	public String getDesc() {
		return "Once cast, everything around you will burn!";
	}

	@Override
	public int reqLevel() {
		return 10;
	}

	@Override
	public int manaCost() {
		return 550;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int duration = getConfig().getInt("flamestep.duration");
		IUser user = Zephyrus.getUser(player);
		user.applyEffect(EffectType.FLAMESTEP, duration*20);
		player.sendMessage(Lang.get("spells.flamestep.applied").replace("[TIME]", user.getEffectTime(EffectType.FLAMESTEP) + ""));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.LAVA_BUCKET));
		s.add(new ItemStack(Material.BLAZE_POWDER, 16));
		s.add(new ItemStack(Material.TORCH, 32));
		s.add(new ItemStack(Material.FIREBALL, 8));
		return s;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 4);
		map.put("duration", 15);
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
		return Spell.forName("fireshielf");
	}

}
