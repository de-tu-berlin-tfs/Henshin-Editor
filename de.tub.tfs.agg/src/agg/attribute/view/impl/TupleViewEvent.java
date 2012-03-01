package agg.attribute.view.impl;

import agg.attribute.AttrTuple;
import agg.attribute.impl.TupleEvent;
import agg.attribute.view.AttrViewEvent;
import agg.attribute.view.AttrViewSetting;

/**
 * @version $Id: TupleViewEvent.java,v 1.4 2010/09/23 08:15:32 olga Exp $
 * @author $Author: olga $
 */
public class TupleViewEvent extends TupleEvent implements AttrViewEvent {

	static final long serialVersionUID = 2422750570570672804L;

	protected ViewSetting view;

	public TupleViewEvent(AttrTuple attr, int id, int index0, int index1,
			AttrViewSetting view) {
		super(attr, id, index0, index1);
		this.view = (ViewSetting) view;
	}

	public TupleEvent cloneWithNewSource(AttrTuple tup) {
		return new TupleViewEvent(tup, this.id, this.index0, this.index1, this.view);
	}

	public AttrViewSetting getView() {
		return this.view;
	}

	public String toString() {
		return (super.toString() + ", view=" + (this.view == null ? "null" : this.view
				.toString()));
	}

	public String toLongString() {
		return (super.toString() + "\n  View: " + (this.view == null ? "null" : this.view
				.toString()));
	}

	//
	// Internal

	protected String idToString(int anID) {
		String r;
		switch (anID) {
		case MEMBER_MOVED:
			r = "MEMBER_MOVED";
			break;
		case MEMBER_VISIBILITY:
			r = "MEMBER_VISIBILITY";
			break;
		default:
			r = super.idToString(anID);
		}
		return r;
	}
}
/*
 * $Log: TupleViewEvent.java,v $
 * Revision 1.4  2010/09/23 08:15:32  olga
 * tuning
 *
 * Revision 1.3  2007/11/05 09:18:23  olga
 * code tuning
 *
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:05 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:07 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:11:32 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
