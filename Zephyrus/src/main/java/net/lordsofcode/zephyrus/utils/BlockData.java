package net.lordsofcode.zephyrus.utils;

import org.bukkit.Material;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @version 1.0.0
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class BlockData {

	private Material mat;
	private byte b;
	
	/**
	 * Stores the material and data of a block
	 * @param mat The material of the block
	 * @param b The data of the block
	 */
	public BlockData(Material mat, byte b) {
		this.mat = mat;
		this.b = b;
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
	
}
