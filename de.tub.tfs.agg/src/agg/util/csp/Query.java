// $Id: Query.java,v 1.15 2010/09/23 08:26:52 olga Exp $

// $Log: Query.java,v $
// Revision 1.15  2010/09/23 08:26:52  olga
// tuning
//
// Revision 1.14  2010/03/04 14:13:31  olga
// code optimizing
//
// Revision 1.13  2010/02/22 14:42:45  olga
// code optimizing
//
// Revision 1.12  2009/10/05 08:53:25  olga
// RSA check - bug fixed
//
// Revision 1.11  2009/02/12 13:03:38  olga
// Some optimization of match searching
//
// Revision 1.10  2007/11/05 09:18:19  olga
// code tuning
//
// Revision 1.9  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.8  2007/09/10 13:05:15  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.7  2006/12/13 13:33:04  enrico
// reimplemented code
//
// Revision 1.6  2006/11/01 11:17:29  olga
// Optimized agg sources of  CSP algorithm,  match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.5  2006/05/08 15:47:10  olga
// some tests with variable order and queries
//
// Revision 1.4  2006/05/08 08:24:12  olga
// Some extentions of GUI: - Undo Delete button of tool bar to undo deletions
// if grammar elements like rule, NAC, graph constraints;
// - the possibility to add a new graph to a grammar or a copy of the current
// host graph;
// - to set one or more layer for consistency constraints.
// Also some bugs fixed of matching and some optimizations of CSP algorithmus done.
//
// Revision 1.3  2006/04/20 11:58:39  olga
// Attr type check: Bug fixed
//
// Revision 1.2  2006/03/01 09:55:47  olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.1  2005/08/25 11:56:55  enrico
// *** empty log message ***
//
// Revision 1.2  2005/06/20 13:37:04  olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1  2005/05/30 12:58:01  olga
// Version with Eclipse
//
// Revision 1.4  2004/05/06 17:23:27  olga
// graph matching OK
//
// Revision 1.3  2004/04/28 12:46:38  olga
// test CSP
//
// Revision 1.2  2003/03/05 18:24:25  komm
// sorted/optimized import statements
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.4  1999/06/28 16:31:58  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.3  1997/12/26 20:34:13  mich
// + First revision after extinction of "impl" package.
//
// Revision 1.2  1997/09/22 05:19:11  mich
// First working version after conversion from
// VariableOrderingStrategy to SearchStrategy.
//
// Revision 1.1  1997/09/16 15:56:18  mich

package agg.util.csp;

import java.util.HashSet;
import java.util.List;
import java.util.Vector;


/** An abstract class that represents a query for a variable domain. */
public abstract class Query {
	
	protected List<Variable> itsSources = new Vector<Variable>(2, 1);

	protected Variable itsTarget;

	protected int itsWeight;

	// for test only
	public String typeNameOfVariable = "";

	/** Construct myself to be a constant query. */
	public Query(Variable tar, int weight) {
		this.itsTarget = tar;
		this.itsWeight = weight;
		initialize();
	}

	/** Construct myself to be a unary query. */
	public Query(Variable src, Variable tar, int weight) {
		this.itsSources.add(src);
		this.itsTarget = tar;
		this.itsWeight = weight;
		initialize();
	}

	/** Construct myself to be a binary query. */
	public Query(Variable src1, Variable src2, Variable tar, int weight) {
		this.itsSources.add(src1);
		this.itsSources.add(src2);
		this.itsTarget = tar;
		this.itsWeight = weight;
		initialize();
	}

	private final void initialize() {
		for (int i = 0; i < this.itsSources.size(); i++) {
			this.itsSources.get(i).addOutgoingQuery(this);
		}
		if (this.itsTarget != null)
			this.itsTarget.addIncomingQuery(this);
		else
			System.out
					.println("agg.util.csp.Query.initialize():: itsTarget is null!");
	}

	/**
	 * Return <code>true</code> iff all my source variables are instantiated,
	 * while my target variable is not.
	 */
	public boolean isApplicable() {
		if (this.itsTarget.getInstance() != null)
			return false;
		for (int i = 0; i < this.itsSources.size(); i++) {
			Variable v = this.itsSources.get(i);
			if (v.getInstance() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return <code>true</code> iff I am a constant query. That means, the
	 * result of <code>execute()</code> is the same for any variable
	 * instantiation configuration.
	 */
	public boolean isConstant() {
		return (this.itsSources.size() == 0);
	}

	/**
	 * Return a list of candidate values for the target variable.
	 * <p>
	 * <b>Pre:</b> <code>isApplicable()</code>.
	 */
//	public abstract List<?> execute();
	public abstract HashSet<?> execute();
	
	/** Return the variable that I'm determining the domain for. */
	public Variable getTarget() {
		return this.itsTarget;
	}

	/**
	 * Return the list of variables that need to be instantiated for
	 * the query to work. Enumeration elements are of type <code>Variable</code>.
	 */
	public final List<?> getSources() {
		return this.itsSources;
	}

	public final Variable getSource(int i) {
		return this.itsSources.get(i);
	}

	/**
	 * Return the number of candidate values <code>execute()</code> will
	 * provide. For non-constant queries, this will most probably be based on
	 * estimation. The value may change in response to re-setting the CSP
	 * domains with the <code>setDomain()</code> method.
	 * <p>
	 * <b>Pre:</b> <code>csp.getDomain() != null</code>.
	 * 
	 * @see agg.util.csp.CSP#setDomain
	 */
	public abstract int getSize();

	/**
	 * Return my weight. This is a constant integer usually chosen inversely
	 * proportional to the estimated size of the candidate set returned by a
	 * query execution.
	 */
	public final int getWeight() {
		return this.itsWeight;
	}

	/**
	 * Return the name of my implementing class.
	 */
	public abstract String getKind();

	public abstract boolean isDomainEmpty();
	
	/**
	 * Return the current instance of the source variable given by the index
	 * <code>i</code>.
	 * <p>
	 * <b>Pre:</b> <code>i &lt; itsSources.size()</code>.
	 */
	protected final Object getSourceInstance(int i) {
		return this.itsSources.get(i).getInstance();
	}
	
	// pablo -->
	
	/**
	 * Stores the correspondent constraint of this query.
	 * 
	 * @see Query.setCorrespondent()
	 * @see Query.activateCorrespondent()
	 * @see Query.deactivateCorrespondent()
	 */
	private BinaryConstraint correspondent;
	
	/**
	 * Sets the correspondent constraint of this query.
	 * 
	 * @param constraint
	 */
	public void setCorrespondent(BinaryConstraint constraint) {
		this.correspondent = constraint;
	}
	
	/**
	 * Activates the correspondent constraint of this query.
	 */
	public void activateCorrespondent() {
		if(this.correspondent == null)
			return;
		this.correspondent.activate();
	}
	
	/**
	 * Deactivates the correspondent constraint of this query.
	 */
	public void deactivateCorrespondent() {
		if(this.correspondent == null)
			return;
		this.correspondent.deactivate();
	}
	// pablo >
}
