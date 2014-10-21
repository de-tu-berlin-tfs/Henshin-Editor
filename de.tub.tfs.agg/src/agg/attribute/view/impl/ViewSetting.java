package agg.attribute.view.impl;

import java.lang.ref.WeakReference;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.AttrEvent;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ManagedObject;
import agg.attribute.impl.TupleObject;
import agg.attribute.view.AttrViewObserver;
import agg.attribute.view.AttrViewSetting;

/**
 * Common superclass for OpenViewSetting and MaskedViewSetting. Provides most
 * routines for handling own observers and event propagation. Most methods that
 * actually manipulate the layout of attribute tuples are in the subclasses
 * mentioned above.
 * 
 * @author $Author: olga $
 * @version $Id: ViewSetting.java,v 1.7 2010/09/23 08:15:32 olga Exp $
 */
public abstract class ViewSetting extends ManagedObject implements
		AttrViewSetting {

	static final long serialVersionUID = -582744399860233794L;

	/** Table of observers for tuples. */
	transient protected Hashtable<AttrTuple, Vector<WeakReference<AttrViewObserver>>> 
	observerTab = new Hashtable<AttrTuple, Vector<WeakReference<AttrViewObserver>>>();

	Object obsvs;
	
	public ViewSetting(AttrTupleManager m) {
		super(m);
	}

	public void dispose() {
	}

	public void finalize() {
	}

	/**
	 * Getting the format for a (type) tuple. Formats are created lazily "on
	 * demand". It means that when there is no format for the specified
	 * AttrTuple yet, it is created and returned.
	 */
	protected abstract TupleFormat getFormat(AttrTuple attr);

	/** Removing the format for a (type) tuple. */
	protected abstract void removeFormat(AttrType type);

	/**
	 * Getting the observers of a tuple managed in this view.
	 * 
	 * @return A vector of observers for the specified tuple or null if the
	 *         tuple is not in this view.
	 */
	protected Vector<WeakReference<AttrViewObserver>> getObserversForTuple(
			AttrTuple attr) {
		if (this.observerTab == null)
			this.observerTab = new Hashtable<AttrTuple, Vector<WeakReference<AttrViewObserver>>>();
		return this.observerTab.get(attr);
	}

	/** Adding an observer for an attribute to its observers' table. */
	protected void addObserverForTuple(AttrViewObserver o, AttrTuple attr) {
		Vector<WeakReference<AttrViewObserver>> observers = getObserversForTuple(attr);
		if (observers == null) {
			observers = new Vector<WeakReference<AttrViewObserver>>(4);
			this.observerTab.put(attr, observers);
			observers.add(new WeakReference<AttrViewObserver>(o));
		}
		else
		if (find(observers, o) == null) {
			observers.add(new WeakReference<AttrViewObserver>(o));
		}
	}

	/** Removing an observer for an attribute from its observers' table. */
	protected void removeObserverForTuple(AttrViewObserver o, AttrTuple attr) {
		Vector<WeakReference<AttrViewObserver>> observers = getObserversForTuple(attr);
		if (observers == null)
			return;
		
		removeElement(observers, o);		
	}

	
	/**
	 * finds the WeakReference which contains the AttrViewObserver or returns
	 * null
	 */
	private WeakReference<?> find(Vector<WeakReference<AttrViewObserver>> observers, AttrViewObserver o) {
		for (int i= 0; i<observers.size(); i++) {
			WeakReference<AttrViewObserver> wr = observers.get(i);
			if (wr.get() == null) {
				observers.remove(i);
				i--;
			}
			else if (o == wr.get()) {
				return wr;
			}
		}
		return null;
	}

	private void removeElement(Vector<WeakReference<AttrViewObserver>> observers, AttrViewObserver o) {
		for (int i= 0; i<observers.size(); i++) {
			WeakReference<AttrViewObserver> wr = observers.get(i);
			if (wr.get() == null) {
				observers.remove(i);
				i--;
			}
			else if (o == wr.get()) {
				observers.remove(i);
				return;
			}
		}
	}

	boolean contains(Vector<WeakReference<AttrViewObserver>> observers, AttrViewObserver o) {
		return (find(observers, o) != null);
	}
	
	/**
	 * Called by fireAttrChanged() from this class. The change event is sent
	 * only to observers who are interested in the attribute's representation.
	 */
	protected void notifyObservers(AttrTuple attr, int id, int slot0, int slot1) {
		AttrViewObserver obs;
		Vector<WeakReference<AttrViewObserver>> observers = getObserversForTuple(attr);
		obsvs = observers;
		if (observers == null)
			return;
		
		TupleViewEvent evt = new TupleViewEvent(attr, id, slot0, slot1, this);

		for (Enumeration<WeakReference<AttrViewObserver>> en = observers.elements(); en.hasMoreElements();) {
			WeakReference<AttrViewObserver> wr = en.nextElement();
			obs = wr.get();
			if (obs == null)
				observers.removeElement(wr);
			else
				obs.attributeChanged(evt);
		}
	}

	/**
	 * Called from within this class whenever the format (layout) of an
	 * attribute is changed. Since the change affects only the attribute type
	 * representation, two things are important:
	 * 
	 * 1. Only observers that are interested in the attribute's REPRESENTATION
	 * should be notified. This is ensured by the sub-method notifyObservers().
	 * 
	 * 2. All the observers of attribute types that are attribute instances have
	 * to notify THEIR view observers as well. Usually this propagating
	 * mechanism is provided by the TupleObject class. But here, it cannot be
	 * used (see 1.). Therefore, this method loops over all the observers of
	 * 'attr' if it's a type and recursively calls fireAttrChanged() for all the
	 * AttrInstance instances among them. The same holds for interfaces.
	 */
	protected void fireAttrChanged(TupleObject attr, int id, int slot0,
			int slot1) {
		Object obj;
		Enumeration<?> objEnum;
		notifyObservers(attr, id, slot0, slot1);

		if (obsvs == null) return;
		
		for (objEnum = attr.getObservers(); objEnum.hasMoreElements();) {
			obj = objEnum.nextElement();
			if (obj instanceof AttrTuple) {
				fireAttrChanged((TupleObject) obj, id, slot0, slot1);
			}
		}
	}

	/** Propagating incoming attribute events to my view observers. */
	protected void propagateAttrEvent(AttrEvent event) {
		// System.out.println("ViewSetting.propagateAttrEvent "+event.getID());
		AttrTuple attr = event.getSource();
		// System.out.println(attr);
		if (attr == null)
			return;

		int id = event.getID();
		int index0 = event.getIndex0();
		int index1 = event.getIndex1();

		if (id == AttrEvent.GENERAL_CHANGE) {
			notifyObservers(attr, id, index0, index1);
		} else {
			int slot0 = convertIndexToSlot(attr, event.getIndex0());
			int slot1 = convertIndexToSlot(attr, event.getIndex1());
			notifyObservers(attr, id, slot0, slot1);
		}
	}

	//
	// Part of AttrViewSetting interface implementation.

	public void moveSlotAppending(AttrTuple attr, int srcSlot, int destSlot) {
		moveSlotInserting(attr, srcSlot, destSlot + 1);
	}

	public String toString(AttrTuple attr) {
		return getFormat(attr).toString();
	}
}
