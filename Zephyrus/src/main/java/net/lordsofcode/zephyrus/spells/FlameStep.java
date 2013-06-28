package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.hooks.PluginHook;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FlameStep extends Spell implements Listener {

	private Map<String, Integer> list;
	private int radius;
	private int duration;
	
	public FlameStep(Zephyrus plugin) {
		super(plugin);
		list = new HashMap<String, Integer>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		Lang.add("spells.flamestep.applied", "You will now burn everything in your path for [TIME] seconds");
		Lang.add("spells.flamestep.warning", "$7You start to cool down...");
		Lang.add("spells.flamestep.end", "$7You are cold again...");
	}

	@Override
	public String name() {
		return "flamestep";
	}

	@Override
	public String bookText() {
		return "Once cast, everything around you will burn!";
	}

	@Override
	public int reqLevel() {
		return 10;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player, String[] args) {
		radius = getConfig().getInt("flamestep.radius");
		duration = getConfig().getInt("flamestep.duration");
		if (list.containsKey(player.getName())) {
			list.put(player.getName(), list.get(player.getName()) + duration);
			player.sendMessage(Lang.get("spells.flamestep.applied").replace("[TIME]", list.get(player.getName()) + ""));
			player.setFireTicks(21);
		} else {
			list.put(player.getName(), duration);
			player.sendMessage(Lang.get("spells.flamestep.applied").replace("[TIME]", list.get(player.getName()) + ""));
			new FlameRunnable(player).runTaskLater(plugin, 20);
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.LAVA_BUCKET, 4));
		s.add(new ItemStack(Material.BLAZE_POWDER, 16));
		s.add(new ItemStack(Material.TORCH, 32));
		s.add(new ItemStack(Material.FIREBALL, 8));
		return s;
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 10);
		map.put("duration", 30);
		return map;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (list.containsKey(e.getPlayer().getName())) {
			for (Entity en : e.getPlayer().getNearbyEntities(radius, radius, radius)) {
				if (en instanceof Creature) {
					Creature cr = (Creature) en;
					cr.setFireTicks(20);
				}
			}
			int radius = this.radius;
			final Block block = e.getPlayer().getLocation().getBlock();
			for (int x = -(radius); x <= radius; x++) {
				for (int y = -(radius); y <= radius; y++) {
					for (int z = -(radius); z <= radius; z++) {
						if (block.getRelative(x,y,z).getType() == Material.SAND && PluginHook.canBuild(e.getPlayer(), block.getRelative(x,y,z))) {
							block.getRelative(x,y,z).setType(Material.GLASS);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FIRE_TICK) {
			Player player = (Player) e.getEntity();
			if (list.containsKey(player.getName())) {
				e.setCancelled(true);
			}
		}
	}
	
	private class FlameRunnable extends BukkitRunnable {

		Player player;

		FlameRunnable(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (list.get(player.getName()) > 0) {
				if (list.get(player.getName()) == 5) {
					Lang.msg("spells.feather.warning", player);
				}
				list.put(player.getName(), list.get(player.getName()) - 1);
				player.setFireTicks(21);
				new FlameRunnable(player).runTaskLater(plugin, 20);
			} else {
				list.remove(player.getName());
				Lang.msg("spells.flamestep.end", player);
			}
		}

	}

	@Override
	public SpellType type() {
		return SpellType.FIRE;
	}

}
