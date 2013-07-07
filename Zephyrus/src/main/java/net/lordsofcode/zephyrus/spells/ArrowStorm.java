package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ArrowStorm extends Spell {

	public ArrowStorm(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "arrowstorm";
	}

	@Override
	public String bookText() {
		return "A storm of arrows!";
	}

	@Override
	public int reqLevel() {
		return 6;
	}

	@Override
	public int manaCost() {
		return 30;
	}

	@Override
	public void run(final Player player, String[] args) {
		int amount = getConfig().getInt(this.name() + ".count");
		new Run(player.getName(), amount).runTask(Zephyrus.getInstance());
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.ARROW, 64));
		s.add(new ItemStack(Material.BOW, 4));
		return s;
	}

	@Override
	public SpellType type() {
		return null;
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", 30);
		return map;
	}

	private class Run extends BukkitRunnable {
		int amount;
		String player;
		
		public Run(String player, int amount) {
			this.amount = amount;
			this.player = player;
		}
		
		@Override
		public void run() {
			if (0 < amount && Bukkit.getPlayer(player) != null) {
				org.bukkit.entity.Arrow arrow = Bukkit.getPlayer(player)
						.launchProjectile(org.bukkit.entity.Arrow.class);
				arrow.setMetadata("no_pickup", new FixedMetadataValue(plugin,
						true));
				new Run(player, amount-1).runTaskLater(Zephyrus.getInstance(), 1);
			}
		}

	}

}
