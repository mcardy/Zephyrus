package minnymin3.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;

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

public class Vanish extends Spell {

	public Vanish(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "vanish";
	}

	@Override
	public String bookText() {
		return "Makes you dissappear!";
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
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
				t * 20, 1));
		player.sendMessage(ChatColor.GRAY + "You have dissappeared!");
	}

	@Override
	public Set<ItemStack> spellItems() {
		// Potion extended Invisibility
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.POTION, 1, (short) 8270));
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

}
