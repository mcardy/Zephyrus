package minnymin3.zephyrus.spells;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Fireball extends Spell {

	public Fireball(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "fireball";
	}

	@Override
	public String bookText() {
		return "Fires a fireball on cast!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public void run(Player player, String[] args) {
		player.launchProjectile(SmallFireball.class);
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.BLAZE_POWDER));
		i.add(new ItemStack(Material.COAL));
		return i;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		return PluginHook.canBuild(player, player.getTargetBlock(null, 1000));
	}

	@Override
	public String failMessage() {
		return ChatColor.DARK_RED + "You don't have permission for this area";
	}

	@Override
	public SpellType type() {
		return SpellType.FIRE;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		Random rand = new Random();
		player.setFireTicks(rand.nextInt(40));
		return false;
	}

}
