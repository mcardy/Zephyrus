package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Punch extends Spell {

	public Punch(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "punch";
	}

	@Override
	public String bookText() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 2;
	}

	@Override
	public void run(Player player) {
		LivingEntity c = (LivingEntity) getTarget(player);
		c.damage(2);
	}

	@Override
	public boolean canRun(Player player) {
		Entity e = getTarget(player);
		if (e != null && e instanceof LivingEntity) {
			return true;
		}
		return false;
	}

	@Override
	public String failMessage() {
		return "Nothing to punch!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		// Potion of instant damage 1
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.IRON_SWORD));
		i.add(new ItemStack(Material.POTION, 1, (short) 8204));
		return i;
	}

	public Entity getTarget(Player player) {

		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		Entity target = null;
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++) {
					for (int z = -acc; z < acc; z++) {
						for (int y = -acc; y < acc; y++) {
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) {
								return target = entity;
							}
						}
					}
				}
			}
		}
		return target;
	}

}
