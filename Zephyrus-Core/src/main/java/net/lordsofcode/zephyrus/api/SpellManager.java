package net.lordsofcode.zephyrus.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.spells.Spell;
import net.lordsofcode.zephyrus.utils.ConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellManager {

	private Map<String, ISpell> spellMap;
	private Map<Set<ItemStack>, ISpell> spellCraftMap;
	private int builtInSpells = 0;
	private ConfigHandler spellsConfig;
	
	public SpellManager() {
		this.spellsConfig = new ConfigHandler("spells.yml");
		this.spellsConfig.saveDefaultConfig();
		this.spellCraftMap = new HashMap<Set<ItemStack>, ISpell>();
		this.spellMap = new LinkedHashMap<String, ISpell>();
	}

	/**
	 * Adds a spell to Zephyrus and loads the spell's config
	 * 
	 * @param spell
	 *            The ISpell to add
	 */
	public void addSpell(ISpell spell) {
		if (spell instanceof Spell) {
			addSpell(spell, true);
		} else {
			addSpell(spell, false);
		}
	}

	/**
	 * Adds a spell to Zephyrus. Config decides if Zephyrus should load the
	 * config for the spell as well.
	 * 
	 * @param spell
	 *            The ISpell to add
	 * @param config
	 *            Whether or not to check the config
	 */
	public void addSpell(ISpell spell, boolean config) {
		if ((spell.getClass().getPackage() == Spell.class.getPackage())) {
			builtInSpells++;
		}
		if (spell.isEnabled()) {
			if (config) {
				spellCfg(spell);
			}
			spellMap.put(spell.getDisplayName().toLowerCase(), spell);
			if (spell.items() != null) {
				spellCraftMap.put(spell.items(), spell);
			}

			Bukkit.getServer().getPluginManager().registerEvents(spell, Zephyrus.getPlugin());

			Permission castPerm = new Permission("zephyrus.cast." + spell.getName().toLowerCase(),
					PermissionDefault.FALSE);
			Bukkit.getPluginManager().addPermission(castPerm);

			Permission spellPerm = new Permission("zephyrus.spell." + spell.getName().toLowerCase(),
					PermissionDefault.TRUE);
			Bukkit.getPluginManager().addPermission(spellPerm);
		}
	}

	/**
	 * Gets the spell from the given name. This is useful to get a spell that
	 * may have a changed display name
	 * 
	 * @param s
	 *            The spell's built-in name
	 * @return An ISpell that has s as it's built-in name
	 */
	public ISpell forName(String s) {
		return spellMap.get(getSpellName(s));
	}

	/**
	 * Gets the spell's display name from its default name.
	 * 
	 * @param defaultName
	 *            The spell's built-in name
	 * @return The spell's display name
	 */
	public String getSpellName(String defaultName) {
		FileConfiguration cfg = new ConfigHandler("spells.yml").getConfig();
		if (cfg.contains(defaultName + ".displayname")) {
			String displayName = cfg.getString(defaultName + ".displayname");
			return displayName;
		} else {
			return defaultName;
		}
	}

	/**
	 * Gets the number of built in spells
	 * 
	 * @return
	 */
	public int getBuiltInSpells() {
		return builtInSpells;
	}

	/**
	 * Gets the number of external spells
	 * 
	 * @return
	 */
	public int getExternalSpells() {
		return spellMap.size() - builtInSpells;
	}

	/**
	 * Gets the number of registered spells
	 * 
	 * @return
	 */
	public int getRegisteredSpells() {
		return spellMap.size();
	}

	/**
	 * Gets the registered spells map
	 * 
	 * @return
	 */
	public Map<String, ISpell> getSpellMap() {
		return spellMap;
	}

	/**
	 * Gets the registered spell recipe map
	 * 
	 * @return
	 */
	public Map<Set<ItemStack>, ISpell> getCraftMap() {
		return spellCraftMap;
	}

	private void spellCfg(final ISpell spell) {
		if (!spellsConfig.getConfig().contains(spell.getName() + ".enabled")) {
			spellsConfig.getConfig().set(spell.getName() + ".enabled", true);
		}
		if (!spellsConfig.getConfig().contains(spell.getName() + ".desc") && spell.getDesc() != null) {
			spellsConfig.getConfig().set(spell.getName() + ".desc",
					spell.getDesc().replace(ChatColor.COLOR_CHAR + "", "$"));
		}
		if (!spellsConfig.getConfig().contains(spell.getName() + ".mana")) {
			spellsConfig.getConfig().set(spell.getName() + ".mana", spell.manaCost());
		}
		if (!spellsConfig.getConfig().contains(spell.getName() + ".level")) {
			spellsConfig.getConfig().set(spell.getName() + ".level", spell.reqLevel());
		}
		if (!spellsConfig.getConfig().contains(spell.getName() + ".exp")) {
			spellsConfig.getConfig().set(spell.getName() + ".exp", spell.manaCost() / 3 + 1);
		}
		if (!spellsConfig.getConfig().contains(spell.getName() + ".displayname")) {
			spellsConfig.getConfig().set(spell.getName() + ".displayname", spell.getName());
		}
		if (spell.getConfiguration() != null) {
			Map<String, Object> cfg = spell.getConfiguration();
			for (String str : cfg.keySet()) {
				if (!spellsConfig.getConfig().contains(spell.getName() + "." + str)) {
					spellsConfig.getConfig().set(spell.getName() + "." + str, cfg.get(str));
				}
			}
		}
		spellsConfig.saveConfig();
	}

}
