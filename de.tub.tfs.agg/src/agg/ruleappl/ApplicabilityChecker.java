/**
 * 
 */
package agg.ruleappl;


import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarTuple;
import agg.parser.CriticalPair;
import agg.parser.CriticalPairOption;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePair;
import agg.parser.ExcludePairContainer;
import agg.parser.ExcludePairHelper;
import agg.parser.PairContainer;
import agg.parser.ParserFactory;
import agg.parser.SimpleExcludePair;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.ConcurrentRule;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
//import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * @author olga
 *
 */
public class ApplicabilityChecker implements Runnable {
	
	private GraGra gragra;	
	
	private final Completion_InjCSP strategy = new Completion_InjCSP();
		
	private MorphCompletionStrategy gragraStrategy;
		
	private CriticalPairOption cpOption;
	
	private RuleSequence ruleSequence;
		
	private final List<Rule> nonApplicableRules = new Vector<Rule>();
	
	private final List<ConcurrentRule> concurrentRules = new Vector<ConcurrentRule>();
	
	private final List<ConcurrentRule> applicableConcurrentRules = new Vector<ConcurrentRule>();
	
//	private boolean checked, 
//					applicabilityChecked, 
//					nonApplicabilityChecked;
	
	private String info = "";
	
	private int depth = -1;
	private boolean completeConcurrency;
	private boolean completeCPA;
	private boolean consistentConcurrency;
	private boolean completeConcurRuleBackward = true;
	private boolean ignoreDanglingEdgeOfDelNode;
	
	private boolean mainResult;
	

	
	public ApplicabilityChecker(
			final RuleSequence sequence,
			final GraGra grammar) {
		
		this.ruleSequence = sequence;
		this.gragra = grammar;
		this.cpOption = sequence.getCriticalPairOption();
		if (this.cpOption == null)
			this.cpOption = new CriticalPairOption();
		this.completeConcurRuleBackward = true;
		if (this.gragra != null)
			this.gragraStrategy = this.gragra.getMorphismCompletionStrategy();
	}
	
	public ApplicabilityChecker(
			final RuleSequence sequence,
			final GraGra grammar,
			final CriticalPairOption option) {
		
		this.ruleSequence = sequence;
		this.gragra = grammar;
		this.cpOption = option;
		this.completeConcurRuleBackward = true;
		this.gragraStrategy = this.gragra.getMorphismCompletionStrategy();
	}

//	public ApplicabilityChecker(
//			final RuleSequence sequence,
//			MorphCompletionStrategy strat) {
//		
//		this.ruleSequence = sequence;
//		this.cpOption = new CriticalPairOption();
//		this.completeConcurRuleBackward = false;
//		if (strat != null)
//			this.gragraStrategy = strat;
//	}
	
	public void setCriticalPairOption(final CriticalPairOption option) {
		this.cpOption = option;
	}
	
	public void run() {
		this.check();		
	}
	
	public boolean getResult() {
		return this.mainResult;
	}

	public void setDepthOfConcurrentRule(final int d) {
		this.depth = d;
	}
	
	public int getDepthOfConcurrentRule() {
		return this.depth;
	}
	
	/**
	 *  If the specified parameter is false,
	 *  only maximal intersection of rhs and lhs of base rules  
	 *  is taken into account for building a concurrent rule,
	 *  otherwise all possible intersections are used.
	 */
	public void setCompleteConcurrency(boolean b) {
		this.completeConcurrency = b;
	}
	
	public boolean getCompleteConcurrency() {
		return this.completeConcurrency;
	}
	
	/**
	 * Set the value of the local variable for checking concurrent rules.
	 * If the specified parameter is true,
	 * created concurrent rules will be checked by critical
	 * pair analysis (CPA), otherwise only its building rules. 
	 */
	public void setCompleteCPAOfConcurrency(boolean b) {
		this.completeCPA = b;
	}
	
	/**
	 * Returns the value of the local variable for checking concurrent rules.
	 */
	public boolean getCompleteCPAOfConcurrency() {
		return this.completeCPA;
	}
	
	public void setConsistentConcurrency(boolean b) {
		this.consistentConcurrency = b;
	}
	
	public boolean getConsistentConcurrency() {
		return this.consistentConcurrency;
	}
	
//	private void setCompletionConcurrentRuleBackward(boolean b) {
//		this.completeConcurrentRuleBackward = b;
//	}
	
	public boolean isCompletionConcurrentRuleForward() {
		return !this.completeConcurRuleBackward;
	}
	
	public boolean isCompletionConcurrentRuleBackward() {
		return this.completeConcurRuleBackward;
	}
	
	/**
	 * Set the value of the local variable for checking 
	 * the dangling edge condition when a rule is node-deleting.
	 */
	public void setIgnoreDanglingEdgeOfDelNode(boolean b) {
		this.ignoreDanglingEdgeOfDelNode = b;
	}
	
	/**
	 * Returns the value of the local variable for checking 
	 * the dangling edge condition when a rule is node-deleting.
	 */
	public boolean getIgnoreDanglingEdgeOfDelNode() {
		return this.ignoreDanglingEdgeOfDelNode;
	}
	
	public void dispose() {
		clear();
		this.gragra = null;
		this.cpOption = null;
		this.gragraStrategy = null;
		this.ruleSequence = null;
	}
	
	public void clear() {
		this.nonApplicableRules.clear();
		this.applicableConcurrentRules.clear();
		this.concurrentRules.clear();
	}
	
//	private void clearHelpContainerOfSequence() {
//		this.nonApplicableRules.clear();
//		this.applicableConcurrentRules.clear();
//		this.concurrentRules.clear();
//	}
			
	public RuleSequence getRuleSequence() {
		return this.ruleSequence;
	}
		
	
	public boolean check() {
		this.mainResult = false;
		
		if (this.ruleSequence != null) {
						
			if (this.ruleSequence.getGraph() == null) {
				this.mainResult = checkWithoutGraph();
			} else {
				this.mainResult = checkAtGraph();
			}
			
			this.ruleSequence.checked = true;
			
			// save created concurrent rules
//			this.ruleSequence.saveConcurrentRules();
		}
		
		return this.mainResult;
	}
	
