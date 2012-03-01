package agg.xt_basis.csp;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Dictionary;

import agg.attribute.AttrContext;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.util.csp.SolutionStrategy;
import agg.util.csp.Solution_Backjump;
import agg.util.csp.Variable;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Type;

/**
 * An implementation of morphism completion as a Constraint Satisfaction Problem
 * (CSP).
 * 
 * Please note: This class is only for internal use of the 
 * critical pair analysis for grammars with node type inheritance.
 * Do not use it for any kind of implementations.
 */
public class Completion_InheritCSP extends MorphCompletionStrategy {
	private ALR_InheritCSP itsCSP;

	private Morphism itsMorph;

	private Dictionary<Object, Variable> relatedVarMap;

	// private boolean allowRestrictedDomain;
	private HashMap<String, String> 
	mapInputParameter = new HashMap<String, String>(1);

	private String errorMsg;

	private static final BitSet itsSupportedProperties = new BitSet(6);
	static {
		itsSupportedProperties.set(CompletionPropertyBits.INJECTIVE);
		itsSupportedProperties.set(CompletionPropertyBits.DANGLING);
		itsSupportedProperties.set(CompletionPropertyBits.IDENTIFICATION);
	}

	public Completion_InheritCSP() {
		super(itsSupportedProperties);
		this.randomDomain = true;
		super.itsName = "CSP NTI"; // Node Type Inheritance
	}
	
	public void dispose() {
		super.dispose();
		itsSupportedProperties.clear();
		this.mapInputParameter.clear();
		this.relatedVarMap = null;
		this.itsMorph = null;
		this.itsCSP = null;
	}
	
	public void setProperties(MorphCompletionStrategy fromStrategy) {
		initialize(itsSupportedProperties, 
					(BitSet) fromStrategy.getProperties().clone());
	}

	/**
	 * Initialize the CSP by the specified morphism. The CSP variables are
	 * created for each node and edge of the source graph of this morphism.
	 */
	public final void initialize(OrdinaryMorphism morph)
			throws BadMappingException {
		this.itsMorph = morph;
		AttrContext aContext = initializeAttrContext(morph);

		// create CSP
		this.itsCSP = new ALR_InheritCSP(morph.getOriginal(), aContext);
		
		if (morph.getImage().getTypeObjectsMap().isEmpty())
			morph.getImage().fillTypeObjectsMap();

		this.itsCSP.setRequester(morph);
		this.itsCSP.setDomain(morph.getImage());
	}
	

