package minny.zephyrus.spells;

import java.util.Set;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.SpellTome;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.utils.ConfigHandler;
import minny.zephyrus.utils.ParticleEffects;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public abstract class Spell extends LevelManager{

	PlayerConfigHandler config;
	ConfigHandler spellConfig;
	
	public Spell(Zephyrus plugin){
		super(plugin);
		if (!plugin.spellMap.containsKey(this.name())){
			plugin.spellMap.put(this.name(), this);
		}
		if (this.spellItems() != null && !plugin.spellCraftMap.containsKey(this.spellItems())){
			plugin.spellCraftMap.put(this.spellItems(), this);
		}
		spellConfig = new ConfigHandler(plugin, "spellconfig.yml");
	}
	
	public abstract String name();
	public abstract String bookText();
	public abstract int reqLevel();
	/*{
		double i = this.manaCost() * plugin.getConfig().getInt("ManaMultiplier") / 100;
		return (int) Math.ceil(i);
	}*/
	public abstract int manaCost();
	public abstract void run(Player player);
	public abstract Set<ItemStack> spellItems();
	
	public boolean canBind() {
		return true;
	}
	
	public int getManaCost() {
		int i = spellConfig.getConfig().getInt(this.name());
		return i;
	}
	
	public Spell reqSpell(){
		return null;
	}
	
	public boolean canRun(Player player) {
		return true;
	}
	public String failMessage(){
		return "";
	}
	
	public boolean isLearned(Player p, String name){
		config = new PlayerConfigHandler(plugin, p.getName());
		config.reloadConfig();
		if (config.getConfig().getStringList("learned").contains(name)){
			return true;
		}
		return false;
	}
	
	public void notEnoughMana(Player p){
		p.sendMessage(ChatColor.DARK_GRAY + "Not enough mana!");
	}
	
	public boolean hasPermission(Player p, Spell spell){
		if (plugin.getConfig().getBoolean("OpKnowledge")){
			if (p.hasPermission("zephyrus.cast." + spell.name())){
				return true;
			}
			if (p.isOp()){
				return true;
			}
		}
		return false;
	}

	public void dropSpell(Block bookshelf, String name, String desc){
		bookshelf.setType(Material.AIR);
		SpellTome tome = new SpellTome(plugin, name, desc);
		Location loc = bookshelf.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		loc.getWorld().dropItem(loc.add(0, +1, 0), tome.item()).setVelocity(new Vector(0, 0, 0));
		try {
			ParticleEffects.sendToLocation(ParticleEffects.ENCHANTMENT_TABLE, loc, 0, 0, 0, 1, 30);
			loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 3, 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
