/**
 * 
 */
package agg.ruleappl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import agg.parser.CriticalPairOption;
import agg.util.Pair;
import agg.util.Triple;
import agg.util.XMLHelper;
import agg.xt_basis.Arc;
import agg.xt_basis.ConcurrentRule;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.RuleScheme;

/**
 * This class implements a rule sequence and checking applicability and non-applicability
 * criteria of this sequence.
 * 
 * The constructor needs a grammar <code>agg.xt_basis.GraGra</code> and 
 * CPA options <code>agg.parser.CriticalPairOption</code> instances.
 * 
 * Adding a rule which belongs to the given gragra can be done 
 * by the method <code>add(agg.xt_basis.Rule)</code>
 * or <code>addRule(String)</code> where a string is a rule name,
 * but also by using  <code>setRules(List<String></code> with list of rule names.
 * 
 * To set a graph on which the rule sequence should be checked is available by 
 * <code>setGraph(agg.xt_basis.Graph</code>. The given graph must belong to the gragra.<br>
 * 
 * To check applicability of the rule sequence is possible by
 * calling the method <code>check</code>.
 * 
 * There are two results of this check: applicability and non-applicability result.
 * 
 * Applicability result can be asked by <code>Pair<Boolean, String> getApplicabilityResult()</code>.
 * The returned pair is (true, ApplicabilityConstants.UNDEFINED) or (false, criterion). 
 * 
 * Non-Applicability result can be asked by <code>Pair<Boolean, String> getNonApplicabilityResult()</code>.
 * The returned pair is (false, ApplicabilityConstants.UNDEFINED) or (true, criterion).<br>
 *  
 * @see class <code>agg.ruleappl.ApplicabilityConstants</code> 
 * for the possible meaning of applicability and non-applicability criterion.<br>
 *
 * The class <code>agg.xt_basis.RuleSequencesGraTraImpl</> can be used for the application
 * of a rule sequence. It is set by calling the method
 * <code>agg.xt_basis.RuleSequencesGraTraImpl.setRuleSequence(RuleSequence</>.
 * 
 * @author olga 
 *
 */
public class RuleSequence implements GraTraEventListener {
	
	public static final int OBJECT_FLOW_TRANSITIVE_CLOSURE_FAILED = 0;
	public static final int OBJECT_FLOW_PERSISTENT_FAILED = 1;
	
	public static final String TRAFO_BY_OBJECT_FLOW = "trafoByOF";	
	public static final String TRAFO_BY_ARS = "trafoByARS";
	private static final String TRAFO_BY_IN_OUT_PARAM = "trafoByIOP";
	
	private String name = "RuleSequence";	
	private GraGra gragra;		
	private CriticalPairOption cpOption;
	private Graph graph;
	
	private List<Pair<List<Pair<String, String>>, String>> 
	subSequenceList = new Vector<Pair<List<Pair<String, String>>, String>>();
	
	private final Vector<Rule> rules = new Vector<Rule>();	
	private final Vector<String> ruleNames = new Vector<String>(); // flattened rule sequence
			
	private final Pair<Boolean, String>
	applResult = new Pair<Boolean, String>(new Boolean(false), ApplicabilityConstants.UNDEFINED);
	
	private final Pair<Boolean, String>
	nonapplResult = new Pair<Boolean, String>(new Boolean(false), ApplicabilityConstants.UNDEFINED);
	
	private final Hashtable<String, Pair<Boolean, List<String>>> 
	ruleResults = new Hashtable<String, Pair<Boolean, List<String>>>();
		
	final private ApplicabilityChecker checker;

	protected boolean checked;	
	protected boolean checkAtGraph;
	
	private final Map<String, List<List<ConcurrentRule>>> 
	concurrentRules = new Hashtable<String, List<List<ConcurrentRule>>>();
	// key String is "rule.hashCode:ruleIndex"
		
	protected final Hashtable<String, ObjectFlow> objectFlow;
	
	private boolean enabledObjectFlow;
	private boolean completeNodesOF;
	private int objectFlowError = -1;
	
	private boolean trafoByOF, trafoByARS;
	
	private int startIndx;
	
	private boolean usePreviousSequenceResults = true;
	
	protected MatchSequence matchSequence;
	
	public RuleSequence(final GraGra gra, final String name)  {
		this.gragra = gra;
		this.name = name;
		
		this.checker = new ApplicabilityChecker(this, this.gragra);
		this.enabledObjectFlow = true;
		this.usePreviousSequenceResults = true;
		
		this.checkAtGraph = true;
		if (this.gragra != null)
			this.graph = this.gragra.getGraph();
		
		this.subSequenceList = new Vector<Pair<List<Pair<String, String>>, String>>();		
		this.objectFlow = new Hashtable<String, ObjectFlow>();
		this.matchSequence = new MatchSequence(this);
		this.matchSequence.setObjectFlow(this.objectFlow);
		this.completeNodesOF = false;
	}
	
	public RuleSequence(final GraGra gra, final String name, final CriticalPairOption option)  {
		this(gra, name);
		
		this.cpOption = option;		
	}

	public RuleSequence(			
			final GraGra gra,
			final String name,
			final CriticalPairOption option,
			final List<Pair<List<Pair<String, String>>, String>> ruleSubSequenceList) {		
		
		this(gra, name, option);
		this.makeFlatSequence();
	}
	
	public RuleSequence(			
			final GraGra gra, 
			final String name,
			final List<Pair<List<Pair<String, String>>, String>> ruleSubSequenceList) {		
		
		this(gra, name);
		this.makeFlatSequence();
	}
	
	
	public boolean isObjFlowDefined() {			
		return !this.objectFlow.isEmpty();
	}
	
	public Hashtable<String, ObjectFlow> getObjectFlow() {
		return this.objectFlow;
	}
	
	public void setCriticalPairOption(final CriticalPairOption option) {
		this.cpOption = option;
		this.checker.setCriticalPairOption(option);
	}
	
	public CriticalPairOption getCriticalPairOption() {
		return this.cpOption;
	}
	
