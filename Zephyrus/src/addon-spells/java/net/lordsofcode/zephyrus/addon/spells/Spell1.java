package net.lordsofcode.zephyrus.addon.spells;
import java.util.HashMap;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Zephyrus - Example
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 *          These spells show how classes can be loaded form jar files in the
 *          Spells folder by Zephyrus
 * 
 */

public class Spell1 extends Spell {

	/**
	 * The name of the spell that will be editable inside of the config
	 */
	@Override
	public String getName() {
		return "example1";
	}

	/**
	 * The description of the spell that can be found inside the spell's
	 * spelltome
	 */
	@Override
	public String getDesc() {
		return "May hurt you for a second or two but you should feel better afterwards";
	}

	/**
	 * A list of configurations that are saved into the config. You can leave
	 * this null if your spell does not have any configurations.
	 */
	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 20);
		return map;
	}

	/**
	 * The default required level to learn the spell
	 */
	@Override
	public int reqLevel() {
		return 2;
	}

	/**
	 * The default mana cost of the spell
	 */
	@Override
	public int manaCost() {
		return 10;
	}

	/**
	 * The items that are used to learn the spell
	 */
	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> set = new HashSet<ItemStack>();
		set.add(new ItemStack(Material.APPLE));
		set.add(new ItemStack(Material.CACTUS));
		return set;
	}

	/**
	 * The primary type of the spell. Currently has no use but will be used for
	 * combo spells and Wizard Paths.
	 */
	@Override
	public Type getPrimaryType() {
		return Type.RESTORE;
	}

	/**
	 * The element of the spell. Currently has no use but will be used for combo
	 * spells and Wizard Paths.
	 */
	@Override
	public Element getElementType() {
		return Element.POTION;
	}

	/**
	 * Gets the priority of the spell. Currently has no use but will be used for
	 * combo spells and Wizard Paths.
	 */
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	/**
	 * This is the method that is called when the spell is cast
	 */
	@Override
	public boolean run(Player player, String[] args) {
		// This gets the potion effect from the config. This field was declared
		// above in the getConfiguration() method.
		int potionEffectDuration = this.getConfig().getInt("example.duration");
		// Adds the poison potion effect with the configurable time and an
		// amplifier of 1 (Poison I)
		player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
				potionEffectDuration, 1));
		// This starts the delayed action task which calls the delayedAction
		// method defined below
		// We want the delayed task to run after the poison potion effect wares
		// out
		startDelay(player, potionEffectDuration);
		// Make sure to return true to drain mana
		return true;
	}

	/**
	 * A method that is called randomly when the spell is cast. It can cause a
	 * negative or positive effect. Since this spell does not need a side
	 * effect, this method will be left blank with return false.
	 * 
	 * @return True to cancel the spell.run() method and false to run the spell
	 *         normally
	 */
	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

	/**
	 * An optional method that allows you to setup a delayed action. In this
	 * case it heals the player.
	 */
	@Override
	public void delayedAction(Player player) {
		player.setHealth(player.getMaxHealth());
	}

}
