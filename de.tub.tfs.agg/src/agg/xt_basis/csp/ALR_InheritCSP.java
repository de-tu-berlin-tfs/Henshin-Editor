package agg.xt_basis.csp;

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
import agg.util.csp.Solution_Backjump;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Type;

/**
 * A CSP whose solutions represent morphisms between two graphs.
 * 
 *  Please note: This class is only for internal use of the 
 * critical pair analysis for grammars with node type inheritance.
 * Do not use it for any kind of implementations.
 *
 * @see agg.util.csp.CSP
 */
public class ALR_InheritCSP extends CSP {

	private AttrContext itsAttrContext;

	private boolean directed = true;
	
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
	final private Dictionary<String, HashSet<GraphObject>> 
	itsTypeMap = new Hashtable<String, HashSet<GraphObject>>();

	/**
	 * Construct myself to be a CSP where every GraphObject of
	 * <code>vargraph</code> corresponds to exactly one of my variables.
	 * <p>
	 * <b>Pre:</b> <code>vargraph.isGraph()</code>.
	 * <p>
	 * <b>Post:</b> <code>getDomain() == null</code>.
	 * 
	 * @param vargraph
	 *            The ALR graph whose elements represent the variables of the
	 *            CSP.
	 * @param ac
	 *            The attribute context to map attributes in.
	 * @param injective
	 *            If set to <code>true</code>, only injective solutions will
	 *            be considered.
	 */
	// * @param mapstyle The MapStyle of the AttrContext which belongs to
	// * the morphism to be completed. One of
	// * <code>AttrMapping.MATCH_MAP</code> or
	// * <code>AttrMapping.PLAIN_MAP</code>.
	// * @see agg.attribute.AttrMapping */
	/*
	public ALR_InheritCSP(final Graph vargraph, final AttrContext ac, boolean injective) {
		super(injective ? new Solution_InjBackjump() : new Solution_Backjump());
		itsAttrContext = ac;
		buildConstraintGraph(vargraph);
	}
*/
	
	/**
	 * Construct myself to be a CSP where every GraphObject of
	 * <code>vargraph</code> corresponds to exactly one of my variables.
	 * <p>
	 * <b>Pre:</b> <code>vargraph.isGraph()</code>.
	 * <p>
	 * <b>Post:</b> <code>getDomain() == null</code>.
	 * 
	 * @param vargraph
	 *            The ALR graph whose elements represent the variables of the
	 *            CSP.
	 * @param ac
	 *            The attribute context to map attributes in.
	 */
	public ALR_InheritCSP(final Graph vargraph, final AttrContext ac) {
		super(new Solution_Backjump(true));
		this.itsAttrContext = ac;
		this.directed = vargraph.getTypeSet().isArcDirected();
		buildConstraintGraph(vargraph);
	}
	
	public void clear() {
		this.itsSolver.clear();
		((Hashtable<GraphObject, Variable>) this.itsObjVarMap).clear();
	}
	
	// This is the static part of initialization, i.e. it can be compiled
	// into a graph rule (or the left graph of a rule).
	private synchronized final void buildConstraintGraph(final Graph vargraph) {
		// Create an empty TypeMap for all the GraphObject types of
		// the variable graph (LHS of a morphism):
		GraphObject anObj;
		Variable anObjVar;

		// iterate over all objects in the vargraph and create variables for
		// them
		Iterator<Node> nodes = vargraph.getNodesSet().iterator();
		while (nodes.hasNext()) {
			anObj = nodes.next();
			// create a hashmap entry for every node and arc type
			String keystr = anObj.convertToKey();
			if (this.itsTypeMap.get(keystr) == null)
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());

			// create a variable for the current graph object
			anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(false);
			
			anObjVar.setKind(0);
			anObjVar.setGraphObject(anObj);

			this.itsObjVarMap.put(anObj, anObjVar);
		}
		
