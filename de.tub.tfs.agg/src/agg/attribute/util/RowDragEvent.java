package agg.attribute.util;

import java.util.EventObject;

/**
 * Listener for dragging of table rows.
 * 
 * @version $Id: RowDragEvent.java,v 1.3 2010/09/23 08:15:16 olga Exp $
 * @author $Author: olga $
 */
public class RowDragEvent extends EventObject {

	static final long serialVersionUID = 5394805885112306429L;

	public static final int STARTED = 0;

	public static final int STOPPED = 0;

	public static final int MOVED = 0;

	int src, tar, eventID;

	public RowDragEvent(TableRowDragger dr, int id, int s, int t) {
		super(dr);
		this.src = s;
		this.tar = t;
		this.eventID = id;
	}

	public int getID() {
		return this.eventID;
	}

	public int getSourceRow() {
		return this.src;
	}

	public int getTargetRow() {
		return this.tar;
	}
}

/*
 * $Log: RowDragEvent.java,v $
 * Revision 1.3  2010/09/23 08:15:16  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:53  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:29 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:06 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:11:18 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
