package minny.zephyrus.items;

import minny.zephyrus.Zephyrus;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftSkeleton;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class SummonSkeleton extends CustomItem {

	public SummonSkeleton(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "¤8Egg of Bones";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.EGG);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		setItemLevel(i, 1);
		setGlow(i);
	}

	@Override
	public Recipe recipe() {
		ItemStack summon = item();

		ShapedRecipe recipe = new ShapedRecipe(summon);
		recipe.shape("BCB", "BAB", "BDB");
		recipe.setIngredient('D', Material.ARROW);
		recipe.setIngredient('C', Material.BOW);
		recipe.setIngredient('B', Material.BONE);
		recipe.setIngredient('A', Material.DIAMOND);
		return recipe;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if (checkName(e.getPlayer().getItemInHand(), this.name())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEvent(PlayerInteractEntityEvent e){
		if (checkName(e.getPlayer().getItemInHand(), this.name())){
			LivingEntity t = (LivingEntity) e.getRightClicked();
			LivingEntity s = (Skeleton) e.getPlayer().getWorld().spawnEntity(e.getRightClicked().getLocation(), EntityType.SKELETON);
			((CraftSkeleton)s).getHandle().bG();
			setTarget(s, t);
		}
	}
	
	 public void setTarget(LivingEntity entity, LivingEntity target) {
         if (entity instanceof Creature) {
                 ((Creature)entity).setTarget(target);
         }
         ((CraftLivingEntity)entity).getHandle().setGoalTarget(((CraftLivingEntity)target).getHandle());
	 }
}
