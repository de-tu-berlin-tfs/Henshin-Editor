// $Id: InstantiationHook.java,v 1.2 2007/09/10 13:05:05 olga Exp $

// $Log: InstantiationHook.java,v $
// Revision 1.2  2007/09/10 13:05:05  olga
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
// Revision 1.2  1999/06/28 16:24:17  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.1  1997/12/26 20:32:04  mich
// Initial revision
//

package agg.util.csp;

/**
 * An interface for the realization of side effects which are to take place at
 * instantiation/uninstantiation time of a CSP variable.
 * 
 * @see agg.util.csp.Variable
 */
public interface InstantiationHook {
	/**
	 * This method is called whenever the variable I'm hooked to gets
	 * instantiated. It is called <i>after</i> the new value has been set, with
	 * the variable as an argument.
	 */
	public void instantiate(Variable var);

	/**
	 * This method is called whenever the variable I'm hooked to gets
	 * uninstantiated, or when it is set to a new value. It is called <i>before</i>
	 * the value is re- or unset, with the variable as an argument.
	 */
	public void uninstantiate(Variable var);
}
