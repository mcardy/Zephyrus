package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Smite extends Spell {

	public Smite(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "smite";
	}

	@Override
	public String bookText() {
		return "Brings a storm of lightning down were you point";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player, String[] args) {
		Location loc = player.getTargetBlock(null, 1000).getLocation();
		new Strike(loc).runTaskLater(plugin, 1);
		new Strike(loc).runTaskLater(plugin, 2);
		new Strike(loc).runTaskLater(plugin, 3);
		new Strike(loc).runTaskLater(plugin, 4);
		new Strike(loc).runTaskLater(plugin, 5);
		new Strike(loc).runTaskLater(plugin, 6);
		new Strike(loc).runTaskLater(plugin, 7);
		new Strike(loc).runTaskLater(plugin, 8);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.DIAMOND));
		s.add(new ItemStack(Material.FLINT_AND_STEEL, 2));
		return s;
	}
	
	@Override
	public Spell reqSpell() {
		return Zephyrus.spellMap.get("bolt");
	}
	
	private class Strike extends BukkitRunnable {
		
		Location loc;
		
		Strike(Location loc) {
			this.loc = loc;
		}
		
		public void run() {
			loc.getWorld().strikeLightning(loc);
		}
	}
	
	@Override
	public boolean canRun(Player player, String[] args) {
		return PluginHook.canBuild(player, player.getTargetBlock(null, 1000));
	}

	@Override
	public String failMessage() {
		return ChatColor.DARK_RED + "You don't have permission for this area";
	}

}
