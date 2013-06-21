package minnymin3.zephyrus.spells;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.items.SpellTome;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.utils.ConfigHandler;
import minnymin3.zephyrus.utils.ParticleEffects;
import minnymin3.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class Spell implements Listener {

	public Zephyrus plugin;
	public boolean listenerEnabled;
	public Set<String> playerMap;
	
	public Spell(Zephyrus plugin) {
		this.plugin = plugin;
		plugin.addSpell(this);
		playerMap = new HashSet<String>();
	}

	/**
	 * Defines the name of the spell
	 * @return The name of the spell
	 */
	public abstract String name();

	/**
	 * The text that appears in the SpellTome
	 * @return The SpellTome text
	 */
	public abstract String bookText();

	/**
	 * The level required by default to craft the spell
	 * @return The default required level
	 */
	public abstract int reqLevel();

	/**
	 * The mana required by default to cast the spell
	 * @return The default required mana
	 */
	public abstract int manaCost();

	/**
	 * The method called when the spell is cast
	 */
	public abstract void run(Player player, String[] args);

	/**
	 * The items that are used in crafting the spell
	 * @return
	 */
	public abstract Set<ItemStack> spellItems();
	
	/**
	 * The type of the spell
	 * @return A SpellType enum value
	 */
	public abstract SpellType type();

	/**
	 * Weather or not the spell can be bound to a wand
	 * @return True by default
	 */
	public boolean canBind() {
		return true;
	}

	/**
	 * A spell that is required for crafting this spell
	 * @return The required spell
	 */
	public Spell reqSpell() {
		return null;
	}

	/**
	 * The boolean dictating if the spell can be run
	 * @return Whether or not the spell can be cast
	 */
	public boolean canRun(Player player, String[] args) {
		return true;
	}

	/**
	 * The message that is sent to the player when canRun returns false
	 * @return "" by default
	 */
	public String failMessage() {
		return "";
	}
	
	/**
	 * If the spell is learned
	 * @return False if the spell is not learned
	 */
	public boolean isLearned(Player p, String name) {
		if (PlayerConfigHandler.getConfig(plugin, p).getStringList("learned")
				.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * The message sent to the player when they lack the required mana.
	 * @param p
	 */
	public void notEnoughMana(Player p) {
		p.sendMessage(ChatColor.DARK_GRAY + "Not enough mana!");
	}

	/**
	 * Checks if the player has permission to cast the spell. Returns false if
	 * OpKnowledge is false in the config
	 * 
	 * @param player
	 *            The target player
	 * @param spell
	 *            The target spell
	 */
	public boolean hasPermission(Player player, Spell spell) {
		if (plugin.getConfig().getBoolean("OpKnowledge")) {
			if (player.hasPermission("zephyrus.cast." + spell.name())) {
				return true;
			}
			if (player.isOp()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the mana cost from the config file.
	 * @return The configured mana cost.
	 */
	public int getManaCost() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		int cost = cfg.getConfig().getInt(this.name() + ".mana");
		return cost;
	}
	
	/**
	 * Gets the level from the config file.
	 * @return The configured level.
	 */
	public int getLevel() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		int level = cfg.getConfig().getInt(this.name() + ".level");
		return level;
	}
	
	/**
	 * Gets whether or not the spell is enabled from the config file.
	 * @return Whether or not the spell is enabled.
	 */
	public boolean isEnabled() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		return cfg.getConfig().getBoolean(this.name() + ".enabled");
	}
	
	/**
	 * A list of configurations defined by the spell
	 * @return The configurations
	 */
	public Map<String, Object> getConfigurations() {
		return null;
	}

	/**
	 * Gets the spell configuration
	 * @return A FileConfiguration of the config
	 */
	public FileConfiguration getConfig() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		return cfg.getConfig();
	}
	
	/**
	 * A method for things to be done on the disabling of the plugin
	 */
	public void onDisable() {
	}
	
	public void delayedAction(Player player) {
	}
	
	public void startDelay(Player player, int time) {
		new DelayedActionRunnable(this, player, time);
	}
	
	/**
	 * A side effect that may occur while casting the spell
	 * @param player The player who cast the spell
	 * @param args The arguments passed through the command
	 * @return True if the spell should be cancelled, false otherwise.
	 */
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}
	
	/**
	 * The compatible spell types. Combo spells should be able to handle all the given types.
	 * @return A list of compatible spell types
	 */
	public Set<SpellType> types() {
		Set<SpellType> types = new HashSet<SpellType>();
		return types;
	}
	
	/**
	 * The effects of the combo spell
	 * @param player The caster
	 * @param args The argument
	 * @param type The SpellType of spell being cast
	 * @param level The combined level of the combo spell spells
	 */
	public void comboSpell(Player player, String[] args, SpellType type, int level) {
	}
	
	/**
	 * Get the target entity of the player
	 * @param player The player
	 * @return The entity the player is looking at.
	 */
	public Entity getTarget(Player player) {
		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++) {
					for (int z = -acc; z < acc; z++) {
						for (int y = -acc; y < acc; y++) {
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) {
								return entity;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * The method for destroying a bookshelf and dropping a spelltome
	 */
	public void dropSpell(Block bookshelf, String name, String desc, Player player) {
		Random r = new Random();
		bookshelf.setType(Material.AIR);
		SpellTome tome = new SpellTome(plugin, name, desc);
		Location loc = bookshelf.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		loc.getWorld().dropItem(loc.add(0, +1, 0), tome.item())
				.setVelocity(new Vector(0, 0, 0));
		int chance = 1;
		if (LevelManager.getLevel(player) < 7) {
			chance = 1;
		} else if (LevelManager.getLevel(player) < 15) {
			chance = 2;
		} else {
			chance = 3;
		}
		loc.getWorld().dropItem(loc.add(0, +0.5, 0), new ItemStack(Material.BOOK, r.nextInt(chance)));
		try {
			ParticleEffects.sendToLocation(ParticleEffects.ENCHANTMENT_TABLE,
					loc, 0, 0, 0, 1, 30);
			loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 3, 12);
		} catch (Exception e) {}
	}
	
	private class DelayedActionRunnable extends BukkitRunnable {
		
		Player player;
		Spell spell;
		
		DelayedActionRunnable(Spell spell, Player player, int time) {
			this.spell = spell;
			this.player = player;
			this.runTaskLater(Zephyrus.getInstance(), time);
		}
		
		public void run() {
			spell.delayedAction(player);
		}
	}
}