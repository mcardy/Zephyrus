package net.lordsofcode.zephyrus.utils;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Effects {

	public static void playEffect(Effect effect, Location loc) {
		playEffect(effect, loc, 0);
	}
	
	public static void playEffect(Effect effect, Location loc, int data) {
		playEffect(effect, loc, data, 0);
	}
	
	public static void playEffect(Effect effect, Location loc, int data, int radius) {
		loc.getWorld().playEffect(loc, effect, data, radius);
	}
	
	public static void playEffect(Sound sound, Location loc) {
		playEffect(sound, loc, 1);
	}
	
	public static void playEffect(Sound sound, Location loc, float volume) {
		playEffect(sound, loc, volume, 1);
	}
	
	public static void playEffect(Sound sound, Location loc, float volume, float pitch) {
		String soundVolume = Zephyrus.getConfig().contains("Sound-Volume") ? Zephyrus.getConfig().getString(
				"Sound-Volume") : "high";
		float newVolume = getVolume(soundVolume, volume);
		loc.getWorld().playSound(loc, sound, newVolume, pitch);
	}
	
	public static void playEffect(ParticleEffects effect, Block block, int count) {
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		playEffect(effect, loc, count);
	}

	public static void playEffect(ParticleEffects effect, Block block, float speed, int count) {
		Location loc = block.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setY(loc.getY() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		playEffect(effect, loc, speed, count);
	}

	public static void playEffect(ParticleEffects effect, Location loc, int count) {
		playEffect(effect, loc, 0.5F, 0.5F, 0.5F, 0, count);
	}

	public static void playEffect(ParticleEffects effect, Location loc, float speed, int count) {
		playEffect(effect, loc, 0.5F, 0.5F, 0.5F, speed, count);
	}

	public static void playEffect(ParticleEffects effect, Location loc, float offsetX, float offsetY, float offsetZ,
			float speed, int count) {
		String particleAmount = Zephyrus.getConfig().contains("Particle-Effects") ? Zephyrus.getConfig().getString(
				"Particle-Effects") : "all";
		int newCount = getCount(particleAmount, count);
		ParticleEffects.sendToLocation(effect, loc, offsetX, offsetY, offsetZ, speed, newCount);

	}

	private static int getCount(String option, int amount) {
		if (option.equalsIgnoreCase("none")) {
			return 0;
		} else if (option.equalsIgnoreCase("less")) {
			return amount / 4;
		} else {
			return amount;
		}
	}
	
	private static float getVolume(String option, float volume) {
		if (option.equalsIgnoreCase("off")) {
			return 0;
		} else if (option.equalsIgnoreCase("low")) {
			return volume / (float) 4;
		} else {
			return volume;
		}
	}

}
