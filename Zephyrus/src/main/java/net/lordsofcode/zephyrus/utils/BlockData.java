package net.lordsofcode.zephyrus.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

//TODO store more data like chest, brewing stand and furnace contents
public class BlockData {

	private Material mat;
	private byte b;
	private Location loc;
	private BlockState state;
	
	/**
	 * Stores the material and data of a block
	 * @param mat The material of the block
	 * @param b The data of the block
	 */
	public BlockData(Block b) {
		this.mat = b.getType();
		this.b = b.getData();
		this.loc = b.getLocation();
		this.state = b.getState();
	}
	
	/**
	 * Gets the material of the block
	 * @return The material of the block
	 */
	public Material getType() {
		return mat;
	}
	
	/**
	 * Gets the data of the block
	 * @return A byte variable containing the data of the block
	 */
	public byte getData() {
		return b;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public BlockState getState() {
		return state;
	}
	
}
