// $Id: SimpleVariableOrder.java,v 1.7 2009/10/05 08:53:25 olga Exp $

// $Log: SimpleVariableOrder.java,v $
// Revision 1.7  2009/10/05 08:53:25  olga
// RSA check - bug fixed
//
// Revision 1.6  2007/09/10 13:05:15  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.5  2007/01/17 14:21:16  olga
// Tests
//
// Revision 1.4  2007/01/11 10:21:17  olga
// Optimized Version 1.5.1beta ,  free for tests
//
// Revision 1.3  2006/11/01 11:17:29  olga
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


public class SimpleVariableOrder implements BinaryPredicate {
	public SimpleVariableOrder() {
	}

	/**
	 * Return true iff the object domain of <code>var1</code> is smaller than the
	 * object domain of <code>var2</code>.
	 * <p>
	 * <b>Pre:</b> <code>var1,var2 instance of Variable</code>.
	 */
	public final boolean execute(Object var1, Object var2) {
		
		boolean result = (((Variable) var1).getDomainSize() 
	 						!= ((Variable) var2).getDomainSize())?
	 					((Variable) var1).getDomainSize() 
	 						< ((Variable) var2).getDomainSize()
//	 						: var1.hashCode() < var2.hashCode();
	 						: true;
	 						
//		boolean result = (((Variable) var1).getWeight() 
//				 			!= ((Variable) var2).getWeight())?
//				 					((Variable) var1).getWeight() 
//				 						< ((Variable) var2).getWeight()
//				 						: var1.hashCode() < var2.hashCode();
	 						
		return result;

	}
}
