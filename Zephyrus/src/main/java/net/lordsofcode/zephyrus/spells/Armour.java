package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.effects.EffectType;
import net.lordsofcode.zephyrus.utils.Lang;

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

public class Armour extends Spell {

	public Armour() {
		Lang.add("spells.armour.applied", "$6Your skin feels hard with magic and gold!");
		Lang.add("spells.armour.name", "$6Magic Armour");
		Lang.add("spells.armour.fail", "You can't be wearing armour!");
	}

	@Override
	public String getName() {
		return "armour";
	}

	@Override
	public String getDesc() {
		return "A set of magical armour that can be called whenever you need it! The armour will block all damage for 30 seconds!";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 300;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (player.getInventory().getHelmet() == null && player.getInventory().getChestplate() == null
				&& player.getInventory().getLeggings() == null && player.getInventory().getBoots() == null) {
			int time = getConfig().getInt(this.getName() + ".delay");
			Zephyrus.getUser(player).applyEffect(EffectType.ARMOUR, time*20);
			Lang.msg("spells.armour.applied", player);
			return true;
		} else {
			Lang.errMsg("spells.armour.fail", player);
			return false;
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delay", 30);
		return map;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.GOLD_BOOTS));
		i.add(new ItemStack(Material.GOLD_LEGGINGS));
		i.add(new ItemStack(Material.GOLD_CHESTPLATE));
		i.add(new ItemStack(Material.GOLD_HELMET));
		return i;
	}

	@Override
	public Type getPrimaryType() {
		return Type.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.MAGIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
}
