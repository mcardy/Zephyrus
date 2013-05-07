package minny.zephyrus.listeners;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.ItemUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ManaPotionListener implements Listener {

	Zephyrus plugin;
	ItemUtil util;
	LevelManager lvl;

	public ManaPotionListener(Zephyrus plugin) {
		this.plugin = plugin;
		this.util = new ItemUtil(plugin);
		this.lvl = new LevelManager(plugin);
	}

	@EventHandler
	public void onManaPotion(PlayerItemConsumeEvent e) {
		if (util.checkName(e.getItem(), "¤bMana Potion")) {
			Player player = e.getPlayer();
			plugin.mana.put(player.getName(),
					lvl.getLevel(player) * 100);
		}
	}

}
