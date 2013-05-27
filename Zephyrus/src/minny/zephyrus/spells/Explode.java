package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Explode extends Spell {

	public Explode(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "explode";
	}

	@Override
	public String bookText() {
		return "Makes a big boom!";
	}

	@Override
	public int reqLevel() {
		return 8;
	}

	@Override
	public int manaCost() {
		return 100;
	}

	@Override
	public void run(Player player) {
		player.getWorld().createExplosion(
				player.getTargetBlock(null, 200).getLocation(), 2, true);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.TNT, 64));
		return i;
	}

	@Override
	public boolean canRun(Player player) {
		boolean b = PluginHook.canBuild(player,
				player.getTargetBlock(null, 1000))
				&& PluginHook.allowExplosion();
		return b;
	}

	@Override
	public String failMessage() {
		return ChatColor.DARK_RED
				+ "TNT Explosions are disabled at your location!";
	}

}
