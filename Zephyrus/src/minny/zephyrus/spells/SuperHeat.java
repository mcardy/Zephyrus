package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class SuperHeat extends Spell {

	public SuperHeat(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "superheat";
	}

	@Override
	public String bookText() {
		return ChatColor.RED
				+ "SuperHeats "
				+ ChatColor.BLACK
				+ "everything you touch! Cooks ores, smelts cobble, and melts sand!"
				+ " It might also work on animals...";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 1;
	}

	@Override
	public void run(Player player) {
		try {
			Material block = player.getTargetBlock(null, 7).getType();
			if (block == Material.COBBLESTONE) {
				player.getTargetBlock(null, 7).setType(Material.STONE);
				player.getWorld().playSound(player.getLocation(),
						Sound.FIRE_IGNITE, 1, 1);
				Location loc = player.getTargetBlock(null, 7).getLocation();
				loc.setX(loc.getX() + 0.5);
				loc.setZ(loc.getZ() + 0.5);
				loc.setY(loc.getY() + 0.5);
				ParticleEffects.sendToLocation(ParticleEffects.FLAME, loc, 1,
						1, 1, 0, 10);
			} else if (block == Material.SAND) {
				player.getTargetBlock(null, 7).setType(Material.GLASS);
				player.getWorld().playSound(player.getLocation(),
						Sound.FIRE_IGNITE, 1, 1);
				Location loc = player.getTargetBlock(null, 7).getLocation();
				loc.setX(loc.getX() + 0.5);
				loc.setZ(loc.getZ() + 0.5);
				loc.setY(loc.getY() + 0.5);
				ParticleEffects.sendToLocation(ParticleEffects.FLAME, loc, 1,
						1, 1, 0, 10);
			} else if (block == Material.IRON_ORE) {
				Block b = player.getTargetBlock(null, 7);
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(),
						new ItemStack(Material.IRON_INGOT));
				player.getWorld().playSound(player.getLocation(),
						Sound.FIRE_IGNITE, 1, 1);
				Location loc = player.getTargetBlock(null, 7).getLocation();
				loc.setX(loc.getX() + 0.5);
				loc.setZ(loc.getZ() + 0.5);
				loc.setY(loc.getY() + 0.5);
				ParticleEffects.sendToLocation(ParticleEffects.FLAME, loc, 1,
						1, 1, 0, 10);
			} else if (block == Material.GOLD_ORE) {
				Block b = player.getTargetBlock(null, 7);
				b.setType(Material.AIR);
				b.getWorld().dropItem(b.getLocation(),
						new ItemStack(Material.GOLD_INGOT));
				player.getWorld().playSound(player.getLocation(),
						Sound.FIRE_IGNITE, 1, 1);
				Location loc = player.getTargetBlock(null, 7).getLocation();
				loc.setX(loc.getX() + 0.5);
				loc.setZ(loc.getZ() + 0.5);
				loc.setY(loc.getY() + 0.5);
				ParticleEffects.sendToLocation(ParticleEffects.FLAME, loc, 1,
						1, 1, 0, 30);
			} else {
				LivingEntity en = (LivingEntity) getTarget(player);
				player.getWorld().playSound(player.getLocation(),
						Sound.FIRE_IGNITE, 1, 1);
				en.setFireTicks(160);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.COAL, 8));
		i.add(new ItemStack(Material.FURNACE));
		return i;
	}

	@Override
	public boolean canRun(Player player) {
		if (hook.worldGuard()) {
			if (!hook.wg.canBuild(player, player.getTargetBlock(null, 7))) {
				return false;
			}
		}
		Material block = player.getTargetBlock(null, 7).getType();
		if (block == Material.COBBLESTONE || block == Material.SAND
				|| block == Material.IRON_ORE || block == Material.GOLD_ORE) {
			return true;
		} else if (getTarget(player) != null) {
			if (getTarget(player) instanceof LivingEntity) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String failMessage() {
		return "You can't superheat that!";
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
