package agg.xt_basis;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrException;
import agg.attribute.AttrVariableTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.AttrConditionTuple;
import agg.cons.Formula;
import agg.util.Pair;
import agg.xt_basis.agt.RuleScheme;
import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * @version $Id: GraTra.java,v 1.59 2010/12/01 20:26:41 olga Exp $
 * @author $Author: olga $
 */
public abstract class GraTra {

	protected String name;

	protected GraGra grammar;

	protected Graph hostgraph;

	protected Rule currentRule;

	protected Vector<Rule> currentRuleSet = new Vector<Rule>();

	protected Match currentMatch;

	protected boolean colimitBasedPushout;
	
	protected boolean updateTypeObjectsMapAfterStep = true;

	protected MorphCompletionStrategy strategy;

	final protected Vector<GraTraEventListener> graTraListeners = new Vector<GraTraEventListener>();

	protected boolean stopping = false;

	protected boolean stoppingRule = false;

	protected boolean pauseRule = false;
	
	protected boolean waitAfterStep = false;
	
	protected boolean consistentGraph = true;

	protected boolean writeLogFile = false;

	protected GraTraOptions options;

	protected String errorMsg = "";

	protected boolean wait;
	
	public void dispose() {
		this.graTraListeners.clear();
		if (this.grammar != null) {
			this.grammar.destroyAllMatches();
		}
		this.currentRuleSet.clear();		
		this.grammar = null;
		this.hostgraph = null;
		this.currentRule = null;
		this.currentMatch = null;	
	}
	
	public void setName(String n) {
		if (n != null) {
			this.name = n;
		} 
	}

	public String getName() {
		if (this.name == null) {
			return ("unnamed");
		} 
		return (this.name);
	}

	public boolean setGraGra(GraGra gg) {
		if (gg == null) {
			this.grammar = null;
			this.hostgraph = null;
			return false;
		} 
		this.grammar = gg;
		setRuleSet();
		this.hostgraph = this.grammar.getGraph();
		this.strategy = this.grammar.getMorphismCompletionStrategy();
		getGraTraOptions();
		return true;
	}

	private void resetTargetOfRuleMatches() {
		for (int i = 0; i < this.currentRuleSet.size(); i++) {
			final Rule r = this.currentRuleSet.get(i);
			final Match m = r.getMatch();
			if (m != null) {
				if (m.getTarget() != this.hostgraph) {
					m.resetTarget(this.hostgraph);
					m.setTypeObjectsMapChanged(true);
				} else
					return;
			}
		}
	}

	public GraGra getGraGra() {
		return (this.grammar);
	}

	public Rule getCurrentRule(){ return this.currentRule;}
	
	public Match getCurrentMatch(){ return this.currentMatch;}
	
	public boolean setHostGraph(Graph g) {
		if (this.hostgraph == null) {
			this.hostgraph = g;
			this.hostgraph.updateTypeObjectsMap();
			resetExistingMatchesOfRules(g);
			resetTargetOfRuleMatches();
			return (true);
		} 
		this.hostgraph = g;
		this.hostgraph.updateTypeObjectsMap();
		resetExistingMatchesOfRules(g);
		resetTargetOfRuleMatches();
		return (false);
	}

	public Graph getHostGraph() {
		return (this.hostgraph);
	}

	private void resetExistingMatchesOfRules(Graph g) {
		for (int i = 0; i < this.grammar.getListOfRules().size(); i++) {
			Rule r = this.grammar.getListOfRules().get(i);
			r.setMatch(this.grammar.getMatch(r, g));
		}
	}

	public GraTraOptions getGraTraOptions() {
		if (this.options == null)
			this.options = new GraTraOptions();
		if (this.strategy != null) {
			this.options.addOption(CompletionStrategySelector.getName(this.strategy));
			BitSet activebits = this.strategy.getProperties();
			for (int i = 0; i < CompletionPropertyBits.BITNAME.length; i++) {
				if (activebits.get(i)) {
					String bitName = CompletionPropertyBits.BITNAME[i];
					this.options.addOption(bitName);
				}
			}
			if (!this.strategy.isRandomisedDomain())
				this.options.addOption(GraTraOptions.DETERMINED_CSP_DOMAIN);
		}
		return this.options;
	}

