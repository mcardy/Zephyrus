package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.Type;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Arrow extends Spell {

	@Override
	public String getName() {
		return "arrow";
	}

	@Override
	public String getDesc() {
		return "Shoots an arrow. Simple.";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public boolean run(Player player, String[] args, int power) {
		org.bukkit.entity.Arrow arrow = player.launchProjectile(org.bukkit.entity.Arrow.class);
		arrow.setMetadata("no_pickup", new FixedMetadataValue(Zephyrus.getPlugin(), true));
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.ARROW, 16));
		return s;
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (event.getItem().hasMetadata("no_pickup")) {
			event.setCancelled(true);
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
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
