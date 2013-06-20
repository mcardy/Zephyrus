package minnymin3.exampleaddon;

import java.util.HashSet;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.hooks.PluginHook;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.spells.SpellType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus Example Addon
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ExampleWorldGuardSpell extends Spell {

	public ExampleWorldGuardSpell(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String bookText() {
		return "Destroies the block you are looking at";
	}

	@Override
	public int manaCost() {
		return 5;
	}

	@Override
	public String name() {
		return "break";
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@Override
	public void run(Player arg0, String[] args) {
		//Gets the player's target block. Null for transparent blocks and 100 for the range.
		Block block = arg0.getTargetBlock(null, 100);
		block.setType(Material.AIR);
	}

	@Override
	public Set<ItemStack> spellItems() {
		//Creates a new HashSet containing all the required items for crafting this spell
		Set<ItemStack> items = new HashSet<ItemStack>();
		//Adds all of the items
		items.add(new ItemStack(Material.IRON_PICKAXE));
		items.add(new ItemStack(Material.GOLD_PICKAXE));
		items.add(new ItemStack(Material.DIAMOND_PICKAXE));
		return items;
	}
	
	@Override
	public boolean canRun(Player player, String[] args) {
		//Checks if the area is protected by WorldGuard
		return PluginHook.canBuild(player, player.getTargetBlock(null, 100));
	}
	
	@Override
	public String failMessage() {
		//The message sent to the player when canBuild returns false
		return ChatColor.DARK_RED + "You do not have permission for this area!";
	}
	
	@Override
	public SpellType type() {
		return SpellType.EARTH;
	}
	
}
