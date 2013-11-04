package net.lordsofcode.zephyrus.items;

import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class RodOfFire extends CustomItem {

	public RodOfFire() {
		Lang.add("firerod.recharge", "The Rod of Fire still needs [TIME] to recharge...");
	}

	@Override
	public String getName() {
		return ChatColor.getByChar("c") + "Rod of Fire";
	}

	@Override
	public int getMaxLevel() {
		return 9;
	}

	@Override
	public Recipe getRecipe() {
		ItemStack fire_rod = getItem();

		ShapedRecipe recipe = new ShapedRecipe(fire_rod);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('C', Material.DIAMOND);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('A', Material.BLAZE_ROD);
		return recipe;
	}

	@Override
	public ItemStack getItem() {
		ItemStack i = new ItemStack(Material.BLAZE_ROD);
		setItemName(i, getDisplayName());
		setItemLevel(i, 1);
		setGlow(i);
		return i;
	}

	@Override
	public int getReqLevel() {
		return 1;
	}

	@EventHandler
	public void fireball(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR && checkName(e.getPlayer().getItemInHand(), getDisplayName())
				&& !ItemDelay.hasDelay(e.getPlayer(), this) && getItemLevel(e.getPlayer().getItemInHand()) < 6) {
			if (PluginHook.canBuild(e.getPlayer(), e.getPlayer().getTargetBlock(null, 1000))) {
				Player player = e.getPlayer();
				SmallFireball fireball = player.launchProjectile(SmallFireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				int delay = delayFromLevel(getItemLevel(e.getItem()));
				ItemDelay.setDelay(e.getPlayer(), this, delay);
			} else {
				Lang.errMsg("worldguard", e.getPlayer());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR && checkName(e.getPlayer().getItemInHand(), getName())
				&& !ItemDelay.hasDelay(e.getPlayer(), this)) {

			if (PluginHook.canBuild(e.getPlayer(), e.getPlayer().getTargetBlock(null, 1000))) {
				Player player = e.getPlayer();
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				int delay = delayFromLevel(getItemLevel(e.getItem()));
				ItemDelay.setDelay(e.getPlayer(), this, delay);
			} else {
				Lang.errMsg("worldguard", e.getPlayer());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR && checkName(e.getPlayer().getItemInHand(), getName())) {
			int time = ItemDelay.getDelay(e.getPlayer(), this);
			e.getPlayer().sendMessage(ChatColor.GRAY + Lang.get("firerod.recharge").replace("[TIME]", time + ""));
		}
	}

	@Override
	public String getPerm() {
		return "firerod";
	}
}
