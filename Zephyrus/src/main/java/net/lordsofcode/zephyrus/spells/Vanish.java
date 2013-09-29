package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Vanish extends Spell {

	public Vanish() {
		Lang.add("spells.vanish.applied", "$7You have dissappeared");
	}

	@Override
	public String getName() {
		return "vanish";
	}

	@Override
	public String getDesc() {
		return "Makes you dissappear!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 96;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int t = getConfig().getInt(getName() + ".duration");
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, t * 20, 1));
		Lang.msg("spells.vanish.applied", player);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		// Potion extended Invisibility
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.POTION, 1, (short) 8270));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 30);
		return map;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.POTION;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
	
	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("vision");
	}

}