	public void setName(final String  aName) {
		this.name = aName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void refresh() {
		this.clear();
		this.makeFlatSequence();
	}
	
	public List<Pair<List<Pair<String, String>>, String>> getSubSequenceList() {
		return this.subSequenceList;
	}
	
	public void setSubsequenceList(final List<Pair<List<Pair<String, String>>, String>> list) {		
		this.subSequenceList = list;
	}
	
	private void copySubsequenceList(final List<Pair<List<Pair<String, String>>, String>> list) {		
		this.subSequenceList.clear();
		
		for (int i=0; i<list.size(); i++) {
			Pair<List<Pair<String, String>>, String> pseg = list.get(i);
			
			List<Pair<String, String>> sublist = new Vector<Pair<String, String>>();
			
			for (int j=0; j<pseg.first.size(); j++) {						
				Pair<String, String> pRule = pseg.first.get(j);
				sublist.add(new Pair<String, String>(pRule.first, pRule.second));				
			}
			
			this.subSequenceList.add(
					new Pair<List<Pair<String, String>>, String>(sublist, pseg.second));
		}
	}
	
	public void makeFlatSequence() {
		this.rules.clear();
		this.ruleNames.clear();
		for (int i=0; i<this.subSequenceList.size(); i++) {
			Pair<List<Pair<String, String>>, String> pseg = this.subSequenceList.get(i);
			int seqIters = 2; // when *
			if (!pseg.second.equals("*")) {
				seqIters = Integer.valueOf(pseg.second).intValue();
			}

			List<Pair<String, String>> rList = pseg.first;
			for (int its=0; its<seqIters; its++) {
				for (int j=0; j<rList.size(); j++) {						
					Pair<String, String> pRule = rList.get(j);
					int rIters = 2; // statt *
					if (!pRule.second.equals("*")) {
						rIters = Integer.valueOf(pRule.second).intValue();
					}
					Rule r = this.gragra.getRuleByQualifiedName(pRule.first);
					if (r != null) {
						if (r instanceof RuleScheme) {
//							this.rules.add(r);
							r = ((RuleScheme) r).getKernelRule();
						}
//						else if (r instanceof KernelRule) {
//							this.rules.add(r.getRuleScheme());
//						}
//						else {
//							this.rules.add(r);
//						}
						for (int itr=0; itr<rIters; itr++) {
							if (r.getRuleScheme() != null && r instanceof KernelRule) {
								this.rules.add(r.getRuleScheme()); //test
								this.ruleNames.add(r.getRuleScheme().getName()+"."+r.getName());								
							} 
							else {
								this.rules.add(r); // test
								this.ruleNames.add(r.getName());
							}
						}
					}						
				}
			}			
		}
	}
	
	
	public void dispose() {
		this.clear();
		this.gragra = null;
		this.cpOption = null;
	}
	
	public ApplicabilityChecker getApplicabilityChecker() {
		return this.checker;
	}
	
	protected Map<String, List<List<ConcurrentRule>>> getConcurrentRulesContainer() {
		return this.concurrentRules;
	}
	
	protected List<List<ConcurrentRule>> getListsOfConcurrentRules(
			final Rule r, 
			final int indx) {
		String key = String.valueOf(r.hashCode()).concat(":").concat(String.valueOf(indx));
		return this.concurrentRules.get(key);
	}
	
	protected void putListsOfConcurrentRules(
			final Rule r, 
			final int indx,
			final List<List<ConcurrentRule>> lists) {		
		String key = String.valueOf(r.hashCode()).concat(":").concat(String.valueOf(indx));
		this.concurrentRules.put(key, lists);
	}
	
	public List<ConcurrentRule> getApplicableConcurrentRules() {
		return this.checker.getApplicableConcurrentRules();
	}
	
	public Iterator<List<List<ConcurrentRule>>> getConcurrentRules() {
		return this.concurrentRules.values().iterator();
	}
	
	public void reinit() {
		this.clearResult();
		
		for (int i=0; i<this.rules.size(); i++) {
			Rule r = this.rules.get(i);
			if (r.getMatch() != null) {
				r.getMatch().dispose();
				r.setMatch(null);
			}
		}
		
		this.matchSequence.reinit(this);
		this.removeEmptyObjFlow();
	}
	
	public void clear() {
		this.matchSequence.clear();
		this.objectFlow.clear();
		this.rules.clear();
		this.ruleNames.clear();
		this.clearResult();
	}
	
	public void clearObjFlow() {
		this.objectFlow.clear();
	}
	
	public void clearResult() {		
		this.concurrentRules.clear();
		clearApplResult();
		clearNonApplResult(); 
		this.ruleResults.clear();
	} 	
	
	private void clearApplResult() {
		this.applResult.first = new Boolean(false);
		this.applResult.second = ApplicabilityConstants.UNDEFINED;
		this.checked = false;
	}
	
	private void clearNonApplResult() {
		this.nonapplResult.first = new Boolean(false);
		this.nonapplResult.second = ApplicabilityConstants.UNDEFINED;
		this.checked = false;
	}
	
	public GraGra getGraGra() {
		return this.gragra;
	}
	
	public void setCheckAtGraph(boolean b) {
		this.checkAtGraph = b;
	}
	
	public boolean doesCheckAtGraph() {
		return this.checkAtGraph;
	}
	
	public void setGraph(final Graph g) {
		this.removeObjFlowOfGraph();
		this.clearResult();

		this.graph = g;		
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public void setTrafoByObjFlow(boolean b) {
		this.trafoByOF = b;
	}
	
	public boolean isTrafoByObjFlow() {
		return this.trafoByOF;
	}
	
	public void setTrafoByARS(boolean b) {
		this.trafoByARS = b;
	}
	
	public boolean isTrafoByARS() {
		return this.trafoByARS;
	}
	
	public void setUsePreviousSequenceResults(boolean b) {
		this.usePreviousSequenceResults = b;
	}
	
	public boolean makeUseOfPreviousSequenceResults() {
		return this.usePreviousSequenceResults;
	}
	
	public void setStartIndexOfCheck(int indx) {
		this.startIndx = indx;
	}
	
	public int getStartIndexOfCheck() {
		return this.startIndx;
	}
	
	public Rule getStartRule() {
		if (!this.rules.isEmpty()) {
			if (this.startIndx >= 0 && this.startIndx < this.rules.size()) 
				return this.rules.get(this.startIndx);
			
			return this.rules.get(0);	
		}
		
		return null;
	}
	
	public void enableObjFlow(boolean b) {
		this.enabledObjectFlow = b;
	}
	
	/**
	 * Returns true if its object flow is enabled,
	 * otherwise false.
	 * If the object flow is empty, it is not taken into account.
	 * 
	 * @see boolean isObjectFlowActive()
	 */
	public boolean isObjFlowEnabled() {
		return this.enabledObjectFlow;
	}
	
	public void enableCompleteObjFlowOfNodes(boolean b) {
		this.completeNodesOF = b;
	}
	
	public boolean isCompleteObjFlowOfNodesEnabled() {
		return this.completeNodesOF;
	}
	
	/**
	 * Returns true if its object flow is enabled and not empty,
	 * otherwise false.
	 */
	public boolean isObjFlowActive() {
		return this.enabledObjectFlow && !this.objectFlow.isEmpty();
	}
	
	public boolean isGraphUsedInObjFlow() {
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");			
			if (keyItems[0].equals("0")) 
				return true;
		}
		return false;
	}
	
	public boolean containsRuleLoop() {
		for (int i=0; i<this.subSequenceList.size(); i++) {
			Pair<List<Pair<String, String>>, String> pseg = this.subSequenceList.get(i);			
			for (int j=0; j<pseg.first.size(); j++) {						
				Pair<String, String> pRule = pseg.first.get(j);
				if ("*".equals(pRule.second))
					return true;
			}
		}
		return false;
	}
	
	public boolean containsOneRuleIterationOnly() {
		boolean result = !this.isEmpty();
		for (int i=0; i<this.subSequenceList.size(); i++) {
			Pair<List<Pair<String, String>>, String> pseg = this.subSequenceList.get(i);			
			for (int j=0; j<pseg.first.size(); j++) {						
				Pair<String, String> pRule = pseg.first.get(j);
				if (!"1".equals(pRule.second))
					result = false;
			}
		}
		return result;
	}
	
	/*
	 * Returns true if this rule sequence contains only one subsequence, 
	 * otherwise false.
	 */
	public boolean hasOneSubsequence() {
		return this.subSequenceList.size() == 1;
	}
	
	public boolean containsRuleScheme() {
		for (int i=0; i<this.rules.size(); i++) {
			Rule r = this.rules.get(i);
			if (r.getRuleScheme() != null)
				return true;
		}

		return false;
	}
	
	public void addRule(final Rule r) {
		this.rules.add(r);
		this.ruleNames.add(r.getName());	
		
		if (this.subSequenceList.isEmpty()) {
			List<Pair<String, String>> l = new Vector<Pair<String, String>>();			
			Pair<List<Pair<String, String>>, String> 
			p = new Pair<List<Pair<String, String>>, String>(l, "1");
			this.subSequenceList.add(p);
		}
		
		this.subSequenceList.get(0).first.add(new Pair<String, String>(r.getName(), "1"));
	}
	
	public boolean addRule(final String rname) {
		if (this.gragra.getRule(rname) != null) {
			Rule r = this.gragra.getRule(rname);			
			if (this.gragra.isLayered() && !this.rules.isEmpty()) {
				Rule r0 = this.rules.get(0);
				if (r0.getLayer() != r.getLayer()) {
					return false;
				}
			}
			this.addRule(this.gragra.getRule(rname));
			return true;
		} 
		return false;
	}
	
	public void setRules(List<String> sequence) {
		this.clear();
		this.makeRules(sequence);
	}
	
	public void setRules(Iterator<Rule> sequence) {
		this.clear();
		this.makeRules(sequence);
	}
	
	public Rule getRule(final int indx) {
		if (indx >= 0 && indx < this.rules.size()) {
			return this.rules.get(indx);
		} 
		return null;
	}
	
	public Rule getRule(final String rname) {
		for (int i=0; i<this.rules.size(); i++) {
			final Rule r = this.rules.get(i);
			if (r.getName().equals(rname))
				return r;
		}
		return null;
	}
	
	public int getIndexOf(final Rule r) {
		return this.rules.indexOf(r);
	}
	
	public List<Rule> getRules() {
		return this.rules;
	}
	
	public void resetRuleNames(final List<String> rnames) {
		this.ruleNames.clear();
		this.ruleNames.addAll(rnames);
		this.resetRules();
	}
	
	private void resetRules() {
		Vector<Rule> v = new Vector<Rule>(this.rules);
		this.rules.clear();
		for (int i=0; i<this.ruleNames.size(); i++) {
			String rn = this.ruleNames.get(i);
			for (int j=0; j<v.size(); j++) {
				Rule r = v.get(j);
				if (r.getQualifiedName().equals(rn)) {
					this.rules.add(r);
					break;
				}
			}
		}
	}
	
	public List<String> getRuleNames() {
		return new Vector<String>(this.ruleNames);
	}
	
	public int getSize() {
		return this.rules.size();
	}
	
	public boolean isEmpty() {
		return this.rules.isEmpty();
	}

	public boolean isValid() {
		boolean ok = true;
		for (int i=0; i<this.rules.size() && ok; i++) {
			Rule r = this.rules.get(i);
			boolean found = false;
			Enumeration<Rule> en = this.gragra.getRules();
			while (en.hasMoreElements() && !found) {
				Rule ru = en.nextElement();
				if (r == ru) {
					found = true;
				}
				else if (ru.getRuleScheme() != null
						&& ((RuleScheme)ru.getRuleScheme()).getKernelRule() == r) {
					found = true;
				}
			}
			ok = found;
		}
		return ok;
	}
	
	public boolean containsRuleWithGACs() {
		for (int i=0; i<this.rules.size(); i++) {
			if (this.rules.get(i).hasEnabledACs(true))
				return true;
		}
		return false;
	}
	
	public String getText() {
		String text = "";
		if (this.graph == null) {
			text = getRuleNamesString();
		} else {
			text = text.concat(this.graph.getName());
			text = text.concat(" <= ");
			text = text.concat(getRuleNamesString());
		}
		return text;
	}
	
	public String getToolTipText() {
		String text = "";
		for (int i = 0; i < this.subSequenceList.size(); i++) {
			Pair<List<Pair<String, String>>, String> g = this.subSequenceList.get(i);
			String grpStr = "";
			List<Pair<String, String>> grpRules = g.first;
			long grpIters = -1;
			String grpItersStr = g.second;
			if (grpItersStr.equals("*"))
				grpStr = grpStr.concat("( ");
			else {
				try {
					grpIters = (new Long(g.second)).longValue();
					if (grpRules.size() > 1 || grpIters > 1)
						grpStr = grpStr.concat("( ");
				} catch (java.lang.NumberFormatException ex) {}
			}
			for (int j = 0; j < grpRules.size(); j++) {
				Pair<String, String> p = grpRules.get(j);
				String rulename = p.first;
				grpStr = grpStr.concat(rulename);
				long ruleIters = -1;
				String ruleItersStr = p.second;
				if (ruleItersStr.equals("*")){
					grpStr = grpStr.concat("{");
					grpStr = grpStr.concat(ruleItersStr);
					grpStr = grpStr.concat("}");
				}
				else {
					ruleIters = (new Long(p.second)).longValue();
					if (ruleIters > 1) {
						grpStr = grpStr.concat("{");
						grpStr = grpStr.concat(String.valueOf(ruleIters)); 
						grpStr = grpStr.concat("}");
					}
				}
				grpStr = grpStr.concat(" ");
			}
			if (grpItersStr.equals("*"))
				grpStr = grpStr.concat(")");
			else if (grpRules.size() > 1 || grpIters > 1)
				grpStr = grpStr.concat(")");

			if (grpRules.size() > 0) {
				if (grpItersStr.equals("*")) {
					grpStr = grpStr.concat("{"); 
					grpStr = grpStr.concat(grpItersStr);
					grpStr = grpStr.concat("}");
				}
				else if (grpIters > 1) {
					grpStr = grpStr + "{" + grpIters + "}";
				}
			} else {
				grpStr = "()";
			}
			grpStr = grpStr.concat("\n");
			text = text.concat(grpStr);
		}
		
		if (this.graph != null) {
			text = this.graph.getName().concat(" <= ").concat(text);
		}
		
		return text;
	}
	
	public String getRuleNamesString(){
		String str = "( ";
		for (int i=0; i<this.rules.size(); i++) {
			str = str.concat(this.rules.get(i).getName());
			str = str.concat(" ");
		}
		str = str.concat(")");
		return str;
	}
	
	public Pair<Boolean, String> getApplicabilityResult() {
		return this.applResult;
	}
	
	public Pair<Boolean, String> getNonApplicabilityResult() {
		return this.nonapplResult;
	}	
	
	
	private void makeRules(final List<String> sequence) {
		for (int j=0; j<sequence.size(); j++) {
			Rule r = this.gragra.getRule(sequence.get(j));
			if (r != null){
				this.rules.add(r);
				this.ruleNames.add(r.getName());
			}
		}
	}
				
	private void makeRules(final Iterator<Rule> sequence) {	
		while(sequence.hasNext()) {
			Rule r = sequence.next();
			this.rules.add(r);
			this.ruleNames.add(r.getName());
		}
	}
	
	public void removeRule(final int indx) {
		if (indx >= 0 && indx < this.rules.size()) {
			Rule r = this.rules.get(indx);
			removeObjFlowOfRule(r, indx);
			
			this.rules.remove(indx);
			this.ruleNames.remove(indx);	
			this.clearResult();
		}
	}
	
	public void removeRule(final Rule r) {
		if (this.rules.contains(r)) {
			int indx = this.rules.indexOf(r);
			
			removeObjFlowOfRule(r, indx);
			
			this.rules.remove(r);
			this.ruleNames.remove(indx);
			
			this.clearResult();
		}
	}
	
	private void removeObjFlowOfRule(final Rule r, int indx) {
		int ind = indx;
		if (this.graph != null)
			ind++;
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");			
			if (Integer.valueOf(keyItems[0]).intValue() == ind
					|| Integer.valueOf(keyItems[1]).intValue() == ind) {
				this.objectFlow.remove(key);
				
				keys = this.objectFlow.keys();
				break;
			}
		}
	}
	
