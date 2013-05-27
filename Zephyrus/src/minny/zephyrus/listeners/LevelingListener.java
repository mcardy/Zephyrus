package minny.zephyrus.listeners;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.player.LevelManager;

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
				lvl.levelProgress(player, 4);
			} else if (en instanceof Player) {
				lvl.levelProgress(player, 6);
			} else {
				lvl.levelProgress(player, 1);
			}
		}
	}

	@EventHandler
	public void onCast(SpellCastEvent e) {
		lvl.levelProgress(e.getPlayer(), e.getSpell().manaCost() / 2);
	}
}
