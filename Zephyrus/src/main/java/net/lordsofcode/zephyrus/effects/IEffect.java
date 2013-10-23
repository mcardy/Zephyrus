package net.lordsofcode.zephyrus.effects;


import org.bukkit.entity.Player;

/**
 * Zephyrus
 * <p>
 * This class represents an effect that can be applied to a player. There must
 * be a constructor that takes player and integer arguments.
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface IEffect {

	/**
	 * The method that is called when the effect is applied
	 */
	public void onApplied(Player player);

	/**
	 * The method that is called when the effect is removed
	 */
	public void onRemoved(Player player);

	/**
	 * Called every tick. Used to do things like damage nearby entities, play
	 * particle effects, etc.
	 */
	public void onTick(Player player);

	/**
	 * Called when 5 seconds remain with the effect
	 */
	public void onWarning(Player player);
	
	/**
	 * The method that is called when the player logs in (used to make sure the
	 * effect is gone)
	 */
	public void onStartup(Player player);
	
	/**
	 * Gets the type of effect that this is
	 * @see EffectType
	 */
	public int getTypeID();
	
	/**
	 * Gets the speed in ticks at which the effect ticks. Set to 0 for no tick.
	 * @return
	 */
	public int getTickTime();
}
