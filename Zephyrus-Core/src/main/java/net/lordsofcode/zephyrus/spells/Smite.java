package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
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

	@Override
	public String getName() {
		return "smite";
	}

	@Override
	public String getDesc() {
		return "Brings a storm of lightning down were you point";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 250;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, String[] args, int power) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 1000), player);
		Bukkit.getPluginManager().callEvent(e);
		if (!e.isCancelled()) {
			Location loc = player.getTargetBlock(null, 1000).getLocation();
			for (int i = 0; i < power*10; i++) {
				new Strike(loc).runTaskLater(Zephyrus.getPlugin(), i);
			}
			return true;
		}
		return false;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.EMERALD, 3));
		s.add(new ItemStack(Material.FLINT_AND_STEEL));
		return s;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("bolt");
	}

	private class Strike extends BukkitRunnable {

		Location loc;

		Strike(Location loc) {
			this.loc = loc;
		}

		@Override
		public void run() {
			loc.getWorld().strikeLightning(loc);
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public Type getPrimaryType() {
		return null;
	}

	@Override
	public Element getElementType() {
		return null;
	}

	@Override
	public Priority getPriority() {
		return null;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