	private boolean checkAtGraph() {
		System.out.println("\n*** ApplicabilityChecker.checkAtGraph    "
				+this.ruleSequence.getGraph().getName()
				+"   start at: "+this.ruleSequence.getStartIndexOfCheck()+"   "+this.ruleSequence.getStartRule().getName());
				
		this.clear();					
		if (this.ruleSequence.checked) {
			this.ruleSequence.reinit();
		}
				
		if (this.ruleSequence.getStartIndexOfCheck() > 0) {
			int preIndx = this.ruleSequence.getStartIndexOfCheck()-1;
			Rule preRule = this.ruleSequence.getRule(preIndx);
			boolean applicabilityOfPreRuleTrue = this.ruleSequence.getRuleApplicabilityResult(preIndx, preRule.getName());
			if (!applicabilityOfPreRuleTrue) {
				setApplicabilityResult(false, ApplicabilityConstants.ENABLING_PREDECESSOR);  						
				setNonApplicabilityResult(true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
				return false;
			}
		}
		
		boolean result1 = this.initializationRule(this.ruleSequence.getRules(), this.ruleSequence.getGraph());		
		if (!result1) {
//			this.checked = true;
//			this.applicabilityChecked = true;
			return result1;
		}
		
		boolean result2 = this.noNodeDeletingRules(this.ruleSequence.getRules());
		if (!result2 && !this.ruleSequence.getIgnoreDanglingEdgeOfDelNode()) {
//			this.checked = true;
//			this.applicabilityChecked = true;
			return result2;
		}
		
		boolean result3 = this.noImpedingPredecessors(this.ruleSequence.getRules());
		
		boolean result4 = true;
		if (this.completeConcurrency) {
			// all dependency overlapping will be consider
			result4 = this.enablingPredecessorApplicablePureConcurrent(
					this.ruleSequence.getStartIndexOfCheck(),
					this.ruleSequence.getRules(), 
					this.ruleSequence.getGraph());
		}
		else {
			// only dependences with max overlapping will be considered
			result4 = this.enablingPredecessorPureConcurrentApplicable(
					this.ruleSequence.getStartIndexOfCheck(),
					this.ruleSequence.getRules(), 
					this.ruleSequence.getGraph());
		}
		
//		this.checked = true;
//		this.applicabilityChecked = true;
		
		return result1 && result2 && result3 && result4;
	}
	
	public boolean checkWithoutGraph() {
		this.clear();		
		if (this.ruleSequence.checked) {
			this.ruleSequence.reinit();
		}
		
		if (this.ruleSequence.getStartIndexOfCheck() > 0) {
			int preIndx = this.ruleSequence.getStartIndexOfCheck()-1;
			Rule preRule = this.ruleSequence.getRule(preIndx);
			boolean applicabilityOfPreRuleTrue = this.ruleSequence.getRuleApplicabilityResult(preIndx, preRule.getName());					
			if (!applicabilityOfPreRuleTrue) {
				setApplicabilityResult(false, ApplicabilityConstants.ENABLING_PREDECESSOR);  						
				setNonApplicabilityResult(true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
				return false;
			}
		}		
		
		boolean result1 = this.initializationRule(this.ruleSequence.getRules(), null);		
		if (!result1) {
//			this.checked = true;
//			this.applicabilityChecked = true;
			return result1;
		}
		
		boolean result2 = this.noNodeDeletingRules(this.ruleSequence.getRules());	
		if (!result2) {
//			this.checked = true;
//			this.applicabilityChecked = true;
			return result2;
		}
		
		boolean result3 = this.noImpedingPredecessors(this.ruleSequence.getRules());
		
		boolean result4 = true;
				
		if (this.completeConcurrency) {
			// all dependency overlapping will be consider
			result4 = this.enablingPredecessorApplicablePureConcurrent(
					this.ruleSequence.getStartIndexOfCheck(),
					this.ruleSequence.getRules());
		}
		else {
			// only dependences with max overlapping will be considered
			result4 = this.enablingPredecessorPureConcurrentApplicable(
					this.ruleSequence.getStartIndexOfCheck(),
					this.ruleSequence.getRules());
		}
		
//		this.checked = true;
//		this.applicabilityChecked = true;
	
		return result1 && result2 && result3 && result4;
	}
	
	public List<ConcurrentRule> getConcurrentRules() {
		return this.concurrentRules;
	}
	
	public List<ConcurrentRule> getApplicableConcurrentRules() {
		return this.applicableConcurrentRules;
	}
	
	
	/* 
	 * Check 1. criterion of applicability :
	 * first rule is applicable on graph g via injective match
	 * (initialization)
	 */
	private boolean initializationRule(
			final List<Rule> sequence, 
			final Graph g) {
		
		boolean result = sequence.isEmpty();
		if (result) {
			setApplicabilityResult(true, ApplicabilityConstants.INITIALIZATION);
		} else {
			final Rule r = sequence.get(0);
			
			final Pair<Boolean, List<String>> 
			ruleRes = this.ruleSequence.getRuleResult(0, r.getName(), ApplicabilityConstants.INITIALIZATION);
			if (ruleRes != null) {
				result = ruleRes.first.booleanValue();
			}
			else {
				if (g != null) {
					result = initializationCheck(0, r, g);	
				} else {
					result = true;  // TODO: implementation			
				}	
			}
			
			if (result) {
				System.out.println("=== >>>  ApplicabilityChecker.initialization:  rule: "+r.getName()+"   applicable");
				setApplicabilityResult(true, ApplicabilityConstants.INITIALIZATION);				
				setNonApplicabilityResult(false, ApplicabilityConstants.INITIALIZATION_ERROR);
				
				setRuleResult(0, r.getName(), true, ApplicabilityConstants.INITIALIZATION, "");		
				setRuleResult(0, r.getName(), false, ApplicabilityConstants.INITIALIZATION_ERROR, "");
			} else {
				setApplicabilityResult(false, ApplicabilityConstants.INITIALIZATION);
				setNonApplicabilityResult(true, ApplicabilityConstants.INITIALIZATION_ERROR);
				
				setRuleResult(0, r.getName(), false, ApplicabilityConstants.INITIALIZATION, "");				
				setRuleResult(0, r.getName(), true, ApplicabilityConstants.INITIALIZATION_ERROR, "");
			} 
		}
		return result;
	}
	
	/* 
	 * Check 1. criterion of non-applicability:
	 * first rule is not applicable on graph g via injective match
	 * (initialization error)
	 */
	/*
	private boolean initializationError(
			final List<Rule> sequence, 
			final Graph g) {
		
		boolean result = sequence.isEmpty();
		if (!result) {
			final Rule r = sequence.get(0);
			result = initializationCheck(0, r, g);
			List<String> v = null;
			if (result) {
				setNonApplicabilityResult(false, ApplicabilityConstants.INITIALIZATION_ERROR);
				setRuleResult(0, r.getName(), false, ApplicabilityConstants.INITIALIZATION_ERROR, "");	
			} else {
				setRuleResult(0, r.getName(), true, ApplicabilityConstants.INITIALIZATION_ERROR, "");	
			}
		}
		return result;
	}
	
	*/
	
	private boolean isNonApplicableRule(final Rule r, final Graph g) {
		boolean result = true;
		Match m = BaseFactory.theFactory().createMatch(r, g);	
		if (m != null) {
			setMatchCompletionStrategy(m, this.gragraStrategy, true);
			m.enableInputParameter(false);
			while (m.nextCompletion()) {
				m.clearErrorMsg();
				if (m.isValid()) {
					result = false;
					break;
				} 
			} 
			m.dispose();
		}
		return result;
	}
	
	private Hashtable<GraphObject, GraphObject> makeMatchMapByObjectFlow(
			int indx, 
			final Rule r, 
			final Graph g) {
		
		int sizeOfObjectFlow = this.ruleSequence.getSizeOfObjFlowForRule(r, indx);
		if (sizeOfObjectFlow == 0)
			return null;
		
		final List<ObjectFlow> objFlowList = this.ruleSequence.getObjFlowForRule(r, indx);
		
		Hashtable<GraphObject, GraphObject> 
		matchmap = this.ruleSequence.getMatchSequence().makeMatchMapByObjectFlow(r, objFlowList);
		
		boolean result = (matchmap.size() == sizeOfObjectFlow);
		if (!result) {
			Rule ri = r;
			int i = indx;
			final Hashtable<GraphObject, GraphObject> 
			inputToPostInput = new Hashtable<GraphObject, GraphObject>();
			while (ri != null) {	
				matchmap.putAll(this.ruleSequence.getInput2outputMapIntoGraphAbovePreRule(
						r, indx, objFlowList, 
						ri, i, 
						inputToPostInput, 
						g));
				result = (matchmap.size() == sizeOfObjectFlow);
				if (!result) {
					i--;
					ri = this.ruleSequence.getRule(i);
				} else
					break;
			}	
		}
		return matchmap;
	}
	
	private boolean initializationCheck(int indx, final Rule r, final Graph g) {
		if (g == null) {
			return true;
		}
		
		boolean result = false;		
		Match m = BaseFactory.theFactory().createMatch(r, g);	
		if (m != null) {
			setMatchCompletionStrategy(m, this.gragraStrategy, true);
			m.enableInputParameter(false);			
			// try to use object flow
			if (!r.getLeft().isEmpty() && this.ruleSequence.isObjFlowActive()) {
				Hashtable<GraphObject, GraphObject> 
				matchmap = makeMatchMapByObjectFlow(indx, r, g);
				if (matchmap != null && !matchmap.isEmpty()) {
					try {
						m.addMapping(matchmap);
						m.setPartialMorphismCompletion(true);
					} catch (BadMappingException ex) {
						// break when OF does not usable
						m.dispose();
						return false;
					}					
					if (m.isTotal()) {
						m.clearErrorMsg();
						if (m.isAttrConditionSatisfied()
								&& m.areNACsSatisfied()
								&& m.arePACsSatisfied()
								&& m.isValid()) {
							result = true;
						} 
					}
				}
			}
			while (!result && m.nextCompletion()) {
				m.clearErrorMsg();
				if (m.isValid()) {
					result = true;
					break;
				} 
			} 			
			if (result) {
				// add match to matchSequence
				this.ruleSequence.getMatchSequence().addDirectMatch(r, m);
			} 
			m.dispose();
		}
		return result;
	}

	private boolean isRuleApplicable(int indx, final Rule r, final Graph g, boolean checkIfRuleReadyToTransform) {
		if (checkIfRuleReadyToTransform && r.isReadyToTransform()
				|| !checkIfRuleReadyToTransform)
			return this.initializationCheck(indx, r, g);
		
		return false;
	}
	
	private Hashtable<GraphObject, GraphObject> makeMatchMapByObjectFlow(
			final ConcurrentRule cr, 
			int indx, 
			final Graph g) {
		
		int sizeOfObjectFlow = cr.getSizeOfReflectedInputObjectFlow();
		if (sizeOfObjectFlow == 0)
			return null;
		
		Hashtable<GraphObject, GraphObject> 
		matchmap = cr.applyReflectedObjectFlowToMatchMap(g);
		
		boolean result = (matchmap.size() == sizeOfObjectFlow);
		if (!result) {			
			final List<ObjectFlow> 
			objFlowList = this.ruleSequence.getObjFlowForRule(cr.getLastSecondSourceRule(), indx+cr.getDepth());
			Rule ri = cr.getLastSecondSourceRule();	
			int i = indx+cr.getDepth();
			final Hashtable<GraphObject, GraphObject> 
			inputToPostInput = new Hashtable<GraphObject, GraphObject>();
			while (ri != null) {
				matchmap.putAll(this.ruleSequence.getReflectedObjectFlowOfGraphAndPreRule(
						cr, 						
						objFlowList, 
						ri, i, 
						inputToPostInput, 
						g));
				result = (matchmap.size() == sizeOfObjectFlow); 
				if (!result) {
					i--;
					ri = this.ruleSequence.getRule(i);
				} else 
					break;
			}	
		}
		return matchmap;
	}
	
	private boolean isRuleApplicable(
			final Rule r, 
			final ConcurrentRule cr, 
			final Graph g,
			boolean checkIfRuleReadyToTransform) {
				
		if (cr.getRule().isNotApplicable())
			return false;
		
//		boolean failed = false;
		boolean result = false;
		boolean crReady = cr.isReadyToTransform();	
//		((VarTuple)cr.getRule().getAttrContext().getVariables()).showVariables();
		if (checkIfRuleReadyToTransform && crReady
				|| !checkIfRuleReadyToTransform) {
				
			Match m = cr.getRule().getMatch();
			if (m == null)
				m = BaseFactory.theFactory().createMatch(cr.getRule(), g);
				
			if (m != null) {
//				System.out.println("##########   "+m.getRule().getName());
				setMatchCompletionStrategy(m, this.gragraStrategy, true); // injective	
//				m.getCompletionStrategy().showProperties();
				m.enableInputParameter(false);						
				
				boolean withOF = false;
				// LHS of CR is empty
				if (cr.getRule().getLeft().isEmpty()) {
					if (m.areNACsSatisfied()
							&& m.arePACsSatisfied()) {
						result = true;
					} else {
						m.dispose();
						return false;
					}
				} 
				// does ObjectFlow of CR exist
				else if (this.ruleSequence.isObjFlowActive()) {
					withOF = true;
					if (cr.getSizeOfReflectedInputObjectFlow() > 0) {
						Hashtable<GraphObject, GraphObject> 
						matchmap = makeMatchMapByObjectFlow(
									cr,
									this.ruleSequence.getIndexOf(cr.getFirstSourceRule()), 
									this.ruleSequence.getGraph());
							
						if ((matchmap == null)
	//							|| (cr.getSizeOfReflectedInputObjectFlow() != matchmap.size())
								) {
								// break because usage of object flow failed
								m.dispose();
								return false;
						}
						
						try {
							m.addMapping(matchmap);
							m.setPartialMorphismCompletion(true);
						} catch(BadMappingException ex) {			
							// break because usage of object flow failed
							m.dispose();
							return false;
						}
					}				
					if (m.isTotal()) {
						m.clearErrorMsg();
						if (m.isAttrConditionSatisfied()
								&& m.areNACsSatisfied()
								&& m.arePACsSatisfied()
								&& m.isValid()) {
							result = true;
						} else {
							m.dispose();
							return false;
						}
					} else {
						if (this.completeConcurRuleBackward)
							m.getCompletionStrategy().getProperties().clear(CompletionPropertyBits.INJECTIVE);
						m.getCompletionStrategy().initialize(m);
						while (m.nextCompletion()) {
							if (m.isAttrConditionSatisfied()
									&& m.areNACsSatisfied()
									&& m.arePACsSatisfied()
									&& m.isValid()) {
								result = true;
								cr.setInjectiveMatchProperty(false);
								// add concurrent match to matchSequence
								this.ruleSequence.getMatchSequence().addConcurrentSourceMatch(r, cr, m);
								System.out.println("=== >>>  ApplicabilityChecker.isRuleApplicable   concurrent rule:  "+cr.getRule().getName()+"  match found  ");
							}
						}
						if (!result) {
							m.dispose();
							cr.getRule().setApplicable(false);
							return false;
						}
					}
					
				}
				// do more if needed 
				if (!result || !withOF) {
					// do injective match completion
					while (!result && m.nextCompletion()) {
						if (!m.isAttrConditionSatisfied()) {
							if (this.usingAttrConditionAndInputParameter(m.getRule(), m)) {
								result = true;
							} else {
								m.dispose();
								return false;
							}
						}
						if (m.isValid()) {
							result = true;
							cr.setInjectiveMatchProperty(true);
							// add concurrent match to matchSequence
							this.ruleSequence.getMatchSequence().addConcurrentSourceMatch(r, cr, m);
							System.out.println("=== >>>  ApplicabilityChecker.isRuleApplicable   concurrent rule:  "+cr.getRule().getName()+"   INJECTIVE match found  ");
							break;
						} 
					}
					
					if (!result) {											
						// try non-injective match	
						m.getCompletionStrategy().getProperties().clear(CompletionPropertyBits.INJECTIVE);
	//					m.getCompletionStrategy().showProperties();
						m.getCompletionStrategy().initialize(m);							
		
						while (m.nextCompletion()) {
							if (!m.isAttrConditionSatisfied()) {
								if (this.usingAttrConditionAndInputParameter(m.getRule(), m)) {
									result = true;
								} else {
									m.dispose();
									return false;
								}
							}								
							if (m.isValid()) {
								result = true;
								cr.setInjectiveMatchProperty(false);
								// add concurrent match to matchSequence
								this.ruleSequence.getMatchSequence().addConcurrentSourceMatch(r, cr, m);
								System.out.println("=== >>>  ApplicabilityChecker.isRuleApplicable   concurrent rule:  "+cr.getRule().getName()+"   NON-INJECTIVE match found  ");
								break;
							}  
						}
					} 
					m.dispose();
				}
			}
		}	
		return result;
	}

	
	private void setMatchCompletionStrategy(
			final Match m, 
			final MorphCompletionStrategy strat,
			boolean injective) {
		m.setCompletionStrategy(strat, true);
		if (injective)
			m.getCompletionStrategy().getProperties().set(CompletionPropertyBits.INJECTIVE);
		else
			m.getCompletionStrategy().getProperties().clear(CompletionPropertyBits.INJECTIVE);
//		m.getCompletionStrategy().showProperties();
	}
	
	/* 
	 * Check 2. criterion of applicability :
	 * each rule occurring in sequence is non-deleting on nodes
	 * (no node-deleting rules)
	 */	
	private boolean noNodeDeletingRules(final List<Rule> sequence) {
		boolean result = true;
		for (int i=0; i<sequence.size(); i++) {
			Rule r = sequence.get(i);
//			System.out.println(r.getName());
			final Pair<Boolean, List<String>> 
			ruleRes = this.ruleSequence.getRuleResult(i, r.getName(), ApplicabilityConstants.NO_NODE_DELETING);
			if (ruleRes != null) {
				if (!ruleRes.first.booleanValue())
					result = false;
			} 
			else {
				if (//r.isNodeDeleting() 
						//&& 
						r.mayCauseDanglingEdge()
				) {
					result = false;		
					setRuleResult(i, r.getName(), false, ApplicabilityConstants.NO_NODE_DELETING, "");	
					System.out.println("=== >>> ApplicabilityChecker.noNodeDeletingRules::  FAILED!  rule: "+r.getName()+"  may cause dangling edge.");
				} else {
					setRuleResult(i, r.getName(), true, ApplicabilityConstants.NO_NODE_DELETING, "");	
				}
			}
		}	
		if (!result && !this.ruleSequence.getIgnoreDanglingEdgeOfDelNode()) {
			setApplicabilityResult(false, ApplicabilityConstants.NO_NODE_DELETING);
		}
		return result;
	}
	

	/* 
	 * Check 3. criterion of applicability :
	 * for all (r_i,r_j) in sequence with 1<=j<i<=n, r_i is asymmetrically parallel
	 * independent on r_j
	 * (no impeding predecessors)
	 */
	private boolean noImpedingPredecessors(
			final List<Rule> sequence) {
		
		boolean result = true;
		for (int i=1; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			boolean localResult = true;
			final Pair<Boolean, List<String>> 
			ruleRes = this.ruleSequence.getRuleResult(i, ri.getName(), ApplicabilityConstants.NO_IMPEDING_PREDECESSORS);
			if (ruleRes != null) {
				localResult = ruleRes.first.booleanValue();
			} 
			else {
				for (int j=0; j<i; j++) {
					Rule rj = sequence.get(j);
					final SimpleExcludePair excludePair = makeExcludePair();
					if(!asymParallelIndependentByCPA(excludePair, rj, j, ri, i)) {					
						localResult = false;
						setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_IMPEDING_PREDECESSORS, rj.getName());	
						break; // evntl. weiter machen!
					} 
					excludePair.dispose();
				}
				if (localResult) {
					setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_IMPEDING_PREDECESSORS, "");
				} 
			}
			// rewrite result only if localResult is FALSE
			if (!localResult) {
				result = false;
			}
		}
		
		if (!result) {
			setApplicabilityResult(false, ApplicabilityConstants.NO_IMPEDING_PREDECESSORS);
		}
		return result;
	}

	/*
	private ExcludePairContainer makeExcludePairContainer() {
		PairContainer 
		pc = ParserFactory.createEmptyCriticalPairs(gragra, CriticalPairOption.EXCLUDEONLY, false);
		
		((ExcludePairContainer) pc).enableComplete(
				cpOption.completeEnabled());
		((ExcludePairContainer) pc).enableNACs(
				cpOption.nacsEnabled());
		((ExcludePairContainer) pc).enablePACs(
				cpOption.pacsEnabled());
		((ExcludePairContainer) pc).enableReduce(
				cpOption.reduceEnabled());
		((ExcludePairContainer) pc).enableConsistent(
				cpOption.consistentEnabled());
		((ExcludePairContainer) pc).enableStrongAttrCheck(
				cpOption.strongAttrCheckEnabled());
		((ExcludePairContainer) pc).enableIgnoreIdenticalRules(
				cpOption.ignoreIdenticalRulesEnabled());
		((ExcludePairContainer) pc).enableReduceSameMatch(
				cpOption.reduceSameMatchEnabled());
		return (ExcludePairContainer) pc;
	}
*/
	
	private DependencyPairContainer makeDependencyPairContainer() {
		PairContainer 
		pc = ParserFactory.createEmptyCriticalPairs(this.gragra, 
											CriticalPair.TRIGGER_DEPENDENCY, 
											false);
		((DependencyPairContainer) pc).enableComplete(
				this.cpOption.completeEnabled());
		((DependencyPairContainer) pc).enableNACs(
				this.cpOption.nacsEnabled());
		((DependencyPairContainer) pc).enablePACs(
				this.cpOption.pacsEnabled());
		((DependencyPairContainer) pc).enableReduce(
				this.cpOption.reduceEnabled());
		((DependencyPairContainer) pc).enableConsistent(
				this.cpOption.consistentEnabled());
		((DependencyPairContainer) pc).enableStrongAttrCheck(
				this.cpOption.strongAttrCheckEnabled());
		((DependencyPairContainer) pc).enableEqualVariableNameOfAttrMapping(
				this.cpOption.equalVariableNameOfAttrMappingEnabled());
		((DependencyPairContainer) pc).enableIgnoreIdenticalRules(
				this.cpOption.ignoreIdenticalRulesEnabled());
		((DependencyPairContainer) pc).enableReduceSameMatch(
				this.cpOption.reduceSameMatchEnabled());
		((DependencyPairContainer) pc).enableDirectlyStrictConfluent(
				this.cpOption.directlyStrictConflEnabled());
		((DependencyPairContainer) pc).enableDirectlyStrictConfluentUpToIso(
				this.cpOption.directlyStrictConflUpToIsoEnabled());
		((DependencyPairContainer) pc).enableNamedObjectOnly(
				this.cpOption.namedObjectEnabled());
		return (DependencyPairContainer) pc;
	}
	
	private SimpleExcludePair makeExcludePair() {
		final SimpleExcludePair pc = new SimpleExcludePair();		
		pc.enableNACs(this.cpOption.nacsEnabled());
		pc.enablePACs(this.cpOption.pacsEnabled());		
		pc.enableReduce(this.cpOption.reduceEnabled());
		pc.enableConsistent(
				this.cpOption.consistentEnabled(), this.gragra);
		pc.enableStrongAttrCheck(true); // cpOption.strongAttrCheckEnabled());
		pc.enableEqualVariableNameOfAttrMapping(
				this.cpOption.equalVariableNameOfAttrMappingEnabled());
		pc.enableIgnoreIdenticalRules(
				this.cpOption.ignoreIdenticalRulesEnabled());
		pc.enableReduceSameMatch(
				this.cpOption.reduceSameMatchEnabled());
		pc.enableDirectlyStrictConfluent(false);
		pc.enableDirectlyStrictConfluentUpToIso(false);
		pc.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
		return pc;
	}

