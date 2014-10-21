package agg.attribute.impl;

import agg.attribute.AttrEvent;
import agg.attribute.AttrMember;
import agg.attribute.AttrTuple;

public class TupleEvent extends AttrObject implements AttrEvent {

	protected int id;

	protected int index0, index1;

	protected AttrTuple src;
	
	protected AttrMember member;
	
	public TupleEvent(AttrTuple attr, int id, int index0, int index1) {
		super();
		this.src = attr;
		this.id = id;
		this.index0 = index0;
		this.index1 = index1;	
		
		if (this.index0 == this.index1) {
			this.member = attr.getMemberAt(index0);
		}
	}

	public TupleEvent(AttrTuple attr, int id, int index) {
		super();
		this.src = attr;
		this.id = id;
		this.index0 = index;
		this.index1 = index;
		
		this.member = attr.getMemberAt(index);
	}

	public TupleEvent cloneWithNewSource(AttrTuple tup) {
		return new TupleEvent(tup, this.id, this.index0, this.index1);
	}

	public AttrTuple getSource() {
		return this.src;
	}

	public int getID() {
		return this.id;
	}

	public int getIndex() {
		return getIndex0();
	}

	public int getIndex0() {
		return this.index0;
	}

	public int getIndex1() {
		return this.index1;
	}

	public AttrMember getAttrMember() {		
		return this.member;
	}
	
	public String toString() {
		return ("<-" + super.toString() + "- [" + this.src + ", id="
				+ idToString(this.id) + ", range=(" + this.index0 + "," + this.index1 + ")]");
	}

	public String toLongString() {
		return (super.toString() + "\n  Source: " + this.src + "\n  ID: "
				+ idToString(this.id) + "\n  Index0=" + this.index0 + "\n  Index1=" + this.index1);
	}

	//
	// Internal

	protected String idToString(int anID) {
		String r;
		switch (anID) {
		case GENERAL_CHANGE:
			r = "GENERAL_CHANGE";
			break;
		case MEMBER_ADDED:
			r = "MEMBER_ADDED";
			break;
		case MEMBER_DELETED:
			r = "MEMBER_DELETED";
			break;
		case MEMBER_MODIFIED:
			r = "MEMBER_MODIFIED";
			break;
		case MEMBER_RENAMED:
			r = "MEMBER_RENAMED";
			break;
		case MEMBER_RETYPED:
			r = "MEMBER_RETYPED";
			break;
		case MEMBER_VALUE_MODIFIED:
			r = "MEMBER_VALUE_MODIFIED";
			break;
		case MEMBER_VALUE_CORRECTNESS:
			r = "MEMBER_VALUE_CORRECTNESS";
			break;
		default:
			r = "Invalid";
			break;
		}
		return r;
	}
}
/*
 * $Log: TupleEvent.java,v $
 * Revision 1.5  2010/09/23 08:14:08  olga
 * tuning
 *
 * Revision 1.4  2008/09/22 13:12:14  olga
 * new AttrEvent: MEMBER_TO_DELETE
 *
 * Revision 1.3  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.2  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty
 * log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.3 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:09:21 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
