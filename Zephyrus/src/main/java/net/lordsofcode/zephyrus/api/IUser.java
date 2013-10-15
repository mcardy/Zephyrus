package net.lordsofcode.zephyrus.api;

import java.util.Collection;

import org.bukkit.command.CommandSender;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface IUser {

	/**
	 * Whether or not the user has learned the spell
	 * 
	 * @param spell
	 *            The spell to check
	 */
	public boolean isLearned(ISpell spell);

	/**
	 * Whether or not the user has the spell's permission. Will only return true
	 * if the user has permission and op-knowledge is enabled in the config.
	 * 
	 * @param spell
	 *            The spell to check
	 * @return
	 */
	public boolean hasPermission(ISpell spell);

	/**
	 * Whether or not the user has the mana specified or more
	 * 
	 * @param mana
	 *            The amount of mana to check
	 * @return True if the user has the specified amount of mana or more, false
	 *         otherwise.
	 */
	public boolean hasMana(int mana);

	/**
	 * Drains mana from the user
	 * 
	 * @param mana
	 *            The amount to drain
	 * @return The remaining amount of mana
	 */
	public int drainMana(int mana);

	/**
	 * Gets the user's mana
	 */
	public int getMana();

	/**
	 * Levels up the user
	 * 
	 * @return The level the user leveled up to
	 */
	public int levelUp();

	/**
	 * Adds progress towards the next level for this user.
	 * 
	 * @param progress
	 *            The amount of progress to add
	 * @return The player's progress after the specified progress is added
	 */
	public int levelProgress(int progress);

	/**
	 * Gets the player's current level progress
	 */
	public int getLevelProgress();

	/**
	 * Gets the user's current level
	 */
	public int getLevel();

	/**
	 * Loads the user's mana from the config file
	 */
	public void loadMana();

	/**
	 * Dumps the user's mana to a config and removes them form the mana lists
	 */
	public void unLoadMana();

	/**
	 * Resets the user's mana to their level * 100
	 */
	public void reLoadMana();

	/**
	 * Displays the user's mana to the user
	 */
	public void displayMana();

	/**
	 * Displays the user's mana to the specified sender
	 * 
	 * @param sender
	 *            The CommandSender to display the mana to
	 */
	public void displayMana(CommandSender sender);

	/**
	 * Displays the user's level progress to the user
	 */
	public void displayLevel();

	/**
	 * Displays the user's level progress to the specified sender
	 * 
	 * @param sender
	 *            The CommandSender to display the mana to
	 */
	public void displayLevel(CommandSender sender);
	
	/**
	 * Sets weather or not the mana bar should always be displayed
	 * @param b Display or not
	 */
	public void setDisplayMana(boolean b);
	
	/**
	 * Get weather or not the mana bar should always be displayed
	 * @return Display or not
	 */
	public boolean getDisplayMana();
	
	/**
	 * Applies the effect to the player
	 * @param type The effect to add
	 * @param time The duration of the effect
	 */
	public void applyEffect(EffectType type, int time);
	
	/**
	 * Removes the effect type from the player
	 * @param type The type to remove
	 */
	public void removeEffect(EffectType type);
	
	/**
	 * Gets a collection of effects currently active on the player
	 */
	public Collection<IEffect> getCurrentEffects();
		
	/**
	 * Gets the amount of time remaining for the specified type
	 * @param type The type to get
	 * @return 0 if the effect is not applied
	 */
	public int getEffectTime(EffectType type);
	
	/**
	 * Checks if the player has the specified effect
	 * @param type The type to get
	 * @return True if the player has the effect
	 */
	public boolean hasEffect(EffectType type);

}
