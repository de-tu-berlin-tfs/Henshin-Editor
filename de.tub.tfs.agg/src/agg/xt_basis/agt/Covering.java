/**
 * 
 */
package agg.xt_basis.agt;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.AttrMapping;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.SymbolTable;
import agg.attribute.handler.impl.javaExpr.JexExpr;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.parser.javaExpr.ASTId;
import agg.attribute.parser.javaExpr.ASTPrimaryExpression;
import agg.attribute.parser.javaExpr.SimpleNode;
import agg.parser.CriticalPair;
import agg.parser.SimpleExcludePair;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.ColimDiagram;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.Completion_NAC;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;
import agg.xt_basis.csp.CompletionPropertyBits;
import agg.xt_basis.csp.Completion_CSP;

/**
 * This class computes an amalgamated rule and amalgamated match based on
 * an interaction rule scheme and a host graph. 
 * 
 * @author olga
 *
 */
public class Covering {

	final private BaseFactory bf = BaseFactory.theFactory();
	
	private GraGra gragra;
	
	/** current rule scheme */
	private RuleScheme ruleScheme;
	
	/** enabled multi rules */	
	private List<Rule> multiRules;
	
	/** current host graph */
	private Graph hostGraph;
		 
	/** match completion strategy of the amalgamated match strategy: Completion_InjCSP */ 
	final private MorphCompletionStrategy strategy = new Completion_InjCSP();	  	  	  
	  		
	final private List<AmalgamationDataOfSingleKernelMatch> amalgamationData;
	
	final private Hashtable<GraphObject, GraphObject> amalgamObj2kernelRuleObj;
	
	/** left graph of the amalgamated rule (the graph of colim diagram) */
	private Graph leftColimGraph;	  
	/** right graph of the amalgamated rule (the graph of colim diagram)  */ 
	private Graph rightColimGraph;
	  	
	final private List<List<GraphObject>> disjointObjects;
	
	/** amalgamated rule  */ 
	private AmalgamatedRule amalgamatedRule;
	/** amalgamated match  */
	protected Match amalgamatedMatch;

	/** kernel rule  */ 
	private Rule kernelRule;
	/**  match of the kernel rule  */ 
	private Match kernelMatch;
		
	private List<GraphObject> localDisjointObjs;
	private String errorMsg="";
	
	private AmalgamationRuleData lastKernelData;
	private AmalgamationDataOfSingleKernelMatch lastDataOfSKM;
	

	
	/**
	 * Initialize a covering object with aim to compute an amalgamated rule 
	 * with amalgamated match based on the given
	 * interaction rule scheme and host graph. 
	 */
	public  Covering(final RuleScheme rs, final Graph hostGraph, final MorphCompletionStrategy s){
		this.ruleScheme = rs;
		this.kernelRule = this.ruleScheme.getKernelRule();
		this.hostGraph = hostGraph;
		if (s != null) {
			this.strategy.getProperties().set(CompletionPropertyBits.INJECTIVE,
					s.getProperties().get(CompletionPropertyBits.INJECTIVE));
			this.strategy.getProperties().set(CompletionPropertyBits.DANGLING,
					s.getProperties().get(CompletionPropertyBits.DANGLING));
			this.strategy.getProperties().set(CompletionPropertyBits.IDENTIFICATION,
					s.getProperties().get(CompletionPropertyBits.IDENTIFICATION));		
			this.strategy.getProperties().set(CompletionPropertyBits.NAC,
					s.getProperties().get(CompletionPropertyBits.NAC));
			this.strategy.getProperties().set(CompletionPropertyBits.PAC,
					s.getProperties().get(CompletionPropertyBits.PAC));
			this.strategy.getProperties().set(CompletionPropertyBits.GAC,
					s.getProperties().get(CompletionPropertyBits.GAC));
			
//			this.strategy.showProperties();
			// because the strategy of an amalgamated rule is always INJECTIVE,
			// the IDENTIFICATION condition is already satisfied
//			this.strategy.getProperties().set(CompletionPropertyBits.IDENTIFICATION,
//					s.getProperties().get(CompletionPropertyBits.IDENTIFICATION));		
		}
	   					
		this.amalgamationData = new Vector<AmalgamationDataOfSingleKernelMatch>();						
		this.disjointObjects = new Vector<List<GraphObject>>();
		this.amalgamObj2kernelRuleObj = new Hashtable<GraphObject, GraphObject>();
	}
	 	
	/** Returns constructed amalgamated rule. */    	  
	public AmalgamatedRule getAmalgamatedRule() {	    
		return this.amalgamatedRule;	  
	}
	 
	/** Returns constructed amalgamated match. */    	  
	public Match getAmalgamatedMatch() {	    
		return this.amalgamatedMatch;	  
	}
	
	/**
	 * Constructs amalgamated rule and match.
	 * <code>getErrorMessage()</code> returns a short hint of the error occurred.
	 * @return
	 * 			true by success, otherwise false (see error message)
	 */
	@SuppressWarnings("unused")
	public boolean amalgamate() {					
		boolean result = false;	
		
		this.multiRules = getEnabledMultiRules(this.ruleScheme.getMultiRules());
		boolean oneMultiRuleMatchExists = false;
		
		getKernelMatch();				
		
		boolean hasKernelMatchCompletion = true;
		while (hasKernelMatchCompletion) {
					
			boolean kernelsteps = false;
			while ((!result || this.ruleScheme.parallelKernelMatch())
					&& hasKernelMatchCompletion) {
				
				hasKernelMatchCompletion =  this.kernelMatch.nextCompletionWithConstantsChecking();		
				if (hasKernelMatchCompletion) {
					
					if (this.kernelMatch.isValid()) {
						kernelsteps = true;
						result = this.createAmalgamationData();	
						
						if (result) {
							oneMultiRuleMatchExists = true;
							
							if (!this.ruleScheme.parallelKernelMatch()) {
								break;
							} 
							clearMultiRuleMatches();
							
						} else {
							clearMultiRuleMatches();
						}
												
					}
				}
				
			}
			
			if (!result && this.ruleScheme.atLeastOneMultiMatchRequired()) {
				continue;
			}
			
			if (!kernelsteps // because partial match can be already total
					&& this.kernelMatch.isTotal() 
					&& this.kernelMatch.isAttrConditionSatisfied()
					&& this.kernelMatch.areNACsSatisfied()
					&& this.kernelMatch.arePACsSatisfied()
					&& this.kernelRule.evalFormula()
					&& this.kernelMatch.isValid()) {
					
				result = this.createAmalgamationData();
				
				if (!result && !this.ruleScheme.parallelKernelMatch()) {
					clearMultiRuleMatches();
				}
			}	
			
			if (!result
					&& !this.ruleScheme.parallelKernelMatch()
					&& this.kernelMatch.isValid()
					&& this.lastDataOfSKM != null) {
				// use last valid match
				
				this.lastDataOfSKM.put(this.kernelRule, this.lastKernelData);
				this.amalgamationData.add(this.lastDataOfSKM);
				
				if (!this.multiRules.contains(this.kernelRule)) 				
					this.multiRules.add(this.kernelRule);
				
				result = true;
			}
								
			if (result) {	
				
				createLkernLinst_RkernRinstMorphs(this.amalgamationData);	
				createInstanceRules(this.amalgamationData);
				
				if (computeColimLeft()
					    && computeColimRight()		        
					    && constructAmalgamatedRule()) {	
					
					this.amalgamatedMatch = constructAmalgamatedMatch(this.amalgamatedRule);		                 
					if (this.amalgamatedMatch != null) {
						
	//					((VarTuple)this.amalgamatedMatch.getAttrContext().getVariables()).showVariables();
						this.amalgamatedRule.adaptAttrContextValues(this.amalgamatedMatch.getAttrContext());
	//					((VarTuple)this.amalgamatedRule.getAttrContext().getVariables()).showVariables();
							
						// match is valid by construction			                	 
						this.ruleScheme.setAmalgamatedRule(this.amalgamatedRule);
						
						result = true;
					}	
					else {
						this.amalgamatedRule.dispose();
						this.amalgamatedRule = null;
						result = false;
					}
				} 
			}
		
			if (result) {
				break;
			} 
			this.clear();
			clearMultiRuleMatches();	
		}
		
		this.ruleScheme.clearMatches();
		this.clear();
		
		return result;
	}
	
	private boolean createAmalgamationData() {
		boolean result = false;
		AmalgamationRuleData 
		kernelData = createInstMatchDuetoKernelMatch(this.kernelMatch, this.kernelRule);
		if (kernelData == null)
			return false;
		
		AmalgamationDataOfSingleKernelMatch 
		dataOfSKM = new AmalgamationDataOfSingleKernelMatch(kernelData);
		// store AmalgamationDataOfSingleKernelMatch to be used later, eventually
		this.lastKernelData = kernelData;
		this.lastDataOfSKM = dataOfSKM;
			
		this.localDisjointObjs = new Vector<GraphObject>();
							
		boolean atLeastOneRule = false; //this.multiRules.isEmpty(); 
		boolean matchValid = true;
		
		for (int i=0; i<this.multiRules.size(); i++) {								
			final Rule rule = this.multiRules.get(i);
			if (rule instanceof KernelRule)	
				continue;
				
			final Match multiMatch = getPartialMultiMatch((MultiRule) rule);
			if (multiMatch != null)
				matchValid = createInstMultiMatchesDuetoKernelMatch(
																multiMatch, 
																(MultiRule) rule,
																dataOfSKM);
			atLeastOneRule = atLeastOneRule 
								|| (matchValid 
										&& !dataOfSKM.isEmpty(rule));						
		}
		// set result
		if (atLeastOneRule) {							
			this.amalgamationData.add(dataOfSKM);
			result = true;
		} else if (this.multiRules.isEmpty()) {
			result = true;
		}
			
		if (!result && !this.ruleScheme.parallelKernelMatch()) {
			clearMultiRuleMatches();
		} 	
		
		// put kernel data into amalgam data to be able 
		// to generate amalgamated rule based on kernel match only		
		if (this.multiRules.isEmpty()
				|| (!atLeastOneRule 
						&& !this.ruleScheme.atLeastOneMultiMatchRequired())) {					
			dataOfSKM.put(this.kernelRule, kernelData);
			this.amalgamationData.add(dataOfSKM);				
			if (!this.multiRules.contains(this.kernelRule)) 				
				this.multiRules.add(this.kernelRule);				
			result = true;
		}
		
		if (!result || this.ruleScheme.parallelKernelMatch()) {			
			clearMultiRuleMatches();
		}
		
		return result;
	}
	
	
	/** 
	 * Returns an error message when amalgamation process failed.
	 */	
	public String getErrorMessage() {
		return this.errorMsg;
	}
	
	/** 
	 * Makes own list of enabled multi rules from the given list.
	 */
	private List<Rule> getEnabledMultiRules(List<Rule> multis) {
		this.multiRules = new Vector<Rule>(multis.size());
		// add enabled multi rules from list
		for (int i=0; i<multis.size(); i++) {								
			final MultiRule multiRule = (MultiRule) multis.get(i);
			if (multiRule.isEnabled()) {				
				this.multiRules.add(multiRule);
			}
		}
		return this.multiRules;
	}
	
