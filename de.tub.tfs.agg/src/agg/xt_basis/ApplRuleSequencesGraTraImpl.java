/**
 * 
 */
package agg.xt_basis;


import java.util.Hashtable;
import java.util.List;

import agg.attribute.AttrException;
import agg.attribute.impl.VarTuple;
import agg.util.Pair;

/**
 * @author olga
 *
 */
public class ApplRuleSequencesGraTraImpl extends RuleSequencesGraTraImpl {

	private Rule preRule;
//	private int preRuleIndx;

	
	public ApplRuleSequencesGraTraImpl() {
		super();
	}
	
	public void dispose() {
		this.clearRuleSequence();			
		super.dispose();
	}
	
	public boolean apply() {
		if (this.ruleSequence != null) {
			this.ruleSequence.getMatchSequence().clearComatches();
			
			this.addGraTraListener(this.ruleSequence);
		}
		
		Pair<List<Pair<Rule, String>>, String> p;
		for (int i = 0; i < this.ruleSubsequences.size(); i++) {
			this.indx = -1;
			this.preRule = null;
			
			String rs = (i + 1) + ". subsequence: "
					+ this.ruleNameSequences.get(i);
			if (this.ruleSubsequences.size() > 1)
				System.out.println(rs);
			if (this.os != null) {
				writeTransformProtocol(rs);
			}

			p = this.ruleSubsequences.get(i);						
			apply(p.first, p.second);

//			System.out.println((i + 1) + ". subsequence applied");
//			System.out.println();
			if (this.os != null) {
				writeTransformProtocol(rs + "\t applied");
			}			
		}
		
		if (this.ruleSequence != null) {
			this.removeGraTraListener(this.ruleSequence);
		}
		
		return this.appliedOnce;
	}

	protected boolean apply(final List<Pair<Rule, String>> group, final String iters) {
		if (iters.equals("*")) {
//			System.out.println("\n	apply  *  times");
			this.appliedOnce = true;
			while (this.appliedOnce) {
				this.appliedOnce = false;
				long time0 = System.currentTimeMillis();
				for (int j = 0; j < group.size(); j++) {
					Pair<Rule, String> p = group.get(j);
					if (p.first == null)
						continue;
					
					this.currentRule = p.first;
					if (this.currentRule.isEnabled()) {
						apply(this.currentRule, p.second);
					}
				}
				System.out.println("used time: "+(System.currentTimeMillis()-time0)+"ms");
				if (this.os != null)
					writeUsedTimeToProtocol("used time: ", time0);
			}
		} else {
			long N = (new Long(iters)).longValue();
//			if (N > 1)
//				System.out.println("\n	apply  " + N + "  time(s)");
			for (long i = 0; i < N; i++) {
				
				long time0 = System.currentTimeMillis();
				
				for (int j = 0; j < group.size(); j++) {
					Pair<Rule, String> p = group.get(j);
					if (p.first == null)
						continue;
					
					this.currentRule = p.first;
					if (this.currentRule.isEnabled()) {
						apply(this.currentRule, p.second);
					}
				}
				System.out.println("used time: "+(System.currentTimeMillis()-time0)+"ms");
				if (this.os != null) 
					writeUsedTimeToProtocol("used time: ", time0);
			}
		}
		
		if (this.options.hasOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO)) {
			this.checkGraphConsistency();
		}
		
