package minny.zephyrus.spells;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.inventory.ItemStack;

public class Fireball extends Spell {

	public Fireball(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
		plugin.spellCraftMap.put(this.spellItem(), this);
	}

	@Override
	public String name() {
		return "fireball";
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
	public ItemStack spellItem() {
		return new ItemStack(Material.COAL);
	}

}
