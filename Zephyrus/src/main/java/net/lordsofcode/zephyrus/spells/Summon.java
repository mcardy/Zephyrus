package net.lordsofcode.zephyrus.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
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
	
	public Summon(Zephyrus plugin) {
		super(plugin);
		en = new ArrayList<Entity>();
	}

	@Override
	public String name() {
		return "summon";
	}

	@Override
	public String bookText() {
		return "Summons the undead to fight with you";
	}

	@Override
	public int reqLevel() {
		return 7;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player, String[] args) {
		Block block = player.getTargetBlock(null, 100);
		Location loc = block.getLocation();
		loc.setY(loc.getY() + 1);
		Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		zombie.setMetadata("owner", new FixedMetadataValue(Zephyrus.getInstance(), player.getName()));
		en.add(zombie);
		new End(zombie).runTaskLater(Zephyrus.getInstance(), getConfig().getInt(this.name() + ".duration") * 20);
		for (Entity e : zombie.getNearbyEntities(20, 20, 20)) {
			if (e instanceof LivingEntity && e != player) {
				CraftLivingEntity m = (CraftLivingEntity) zombie;
				CraftLivingEntity tar = (CraftLivingEntity) e;
				m.getHandle().setGoalTarget(tar.getHandle());
				break;
			}
		}
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		Block block = player.getTargetBlock(null, 100);
		Location loc = block.getLocation();
		loc.setY(loc.getY() + 1);
		Block block2 = loc.getBlock();
		loc.setY(loc.getY() + 1);
		Block block3 = loc.getBlock();
		return block != null && block.getType() != Material.AIR && block2.getType() == Material.AIR && block3.getType() == Material.AIR;
	}
	
	@Override
	public String failMessage() {
		return "You can't spawn a mob there!";
	}
	
	@Override
	public void onDisable() {
		for (Entity entity : en) {
			entity.remove();
		}
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 60);
		return cfg;
	}
	
	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.ROTTEN_FLESH, 64));
		i.add(new ItemStack(Material.BONE, 64));
		return i;
	}

	@Override
	public SpellType type() {
		return SpellType.CONJURE;
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (e.getEntityType() == EntityType.ZOMBIE && e.getTarget() instanceof Player && e.getEntity().hasMetadata("owner")) {
			String s = e.getEntity().getMetadata("owner").get(0).asString();
			if (((Player)e.getTarget()).getName().equals(s)) {
				e.setCancelled(true);
			}
		}
	}
	
	private class End extends BukkitRunnable {
		
		LivingEntity entity;
		
		public End(LivingEntity e) {
			entity = e;
		}
		
		public void run() {
			entity.damage(1000);
		}
	}
	
}
