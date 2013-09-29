package net.lordsofcode.zephyrus.player.mana;

import java.util.HashMap;
import java.util.Map;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.ManaChangeEvent;
import net.lordsofcode.zephyrus.utils.ReflectionUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
					/ ((float) user.getLevel() * 100));
		} else {
			if (manaDisplayMap.containsKey(player.getName())) {
				DummyDragon dragon = manaDisplayMap.get(player.getName());
				Object destroyPacket = dragon.getDestroyEntityPacket();
				ReflectionUtils.sendPacket(player, destroyPacket);
				manaDisplayMap.remove(player.getName());
			}
		}
	}

	private Integer ENTITY_ID = 6000;
	private Map<String, DummyDragon> manaDisplayMap = new HashMap<String, DummyDragon>();

	public void setStatus(Player player, String text, float percent) {
		DummyDragon dragon = null;

		if (manaDisplayMap.containsKey(player.getName())) {
			dragon = manaDisplayMap.get(player.getName());
		} else {
			dragon = new DummyDragon(text, ENTITY_ID, player.getLocation().add(0, -200, 0), percent
					* DummyDragon.MAX_HEALTH, false);
			Object mobPacket = dragon.getMobPacket();
			ReflectionUtils.sendPacket(player, mobPacket);
			manaDisplayMap.put(player.getName(), dragon);
		}

		if (text == "") {
			Object destroyPacket = dragon.getDestroyEntityPacket();
			ReflectionUtils.sendPacket(player, destroyPacket);
			manaDisplayMap.remove(player.getName());
		} else {
			dragon.name = text;
			dragon.health = percent * DummyDragon.MAX_HEALTH;
			Object metaPacket = dragon.getMetadataPacket(dragon.getWatcher());
			Object teleportPacket = dragon.getTeleportPacket(player.getLocation().add(0, -200, 0));
			ReflectionUtils.sendPacket(player, metaPacket);
			ReflectionUtils.sendPacket(player, teleportPacket);
		}
	}

}