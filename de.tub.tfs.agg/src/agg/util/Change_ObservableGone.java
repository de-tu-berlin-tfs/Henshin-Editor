// $Id: Change_ObservableGone.java,v 1.2 2007/09/10 13:05:52 olga Exp $

// $Log: Change_ObservableGone.java,v $
// Revision 1.2  2007/09/10 13:05:52  olga
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
// Revision 1.1.1.1  2002/07/11 12:17:25  olga
// Imported sources
//
// Revision 1.5  1999/06/28 16:53:52  shultzke
// alte version wiedergeholt
//
// Revision 1.3  1997/12/26 20:02:30  mich
// + Minor commentary corrections.
//

package agg.util;

import java.util.Observable;

/**
 * This change information may be sent out by an observable object when it wants
 * its Observers to dispose their reference to it. This is necessary to break
 * the circular reference inherent to the observer pattern which makes it
 * inaccessible for the garbage collector.
 * 
 * <p>
 * Its item is of type <code>java.util.Observable</code> and denotes the
 * observable that should no longer be referenced.
 */
public class Change_ObservableGone extends Change {
	public Change_ObservableGone(Observable item) {
		super(item);
	}
}
