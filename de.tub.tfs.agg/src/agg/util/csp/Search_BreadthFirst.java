// $Id: Search_BreadthFirst.java,v 1.15 2010/03/08 15:50:35 olga Exp $

// $Log: Search_BreadthFirst.java,v $
// Revision 1.15  2010/03/08 15:50:35  olga
// code optimizing
//
// Revision 1.14  2010/02/22 14:42:56  olga
// code optimizing
//
// Revision 1.13  2009/02/12 13:03:38  olga
// Some optimization of match searching
//
// Revision 1.12  2007/11/05 09:18:19  olga
// code tuning
//
// Revision 1.11  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.10  2007/09/10 13:05:06  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.9  2007/05/07 07:59:35  olga
// CSP: extentions of CSP variables concept
//
// Revision 1.8  2007/01/11 10:21:18  olga
// Optimized Version 1.5.1beta ,  free for tests
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
// Revision 1.3  2003/03/05 18:24:25  komm
// sorted/optimized import statements
//
// Revision 1.2  2002/12/04 14:00:18  komm
// code newly formated for debuging
//
// Revision 1.1.1.1  2002/07/11 12:17:26  olga
// Imported sources
//
// Revision 1.5  2000/05/17 11:58:37  olga
// Testversion fuer Gabi mit diversen Aenderungen. Fehler sind moeglich!!
//
// Revision 1.4  1999/12/06 07:40:39  olga
// *** empty log message ***
//
// Revision 1.3  1999/06/28 16:34:55  shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2  1998/04/07 14:16:12  mich
// Updated for use with JGL V3.1.
//
// Revision 1.1  1997/12/26 21:03:59  mich
// Initial revision
//
// ********************************************
// *** moved from util.csp.impl to util.csp ***
// ********************************************
//
// Revision 1.2  1997/10/15 06:21:13  mich
// + SimpleVariableOrder used for ordering of internal OrderedSets.
// Tested.
//
// Revision 1.1  1997/09/22 05:09:04  mich
// Initial revision
//

package agg.util.csp;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.util.OrderedSet;


/** A search strategy that traverses the constraint graph breadth first. */
public class Search_BreadthFirst implements SearchStrategy {

	private static BinaryPredicate theirVariableOrder = new SimpleVariableOrder();
	
	public Search_BreadthFirst() {
	}

	/**
	 * Returns the best possible query order of the CSP.
	 */
	public final Vector<Query> execute(CSP csp) {
		Vector<Query> aQueryResult = new Vector<Query>();
		
		OrderedSet<Variable> allVarsLeft = new OrderedSet<Variable>(theirVariableOrder);
		
		Enumeration<?> anEnum = csp.getVariables();
		Variable aVar;
		Query aQuery;
		
		// only variables without instance will be used
		while (anEnum.hasMoreElements()) {
			aVar = (Variable) anEnum.nextElement();
			if (aVar.getInstance() == null) {
				allVarsLeft.add(aVar);
			}
		}
		// showVariables(allVarsLeft);

		List<Query> aQueryList = bfs(new OrderedSet<Variable>(theirVariableOrder), allVarsLeft); 
		
		if (aQueryList != null) {
			Iterator<Query> iter1 = aQueryList.iterator();
			while (iter1.hasNext()) {
				aQuery = iter1.next();
				// remove dummy instantiation which was set by bfs1():
				if ((aQuery != null) && (aQuery.getTarget() != null)) {				
					aQuery.getTarget().setInstance(null);
					aQueryResult.addElement(aQuery);
				}
			}			
	//		showQueries(aQueryResult, "");
		}
		return aQueryResult;
	}
	
	/*
	 * Returns the best possible queries order or null.
	 * Note: returned null will be caught in execute(CSP). 
	 */
	private final Vector<Query> bfs(OrderedSet<Variable> breadthvars,
									OrderedSet<Variable> varsleft) {
		if (!breadthvars.isEmpty()) {
			Vector<Query> aQueryList = new Vector<Query>();
			OrderedSet<Variable> aVarSet = new OrderedSet<Variable>(theirVariableOrder);							
			Variable aVar;
			for (Enumeration<?> en = breadthvars.elements(); en.hasMoreElements();) {
				aVar = (Variable) en.nextElement();
				// search for best query of the current variable
				Query q = getBestQuery(aVar);			
				aQueryList.add(q);
				// remove current variable from the set
				varsleft.remove(aVar);				
				// dummy instantiation to make constraints & queries applicable:
				aVar.setInstance(this);
				// make union of variable set with the vicinity set of current variable
//				aVarSet = aVarSet.union(getVicinity(aVar, varsleft));
				OrderedSet<Variable> vic = getVicinity(aVar, varsleft);
				if (vic != null)
					aVarSet = aVarSet.union(vic);
			}
			// call recursively 
			List<Query> aVicQueryList = bfs(aVarSet, varsleft);
			
			// add result if not null
			if (aVicQueryList != null && !aVicQueryList.isEmpty())
				aQueryList.addAll(aVicQueryList);
			
			return aQueryList;
		} 
		else {
			if (varsleft.isEmpty()) {
				return null;
			}
			
			// get best variable which is the first Arc or if no arcs exist, the first Node
			Variable aVar = getBestVar(varsleft);
			breadthvars.add(aVar);
			
			// call recursively 
			return bfs(breadthvars, varsleft);
		}
	}
	
