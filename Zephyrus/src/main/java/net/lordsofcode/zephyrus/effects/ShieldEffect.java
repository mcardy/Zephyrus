package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Location;
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

public class ShieldEffect implements IEffect {

	private final int ID;
	private final int DAMAGE;
	
	public ShieldEffect(int ID) {
		this.ID = ID;
		this.DAMAGE = new ConfigHandler("spells.yml").getConfig().getInt("shield.damage");
	}
	
	@Override
	public void onApplied(Player player) {
	}

	@Override
	public void onRemoved(Player player) {
		Lang.msg("zephyrus.spells.shield.removed", player);
	}

	@Override
	public void onTick(Player player) {
		Location loc = player.getLocation();
		loc.setY(player.getLocation().getY() + 1);
		Effects.playEffect(ParticleEffects.BLUE_SPARKLE, loc, 0.5F, 1, 0.5F, 100, 12);
		for (Entity e : player.getNearbyEntities(2, 2, 2)) {
			if (e instanceof LivingEntity) {
				((LivingEntity) e).damage(DAMAGE);
			}
		}
	}

	@Override
	public void onWarning(Player player) {
		Lang.msg("zephyrus.spells.shield.warning", player);
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
		return 10;
	}

}
