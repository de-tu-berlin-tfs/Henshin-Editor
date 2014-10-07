// $Id: Filter_Duplicates.java,v 1.3 2010/09/20 14:30:29 olga Exp $

// $Log: Filter_Duplicates.java,v $
// Revision 1.3  2010/09/20 14:30:29  olga
// tuning
//
// Revision 1.2  2007/09/10 13:05:53  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1  2005/08/25 11:57:00  enrico
// *** empty log message ***
//
// Revision 1.1  2005/05/30 12:58:04  olga
// Version with Eclipse
//
// Revision 1.2  2002/09/19 16:19:43  olga
// Nicht wichtig.
//
// Revision 1.1.1.1  2002/07/11 12:17:25  olga
// Imported sources
//
// Revision 1.4  1999/06/28 16:53:54  shultzke
// alte version wiedergeholt
//
// Revision 1.2  1998/09/03 14:28:34  mich
// Updated for use with JGL V3.1.
//
// Revision 1.1  1998/05/03 23:11:27  mich
// Initial revision
//

package agg.util.csp;

import java.util.HashSet;


/**
 * A sample filter for use with <code>FilterIterator</code>. It removes
 * duplicate elements from an iteration. <code>equals()</code> is used as the
 * method to determine if two objects are the same.
 */
public class Filter_Duplicates implements UnaryPredicate {
	/**
	 * Construct myself to be a filter that skips an object if the same object
	 * has been processed before.
	 */
	@SuppressWarnings("rawtypes")
	public Filter_Duplicates() {
		this.itsSet = new HashSet();
	}

	/**
	 * Return <code>true</code> for an object if <code>execute()</code> has
	 * been called for it before.
	 */
	@SuppressWarnings("unchecked")
	public final boolean execute(Object obj) {
		return (this.itsSet.add(obj)) ? true : false;
	}

	@SuppressWarnings("rawtypes")
	private HashSet itsSet;

}
