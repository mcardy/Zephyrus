package minny.zephyrus.spells;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import minny.zephyrus.Zephyrus;

public class Feed extends Spell{

	public Feed(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
	}

	@Override
	public String name() {
		return "feed";
	}

	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player) {
		player.setFoodLevel(player.getFoodLevel() + 1);
	}

	@Override
	public ItemStack spellItem() {
		return null;
	}

}
