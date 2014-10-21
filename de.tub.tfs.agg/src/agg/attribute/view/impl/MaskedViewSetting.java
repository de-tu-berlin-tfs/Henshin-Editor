package agg.attribute.view.impl;

import java.lang.ref.WeakReference;
import java.util.Vector;

import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.AttrEvent;
import agg.attribute.impl.TupleObject;
import agg.attribute.impl.DeclMember;
import agg.attribute.view.AttrViewEvent;
import agg.attribute.view.AttrViewObserver;
import agg.attribute.view.AttrViewSetting;

/**
 * @version $Id: MaskedViewSetting.java,v 1.6 2010/09/23 08:15:32 olga Exp $
 * @author $Author: olga $
 */
public class MaskedViewSetting extends ViewSetting {

	static final long serialVersionUID = -7941882634203060406L;

	protected OpenViewSetting openView;

	public MaskedViewSetting(OpenViewSetting ov) {
		super(ov.getManager());
		this.openView = ov;
	}

	/**
	 * Getting the tuple format for a (type) tuple. Format tuples are created
	 * lazily "on demand". It means that when there is no format for the
	 * specified AttrTuple yet, it is created and returned.
	 */
	protected TupleFormat getFormat(AttrTuple attr) {
		return ((ViewSetting) getOpenView()).getFormat(attr);
	}

	/** Removing the format for a (type) tuple. */
	protected void removeFormat(AttrType type) {
		((ViewSetting) getOpenView()).removeFormat(type);
	}

	// protected ViewSetting getSharingView(){ return openView; }

	//
	// Public methods
	//

	public AttrViewSetting getOpenView() {
		return this.openView;
	}

	public AttrViewSetting getMaskedView() {
		return this;
	}

	public void addObserver(AttrViewObserver o, AttrTuple attr) {
		this.openView.ensureBeingAttrObserver(attr);
		addObserverForTuple(o, attr);
	}

	public void removeObserver(AttrViewObserver o, AttrTuple attr) {
		removeObserverForTuple(o, attr);
		// If 'attr' has no more view observers, there's no point observing it.
		this.openView.stopObservingIfNeedless(attr);
	}

	public boolean hasObserver(AttrTuple attr) {
		Vector<WeakReference<AttrViewObserver>> observers =  getObserversForTuple(attr);
		return (observers == null || observers.isEmpty())?false:true;
	}
	
	public int convertIndexToSlot(AttrTuple attr, int index) {
		TupleFormat f = getFormat(attr);
		return f.getVisibleSlotForIndex(index);
	}

	public int convertSlotToIndex(AttrTuple attr, int slot) {
		TupleFormat f = getFormat(attr);
		return f.getIndexAtVisibleSlot(slot);
	}

	public int getSize(AttrTuple attr) {
		return getFormat(attr).getVisibleSize();
	}

	public boolean isVisible(AttrTuple attr, int slot) {
		return getFormat(attr).isVisible(slot);
	}

	public void setVisibleAt(AttrTuple attr, boolean b, int slot) {
		TupleFormat f = getFormat(attr);
		synchronized (f) {
			f.setVisible(b, f.getTotalSlot(slot));

			((DeclMember) ((TupleObject) attr).getTupleType().getMemberAt(slot))
					.setVisible(b);

			fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrViewEvent.MEMBER_VISIBILITY, slot, slot);
			this.openView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void setAllVisible(AttrTuple attr, boolean b) {
		this.openView.setAllVisible(attr, b);
	}

	public void setVisible(AttrTuple attr) {
		this.openView.setVisible(attr);
	}

	public void moveSlotInserting(AttrTuple attr, int srcSlot, int destSlot) {
		TupleFormat f = getFormat(attr);
		synchronized (f) {
			f.moveSlotInserting(f.getTotalSlot(srcSlot), f
					.getTotalSlot(destSlot));
			fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrViewEvent.MEMBER_MOVED, srcSlot, destSlot);
			this.openView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void resetTuple(AttrTuple attr) {
		this.openView.resetTuple(attr);
	}
}
/*
 * $Log: MaskedViewSetting.java,v $
 * Revision 1.6  2010/09/23 08:15:32  olga
 * tuning
 *
 * Revision 1.5  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.4  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/12/13 13:33:05 enrico
 * reimplemented code
 * 
 * Revision 1.2 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:04 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:07 olga Imported sources
 * 
 * Revision 1.5 2000/06/05 14:08:19 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.4 2000/04/05 12:11:26 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
