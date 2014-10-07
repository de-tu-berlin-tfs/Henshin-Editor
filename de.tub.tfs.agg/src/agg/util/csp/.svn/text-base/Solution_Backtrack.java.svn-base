// $Id: Solution_Backtrack.java,v 1.18 2010/09/23 08:26:53 olga Exp $

// $Log: Solution_Backtrack.java,v $
// Revision 1.18  2010/09/23 08:26:53  olga
// tuning
//
// Revision 1.17  2010/06/09 10:24:31  olga
// tuning
//
// Revision 1.16  2010/03/08 15:50:34  olga
// code optimizing
//
// Revision 1.15  2010/02/22 14:43:23  olga
// code optimizing
//
// Revision 1.14  2009/05/12 10:36:53  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.13  2009/02/23 09:01:57  olga
// Convert Atomic Graph Constraints to Post Application Condition of Rule - bug fixed, reworked and improved error messaging
// Graph copy - bug fixed
// Code tuning
//
// Revision 1.12  2008/07/30 06:27:14  olga
// Applicability of RS , concurrent rule - handling of attributes improved
//
// Revision 1.11  2008/04/07 09:36:55  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.10  2007/12/03 08:35:12  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.9  2007/11/19 08:48:41  olga
// Some GUI usability mistakes fixed.
// Default values in node/edge of a type graph implemented.
// Code tuning.
//
// Revision 1.8  2007/11/05 09:18:19  olga
// code tuning
//
// Revision 1.7  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.6  2007/09/10 13:05:09  olga
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
// Revision 1.5  2004/12/20 14:53:48  olga
// Changes because of matching optimisation.
//
// Revision 1.4  2004/01/15 16:43:06  olga
// Korrektur an transformation
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
// Revision 1.5  1999/07/14 09:09:20  olga
// Umstellung auf JAVA2.0
// Zum Testen oeffnet sich nur in
// Transform/Step der InputParameterEditor
// Verbesserung von GUI und AttrEditor
//
// Revision 1.4  1999/06/28 16:36:10  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.3  1998/09/03 14:31:36  mich
// Updated for use with JGL V3.1.
//
// Revision 1.2  1998/07/15 22:14:33  mich
// *** empty log message ***
//
// Revision 1.1  1998/07/15 22:13:00  mich
// Initial revision
//

// This is basically the same as Solution_Backjump rev 1.1, 
// slightly hacked up so it does 
// no backjumps, because backjumping may lose some solutions as long as
// attributes don't provide information about their interdependencies 
// caused by the use of variables.
//    It is a hack because it still does all the work needed to
// prepare backjumping. This should really be optimized away before
// doing efficiency comparisons BJ vs. BT!
//
// (see sec. 7.1.5. (page 89) of my master's thesis about the dis-
//  advantages of BJ which should be optimized away here)

package agg.util.csp;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.xt_basis.GraphObject;
import agg.xt_basis.csp.Query_Type;


/** A CSP solution strategy NOT using the back jumping technique. */
public class Solution_Backtrack implements SolutionStrategy {
	private CSP itsCSP;

	private boolean parallel;
	
	private boolean startParallelbyFirst;

	private Vector<Query> itsQueries = new Vector<Query>();

	final private Dictionary<Variable, Integer> itsVarIndexMap = new Hashtable<Variable, Integer>();

	final private Dictionary<Object, Variable> itsInstanceVarMap = new Hashtable<Object, Variable>();

	private boolean itsInjectiveFlag;

	// the map of other solution solver
	private Dictionary<Object, Variable> otherInstanceVarMap;

	private SearchStrategy itsSearcher = new Search_BreadthFirst();

	/**
	 * Vector elements are of type <code>OrderedSet</code> of
	 * <code>Variable</code>. Elements are of type <code>Variable</code>.
	 */
//	final private HashSet itsBackjumpTargets = new HashSet();

	/**
	 * Value is either <code>NEXT</code> or <code>BACK</code> according to
	 * the recent traversal direction of the search tree.
	 */
//	private int itsDirection;

	/** Index into <code>itsVariables</code>. */
	private int itsCurrentIndex;

	/**
	 * Index into <code>itsVariables</code>. Variables below this index are
	 * considered to be pre-instantiated and will not be touched by the solution
	 * algorithm.
	 */
	private Variable itsCurrentVar;

	private Query itsCurrentQuery;

	private int itsState;

