package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

	@Override
	public String getName() {
		return "bang";
	}

	@Override
	public String getDesc() {
		return "Pushes enemies away from your pointer location a small whirlwind";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 200;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, String[] args, int power) {
		int r = getConfig().getInt(getName() + ".radius");
		int p = getConfig().getInt(getName() + ".power");
		Location loc = player.getTargetBlock(null, 1000).getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		Location ploc = loc;
		ploc.setY(ploc.getY() + 2);
		Effects.playEffect(ParticleEffects.MOB_SPELL_AMBIENT, loc, 1, 2, 1, 0, 200);
		Effects.playEffect(Sound.EXPLODE, loc);
		for (Entity e : getNearbyEntities(loc, r)) {
			if (e != player) {
				Vector unitVector = e.getLocation().toVector().subtract(loc.toVector()).normalize();
				unitVector.setY(0.4);
				e.setVelocity(unitVector.multiply(p*power * 0.4));
			}
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FEATHER, 16));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
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
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
						radiusEntities.add(e);
					}
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	@Override
	public Type getPrimaryType() {
		return Type.MOVEMENT;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
