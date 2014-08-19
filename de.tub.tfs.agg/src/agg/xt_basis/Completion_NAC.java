package agg.xt_basis;

import java.util.BitSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.impl.VarTuple;
import agg.util.csp.Variable;
import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * A decorator class which adds support for negative application conditions
 * (NACs) to a given completion strategy implementation.
 */
public class Completion_NAC extends MorphCompletionStrategy {
	
	private MorphCompletionStrategy itsStrategy;

	private final Vector<GraphObject> itsSavedState = new Vector<GraphObject>();

	private boolean globalNAC, globalPAC;

	/**
	 * Construct myself to be a NAC-supporting completion strategy. This uses
	 * the given <code>strategy</code> for the basic (non-NAC related) morphism
	 * completion functionality. The same holds for PAC-supporting.
	 * <p>
	 * <b>Post:</b> <code>getProperties().get(NAC) == true</code>.
	 * <b>Post:</b> <code>getProperties().get(PAC) == true</code>.
	 * <b>Post:</b> <code>getProperties().get(GAC) == true</code>.
	 */
	public Completion_NAC(MorphCompletionStrategy strategy) {
		BitSet aSupportedBits = (BitSet) strategy.getSupportedProperties().clone();
		aSupportedBits.set(CompletionPropertyBits.NAC);
		aSupportedBits.set(CompletionPropertyBits.PAC);
		aSupportedBits.set(CompletionPropertyBits.GAC);
		initialize(aSupportedBits, strategy.getProperties());
		
		getProperties().set(CompletionPropertyBits.NAC);
		getProperties().set(CompletionPropertyBits.PAC);
		getProperties().set(CompletionPropertyBits.GAC);
		this.itsStrategy = strategy;
		this.randomDomain = strategy.randomDomain;
		// showProperties();
	}

	public void dispose() {
		this.itsStrategy.dispose();
		this.itsSavedState.clear();	
	}
	
	public String getName() {
		return this.itsStrategy.getName();
	}
		
	public void setRandomisedDomain(boolean b) {		
		this.itsStrategy.setRandomisedDomain(b);
		this.randomDomain = b;
	}
	
	public MorphCompletionStrategy getSourceStrategy() {
		return this.itsStrategy;
	}

	public AttrContext getAttrContext() {
		return this.itsStrategy.getAttrContext();
	}

	public void setProperties(MorphCompletionStrategy fromStrategy) {
		BitSet aSupportedBits = (BitSet) fromStrategy.getSupportedProperties()
				.clone();
		aSupportedBits.set(CompletionPropertyBits.NAC);
		aSupportedBits.set(CompletionPropertyBits.PAC);
		aSupportedBits.set(CompletionPropertyBits.GAC);
		initialize(aSupportedBits, 
					(BitSet) fromStrategy.getProperties().clone());
		getProperties().set(CompletionPropertyBits.NAC);
		getProperties().set(CompletionPropertyBits.PAC);
		getProperties().set(CompletionPropertyBits.GAC);
	}

	public final void reset() {
			this.itsStrategy.reset();	
	}
		
	public void enableParallelSearch(boolean b) {
		this.parallel = b;
		this.itsStrategy.enableParallelSearch(b);
	}
	
	public void setStartParallelSearchByFirst(boolean b) {
		this.startParallelMatchByFirstCSPVar = b;
		this.itsStrategy.setStartParallelSearchByFirst(b);
	}
	
	public void resetSolverQuery_Type() {
		this.itsStrategy.resetSolverQuery_Type();
	}
	
	public void resetSolver(boolean doUpdateQueries) {
		this.itsStrategy.resetSolver(doUpdateQueries);
	}
	
	public final void initialize(OrdinaryMorphism morph) {
		this.itsStrategy.initialize(morph);
	}
	
	public void reinitializeSolver(boolean doUpdateQueries) {
		this.itsStrategy.reinitializeSolver(doUpdateQueries);
	}

	public void resetSolverVariables() {
		this.itsStrategy.resetSolverVariables();
	}

