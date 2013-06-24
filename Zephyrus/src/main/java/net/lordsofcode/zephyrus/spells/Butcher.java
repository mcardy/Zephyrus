package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
		return "Brutally murders all creatures within a radius";
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
		List<Entity> e = player.getNearbyEntities(r, r, r);
		for (Entity en : e) {
			if (en instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) en;
				if (entity instanceof Player) {
					entity.damage(10, player);
				} else if (entity instanceof EnderDragon) {
					entity.damage(20, player);
				} else {
					entity.damage(50, player);
				}
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

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		player.damage(rand.nextInt(4));
		return false;
	}

	@Override
	public Set<SpellType> types() {
		Set<SpellType> t = types();
		t.add(SpellType.AIR);
		t.add(SpellType.RESTORE);
		t.add(SpellType.FIRE);
		t.add(SpellType.TELEPORTATION);
		return t;
	}

	@Override
	public void comboSpell(Player player, String[] args, SpellType type, int level) {
		int r = getConfig().getInt(this.name() + ".radius");
		List<Entity> e = player.getNearbyEntities(r, r, r);
		switch (type) {
		case AIR:
			for (Entity en : e) {
				if (en instanceof LivingEntity) {
					en.setVelocity(new Vector(0, level, 0));
				}
			}
		case FIRE: 
			for (Entity en : e) {
				if (en instanceof LivingEntity) {
					en.setFireTicks(level * 8);
				}
			}
		case RESTORE:
			for (Entity en : e) {
				if (en instanceof LivingEntity) {
					LivingEntity ent = (LivingEntity) en;
					ent.setHealth(ent.getMaxHealth());
				}
			}
		case TELEPORTATION:
			for (Entity en : e) {
				if (en instanceof LivingEntity) {
					Location loc = en.getLocation();
					loc.setY(loc.getY() + level * 4);
					en.teleport(loc);
				}
			}
		default:
			break;
		}
	}

}
