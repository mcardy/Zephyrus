package net.lordsofcode.zephyrus.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.api.ISpell;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellLoader {
	
	private List<ISpell> spellMap = new ArrayList<ISpell>();
	
	/**
	 * Loads spells from the spell folder
	 * @throws MalformedURLException 
	 */
	public void loadSpells() throws MalformedURLException {
		File dir = new File(Zephyrus.getPlugin().getDataFolder(), "Spells");
		if (!dir.exists()) {
			dir.mkdirs();
			Zephyrus.getPlugin().getLogger().info("Creating Spells folder...");
			return;
		}
		URL[] urls = new URL[] { dir.toURI().toURL() };
		ClassLoader cl = new URLClassLoader(urls,
				Zephyrus.class.getClassLoader());
		for (File f : dir.listFiles()) {
			try {
				if (f.getName().endsWith(".class")) {
					Class<?> cls = cl.loadClass(f.getName().replace(".class",
							""));
					Object obj = cls.newInstance();
					if (obj instanceof ISpell) {
						spellMap.add((ISpell) obj);
					}
				} else if (f.getName().endsWith(".jar")) {
					JarFile jFile = new JarFile(f.getPath());
					Enumeration<JarEntry> e = jFile.entries();
					urls = new URL[] { f.toURI().toURL() };
					cl = new URLClassLoader(urls,
							Zephyrus.class.getClassLoader());
					while (e.hasMoreElements()) {
						JarEntry entry = e.nextElement();
						if (entry.isDirectory()
								|| !entry.getName().endsWith(".class"))
							continue;
						String className = entry.getName().substring(0,
								entry.getName().length() - 6);
						className = className.replace('/', '.');
						try {
							Class<?> cls = cl.loadClass(className);
							Object obj = cls.newInstance();
							if (obj instanceof ISpell) {
								spellMap.add((ISpell) obj);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			} catch (Exception ex) {
				Zephyrus.getPlugin()
						.getLogger()
						.warning(
								"Error loading file " + f.getName() + "! "
										+ ex.getMessage());
			}
		}
	}

	/**
	 * Registers the pending loaded spells in the private class map
	 */
	public void registerSpells() {
		for (int i = 0; i < spellMap.size(); i++) {
			Zephyrus.registerSpell(spellMap.get(i));
		}
		spellMap.clear();
	}

}
