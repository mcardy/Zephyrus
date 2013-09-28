package net.lordsofcode.zephyrus.player.mana;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.lordsofcode.zephyrus.utils.ReflectionUtils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class DummyDragon {

	public static final int MAX_HEALTH = 200;
	public boolean visible;
	public int EntityID;
	public int x;
	public int y;
	public int z;
	public int pitch = 0;
	public int head_pitch = 0;
	public int yaw = 0;
	public byte xvel = 0;
	public byte yvel = 0;
	public byte zvel = 0;
	public float health;
	public String name;

	public DummyDragon(String name, int EntityID, Location loc) {
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D),
				(int) Math.floor(loc.getBlockZ() * 32.0D));
	}

	public DummyDragon(String name, int EntityID, Location loc, float health, boolean visible) {
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D),
				(int) Math.floor(loc.getBlockZ() * 32.0D), health, visible);
	}

	public DummyDragon(String name, int EntityID, int x, int y, int z) {
		this(name, EntityID, x, y, z, MAX_HEALTH, false);
	}

	public DummyDragon(String name, int EntityID, int x, int y, int z, float health, boolean visible) {
		this.name = name;
		this.EntityID = EntityID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.health = health;
		this.visible = visible;
	}

	public Object getMobPacket() {
		Class<?> mob_class = ReflectionUtils.getCraftClass("Packet24MobSpawn");
		Object mobPacket = null;
		try {
			mobPacket = mob_class.newInstance();

			Field a = ReflectionUtils.getField(mob_class, "a");
			a.setAccessible(true);
			a.set(mobPacket, EntityID);// Entity ID
			Field b = ReflectionUtils.getField(mob_class, "b");
			b.setAccessible(true);
			b.set(mobPacket, EntityType.ENDER_DRAGON.getTypeId());// Mob type
																	// (ID: 64)
			Field c = ReflectionUtils.getField(mob_class, "c");
			c.setAccessible(true);
			c.set(mobPacket, x);// X position
			Field d = ReflectionUtils.getField(mob_class, "d");
			d.setAccessible(true);
			d.set(mobPacket, y);// Y position
			Field e = ReflectionUtils.getField(mob_class, "e");
			e.setAccessible(true);
			e.set(mobPacket, z);// Z position
			Field f = ReflectionUtils.getField(mob_class, "f");
			f.setAccessible(true);
			f.set(mobPacket, (byte) ((int) (pitch * 256.0F / 360.0F)));// Pitch
			Field g = ReflectionUtils.getField(mob_class, "g");
			g.setAccessible(true);
			g.set(mobPacket, (byte) ((int) (head_pitch * 256.0F / 360.0F)));// Head
																			// Pitch
			Field h = ReflectionUtils.getField(mob_class, "h");
			h.setAccessible(true);
			h.set(mobPacket, (byte) ((int) (yaw * 256.0F / 360.0F)));// Yaw
			Field i = ReflectionUtils.getField(mob_class, "i");
			i.setAccessible(true);
			i.set(mobPacket, xvel);// X velocity
			Field j = ReflectionUtils.getField(mob_class, "j");
			j.setAccessible(true);
			j.set(mobPacket, yvel);// Y velocity
			Field k = ReflectionUtils.getField(mob_class, "k");
			k.setAccessible(true);
			k.set(mobPacket, zvel);// Z velocity

			Object watcher = getWatcher();
			Field t = ReflectionUtils.getField(mob_class, "t");
			t.setAccessible(true);
			t.set(mobPacket, watcher);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}

		return mobPacket;
	}

	public Object getDestroyEntityPacket() {
		Class<?> packet_class = ReflectionUtils.getCraftClass("Packet29DestroyEntity");
		Object packet = null;
		try {
			packet = packet_class.newInstance();

			Field a = ReflectionUtils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, new int[] { EntityID });
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return packet;
	}

	public Object getMetadataPacket(Object watcher) {
		Class<?> packet_class = ReflectionUtils.getCraftClass("Packet40EntityMetadata");
		Object packet = null;
		try {
			packet = packet_class.newInstance();

			Field a = ReflectionUtils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, EntityID);

			Method watcher_c = ReflectionUtils.getMethod(watcher.getClass(), "c");
			Field b = ReflectionUtils.getField(packet_class, "b");
			b.setAccessible(true);
			b.set(packet, watcher_c.invoke(watcher));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return packet;
	}

	public Object getTeleportPacket(Location loc) {
		Class<?> packet_class = ReflectionUtils.getCraftClass("Packet34EntityTeleport");
		Object packet = null;
		try {
			packet = packet_class.newInstance();

			Field a = ReflectionUtils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, EntityID);
			Field b = ReflectionUtils.getField(packet_class, "b");
			b.setAccessible(true);
			b.set(packet, (int) Math.floor(loc.getX() * 32.0D));
			Field c = ReflectionUtils.getField(packet_class, "c");
			c.setAccessible(true);
			c.set(packet, (int) Math.floor(loc.getY() * 32.0D));
			Field d = ReflectionUtils.getField(packet_class, "d");
			d.setAccessible(true);
			d.set(packet, (int) Math.floor(loc.getZ() * 32.0D));
			Field e = ReflectionUtils.getField(packet_class, "e");
			e.setAccessible(true);
			e.set(packet, (byte) ((int) (loc.getYaw() * 256.0F / 360.0F)));
			Field f = ReflectionUtils.getField(packet_class, "f");
			f.setAccessible(true);
			f.set(packet, (byte) ((int) (loc.getPitch() * 256.0F / 360.0F)));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return packet;
	}

	public Object getRespawnPacket() {
		Class<?> packet_class = ReflectionUtils.getCraftClass("Packet205ClientCommand");
		Object packet = null;
		try {
			packet = packet_class.newInstance();

			Field a = ReflectionUtils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, 1);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return packet;
	}

	public Object getWatcher() {
		Class<?> watcher_class = ReflectionUtils.getCraftClass("DataWatcher");
		Object watcher = null;
		try {
			watcher = watcher_class.newInstance();

			Method a = ReflectionUtils.getMethod(watcher_class, "a", new Class<?>[] { int.class, Object.class });
			a.setAccessible(true);

			a.invoke(watcher, 0, visible ? (byte) 0 : (byte) 0x20);
			a.invoke(watcher, 6, (Float) (float) health);
			a.invoke(watcher, 7, (Integer) (int) 0);
			a.invoke(watcher, 8, (Byte) (byte) 0);
			a.invoke(watcher, 10, (String) name);
			a.invoke(watcher, 11, (Byte) (byte) 1);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return watcher;
	}
}