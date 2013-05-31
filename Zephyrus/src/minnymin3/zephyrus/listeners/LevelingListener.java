package minnymin3.zephyrus.listeners;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.events.PlayerCastSpellEvent;
import minnymin3.zephyrus.player.LevelManager;

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
		float f = e.getSpell().manaCost() / 3;
		lvl.levelProgress(e.getPlayer(), Math.round(f) + 1);
	}
}