	/*
	private boolean asymParallelIndependentByCPA(
			final ExcludePairContainer excludeContainer, 
			final Rule r1, 
			int indx_r1,
			final Rule r2,
			int indx_r2) {	
		boolean result = false;
		try {
			if (!this.gragra.isLayered()
					|| r1.getLayer() == r2.getLayer()) {
//				result = (excludeContainer.getCriticalPair(r1, r2, CriticalPair.CONFLICT, true) == null) ? true : false;
				final Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
				conflicts = excludeContainer.getCriticalPair(r1, r2, CriticalPair.CONFLICT, true);
				if (conflicts != null && !conflicts.isEmpty()) {
					result = false;
					for (int i=0; i<conflicts.size(); i++) {
						final Pair<OrdinaryMorphism, OrdinaryMorphism>
						pair = conflicts.get(i).first;
						if (this.ruleSequence.isObjectFlowActive()) {
							ObjectFlow objFlow = this.ruleSequence.getObjectFlowForRules(r1, indx_r1, r2, indx_r2);
							if (objFlow != null && !objFlow.isEmpty()) {
								boolean inside = false;
								final List<Object> inputs = objFlow.getInputs();
								final Enumeration<GraphObject> objs = pair.second.getDomain();
								while (objs.hasMoreElements() && !inside) {
									final GraphObject obj = objs.nextElement();
									if (pair.first.getInverseImage(pair.second.getImage(obj)).hasMoreElements()
											&& inputs.contains(obj)) {
										inside = true;
									}
								}
								if (!inside) {
									conflicts.remove(i);
									i--;
//									System.out.println("Conflict overlapping removed, because Object Flow outside of it.");
								}
							}
							
						}
					}
				} else {
					result = true;
				}
			
			} else {
				return true;
			}
		} catch (Exception ex) {}
		
		return result;
	}
	*/
	
	private boolean asymParallelIndependentByCPA(
			final ExcludePair excludePair, 
			final Rule r1, 
			int indx_r1,
			final Rule r2,
			int indx_r2) {	
		boolean result = false;
		try {
			if (!this.gragra.isLayered()
					|| r1.getLayer() == r2.getLayer()) {
				final Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
				conflicts = excludePair.isCritical(CriticalPair.CONFLICT, r1, r2);
				if (conflicts != null && !conflicts.isEmpty()) {
					result = false;
					if (indx_r1 >=0 && indx_r1 >= 0) {
						for (int i=0; i<conflicts.size(); i++) {
							final Pair<OrdinaryMorphism, OrdinaryMorphism>
							pair = conflicts.get(i).first;
							if (this.ruleSequence.isObjFlowActive()) {
								ObjectFlow objFlow = this.ruleSequence.getObjFlowForRules(r1, indx_r1, r2, indx_r2);						
								if (objFlow != null && !objFlow.isEmpty()) {
									boolean inside = false;
									final List<Object> inputs = objFlow.getInputs();
									final Enumeration<GraphObject> objs = pair.second.getDomain();
									while (objs.hasMoreElements() && !inside) {
										final GraphObject obj = objs.nextElement();
										if (pair.first.getInverseImage(pair.second.getImage(obj)).hasMoreElements()
												&& inputs.contains(obj)) {
											inside = true;
										}
									}
									if (!inside) {
										conflicts.remove(i);
										i--;
//										System.out.println("Conflict overlapping removed, because Object Flow outside of it.");
									}
								}
							}
						}
					}
				} else {
					result = true;
				}
			}  else {
				result = true;
			}
		} catch (Exception ex) {}
		
		return result;
	}

	/*
	private boolean asymParallelIndependentByCPA(final Rule r1, final Rule r2, final Graph graph) {		
		final CriticalRulePairAtGraph crp = new CriticalRulePairAtGraph(r1, r2, graph);
//		final Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> 
//		crpResult = crp.isCriticalAtGraph(); 
		return (crp.isCriticalAtGraph() == null);
	}
	*/
	
