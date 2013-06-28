package net.lordsofcode.zephyrus.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.events.PlayerCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerLevelUpEvent;
import net.lordsofcode.zephyrus.player.LevelManager;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LevelingListener implements Listener {

	LevelManager lvl;
	Zephyrus plugin;

	public LevelingListener(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
		Lang.add("levelling.nonew", "You have not learned any new spells");
		Lang.add("levelling.newspells", "You have learned");
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player player = e.getEntity().getKiller();
			Entity en = e.getEntity();
			if (en instanceof Monster) {
				lvl.levelProgress(player, 2);
			} else if (en instanceof Player) {
				lvl.levelProgress(player, 4);
			}
		}
	}

	@EventHandler
	public void onCast(PlayerCastSpellEvent e) {
		float f = e.getSpell().getManaCost() / 3;
		lvl.levelProgress(e.getPlayer(), Math.round(f) + 1);
	}

	@EventHandler
	public void onLevelUp(final PlayerLevelUpEvent e) {
		if (plugin.getConfig().getBoolean("Levelup-Spells")) {
			Player player = e.getPlayer();
			List<String> l = new ArrayList<String>();
			List<String> learned = PlayerConfigHandler
					.getConfig(plugin, player).getStringList("learned");
			for (Spell spell : Zephyrus.spellMap.values()) {
				if (spell.getLevel() == e.getLevel()) {
					learned.add(spell.getDisplayName().toLowerCase());
					l.add(spell.getDisplayName().toLowerCase());
				}
			}
			FileConfiguration cfg = PlayerConfigHandler.getConfig(plugin,
					player);
			cfg.set("learned", learned);
			PlayerConfigHandler.saveConfig(plugin, player, cfg);
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = l.iterator();
			while (it.hasNext()) {
				String str = it.next();
				if (it.hasNext()) {
					sb.append(", " + str);
				} else if (sb.length() != 0) {
					sb.append(" and " + str);
				} else {
					sb.append(str);
				}
			}
			String str = sb.toString();
			if (str.equals("") || sb.length() == 0) {
				player.sendMessage(ChatColor.AQUA + Lang.get("levelling.nonew"));
			} else {
				player.sendMessage(ChatColor.AQUA + Lang.get("levelling.newspells")
						+ ChatColor.DARK_AQUA + str.replaceFirst(",", ""));
			}
		}
	}
}