	private void clear() {
		this.disjointObjects.clear();
		this.amalgamationData.clear();
	}
	
	/*
	 * Clears matches of kernel and multi rules.
	 */
//	private void clearRuleSchemeMatches() {
//		clearKernelRuleMatche();		
//		clearMultiRuleMatches();
//	}
	
//	private void clearKernelRuleMatche() {
//		if (this.kernelMatch != null) {
//			this.kernelMatch.clear();	
//			this.kernelMatch.dispose();
//			this.kernelRule.setMatch(null);
//			this.kernelMatch = null;
//		}
//	}
	
	private void clearMultiRuleMatches() {
		for (int i=0; i<this.multiRules.size(); i++) {
			if (this.multiRules.get(i) instanceof MultiRule) {
				 final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
				 if (multiRule.getMatch() != null) {
//					 ((VarTuple)multiRule.getAttrContext().getVariables()).showVariables();
//					 multiRule.getMatch().clear();	
					 multiRule.getMatch().dispose();
					 multiRule.setMatch(null);	
				 }
			}
		}
	}
	 
	/** Create isomorphic copies of multi rules based on the kernel rule. */     
	private boolean createInstMultiMatchesDuetoKernelMatch(
			 final Match multiMatch, 
			 final MultiRule multiRule,
			 final AmalgamationDataOfSingleKernelMatch dataOfSKM) {				 	
		
		this.errorMsg = "";	
		boolean valid = false;
		// try to make more
	
		while (multiMatch.nextCompletion()) {				 	
			 if (multiMatch.isValid()) {
				 valid = true;
//				 ((VarTuple)multiMatch.getAttrContext().getVariables()).showVariables();
				 
				 makeInstMultiMatchDuetoKernelMatch(multiMatch, 
													multiRule,
													dataOfSKM);				 
			 }		 				 
		}
		
		if (!valid // because partial match set before can be total
				&& multiMatch.isTotal()
				&& multiMatch.isAttrConditionSatisfied()
				&& multiMatch.areNACsSatisfied()
				&& multiMatch.arePACsSatisfied()
				&& multiRule.evalFormula()
				&& multiMatch.isValid()) {			
			makeInstMultiMatchDuetoKernelMatch(multiMatch, 
												multiRule,
												dataOfSKM);
			valid = true;
		}
		
		multiMatch.clear();
		 		 		 		 
		return valid;
	 }
	 
	 private boolean makeInstMultiMatchDuetoKernelMatch(
			 final Match multiMatch, 
			 final MultiRule multiRule,
			 final AmalgamationDataOfSingleKernelMatch dataOfSKM) {
		 
//		 ((VarTuple)multiMatch.getAttrContext().getVariables()).showVariables();
		 
		 boolean nextComplMayExist = true;
		 
		 if (this.ruleScheme.disjointMultiMatches()) {
			 if (!isDisjoint(multiMatch, multiRule)) {
				 nextComplMayExist = false;
			 } else {
				 this.disjointObjects.add(this.localDisjointObjs);	
			 }
		 } 
		 else if (!isDeleteUseConflictFree(multiMatch, multiRule, dataOfSKM)) {
				 nextComplMayExist = false;
		 }
	 
		 if (nextComplMayExist) {
			 AmalgamationRuleData data = new AmalgamationRuleData(multiRule);
			 
			 data.isoCopyLeft = multiRule.getLeft().isomorphicCopy();
			 data.isoCopyRight = multiRule.getRight().isomorphicCopy();
			 nextComplMayExist = false;
			 if (data.isoCopyLeft != null && data.isoCopyRight != null)	{
				 // create instance match of a multi match	
				 data.instMatch = makeInstanceMatchOfRuleMatch(data.isoCopyLeft, multiMatch);
				 if (data.instMatch == null) {
					 data.isoCopyLeft.dispose();
					 data.isoCopyRight.dispose();
					 data = null;
				 }
				 else {
					 dataOfSKM.put(multiRule, data); 
//			 		((VarTuple)data.instMatch.getAttrContext().getVariables()).showVariables();
				 }
			 } else {
				 data = null;
			 }
		 }
		 return nextComplMayExist;
	 }
	 
	 
	 /**
	  * Checks whether the match of the given multi rule is disjoint due to
	  * already existing matches of other multi rules over the
	  * matches of the kernel rule.
	  * 
	  * @param multiMatch
	  * @param rule
	  * @return  true by success
	  */
	 private boolean isDisjoint(final Rule rule, final List<GraphObject> owns) {
		 if (!owns.isEmpty()) {
			 for (int k=0; k<owns.size(); k++) {
				 final GraphObject obj = owns.get(k);
				 for (int i=0; i<this.disjointObjects.size(); i++) {
					final List<GraphObject>  list = this.disjointObjects.get(i);				 
					 if (list.contains(obj)) {	
//						 System.out.println("Rule:  "+rule.getName()+"  - (multi) disjoint match failed.");
						 this.errorMsg = "Rule:  "+rule.getName()+"  - (multi) disjoint match failed.";
						 return false;					 
					 }	
				 }
			 }
		 }
		 return true;
	 }
	 
	 /**
	  * Checks whether the given match of the given multi rule is disjoint due to
	  * already existing matches of other multi rules over the single match of the 
	  * kernel rule.
	  * 
	  * @param multiMatch
	  * @param rule
	  * @return  true by success
	  */
	 private boolean isDisjoint(final Match multiMatch, final MultiRule rule) {
		 final List<GraphObject> owns = new Vector<GraphObject>();
		 final Enumeration<GraphObject> objs = multiMatch.getDomain();
		 while (objs.hasMoreElements()) {
			 final GraphObject obj = objs.nextElement();
			 if (!rule.isTargetOfEmbeddingLeft(obj)) {
				 owns.add(multiMatch.getImage(obj));				 
			 }
		 }
		 if (!owns.isEmpty()) {
			 boolean localdisjointOK = true;
			 for (int k=0; k<owns.size(); k++) {
				 final GraphObject obj = owns.get(k);				 				 				
				 if (this.localDisjointObjs.contains(obj)) {
//					 System.out.println("Rule:  "+rule.getName()+"  - local (multi) disjoint match failed.");
					 this.errorMsg = "Rule:  "+rule.getName()+"  - (multi) disjoint match failed.";
					 localdisjointOK = false;
					 break;
				 } 				 
			 }
			 if (localdisjointOK
					 && isDisjoint(rule, owns)) {
				 this.localDisjointObjs.addAll(owns); 
			 } else {
				 return false;
			 }
		 }
		 return true;
	 }
	  
	 /**
	  * Checks whether the given multi rule and match is in delete-use conflict
	  * with other already existing matches of the multi rules.
	  * 
	  * @param multiMatch	current match
	  * @param rule	current multi rule
	  * @param askMultiMatchData	already existing multi match data
	  * @return  true when no conflict found
	  */
	 private boolean isDeleteUseConflictFree(
			 final Match multiMatch, 
			 final MultiRule rule,
			 final AmalgamationDataOfSingleKernelMatch askMultiMatchData) {
		 
		 final List<GraphObject> owns = new Vector<GraphObject>();
		 final Enumeration<GraphObject> objs = multiMatch.getDomain();
		 while (objs.hasMoreElements()) {
			 final GraphObject obj = objs.nextElement();
			 if (!rule.isTargetOfEmbeddingLeft(obj)) {
				 owns.add(multiMatch.getImage(obj));				 
			 }
		 }
		 
		 if (!owns.isEmpty()) {	
			
			final Enumeration<Rule> keys = askMultiMatchData.getData().keys();			 
			while (keys.hasMoreElements()) {	
				final Rule r = keys.nextElement();
				final List<AmalgamationRuleData> datas =  askMultiMatchData.getData().get(r);		
				for (int i=0; i<datas.size(); i++) {					
					final OrdinaryMorphism m = datas.get(i).instMatch;
					final OrdinaryMorphism iso = datas.get(i).isoCopyLeft;				 
					if (iso != null && iso.getSource() == r.getLeft()) {
						
						for (int k=0; k<owns.size(); k++) {
							// object of host graph
							final GraphObject obj = owns.get(k);									 
							if (m.getInverseImage(obj).hasMoreElements()) {
								// object in domain of an already existing match
								GraphObject obj1 = m.getInverseImage(obj).nextElement();								
								if (iso.getInverseImage(obj1).hasMoreElements()) {
									// object of the LHS of its multi rule
									GraphObject obj2 = iso.getInverseImage(obj1).nextElement();
									// other multi rule deletes this object
									if (r.getImage(obj2) == null) {
										if (this.ruleScheme.checkDeleteUseConflictRequired() ){
											this.errorMsg = "Rule:  "+rule.getName()
														+"  has use-delete conflict with rule:  "+r.getName();
											return false;
										}
									} 
									// current multi rule deletes this object
									else if (rule.getImage(multiMatch
												 .getInverseImage(obj).nextElement()) == null) {
										if (this.ruleScheme.checkDeleteUseConflictRequired()) {
											this.errorMsg = "Rule:  "+rule.getName() +"  causes delete-use conflict.";
											return false;
										} else if (obj2.isNode()) {
											// check new edges from obj2 of rule r
											GraphObject img2 = r.getImage(obj2);
											Iterator<Arc> arcs = ((Node)img2).getOutgoingArcs();
											while (arcs.hasNext()) {
												if (!r.getInverseImage(arcs.next()).hasMoreElements()) {
													this.errorMsg = "Rule:  "+rule.getName() +"  causes delete-use conflict.";
													return false;
												}
											}
											arcs = ((Node)img2).getIncomingArcs();
											while (arcs.hasNext()) {
												if (!r.getInverseImage(arcs.next()).hasMoreElements()) {
													this.errorMsg = "Rule:  "+rule.getName() +"  causes delete-use conflict.";
													return false;
												}
											}
										}
									}
								}
							}
						}
					}					 
				 }
			 }
		 }
		 return true;
	 }
	
	 @SuppressWarnings("unused")
	private boolean deleteUseConflictFound() {
		 boolean result = false;
		 this.ruleScheme.propagateApplCondsOfKernelToMultiRules();
		 final List<Rule> list = this.ruleScheme.getMultiRules();
		 for (int i=list.size()-1; i>=0 && !result; i--) {
			 Rule r1 = list.get(i);
			 if (r1.isEnabled()) {
				 for (int j=0; j<list.size() && !result; j++) {
					 Rule r2 = list.get(j);
					 if (r2.isEnabled()) {
						 if (r1 != r2) {
							 if (this.delUseConflictFound((MultiRule) r1, (MultiRule) r2)) {
								 result = true;
							 }
						 }
					 }
				 }
			 }
		 }
		 this.ruleScheme.removeShiftedApplConditionsFromMultiRules();
		 return result;
	 }
	 
