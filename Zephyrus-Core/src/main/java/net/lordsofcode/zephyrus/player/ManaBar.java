package net.lordsofcode.zephyrus.player;

import java.util.HashMap;
import java.util.Map;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.ManaChangeEvent;
import net.lordsofcode.zephyrus.nms.NMSHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ManaBar implements Listener {

	@EventHandler
	public void onManaChange(ManaChangeEvent event) {
		Player player = event.getPlayer();
		IUser user = Zephyrus.getUser(player);
		if (user.getDisplayMana()) {
			setStatus(player, ChatColor.DARK_AQUA + "---{" + ChatColor.BOLD + ChatColor.AQUA + user.getMana() + "/"
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---", user.getMana()
					/ ((float) user.getLevel() * 100), false);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				DummyDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyEntityPacket();
				NMSHandler.sendPacket(player, destroyPacket);
				manaDisplayMap.remove(player.getName());
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		IUser user = Zephyrus.getUser(player);
		if (user.getDisplayMana()) {
			setStatus(player, ChatColor.DARK_AQUA + "---{" + ChatColor.BOLD + ChatColor.AQUA + user.getMana() + "/"
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---", user.getMana()
					/ ((float) user.getLevel() * 100), true);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				DummyDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyEntityPacket();
				NMSHandler.sendPacket(player, destroyPacket);
				manaDisplayMap.remove(player.getName());
			}
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		IUser user = Zephyrus.getUser(player);
		if (user.getDisplayMana()) {
			setStatus(player, ChatColor.DARK_AQUA + "---{" + ChatColor.BOLD + ChatColor.AQUA + user.getMana() + "/"
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---", user.getMana()
					/ ((float) user.getLevel() * 100), true);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				DummyDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyEntityPacket();
				NMSHandler.sendPacket(player, destroyPacket);
				manaDisplayMap.remove(player.getName());
			}
		}
	}
	
	private Integer ENTITY_ID = 6000;
	private Map<String, DummyDragon> manaDisplayMap = new HashMap<String, DummyDragon>();

	public void setStatus(Player player, String text, float percent, boolean reset) {
		DummyDragon dragon = null;

		if (manaDisplayMap.containsKey(player.getName()) && !reset) {
			dragon = manaDisplayMap.get(player.getName());
		} else {
			dragon = new DummyDragon(text, ENTITY_ID, player.getLocation().add(0, -200, 0), percent
					* DummyDragon.MAX_HEALTH, false);
			Object mobPacket = dragon.getMobPacket();
			NMSHandler.sendPacket(player, mobPacket);
			manaDisplayMap.put(player.getName(), dragon);
		}

		if (text == "") {
			Object destroyPacket = dragon.getDestroyEntityPacket();
			NMSHandler.sendPacket(player, destroyPacket);
			manaDisplayMap.remove(player.getName());
		} else {
			dragon.name = text;
			dragon.health = percent * DummyDragon.MAX_HEALTH;
			Object metaPacket = dragon.getMetadataPacket(dragon.getWatcher());
			Object teleportPacket = dragon.getTeleportPacket(player.getLocation().add(0, -200, 0));
			NMSHandler.sendPacket(player, metaPacket);
			NMSHandler.sendPacket(player, teleportPacket);
		}
	}

}
