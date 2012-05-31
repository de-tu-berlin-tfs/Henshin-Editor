// $Id: QueryOrder.java,v 1.3 2007/09/10 13:05:15 olga Exp $

// $Log: QueryOrder.java,v $
// Revision 1.3  2007/09/10 13:05:15  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2  2006/03/01 09:55:47  olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.1  2005/08/25 11:56:55  enrico
// *** empty log message ***
//
// Revision 1.1  2005/05/30 12:58:01  olga
// Version with Eclipse
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.3  1999/06/28 16:32:03  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1998/04/07 14:08:23  mich
// Updated for use with JGL V3.1.
//
// Revision 1.1  1997/12/26 20:52:42  mich
// Initial revision
//
// Revision 1.1  1997/09/22 19:56:53  mich
// Initial revision

package agg.util.csp;

public class QueryOrder implements BinaryPredicate {
	public QueryOrder() {
	}

	/**
	 * Return true iff the weight of <code>q1</code> is greater or equal the
	 * weight of <code>q2</code>.
	 * <p>
	 * <b>Pre:</b> <code>q1,q2 instanceof Query</code>.
	 * 
	 * @see agg.util.csp.Query
	 */
	public final boolean execute(Object q1, Object q2) {
		return (((Query) q1).getWeight() >= ((Query) q2).getWeight());
	}
}