	/**
	 * Creates a new RuleSequence as a copy of the current RuleSequence instance
	 * inclusive its object flow and applicability resp. non-applicability results.
	 * In case when the new copied sequence will be extended the start index of check
	 * should be set to the index of the first added rule:
	 * <code>copiedSeq.setStartIndexOfCheck(indxOfExtention)</code>
	 * 
	 */
	public RuleSequence getCopy() {
		
		final RuleSequence seq = new RuleSequence(
				this.gragra, 
				this.name.concat("_copy"),
				this.cpOption);
		
		seq.setCriticalPairOption(this.cpOption);
		seq.setRules(this.getRules().iterator());
		seq.setGraph(this.graph);	
		seq.copySubsequenceList(this.subSequenceList);	
		if (!seq.getRuleNames().equals(this.getRuleNames())) {
			seq.resetRuleNames(this.getRuleNames());
		}
		seq.copyObjFlow(this.getObjectFlow());	
		seq.enableCompleteObjFlowOfNodes(this.completeNodesOF);
		seq.tryToApplyResultsOfRuleSequence(this);
		
		return seq;
	}
	
	private void copyObjFlow(final Hashtable<String, ObjectFlow> objFlow) {
		Enumeration<String> keys = objFlow.keys();
		while (keys.hasMoreElements()) {
			final String k = keys.nextElement();
			final ObjectFlow flow = objFlow.get(k);
			
			final ObjectFlow f = new ObjectFlow(
					flow.srcOfOutput,
					flow.srcOfInput,
					flow.indxOfOutput,
					flow.indxOfInput,
					new Hashtable<Object,Object>(flow.outputInputMap));
			
			this.objectFlow.put(k, f);			
		}		
	}
	
	public void moveRule(final int from, final int to) {
		if (from >= 0 && from < this.rules.size()
				&& to >= 0 && to < this.rules.size()) {
			Rule r = this.rules.get(from);
			this.clearResult();		
			move(r, from, to);	
		}
	}
	
	private void move(final Rule r, final int from, final int to) {
		this.rules.remove(from);
		this.ruleNames.remove(from);
		
		this.rules.add(to, r);		
		this.ruleNames.add(to, r.getName());	
	}
		
	public boolean isChecked() {
		return this.checked;
	}
	
	public void uncheck() {
		this.clearResult();
		this.checked = false;
	}
	
	/*
	 * Number of direct enabling predecessors
	 */
	public void setDepthOfConcurrentRule(final int d) {
		this.checker.setDepthOfConcurrentRule(d);
	}
	
	public int getDepthOfConcurrentRule() {
		return this.checker.getDepthOfConcurrentRule();
	}
	
	/**
	 *  If the specified parameter is true,
	 *  only maximal intersection of rhs and lhs of base rules  
	 *  is taken into account for building a concurrent rule,
	 *  otherwise all possible intersections are used.
	 */
	public void setCompleteConcurrency(boolean b) {
		this.checker.setCompleteConcurrency(b);
	}
	
	public boolean getCompleteConcurrency() {
		return this.checker.getCompleteConcurrency();
	}
	
	public boolean getCompleteCPAOfConcurrency() {
		return this.checker.getCompleteCPAOfConcurrency();
	}
	
	/*
	 * Complete recognition of potential conflict free 
	 * summarized predecessors
	 */
	public void setCompleteCPAOfConcurrency(boolean b) {
		this.checker.setCompleteCPAOfConcurrency(b);
	}
	
//	public void setConsistentConcurrency(boolean b) {
//		this.checker.setConsistentConcurrency(b);
//	}
//	
//	public boolean getConsistentConcurrency() {
//		return this.checker.getConsistentConcurrency();
//	}
	
	/**
	 * Set the value of the local variable for checking 
	 * the dangling edge condition when a rule is node-deleting.
	 */
	public void setIgnoreDanglingEdgeOfDelNode(boolean b) {
		this.checker.setIgnoreDanglingEdgeOfDelNode(b);
	}
	
	/**
	 * Returns the value of the local variable for checking 
	 * the dangling edge condition when a rule is node-deleting.
	 */
	public boolean getIgnoreDanglingEdgeOfDelNode() {
		return this.checker.getIgnoreDanglingEdgeOfDelNode();
	}
	
	/**
	 * Checks the current rule sequence.
	 * Do not forget to call <code>this.setCriticalPairOption(CriticalPairOption)</code> before.
	 * 
	 * This method starts with checking of validity of the defined object flow of this sequence.
	 * Use the method <code>int getMessageOfInvalidObjectFlow</code> to get the error message:
	 * <code>RuleSequence.OBJECT_FLOW_TRANSITIVE_CLOSURE_FAILED = 0</code>
	 * <code>RuleSequence.OBJECT_FLOW_PERSISTENT_FAILED = 1</code>,
	 * otherwise (-1).
	 * When the object flow is valid or not defined the applicability criteria will be checked.
	 */
	public boolean check() {
		if (this.checked) {
			this.reinit();
			this.matchSequence.reinit(this);
		}
		
		if (!this.isObjFlowValid()) {			
			return false;
		}
		
//		this.tryCompleteObjFlowTransClosure();
		
		boolean result = this.checker.check();
		this.checked = true;
		return result;
	}
	
