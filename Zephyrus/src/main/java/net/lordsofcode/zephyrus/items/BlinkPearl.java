package net.lordsofcode.zephyrus.items;

import net.lordsofcode.zephyrus.PluginHook;
import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Effects;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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

public class BlinkPearl extends CustomItem {

	public BlinkPearl() {
		Lang.add("blinkpearl.outofrange", "That location is out of range!");
		Lang.add("blinkpearl.recharge", "The BlinkPearl still needs [TIME] to recharge...");
		Lang.add("blinkpearl.noblink", "You can't blink there!");
	}

	@Override
	public String getName() {
		return ChatColor.getByChar("1") + "Blink Pearl";
	}

	@Override
	public ItemStack getItem() {
		ItemStack i = new ItemStack(Material.ENDER_PEARL);
		setItemName(i, getName());
		setItemLevel(i, 1);
		i.addEnchantment(Zephyrus.getInstance().glow, 1);
		return i;
	}

	@Override
	public Recipe getRecipe() {
		ItemStack blinkPearl = getItem();
		ShapedRecipe recipe = new ShapedRecipe(blinkPearl);
		recipe.shape("CCC", "BAB", "CCC");
		recipe.setIngredient('C', Material.ENDER_PEARL);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('A', Material.EYE_OF_ENDER);
		return recipe;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void blink(PlayerInteractEvent e) {
		if (checkName(e.getPlayer().getItemInHand(), getName())) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK
						&& e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
					return;
				}
				if (!ItemDelay.hasDelay(e.getPlayer(), this)) {
					if (e.getPlayer().getTargetBlock(null, 100) != null
							&& e.getPlayer().getTargetBlock(null, 100).getType() != Material.AIR) {
						Location loc = e.getPlayer().getTargetBlock(null, 100).getLocation();
						loc.setX(loc.getX() + 0.5);
						loc.setY(loc.getY() + 0.25);
						loc.setZ(loc.getZ() + 0.5);
						loc.setPitch(e.getPlayer().getLocation().getPitch());
						loc.setYaw(e.getPlayer().getLocation().getYaw());
						Location loc2 = loc;
						loc2.setY(loc2.getY() + 1);
						Block block = loc.getBlock();
						Block block2 = loc2.getBlock();
						if (block.getType() == Material.AIR && block2.getType() == Material.AIR) {
							if (PluginHook.canBuild(e.getPlayer(), e.getPlayer().getTargetBlock(null, 100))) {
								Effects.playEffect(ParticleEffects.TOWN_AURA, loc, 1, 1, 1, 1, 10);
								Effects.playEffect(ParticleEffects.ENDER, e.getPlayer().getLocation(), 1, 1, 1, 1, 16);
								e.getPlayer().getWorld()
										.playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
								e.getPlayer().teleport(loc);
								e.getPlayer().getWorld()
										.playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
								int delay = delayFromLevel(getItemLevel(e.getItem()));
								ItemDelay.setDelay(e.getPlayer(), this, delay);
							} else {
								Lang.errMsg("worldguard", e.getPlayer());
							}
						} else {
							Lang.errMsg("blinkpearl.noblink", e.getPlayer());
						}
					} else {
						Lang.errMsg("blinkpearl.outofrange", e.getPlayer());
					}
				} else {
					int time = ItemDelay.getDelay(e.getPlayer(), this);
					e.getPlayer().sendMessage(
							ChatColor.GRAY + Lang.get("blinkpearl.recharge").replace("[TIME]", time + ""));
				}
			}
		}
	}

	@Override
	public String getPerm() {
		return "blinkpearl";
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

}
