package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Explode extends Spell {

	@Override
	public String getName() {
		return "explode";
	}

	@Override
	public String getDesc() {
		return "Makes a big boom!";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 400;
	}

	@Override
	public boolean run(Player player, String[] args) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 200), player);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled()) {
			return false;
		}
		int r = getConfig().getInt(getName() + ".power");
		player.getWorld().createExplosion(player.getTargetBlock(null, 200).getLocation(), r, false);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.TNT, 64));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("power", 2);
		return map;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		int r = getConfig().getInt(getName() + ".power");
		player.getWorld().createExplosion(player.getTargetBlock(null, 200).getLocation(), r * 2, false);
		return true;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("bang");
	}
	
	@Override
	public EffectType getPrimaryType() {
		return EffectType.EXPLOSION;
	}

	@Override
	public Element getElementType() {
		return Element.EARTH;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

}
