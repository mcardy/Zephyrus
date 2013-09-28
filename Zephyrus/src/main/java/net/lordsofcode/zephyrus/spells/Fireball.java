package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Fireball extends Spell {

	@Override
	public String getName() {
		return "fireball";
	}

	@Override
	public String getDesc() {
		return "Fires a fireball on cast!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public boolean run(Player player, String[] args) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 1000), player);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled()) {
			return false;
		}
		player.launchProjectile(SmallFireball.class);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.BLAZE_POWDER));
		i.add(new ItemStack(Material.COAL));
		return i;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		player.setFireTicks(rand.nextInt(40));
		return false;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.FIRE;
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

}
