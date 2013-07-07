package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.utils.Lang;
import net.lordsofcode.zephyrus.utils.ParticleEffects;
import net.lordsofcode.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Home extends Spell {

	public Home(Zephyrus plugin) {
		super(plugin);
		Lang.add("spells.home.set", "Your home has been set!");
		Lang.add("spells.home.applied", "Welcome home!");
	}

	@Override
	public String name() {
		return "home";
	}

	@Override
	public String bookText() {
		return "Set your home with" + ChatColor.BOLD + "/cast home set!"
				+ ChatColor.RESET + "Then go to your home with "
				+ ChatColor.BOLD + "/cast home!";
	}

	@Override
	public int reqLevel() {
		return 3;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		if (args.length == 2 && args[1].equalsIgnoreCase("set")) {
			setHome(player);
			Lang.msg("spells.home.set", player);
		} else {
			goHome(player);
			Lang.msg("spells.home.applied", player);
		}
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		if (args.length == 2 && args[1].equalsIgnoreCase("set")) {
			return true;
		} else if (isHomeSet(player)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String failMessage() {
		return ChatColor.GRAY + "No home set! Set one with /cast home set";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> s = new HashSet<ItemStack>();
		s.add(new ItemStack(Material.WOODEN_DOOR));
		s.add(new ItemStack(Material.BED));
		s.add(new ItemStack(Material.FURNACE));
		return s;
	}

	private void setHome(Player player) {
		FileConfiguration cfg = PlayerConfigHandler.getConfig(plugin, player);
		Location loc = player.getLocation();
		cfg.set("spell.home.x", loc.getX());
		cfg.set("spell.home.y", loc.getY());
		cfg.set("spell.home.z", loc.getZ());
		cfg.set("spell.home.yaw", loc.getYaw());
		cfg.set("spell.home.pitch", loc.getPitch());
		cfg.set("spell.home.world", loc.getWorld().getName());
		PlayerConfigHandler.saveConfig(plugin, player, cfg);
	}

	private boolean isHomeSet(Player player) {
		FileConfiguration cfg = PlayerConfigHandler.getConfig(plugin, player);
		return cfg.contains("spell.home.x");
	}

	private void goHome(Player player) {
		FileConfiguration cfg = PlayerConfigHandler.getConfig(plugin, player);
		int x = cfg.getInt("spell.home.x");
		int y = cfg.getInt("spell.home.y");
		int z = cfg.getInt("spell.home.z");
		int yaw = cfg.getInt("spell.home.yaw");
		int pitch = cfg.getInt("spell.home.pitch");
		String world = cfg.getString("spell.home.world");
		Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		player.teleport(loc);
		ParticleEffects.sendToLocation(ParticleEffects.ENDER, loc, 0, 0, 0, 2,
				20);
		player.getWorld().playSound(loc, Sound.ENDERMAN_TELEPORT, 10, 1);
	}

	@Override
	public SpellType type() {
		return SpellType.TELEPORTATION;
	}
	
	@Override
	public boolean canBind() {
		return false;
	}

}