	 private boolean delUseConflictFound(final MultiRule r1, final MultiRule r2) {	
		 final SimpleExcludePair cp = new SimpleExcludePair();
		 cp.setGraGra(this.gragra);
		 cp.setMorphismCompletionStrategy(this.strategy);
		 cp.enableStrongAttrCheck(true);
		 cp.enableEqualVariableNameOfAttrMapping(true);
		 boolean result = false;
		 try {
			 final Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
			 conflicts = cp.isCritical(CriticalPair.EXCLUDE, r1, r2);
			 if (conflicts != null && !conflicts.isEmpty()) {
				 for (int i=0; i<conflicts.size() && !result; i++) {
					 OrdinaryMorphism om1 = conflicts.get(i).first.first;
					 OrdinaryMorphism om2 = conflicts.get(i).first.second;
					 if (this.isMultiObjConflict(r1, om1)
							 || this.isMultiObjConflict(r2, om2)) {
						 this.errorMsg = "Some multi rules are not conflict free ( "
			 					.concat(r1.getName().concat(" , ")
			 					.concat(r2.getName()).concat(" )"));
						 result = true;
					 }
				 }
			 }
		 } catch (Exception ex) {}
		 
		 return result;
	 }
	 
	 
	 private boolean isMultiObjConflict(final MultiRule r, final OrdinaryMorphism om) {
		 Enumeration<GraphObject> dom = om.getDomain();
		 while (dom.hasMoreElements()) {
			 GraphObject obj = dom.nextElement();
			 if (om.getImage(obj).isCritical()) {
				 if (!r.getEmbeddingLeft().getInverseImage(obj).hasMoreElements()) {
					 return true;
				 }
			 }
		 }
		 
		 return false;
	 }
	 
	 private AmalgamationRuleData createInstMatchDuetoKernelMatch(
			 final Match match, 
			 final Rule rule) {				 	
			     
		 AmalgamationRuleData data = new AmalgamationRuleData(rule);
		 data.isoCopyLeft = rule.getLeft().isomorphicCopy();
		 data.isoCopyRight = rule.getRight().isomorphicCopy();
		 if (data.isoCopyLeft != null && data.isoCopyRight != null) {
//		 ((VarTuple) LmultiLinst.getAttrContext().getVariables()).showVariables();			 
//		 ((VarTuple) RmultiRinst.getAttrContext().getVariables()).showVariables();
				 									 					
			 // create instance match of a plain match				 		
			 data.instMatch = makeInstanceMatchOfRuleMatch(data.isoCopyLeft, match);
			 if (data.instMatch == null) {
				 data.isoCopyLeft.dispose();
				 data.isoCopyRight.dispose();
				 data = null;
			 }
		 } else {
			 data = null;
		 }
		 return data;
	 }
	 
	 /** 
	  * For each multi rule of the amalgamation data  creates morphisms 
	  * of the lhs of the kernel rule into lhs of the instance of the multi rule 
	  * inside the host graph.
	  * Creates similar morphisms for the rhs of the kernel rule 
	  * and instance of the multi rule.
	  * 
	  * @param amalgamData 
	  */
	 private void createLkernLinst_RkernRinstMorphs(
			 final List<AmalgamationDataOfSingleKernelMatch> amalgamData
			 ) {
//		 System.out.println("### createLkernLinst_RkernRinstMorphs");
		
		 for (int j=0; j<this.amalgamationData.size(); j++) {				
			 final AmalgamationDataOfSingleKernelMatch dataOfSKM = this.amalgamationData.get(j);			
			 final AmalgamationRuleData kerneldata = dataOfSKM.getKernelData();
				
			 for (int i=0; i<this.multiRules.size(); i++) {													
				 final Rule rule = this.multiRules.get(i);	
				 if (dataOfSKM.isEmpty(rule))
						continue;					
		 
				 Hashtable<Rule, List<AmalgamationRuleData>> 
				 amalgamationRuleData = dataOfSKM.getData();
				 
				 final List<AmalgamationRuleData> datas = amalgamationRuleData.get(rule);
				 if (datas.isEmpty()) 
					 continue;
				
				 boolean failed = false;		
				 for (int k=0; k<datas.size() && !failed; k++) {			
					 final AmalgamationRuleData data = datas.get(k);
					 if (data.isoCopyLeft == null ||  data.isoCopyRight == null)
						 continue;
					 
					 OrdinaryMorphism embLeft = null;				
					 OrdinaryMorphism embRight = null;				
					 if (rule instanceof MultiRule) {					 
						 embLeft = ((MultiRule) rule).getEmbeddingLeft();	        					 
						 embRight = ((MultiRule) rule).getEmbeddingRight();				
					 } else {					
						 embLeft = data.isoCopyLeft;					
						 embRight = data.isoCopyRight;				
					 }
									
					 //add mappings to LkernLinst morphism	
					 if (rule instanceof MultiRule)
						 data.LkernelLinst = this.bf.createMorphism(
		//							 this.kernelRule.getLeft(),
									 kerneldata.isoCopyLeft.getImage(),
									 data.isoCopyLeft.getImage());
					 else {
						 data.LkernelLinst = data.isoCopyLeft;
					 }
									
					 final Enumeration<GraphObject> embLeftDomain = embLeft.getDomain();						
					 while (embLeftDomain.hasMoreElements()) {		 					
						 GraphObject domElem = embLeftDomain.nextElement();	
						 GraphObject obj = null;
						 GraphObject img = null;					
						 if (rule instanceof MultiRule)	{
							 obj = kerneldata.isoCopyLeft.getImage(domElem);
							 img = data.isoCopyLeft.getImage(embLeft.getImage(domElem));
								 					
							 try {						
								 data.LkernelLinst.addMapping(obj, img);		 						
								 adoptEntriesWhereEmpty(data.LkernelLinst, obj, img, null);					
							 } catch (BadMappingException ex) {	        						
								 failed = true;						
								 break;					
							 } 
						 }
					 }		
						
					 if (!failed) {							
						 //add mappings to RkernRinst morphism 
						 if (rule instanceof MultiRule)
							 data.RkernelRinst = this.bf.createMorphism(
		//								 this.kernelRule.getRight(),
										 kerneldata.isoCopyRight.getImage(),
										 data.isoCopyRight.getImage());
						 else {
							 data.RkernelRinst = data.isoCopyRight;
						 }
											
						 final Enumeration<GraphObject> embRightDomain = embRight.getDomain();  							
						 while(embRightDomain.hasMoreElements()) {
							 GraphObject domElem = embRightDomain.nextElement();	
							 GraphObject obj = null;		  						
							 GraphObject img = null;						
							 if (rule instanceof MultiRule)	{
								 obj = kerneldata.isoCopyRight.getImage(domElem);
								 img = data.isoCopyRight.getImage(embRight.getImage(domElem));
									 						
								 try {							
									 data.RkernelRinst.addMapping(obj, img);		  							
									 adoptEntriesWhereEmpty(data.RkernelRinst, obj, img, null);						
								 } catch (BadMappingException ex) {	        							
									 failed = true;							
									 break;						
								 }  
							 }
						 }	
					 }		
				 } 
			 }
		 }         	  
	 }

	 /**
	  * For the multi rules and all its matches over a single kernel match
	  * create an instance morphism to be basis of an instance rule. 
	  * Apply attribute context of the kernel rule to each instance morphism. 
	  */
	private void createInstanceRules(
			final List<AmalgamationDataOfSingleKernelMatch> amalgamData) {
//		System.out.println("createInstanceRules...");
		
		for (int i=0; i<this.multiRules.size(); i++) {								
			final Rule rule = this.multiRules.get(i);
			
			final List<AmalgamationRuleData> datas = new Vector<AmalgamationRuleData>();
			for (int j=0; j<amalgamData.size(); j++) {
				if (amalgamData.get(j).getRuleData(rule) != null)
					datas.addAll(amalgamData.get(j).getRuleData(rule));
			}
	 		 	
			for (int k=0; k<datas.size(); k++) {	
				final AmalgamationRuleData data = datas.get(k);
				 				
				final OrdinaryMorphism instRuleMorph = this.makeInstanceMorphism(
									 						data.isoCopyLeft.getTarget(), 
									 						data.isoCopyRight.getTarget());
								
				instRuleMorph.addToAttrContextFromList(rule.getInputParametersLeft(), true);
				instRuleMorph.addToAttrContextFromList(rule.getInputParametersRight(), true);

				instRuleMorph.adaptAttrContextValues(rule.getAttrContext());
//				((VarTuple) instRuleMorph.getAttrContext().getVariables()).showVariables();
				
				
				// isomorphism data.isoCopyLeft: Lmulti -> Linst 
				// isomorphism data.isoCopyRight: Rmulti -> Rinst						 				 
						 				 
				// set morphism mappings	
				boolean mapOK = true;
				final Enumeration<GraphObject> dom = rule.getDomain();	       				 
				while (dom.hasMoreElements()) {	         					 					 
					GraphObject obj = dom.nextElement();	         										 
					GraphObject img = rule.getImage(obj);	         										 
					GraphObject objLeft = data.isoCopyLeft.getImage(obj);	         					 					 
					GraphObject objRight = data.isoCopyRight.getImage(img);	          					 					 
					if (instRuleMorph.getImage(objLeft) == null) {						 						 
						try {														 
							instRuleMorph.addMapping(objLeft, objRight);	
							adoptEntriesWhereEmpty(instRuleMorph, objLeft, objRight, img);						 						 
						} catch (BadMappingException ex) {
							mapOK = false;
							break;
						}					 					 
					}	       				 				 
				}
			       	
				if (mapOK) {					
										
					data.instRule = this.bf.constructRuleFromMorph(instRuleMorph);
					data.instRule.setName(rule.getName()+k); 
					
					data.instRule.addToAttrContext((VarTuple)data.instMatch.getAttrContext().getVariables());
					data.instRule.adaptAttrContextValues(data.instMatch.getAttrContext()); 
					
//					((VarTuple)data.instMatch.getAttrContext().getVariables()).showVariables();
					
					if (k>0) {						 
						renameVariables(this.kernelRule, data.instRule, k);				 
					}

//					((VarTuple)data.instRule.getAttrContext().getVariables()).showVariables();
					
					this.tryToSetAttrValuesOfMorph(data.instRule);
					
				}				 
			}  		
		}
	}
	
