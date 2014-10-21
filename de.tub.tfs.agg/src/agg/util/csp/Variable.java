// $Id: Variable.java,v 1.25 2010/09/23 08:26:52 olga Exp $

// $Log: Variable.java,v $
// Revision 1.25  2010/09/23 08:26:52  olga
// tuning
//
// Revision 1.24  2010/06/09 10:24:32  olga
// tuning
//
// Revision 1.23  2010/03/18 18:18:30  olga
// tuning
//
// Revision 1.22  2010/03/08 15:50:35  olga
// code optimizing
//
// Revision 1.21  2010/03/04 14:13:51  olga
// code optimizing
//
// Revision 1.20  2010/02/22 14:43:23  olga
// code optimizing
//
// Revision 1.19  2010/02/07 16:43:57  olga
// tuning
//
// Revision 1.18  2009/10/22 11:16:37  olga
// tuning and tests
//
// Revision 1.17  2009/10/05 08:53:25  olga
// RSA check - bug fixed
//
// Revision 1.16  2009/05/28 13:18:29  olga
// Amalgamated graph transformation - development stage
//
// Revision 1.15  2009/05/12 10:36:53  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.14  2008/04/07 09:36:55  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.13  2007/12/10 08:42:58  olga
// CPA of grammar with node type inheritance for attributed graphs - bug fixed
//
// Revision 1.12  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.11  2007/10/11 15:03:38  olga
// code tuning
//
// Revision 1.10  2007/09/10 13:05:07  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.9  2007/01/11 10:21:17  olga
// Optimized Version 1.5.1beta ,  free for tests
//
// Revision 1.8  2006/12/13 13:33:04  enrico
// reimplemented code
//
// Revision 1.7  2006/11/15 09:00:32  olga
// Transform with input parameter : bug fixed
//
// Revision 1.6  2006/11/01 11:17:29  olga
// Optimized agg sources of  CSP algorithm,  match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.5  2006/05/22 08:27:33  olga
// CPA: Bug fixed
// Gragra trash: tuning
//
// Revision 1.4  2006/05/08 15:47:10  olga
// some tests with variable order and queries
//
// Revision 1.3  2006/05/08 08:24:12  olga
// Some extentions of GUI: - Undo Delete button of tool bar to undo deletions
// if grammar elements like rule, NAC, graph constraints;
// - the possibility to add a new graph to a grammar or a copy of the current
// host graph;
// - to set one or more layer for consistency constraints.
// Also some bugs fixed of matching and some optimizations of CSP algorithmus done.
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
// Revision 1.6  2004/12/20 14:53:48  olga
// Changes because of matching optimisation.
//
// Revision 1.5  2004/05/06 17:23:27  olga
// graph matching OK
//
// Revision 1.4  2004/04/28 12:46:38  olga
// test CSP
//
// Revision 1.3  2003/12/18 16:27:25  olga
// .
//
// Revision 1.2  2003/03/05 18:24:25  komm
// sorted/optimized import statements
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.7  2000/07/31 09:46:19  shultzke
// Nichtdeterministische Regelauswahl und Ansatzsuche ist moeglich
//
// Revision 1.6  1999/06/28 16:42:05  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.5  1998/04/07 14:22:32  mich
// Updated for use with JGL V3.1.
//
// Revision 1.4  1997/12/26 20:43:17  mich
// + First revision after extinction of "impl" package.
// + Debug output now uses Debug.println().
//
// Revision 1.3  1997/10/15 04:57:09  mich
// + InstantiationHook funcionality added.
// Tested.
//
// Revision 1.2  1997/09/22 22:22:04  mich
// + checkConstraints() added
//
// Revision 1.1  1997/09/16 15:56:18  mich
// Initial revision
//

package agg.util.csp;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.LinkedHashSet;
import java.util.Vector;

import agg.util.OrderedSet;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.csp.Query_Type;


/** Implements a variable of the CSP algorithm. */
public class Variable {
	public final static int NODE = 0;
	public final static int ARC = 1;
	
	private final static BinaryPredicate theirQueryOrder = new QueryOrder();
	
	final private OrderedSet<Object> itsConstraints;
	final private OrderedSet<Query> itsOutgoingQueries;
	final private OrderedSet<Query> itsIncomingQueries;
	
	private Object itsInstance;

	private Iterator<?> itsDomain;
	
//	private boolean randomizedDomain;
	private boolean enabled;