	public void removeFromObjectVarMap(GraphObject anObj) {
		this.itsStrategy.removeFromObjectVarMap(anObj);
	}
	
	public void removeFromTypeObjectsMap(GraphObject anObj) {
		this.itsStrategy.removeFromTypeObjectsMap(anObj);
	}
	
	public void resetTypeMap(Hashtable<String, HashSet<GraphObject>> typeMap) {
		this.itsStrategy.resetTypeMap(typeMap);
	}

	public void resetTypeMap(Graph g) {
		this.itsStrategy.resetTypeMap(g);
	}

	public void resetVariableDomain(boolean resetByNull) {
		this.itsStrategy.resetVariableDomain(resetByNull);
	}

	public void resetVariableDomain(GraphObject go) {
		this.itsStrategy.resetVariableDomain(go);
	}

	public void setPartialMorphism(OrdinaryMorphism morph) {
		this.itsStrategy.setPartialMorphism(morph);
	}

	public boolean isDomainOfTypeEmpty(Type t) {
		return this.itsStrategy.isDomainOfTypeEmpty(t);
	}

	public boolean isDomainOfTypeEmpty(Type t, Type src, Type tar) {
		return this.itsStrategy.isDomainOfTypeEmpty(t, src, tar);
	}

	public void setRelatedInstanceVarMap(
			Dictionary<Object, Variable> relatedVarMap) {
		this.itsStrategy.setRelatedInstanceVarMap(relatedVarMap);
	}

	public Dictionary<Object, Variable> getInstanceVarMap() {
		return this.itsStrategy.getInstanceVarMap();
	}

	public Object clone() {
		Completion_NAC aClone = new Completion_NAC(
				(MorphCompletionStrategy) this.itsStrategy.clone());
		aClone.itsProperties = (BitSet) this.itsProperties.clone();
		aClone.randomDomain = this.randomDomain;
		aClone.parallel = this.parallel;
		aClone.startParallelMatchByFirstCSPVar = this.startParallelMatchByFirstCSPVar;
		
		return aClone;
	}

	public final boolean next(final OrdinaryMorphism morph) {
		if (morph instanceof Match) {	
						
//			((VarTuple) aMatch.getAttrContext().getVariables())
//						.unsetNotInputVariables();
//			((VarTuple) morph.getAttrContext().getVariables()).showVariables();
			
			if (this.itsStrategy.next(morph)) {
				return areLeftApplCondSatisfied((Match) morph);	
			} 
			return false;			
		} 
		return this.itsStrategy.next(morph); 
	}
	
	public final boolean next(final OrdinaryMorphism morph, 
			Collection<Node> nodes,
			Collection<Arc> edges) {
		
		if (morph instanceof Match) {	
						
//			((VarTuple) aMatch.getAttrContext().getVariables())
//						.unsetNotInputVariables();
			
			if (this.itsStrategy.next(morph, nodes, edges)) {	
				return areLeftApplCondSatisfied((Match) morph);				
			} 
			return false;
		}
		return this.itsStrategy.next(morph, nodes, edges); 
	}
	
