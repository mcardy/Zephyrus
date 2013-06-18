package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;

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

public class Explode extends Spell {

	public Explode(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "explode";
	}

	@Override
	public String bookText() {
		return "Makes a big boom!";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player, String[] args) {
		int r = getConfig().getInt(this.name() + ".power");
		player.getWorld().createExplosion(
				player.getTargetBlock(null, 200).getLocation(), r, false);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.TNT, 64));
		return i;
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("power", 2);
		return map;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		boolean b = PluginHook.canBuild(player,
				player.getTargetBlock(null, 1000))
				&& PluginHook.allowExplosion() && player.getTargetBlock(null, 1000).getType() != Material.AIR;
		return b;
	}

	@Override
	public String failMessage() {
		return ChatColor.DARK_RED
				+ "Can't explode there!";
	}

	@Override
	public SpellType type() {
		return SpellType.EARTH;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		int r = getConfig().getInt(this.name() + ".power");
		player.getWorld().createExplosion(
				player.getTargetBlock(null, 200).getLocation(), r * 2, false);
		return true;
	}
	
	@Override
	public Set<SpellType> types() {
		Set<SpellType> t = types();
		t.add(SpellType.FIRE);
		t.add(SpellType.AIR);
		return t;
	}
	
	@Override
	public void comboSpell(Player player, String[] args, SpellType type, int level) {
		int r = getConfig().getInt(this.name() + ".power");
		switch (type) {
		case FIRE:
			player.getWorld().createExplosion(
					player.getTargetBlock(null, 200).getLocation(), r, true);
			break;
		case AIR:
			player.getWorld().createExplosion(
					player.getTargetBlock(null, 200).getLocation(), r * 2, true);
			break;
		default: break;
		}
	}

}