	public MatchSequence getMatchSequence() {
		return this.matchSequence;
	}
	
	protected void saveConcurrentRules() {
		System.out.println("saveConcurrentRules...  (for test only)");
		boolean dosave = false;
		int nn = 0;
		// add concurrent rules to grammar and save
		for (int i=0; i<this.rules.size(); i++) {
			Rule r = this.rules.get(i);
			String key = String.valueOf(r.hashCode()).concat(":").concat(String.valueOf(i));
			List<List<ConcurrentRule>> lists = this.concurrentRules.get(key);
			if (lists != null) {
				if (!lists.isEmpty()) {			
					dosave = true;
				}
				for (int j=0; j<lists.size(); j++) {
					List<ConcurrentRule> list = lists.get(j);
					for (int k=0; k<list.size(); k++) {
						Rule cr = list.get(k).getRule();
						this.gragra.addRule(cr);
						nn++;
					}
				}
			}
		}
		if (dosave) {
			String aname = "CR_".concat(this.gragra.getName()).concat(".ggx");
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(this.gragra);
			xmlh.save_to_xml(aname);
			
			//remove concurrent rules from grammar
			for (int i=0; i<nn; i++) {
				int indx = this.gragra.getListOfRules().size()-1;
				this.gragra.getListOfRules().remove(indx);
			}
		}
	}
	
	public Hashtable<String, Pair<Boolean, List<String>>> getRuleResults() {
		return this.ruleResults;
	}
	
	public Pair<Boolean, List<String>> getRuleResult(
			final int indx,
			final String ruleName, 
			final String criterion) {
		
		String key = this.makeRuleKey(indx, ruleName, criterion);
//		System.out.println("\nRuleSequence.getRuleResult   "+key+"   "+this.ruleResults.get(key));
		return this.ruleResults.get(key);
	}
	
	public boolean getRuleApplicabilityResult(final int indx, final String ruleName) {
		boolean result = false;
		if (indx==0) {
			String criterion = ApplicabilityConstants.INITIALIZATION;
			String key = this.makeRuleKey(indx, ruleName, criterion);
			Pair<Boolean, List<String>> pair = this.ruleResults.get(key);
			if (pair != null) {	
				result = pair.first.booleanValue();
//				System.out.println(result+"   "+pair.second);
			}
		} else { // indx > 0
			String criterion = ApplicabilityConstants.PURE_ENABLING_PREDECESSOR;
			String key = this.makeRuleKey(indx, ruleName, criterion);
			Pair<Boolean, List<String>> pair = this.ruleResults.get(key);
			if (pair != null) {
				result = result || pair.first.booleanValue();
//				System.out.println(pair.first.booleanValue()+"   "+pair.second);
			}
			if (pair == null || !result) {
				criterion = ApplicabilityConstants.DIRECT_ENABLING_PREDECESSOR;
				key = this.makeRuleKey(indx, ruleName, criterion);				
				pair = this.ruleResults.get(key);
				if (pair != null) {
					result = result || pair.first.booleanValue();
//					System.out.println(pair.first.booleanValue()+"   "+pair.second);
				}
			}
			if (pair == null || !result) {						
				criterion = ApplicabilityConstants.PARTIAL_ENABLING_PREDECESSOR;
				key = this.makeRuleKey(indx, ruleName, criterion);
				pair = this.ruleResults.get(key);
				if (pair != null) {
					result = result || this.ruleResults.get(key).first.booleanValue();	
//					System.out.println(pair.first.booleanValue()+"   "+pair.second);
				}
			}
			if (pair == null || !result) {
				criterion = ApplicabilityConstants.PREDECESSOR_NOT_NEEDED;
				key = this.makeRuleKey(indx, ruleName, criterion);
				pair = this.ruleResults.get(key);
				if (pair != null) {
					result = result || this.ruleResults.get(key).first.booleanValue();
//					System.out.println(pair.first.booleanValue()+"   "+pair.second);
				}
			}
		}
		return result;
	}
	
	public boolean getRuleNonApplicabilityResult(final int indx, final String ruleName) {
		boolean result = false;
		String criterion = ApplicabilityConstants.INITIALIZATION_ERROR;
		String key = this.makeRuleKey(indx, ruleName, criterion);
		Pair<Boolean, List<String>> pair = this.ruleResults.get(key);
		if (pair != null) {
			result = pair.first.booleanValue();
//			System.out.println(pair.first.booleanValue()+"   "+pair.second);
			if (!result) {
				criterion = ApplicabilityConstants.NO_ENABLING_PREDECESSOR;
				key = this.makeRuleKey(indx, ruleName, criterion);
				pair = this.ruleResults.get(key);
				if (pair != null) {
					result = result || pair.first.booleanValue();
//					System.out.println(pair.first.booleanValue()+"   "+pair.second);
				}
			}
		}
		return result;
	}
	
	private String makeRuleKey(final int indx, final String rName, final String criterion) {
		String ruleKey = String.valueOf(indx);	
		ruleKey = ruleKey.concat(rName);
		ruleKey = ruleKey.concat(criterion);
		return ruleKey;
	}
	
	public void setRuleResult(
			final int indx,
			final String ruleName, 
			final boolean result,
			final String criterion, 
			final String otherRuleName) {
		final List<String> v = new Vector<String>();		
		v.add(criterion);
		v.add(otherRuleName);
		final Pair<Boolean, List<String>> pair = new Pair<Boolean, List<String>>(Boolean.valueOf(result), v);		
		String key = makeRuleKey(indx, ruleName, criterion);
//		System.out.println("RuleSequence.setRuleResult::  "+key+"   "+result +" ((  "+otherRuleName);
		this.ruleResults.put(key, pair);	
	}
	
	public void setApplicabilityResult(final boolean result, final String criterion) {
		if (ApplicabilityConstants.UNDEFINED.equals(this.applResult.second)
				|| (this.applResult.first.booleanValue() && !result)) {
			
			this.applResult.first = new Boolean(result);
			this.applResult.second = criterion;
		}
	}

	/*
	private void setNonApplicabilityResultORIG(final boolean result, final String criterion) {
		if (ApplicabilityConstants.UNDEFINED.equals(this.nonapplResult.second)
				|| (this.nonapplResult.first.booleanValue() && !result)) {
			
			this.nonapplResult.first = new Boolean(result);
			this.nonapplResult.second = criterion;
		}
	}
	*/
	
	public void setNonApplicabilityResult(final boolean result, final String criterion) {
		if (ApplicabilityConstants.UNDEFINED.equals(this.nonapplResult.second)
				|| (!this.nonapplResult.first.booleanValue() && result)) {
			
			this.nonapplResult.first = new Boolean(result);
			this.nonapplResult.second = criterion;
		}
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.GraTraEventListener#graTraEventOccurred(agg.xt_basis.GraTraEvent)
	 */
	public void graTraEventOccurred(GraTraEvent e) {
		if ((e.getMessage() == GraTraEvent.STEP_COMPLETED)) {
			Match match = e.getMatch();
			OrdinaryMorphism comatch = match.getCoMorphism();
			this.matchSequence.addComatch(match.getRule(), comatch);
		}
	}	
	
	public boolean containsObjFlow(final Hashtable<String, ObjectFlow> objFlow) {
		List<Object> keys1 = new Vector<Object>(this.objectFlow.keySet());
		List<Object> keys2 = new Vector<Object>(objFlow.keySet());
		if ((keys1.isEmpty() && !keys2.isEmpty())
				|| (!keys1.isEmpty() && keys2.isEmpty())) {
			return false;
		}
		
		if (keys1.containsAll(keys2)) {
			for (int i=0; i<keys2.size(); i++) {
				final ObjectFlow flow2 = objFlow.get(keys2.get(i));
				final ObjectFlow flow1 = this.objectFlow.get(flow2.getKey());				
				if (flow1 == null 
						|| !flow1.compareTo(flow2)) {
					return false;
				}
			}
		} else {
			return false;
		}			
		return true;
	}
	
	public boolean extendsRuleList(final List<String> ruleList) {
		if (this.ruleNames.size() < ruleList.size())
			return false;
		
		for (int i=0; i<ruleList.size(); i++) {
			if (!ruleList.get(i).equals(this.ruleNames.get(i)))
				return false;
		}
	
		return true;
	}
	
	/**
	 * Returns true when these three conditions are satisfied :
	 * 1) the graph (name) of this sequence is equal to the graph (name) 
	 * of the specified sequence or both graphs are undefined,
	 * 2) the rule list of this sequence extends the rule list of the specified sequence,
	 * 3) this sequence contains the object flow of the specified sequence,
	 * otherwise - false.
	 * 
	 */
	public boolean extendsRuleSequence(final RuleSequence ruleSeq) {
		if (((this.graph != null && ruleSeq.getGraph() != null
							&& this.graph.getName().equals(ruleSeq.getGraph().getName()))
					|| (this.graph == null && ruleSeq.getGraph() == null))
				&& this.extendsRuleList(ruleSeq.getRuleNames())
				&& this.containsObjFlow(ruleSeq.getObjectFlow())) {	
			
			return true;
		} 
		return false;
	}
	
	/**
	 * When results of the specified rule sequence, which is already checked, are applied,
	 * returns the index of the next rule to check,
	 * otherwise - 0;
	 * 
	 * The specified rule sequence must be :
	 * - shorter then current sequence,
	 * - all its rules in given order are at beginning of the current sequence,
	 * - using the same host graph,
	 * - using similar object flow,
	 * - already checked.
	 * 
	 */
	public int tryToApplyResultsOfRuleSequence(final RuleSequence ruleSeq) {
		int res = 0;
		if (this.usePreviousSequenceResults
				&& ruleSeq != null
				&& ruleSeq.isChecked()
				&& !this.checked
				&& this.extendsRuleSequence(ruleSeq)
				&& this.getDepthOfConcurrentRule() == ruleSeq.getDepthOfConcurrentRule()
				&& this.getCompleteConcurrency() == ruleSeq.getCompleteConcurrency()
				&& this.getCompleteCPAOfConcurrency() == ruleSeq.getCompleteCPAOfConcurrency()) {
									
			final Hashtable<String, Pair<Boolean, List<String>>> table = ruleSeq.getRuleResults();
			Enumeration<String> keys = table.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				Pair<Boolean, List<String>> p = table.get(key);
				Pair<Boolean, List<String>> p1 = new Pair<Boolean, List<String>>(
												Boolean.valueOf(p.first.booleanValue()),
												new Vector<String>(p.second));
				this.getRuleResults().put(key, p1);
			}
			
			final Pair<Boolean, String> applRes = ruleSeq.getApplicabilityResult();
			this.applResult.first = applRes.first;
			this.applResult.second = applRes.second;
			
			final Pair<Boolean, String> nonapplRes = ruleSeq.getNonApplicabilityResult();
			this.nonapplResult.first = nonapplRes.first;
			this.nonapplResult.second = nonapplRes.second;
			
			tryToApplyDoneMatchesOfRuleSequence(ruleSeq);
			
			res = ruleSeq.getRules().size();
		}
		return res;
	}
	