	/** 
	 * Check 4a. criterion of applicability :
	 * for all r_i without NACs in sequence with 1<i<=n which are no applicable on graph g
	 * via an injective match there exists a rule r_j in sequence with 1<=j<i<=n
	 * and r_i is purely sequential dependent on r_j
	 * (pure enabling predecessor)
	 */
	private boolean pureEnablingPredecessor(
			final Rule ri,
			final int i,
			final List<Rule> sequence, 
			final Graph g) {
		
		boolean result = false;
		
		for (int j=0; j<i; j++) {
			final Rule rj = sequence.get(j);
			
			if ( (g != null && !checkForbiddenObjects(ri.getNACs(), rj, g))
					|| (g == null && !checkForbiddenObjects(ri.getNACs(), rj)) ) {
				
				if (purelySequentialDependent(rj, j, ri, i, g)) {							
					result = true;	
					System.out.println("=== >>>  ApplicabilityChecker.pureEnablingPredecessor  of  rule: "+ri.getName() +"  is  rule: "+rj.getName());

//					setRuleResult( i, ri.getName(), true, ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, rj.getName());	
					setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "pure");
					break;
				} 
			}
		}					
		return result;
	}

	private boolean pureEnablingPredecessor(
			final Rule ri,
			final int i,
			final List<Rule> sequence) {
		
		boolean result = false;
		for (int j=0; j<i; j++) {
			final Rule rj = sequence.get(j);
			if (!checkForbiddenObjects(ri.getNACs(), rj)) {
				if (purelySequentialDependent(rj, j, ri, i, null)) {							
					result = true;	
					System.out.println("=== >>>  ApplicabilityChecker.pureEnablingPredecessor  of  rule: "+ri.getName() +"  is  rule: "+rj.getName());

					setRuleResult( i, ri.getName(), true, ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, rj.getName());	
					setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "pure");
					break;
				} 
			}
		}					
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean enablingPredecessorApplicablePureConcurrent(
			int startIndx,
			final List<Rule> sequence, 
			final Graph graph) {
	
		System.out.println("=== >>>  ApplicabilityChecker.enablingPredecessorApplicablePureConcurrent" );
				
		DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		
		String criterion = "";
		boolean result = true;
		boolean noEnablingPredecessor = false;
		
		int start = (startIndx > 1) ? startIndx : 1;
		
		for (int i=start; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			Rule ri_1 = sequence.get(i-1);
								
			criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
			result = this.isPredecessorNotNeeded(ri, i, graph);
			if (!result) {
				this.nonApplicableRules.add(ri);
				setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");								

				noEnablingPredecessor = noEnablingPredecessor(sequence, i, ri, dependencyContainer);				
				if (!noEnablingPredecessor) {
				
					criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR;
					result = this.pureEnablingPredecessor(ri, i, sequence, graph);												
					if (!result) {
						setRuleResult(i, ri.getName(), false,  ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, "");	
						
						criterion = ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR;					
						result = this.partialEnablingPredecessor(ri, i, sequence, graph);
						if (!result) {
							setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR, "");		
											
							criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
							result = this.directEnablingPredecessor(i, ri, sequence, graph);						
							if (!result) {
								setRuleResult(i, ri.getName(), false, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");		
							}
						}
					} 
				}	
			}		

			setApplicabilityResult(result, ApplicabilityConstants.ENABLING_PREDECESSOR); 
			setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR);					
		}		
		
		dependencyContainer.clear();
		
		return result;
	}

	@SuppressWarnings("unused")
	private boolean enablingPredecessorApplicablePureConcurrent(
			int startIndx,
			final List<Rule> sequence) {
	
		System.out.println("=== >>>  ApplicabilityChecker.enablingPredecessorApplicablePureConcurrent" );
				
		DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		
		String criterion = "";
		boolean result = true;
		boolean noEnablingPredecessor = false;
		
		int start = (startIndx > 1) ? startIndx : 1;
		
		for (int i=start; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			Rule ri_1 = sequence.get(i-1);
								
			criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
			result = this.isPredecessorNotNeeded(ri, i, null);
			if (!result) {
				this.nonApplicableRules.add(ri);
				setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");								

				noEnablingPredecessor = noEnablingPredecessor(sequence, i, ri, dependencyContainer);				
				if (!noEnablingPredecessor) {
				
					criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR;
					result = this.pureEnablingPredecessor(ri, i, sequence);												
					if (!result) {
						setRuleResult(i, ri.getName(), false,  ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, "");	
						
						criterion = ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR;					
						result = this.partialEnablingPredecessor(ri, i, sequence, null);
						if (!result) {
							setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR, "");		
											
							criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
							result = this.directEnablingPredecessor(i, ri, sequence, null);						
							if (!result) {
								setRuleResult(i, ri.getName(), false, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");		
							}
						}
					} 
				}	
			}		

			setApplicabilityResult(result, ApplicabilityConstants.ENABLING_PREDECESSOR); 
			setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR);					
		}		
		
		dependencyContainer.clear();
		
		return result;
	}
	
	private boolean isRuleWithEmptyLHSApplicableAtGraph(
			final Rule r,
			int indx,
			final Graph g) {
		boolean result = false;
		if (r.getLeft().isEmpty()) {
//			String criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
			if (this.isRuleApplicable(indx, r, g, true)) {
				result = true;
				System.out.println("=== >>>   ApplicabilityChecker.isRuleWithEmptyLHSApplicableAtGraph: rule: "+r.getName()+"  applicable");
				setRuleResult(indx, r.getName(), true, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "(applicable)");	
				setRuleResult(indx, r.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "(applicable)");			
			} else {
				result = false;
				this.nonApplicableRules.add(r);					
				setRuleResult(indx, r.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");	
			}
		}
		return result;
	}
	
	private boolean isPredecessorNotNeeded(
			final Rule r,
			int indx,
			final Graph g) {
		boolean result = false;
		result = this.isRuleApplicable(indx, r, g, true);
		if (result) {
			setRuleResult(indx, r.getName(), true, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "(applicable)");	
			setRuleResult(indx, r.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "(applicable)");			
		} 
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean enablingPredecessorPureConcurrentApplicable(
			int startIndx,
			final List<Rule> sequence, 
			final Graph graph) {	
		
		DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		
		boolean result = true;
		String criterion = "";
		boolean noEnablingPredecessor = false;
		
		int start = (startIndx > 1) ? startIndx : 1;
		
		for (int i=start; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			Rule ri_1 = sequence.get(i-1);				
			System.out.println("=== >>>ApplicabilityChecker.enablingPredecessorPureConcurrentApplicable:  check rule: "+ri.getName());
			
			// extra case when LHS of the rule is empty
			criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
			result = isRuleWithEmptyLHSApplicableAtGraph(ri, i, graph);			
			if (!result) {	
				// check is ri applicable in general (do not respect ObjectFlow)
				if (this.isNonApplicableRule(ri, graph)) {
					noEnablingPredecessor = noEnablingPredecessor(sequence, i, ri, dependencyContainer); 
				}
				
				if (!noEnablingPredecessor) {
					criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR; 						
					result = this.pureEnablingPredecessor(ri, i, sequence, graph);												
					if (!result) {
						setRuleResult(i, ri.getName(), false,  ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, "");	
							
						criterion = ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR;					
						result = this.partialEnablingPredecessor(ri, i, sequence, graph);
						if (!result) {
							setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR, "");		
							
							criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
							result = this.directEnablingPredecessor(i, ri, sequence, graph);						
							if (!result) {
								setRuleResult(i, ri.getName(), false, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");		
								
								criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
								result = this.isPredecessorNotNeeded(ri, i, graph);
								if (!result) {
									this.nonApplicableRules.add(ri);
									setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");	
										
									// rule ri is not applicable
									// and there are no enabling predecessors -
									// - this implies that sequence is not applicable!
									noEnablingPredecessor = true;
									setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
									
	//								setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
								}
							}							
						} 
					} else {
						Pair<Boolean, List<String>> resPair = this.ruleSequence.getRuleResult(i, ri.getName(), ApplicabilityConstants.PURE_ENABLING_PREDECESSOR);
						if (resPair != null && !resPair.first.booleanValue()) {
							result = false;
							setRuleResult(i, ri.getName(), true, ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, ri_1.getName());
						} 							
					}
				} else {
					criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
					result = this.isPredecessorNotNeeded(ri, i, graph);
					if (!result) {
						this.nonApplicableRules.add(ri);
						setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");	
						
						// rule ri is not applicable
						// and there are no enabling predecessors -
						// - this implies that sequence is not applicable!
						noEnablingPredecessor = true;
						setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
					}
				}
			}
			
			setApplicabilityResult(result, ApplicabilityConstants.ENABLING_PREDECESSOR);  						
			setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
		
			if (noEnablingPredecessor) {
				if (this.ruleSequence.getNonApplicabilityResult().first.booleanValue()) {
					break;
				}
			}
		}	
				
		dependencyContainer.clear();
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean enablingPredecessorPureConcurrentApplicable(
			int startIndx,
			final List<Rule> sequence) {	
		
		DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		
		boolean result = true;
		String criterion = "";
		boolean noEnablingPredecessor = false;
		
		int start = (startIndx > 1) ? startIndx : 1;
		
		for (int i=start; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			Rule ri_1 = sequence.get(i-1);				
			System.out.println("=== >>>ApplicabilityChecker.enablingPredecessorPureConcurrentApplicable:  check rule: "+ri.getName());
			
			// extra case when LHS of the rule is empty
			criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
			result = isRuleWithEmptyLHSApplicableAtGraph(ri, i, null);			
			if (!result) {								
				noEnablingPredecessor = noEnablingPredecessor(sequence, i, ri, dependencyContainer); 
				
				if (!noEnablingPredecessor) {
					criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR; 						
					result = this.pureEnablingPredecessor(ri, i, sequence, null);												
					if (!result) {
						setRuleResult(i, ri.getName(), false,  ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, "");	
							
						criterion = ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR;					
						result = this.partialEnablingPredecessor(ri, i, sequence, null);
						if (!result) {
							setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR, "");		
											
							criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
							result = this.directEnablingPredecessor(i, ri, sequence, null);						
							if (!result) {
								setRuleResult(i, ri.getName(), false, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");		
							
								criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
								result = this.isPredecessorNotNeeded(ri, i, null);
								if (!result) {
									this.nonApplicableRules.add(ri);
									setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");	
									
									// rule ri is not applicable
									// and there are no enabling predecessors -
									// - this implies that sequence is not applicable!
									noEnablingPredecessor = true;
									setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
								
//									setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
								}
							}
						}
					} 
				} else {
					criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
					result = this.isPredecessorNotNeeded(ri, i, null);
					if (!result) {
						this.nonApplicableRules.add(ri);
						setRuleResult(i, ri.getName(), false, ApplicabilityConstants.PREDECESSOR_NOT_NEEDED, "");	
						
						// rule ri is not applicable
						// and there are no enabling predecessors -
						// - this implies that sequence is not applicable!
						noEnablingPredecessor = true;
						setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
					}
				}
			}
			
			setApplicabilityResult(result, ApplicabilityConstants.ENABLING_PREDECESSOR);  						
			setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR); 
		
			if (noEnablingPredecessor) {
				if (this.ruleSequence.getNonApplicabilityResult().first.booleanValue()) {
					break;
				}
			}
		}	
				
		dependencyContainer.clear();
		
		return result;
	}

	
	public List<ConcurrentRule> buildPlainConcurrentRule(final List<Rule> seq, final Graph g) {	
		if (seq.size()<=1 
				|| BaseFactory.theFactory().checkApplCondsOfRules(seq) != null) {
			return null;
		}

		List<ConcurrentRule> crs = new Vector<ConcurrentRule>(1);
	 	for (int i=1; i<seq.size(); i++) {
	 		Rule ri = seq.get(i);
	 		int preI = i-1;
	 		Rule preR = seq.get(preI);
	 		List<List<ConcurrentRule>> 
			concurRuleListsOfRule = this.getListsOfConcurrentRulesOfRule(ri, i);
	 		if (concurRuleListsOfRule ==  null) {
				concurRuleListsOfRule = new Vector<List<ConcurrentRule>> ();
				this.ruleSequence.putListsOfConcurrentRules(ri, i, concurRuleListsOfRule);
			}
	 		List<ConcurrentRule> list = null;
	 		if (i==1) {
	 			list = this.makeConcurrentRulesDuetoDependency(preR, preI, ri, i, null);	
	 			concurRuleListsOfRule.add(list); 				
	 		} 
	 		else {								
	 			if (this.getListsOfConcurrentRulesOfRule(preR, preI) != null) {
	 				List<ConcurrentRule> 
	 				listOfPreRule = this.getListsOfConcurrentRulesOfRule(preR, preI).get(0);	 				
		 			list = new Vector<ConcurrentRule>();
					for (int c=0; c<listOfPreRule.size(); c++) {
						ConcurrentRule cr = listOfPreRule.get(c);
						list.addAll(this.makeConcurrentRulesDuetoDependency(cr, ri, null));
					}
					concurRuleListsOfRule.add(list);
		 		}
	 		} 
	 		
	 		if (list != null && !list.isEmpty()) {
	 			crs.clear();
	 			crs.addAll(list);	 				
				System.out.println(
					"=== >>> ApplicabilityChecker.buildPlainConcurrentRule:: "+list.get(0).getRule().getName());	 				
	 		}
	 	}
	 	return crs;
	}
	
	
	/*
	private boolean enablingPredecessor(
			int startIndx,
			final List<Rule> sequence) {
	
		Graph graph = null;
		
		DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		
		String criterion = "";
		boolean result = true;
		boolean noEnablingPredecessor = false; 
		
		int start = (startIndx > 1) ? startIndx : 1;
		
		for (int i=start; i<sequence.size(); i++) {
			Rule ri = sequence.get(i);
			Rule ri_1 = sequence.get(i-1);
			
//			System.out.println("=== >>>  ApplicabilityChecker.enablingPredecessor    of   "+ri.getName());
					
			criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR;
			result = this.pureEnablingPredecessor(ri, i, sequence);										
			if (!result) {
				setRuleResult(i, ri.getName(), false,  ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, "");	
							
				criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
				result = this.directEnablingPredecessor(i, ri, sequence, graph);					
				if (!result) {
					criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
					setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");		
				}

				noEnablingPredecessor = noEnablingPredecessor(sequence, i, ri, dependencyContainer);
			} 								
						
			setApplicabilityResult(result, ApplicabilityConstants.ENABLING_PREDECESSOR); 
			setNonApplicabilityResult(noEnablingPredecessor, ApplicabilityConstants.NO_ENABLING_PREDECESSOR);	
			
		}		
		
		dependencyContainer.clear();
		
		return result;
	}


	private List<Rule> getPotentialEnablingPredecessor(
			final List<Rule> sequence,
			final int i,
			final Rule ri, 	
			final DependencyPairContainer dependencyContainer) {
		
		final List<Rule> list = new Vector<Rule>();
		
		dependencyContainer.enableProduceConcurrentRule(false);
		dependencyContainer.enableComplete(false);
		
		Rule r = null;
		for (int j=0; j<i; j++) {
			Rule rj = sequence.get(j);
			try {
				if (dependencyContainer.getCriticalPair(rj, ri, CriticalPair.EXCLUDE, true) 
						!= null) {
					r = rj;
					break;
				}
			} catch (Exception ex) {}
		}
		dependencyContainer.enableComplete(true);
		
		return list;
	}

	
	private boolean anyEnablingPredecessor(
			final List<Rule> sequence,
			final int i,
			final Rule ri, 	
			final DependencyPairContainer dependencyContainer) {
		
		dependencyContainer.enableProduceConcurrentRule(false);
		dependencyContainer.enableComplete(false);
		
		boolean result = false;
		for (int j=0; j<i; j++) {
			Rule rj = sequence.get(j);
			try {
				if (dependencyContainer.getCriticalPair(rj, ri, CriticalPair.EXCLUDE, true) 
						!= null) {
					result = true;
					break;
				}
			} catch (Exception ex) {}
		}
		dependencyContainer.enableComplete(true);		
		return result;
	}
	*/
	
	private Rule getFirstEnablingPredecessor(
			final List<Rule> sequence,
			final int i,
			final Rule ri, 	
			final DependencyPairContainer dependencyContainer) {
		
		dependencyContainer.enableProduceConcurrentRule(false);
		dependencyContainer.enableComplete(false);
		
		Rule r = null;
		for (int j=0; j<i; j++) {
			Rule rj = sequence.get(j);
			try {
				if (dependencyContainer.getCriticalPair(rj, ri, CriticalPair.EXCLUDE, true) 
						!= null) {
					r = rj;
					break;
				}
			} catch (Exception ex) {}
		}
		dependencyContainer.enableComplete(true);
		
		return r;
	}
/*	
	private boolean noEnablingPredecessor(
			final List<Rule> sequence,
			final int i,
			final Rule ri, 	
			final Rule enablingRule) {
		
		boolean result = false;
		info = "";
			
		if (enablingRule != null) {
			info = enablingRule.getName();		
			setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, info);
		
		} else {
			result = true;
			setNonApplicabilityResult(true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR);
			setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
		} 
		
		return result;
	}
	*/
	
	private boolean noEnablingPredecessor(
			final List<Rule> sequence,
			final int i,
			final Rule ri, 	
			final DependencyPairContainer dependencyContainer) {
		
		boolean result = false;
		this.info = "";
			
		Rule enablingRule = this.getFirstEnablingPredecessor(sequence, i, ri, dependencyContainer);
		
		if (enablingRule != null) {
//			info = enablingRule.getName();		
			setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, this.info);
		} else {
			result = true;
			setNonApplicabilityResult(true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR);
			setRuleResult(i, ri.getName(), true, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "");
		} 
		
		return result;
	}
			
	private boolean purelySequentialDependent(
			final Rule r1, 
			int indx_r1,
			final Rule r2,
			int indx_r2,
			Graph g) {
		
//		r1.getRight().setNotificationRequired(false);  // zu testen!!!
		
		final Match embedding = BaseFactory.theFactory().createMatch(r2, r1.getRight());		
		embedding.setCompletionStrategy(this.strategy, true);
		boolean result = false;
		while (embedding.nextCompletionWithConstantsChecking() && !result) {
			Enumeration<GraphObject> codom = embedding.getCodomain();						
			// exist l21 : L2 -> R1
			while (codom.hasMoreElements()) {
				GraphObject obj = codom.nextElement();
				// rule r1 produce at least one object which is used in LHS of r2
				if (!r1.getInverseImage(obj).hasMoreElements()) {
					result = true;
					
					if (this.ruleSequence.isObjFlowActive()) {
						final ObjectFlow objFlow = this.ruleSequence.getObjFlowForRules(r1, indx_r1, r2, indx_r2);
						if (objFlow != null && !objFlow.isEmpty()) 
							result = pureEnablingAlongObjectFlow(embedding,  objFlow);
					}
					// add match to matchSequence
					if (result)
						this.ruleSequence.getMatchSequence().addTotalPureEnablingSourceMatch(r2, r1, embedding, indx_r2, indx_r1);
					
					break; 
				}				
			}
			if (result) {
				boolean attrCondUsesIP = attrConditionUsesInputParameterRight(r2, r1, embedding);
					
				if (attrCondUsesIP) {
					setRuleResult(indx_r2, r2.getName(), false, ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, r1.getName());
				} else {
					setRuleResult(indx_r2, r2.getName(), true, ApplicabilityConstants.PURE_ENABLING_PREDECESSOR, r1.getName());	
				}				
			}
			
			// TODO: apply r1, then r2 along the comatch of r2 to check NACs of r2
//			result = isPurelyEnabledRuleApplicable(r1, r2, embedding, g);
//			if (result) {
//				// to test : add match to matchSequence
//				this.ruleSequence.getMatchSequence().addTotalPureEnablingSourceOfMatch(r2, r1, embedding, indx_r2, indx_r1);
//			}
			
		}
		
		embedding.dispose();
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(r1);
//		r1.getRight().setNotificationRequired(true);
		
		return result;
	}
		
	private boolean attrConditionUsesInputParameterRight(
			final Rule ruleAC,
			final Rule preRuleIP,
			final Match ruleLHS2preRuleRHS) {
		
		if (ruleAC.getAttrContext().getConditions().getNumberOfEntries() > 0
				&& ((VarTuple)preRuleIP.getAttrContext().getVariables()).hasInputParameter()) {
						
			List<String> inputParams = preRuleIP.getInputParameterNames();
			
			// find object with IP in the RHS of preRuleWithInputParam
			List<GraphObject> goIP = preRuleIP.getInputParameterObjectsRight(inputParams);
			
			// collect objects with variable of the conditions of ruleAC
			Vector<String> varsAC = ((CondTuple) ruleAC.getAttrContext().getConditions()).getAllVariables();
			List<GraphObject> goAC = new Vector<GraphObject>();
			
			// find LHS object with variable of the conditions of ruleAC
			addObjsWithVarOfCond(ruleAC.getLeft(), varsAC, null, goAC);

			// find PAC object with variable in the conditions of ruleAC
			Enumeration<OrdinaryMorphism> morphs = ruleAC.getPACs();
			while (morphs.hasMoreElements()) {
				OrdinaryMorphism morph = morphs.nextElement();			
				addObjsWithVarOfCond(morph.getTarget(), varsAC, morph, goAC);
			}
			// find NAC object with variable in the conditions of ruleAC
			morphs = ruleAC.getNACs();
			while (morphs.hasMoreElements()) {
				OrdinaryMorphism morph = morphs.nextElement();
				addObjsWithVarOfCond(morph.getTarget(), varsAC, morph, goAC);			
			}
			
			Enumeration<GraphObject> dom = ruleLHS2preRuleRHS.getDomain();
			while (dom.hasMoreElements()) {
				GraphObject go_ac = dom.nextElement();
				GraphObject go_ip = ruleLHS2preRuleRHS.getImage(go_ac);
				if (goAC.contains(go_ac) && goIP.contains(go_ip)) {
					return true;
				}
			}			
		}
		
		return false;
	}

	/*
	private boolean attrConditionUsesInputParameter(final Rule rule, final Match m) {		
		if (rule.getAttrContext().getConditions().getNumberOfEntries() > 0
				&& ((VarTuple)rule.getAttrContext().getVariables()).hasInputParameter()) {
						
			List<String> inputParams = rule.getInputParameterNames();
			
			// find object with IP 
			List<GraphObject> goIPleft = rule.getInputParameterObjectsLeft(inputParams);
			List<GraphObject> goIPright = rule.getInputParameterObjectsRight(inputParams);
			
			// collect objects with variable of the conditions of rule
			Vector<String> varsAC = ((CondTuple) rule.getAttrContext().getConditions()).getAllVariables();
			List<GraphObject> goAC = new Vector<GraphObject>();
			
			// find LHS object with variable of conditions of rule
			addObjsWithVarOfCond(rule.getLeft(), varsAC, null, goAC);

			// find PAC object with variable in conditions of rule
			Enumeration<OrdinaryMorphism> morphs = rule.getPACs();
			while (morphs.hasMoreElements()) {
				OrdinaryMorphism morph = morphs.nextElement();			
				addObjsWithVarOfCond(morph.getTarget(), varsAC, morph, goAC);
			}
			// find NAC object with variable in the conditions of ruleAC
			morphs = rule.getNACs();
			while (morphs.hasMoreElements()) {
				OrdinaryMorphism morph = morphs.nextElement();
				addObjsWithVarOfCond(morph.getTarget(), varsAC, morph, goAC);			
			}
			
			Enumeration<GraphObject> dom = m.getDomain();
			while (dom.hasMoreElements()) {
				GraphObject go = dom.nextElement();
				if (goAC.contains(go) && goIPleft.contains(go)) {
					return true;
				}
			}			
			
			Iterator<?> iter = rule.getRight().getNodesSet().iterator();
			while (iter.hasNext()) {
				GraphObject go = (GraphObject) iter.next();			
				if (goAC.contains(go) && goIPright.contains(go)) {
					return true;
				}
			}
			iter = rule.getRight().getArcsSet().iterator();
			while (iter.hasNext()) {
				GraphObject go = (GraphObject) iter.next();			
				if (goAC.contains(go) && goIPright.contains(go)) {
					return true;
				}
			}
		}
		
		return false;
	}
	*/
	
	private boolean usingAttrConditionAndInputParameter(final Rule rule, final Match m) {		
		if (rule.getAttrContext().getConditions().getNumberOfEntries() > 0
				&& ((VarTuple)rule.getAttrContext().getVariables()).hasInputParameter()) 
			return true;
		
		return false;
	}
	
	private void addObjsWithVarOfCond(final Graph g, 
										final List<String> vars, 
										final OrdinaryMorphism morph, 
										final List<GraphObject> list) {
		addGOsWithVarOfCond(g.getNodesSet().iterator(), vars,  morph, list);
		addGOsWithVarOfCond(g.getArcsSet().iterator(), vars,  morph, list);		
	}
	
	private void addGOsWithVarOfCond(final Iterator<?> iter, 
			final List<String> vars, 
			final OrdinaryMorphism morph, 
			final List<GraphObject> list) {

		while (iter.hasNext()) {
			GraphObject go = (GraphObject)iter.next();
			if (go.getAttribute() != null) {
				ValueTuple val = (ValueTuple)go.getAttribute(); 
				for (int i=0; i<val.getNumberOfEntries(); i++) {
					ValueMember mem = val.getEntryAt(i);
					if (mem.isSet() && mem.getExpr().isVariable()) {
						if (vars.contains(mem.getExprAsText())) {
							if (morph == null) {
								if (!list.contains(go))
									list.add(go);
							} else if (morph.getInverseImage(go).hasMoreElements()) {
								GraphObject go1 = morph.getInverseImage(go).nextElement();
								if (!list.contains(go1))
									list.add(go1);
							}
						}
					}
				}
			}
		}
	}
	private boolean pureEnablingAlongObjectFlow(
			final OrdinaryMorphism morph, 
			final ObjectFlow objFlow) {
				
		Enumeration<Object> outs = objFlow.getMapping().keys();
		while (outs.hasMoreElements()) {
			Object out = outs.nextElement();
			Object in = objFlow.getMapping().get(out);
			GraphObject img = morph.getImage((GraphObject)in);
			if (img != null && img != out) {
				return false;
			}
		}
		return true;
	}

	/*
	private boolean checkForbiddenObjects(
			final Rule rule,
			final List<Rule> predecessors, 
			final Graph g) {
		
		boolean noObj = true;
		List<GraphObject> toCreate = null;
		
		for (int r=0; r<predecessors.size(); r++) {
			
			final Rule prerule = predecessors.get(r);		
			toCreate = prerule.getElementsToCreate();			
			noObj = !toCreate.isEmpty();
			
			Enumeration<OrdinaryMorphism> nacs = rule.getNACs();
			while (nacs.hasMoreElements() && noObj) {
				OrdinaryMorphism nac = nacs.nextElement();
				

				noObj = doCheckForbiddenObjs(nac, toCreate, nac.getTarget().getNodesSet().iterator())
						|| doCheckForbiddenObjs(nac, toCreate, nac.getTarget().getArcsSet().iterator());				
			}
		}
		
		if (noObj) {
			// check graph
			Enumeration<OrdinaryMorphism> nacs = rule.getNACs();
			while (nacs.hasMoreElements() && noObj) {
				OrdinaryMorphism nac = nacs.nextElement();
				
				noObj = doCheckForbiddenObjsAtGraph(nac.getTarget().getNodesSet().iterator(), g)
						|| doCheckForbiddenObjsAtGraph(nac.getTarget().getArcsSet().iterator(), g);
			}
		}
		
		return !noObj;
	}
	
	
	private boolean doCheckForbiddenObjsAtGraph(
			final Iterator<?> elems,
			final Graph g) {
		
		boolean noObj = true;
		while (elems.hasNext() && noObj) {
			GraphObject elem = (GraphObject) elems.next();

			if (elem.isNode()) {							
				String key = ((Node) elem).convertToKey();
				if (g.getTypeObjectsMap().get(key) != null
						&& !g.getTypeObjectsMap().get(key).isEmpty()) {
					noObj = false;		
//					System.out.println(" Graph: "+g.getName()+"   contains forbidden object of type: "+t.getName());
				}
			} else {
				String key = ((Arc) elem).convertToKey();
				if (g.getTypeObjectsMap().get(key) != null
						&& !g.getTypeObjectsMap().get(key).isEmpty()) {
					noObj = false;
//						System.out.println(" Graph: "+g.getName()+"   contains forbidden object of type: "+t.getName());
				}							
			}
		}
		return noObj;
	}
	*/
	
	/**
	 * Returns true if the specified rule r produces an object 
	 * which is forbidden from the specified Negative Application Conditions nacs
	 * of an other rule
	 * or it is already preserved in the specified graph g.
	 * Otherwise false.
	 */
	private boolean checkForbiddenObjects(
			final Enumeration<OrdinaryMorphism> nacs,
			final Rule r, 
			final Graph g) {
		
		final List<GraphObject> toCreate = r.getElementsToCreate();
		boolean found = false;
		
		while (nacs.hasMoreElements() && !found) {
			OrdinaryMorphism nac = nacs.nextElement();
			
			found = checkForbiddenObjs(nac, 
										g, 
										nac.getTarget().getNodesSet().iterator(), 
										toCreate)
					|| checkForbiddenObjs(
										nac, 
										g, 
										nac.getTarget().getArcsSet().iterator(), 
										toCreate);
		}		
		return found;
	}
	
	/**
	 * Returns true if the specified graph object to create 
	 * which is forbidden from the specified Negative Application Condition nac
	 * of an other rule
	 * or it is already preserved in the specified graph g.
	 * Otherwise false.
	 */
	private boolean checkForbiddenObjs(
			final OrdinaryMorphism nac,
			final Graph g,
			final Iterator<?> iter,
			final List<GraphObject> toCreate) {
		
		boolean found = false;
		
		while (iter.hasNext()) {
			GraphObject elem = (GraphObject) iter.next();
			if (!nac.getInverseImage(elem).hasMoreElements()) {
				Type t = elem.getType();
				// check RHS of rule
				for (int i=0; i<toCreate.size(); i++) {
					GraphObject newgo = toCreate.get(i);
					if (elem.isNode()) {
						if (t.isParentOf(newgo.getType())) {
							found = true;
							break;
						}
					} else {
						if (t == newgo.getType()
								&& ((Arc)elem).getSource().getType().isParentOf(((Arc)newgo).getSource().getType())
								&& ((Arc)elem).getTarget().getType().isParentOf(((Arc)newgo).getTarget().getType())) {
							found = true;
							break;
						}
					}
				}
				if (!found) {
					// check graph
					Hashtable<String, HashSet<GraphObject>> 
					type2objects = g.getTypeObjectsMap();
					if (elem.isNode()) {
						String key = elem.convertToKey();
						if (type2objects.get(key) != null
								&& !type2objects.get(key).isEmpty()) {
							found = true;		
						}
					} else {
						String key = ((Arc) elem).convertToKey();
						if (type2objects.get(key) != null
								&& !type2objects.get(key).isEmpty()) {
							found = true;
						}							
					}
				}					
			}
		}		
		return found;
	}
	
	/**
	 * Returns true if the specified rule r produces an object 
	 * which is forbidden from the specified Negative Application Conditions nacs
	 * of an other rule.
	 * Otherwise false.
	 */
	private boolean checkForbiddenObjects(
			final Enumeration<OrdinaryMorphism> nacs,
			final Rule r) {
		
		final List<GraphObject> toCreate = r.getElementsToCreate();
		boolean found = false;
		
		while (nacs.hasMoreElements() && !found) {
			OrdinaryMorphism nac = nacs.nextElement();
			
			found = doCheckForbiddenObjs(nac, toCreate, nac.getTarget().getNodesSet().iterator())
					|| doCheckForbiddenObjs(nac, toCreate, nac.getTarget().getArcsSet().iterator());
		}		
		return found;
	}

	private boolean doCheckForbiddenObjs(
			final OrdinaryMorphism nac,
			final List<GraphObject> toCreate,
			final Iterator<?> elems) {

		boolean noObj = !toCreate.isEmpty();
		while (elems.hasNext()) {
			GraphObject elem = (GraphObject) elems.next();
			if (!nac.getInverseImage(elem).hasMoreElements()) {
				Type t = elem.getType();
				// check new objects of rule
				for (int i=0; i<toCreate.size(); i++) {
					if (t.isParentOf(toCreate.get(i).getType())) {
						noObj = false;
//						System.out.println(" Rule: "+r.getName()+"   produces forbidden object of type: "+t.getName());
						break;
					}						
				}				
			}
		}		
		return !noObj;
	}

	/*
	private boolean doCheckNACGraphContextOnGraph(
			final Enumeration<OrdinaryMorphism> nacs,
			final Graph g) {
//		System.out.println("doCheckNACGraphContextOnGraph.... ");
		boolean nacResult = true;
		while (nacs.hasMoreElements() && nacResult) {
			OrdinaryMorphism nac = nacs.nextElement();
			OrdinaryMorphism nacIso = nac.getTarget().isomorphicCopy();
			Graph contextGraph = nacIso.getTarget();
			
			// remove mapped edge/node from context
			Enumeration<GraphObject> codom = nac.getCodomain();
			while (codom.hasMoreElements()) {
				GraphObject obj = codom.nextElement();	
				if (obj.isArc()) {
					try {
						contextGraph.destroyArc((Arc)nacIso.getImage(obj), false, true);
					} catch (Exception ex) {
//						System.out.println(ex.getStackTrace());
					}
				}
			}
			codom = nac.getCodomain();
			while (codom.hasMoreElements()) {
				GraphObject obj = codom.nextElement();
				if (obj.isNode() && ((Node) obj).isIsolated()) {
					try {
						contextGraph.destroyNode((Node)nacIso.getImage(obj), false, true);
					} catch (Exception ex) {
//						System.out.println(ex.getStackTrace());
					}
				}
			}
			
			// make embedding emb: context -> g
			OrdinaryMorphism emb = BaseFactory.theFactory().createMorphism(contextGraph, g);
			emb.addToAttrContext((CondTuple) nac.getAttrContext().getConditions());
			emb.setCompletionStrategy(this.strategy, true);
			nacResult = false;
			while (emb.nextCompletionWithConstantsChecking()) {
				nacResult = true;
				// check attr conditions
				CondTuple conds = (CondTuple) emb.getAttrContext().getConditions();
				for (int i=0; i<conds.getSize(); i++) {
					CondMember cond = conds.getCondMemberAt(i);
//					System.out.println(cond.toString()+"   "+cond.getMark());
				}
				if (conds.isDefinite() && !conds.isTrue()) {
					nacResult = false;
				} else {
//					System.out.println("---> NAC: "+nac.getName() +"  found in graph");
					break;
				}
			} 
			
			emb.dispose();
			nacIso.dispose(false, true);
		}
		
		return !nacResult;
	}
	*/
	
	/**
	 * Check 4c. (direct enabling predecessor) criterion of applicability :
	 * there exists a concurrent rule r_c of r_i-1 and r_i such that r_c is applicable
	 * via an injective match on graph g and r_c is asymmetrically parallel independent
	 * on r_j for all j<(i-1) and r_j is asymmetrically parallel independent
	 * on r_c for all i<j<=n.
	 */
	private boolean directEnablingPredecessor(
			final int i,
			final Rule ri,
			final List<Rule> sequence, 
			final Graph g) {
		
		boolean result = false;		
		Rule ri_1 = sequence.get(i-1);
		if (ri_1 != null) {
			this.info = ri_1.getName();				
			// get already existing or new list of concurrent rules of the current rule ri
			// OLD part,  TODO: refactoring
			List<List<ConcurrentRule>> concurRuleLists = this.ruleSequence.getListsOfConcurrentRules(ri, i);
			if (concurRuleLists ==  null) {
				concurRuleLists = new Vector<List<ConcurrentRule>> ();
				this.ruleSequence.putListsOfConcurrentRules(ri, i, concurRuleLists);
			}
			
			//test
			this.completeConcurRuleBackward = this.ruleSequence.isObjFlowActive();
//	System.out.println(ri.getName()+"      completeConcurRuleBackward: "+completeConcurRuleBackward);		
			if (this.completeConcurRuleBackward) {
				result = this.buildConcurrentRulesBackward(ri_1, i-1, ri, i, g);
			}
			else {
				result = this.buildConcurrentRulesForward(ri_1, i-1, ri, i, g);
//				if (!result) {
//					result = this.buildPlainConcurrentRule(sequence, g);
//				}
			}

		}
		if (result) {
			System.out.println("=== >>>  ApplicabilityChecker.directEnablingPredecessor:  of  rule: "+ri.getName()+"  is  rule: "+this.info);
			
			setRuleResult(i, ri.getName(), true, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, this.info);
			setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "(direct)");	
		}

		return result;
	}

	/**
	 * Check 4b. (partial enabling predecessor) criterion of applicability :
	 * there exists a concurrent rule r_c of a not necessarily direct predecessor rule r
	 * and r_i such that r_c is applicable to graph g
	 * and all predecessors of r_c do not cause a conflict with r_c.
	 * Moreover, r is self-enabled such that its match can be forwarded
	 * to the match of r_c. 
	 */
	private boolean partialEnablingPredecessor(
			final Rule ri,
			final int i,
			final List<Rule> sequence, 
			final Graph g) {
//		System.out.println("\n=== >>>  ApplicabilityChecker.partialEnablingPredecessor ...  of  "+ri.getName());
		
		boolean result = false;
		
		for (int j=i-1; j>=0; j--) {
			Rule rj = sequence.get(j);
			this.info = rj.getName();	
				
			Pair<Boolean, List<String>> pair = this.ruleSequence.getRuleResult(j, rj.getName(), ApplicabilityConstants.PREDECESSOR_NOT_NEEDED);
			if (pair == null)
				pair = this.ruleSequence.getRuleResult(j, rj.getName(), ApplicabilityConstants.INITIALIZATION);
			
			if (pair != null && pair.first.booleanValue()) {
//				System.out.println("=== >>>  ApplicabilityChecker.partialEnablingPredecessor ::  applicable: "+rj.getName());
				// rule rj is applicable to graph g
								
				// get new list of concurrent rules of the current rule ri
				List<List<ConcurrentRule>> concurRuleLists = this.ruleSequence.getListsOfConcurrentRules(ri, i);
				if (concurRuleLists == null) {
					concurRuleLists = new Vector<List<ConcurrentRule>> ();
					this.ruleSequence.putListsOfConcurrentRules(ri, i, concurRuleLists);
				}
				// make concurrent rule(s) due to dependency pair overlapping
				List<ConcurrentRule> list = this.makeJointlyConcurrentRule(rj, j, ri, i);
				
		 		if (list != null && !list.isEmpty()) {
		 			concurRuleLists.add(list);
		 			int d = 1;
		 			
		 			// set match of concurrent rule,  (check due to ObjectFlow?)
					final Hashtable<GraphObject, GraphObject> 
					rjMatch = this.ruleSequence.getMatchSequence().getDirectMatch(j, rj);
					if (rjMatch != null) {						
		 				for (int c=0; c<list.size(); c++) {
		 					final ConcurrentRule cr = list.get(c);
		 					if (!rjMatch.isEmpty() &&
		 							!cr.forwardMatchMappingOfFirstSourceRule(rjMatch, this.ruleSequence.getGraph())) {
		 						list.remove(c);
		 						c--;
		 					} 
		 				}
		 				for (int k = 0; k<list.size(); k++) {
		 					ConcurrentRule cr = list.get(k);				
		 					if (isConcurrentRuleApplicable(i, ri, 
		 							this.ruleSequence.getRules(), g, 
		 							cr, d, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR)) {
		 						result = true;
		 						System.out.println("=== >>>  ApplicabilityChecker.partialEnablingPredecessor:  of  rule: "+ri.getName()+"  is  rule: "+rj.getName());
		 						break;
		 					}
		 				}			
		 				if (result)
		 					break;		 				
		 			}	
				}
			}
		}
		if (result) {
			setRuleResult(i, ri.getName(), true, ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR, this.info);
//			setRuleResult(i, ri.getName(), true, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");
			setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, "(partial)");	
		}

		return result;
	}
	
