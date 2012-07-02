package agg.attribute.impl;

import java.util.Vector;

import agg.attribute.AttrMember;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.view.AttrViewSetting;
import agg.util.Disposable;

/**
 * Partial implementation of the interface agg.attribute.AttrTuple.
 * 
 * @see agg.attribute.AttrTuple
 * @author $Author: olga $
 * @version $Id: TupleObject.java,v 1.13 2010/09/23 08:14:08 olga Exp $
 */
public abstract class TupleObject extends ChainedObserver implements AttrTuple,
		Disposable {

	/** Parent of this type. All parent entries are "inherited". */
	protected TupleObject parent;

	/* Parent list of this type. All parent entries are "inherited". */
	// protected Vector<TupleObject> parents = new Vector<TupleObject>(5);
	
	
	
	/**
	 * Container with members, all of which implement the AttrMember interface.
	 * 
	 * @see agg.attribute.AttrMember
	 */
	protected final Vector<AttrMember> members = new Vector<AttrMember>();

	public TupleObject(AttrTupleManager manager, TupleObject parent) {
		super(manager);
		assignParent(parent);
	}

	protected void finalize() {
	}

	public void dispose() {
		if (this.parent != null)
			this.parent.removeObserver(this);
				
		 this.members.clear();
	}

	/**
	 * @param newParent
	 */
	protected void assignParent(TupleObject newParent) {
		if (this.parent != null)
			this.parent.removeObserver(this);

		this.parent = newParent;
		if (this.parent != null)
			this.parent.addObserver(this);
	}

	/** Propagates the event to the observers, pretending to be the source. */
	protected void propagateEvent(TupleEvent e) {
		fireAttrChanged(e.cloneWithNewSource(this));
	}

	public void memberChanged(int code, AttrMember member) {
		fireAttrChanged(code, getIndexForMember(member));
	}

	//
	// Container-specific access to the container of [members].
	// Singling these methods out allows for easy replacement of the
	// container, e.g. by a more efficient one.

	protected synchronized int rawGetSize() {
		return this.members.size();
	}

	protected synchronized AttrMember rawGetMemberAt(int index) {
		int indx = index;
		if ((this.members.size() == 0) || (indx < 0))
			return null;

		if (indx >= this.members.size()) {
			// warn("index="+index+" >= size=" + this.members.size()+"\nSetting to
			// 0.", true );
			indx = 0;
		}

		return this.members.elementAt(indx);
	}

	protected synchronized void rawAddMember(AttrMember member) {
		if (member != null)
			this.members.add(member);
	}

	protected synchronized void rawAddMember(int index, AttrMember member) {
		if (member != null)
			this.members.add(index, member);
	}

	protected synchronized void rawDeleteMemberAt(int index) {
		if (index == -1 || index >= this.members.size()) {
			// warn("index="+index+" >= size=" + this.members.size()+"\nReturning.",
			// true );
			return;
		}
		this.members.removeElementAt(index);
	}

	// End of container-specific access.
	//

	/**
	 * This method interface is needed in order to treat attribute types and
	 * instances uniformly.
	 */
	public abstract DeclTuple getTupleType();

	public AttrType getType() {
		return getTupleType();
	}

	//
	// Fixed form.

	protected AttrViewSetting getForm() {
		return getTupleType().getForm();
	}

	protected void setForm(AttrViewSetting formSetting) {
		getTupleType().setForm(formSetting);
	}

	protected AttrViewSetting ensureNonNull(AttrViewSetting viewSetting) {
		if (viewSetting == null)
			return this.manager.getDefaultMaskedView();
		return viewSetting;
	}

	/** Transforming a mask entry index into the real index. */
	protected int getIndexInView(AttrViewSetting viewSetting, int slot) {
		return ensureNonNull(viewSetting).convertSlotToIndex(this, slot);
	}

	/**
	 * @return parent TupleObject
	 */
	protected TupleObject getParent() {
		return this.parent;
	}

	/**
	 * Obtaining the size of the current parent.
	 */
	public int getParentSize() {
		if (this.parent == null)
			return 0;
	
		return this.parent.getSize();
	}

	/** @return The ancestor that originated the member at 'index'. */
	protected TupleObject getParentInCharge(int index) {
		if ((index >= getParentSize()))
			return this;
		return this.parent.getParentInCharge(index);
	}

	protected void addMember(AttrMember member) {
		rawAddMember(member);
	}

	protected void addMember(int index, AttrMember member) {
		rawAddMember(index, member);
	}

	protected void deleteMemberAt(int index) {
		rawDeleteMemberAt(index);
	}

	//
	// Implementation of the inheritance mechanism.
	// 

	/**
	 * Inheritance mechanism: Checking inheritance relation.
	 */
	public boolean isSubclassOf(TupleObject maybeParent) {
		if (this.parent == null)
			return false;
		if (this.parent == maybeParent)
			return true;
		return this.parent.isSubclassOf(maybeParent);
	}

	//
	// Querying the state. Implementation of agg.attribute.AttrTuple
	//

	public int getSize() {
		return rawGetSize();
	}

	public boolean isValid() {
		for (int i = 0; i < getSize(); i++) {
			if (getMemberAt(i) == null || !getMemberAt(i).isValid())
				return false;
		}
		return true;
	}
	
	public AttrMember getMemberAt(int index) {
		return rawGetMemberAt(index);
	}

	public AttrMember getMemberAt(AttrViewSetting viewSetting, int slot) {
		return getMemberAt(ensureNonNull(viewSetting).convertSlotToIndex(this,
				slot));
	}

	public AttrMember getMemberAt(String name) {
		return getMemberAt(getIndexForName(name));
	}

	/**
	 * Translation between number- and name-oriented access.
	 * 
	 * @return The corresponding index if the name is declared within the tuple,
	 *         -1 otherwise.
	 */
	public int getIndexForName(String name) {
		return getTupleType().getIndexForName(name);
	}

	/**
	 * Translation between address- and number-oriented access.
	 * 
	 * @return The corresponding index if the member is within the tuple, -1
	 *         otherwise.
	 */
	public int getIndexForMember(AttrMember m) {
		if (m == null)
			return -1;
		int size = getSize();

		for (int i = 0; i < size; i++) {
			if (m == getMemberAt(i))
				return i;
		}
		return -1;
	}

	//
	// Simple representation.
	//

	/***************************************************************************
	 * Getting the total number of shown attribute entries (lines); The
	 * retrieval index range is [0 .. (getNumberOfEntries() - 1)].
	 */
	public int getNumberOfEntries() {
		return getSize();
	}

	/** Getting a simple representation of a type as String. */
	public String getTypeAsString(int index) {
		return getType().getTypeAsString(index);
	}

	/** Getting a simple representation of a name as String. */
	public String getNameAsString(int index) {
		return getType().getNameAsString(index);
	}

	public String getValueAsString(int index) {
		return "";
	}

	//

	public int getNumberOfEntries(AttrViewSetting viewSetting) {
		return ensureNonNull(viewSetting).getSize(this);
	}

	public String getTypeAsString(AttrViewSetting viewSetting, int entryIndex) {
		return getTypeAsString(getIndexInView(viewSetting, entryIndex));
	}

	public String getNameAsString(AttrViewSetting viewSetting, int entryIndex) {
		return getNameAsString(getIndexInView(viewSetting, entryIndex));
	}

	public String getValueAsString(AttrViewSetting viewSetting, int entryIndex) {
		return getValueAsString(getIndexInView(viewSetting, entryIndex));
	}
}
/*
 * $Log: TupleObject.java,v $
 * Revision 1.13  2010/09/23 08:14:08  olga
 * tuning
 *
 * Revision 1.12  2010/03/17 21:37:37  olga
 * tuning
 *
 * Revision 1.11  2010/03/08 15:37:42  olga
 * code optimizing
 *
 * Revision 1.10  2009/04/20 08:50:45  olga
 * CPA: bug fixed
 *
 * Revision 1.9  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.8  2007/11/14 08:53:43  olga
 * code tuning
 *
 * Revision 1.7  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.6  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2007/03/28 10:00:30 olga - extensive
 * changes of Node/Edge Type Editor, - first Undo implementation for graphs and
 * Node/edge Type editing and transformation, - new / reimplemented options for
 * layered transformation, for graph layouter - enable / disable for NACs, attr
 * conditions, formula - GUI tuning
 * 
 * Revision 1.4 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.3 2006/04/06 09:28:53 olga Tuning of Import Type Graph and Import
 * Graph
 * 
 * Revision 1.2 2006/04/03 08:57:50 olga New: Import Type Graph and some bugs
 * fixed
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2.2.1 2005/08/16 09:56:28 enrico made getParentSize() public
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.5 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/02/03 17:46:30 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.3 2002/11/25 14:56:27 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/10/04 16:36:39 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.11 2000/05/17 11:56:59 olga Testversion an Gabi mit diversen
 * Aenderungen. Fehler sind moeglich!!
 * 
 * Revision 1.10 2000/04/05 12:09:25 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
