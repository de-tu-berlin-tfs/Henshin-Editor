// $Id: Disposable.java,v 1.2 2007/09/10 13:05:53 olga Exp $
package agg.util;

/**
 * <code>Disposable</code> is an interface that is implemented by classes that
 * need explicit finalization in order to become accessible to garbage
 * collection. The contract is as follows: The caller of the constructor of a
 * disposable object is responsible for calling its <code>dispose()</code>
 * method when the object is no longer needed.
 */
public interface Disposable {
	/** Prepare myself for garbage collection. */
	public void dispose();
}
