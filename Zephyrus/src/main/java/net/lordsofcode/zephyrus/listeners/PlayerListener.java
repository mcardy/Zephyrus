package net.lordsofcode.zephyrus.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerCraftCustomItemEvent;
import net.lordsofcode.zephyrus.utils.ItemUtil;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;
import net.lordsofcode.zephyrus.utils.UpdateChecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class PlayerListener extends ItemUtil implements Listener {

	Map<ItemStack, ICustomItem> results;

	public PlayerListener() {
		results = new HashMap<ItemStack, ICustomItem>();
		for (String s : Zephyrus.getItemMap().keySet()) {
			ICustomItem item = Zephyrus.getItemMap().get(s);
			if (item.getRecipe() != null) {
				results.put(item.getRecipe().getResult(), item);
			}
		}
	}

	@EventHandler
	public void updateMessage(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("zephyrus.notify")) {
			Player player = e.getPlayer();
			if (UpdateChecker.isUpdate) {
				player.sendMessage(ChatColor.DARK_RED
						+ "Zephyrus is out of date!");
				player.sendMessage(ChatColor.DARK_RED
						+ "Get the new version at: " + ChatColor.GRAY
						+ "dev.bukkit.org/server-mods/Zephyrus");
				player.sendMessage(ChatColor.DARK_RED + "ChangeLog: "
						+ ChatColor.GRAY + UpdateChecker.changelog);
			}
		}
	}

	@EventHandler
	public void craftingHandler(PrepareItemCraftEvent e) {
		if (results.containsKey(e.getRecipe().getResult())) {
			ICustomItem item = results.get(e.getRecipe().getResult());
			List<HumanEntity> player = e.getViewers();
			PlayerCraftCustomItemEvent event = new PlayerCraftCustomItemEvent(
					player, item, e);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				e.getInventory().setResult(null);
			}
			for (HumanEntity en : e.getViewers()) {
				if (!en.hasPermission("zephyrus.craft." + item.getPerm())
						&& !en.hasPermission("zephyrus.craft.*")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}

	@EventHandler
	public void playerFile(PlayerJoinEvent e) {
		File playerFiles = new File(Zephyrus.getPlugin().getDataFolder(), "Players");
		File checkPlayer = new File(playerFiles, e.getPlayer().getName()
				+ ".yml");
		Player player = e.getPlayer();
		if (!checkPlayer.exists()) {
			FileConfiguration cfg = PlayerConfigHandler.getConfig(player);
			if (cfg.contains("mana") && cfg.contains("learned") && cfg.contains("progress")) {
				if (cfg.contains("Level")) {
					int level = cfg.getInt("Level");
					cfg.set("Level", null);
					cfg.set("level", level);
					PlayerConfigHandler.saveConfig(player, cfg);
					return;
				} else if (cfg.contains("level")) {
					return;
				}
			}
			PlayerConfigHandler.saveDefaultConfig(player);
			if (!cfg.contains("Level") || cfg == null) {
				cfg.set("Level", 1);
			}
			if (!cfg.contains("mana") || cfg == null) {
				cfg.set("mana", 100);
			}
			if (Zephyrus.getConfig().getBoolean("Levelup-Spells")) {
				for (ISpell spell : Zephyrus.getSpellMap().values()) {
					if (spell.getReqLevel() == 1 && spell.isEnabled()) {
						List<String> learned = cfg.getStringList("learned");
						learned.add(spell.getDisplayName().toLowerCase());
						cfg.set("learned", learned);
					}
				}
			} else {
				if (!cfg.contains("learned") || cfg == null) {
					cfg.set("learned", "[]");
				}
			}
			if (!cfg.contains("progress") || cfg == null) {
				cfg.set("progress", 0);
			}
			PlayerConfigHandler.saveConfig(player, cfg);
		}
	}

	@EventHandler
	public void setMana(PlayerJoinEvent e) {
		Zephyrus.getUser(e.getPlayer()).loadMana();
	}

	@EventHandler
	public void removeMana(PlayerQuitEvent e) {
		Zephyrus.getUser(e.getPlayer()).unLoadMana();
	}

	@EventHandler
	public void removeMana(PlayerKickEvent e) {
		Zephyrus.getUser(e.getPlayer()).unLoadMana();
	}

	@EventHandler
	public void onSpellCast(PlayerCastSpellEvent e) {
		if (e.isSideEffect()) {
			return;
		}
		if (Zephyrus.getConfig().getBoolean("Enable-Side-Effects")
				|| !Zephyrus.getConfig().contains("Enable-Side-Effects")) {
			int chanceMultiplier = Zephyrus.getPlugin().getConfig().getInt(
					"Side-Effect-Chance");
			if (chanceMultiplier < 1) {
				chanceMultiplier = 1;
			}
			Random rand = new Random();
			int chance = Zephyrus.getUser(e.getPlayer()).getLevel()
					* chanceMultiplier;
			if (rand.nextInt(chance) == 0) {
				boolean b = e.getSpell().sideEffect(e.getPlayer(), e.getArgs());
				PlayerCastSpellEvent ev = new PlayerCastSpellEvent(e.getPlayer(), e.getSpell(), e.getArgs(), true);
				Bukkit.getPluginManager().callEvent(ev);
				if (b == true) {
					e.setCancelled(true);
					if (!ev.isCancelled()) {
						Zephyrus.getUser(e.getPlayer()).drainMana(e.getSpell().getManaCost());
					}
				}
			}
		}
	}
}
