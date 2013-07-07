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

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Shield extends Spell {
	
	public Shield(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "shield";
	}

	@Override
	public String bookText() {
		return "Keeps enemies away by zapping them if they get too close";
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
		int damage = getConfig().getInt(name() + ".damage");
		int time = getConfig().getInt(name() + ".duration");
		playerMap.add(player.getName());
		new Run(player, damage).runTaskTimer(Zephyrus.getInstance(),
				(long) 0.5, (long) 0.5);
		startDelay(player, time * 20);
	}

	@Override
	public void delayedAction(Player player) {
		playerMap.remove(player.getName());
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.DIAMOND_SWORD));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		map.put("damage", 1);
		return map;
	}

	private class Run extends BukkitRunnable {

		Player player;
		int damage;

		Run(Player player, int damage) {
			this.player = player;
			this.damage = damage;
		}

		public void run() {
			Player p = Bukkit.getPlayer(player.getName());
			if (p != null && playerMap.contains(player.getName())) {
				Location loc = p.getLocation();
				loc.setY(player.getLocation().getY() + 1);
				ParticleEffects.sendToLocation(ParticleEffects.BLUE_SPARKLE,
						loc, 0.5F, 1, 0.5F, 100, 10);
				for (Entity e : p.getNearbyEntities(2, 2, 2)) {
					if (e instanceof LivingEntity) {
						((LivingEntity) e).damage((double)damage);
					}
				}
			} else {
				this.cancel();
			}
		}
	}

}
