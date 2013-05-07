package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.spells.Spell;
import minny.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SpellTome extends CustomItem{

	String spell;
	PlayerConfigHandler config;
	
	public SpellTome(Zephyrus plugin, String imput) {
		super(plugin);
		this.spell = imput;
	}

	@Override
	public String name() {
		return "¤bSpell Tome";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.BOOK);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		ItemMeta m = i.getItemMeta();
		List<String> l = new ArrayList<String>();
		l.add("¤7" + spell);
		m.setLore(l);
		i.setItemMeta(m);
		setGlow(i);
	}

	@Override
	public Recipe recipe() {
		return null;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e){
		if (checkName(e.getPlayer().getItemInHand(), this.name())){
			ItemStack i = e.getPlayer().getItemInHand();
			List<String> l = i.getItemMeta().getLore();
			String s = l.get(0).replace("¤7", "");
			if (plugin.spellMap.containsKey(s)){
				Spell spell = plugin.spellMap.get(s);
				config = new PlayerConfigHandler(plugin, e.getPlayer().getName());
				config.reloadConfig();
				if (!config.getConfig().getStringList("learned").contains(spell.name())){
					List<String> learned = config.getConfig().getStringList("learned");
					learned.add(spell.name());
					config.getConfig().set("learned", learned);
					e.getPlayer().sendMessage("You have successfully learned " + ChatColor.GOLD + spell.name());
					e.getPlayer().setItemInHand(null);
					config.saveConfig();
				} else {
					e.getPlayer().sendMessage("You already know that spell!");
				}
				
			} else {
				e.getPlayer().sendMessage("Spell not found...");
			}
		}
	}
	
}
