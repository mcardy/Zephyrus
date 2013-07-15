package net.lordsofcode.zephyrus.api;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellTypes {

	public enum Element {
		FIRE, WATER, ICE, EARTH, AIR, LIGHT, ENDER, WITHER, SHADOW, MAGIC, POTION, GENERIC;
	}
	
	public enum EffectType {
		EXPLOSION, ATTACK, MOVEMENT, TELEPORTATION, DESTRUCTION, CREATION, BUFF, RESTORE, ILLUSION, MONITOR, WORLD;
	}
	
	public enum Priority {
		HIGH, MEDIUM, LOW;	
	}
	
}
