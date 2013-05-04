package minny.zephyrus.spells;

import minny.zephyrus.LevelManager;
import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Spell extends LevelManager{

	PlayerConfigHandler config;
	LevelManager lvl;
	
	public Spell(Zephyrus plugin){
		super(plugin);
		lvl = new LevelManager(plugin);
	}
	
	public abstract String name();
	public abstract String permission();
	public abstract int reqLevel();
	public abstract int manaCost();
	public abstract void run(Player player);
	
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

}