		Iterator<Arc> arcs = vargraph.getArcsSet().iterator();
		while (arcs.hasNext()) {
			anObj = arcs.next();
			// create a hashmap entry for every node and arc type
			String keystr = anObj.convertToKey();
			if (this.itsTypeMap.get(keystr) == null)
				this.itsTypeMap.put(keystr, new LinkedHashSet<GraphObject>());

			// create a variable for the current graph object
			anObjVar = new Variable();
//			anObjVar.setRandomizedDomain(false);
			
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
			
			constraint = new Constraint_InheritType(anObj, anObjVar);
			query.setCorrespondent(constraint);
			
			if (anObj.getType().getAttrType() != null
					|| anObj.getType().hasInheritedAttribute())
				new Constraint_InheritAttribute(anObj, anObjVar, this.itsAttrContext,
						AttrTupleManager.getDefaultManager());

			// create queries for source and target nodes if current object is
			// an arc
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
				else {
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

//	 CSP
	protected synchronized final void preprocessDomain(final Object domaingraph) {
		// fillTypeMap((Graph) domaingraph);
		resetTypeMap((Graph) domaingraph);
		// showTypeMap(itsTypeMap);
	}

	public AttrContext getAttrContext() {
		return this.itsAttrContext;
	}

//	 CSP
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

	public boolean isDomainOfTypeEmpty(final Type t, final Type src,
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

	// CSP
	public final int getSize() {
		return this.itsObjVarMap.size();
	}

	// CSP
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
	// not more in use
	protected void fillTypeMap(final Graph domaingraph) {
		// handle nodes
		Iterator<?> anEnum = domaingraph.getNodesSet().iterator();
		while (anEnum.hasNext()) {
			Node anObj = (Node) anEnum.next();
			String keystr = anObj.convertToKey();
			if (anObj.getType().hasParent()) {
				Vector<Type> myParents = anObj.getType().getAllParents();
				for (int i = 0; i < myParents.size(); ++i) {
					Type anObjType = myParents.get(i);
					keystr = anObjType.convertToKey();
					if (this.itsTypeMap.get(keystr) != null) {
						HashSet<GraphObject> anObjVec = this.itsTypeMap.get(keystr);
						anObjVec.add(anObj);
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
			Arc anObj = (Arc)anEnum.next();
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

	protected void resetTypeMap(final Graph g) {
		Enumeration<GraphObject> lhsObjs = this.itsObjVarMap.keys();
		while (lhsObjs.hasMoreElements()) {
			GraphObject lhsobj = lhsObjs.nextElement();
			Variable var = this.itsObjVarMap.get(lhsobj);
			
			String key = lhsobj.convertToKey();
			if (g.getTypeObjectsMap().get(key) == null) {
				final HashSet<GraphObject> v = new LinkedHashSet<GraphObject>();
				if (lhsobj.isNode()) {
					Vector<Type> parents = lhsobj.getType().getAllParents();
					for (int p = 1; p < parents.size(); p++) {
						Type pt = parents.get(p);
						Vector<GraphObject> vp = g.getElementsOfTypeAsVector(pt);
						for (int i=0; i<vp.size(); i++) {
							if (!v.contains(vp.get(i)))
								v.add(vp.get(i));
						}					
					}
				} else { // lhsobj.isArc()
					GraphObject src = ((Arc)lhsobj).getSource();
					GraphObject tar = ((Arc)lhsobj).getTarget();
					
					Vector<Type> src_parents = src.getType().getAllParents();
					Vector<Type> tar_parents = tar.getType().getAllParents();
					for (int i = 0; i < src_parents.size(); i++) {
						Type srcp = src_parents.get(i);
						Vector<GraphObject> vsrcp = g.getElementsOfTypeAsVector(lhsobj.getType(), srcp, tar.getType());
						for (int k=0; k<vsrcp.size(); k++) {
							if (!v.contains(vsrcp.get(k)))
								v.add(vsrcp.get(k));
						}

						for (int j = 0; j<tar_parents.size(); j++) {
							Type tarp = tar_parents.get(j);
							Vector<GraphObject> vtarp = g.getElementsOfTypeAsVector(lhsobj.getType(), srcp, tarp);
							for (int l=0; l<vtarp.size(); l++) {
								if (!v.contains(vtarp.get(l)))
									v.add(vtarp.get(l));
							}							
						}
					}
				}
				g.getTypeObjectsMap().put(key, v);
			}
			
			this.itsTypeMap.put(key, g.getTypeObjectsMap().get(key));
			var.getTypeQuery().setObjects(g.getTypeObjectsMap().get(key));
		}
	}

	protected void resetTypeMap(final Hashtable<String, HashSet<GraphObject>> aTypeMap) {
		Enumeration<GraphObject> lhsObjs = this.itsObjVarMap.keys();
		while (lhsObjs.hasMoreElements()) {
			GraphObject obj = lhsObjs.nextElement();
			Variable var = this.itsObjVarMap.get(obj);
			String key = obj.convertToKey();
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
		Enumeration<GraphObject> lhsObjs = this.itsObjVarMap.keys();
		while (lhsObjs.hasMoreElements()) {
			GraphObject obj = lhsObjs.nextElement();
			Variable var = this.itsObjVarMap.get(obj);
			var.setInstance(null);
		}
	}


	protected void resetVariableDomain(boolean resetByNull) {
		if (resetByNull) {
			final Enumeration<Variable> cspVars = this.itsObjVarMap.elements();
			while (cspVars.hasMoreElements()) {			 
				cspVars.nextElement().setInstance(null);
			}
		}
		
//		Enumeration<GraphObject> keys = itsObjVarMap.keys();
//		while (keys.hasMoreElements()) {
//			GraphObject obj = keys.nextElement();
//			Variable var = itsObjVarMap.get(obj);
//			if (!var.isEnabled()) {
//				continue;
//			}
////			var.setDomainEnum(getTypeQuerySet(obj));
//			if (instanceToNull) {
//				var.setInstance(null);
//			}
//		}

		unsetAttrContextVariable();
	}

	protected void resetVariableDomain(final GraphObject go) {
		Enumeration<GraphObject> keys = this.itsObjVarMap.keys();
		while (keys.hasMoreElements()) {
			GraphObject obj = keys.nextElement();
			if (obj == go) {
				Variable var = this.itsObjVarMap.get(obj);
				// String key = convertToKey(obj);
				// reset domain of a variable
				this.itsSolver.reinitialize(var);
//				var.setDomainEnum(itsTypeMap.get(go.convertToKey()));
				var.setInstance(null);
				return;
			}
		}

		unsetAttrContextVariable(go);
	}

	protected void unsetAttrContextVariable() {
		// System.out.println("ALR_CSP.unsetAttrContextVariable...
		// itsAttrContext: "+itsAttrContext.hashCode());
		VarTuple varTuple = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < varTuple.getSize(); i++) {
			VarMember vm = varTuple.getVarMemberAt(i);
			if (vm != null)
				vm.setExpr(null);
		}
	}

	protected void unsetAttrContextVariable(final GraphObject go) {
		if (go.getAttribute() == null)
			return;

		Vector<String> attrVars = ((ValueTuple) go.getAttribute())
				.getAllVariableNames();
		VarTuple varTup = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < attrVars.size(); i++) {
			String name = attrVars.elementAt(i);
			VarMember vm = varTup.getVarMemberAt(name);
			if (vm != null)
				vm.setExpr(null);
		}
	}



}
