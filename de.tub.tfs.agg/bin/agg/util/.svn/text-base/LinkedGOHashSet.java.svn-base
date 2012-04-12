/**
 * 
 */
package agg.util;


import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.GraphObject;


/**
 * @author olga
 *
 */
public class LinkedGOHashSet<T extends GraphObject> extends 
//java.util.HashSet<T>
java.util.LinkedHashSet<T> 
{
	
	public LinkedGOHashSet() {
		super(5, .75f); //3.f); 
	}
	
	public LinkedGOHashSet(int initialCapacity) {
		super(initialCapacity, .75f); //3.f);
	}
		
	public LinkedGOHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
	
	public LinkedGOHashSet(Collection<? extends T> c, float loadFactor) {
		super(c.size(), loadFactor);
		addAll(c);
	}
	

	
//	private T get(int indx) {		
//		T obj = null;
//		int i = 0;
//		final Iterator<T> iter = this.iterator();
//		while (iter.hasNext()) {
//			if (i==indx) {
//				obj = iter.next();
//				break;
//			}
//			iter.next();
//			i++;
//		}
//		return obj;
//	}
	
	public int indexOf(T obj, int startIndx)  {
		int i = startIndx;
		final Iterator<T> iter = this.iterator();
		while (iter.hasNext()) {
			if (obj == iter.next()) {
				break;
			}
			iter.next();
			i++;
		}
		return i;
	}
	
	public int indexOf(T obj)  {
		return this.indexOf(obj, 0);
	}
	
	
	public Enumeration<T> elements() {
		return (new Vector<T>(this)).elements();
	}

	public List<T> list() {
		return (new Vector<T>(this));
	}
	
	
}
