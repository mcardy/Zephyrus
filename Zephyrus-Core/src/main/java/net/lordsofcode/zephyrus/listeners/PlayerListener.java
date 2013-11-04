package net.lordsofcode.zephyrus.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItem;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.events.PlayerCraftCustomItemEvent;
import net.lordsofcode.zephyrus.events.PlayerPreCastSpellEvent;
import net.lordsofcode.zephyrus.items.ItemUtil;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;
import net.lordsofcode.zephyrus.utils.PluginHook;

import org.bukkit.Bukkit;
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
		Lang.add("zephyrus.craft.nolevel", "$7You are not high enough level to craft that item!");
		results = new HashMap<ItemStack, ICustomItem>();
		for (ICustomItem item : Zephyrus.getItemManager().getItemMap()) {
			if (item.getRecipe() != null) {
				results.put(item.getRecipe().getResult(), item);
			}
		}
	}

	@EventHandler
	public void craftingHandler(PrepareItemCraftEvent e) {
		if (results.containsKey(e.getRecipe().getResult())) {
			ICustomItem item = results.get(e.getRecipe().getResult());
			List<HumanEntity> players = e.getViewers();
			PlayerCraftCustomItemEvent event = new PlayerCraftCustomItemEvent(players, item, e);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				e.getInventory().setResult(null);
			}
			for (HumanEntity en : e.getViewers()) {
				if (en instanceof Player) {
					Player player = (Player) en;
					if (!player.hasPermission("zephyrus.craft." + item.getPerm())) {
						e.getInventory().setResult(null);
					}
					if (Zephyrus.getUser(player).getLevel() < item.getReqLevel()) {
						Lang.msg("zephyrus.craft.nolevel", player);
						e.getInventory().setResult(null);
					}
				}
			}
		}
	}

	@EventHandler
	public void playerFile(PlayerJoinEvent e) {
		File playerFiles = new File(Zephyrus.getPlugin().getDataFolder(), "Players");
		File checkPlayer = new File(playerFiles, e.getPlayer().getName() + ".yml");
		Player player = e.getPlayer();
		if (!checkPlayer.exists()) {
			FileConfiguration cfg = PlayerConfigHandler.getConfig(player);
			PlayerConfigHandler.saveDefaultConfig(player);
			if (!cfg.contains("level") || cfg == null) {
				cfg.set("level", 1);
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
		} else {
			FileConfiguration cfg = PlayerConfigHandler.getConfig(player);
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
	public void sideEffect(PlayerPreCastSpellEvent e) {
		if (e.isSideEffect()) {
			return;
		}
		if (Zephyrus.getConfig().getBoolean("Enable-Side-Effects")
				|| !Zephyrus.getConfig().contains("Enable-Side-Effects")) {
			int chanceMultiplier = Zephyrus.getPlugin().getConfig().getInt("Side-Effect-Chance");
			if (chanceMultiplier < 1) {
				chanceMultiplier = 1;
			}
			Random rand = new Random();
			int chance = Zephyrus.getUser(e.getPlayer()).getLevel() * chanceMultiplier;
			if (rand.nextInt(chance) == 0) {
				boolean b = e.getSpell().sideEffect(e.getPlayer(), e.getArgs());
				PlayerPreCastSpellEvent ev = new PlayerPreCastSpellEvent(e.getPlayer(), e.getSpell(), e.getArgs(), true);
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

	@EventHandler
	public void onRegionCast(PlayerPreCastSpellEvent e) {
		if (!PluginHook.canCast(e.getPlayer(), e.getPlayer().getLocation())) {
			e.setCancelled(true);
			Lang.errMsg("nospellzone", e.getPlayer());
		}
	}

}
