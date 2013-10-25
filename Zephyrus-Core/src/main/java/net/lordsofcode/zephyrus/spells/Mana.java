package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

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

	@Override
	public String getName() {
		return "mana";
	}

	@Override
	public String getDesc() {
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
	public boolean run(Player player, String[] args, int power) {
		int a = getConfig().getInt(getName() + ".amount");
		int d = getConfig().getInt(getName() + ".damage");
		IUser user = Zephyrus.getUser(player);
		user.drainMana(-a);
		player.damage(d);
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.GLOWSTONE_DUST, 16));
		return i;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", 20);
		map.put("damage", 4);
		return map;
	}

	@Override
	public Type getPrimaryType() {
		return Type.RESTORE;
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
