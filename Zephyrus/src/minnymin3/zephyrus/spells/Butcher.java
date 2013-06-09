package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Butcher extends Spell {

	public Butcher(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "butcher";
	}

	@Override
	public String bookText() {
		int r = getConfig().getInt(this.name() + ".radius");
		return "Brutally murders all creatures within a " + r + " block radius!";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 40;
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public void run(Player player, String[] args) {
		int r = getConfig().getInt(this.name() + ".radius");
		LivingEntity[] e = getNearbyEntities(player.getLocation(), r);
		for (LivingEntity entity : e) {
			if (entity instanceof Player) {
				entity.damage(10, player);
			} else if (entity instanceof EnderDragon) {
				entity.damage(20, player);
			} else {
				entity.damage(50, player);
			}
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_SWORD));
		return i;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 5);
		return map;
	}
	
	public static LivingEntity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<LivingEntity> radiusEntities = new HashSet<LivingEntity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
						+ (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius
							&& e.getLocation().getBlock() != l.getBlock()) {
						if (e instanceof LivingEntity) {
							radiusEntities.add((LivingEntity) e);
						}
					}
				}
			}
		}
		return radiusEntities.toArray(new LivingEntity[radiusEntities.size()]);
	}

}
