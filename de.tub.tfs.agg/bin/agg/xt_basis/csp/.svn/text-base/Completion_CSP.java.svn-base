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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Dictionary;

import agg.attribute.AttrContext;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.AttrVariableTuple;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.CondMember;
import agg.util.csp.SolutionStrategy;
import agg.util.csp.Variable;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Morphism;
import agg.xt_basis.NACStarMorphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.PACStarMorphism;
import agg.xt_basis.Type;

/**
 * An implementation of morphism completion as a Constraint Satisfaction Problem (CSP).
 */
public class Completion_CSP extends MorphCompletionStrategy {
	
	protected static final BitSet itsSupportedProperties = new BitSet(6);
	static {
		itsSupportedProperties.set(CompletionPropertyBits.INJECTIVE);
		itsSupportedProperties.set(CompletionPropertyBits.DANGLING);
		itsSupportedProperties.set(CompletionPropertyBits.IDENTIFICATION);
	}
	
	protected ALR_CSP itsCSP;
	
	protected Morphism itsMorph;

	protected Dictionary<Object, Variable> relatedVarMap;

	final protected HashMap<String, String> 
	inputParameterMap = new HashMap<String, String>(1);

	final protected List<VarMember> 
	disabledInputParameter = new Vector<VarMember>(2);
	
	protected String errorMsg;


	public Completion_CSP() {
		super(itsSupportedProperties);
		this.itsName = "CSP";
	}

	public Completion_CSP(boolean randomizeDomain) {
		super(itsSupportedProperties);
		this.randomDomain = randomizeDomain;
		this.itsName = "CSP";
	}
	
	public void setProperties(MorphCompletionStrategy fromStrategy) {
		initialize(itsSupportedProperties, 
					(BitSet) fromStrategy.getProperties().clone());
	}

	public void clear() {
		if (this.itsCSP != null) {
			this.itsCSP.clear();	
		}
	}
	
	public void dispose() {
		if (this.itsCSP != null) {
			this.itsCSP.resetSolverVariables();
			this.itsCSP.clear();
		}
		this.relatedVarMap = null;
		this.itsMorph = null;
	}
	
	
	/**
	 * Initialize the CSP by the specified morphism. The CSP variables are
	 * created for each node and edge of the source graph of the given morphism.
	 * Initialize the CSP variables (partially) according mappings of the given morphism. 
	 */
	public void initialize(OrdinaryMorphism morph)
			throws BadMappingException {
		this.itsMorph = morph;
		
		AttrContext aContext = initializeAttrContext(morph);

		// create CSP
		this.itsCSP = new ALR_CSP(
				morph.getOriginal(), 
				aContext,
				this.createSolutionStrategy(getProperties().get(CompletionPropertyBits.INJECTIVE)), 
				this.randomDomain);
		
		if (morph.getImage().getTypeObjectsMap().isEmpty())
			morph.getImage().fillTypeObjectsMap();

		this.itsCSP.setRequester(morph);
		this.itsCSP.setDomain(morph.getImage());
		
		this.inputParameterMap.clear();		
		this.disabledInputParameter.clear();
		
		this.itsCSP.getSolutionSolver().enableParallelSearch(this.parallel);
		this.itsCSP.getSolutionSolver().setStartParallelSearchByFirst(this.startParallelMatchByFirstCSPVar);
		
		// initialize CSP variables (partially) according mappings of the morphism 
		setPartialMorphism(morph);
	}
	
	/**
	 * Initialize the CSP by the specified morphism. The CSP variables are
	 * created for nodes and edge of the specified sets of nodes and edges, only.
	 * Initialize the CSP variables (partially) according mappings of the given morphism.
	 */
	public void initialize(final OrdinaryMorphism morph, 
			Collection<Node> nodes,
			Collection<Arc> edges) 
	throws BadMappingException {
		this.itsMorph = morph;
		AttrContext aContext = initializeAttrContext(morph);
		
		// create CSP
		if (nodes != null && edges != null) {
//			 new : only injective mapping allowed
			this.itsCSP = new ALR_CSP(nodes, edges, aContext, true, this.randomDomain);
		} else {			
			this.itsCSP = new ALR_CSP(
					morph.getOriginal(), 
					aContext,
					this.createSolutionStrategy(getProperties().get(CompletionPropertyBits.INJECTIVE)), 
					this.randomDomain);
		}

		if (morph.getImage().getTypeObjectsMap().isEmpty()) {
			morph.getImage().fillTypeObjectsMap();
		}
		
		this.itsCSP.setRequester(morph);
		this.itsCSP.setDomain(morph.getImage());
		
		this.inputParameterMap.clear();		
		this.disabledInputParameter.clear();
		
		this.itsCSP.getSolutionSolver().enableParallelSearch(this.parallel);
		this.itsCSP.getSolutionSolver().setStartParallelSearchByFirst(this.startParallelMatchByFirstCSPVar);
		
		// represent the mappings of a partial morphism in the CSP:
		setPartialMorphism(morph);
	}

