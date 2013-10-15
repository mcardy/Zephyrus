package net.lordsofcode.zephyrus.api;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.effects.ArmourEffect;
import net.lordsofcode.zephyrus.effects.FeatherEffect;
import net.lordsofcode.zephyrus.effects.FlameStepEffect;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public enum EffectType {

	ARMOUR(new ArmourEffect(0), 0), FEATHER(new FeatherEffect(1), 1), FLAMESTEP(new FlameStepEffect(2), 2);

	private IEffect effect;
	private int ID;

	EffectType(IEffect effect, int ID) {
		this.effect = effect;
		this.ID = ID;
		if (effect instanceof Listener) {
			Bukkit.getServer().getPluginManager().registerEvents((Listener) effect, Zephyrus.getPlugin());
		}
	}

	public IEffect getEffect() {
		return this.effect;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public static EffectType getByID(int id) {
		return values()[id];
	}

}
