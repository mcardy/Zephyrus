package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Feather extends Spell {

	Map<String, Integer> list;

	public Feather() {
		list = new HashMap<String, Integer>();
		Lang.add("spells.feather.applied", "You'll be light as a feather for [TIME] seconds!");
		Lang.add("spells.feather.warning", "$7You start to feel heavier");
		Lang.add("spells.feather.end", "$7You feel much heavier");
	}

	@Override
	public String getName() {
		return "feather";
	}

	@Override
	public String getDesc() {
		return "Makes you fall like a feather";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public boolean run(Player player, String[] args) {
		int t = getConfig().getInt(getName() + ".duration");
		if (list.containsKey(player.getName())) {
			list.put(player.getName(), list.get(player.getName()) + t);
			player.sendMessage(Lang.get("spells.feather.applied").replace("[TIME]", list.get(player.getName()) + ""));
		} else {
			list.put(player.getName(), t);
			player.sendMessage(Lang.get("spells.feather.applied").replace("[TIME]", list.get(player.getName()) + ""));
			new FeatherRunnable(player).runTaskLater(Zephyrus.getPlugin(), 20);
		}
		return true;
	}

	@Override
	public Set<ItemStack> items() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.FEATHER, 8));
		return s;
	}
	
	@Override
	public Map<String, Object> getConfiguration() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 120);
		return map;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (list.containsKey(e.getPlayer().getName())
				&& !e.getPlayer().isFlying()) {
			if (e.getFrom().getY() > e.getTo().getY()) {
				Location loc = e.getPlayer().getLocation();
				loc.setY(loc.getY() - 1);
				if (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
					Vector v = e.getPlayer().getVelocity();
					v.setY(v.getY() / 1.5);
					e.getPlayer().setVelocity(v);
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (list.containsKey(player.getName())
					&& e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (list.containsKey(e.getPlayer())) {
			list.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		if (list.containsKey(e.getPlayer())) {
			list.remove(e.getPlayer().getName());
		}
	}
	
	private class FeatherRunnable extends BukkitRunnable {

		Player player;

		FeatherRunnable(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			if (list.get(player.getName()) > 0) {
				if (list.get(player.getName()) == 5) {
					Lang.msg("spells.feather.warning", player);
				}
				list.put(player.getName(), list.get(player.getName()) - 1);
				new FeatherRunnable(player).runTaskLater(Zephyrus.getPlugin(), 20);
			} else {
				list.remove(player.getName());
				Lang.msg("spells.feather.end", player);
			}
		}

	}

	@Override
	public EffectType getPrimaryType() {
		return EffectType.BUFF;
	}

	@Override
	public Element getElementType() {
		return Element.AIR;
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean sideEffect(Player player, String[] args) {
		return false;
	}

}
