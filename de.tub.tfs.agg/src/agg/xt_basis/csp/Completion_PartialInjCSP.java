package agg.xt_basis.csp;

import java.util.Collection;
import java.util.List;

import agg.attribute.AttrContext;
import agg.util.csp.Solution_Backjump_PartialInj;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Match;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.ParallelRule;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

/**
 * An implementation of morphism completion as a Constraint Satisfaction Problem (CSP), 
 * considering partial injective solutions.
 */
public class Completion_PartialInjCSP extends Completion_CSP {
	
	public Completion_PartialInjCSP() {
		super();
		// set default properties:
		getProperties().set(CompletionPropertyBits.INJECTIVE);
		getProperties().set(CompletionPropertyBits.DANGLING);
		getProperties().set(CompletionPropertyBits.IDENTIFICATION);
	}

	public Completion_PartialInjCSP(boolean randomizeDomain) {
		super(randomizeDomain);
		// set default properties:
		getProperties().set(CompletionPropertyBits.INJECTIVE);
		getProperties().set(CompletionPropertyBits.DANGLING);
		getProperties().set(CompletionPropertyBits.IDENTIFICATION);
	}

	/**
	 * Initialize the CSP by the specified morphism. The CSP variables are
	 * created for each node and edge of the source graph of the given morphism.
	 * Initialize the CSP variables (partially) according mappings of the given morphism. 
	 */
	public final void initialize(OrdinaryMorphism morph)
			throws BadMappingException {
		this.itsMorph = morph;
		
		final AttrContext aContext = initializeAttrContext(morph);
		
		final Solution_Backjump_PartialInj itsSolution = new Solution_Backjump_PartialInj(getProperties().get(INJECTIVE));

		// create CSP
		this.itsCSP = new ALR_CSP(
				morph.getOriginal(), 
				aContext,
				itsSolution, 
				this.randomDomain);
		
		if (morph.getImage().getTypeObjectsMap().isEmpty())
			morph.getImage().fillTypeObjectsMap();

		this.itsCSP.setRequester(morph);
		this.itsCSP.setDomain(morph.getImage());
		
		if (morph instanceof Match
				&& ((Match)morph).getRule() instanceof ParallelRule) {
		
			final List<OrdinaryMorphism> list = ((ParallelRule)((Match)morph).getRule()).getLeftEmbedding();
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					itsSolution.setSubcontextOfVariable(
										this.itsCSP.getVariables(),
										list.get(i).getOriginal(), 
										list.get(i).getCodomainObjects());
				}
			}
		}
		
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
	public final void initialize(final OrdinaryMorphism morph, 
			final Collection<Node> nodes,
			final Collection<Arc> edges) 
	throws BadMappingException {
		this.itsMorph = morph;
		final AttrContext aContext = initializeAttrContext(morph);
		
		final Solution_Backjump_PartialInj itsSolution = new Solution_Backjump_PartialInj(getProperties().get(INJECTIVE));
		
		// create CSP
		if (nodes != null && edges != null) {
//			 new : only injective mapping allowed
			this.itsCSP = new ALR_CSP(nodes, edges, aContext, true, true);
		} else {			
			this.itsCSP = new ALR_CSP(
					morph.getOriginal(), 
					aContext,
					itsSolution,
					this.randomDomain);
		}

		if (morph.getImage().getTypeObjectsMap().isEmpty()) {
			morph.getImage().fillTypeObjectsMap();
		}
		
		this.itsCSP.setRequester(morph);
		this.itsCSP.setDomain(morph.getImage());

		if (morph instanceof Match
				&& ((Match)morph).getRule() instanceof ParallelRule) {
		
			final List<OrdinaryMorphism> list = ((ParallelRule)((Match)morph).getRule()).getLeftEmbedding();
			if (list != null) {
				for (int i=0; i<list.size(); i++) {
					itsSolution.setSubcontextOfVariable(
										this.itsCSP.getVariables(),
										list.get(i).getOriginal(), 
										list.get(i).getCodomainObjects());
				}
			}
		}
		
		this.inputParameterMap.clear();		
		this.disabledInputParameter.clear();
		
		this.itsCSP.getSolutionSolver().enableParallelSearch(this.parallel);
		this.itsCSP.getSolutionSolver().setStartParallelSearchByFirst(this.startParallelMatchByFirstCSPVar);
		
		// represent the mappings of a partial morphism in the CSP:
		setPartialMorphism(morph);
	}

}
