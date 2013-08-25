package net.lordsofcode.zephyrus.api;

import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ISpell extends Listener {

	/**
	 * This method is for defining the internal name of the spell
	 * <p>
	 * Should be the default name of the spell editable inside of a config and
	 * it should also be lowercase
	 * 
	 * @return A string with the internal name of the spell
	 */
	public String getName();

	/**
	 * Gets the spell's display name. Usually used to get the name from a config
	 * file. Should be used for all player-related spell messages.
	 * 
	 * @return The spell's display name
	 */
	public String getDisplayName();

	/**
	 * Gets the default description of the spell. The description appears inside
	 * of the Spell Tome for the spell
	 * 
	 * @return The default description of the spell
	 */
	public String getDesc();

	/**
	 * Gets the display description of the spell. Usually used to get the
	 * description from a config file.
	 * 
	 * @return The spell's display description
	 */
	public String getDisplayDesc();

	/**
	 * Gets the spell's required spell. Use Spell.forName() to get the spell by
	 * its internal name
	 * 
	 * @return Null if there is no required spell
	 */
	public ISpell getRequiredSpell();

	/**
	 * Gets the configurations for the spell. These are written into the config
	 * file when using
	 * 
	 * @return Null if there are none.
	 */
	public Map<String, Object> getConfiguration();

	/**
	 * Gets the default reqired level to learn the spell. This level multiplied
	 * by 100 should be higher than the mana cost multiplied by 5.
	 * 
	 * @return An int with the required level.
	 */
	public int reqLevel();

	/**
	 * Usually used to get the required level from a config.
	 * 
	 * @return The final level
	 */
	public int getReqLevel();

	/**
	 * Gets the default mana cost of the spell.
	 * 
	 * @return An int with the default mana cost
	 */
	public int manaCost();

	/**
	 * Gets the mana cost usually from the config (though may be different with
	 * external spells)
	 * 
	 * @return
	 */
	public int getManaCost();

	/**
	 * Whether or not the spell can be bound to a wand or any other similar
	 * object.
	 * 
	 * @return False if the spell can't be bound, true otherwise.
	 */
	public boolean canBind();

	/**
	 * Gets the default item stacks for the spell.
	 * 
	 * @return
	 */
	public Set<ItemStack> items();

	/**
	 * Gets the items required to craft the spell. Used for getting items from
	 * config
	 * 
	 * @return
	 */
	public Set<ItemStack> getItems();

	/**
	 * Gets the primary EffectType of the spell.
	 * 
	 * @return
	 */
	public EffectType getPrimaryType();

	/**
	 * Gets the Element of the spell
	 * 
	 * @return
	 */
	public Element getElementType();

	/**
	 * Gets the Priority of the spell. The priority should be high if the spell
	 * has high compatibility with other spells in combo spells, medium if the
	 * spell has medium compatibility and low if the spell has no combo spell
	 * effect. 
	 * 
	 * @return
	 */
	public Priority getPriority();

	/**
	 * Whether or not the spell is enabled. Usually used for configuration
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * Gets the exp reward for casting the spell
	 * 
	 * @return
	 */
	public int getExp();

	/**
	 * The main method of the spell
	 * 
	 * @param player
	 *            The player who cast the spell
	 * @param args
	 *            The arguments used to cast the spell
	 * @return True to drain mana, false otherwise.
	 */
	public boolean run(Player player, String[] args);

	/**
	 * The side effect of the spell. Called randomly when the spell is cast
	 * 
	 * @param player
	 *            The player who cast the spell
	 * @param args
	 *            The arguments used to cast the spell
	 * @return True to cancel the casting of the spell, false otherwise.
	 */
	public boolean sideEffect(Player player, String[] args);

	/**
	 * TODO Implement combo spells
	 * NOT IMPLEMENTED YET
	 * <p>
	 * The spell's combo spell effect.
	 * 
	 * @param player
	 *            The player who cast the spell
	 * @param args
	 *            The arguments used to cast the spell
	 * @param type
	 *            The type of spell the other spell is
	 * @param element
	 *            The element the other spell is
	 * @param power
	 *            The power of the spell
	 * @return
	 */
	public boolean comboSpell(Player player, String[] args, EffectType type,
			Element element, int power);

	/**
	 * The method called when the plugin is disabled. Used to remove potion
	 * effects, reset blocks, etc.
	 */
	public void onDisable();

}
