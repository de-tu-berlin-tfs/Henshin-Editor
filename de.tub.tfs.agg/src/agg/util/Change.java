// $Id: Change.java,v 1.9 2010/08/23 07:35:05 olga Exp $
package agg.util;

/**
 * Encapsulation of change information sent out by observable classes using the
 * method <code>notifyObservers(change)</code> of the class
 * <code>java.util.Observable</code>. Observer objects then receive such
 * information as the second argument to their <code>update()</code> method.
 * <p>
 * Subclasses may specifiy the detailed semantics.
 */
public class Change {
	// for all
	/**
	 * code for a not specified change object. No object of the official
	 * packages should create such a change object. But you can use it for your
	 * own projects.
	 */
	public final static int NOT_DEFINED = 0;

	/**
	 * code for the last message of a observable just before it is marked for
	 * deletion. All Observes should remove all connections to this object as
	 * fast as possible, so the garbage collector can remove the old observable.
	 */
	public final static int OBSERVABLE_GONE = 1;

	// for graphs
	/**
	 * code for a newly created subobject. This change will be send from graphs
	 * for example. The {@link #getItem} method should return the new object.
	 */
	public final static int OBJECT_CREATED = 10;

	/**
	 * code for a modified subobject. This change will be send from graphs if
	 * the attributes of graph objects would be changed for example. The
	 * {@link #getItem} method should return a Pair with the modified object as
	 * first element and an Integer code of attribute event ID
	 * {@link agg.attribute.AttrEvent#getID}
	 */
	public final static int OBJECT_MODIFIED = 11;

	public final static int WANT_MODIFY_OBJECT = 110;

	/**
	 * code for a subobject, which will be destroyed. This change will be send
	 * from graphs for example. The {@link #getItem} method should return the
	 * object to destroy.
	 */
	public final static int OBJECT_DESTROYED = 12;

	public final static int WANT_DESTROY_OBJECT = 120;

	/**
	 * code for two subobjects, which will be glued. This change will be send
	 * from graphs for example. The {@link #getItem} method should return a Pair
	 * of objects to glue with object to keep as first element and object to
	 * glue (and destroy) as second.
	 */
	public final static int OBJECT_GLUED = 13;

	// for morphism
	/**
	 * code for the adding of a mapping. This change will be send from morphisms
	 * for example. The {@link #getItem} method should return the object to map.
	 */
	public final static int MAPPING_ADDED = 20;

	/**
	 * code for the removing of a mapping. This change will be send from
	 * morphisms for example. The {@link #getItem} method should return the
	 * object to remove the mapping.
	 */
	public final static int MAPPING_REMOVED = 21;

	/**
	 * code for general modifications. This change will be send from graphs
	 * without a certain object. The {@link #getItem} method returns null.
	 */
	public final static int MODIFIED = 22;

	public final static int SOURCE_SET = 23;

	public final static int SOURCE_UNSET = 24;

	public final static int TARGET_SET = 25;

	public final static int TARGET_UNSET = 26;

	public final static int REDO_DONE = 27;

	/**
	 * an object that plays a role in the change.
	 */
	private Object itsItem;

	private Object itsItem2;
	
	/**
	 * a code for the change occured
	 */
	private int event;

	/** Construct myself to be a change information with the given item. */
	public Change(Object item) {
		this.itsItem = item;
		this.event = NOT_DEFINED;
	}// Change(Object)

	/**
	 * Construct myself to be a change information with the given item and
	 * representing the coded event.
	 * 
	 * @param event
	 *            a number code for the event happened, you can use the here
	 *            defined event codes or your own
	 * @param item
	 *            the object of the event (for example the newly created arc)
	 */
	public Change(int event, Object item) {
		this.itsItem = item;
		this.event = event;
	}// Change(Object,int)

	/**
	 * Construct myself to be a change information with the given item and its
	 * changer and representing the coded event.
	 * 
	 * @param event
	 *            a number code for the event happened, you can use the here
	 *            defined event codes or your own
	 * @param changed
	 *            the object of the event (for example the newly created arc)
	 * @param changer
	 *            the object which performed this change
	 */
	public Change(int event, Object changed, Object changer) {
		this.itsItem = changed;
		this.itsItem2 = changer;		
		this.event = event;
	}

	/**
	 * Construct myself to be a change information representing the coded event.
	 * 
	 * @param event
	 *            a number code for the event happened, you can use the here
	 *            defined event codes or your own
	 */
	public Change(int event) {
		this.itsItem = null;
		this.event = event;
	}// Change(int)

	/** Return my item. */
	public final Object getItem() {
		return this.itsItem;
	}// getItem
	
	/** Return the changer of the item. */
	public final Object getChanger() {
		return this.itsItem2;
	}

	/**
	 * returns the code for this event.
	 */
	public int getEvent() {
		return this.event;
	}// getEvent

}// class Change
