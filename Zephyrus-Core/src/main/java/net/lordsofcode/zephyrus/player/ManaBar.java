package net.lordsofcode.zephyrus.player;

import java.util.HashMap;
import java.util.Map;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.ManaChangeEvent;
import net.lordsofcode.zephyrus.nms.IDragon;
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

	public static final int MAX_HEALTH = 200;

	@EventHandler
	public void onManaChange(ManaChangeEvent event) {
		Player player = event.getPlayer();
		IUser user = Zephyrus.getUser(player);
		if (user.getDisplayMana()) {
			setStatus(player, ChatColor.DARK_AQUA + "---{" + ChatColor.BOLD + ChatColor.AQUA + user.getMana() + "/"
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---",
					(int) ((float) user.getMana() / (float) (user.getLevel())), false);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				IDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyPacket();
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
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---",
					(int) ((float) user.getMana() / (float) (user.getLevel())), true);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				IDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyPacket();
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
					+ user.getLevel() * 100 + ChatColor.RESET + ChatColor.DARK_AQUA + "}---",
					(int) ((float) user.getMana() / (float) (user.getLevel())), true);
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				IDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyPacket();
				NMSHandler.sendPacket(player, destroyPacket);
				manaDisplayMap.remove(player.getName());
			}
		}
	}

	private Map<String, IDragon> manaDisplayMap = new HashMap<String, IDragon>();

	public void setStatus(Player player, String text, int percent, boolean reset) {
		IDragon dragon = null;

		if (manaDisplayMap.containsKey(player.getName()) && !reset) {
			dragon = manaDisplayMap.get(player.getName());
		} else {
			dragon = NMSHandler.getDragon(text, player.getLocation().add(0, -200, 0), percent);
			Object mobPacket = dragon.getSpawnPacket();
			NMSHandler.sendPacket(player, mobPacket);
			manaDisplayMap.put(player.getName(), dragon);
		}

		if (text == "") {
			Object destroyPacket = dragon.getDestroyPacket();
			NMSHandler.sendPacket(player, destroyPacket);
			manaDisplayMap.remove(player.getName());
		} else {
			dragon.setName(text);
			dragon.setHealth(percent);
			Object metaPacket = dragon.getMetaPacket(dragon.getWatcher());
			Object teleportPacket = dragon.getTeleportPacket(player.getLocation().add(0, -200, 0));
			NMSHandler.sendPacket(player, metaPacket);
			NMSHandler.sendPacket(player, teleportPacket);
		}
	}

}
