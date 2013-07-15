package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.PluginHook;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Blink extends Spell {

	public Blink () {
		Lang.add("spells.blink.fail", ChatColor.DARK_RED + "Can't blink there!");
	}
	
	@Override
	public String getName() {
		return "blink";
	}

	@Override
	public String getDesc() {
		return "Gets you from point A to point C without bothering with point B";
	}

	@Override
	public int reqLevel() {
		return 3;
	}

	@Override
	public int manaCost() {
		return 8;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.ENDER_PEARL));
		return i;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (player.getTargetBlock(null, 100) != null
				&& player.getTargetBlock(null, 100).getType() != Material.AIR) {
			Location loc = player.getTargetBlock(null, 100).getLocation();
			loc.setY(loc.getY() + 1);
			Location loc2 = loc;
			loc2.setY(loc2.getY() + 1);
			Block block = loc.getBlock();
			Block block2 = loc2.getBlock();
			if (block.getType() == Material.AIR
					&& block2.getType() == Material.AIR) {
				if (PluginHook.canBuild(player, block)) {
					Location oldLoc = player.getLocation();
					ParticleEffects.sendToLocation(ParticleEffects.ENDER,
							oldLoc, 1, 1, 1, 1, 16);
					player.getWorld().playSound(player.getLocation(),
							Sound.ENDERMAN_TELEPORT, 10, 1);
					loc.setX(loc.getX() + 0.5);
					loc.setY(loc.getY() + 0.25);
					loc.setZ(loc.getZ() + 0.5);
					loc.setPitch(player.getLocation().getPitch());
					loc.setYaw(player.getLocation().getYaw());
					player.teleport(loc);
					player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
					return true;
				}
			}
		}
		Lang.errMsg("spells.blink.fail", player);
		return false;
	}

	@Override
	public Map<String, Object> getConfiguration() {
		return null;
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.TELEPORTATION;
	}

	@Override
	public Element getElementType() {
		return Element.ENDER;
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
