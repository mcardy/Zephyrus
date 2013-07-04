package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Bang extends Spell {

	public Bang(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "bang";
	}

	@Override
	public String bookText() {
		return "Pushes enemies away from your pointer location a small whirlwind";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player, String[] args) {
		int r = getConfig().getInt(name() + ".radius");
		int p = getConfig().getInt(name() + ".power");
		Location loc = player.getTargetBlock(null, 1000).getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		Location ploc = loc;
		ploc.setY(ploc.getY() + 2);
		ParticleEffects.sendToLocation(ParticleEffects.MOB_SPELL_AMBIENT, loc,
				(float) 1, (float) 2, (float) 1, (float) 0, 200);
		for (Entity e : getNearbyEntities(loc, r)) {
			if (e != player) {
				Vector unitVector = e.getLocation().toVector()
						.subtract(loc.toVector()).normalize();
				unitVector.setY(0.4);
				e.setVelocity(unitVector.multiply(p * 0.4));
			}
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FEATHER, 16));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.AIR;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 6);
		map.put("power", 4);
		return map;
	}

	private Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
						+ (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius
							&& e.getLocation().getBlock() != l.getBlock()) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

}
