package minny.zephyrus.spells;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Spell extends LevelManager{

	PlayerConfigHandler config;
	
	public Spell(Zephyrus plugin){
		super(plugin);
	}
	
	public abstract String name();
	public abstract int reqLevel();
	public abstract int manaCost();
	public abstract void run(Player player);
	public boolean canRun(Player player){
		return true;
	}
	public String failMessage(){
		return "";
	}
	
	public boolean isLearned(Player p, String name){
		config = new PlayerConfigHandler(plugin, p.getName());
		if (config.getConfig().getStringList("learned-spells").contains(name)){
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

}