	protected AttrContext initializeAttrContext(OrdinaryMorphism morph) {
		if (morph instanceof Match) {
			
			return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.MATCH_MAP,
					((Match) morph).getRule().getAttrContext());
			
		} else if (morph instanceof NACStarMorphism) {
			((NACStarMorphism) morph).setPartialMorphismCompletion(true);
			
			return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.MATCH_MAP,
					((NACStarMorphism) morph).getRelatedMatchContext());
			
		} else if (morph instanceof PACStarMorphism) {
			((PACStarMorphism) morph).setPartialMorphismCompletion(true);
			
			return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.MATCH_MAP,
					((PACStarMorphism) morph).getRelatedMorphContext());
			
		} else {
			return morph.getAttrManager().newContext(
					agg.attribute.AttrMapping.PLAIN_MAP);
		}
	}

	/**
	 * If an image of the source node or edge exists, set it to be instance of
	 * the appropriate CSP variable.
	 */
	public void setPartialMorphism(final OrdinaryMorphism morph) {
		if (this.itsMorph != null) {	
			this.itsCSP.enableAllVariables();
			
			final Iterator<Node> anEnum = morph.getOriginal().getNodesSet().iterator();
			while (anEnum.hasNext()) {
				final Node anObj = anEnum.next();
				final GraphObject anImage = morph.getImage(anObj);
				if (anImage != null) {
					final Variable anObjVar = this.itsCSP.getVariable(anObj);
					if (anObjVar != null) {
						if (anObjVar.getInstance() != anImage) {
							anObjVar.setInstance(anImage);
							final Enumeration<?> conflicts = anObjVar.checkConstraints();
							if (conflicts.hasMoreElements()) {
								anObjVar.setInstance(null);
								throw new BadMappingException(
										"Completion_CSP.setPartialMorphism:: "+morph+"  There are conflicts for some CSP constraints!");
							}
						}
					}
				}
			}
			final Iterator<Arc> anEnum1 = morph.getOriginal().getArcsSet().iterator();
			while (anEnum1.hasNext()) {
				final Arc anObj = anEnum1.next();
				final GraphObject anImage = morph.getImage(anObj);
				if (anImage != null) {				
					final Variable anObjVar = this.itsCSP.getVariable(anObj);
					if (anObjVar != null) {
						if (anObjVar.getInstance() != anImage) {
							anObjVar.setInstance(anImage);
							final Enumeration<?> conflicts = anObjVar.checkConstraints();
							if (conflicts.hasMoreElements()) {
								anObjVar.setInstance(null);
								throw new BadMappingException(
										"Completion_CSP.setPartialMorphism:: "+morph+"  There are conflicts for some constraints!");
							}
						}
					}
				}
			}
		}
	}

	/*
	private void unsetUsedVariable(GraphObject go, OrdinaryMorphism morph) {
		if (go.getAttribute() != null) {
			final Vector<String> attrVars = ((ValueTuple) go.getAttribute())
					.getAllVariableNames();
			final VarTuple varTup = (VarTuple) morph.getAttrContext().getVariables();
			for (int i = 0; i < attrVars.size(); i++) {
				final VarMember vm = varTup.getVarMemberAt(attrVars.elementAt(i));
				if (vm != null)
					vm.setExpr(null);
			}
		}
	}
*/

	/**
	 * Template method to enable creation of CSPs with varying solution
	 * strategies by subclasses.
	 */
	protected SolutionStrategy createSolutionStrategy(boolean injective) {
		return new agg.util.csp.Solution_Backjump(injective);
		// test only
//		return new agg.util.csp.Solution_Backtrack(injective);
	}
	
	public void resetSolverQuery_Type() {
		if (this.itsCSP != null)
			this.itsCSP.resetQuery_Type();
	}
		
	public void enableParallelSearch(boolean b) {
		this.parallel = b;
		if (this.itsCSP != null) {
			this.itsCSP.getSolutionSolver().enableParallelSearch(b);
		}
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

	public void removeFromObjectVarMap(GraphObject anObj) {
		if (this.itsCSP != null)
			this.itsCSP.removeFromObjectVarMap(anObj);
	}
	
	public final void reset() {
		if (this.itsCSP != null && this.parallel)
			this.itsCSP.reset();
	}

	public void removeFromTypeObjectsMap(GraphObject anObj) {
		if (this.itsCSP != null)
			this.itsCSP.removeFromTypeObjectsMap(anObj);
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
			
			this.inputParameterMap.clear();		
			this.disabledInputParameter.clear();
		}
	}

	public void resetVariableDomain(final GraphObject go) {
		if (this.itsCSP != null)
			this.itsCSP.resetVariableDomain(go);
	}

	protected void unsetAttrContextVariable() {
		this.itsCSP.unsetAttrContextVariable();
	}

	public final boolean next(final OrdinaryMorphism morph) {
		// long time0 = System.currentTimeMillis();		
		if (morph != this.itsMorph) {
			try {
				initialize(morph);
			} catch (BadMappingException ex) {
				return false;
			}
		} 

		return doNext((OrdinaryMorphism) this.itsMorph);
	}

	public final boolean next(final OrdinaryMorphism morph, 
			Collection<Node> varnodes,
			Collection<Arc> varedges) {
		// long time0 = System.currentTimeMillis();
		if (morph != this.itsMorph) {
			try {
				initialize(morph, varnodes, varedges);
			} catch (BadMappingException ex) {
				return false;
			}
		} 
		
		return doNext((OrdinaryMorphism) this.itsMorph);
	}

	private boolean doNext(final OrdinaryMorphism morph) {
		this.itsCSP.setRelatedInstanceVarMap(this.relatedVarMap);
		boolean flag = false;
		this.errorMsg = "";
		
		if (morph.getAttrContext() != null) {
			((VarTuple) morph.getAttrContext().getVariables())
									.unsetNotInputVariables();
//			((VarTuple) morph.getAttrContext().getVariables()).showVariables();	
			storeValueOfInputParameter(morph);
		}
		
		while (this.itsCSP.nextSolution()) {
			flag = true;
			this.errorMsg = "";
			// try add morph. mapping after CSP success
			final Iterator<Node> anNodeIter = morph.getOriginal().getNodesSet().iterator();
			while (flag && anNodeIter.hasNext()) {
				final GraphObject anOrig = anNodeIter.next();
				Variable lhsVariable = this.itsCSP.getVariable(anOrig);
				if (lhsVariable != null) {
					final GraphObject anImage = (GraphObject) lhsVariable.getInstance();
					if (anImage != null) {
						try {
							morph.addMapping(anOrig, anImage);
						} catch (BadMappingException ex) {
							flag = false;
						}
					} else
						flag = false;					
				}
			}
			final Iterator<Arc> anArcIter = morph.getOriginal().getArcsSet().iterator();
			while (flag && anArcIter.hasNext()) {
				final GraphObject anOrig = anArcIter.next();
				Variable lhsVariable = this.itsCSP.getVariable(anOrig);
				if (lhsVariable != null) {
					final GraphObject anImage = (GraphObject) lhsVariable.getInstance();
					if (anImage != null) {
						try {
							morph.addMapping(anOrig, anImage);
						} catch (BadMappingException ex) {
							flag = false;
						}
					} else {
						flag = false;
					}
				}
			}
			
			if (!flag) {
				restoreValueOfInputParameter(morph);
				continue;
			}

			if (morph instanceof Match) {
				if (!((Match) morph).areTotalIdentDanglAttrGluingSatisfied()
						|| !((Match) morph).isParallelArcSatisfied()) {
					flag = false;
					
					restoreValueOfInputParameter(morph);
					continue;
				}
			}			
			
			if (morph.getAttrContext() != null) {
				flag = flag && checkInputParameter(morph) && checkObjectsWithSameVariable(morph);
			
				if (flag 
						&& !(morph instanceof NACStarMorphism)
						&& !(morph instanceof PACStarMorphism)
						&& !checkAttrCondition(morph)) {
					flag = false;
				}

				restoreValueOfInputParameter(morph);
			}
			
			if (flag) {
				morph.clearErrorMsg();
				break;
			} 
			morph.addErrorMsg(this.errorMsg);
		}
		
		return flag;
	}

	/*
	 * first save values of attr. context variables which are used as input parameters,
	 * otherwise its will be overwritten when next() done.
	 */
	private void storeValueOfInputParameter(OrdinaryMorphism morph) {		
		final AttrVariableTuple avt = morph.getAttrContext().getVariables();
		int num = avt.getSize();
		for (int i = 0; i < num; i++) {
			final VarMember var = avt.getVarMemberAt(i);
			if (var.isInputParameter()) {
				if (this.inputParameterMap.get(var.getName()) == null) {
					if (var.isSet()) {							
						this.inputParameterMap.put(var.getName(), var.getExprAsText());
					}
					else {						
						this.disabledInputParameter.add(var);
					}
				}
			}
		}
	}

	/*
	 * restore values of attr. context variables which are used as input parameters
	 */
	private void restoreValueOfInputParameter(OrdinaryMorphism morph) {
		final AttrVariableTuple avt = morph.getAttrContext().getVariables();
		if (!this.inputParameterMap.isEmpty()) {
			final Iterator<String> iter = this.inputParameterMap.keySet().iterator();
			while (iter.hasNext()) {
				final String name = iter.next();
				final String val = this.inputParameterMap.get(name);
				final VarMember var = avt.getVarMemberAt(name);
				if (var != null) {
					if (!val.equals(var.getExprAsText())) {
						var.setExprAsText(val);
					}
				}
			}
		}
	}

	private boolean checkInputParameter(OrdinaryMorphism morph) {	
//		System.out.println("Completion_CSP.checkInputParameter...  of "+morph.getName());
		final AttrVariableTuple avt = morph.getAttrContext().getVariables();
		if (!this.inputParameterMap.isEmpty()) {
			final Iterator<String> iter = this.inputParameterMap.keySet().iterator();
			while (iter.hasNext()) {
				final String IPname = iter.next();
				final String IPvalue = this.inputParameterMap.get(IPname);
				final VarMember VM = avt.getVarMemberAt(IPname);
				if (VM != null && VM.isInputParameter()) {
					if (VM.isEnabled()) {
						final String VMvalue = VM.getExprAsText();
						if (morph instanceof Match) {
							if (VMvalue != null) {
								if ((VM.getMark() != VarMember.NAC)
										&& (VM.getMark() != VarMember.PAC)
										&& !IPvalue.equals(VMvalue)) {
									this.errorMsg = "Value of the input parameter  [ "
										+ VM.getName() + " ] not found.";
									return false;
								}
							}
						} else if (morph instanceof NACStarMorphism) {
							if (VMvalue != null) {
								if ((VM.getMark() == VarMember.NAC)
										&& !IPvalue.equals(VMvalue)) {
									this.errorMsg = "Value of the input parameter  [ "
										+ VM.getName() + " ] not found.";
									return false;
								}
							}
						} else if (morph instanceof PACStarMorphism) {
							if (VMvalue != null) {
								if ((VM.getMark() == VarMember.PAC)
										&& !IPvalue.equals(VMvalue)) {
									this.errorMsg = "Value of the input parameter  [ "
										+ VM.getName() + " ] not found.";
									return false;
								}
							}
						} else {
							if (VMvalue != null) {
								if ((VM.getMark() != VarMember.NAC)
										&& (VM.getMark() != VarMember.PAC)
										&& !IPvalue.equals(VMvalue)) {
									this.errorMsg = "Value of the input parameter  [ "
										+ VM.getName() + " ] not found.";
									return false;
								}
							}
						}
					} 
				} 
			}			
		} 
			
		if (!this.disabledInputParameter.isEmpty()) {
			if (morph instanceof NACStarMorphism) {
				final OrdinaryMorphism relatedMatch = ((NACStarMorphism) morph).getRelatedMatch();
				final VarTuple varsOfMatch = (VarTuple) relatedMatch.getAttrContext().getVariables();
				for (int i=0; i<this.disabledInputParameter.size(); i++) {
					final VarMember VM = this.disabledInputParameter.get(i);
					if (VM != null) {
						VarMember matchVM = varsOfMatch.getVarMemberAt(VM.getName());
						if (matchVM != null 
								&& matchVM.getMark() == VarMember.NAC
								&& !matchVM.isEnabled()) {
							return false;
						}
					}
				}
				
			} else if (morph instanceof PACStarMorphism) {
				final OrdinaryMorphism relatedMatch = ((PACStarMorphism) morph).getRelatedMorph();
				final VarTuple varsOfMatch = (VarTuple) relatedMatch.getAttrContext().getVariables();
				for (int i=0; i<this.disabledInputParameter.size(); i++) {
					VarMember VM = this.disabledInputParameter.get(i);
					if (VM != null) {
						final VarMember matchVM = varsOfMatch.getVarMemberAt(VM.getName());
						if (matchVM != null 
								&& matchVM.getMark() == VarMember.PAC
								&& !matchVM.isEnabled()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean checkObjectsWithSameVariable(final OrdinaryMorphism morph) {
//		System.out.println("Completion_CSP.checkObjectsWithSameVariable : "+morph.getName());
		final VarTuple variables = (VarTuple) morph.getAttrContext().getVariables();
		for (int i = 0; i < variables.getSize(); i++) {
			final VarMember var = variables.getVarMemberAt(i);
			
			final Vector<Pair<GraphObject, String>> v = new Vector<Pair<GraphObject, String>>();
			
			final Iterator<Node> iter = morph.getOriginal().getNodesSet().iterator();
			while (iter.hasNext()) {
				GraphObject orig = iter.next();
				if (orig.getAttribute() != null) {
					final ValueTuple origVal = (ValueTuple) orig.getAttribute();
					for (int k = 0; k < origVal.getSize(); k++) {
						final ValueMember mem = origVal.getValueMemberAt(k);
						if (mem.isSet()
								&& mem.getExpr().isVariable()
								&& mem.getExprAsText().equals(var.getName())
								&& mem.getDeclaration().getTypeName().equals(
										var.getDeclaration().getTypeName())) {
							v.add(new Pair<GraphObject, String>(orig, mem.getName()));
	//						System.out.println("Variable: "+var.getName()+" at "+mem.getName());
						}
					}
				}
			}
			final Iterator<Arc> iter1 = morph.getOriginal().getArcsSet().iterator();
			while (iter1.hasNext()) {
				GraphObject orig = iter1.next();
				if (orig.getAttribute() != null) {
					final ValueTuple origVal = (ValueTuple) orig.getAttribute();
					for (int k = 0; k < origVal.getSize(); k++) {
						final ValueMember mem = origVal.getValueMemberAt(k);
						if (mem.isSet()
								&& mem.getExpr().isVariable()
								&& mem.getExprAsText().equals(var.getName())
								&& mem.getDeclaration().getTypeName().equals(
										var.getDeclaration().getTypeName())) {
							v.add(new Pair<GraphObject, String>(orig, mem.getName()));
						}
					}
				}
			}

			if (v.size() > 1) {
				final Pair<GraphObject, String> p = v.elementAt(0);
				final GraphObject img = morph.getImage(p.first);
				final ValueTuple val = (ValueTuple) img.getAttribute();				
				final ValueMember mem = val.getValueMemberAt(p.second);
				for (int j = 1; j < v.size(); j++) {
					final Pair<GraphObject, String> pj = v.elementAt(j);					
					final GraphObject imgj = morph.getImage(pj.first);
					final ValueTuple valj = (ValueTuple) imgj.getAttribute();
					final ValueMember memj = valj.getValueMemberAt(pj.second);
					if (mem.isSet() && memj.isSet()) {
						if (mem.getExpr().isConstant() 
								&& memj.getExpr().isConstant()) {
							if (!mem.getExprAsText().equals(memj.getExprAsText())) {
								this.errorMsg = "Attribute check (value is Constant): equal value due to equal variable - failed!";
								return false;
							}
						} else if (mem.getExpr().isVariable()
								&& memj.getExpr().isVariable()) {
							if (!mem.getExprAsText().equals(memj.getExprAsText())) {
								if (morph.getTarget().isCompleteGraph()) {
									this.errorMsg = "Attribute check (value is Variable): equal value due to equal variable - failed!";
									return false;
								} 
								else if ((mem.isTransient() || memj.isTransient())
//										&& morph.getOriginal().getKind() == GraphKind.CONCLUSION
										&& (morph.getOriginal().getKind() == GraphKind.PREMISE
												|| morph.getOriginal().getKind() == GraphKind.CONCLUSION)
										) {
									this.errorMsg = "Attribute check (value is Variable): equal (transient) variable due to equal variable - failed!";
//									System.out.println(this.getClass().getName()+":  "+this.errorMsg);
									return false;
								}
							}							
						} else if (morph.getTarget().isCompleteGraph()) {
							this.errorMsg = "Attribute check: equal value due to equal variable - failed!";
							System.out.println(this.getClass().getName()+":  "+this.errorMsg);
							return false;
						}
					} 
				}
			}
		}

		return true;
	}

	private boolean checkAttrCondition(final OrdinaryMorphism morph) {
		final CondTuple conds = (CondTuple) morph.getAttrContext().getConditions();
		for (int i = 0; i < conds.getSize(); i++) {
			final CondMember cond = conds.getCondMemberAt(i);
			if (!cond.isEnabled() 
					|| cond.getMark() != CondMember.LHS) {
				continue;
			}
			if (cond.isDefinite()
					&& !cond.isTrue()) {	
				this.errorMsg = "Attribute condition  [ " + cond.getExprAsText()
						+ " ]  failed.";

				((VarTuple) morph.getAttrContext().getVariables()).unsetVariables();
				morph.removeAllMappings();

				return false;
			}
		}
		return true;
	}

}
