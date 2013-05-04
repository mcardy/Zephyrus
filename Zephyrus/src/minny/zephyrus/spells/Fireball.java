package minny.zephyrus.spells;

import minny.zephyrus.Zephyrus;

import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;

public class Fireball extends Spell {

	public Fireball(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put("fireball", this);
	}

	@Override
	public String name() {
		return "Fireball";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player) {
		if (!(lvl.getMana(player) < this.manaCost())) {
			player.launchProjectile(SmallFireball.class);
			drainMana(player, this.manaCost());
		} else {
			notEnoughMana(player);
		}
	}

	@Override
	public String permission() {
		return "zephyrus.spell.fireball";
	}

}
