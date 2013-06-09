package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
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

public class Heal extends Spell {

	public Heal(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "heal";
	}

	@Override
	public String bookText() {
		return "Can't you guess what this does by the title?";
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
		int a = getConfig().getInt(this.name() + ".amount");
		player.setHealth(player.getHealth() + a);
		player.sendMessage(ChatColor.GRAY + "You feel a bit stronger");
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 1);
		return map;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (player.getHealth() == 20) {
			return false;
		}
		return true;
	}

	@Override
	public String failMessage() {
		return "You are already at max health!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.GOLDEN_APPLE));
		return items;
	}

}