	/**Computes left colim diagram. */ 
	private boolean computeColimLeft() {	
		final AttrContext aRuleContext = agg.attribute.impl.AttrTupleManager.getDefaultManager().newContext(AttrMapping.PLAIN_MAP );	    
		final AttrContext aLeftContext = agg.attribute.impl.AttrTupleManager.getDefaultManager().newLeftContext(aRuleContext);
	    
		this.leftColimGraph = BaseFactory.theFactory().createGraph(this.kernelRule.getTypeSet());
		this.leftColimGraph.setAttrContext(aLeftContext);	    
	    
		final ColimDiagram colimL = new ColimDiagram(this.leftColimGraph);	    
		colimL.addNode(this.leftColimGraph);			
//		colimL.addNode(this.kernelRule.getLeft());
		
		for (int j=0; j<this.amalgamationData.size(); j++) {
			final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
			
			final AmalgamationRuleData kernelData = askMatchdata.getKernelData();
			colimL.addNode(kernelData.isoCopyLeft.getImage());
			
			Enumeration<Rule> keys = askMatchdata.getData().keys();			
		    while (keys.hasMoreElements()) {
		    	final Rule rule = keys.nextElement();	 	    	
		    	final List<AmalgamationRuleData> datas = askMatchdata.getData().get(rule);
			    for (int i=0; i<datas.size(); i++) { 
			    	final AmalgamationRuleData data = datas.get(i);			
			    	if (data.LkernelLinst == null) 
			    		continue;
			    	
					OrdinaryMorphism colimedge = data.LkernelLinst;
					
					if (rule instanceof KernelRule) {
						colimL.addNode(this.kernelRule.getLeft());
						colimL.addEdge(kernelData.isoCopyLeft);
					}
					else {
						colimL.addNode(colimedge.getImage());
					}

					colimL.addEdge(colimedge);
					
					data.leftRequestEdge = new OrdinaryMorphism(
												colimedge.getImage(),		   
								 				this.leftColimGraph,
								 				AttrTupleManager.getDefaultManager().newContext(AttrMapping.PLAIN_MAP));		   
				
					colimL.requestEdge(data.leftRequestEdge);
			    }
			}
		}
		
		try {	
			colimL.computeColimit(true);
		} catch(TypeException ex) {
			this.errorMsg = "Construction of the LHS of the amalgamated rule failed.";
			return false;
		}
	
		return true;
	}
		
	/**Computes right colim diagram. */  	   
	private boolean computeColimRight() {	    	    
		final AttrContext aRuleContext = agg.attribute.impl.AttrTupleManager.getDefaultManager().newContext(AttrMapping.PLAIN_MAP);	    
		final AttrContext aRightContext = agg.attribute.impl.AttrTupleManager.getDefaultManager().newRightContext(aRuleContext);
	    
		this.rightColimGraph = BaseFactory.theFactory().createGraph(this.kernelRule.getTypeSet());
		this.rightColimGraph.setAttrContext(aRightContext);
	    	    
		final ColimDiagram colimR = new ColimDiagram(this.rightColimGraph);	    
		colimR.addNode(this.rightColimGraph);
//		colimR.addNode(this.kernelRule.getRight());		 
		
		for (int j=0; j<this.amalgamationData.size(); j++) {
			final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
			
			final AmalgamationRuleData kernelData = askMatchdata.getKernelData();
			colimR.addNode(kernelData.isoCopyRight.getImage());
			
			final Enumeration<Rule> keys = askMatchdata.getData().keys();				    
			while (keys.hasMoreElements()) {				    	
				final Rule rule = keys.nextElement();	 	    					    	
				final List<AmalgamationRuleData> datas = askMatchdata.getData().get(rule);					    
				for (int i=0; i<datas.size(); i++) { 					    	
					final AmalgamationRuleData data = datas.get(i);						 						
					if (data.RkernelRinst == null) 
			    		continue;
					
					OrdinaryMorphism colimedge = data.RkernelRinst;	
						 
					if (rule instanceof KernelRule) {
						colimR.addNode(this.kernelRule.getRight());	
						colimR.addEdge(	kernelData.isoCopyRight);
					}
					else
						colimR.addNode(colimedge.getImage());	
					
					colimR.addEdge(colimedge);
					
					data.rightRequestEdge = new OrdinaryMorphism(
						 					colimedge.getImage(),		   
						 					this.rightColimGraph,		   
						 					AttrTupleManager.getDefaultManager().newContext(AttrMapping.PLAIN_MAP));		   
					 
					colimR.requestEdge(data.rightRequestEdge);
				}					 
			}
		}
		
		try{		    				
			colimR.computeColimit(true);			
		} catch(TypeException ex) {	
			this.errorMsg = "Construction of the RHS of the amalgamated rule failed.";					
			return false;			
		}		
		return true;	 
	}
	 	  
	
	
	 /** Constructs an amalgamated rule. */   	  
	 private boolean constructAmalgamatedRule() {		 
		 final OrdinaryMorphism 
		 amalgamMorph = this.makeInstanceMorphism(this.leftColimGraph, this.rightColimGraph);
		 	 		 
	     for (int j=0; j<this.amalgamationData.size(); j++) {
	    	 boolean stored = false;
	    	 
			final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
	
		     final Enumeration<Rule> keys = askMatchdata.getData().keys();				    		
		     while (keys.hasMoreElements()) {				    					
		    	 final Rule rule = keys.nextElement();	 	    					    					
		    	 final List<AmalgamationRuleData> datas = askMatchdata.getData().get(rule);					    				
		    	 for (int i=0; i<datas.size(); i++) { 					    						
		    		 final AmalgamationRuleData data = datas.get(i);	
		    		 if (data.leftRequestEdge == null) 
				    		continue;
					 	
//		    		 System.out.println("data.instRule... "+data.instRule.getName());
//		    		 ((VarTuple) data.instRule.getAttrContext().getVariables()).showVariables();
		    		 
		    		 // set morphism mappings	     										
		    		 final Enumeration<GraphObject> dom = data.instRule.getDomain();			
		    		 while (dom.hasMoreElements()) {	           						
		    			 GraphObject obj = dom.nextElement();
		    			 GraphObject img = data.instRule.getImage(obj);	
		    			 GraphObject objLeft = data.leftRequestEdge.getImage(obj);	           						
		    			 GraphObject objRight = data.rightRequestEdge.getImage(img);		    			 		    			 
		    			 if(objLeft != null && objRight != null) {
							if (amalgamMorph.getImage(objLeft) == null) {							
			    				 try {								
			    					 amalgamMorph.addMapping(objLeft, objRight); 
			    					 GraphObject origRight = data.isoCopyRight.getInverseImage(img).nextElement();
			    					 adoptEntriesWhereEmpty(amalgamMorph, objLeft, objRight, origRight); 
			    				 } catch (BadMappingException ex) {		
			    					 this.errorMsg = "Amalgamated rule failed.\n"+ex.getMessage();
	//		    					 System.out.println("Mapping of amalgamated rule failed! "+ex.getLocalizedMessage());								
			    					 return false;							
			    				 }
							}
		    			 }	         					
		    		 }
		    		 
		    		 if (!stored) {
		    			 storeMappingAmalgamObjToKernelObj(data);
		    			 stored = true;
		    		}
		    	 
		    		 setAttrContext(data.instRule, amalgamMorph);
//		    		 ((VarTuple)amalgamMorph.getAttrContext().getVariables()).showVariables();
		    	 }			
		     }			
	     }
	     setAttrContext(this.kernelRule, amalgamMorph);
	     
//	     ((VarTuple) amalgamMorph.getAttrContext().getVariables()).showVariables();
	     
	     this.amalgamatedRule = new AmalgamatedRule(amalgamMorph);
	     this.amalgamatedRule.setName(this.ruleScheme.getName()+"-Amalgamation");
	     
//	     System.out.println("AmalgamatedRule: "+this.amalgamatedRule.getName());
//	     ((VarTuple)this.amalgamatedRule.getAttrContext().getVariables()).showVariables();
	     
//	     takeNACsFromKernelRule();		             	 	 			
//	     takeNACsFromMultiRules();	     
			
	     return true;
	 }
	  
