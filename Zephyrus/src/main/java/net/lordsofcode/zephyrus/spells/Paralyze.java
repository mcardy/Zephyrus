package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

public class Paralyze extends Spell {

	public Paralyze(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "paralyze";
	}

	@Override
	public String bookText() {
		return "Stop your enemy dead in their tracks!";
	}

	@Override
	public int reqLevel() {
		return 6;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		LivingEntity en = (LivingEntity) getTarget(player);
		int time = getConfig().getInt(name() + ".duration");
		en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 100));
	}
	
	@Override
	public boolean canRun(Player player, String[] args) {
		Entity en = getTarget(player);
		return en != null && en instanceof LivingEntity;
	}
	
	@Override
	public String failMessage() {
		return "You do not have a target!";
	}
	
	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> cfg = new HashMap<String, Object>();
		cfg.put("duration", 10);
		return cfg;
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		//Slowness extended potions
		s.add(new ItemStack(Material.POTION, 1, (short) 8202));
		return s;
	}

	@Override
	public SpellType type() {
		return SpellType.OTHER;
	}

}
