// $Id: ExtObservable.java,v 1.4 2008/04/07 09:36:56 olga Exp $

// $Log: ExtObservable.java,v $
// Revision 1.4  2008/04/07 09:36:56  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.3  2007/09/10 13:05:53  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2  2007/04/12 14:48:08  olga
// undo/redo
//
// Revision 1.1  2005/08/25 11:57:00  enrico
// *** empty log message ***
//
// Revision 1.2  2005/06/20 13:37:04  olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1  2005/05/30 12:58:04  olga
// Version with Eclipse
//
// Revision 1.2  2002/11/11 10:41:00  komm
// Debug state changed
//
// Revision 1.1.1.1  2002/07/11 12:17:25  olga
// Imported sources
//
// Revision 1.3  1999/06/28 16:13:54  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1997/12/26 20:08:53  mich
// + Commentary added.
// + Debug output now uses Debug.println().
//

package agg.util;

import java.util.Observable;

/**
 * An extension of the native Java <code>Observable</code> class that
 * addresses the need for explicit disposure arising from the circular
 * references inherent to the observer pattern (observer knows observable and
 * vice versa). In a multi-layer observer architecture, the disposure command
 * has to be passed way up the observer hierarchy to break the circular
 * references in every layer and thus make the participants amenable for garbage
 * collection.
 */
public class ExtObservable extends Observable implements Disposable {
	/**
	 * Prepare myself for garbage collection. A change message
	 * <code>Change_ObservableGone</code> with myself as the item is sent out
	 * to all of my observers. Subclasses may override this to break their
	 * individual circular references, but they should always include a call to
	 * this original implementation.
	 * 
	 * @see agg.util.Change_ObservableGone
	 */
	public void dispose() {
		super.setChanged();
		super.notifyObservers(new Change(Change.OBSERVABLE_GONE, this));
		super.deleteObservers();
	}

	protected void finalize() throws Throwable {
//		 System.out.println("ExtObservable.finalize:: "+this);
	}

	public synchronized void setChanged() {
		super.setChanged();
	}

}
