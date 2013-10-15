package net.lordsofcode.zephyrus.example;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.spells.Spell;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus Example Addon
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 *          This spell will dig the block that the player is looking at only if
 *          they are allowed to and no other plugin cancels the breaking of the
 *          block
 * 
 */

public class ExampleSpell extends Spell {

	@Override
	public String getName() {
		return "break";
	}

	@Override
	public String getDesc() {
		return "Breaks the block in front of you!";
	}

	@Override
	public Map<String, Object> getConfiguration() {
		// No configurations necessary for this spell
		return null;
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public Set<ItemStack> items() {
		// This is how a new hash set with the type of itemstack is made
		Set<ItemStack> set = new HashSet<ItemStack>();
		// This is how an itemstack is added to the set
		set.add(new ItemStack(Material.IRON_PICKAXE));
		return set;
	}

	@Override
	public Type getPrimaryType() {
		return Type.DESTRUCTION;
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
	public boolean run(Player player, String[] args) {
		// We want to check if the block can be broken
		if (!this.blockBreak(player)) {
			// If the block can be broken then break the block
			player.getTargetBlock(null, 100).breakNaturally(
					new ItemStack(Material.IRON_PICKAXE));
			// Return true to drain mana
			return true;
		} else {
			// If the block cannot be broken then we do not want to drain mana
			return false;
		}
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		// We do not need a side effect for this spell so we return false here
		// to let the spell run normallys
		return false;
	}

}