	private void tryToApplyDoneMatchesOfRuleSequence(final RuleSequence ruleSeq) {
		final List<Rule> otherRules = ruleSeq.matchSequence.rules;
		for (int i=0; i<otherRules.size(); i++) {
			final Rule r = otherRules.get(i);
			final Rule preRule = (i==0)? null : otherRules.get(i-1);
			
			final Hashtable<GraphObject, GraphObject> 
			match = new Hashtable<GraphObject, GraphObject>(ruleSeq.matchSequence.matches.get(i));		
			this.matchSequence.matches.add(match);
			this.matchSequence.rules.add(r);
			
			if (preRule != null) {
				if (ruleSeq.matchSequence.imgObj2Rule.get(Integer.valueOf(i-1)) != null) {
					this.matchSequence.imgObj2Rule.put(Integer.valueOf(i-1), preRule);
				}
				else {
					if (ruleSeq.matchSequence.imgObj2ConcurrentRule.get(Integer.valueOf(i)) != null) {
						this.matchSequence.imgObj2ConcurrentRule.put(
								Integer.valueOf(i), 
								ruleSeq.matchSequence.imgObj2ConcurrentRule.get(Integer.valueOf(i)));
					}
				}
			}
		}
	}
	
	public Hashtable<GraphObject, GraphObject> getInput2outputMapIntoGraphAbovePreRule(
			final Rule targetRule, 
			int targetIndx,
			final List<ObjectFlow> targetObjFlowList,
			final Rule rule, 
			int ruleIndx,
			final Hashtable<GraphObject, GraphObject> input2postInput,
			final Graph g) {
		
		final Hashtable<GraphObject, GraphObject> map = new Hashtable<GraphObject, GraphObject>();
		final Rule preRule = this.getRule(ruleIndx-1);
		if (preRule != null) {
			final List<ObjectFlow> preObjFlowList = this.getObjFlowForRule(preRule, ruleIndx-1);
			if (preObjFlowList.isEmpty()) {
				return map;
			}
				
			boolean alreadyChecked = (rule == targetRule);
			
			List<ObjectFlow> objFlowList = this.getObjFlowForRule(rule, ruleIndx);
			
			if (objFlowList.isEmpty() && rule != targetRule) {
				objFlowList = targetObjFlowList;
				alreadyChecked = true;
			}
				
			boolean done = reflectObjFlow(preObjFlowList, objFlowList, map, input2postInput, g);
			
			if (!done && !alreadyChecked) {
				objFlowList = targetObjFlowList;
				reflectObjFlow(preObjFlowList, objFlowList, map, input2postInput, g);
			}
		}		
		return map;
	}
	