	 private void storeMappingAmalgamObjToKernelObj(AmalgamationRuleData data) {
//		 Iterator<?> elems = data.instRule.getLeft().getNodesLinkedList().iterator();			
//		 while (elems.hasNext()) {	           						
//			 GraphObject obj = (GraphObject) elems.next();			 
//			 if (data.LkernelLinst.getInverseImage(obj).hasMoreElements()) {
//				 GraphObject kernelObj = data.LkernelLinst.getInverseImage(obj).nextElement();						 
//				 GraphObject objAmalgam = data.leftRequestEdge.getImage(obj);	           								    			 		    			 
//				 if(kernelObj != null && objAmalgam != null) {
//					 if (this.lastKernelData.isoCopyLeft.getInverseImage(kernelObj).hasMoreElements()) {
//						 GraphObject kernel = this.lastKernelData.isoCopyLeft.getInverseImage(kernelObj).nextElement();
//						 this.amalgamObj2kernelRuleObj.put(objAmalgam, kernel);
//					 }
//				 }
//			 }
//		 }
//		 elems = data.instRule.getLeft().getArcsLinkedList().iterator();			
//		 while (elems.hasNext()) {	           						
//			 GraphObject obj = (GraphObject) elems.next();
//			 if (data.RkernelRinst.getInverseImage(obj).hasMoreElements()) {
//				 GraphObject kernelObj = data.RkernelRinst.getInverseImage(obj).nextElement();
//				 GraphObject objAmalgam = data.rightRequestEdge.getImage(obj);	           						
//				 if(kernelObj != null && objAmalgam != null) {
//					 if (this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).hasMoreElements()) {
//						 GraphObject kernel = this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).nextElement();
//						 this.amalgamObj2kernelRuleObj.put(objAmalgam, kernel);
//					 }
//				 }
//			 }	
//		 }
		 
		 
		 Iterator<?> elems = data.instRule.getRight().getNodesSet().iterator();			
		 while (elems.hasNext()) {	           						
			 GraphObject obj = (GraphObject) elems.next();
			 if (data.RkernelRinst.getInverseImage(obj).hasMoreElements()) {
				 GraphObject kernelObj = data.RkernelRinst.getInverseImage(obj).nextElement();
				 GraphObject objAmalgam = data.rightRequestEdge.getImage(obj);	           						
				 if(kernelObj != null && objAmalgam != null) {
					 if (this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).hasMoreElements()) {
						 GraphObject kernel = this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).nextElement();
						 this.amalgamObj2kernelRuleObj.put(objAmalgam, kernel);
					 }
				 }
			 }	
		 }
		 elems = data.instRule.getRight().getArcsSet().iterator();			
		 while (elems.hasNext()) {	           						
			 GraphObject obj = (GraphObject) elems.next();
			 if (data.RkernelRinst.getInverseImage(obj).hasMoreElements()) {
				 GraphObject kernelObj = data.RkernelRinst.getInverseImage(obj).nextElement();
				 GraphObject objAmalgam = data.rightRequestEdge.getImage(obj);	           						
				 if(kernelObj != null && objAmalgam != null) {
					 if (this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).hasMoreElements()) {
						 GraphObject kernel = this.lastKernelData.isoCopyRight.getInverseImage(kernelObj).nextElement();
						 this.amalgamObj2kernelRuleObj.put(objAmalgam, kernel);
					 }
				 }
			 }	
		 }
	 }
	 
	 
	 public Hashtable<GraphObject, GraphObject> getMappingAmalgamToKernelRule() {
		 return this.amalgamObj2kernelRuleObj;
	 }
	 
	 /** Constructs an amalgamated match of the given amalgamated rule. */ 	  
	 private Match constructAmalgamatedMatch(final Rule amalgamRule) { 
		 Match m = this.bf.createMatch(amalgamRule, this.hostGraph);
		 amalgamRule.setMatch(m);
		 		 
		 boolean mapOK = true;	
		 for (int j=0; mapOK && j<this.amalgamationData.size(); j++) {
			 final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
		 
		     final Enumeration<Rule> keys = askMatchdata.getData().keys();				    		
		     while (mapOK && keys.hasMoreElements()) {				    					
		    	 final Rule rule = keys.nextElement();	 	    					    					
		    	 final List<AmalgamationRuleData> datas = askMatchdata.getData().get(rule);					    				
		    	
		    	 for (int i=0; i<datas.size() && mapOK; i++) { 					    						
		    		 final AmalgamationRuleData data = datas.get(i);	    		
		    		 if (data.leftRequestEdge == null) 
				    		continue;
					 
					 Iterator<?> LkernObjs = data.LkernelLinst.getOriginal().getNodesSet().iterator();	
					 while (LkernObjs.hasNext()) {					 
						 GraphObject obj = (GraphObject) LkernObjs.next();
						 GraphObject imgLi = data.LkernelLinst.getImage(obj);
						 GraphObject imgG = data.instMatch.getImage(imgLi);	 
						 GraphObject imgL = data.leftRequestEdge.getImage(imgLi);
						 try {
							 if (imgL != null && imgG != null) {
								 m.addMapping(imgL, imgG); 
							 }
						 } catch (BadMappingException ex) {
							 mapOK = false;
							 System.out.println(ex.getLocalizedMessage());
							 break;
						 }
					 }
					 LkernObjs = data.LkernelLinst.getOriginal().getArcsSet().iterator();	
					 while (LkernObjs.hasNext()) {					 
						 GraphObject obj = (GraphObject) LkernObjs.next();
						 GraphObject imgLi = data.LkernelLinst.getImage(obj);
						 GraphObject imgG = data.instMatch.getImage(imgLi);	 
						 GraphObject imgL = data.leftRequestEdge.getImage(imgLi);
						 try {
							 if (imgL != null && imgG != null) {
								 m.addMapping(imgL, imgG); 
							 }
						 } catch (BadMappingException ex) {
							 mapOK = false;
							 System.out.println(ex.getLocalizedMessage());
							 break;
						 }
					 }
					 
					 if (!m.makeDiagram(data.leftRequestEdge, data.instMatch)) {
						 System.out.println("makeDiagram   FAILED!");
						 break;
					 } 
			 
		    	 }
		     }
		 }
		 
		 if (!mapOK || !m.isTotal()) {
			 this.errorMsg = "Amalgamated match failed.\n"+m.getErrorMsg();
		 }
		 else if (this.ruleScheme.parallelKernelMatch()) {
			 if (this.glueObjectsOfAmalgamatedRule(amalgamRule, m)) {
				 return m; 
			 } 
			 this.errorMsg = "Amalgamated rule failed.\n" + this.errorMsg;
		 } 
		 else {		 
			 m.setCompletionStrategy(this.strategy);
			 m.getCompletionStrategy().getProperties()
				.set(CompletionPropertyBits.INJECTIVE, this.ruleScheme.disjointMultiMatches());
			 
			 if (this.ruleScheme.disjointMultiMatches()) {
				 if (m.getCompletionStrategy().getProperties().get(CompletionPropertyBits.DANGLING)) {
					if (m.isDanglingSatisfied()) 
						return m; 
					
					this.errorMsg = "Amalgamated match failed.\n" + m.getErrorMsg(); 
				 } else
					 return m;
			 }
			 else {
				 if (this.glueObjectsOfAmalgamatedRule(amalgamRule, m)) { 					
					if (m.getCompletionStrategy().getProperties().get(CompletionPropertyBits.DANGLING)) {
						if (m.isDanglingSatisfied()) 	
							return m;
						
						this.errorMsg = "Amalgamated match failed.\n" + m.getErrorMsg();	
					} else
						return m;					
				}
				else {
					this.errorMsg = "Amalgamated rule failed.\n" + this.errorMsg;
				}
			 }
		 }
		 
		// match failed, return null
		 amalgamRule.setMatch(null);			 
		 m.dispose();			
		 m = null;			 
		 return null; 		  
	 }
	 
	 
	 private boolean glueObjectsOfAmalgamatedRule(final Rule amalgamRule, final Match m) {		 
		 int tgCheckLevel = m.getTarget().getTypeSet().getLevelOfTypeGraphCheck();
		 m.getTarget().getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
		 
		 final Hashtable<GraphObject, GraphObject> l2r = new Hashtable<GraphObject, GraphObject>();
		 final Hashtable<GraphObject, List<GraphObject>> keep2glue = new Hashtable<GraphObject,List<GraphObject>>(); 
		 final List<GraphObject> toDelete = new Vector<GraphObject>();
		 
		 Enumeration<GraphObject> matchCodom = m.getCodomain();		 
		 while (matchCodom.hasMoreElements()) {			 
			 final GraphObject codomObj = matchCodom.nextElement();
			 // store arcs to glue
			 if (codomObj instanceof Arc) {
				 List<GraphObject> listL = m.getInverseImageList(codomObj); // LHS arcs				 
				 if (listL.size() > 1) {
					 List<GraphObject> listR = new Vector<GraphObject>(); // RHS arcs
					 for (int i=0; i<listL.size(); i++) {					 
						 GraphObject img = amalgamRule.getImage(listL.get(i));
						 if (img != null)
							 listR.add(img);
					 }	 
					 boolean shouldDelete = listL.size() > listR.size();
					 GraphObject objL = listL.get(0);
					 GraphObject objR = (listR.size() > 0)? listR.get(0): null;
					 keep2glue.put(objL, listL);
					 if (objR != null) {
						 keep2glue.put(objR, listR);
						 l2r.put(objL, objR);
						 if (shouldDelete)
							 toDelete.add(objR);
					 }
				 }
			 }
		 }
		 boolean result = true;
		 // glue nodes
		 matchCodom = m.getCodomain();		 
		 while (matchCodom.hasMoreElements()) {			 
			 GraphObject codomObj = matchCodom.nextElement();	
			 if (codomObj instanceof Node) { 
				 List<GraphObject> listL = m.getInverseImageList(codomObj); // LHS nodes
				 if (listL.size() > 1) {
					 List<GraphObject> listR = new Vector<GraphObject>();
					 for (int i=0; i<listL.size(); i++) {	
						 GraphObject obj = listL.get(i);
						 GraphObject img = amalgamRule.getImage(obj);
						 if (img != null)
							 listR.add(img);
					 }
					 boolean shouldDelete = listL.size() > listR.size();
					 GraphObject objL = listL.get(0);
					 GraphObject objR = (listR.size() > 0)? listR.get(0): null;	
					 try {
						 if (this.glueObjects(objL, listL)) {
							 if (objR != null) {
								 try {
									 if (this.glueObjects(objR, listR)) {
										 l2r.put(objL, objR);
										 if (shouldDelete)
											 toDelete.add(objR);
									 } else {
										 this.errorMsg =  "Gluing nodes failed.";
										 result = false; 
									 }
								 } catch (TypeException ex) {
									 this.errorMsg =  "Gluing nodes failed. \n"+ex.getLocalizedMessage();
									 result = false; 
								 }
							 }
						 } else {
							 this.errorMsg =  "Gluing nodes failed.";
							 result = false; 
						 }	
					 } catch (TypeException ex) {
						 this.errorMsg =  "Gluing nodes failed. \n"+ex.getLocalizedMessage();
						 result = false; 
					 }
				 }
			 }
		 }
		 // glue arcs
		 final Enumeration<GraphObject> keep = keep2glue.keys();
		 while (keep.hasMoreElements() && result) {
			 GraphObject keepObj = keep.nextElement(); 
			 List<GraphObject> glue = keep2glue.get(keepObj);
			 try {
				 if (!this.glueObjects(keepObj, glue)) {
					 this.errorMsg =  "Gluing edges failed.";
					 result = false;
				 }
			 } catch (TypeException ex) {
				 this.errorMsg =  "Gluing edges failed. \n"+ex.getLocalizedMessage();
				 result = false; 
			 }
		 }
		 // restore mapping
		 if (result) {
			 final Enumeration<GraphObject> lhs = l2r.keys();
			 while (lhs.hasMoreElements()) {				 
				 GraphObject l = lhs.nextElement();
				 if (l.isNode()) {
					 GraphObject r = l2r.get(l);
					 if (amalgamRule.getImage(l) == null) {
						 try {
							 amalgamRule.addMapping(l, r);
						 } catch (BadMappingException ex) {
							 this.errorMsg =  "Node mapping after gluing nodes failed.";
							 result = false;
							 break;
						 }
					 }
				 }
			 }
			 while (lhs.hasMoreElements()) {				 
				 GraphObject l = lhs.nextElement();
				 if (l.isArc()) {
					 GraphObject r = l2r.get(l);
					 if (amalgamRule.getImage(l) == null) {
						 try {
							 amalgamRule.addMapping(l, r);
						 } catch (BadMappingException ex) {
							 this.errorMsg =  "Edge mapping after gluing edges failed.";
							 result = false;
							 break;
						 }
					 }
				 }
			 }
		}
		
		// remove rhs node/edge  because at least one of glued lhs nodes/edges does not have an rhs image   
		if (result) {
			for (int i=0; i<toDelete.size(); i++) {
				GraphObject go = toDelete.get(i);				 
				try {
					if (go.getContext() != null) {	
						if (go instanceof Node) 
							amalgamRule.getRight().destroyNode((Node) go, true, false);
						else 
							amalgamRule.getRight().destroyArc((Arc) go, true, false);
					}
				} catch (TypeException ex) {}
			}
		}
		 
		m.getTarget().getTypeSet().setLevelOfTypeGraph(tgCheckLevel);
		if (result) {
			if (amalgamRule.getTypeSet().checkType(amalgamRule.getLeft(), tgCheckLevel).isEmpty()		
					&& amalgamRule.getTypeSet().checkType(amalgamRule.getRight(), tgCheckLevel).isEmpty()) {
				result = true;
			} 
			else {
				this.errorMsg = "Gluing of graph objects failed (type check). ";
				result = false;
			}
		}
		
		return result;
	}
	 
	private boolean glueObjects(
			final GraphObject keep, 
			final List<GraphObject> list) throws TypeException {
		
		for (int i=0; i<list.size(); i++) {
			GraphObject glue = list.get(i);
			if (keep != glue) {
				try {
					if (!keep.getContext().glue(keep, glue)) {
						return false;
					} 
					list.remove(glue);
					i--;
					
				} catch (TypeException ex) {
					throw ex;
				}
			}
		}
		return true;
	}
	
	private void setAttrContext(
			 final OrdinaryMorphism from, 
			 final OrdinaryMorphism to) {
		 
		final VarTuple varsTo = (VarTuple) to.getAttrContext().getVariables();      	   
		final VarTuple varsFrom = (VarTuple) from.getAttrContext().getVariables();	   
		for (int j=0; j<varsFrom.getSize(); j++) {	     
			final VarMember vmFrom = varsFrom.getVarMemberAt(j);	     
			final DeclMember dm = (DeclMember) vmFrom.getDeclaration();	     
			if (!varsTo.isDeclared(dm.getTypeName(), dm.getName())) {	       
				varsTo.declare(dm.getHandler(), dm.getTypeName(), dm.getName());
			}
			final VarMember vmTo = varsTo.getVarMemberAt(dm.getName());
			vmTo.setInputParameter(vmFrom.isInputParameter());
			if (vmFrom.isSet()) {
				vmTo.setExprAsText(vmFrom.getExprAsText());
			}
		 }
		 
//		 final CondTuple conds = (CondTuple) to.getAttrContext().getConditions();	   
//		 final CondTuple condsFrom = (CondTuple) from.getAttrContext().getConditions();  	   
//		 for(int j=0; j<condsFrom.getSize(); j++){	    
//			 final CondMember cm = condsFrom.getCondMemberAt(j);	     
//			 if(!cm.getExprAsText().equals("")	       				  
//					 &&!conds.contains(cm.getExprAsText())) {	         				
//				 conds.addCondition(cm.getExprAsText());
//			 }
//		 }    	 
	 }
	 
	 /* Converts NACs form kernel rule */
