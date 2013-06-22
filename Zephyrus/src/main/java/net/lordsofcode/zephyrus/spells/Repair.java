package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

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

public class Repair extends Spell {

	public Repair(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "repair";
	}

	@Override
	public String bookText() {
		return "Repairs your items! Extends your tools life by 30!";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 12;
	}

	@Override
	public void run(Player player, String[] args) {
		int amount = getConfig().getInt(this.name() + ".amount");
		ItemStack i = player.getItemInHand();
		if (i.getDurability() < i.getType().getMaxDurability() + 30) {
			player.getItemInHand().setDurability(
					(short) (player.getItemInHand().getDurability() - amount));
		} else {
			player.getItemInHand().setDurability(
					player.getItemInHand().getType().getMaxDurability());
		}
		player.sendMessage(ChatColor.GRAY + "Your tool feels a bit stronger");
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (player.getItemInHand() != null
				&& player.getItemInHand().getType().getMaxDurability() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 30);
		return map;
	}

	@Override
	public String failMessage() {
		return ChatColor.GRAY + "That item can't be repaired!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ANVIL));
		return items;
	}

	@Override
	public SpellType type() {
		return SpellType.RESTORE;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		int amount = getConfig().getInt(this.name() + ".amount");
		player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + amount));
		player.sendMessage(ChatColor.GRAY + "Your tool feels a bit weaker...");
		return true;
	}

}
