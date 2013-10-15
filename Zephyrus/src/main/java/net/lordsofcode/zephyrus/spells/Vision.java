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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Vision extends Spell {

	@Override
	public String getName() {
		return "vision";
	}

	@Override
	public String getDesc() {
		return "You can see in the dark now!";
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
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, t * 20, 1));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		// Potion extended Nightvision
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.POTION, 1, (short) 8262));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 30);
		return map;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		int t = getConfig().getInt(getName() + ".duration");
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, t * 2, 1));
		return false;
	}

	@Override
	public Type getPrimaryType() {
		return Type.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.POTION;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

}
