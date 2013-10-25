package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

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

	@Override
	public String getName() {
		return "arrowstorm";
	}

	@Override
	public String getDesc() {
		return "A storm of arrows!";
	}

	@Override
	public int reqLevel() {
		return 6;
	}

	@Override
	public int manaCost() {
		return 150;
	}

	@Override
	public boolean run(final Player player, String[] args, int power) {
		int amount = getConfig().getInt("arrowstorm.count");
		new Run(player.getName(), amount*power).runTask(Zephyrus.getPlugin());
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.ARROW, 64));
		s.add(new ItemStack(Material.BOW, 1));
		return s;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", 30);
		return map;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("arrow");
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
				arrow.setMetadata("no_pickup", new FixedMetadataValue(Zephyrus.getPlugin(), true));
				new Run(player, amount - 1).runTaskLater(Zephyrus.getPlugin(), 1);
			}
		}

	}

	@Override
	public Type getPrimaryType() {
		return Type.ATTACK;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
	}

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
