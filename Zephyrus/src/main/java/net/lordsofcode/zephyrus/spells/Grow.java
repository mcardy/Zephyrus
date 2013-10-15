package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.registry.PlantRegistry;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Grow extends Spell {

	public Grow() {
		Lang.add("spells.grow.fail", "That block can't be grown...");
	}

	@Override
	public String getName() {
		return "grow";
	}

	@Override
	public String getDesc() {
		return "Grows wheat and Saplings";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 25;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, String[] args) {
		Block target = player.getTargetBlock(null, 6);
		if (PlantRegistry.grow(target)) {
			Location loc = target.getLocation();
			loc.setX(loc.getX() + 0.6);
			loc.setZ(loc.getZ() + 0.6);
			loc.setY(loc.getY() + 0.3);
			Effects.playEffect(ParticleEffects.GREEN_SPARKLE, loc, (float) 0.25, (float) 0.1, (float) 0.25, 100, 20);
			return true;
		}
		Lang.errMsg("spells.grow.fail", player);
		return false;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.SAPLING));
		items.add(new ItemStack(Material.BONE));
		items.add(new ItemStack(Material.SEEDS));
		return items;
	}

	@Override
	public Type getPrimaryType() {
		return Type.CREATION;
	}

	@Override
	public Element getElementType() {
		return Element.EARTH;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
