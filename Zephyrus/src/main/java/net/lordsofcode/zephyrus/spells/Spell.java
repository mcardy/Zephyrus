package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.utils.ConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class Spell implements ISpell {

	public Set<String> playerMap = new HashSet<String>();

	@Override
	public String getDisplayName() {
		if (getConfig().contains(getName() + ".displayname")) {
			return getConfig().getString(getName() + ".displayname");
		}
		return getName();
	}

	@Override
	public String getDisplayDesc() {
		if (getConfig().contains(getName() + ".desc")) {
			return getConfig().getString(getName() + ".desc");
		}
		return getName();
	}

	@Override
	public int getReqLevel() {
		if (getConfig().contains(getName() + ".level")) {
			return getConfig().getInt(getName() + ".level");
		}
		return reqLevel();
	}

	@Override
	public int getManaCost() {
		if (getConfig().contains(getName() + ".mana")) {
			return getConfig().getInt(getName() + ".mana");
		}
		return reqLevel();
	}

	@Override
	public Set<ItemStack> getItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		// TODO Configuration for items
		return items;
	}

	@Override
	public ISpell getRequiredSpell() {
		return null;
	}

	@Override
	public int getExp() {
		if (getConfig().contains(getName() + ".exp")) {
			return getConfig().getInt(getName() + ".exp");
		}
		return reqLevel();
	}

	@Override
	public boolean isEnabled() {
		if (getConfig().contains(getName() + ".enabled")) {
			return getConfig().getBoolean(getName() + ".enabled");
		}
		return true;
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean comboSpell(Player player, String[] args, EffectType type,
			Element element, int power) {
		return false;
	}

	@Override
	public boolean canBind() {
		return true;
	}

	/**
	 * An action that can be called in the future with the startDelay method
	 * 
	 * @param player
	 *            The player passed in the startDelay action, usually the player
	 *            who cast the spell
	 */
	public void delayedAction(Player player) {
	}

	/**
	 * Starts the delayed timer for the delayed action. Usually used on spell
	 * cast.
	 * 
	 * @param player
	 *            The player who cast the spell and whom to pass to the
	 *            delayedAction method
	 * @param time
	 *            The amount of time, in ticks, to wait to fire the delayed
	 *            action
	 */
	public void startDelay(Player player, int time) {
		new DelayedActionRunnable(this, player, time);
	}

	/**
	 * Gets the file configuration of spells.yml
	 */
	public FileConfiguration getConfig() {
		return new ConfigHandler("spells.yml").getConfig();
	}

	/**
	 * Checks if the player's target block can be broken
	 * 
	 * @param player
	 *            The player to check
	 * @return True if the block cannot be broken
	 */
	public boolean blockBreak(Player player) {
		return blockBreak(player, player.getTargetBlock(null, 1000));
	}

	/**
	 * Checks if the block can be broken
	 * 
	 * @param player
	 *            The player who broke the block
	 * @param block
	 *            The block to check
	 * @return True if the block cannot be broken
	 */
	public boolean blockBreak(Player player, Block block) {
		BlockBreakEvent e = new BlockBreakEvent(block, player);
		Bukkit.getPluginManager().callEvent(e);
		return e.isCancelled();
	}

	/**
	 * Gets the player's target entity
	 * 
	 * @param player
	 *            The player to get the target of
	 * @return Null if there is no target
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

	private class DelayedActionRunnable extends BukkitRunnable {

		Player player;
		Spell spell;

		DelayedActionRunnable(Spell spell, Player player, int time) {
			this.spell = spell;
			this.player = player;
			this.runTaskLater(Zephyrus.getPlugin(), time);
		}

		@Override
		public void run() {
			spell.delayedAction(player);
		}
	}

	/**
	 * Gets the spell from its default name. The spell map does not contain the
	 * default name which is why this method is needed.
	 * 
	 * @param s
	 *            The default name of the spell
	 * @return The ISpell instance of the spell from the loaded spellmap
	 */
	public static ISpell forName(String s) {
		return Zephyrus.getSpellMap().get(getSpellName(s));
	}

	/**
	 * Gets the spell's display name (the name that may have been edited in the
	 * config) from its default name.
	 * 
	 * @param defaultName
	 *            The default name of the spell
	 * @return The spell's display name
	 */
	public static String getSpellName(String defaultName) {
		FileConfiguration cfg = new ConfigHandler("spells.yml").getConfig();
		if (cfg.contains(defaultName + ".displayname")) {
			String displayName = cfg.getString(defaultName + ".displayname");
			return displayName;
		} else {
			return defaultName;
		}
	}

}
