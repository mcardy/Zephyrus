package net.lordsofcode.zephyrus.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ReflectionUtils {

	public static void setField(Object obj, Object value, String fieldName) {
		try {
			getField(obj.getClass(), fieldName).set(obj, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a field
	 * @param cl The class to retrieve the field from
	 * @param fieldName The name of the field
	 * @return
	 */
	public static Field getField(Class<?> cl, String fieldName) {
		try {
			Field field = cl.getDeclaredField(fieldName);
			return field;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves a method
	 * @param cl The class to retrieve the method from
	 * @param method The name of the method
	 * @param args The argument types that the method should have
	 * @return null if no method was found
	 */
	public static Method getMethod(Class<?> cl, String method, Class<?>[] args) {
		for (Method m : cl.getMethods()) {
			if (m.getName().equals(method) && classListEqual(args, m.getParameterTypes())) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Retrieves a method
	 * @param cl The class to retrieve the method from
	 * @param method The name of the method
	 * @param args The number of arguments that the method has
	 * @return null if no method was found
	 */
	public static Method getMethod(Class<?> cl, String method, Integer args) {
		for (Method m : cl.getMethods()) {
			if (m.getName().equals(method) && args.equals(new Integer(m.getParameterTypes().length))) {
				return m;
			}
		}
		return null;
	}

	/**
	 * Retrieves a method
	 * @param cl The class to retrieve the method from
	 * @param method The name of the method
	 * @return null if no method was found
	 */
	public static Method getMethod(Class<?> cl, String method) {
		for (Method m : cl.getMethods()) {
			if (m.getName().equals(method)) {
				return m;
			}
		}
		return null;
	}

	private static boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;

		if (l1.length != l2.length) {
			return false;
		}
		
		for (int i = 0; i < l1.length; i++) {
			if (l1[i] != l2[i]) {
				return false;
			}
		}
		return equal;
	}
}
