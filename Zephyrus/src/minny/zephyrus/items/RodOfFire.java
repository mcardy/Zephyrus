package minny.zephyrus.items;

import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.hooks.PluginHook;
import minny.zephyrus.player.LevelManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
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

public class RodOfFire extends CustomItem {

	LevelManager lvl;
	PluginHook hook;

	public RodOfFire(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
		hook = new PluginHook();
	}

	@Override
	public String name() {
		return "¤cRod of Fire";
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		setItemLevel(i, 1);
		setGlow(i);
	}

	@Override
	public int maxLevel() {
		return 9;
	}

	@Override
	public Recipe recipe() {
		ItemStack fire_rod = new ItemStack(Material.BLAZE_ROD);
		createItem(fire_rod);

		ShapedRecipe recipe = new ShapedRecipe(fire_rod);
		recipe.shape("BCB", "BAB", "BBB");
		recipe.setIngredient('C', Material.DIAMOND);
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('A', Material.BLAZE_ROD);
		return recipe;
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.BLAZE_ROD);
		createItem(i);
		return i;
	}

	@Override
	public int reqLevel() {
		return 1;
	}

	@EventHandler
	public void fireball(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")
				&& !plugin.fireRodDelay.containsKey(e.getPlayer().getName())
				&& getItemLevel(e.getPlayer().getItemInHand()) < 6) {
			if (PluginHook.worldGuard()) {
				PluginHook.hookWG();
				if (PluginHook.wg.canBuild(e.getPlayer(), e.getPlayer()
						.getTargetBlock(null, 1000))) {
					Player player = e.getPlayer();
					SmallFireball fireball = player
							.launchProjectile(SmallFireball.class);
					fireball.setVelocity(fireball.getVelocity().multiply(10));
					delay(plugin.fireRodDelay,
							plugin,
							delayFromLevel(getItemLevel(player.getItemInHand())),
							e.getPlayer().getName());
				} else {
					e.getPlayer()
							.sendMessage(
									ChatColor.DARK_RED
											+ "You don't have permission for this area");
				}
			} else {
				Player player = e.getPlayer();
				SmallFireball fireball = player
						.launchProjectile(SmallFireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				delay(plugin.fireRodDelay, plugin,
						delayFromLevel(getItemLevel(player.getItemInHand())), e
								.getPlayer().getName());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")
				&& !plugin.fireRodDelay.containsKey(e.getPlayer().getName())) {

			if (PluginHook.worldGuard()) {
				PluginHook.hookWG();
				if (PluginHook.wg.canBuild(e.getPlayer(), e.getPlayer()
						.getTargetBlock(null, 1000))) {
					Player player = e.getPlayer();
					Fireball fireball = player.launchProjectile(Fireball.class);
					fireball.setVelocity(fireball.getVelocity().multiply(10));
					delay(plugin.fireRodDelay,
							plugin,
							delayFromLevel(getItemLevel(player.getItemInHand())),
							e.getPlayer().getName());
				} else {
					e.getPlayer()
							.sendMessage(
									ChatColor.DARK_RED
											+ "You don't have permission for this area");
				}
			} else {
				Player player = e.getPlayer();
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				delay(plugin.fireRodDelay, plugin,
						delayFromLevel(getItemLevel(player.getItemInHand())), e
								.getPlayer().getName());
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR
				&& plugin.fireRodDelay.containsKey(e.getPlayer().getName())
				&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")) {
			int time = (Integer) plugin.fireRodDelay.get(e.getPlayer()
					.getName());
			e.getPlayer().sendMessage(
					ChatColor.GRAY + "The rod of fire still needs " + time
							+ " seconds to recharge!");
		}
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (checkName(e.getRecipe().getResult(), this.name())) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.firerod")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}
}
