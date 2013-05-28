package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.ItemUtil;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellTome extends ItemUtil implements Listener {

	String spell;
	String desc;

	public SpellTome(Zephyrus plugin, String imput, String desc) {
		super(plugin);
		spell = imput;
		this.desc = desc;
	}

	public String name() {
		return "¤bSpell Tome";
	}

	public ItemStack item() {
		ItemStack i = new ItemStack(Material.WRITTEN_BOOK);
		createItem(i);
		return i;
	}

	public static ItemStack getSpelltome(Spell spell) {
		ItemStack i = new ItemStack(Material.BOOK);
		BookMeta m = (BookMeta) i.getItemMeta();
		List<String> l = new ArrayList<String>();
		l.add("¤7" + spell);
		m.setLore(l);
		m.setTitle(spell.name());
		m.addPage(spell.bookText() + "\n\n¤0Cast the spell with:\n¤9/cast " + spell
				+ "\n\n¤0Learn this spell by left clicking this book!");
		i.setItemMeta(m);
		i.addEnchantment(Zephyrus.sGlow, 1);
		return i;
	}
	
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		BookMeta m = (BookMeta) i.getItemMeta();
		List<String> l = new ArrayList<String>();
		l.add("¤7" + spell);
		m.setLore(l);
		m.setTitle(spell);
		m.addPage(desc + "\n\n¤0Cast the spell with:\n¤9/cast " + spell
				+ "\n\n¤0Learn this spell by left clicking this book!");
		i.setItemMeta(m);
		setGlow(i);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (checkName(e.getPlayer().getItemInHand(), this.name())) {
				ItemStack i = e.getPlayer().getItemInHand();
				List<String> l = i.getItemMeta().getLore();
				String s = l.get(0).replace("¤7", "");
				if (Zephyrus.spellMap.containsKey(s)) {
					Spell spell = Zephyrus.spellMap.get(s);
					Player player = e.getPlayer();
					PlayerConfigHandler.reloadConfig(plugin, e.getPlayer());
					if (!PlayerConfigHandler.getConfig(plugin, player)
							.getStringList("learned").contains(spell.name())) {
						List<String> learned = PlayerConfigHandler.getConfig(
								plugin, player).getStringList("learned");
						learned.add(spell.name());
						PlayerConfigHandler.getConfig(plugin, player).set(
								"learned", learned);
						e.getPlayer().sendMessage(
								"You have successfully learned "
										+ ChatColor.GOLD + spell.name());
						e.getPlayer().setItemInHand(null);
						PlayerConfigHandler.saveConfig(plugin, player);
					} else {
						e.getPlayer().sendMessage(
								"You already know that spell!");
						return;
					}

				} else {
					e.getPlayer().sendMessage("Spell not found...");
				}
			}
		}
	}
}
