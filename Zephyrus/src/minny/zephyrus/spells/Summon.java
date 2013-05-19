package minny.zephyrus.spells;

import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.DespawnEntityUtil;
import net.minecraft.server.v1_5_R3.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftZombie;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Summon extends Spell {

	public Summon(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "summon";
	}

	@Override
	public String bookText() {
		return null;
	}

	@Override
	public int reqLevel() {
		return 3;
	}

	@Override
	public int manaCost() {
		return 40;
	}

	@Override
	public void run(Player player) {
		Creature tar = getTarget(player);
		Creature skel = (Creature) player.getWorld().spawnEntity(
				tar.getLocation(), EntityType.ZOMBIE);
		skel.setCustomName(player.getDisplayName() + "'s Zombie");
		CraftZombie cs = (CraftZombie) skel;
		CraftCreature ctar = (CraftCreature) tar;
		cs.getHandle().a(new NBTTagCompound());
		cs.getHandle().setGoalTarget(ctar.getHandle());
		new DespawnEntityUtil(skel).runTaskLater(plugin, 100);
	}

	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

	@Override
	public boolean canRun(Player player) {
		try {
			new CraftLivingEntity(null, null);
			if (getTarget(player) != null) {
				return true;
			} else {
				return false;
			}
		} catch (NoClassDefFoundError err) {
			return false;
		}
	}

	@Override
	public String failMessage() {
		return ChatColor.RED
				+ "Zephyrus is not fully compatible with this version of Bukkit.This spell has been disabled :(";
	}

	public Creature getTarget(Player player) {

		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		Creature target = null;
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++) {
					for (int z = -acc; z < acc; z++) {
						for (int y = -acc; y < acc; y++) {
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) {
								if (entity instanceof Creature) {
									return target = (Creature) entity;
								}
							}
						}
					}
				}
			}
		}
		return target;
	}

}
