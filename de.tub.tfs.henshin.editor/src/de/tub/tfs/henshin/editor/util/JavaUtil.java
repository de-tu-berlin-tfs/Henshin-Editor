/**
 * JavaUtil.java
 *
 * Created 30.12.2011 - 14:52:43
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;

/**
 * @author nam
 * 
 */
public final class JavaUtil {

	public static interface IPredicate<T> {
		public boolean assertTrue(T obj);
	}

	/**
	 * Returns a new {@link Map map} with all {@code key - value} pairs of a
	 * given {@link Map map} being swapped. The given {@link Map map} should be
	 * <em>injective</em>.
	 * 
	 * @param map
	 *            a {@link Map}.
	 * @return a copy of {@code map} with all {@code key - value} pairs being
	 *         swapped.
	 */
	public static <K, V> Map<V, K> swapKeysValues(final Map<K, V> map) {
		Map<V, K> swapped = new HashMap<V, K>();

		for (Entry<K, V> entry : map.entrySet()) {
			swapped.put(entry.getValue(), entry.getKey());
		}

		return swapped;
	}

	/**
	 * @param objs
	 * @return
	 */
	public static boolean notNull(Object... objs) {
		for (Object o : objs) {
			if (o == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param l
	 * @param cond
	 * @return
	 */
	public static <T> T find(Collection<T> l, IPredicate<T> cond) {
		for (T o : l) {
			if (cond.assertTrue(o)) {
				return o;
			}
		}

		return null;
	}

	/**
	 * Returns the {@link Class type} of the elements in a given {@link List
	 * list}, if all these elements are of a same type.
	 * 
	 * @param l
	 * @return
	 */
	public static Class<?> getDataType(Collection<?> l) {
		Assert.isLegal(l != null);

		Class<?> result = null;

		if (!l.isEmpty()) {
			for (Object o : l) {
				Class<?> clazz = o.getClass();

				if (result == null) {
					result = clazz;
				} else if (!clazz.equals(result)) {
					result = null;

					break;
				}
			}
		}

		return result;
	}

	/**
	 * @param c
	 * @param expectedType
	 * @return
	 */
	public static boolean checkContentType(Collection<?> c,
			Class<?> expectedType) {
		for (Object o : c) {
			if (!(expectedType.isInstance(o))) {
				return false;
			}
		}

		return true;
	}

	/**
     * 
     */
	private JavaUtil() {
	}

	/**
	 * 
	 * @param bigList
	 * @param smallList
	 * @return
	 */
	public static <T> List<T> subList(Collection<T> bigList, Collection<T> smallList) {
		List<T> subList = new ArrayList<T>();
		
		for (T t : bigList) {
			if (!smallList.contains(t)) {
				subList.add(t);
			}
		}
		return subList;
	}
}
