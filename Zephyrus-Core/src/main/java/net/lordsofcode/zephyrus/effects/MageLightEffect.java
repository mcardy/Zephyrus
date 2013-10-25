package net.lordsofcode.zephyrus.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class MageLightEffect implements IEffect {

	private final int ID;
	
	public MageLightEffect(int ID) {
		this.ID = ID;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onApplied(Player player) {
		Location loc = player.getLocation();
		loc.setY(loc.getY() - 1);
		player.sendBlockChange(loc, Material.GLOWSTONE, (byte) 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemoved(Player player) {
		Location loc = player.getLocation();
		loc.setY(loc.getY() - 1);
		player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
	}

	@Override
	public void onTick(Player player) {
	}

	@Override
	public void onWarning(Player player) {
	}

	@Override
	public void onStartup(Player player) {
	}

	@Override
	public int getTypeID() {
		return ID;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (EffectHandler.hasEffect(e.getPlayer(), EffectType.MAGELIGHT)) {
			Location loc = e.getTo();
			loc.setY(loc.getY() - 1);
			Material mat = loc.getBlock().getType();
			if (mat != Material.AIR && mat != Material.WATER && mat != Material.STATIONARY_WATER
					&& mat != Material.LAVA && mat != Material.STATIONARY_LAVA) {
				e.getPlayer().sendBlockChange(loc, Material.GLOWSTONE, (byte) 0);
			}
			Location loc2 = e.getFrom();
			loc2.setY(loc2.getY() - 1);
			if (loc.getBlockX() == loc2.getBlockX() && loc.getBlockY() == loc2.getBlockY()
					&& loc.getBlockZ() == loc2.getBlockZ()) {
			} else {
				e.getPlayer().sendBlockChange(loc2, loc2.getBlock().getType(), loc2.getBlock().getData());
			}
		}
	}
	
	@Override
	public int getTickTime() {
		return 0;
	}

}
