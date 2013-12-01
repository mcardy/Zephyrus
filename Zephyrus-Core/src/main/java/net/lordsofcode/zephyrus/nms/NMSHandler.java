package net.lordsofcode.zephyrus.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.lordsofcode.zephyrus.utils.ReflectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class NMSHandler {

	/**
	 * Gets the NMS specific trader for this version
	 * 
	 * @return null if there is no trader for this version
	 */
	public static ITrader getTrader() {
		try {
			Class<?> clazz = Class.forName(getPackageName() + ".Trader");
			return (ITrader) clazz.getConstructors()[0].newInstance();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Gets the NMS specific dragon for this version
	 * 
	 * @return null if there is no dragon for this version
	 */
	public static IDragon getDragon(String name, Location loc, int percent) {
		try {
			Class<?> clazz = Class.forName(getPackageName() + ".Dragon");
			return (IDragon) clazz.getConstructor(String.class, Location.class, int.class).newInstance(name, loc,
					percent);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Object getNMSItemStack(ItemStack stack) {
		try {
			Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".inventory.CraftItemStack");
			Method m = ReflectionUtils.getMethod(clazz, "asNMSCopy", new Class<?>[] { ItemStack.class });
			return m.invoke(null, stack);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sends the given packet object to the given player
	 */
	public static void sendPacket(Player player, Object packet) {
		try {
			Object nmsPlayer = getHandle(player);
			Field con_field = nmsPlayer.getClass().getField("playerConnection");
			Object con = con_field.get(nmsPlayer);
			Method packet_method = ReflectionUtils.getMethod(con.getClass(), "sendPacket");
			packet_method.invoke(con, packet);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static Object getCraftObject(String name) {
		try {
			return getCraftClass(name).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getCraftClass(String name) {
		String version = getVersion() + ".";
		String className = "net.minecraft.server." + version + name;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Object getHandle(Entity entity) {
		Object nms_entity = null;
		Method entity_getHandle = ReflectionUtils.getMethod(entity.getClass(), "getHandle");
		try {
			nms_entity = entity_getHandle.invoke(entity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return nms_entity;
	}

	private static String getPackageName() {
		return "net.lordsofcode.zephyrus.nms." + getVersion();
	}

	private static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

}
