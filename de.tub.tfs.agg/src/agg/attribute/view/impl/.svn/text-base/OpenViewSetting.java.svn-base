package agg.attribute.view.impl;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.AttrEvent;
import agg.attribute.AttrObserver;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.TupleObject;
import agg.attribute.view.AttrViewEvent;
import agg.attribute.view.AttrViewObserver;
import agg.attribute.view.AttrViewSetting;

/**
 * @author $Author: olga $
 * @version $Id: OpenViewSetting.java,v 1.12 2010/09/23 08:15:32 olga Exp $
 */
public class OpenViewSetting extends ViewSetting implements AttrObserver {

	/** Table of tuple formats for (type) tuples. */
	protected Hashtable<DeclTuple, TupleFormat> formatTab;

	protected MaskedViewSetting maskedView;

	protected int lastOpenDeletedSlot0 = -1;

	protected int lastOpenDeletedSlot1 = -1;

	protected int lastMaskedDeletedSlot0 = -1;

	protected int lastMaskedDeletedSlot1 = -1;

	static final long serialVersionUID = 4242537253046200014L;

	public OpenViewSetting(AttrTupleManager m) {
		super(m);
		this.maskedView = new MaskedViewSetting(this);
		this.formatTab = new Hashtable<DeclTuple, TupleFormat>(50);
	}

	/** Getting the tuple format for a (type) tuple, raw style. */
	protected TupleFormat rawGetFormat(AttrTuple attr) {
		DeclTuple type = ((TupleObject) attr).getTupleType();
		TupleFormat format = this.formatTab.get(type);
		return format;
	}

	/** Getting the tuple format for a (type) tuple, raw style. */
	protected TupleFormat rawAddFormatFor(AttrTuple attr) {
		DeclTuple type = ((TupleObject) attr).getTupleType();
		TupleFormat format = new TupleFormat(type.getNumberOfEntries());
		this.formatTab.put(type, format);
		return format;
	}

	/**
	 * Getting the tuple format for a (type) tuple. Format tuples are created
	 * lazily "on demand". It means that when there is no format for the
	 * specified AttrTuple yet, it is created and returned.
	 */
	protected TupleFormat getFormat(AttrTuple attr) {
		TupleFormat format = rawGetFormat(attr);
		if (format == null) {
			format = rawAddFormatFor(attr);
		}
		return format;
	}

	/** Removing the format for a (type) tuple. */
	public synchronized void removeFormat(AttrType type) {
		DeclTuple typ = ((TupleObject) type).getTupleType();
		this.formatTab.remove(typ);
	}

	// protected ViewSetting getSharingView(){ return maskedView; }

	protected boolean hasObserversForTuple(AttrTuple attr) {
		Vector<WeakReference<AttrViewObserver>> observers1 = getObserversForTuple(attr);
		Vector<WeakReference<AttrViewObserver>> observers2 = this.maskedView.getObserversForTuple(attr);
		if ((observers1 == null || observers1.isEmpty())
				&& (observers2 == null || observers2.isEmpty())) {
			return false;
		} 
		return true;
	}

	//
	// Public methods
	//

	/**
	 * Called by addObserver(), from MaskedViewSetting as well as from this
	 * class.
	 */
	public void ensureBeingAttrObserver(AttrTuple attr) {
		// System.out.println("OpenViewSetting.ensureBeingAttrObserver vor
		// getFormat...");
		if (attr == null)
			return;

		DeclTuple type = ((TupleObject) attr).getTupleType();
		if (!hasObserversForTuple(attr)) {
			type.addObserver(this);
			attr.addObserver(this);
			getFormat(attr);
		}
	}

	/**
	 * Called by removeObserver(), from MaskedViewSetting as well as from this
	 * class.
	 */
	public void stopObservingIfNeedless(AttrTuple attr) {
//		DeclTuple type = ((TupleObject) attr).getTupleType();
		if (!hasObserversForTuple(attr)) {
			attr.removeObserver(this);
			// To do:
			// type.removeObserver( this )
			// if we're not observating any instances of 'type' anymore.
			// Not urgent.
		}
	}

	//
	// AttrViewSetting interface implementation
	//

	public AttrViewSetting getOpenView() {
		return this;
	}

	public AttrViewSetting getMaskedView() {
		return this.maskedView;
	}

	public void addObserver(AttrViewObserver o, AttrTuple attr) {
		if (attr == null)
			return;

		ensureBeingAttrObserver(attr);
		addObserverForTuple(o, attr);
	}

