package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

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
//TODO Change to effect
public class Shield extends Spell {

	@Override
	public String getName() {
		return "shield";
	}

	@Override
	public String getDesc() {
		return "Keeps enemies away by zapping them if they get too close";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 400;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int damage = getConfig().getInt(getName() + ".damage");
		int time = getConfig().getInt(getName() + ".duration");
		playerMap.add(player.getName());
		new Run(player, damage).runTaskTimer(Zephyrus.getPlugin(), (long) 0.5, (long) 0.5);
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
		i.add(new ItemStack(Material.DIAMOND_SWORD));
		i.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
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

		@Override
		public void run() {
			Player p = Bukkit.getPlayer(player.getName());
			if (p != null && playerMap.contains(player.getName())) {
				Location loc = p.getLocation();
				loc.setY(player.getLocation().getY() + 1);
				Effects.playEffect(ParticleEffects.BLUE_SPARKLE, loc, 0.5F, 1, 0.5F, 100, 10);
				for (Entity e : p.getNearbyEntities(2, 2, 2)) {
					if (e instanceof LivingEntity) {
						((LivingEntity) e).damage(damage);
					}
				}
			} else {
				this.cancel();
			}
		}
	}

	@Override
	public Type getPrimaryType() {
		return Type.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
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
