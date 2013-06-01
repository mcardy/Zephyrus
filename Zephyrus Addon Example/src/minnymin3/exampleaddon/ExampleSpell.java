package minnymin3.exampleaddon;

import java.util.HashSet;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.spells.Spell;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus Example Addon
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ExampleSpell extends Spell {

	public ExampleSpell(Zephyrus plugin) {
		super(plugin);
	}

	//This is the text that appears in the SpellTome
	@Override
	public String bookText() {
		return "The spell will heal you";
	}

	//The mana cost of this spell multiplied by the ManaMultiplier (default 5, changeable in config file)
	@Override
	public int manaCost() {
		return 5;
	}

	//The name of the spell used when casting or binding
	@Override
	public String name() {
		return "regenerate";
	}

	//The required level for the spell. Should line up with the mana cost.
	@Override
	public int reqLevel() {
		return 1;
	}

	//The stuff the spell does
	@Override
	public void run(Player player, String[] args) {
		//As an example this spell heals the player
		player.setHealth(20);
	}

	//Making sure that the player isn't at full health
	@Override
	public boolean canRun(Player player, String[] args) {
		if (player.getHealth() == 20) {
			return false;
		} else {
			return true;
		}
	}
	
	//The message sent to the player when canRun fails
	@Override
	public String failMessage() {
		return "You already are healed";
	}
	
	//A HashSet containing all the items required to learn this spell
	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> items = new HashSet<ItemStack>();
		ItemStack item = new ItemStack(Material.APPLE);
		items.add(item);
		return items;
	}
	
}
