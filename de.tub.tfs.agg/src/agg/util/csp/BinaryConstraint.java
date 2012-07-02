// $Id: BinaryConstraint.java,v 1.7 2010/08/23 07:35:26 olga Exp $

// $Log: BinaryConstraint.java,v $
// Revision 1.7  2010/08/23 07:35:26  olga
// tuning
//
// Revision 1.6  2009/02/12 13:03:38  olga
// Some optimization of match searching
//
// Revision 1.5  2008/04/07 09:36:55  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.4  2007/09/10 13:05:08  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.3  2006/11/01 11:17:29  olga
// Optimized agg sources of  CSP algorithm,  match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.2  2006/05/08 08:24:12  olga
// Some extentions of GUI: - Undo Delete button of tool bar to undo deletions
// if grammar elements like rule, NAC, graph constraints;
// - the possibility to add a new graph to a grammar or a copy of the current
// host graph;
// - to set one or more layer for consistency constraints.
// Also some bugs fixed of matching and some optimizations of CSP algorithmus done.
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
// Revision 1.3  1999/06/28 16:00:52  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1997/12/26 20:27:43  mich
// + First revision after extinction of "impl" package.
//
// Revision 1.1  1997/09/16 15:56:18  mich
// Initial revision
//

package agg.util.csp;

/** An abstract class for binary constraints. */
public abstract class BinaryConstraint {
	protected Variable itsVar1;

	protected Variable itsVar2;

	protected int itsWeight;

	/**
	 * Construct myself to be a binary constraint between variables
	 * <code>v1</code> and <code>v2</code>, with the specified
	 * <code>weight</code>.
	 */
	public BinaryConstraint(Variable v1, Variable v2, int weight) {
		this.itsVar1 = v1;
		this.itsVar2 = v2;
		this.itsWeight = weight;
		this.itsVar1.addConstraint(this);
		this.itsWeight++;
		this.itsVar2.addConstraint(this);
		this.itsWeight++;
	}

	/**
	 * Construct myself to be a &quot;unary&quot; constraint on <code>v</code>.
	 * Actually, this is a BinaryConstraint with both its variables being
	 * <code>v</code>.
	 */
	public BinaryConstraint(Variable v, int weight) {
		this.itsVar1 = v;
		this.itsVar2 = v;
		this.itsWeight = weight;
		this.itsVar1.addConstraint(this);
		this.itsWeight++;
	}

	public abstract void clear();
	
	// pablo -->
	/**
	 * Determines whether this constraint is active or not. 
	 */
	private boolean active = true;
	
	/**
	 * Activate this constraint.
	 */
	public void activate() {
		this.active = true;
	}
	
	/**
	 * Deactivate this constraint.
	 */
	public void deactivate() {
		this.active = false;
	}
	// pablo >
	
	/** Return <code>true</code> iff all variables involved are instantiated. */
	public boolean isApplicable() {
		if(!this.active) // pablo
			return false; 
		
		return ((this.itsVar1.getInstance() != null) && (this.itsVar2.getInstance() != null));
	}

	/**
	 * Check if the constraint is satisfied.
	 * <p>
	 * Pre: <code>isApplicable()</code>.
	 */
	public abstract boolean execute();

	/**
	 * When <code>execute()</code> failed, this returns the variable that is
	 * supposed to have caused the failure.
	 * 
	 * @param rvar
	 *            the variable (of the two involved) that has been instantiated
	 *            most recently.
	 */
	public Variable getCause(Variable rvar) {
		return (rvar.equals(this.itsVar1)) ? this.itsVar2 : this.itsVar1;
	}

	/** Return my first variable. */
	public Variable getVar1() {
		return this.itsVar1;
	}

	/** Return my second variable. */
	public Variable getVar2() {
		return this.itsVar2;
	}

	/**
	 * Return my weight. The higher the value, the higher the significance of
	 * the constraint.
	 */
	public int getWeight() {
		return this.itsWeight;
	}
}