//	private boolean isConcurrentRuleApplicableWithoutConflicts(
//			final int i,
//			final Rule ri,
//			final List<Rule> sequence,
//			final Graph g,
//			final List<Rule> concurRules) {
//		System.out.println("---> ApplicabilityChecker.isConcurrentRuleApplicableWithoutConflicts... ::  of  "+ri.getName());
//		ExcludePair excludePair = null;
//		boolean result = false;
//		for (int k = 0; k<concurRules.size() && !result; k++) {
//			Rule cr = concurRules.get(k);				
//			System.out.println("---> applicability of Concurrent rule: "+cr.getName());		
//			result = true;
//			
//			if (cr.isApplicable(g, this.gragraStrategy, false)) {	
//				
//				Vector<Rule> tmp = new Vector<Rule>();
//				tmp.addAll(sequence);
//				tmp.remove(i);
//				tmp.remove(i-1);
//				tmp.add(i-1, cr);
//				int c = tmp.indexOf(cr);
//				
//				// find conflicts of concurrent rule with rules before
//				for (int j=0; j<c; j++) {
//					Rule rj = tmp.get(j);	
//					excludePair = this.makeExcludePair();
//					if(!asymParallelIndependentByCPA(excludePair, rj, cr)) {
//						result = false;		
////						info = cr.getName();
//						System.out.println("---> Concurrent rule: "+cr.getName()+"  has conflicts!");
//						break;
//					} 
//					excludePair.dispose();
//				}
//				if (result) {
//					// find conflicts of concurrent rule with rules after
//					for (int l=c+1; l<tmp.size(); l++) {
//						Rule rj = tmp.get(l);	
//						excludePair = this.makeExcludePair();
//						if (!asymParallelIndependentByCPA(excludePair, cr, rj)) {
//							result = false;	
////							info = cr.getName();
//							System.out.println("---> Concurrent rule: "+cr.getName()+"  has conflicts!");
//							break;
//						} 
//						excludePair.dispose();
//					}
//					if (result) {
//						info = cr.getName();
//						setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, info);
//						setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, info);
//						System.out.println("---> Concurrent rule: "+cr.getName()+"  is applicable without conflicts!");
//						applicableConcurrentRules.add(cr);
//						break;
//					}
//
//				}
//				tmp.clear();
//			} else {
//				result = false;
//				System.out.println("---> Concurrent rule: "+cr.getName()+"  is not applicable!");			
//			}
//		}	
//		
//		return result;
//	}



	/**
	 * Checks applicability of an concurrent rule at given Graph g.
	 * Additionaly, for an applicable concurrent rule check 
	 * whether it produces conflicts with other rules or not.
	 * 
	 * @return	true, if at least one concurrent rule is applicable, otherwise false
	 */
	private boolean isConcurrentRuleApplicable(
			final int i,
			final Rule ri,
			final List<Rule> sequence,
			final Graph g,
			final ConcurrentRule cr,
			final int concurdepth,
			String criterion) {

		boolean result = false;			
		if (g == null
				|| this.isRuleApplicable(ri, cr, g, false)) {	
			this.applicableConcurrentRules.add(cr);
			result = true;
			System.out.println("=== >>> Concurrent rule:  "+cr.getRule().getName()+"    applicable");				
			// check whether an applicable concurrent rule produces conflicts with other rules
			if (this.completeCPA) {
//				result = 
				concurrentRuleAsymParallelIndependentByCPA(i, ri, cr, sequence, concurdepth, criterion);
			} else {
//				result = 
				concurrentRuleAsymParallelIndependent(i, ri, cr, sequence, concurdepth, criterion);						
			}				
		} 			
		return result;
	}
	
	private boolean concurrentRuleAsymParallelIndependent(
			final int i,
			final Rule ri,
			final ConcurrentRule cr,
			final List<Rule> sequence,
			final int concurdepth,
			String criterion) {
		
//		System.out.println("\n=== >>>  concurrentRuleAsymParallelIndependent: check conflicts of its base rules...  cr.depth: "+
//				cr.getRule().getName()+"   depth: "+cr.getDepth());

		boolean result = true; 		
		int concurrrentRuleDepth = cr.getDepth();
			
		Vector<Rule> remainList = new Vector<Rule>();
		for (int l=i+1; l<sequence.size(); l++) {
			remainList.add(sequence.get(l));
		}
		
		Vector<Rule> crSourceList = new Vector<Rule>();
		int start = i-concurrrentRuleDepth;
		for (int l=start; l<=i; l++) {
			crSourceList.add(sequence.get(l));
		}
		
		// find conflicts of concurrent rule with rules before
		for (int l = 0; l<crSourceList.size(); l++) {
			Rule r = crSourceList.get(l);
			int indx = start + l;
			Pair<Boolean, List<String>> 
			ruleRes = this.ruleSequence.getRuleResult(indx, r.getName(), ApplicabilityConstants.NO_IMPEDING_PREDECESSORS);
			if (ruleRes != null && !ruleRes.first.booleanValue()) {
				result = false;	
//				String otherRule = ruleRes.second.isEmpty()? "": ruleRes.second.get(0);
//				System.out.println("---> Concurrent rule: "+cr.getRule().getName()+": its base rule: "+r.getName()+"   has conflict with  " +otherRule);
				break;
			}
		}
		if (result) {
			// find conflicts of concurrent rule with successor rules 
			for (int l = 0; l<crSourceList.size(); l++) {
				Rule r1 = crSourceList.get(l);
				for (int k=0; k<remainList.size(); k++) {
					Rule r2 = remainList.get(k);
					SimpleExcludePair excludePair = this.makeExcludePair();
					if (!asymParallelIndependentByCPA(excludePair, r1, -1, r2, -1)) {
						result = false;	
//						System.out.println("---> Concurrent rule: "+cr.getRule().getName()+": its base rule: "+r1.getName()+"   has conflict with  " +r2.getName());
						break;
					} 
					excludePair.dispose();
				}
				if (!result) {
					break;
				}
			}
		}
		this.info = this.getConcurrentRuleNameInfo(cr.getRule(), ri);			
		setRuleResult(i, ri.getName(), result, criterion, this.info);			

		remainList.clear();
		crSourceList.clear();
		
		return result;
	}
	
	private boolean concurrentRuleAsymParallelIndependentByCPA(
			final int i,
			final Rule ri,
			final ConcurrentRule cr,
			final List<Rule> sequence,
			final int concurdepth,
			String criterion) {
//		System.out.println("=== >>>  concurrentRuleAsymParallelIndependentByCPA: check conflicts of concurrent rule...");
		
		boolean result = true; 
		Vector<Rule> tmp = new Vector<Rule>();
		tmp.addAll(sequence);
		int n = 0;
		while (concurdepth-n >= 0) {			
			tmp.remove(i-n);
			n++;
		}
		if ((i-n) < 0) {
			tmp.add(0, cr.getRule());
		} else {
			tmp.add(i-n, cr.getRule());
		}
		int c = tmp.indexOf(cr.getRule());
		
		// find conflicts of concurrent rule with rules before
		for (int j=0; j<c; j++) {
			final Rule rj = tmp.get(j);	
			final SimpleExcludePair excludePair = this.makeExcludePair();				
			if(!asymParallelIndependentByCPA(excludePair, rj, -1, cr.getRule(), -1)) {
				result = false;		
//				System.out.println("---> Concurrent rule: "+cr.getRule().getName()+"  has conflict with  " +rj.getName());
				break;
			} 
			excludePair.dispose();
		}
		if (result) {
			// find conflicts of concurrent rule with successor rules
			for (int l=c+1; l<tmp.size(); l++) {
				final Rule rj = tmp.get(l);	
				final SimpleExcludePair excludePair = this.makeExcludePair();
				if (!asymParallelIndependentByCPA(excludePair, cr.getRule(), -1, rj, -1)) {
					result = false;	
//					System.out.println("---> Concurrent rule: "+cr.getRule().getName()+"  has conflict with  " +rj.getName());
					break;
				} 
				excludePair.dispose();
			}
		}

		this.info = this.getConcurrentRuleNameInfo(cr.getRule(), ri);
		setRuleResult(i, ri.getName(), result, criterion, this.info);
//		System.out.println("=== >>>  Concurrent rule: "+cr.getRule().getName()+"  is applicable without conflicts!");
				
		tmp.clear();
		
		return result;
	}
	
	
	/*
	private boolean directEnablingPredecessor(
			final Rule ri,
			final Rule concurrentRule,
			final List<Rule> sequence, 
			final Graph g) {
		System.out.println("---> ApplicabilityChecker.directEnablingPredecessor ...  of  "+concurrentRule.getName());

		ExcludePair excludePair = null;
		boolean result = true;
		
		int i = sequence.indexOf(ri);
		if (i-1 < 0) {	
			result = false;
			setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");	
			return result;
		}
		
		Rule ri_1 = sequence.get(i-1);	
		String info = ri_1.getName();	
		List<Rule> cRules = makeConcurrentRules(ri_1, concurrentRule);
		if (cRules != null) {
			this.concurrentRules.addAll(cRules);
			
			for (int k = 0; k<cRules.size(); k++) {
				Rule cr = cRules.get(k);				
				System.out.println("---> Concurrent rule: "+cr.getName());		
			
				if (cr.isApplicable(g, strategy, false)) {					
					Vector<Rule> tmp = new Vector<Rule>();
					tmp.addAll(sequence);
					tmp.add(i, cr);
					tmp.remove(i-1);
					tmp.remove(i);
					int c = tmp.indexOf(cr);
					
					// find conflicts of concurrent rule with rules before
					for (int j=0; j<c; j++) {
						Rule rj = tmp.get(j);	
						excludePair = this.makeExcludePair();
						if(!asymParallelIndependentByCPA(excludePair, rj, cr)) {
							result = false;						
							setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, rj.getName());	
							System.out.println("---> Concurrent rule: "+cr.getName()+"  has conflicts!");
							break;
						} 
						excludePair.dispose();
					}
					if (result) {
						// find conflicts of concurrent rule with rules after
						for (int l=c+1; l<tmp.size(); l++) {
							Rule rj = tmp.get(l);	
							excludePair = this.makeExcludePair();
							if (!asymParallelIndependentByCPA(excludePair, cr, rj)) {
								result = false;								
								setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, rj.getName());	
								System.out.println("---> Concurrent rule: "+cr.getName()+"  has conflicts!");
								break;
							} 
							excludePair.dispose();
						}
						if (result) {
							setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, info);
							setRuleResult(i, ri.getName(), false, ApplicabilityConstants.NO_ENABLING_PREDECESSOR, info);
							
							applicableConcurrentRules.add(cr);
							break;
						}

					}
					tmp.clear();
				} else {
					result = false;						
					setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");
					System.out.println("---> Concurrent rule: "+cr.getName()+"  is not applicable!");						
				}
			} 
		} else {
			result = false;						
			setRuleResult(i, ri.getName(), result, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR, "");	
		}
		return result;
	}

	private boolean checkConcurrentRuleAsPredecessor(
			final int i,
			final Rule ri,
			List<Rule> applicableConcurRules,
			final Graph graph) {
		
		List<Rule> concurRules = new Vector<Rule>(applicableConcurRules);
		boolean result = this.pureEnablingPredecessorAsConcurrentRule(i, ri, concurRules, graph);
		
		return result;
	}
*/
	
	private String getConcurrentRuleNameInfo(final Rule concurRule, final Rule rule) {
		String name = concurRule.getName();
		int pos = name.indexOf(rule.getName());
		if (pos > 0) {
			name = name.substring(0, pos-1);
		}
		return name;
	}
	
	private List<List<ConcurrentRule>> getListsOfConcurrentRulesOfRule(
			final Rule r,
			final int indx) {
		return this.ruleSequence.getListsOfConcurrentRules(r, indx);
	}

	
	private List<ConcurrentRule> getConcurrentRulesOfRule(
			final Rule r,
			final int indx,
			final int listIndx) {
		List<List<ConcurrentRule>> lists = this.ruleSequence.getListsOfConcurrentRules(r, indx);
		if (lists == null) {
			lists = new Vector<List<ConcurrentRule>>();
			this.ruleSequence.putListsOfConcurrentRules(r, indx, lists);
			return null;
		} else if (listIndx < lists.size()) {
			return lists.get(listIndx);
		} else {
			return null;
		}
	}

	
	
 	private boolean completeConcurrentRules(
			final Rule r,
			final int rIndx,
			final List<List<ConcurrentRule>> crsOfRule,
			final int listIndx,
			final Hashtable<?, ?> matchmap) {
//		System.out.println("=== >>>  ApplicabilityChecker.completeConcurrentRules:  of "+r.getName());
 		
		boolean res = false;
		if (listIndx >= crsOfRule.size()) {
			int x = listIndx;
			int preIndx = rIndx-1;
			Rule preRule = this.ruleSequence.getRule(preIndx);
			if (x == 0) {
				List<ConcurrentRule> list = this.makeConcurrentRulesDuetoDependency(preRule, preIndx, r, rIndx, matchmap);				
				crsOfRule.add(list);
				res = true;
			} else {
				List<ConcurrentRule>
				crListOfPreRule = this.getConcurrentRulesOfRule(preRule, preIndx, x-1);
				if (crListOfPreRule == null || crListOfPreRule.isEmpty()) {
					// create a new list of lists to fill
					final List<List<ConcurrentRule>> 
					conRuleListsOfPreRule = this.getListsOfConcurrentRulesOfRule(preRule, preIndx);	
					// put this list to fill
					this.completeConcurrentRules(preRule, rIndx-1, conRuleListsOfPreRule, x-1, matchmap);
					// get the (x-1) list
					crListOfPreRule = this.getConcurrentRulesOfRule(preRule, preIndx, x-1);
				} 
					
				if (crListOfPreRule != null) {
					final  List<ConcurrentRule> list = new Vector<ConcurrentRule>();
					for (int c=0; c<crListOfPreRule.size(); c++) {
						final ConcurrentRule cr = crListOfPreRule.get(c);
						list.addAll(this.makeConcurrentRulesDuetoDependency(cr, r, matchmap));
					}
					while (crsOfRule.size() < x) {
						crsOfRule.add(new Vector<ConcurrentRule>());
					}
					crsOfRule.add(list);
					res = true;
				}					
			}
		}		
		return res;
	}

	
 	/*
 	 * Creates concurrent rule forwards:
 	 * Exmpl.:  CR(r1,r2,r3)
 	 * 1) CR(r1,r2) = r1 *E r2;  
 	 * 2) CR(r1,r2,r3) = CR(r1,r2) *E r3;
 	 * 
 	 * @param ri_1  the direct predecessor rule
 	 * @param i_1	index of the direct predecessor rule
 	 * @param ri	the current rule
 	 * @param i		index of the current rule
 	 * @param g		a graph to apply the rule 
 	 * 
 	 * @return		true, when at least one rule of created concurrent rules is applicable,
 	 * 				otherwise false
 	 */ 
 	private boolean buildConcurrentRulesForward(
 			final Rule ri_1, final int i_1,
 			final Rule ri, final int i,			
 			final Graph g) {
 		System.out.println("=== >>>  ApplicabilityChecker.buildConcurrentRulesForwards:: "+ri_1.getName()+"  &  "+ri.getName());

 		boolean result = false;
 		
 		final List<List<ConcurrentRule>> 
		crListsOfRuleI = this.getListsOfConcurrentRulesOfRule(ri, i);
 		
 		final List<List<ConcurrentRule>> 
		crListsOfRuleI_1 = this.getListsOfConcurrentRulesOfRule(ri_1, i_1);
 		if (crListsOfRuleI_1 != null && !crListsOfRuleI_1.isEmpty()) {
 			for (int l=0; l<crListsOfRuleI_1.size() && !result; l++) { 
 				List<ConcurrentRule> listAll = new Vector<ConcurrentRule>();
 				crListsOfRuleI.add(listAll);
 				List<ConcurrentRule> crList = crListsOfRuleI_1.get(l);
 				for (int c=0; c<crList.size() && !result; c++) {
 					ConcurrentRule cr = crList.get(c);
 					List<ConcurrentRule> list = this.makeConcurrentRulesDuetoDependency(cr, ri, null);
 					listAll.addAll(list);
 					if (!list.isEmpty()) {
 		 				int d = crListsOfRuleI.size();
 		 				for (int j = 0; j<list.size(); j++) {
 		 					ConcurrentRule crj = list.get(j);				
 		 					if (isConcurrentRuleApplicable(i, ri, 
 		 							this.ruleSequence.getRules(), g, 
 		 							crj, d, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR)) {
 		 						result = true;
 		 					}
 		 					else {
 		 						list.remove(j);
 		 						j--;
 		 					}
 		 				}
 		 			}
 				}
 			}
 		} 
  		if (!result) {
	 		for (int k=0; k<i; k++) {	
	 			Rule r = this.ruleSequence.getRule(k);
	 			int k1 = k+1;
	 			Rule r1 = this.ruleSequence.getRule(k1);
	 			List<ConcurrentRule> list = null;
	 			if (k==0) {
	 				list = this.makeConcurrentRulesDuetoDependency(r, k, r1, k1, null);	
	 				crListsOfRuleI.add(list); 				
	 			} 
	 			else //if (this.depth == -1 || k < this.depth) 
	 			{								
	 				List<ConcurrentRule> listOfPreRule = this.getConcurrentRulesOfRule(r, k, k-1);
	 				if (listOfPreRule == null || listOfPreRule.isEmpty()) {
	 					final List<List<ConcurrentRule>> 
	 					crListsOfPreRule = this.getListsOfConcurrentRulesOfRule(r, k); 
	 					
	 					this.completeConcurrentRules(r, k, crListsOfPreRule, k-1, null);
	 					
	 					listOfPreRule = this.getConcurrentRulesOfRule(r, k, k-1);
	 					if (listOfPreRule == null || listOfPreRule.isEmpty()) {
	 						result = false;
	 						break;
	 					} 
	 				}
	 				
	 				list = new Vector<ConcurrentRule>();
					for (int c=0; c<listOfPreRule.size(); c++) {
						final ConcurrentRule cr = listOfPreRule.get(c);
						list.addAll(this.makeConcurrentRulesDuetoDependency(cr, r1, null));
					}
					crListsOfRuleI.add(list);
	 			} 
	
	 			if (!list.isEmpty()) {
	 				int d = crListsOfRuleI.size();
	 				for (int c = 0; c<list.size(); c++) {
	 					ConcurrentRule cr = list.get(c);				
	 					if (isConcurrentRuleApplicable(i, ri, 
	 							this.ruleSequence.getRules(), g, 
	 							cr, d, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR)) {
	 						result = true;
	 					}
//	 					else {
//	 						list.remove(c);
//	 						c--;
//	 					}
	 				}
	 				if (result)
	 					break;
	 			}
	 	 	}
 		}
// 		System.out.println("---> ApplicabilityChecker.buildConcurrentRulesForward::  result: "+result);
 		return result;
 	}
 	
 	