	final private Vector<InstantiationHook> itsInstantiationHooks;

	private Object LHSgo; // node or arc from LHS of a rule
//	private boolean isEdge;
	private String convertedTypeString;
	
	private int kind=-1; // 0 is Node, 1 is Arc, otherwise is -1;
	private int domainsize;
	private int itsWeight;
	
	private Query_Type itsTypeQuery;

	public Variable() {
//		randomizedDomain = true;
		this.enabled = true;
		
		this.itsConstraints = new OrderedSet<Object>();
		this.itsOutgoingQueries = new OrderedSet<Query>(theirQueryOrder);
		this.itsIncomingQueries = new OrderedSet<Query>(theirQueryOrder);
		this.itsInstantiationHooks = new Vector<InstantiationHook>(2, 2);
	}

	public void clear() {
		this.LHSgo = null;
		this.itsTypeQuery = null;
		this.itsIncomingQueries.clear();
		this.itsOutgoingQueries.clear();
		final Enumeration<?> allConstraints = this.itsConstraints.elements();
		while (allConstraints.hasMoreElements()) {
			((BinaryConstraint) allConstraints.nextElement()).clear();	
		}
		this.itsConstraints.clear();
		this.itsInstantiationHooks.clear();
	}
	

	/**
	 * Return my current value, <code>null</code> if uninstantiated.
	 */
	public final Object getInstance() {
		return this.itsInstance;
	}

	/**
	 * Instantiate me by given value. 
	 */
	public final void setInstance(Object value) {
		Enumeration<InstantiationHook> en;
		if (this.itsInstance != null) {
			en = this.itsInstantiationHooks.elements();
			while (en.hasMoreElements())
				en.nextElement().uninstantiate(this);
		}

		this.itsInstance = value;

		if (this.itsInstance != null) {
			en = this.itsInstantiationHooks.elements();
			while (en.hasMoreElements())
				en.nextElement().instantiate(this);
		}
	}

	/**
	 * Check all my applicable constraints, i.e., check the consistency of my
	 * current instantiation with all previously instantiated variables.
	 * <p>
	 * <b>Pre:</b> <code>getInstance() != null</code>.
	 * 
	 * @return An Enumeration of all the Variables whose instantiations conflict
	 *         with my current instantiation. If all applicable constraints are
	 *         satisfied, the Enumeration is empty. Enumeration elements are of
	 *         type <code>Variable</code>.
	 */
	public final Enumeration<Variable> checkConstraints() {
		Vector<Variable> allConflictVars = new Vector<Variable>(5);
		Enumeration<?> allConstraints = this.itsConstraints.elements();
		while (allConstraints.hasMoreElements()) {
			BinaryConstraint aConstraint = (BinaryConstraint) allConstraints
					.nextElement();			
			if (aConstraint.isApplicable() && !aConstraint.execute()) {
				allConflictVars.addElement(aConstraint.getCause(this));
			}
		}
		return allConflictVars.elements();
	}

	/**
	 * Add <code>hook</code> to the set of my InstantiationHooks. I will call
	 * the encapsulated operations at the respective times of
	 * instantiation/uninstantiation, with myself as an argument.
	 * 
	 * @see agg.util.csp.InstantiationHook
	 */
	public final void addInstantiationHook(InstantiationHook hook) {
		this.itsInstantiationHooks.addElement(hook);
	}
	
	/**
	 * Return next object of the enumeration of my domain. This enumeration continues at the
	 * position where a previous access left off. The type of enumeration elements
	 * is dependent on the concrete domain.
	 */
	public final Object getNext() {
//		try {
		return this.itsDomain.next();
//		} catch (java.util.ConcurrentModificationException ex) {
//			return null;
//		}
	}
	
	public boolean hasNext() {
		return this.itsDomain.hasNext();
	}
	
	/**
	 * Set my domain in an enumeration representation. This very same enumeration is
	 * returned from a subsequent call of <code>getDomainEnum()</code>.
	 */
	public final void setDomainEnum(final HashSet<?> dom) {
		// NOTE: randomization will be done in Query_Type.setObjects(List<GraphObject> objects)
		
		this.itsDomain = dom.iterator();
	}
	
	
//	public void setRandomizedDomain(boolean randomized) {
//		this.randomizedDomain = randomized;
//	}
	
