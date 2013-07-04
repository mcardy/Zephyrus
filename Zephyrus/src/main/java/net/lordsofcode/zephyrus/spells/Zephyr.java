package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Zephyr extends Spell {

	public Zephyr(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "zephyr";
	}

	@Override
	public String bookText() {
		return "Pushes enemies away with a small whirlwind";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player, String[] args) {
		int time = getConfig().getInt(name() + ".duration");
		boolean b = getConfig().getBoolean(name() + ".block-all");
		playerMap.add(player.getName());
		new Run(player, b).runTaskTimer(Zephyrus.getInstance(), (long) 0.1,
				(long) 0.1);
		startDelay(player, time * 20);
	}

	@Override
	public void delayedAction(Player player) {
		playerMap.remove(player.getName());
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FEATHER, 8));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.AIR;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		map.put("block-all", true);
		return map;
	}

	private class Run extends BukkitRunnable {

		Player player;
		boolean b;

		Run(Player player, boolean b) {
			this.player = player;
			this.b = b;
		}

		public void run() {
			Player p = Bukkit.getPlayer(player.getName());
			if (p != null && playerMap.contains(player.getName())) {
				Location loc = p.getLocation();
				loc.setY(player.getLocation().getY() + 1);
				ParticleEffects.sendToLocation(
						ParticleEffects.MOB_SPELL_AMBIENT, loc, (float) 0.5,
						(float) 0.5, (float) 0.5, (float) 0, 2);
				for (Entity e : p.getNearbyEntities(3, 3, 3)) {
					if (b) {
						Vector unitVector = e.getLocation().toVector()
								.subtract(p.getLocation().toVector())
								.normalize();
						unitVector.setY(0.4);
						e.setVelocity(unitVector.multiply(0.8));
					} else if (e instanceof LivingEntity) {
						Vector unitVector = e.getLocation().toVector()
								.subtract(p.getLocation().toVector())
								.normalize();
						unitVector.setY(0.4);
						e.setVelocity(unitVector.multiply(0.8));
					}
				}
			} else {
				this.cancel();
			}
		}
	}

}
