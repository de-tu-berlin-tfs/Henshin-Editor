package agg.attribute.view;

import java.io.Serializable;

import agg.attribute.AttrTuple;
import agg.util.Disposable;

/**
 * Mediator interface, facilitating view-dependent access to attribute objects.
 * The "Mediator" design pattern was chosen for a loose and lightweight coupling
 * of attribute objects and their visual representation. It also allows
 * view-dependent (editor) and view-independent (graph transformation unit)
 * users of the attribute component to identify their attribute objects by the
 * same handles.
 * 
 * Please pay attention that the integer selectors for attribute tuple members
 * are not absolute indexes as in the AttrTuple interface. Rather, they are
 * "slots", member positions in respect to this view.
 * 
 * There can be an arbitrary number of views, each holding exactly one
 * (changeable) representation of an attribute tuple (order of members, hiding
 * of members).
 * 
 * Each view has two 'subviews', an 'open view' and a 'masked view'. They
 * basically share the same tuple layout information, with one exception. Tuple
 * access using the subview obtained by calling getOpenView() does not hide the
 * 'hidden' members, although they can be hidden by invoking setVisibleAt(
 * aTuple, false, aSlot ). The hiding effect only occurs when using the subview
 * obtained by calling getMaskedView(). The order of members is consistent, i.e.
 * if <CODE> memberA == aTuple.getMember( slotA1, aViewSetting.getOpenView()) &&
 * mamberB == aTuple.getMember( slotB1, aViewSetting.getOpenView()) && memberA ==
 * aTuple.getMember( slotA2, aViewSetting.getMaskedView()) && memberB ==
 * aTuple.getMember( slotB2, aViewSetting.getMaskedView()) && slotA1 < slotB1
 * </CODE> then <CODE> slotA2 < slotB2 </CODE>
 * 
 * The event indices (slots) are delivered according to the view for which the
 * observer registered. When accessing a tuple using these slots always remember
 * to supply the appropriate view. It can be obtained by invoking getView() on
 * the delivered event.
 * 
 * @version $Id: AttrViewSetting.java,v 1.3 2008/04/07 09:36:56 olga Exp $
 * @author $Author: olga $
 * 
 */
public interface AttrViewSetting extends Serializable, Disposable {

	static final long serialVersionUID = 1401064072022382181L;

	/**
	 * Returns the 'open subview', manipulating of visibility of members
	 * (setVisibleAt(...)) only affects the other subview.
	 * 
	 * @see agg.attribute.view.AttrViewSetting#getMaskedView().
	 */
	public AttrViewSetting getOpenView();

	/**
	 * Returns the 'masked subview', manipulating of visibility of members
	 * (setVisibleAt(...)) affects this subview.
	 */
	public AttrViewSetting getMaskedView();

	/** Adding an observer for an attribute tuple's representation. */
	public void addObserver(AttrViewObserver o, AttrTuple attr);

	/** Removing an observer for an attribute tuple's representation. */
	public void removeObserver(AttrViewObserver o, AttrTuple attr);

	
	public boolean hasObserver(AttrTuple attr);
	
	/** Returns the slot position in the view layout for 'attr' at 'index'. */
	public int convertIndexToSlot(AttrTuple attr, int index);

	/** Returns the index for 'attr' at 'slot', as set in this view layout. */
	public int convertSlotToIndex(AttrTuple attr, int slot);

	/** Returns the number of members that are visible in this view. */
	public int getSize(AttrTuple attr);

	/**
	 * Testing if the attribute member at the specified slot is visible in this
	 * view.
	 */
	public boolean isVisible(AttrTuple attr, int slot);

	/**
	 * Setting, if the attribute member at the specified slot of this view
	 * should be visible or not.
	 */
	public void setVisibleAt(AttrTuple attr, boolean b, int slot);

	/**
	 * Setting, if all attribute members of 'attr' should either be at once made
	 * visible or hidden.
	 */
	public void setAllVisible(AttrTuple attr, boolean b);

	/**
	 * Set visibility to true, if declaration type member of AttrTuple attr is
	 * visible, else - to false.
	 */
	public void setVisible(AttrTuple attr);

	/** Moves the member at "srcSlot" to "destSlot", inserting-wise. */
	public void moveSlotInserting(AttrTuple attr, int srcSlot, int destSlot);

	/** Moves the member at "srcSlot" to "destSlot", appending-wise. */
	public void moveSlotAppending(AttrTuple attr, int srcSlot, int destSlot);

	/**
	 * Reset the tuple layout, so each slot number is the same as the index it
	 * contains, with all slots visible.
	 */
	public void resetTuple(AttrTuple attr);
}
/*
 * $Log: AttrViewSetting.java,v $
 * Revision 1.3  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.2  2007/09/10 13:05:51  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:06 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:11:24 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
