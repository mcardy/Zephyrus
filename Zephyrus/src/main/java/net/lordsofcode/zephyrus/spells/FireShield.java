package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FireShield extends Spell {
	
	public FireShield(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "fireshield";
	}

	@Override
	public String bookText() {
		return "Keeps enemies away by burning them if they get too close";
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
		playerMap.add(player.getName());
		new Run(player).runTaskTimer(Zephyrus.getInstance(),
				(long) 0.5, (long) 0.5);
		startDelay(player, time * 20);
	}

	@Override
	public void delayedAction(Player player) {
		playerMap.remove(player.getName());
	}

	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

	@Override
	public SpellType type() {
		return null;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		return map;
	}

	private class Run extends BukkitRunnable {

		Player player;

		Run(Player player) {
			this.player = player;
		}

		public void run() {
			Player p = Bukkit.getPlayer(player.getName());
			if (p != null && playerMap.contains(player.getName())) {
				Location loc = p.getLocation();
				loc.setY(player.getLocation().getY() + 1);
				ParticleEffects.sendToLocation(ParticleEffects.REDSTONE_DUST,
						loc, 1, 1, 1, 0, 5);
				for (Entity e : p.getNearbyEntities(2, 2, 2)) {
					if (e instanceof LivingEntity) {
						((LivingEntity) e).setFireTicks(20);
					}
				}
			} else {
				this.cancel();
			}
		}
	}

}
