package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FeatherEffect implements IEffect, Listener {

	private int ID;
	
	public FeatherEffect(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void onApplied(Player player) {
	}

	@Override
	public void onRemoved(Player player) {
		Lang.msg("spells.feather.end", player);
	}

	@Override
	public void onTick(Player player) {
	}

	@Override
	public void onStartup(Player player) {
	}

	@Override
	public int getTypeID() {
		return this.ID;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (Zephyrus.getUser(e.getPlayer()).hasEffect(EffectType.FEATHER) && !e.getPlayer().isFlying()) {
			if (e.getFrom().getY() > e.getTo().getY()) {
				Location loc = e.getPlayer().getLocation();
				loc.setY(loc.getY() - 1);
				if (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
					Vector v = e.getPlayer().getVelocity();
					v.setY(v.getY() / 1.5);
					e.getPlayer().setVelocity(v);
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (EffectHandler.hasEffect(player, EffectType.FEATHER) && e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}
		}
	}

	@Override
	public void onWarning(Player player) {
		Lang.msg("spells.feather.warning", player);
	}

	@Override
	public int getTickTime() {
		return 0;
	}
	
}