// 	private void showObjectFlow() {
// 		Enumeration<String> keys = this.ruleSequence.getObjectFlow().keys();
// 		while (keys.hasMoreElements()) {
// 			String key = keys.nextElement();
// 			ObjectFlow of = this.ruleSequence.getObjectFlow().get(key);
// 			System.out.println(key+"::  "+of.getNameOfOutput()+" -> "+of.getNameOfInput()
// 					+"  ::  "+of.getMapping().size());
// 		}
// 	}
 	
	
 	/**
 	 * Creates concurrent rule backwards:
 	 * Exmpl.:  CR(1,2,3)
 	 * 1) CR(2,3) = r2 + r3; 
 	 * 2) CR(1,2,3) = r1 + CR(2,3);
 	 * 
 	 * @param ri_1  the direct predecessor rule
 	 * @param i_1	index of the direct predecessor rule
 	 * @param ri	the current rule
 	 * @param i		index of the current rule
 	 * @param g		a graph to apply the rule 
 	 * 
 	 * @return		true, when at least one rule of created concurrent rules is applicable,
 	 * 				otherwise false
 	 */
 	private boolean buildConcurrentRulesBackward(
 			final Rule ri_1, final int i_1,
 			final Rule ri, final int i,			
 			final Graph g) {
 		System.out.println("=== >>>  ApplicabilityChecker.buildConcurrentRulesBackwards:: "+ri_1.getName()+"  &  "+ri.getName());

 		boolean result = false;
 		int size = i;
 		final List<List<ConcurrentRule>> 
		concurrentRuleListsOfRule = this.getListsOfConcurrentRulesOfRule(ri, i);
 		
// 		showObjectFlow();
 		
 		List<ConcurrentRule> list = null;
 		for (int m=size-1; m>=0; m--) {
 			if (m==size-1) {
 				// try to use object flow
 				if (this.ruleSequence.isObjFlowActive()) {
 					
  					final ObjectFlow objFlow = this.ruleSequence.getObjFlowForRules(ri_1, i_1, ri, i);
  					if (objFlow != null && !objFlow.isEmpty()) {	
  						// test:: reset depth when using ObjectFlow  
//  					this.depth = 1; 
  						final Hashtable<Object, Object> objFlowMap = objFlow.getMapping();	 					
	 					System.out.println("=== >>>  ApplicabilityChecker.buildConcurrentRulesBackwards::  USE   ObjectFlow ");
	 					list = this.makeConcurrentRules(ri_1, i_1, ri, i, objFlowMap);
  					} 
  					else {
  						list = this.makeConcurrentRules(ri_1, i_1, ri, i);
  					}
// 					list = this.makeConcurrentRulesDuetoDependency(ri_1, i_1, ri, i, matchmap);
 				} 
 				else {
  					System.out.println("=== >>>  ApplicabilityChecker.buildConcurrentRulesBackwards::  WITHOUT   ObjectFlow ");
 					list = this.makeConcurrentRules(ri_1, i_1, ri, i);
 				}
 
 				concurrentRuleListsOfRule.add(list);
 				
 			} else if (this.depth == -1 || m < this.depth) {
// 				System.out.println("=== >>>  ApplicabilityChecker.buildConcurrentRulesBackwards::  CR = (r, cr)");
 				
 				Rule r = this.ruleSequence.getRule(m); 
 				list = this.makeConcurrentRules(r, m, list, m+1);
 				
// 				list = this.makeConcurrentRulesDuetoDependency(r, m,  list, m+1, null);
 				
				concurrentRuleListsOfRule.add(list);
 			}

 			// check applicability of concurrent rules
 			if (list != null && !list.isEmpty()) {
 				int d = concurrentRuleListsOfRule.size(); 
 				for (int k = 0; k<list.size(); k++) {
 					ConcurrentRule cr = list.get(k);				
 					if (isConcurrentRuleApplicable(i, ri, 
 							this.ruleSequence.getRules(), g, 
 							cr, d, ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR)) {
 						result = true;
 						break;
 					}
 				}			
 				if (result)
 					break;
 			}
 	 	}
 				
 		return result;
 	}
 
 	private List<ConcurrentRule> makeJointlyConcurrentRule(
			final Rule r1, 
			int indx_r1,
			final Rule r2,
			int indx_r2) {
		
		final DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		dependencyContainer.enableProduceConcurrentRule(true);
		// here all overlappings will be considered
		dependencyContainer.setCompleteConcurrency(true); //this.completeConcurrency);
		
		List<ConcurrentRule> list = null; 
		try {
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
			criticalPairs = dependencyContainer.getCriticalPair(r1, r2, CriticalPair.EXCLUDE, true);
			if (criticalPairs != null) {				
				list = dependencyContainer.getConcurrentRules();
				// check overlappings against ObjectFlow
				reduceConcurrentRulesDuetoObjectFlow(r1, indx_r1, r2, indx_r2, list);
				
				extendRuleNameByIndex(list);								
			}
		} catch (Exception ex) {}
	
		return list;
	}

