package net.lordsofcode.zephyrus.registry;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlantRegistry {
	
	private static Set<Plant> plantSet = new HashSet<Plant>();
	
	public static void init() {
		add(new Plant(Material.MELON_STEM));
		add(new Plant(Material.CROPS));
		add(new Plant(Material.PUMPKIN_STEM));
		add(new Plant(Material.CARROT));
		add(new Plant(Material.POTATO));
		add(new Tree());
	}
	
	public static void add(Plant plant) {
		plantSet.add(plant);
	}
	
	public static boolean grow(Block block) {
		for (Plant plant : plantSet) {
			if (plant.grow(block)) {
				return true;
			}
		}
		return false;
	}
	
}
