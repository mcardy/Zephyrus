package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.utils.ConfigHandler;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;
import net.lordsofcode.zephyrus.utils.effects.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ZephyrEffect implements IEffect {

	private final int ID;
	private final boolean BLOCK_ALL;
	private final int POWER;
	
	public ZephyrEffect(int ID) {
		this.ID = ID;
		this.BLOCK_ALL = new ConfigHandler("spells.yml").getConfig().getBoolean("zephyr" + ".block-all");
		this.POWER = new ConfigHandler("spells.yml").getConfig().getInt("zephyr" + ".power");
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
		Effects.playEffect(ParticleEffects.CLOUD, loc, (float) 0.5, (float) 0.5, (float) 0.5, 0, 10);
		for (Entity e : player.getNearbyEntities(3, 3, 3)) {
			if (this.BLOCK_ALL) {
				Vector unitVector = e.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
				unitVector.setY(0.4);
				e.setVelocity(unitVector.multiply(POWER * 0.4));
			} else if (e instanceof LivingEntity) {
				Vector unitVector = e.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
				unitVector.setY(0.4);
				e.setVelocity(unitVector.multiply(POWER * 0.4));
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
		return 4;
	}

}
