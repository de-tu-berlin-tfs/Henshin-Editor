package agg.termination;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import agg.xt_basis.GraphObject;

/**
 * Type layer is a set of type layers of a given layered graph grammar. 
 * A type means to be an object (Node resp. Edge) of a type graph.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class TypeLayerOfTypeGraph {

	private Hashtable<GraphObject, Integer> typeLayer;

	private Hashtable<GraphObject, Integer> types;

	/**
	 * Creates a new set of type layers for a given layered graph grammar.
	 * 
	 * @param types
	 *            The types of a graph grammar.
	 */
	public TypeLayerOfTypeGraph(Hashtable<GraphObject, Integer> types) {
		this.types = types;
		initTypeLayer();
	}

	/** Sets the layer of the specified type */
	public void setLayer(GraphObject type, int layer) {
		this.typeLayer.put(type, Integer.valueOf(layer));
		// System.out.println("type layer: "+((Integer)
		// this.typeLayer.get(type)).toString());

	}

	private void initTypeLayer() {
		this.typeLayer = new Hashtable<GraphObject, Integer>();
		for (Enumeration<GraphObject> keys = this.types.keys(); keys.hasMoreElements();) {
			GraphObject t = keys.nextElement();
			this.typeLayer.put(t, this.types.get(t));
			// System.out.println("type , layer: "+t+" ," +((Integer)
			// this.typeLayer.get(t)).toString());
		}
	}

	/**
	 * Returns the type layer. A type graph object is a key, a layer is a value.
	 * 
	 * @return The type layer.
	 */
	public Hashtable<GraphObject, Integer> getTypeLayer() {
		return this.typeLayer;
	}

	/**
	 * Returns the smallest layer of the type layer.
	 * 
	 * @return The smallest layer.
	 */
	public Integer getStartLayer() {
		int startLayer = Integer.MAX_VALUE;
		Integer result = null;
		for (Enumeration<GraphObject> keys = getTypeLayer().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer layer = getTypeLayer().get(key);
			if (layer.intValue() < startLayer) {
				startLayer = layer.intValue();
				result = layer;
			}
		}
		return result;
	}

	/**
	 * Inverts a type layer so that the layer is the key and the value is a set.
	 * 
	 * @return The inverted layer function.
	 */
	public Hashtable<Integer, HashSet<Object>> invertLayer() {
		Hashtable<Integer, HashSet<Object>> inverted = new Hashtable<Integer, HashSet<Object>>();
		for (Enumeration<GraphObject> keys = this.typeLayer.keys(); keys.hasMoreElements();) {
			GraphObject key = keys.nextElement();
			// System.out.println("TypeLayer:: "+key);
			Integer value = this.typeLayer.get(key);
			// System.out.println("TypeLayer:: "+value);
			HashSet<Object> invertedValue = inverted.get(value);
			if (invertedValue == null) {
				invertedValue = new HashSet<Object>();
				invertedValue.add(key);
				inverted.put(value, invertedValue);
			} else {
				invertedValue.add(key);
			}
		}
		return inverted;
	}

	/**
	 * Returns the type layer in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		String resultString = "Type:\t\tLayer:\n";
		for (Enumeration<GraphObject> keys = this.typeLayer.keys(); keys.hasMoreElements();) {
			GraphObject key = keys.nextElement();
			Integer value = this.typeLayer.get(key);
			resultString += key.toString() + "\t\t" + value.toString()
					+ "\n";
		}
		return resultString;
	}
}