	public void setGraTraOptions(GraTraOptions newOptions) {
		this.options = newOptions;
		this.options.updateMorphismCompletionStrategy();
		setCompletionStrategy(this.options.getCompletionStrategy());
	}

	public void setGraTraOptions(Vector<String> newOptions) {
		GraTraOptions nOptions = new GraTraOptions();
		for (int i = 0; i < newOptions.size(); i++) {
			String opt = newOptions.elementAt(i);
			nOptions.addOption(opt);
		}
		this.options = nOptions;
		this.options.updateMorphismCompletionStrategy();
		setCompletionStrategy(this.options.getCompletionStrategy());
	}

	public void setCompletionStrategy(MorphCompletionStrategy strat) {
		this.strategy = strat;
	}

	public MorphCompletionStrategy getCompletionStrategy() {
		return this.strategy;
	}

	public Enumeration<Match> getMatches(Rule r) {
		Iterator<Rule> rules = this.grammar.getListOfRules().iterator();
		boolean indicator = false;
		while (rules.hasNext()) {
			if (r == rules.next()) {
				indicator = true;
				break;
			}
		}
		if (indicator == false) {
			return (null);
		} 
		return ((this.grammar).getMatches(r));
	}

	public Match createMatch(Rule r) {
		Iterator<Rule> rules = this.grammar.getListOfRules().iterator();
		boolean indicator = false;
		while (rules.hasNext()) {
			if (r == rules.next()) {
				indicator = true;
				break;
			}
		}
		if (indicator == false) {
			return (null);
		}
		return (this.grammar.createMatch(r));
	}

	public void destroyMatch(Match m) {
		fireGraTra(new GraTraEvent(this, GraTraEvent.DESTROY_MATCH, m));
		this.grammar.destroyMatch(m);
		return;
	}
	
