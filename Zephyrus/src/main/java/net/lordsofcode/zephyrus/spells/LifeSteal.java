package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LifeSteal extends Spell {

	public LifeSteal(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "lifesteal";
	}

	@Override
	public String bookText() {
		return "This spell will damage your enemy and give you their health";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 2;
	}

	@Override
	public void run(Player player, String[] args) {
		Creature en = (Creature) getTarget(player);
		en.damage(2);
		try {
			player.setHealth(player.getMaxHealth() + 2);
		} catch (Exception e) {
			player.setHealth(player.getMaxHealth());
		}
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		Entity en = getTarget(player);
		return en != null && en instanceof Creature;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.GHAST_TEAR, 2));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		if (rand.nextInt(2) == 0) {
			Creature en = (Creature) getTarget(player);
			try {
				en.setHealth(en.getHealth() + 2);
			} catch (Exception e) {
				en.setHealth(en.getMaxHealth());
			}
			player.damage(2, en);
			return true;
		}
		return false;

	}

}