/*
	private void takeNACsFromKernelRule() {
	    final List<OrdinaryMorphism> NACs = this.kernelRule.getNACsList();
	    if (NACs.isEmpty()) {
	    	return;
	    }
	    
	    final OrdinaryMorphism LkernL = createLkernLcolimMorph();
	    if (LkernL == null) {
//	    	System.out.println("Covering.takeNACsFromKernelRule:   FAILED");
	    	return;
	    }
	    
	    for (int i=0; i<NACs.size(); i++) {
			final OrdinaryMorphism nac = NACs.get(i);	        
	    	final OrdinaryMorphism NkernNamalgam = nac.getImage().isomorphicCopy();        
	    	final OrdinaryMorphism 
	    	amalgamNAC = this.bf.createMorphism(amalgamatedRule.getLeft(), NkernNamalgam.getTarget());
	        boolean nacOK = true;
	    	final Enumeration dom = nac.getDomain();
	    	while (dom.hasMoreElements()) {
	    		GraphObject obj = (GraphObject) dom.nextElement();
	    		GraphObject img = nac.getImage(obj);
	    		GraphObject objRight = NkernNamalgam.getImage(img);
	    		GraphObject objLeft = LkernL.getImage(obj);
	    		if (objLeft != null && objRight != null) {
	    			try {
	    				amalgamNAC.addMapping(objLeft, objRight);
	    			} catch (BadMappingException ex) {
//	    				System.out.println("Covering.takeNACsFromKernelRule:  kernel  "+nac.getName()+"   failed.  "+ex.getLocalizedMessage());
	    				nacOK = false;
	    				break;
	    			}
	    		} else {
//	    			System.out.println("Covering.takeNACsFromKernelRule:  kernel  "+nac.getName()+"   failed.  ");
    				nacOK = false;
    				break;
	    		}
	    	}
	    	if (nacOK) {
		    	amalgamNAC.setName(nac.getName());
		    	amalgamatedRule.addNAC(amalgamNAC);	
	    	} 
	    }	  	 
	 }
*/
	 
	 /* Converts NACs from multi rules. */        	 
	/* 
	private boolean takeNACsFromMultiRules() {   	
	     int nn = this.multiRules.size();
	     if (this.multiRules.contains(this.kernelRule)) 
	    	 nn--;
		
	     for (int i=0; i<nn; i++) {	                 	     
			 final MultiRule multi = (MultiRule) multiRules.get(i);	 
			 
			 final Enumeration<OrdinaryMorphism> multiNACs = multi.getNACs();	      
			 while(multiNACs.hasMoreElements()) {	        
				 final OrdinaryMorphism nac = multiNACs.nextElement(); 
				 
				 final List<OrdinaryMorphism> isoNACs = createIsoNACs(multi, nac);                  	       
	        
				 if (!makeNACfromInstanceNAC(multi, nac, isoNACs)) {	             
					 return false;	
				 }
				 
			 }	    
		 }	    
		 return true;	  
	 }     
	  */
	
	 /* Create NAC morphism for the instance rules. */		  
	 
	/*private List<OrdinaryMorphism> createIsoNACs(
			  final Rule rule, 
			  final OrdinaryMorphism nac) {   
	    
		 final List<OrdinaryMorphism> isoNACs = new Vector<OrdinaryMorphism>();
		 
		 for (int j=0; j<this.amalgamationData.size(); j++) {
			 final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
			 if (askMatchdata.getRuleData(rule) == null)
				 continue;
			 
			 final List<AmalgamationRuleData> datas = askMatchdata.getRuleData(rule);
			 for (int i=0; i<datas.size(); i++) { 		    
				 final AmalgamationRuleData data = datas.get(i);
				 
				 final OrdinaryMorphism currentLeLi = data.isoCopyLeft;    	      	     
				 final OrdinaryMorphism NeNi = nac.getImage().isomorphicCopy();        	      
				 final OrdinaryMorphism NiLi = this.bf.createMorphism(
						 								data.isoCopyLeft.getTarget(),
						 								NeNi.getTarget());
				 
				 final Enumeration<GraphObject> dom = nac.getDomain();	      
				 while (dom.hasMoreElements()) {	       
					 final GraphObject obj = dom.nextElement();	        
					 final GraphObject img = nac.getImage(obj);	        
					 final GraphObject objR = NeNi.getImage(img);	        
					 final GraphObject objL = data.isoCopyLeft.getImage(obj);	        
					 if (objL != null && objR != null) {
						 try {
							 NiLi.addMapping(objL, objR);
						 } catch (BadMappingException ex) {	          
							 return null;    
						 }
						 if (objL.getAttribute() != null
								 && objR.getAttribute() != null) {		    
							 final ValueTuple value = (ValueTuple) objL.getAttribute();		    
							 final ValueTuple value1 = (ValueTuple) objR.getAttribute(); 		    
							 for (int ii=0; ii<value.getSize(); ii++) {		      
								 final ValueMember vm = value.getValueMemberAt(ii);		      
								 if (vm.isSet() && vm.getExpr().isVariable()) {		        
									 final ValueMember vm1 = value1.getValueMemberAt(ii);			
									 if (vm1.isSet() 
											 && !vm1.getExprAsText().equals(vm.getExprAsText())) {			  
										 vm1.setExprAsText(vm.getExprAsText());	
									 }
								 }		      
							 }  		    
						 }		  
					 }	        
				 }	      
				 isoNACs.add(NiLi);	
				 if (nac.getSize() == 0)
					 break;
			 }	 
		 }
		 return isoNACs;	  
	 }
	  
	
	 private boolean makeNACfromInstanceNAC(
			 final Rule rule, 
			 final OrdinaryMorphism nac,
			 final List<OrdinaryMorphism> isoNacs) {
		 	
		 final List<OrdinaryMorphism> isoNACs = new Vector<OrdinaryMorphism>();   
		 for (int j=0; j<this.amalgamationData.size(); j++) {
			 final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
			 if (askMatchdata.getRuleData(rule) == null)
				 continue;
			 
			 final List<AmalgamationRuleData> datas = askMatchdata.getRuleData(rule);
			 for (int i=0; i<datas.size(); i++) { 		    
				 final AmalgamationRuleData data = datas.get(i);
				 if (data.leftRequestEdge == null)
					 continue;
	
				 for (int k=0; k<isoNacs.size(); k++) {	 
					 OrdinaryMorphism NiNa = isoNacs.get(k);				 
					 
					 OrdinaryMorphism LaNa = this.bf.
					 			createMorphism(amalgamatedRule.getLeft(), NiNa.getTarget());
					 LaNa.setName(nac.getName()); 
					 
				     boolean nacOK = true;			     
				     if (nac.getSize() > 0) {
				    	 LaNa.setName(nac.getName()+k);
						 Enumeration<GraphObject> dom = NiNa.getDomain();        	   
						 while (dom.hasMoreElements()) {	      
							 GraphObject obj = dom.nextElement();	      	      
							 GraphObject objLeft = NiNa.getImage(obj);	      
							 GraphObject objRight = data.leftRequestEdge.getImage(obj);      
							 if (objLeft != null && objRight != null) {
								 try {
									 LaNa.addMapping(objRight, objLeft);
								 } catch (BadMappingException ex) {
//									 System.out.println("Covering.takeNACsFromMultiRule:  multi nac  "+LaNa.getName()+"   failed.  "+ex.getLocalizedMessage());
									 nacOK = false;
									 break;		 
								 }	       
							 } 
							 else {
								 nacOK = false;
								 break;
							 }
						 }
				     }				
				     if (nacOK) {						 
				    	 amalgamatedRule.addNAC(LaNa);					 
				     }
				     if (nac.getSize() == 0)
				    	 break;
				 }
				 if (nac.getSize() == 0)
			    	 break;
			 }
		 }
		 return true; 	   
	 }
*/
	   	   
	 /* Creates morphism of left hand side of the kernel rule into left colim diagram. */	  
	/* 
	private OrdinaryMorphism createLkernLcolimMorph() { 
		 OrdinaryMorphism leftRequest = null;
		 OrdinaryMorphism LkernLinst = null;
		 OrdinaryMorphism isoLeft = null;
		 
		 for (int j=0; j<this.amalgamationData.size(); j++) {
			 final AmalgamationDataOfSingleKernelMatch askMatchdata = this.amalgamationData.get(j);
			 if (askMatchdata.getData().isEmpty())
				 continue;
			 
			 final AmalgamationRuleData kernelData = askMatchdata.getKernelData();
			 leftRequest = kernelData.leftRequestEdge;
			 isoLeft = kernelData.isoCopyLeft;
			 
			 final Enumeration<Rule> keys = askMatchdata.getData().keys();				    
			 while (keys.hasMoreElements() && (leftRequest == null)) {				    					
				 final Rule rule = keys.nextElement();	 	    					    					
				 final List<AmalgamationRuleData> datas = askMatchdata.getData().get(rule);					    				
				 for (int i=0; i<datas.size(); i++) { 					    						
					 final AmalgamationRuleData data = datas.get(i);						
					 if (data.leftRequestEdge != null) {						
						 leftRequest = data.leftRequestEdge;						
						 break;					
					 }				
				 }			
			 }
			 		 
			 if (askMatchdata.getData().get(this.kernelRule) != null) {
				 AmalgamationRuleData data = askMatchdata.getData().get(this.kernelRule).get(0);
				 LkernLinst = data.LkernelLinst;
				 isoLeft = data.isoCopyLeft;
			 }
			 else {		 
				Enumeration<List<AmalgamationRuleData>> all = askMatchdata.getData().elements();
				while (all.hasMoreElements() && (LkernLinst == null)) {
					 List<AmalgamationRuleData> datas = all.nextElement();
					 for (int i=0; i<datas.size(); i++) { 					    						
						 final AmalgamationRuleData data = datas.get(i);
						 if (data.LkernelLinst != null) {
							 LkernLinst = data.LkernelLinst;
							 break;
						 }
					 }
				 }
			 }
			 
			 if (LkernLinst != null && leftRequest != null) {
				 
				 final OrdinaryMorphism LkernL = this.bf.createMorphism(		
						 					 		this.kernelRule.getLeft(),
						 					 		amalgamatedRule.getLeft());		 
				 //add mappings to LkerL morphism	
				 final Enumeration<GraphObject> el = isoLeft.getDomain();
				 while (el.hasMoreElements()) {		   
					 final GraphObject obj = el.nextElement();		   
					 GraphObject img = leftRequest.getImage(LkernLinst.getImage(isoLeft.getImage(obj)));
					 if (img == null 
							 && isoLeft == LkernLinst) {
						 img = leftRequest.getImage(LkernLinst.getImage(obj));
					 }
					 if (obj != null && img != null) {
						 try {				 
							 LkernL.addMapping(obj, img);
						 } catch (BadMappingException ex) {
//							 System.out.println("Covering.createLkernLcolimMorph  mapping FAILED "+ex.getLocalizedMessage());
							 return null;
						 }
					 } else {
//						 System.out.println("Covering.createLkernLcolimMorph  FAILED ");
						 return null;
					 }
				 }	
				 
				 return LkernL; 
			 }
		 }
		 return null;
	 } 
	 */
	 	 
	 /** Returns match of the kernel rule. */   
	 private void getKernelMatch() {
		 this.kernelMatch = this.kernelRule.getMatch();
		 if (this.kernelMatch == null) {
			 this.kernelMatch = this.bf.createMatch(this.kernelRule, this.hostGraph);
			 this.kernelRule.setMatch(this.kernelMatch);
			 
//			 if (this.strategy.getProperties().get(CompletionPropertyBits.INJECTIVE)) 
//				 this.kernelMatch.setCompletionStrategy(new Completion_NAC(new Completion_InjCSP())); 
//			 else 
//				 this.kernelMatch.setCompletionStrategy(new Completion_NAC(new Completion_CSP())); 
		 }
//		 else {
//			 this.kernelMatch.getCompletionStrategy().getProperties()
//				.set(CompletionPropertyBits.INJECTIVE, 
//						this.strategy.getProperties().get(CompletionPropertyBits.INJECTIVE));			 
//		 }
		 
		 if (this.strategy.getProperties().get(CompletionPropertyBits.INJECTIVE)) 
			 this.kernelMatch.setCompletionStrategy(new Completion_NAC(new Completion_InjCSP())); 
		 else 
			 this.kernelMatch.setCompletionStrategy(new Completion_NAC(new Completion_CSP())); 
		 
		 this.kernelMatch.getCompletionStrategy().getProperties()
			.set(CompletionPropertyBits.IDENTIFICATION, 
					this.strategy.getProperties().get(CompletionPropertyBits.IDENTIFICATION));
		 this.kernelMatch.getCompletionStrategy().getProperties()
			.set(CompletionPropertyBits.NAC, 
					this.strategy.getProperties().get(CompletionPropertyBits.NAC));
		 this.kernelMatch.getCompletionStrategy().getProperties()
			.set(CompletionPropertyBits.PAC, 
					this.strategy.getProperties().get(CompletionPropertyBits.PAC));
		 this.kernelMatch.getCompletionStrategy().getProperties()
			.set(CompletionPropertyBits.GAC, 
					this.strategy.getProperties().get(CompletionPropertyBits.GAC));
		 this.kernelMatch.getCompletionStrategy().getProperties()
			.set(CompletionPropertyBits.DANGLING, 
					this.strategy.getProperties().get(CompletionPropertyBits.DANGLING));
		 // unset dangling bit for match of kernel rule
		 this.kernelMatch.getCompletionStrategy().getProperties()
		 					.set(CompletionPropertyBits.DANGLING, false);
		 this.kernelMatch.getCompletionStrategy().setRandomisedDomain(this.strategy.isRandomisedDomain());
//		 this.kernelMatch.getCompletionStrategy().showProperties();
		 
		 this.kernelMatch.getCompletionStrategy().initialize(this.kernelMatch);
	 }
	 
	 
	 /** Returns partial match of the specified multi rule. */    
	 private Match getPartialMultiMatch(final MultiRule multiRule) {
		 Match multiMatch = multiRule.getMatch();
//		 System.out.println("getPartialMultiMatch::::  "+multiRule.getName()+"   match:  "+multiMatch);
		 if (multiMatch == null) {
			 multiMatch = multiRule.getMatch(this.kernelRule); 
		 }
		 if (multiMatch != null) {
			 if (this.strategy.getProperties().get(CompletionPropertyBits.INJECTIVE)) {
				 multiMatch.setCompletionStrategy(new Completion_NAC(new Completion_InjCSP()));
			 } else {
				 multiMatch.setCompletionStrategy(new Completion_NAC(new Completion_CSP()));
			 }
			 
			 multiMatch.getCompletionStrategy().getProperties()
				.set(CompletionPropertyBits.IDENTIFICATION, 
						this.strategy.getProperties().get(CompletionPropertyBits.IDENTIFICATION));
			 multiMatch.getCompletionStrategy().getProperties()
				.set(CompletionPropertyBits.NAC, 
						this.strategy.getProperties().get(CompletionPropertyBits.NAC));
			 multiMatch.getCompletionStrategy().getProperties()
				.set(CompletionPropertyBits.PAC, 
						this.strategy.getProperties().get(CompletionPropertyBits.PAC));
			 multiMatch.getCompletionStrategy().getProperties()
				.set(CompletionPropertyBits.GAC, 
						this.strategy.getProperties().get(CompletionPropertyBits.GAC));
			// unset dangling bit for match of multi rule
			 multiMatch.getCompletionStrategy().getProperties()
	 						.set(CompletionPropertyBits.DANGLING, false);
			 multiMatch.getCompletionStrategy().setRandomisedDomain(this.strategy.isRandomisedDomain());
//			 multiMatch.getCompletionStrategy().showProperties();
			 
			 multiMatch.getCompletionStrategy().initialize(multiMatch);
		 }
		 return multiMatch;
	 }
	 	 
	 private OrdinaryMorphism makeInstanceMorphism(final Graph src, final Graph tar) {
		 AttrContext context = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newContext(AttrMapping.PLAIN_MAP);
		 OrdinaryMorphism morph = new OrdinaryMorphism(src, tar, context);
		 return morph;
	 }
	 
	 private OrdinaryMorphism makeInstanceMatchOfRuleMatch(
			 final OrdinaryMorphism LruleLinst, 
			 final Match ruleMatch) {
		 
//		 ((VarTuple)ruleMatch.getAttrContext().getVariables()).showVariables();

		 OrdinaryMorphism instMatch = this.makeInstanceMorphism(LruleLinst.getTarget(), 
															ruleMatch.getTarget());
		 instMatch.setName(ruleMatch.getName());
		 
		 final Enumeration<GraphObject> e = ruleMatch.getDomain();
		 while (e.hasMoreElements()) {	      
			 final GraphObject o = e.nextElement();	      
			 final GraphObject oImg = LruleLinst.getImage(o);	      
			 final GraphObject img = ruleMatch.getImage(o);     	      
			 try {
				 instMatch.addMapping(oImg, img);
				 
				 // set attr value from graph to LHS object 
//				 adoptEntries(instMatch, img, oImg);
				 				 
			 } catch (BadMappingException ex) {			 
				 return null; 	   
			 }
		 }
		 
		 instMatch.addToAttrContext((VarTuple) ruleMatch.getRule().getAttrContext().getVariables());
		 
//		 instMatch.addToAttrContextFromList(ruleMatch.getRule().getInputParametersLeft(), true);
//		 instMatch.addToAttrContextFromList(ruleMatch.getRule().getInputParametersRight(), true);
//		 instMatch.addToAttrContextFromList(ruleMatch.getRule().getNonInputParametersOfNewGraphObjects(), false);
		 
		 instMatch.adaptAttrContextValues(ruleMatch.getAttrContext());
//		 ((VarTuple)instMatch.getAttrContext().getVariables()).showVariables();
		 
		 return instMatch;
	 }	 
	 
	 private void tryToSetAttrValuesOfMorph(final OrdinaryMorphism morph) {
		 for (Iterator<Node> e1 = morph.getSource().getNodesSet().iterator(); e1.hasNext();) {
			 tryToSetValueFromVar(morph, e1.next());			 
		 }
		 for (Iterator<Arc> e1 = morph.getSource().getArcsSet().iterator(); e1.hasNext();) {
			 tryToSetValueFromVar(morph, e1.next());			 
		 }
		 for (Iterator<Node> e1 = morph.getTarget().getNodesSet().iterator(); e1.hasNext();) {
			 tryToSetValueFromVarAndExpr(morph, e1.next());
		 }
		 for (Iterator<Arc> e1 = morph.getTarget().getArcsSet().iterator(); e1.hasNext();) {
			 tryToSetValueFromVarAndExpr(morph, e1.next());
		 }
	 }
	 
	 private void tryToSetValueFromVar(final OrdinaryMorphism morph, final GraphObject go) {
		 if (go.getAttribute() != null) {
			 ValueTuple value = (ValueTuple) go.getAttribute();
			 for (int j=0; j<value.getNumberOfEntries(); j++) {
				 ValueMember mem = value.getValueMemberAt(j);
				 if (mem.isSet() && mem.getExpr().isVariable()) {
					 VarMember var = ((VarTuple)morph.getAttrContext().getVariables())
						 					.getVarMemberAt(mem.getExprAsText());
					 if (var != null && var.isSet()) {
						 mem.setExpr(var.getExpr());
					 }
				 }					 
			 }
		 }		 
	 }
	 
	 private void tryToSetValueFromVarAndExpr(final OrdinaryMorphism morph, final GraphObject go) {
		 if (go.getAttribute() != null) {
			 ValueTuple value = (ValueTuple) go.getAttribute();
			 for (int j=0; j<value.getNumberOfEntries(); j++) {
				 ValueMember mem = value.getValueMemberAt(j);
				 if (mem.isSet()) {
					 if (mem.getExpr().isVariable()) {
						 VarMember var = ((VarTuple)morph.getAttrContext().getVariables())
		 					.getVarMemberAt(mem.getExprAsText());
						 if (var != null && var.isSet()) {
							 mem.setExpr(var.getExpr());
						 }
					 } else if (mem.getExpr().isComplex()) {				 
						 try {
							 mem.getExpr().evaluate(morph.getAttrContext());
						 } catch (AttrHandlerException ex) {
							 System.out.println("Covering.tryToComputeAttrExpresionOfMorph:: "+ex);
						 }
					 }	
				 }
			 }
		 }
	 }
	 
	 @SuppressWarnings("unused")
	private void adoptEntries(
			 final OrdinaryMorphism morph, 
			 final GraphObject from, 
			 final GraphObject to) {
		 	   
		 if (from.getAttribute() != null
				 && to.getAttribute() != null) {			 	     
			 ValueTuple valFrom = (ValueTuple) from.getAttribute(); 
			 ValueTuple valTo = (ValueTuple) to.getAttribute(); 
			 for (int i=0; i<valFrom.getNumberOfEntries(); i++) {
				 ValueMember vmFrom = valFrom.getEntryAt(i);
				 if (vmFrom.isSet()) {
					 ValueMember vmTo = valTo.getEntryAt(vmFrom.getName());
					 if (vmTo != null)
						 vmTo.setExprAsText(vmFrom.getExprAsText());
				 }
			 }
		 }	  
	 }
	 
	 private void adoptEntriesWhereEmpty(
			 final OrdinaryMorphism morph, 
			 final GraphObject from, 
			 final GraphObject to,
			 final GraphObject origTo) {
		 	   
		 if (morph.getImage(from) != null
				 && from.getAttribute() != null
				 && to.getAttribute() != null) {
			 
			final ValueTuple valuefrom = (ValueTuple) from.getAttribute();
			final ValueTuple value = (ValueTuple) to.getAttribute();
			for (int i = 0; i < value.getSize(); i++) {
				ValueMember vm = value.getValueMemberAt(i);
				ValueMember vmfrom = valuefrom.getValueMemberAt(vm.getName());
				if (origTo == null) {
					if (!vm.isSet() && vmfrom != null && vmfrom.isSet()) {					
						vm.setExprAsText(vmfrom.getExprAsText());
					}
				} else {
					ValueMember vm_origTo = ((ValueTuple)origTo.getAttribute()).getValueMemberAt(vm.getName());
					if (vm_origTo.isSet()) {
						if (!vm.isSet() && vmfrom != null && vmfrom.isSet()) {	
							vm.setExprAsText(vmfrom.getExprAsText());
						}
					} 
				}
			}			 
		 }	  
	 }

	 /*
	 private void setAttrCond(final Rule r, final OrdinaryMorphism LiRi) {	
//		 System.out.println("Covering.setAttrCond ...");
		 final VarTuple varsLiRi = (VarTuple) LiRi.getAttrContext().getVariables();
		 final VarTuple varsR = (VarTuple) r.getAttrContext().getVariables();
	     for(int jj=0; jj<varsR.getSize(); jj++) {	        
	    	 final VarMember vm = varsR.getVarMemberAt(jj);		
	    	 final DeclMember dm = (DeclMember) vm.getDeclaration();	        
	    	 if(!varsLiRi.isDeclared(dm.getTypeName(), dm.getName())){	          
	    		 varsLiRi.declare(dm.getHandler(), dm.getTypeName(), dm.getName());	          
	    		 final VarMember var = varsLiRi .getVarMemberAt(vm.getName());	          
	    		 var.setInputParameter(vm.isInputParameter());		  
	    		 if(var.isInputParameter() && ((ValueMember)vm).isSet()) {		     
	    			 ((ValueMember)var).setExprAsText(((ValueMember)vm).getExprAsText());
	    		 }     	        
	    	 }	     
	     }
	  
	     final CondTuple condsR = (CondTuple) r.getAttrContext().getConditions();
	     final CondTuple condsLiRi = (CondTuple) LiRi.getAttrContext().getConditions();
	     for (int jj=0; jj<condsR.getSize(); jj++) {	       
	    	 final CondMember cm = condsR.getCondMemberAt(jj);		
	    	 if(!cm.getExprAsText().equals("") && !condsLiRi.contains(cm.getExprAsText())) {		 
	    		 condsLiRi.addCondition(cm.getExprAsText());
	    	 }	     
	     }	  
	 }
	 */
	  
	 private void setAttributeVariable(
			 final String from, 
			 final String to, 
			 final AttrContext ac,
			 final Iterator<?> e) {
	
		 final VarTuple vars = (VarTuple) ac.getVariables();  
		 while (e.hasNext()) {	     
			 final GraphObject obj = (GraphObject)e.next();
			 if (obj.getAttribute() == null) {
				 continue;
			 }
			 final ValueTuple fromObj = (ValueTuple) obj.getAttribute();	     
			 for (int i=0; i<fromObj.getSize(); i++) {	       
				 final ValueMember fromVM = fromObj.getValueMemberAt(i);	      
				 if (fromVM.isSet()) {	         
					 if (fromVM.getExpr().isVariable()	            
							 && fromVM.getExprAsText().equals(from)) {	           
						 fromVM.setExprAsText(to);
						 // test variable
						 final VarMember var = vars.getVarMemberAt(to);		  
						 if(var != null) {	
//							 System.out.println("Variable  "+to+"  exists");		   
						 } else {
							 // declare variable
							 vars.declare(fromVM.getDeclaration().getHandler(), fromVM.getDeclaration().getTypeName(), to);
						 }
					 }		 
					 else if (fromVM.getExpr().isComplex()) {
//						 final VarMember toVM = vars.getVarMemberAt(to);
						 final JexExpr oldExpr = (JexExpr) fromVM.getExpr();
						 
						 final Vector<String> variables = new Vector<String>();		  
						 oldExpr.getAllVariables(variables);
						 
						 this.findPrimaryAndReplace(
								 (SimpleNode) oldExpr.getAST(),
		                          from, to, 
		                          ac,
		                          vars);
					 }	      
				 }	     
			 }	   
		 }	  
	 }
	  
	 private void renameVariables(final Rule basicr, 
			 final OrdinaryMorphism LiRi, 
			 int index) {
	
//		 System.out.println("Covering.renameVariables ... for  "+index);
		 final String mark = String.valueOf(index); 
		 final VarTuple varsBasic = (VarTuple) basicr.getAttrContext().getVariables();
	           
		 final VarTuple varsLiRi = (VarTuple)LiRi.getAttrContext().getVariables();
	     for (int i=0; i<varsLiRi.getSize(); i++) {	       
	    	 final VarMember vm = varsLiRi.getVarMemberAt(i);	       
	    	 final VarMember vmKernel = varsBasic.getVarMemberAt(vm.getName());	 
	    	 // do not rename variables of the kernel rule
	    	 if(vmKernel == null) {	         
	    		 final String from = vm.getName();		 
	    		 final String to = vm.getName()+mark;
	        
	    		 vm.getDeclaration().setName(to);		 
		 
	    		 // rename variables in left/right graphs of instance morphs
	    		 setAttributeVariable(from, to, LiRi.getAttrContext(),
	    				 LiRi.getSource().getNodesSet().iterator());
	    		 setAttributeVariable(from, to, LiRi.getAttrContext(),
	    				 LiRi.getSource().getArcsSet().iterator());
	    		 
	    		 setAttributeVariable(from, to, LiRi.getAttrContext(),
	    				 LiRi.getTarget().getNodesSet().iterator());
	    		 setAttributeVariable(from, to, LiRi.getAttrContext(),
	    				 LiRi.getTarget().getArcsSet().iterator());
	    		 
	    		 // rename variables in conditions		
	    		 final CondTuple conds = (CondTuple) LiRi.getAttrContext().getConditions(); 	         
	    		 for (int j=0; j<conds.getSize(); j++) {	          
	    			 final CondMember cm = conds.getCondMemberAt(j);  		   
//	    			 final String condStr = cm.getExprAsText();		   
	    			 final Vector<String> v1 = cm.getAllVariables();		   
	    			 if (v1.contains(from)) {		     
	    				 final JexExpr oldExpr = (JexExpr) cm.getExpr();
	    				 final Vector<String> variables = new Vector<String>();		  
	    				 oldExpr.getAllVariables(variables);
	    				 
	    				 this.findPrimaryAndReplace((SimpleNode) oldExpr.getAST(),
		                          from, to, 
		                          LiRi.getAttrContext(),
		                          null);
	    			 }		 
	    		 }	      
	    	 } 	    
	     } 	   
	 }     
	 
	 private void findPrimaryAndReplace(
			  final SimpleNode node,
			  final String from,  
			  final String to, 
			  final AttrContext ac,
			  final VarTuple vars) {
		  		  
//		 System.out.println("Covering.findPrimaryAndChange:  in  "+node);
		 
		 final SymbolTable symbs = ac;
		 
//		 System.out.println(ac);
		 
		 for (int j=0; j<node.jjtGetNumChildren(); j++) {
			 final SimpleNode n = (SimpleNode) node.jjtGetChild(j);
			 
//			 System.out.println(j+"  Child of ast:  "+n+"  is  "+n.getString()+"   "+n.getIdentifier()); 
			 
			 if (n instanceof ASTPrimaryExpression
					 || n instanceof ASTId) {
				 
//				 String ident = "";
//				 if (n instanceof ASTPrimaryExpression)
//					 ident = ((ASTPrimaryExpression) n).getIdentifier();
//				 else if (n instanceof ASTId)
//					 ident = ((ASTId) n).getIdentifier();
//				 System.out.println("Identifier:  "+ ident+"   "+ n.getString());
				
				 if (n.getString().equals(from)) {
					 
//					 System.out.println("SymbolTable: "+from+"  type= "+symbs.getType(from)+"  expr= "+ symbs.getExpr(from));
//					 System.out.println("SymbolTable: "+to+"  type= "+symbs.getType(to)+"  expr= "+ symbs.getExpr(to));

					 boolean to_found = false;
					 final ContextView context = (ContextView) symbs;
					 final VarTuple vt = (VarTuple) context.getVariables();  
					 for (int i=0; i<vt.getSize(); i++ ){
						 final VarMember vm = vt.getVarMemberAt(i);
//						 System.out.println(vm.getName()+"   "+vm);	    
						 if (vm.getName().equals(to)) {
							 to_found = true;
//							 System.out.println(to+"  exists::  "+vm.getName()+"   "+vm);       
							 final HandlerType t = vm.getDeclaration().getType();
							 try {
								 final HandlerExpr expression = vm.getHandler().newHandlerExpr(t, to); 
//								 System.out.println(expression.getAST()); 
								 final SimpleNode test = (SimpleNode) expression.getAST().jjtGetChild(0);   
//								 System.out.println(test);
								 node.replaceChild(n, test);
//								 System.out.println("child replaced: getString()= "+node.jjtGetChild(0).getString()+"    "+node.jjtGetChild(0).toString());
							 } catch (AttrHandlerException ex) {}       
						 }					  	
					 }
					 if (!to_found) {
//						 System.out.println("Something wrong:  "+to+"  NOT FOUND in SymbolTable! Try to replace if variable exists i VarTuple.");
						 for (int i=0; i<vars.getSize(); i++) {
							 final VarMember vm = vars.getVarMemberAt(i);
//							 System.out.println(vm.getName()+"   "+vm);	    
							 if (vm.getName().equals(to)) {
								 to_found = true;
//								 System.out.println(to+"  exists in vars  "+vm.getName()+"   "+vm);  

								 final HandlerType t = vm.getDeclaration().getType();
								 try {
									 final  HandlerExpr expression = vm.getHandler().newHandlerExpr(t, to); 
//									 System.out.println(expression.getAST()); 
									 final SimpleNode test = (SimpleNode) expression.getAST().jjtGetChild(0);   
//									 System.out.println(test);
									 node.replaceChild(n, test);
//									 System.out.println("child replaced: getString()= "+node.jjtGetChild(0).getString()+"    "+node.jjtGetChild(0).toString());
								 } catch (AttrHandlerException ex) {}       
							 }
						 }
					 }
				 } else if (n.getString().contains(from)) {
//					 System.out.println("Take Child of ast:  "+n);
					 findPrimaryAndReplace(n, from, to, ac, vars);
				 }
			 }
			 else {
//				 System.out.println("Take Child of ast:  "+n);
				 findPrimaryAndReplace(n, from, to, ac, vars);
			 }
		 }
	 }

	 
}
