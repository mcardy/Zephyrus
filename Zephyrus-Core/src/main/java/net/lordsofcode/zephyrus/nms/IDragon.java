package net.lordsofcode.zephyrus.nms;

import org.bukkit.Location;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public interface IDragon {

	public void setHealth(int percent);
	public void setName(String name);
	
	public Object getSpawnPacket();
	public Object getDestroyPacket();
	public Object getMetaPacket(Object watcher);
	public Object getTeleportPacket(Location loc);
	public Object getWatcher();
	
}
