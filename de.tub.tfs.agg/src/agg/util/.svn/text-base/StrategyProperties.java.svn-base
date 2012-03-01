// $Id: StrategyProperties.java,v 1.2 2007/09/10 13:05:53 olga Exp $

// $Log: StrategyProperties.java,v $
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
// Revision 1.1.1.1  2002/07/11 12:17:25  olga
// Imported sources
//
// Revision 1.2  1999/06/28 16:37:22  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.1  1997/12/29 16:30:57  mich
// Initial revision
//

package agg.util;

import java.util.BitSet;

/**
 * This interface may be implemented by abstract strategies to provide support
 * for special properties that a concrete strategy may have. A property is
 * represented as a bit in a <code>BitSet</code>. Symbolic names for the
 * property bits of a category of strategies may be defined in separate
 * interfaces.
 */
public interface StrategyProperties {
	/**
	 * Return information about what properties I support. A property is
	 * supported if its corresponding bit is set.
	 */
	public abstract BitSet getSupportedProperties();

	/**
	 * Return information about what properties are currently activated.
	 * Properties can be activated or deactivated by setting or clearing their
	 * respective bits via the <code>BitSet</code> interface.
	 */
	public abstract BitSet getProperties();
}
