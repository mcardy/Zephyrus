package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Effects;
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

	@Override
	public String getName() {
		return "zephyr";
	}

	@Override
	public String getDesc() {
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
	public boolean run(Player player, String[] args) {
		int time = getConfig().getInt(getName() + ".duration");
		boolean b = getConfig().getBoolean(getName() + ".block-all");
		int p = getConfig().getInt(getName() + ".power");
		playerMap.add(player.getName());
		new Run(player, b, p).runTaskTimer(Zephyrus.getPlugin(), (long) 0.1, (long) 0.1);
		startDelay(player, time * 20);
		return true;
	}

	@Override
	public void delayedAction(Player player) {
		playerMap.remove(player.getName());
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FEATHER, 8));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 60);
		map.put("block-all", true);
		map.put("power", 3);
		return map;
	}

	private class Run extends BukkitRunnable {

		Player player;
		boolean b;
		int power;

		Run(Player player, boolean b, int power) {
			this.player = player;
			this.b = b;
			this.power = power;
		}

		@Override
		public void run() {
			Player p = Bukkit.getPlayer(player.getName());
			if (p != null && playerMap.contains(player.getName())) {
				Location loc = p.getLocation();
				loc.setY(player.getLocation().getY() + 1);
				Effects.playEffect(ParticleEffects.CLOUD, loc, (float) 0.5, (float) 0.5, (float) 0.5, 0, 10);
				for (Entity e : p.getNearbyEntities(3, 3, 3)) {
					if (b) {
						Vector unitVector = e.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
						unitVector.setY(0.4);
						e.setVelocity(unitVector.multiply(power * 0.4));
					} else if (e instanceof LivingEntity) {
						Vector unitVector = e.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
						unitVector.setY(0.4);
						e.setVelocity(unitVector.multiply(power * 0.4));
					}
				}
			} else {
				this.cancel();
			}
		}
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
