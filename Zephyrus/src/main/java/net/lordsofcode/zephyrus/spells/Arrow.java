package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

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

	public Arrow(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "arrow";
	}

	@Override
	public String bookText() {
		return "Shoots an arrow. Simple.";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 4;
	}

	@Override
	public void run(Player player, String[] args) {
		org.bukkit.entity.Arrow arrow = player.launchProjectile(org.bukkit.entity.Arrow.class);
		arrow.setMetadata("no_pickup", new FixedMetadataValue(plugin, true));
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.ARROW, 16));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.DAMAGE;
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
	    if(event.getItem().hasMetadata("no_pickup")) {
	        event.setCancelled(true);
	    }
	}

}
