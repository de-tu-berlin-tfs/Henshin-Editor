// $Id: SearchStrategy.java,v 1.4 2010/02/22 14:43:06 olga Exp $

// $Log: SearchStrategy.java,v $
// Revision 1.4  2010/02/22 14:43:06  olga
// code optimizing
//
// Revision 1.3  2007/09/10 13:05:05  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2  2006/11/01 11:17:29  olga
// Optimized agg sources of  CSP algorithm,  match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.1  2005/08/25 11:56:55  enrico
// *** empty log message ***
//
// Revision 1.1  2005/05/30 12:58:01  olga
// Version with Eclipse
//
// Revision 1.3  2004/04/15 10:49:48  olga
// Kommentare
//
// Revision 1.2  2003/03/05 18:24:25  komm
// sorted/optimized import statements
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.3  1999/06/28 16:34:47  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1997/12/26 20:35:50  mich
// + Commentary added.
//
// Revision 1.1  1997/09/22 05:05:36  mich
// Initial revision
//

package agg.util.csp;

import java.util.Vector;

/**
 * An interface for algorithms calculating search plans (variable orderings
 * given by a list of queries).
 */
public interface SearchStrategy {
	/**
	 * Return a list of queries representing a search plan. A variable ordering
	 * is given by the target variables of the queries, and the domain for such
	 * a target variable is given by its query. Vector elements are of type
	 * <code>Query</code>.
	 * 
	 * @see agg.util.csp.Query
	 */
	public Vector<Query> execute(CSP csp);

}