	/**
	 * The match m has to be valid. Returns a co-morphism.
	 */
	public Morphism apply(Match m) {
		synchronized (m) {
			this.errorMsg = "";
		Morphism co_match = null;
		this.consistentGraph = true;

		if (this.options == null)
			this.options = getGraTraOptions();

		if (this.options.hasOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO)) {
			try {
//				fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_VALID, m));
				co_match = StaticStep.execute(m);
//				co_match = StaticStep.executeColimBased(m);
			} catch (TypeException e) {
				this.errorMsg = e.getMessage();
				fireGraTra(new GraTraEvent(this, GraTraEvent.CANNOT_TRANSFORM, m, this.errorMsg));
				// destroyMatch(m);
				return null;
			}
		}	
		else if (!this.options.hasOption(GraTraOptions.CONSISTENT_ONLY)) {
			try {
				// break after inconsistent step
//				fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_VALID, m));

				co_match = StaticStep.execute(m);
//				co_match = StaticStep.executeColimBased(m);
				if ((co_match != null)
						&& !checkGraphConsistency(m.getRule(), m.getTarget())) {
					this.consistentGraph = false;
				}
			} catch (TypeException e) {
				this.errorMsg = e.getMessage();
				fireGraTra(new GraTraEvent(this, GraTraEvent.CANNOT_TRANSFORM, m, this.errorMsg));
				// destroyMatch(m);
				return null;
			}
		} 	
		else { // GraTraOptions.CONSISTENT_ONLY
			boolean validStep = false;
//			consistentGraph = false;
			// try to make consistent step
			try {
				co_match = this.makeTestStep(m);
				validStep = true;
				this.consistentGraph = this.checkGraphConsistencyForComatch(m, (OrdinaryMorphism) co_match);
				// do dispose, because co_match was a test comatch only! 
				co_match.dispose(); 
				co_match = null;
			} catch (TypeException ex) {
				this.errorMsg = ex.getLocalizedMessage();
			}
			// try to find next completion
			while ((!validStep || !this.consistentGraph) && m.nextCompletion()) {
				if (m.isValid()) {
					// try to make consistent step
					try {
						co_match = this.makeTestStep(m);
						validStep = true;
						this.consistentGraph = this.checkGraphConsistencyForComatch(m, (OrdinaryMorphism) co_match);
						// do dispose, because co_match was a test co-match only! 
						co_match.dispose(); 
						co_match = null;
						if (this.consistentGraph) {
							break;
						} 
					} catch (TypeException ex) {
						this.errorMsg = ex.getLocalizedMessage();
					}
				} 
			}				
			if (!validStep) {
				fireGraTra(new GraTraEvent(this, GraTraEvent.CANNOT_TRANSFORM, m, this.errorMsg));
				return null;
			} else if (this.consistentGraph) {
				fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_VALID, m));
				// now make the current step
				try {
					co_match = StaticStep.execute(m);
//					co_match = StaticStep.executeColimBased(m);
				} catch (TypeException e) {					
					this.errorMsg = e.getMessage();
					fireGraTra(new GraTraEvent(this, GraTraEvent.CANNOT_TRANSFORM, m, this.errorMsg));
					// destroyMatch(m);
					return null;
				}
			} else {
				this.errorMsg = "Graph inconsistency after transformation.";
			}
		}
		
		if (co_match != null) {
			// hostgraph = co.getImage();
			fireGraTra(new GraTraEvent(this, GraTraEvent.STEP_COMPLETED, m)); //, co));			
			// destroyMatch(m);
		}
		if (!this.consistentGraph) {
			fireGraTra(new GraTraEvent(this, GraTraEvent.INCONSISTENT, m));
			// destroyMatch(m);
		}
		return co_match;
		}
	}

	public boolean step(final Match m) {
		return ((m != null) && (apply(m) != null))? true: false;
	}

	public boolean apply(Rule r) {
		synchronized (r) { // this
//			System.gc();
	
//		long time0 = System.currentTimeMillis();

		this.stoppingRule = false;		
		boolean result = false;
		boolean valid = false;

		this.currentMatch = r.getMatch();
		if (this.currentMatch == null) {
			this.currentMatch = this.grammar.createMatch(r);
			this.currentMatch.setCompletionStrategy(
						(MorphCompletionStrategy) this.strategy.clone(), true);
//			strategy.showProperties();
		} else if (this.updateTypeObjectsMapAfterStep) {
			this.currentMatch.setTypeObjectsMapChanged(true);
		}

		
		boolean parallelApply = true;
		boolean is_applied = false;
//		int matchCompletions = 0;
		
//		time0 = System.currentTimeMillis();
		
		while (parallelApply) {

			if (!isInputParameterSet(r.getLeft(), true, this.currentMatch)) {
				fireGraTra(
					new GraTraEvent(this, GraTraEvent.INPUT_PARAMETER_NOT_SET, this.currentMatch));
			}
			
			if(this.stopping || this.stoppingRule) {
//				this.destroyMatch(currentMatch);
				this.currentMatch.clear(); 
		        return false;
		    }
			
			if(this.pauseRule)
				return false; 
			
			valid = false;
			while (!valid) {
				if (this.currentMatch.nextCompletion()) {
					if (this.currentMatch.isValid()) {
						valid = true;
//						matchCompletions++;
						if (r.isParallelApplyEnabled()
								&& this.currentMatch.typeObjectsMapChanged) {
							this.currentMatch.typeObjectsMapChanged = false;
							this.updateTypeObjectsMapAfterStep = false;
							// das hat Auswirkung auf den naechsten Aufruf 							
							// von nextCompletion():
							// die Graphaenderungen nach dem Step werden 
							// NICHT BEACHTET!!!
						}
					} else {
						this.errorMsg = this.currentMatch.getErrorMsg();
						this.currentMatch.clear();
					}
				} else {
					this.errorMsg = this.currentMatch.getErrorMsg();
					break;
				}
			}

			if (valid) {
				fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_VALID,
						this.currentMatch));

				if (!isInputParameterSet(r.getRight(), false, this.currentMatch)) {
					fireGraTra(new GraTraEvent(this,
							GraTraEvent.INPUT_PARAMETER_NOT_SET, this.currentMatch));
				}
				
//				if(stopping || stoppingRule) {
//					if (currentMatch != null) {
//						currentMatch.clear(); 
//					}
//			        return false;
//			    }
//
//				if(pauseRule)
//		    		return false;				
				
				try { // check attr context: variables only
					boolean checkVarsOnly = true;
					this.currentMatch.getAttrContext().getVariables()
							.getAttrManager().checkIfReadyToTransform(
									this.currentMatch.getAttrContext(),
									checkVarsOnly);
				} catch (AttrException ex) {
					fireGraTra(new GraTraEvent(this,
							GraTraEvent.NOT_READY_TO_TRANSFORM, r.getName()));
					// destroyMatch(currentMatch);					
					return false;
				}
				
				Morphism coMatch = apply(this.currentMatch);				
				if (coMatch != null) {
					this.errorMsg = "";
					is_applied = true;
					
					this.currentMatch.clear();
//					destroyMatch(this.currentMatch);
					coMatch = null;
					result = true;
				} else {
					valid = false;
					fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
							this.currentMatch, this.errorMsg));					
					this.currentMatch.clear();
//					destroyMatch(currentMatch);
					
					result = false;	
				}
			} else {
				fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
						this.currentMatch, this.currentMatch.getErrorMsg()));				
				this.currentMatch.clear();
