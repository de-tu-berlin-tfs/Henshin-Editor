package agg.xt_basis.csp;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.csp.BinaryConstraint;
import agg.util.csp.CSP;
import agg.util.csp.Query;
import agg.util.csp.SolutionStrategy;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Type;

/**
 * A CSP whose solutions represent morphisms between two graphs.
 * 
 * @see agg.util.csp.CSP
 */
public class ALR_CSP extends CSP {

	private AttrContext itsAttrContext;
	
	private boolean randomizedDomain;
	
	private boolean directed = true;
	
//	private boolean withNTI;
	
	/**
	 * A 1:1 mapping of the objects of <code>itsVariableGraph</code> to the
	 * variables of the CSP. Keys are of type <code>GraphObject</code>,
	 * values of type <code>Variable</code>.
	 */
	final private Dictionary<GraphObject, Variable> 
	itsObjVarMap = new Hashtable<GraphObject, Variable>();

	/**
	 * A mapping of every <code>Type.convertToKey()</code> of the variable
	 * graph to the set of graph objects of this type in the domain graph.
	 * <p>
	 * Keys are of type <code>String</code>, values of type
	 * <code>Vector</code> of <code>GraphObject</code>.
	 * 
	 * @see agg.xt_basis.Type
	 * @see agg.xt_basis.GraphObject
	 * @see java.util.Vector
	 */
	final private Dictionary<String, HashSet<GraphObject>> itsTypeMap 
							= new Hashtable<String, HashSet<GraphObject>>();

	/**
	 * @deprecated
	 */
	public ALR_CSP(final Graph vargraph, 
					final AttrContext ac, 
					boolean injective) {
		super(new agg.util.csp.Solution_Backjump(injective));
		
		this.itsAttrContext = ac;
//		this.withNTI = vargraph.getTypeSet().hasInheritance();
		
		this.directed = vargraph.getTypeSet().isArcDirected();
		buildConstraintGraph(vargraph);
	}

	/**
	 * @deprecated
	 */
	public ALR_CSP(final Graph vargraph, 
					final AttrContext ac,
					final SolutionStrategy st) {
		super(st);
		
		this.itsAttrContext = ac;
//		this.withNTI = vargraph.getTypeSet().hasInheritance();
		
		this.directed = vargraph.getTypeSet().isArcDirected();
		buildConstraintGraph(vargraph);
	}

	/**
	 * Construct myself to be a CSP where every GraphObject of the specified Graph
	 * <code>vargraph</code> corresponds to exactly one of my variables.<br>
	 * <b>Pre:</b> <code>vargraph.isGraph()</code>.<br>
	 * <b>Post:</b> <code>getDomain() == null</code>.<br>
	 * 
	 * @param vargraph
	 *            The graph whose elements correspond to the variables of the
	 *            CSP.
	 * @param ac
	 *            The attribute context to map attributes in.
	 *            @see agg.attribute.AttrMapping
	 * @param injective
	 *            If <code>true</code>, then only injective solutions will
	 *            be considered.
	 * @param randomizeDomainOfVariable
	 * 				If <code>true</code>, then do randomize the object domain of each CSP variable 
	 */
	public ALR_CSP(final Graph vargraph, 
					final AttrContext ac,
					boolean injective, 
					boolean randomizeDomainOfVariable) {
		
		super(new agg.util.csp.Solution_Backjump(injective));
		
		this.itsAttrContext = ac;
		this.randomizedDomain = randomizeDomainOfVariable;
//		this.withNTI = vargraph.getTypeSet().hasInheritance();
		
		this.directed = vargraph.getTypeSet().isArcDirected();
		buildConstraintGraph(vargraph);
	}

	/**
	 * Construct myself to be a CSP where every GraphObject of the specified Graph
	 * <code>vargraph</code> corresponds to exactly one of my variables.<br>
	 * <b>Pre:</b> <code>vargraph.isGraph()</code>.<br>
	 * <b>Post:</b> <code>getDomain() == null</code>.<br>
	 * 
	 * @param vargraph
	 *            The graph whose elements correspond to the variables of the
	 *            CSP.
	 * @param ac
	 *            The attribute context to map attributes in.
	 *            @see agg.attribute.AttrMapping
	 * @param st
	 *            The search solution strategy
	 * @param randomizeDomainOfVariable
	 * 				If <code>true</code>, then do randomize the object domain of each CSP variable 
	 */
	public ALR_CSP(final Graph vargraph, 
					final AttrContext ac,
					final SolutionStrategy st, 
					boolean randomizeDomainOfVariable) {
		super(st);
		
		this.itsAttrContext = ac;
		this.randomizedDomain = randomizeDomainOfVariable;
//		this.withNTI = vargraph.getTypeSet().hasInheritance();
		
		this.directed = vargraph.getTypeSet().isArcDirected();
		buildConstraintGraph(vargraph);
	}

