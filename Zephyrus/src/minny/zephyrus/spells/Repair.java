package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Repair extends Spell{

	public Repair(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "repair";
	}

	@Override
	public String bookText() {
		return "Repairs your items! Extends your tools life by 30!";
	}
	
	@Override
	public int reqLevel() {
		return 0;
	}

	@Override
	public int manaCost() {
		return 12;
	}

	@Override
	public void run(Player player) {
		player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 30));
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
		return ChatColor.GRAY + "That item can't be repaired!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		items.add(new ItemStack(Material.ANVIL));
		return items;
	}

}