	private final Query getBestQuery(Variable var) {
		// Remember: CSP implementation have to ensure that there
		// is always at least one incoming Query applicable for each
		// uninstantiated variable!

		// Initialize aBestSize with the size of the first applicable
		// Query:
		Enumeration<?> anEnum = var.getIncomingQueries();
		Query aBestQuery = null;
		int aBestSize = -1;
		while (anEnum.hasMoreElements()) {
			aBestQuery = (Query) anEnum.nextElement();
			if (aBestQuery.isApplicable()) {
				aBestSize = aBestQuery.getSize();
//				aBestSize = aBestQuery.getTarget().getTypeQuery().getSize(); 
				break;
			}
		}		
		if (aBestSize == -1)
			return null; // should never happen!

		// Search best Query:
		Query aQuery;
		int aSize;
		while (anEnum.hasMoreElements()) {
			aQuery = (Query) anEnum.nextElement();
			aSize = aQuery.getSize(); 
//			aSize = aQuery.getTarget().getTypeQuery().getSize();
			if (aQuery.isApplicable() && (aSize < aBestSize)) {
				aBestSize = aSize;
				aBestQuery = aQuery;
			}
		}
		return aBestQuery;
	}
	
	/*
	 * Returns the vicinity of the Variable v when it exists, otherwise - null.
	 */
	private final OrderedSet<Variable> getVicinity(Variable v, OrderedSet<Variable> varsleft) {
		OrderedSet<Variable> aVicinity = null;
		Variable aVar;
		Enumeration<?> allConstraints = v.getConstraints();
		while (allConstraints.hasMoreElements()) {
			aVar = getOtherVariable((BinaryConstraint) allConstraints
					.nextElement(), v);
			if (varsleft.indexOf(aVar, 0) != -1) {
				if (aVicinity == null)
					aVicinity = new OrderedSet<Variable>(theirVariableOrder);
				aVicinity.add(aVar);
			}
		}
		return aVicinity;
	}
	
	/*
	 * Returns the first variable if it represents an edge,
	 * otherwise - the next first variable which represents an edge.
	 */
	private final Variable getBestVar(OrderedSet<Variable> vars) {
		vars.start();
		
		Variable v = vars.get();
		while ((v.getKind() != Variable.ARC) && vars.hasNext()) {
			v = vars.get();
		}
		return v;
	}
	
	private final Variable getOtherVariable(BinaryConstraint bc, Variable v) {
		return v.equals(bc.getVar1()) ? bc.getVar2() : bc.getVar1();
	}

	
/*	
	private void showQueries(Vector<Query> aQueryVec, String text) {
		System.out.println(text+"\n*** Search_BreadthFirst: QUERIES *** count: "
				+ aQueryVec.size());
		for (int i = 0; i < aQueryVec.size(); i++) {
			Query aQuery = aQueryVec.get(i);
			GraphObject go = (GraphObject) aQuery.getTarget().getGraphObject();
			System.out.println("Query::"
					+ "   kind: " + aQuery.getKind()
					+ "   size: " + aQuery.getSize() 
					+ "  applicable: " + aQuery.isApplicable() 
					+ "  tar: " + aQuery.getTarget() 
					+ "  weight: " + aQuery.getWeight() 
					+ "  tar weight: " + aQuery.getTarget().getWeight() 
					+ "  tar obj is Node: " + (aQuery.getTarget().getKind() == 0)
					+ "  LHS obj: "+ go.getType().getName());
		}
	}

	private void showVariables(OrderedSet allVars) {
		for (Enumeration<?> en = allVars.elements(); en.hasMoreElements();) {
			Variable aVar = (Variable) en.nextElement();
			System.out.println("\t"
					+ ((GraphObject) aVar.getGraphObject()).getType().getName()
					+ "   dom size: " + aVar.getDomainSize());
		}
		System.out.println();
	}
	*/
}