	private AttrContext initializeAttrContext(OrdinaryMorphism morph) {
		if (morph instanceof Match) {
			return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.MATCH_MAP,
					((Match) morph).getRule().getAttrContext());
		} 
		return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.PLAIN_MAP);
	}

	/*
	private void unsetUsedVariable(GraphObject go, OrdinaryMorphism morph) {
		if (go.getAttribute() == null)
			return;
		Vector<String> attrVars = ((ValueTuple) go.getAttribute())
				.getAllVariableNames();
		VarTuple varTup = (VarTuple) morph.getAttrContext().getVariables();
		for (int i = 0; i < attrVars.size(); i++) {
			String name = attrVars.elementAt(i);
			VarMember vm = varTup.getVarMemberAt(name);
			if (vm != null)
				vm.setExpr(null);
		}
	}
*/
	
	/**
	 * Template method to enable creation of CSPs with varying solution
	 * strategies by subclasses.
	 */
	protected SolutionStrategy createSolutionStrategy() {
		return new Solution_Backjump(getProperties().get(CompletionPropertyBits.INJECTIVE));
	}

	public final void reset() {
		if (this.itsCSP != null)
			this.itsCSP.reset();
	}

	public void resetSolverQuery_Type() {
		if (this.itsCSP != null)
			this.itsCSP.resetQuery_Type();
	}
	
	public void enableParallelSearch(boolean b) {
		this.parallel = b;
		if (this.itsCSP != null)
			this.itsCSP.getSolutionSolver().enableParallelSearch(b);
	}
	
	public void setStartParallelSearchByFirst(boolean b) {
		this.startParallelMatchByFirstCSPVar = b;
		if (this.itsCSP != null) {
			this.itsCSP.getSolutionSolver().setStartParallelSearchByFirst(b);
		}
	}
	
	public AttrContext getAttrContext() {
		return (this.itsCSP != null)? this.itsCSP.getAttrContext(): null;
	}

	public void resetSolver(boolean doUpdateQueries) {
		if (this.itsCSP != null)
			this.itsCSP.resetSolver(doUpdateQueries);
	}

	public void reinitializeSolver(boolean doUpdateQueries) {
		if (this.itsCSP != null)
			this.itsCSP.reinitializeSolver(doUpdateQueries);
	}

	public void resetSolverVariables() {
		if (this.itsCSP != null)
			this.itsCSP.resetSolverVariables();
	}

	public boolean isDomainOfTypeEmpty(Type t) {
		return this.itsCSP.isDomainOfTypeEmpty(t);
	}

	public boolean isDomainOfTypeEmpty(Type t, Type src, Type tar) {
		return this.itsCSP.isDomainOfTypeEmpty(t, src, tar);
	}

	public void setRelatedInstanceVarMap(
			Dictionary<Object, Variable> relVarMap) {
		this.relatedVarMap = relVarMap;
	}

	public boolean hasRelatedInstanceVarMap() {
		if (this.relatedVarMap != null)
			return true;
		
		return false;
	}

	public Dictionary<Object, Variable> getInstanceVarMap() {
		if (this.itsCSP != null)
			return this.itsCSP.getInstanceVarMap();
		
		return null;
	}

	public void resetTypeMap(Hashtable<String, HashSet<GraphObject>> typeMap) {
		if (this.itsCSP != null)
			this.itsCSP.resetTypeMap(typeMap);
	}

	public void resetTypeMap(Graph g) {
		if (this.itsCSP != null)
			this.itsCSP.resetTypeMap(g);
	}

	public void resetVariableDomain(boolean initInstanceByNull) {
		if (this.itsCSP != null) {
			this.itsCSP.resetVariableDomain(initInstanceByNull);
		}
	}

	public void resetVariableDomain(GraphObject go) {
		if (this.itsCSP != null)
			this.itsCSP.resetVariableDomain(go);
	}

	protected void unsetAttrContextVariable() {
		this.itsCSP.unsetAttrContextVariable();
	}

	public final boolean next(OrdinaryMorphism morph) {
		if (morph != this.itsMorph) {
			try {
				initialize(morph);
			} catch (BadMappingException ex) {
				return false;
			}
		}

		return doNext((OrdinaryMorphism) this.itsMorph);
	}

	private boolean doNext(OrdinaryMorphism morph) {
		this.itsCSP.setRelatedInstanceVarMap(this.relatedVarMap);
		boolean flag = false;
		this.errorMsg = "";

		if (morph.getAttrContext() != null) {
			((VarTuple) morph.getAttrContext().getVariables())
									.unsetNotInputVariables();
		}
		
		while (this.itsCSP.nextSolution()) {
			flag = true;
			this.errorMsg = "";
			// try add morph. mapping after CSP success
			GraphObject anOrig, anImage;
			Iterator<Node> anNodeIter = morph.getOriginal().getNodesSet().iterator();
			while (flag && anNodeIter.hasNext()) {
				anOrig = anNodeIter.next();
				Variable lhsVariable = this.itsCSP.getVariable(anOrig);
				if (lhsVariable != null) {
					anImage = (GraphObject) lhsVariable.getInstance();
					if (anImage != null) {
						try {
							morph.addChild2ParentMapping(anOrig, anImage);
							if (morph.getImage(anOrig) == null) {
								flag = false;
							}
						} catch (BadMappingException ex) {
							flag = false;
						}
					}
				}
			}
			Iterator<Arc> anArcIter = morph.getOriginal().getArcsSet().iterator();
			while (flag && anArcIter.hasNext()) {
				anOrig = anArcIter.next();
				Variable lhsVariable = this.itsCSP.getVariable(anOrig);
				if (lhsVariable != null) {
					anImage = (GraphObject) lhsVariable.getInstance();
					if (anImage != null) {
						try {
							morph.addChild2ParentMapping(anOrig, anImage);
							if (morph.getImage(anOrig) == null) {
								flag = false;
							}
						} catch (BadMappingException ex) {
							flag = false;
						}
					}
				}
			}
			if (!flag)
				continue;

			if (morph.getAttrContext() != null) {
				flag = flag && checkObjectsWithSameVariable(morph);
			}
			
			if (flag) {
				break;
			} 
			morph.addErrorMsg(this.errorMsg);
		}
		return flag;
	}


	private boolean checkObjectsWithSameVariable(OrdinaryMorphism morph) {
		VarTuple variables = (VarTuple) morph.getAttrContext().getVariables();
		for (int i = 0; i < variables.getSize(); i++) {
			VarMember var = variables.getVarMemberAt(i);
			Vector<Pair<GraphObject, String>> v = new Vector<Pair<GraphObject, String>>();
			Iterator<?> iter = morph.getOriginal().getNodesSet().iterator();
			while (iter.hasNext()) {
				GraphObject orig = (GraphObject) iter.next();
				if (orig.getAttribute() == null)
					continue;
				ValueTuple origVal = (ValueTuple) orig.getAttribute();
				for (int k = 0; k < origVal.getSize(); k++) {
					ValueMember mem = origVal.getValueMemberAt(k);
					if (mem.isSet()
							&& mem.getExpr().isVariable()
							&& mem.getExprAsText().equals(var.getName())
							&& mem.getDeclaration().getTypeName().equals(
									var.getDeclaration().getTypeName())) {
						v.add(new Pair<GraphObject, String>(
										orig, mem.getName()));
					}
				}
			}
			iter = morph.getOriginal().getArcsSet().iterator();
			while (iter.hasNext()) {
				GraphObject orig = (GraphObject) iter.next();
				if (orig.getAttribute() == null)
					continue;
				ValueTuple origVal = (ValueTuple) orig.getAttribute();
				for (int k = 0; k < origVal.getSize(); k++) {
					ValueMember mem = origVal.getValueMemberAt(k);
					if (mem.isSet()
							&& mem.getExpr().isVariable()
							&& mem.getExprAsText().equals(var.getName())
							&& mem.getDeclaration().getTypeName().equals(
									var.getDeclaration().getTypeName())) {
						v.add(new Pair<GraphObject, String>(
										orig, mem.getName()));
					}
				}
			}

			if (v.size() > 1) {
				Pair<GraphObject, String> p = v.elementAt(0);
				GraphObject img = morph.getImage(p.first);
//				if (img == null) {
//					System.out.println(img+"   "+p.first.getType().getName());
//				}
				ValueTuple val = (ValueTuple) img.getAttribute();
				ValueMember mem = val.getValueMemberAt(p.second);
				for (int j = 1; j < v.size(); j++) {
					Pair<GraphObject, String> pj = v.elementAt(j);
					GraphObject imgj = morph.getImage(pj.first);
					ValueTuple valj = (ValueTuple) imgj.getAttribute();
					ValueMember memj = valj.getValueMemberAt(pj.second);
					if (mem.isSet() && memj.isSet()) {
						if (mem.getExpr().isConstant() 
								&& memj.getExpr().isConstant()) {
							if (!mem.getExprAsText().equals(memj.getExprAsText())) {
								this.errorMsg = "Attribute check: equal value by equal variable - failed!.";
								return false;
							}
						} else if (mem.getExpr().isVariable()
								&& memj.getExpr().isVariable()) {
							if (!mem.getExprAsText().equals(
									memj.getExprAsText())) {
								this.errorMsg = "Attribute check: equal value by equal variable - failed!.";
								return false;
							}
						} else {
							this.errorMsg = "Attribute check: equal value by equal variable - failed!";
							return false;
						}						
					}
				}
			}
		}
		return true;
	}


}
