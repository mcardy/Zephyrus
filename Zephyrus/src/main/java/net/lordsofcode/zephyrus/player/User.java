package net.lordsofcode.zephyrus.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.effects.EffectType;
import net.lordsofcode.zephyrus.events.ManaChangeEvent;
import net.lordsofcode.zephyrus.events.PlayerGainXPEvent;
import net.lordsofcode.zephyrus.events.PlayerLevelUpEvent;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class User implements IUser {

	Player player;
	FileConfiguration cfg;

	public User(Player player) {
		this.player = player;
	}

	@Override
	public boolean isLearned(ISpell spell) {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		return (cfg.getStringList("learned").contains(spell.getName().toLowerCase()));
	}

	@Override
	public boolean hasPermission(ISpell spell) {
		if (Zephyrus.getConfig().getBoolean("OpKnowledge")) {
			if (player.hasPermission("zephyrus.cast." + spell.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasMana(int mana) {
		return Zephyrus.getManaMap().get(player.getName()) - mana >= 0;
	}

	@Override
	public int drainMana(int mana) {
		int level = getLevel();
		onManaChanged(mana);
		if (getMana() - mana > level * 100) {
			Zephyrus.getManaMap().put(player.getName(), level * 100);
			return level * 100;
		} else if (hasMana(mana)) {
			int getMana = getMana();
			Zephyrus.getManaMap().put(player.getName(), getMana - mana);
			return getMana - mana;
		} else {
			Zephyrus.getManaMap().put(player.getName(), 0);
			return 0;
		}
	}

	@Override
	public int getMana() {
		return Zephyrus.getManaMap().get(player.getName());
	}

	@Override
	public int levelUp() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		int current = cfg.getInt("level");
		current++;
		cfg.set("level", current);
		PlayerConfigHandler.saveConfig(player, cfg);
		player.sendMessage(ChatColor.AQUA + "You leveled up to level " + getLevel());
		if (Zephyrus.getConfig().getBoolean("Levelup-Sound")) {
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, 1);
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, 8);
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, -1);
		}
		PlayerLevelUpEvent event = new PlayerLevelUpEvent(player, current);
		Bukkit.getServer().getPluginManager().callEvent(event);
		return current;
	}

	@Override
	public int levelProgress(int progress) {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		PlayerGainXPEvent e = new PlayerGainXPEvent(player, progress);
		Bukkit.getPluginManager().callEvent(e);
		int levelBalance = Zephyrus.getConfig().getInt("LevelBalance");
		int current = cfg.getInt("progress");
		if (e.isCancelled()) {
			return current;
		}
		current = current + progress;
		int level = getLevel();
		while (current > (level * levelBalance) + (level * level + 100)) {
			current = current - ((level * levelBalance) + (level * level + 100));
			levelUp();
			level++;
		}
		cfg.set("progress", current);
		PlayerConfigHandler.saveConfig(player, cfg);
		return current;
	}

	@Override
	public int getLevelProgress() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		return cfg.getInt("progress");
	}

	@Override
	public int getLevel() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		return cfg.getInt("level");
	}

	@Override
	public void unLoadMana() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		cfg.set("mana", Zephyrus.getManaMap().get(player.getName()));
		PlayerConfigHandler.saveConfig(player, cfg);
		Zephyrus.getManaMap().remove(player.getName());
	}

	@Override
	public void loadMana() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		if (!Zephyrus.getManaMap().containsKey(player.getName())) {
			Zephyrus.getManaMap().put(player.getName(), cfg.getInt("mana"));
			new ManaRecharge(player.getName()).runTaskLater(Zephyrus.getPlugin(), Zephyrus.getManaRegenTime());
			onManaChanged(cfg.getInt("mana"));
		}
	}

	@Override
	public void reLoadMana() {
		Zephyrus.getManaMap().put(player.getName(), getLevel() * 100);
	}

	@Override
	public void displayMana() {
		new DisplayMana(player);
	}

	@Override
	public void displayMana(CommandSender sender) {
		new DisplayMana(sender);
	}

	@Override
	public void displayLevel() {
		new DisplayLevel(player);
	}

	@Override
	public void displayLevel(CommandSender sender) {
		new DisplayLevel(sender);
	}

	private class DisplayMana extends BukkitRunnable {

		CommandSender sender;

		DisplayMana(CommandSender sender) {
			this.sender = sender;
			this.run();
		}

		@Override
		public void run() {
			double maxMana = getLevel() * 100;
			double currentMana = getMana();
			double devider = maxMana / 120;
			StringBuffer full = new StringBuffer();
			StringBuffer empty = new StringBuffer();
			if (currentMana != 0) {
				double emptyMana = maxMana - currentMana;
				for (double i = currentMana / devider; i >= 0; i = i - 1) {
					full.append("|");
				}
				for (double i = emptyMana / devider; i >= 1; i = i - 1) {
					if (emptyMana >= 1) {
						empty.append("|");
					} else {
						break;
					}
				}
				sender.sendMessage(ChatColor.GOLD + "        ---===[" + ChatColor.RED + "Mana: " + currentMana + " / "
						+ maxMana + ChatColor.GOLD + "]===---");
				sender.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.AQUA + full + ChatColor.GRAY + empty
						+ ChatColor.DARK_AQUA + "}");
				sender.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.AQUA + full + ChatColor.GRAY + empty
						+ ChatColor.DARK_AQUA + "}");
			} else {
				for (int i = 120; i > 0; i = i - 1) {
					empty.append("|");
				}
				sender.sendMessage(ChatColor.GOLD + "              ---===[" + ChatColor.RED + "Mana: " + currentMana
						+ " / " + maxMana + ChatColor.GOLD + "]===---");
				sender.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY + empty + ChatColor.DARK_AQUA + "}");
				sender.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY + empty + ChatColor.DARK_AQUA + "}");
			}
		}
	}

	private class DisplayLevel extends BukkitRunnable {

		CommandSender sender;

		DisplayLevel(CommandSender sender) {
			this.sender = sender;
			this.run();
		}

		@Override
		public void run() {
			int levelBalance = Zephyrus.getConfig().getInt("LevelBalance");
			int level = getLevel();
			int currentLevelProg = getLevelProgress();
			int maxLevelProg = (level * levelBalance) + (level * level + 100);
			long devider = (maxLevelProg * 100) / 120;
			StringBuffer full = new StringBuffer();
			StringBuffer empty = new StringBuffer();
			if (level != 0) {
				if (currentLevelProg != 0) {
					double emptyLvl = maxLevelProg - currentLevelProg;
					for (long i = (currentLevelProg * 100) / devider; i >= 0; i = i - 1) {
						if (i != Double.POSITIVE_INFINITY) {
							full.append("|");
						}
					}
					for (double i = (emptyLvl * 100) / devider; i >= 1; i = i - 1) {
						if (emptyLvl >= 1) {
							empty.append("|");
						} else {
							break;
						}
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "  ---===[" + ChatColor.BLUE + "Level: " + level
							+ ChatColor.BOLD + "" + ChatColor.DARK_BLUE + " -=- " + ChatColor.BLUE + "Progress: "
							+ currentLevelProg + "/" + maxLevelProg + ChatColor.DARK_BLUE + "]===---");
					sender.sendMessage(ChatColor.DARK_GRAY + "{" + ChatColor.LIGHT_PURPLE + full + ChatColor.GRAY
							+ empty + ChatColor.DARK_GRAY + "}");
				} else {
					for (int i = 120; i > 0; i = i - 1) {
						empty.append("|");
					}
					sender.sendMessage(ChatColor.DARK_BLUE + "  ---===[" + ChatColor.BLUE + "Level: " + level
							+ ChatColor.DARK_BLUE + " -=- " + ChatColor.BLUE + "Progress: " + currentLevelProg + "/"
							+ maxLevelProg + ChatColor.DARK_BLUE + "]===---");
					sender.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY + empty + ChatColor.DARK_AQUA + "}");
				}

			} else {
				player.sendMessage(ChatColor.DARK_BLUE + "             ---===[" + ChatColor.BLUE + "Level: " + 0
						+ ChatColor.DARK_BLUE + "]===---");
			}
		}
	}

	@Override
	public void setDisplayMana(boolean b) {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		cfg.set("display-mana", b);
		PlayerConfigHandler.saveConfig(player, cfg);
	}

	@Override
	public boolean getDisplayMana() {
		if (cfg == null) {
			cfg = PlayerConfigHandler.getConfig(player);
		}
		return cfg.getBoolean("display-mana");
	}

	private void onManaChanged(int amount) {
		Bukkit.getPluginManager().callEvent(new ManaChangeEvent(this.player, amount));
	}
	
	@Override
	public void applyEffect(EffectType type, int time) {
		if (getEffectMap().containsKey(type.getID())) {
			time += getEffectMap().get(type.getID());
			getEffectMap().put(type.getID(), time);
		} else {
			getEffectMap().put(type.getID(), time);
			type.getEffect().onApplied(player);
		}
	}
	
	@Override
	public Collection<EffectType> getCurrentEffects() {
		Collection<EffectType> col = new ArrayList<EffectType>();
		for (Entry<Integer, Integer> entry : getEffectMap().entrySet()) {
			EffectType type = EffectType.values()[entry.getKey()];
			col.add(type);
		}
		return col;
	}
	
	@Override
	public int getEffectTime(EffectType type) {
		return getEffectMap().get(type.getID());
	}
	
	@Override
	public void removeEffect(EffectType type) {
		getEffectMap().remove(type.getID());
		type.getEffect().onRemoved(player);
	}
	
	private Map<Integer, Integer> getEffectMap() {
		return Zephyrus.getEffectMap().get(player.getName());
	}
	
	@Override
	public boolean hasEffect(EffectType type) {
		return getEffectMap().containsKey(type.getID());
	}

}
