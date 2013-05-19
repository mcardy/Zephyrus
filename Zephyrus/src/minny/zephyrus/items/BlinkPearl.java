package minny.zephyrus.items;

import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;
import minny.zephyrus.utils.ParticleEffects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
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

	PluginHook hook;

	public BlinkPearl(Zephyrus plugin) {
		super(plugin);
		hook = new PluginHook();
	}

	@Override
	public String name() {
		return "¤1Blink Pearl";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.ENDER_PEARL);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		setItemLevel(i, 1);
		i.addEnchantment(plugin.glow, 1);
	}

	@Override
	public Recipe recipe() {
		ItemStack blinkPearl = new ItemStack(Material.ENDER_PEARL);
		createItem(blinkPearl);
		ShapedRecipe recipe = new ShapedRecipe(blinkPearl);
		recipe.shape("CCC", "BAB", "CCC");
		recipe.setIngredient('C', Material.ENDER_PEARL);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('A', Material.EYE_OF_ENDER);
		return recipe;
	}

	@Override
	public int maxLevel() {
		return 5;
	}

	@EventHandler
	public void blink(PlayerInteractEvent e) throws Exception {
		if (checkName(e.getPlayer().getItemInHand(), this.name())) {
			e.setCancelled(true);
			if (e.getAction() == Action.RIGHT_CLICK_AIR
					|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!plugin.blinkPearlDelay
						.containsKey(e.getPlayer().getName())) {
					if (e.getPlayer().getTargetBlock(null, 100) != null
							&& e.getPlayer().getTargetBlock(null, 100)
									.getType() != Material.AIR) {
						Location loc = e.getPlayer().getTargetBlock(null, 100)
								.getLocation();
						loc.setY(loc.getY() + 1);
						loc.setPitch(e.getPlayer().getLocation().getPitch());
						loc.setYaw(e.getPlayer().getLocation().getYaw());
						Location loc2 = loc;
						loc2.setY(loc2.getY() + 1);
						Block block = loc.getBlock();
						Block block2 = loc2.getBlock();
						if (block.getType() == Material.AIR
								&& block2.getType() == Material.AIR) {
							if (PluginHook.worldGuard()) {
								PluginHook.hookWG();
								if (PluginHook.wg.canBuild(e.getPlayer(), e
										.getPlayer().getTargetBlock(null, 100))) {
									ParticleEffects.sendToLocation(
											ParticleEffects.TOWN_AURA, loc, 1,
											1, 1, 1, 10);
									ParticleEffects.sendToLocation(
											ParticleEffects.PORTAL, e
													.getPlayer().getLocation(),
											1, 1, 1, 1, 16);
									e.getPlayer()
											.getWorld()
											.playSound(
													e.getPlayer().getLocation(),
													Sound.ENDERMAN_TELEPORT,
													10, 1);
									e.getPlayer().teleport(loc);
									delay(plugin.blinkPearlDelay, plugin,
											delayFromLevel(getItemLevel(e
													.getPlayer()
													.getItemInHand())), e
													.getPlayer().getName());
								} else {
									e.getPlayer()
											.sendMessage(
													ChatColor.DARK_RED
															+ "You don't have permission for this area");
								}
							} else {
								ParticleEffects.sendToLocation(
										ParticleEffects.TOWN_AURA, loc, 1, 1,
										1, 1, 10);
								ParticleEffects.sendToLocation(
										ParticleEffects.PORTAL, e.getPlayer()
												.getLocation(), 1, 1, 1, 1, 16);
								e.getPlayer()
										.getWorld()
										.playSound(e.getPlayer().getLocation(),
												Sound.ENDERMAN_TELEPORT, 10, 1);
								e.getPlayer().teleport(loc);
								delay(plugin.blinkPearlDelay, plugin,
										delayFromLevel(getItemLevel(e
												.getPlayer().getItemInHand())),
										e.getPlayer().getName());
							}
						} else {
							e.getPlayer().sendMessage(
									ChatColor.GRAY + "Cannot blink there!");
						}
					} else {
						e.getPlayer().sendMessage(
								ChatColor.GRAY + "Location out of range!");
					}
				} else {
					int time = (Integer) plugin.blinkPearlDelay.get(e
							.getPlayer().getName());
					e.getPlayer().sendMessage(
							ChatColor.GRAY + "The BlinkPearl still needs "
									+ time + " seconds to recharge!");
				}
			}
		}
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (checkName(e.getRecipe().getResult(), this.name())) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.blinkpearl")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}
}