	@SuppressWarnings("unused")
	private int itsInstantiationCounter;

	@SuppressWarnings("unused")
	private int itsBackstepCounter;

	private boolean solutionFound;
	
	// constants for csp solution state machine:
	private final static int START = 1;

	private final static int NEXT = 2;

	private final static int INSTANTIATE = 3;

	private final static int BACK = 4;

	private final static int SUCCESS = 5;

	private final static int NO_MORE_SOLUTIONS = 6;

	private final static int BACKJUMP = 7;

	public Solution_Backtrack() {
		this.itsInjectiveFlag = false;
	}

	public Solution_Backtrack(boolean injective) {
		this.itsInjectiveFlag = injective;
	}

	public void clear() {
		this.itsQueries.clear();
		((Hashtable<Variable, Integer>) this.itsVarIndexMap).clear();
		((Hashtable<Object, Variable>) this.itsInstanceVarMap).clear();
	}
	
	public void setRelatedInstanceVarMap(
			Dictionary<Object, Variable> relatedVarIndexMap) {
		this.otherInstanceVarMap = relatedVarIndexMap;
	}

	public Dictionary<Object, Variable> getInstanceVarMap() {
		return this.itsInstanceVarMap;
	}

	/**
	 * Compute the search plan (variable order) and do some other initialization
	 * stuff.
	 * 
	 * @return <code>false</code> iff some pre-instantiated variables are
	 *         violating some constraint.
	 */
	private final boolean initialize(CSP csp) {
		this.itsCSP = csp;
		this.itsQueries.removeAllElements();
		this.itsQueries = this.itsSearcher.execute(this.itsCSP);
		this.itsQueries.trimToSize();
 
		for (int i = 0; i < this.itsQueries.size(); i++) {
			this.itsVarIndexMap.put(this.itsQueries.elementAt(i).getTarget(),
					Integer.valueOf(i));
		}

		Enumeration<Variable> anEnum = this.itsCSP.getVariables();
		Variable aVar;
		while (anEnum.hasMoreElements()) {
			aVar = anEnum.nextElement();
			if (aVar.getInstance() != null) {
				if (aVar.checkConstraints().hasMoreElements())
					return false;
				this.itsVarIndexMap.put(aVar, Integer.valueOf(-1));
				this.itsInstanceVarMap.put(aVar.getInstance(), aVar);
			}
		}
		return true;
	}

	public final boolean reinitialize(boolean doUpdateQueries) {
		if (doUpdateQueries) {
			this.itsQueries.removeAllElements();
			this.itsQueries = this.itsSearcher.execute(this.itsCSP);
			this.itsQueries.trimToSize();
		}

		((Hashtable<Variable, Integer>) this.itsVarIndexMap).clear();
		((Hashtable<Object, Variable>) this.itsInstanceVarMap).clear();

		for (int i = 0; i < this.itsQueries.size(); i++) {
			Query q = this.itsQueries.elementAt(i);
			this.itsVarIndexMap.put(q.getTarget(), Integer.valueOf(i));
			if (q instanceof Query_Type) {
				((Query_Type)q).resetObjects();
			}
		}

		Enumeration<Variable> anEnum = this.itsCSP.getVariables();
		while (anEnum.hasMoreElements()) {
			Variable aVar = anEnum.nextElement();
			if (aVar.getInstance() != null) {
				if (aVar.checkConstraints().hasMoreElements())
					return false;
				this.itsVarIndexMap.put(aVar, Integer.valueOf(-1));
				this.itsInstanceVarMap.put(aVar.getInstance(), aVar);
			}
		}

		this.itsState = START;
		return true;
	}

	public void reinitialize(Variable var) {
		boolean queryExists = false;
		Query q = null;
		for (int i = 0; i < this.itsQueries.size(); i++) {
			q = this.itsQueries.elementAt(i);
			Variable v = this.itsQueries.elementAt(i).getTarget();
			if (v == var) {
				queryExists = true;
				if (var.getInstance() != null)
					this.itsInstanceVarMap.remove(var.getInstance());
				this.itsVarIndexMap.put(v, Integer.valueOf(i));
				this.itsState = START;
				break;
			}
		}
		if (!queryExists) {
			if (var.getInstance() != null)
				this.itsInstanceVarMap.remove(var.getInstance());
			q = var.getTypeQuery();
			if (q != null) {
				this.itsQueries.add(0, q);

				for (int i = 0; i < this.itsQueries.size(); i++) {
					q = this.itsQueries.elementAt(i);
					Variable v = this.itsQueries.elementAt(i).getTarget();
					this.itsVarIndexMap.put(v, Integer.valueOf(i));
				}
				this.itsState = START;
			}
		}
	}

