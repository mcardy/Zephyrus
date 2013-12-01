package net.lordsofcode.zephyrus.items.wands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ICustomItemWand;
import net.lordsofcode.zephyrus.api.ISpell;
import net.lordsofcode.zephyrus.api.IUser;
import net.lordsofcode.zephyrus.events.PlayerPostCastSpellEvent;
import net.lordsofcode.zephyrus.events.PlayerPreCastSpellEvent;
import net.lordsofcode.zephyrus.utils.Lang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class WandListener implements Listener {

	public WandListener() {
		Lang.add("wand.enchanter", "You have successfully created an $6$lArcane Leveller");
		Lang.add("wand.nospell", "Spell recipe not found!");
		Lang.add("wand.noperm", "You do not have permission to learn [SPELL]");
		Lang.add("wand.reqlevel", "That spell requires level [LEVEL]");
		Lang.add("wand.reqspell", "That spell requires the knowledge of [SPELL]");
		Lang.add("wand.lowlevelwand", "Your wand cannot craft that spell. It is a too low level");
	}
	
	@EventHandler
	public void onBoundClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack i = e.getItem();
			if (Zephyrus.getItemManager().isWand(i)) {
				ICustomItemWand wand = Zephyrus.getItemManager().getWand(i);
				String s = wand.getSpell(i);
				if (Zephyrus.getSpellMap().containsKey(s)) {
					ISpell spell = Zephyrus.getSpellMap().get(s);
					Player player = e.getPlayer();
					IUser user = Zephyrus.getUser(player);
					if (user.isLearned(spell) || user.hasPermission(spell)) {
						if (user.hasMana(spell.getManaCost())) {
							PlayerPreCastSpellEvent precast = new PlayerPreCastSpellEvent(player, spell, null);
							Bukkit.getPluginManager().callEvent(precast);
							if (!precast.isCancelled()) {
								boolean b = spell.run(player, null, wand.getPower(spell));
								if (b) {
									float discount = (spell.getManaCost()/(float)100) * wand.getManaDiscount(spell);
									user.drainMana(spell.getManaCost() - (int)discount);
									PlayerPostCastSpellEvent postcast = new PlayerPostCastSpellEvent(player, spell);
									Bukkit.getPluginManager().callEvent(postcast);
								}
							}
						} else {
							Lang.errMsg("nomana", player);
						}
					} else {
						Lang.errMsg("notlearned", player);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEnchantClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
			ItemStack i = e.getItem();
			if (Zephyrus.getItemManager().isWand(i)) {
				Block b = e.getClickedBlock();
				if (b.getType() == Material.ENCHANTMENT_TABLE && b.getData() != 12) {
					e.setCancelled(true);
					b.setData((byte) 12);
					e.getPlayer().sendMessage(Lang.get("wand.enchanter").replace("#", ChatColor.COLOR_CHAR + ""));
				}
			}
		}
	}
	
}
