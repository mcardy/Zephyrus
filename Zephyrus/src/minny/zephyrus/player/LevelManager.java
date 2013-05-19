package minny.zephyrus.player;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LevelManager {

	public static Zephyrus plugin;
	private int levelBalance;

	public LevelManager(Zephyrus plugin) {
		LevelManager.plugin = plugin;
	}

	public void levelUp(Player player) {
		PlayerConfigHandler.reloadConfig(plugin, player);
		int current = PlayerConfigHandler.getConfig(plugin, player).getInt(
				"Level");
		current = current + 1;
		PlayerConfigHandler.getConfig(plugin, player).set("Level", current);
		PlayerConfigHandler.saveConfig(plugin, player);
		PlayerConfigHandler.reloadConfig(plugin, player);
		Zephyrus.mana.put(player.getName(), current * 100);
		player.sendMessage(ChatColor.AQUA + "You leveled up to level "
				+ getLevel(player));
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, 1);
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, 8);
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 2, -1);
	}

	public void levelProgress(Player player, int amount) {
		levelBalance = plugin.getConfig().getInt("LevelBalance");
		PlayerConfigHandler.reloadConfig(plugin, player);
		int current = PlayerConfigHandler.getConfig(plugin, player).getInt(
				"progress");
		current = current + amount;
		if (current > getLevel(player) * levelBalance - 12) {
			current = current - (getLevel(player) * levelBalance - 12);
			levelUp(player);
		}
		PlayerConfigHandler.getConfig(plugin, player).set("progress", current);
		PlayerConfigHandler.saveConfig(plugin, player);
		PlayerConfigHandler.reloadConfig(plugin, player);
	}

	public static int getLevelProgress(Player player) {
		PlayerConfigHandler.reloadConfig(plugin, player);
		int current = PlayerConfigHandler.getConfig(plugin, player).getInt(
				"progress");
		return current;
	}

	public static int getLevel(Player player) {
		PlayerConfigHandler.reloadConfig(plugin, player);
		return PlayerConfigHandler.getConfig(plugin, player).getInt("Level");
	}

	public int getLevel(HumanEntity player) {
		Player p = (Player) player;
		PlayerConfigHandler.reloadConfig(plugin, p);
		return PlayerConfigHandler.getConfig(plugin, p).getInt("Level");
	}

	public static void resetMana(Player player) {
		Zephyrus.mana.put(player.getName(), getLevel(player) * 100);
	}

	public static int getMana(Player player) {
		try {
			return (Integer) Zephyrus.mana.get(player.getName());
		} catch (Exception e) {
			resetMana(player);
			return getLevel(player) * 100;
		}
	}

	public static void saveMana(Player player) {
		PlayerConfigHandler.getConfig(plugin, player).set("mana",
				Zephyrus.mana.get(player.getName()));
		PlayerConfigHandler.saveConfig(plugin, player);
	}

	public static int loadMana(Player player) {
		int i = PlayerConfigHandler.getConfig(plugin, player).getInt("mana");
		return i;
	}

	public static void drainMana(Player player, int amount) {
		Zephyrus.mana.put(player.getName(),
				(Integer) Zephyrus.mana.get(player.getName()) - amount);
	}

	public void displayMana(Player player) {
		new DisplayMana(player).run();
	}

	public void displayLevel(Player player) {
		new DisplayLevel(player).run();
	}
	
	private class DisplayMana extends BukkitRunnable {
		
		Player player;
		
		DisplayMana(Player player) {
			this.player = player;
		}
		
		public void run() {
			double maxMana = getLevel(player) * 100;
			double currentMana = getMana(player);
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
				player.sendMessage(ChatColor.GOLD + "        ---===["
						+ ChatColor.RED + "Mana: " + currentMana + " / " + maxMana
						+ ChatColor.GOLD + "]===---");
				player.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.AQUA
						+ full + ChatColor.GRAY + empty + ChatColor.DARK_AQUA + "}");
				player.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.AQUA
						+ full + ChatColor.GRAY + empty + ChatColor.DARK_AQUA + "}");
			} else {
				for (int i = 120; i > 0; i = i - 1) {
					empty.append("|");
				}
				player.sendMessage(ChatColor.GOLD + "              ---===["
						+ ChatColor.RED + "Mana: " + currentMana + " / " + maxMana
						+ ChatColor.GOLD + "]===---");
				player.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY
						+ empty + ChatColor.DARK_AQUA + "}");
				player.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY
						+ empty + ChatColor.DARK_AQUA + "}");
			}
		}
	}
	
	private class DisplayLevel extends BukkitRunnable {
		
		Player player;
		
		DisplayLevel(Player player) {
			this.player = player;
		}
		
		public void run() {
			levelBalance = plugin.getConfig().getInt("LevelBalance");
			int level = getLevel(player);
			int currentLevelProg = getLevelProgress(player);
			int maxLevelProg = level * levelBalance - 12;
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
					player.sendMessage(ChatColor.DARK_BLUE + "  ---===["
							+ ChatColor.BLUE + "Level: " + level + ChatColor.BOLD
							+ "" + ChatColor.DARK_BLUE + " -=- " + ChatColor.BLUE
							+ "Progress: " + currentLevelProg + "/" + maxLevelProg
							+ ChatColor.DARK_BLUE + "]===---");
					player.sendMessage(ChatColor.DARK_GRAY + "{"
							+ ChatColor.LIGHT_PURPLE + full + ChatColor.GRAY
							+ empty + ChatColor.DARK_GRAY + "}");
				} else {
					for (int i = 120; i > 0; i = i - 1) {
						empty.append("|");
					}
					player.sendMessage(ChatColor.DARK_BLUE + "  ---===["
							+ ChatColor.BLUE + "Level: " + level
							+ ChatColor.DARK_BLUE + " -=- " + ChatColor.DARK_BLUE
							+ "Progress: " + currentLevelProg + "/" + maxLevelProg
							+ ChatColor.DARK_BLUE + "]===---");
					player.sendMessage(ChatColor.DARK_AQUA + "{" + ChatColor.GRAY
							+ empty + ChatColor.DARK_AQUA + "}");
				}

			} else {
				player.sendMessage(ChatColor.DARK_BLUE + "             ---===["
						+ ChatColor.BLUE + "Level: " + 0 + ChatColor.DARK_BLUE
						+ "]===---");
			}
		}
	}

}