	public final void reset() {
		this.itsState = START;
	}

	public void resetQuery_Type() {
		for (int i = 0; i < this.itsQueries.size(); i++) {
			if (this.itsQueries.elementAt(i) instanceof Query_Type) {
				((Query_Type)this.itsQueries.elementAt(i)).resetObjects();
			}
		}
	}
	
	public synchronized final boolean next(CSP csp) {

		this.solutionFound = false;
		
		if (!csp.equals(this.itsCSP)) {
			if (!initialize(csp))
				return false;
			this.itsState = START;
		}

		if (this.itsState == SUCCESS) {
			this.itsState = BACK;
			// we want to continue where we left off, instead of actually
			// making a back step:
			this.itsCurrentIndex++;
		}

		while (true) {
			switch (this.itsState) {
			case START:
				this.itsInstantiationCounter = 0;
				this.itsBackstepCounter = 0;
				this.itsCurrentIndex = -1;
				this.itsState = NEXT;
				break;

			case NEXT:
				if (this.itsCurrentIndex >= this.itsQueries.size() - 1) {
					this.itsState = SUCCESS;
				} else {
//					itsBackjumpTargets.clear();

					this.itsCurrentQuery = this.itsQueries.elementAt(++this.itsCurrentIndex);
					this.itsCurrentVar = this.itsCurrentQuery.getTarget();

					if (this.itsCurrentQuery.isApplicable()) {
						this.itsCurrentVar.setDomainEnum(this.itsCurrentQuery.execute());
//						addToBackjumpTargets(itsCurrentQuery.getSources());
						this.itsState = INSTANTIATE;
					} else {
						this.itsState = NO_MORE_SOLUTIONS;
					}
				}
//				itsDirection = NEXT;
				break;

			case INSTANTIATE:
				// HACK to never use backjumping at all:
				this.itsState = BACK;

				// deactivate correspondent constraint before checking // pablo
				this.itsCurrentQuery.deactivateCorrespondent();
				
				while (this.itsCurrentVar.hasNext()) {
					this.itsInstantiationCounter++;
					this.itsCurrentVar.setInstance(this.itsCurrentVar.getNext());
					
					Variable aConflictVar = checkInjection(this.itsCurrentVar);
					if (aConflictVar != null) {
						this.itsCurrentVar.setInstance(null);
//						if (this.itsVarIndexMap.get(aConflictVar) != null)
//							itsBackjumpTargets.add(aConflictVar);
						continue;
					}

					Enumeration<?> allConflictVars = this.itsCurrentVar.checkConstraints();
					if (!allConflictVars.hasMoreElements()) {
						this.itsState = NEXT;
						addInjection(this.itsCurrentVar);
						break;
					} 
//					else {
//						if (itsState == BACKJUMP)
//							itsBackjumpTargets.add(getFirstOf(allConflictVars));
//					}
				}
				
				// re-activate correspondent constraint after checking // pablo
				this.itsCurrentQuery.activateCorrespondent();
				
				break;

			case BACK:
				this.itsBackstepCounter++;
				if (this.itsCurrentIndex == 0) {
					if (this.itsCurrentVar != null
							&& this.itsCurrentVar.hasNext()) {
						removeInjection(this.itsCurrentVar);
						this.itsCurrentVar.setInstance(null);
						this.itsState = INSTANTIATE;
//						this.itsDirection = NEXT;
					} else {
						this.itsState = NO_MORE_SOLUTIONS;
//						this.itsDirection = BACK;
					}
				} else {
					removeInjection(this.itsCurrentVar);
					this.itsCurrentVar.setInstance(null);
					this.itsCurrentVar = this.itsQueries.elementAt(--this.itsCurrentIndex)
							.getTarget();
					removeInjection(this.itsCurrentVar);
					this.itsState = INSTANTIATE;
//					itsDirection = BACK;
				}
				break;
			// BACKJUMP is not used here
			case BACKJUMP:
				this.itsBackstepCounter++;
//				int aStepCounter = 0;
				this.itsState = NO_MORE_SOLUTIONS;
				while (this.itsCurrentIndex > 0) {
//					aStepCounter++;
					removeInjection(this.itsCurrentVar);
					this.itsCurrentVar.setInstance(null);
					this.itsCurrentVar = this.itsQueries.elementAt(--this.itsCurrentIndex)
							.getTarget();
					removeInjection(this.itsCurrentVar);

					this.itsState = INSTANTIATE;

//					if (!itsBackjumpTargets.find(itsCurrentVar).equals(
//							itsBackjumpTargets.end())) {
//						break;
//					}
				}
//				itsDirection = BACK;
				break;

			case SUCCESS:
				if (this.parallel && this.startParallelbyFirst) {
					removeUsedObjectFromDomain();
				}
				
				this.solutionFound = true;
				
				return true;

			case NO_MORE_SOLUTIONS:
				return false;

			default:
				System.out.println("Should have never come here..." + this.itsState);
			}
		}
	}

