package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.effects.Effects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

	public Repair() {
		Lang.add("spells.repair.side", "$7Your tool feels a bit weaker...");
		Lang.add("spells.repair.applied", "$7Your tool feels a bit stronger");
		Lang.add("spells.repair.fail", "That item can't be repaired!");
	}

	@Override
	public String getName() {
		return "repair";
	}

	@Override
	public String getDesc() {
		return "Repairs your items! Extends your tools life by 30!";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 60;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (player.getItemInHand() != null && player.getItemInHand().getType().getMaxDurability() != 0) {
			int amount = getConfig().getInt(getName() + ".amount");
			ItemStack i = player.getItemInHand();
			if (i.getDurability() < i.getType().getMaxDurability() + 30) {
				player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() - amount));
			} else {
				player.getItemInHand().setDurability(player.getItemInHand().getType().getMaxDurability());
			}
			player.sendMessage(ChatColor.GRAY + "Your tool feels a bit stronger");
			Effects.playEffect(Sound.ANVIL_USE, player.getLocation());
			return true;
		} else {
			Lang.errMsg("spells.repair.fail", player);
			return false;
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 30);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ANVIL));
		return items;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		int amount = getConfig().getInt(getName() + ".amount");
		player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + amount));
		Lang.msg("spells.repair.side", player);
		return true;
	}

	@Override
	public boolean canBind() {
		return false;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.RESTORE;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

}
