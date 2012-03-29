// $Id: SolutionStrategy.java,v 1.11 2010/02/22 14:43:23 olga Exp $

// $Log: SolutionStrategy.java,v $
// Revision 1.11  2010/02/22 14:43:23  olga
// code optimizing
//
// Revision 1.10  2009/05/12 10:36:53  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.9  2008/07/30 06:27:14  olga
// Applicability of RS , concurrent rule - handling of attributes improved
//
// Revision 1.8  2008/04/07 09:36:55  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.7  2007/12/03 08:35:12  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.6  2007/09/10 13:05:06  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.5  2007/05/07 07:59:35  olga
// CSP: extentions of CSP variables concept
//
// Revision 1.4  2007/01/11 10:21:17  olga
// Optimized Version 1.5.1beta ,  free for tests
//
// Revision 1.3  2006/12/13 13:33:04  enrico
// reimplemented code
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
// Revision 1.3  2004/01/15 16:43:06  olga
// Korrektur an transformation
//
// Revision 1.2  2003/03/05 18:24:25  komm
// sorted/optimized import statements
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.3  1999/06/28 16:36:00  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1997/12/26 20:37:17  mich
// + Commentary corrections.
//
// Revision 1.1  1997/09/21 15:43:13  mich
// Initial revision
//
//

package agg.util.csp;

import java.util.Dictionary;

/**
 * An interface for solution strategies for Constraint Satisfaction Problems.
 * 
 * @see agg.util.csp.CSP
 */
public interface SolutionStrategy {
	/**
	 * Find the next solution of <code>csp</code>, and instantiate its
	 * variables accordingly. Variables already instantiated will not be
	 * altered, so this method can be used to complete partial solutions. Invoke
	 * this method successively with the same argument to get all solutions (or
	 * all completions of a given partial solution).
	 * 
	 * @param csp
	 *            The CSP to solve.
	 * @return <code>false</code> if there are no more solutions.
	 */
	public boolean next(CSP csp);

	/**
	 * Reset my internal state, so that the forthcoming invocation of
	 * <code>next()</code> returns the first solution of the given CSP.
	 */
	public void reset();

	/**
	 * Reinitialize my search strategy.
	 * The search queries will be generated newly if the given parameter is <code>true</code>. 
	 */
	public boolean reinitialize(boolean doUpdateQueries);

	/**
	 * Reinitialize my search strategy. The instance object of the specified variable
	 * will be set to null.
	 */
	public void reinitialize(Variable var);

	/**
	 * Clear internal data. 
	 */
	public void clear();
	
	
	public boolean hasQueries();
	
	/**
	 * Reset the object domain of the query <code>Query_Type</code>.
	 */
	public void resetQuery_Type();
	
	public boolean hasSolution();
	
	public void setRelatedInstanceVarMap(
			Dictionary<Object, Variable> relatedVarMap);

	public Dictionary<Object, Variable> getInstanceVarMap();

	public boolean parallelSearch();
	
	public void enableParallelSearch(boolean b);
	
	public void setStartParallelSearchByFirst(boolean b);
	
	public Variable getStartVariable();
	
	public Query getQuery(final Variable var);
}
