package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class FireShieldEffect implements IEffect {

	private final int ID;
	
	public FireShieldEffect(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void onApplied(Player player) {
	}

	@Override
	public void onRemoved(Player player) {
		Lang.msg("zephyrus.spells.fireshield.removed", player);
	}

	@Override
	public void onTick(Player player) {
		Location loc = player.getLocation();
		loc.setY(player.getLocation().getY() + 1);
		Effects.playEffect(ParticleEffects.REDSTONE_DUST, loc, 1, 1, 1, 0, 5);
		Effects.playEffect(Sound.FIRE, loc, 0.4F);
		for (Entity e : player.getNearbyEntities(2, 2, 2)) {
			if (e instanceof LivingEntity) {
				((LivingEntity) e).setFireTicks(10);
			}
		}
	}

	@Override
	public void onWarning(Player player) {
		Lang.msg("zephyrus.spells.fireshield.warning", player);
	}

	@Override
	public void onStartup(Player player) {
	}

	@Override
	public int getTypeID() {
		return ID;
	}
	
	@Override
	public int getTickTime() {
		return 5;
	}

}
