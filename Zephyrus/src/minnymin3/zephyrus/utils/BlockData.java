package minnymin3.zephyrus.utils;

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
	
	public BlockData(Material mat, byte b) {
		this.mat = mat;
		this.b = b;
	}
	
	public Material getType() {
		return mat;
	}
	
	public byte getData() {
		return b;
	}
	
}
