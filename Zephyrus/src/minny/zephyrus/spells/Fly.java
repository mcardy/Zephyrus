package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Fly extends Spell {

	public Fly(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "fly";
	}

	@Override
	public String bookText() {
		return "Allows you to fly for 30 seconds.";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 50;
	}

	@Override
	public void run(Player player) {
		player.setAllowFlight(true);
		BukkitRunnable task = new RemoveFlightUtil(plugin, player);
		task.runTaskLater(plugin, 500);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		
		i.add(new ItemStack(Material.FEATHER, 32));
		
		return i;
	}
	
	@Override
	public boolean canRun(Player player) {
		return !player.getAllowFlight();
	}
	
	@Override
	public String failMessage() {
		return "You can already fly!";
	}

} class RemoveFlightUtil extends BukkitRunnable {

	Zephyrus plugin;
	Player player;
	
	public RemoveFlightUtil(Zephyrus plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	public void run() {
		player.sendMessage(ChatColor.GRAY + "5 seconds of flight remaining!");
		new RemoveFlight().runTaskLater(plugin, 100);
	}
	
	private class RemoveFlight extends BukkitRunnable {
		public void run() {
			player.setAllowFlight(false);
		}
	}
	
}