/*
 	private List<ConcurrentRule> makeConcurrentRulesDuetoDependency(
 			final Rule r1,
 			int indx_r1,
 			final List<ConcurrentRule> crules,
 			int indx_cr,
			final Hashtable<?, ?> matchmap) {
 		
 		final List<ConcurrentRule> reslist = new Vector<ConcurrentRule>();
 		for (int i=0; i<crules.size(); i++) {
 			final ConcurrentRule cr = crules.get(i);
 			final List<ConcurrentRule> list = makeConcurrentRulesDuetoDependency(r1, indx_r1, cr.getRule(), indx_cr, matchmap);
 			if (list != null && !list.isEmpty()) {
 				reslist.addAll(list);
 			}			
 		}
 		
 		return reslist;
 	}
 	*/
 	
	private List<ConcurrentRule> makeConcurrentRulesDuetoDependency(
			final Rule r1, 
			int indx_r1,
			final Rule r2,
			int indx_r2,
			final Hashtable<?, ?> matchmap) {
		
		final DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		dependencyContainer.enableProduceConcurrentRule(true);
		// if this.completeConcurrency Is false, only max overlapping above nodes will be considered
		// otherwise use all overlappings to create concurrent rules
		dependencyContainer.setCompleteConcurrency(this.completeConcurrency);
		
		List<ConcurrentRule> list = null; 
		try {
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
			criticalPairs = dependencyContainer.getCriticalPair(r1, r2, CriticalPair.EXCLUDE, true);
			if (criticalPairs != null) {				
				list = dependencyContainer.getConcurrentRules();
			}
		} catch (Exception ex) {}
		
		if (list == null) 
			list = new Vector<ConcurrentRule>(1);
		else {
			for (int i=0; i<list.size(); i++) {
				list.get(i).setIndexOfFirstSourceRule(indx_r1);
				list.get(i).setIndexOfSecondSourceRule(indx_r2);
			}
		}
		if (this.completeConcurrency) {	
			// make concurrent rule with disjoint (RHS1+LHS2) of (r1, r2)	
			final ConcurrentRule cr = new ConcurrentRule(r1, r2);
			if (cr.getRule() != null) {
				cr.setIndexOfFirstSourceRule(indx_r1);
				cr.setIndexOfSecondSourceRule(indx_r2);
				list.add(cr);
			}
		}
		
		// try to use object flow			
		if (!list.isEmpty())
			reduceConcurrentRulesDuetoObjectFlow(r1, indx_r1, r2, indx_r2, list);							
			
		extendRuleNameByIndex(list);
//		System.out.println("---> ApplicabilityChecker.makeConcurrentRulesDuetoDependency:  count: "+list.size());
		return list;
	}	
	
	private List<ConcurrentRule> makeConcurrentRulesDuetoDependency(
			final ConcurrentRule cr1, 
			final Rule r2,
			final Hashtable<?, ?> matchmap) {
		
//		System.out.println("---> ApplicabilityChecker.makeConcurrentRulesDuetoDependency(ConcurrentRule r1, final Rule r2 ");
		final DependencyPairContainer 
		dependencyContainer = this.makeDependencyPairContainer();
		dependencyContainer.enableProduceConcurrentRule(true);
		// if this.completeConcurrency Is false, only max overlapping above nodes will be considered
		// otherwise use all overlappings to create concurrent rules
		dependencyContainer.setCompleteConcurrency(this.completeConcurrency);
		
		List<ConcurrentRule> list = null; 
		try {
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
			criticalPairs = dependencyContainer.getCriticalPair(cr1.getRule(), r2, CriticalPair.EXCLUDE, true);
			if (criticalPairs != null) {
				list = dependencyContainer.getConcurrentRules();
			}
		} catch (Exception ex) {}
		
		if (list == null) 
			list = new Vector<ConcurrentRule>(1);		
	
		if (this.completeConcurrency) {
			// make concurrent rule with disjoint (RHS1+LHS2) of (r1, r2)	
			final ConcurrentRule cr = new ConcurrentRule(cr1, r2);
			if (cr.getRule() != null) {
				list.add(cr);
			}
		} 
		
		for (int c=0; c<list.size(); c++) {
			if (this.completeConcurRuleBackward)
				list.get(c).setSecondSourceConcurrentRule(cr1);
			else
				list.get(c).setFirstSourceConcurrentRule(cr1);
		}
	
		extendRuleNameByIndex(list);
//		System.out.println("---> ApplicabilityChecker.makeConcurrentRulessDuetoDependency:  count: "+list.size());
		return list;
	}
	
	
	private Hashtable<Object, Object> getObjectFlowForRules(
			final Rule r1,
			int indx_r1,
			final ConcurrentRule cr,
			int indx_cr) {
		
		Hashtable<Object, Object> map = new Hashtable<Object, Object>();

//		Rule r2 = cr.getFirstSourceRule();	
		
		map.putAll(cr.getReflectedInputObjectFlowFromRule(
								r1,
								this.ruleSequence.getObjFlowFromRule(r1, indx_r1)));			
		return map;
	}
	
	private List<ConcurrentRule> makeConcurrentRules(
 			final Rule r1,
 			int indx_r1,
 			final List<ConcurrentRule> crules,
 			int indx_cr) {
		
 		final List<ConcurrentRule> reslist = new Vector<ConcurrentRule>();
 		if (this.completeConcurRuleBackward) {	 		
	 		for (int i=0; i<crules.size(); i++) {
	 			final ConcurrentRule cr = crules.get(i);
	 			if (this.ruleSequence.isObjFlowActive()) {
//	 				Rule r2 = cr.getFirstSourceRule();
					// get object flow of (r1.RHS -> r2.LHS) to use it for overlapping (Graph E)
//					ObjectFlow objFlow = this.ruleSequence.getObjectFlowForRules(r1, indx_r1, r2, indx_cr); 									
//					if (objFlow != null && !objFlow.isEmpty()) {
//						System.out.println("=== >>>  try to forward the object flow to concurrent rule along embedding");
//		 	 			final Hashtable<Object, Object> matchmap12 = objFlow.getMapping(); 
	 				
	 				// output --> input		 	 			
	 				final Hashtable<Object, Object> matchmap = getObjectFlowForRules(r1, indx_r1, cr, indx_cr);
	 				if (!matchmap.isEmpty()) {
				 		// make shifting object flow along embedding
//				 		final Hashtable<Object, Object> matchmap = new Hashtable<Object, Object>();
				 		// fill matchmap from matchmapOI along embedding of cr.r1.LHS -> cr.LHS
//			 			boolean ok = true;
//			 			Enumeration<Object> outputs = matchmapOI.keys();
//			 			while (outputs.hasMoreElements() && ok) {
//			 				Object output = outputs.nextElement();	 				
//			 				Object input_cr = matchmapOI.get(output); 
//			 				if (input_cr != null) {
//			 					matchmap.put(output, input_cr);
//			 				}
//			 				ok = ok && (input_cr != null);
//			 			}
//			 			if (ok) 
			 			{
				 			final List<ConcurrentRule> list = makeConcurrentRules(r1, indx_r1, cr.getRule(), indx_cr, matchmap);	 			
				 			if (list != null) {
				 				for (int j=0; j<list.size(); j++) {	
				 					ConcurrentRule concrule = list.get(j);
				 					concrule.setSecondSourceConcurrentRule(cr);
				 					
				 					reflectObjectFlows(concrule, indx_r1, (indx_r1+concrule.getDepth()));
				 					
//				 					if (cr.reflectObjectFlow(this.ruleSequence.getObjectFlowForRule(r1, indx_r1))
//				 							&& cr.reflectObjectFlow(this.ruleSequence.getObjectFlowForRule(r2, cr.getIndexOfSecondSourceRule()))) {													 						
//				 					}
				 					
				 					reslist.add(concrule);
				 				}
				 			}
			 			}
		 			}
					else {
						System.out.println("=== >>>  "+r1.getName()+"  &  "+cr.getRule().getName()+"  ::  ObjectFlow is EMPTY. ");
		 				final List<ConcurrentRule> list = makeConcurrentRules(r1, indx_r1, cr.getRule(), indx_cr);
		 	 			if (list != null) {
		 	 				for (int j=0; j<list.size(); j++) {	
			 	 				list.get(j).setSecondSourceConcurrentRule(cr);
			 					reslist.add(list.get(j));
		 	 				}
		 	 			}
		 			}
	 			}
	 			else {
	 				final List<ConcurrentRule> list = makeConcurrentRules(r1, indx_r1, cr.getRule(), indx_cr);
	 	 			if (list != null) {
	 	 				for (int j=0; j<list.size(); j++) {	
		 	 				list.get(j).setSecondSourceConcurrentRule(cr);
		 					reslist.add(list.get(j));
	 	 				}
	 	 			}
	 			}
	 		}
		}
 		return reslist;
 	}
	
	private void reflectObjectFlows(final ConcurrentRule cr, int fromRuleIndx, int toRuleIndx) {
		for (int i=fromRuleIndx; i<=toRuleIndx; i++) {
			Rule r = this.ruleSequence.getRule(i);
			if (r != null) {
				cr.reflectObjectFlow(this.ruleSequence.getObjFlowForRule(r, i));
			}
		}
	}
	
	private List<ConcurrentRule> makeConcurrentRules(
			final Rule r1,
			int indx_r1,
			final Rule r2,
			int indx_r2,
			final Hashtable<?, ?> objFlowMap) {
		
		if (objFlowMap == null || objFlowMap.isEmpty()) {
			return makeConcurrentRules(r1, indx_r1, r2, indx_r2);
		}
		
		System.out.println("=== >>>  ApplicabilityChecker.makeConcurrentRules::  "+r1.getName()+"   "+r2.getName()+"  by  Object Flow: ");
		
		// rename similar variables of rule1
		final Hashtable<String, String> storeNewName2OldName = new Hashtable<String, String>();
		if (r1 != r2) {
			BaseFactory.theFactory().renameSimilarVariable(r2, r1, "r1_", storeNewName2OldName);
//			((VarTuple) r1.getAttrContext().getVariables()).showVariables();
//			((CondTuple) r1.getAttrContext().getConditions()).showConditions();
		}
		
		final List<ConcurrentRule> list = new Vector<ConcurrentRule>();
		
		Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		inverseRulePair = BaseFactory.theFactory().reverseRule(r1);
		Rule inverseRule1 = inverseRulePair.first.first;		
				
		int maxsize = objFlowMap.size();
		
		if (maxsize > 0) {
			// matchmap inverse:: keys to values, values to keys, because of inverse r1
			final Hashtable<Object, Object> inversematchmap = new Hashtable<Object, Object>();
			Enumeration<?> keys = objFlowMap.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				inversematchmap.put(objFlowMap.get(key), 
						inverseRulePair.second.second.getImage((GraphObject)key));
			}
						
			Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
			enums = BaseFactory.theFactory().getOverlappingByPredefinedIntersection(
						r2.getLeft(), inverseRule1.getLeft(), inversematchmap);									
			
			if (enums != null && enums.hasMoreElements()) {				
				while (enums.hasMoreElements()) {
					final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping = enums.nextElement();										
					
					if (this.checkIntersectionDuetoObjectFlow(overlapping, objFlowMap)) {
						
						ConcurrentRule cr = new ConcurrentRule(r1, inverseRulePair.first.first, r2,
								inverseRulePair.second.first, 
								inverseRulePair.second.second,
								overlapping.second,
								overlapping.first);
						
						if (cr.getRule() != null) {
							cr.setIndexOfFirstSourceRule(indx_r1);
							cr.setIndexOfSecondSourceRule(indx_r2);

							cr.reflectObjectFlow(this.ruleSequence.getObjFlowForRule(r1, indx_r1));
							cr.reflectObjectFlow(this.ruleSequence.getObjFlowForRule(r2, indx_r2));
							
							list.add(0, cr);	
								
							System.out.println("=== >>>  Concurrent rule: "
										+cr.getRule().getName()
										+"  has NACs: "+cr.getRule().getNACs().hasMoreElements()
										+", has PACs: "+cr.getRule().getPACs().hasMoreElements());
	//						((VarTuple) cr.getRule().getAttrContext().getVariables()).showVariables();
						}
					}
				}
			}
		}
				
		extendRuleNameByIndex(list);
		System.out.println("=== >>> ApplicabilityChecker.makeConcurrentRules::  count: "+list.size());
	
		if (!storeNewName2OldName.isEmpty()) {
			BaseFactory.theFactory().restoreVariableNameOfRule(r1, storeNewName2OldName);
		}

		return list;
	}
	
	private List<ConcurrentRule> makeConcurrentRules(
			final Rule r1,
			int indx_r1,
			final Rule r2,
			int indx_r2) {
		
		System.out.println("=== >>>  ApplicabilityChecker.makeConcurrentRules::  of  "+r1.getName()+"   "+r2.getName());	
		
		// rename similar variables of rule1
		final Hashtable<String, String> storeNewName2OldName = new Hashtable<String, String>();
		if (r1 != r2) {
			BaseFactory.theFactory().renameSimilarVariable(r2, r1, "r1_", storeNewName2OldName);
//			((VarTuple) rule1.getAttrContext().getVariables()).showVariables();
		}
		
		final List<ConcurrentRule> list = new Vector<ConcurrentRule>();
		
//		final Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
//			inverseRulePair = BaseFactory.theFactory().makeInverseRule(r1);
//		Rule inverseRule1 = inverseRulePair.first;			
		
//		if (list.isEmpty()) { 
			
			// make disjoint concurrent rule
			ConcurrentRule cr = new ConcurrentRule(r1, r2);
			if (cr.getRule() != null) {
				
				if (this.isOverlappingGraphValid(r1, null, r2, 
						cr.getFirstLeftEmbedding(), 
						cr.getSecondLeftEmbedding())) {	
					
					cr.setIndexOfFirstSourceRule(indx_r1);
					cr.setIndexOfSecondSourceRule(indx_r2);

					cr.reflectObjectFlow(this.ruleSequence.getObjFlowForRule(r1, indx_r1));
					cr.reflectObjectFlow(this.ruleSequence.getObjFlowForRule(r2, indx_r2));
					
					System.out.println("=== >>>  ApplicabilityChecker.makeConcurrentRules::  DISJOINT  CR: "
								+cr.getRule().getName()
								+"  has NACs: "+cr.getRule().getNACs().hasMoreElements()
								+", has PACs: "+cr.getRule().getPACs().hasMoreElements());
					list.add(cr);					
				}
			}
			
//		}
		
		extendRuleNameByIndex(list);
		System.out.println("=== >>>  ApplicabilityChecker.makeConcurrentRules::  count: "+list.size());
		
		if (!storeNewName2OldName.isEmpty()) {
			BaseFactory.theFactory().restoreVariableNameOfRule(r1, storeNewName2OldName);
		}
		
		return list;
	}
	
	private boolean isOverlappingGraphValid(
			final Rule r1,
			final Rule inverse_r1,
			final Rule r2,
			final OrdinaryMorphism morph1,
			final OrdinaryMorphism morph2) {
		
		
		boolean valid = true;		
		// make match of r1
		((ContextView) morph1.getAttrContext()).setVariableContext(true);	
		if (inverse_r1 != null) {
//			Match m1 = BaseFactory.theFactory().makeMatch(inverse_r1, morph1);	
//			if (m1 != null && inverse_r1.hasNACs()) {
//				valid = ExcludePairHelper.isMatchValid(inverse_r1, m1, null, true, null);
//			} 
//			if (!valid)	{
//				System.out.println("=== >>> Match of  rule: "+inverse_r1.getName()+"    FAILED  (NAC) ");
//				return false;
//			}
		}
		else {
			Match m1 = BaseFactory.theFactory().makeMatch(r1, morph1);	
			if (m1 != null) {
				if (r1.hasNACs()) {
					valid = ExcludePairHelper.isMatchValid(r1, m1, null, true, null);
				} 
				if (!valid)	{
					return false;
				}
			} else {
				return false;
			}
		}
		
		// make match of r2
		((ContextView) morph2.getAttrContext()).setVariableContext(true);
		Match m2 = BaseFactory.theFactory().makeMatch(r2, morph2);
		if (m2 != null) {	
			if (valid) {
				valid = ExcludePairHelper.isMatchValid(r2, m2, null, false, null);
			}
			if (!valid)	{
				return false;
			}
		} else {
			return false;
		}
		
		return valid;
	}
	
	/*
	private boolean checkIntersectionOfConcurrentRuleDuetoObjectFlow(
			final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping,
			final Hashtable<?, ?> objFlow) {
				
		boolean ok = true;
		if (objFlow != null) {
			List<GraphObject> r2LHSobjs = getOverlappingObjectsOfSecondMorphism(overlapping);
			
			Iterator<?> r2_objFlow = objFlow.values().iterator();		
			while (r2_objFlow.hasNext() && ok) {
				ok = ok && r2LHSobjs.contains(r2_objFlow.next());
			}
		}
		return ok;
	}
	
	
	private List<GraphObject> getOverlappingObjectsOfSecondMorphism(
			final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping) {		
		
		List<GraphObject> list = new Vector<GraphObject>();
		Iterator<?> elems = overlapping.first.getTarget().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (overlapping.first.getInverseImage(go).hasMoreElements()
					&& overlapping.second.getInverseImage(go).hasMoreElements()) {
				list.add(overlapping.second.getInverseImage(go).nextElement());
			}
		}
		elems = overlapping.first.getTarget().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (overlapping.first.getInverseImage(go).hasMoreElements()
					&& overlapping.second.getInverseImage(go).hasMoreElements()) {
				list.add(overlapping.second.getInverseImage(go).nextElement());
			}
		}
		return list;
	}
	*/
	
	private boolean checkIntersectionDuetoObjectFlow(
			final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping,
			final Hashtable<?, ?> objFlow) {
				
		boolean ok = true;
		if (objFlow != null) {
			List<GraphObject> r2LHSobjs = getOverlappingObjectsOfFirstMorphism(overlapping);
			
			Iterator<?> r2_objFlow = objFlow.values().iterator();		
			while (r2_objFlow.hasNext() && ok) {
				ok = ok && r2LHSobjs.contains(r2_objFlow.next());
			}
		}
		return ok;
	}
	
	private List<GraphObject> getOverlappingObjectsOfFirstMorphism(
			final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping) {		
		
		List<GraphObject> list = new Vector<GraphObject>();
		Iterator<?> elems = overlapping.first.getTarget().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (overlapping.second.getInverseImage(go).hasMoreElements()
					&& overlapping.first.getInverseImage(go).hasMoreElements()) {
				list.add(overlapping.first.getInverseImage(go).nextElement());
			}
		}
		elems = overlapping.first.getTarget().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (overlapping.second.getInverseImage(go).hasMoreElements()
					&& overlapping.first.getInverseImage(go).hasMoreElements()) {
				list.add(overlapping.first.getInverseImage(go).nextElement());
			}
		}
		return list;
	}
	
	private boolean checkConcurrentRuleDuetoObjectFlow(
			final ConcurrentRule cr,
			final Hashtable<?, ?> objFlow) {
		
		if (objFlow == null || objFlow.isEmpty()
				|| cr.getOverlappingObjectsOfSecondRule() == null) {
			return true;
		}
		
		boolean ok = true;
		boolean inside = false;
//		List<GraphObject> r2LHSobjs = cr.getOverlappingObjectsOfSecondRule();		
//		Iterator<?> r2_objFlow = objFlow.values().iterator();		
//		while (r2_objFlow.hasNext() && ok) {
//			ok = ok && r2LHSobjs.contains(r2_objFlow.next());
//		}

		Enumeration<?> outs = objFlow.keys();
		while (outs.hasMoreElements()) {
			GraphObject out = (GraphObject)outs.nextElement();
			Hashtable<GraphObject,GraphObject> map = cr.getOverlappingObjects();
			Enumeration<GraphObject> objs = map.keys();
			while (objs.hasMoreElements()) {
				GraphObject obj_rhs1 = objs.nextElement();// == output object
				GraphObject obj_lhs2 = map.get(obj_rhs1); // == input object
				GraphObject input = (GraphObject) objFlow.get(out);
				if (input != obj_lhs2) {
					ok = false;
//					return false;
				}
				else {
					inside = true;
				}
			}
		}
		if (!inside)
			return true;
		
		return ok;
	}
	
	private void reduceConcurrentRulesDuetoObjectFlow(
			final Rule r1,
			int indx_r1,
			final Rule r2,
			int indx_r2,
			final List<ConcurrentRule> list) {
		// try to use object flow
		if (this.ruleSequence.isObjFlowActive()) {
			
			final ObjectFlow objFlow = this.ruleSequence.getObjFlowForRules(r1, indx_r1, r2, indx_r2);
			if (objFlow != null && !objFlow.isEmpty()) {
				Hashtable<Object, Object> 
				map = objFlow.getMapping();
				// remove overlappings without object flow
				for (int c=0; c<list.size(); c++) {
					ConcurrentRule cr = list.get(c);
					
					if (!checkConcurrentRuleDuetoObjectFlow(cr, map)) {
						list.remove(c);
						c--;
					}
				}
			}
		}
	}

	/*
	private boolean hasConflictsWithSuccessors(final Rule r, final List<Rule> successors) {
		boolean result = false;
		for (int i=0; i<successors.size(); i++) {
			Rule ri = successors.get(i);				
			final SimpleExcludePair excludePair = makeExcludePair();
			if(!asymParallelIndependentByCPA(excludePair, r, -1,  ri, -1)) {					
				result = true;
				break;
			} 
			excludePair.dispose();
		}
//		System.out.println("=== >>> ApplicabilityChecker.hasConflictsWithSuccessors:  r2: "+r.getName()+"   "+result);
		return result;
	}
	*/
	
	private void extendRuleNameByIndex(final List<ConcurrentRule> list) {
		for (int i=1; i<list.size(); i++) {
			final Rule r = list.get(i).getRule();
			r.setName(r.getName().concat("_").concat(String.valueOf(i)));
		}
	}
	
	/*
	private boolean doesDependencyExist(
			final DependencyPairContainer dependencyContainer,
			final Rule r1, final Rule r2) {
		try {
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
			criticalPairs = dependencyContainer.getCriticalPair(r1, r2, CriticalPair.EXCLUDE, true);
			if (criticalPairs == null) {
//				System.out.println("=== >>> "+r1.getName()+" & "+r2.getName()+" : dependency does not exist! ");
				return false;
			} 
		} catch (Exception ex) {
//			System.out.println("---> "+r1.getName()+" & "+r2.getName()+" : dependency does not exist! ");
			return false;
		}
		return true;
	}
	
	
	private String makeRuleKey(final int indx, final String rName, final String criterion) {
		String ruleKey = String.valueOf(indx); 	
		ruleKey = ruleKey.concat(rName);
		ruleKey = ruleKey.concat(criterion);
		return ruleKey;
	}
	*/
	
	private void setRuleResult( 
			final int indx,
			final String ruleName, 
			final boolean result,
			final String criterion, 
			final String otherRuleName) {
		
		this.ruleSequence.setRuleResult(indx, ruleName, result, criterion, otherRuleName);
	}
	
	private void setApplicabilityResult(
			final boolean result, 
			final String criterion) {
		
		this.ruleSequence.setApplicabilityResult(result, criterion);
	}
	
	private void setNonApplicabilityResult(
			final boolean result, 
			final String criterion) {

		this.ruleSequence.setNonApplicabilityResult(result, criterion);
	}
	
	
}