		return this.appliedOnce;
	}
	
	protected void apply(Rule r, String iters) {
		boolean ruleapplied = true;
		if (iters.equals("*")) {
//			System.out.println("\nrule: " + r.getName()+ "\t*  times");
			
			while (ruleapplied && !this.stopping) {
				ruleapplied = apply(r);

				if (ruleapplied) {
					this.appliedOnce = true;
				}

//				System.out
//						.println(r.getName() + " \t applied:  " + ruleapplied);
				
				if (this.os != null)					
					writeTransformProtocol(r.getName() + " \t applied:  "
							+ ruleapplied);

				if (!isGraphConsistent())
					this.stopping = true;
			}
		} else {
			long N = (new Long(iters)).longValue();
			
//			String str = "";
//			if (N > 1) 
//				str = "\t{"+ N+"} times";
//			System.out.println("apply rule: " + r.getName());
			
			if (this.options.hasOption(GraTraOptions.WAIT_AFTER_STEP)) {
				fireGraTra(
					new GraTraEvent(this, GraTraEvent.RULE, r));
			}
			
			if (this.ruleSequence != null) {
				this.indx++;
				int preIndx = this.indx-1;
				Hashtable<GraphObject, GraphObject> matchMap = 
						this.ruleSequence.getMatchSequence().getMatch(
								this.indx, 
								r,
								preIndx,
								this.preRule, this.grammar.getGraph());
								
				if (r.getMatch() == null) {
					this.currentMatch = this.grammar.createMatch(r);
					this.currentMatch.setCompletionStrategy(
								(MorphCompletionStrategy) this.strategy.clone(), true);
				}
				if (matchMap != null) {
					try {
						this.currentMatch.addMapping(matchMap);
					} catch (BadMappingException ex) {
						System.out.println("match mapping  FAILED!  "+ex.getMessage());
						this.currentMatch.clear();
					}
				}
			}
			
			for (long i = 0; i < N && !this.stopping; i++) {

				ruleapplied = apply(r);
				
				if (r.getMatch() != null)
					((VarTuple)r.getMatch().getAttrContext().getVariables()).unsetInputParameters();
				
				if (ruleapplied) {
					this.preRule = r;
					this.appliedOnce = true;
				}

				System.out
						.println(r.getName() + " \t applied:  " + ruleapplied);
				
				if (this.os != null)
					writeTransformProtocol(r.getName() + " \t applied:  "
							+ ruleapplied);

				if (!isGraphConsistent())
					this.stopping = true;
			}
		}
	}

	
	public boolean apply(Rule r) {
//		System.out.println("ApplRuleSequencesGraTraImpl.apply(Rule) : "+r.getName()+"   "+updateTypeObjectsMapAfterStep);
	
//		long time0 = System.currentTimeMillis();

		this.stoppingRule = false;		
		boolean result = false;
		boolean valid = false;

		this.currentMatch = r.getMatch();
		if (this.currentMatch == null) {
			this.currentMatch = this.grammar.createMatch(r);
			this.currentMatch.setCompletionStrategy(
						(MorphCompletionStrategy) this.strategy.clone(), true);
	//		strategy.showProperties();
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
				this.currentMatch.clear(); 
		        return false;
		    }
			
			if(this.pauseRule)
				return false; 
			
			valid = false;
			while (!valid) {
				if (this.currentMatch.isTotal()
						|| this.currentMatch.nextCompletion()) {
					if (this.currentMatch.isValid()) {
						valid = true;
//						matchCompletions++;
						
						if (r.isParallelApplyEnabled()
								&& this.currentMatch.typeObjectsMapChanged) {
							this.currentMatch.typeObjectsMapChanged = false;
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
				
				if(this.stopping || this.stoppingRule) {
					if (this.currentMatch != null) {
						this.currentMatch.clear(); 
					}
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
							GraTraEvent.NOT_READY_TO_TRANSFORM, r.getName()));
					// destroyMatch(currentMatch);					
					return false;
				}

				Morphism coMatch = apply(this.currentMatch);
				if (coMatch != null) {
					this.errorMsg = "";
					is_applied = true;
					
					this.currentMatch.clear();
					// destroyMatch(currentMatch);
					coMatch.dispose(); coMatch = null;
					result = true;
				} else {
					valid = false;
					fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
							this.currentMatch, this.errorMsg));					
					this.currentMatch.clear();
					// destroyMatch(currentMatch);
					
					result = false;					
				}
			} else {
				fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
						this.currentMatch, this.currentMatch.getErrorMsg()));				
				this.currentMatch.clear();
				// destroyMatch(currentMatch);
				
				result = false;
			}

			//
			if (r.isParallelApplyEnabled()) {
				if (!valid) {
					parallelApply = false;
					this.currentMatch.typeObjectsMapChanged = true;
				}
				if (is_applied) {
					result = true;
				}
			} else {
				parallelApply = false;
				break;
			}
			//   	      
		}
		
		return result;
	}

	protected boolean isInputParameterSet(
			final Graph g, 
			boolean left,
			final Match match) {

		if (match != null
				&& left
				&& this.ruleSequence != null 
//				&& !this.ruleSequence.getObjectFlow().isEmpty()
				&& match.getAttrContext().getVariables().areInputParametersSet()) {
			return true;
		}
		
		return super.isInputParameterSet(g, left, match);
	}
	
}
