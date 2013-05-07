package minny.zephyrus.spells;

import java.util.Set;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.items.SpellTome;
import minny.zephyrus.utils.ParticleEffects;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Spell extends LevelManager{

	PlayerConfigHandler config;
	
	public Spell(Zephyrus plugin){
		super(plugin);
	}
	
	public abstract String name();
	public abstract String bookText();
	public abstract int reqLevel();
	public abstract int manaCost();
	public abstract void run(Player player);
	public abstract Set<ItemStack> spellItems();
	
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
		if (p.hasPermission("zephyrus.cast." + spell.name())){
			return true;
		}
		return false;
	}

	public void dropSpell(Block bookshelf, String name, String desc){
		bookshelf.breakNaturally();
		SpellTome tome = new SpellTome(plugin, name, desc);
		Location loc = bookshelf.getLocation();
		loc.getWorld().dropItemNaturally(loc, tome.item());
		try {
			ParticleEffects.sendToLocation(ParticleEffects.ENCHANTMENT_TABLE, loc, 0, 0, 0, -4, 12);
			ParticleEffects.sendToLocation(ParticleEffects.WITCH_MAGIC, loc, 0, 0, 0, 4, 3);
			loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 3, 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
