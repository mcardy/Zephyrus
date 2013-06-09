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

public class Feed extends Spell {

	public Feed(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "feed";
	}

	@Override
	public String bookText() {
		return "You hungry? Not anymore!";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 1;
	}

	@Override
	public void run(Player player, String[] args) {
		int a = getConfig().getInt(this.name() + ".amount");
		player.setFoodLevel(player.getFoodLevel() + a);
		player.sendMessage(ChatColor.GRAY + "You feel slightly less hungry");
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (player.getFoodLevel() == 20) {
			return false;
		}
		return true;
	}

	@Override
	public String failMessage() {
		return "You are already at max hunger!";
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 1);
		return map;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.COOKED_BEEF));
		items.add(new ItemStack(Material.COOKED_CHICKEN));
		items.add(new ItemStack(Material.COOKED_FISH));
		return items;
	}

}
