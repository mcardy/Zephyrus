package net.lordsofcode.zephyrus.spells;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ReflectionUtils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
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

public class Summon extends Spell {

	List<Entity> en;

	public Summon() {
		en = new ArrayList<Entity>();
		Lang.add("spells.summon.fail", "The undead can't be spawned there!");
	}

	@Override
	public String getName() {
		return "summon";
	}

	@Override
	public String getDesc() {
		return "Summon the dead to fight with you";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 450;
	}

	@Override
	public boolean run(Player player, String[] args) {
		if (canRun(player, args)) {
			Block block = player.getTargetBlock(null, 100);
			Location loc = block.getLocation();
			loc.setY(loc.getY() + 1);
			Skeleton skel = loc.getWorld().spawn(loc, Skeleton.class);
			skel.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			skel.setMetadata("owner", new FixedMetadataValue(Zephyrus.getPlugin(), player.getName()));
			en.add(skel);
			new End(skel).runTaskLater(Zephyrus.getPlugin(), getConfig().getInt(getName() + ".duration") * 20);
			for (Entity e : skel.getNearbyEntities(20, 20, 20)) {
				if (e instanceof LivingEntity && e != player) {
					Object m = ReflectionUtils.getHandle(skel);
					Object tar = ReflectionUtils.getHandle(e);
					Method method = ReflectionUtils.getMethod(m.getClass(), "setGoalTarget");
					try {
						method.invoke(m, tar);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
			}
			return true;
		} else {
			Lang.errMsg("spells.summon.fail", player);
			return false;
		}
	}

	public boolean canRun(Player player, String[] args) {
		Block block = player.getTargetBlock(null, 100);
		Location loc = block.getLocation();
		loc.setY(loc.getY() + 1);
		Block block2 = loc.getBlock();
		loc.setY(loc.getY() + 1);
		Block block3 = loc.getBlock();
		return block != null && block.getType() != Material.AIR && block2.getType() == Material.AIR
				&& block3.getType() == Material.AIR;
	}

	@Override
	public void onDisable() {
		for (Entity entity : en) {
			entity.remove();
		}
	}

	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 60);
		return cfg;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.ROTTEN_FLESH, 64));
		i.add(new ItemStack(Material.BONE, 64));
		return i;
	}

	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (e.getEntityType() == EntityType.SKELETON && e.getTarget() instanceof Player
				&& e.getEntity().hasMetadata("owner")) {
			String s = e.getEntity().getMetadata("owner").get(0).asString();
			if (((Player) e.getTarget()).getName().equals(s)) {
				e.setCancelled(true);
			}
		}
	}

	private class End extends BukkitRunnable {

		LivingEntity entity;

		public End(LivingEntity e) {
			entity = e;
		}

		@Override
		public void run() {
			entity.damage(1000);
		}
	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.CREATION;
	}

	@Override
	public Element getElementType() {
		return Element.GENERIC;
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
