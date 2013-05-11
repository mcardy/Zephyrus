package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;

public class Fireball extends Spell {

	PluginHook hook;
	
	public Fireball(Zephyrus plugin) {
		super(plugin);
		hook = new PluginHook();
	}

	@Override
	public String name() {
		return "fireball";
	}
	
	@Override
	public String bookText() {
		return "Fires a ¤cfireball ¤0on cast!";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 10;
	}

	@Override
	public void run(Player player) {
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
	public boolean canRun(Player player) {
		if (hook.worldGuard()) {
			hook.wgHook();
			if (hook.wg.canBuild(player, player.getTargetBlock(null, 1000))){
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String failMessage(){
		return ChatColor.DARK_RED + "You don't have permission for this area";
	}

}
