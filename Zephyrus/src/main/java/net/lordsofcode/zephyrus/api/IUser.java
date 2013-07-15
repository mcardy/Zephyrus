package net.lordsofcode.zephyrus.api;

import org.bukkit.command.CommandSender;


/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface IUser {
	
	public boolean isLearned(ISpell spell);
	public boolean hasPermission(ISpell spell);
	
	public boolean hasMana(int mana);
	public int drainMana(int mana);
	public int getMana();
	
	public int levelUp();
	public int levelProgress(int progress);
	public int getLevelProgress();
	public int getLevel();
	
	public void loadMana();
	public void unLoadMana();
	public void reLoadMana();
	
	public void displayMana();
	public void displayMana(CommandSender sender);
	public void displayLevel();
	public void displayLevel(CommandSender sender);
	
}