	private boolean areLeftApplCondSatisfied(final Match aMatch) {		
		boolean matchCompletionDone = true;
		while (matchCompletionDone) {	
			
			if (!this.itsProperties.get(CompletionPropertyBits.GAC) 
					|| aMatch.getRule().evalFormula()) {
				// check more
				if (this.itsProperties.get(CompletionPropertyBits.PAC)) {
					if(arePACsSatisfied(aMatch)) {
						// check more
						if (this.itsProperties.get(CompletionPropertyBits.NAC)) {
							if (areNACsSatisfied(aMatch)) {
								return true;
							}
							if ( this.globalNAC
									&& !aMatch.getTarget().isAttributed()
									&& (((VarTuple) aMatch.getAttrContext()
												.getVariables()).getSize() == 0) ) {
								return false;
							}
							
	//						((VarTuple) aMatch.getAttrContext().getVariables())
	//									.unsetNotInputVariables();
							matchCompletionDone = this.itsStrategy.next(aMatch);
						}
						else 
							return true;
					} else {
						if ( this.globalPAC
								&& !aMatch.getTarget().isAttributed()
								&& (((VarTuple) aMatch.getAttrContext()
										.getVariables()).getSize() == 0) ) {								
							return false;
						}
						
	//					((VarTuple) aMatch.getAttrContext().getVariables())
	//								.unsetNotInputVariables();						
						matchCompletionDone = this.itsStrategy.next(aMatch);
					}
				} else if (getProperties().get(CompletionPropertyBits.NAC)) {
					if (areNACsSatisfied(aMatch)) {
						return true;
					}
					if (this.globalNAC
							&& !aMatch.getTarget().isAttributed()
							&& (((VarTuple) aMatch.getAttrContext()
										.getVariables()).getSize() == 0)) {
						return false;
					}
					
	//				((VarTuple) aMatch.getAttrContext().getVariables())
	//							.unsetNotInputVariables();
					matchCompletionDone = this.itsStrategy.next(aMatch);
				} 
				else
					return true;
			}
			else {
				matchCompletionDone = this.itsStrategy.next(aMatch);
			}
		}
		
		return false;
	}

	private final boolean areNACsSatisfied(Match match) {
		if (match.getRule().hasNACs()) {		
			this.globalNAC = true;
			final Enumeration<OrdinaryMorphism> nacs = match.getRule().getNACs();
			while (nacs.hasMoreElements()) {
				final OrdinaryMorphism nac = nacs.nextElement();
				
				if (!nac.isEnabled())
					continue;
				
				if (nac.getSize() != 0)
					this.globalNAC = false;
				
				if (!MatchHelper.isDomainOfApplCondEmpty(match, nac)) {
					if (match.checkNAC(nac) != null) {				
						return false;
					} 
				}
			}
		}
		return true;
	}


	private final boolean arePACsSatisfied(Match match) {
		if (match.getRule().hasPACs()) {		
			this.globalPAC = true;
			final Enumeration<OrdinaryMorphism> pacs = match.getRule().getPACs();
			while (pacs.hasMoreElements()) {
				final OrdinaryMorphism pac = pacs.nextElement();
				if (pac.isEnabled() && !pac.isShifted()) {	
					if (pac.getSize() != 0)
						this.globalPAC = false;
		
					if (!MatchHelper.isDomainOfApplCondEmpty(match, pac)) {
						if (match.checkPAC(pac) == null) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}
		
		// check PACs shifted from source rule to concurrent rule during its creation
		if (!areShiftedPACsSatisfied(match)) {
			return false;
		}
		
		return true;
	}

	private boolean areShiftedPACsSatisfied(Match match) {		
		boolean ok = match.getRule().getShiftedPACs() == null
						|| match.getRule().getShiftedPACs().isEmpty();
		if (!ok) {
			for (int i=0; i<match.getRule().getShiftedPACs().size() && !ok; i++) {
				final ShiftedPAC shiftedPAC = match.getRule().getShiftedPACs().get(i);
				if (shiftedPAC.eval(match)) {
					ok = true;
				}
			}
			this.globalPAC = false;
		}
		return ok;
	}

	
	
	/*
	private final void saveState(OrdinaryMorphism morph) {
		GraphObject anObj;
		itsSavedState.clear();
		Enumeration<GraphObject> aDomainEnum = morph.getDomain();
		while (aDomainEnum.hasMoreElements()) {
			anObj = aDomainEnum.nextElement();
			itsSavedState.addElement(anObj);
			itsSavedState.addElement(morph.getImage(anObj));
		}
	}

	private final void restoreState(OrdinaryMorphism morph) {
		morph.clear();
		for (int i = 0; i < itsSavedState.size() - 1; i = i + 2) {
			morph.addMapping(itsSavedState.elementAt(i), itsSavedState
					.elementAt(i + 1));
		}
		itsSavedState.clear();
	}
*/
	
}
