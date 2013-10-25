package net.lordsofcode.zephyrus.effects;


import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpeedEffect implements IEffect {

	private final int ID;
	
	public SpeedEffect(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void onApplied(Player player) {
		player.setWalkSpeed(0.4F);
	}

	@Override
	public void onRemoved(Player player) {
		player.setWalkSpeed(0.2F);
	}

	@Override
	public void onTick(Player player) {
	}

	@Override
	public void onWarning(Player player) {
		Lang.msg("spells.superspeed.warning", player);
		player.setWalkSpeed(0.3F);
	}

	@Override
	public void onStartup(Player player) {
		player.setWalkSpeed(0.2F);
	}

	@Override
	public int getTypeID() {
		return ID;
	}
	
	@Override
	public int getTickTime() {
		return 0;
	}

}
