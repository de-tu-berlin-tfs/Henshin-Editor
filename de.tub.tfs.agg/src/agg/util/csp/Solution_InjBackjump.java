// $Id: Solution_InjBackjump.java,v 1.3 2010/02/22 14:43:23 olga Exp $

// $Log: Solution_InjBackjump.java,v $
// Revision 1.3  2010/02/22 14:43:23  olga
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
// Revision 1.1  2005/08/25 11:56:55  enrico
// *** empty log message ***
//
// Revision 1.1  2005/05/30 12:58:01  olga
// Version with Eclipse
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.2  1999/06/28 16:36:16  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.1  1997/12/26 21:18:38  mich
// Initial revision
//

package agg.util.csp;

/**
 * A CSP solution strategy using the backjumping technique. Only injective
 * solutions are considered.
 * @deprecated	replaced by <code>Solution_Backjump(true)</code>
 */
public class Solution_InjBackjump extends Solution_Backjump {
	public Solution_InjBackjump() {
		super(true);
	}
}
