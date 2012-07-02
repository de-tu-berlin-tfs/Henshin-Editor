// $Id: FilterIterator.java,v 1.4 2010/09/20 14:30:28 olga Exp $

// $Log: FilterIterator.java,v $
// Revision 1.4  2010/09/20 14:30:28  olga
// tuning
//
// Revision 1.3  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
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
// Revision 1.2  2003/03/05 13:57:33  komm
// no change
//
// Revision 1.1.1.1  2002/07/11 12:17:25  olga
// Imported sources
//
// Revision 1.5  1999/06/28 16:54:49  shultzke
// alte version wiederhergestellt
//
// Revision 1.3  1998/04/07 14:01:32  mich
// Updated for use with JGL V3.1.
//

package agg.util.csp;

import java.util.Enumeration;


public class FilterIterator {
	/**
	 * Construct myself to be an iterator that combines filtering and conversion
	 * on the elements of an underlying iterator.
	 * 
	 * @param base
	 *            The underlying iterator.
	 * @param select
	 *            A predicate that returns false for any object that the
	 *            FilterIterator should skip.
	 * @param convert
	 *            A function object that specifies the conversion to be done on
	 *            each element of the iteration. This determines the type of the
	 *            objects returned by <code>nextElement()</code>.
	 */
	public FilterIterator(Enumeration<?> base, UnaryPredicate select,
			UnaryFunction convert) {
		this.itsBase = base;
		this.itsFilter = select;
		this.itsConverter = convert;
		next();
	}

	/**
	 * Construct myself to be an iterator that performs a conversion on any
	 * element of the base iterator before it is passed along to the caller.
	 * Don't do any filtering.
	 * 
	 * @param base
	 *            The underlying iterator.
	 * @param convert
	 *            A function object that specifies the conversion to be done on
	 *            each element of the iteration. This determines the type of the
	 *            objects returned by <code>nextElement()</code>.
	 */
	public FilterIterator(Enumeration<?> base, UnaryFunction convert) {
		this.itsBase = base;
		this.itsFilter = new Filter_Default();
		this.itsConverter = convert;
		next();
	}

	/**
	 * Construct myself to be an iterator that skips some elements of the base
	 * iterator according to a given predicate. Don't do any conversion on the
	 * iteration elements.
	 * 
	 * @param base
	 *            The underlying iterator.
	 * @param select
	 *            A predicate that returns false for any object that the
	 *            FilterIterator should skip.
	 */
	public FilterIterator(Enumeration<?> base, UnaryPredicate select) {
		this.itsBase = base;
		this.itsFilter = select;
		this.itsConverter = new Convert_Default();
		next();
	}

	public final boolean hasMoreElements() {
		return (this.itsNextElement == null) ? false : true;
	}

	public final Object nextElement() {
		this.itsReturnElement = this.itsNextElement;
		next();
		return this.itsConverter.execute(this.itsReturnElement);
	}

	private final void next() {
		while (this.itsBase.hasMoreElements()) {
			this.itsNextElement = this.itsBase.nextElement();
			if (this.itsFilter.execute(this.itsNextElement))
				return;
		}

		this.itsNextElement = null;
	}

	// ---------- member variables -------------------------------
	private Enumeration<?> itsBase;

	private UnaryPredicate itsFilter;

	private UnaryFunction itsConverter;

	private Object itsNextElement;

	private Object itsReturnElement;
}

/**
 * Filter_Default is the default filter for FilterIterator. This filter is
 * transparent, that means that no element of the underlying iterator will be
 * skipped by the FilterIterator.
 */
class Filter_Default implements UnaryPredicate {
	/**
	 * Return always true.
	 */
	public final boolean execute(Object obj) {
		return true;
	}
}

/**
 * Convert_Default is the default converter for FilterIterator. No conversion is
 * performed at all, objects are just passed through.
 */
class Convert_Default implements UnaryFunction {
	/**
	 * Return the given object as is.
	 */
	public final Object execute(Object obj) {
		return obj;
	}
}