	private void removeUsedObjectFromDomain() {
		for (int i=0; i<this.itsQueries.size(); i++) {
			Query q = this.itsQueries.get(i);
			if (q instanceof Query_Type) {
				((Query_Type)q).removeObject((GraphObject) q.getTarget().getInstance());
			}
		}

		((Hashtable<Object, Variable>) this.itsInstanceVarMap).clear();
//		itsBackjumpTargets.clear();	
	}
	
	public boolean hasSolution() {
		return this.solutionFound;
	}
	
	public boolean hasQueries() {
		return (!this.itsQueries.isEmpty());
	}
	
	/*
	 * Return the Variable with the smallest index of <code>vars</code>.
	 * <p>
	 * <b>Pre:</b> <code>vars.hasMoreElements()</code>.
	 */
	/*
	private final Variable getFirstOf(Enumeration<Variable> vars) {
		Variable aFirstVar = vars.nextElement();
		int aFirstIndex = itsQueries.size();
		Variable aCurrentVar;
		int aCurrentIndex;
		while (vars.hasMoreElements()) {
			aCurrentVar = vars.nextElement();
			aCurrentIndex = itsVarIndexMap.get(aCurrentVar).intValue();
			if (aCurrentIndex < aFirstIndex) {
				aFirstIndex = aCurrentIndex;
				aFirstVar = aCurrentVar;
			}
		}
		return aFirstVar;
	}
*/
	private final void addInjection(Variable var) {
		if (this.itsInjectiveFlag)
			this.itsInstanceVarMap.put(var.getInstance(), var);
	}

	private final void removeInjection(Variable var) {
		if (this.itsInjectiveFlag && (var.getInstance() != null))
			this.itsInstanceVarMap.remove(var.getInstance());
	}

	private final Variable checkInjection(Variable var) {
		if (var.getInstance() != null) {
			if (this.otherInstanceVarMap != null) {
				// this check is always injective
				Variable other = this.otherInstanceVarMap.get(var.getInstance());
				if (other != null) {
					return other;
				}
			}
			if (this.itsInjectiveFlag)
				return this.itsInstanceVarMap.get(var.getInstance());
			
			return null;
		} 
		return null;
	}

//	private final void addToBackjumpTargets(Enumeration<?> en) {
//		while (en.hasMoreElements())
//			itsBackjumpTargets.add(en.nextElement());
//	}

	/**
	 * @see agg.util.csp.SolutionStrategy#parallelSearch()
	 */
	public boolean parallelSearch() {
		return this.parallel;
	}

	/**
	 * @see agg.util.csp.SolutionStrategy#enableParallelSearch(boolean)
	 */
	public void enableParallelSearch(boolean b) {
		this.parallel = b;
	}

	/**
	 * @see agg.util.csp.SolutionStrategy#setStartParallelSearchByFirst(boolean)
	 */
	public void setStartParallelSearchByFirst(boolean b) {
		this.startParallelbyFirst = b;
	}

	/* (non-Javadoc)
	 * @see agg.util.csp.SolutionStrategy#getStartVariable()
	 */
	public Variable getStartVariable() {
		return this.itsQueries.get(0).itsTarget;
	}

	/* (non-Javadoc)
	 * @see agg.util.csp.SolutionStrategy#getQuery(agg.util.csp.Variable)
	 */
	public Query getQuery(Variable var) {
		return this.itsQueries.get(this.itsVarIndexMap.get(var).intValue());
	}
}
