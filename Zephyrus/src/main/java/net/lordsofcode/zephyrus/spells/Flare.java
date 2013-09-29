package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Flare extends Spell {

	@Override
	public String getName() {
		return "flare";
	}

	@Override
	public String getDesc() {
		return "Fires a big fireball on cast!";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 80;
	}

	@Override
	public boolean run(Player player, String[] args) {
		BlockBreakEvent e = new BlockBreakEvent(player.getTargetBlock(null, 1000), player);
		Bukkit.getPluginManager().callEvent(e);
		if (e.isCancelled()) {
			return false;
		}
		player.launchProjectile(org.bukkit.entity.Fireball.class);
		if (player.getInventory().contains(Material.FIRE)) {
			Arrow arrow = player.launchProjectile(Arrow.class);
			arrow.setFireTicks(1000);
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FIREBALL, 32));
		i.add(new ItemStack(Material.BLAZE_POWDER, 16));
		return i;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("fireball");
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.DESTRUCTION;
	}

	@Override
	public Element getElementType() {
		return Element.FIRE;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		player.getLocation().getBlock().setType(Material.FIRE);
		return false;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}
}
