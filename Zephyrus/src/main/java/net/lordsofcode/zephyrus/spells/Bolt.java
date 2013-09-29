package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class Bolt extends Spell {

	@Override
	public String getName() {
		return "bolt";
	}

	@Override
	public String getDesc() {
		return "Strikes lightning where you point!";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public boolean run(Player player, String[] args) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 1000), player);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled()) {
			return false;
		}
		Location loc = player.getTargetBlock(null, 1000).getLocation();
		player.getWorld().strikeLightning(loc);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.EMERALD));
		items.add(new ItemStack(Material.FLINT_AND_STEEL));
		return items;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		if (rand.nextInt(4) == 1) {
			player.getWorld().strikeLightning(player.getLocation());
			return true;
		}
		return false;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

}
