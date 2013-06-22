package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Vision extends Spell {

	public Vision(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "vision";
	}

	@Override
	public String bookText() {
		return "You can see in the dark now!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		int t = getConfig().getInt(this.name() + ".duration");
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,
				t * 20, 1));
		player.sendMessage(ChatColor.GRAY + "You can now see in the dark!");
	}

	@Override
	public Set<ItemStack> spellItems() {
		// Potion extended Nightvision
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.POTION, 1, (short) 8262));
		return i;
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("duration", 30);
		return map;
	}

	@Override
	public SpellType type() {
		return SpellType.ILLUSION;
	}
	
	@Override
	public boolean sideEffect(Player player, String[] args) {
		int t = getConfig().getInt(this.name() + ".duration");
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, t*4, 1));
		return false;
	}

}
