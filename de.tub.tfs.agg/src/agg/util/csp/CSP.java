// $Id: CSP.java,v 1.14 2010/08/23 07:35:26 olga Exp $

// $Log: CSP.java,v $
// Revision 1.14  2010/08/23 07:35:26  olga
// tuning
//
// Revision 1.13  2010/06/23 13:44:00  olga
// tuning
//
// Revision 1.12  2010/02/22 14:42:36  olga
// code optimizing
//
// Revision 1.11  2009/05/12 10:36:53  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.10  2008/07/30 06:27:14  olga
// Applicability of RS , concurrent rule - handling of attributes improved
//
// Revision 1.9  2007/12/03 08:35:12  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.8  2007/11/05 09:18:19  olga
// code tuning
//
// Revision 1.7  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.6  2007/10/10 14:30:34  olga
// Enumeration typing
//
// Revision 1.5  2007/09/10 13:05:10  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.4  2007/05/07 07:59:35  olga
// CSP: extentions of CSP variables concept
//
// Revision 1.3  2007/01/11 10:21:17  olga
// Optimized Version 1.5.1beta ,  free for tests
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
// Revision 1.5  2004/12/20 14:53:48  olga
// Changes because of matching optimisation.
//
// Revision 1.4  2004/04/15 10:49:48  olga
// Kommentare
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
// Revision 1.5  1999/06/28 16:08:04  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.4  1997/12/26 20:31:03  mich
// + Some commentary corrections.
// ! setDomain() now uninstantiates all variables.
//
// Revision 1.3  1997/10/09 07:53:01  mich
// + CSP is now an abstract class with as much preimplementation
//   as possible.
// Tested.
//
// Revision 1.2  1997/10/09 06:16:22  mich
// + Some commentary added.
//
// Revision 1.1  1997/09/22 03:55:39  mich
// Initial revision
//
//

package agg.util.csp;

import java.util.Enumeration;

import agg.xt_basis.GraphObject;

/**
 * An abstract class for Constraint Satisfaction Problems with only binary
 * constraints.
 */
public abstract class CSP {
	protected Object itsDomain;
	
	protected Object itsRequester;
	
	protected SolutionStrategy itsSolver;

	protected boolean itsTouchedFlag;

	/**
	 * Construct myself with an initial SolutionStrategy.
	 * <p>
	 * <b>Post:</b> <code>getDomain() == null</code>.
	 */
	public CSP(SolutionStrategy solver) {
		this.itsDomain = null;
		setSolutionStrategy(solver);
	}

	public SolutionStrategy getSolutionSolver() {
		return this.itsSolver;
	}
	
	
	/**
	 * Return an Enumeration of all my variables. Enumeration elements are of
	 * type <code>Variable</code>.
	 */
	public abstract Enumeration<Variable> getVariables();

	public abstract Variable getVariable(agg.xt_basis.GraphObject obj);

	/**
	 * An additional object name constraint will be added for the CSP variable
	 * of the given GraphObject anObj. This constraint requires equality of the object names.  
	 */
	public abstract void addObjectNameConstraint(GraphObject anObj);
	
	/**
	 * Removes the object name constraint for the CSP variable
	 * of the given GraphObject anObj.
	 */
	public abstract void removeObjectNameConstraint(GraphObject anObj);
	
	/** Return the number of variables in the CSP. */
	public abstract int getSize();

	/**
	 * Set the global domain of values for the variables, and call
	 * <code>preprocessDomain()</code> with the given <code>domain</code>.
	 * <p>
	 * <b>Post:</b> <code>getDomain() == domain</code>.
	 * 
	 * @see agg.util.csp.CSP#preprocessDomain
	 */
	public final void setDomain(Object domain) {
		// any old variable instantiations are obsolete:
		final Enumeration<Variable> en = getVariables();
		while (en.hasMoreElements()) {
			en.nextElement().setInstance(null);
		}
		// if flag is true, this causes a reset() of solution strategy
		// when nextSolution() is called
		this.itsTouchedFlag = true; 
		this.itsDomain = domain;
		preprocessDomain(domain);
	}

	public final void setRequester(final Object requester) {
		this.itsRequester = requester;
	}
	
	public final Object getRequester() {
		return this.itsRequester;
	}
	
	/**
	 * Pre-process the given domain for optimization purposes (to get more
	 * accurate data for Constraint weights, or to initialize Query databases).
	 * This is a template method to be implemented in subclasses, and is invoked
	 * out of <code>setDomain()</code>.
	 * 
	 * @see agg.util.csp.CSP#setDomain
	 */
	protected abstract void preprocessDomain(Object domain);

	/** Return the current global domain of values. */
	public final Object getDomain() {
		return this.itsDomain;
	}

	/**
	 * Compute my next solution, and instantiate my variables appropriately.
	 * Variables already instantiated will not be altered, so this method can be
	 * used to complete partial solutions. Invoke this method repeatedly to get
	 * all solutions.
	 * <p>
	 * <b>Pre:</b> <code>getDomain() != null</code>.
	 * 
	 * @return <code>false</code> if there are no more solutions.
	 */
	public final boolean nextSolution() {
		if (this.itsTouchedFlag) {
			this.itsSolver.reset();
			this.itsTouchedFlag = false;
		}
		return this.itsSolver.next(this);
	}

	/** Set the search algorithm which is used to compute my solutions. */
	public final void setSolutionStrategy(SolutionStrategy solver) {
		this.itsSolver = solver;
		this.itsTouchedFlag = true;
	}

	public boolean hasSolution() {
		return this.itsSolver.hasSolution();
	}
	
	public boolean hasQueries() {
		return this.itsSolver.hasQueries();
	}
	
	/**
	 * Reset the state of the search algorithms. 
	 */
	public final void reset() {
		this.itsSolver.reset();
		this.itsTouchedFlag = false;
	}

	/**
	 * Reset the object domain of the query <code>Query_Type</code> of the search algorithms.
	 */
	public final void resetQuery_Type() {
		this.itsSolver.resetQuery_Type();
	}
	
	/**
	 * Reinitialize my search algorithm.
	 * The search queries will be generated newly if the given parameter is <code>true</code>. 
	 */
	public final void reinitialize(boolean doUpdateQueries) {
		this.itsSolver.reinitialize(doUpdateQueries);
		this.itsTouchedFlag = false;
	}

}
