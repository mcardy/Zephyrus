package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;

public class Fireball extends Spell {

	public Fireball(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
		plugin.spellCraftMap.put(this.spellItems(), this);
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
		return 50;
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

}