	private boolean reflectObjFlow(
			final List<ObjectFlow> preObjFlowList,
			final List<ObjectFlow> objFlowList,
			final Hashtable<GraphObject, GraphObject> map,
			final Hashtable<GraphObject, GraphObject> input2postInput,
			final Graph g) {
		
		boolean result = false;
		if (!objFlowList.isEmpty()) {
			if (!preObjFlowList.isEmpty()) {
				for (int i=0; i<objFlowList.size(); i++) {
					ObjectFlow objFlow = objFlowList.get(i);
					
					List<Object> inputObjs = objFlow.getInputs();
					for (int k=0; k<inputObjs.size(); k++) {
						GraphObject inObj = (GraphObject) inputObjs.get(k);	
						
						for (int j=0; j<preObjFlowList.size(); j++) {
							ObjectFlow preObjFlow = preObjFlowList.get(j);
							
							GraphObject connectedInObj = (GraphObject) objFlow.getConnectedInput(preObjFlow, inObj);
							GraphObject connectedOutObj = (GraphObject) objFlow.getConnectedOutput(preObjFlow, inObj);
							if (connectedOutObj != null) {
								if (g.isElement(connectedOutObj)) {				
									if (input2postInput.isEmpty()) {
										map.put(inObj, connectedOutObj);
										result = true;
									}
									else if (connectedInObj != null
												&& input2postInput.get(connectedInObj) != null) {
										map.put(input2postInput.get(connectedInObj), connectedOutObj);
										input2postInput.remove(connectedInObj);
										result = true;
									}
								} else if (connectedInObj != null) {
									input2postInput.put(connectedInObj, inObj);	
									result = true;
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	//TODO: to optimize
	public Hashtable<GraphObject, GraphObject> getReflectedObjectFlowOfGraphAndPreRule(
			final ConcurrentRule cr, 
			final List<ObjectFlow> targetObjFlowList, 
			final Rule rule,
			int ruleIndx,
			final Hashtable<GraphObject, GraphObject> input2postInput,
			final Graph g) {
		
		final Hashtable<GraphObject, GraphObject> 
		map = new Hashtable<GraphObject, GraphObject>();

		final Rule preRule = this.getRule(ruleIndx-1);
		if (preRule != null) {
			final List<ObjectFlow> preObjFlowList = this.getObjFlowForRule(preRule, ruleIndx-1);
			if (preObjFlowList.isEmpty()) {
				return map;
			}
				
			boolean alreadyChecked = false;
			
			List<ObjectFlow> objFlowList = this.getObjFlowForRule(rule, ruleIndx);
			
			if (objFlowList.isEmpty()) {
				objFlowList = targetObjFlowList;
				alreadyChecked = true;
			}
									
			boolean done = reflectObjFlow(cr, preRule, preObjFlowList, objFlowList,
								 map, input2postInput, g);
						
			if (!done && !alreadyChecked) {
				objFlowList = targetObjFlowList;
				reflectObjFlow(cr, preRule, preObjFlowList, objFlowList, 
								map, input2postInput, g);
			}					
		}		
		return map;
	}
	
	public void removeEmptyObjFlow() {
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			ObjectFlow objFlow = this.objectFlow.get(key);
			if (objFlow.isEmpty()) {
				this.objectFlow.remove(key);
				keys = this.objectFlow.keys();
			}
		}
	}
	
	private boolean reflectObjFlow(
			final ConcurrentRule cr,
			final Rule preRule,
			final List<ObjectFlow> preObjFlowList,
			final List<ObjectFlow> objFlowList,
			final Hashtable<GraphObject, GraphObject> map,
			final Hashtable<GraphObject, GraphObject> input2postInput,
			final Graph g) {
		
		boolean result = false;
		if (!objFlowList.isEmpty()
				&& !preObjFlowList.isEmpty()) {
			for (int i=0; i<objFlowList.size(); i++) {
				ObjectFlow objFlow = objFlowList.get(i);
				Hashtable<Object, Object> outInMap = objFlow.getMapping();
				Enumeration<Object> outs = outInMap.keys();
				while (outs.hasMoreElements()) {
					Object out = outs.nextElement();
					if (out instanceof GraphObject
							&& preRule.getRight().isElement((GraphObject) out)) {
						Enumeration<GraphObject> inverse = preRule.getInverseImage((GraphObject) out);
						while (inverse.hasMoreElements()) {
							GraphObject prein = inverse.nextElement();
								
							for (int j=0; j<preObjFlowList.size(); j++) {
								ObjectFlow preObjFlow = preObjFlowList.get(j);
								Hashtable<Object, Object> preOutInMap = preObjFlow.getMapping();
								Enumeration<Object> preOuts = preOutInMap.keys();
								while (preOuts.hasMoreElements()) {
									Object preOut = preOuts.nextElement();
									if (preOut instanceof GraphObject
											&& (prein == preObjFlow.getMapping().get(preOut))) {
										GraphObject inObj = (GraphObject)objFlow.getMapping().get(out);
										
										GraphObject crInObj = cr.getLeftEmbedding(inObj);
										if (crInObj != null) {
											if (g.isElement((GraphObject) preOut)) {
												if (input2postInput.isEmpty()) {															
													map.put(crInObj, (GraphObject)preOut);
													result = true;
												}
												else if (input2postInput.get(prein) != null) {
													map.put(input2postInput.get(prein), (GraphObject)preOut);
													input2postInput.remove(prein);
													result = true;
												}
											} else if (preRule.getRight().isElement((GraphObject) preOut)) {
													input2postInput.put(prein, crInObj);
													result = true;
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
		return result;
	}
	
	public void addObjFlow(final ObjectFlow objFlow) {
		if (objFlow != null) {
			int indx1 = objFlow.getIndexOfOutput();
			int indx2 = objFlow.getIndexOfInput();
			if (indx1 >= 0 && indx2 > indx1) {
				String keyStr = objFlow.getKey();
				this.objectFlow.put(keyStr, objFlow);
//				System.out.println("Rulesequence.addObjectFlow::  "+keyStr+"   "+objFlow);
			}
		}
	}
	
	public void removeObjFlow(final ObjectFlow objFlow) {
		if (objFlow != null) {
			int indx1 = objFlow.getIndexOfOutput();
			int indx2 = objFlow.getIndexOfInput();
			if (indx1 >= 0 && indx2 > indx1) {
				String keyStr = objFlow.getKey();
				this.objectFlow.remove(keyStr);
//				System.out.println("Rulesequence.removeObjectFlow::  "+keyStr+"   "+objFlow);
			}
		}
	}
	
	public void removeObjFlowOfGraph() {
		// remove graph outputs from object flow
		if (this.graph != null) {
			Enumeration<String> keys = this.objectFlow.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String[] keyItems  = key.split(":");			
				if (keyItems[0].equals("0")) {
					this.objectFlow.remove(key);
					keys = this.objectFlow.keys();
				}
			}
		}
	}
	
	public void removeObjFlow() {
		this.objectFlow.clear();
	}
	
	/**
	 * Returns an object flow which is defined for the given rule r1 as output and
	 * the rule r2 as input.
	 * 
	 * @param r1		output of an object flow
	 * @param indx_r1  	index in the rule sequence
	 * @param r2		input of an object flow
	 * @param indx_r2	index in the rule sequence
	 * @return
	 */
	public ObjectFlow getObjFlowForRules(final Rule r1, int indx_r1, final Rule r2, int indx_r2) {
		int i1 = indx_r1;
		int i2 = indx_r2;
		if (this.graph != null) {
			i1++;
			i2++;
		}
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			
			ObjectFlow objFlow = this.objectFlow.get(key);
			if (objFlow.getSourceOfOutput() == r1
					&& objFlow.getIndexOfOutput() == i1
					&& objFlow.getSourceOfInput() == r2
					&& objFlow.indxOfInput == i2) {
				return objFlow;
			}
		}
		return null;
	}
	
	public ObjectFlow getObjFlowForGraphAndRule(final Rule r, int indx_r) {
		int i = indx_r;
		if (this.graph == null) 
			return null;
		
		i++;
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
//			System.out.println(key);
			ObjectFlow objFlow = this.objectFlow.get(key);
			if (objFlow.getSourceOfOutput() == this.graph
					&& objFlow.getSourceOfInput() == r
					&& objFlow.indxOfInput == i) {
				return objFlow;
			}
		}
		return null;
	}
	
	/**
	 * Returns a list with all defined object flows of the given rule.
	 * 
	 * @param r	 rule
	 * @param indx   rule index in the rule sequence
	 * @return
	 */
	public List<ObjectFlow> getObjFlowForRule(final Rule r, int indx) {		
		final List<ObjectFlow> list = new Vector<ObjectFlow>();
		if (indx < 0) {
			return list;
		}
		
		int i = indx;
		if (this.graph != null)
			i++;

		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[1]).intValue() == i) {
				list.add(this.objectFlow.get(key));
			}
		}
		return list;
	}
	
	public List<ObjectFlow> getObjFlowFromGraph() {		
		final List<ObjectFlow> list = new Vector<ObjectFlow>();
		
		if (this.graph != null) {	
			Enumeration<String> keys = this.objectFlow.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String[] keyItems  = key.split(":");
				if (Integer.valueOf(keyItems[0]).intValue() == 0) {
					list.add(this.objectFlow.get(key));
				}
			}
		}
		return list;
	}
	
	public List<ObjectFlow> getObjFlowFromRule(final Rule r, int indx) {		
		final List<ObjectFlow> list = new Vector<ObjectFlow>();
		if (indx < 0) {
			return list;
		}
		
		int i = indx;
		if (this.graph != null)
			i++;

		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[0]).intValue() == i) {
				list.add(this.objectFlow.get(key));
			}
		}
		return list;
	}
	
	public int getSizeOfObjFlowForRule(final Rule r, int indx) {
		if (indx < 0) {
			return 0;
		}
		
		int i = indx;
		if (this.graph != null)
			i++;
		
		int size = 0;		
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[1]).intValue() == i) {
				size = size + this.objectFlow.get(key).getSizeOfInput();
			}
		}
		return size;
	}
	
	
	/*
	 * When an object flow defined for this rule sequence
	 * returns all rules of an input object which is connected to 
	 * an output object of the given Object which can be Rule or Graph,
	 * otherwise - empty list.
	 * 
	 * @param output  is Rule or Graph
	 * @param itsIndx
	 * @return
	 */
	/*
	private List<Rule> getConnectedInputOfOutput(final Object outputSource, int itsIndx) {
		final List<Rule> list = new Vector<Rule>();
		if (itsIndx < 0) {
			return list;
		}
		
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[0]).intValue() == itsIndx
					&& this.objectFlow.get(key).getSourceOfOutput() == outputSource) {
				int rindx = Integer.valueOf(keyItems[1]).intValue();
				if (this.graph != null)
					rindx--;
				final Rule inRule = this.getRule(rindx);
				if (inRule != null)
					list.add(inRule);
			}
		}
		return list;
	}
	*/
	
	private List<Pair<Rule, Integer>> getNextRuleOfObjFlow(final Object outputSource, int itsIndx) {
		final List<Pair<Rule, Integer>> list = new Vector<Pair<Rule, Integer>>();
		if (itsIndx < 0) {
			return list;
		}
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[0]).intValue() == itsIndx
					&& this.objectFlow.get(key).getSourceOfOutput() == outputSource) {
				final Rule r2 = (Rule) this.objectFlow.get(key).getSourceOfInput();				
				list.add(new Pair<Rule, Integer>(r2, Integer.valueOf(keyItems[1])));
			}
		} 
		return list;
	}
	
	private GraphObject getConnectedInputForOutput(
			final Object ouputSource, 
			int itsIndx,
			final GraphObject out, 
			final Rule r2) {
		
		if (itsIndx < 0) {
			return null;
		}	
		
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[0]).intValue() == itsIndx
					&& this.objectFlow.get(key).getSourceOfOutput() == ouputSource
					&& this.objectFlow.get(key).getSourceOfInput() == r2) {						
				return (GraphObject) this.objectFlow.get(key).getMapping().get(out);
			}
		}
		return null;
	}
	
	private List<GraphObject> getOutputObjsOfOutput(
			final Object output, 
			int itsIndx) {
		
		final List<GraphObject> list = new Vector<GraphObject>();
		if (itsIndx < 0) {
			return list;
		}
		
		final Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String[] keyItems  = key.split(":");
			if (Integer.valueOf(keyItems[0]).intValue() == itsIndx
					&& this.objectFlow.get(key).getSourceOfOutput() == output) {

				final ObjectFlow objFlow = this.objectFlow.get(key);
				if (objFlow.getSourceOfOutput() == output) {
					final Enumeration<Object> outputs = objFlow.getMapping().keys();
					while (outputs.hasMoreElements()) {
						final Object obj = outputs.nextElement();
						if (!list.contains(obj))
							list.add((GraphObject) obj);
					}
				}
			}
		}	
		return list;
	}
	
	
	private void getClosuresOfObjFlow(
			final Object outputSource, 
			int itsIndx,
			final GraphObject outObj,
			final List<Triple<GraphObject, Rule, Integer>> closures) {
		
		if (itsIndx < 0) {
			return;
		}
		
		final List<Pair<Rule, Integer>> nextRule = this.getNextRuleOfObjFlow(outputSource, itsIndx);	
		
		for (int r=0; r<nextRule.size(); r++) {
			final Pair<Rule, Integer> pair = nextRule.get(r);
			final Rule rule = pair.first;
			
			final GraphObject inputObj = getConnectedInputForOutput(
											outputSource, itsIndx, outObj, rule);			
			if (inputObj != null) {
				closures.add(new Triple<GraphObject, Rule, Integer>(inputObj, rule, pair.second));
				
				final GraphObject rhsObj = rule.getImage(inputObj);
				if (rhsObj != null) {					
					getClosuresOfObjFlow(rule, pair.second.intValue(), rhsObj, closures);
				} 
			}
		}
	}
	
	public int getMessageOfInvalidObjectFlow() {
		return this.objectFlowError;
	}
	
	/**
	 * Returns true when the transitive closure of the object flow 
	 * for this rule sequence exists and the object flow is persistent, 
	 * otherwise false.
	 * 
	 * @see getMessageOfInvalidObjectFlow() for error kind which can be:
	 * <code> RuleSequence.OBJECT_FLOW_TRANSITIVE_CLOSURE_FAILED</code> or
	 * <code> RuleSequence.OBJECT_FLOW_PERSISTENT_FAILED</code>.
	 */
	public boolean isObjFlowValid() {
		this.objectFlowError = -1;
		if (this.objectFlow != null && !this.objectFlow.isEmpty()) {
			final List<Object> tmp = new Vector<Object> (this.rules);
			if (this.graph != null) 
				tmp.add(0, this.graph);
			
			boolean closureOK = true;
			for (int i=0; i<tmp.size() && closureOK; i++) {
				final Object outputSource = tmp.get(i);
				
				final List<GraphObject> rOutputs = getOutputObjsOfOutput(outputSource, i);
				if (rOutputs.isEmpty())
					continue;

				for (int j=0; j<rOutputs.size() && closureOK; j++) {
					final GraphObject outObj = rOutputs.get(j);
//					System.out.println("\n  out object: "+outObj);
							
					final List<Triple<GraphObject, Rule, Integer>> 
					closures = new Vector<Triple<GraphObject, Rule, Integer>>();
							
					// collect recursively closure input objects of an output object
					// outputSource - Graph or Rule
					// i - index of this rule sequence
					// outObj - GraphObject as output
					// closures - container to fill
					getClosuresOfObjFlow(outputSource, i, outObj, closures);

					if (!this.isObjFlowTransitive(closures)) {
						this.objectFlowError = RuleSequence.OBJECT_FLOW_TRANSITIVE_CLOSURE_FAILED;
						closureOK = false;
					}
					else if (!this.isObjFlowPersistent(closures)) {
						this.objectFlowError = RuleSequence.OBJECT_FLOW_PERSISTENT_FAILED;
						closureOK = false;
					}
				}
			}
			return closureOK;
		} 
		
		return true;
	}
	
	/**
	 * Checks whether the transitive closure of the object flow 
	 * for this rule sequence exists.
	 */
	private boolean isObjFlowTransitive(
			final List<Triple<GraphObject, Rule, Integer>> closures) {
		// no different input objects for an output inside the same graph
		for (int k=0; k<closures.size()-1; k++) {
			Triple<GraphObject, Rule, Integer> triple = closures.get(k);
			GraphObject go1 = triple.first;
			for (int l=1; l<closures.size(); l++) { // l=k+1???
				Triple<GraphObject, Rule, Integer> triple2 = closures.get(l);
				GraphObject go2 = triple2.first;
				if (go1.getContext() == go2.getContext()
						&& go1 != go2) {
					return false;
				}
			}
		}		
		return true;
	}
	
	public void tryCompleteObjFlowTransClosure() {
		tryCompleteArcsOF(); 
		
		final Vector<Object> tmp = new Vector<Object> (this.rules);
		boolean withGraph = false;
		if (this.graph != null) {
			if (tmp.get(0) != this.graph)  
				tmp.add(0, this.graph);
			withGraph = true;
		}		
		
		completeNodeTC(tmp, withGraph);
		completeNodeTC1(tmp, withGraph);
	}
	
	private void completeNodeTC(final Vector<Object> tmp, boolean withGraph) {					
		for (int i=0; i<tmp.size()-1; i++) {
			final Object outSrc = tmp.get(i);
			if (outSrc instanceof Rule) {
				for (int j=i+1; j<tmp.size()-1; j++) {
					final Object inSrc1 = tmp.get(j);
					final Object inSrc2 = tmp.get(j+1);
					if (inSrc1 instanceof Rule && inSrc2 instanceof Rule) {
						Iterator<Node> elems = ((Rule)outSrc).getRight().getNodesSet().iterator();
						while (elems.hasNext()) {
							GraphObject go_out = elems.next();
							this.tryCompleteNodeTC(go_out, (Rule)outSrc, i, (Rule)inSrc1, j, (Rule)inSrc2, j+1, withGraph);
						}
					}
				}
			}
		}
	}
	
	private void completeNodeTC1(
			final Vector<Object> tmp, boolean withGraph) {	
		int c= withGraph? -1: 0;
		for (int i=0; i<tmp.size()-1; i++) {
			final Object outSrc = tmp.get(i);
			int j = i+1;
			final Object inSrc = tmp.get(j);

			ObjectFlow of = null;
			if (outSrc instanceof Rule)
				of = this.getObjFlowForRules((Rule)outSrc, i+c, (Rule)inSrc, j+c);
			else if (outSrc instanceof Graph)
				of = this.getObjFlowForGraphAndRule((Rule)inSrc, j+c);
			
			if (of != null && i >= 1
					&& tmp.get(i-1) instanceof Rule) {								
				Enumeration<GraphObject> elems = ((Rule)inSrc).getLeft().getElements();
				while (elems.hasMoreElements()) {
					GraphObject go_in = elems.nextElement();
					if (of.isInputObject(go_in)) {
						if (i-1 >= 0) {
							GraphObject go_out = (GraphObject)of.getOutput(go_in);								
							Triple<GraphObject, Rule, Integer> result = tryCompleteTransClosure(
										go_out, (Rule) tmp.get(i-1), i-1, (Rule) tmp.get(j-1), j-1, withGraph);						
							int l = i-1;
							int k = j-1;
							while (result != null) {
								ObjectFlow of1 = this.getObjFlowForRules(
													result.second, result.third.intValue()+c, 
													(Rule)inSrc, j+c);
								if (of1 == null) {
									of1 = new ObjectFlow(result.second, inSrc, 
															result.third.intValue(), j);
									of1.addMapping(result.first, go_in);
									this.addObjFlow(of1);
								} 
								else if (!of1.isInputObject(go_in)) {
									of1.addMapping(result.first, go_in);
								}								
								l--; k--;
								if (l >= 0 && k >= 0
										&& tmp.get(l) instanceof Rule
										&& tmp.get(k) instanceof Rule) {
									result = tryCompleteTransClosure(result.first, (Rule)tmp.get(l), l, (Rule)tmp.get(k), k, withGraph);
								} 
								else 
									break;
							}
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void tryCompleteNodeOF(
			final GraphObject go_in, 
			final Object inSrc, 
			int inIndx,
			final List<Object> tmp) {	

		ObjectFlow of = null;
		int c=0;
		if (this.graph != null && tmp.get(0) == this.graph)  
			c--;
		
		for (int i=tmp.size()-1; i>=0; i--) {
			final Object outSrc = tmp.get(i);

			if (outSrc instanceof Rule) 
				of = this.getObjFlowForRules((Rule)outSrc, i+c, (Rule)inSrc, inIndx+c);
			else if (outSrc instanceof Graph)
				of = this.getObjFlowForGraphAndRule((Rule)inSrc, inIndx+c);
			
			GraphObject go_out = null;
			if (outSrc instanceof Rule) {
				List<GraphObject> objs = ((Rule)outSrc).getElementsToCreate();
				for (int l=0; l<objs.size(); l++) {
					go_out = objs.get(l);
					if (go_out.compareTo(go_in)) { // TODO: check if can map? 
						break;
					} 
					else
						go_out = null;
				}
				if (go_out == null) {
					objs = ((Rule)outSrc).getCodomainObjects();
					for (int l=0; l<objs.size(); l++) {
						go_out = objs.get(l);
						if (go_out.compareTo(go_in)) { // TODO: check if can map? 
							break;
						}
						else
							go_out = null;
					}
				}			
				if (go_out != null) {
					if (of == null) {
						of = new ObjectFlow((Rule)outSrc, inSrc, i, inIndx);
						this.addObjFlow(of);
					}
					this.addObjFlow(of);
					of.addMapping(go_out, go_in);
					break;
				}
			}
		}
	}
	
	private Triple<GraphObject, Rule, Integer> tryCompleteTransClosure(
			final GraphObject go_out,
			Rule r_out,
			int j,
			Rule r_in,
			int i,
			boolean withGraph) {
		
		int c = withGraph? -1: 0;		
		Enumeration<GraphObject> en = r_in.getInverseImage(go_out);
		if (en.hasMoreElements()) {
			GraphObject go = en.nextElement();
			ObjectFlow of = this.getObjFlowForRules(r_out, j+c, r_in, i+c);
			if (of != null && of.isInputObject(go)) {
				GraphObject go_out_j = (GraphObject)of.getOutput(go);
				return new Triple<GraphObject, Rule, Integer>(go_out_j, r_out, Integer.valueOf(j));
			}
		}
		return null;
	}
	
	private void tryCompleteNodeTC(
			final GraphObject go_out, final Rule r_out, int j,
			final Rule r_in1, int i1,
			final Rule r_in2, int i2,
			boolean withGraph) {
			
		int c = withGraph? -1: 0;
		if (i2 > i1) {
			ObjectFlow of1 = this.getObjFlowForRules(r_out, j+c, r_in1, i1+c);
			if (of1 != null) {
				ObjectFlow of2 = this.getObjFlowForRules(r_out, j+c, r_in2, i2+c);
				Object go_in1 = of1.getInput(go_out);	
				if (go_in1 != null) {
					GraphObject go_out3 = r_in1.getImage((GraphObject) go_in1);
					if (go_out3 != null) {
						if (of2 != null) {		
							ObjectFlow of3 = this.getObjFlowForRules(r_in1, i1+c, r_in2, i2+c);
							Object go_in2 = of2.getInput(go_out);  
							if (go_in2 != null) {
								if (of3 == null) {
									of3 = new ObjectFlow(r_in1, r_in2, i1, i2);
									this.addObjFlow(of3);
								}
								if (of3.getInput(go_out3) == null) {
									of3.addMapping(go_out3, (GraphObject)go_in2);	
								}
							}
							else {
								if (of3 != null) {
									Object go_in3 = of3.getInput(go_out3);
									if (go_in3 != null) {
										of2.addMapping(go_out, (GraphObject)go_in3);
									}
								}
							}
						}
						else {
							ObjectFlow of3 = this.getObjFlowForRules(r_in1, i1+c, r_in2, i2+c);
							if (of3 != null) {
								Object go_in3 = of3.getInput(go_out3);
								if (go_in3 != null) {
									of2 = new ObjectFlow(r_out, r_in2, j, i2);
									this.addObjFlow(of2);
									of2.addMapping(go_out, (GraphObject)go_in3);
								}
							}
						}
					}
				}
			}
			else {
				ObjectFlow of2 = this.getObjFlowForRules(r_out, j+c, r_in2, i2+c);
				if (of2 != null) {
					Object go_in2 = of2.getInput(go_out);	
					if (go_in2 != null) {
						ObjectFlow of3 = this.getObjFlowForRules(r_in1, i1+c, r_in2, i2+c);
						if (of3 != null) {
							Object go_out3 = of3.getOutput(go_in2);
							if (go_out3 != null) {
								Enumeration<GraphObject> en = r_in1.getInverseImage((GraphObject)go_out3);
								if (en.hasMoreElements()) {
									of1 = new ObjectFlow(r_out, r_in1, j, i1);
									this.addObjFlow(of1);
									of1.addMapping(go_out, en.nextElement());
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void tryCompleteArcsOF() {
		Enumeration<String> keys = this.objectFlow.keys();
		while (keys.hasMoreElements()) {
			ObjectFlow of = this.objectFlow.get(keys.nextElement());
			int indxR1 = of.getIndexOfOutput();
			if (this.graph != null) 
				indxR1--;
			Rule r1 = this.getRule(indxR1);
			if ( r1 != null) {
				Object outSrc = of.getSourceOfOutput();
				Object inSrc = of.getSourceOfInput();
				Graph inG = ((Rule)inSrc).getLeft();
				Graph outG = ((Rule)outSrc).getRight();
				Iterator<Arc> arcs = outG.getArcsCollection().iterator();
				while (arcs.hasNext()) {
					Arc a = arcs.next();
					if (this.completeNodesOF) {
						// source of arc is connected by OF
						if (of.isOutputObject(a.getSource()) && !of.isOutputObject(a.getTarget())) {
							Node inNode = (Node) of.getInput(a.getSource());
							Iterator<Arc> outgoings = inNode.getOutgoingArcs();
							while (outgoings.hasNext()) {
								Arc outgoing = outgoings.next();
								if (outgoing.getTarget().getType().isParentOf(a.getTarget().getType())) {
									Node goL1 = null;
									// get left go of r1
									Enumeration<GraphObject> gosL1 = r1.getInverseImage(a.getTarget());
									if (gosL1.hasMoreElements()) {
										goL1 = (Node) gosL1.nextElement();
									}
									if (goL1 == null) {
										if (!outgoing.getTarget().isAttrMemConstantValDifferent(a.getTarget())) {
											// connect target
											of.addMapping(a.getTarget(), outgoing.getTarget());
		//									System.out.println(of.getKey()+"   new (target) node mapping of ObjectFlow added");
											break;
										}
									}
									else if (!outgoing.getTarget().isAttrMemConstantValDifferent(a.getTarget(), goL1)) {
											// connect target
											of.addMapping(a.getTarget(), outgoing.getTarget());
		//									System.out.println(of.getKey()+"   new (target) node mapping of ObjectFlow added");
											break;
									}
								}
							}
						}
						// target of arc is connected by OF
						else if (!of.isOutputObject(a.getSource()) && of.isOutputObject(a.getTarget())) {
							Node inNode = (Node) of.getInput(a.getTarget());
							Iterator<Arc> incomings = inNode.getIncomingArcs();
							while (incomings.hasNext()) {
								Arc incoming = incomings.next();
								if (incoming.getSource().getType().isParentOf(a.getSource().getType())) {
									Node goL1 = null;
									// get left go of r1
									// get left go of r1
									Enumeration<GraphObject> gosL1 = r1.getInverseImage(a.getSource());
									if (gosL1.hasMoreElements()) {
										goL1 = (Node) gosL1.nextElement();
									}
									if (goL1 == null) {
										if (!incoming.getSource().isAttrMemConstantValDifferent(a.getSource())) {
											// connect source
											of.addMapping(a.getSource(), incoming.getSource());
		//									System.out.println(of.getKey()+"   new (source) node mapping of ObjectFlow added");
											break;
										}
									}
									else if (!incoming.getSource().isAttrMemConstantValDifferent(a.getSource(), goL1)) {
										// connect target
										of.addMapping(a.getSource(), incoming.getSource());
		//								System.out.println(of.getKey()+"   new (source) node mapping of ObjectFlow added");
										break;
									}
								}
							}
						}
					}
					// source and target of arc are connected by OF
					if (of.isOutputObject(a.getSource()) && of.isOutputObject(a.getTarget())
							&& !of.isOutputObject(a)) {
						Node src = (Node)of.getInput(a.getSource());
						Node tar = (Node)of.getInput(a.getTarget());
						List<Arc> list = inG.getArcs(a.getType(), src, tar);
						if (!list.isEmpty()) {
							// connect arc
							for (int i=0; i<list.size(); i++) {
								Arc a1 = list.get(0);
								if (a1.getType().isParentOf(a.getType())) {
									Arc goL1 = null;
									// get left go of r1
									Enumeration<GraphObject> gosL1 = r1.getInverseImage(a);
									if (gosL1.hasMoreElements()) {
										goL1 = (Arc) gosL1.nextElement();
									}
									if (goL1 == null) {
										if (!a1.isAttrMemConstantValDifferent(a)) {
											of.addMapping(a,a1);
	//										System.out.println(of.getKey()+"   new arc mapping of ObjectFlow added");
											break;
										}
									}
									else if (!a1.isAttrMemConstantValDifferent(a, goL1)) {
										// connect target
										of.addMapping(a, a1);
	//									System.out.println(of.getKey()+"   new arc mapping of ObjectFlow added");
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
					
	private boolean isObjFlowPersistent(
			final List<Triple<GraphObject, Rule, Integer>> closures) {
		
		for (int k=0; k<closures.size()-1; k++) {
			Triple<GraphObject, Rule, Integer> triple = closures.get(k);
			GraphObject go1 = triple.first;
			Rule r1 = triple.second;
			int indx1 = triple.third.intValue();
//			System.out.println("1) "+r1.getName()+"   "+indx1);
			for (int l=1; l<closures.size(); l++) {
				Triple<GraphObject, Rule, Integer> triple2 = closures.get(l);
				GraphObject go2 = triple2.first;
				Rule r2 = triple2.second;
				int indx2 = triple2.third.intValue();
//				System.out.println("2) "+r2.getName()+"   "+indx2);
				if (go1 != go2) {
					if (indx1 < indx2
							&& (r1.getImage(go1) == null)) {
//						System.out.println("persistent object of the object flow failed :  "
//									+go1+" , "+go2);
						return false;
					}
					else if (indx2 < indx1
							&& (r2.getImage(go2) == null)) {
//						System.out.println("persistent object of the object flow failed :  "
//									+go1+" , "+go2);
						return false;
					}
				}
			}
		}		
		return true;
	}
	
	public static void printObjFlow(final RuleSequence currentSequence) {
		System.out.println();
        final Hashtable<String, ObjectFlow> objectFlows=currentSequence.getObjectFlow();
        for(int i=0;i<=currentSequence.getRules().size()-1;i++){
            for(int j=i+1;j<=currentSequence.getRules().size();j++){
                ObjectFlow of=objectFlows.get(i+":"+j);
                if(of!=null){
                    System.out.println("Object flow:  "+i+":"+j
                                		+"  from  "+of.getNameOfOutput()+"  to  " +of.getNameOfInput());
                    for(Object oneKey:of.getMapping().keySet()){
                         GraphObject value=(GraphObject) of.getMapping().get(oneKey);
                         GraphObject key=(GraphObject) oneKey;
                         System.out.println("Map:  from  "+key.getObjectName()
                                        				+":"+key.getType().getName()
                                        				+ "  to  "+value.getObjectName()+":"+value.getType().getName());
                    }
                }
            }
        }
	}

	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.rules.trimToSize();
		this.ruleNames.trimToSize();
		((Vector<Pair<List<Pair<String, String>>, String>>)this.subSequenceList).trimToSize();
	}
}