	/**
	 * Construct myself to be a CSP. The CSP variables correspond to 
	 * nodes and edge of the specified sets. 
	 */
	public ALR_CSP(
					final Collection<Node> varnodes, 
					final Collection<Arc> varedges,
					final AttrContext ac, 
					boolean injective, 
					boolean randomizeDomainOfVariable) {
		super(new agg.util.csp.Solution_Backjump(injective));
		this.itsAttrContext = ac;	
		this.randomizedDomain = randomizeDomainOfVariable;
		buildConstraintGraph(varnodes, varedges);
	}

	
	public void clear() {
		this.itsSolver.clear();
		((Hashtable<GraphObject, Variable>) this.itsObjVarMap).clear();
	}
	
	private synchronized final void buildConstraintGraph(
			Collection<Node> varnodes,
			Collection<Arc> varedges) {
		
		// Create a value empty TypeMap for all specified nodes and edges.
		// Edges without source / target are not taken in account.

		// iterate over the nodes and create variables for them
		while (varnodes.iterator().hasNext()) {
			final Node anObj = varnodes.iterator().next();
			final String keystr = anObj.convertToKey();
			// create a hashmap entry for the type
			if (this.itsTypeMap.get(keystr) == null) {
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());
			}
			// create a variable for the current graph object			
			final Variable anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(false);
			
			anObjVar.setKind(0);
			anObjVar.setGraphObject(anObj);

			this.itsObjVarMap.put(anObj, anObjVar);
		}

		// iterate over the edges and create variables for them
		while (varedges.iterator().hasNext()) {
			final Arc anObj = varedges.iterator().next();
			final String keystr = anObj.convertToKey();
			// create a hashmap entry for the type
			if (this.itsTypeMap.get(keystr) == null) {
				// check source type
				String src_keystr = anObj.getSource().convertToKey();
				String tar_keystr = anObj.getTarget().convertToKey();
				if (this.itsTypeMap.get(src_keystr) == null
						|| this.itsTypeMap.get(tar_keystr) == null)
					continue;

				// put edge type
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());
			}
			// create a variable for the current graph object
			final Variable anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(false);
			
			anObjVar.setKind(1);
			anObjVar.setGraphObject(anObj);

