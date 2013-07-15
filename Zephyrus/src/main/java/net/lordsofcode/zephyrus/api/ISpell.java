package net.lordsofcode.zephyrus.api;

import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.api.SpellTypes.EffectType;
import net.lordsofcode.zephyrus.api.SpellTypes.Element;
import net.lordsofcode.zephyrus.api.SpellTypes.Priority;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface ISpell extends Listener {

	public String getName();
	public String getDisplayName();
	
	public String getDesc();
	public String getDisplayDesc();
	
	public ISpell getRequiredSpell();
	
	public Map<String, Object> getConfiguration();
	
	public int reqLevel();
	public int getReqLevel();
	
	public int manaCost();
	public int getManaCost();
	
	public boolean canBind();
	
	public Set<ItemStack> items();
	public Set<ItemStack> getItems();
	
	public EffectType getPrimaryType();
	public Element getElementType();
	public Priority getPriority();
	
	public boolean isEnabled();
	public int getExp();
	
	public boolean run(Player player, String[] args);
	public boolean sideEffect(Player player, String[] args);
	public boolean comboSpell(Player player, String[] args, EffectType type, Element element, int power);
	
	public void onDisable();
	
}
