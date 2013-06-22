package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Punch extends Spell {

	public Punch(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "punch";
	}

	@Override
	public String bookText() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 2;
	}

	@Override
	public void run(Player player, String[] args) {
		int damage = getConfig().getInt(this.name() + ".damage");
		LivingEntity c = (LivingEntity) getTarget(player);
		c.damage(damage);
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		return getTarget(player) != null;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("damage", 4);
		return map;
	}
	
	@Override
	public String failMessage() {
		return "Nothing to punch!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		// Potion of instant damage 1
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.IRON_SWORD));
		i.add(new ItemStack(Material.POTION, 1, (short) 8204));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.damage(1, player);
		return false;
	}

}