	public void removeObserver(AttrViewObserver o, AttrTuple attr) {
		// System.out.println("OpenViewSetting.removeObserver ...");
		removeObserverForTuple(o, attr);
		// If 'attr' has no more view observers, there's no point observing it.
		stopObservingIfNeedless(attr);
	}

	public boolean hasObserver(AttrTuple attr) {
		Vector<WeakReference<AttrViewObserver>> observers =  getObserversForTuple(attr);
		return (observers == null || observers.isEmpty())?false:true;
	}
	
	public int convertIndexToSlot(AttrTuple attr, int index) {
		TupleFormat f = getFormat(attr);
		return f.getTotalSlotForIndex(index);
	}

	public int convertSlotToIndex(AttrTuple attr, int slot) {
		TupleFormat f = getFormat(attr);
		return f.getIndexAtTotalSlot(slot);
	}

	public int getSize(AttrTuple attr) {
		return attr.getNumberOfEntries();
	}

	public boolean isVisible(AttrTuple attr, int slot) {
		TupleFormat f = getFormat(attr);
		return f.isVisible(slot);
	}

	public void setVisibleAt(AttrTuple attr, boolean b, int slot) {
		TupleFormat f = getFormat(attr);
		synchronized (f) {
			f.setVisible(b, slot);

			((DeclMember) (((TupleObject) attr).getTupleType())
					.getMemberAt(slot)).setVisible(b);

			fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrViewEvent.MEMBER_VISIBILITY, slot, slot);
			this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void setAllVisible(AttrTuple attr, boolean b) {
		TupleFormat f = getFormat(attr);
		synchronized (f) {
			for (int i = 0; i < attr.getNumberOfEntries(); i++) {
				f.setVisible(b, i);
				((DeclMember) (((TupleObject) attr).getTupleType())
						.getMemberAt(i)).setVisible(b);
			}
			fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
			this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void setVisible(AttrTuple attr) {
		TupleFormat f = getFormat(attr);
		synchronized (f) {
			for (int i = 0; i < attr.getNumberOfEntries(); i++) {
				boolean b = ((DeclMember) (((TupleObject) attr).getTupleType())
						.getMemberAt(i)).isVisible();
				f.setVisible(b, i);
			}
			fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
			this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void moveSlotInserting(AttrTuple attr, int srcSlot, int destSlot) {
		getFormat(attr).moveSlotInserting(srcSlot, destSlot);

		fireAttrChanged(((TupleObject) attr).getTupleType(),
				AttrViewEvent.MEMBER_MOVED, srcSlot, destSlot);
		if (isVisible(attr, destSlot)) {
			this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
					AttrEvent.GENERAL_CHANGE, 0, 0);
		}
	}

	public void resetTuple(AttrTuple attr) {
		// Thread.dumpStack();
		// System.out.println("OpenViewSetting.resetTuple... "+
		// attr.hashCode());
		DeclTuple type = ((TupleObject) attr).getTupleType();

		removeFormat(type);

//		TupleFormat format = 
		getFormat(type);

		// Hashtable t = getIndexOfSameMember(attr); // -olga
		// System.out.println("after getFormat: "+type.getSize()+"
		// "+type.getNumberOfEntries());

		fireAttrChanged(((TupleObject) attr).getTupleType(),
				AttrEvent.GENERAL_CHANGE, 0, 0);
		this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
				AttrEvent.GENERAL_CHANGE, 0, 0);
	}

/*
	private Hashtable<DeclMember, Vector<Integer>> getIndexOfSameMember(AttrTuple attr) {
		DeclTuple type = ((TupleObject) attr).getTupleType();
		Hashtable<DeclMember, Vector<Integer>> t = new Hashtable<DeclMember, Vector<Integer>>();
		int length = type.getNumberOfEntries();
		for (int i = 0; i < length; i++) {
			DeclMember mi = (DeclMember) type.getMemberAt(i);
			Vector<Integer> v = new Vector<Integer>(5);
			for (int j = 0; j < length; j++) {
				DeclMember mj = (DeclMember) type.getMemberAt(j);
				if (i < j && mi != null && mj != null) {
					if (mi.getHoldingTuple() == mj.getHoldingTuple()) {
						if (mi.compareTo(mj)) {
							// System.out.println(attr);
							// mi.setEnabled(false);
							// mi.setVisible(false);

							System.out.println(i + " | " + j + ":  "
									+ mi.getHoldingTuple().hashCode()
									+ "  ==  "
									+ mj.getHoldingTuple().hashCode());
							v.add(new Integer(j));
						}
					}
				}
			}
			if (!v.isEmpty() && t.get(mi) == null)
				t.put(mi, v);
		}
		return t;
	}
*/
	
	public void reorderTuple(AttrTuple attr) {
		// System.out.println("OpenViewSetting.reorderTuple..."+
		// attr.hashCode());
		DeclTuple type = ((TupleObject) attr).getTupleType();
		// removeFormat( type );

		// multiple inheritance - olga
		removeFormat(type);
		getFormat(type);
		//	    
		fireAttrChanged(((TupleObject) attr).getTupleType(),
				AttrEvent.GENERAL_CHANGE, 0, 0);
		this.maskedView.fireAttrChanged(((TupleObject) attr).getTupleType(),
				AttrEvent.GENERAL_CHANGE, 0, 0);
	}

	/**
	 * AttrObserver implementation; Attribute event handling relies on the fact
	 * that an AttrType always sends its MEMBER_ADDED and MEMBER_DELETED events
	 * before his AttrInstance.
	 */
	public void attributeChanged(AttrEvent event) {
		// System.out.println(this+ " attributeChanged "+event+")");
		AttrTuple attr = event.getSource();
		int id = event.getID();
		int index0 = event.getIndex0();
		int index1 = event.getIndex1();

		switch (id) {
		case AttrEvent.MEMBER_ADDED:
			if (attr instanceof AttrType) { // Only for types
				// System.out.println(this+ " attributeChanged "+attr);
				TupleFormat form = getFormat(attr);
				for (int i = index0; i <= index1; i++) {
					form.addMember(i);
				}
			}
			propagateAttrEvent(event);
			this.maskedView.propagateAttrEvent(event);
			break;
		case AttrEvent.MEMBER_DELETED:
			if (attr instanceof AttrType) { // Only for types
				TupleFormat form = getFormat(attr);
				this.lastOpenDeletedSlot0 = convertIndexToSlot(attr, index0);
				this.lastOpenDeletedSlot1 = convertIndexToSlot(attr, index1);
				this.lastMaskedDeletedSlot0 = this.maskedView.convertIndexToSlot(attr,
						index0);
				this.lastMaskedDeletedSlot1 = this.maskedView.convertIndexToSlot(attr,
						index1);
				for (int i = index0; i <= index1; i++) {
					form.deleteMember(i);
				}
			}
			/*
			 * log( "converted slots: ("+lastOpenDeletedSlot0+", "+
			 * lastOpenDeletedSlot1+"); ("+lastMaskedDeletedSlot0+", "+
			 * lastMaskedDeletedSlot1+")");
			 */

			notifyObservers(attr, id, this.lastOpenDeletedSlot0,
					this.lastOpenDeletedSlot1);
			this.maskedView.notifyObservers(attr, id, this.lastMaskedDeletedSlot0,
					this.lastMaskedDeletedSlot1);
			break;
		default:
			propagateAttrEvent(event);
			this.maskedView.propagateAttrEvent(event);
		} // switch( id )
	}

	/** AttrObserver implementation */
	public boolean isPersistentFor(AttrTuple at) {
		return true;
	}

}
/*
 * $Log: OpenViewSetting.java,v $
 * Revision 1.12  2010/09/23 08:15:32  olga
 * tuning
 *
 * Revision 1.11  2010/03/08 15:38:56  olga
 * code optimizing
 *
 * Revision 1.10  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.9  2007/11/05 09:18:23  olga
 * code tuning
 *
 * Revision 1.8  2007/11/01 09:58:20  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2006/12/13 13:33:05 enrico
 * reimplemented code
 * 
 * Revision 1.5 2006/11/01 11:17:30 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.4 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.3 2006/04/12 14:54:07 olga Restore attr. values of attr. type
 * observers after type graph imported.
 * 
 * Revision 1.2 2005/11/03 14:16:03 enrico Implemented method reorderTuple
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.5 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.4 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/10/04 16:36:44 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.2 2002/09/23 12:24:04 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:07 olga Imported sources
 * 
 * Revision 1.12 2000/06/05 14:08:21 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.11 2000/04/05 12:11:27 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.10 1999/10/11 10:47:32 shultzke debugmeldungen geloescht
 * 
 * Revision 1.9 1999/10/11 10:42:55 shultzke kleine Bugfixes
 * 
 * Revision 1.7 1999/10/07 11:50:15 olga *** empty log message ***
 * 
 * Revision 1.6 1999/10/05 08:20:31 shultzke SlotSequences werden zwar
 * geloescht, aber gleich wieder erzeugt
 * 
 * Revision 1.5 1999/09/27 16:16:44 olga dispose Methoden hinzugefuegt.
 * 
 * Revision 1.4 1999/09/06 13:39:40 shultzke ChainedObserver auf WeakReferences
 * umgestellt, samt serialUID
 */