			this.itsObjVarMap.put(anObj, anObjVar);
		}

		buildQueriesAndConstraints(this.itsObjVarMap.keys());
	}

	// This is the static part of initialization, i.e. it can be compiled
	// into a graph rule (or the left graph of a rule).
	private synchronized final void buildConstraintGraph(final Graph vargraph) {		
		// Create a value empty TypeMap for all the GraphObject types of
		// the variable graph (LHS of a morphism):

		// iterate over all objects in the vargraph 
		// and create variables for them
		final Iterator<Node> nodes = vargraph.getNodesSet().iterator();
		while (nodes.hasNext()) {
			final GraphObject anObj = nodes.next();
			// create a hashmap entry for every node and arc type
			final String keystr = anObj.convertToKey();
			if (this.itsTypeMap.get(keystr) == null) {
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());
			}
			// create a variable for the current graph object
			final Variable anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(this.randomizedDomain);
			 
			anObjVar.setKind(0);
			anObjVar.setGraphObject(anObj);

			this.itsObjVarMap.put(anObj, anObjVar);
		}
		
		final Iterator <Arc> arcs = vargraph.getArcsSet().iterator();
		while (arcs.hasNext()) {
			final GraphObject anObj = arcs.next();
			// create a hashmap entry for every node and arc type
			final String keystr = anObj.convertToKey();
			if (this.itsTypeMap.get(keystr) == null) {
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());
			}
			// create a variable for the current graph object
			final Variable anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(this.randomizedDomain); 
			
			anObjVar.setKind(1);
			anObjVar.setGraphObject(anObj);
			
			this.itsObjVarMap.put(anObj, anObjVar);
		}
		
		buildQueriesAndConstraints(this.itsObjVarMap.keys());
	}

	
	private void buildQueriesAndConstraints(final Enumeration<GraphObject> anEnum) {
		GraphObject anObj;
		Variable anObjVar, aSrcObjVar, aTarObjVar;

		Query query;
		BinaryConstraint constraint;
		
		while (anEnum.hasMoreElements()) {
			anObj = anEnum.nextElement();
			anObjVar = this.itsObjVarMap.get(anObj);

			// create queries for the current variable
//			query = new Query_Type(itsTypeMap.get(anObj.convertToKey()), anObjVar);
			query = new Query_Type(anObjVar);
			((Query_Type)query).setRandomizedDomain(this.randomizedDomain);
			
			constraint = new Constraint_Type(anObj, anObjVar);
			query.setCorrespondent(constraint);
			
			if (anObj.getType().getAttrType() != null
					|| anObj.getType().hasInheritedAttribute())
				new Constraint_Attribute(anObj, anObjVar, 
										this.itsAttrContext,
										AttrTupleManager.getDefaultManager());
			// additional for node
//			if (anObj.isNode()) {
//				// in case of node deletion do create a DanglingPoint constraint
//				if (this.getRequester() instanceof Match
//						&& ((Match)this.getRequester()).getRule().getImage(anObj) == null) {
//					constraint = new Constraint_DanglingPoint(anObj, anObjVar);
//					query.setCorrespondent(constraint);
//				}
//			} 
//			else {
			
			// create queries for source and target nodes of an arc
			if (anObj.isArc()) {
				aSrcObjVar = this.itsObjVarMap.get(((Arc) anObj).getSource());
				aTarObjVar = this.itsObjVarMap.get(((Arc) anObj).getTarget());

				if (this.directed)  {
					// constraint_source
					constraint = new Constraint_Source(aSrcObjVar, anObjVar);
					
					query = new Query_Outgoing(aSrcObjVar, anObjVar);
					query.setCorrespondent(constraint);				
					query = new Query_Source(anObjVar, aSrcObjVar);
					query.setCorrespondent(constraint);
					// constraint_target
					constraint = new Constraint_Target(aTarObjVar, anObjVar);
					
					query = new Query_Incoming(aTarObjVar, anObjVar);
					query.setCorrespondent(constraint);				
					query = new Query_Target(anObjVar, aTarObjVar);
					query.setCorrespondent(constraint);
				}
				else { // undirected arc
					// constraint_source_target
					constraint = new Constraint_SourceTarget(aSrcObjVar, anObjVar);					
					query = new Query_OutgoingIncoming(aSrcObjVar, anObjVar);
					query.setCorrespondent(constraint);				
					query = new Query_SourceTarget(anObjVar, aSrcObjVar);
					query.setCorrespondent(constraint);				
					// constraint_source_target	
					constraint = new Constraint_TargetSource(aTarObjVar, anObjVar);
					query = new Query_IncomingOutgoing(aTarObjVar, anObjVar);
					query.setCorrespondent(constraint);				
					query = new Query_TargetSource(anObjVar, aTarObjVar);
					query.setCorrespondent(constraint);
				}				
			}
		}
		
	}

	protected synchronized final void preprocessDomain(final Object domaingraph) {
		// fillTypeMap((Graph) domaingraph);
		resetTypeMap((Graph) domaingraph);
	}

	protected AttrContext getAttrContext() {
		return this.itsAttrContext;
	}

	public final Enumeration<Variable> getVariables() {
		return this.itsObjVarMap.elements();
	}

	public void enableAllVariables() {
		Enumeration<GraphObject> keys = this.itsObjVarMap.keys();
		while (keys.hasMoreElements()) {
			GraphObject obj = keys.nextElement();
			Variable var = this.itsObjVarMap.get(obj);
			var.setEnabled(true);
		}
	}

	public boolean isDomainOfTypeEmpty(final Type t) {
		Enumeration<GraphObject> keys = this.itsObjVarMap.keys();
		while (keys.hasMoreElements()) {
			GraphObject go = keys.nextElement();
			if (go.isArc())
				continue;
			if (go.getType().compareTo(t)) {
				Variable var = this.itsObjVarMap.get(go);
								
				return !var.hasNext();
			}
		}
		return false;
	}

	public boolean isDomainOfTypeEmpty(final Type t, 
										final Type src,
										final Type tar) {
		Enumeration<GraphObject> keys = this.itsObjVarMap.keys();
		while (keys.hasMoreElements()) {
			GraphObject go = keys.nextElement();
			if (go.isNode())
				continue;
			if (go.getType().compareTo(t)
					&& ((Arc) go).getSource().getType().compareTo(src)
					&& ((Arc) go).getTarget().getType().compareTo(tar)) {
				Variable var = this.itsObjVarMap.get(go);
								
				return !var.hasNext();
			}
		}
		return false;
	}

	public void setRelatedInstanceVarMap(
			final Dictionary<Object, Variable> relatedVarMap) {
		this.itsSolver.setRelatedInstanceVarMap(relatedVarMap);
	}

	public Dictionary<Object, Variable> getInstanceVarMap() {
		return this.itsSolver.getInstanceVarMap();
	}

	public final int getSize() {
		return this.itsObjVarMap.size();
	}

	public final Variable getVariable(final GraphObject obj) {
		return this.itsObjVarMap.get(obj);
	}

	/**
	 * An additional object name constraint will be added for the CSP variable
	 * of the given GraphObject anObj. This constraint requires equality of the object names.  
	 */
	public void addObjectNameConstraint(GraphObject anObj) {
		Variable anObjVar = this.itsObjVarMap.get(anObj);
		if (anObjVar != null)
			new Constraint_ObjectName(anObj, anObjVar);
	}
	
	/**
	 * Removes the object name constraint for the CSP variable
	 * of the given GraphObject anObj.
	 */
	public void removeObjectNameConstraint(GraphObject anObj) {
		Variable anObjVar = this.itsObjVarMap.get(anObj);
		if (anObjVar != null) {
			Enumeration<?> cons = anObjVar.getConstraints();
			while (cons.hasMoreElements()) {
				Object c = cons.nextElement();
				if (c instanceof Constraint_ObjectName) {
					anObjVar.removeConstraint((Constraint_ObjectName) c);
				}
			}
		}
	}
	
	// This is dynamic, i.e. can only be done when the domain graph is known.
	// Not more in use.
	protected void fillTypeMap(final Graph domaingraph) {
		// handle nodes
		Iterator<?> anEnum = domaingraph.getNodesSet().iterator();
		while (anEnum.hasNext()) {
			Node anObj = (Node) anEnum.next();
			String keystr = anObj.convertToKey();
			
			if (anObj.getType().hasParent()) {				
				Vector<Type> myParents = anObj.getType().getAllParents();
				if (myParents != null) {
					for (int i = 0; i < myParents.size(); ++i) {						
						keystr = myParents.get(i).convertToKey();
						if (this.itsTypeMap.get(keystr) != null) {
							HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
							anObjVec.add(anObj);
						}
					}
				}
			} else if (this.itsTypeMap.get(keystr) != null) {
				HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
				anObjVec.add(anObj);
			} 
		}
		// handle arcs
		anEnum = domaingraph.getArcsSet().iterator();
		while (anEnum.hasNext()) {
			Arc anObj = (Arc) anEnum.next();
			String keystr = anObj.convertToKey();
			
			if (anObj.getSource().getType().hasParent()
					|| anObj.getTarget().getType().hasParent()) {
				Vector<Type> mySrcParents = anObj.getSource().getType().getAllParents();
				Vector<Type> myTarParents = anObj.getTarget().getType().getAllParents();
	
				for (int i = 0; i < mySrcParents.size(); ++i) {
					
					for (int j = 0; j < myTarParents.size(); ++j) {
						
						keystr = mySrcParents.get(i).convertToKey()
								+ anObj.getType().convertToKey()
								+ myTarParents.get(j).convertToKey();
						if (this.itsTypeMap.get(keystr) != null) {
							HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
							anObjVec.add(anObj);
						}
					}
				}
			} else if (this.itsTypeMap.get(keystr) != null) {
				HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
				anObjVec.add(anObj);
			} 
		}
	}

	public void removeFromObjectVarMap(final GraphObject anObj) {
		if (anObj.isNode()) {			
			Iterator<?> iter = ((Node)anObj).getIncomingArcs();
			while (iter.hasNext()) {
				removeFromObjectVarMap((Arc)iter.next());
			}
			iter = ((Node)anObj).getOutgoingArcs();
			while (iter.hasNext()) {
				removeFromObjectVarMap((Arc)iter.next());
			}
		}
		
		Variable v = this.itsObjVarMap.get(anObj);
		if (v != null) {
			v.setInstance(null);
			v.clear();
			this.itsObjVarMap.remove(anObj);
		}
	}
	
	protected void removeFromTypeObjectsMap(GraphObject anObj) {
		if (anObj.isNode()) {
			if (anObj.getType().hasParent()) {
				Vector<Type> myParents = anObj.getType().getAllParents();
				if (myParents != null) {
					for (int i = 0; i < myParents.size(); ++i) {
						String keystr = myParents.get(i).convertToKey();
						HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
						if (anObjVec != null) {
							anObjVec.remove(anObj);
						}
					}
				}
			} else {
				HashSet<GraphObject> anObjVec = this.itsTypeMap.get(anObj.getType().convertToKey());
				if (anObjVec != null) {
					anObjVec.remove(anObj);
				}
			}
			
		} else {
			if (((Arc) anObj).getSource().getType().hasParent()
					|| ((Arc) anObj).getTarget().getType().hasParent()) {
				
				Vector<Type> mySrcParents = ((Arc) anObj).getSource().getType().getAllParents();
				Vector<Type> myTarParents = ((Arc) anObj).getTarget().getType().getAllParents();
				
				for (int i = 0; i < mySrcParents.size(); ++i) {					
					for (int j = 0; j < myTarParents.size(); ++j) {						
						String keystr = mySrcParents.get(i).convertToKey()
								+ anObj.getType().convertToKey()
								+ myTarParents.get(j).convertToKey();
						HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
						if (anObjVec != null) {
							anObjVec.remove(anObj);
						}
					}
				}
				
			} else {
				HashSet<GraphObject> anObjVec = this.itsTypeMap.get(anObj.getType().convertToKey());
				if (anObjVec != null) {
					anObjVec.remove(anObj);
				}
			}
		}
	}

	protected void resetTypeMap(final Graph g) {
		final Enumeration<GraphObject> lhsObjs = this.itsObjVarMap.keys();
		while (lhsObjs.hasMoreElements()) {
			final GraphObject obj = lhsObjs.nextElement();
			final Variable var = this.itsObjVarMap.get(obj);
			final String key = obj.convertToKey();
			HashSet<GraphObject> list = g.getTypeObjectsMap().get(key);
			if (list == null) {
				list = new LinkedHashSet<GraphObject>();
				g.getTypeObjectsMap().put(key, list);
			}

			this.itsTypeMap.put(key, list);	
			
			var.getTypeQuery().setObjects(list);			
		}
	}

	protected void resetTypeMap(
			final Hashtable<String, HashSet<GraphObject>> aTypeMap) {
		
		final Enumeration<GraphObject> lhsObjs = this.itsObjVarMap.keys();
		while (lhsObjs.hasMoreElements()) {
			final GraphObject obj = lhsObjs.nextElement();
			final Variable var = this.itsObjVarMap.get(obj);
			final String key = obj.convertToKey();
			HashSet<GraphObject> list = aTypeMap.get(key);
			if (list == null) {
				list = new LinkedHashSet<GraphObject>();
				aTypeMap.put(key, list);
			}

			this.itsTypeMap.put(key, list);
			
			var.getTypeQuery().setObjects(list);
		}
	}	
	
	protected void reinitializeSolver(boolean doUpdateQueries) {
		this.itsSolver.reinitialize(doUpdateQueries);
	}

	protected void resetSolver(boolean doUpdateQueries) {
		resetSolverVariables();
		this.itsSolver.reinitialize(doUpdateQueries);
	}

	protected void resetSolverVariables() {
		final Enumeration<Variable> cspVars = this.itsObjVarMap.elements();
		while (cspVars.hasMoreElements()) { 
			cspVars.nextElement().setInstance(null);
		}
	}
	
	
	protected void resetVariableDomain(boolean resetByNull) {
		if (resetByNull) {
			final Enumeration<Variable> cspVars = this.itsObjVarMap.elements();
			while (cspVars.hasMoreElements()) {
				cspVars.nextElement().setInstance(null);
			}
		}
		
		unsetAttrContextVariable();
	}

	protected void resetVariableDomain(final GraphObject go) {
		final Variable var = this.itsObjVarMap.get(go);
		if (var != null) {
			this.itsSolver.reinitialize(var);
			var.setInstance(null);
			unsetAttrContextVariable(go);
		}
	}
	
	protected void unsetAttrContextVariable() {
		if (this.itsSolver.hasQueries()) {
			final VarTuple varTuple = (VarTuple) this.itsAttrContext.getVariables();
			for (int i = 0; i < varTuple.getSize(); i++) {
				final VarMember vm = varTuple.getVarMemberAt(i);
				if (vm != null)
					vm.setExpr(null);
			}
		}
	}

	protected void unsetAttrContextVariable(final GraphObject go) {
		if (go.getAttribute() != null
				&& this.itsSolver.hasQueries()) {
			final Vector<String> attrVars = ((ValueTuple) go.getAttribute())
					.getAllVariableNames();
			final VarTuple varTup = (VarTuple) this.itsAttrContext.getVariables();
			for (int i = 0; i < attrVars.size(); i++) {
				final String name = attrVars.elementAt(i);
				final VarMember vm = varTup.getVarMemberAt(name);
				if (vm != null)
					vm.setExpr(null);
			}
		}
	}



}
