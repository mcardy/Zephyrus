package net.lordsofcode.zephyrus.spellbook;

import net.lordsofcode.zephyrus.Zephyrus;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellBook extends JavaPlugin implements Listener {

	public static int manaMultiplier;
	public static int ID;
	public static ClickType forward;
	public static ClickType backward;
	public static ClickType cast;
	public static String name;
	
	Zephyrus zephyrus;
	boolean isVault;
	Economy econ;

	@Override
	public void onEnable() {
		setUpEcon();
		zephyrus = Zephyrus.getInstance();
		
		ConfigHandler cfg = new ConfigHandler(this, "spellbook");
		cfg.saveDefaultConfig();
		
		forward = ClickType.valueOf(cfg.getConfig().getString("Cycle-Forward"));
		backward = ClickType.valueOf(cfg.getConfig().getString("Cycle-Backward"));
		cast = ClickType.valueOf(cfg.getConfig().getString("Cast-Spell"));
		
		name = cfg.getConfig().getString("Item-Name");
		ID = cfg.getConfig().getInt("Item-ID");
		
		SpellBookItem sbi = new SpellBookItem(this, Zephyrus.getInstance());
		
		getServer().getPluginManager().registerEvents(sbi, this);
		if (cfg.getConfig().getBoolean("Enable-Crafting")) {
			getServer().addRecipe(SpellBookItem.getRecipe());
		}

		manaMultiplier = Zephyrus.getConfig().getInt("ManaMultiplier");
		new SpellBookItem(this, zephyrus);
		
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void setUpEcon() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Vault");
		if (plugin instanceof Vault) {
			isVault = true;
			RegisteredServiceProvider<Economy> rsp = Bukkit.getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (rsp != null) {
				econ = rsp.getProvider();
			}
		} else {
			isVault = false;
			econ = null;
		}
	}
	
	public enum ClickType {
		SHIFT_LEFT, SHIFT_RIGHT, LEFT, RIGHT;
	}
	
	public static ClickType getType(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().isSneaking()) {
				return ClickType.SHIFT_RIGHT;
			} else {
				return ClickType.RIGHT;
			}
		} else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getPlayer().isSneaking()) {
				return ClickType.SHIFT_LEFT;
			} else {
				return ClickType.LEFT;
			}
		}
		return null;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getPlayer().hasPermission("zephyrus.spellbook.sign")) {
			if (e.getLine(1).equals("[SpellBook]")) {
				if (isVault) {
					if (e.getLine(2).startsWith("$")) {
						try {
							Integer.parseInt(e.getLine(2).replace("$", ""));
						} catch (NumberFormatException ex) {
							e.getPlayer().sendMessage("Invalad Cost!");
							return;
						}
						e.setLine(1, ChatColor.AQUA + "[SpellBook]");
						e.getPlayer().sendMessage(
								"SpellBook sign successfully created!");
					}
				} else {
					e.getPlayer()
							.sendMessage(
									"Vault is not installed! Signs have been disabled!");
				}
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(1).equals(ChatColor.AQUA + "[SpellBook]")) {
					if (isVault) {
						int amount = Integer.parseInt(sign.getLine(2).replace(
								"$", ""));
						if (econ.getBalance(e.getPlayer().getName()) >= amount) {
							econ.withdrawPlayer(e.getPlayer().getName(), amount);
							e.getPlayer().getInventory()
									.addItem(SpellBookItem.item());
							e.getPlayer().sendMessage("You got a spellbook!");

						} else {
							e.getPlayer().sendMessage("Insufficient Funds!");
						}
					} else {
						e.getPlayer()
								.sendMessage(
										"Vault is not installed! Signs have been disabled!");
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			if (sender.hasPermission("zephyrus.spellbook.give")) {
				if (args.length == 0) {
					Player player = (Player) sender;
					player.getInventory().addItem(SpellBookItem.item());
					sender.sendMessage("Spellbook given");
					return true;
				}
				if (sender.hasPermission("zephyrus.spellbook.give.other")) {
					Player player = Bukkit.getPlayer(args[0]);
					if (player == null) {
						sender.sendMessage("That player is not online!");
						return true;
					}
					player.getInventory().addItem(SpellBookItem.item());
					sender.sendMessage("Gave " + player.getName()
							+ " a spellbook");
					player.sendMessage("You were given a spellbook");
				}
			}
		} else {
			if (args.length == 0) {
				sender.sendMessage("Usage: /spellbook [player]");
				return true;
			}
			Player player = Bukkit.getPlayer(args[0]);
			if (player == null) {
				sender.sendMessage("That player is not online!");
				return true;
			}
			player.getInventory().addItem(SpellBookItem.item());
			sender.sendMessage("Gave " + player.getName() + " a spellbook");
			player.sendMessage("You were given a spellbook");
		}
		return true;
	}

}
