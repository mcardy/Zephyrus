package minny.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Armour extends Spell {

	public Armour(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "armour";
	}

	@Override
	public String bookText() {
		return "A set of magical armour that can be called whenever you need it! The armour will block all damage for 30 seconds!";
	}

	@Override
	public int reqLevel() {
		return 4;
	}

	@Override
	public int manaCost() {
		return 60;
	}

	@Override
	public void run(Player player) {
		ItemStack helm = new ItemStack(Material.GOLD_HELMET);
		ItemStack chest = new ItemStack(Material.GOLD_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.GOLD_LEGGINGS);
		ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
		ItemMeta meta = helm.getItemMeta();
		meta.setDisplayName("¤6Magic Armour");
		helm.setItemMeta(meta);
		chest.setItemMeta(meta);
		legs.setItemMeta(meta);
		boots.setItemMeta(meta);
		player.getInventory().setBoots(boots);
		player.getInventory().setLeggings(legs);
		player.getInventory().setChestplate(chest);
		player.getInventory().setHelmet(helm);
		new RemoveArmour(player).runTaskLater(plugin, 12000);
	}

	@Override
	public boolean canRun(Player player) {
		if (player.getInventory().getHelmet() == null
				&& player.getInventory().getChestplate() == null
				&& player.getInventory().getLeggings() == null
				&& player.getInventory().getBoots() == null) {
			return true;
		}
		return false;
	}

	@Override
	public String failMessage() {
		return "You can't be wearing armour!";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.GOLD_BOOTS));
		i.add(new ItemStack(Material.GOLD_LEGGINGS));
		i.add(new ItemStack(Material.GOLD_CHESTPLATE));
		i.add(new ItemStack(Material.GOLD_HELMET));
		return i;
	}

	private class RemoveArmour extends BukkitRunnable {

		Player player;

		RemoveArmour(Player player) {
			this.player = player;
		}

		@Override
		public void run() {
			player.getInventory().setArmorContents(null);
		}
	}

}
