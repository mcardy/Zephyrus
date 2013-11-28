package net.lordsofcode.zephyrus.effects;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;

import org.apache.commons.lang.WordUtils;
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

	ARMOUR(new ArmourEffect(0), 0, "Armour"), FEATHER(new FeatherEffect(1), 1, "Feather"), FLAMESTEP(
			new FlameStepEffect(2), 2, "Flame Step"), SUPERSPEED(new SpeedEffect(3), 3, "SuperSpeed"), FLY(
			new FlyEffect(4), 4, "Fly"), MAGELIGHT(new MageLightEffect(5), 5, "Mage Light"), FIRESHIELD(
			new FireShieldEffect(6), 6, "Fire Shield"), SHIELD(new ShieldEffect(7), 7, "Shield"), ZEPHYR(
			new ZephyrEffect(8), 8, "Zephyr");

	private IEffect effect;
	private int id;
	private String name;

	EffectType(IEffect effect, int id, String name) {
		this.effect = effect;
		this.id = id;
		this.name = name.toLowerCase();
		Lang.add("effects." + name.toLowerCase() + ".name", WordUtils.capitalizeFully(name));
		if (effect instanceof Listener) {
			Bukkit.getServer().getPluginManager().registerEvents((Listener) effect, Zephyrus.getPlugin());
		}
	}

	public IEffect getEffect() {
		return this.effect;
	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return Lang.get("effects." + this.name + ".name");
	}

	public static EffectType getByID(int id) {
		return values()[id];
	}

}
