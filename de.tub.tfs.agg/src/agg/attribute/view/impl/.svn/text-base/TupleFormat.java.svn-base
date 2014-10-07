package agg.attribute.view.impl;

import java.io.Serializable;

import agg.attribute.impl.AttrObject;

// import java.util.*;

/**
 * Format of an attribute tuple. Usually, only attribute types have a format
 * instance attached to them.
 * 
 * @author $Author: olga $
 * @version $Id: TupleFormat.java,v 1.4 2010/09/23 08:15:32 olga Exp $
 */
public class TupleFormat extends AttrObject implements Serializable {

	static final long serialVersionUID = 2585332603986553230L;

	/** Constant, returned when a member index is not in the representation. */
	public static final int HIDDEN = -1;

	/**
	 * A container with index positions (slots), containing indices for all
	 * tuple members.
	 */
	protected SlotSequence visibleSlots;

	/**
	 * A container with index positions (slots), containing indices for visible
	 * members only.
	 */
	protected SlotSequence allSlots;

//	private static transient int COUNTER = 0;

	/**
	 * Constructing with all parameters.
	 * 
	 * @param size
	 *            The initial number of slots.
	 */
	public TupleFormat(int size) {
		super();
		this.visibleSlots = new SlotSequence();
		this.allSlots = new SlotSequence();
		for (int i = 0; i < size; i++) {
			addMember(i);
		}
//		COUNTER++;
	}

	protected void finalize() {
//		COUNTER--;
	}

	/** Appends a new slot for 'index' in each of the position containers. */
	public void addMember(int index) {
		this.allSlots.incrementAllGreaterThan(index - 1);
		this.allSlots.addSlot(index);
		this.visibleSlots.incrementAllGreaterThan(index - 1);
		this.visibleSlots.addSlot(index);
	}

	/** Deletes the slot for 'index' in each of the position containers. */
	public void deleteMember(int index) {
		this.allSlots.deleteSlotForIndex(index);
		this.allSlots.decrementAllGreaterThan(index);
		this.visibleSlots.deleteSlotForIndex(index);
		this.visibleSlots.decrementAllGreaterThan(index);
	}

	//
	// Public Methods.
	//

	/** Returns the number of visible members. */
	public int getVisibleSize() {
		return this.visibleSlots.getSize();
	}

	/** Testing if the member at a given slot is visible. */
	public boolean isVisible(int slot) {
		int index = this.allSlots.getIndexAt(slot);
		return this.visibleSlots.getSlotForIndex(index) != HIDDEN;
	}

	/**
	 * Returns the member index at the specified slot, wrt the visible position
	 * container.
	 */
	public int getIndexAtVisibleSlot(int slot) {
		return this.visibleSlots.getIndexAt(slot);
	}

	/**
	 * Returns the member index at the specified slot; If the boolean parameter
	 * is true, the slot is interpreted wrt the container with all members,
	 * otherwise wrt the container with the visible members only.
	 */
	public int getIndexAtTotalSlot(int slot) {
		return this.allSlots.getIndexAt(slot);
	}

	/**
	 * Returns the slot, from the container with all members, for the specified
	 * member index.
	 */
	public int getTotalSlotForIndex(int index) {
		return this.allSlots.getSlotForIndex(index);
	}

	/**
	 * Returns the slot, from the container with visible members, for the
	 * specified member index.
	 */
	public int getVisibleSlotForIndex(int index) {
		return this.visibleSlots.getSlotForIndex(index);
	}

	/**
	 * Moves the member at "srcSlot" to "destSlot", inserting-wise. The slots
	 * refer to the container with all members. The slot order for visible
	 * members is adapted accordingly.
	 */
	public void moveSlotInserting(int srcSlot, int destSlot) {
		int dSlot = destSlot;
		// log("Before inserting slot "+srcSlot+" before " + destSlot );
		this.allSlots.moveSlotInserting(srcSlot, dSlot);

		// When inserting from e.g. 0 to before 3, then the result will be in
		// slot 2:
		if (srcSlot < dSlot)
			dSlot--;
		if (isVisible(dSlot)) {
			int index = this.allSlots.getIndexAt(dSlot);
			this.visibleSlots.deleteSlotForIndex(index);
			insertIndexInVisible(index);
		}
		// log("After inserting slot "+srcSlot+" before " + destSlot );
	}

	/**
	 * Inserting the index in the visible container, so that the order matches
	 * that of the total container.
	 */
	protected void insertIndexInVisible(int index) {
		int totalDestSlot = this.allSlots.getSlotForIndex(index);
		int visibleDestSlot;

		// log("index="+index+"; totalDestSlot="+totalDestSlot);
		for (visibleDestSlot = 0; visibleDestSlot < this.visibleSlots.getSize()
				&& getTotalSlot(visibleDestSlot) < totalDestSlot; visibleDestSlot++)
			;
		// log("i="+visibleDestSlot+"; getTotalSlot( i )="+
		// getTotalSlot( visibleDestSlot )+"; totalDestSlot="+totalDestSlot);

		// log("visibleDestSlot="+visibleDestSlot);

		this.visibleSlots.addSlot(index);
		this.visibleSlots.moveSlotInserting(this.visibleSlots.getSize() - 1,
				visibleDestSlot);
	}

	/**
	 * Returns the slot for visible members containing the same index as the
	 * specified slot for all members, or 'HIDDEN'.
	 */
	protected int getVisibleSlot(int totalSlot) {
		int index = this.allSlots.getIndexAt(totalSlot);
		return this.visibleSlots.getSlotForIndex(index);
	}

	/**
	 * Returns the slot for all members containing the same index as the
	 * specified slot for visible members.
	 */
	protected int getTotalSlot(int visibleSlot) {
		int index = this.visibleSlots.getIndexAt(visibleSlot);
		return this.allSlots.getSlotForIndex(index);
	}

	/**
	 * Setting, if the attribute member at the specified slot of this view
	 * should be visible or not.
	 * 
	 * @return true if a change was made, false otherwise.
	 */
	public boolean setVisible(boolean v, int slot) {
		int index = this.allSlots.getIndexAt(slot);
		boolean isVisible = isVisible(slot);

		// log("setVisible("+v+"); slot="+slot+"; index= "+index+" isVis=
		// "+isVisible);
		if (v && !isVisible) {
			insertIndexInVisible(index);
			// log("Shown.");
			return true;
		} else if (!v && isVisible) {
			this.visibleSlots.deleteSlotForIndex(index);
			// log("Hidden.");
			return true;
		} else {
			// log("Didn't change.");
			return false;
		}
	}

	public String toString() {
		String tmp = null;
		try {
			tmp = "open: " + this.allSlots.toString() + "; mask: "
					+ this.visibleSlots.toString();
		} catch (NullPointerException e) {
			tmp = "open: " + "???" + "; mask: " + "???";
		}
		return tmp;
	}
}
/*
 * $Log: TupleFormat.java,v $
 * Revision 1.4  2010/09/23 08:15:32  olga
 * tuning
 *
 * Revision 1.3  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:04 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:07 olga Imported sources
 * 
 * Revision 1.8 2000/06/05 14:08:23 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.7 2000/04/05 12:11:31 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
