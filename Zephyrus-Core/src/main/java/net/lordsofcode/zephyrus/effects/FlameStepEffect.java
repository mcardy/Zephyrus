package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FlameStepEffect implements IEffect, Listener {

	private final int ID;
	private final int RADIUS;

	public FlameStepEffect(int ID) {
		this.ID = ID;
		this.RADIUS = new ConfigHandler("spells.yml").getConfig().getInt("flamestep.radius");
	}

	@Override
	public void onApplied(Player player) {
	}

	@Override
	public void onRemoved(Player player) {
		Lang.msg("spells.flamestep.end", player);
	}

	@Override
	public void onTick(Player player) {
		player.setFireTicks(20);
	}

	@Override
	public void onStartup(Player player) {
	}

	@Override
	public void onWarning(Player player) {
		Lang.msg("spells.feather.warning", player);
	}

	@Override
	public int getTypeID() {
		return this.ID;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (EffectHandler.hasEffect(e.getPlayer(), EffectType.FLAMESTEP)) {
			for (Entity en : e.getPlayer().getNearbyEntities(RADIUS, RADIUS, RADIUS)) {
				if (en instanceof Creature) {
					Creature cr = (Creature) en;
					cr.setFireTicks(20);
				}
			}
			int radius = this.RADIUS;
			final Block block = e.getPlayer().getLocation().getBlock();
			for (int x = -(radius); x <= radius; x++) {
				for (int y = -(radius); y <= radius; y++) {
					for (int z = -(radius); z <= radius; z++) {
						Block b = block.getRelative(x, y, z);
						if (b.getType() == Material.SAND || b.getType() == Material.COBBLESTONE) {
							BlockBreakEvent event = new BlockBreakEvent(b, e.getPlayer());
							Bukkit.getPluginManager().callEvent(event);
							if (event.isCancelled()) {
								continue;
							}
							if (b.getType() == Material.SAND) {
								b.setType(Material.GLASS);
							}
							if (b.getType() == Material.COBBLESTONE) {
								b.setType(Material.STONE);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		try {
			if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FIRE_TICK) {
				Player player = (Player) e.getEntity();
				if (EffectHandler.hasEffect(player, EffectType.FLAMESTEP)) {
					e.setCancelled(true);
				}
			}
		} catch (Exception ex) {
		}
	}

	@Override
	public int getTickTime() {
		return 20;
	}

}
