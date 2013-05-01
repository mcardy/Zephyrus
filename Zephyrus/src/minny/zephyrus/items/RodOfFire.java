package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.DelayUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class RodOfFire extends Item {

	public RodOfFire(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "¤cRod of Fire";
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, "Rod of Fire", "c");
		setItemLevel(i, 1);
		setGlow(i);
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

	public void fireball(PlayerInteractEvent e) {
		//try {
			if (e.getAction() == Action.RIGHT_CLICK_AIR
					&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")
					&& !isRecharging(e.getPlayer().getItemInHand())
					&& getItemLevel(e.getPlayer().getItemInHand()) < 6) {
				Player player = e.getPlayer();
				Fireball fireball = player
						.launchProjectile(SmallFireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				setRecharging(e.getPlayer().getItemInHand(), true);
				new DelayUtil(plugin, e.getPlayer().getItemInHand(), false)
						.runTaskLater(plugin, delayFromLevel(getItemLevel(e
								.getPlayer().getItemInHand())));
			} else if (e.getAction() == Action.RIGHT_CLICK_AIR
					&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")
					&& !isRecharging(e.getPlayer().getItemInHand())) {
				Player player = e.getPlayer();
				Fireball fireball = player.launchProjectile(Fireball.class);
				fireball.setVelocity(fireball.getVelocity().multiply(10));
				setRecharging(e.getPlayer().getItemInHand(), true);
				new DelayUtil(plugin, e.getPlayer().getItemInHand(), false)
						.runTaskLater(plugin, delayFromLevel(getItemLevel(e
								.getPlayer().getItemInHand())));
			} else if (isRecharging(e.getPlayer().getItemInHand())
					&& checkName(e.getPlayer().getItemInHand(), "¤cRod of Fire")) {
				e.getPlayer().sendMessage(
						ChatColor.GRAY + "Your wand is recharging...");
			}
		//} catch (NullPointerException exception) {

		//}
	}
}