//				 destroyMatch(currentMatch);
				
				result = false;
			}

			//
			if (r.isParallelApplyEnabled()) {
				if (!valid) {
					parallelApply = false;
					this.currentMatch.typeObjectsMapChanged = true;
					this.updateTypeObjectsMapAfterStep = true;
				}
				if (is_applied) 
					result = true;
				
			} else {
				parallelApply = false;
				break;
			}
			//   	      
		}

		return result;
		}
	}
	
	public void enableWriteLogFile(boolean b) {
		this.writeLogFile = b;
	}


	public abstract boolean apply();

	public abstract Pair<Morphism, Morphism> derivation(Match m);
	
	public abstract void transform(List<Rule> ruleSet);

	public abstract void transform();

	public abstract boolean transformationDone();

	public boolean isGraphConsistent() {
		return this.consistentGraph;
	}

	public void stop() {
		this.stopping = true;
		this.pauseRule = false;
	}

	public void unsetStop() {
		this.stopping = false;
		this.stoppingRule = false;
		this.pauseRule = false;
	}
	
	public void stopRule() {
		this.stoppingRule = true;
		this.pauseRule = false;
	}

	public void pauseRule(boolean pause) {
		this.pauseRule = pause;
	}
	
	/**
	 * @deprecated 
	 * replaced by <code>pauseRule(boolean pause)</code>
	 */
	public void pauseRule() { 
		this.pauseRule = true;  
    }
	
	public boolean isPaused() { return this.pauseRule; }
		
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void doUpdateTypeObjectsMapAfterStep(boolean b) {
		this.updateTypeObjectsMapAfterStep = b;
	}

	public synchronized void removeGraTraListener(GraTraEventListener l) {
		if (this.graTraListeners.contains(l)) {
			this.graTraListeners.removeElement(l);
		}
	}

	public synchronized void addGraTraListener(GraTraEventListener l) {
		if (!this.graTraListeners.contains(l)) {
			this.graTraListeners.addElement(l);
		}
	}

	protected void fireGraTra(GraTraEvent e) {
		int count = this.graTraListeners.size();
		for (int i = 0; i < count; i++) {
			this.graTraListeners.elementAt(i).graTraEventOccurred(e);
		}
	}

	protected void setRuleSet() {
		if (this.grammar != null) {
			this.currentRuleSet.addAll(this.grammar.getListOfRules());
			
//			Iterator<Rule> rules = grammar.getListOfRules().iterator();
//			while ((rules.hasNext())) {
//				Rule r = rules.next();
//				currentRuleSet.addElement(r);
//			}
		}
	}

	protected boolean isInputParameterSet(
			final Graph g, 
			boolean left,
			final Match match) {
		
		if (match == null
				|| match.getAttrContext().getVariables().getNumberOfEntries() == 0)
			return true;

//		boolean set = true;
		
		final AttrConditionTuple act = match.getAttrContext().getConditions();
		final AttrVariableTuple avt = match.getAttrContext().getVariables();
		for (int i = 0; i < avt.getNumberOfEntries(); i++) {
			VarMember v = avt.getVarMemberAt(i);
			if (v.isInputParameter() && !v.isSet()) {
				if (g.isUsingVariable(v)) {
					// fireGraTra(new
					// GraTraEvent(this,GraTraEvent.PARAMETER_NOT_SET,match));
					return false;
				}
				
				if (left) {
					if (v.getMark() == VarMember.RHS) {
						
					}
					else if (v.getMark() == VarMember.LHS) {
						return false;
					}
					else {
						return (!match.getRule().nacIsUsingVariable(v, act)
								&& !match.getRule().pacIsUsingVariable(v, act));
					}
				}
			}
		}
		return true;
	}
	
	
	protected boolean checkGraphConsistency(Rule r, Graph g) {
		// check graph consistancy after applying rule
		if (this.grammar.isLayered()) {
			List<Formula> constraints = this.grammar.getConstraintsForLayer(-1);
			// first check global constraints
			if (this.grammar.checkGraphConsistency(g, constraints)) {
				constraints = this.grammar.getConstraintsForLayer(r.getLayer());
				// now for the layer only
				if (this.grammar.checkGraphConsistency(g, constraints))
					return true;
				
				return false;
			}
			return false;
		} else if (this.grammar.trafoByPriority()) {
			Vector<Formula> constraints = this.grammar.getConstraintsForPriority(-1);
			// first check global constraints
			if (this.grammar.checkGraphConsistency(g, constraints)) {
				constraints = this.grammar
						.getConstraintsForPriority(r.getPriority());
				// now for the priority only
				if (this.grammar.checkGraphConsistency(g, constraints))
					return true;
				
				return false;
			} 
			return false;
		} else if (this.grammar.checkGraphConsistency(g))
			return true;
		else
			return false;
	}

	/*
	 * occurred
	 */
	protected OrdinaryMorphism makeTestStep(Match m) throws TypeException {
		// make test step to check graph constraints
		BaseFactory bf = BaseFactory.theFactory();
		OrdinaryMorphism copy = m.getImage().isomorphicCopy();
		if (copy == null) {
			throw new TypeException("Undefined error occurred during construction of test step.");	
		}
		copy.getImage().setCompleteGraph(true);
		OrdinaryMorphism com = m.compose(copy);
		Match m2 = bf.makeMatch(m.getRule(), com);		
		if (m2 != null) {
			m2.adaptAttrContextValues(m.getAttrContext());
			try {
				OrdinaryMorphism co_match = (OrdinaryMorphism) StaticStep.execute(m2);
//				OrdinaryMorphism co_match = (OrdinaryMorphism) StaticStep.executeColimBased(m2);
				return co_match;
			} catch (TypeException ex) {
				System.out.println(ex.getLocalizedMessage());
				m2.dispose();
				com.dispose();
				copy.dispose();
				throw (ex);
			}
		} 
		com.dispose();
		copy.dispose();
		throw new TypeException("Undefined error occurred during construction of test step.");	
	}
		
	protected boolean checkGraphConsistencyForComatch(final Match m, final OrdinaryMorphism co_match) {
		if (co_match != null) {
			boolean result = false;
			if (this.grammar.isLayered()) {
				List<Formula> constraints = this.grammar.getConstraintsForLayer(-1);
				// first check global constraints
				if (this.grammar.checkGraphConsistency(co_match.getImage(), constraints)) {
					constraints =this. grammar.getConstraintsForLayer(m.getRule().getLayer());
					// now for the layer only
					if (this.grammar.checkGraphConsistency(co_match.getImage(), constraints)) 								
						result = true;			
					else 
						result = false;
				} else {
					result = false;
				}
				co_match.dispose();
				return result;
			} else if (this.grammar.trafoByPriority()) {
				Vector<Formula> constraints = this.grammar.getConstraintsForPriority(-1);
				// first check global constraints
				if (this.grammar.checkGraphConsistency(co_match.getImage(), constraints)) {
					constraints = this.grammar.getConstraintsForPriority(m.getRule().getPriority());
					// now for the priority only
					if (this.grammar.checkGraphConsistency(co_match.getImage(), constraints))
						result = true;
					else
						result = false;
				} else {
					result = false;
				}
				co_match.dispose();
				return result;
			} else if (this.grammar.checkGraphConsistency(co_match.getImage())) {
				result = true;
			} else {
				result = false;
			}
			co_match.dispose();
			return result;
		} 
		return false;	
	}
	
	protected boolean checkGraphConsistency(Match m) {
		// make test step to check graph constraints
		BaseFactory bf = BaseFactory.theFactory();
		OrdinaryMorphism copy = m.getImage().isomorphicCopy();
		if (copy == null)
			return false;
		
		copy.getImage().setCompleteGraph(true);
		OrdinaryMorphism com = m.compose(copy);
		Match m2 = bf.makeMatch(m.getRule(), com);		
		if (m2 != null) {
			m2.adaptAttrContextValues(m.getAttrContext());
			try {
				OrdinaryMorphism co_match = (OrdinaryMorphism) StaticStep.execute(m2);
//				OrdinaryMorphism co_match = (OrdinaryMorphism) StaticStep.executeColimBased(m2);
				if (co_match != null) {
					boolean result = false;
					if (this.grammar.isLayered()) {
						List<Formula> constraints = this.grammar.getConstraintsForLayer(-1);
						// first check global constraints
						if (this.grammar.checkGraphConsistency(co_match.getImage(),
								constraints)) {
							constraints = this.grammar.getConstraintsForLayer(m
									.getRule().getLayer());
							// now for the layer only
							if (this.grammar.checkGraphConsistency(co_match
									.getImage(), constraints)) 								
								result = true;			
							else 
								result = false;
						} else
							result = false;						
						co_match.dispose();
						m2.dispose();
						com.dispose();
						copy.dispose();
						return result;
					} else if (this.grammar.trafoByPriority()) {
						Vector<Formula> constraints = this.grammar
								.getConstraintsForPriority(-1);
						// first check global constraints
						if (this.grammar.checkGraphConsistency(co_match.getImage(),
								constraints)) {
							constraints = this.grammar.getConstraintsForPriority(m
									.getRule().getPriority());
							// now for the priority only
							if (this.grammar.checkGraphConsistency(co_match
									.getImage(), constraints))
								result = true;
							else
								result = false;
						} else
							result = false;
						co_match.dispose();
						m2.dispose();
						com.dispose();
						copy.dispose();
						return result;
					} else if (this.grammar.checkGraphConsistency(co_match
							.getImage())) {
						result = true;
					} else
						result = false;
					co_match.dispose();
					m2.dispose();
					com.dispose();
					copy.dispose();
					return result;
				} 
				m2.dispose();
				com.dispose();
				copy.dispose();
				return false;
			} catch (TypeException e) {
				System.out.println(e.getLocalizedMessage());
				m2.dispose();
				com.dispose();
				copy.dispose();
				return false;
			}
		}
		com.dispose();
		copy.dispose();
		return false;
	}

	protected boolean checkGraphConsistency() {
		if (this.grammar == null)
			return true;
		
		List<Formula> constraints = null;	
		if (this.grammar.isLayered()) 
			constraints = this.grammar.getConstraintsForLayer(-1);
		else if (this.grammar.trafoByPriority()) 
			constraints = this.grammar.getConstraintsForPriority(-1);
		else
			constraints = this.grammar.getGlobalConstraints();
		
		if (this.grammar.checkGraphConsistency(this.grammar.getGraph(), constraints)) {
			return true;			
		} 
		String msgstr = " Constraint:"+this.grammar.getConsistencyErrorMsg()+"- failed.";
		fireGraTra(new GraTraEvent(this,GraTraEvent.INCONSISTENT, msgstr));
		return false;	
	}
	
	protected boolean checkGraphConsistencyForLayer(int layer) {
		if (this.grammar == null)
			return true;
		
		List<Formula> constraints = this.grammar.getConstraintsForLayer(layer);
		if (this.grammar.checkGraphConsistency(this.grammar.getGraph(), constraints)) 								
			return true;			
		
		String msgstr = " Layer: "+layer+"  Constraint:"+this.grammar.getConsistencyErrorMsg()+"- failed.";
		fireGraTra(new GraTraEvent(this, GraTraEvent.INCONSISTENT, msgstr));
		return false;	
	}
	
	protected boolean checkGraphConsistencyForPriority(int priority) {
		if (this.grammar == null)
			return true;
		
		List<Formula> constraints = this.grammar.getConstraintsForPriority(priority);
		if (this.grammar.checkGraphConsistency(this.grammar.getGraph(), constraints)) 								
			return true;			
		
		String msgstr = " Priority: "+priority+"  Constraint:"+this.grammar.getConsistencyErrorMsg()+"- failed.";
		fireGraTra(new GraTraEvent(this,GraTraEvent.INCONSISTENT, msgstr));
		return false;	
	}
	
	//===================================================
	
	/** 
	 * Try to apply the specified RuleScheme by creating an amalgamated rule
	 * and amalgamated match. Such an amalgamated match is a union of all valid matches
	 * of each multi rule based on the valid match of the kernel rule of the scheme.
	 * 
	 *  NOTE: This work still in progress.	   
	 */
	public boolean apply(final RuleScheme rs) {	
		synchronized (this) {
			
		// long time0 = System.currentTimeMillis();
		
		this.stoppingRule = false;
		
		boolean result = false;

		this.currentMatch = rs.getMatch();
		if (this.currentMatch == null) {
			if (!rs.isInputParameterSet(true)) {
				fireGraTra(
					new GraTraEvent(this, GraTraEvent.INPUT_PARAMETER_NOT_SET, rs));
			}				
			this.currentMatch = rs.getMatch(this.hostgraph, this.strategy);
		}
		
		if (this.currentMatch == null) {
			fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_FAILED,
											"Amalgamated match failed.\n"+rs.getErrorMsg()));
			return false;
		}
					
		fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_VALID,
				this.currentMatch));
				
		if(this.stopping || this.stoppingRule) {			
			rs.disposeMatch(); 
			rs.disposeAmalgamatedRule();
			return false;
		}

		if(this.pauseRule)
		    return false;								

		try { // check attr context: variables only
			boolean checkVarsOnly = true;
			this.currentMatch.getAttrContext().getVariables()
							.getAttrManager().checkIfReadyToTransform(
									this.currentMatch.getAttrContext(),
									checkVarsOnly);
		} catch (AttrException ex) {
			fireGraTra(new GraTraEvent(this,
							GraTraEvent.NOT_READY_TO_TRANSFORM, rs.getName()));
			rs.disposeMatch();
			rs.disposeAmalgamatedRule();
			return false;
		}

		Morphism coMatch = apply(this.currentMatch);
		if (coMatch != null) {
			this.errorMsg = "";	
			rs.disposeMatch();
			rs.disposeAmalgamatedRule();
			coMatch = null;
			result = true;
		} 
		else {
			fireGraTra(new GraTraEvent(this, GraTraEvent.MATCH_FAILED,
					this.errorMsg));					
			rs.disposeMatch();	
			rs.disposeAmalgamatedRule();
			result = false;					
		}

		return result;
		}
	}
	
	protected boolean isInputParameterSet(
			final RuleScheme rs,
			boolean left) {
		
		return rs.isInputParameterSet(left);
	}
	
	
}
