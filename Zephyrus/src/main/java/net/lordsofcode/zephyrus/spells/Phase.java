package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Effects;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Phase extends Spell {

	public Phase() {
		Lang.add("spells.phase.fail", "You can't phase through that!");
	}

	@Override
	public String getName() {
		return "phase";
	}

	@Override
	public String getDesc() {
		return "Phase through blocks!";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (!canRun(player, args)) {
			Lang.errMsg("spells.phase.fail", player);
			return false;
		}
		Location loc = player.getTargetBlock(null, 4).getLocation();
		BlockFace bf = yawToFace(player);
		loc.setY(loc.getY() - 1);
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		loc.setYaw(player.getLocation().getYaw());
		loc.setPitch(player.getLocation().getPitch());
		if (bf == BlockFace.NORTH) {
			loc.setZ(loc.getZ() + 1);
		} else if (bf == BlockFace.SOUTH) {
			loc.setZ(loc.getZ() - 1);
		} else if (bf == BlockFace.EAST) {
			loc.setX(loc.getX() - 1);
		} else if (bf == BlockFace.WEST) {
			loc.setX(loc.getX() + 1);
		} else if (bf == BlockFace.DOWN) {
			loc.setY(loc.getY() - 1);
		} else if (bf == BlockFace.UP) {
			loc.setY(loc.getY() + 2);
		}
		Effects.playEffect(Sound.ENDERMAN_TELEPORT, player.getLocation(), 1, -1);
		Location ploc = player.getLocation();
		ploc.setX(ploc.getX() + 0.5);
		ploc.setZ(ploc.getZ() + 0.5);
		ploc.setY(ploc.getY() + 2);
		Effects.playEffect(ParticleEffects.ENDER, player.getLocation(), 0, 0, 0, 2, 40);
		player.teleport(loc);
		return true;
	}

	@Override
	public ISpell getRequiredSpell() {
		return Spell.forName("blink");
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.EYE_OF_ENDER));
		i.add(new ItemStack(Material.ENDER_PEARL));
		return i;
	}

	public boolean canRun(Player player, String[] args) {
		Location loc1 = player.getTargetBlock(null, 4).getLocation();
		Location loc2;
		BlockFace bf = yawToFace(player);
		if (bf == BlockFace.NORTH) {
			loc1.setZ(loc1.getZ() + 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() - 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		} else if (bf == BlockFace.SOUTH) {
			loc1.setZ(loc1.getZ() - 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() - 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		} else if (bf == BlockFace.EAST) {
			loc1.setX(loc1.getX() - 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() - 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		} else if (bf == BlockFace.WEST) {
			loc1.setX(loc1.getX() + 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() - 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		} else if (bf == BlockFace.DOWN) {
			loc1.setY(loc1.getY() - 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() - 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		} else if (bf == BlockFace.UP) {
			loc1.setY(loc1.getY() + 1);
			loc2 = loc1;
			loc2.setY(loc2.getY() + 1);
			if (loc1.getBlock().getType() == Material.AIR && loc2.getBlock().getType() == Material.AIR) {
				return true;
			}
		}
		return false;
	}

	private BlockFace yawToFace(Player player) {
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
		if (pitch < 45 && pitch > -45) {
			return axis[Math.round(yaw / 90f) & 0x3];
		} else if (pitch < -45) {
			return BlockFace.UP;
		} else if (pitch > 45) {
			return BlockFace.DOWN;
		} else {
			return null;
		}
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
