package minny.zephyrus.spells;

import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Repair extends Spell{

	public Repair(Zephyrus plugin) {
		super(plugin);
		plugin.spellMap.put(this.name(), this);
	}

	@Override
	public String name() {
		return "repair";
	}

	@Override
	public String bookText() {
		return "";
	}
	
	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 60;
	}

	@Override
	public void run(Player player) {
		player.getItemInHand().setDurability(player.getItemInHand().getDurability());
	}
	
	@Override
	public boolean canRun(Player player) {
		if (player.getItemInHand() != null){
			if (player.getItemInHand().getDurability() < player.getItemInHand().getType().getMaxDurability() - 30){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String failMessage(){
		return ChatColor.GRAY + "Cannot repair that item!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		return null;
	}

}
