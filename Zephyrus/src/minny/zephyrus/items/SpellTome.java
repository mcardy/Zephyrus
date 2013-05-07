package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;

public class SpellTome extends CustomItem {

	String spell;
	String desc;
	PlayerConfigHandler config;

	public SpellTome(Zephyrus plugin, String imput, String desc) {
		super(plugin);
		this.spell = imput;
		this.desc = desc;
	}

	@Override
	public String name() {
		return "¤bSpell Tome";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.WRITTEN_BOOK);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		BookMeta m = (BookMeta) i.getItemMeta();
		List<String> l = new ArrayList<String>();
		l.add("¤7" + spell);
		m.setLore(l);
		m.setTitle(spell);
		m.addPage(desc + "\n\n¤0Cast the spell with:\n¤9/cast " + spell + "\n\n¤4Learn this spell by left clicking this book!");
		i.setItemMeta(m);
		setGlow(i);
	}

	@Override
	public Recipe recipe() {
		return null;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR
				|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (checkName(e.getPlayer().getItemInHand(), this.name())) {
				ItemStack i = e.getPlayer().getItemInHand();
				List<String> l = i.getItemMeta().getLore();
				String s = l.get(0).replace("¤7", "");
				if (plugin.spellMap.containsKey(s)) {
					Spell spell = plugin.spellMap.get(s);
					config = new PlayerConfigHandler(plugin, e.getPlayer()
							.getName());
					config.reloadConfig();
					if (!config.getConfig().getStringList("learned")
							.contains(spell.name())) {
						List<String> learned = config.getConfig()
								.getStringList("learned");
						learned.add(spell.name());
						config.getConfig().set("learned", learned);
						e.getPlayer().sendMessage(
								"You have successfully learned "
										+ ChatColor.GOLD + spell.name());
						e.getPlayer().setItemInHand(null);
						config.saveConfig();
					} else {
						e.getPlayer().sendMessage(
								"You already know that spell!");
					}

				} else {
					e.getPlayer().sendMessage("Spell not found...");
				}
			}
		}
	}

}
