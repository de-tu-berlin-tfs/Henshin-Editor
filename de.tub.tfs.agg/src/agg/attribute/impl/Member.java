package agg.attribute.impl;

import agg.attribute.AttrMember;
import agg.attribute.AttrTuple;

/**
 * @version $Id: Member.java,v 1.3 2010/03/21 21:22:54 olga Exp $
 * @author $Author: olga $
 */
public abstract class Member extends AttrObject implements AttrMember {
	static final long serialVersionUID = -1564021289077614857L;

	public Member() {
		super();
	}

	public abstract AttrTuple getHoldingTuple();

	public int getIndexInTuple() {
		if (getHoldingTuple() == null)
			return -1;
		return ((TupleObject) getHoldingTuple()).getIndexForName(getName());
	}

	// 
	// Internal Methods:
	//

	protected void fireChanged(int code) {
		if (getHoldingTuple() == null)
			return;
		((TupleObject) getHoldingTuple()).memberChanged(code, this);
	}

}
/*
 * $Log: Member.java,v $
 * Revision 1.3  2010/03/21 21:22:54  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/10/30 18:06:43 olga Aenderung an der XML Ausgabe von
 * Values.
 * 
 * Revision 1.2 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:09:18 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
