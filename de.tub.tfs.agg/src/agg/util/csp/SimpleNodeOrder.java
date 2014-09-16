// $Id: SimpleNodeOrder.java,v 1.3 2010/03/08 15:50:34 olga Exp $

// $Log: SimpleNodeOrder.java,v $
// Revision 1.3  2010/03/08 15:50:34  olga
// code optimizing
//
// Revision 1.2  2007/09/10 13:05:06  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1  2006/11/01 11:17:29  olga
// Optimized agg sources of  CSP algorithm,  match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.2  2006/04/20 11:58:39  olga
// Attr type check: Bug fixed
//
// Revision 1.1  2005/08/25 11:56:55  enrico
// *** empty log message ***
//
// Revision 1.2  2005/07/11 09:30:19  olga
// This is test version AGG V1.2.8alfa .
// What is new:
// - saving rule option <disabled>
// - setting trigger rule for layer
// - display attr. conditions in gragra tree view
// - CPA algorithm <dependencies>
// - creating and display CPA graph with conflicts and/or dependencies
// 	based on (.cpx) file
//
// Revision 1.1  2005/05/30 12:58:01  olga
// Version with Eclipse
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.3  1999/06/28 16:35:29  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1998/04/07 14:15:49  mich
// Updated for use with JGL V3.1.
//
// Revision 1.1  1997/12/26 20:59:22  mich
// Initial revision
//
// Revision 1.1  1997/09/16 15:56:55  mich
// Initial revision
//

package agg.util.csp;

import agg.xt_basis.Node;


public class SimpleNodeOrder implements BinaryPredicate {

	Object varObj;

	public SimpleNodeOrder(Object varObj) {
		this.varObj = varObj;
	}

	/**
	 * Return true iff the sum of in and out arcs of <code>o1</code> is greater 
	 * the sum of in and out arcs of <code>o2</code>.
	 * <p>
	 * <b>Pre:</b> <code>o1,o2 instance of Node</code>.
	 */
	public final boolean execute(Object o1, Object o2) {
		Node n1 = (Node) o1;
		Node n2 = (Node) o2;
		int arcs1 = n1.getNumberOfInOutArcs();
		int arcs2 = n2.getNumberOfInOutArcs();
		if (arcs1 > arcs2) {
			return true;
		} 
		return false;
	}
}
