package net.lordsofcode.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.events.PlayerLearnSpellEvent;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.ItemUtil;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
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
		return ChatColor.getByChar("b") + "Spell Tome";
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
		l.add(ChatColor.GRAY + "" + spell);
		m.setLore(l);
		m.setTitle(spell.getDisplayName());
		m.addPage(spell.getDesc() + "\n\n" + Lang.get("spelltome.cast").replace("[SPELL]", spell.getDisplayName())
				+ "\n\n" + Lang.get("spelltome.learn"));
		i.setItemMeta(m);
		i.addEnchantment(Zephyrus.getInstance().glow, 1);
		return i;
	}

	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		BookMeta m = (BookMeta) i.getItemMeta();
		List<String> l = new ArrayList<String>();
		l.add(ChatColor.GRAY + "" + spell);
		m.setLore(l);
		m.setTitle(spell);
		m.addPage(desc + "\n\n" + Lang.get("spelltome.cast").replace("[SPELL]", spell)
				+ "\n\n" + Lang.get("spelltome.learn"));
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
				String s = l.get(0).replace(ChatColor.GRAY + "", "");
				if (Zephyrus.spellMap.containsKey(s)) {
					Spell spell = Zephyrus.spellMap.get(s);
					Player player = e.getPlayer();
					FileConfiguration cfg = PlayerConfigHandler.getConfig(
							plugin, player);
					if (!cfg.getStringList("learned").contains(spell.getDisplayName().toLowerCase())) {
						PlayerLearnSpellEvent event = new PlayerLearnSpellEvent(
								player, spell);
						Bukkit.getServer().getPluginManager().callEvent(event);
						if (!event.isCancelled()) {
							List<String> learned = PlayerConfigHandler
									.getConfig(plugin, player).getStringList(
											"learned");
							learned.add(spell.getDisplayName().toLowerCase());
							cfg.set("learned", learned);
							e.getPlayer().sendMessage(Lang.get("spelltome.success").replace("[SPELL]", spell.getDisplayName()));
							e.getPlayer().setItemInHand(null);
							PlayerConfigHandler.saveConfig(plugin, player, cfg);
						}
					} else {
						Lang.errMsg("spelltome.known", e.getPlayer());
					}
				} else {
					Lang.errMsg("spelltome.nospell", e.getPlayer());
				}
			}
		}
	}
}
