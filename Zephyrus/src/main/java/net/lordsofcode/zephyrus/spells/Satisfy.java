package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
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

public class Satisfy extends Spell {

	public Satisfy() {
		Lang.add("spells.satisfy.applied", "$aYou feel completely satisfied");
	}

	@Override
	public String getName() {
		return "satisfy";
	}

	@Override
	public String getDesc() {
		return "You will be completely satisfied!";
	}

	@Override
	public int reqLevel() {
		return 3;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public boolean run(Player player, String[] args) {
		player.setFoodLevel(20);
		player.setSaturation(20);
		Lang.msg("spells.satisfy.applied", player);
		return true;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("feed");
	}
	
	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.COOKED_BEEF, 8));
		s.add(new ItemStack(Material.COOKED_CHICKEN, 8));
		s.add(new ItemStack(Material.GOLDEN_APPLE));
		s.add(new ItemStack(Material.GOLDEN_CARROT));
		return s;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.RESTORE;
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