	public int getDomainSize() {
		return this.domainsize;
	}
		
	/**
	 * Return my weight. It is computed as the sum of the weights of all
	 * constraints attached and of all outgoing queries.
	 */
	public final int getWeight() {
		return this.itsWeight;
	}

	public final void addWeight(int w) {
		this.itsWeight += w;
	}

	/**
	 * Return an enumeration of all the constraints I'm involved in. Enumeration
	 * elements are of type <code>BinaryConstraint</code>.
	 * 
	 * @see agg.util.csp.BinaryConstraint
	 */
	public final Enumeration<?> getConstraints() {
		return this.itsConstraints.elements();
	}

	public void setDomainSize(int size) {
		this.domainsize = size;
	}

	/**
	 * Return an anumeration of all my outgoing queries. Elements are
	 * of type <code>Query</code>.
	 * 
	 * @see agg.util.csp.Query
	 */
	public final Enumeration<?> getOutgoingQueries() {
		return this.itsOutgoingQueries.elements();
	}

	public final int getOutgoingQueriesCount() {
		return this.itsOutgoingQueries.size();
	}

	/**
	 * Return an enumeration of all my incoming queries. Enumeration elements
	 * are of type <code>Query</code>.
	 * 
	 * @see agg.util.csp.Query
	 */
	public final Enumeration<?> getIncomingQueries() {
		return this.itsIncomingQueries.elements();
	}

	public final int getIncomingQueriesCount() {
		return this.itsIncomingQueries.size();
	}

	public final Vector<Variable> getIncomingVariables() {
		Vector<Variable> vec = new Vector<Variable>(2);
		Enumeration<?> e = this.itsIncomingQueries.elements();
		while (e.hasMoreElements()) {
			Variable v = ((Query) e.nextElement()).getTarget();
			if (this != v)
				vec.add(v);
		}
		return vec;
	}

	public final Vector<Variable> getOutgoingVariables() {
		Vector<Variable> vec = new Vector<Variable>(2);
		Enumeration<?> e = this.itsOutgoingQueries.elements();
		while (e.hasMoreElements()) {
			Variable v = ((Query) e.nextElement()).getTarget();
			if (this != v)
				vec.add(v);
		}
		return vec;
	}

	/** Let me know of a new constraint which I'm involved in. */
	protected final void addConstraint(BinaryConstraint c) {
		this.itsConstraints.add(c);
		this.itsWeight += c.getWeight();
	}

	public final void removeConstraint(BinaryConstraint c) {
		this.itsWeight -= c.getWeight();
		this.itsConstraints.remove(c);
	}
	
	/** Let me know of a query for which I am a source variable. */
	protected final void addOutgoingQuery(Query q) {
		this.itsOutgoingQueries.add(q);
		this.itsWeight += q.getWeight();
	}

	/** Let me know of a query for which I am the target variable. */
	protected final void addIncomingQuery(Query q) {
		if (q instanceof Query_Type) {
			this.itsTypeQuery = (Query_Type) q;
		}
		this.itsIncomingQueries.add(q);
	}

	public Query_Type getTypeQuery() {
		return this.itsTypeQuery;
	}

	/**
	 * Set the object for which this variable is defined.
	 */
	public void setGraphObject(final Object go) {
		this.LHSgo = go;
		if (this.LHSgo != null) { 
			if (((GraphObject)this.LHSgo).isArc()) { 		
//				isEdge = true;
				this.convertedTypeString = ((Arc) this.LHSgo).convertToKey();
			} else {
				this.convertedTypeString = ((Node) this.LHSgo).convertToKey();
			}		
		}
	}

	public String getConvertedTypeString() {
		return this.convertedTypeString;
	}
	
	/**
	 * Get the object for which this variable defined.
	 */
	public Object getGraphObject() {
		return this.LHSgo;
	}

	/**
	 * Set 0, if an object behind this variable is of type Node, set 1, if an
	 * object behind this variable is of type Arc, otherwise -1. Default is -1.
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/**
	 * Returns 0, if the object behind this variable is a Node, <br>
	 * returns 1, if the object behind this variable is an Arc, <br>
	 * otherwise -1.
	 */
	public int getKind() {
		if (this.kind == 0 || this.kind == 1)
			return this.kind;
		
		return -1;
	}

	public void setEnabled(boolean e) {
		this.enabled = e;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

}
