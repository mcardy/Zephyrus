package net.lordsofcode.zephyrus.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

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

	private Set<Class<? extends ISpell>> classMap = new HashSet<Class<? extends ISpell>>();

	/**
	 * Loads spells from the spell folder
	 * 
	 * @throws MalformedURLException
	 *             Thrown when the spell loader can't access the directory for
	 *             any reason.
	 */
	public void loadSpells() throws MalformedURLException {
		File file = new File(Zephyrus.getPlugin().getDataFolder(), "Spells");
		if (!file.exists()) {
			file.mkdirs();
			Zephyrus.getPlugin().getLogger().info("Creating Spells folder...");
			return;
		}
		URL[] urls = new URL[] { file.toURI().toURL() };
		ClassLoader cl = new URLClassLoader(urls,
				Zephyrus.class.getClassLoader());
		for (File f : file.listFiles()) {
			if (f.getName().endsWith(".class")) {
				try {
					Class<?> cls = cl.loadClass(f.getName().replace(".class",
							""));
					Object obj = cls.newInstance();
					if (obj instanceof ISpell) {
						@SuppressWarnings("unchecked")
						Class<? extends ISpell> clazz = (Class<? extends ISpell>) cls;
						classMap.add(clazz);
					}
				} catch (ClassNotFoundException ex) {
					Zephyrus.getPlugin()
							.getLogger()
							.warning(
									"Error loading class " + f.getName()
											+ " from Spells folder...");
				} catch (InstantiationException e) {
					Zephyrus.getPlugin()
							.getLogger()
							.warning(
									"Error loading class "
											+ f.getName()
											+ ". Contact the maker of the spell.");
				} catch (IllegalAccessException e) {
					Zephyrus.getPlugin()
							.getLogger()
							.warning(
									"Error loading class " + f.getName()
											+ " from Spells folder...");
				}
			}
		}
	}

	/**
	 * Registers the pending loaded spells in the private class map
	 */
	public void registerSpells() {
		for (Class<? extends ISpell> clazz : classMap) {
			try {
				ISpell spell = clazz.newInstance();
				Zephyrus.registerSpell(spell);
			} catch (InstantiationException e) {
				Zephyrus.getPlugin()
						.getLogger().warning("Error loading spell "
								+ clazz.getName()+ "! Contact the spell author to fix this.");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
