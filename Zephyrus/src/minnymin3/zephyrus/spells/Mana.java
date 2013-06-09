package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.player.LevelManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Mana extends Spell {

	public Mana(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "mana";
	}

	@Override
	public String bookText() {
		return "Will hurt you to restore mana";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 0;
	}

	@Override
	public void run(Player player, String[] args) {
		int a = getConfig().getInt(this.name() + ".amount");
		if (LevelManager.getMana(player) + a < LevelManager.getLevel(player) * 100) {
			LevelManager.drainMana(player, -a);
			player.damage(4);
		} else {
			LevelManager.resetMana(player);
			player.damage(4);
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.GLOWSTONE_DUST, 16));
		return i;
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 20);
		return map;
	}

}
