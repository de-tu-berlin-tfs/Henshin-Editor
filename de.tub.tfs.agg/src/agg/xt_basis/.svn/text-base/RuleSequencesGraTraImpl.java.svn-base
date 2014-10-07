package agg.xt_basis;


import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import agg.attribute.AttrException;
import agg.attribute.impl.VarTuple;
import agg.cons.AtomConstraint;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;
import agg.xt_basis.agt.RuleScheme;



public class RuleSequencesGraTraImpl extends GraTra {

	protected boolean appliedOnce, eachRuleToApply;
	
	protected boolean trafoSequenceBroken;
	
	protected boolean allRulesEnabled = false;

	protected int indx=-1;
	
	protected RuleSequence ruleSequence;
	
	protected List<Pair<List<Pair<Rule, String>>, String>> ruleSubsequences;

	protected List<String> ruleNameSequences;
	
	protected File f;

	protected FileOutputStream os;

	protected String protocolFileName = "";

	protected boolean grammarChecked;
	
	protected long time;
	
	public RuleSequencesGraTraImpl() {}

	public void dispose() {
		this.clearRuleSequence();	
		super.dispose();
	}
	
	public boolean setGraGra(GraGra gg) {
		boolean res = super.setGraGra(gg);
		if (this.grammar.getRuleSequenceList() != null) {
			setRuleSequence(this.grammar.getRuleSequenceList());
		}
		return res;
	}

	public void setEachRuleToApply(boolean b) {
		this.eachRuleToApply = b;
	}
	
	public boolean isEachRuleToApply() {
		return this.eachRuleToApply;
	}
	
	public boolean apply() {
		if (this.ruleSequence != null) {
			this.addGraTraListener(this.ruleSequence);
		}
		
		Pair<List<Pair<Rule, String>>, String> p;
		for (int i = 0; i < this.ruleSubsequences.size() && !this.stopping; i++) {
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

	public void setRuleSequence(final RuleSequence ruleSeq) {
		this.ruleSequence = ruleSeq;
		this.setRuleSequence(ruleSeq.getSubSequenceList());
	}
	
	protected void setRuleSequence(
			final List<Pair<List<Pair<String, String>>, String>> sequence) {
		
		this.ruleSubsequences = new Vector<Pair<List<Pair<Rule, String>>, String>>(
				sequence.size());
		for (int i = 0; i < sequence.size(); i++) {
			Pair<List<Pair<String, String>>, String> pi = sequence.get(i);
			List<Pair<String, String>> v = pi.first;
			List<Pair<Rule, String>> vec = new Vector<Pair<Rule, String>>(v
					.size());
			for (int j = 0; j < v.size(); j++) {
				Pair<String, String> pj = v.get(j);
				Rule r = this.grammar.getRuleByQualifiedName(pj.first);
				Pair<Rule, String> p = new Pair<Rule, String>(r, pj.second);
				vec.add(p);
			}
			this.ruleSubsequences.add(
					new Pair<List<Pair<Rule, String>>, String>(vec, pi.second));
		}
	}

	protected void clearRuleSequence() {
		for (int i = 0; i < this.ruleSubsequences.size(); i++) {
			Pair<List<Pair<Rule, String>>, String> pi = this.ruleSubsequences.get(i);
			List<Pair<Rule, String>> v = pi.first;			
			for (int j = 0; j < v.size(); j++) {
				v.get(j).first = null;
			}
			v.clear();
		}
		this.ruleSubsequences.clear();
	}
	
	protected void apply(Rule r, String iters) {
		boolean ruleapplied = true;
		if (iters.equals("*")) {
//			System.out.println("\nrule: " + r.getName()+ "\t*  times");
			int i=0;
			while (ruleapplied && !this.stopping) {

				if (this.options.hasOption(GraTraOptions.WAIT_AFTER_STEP)) {
					fireGraTra(new GraTraEvent(this, GraTraEvent.RULE, r));
				}

				if (this.ruleSequence.isTrafoByObjFlow()
							&& !this.ruleSequence.getObjectFlow().isEmpty()) {
					if (this.ruleSequence.getRule(this.indx) != r
							|| this.ruleSequence.getRule(this.indx-1) != r) {
						this.indx++;
						this.ruleSequence.getMatchSequence().setTrafoIndex(this.indx);	
					}
					propagateObjFlowOfRule(this.indx, r);
				}
				
				if (this.currentRule.getRuleScheme() != null) {
					ruleapplied = apply((RuleScheme) this.currentRule);
	    		} 
				else {
					ruleapplied = apply(r);
				}
				
				if (ruleapplied) {
					i++;
					this.appliedOnce = true;
				}

				System.out.println(r.getName() + " \t applied:  " + ruleapplied);
				
				if (this.os != null)					
					writeTransformProtocol(r.getName() + " \t applied:  "
							+ ruleapplied);

				if ((i==0 && !ruleapplied && this.eachRuleToApply)
						|| !isGraphConsistent()) {
					this.stopping = true;
					this.trafoSequenceBroken = true;
				}
			}
		} else {
			long N = (new Long(iters)).longValue();			
			for (long i = 0; i < N && !this.stopping; i++) {
							
				if (this.options.hasOption(GraTraOptions.WAIT_AFTER_STEP)) {
					fireGraTra(new GraTraEvent(this, GraTraEvent.RULE, r));
				}
				
				if (this.ruleSequence.isTrafoByObjFlow()
							&& !this.ruleSequence.getObjectFlow().isEmpty()) {	
					this.indx++;
//					if (this.ruleSequence.getRule(this.indx) != r
//							|| this.ruleSequence.getRule(this.indx-1) != r) {
////						this.indx++;
//						this.ruleSequence.getMatchSequence().setTrafoIndex(this.indx);
//					}
					this.ruleSequence.getMatchSequence().setTrafoIndex(this.indx);
					propagateObjFlowOfRule(this.indx, r);
				}
			
				if (!this.stopping) {
				
					if (this.currentRule.getRuleScheme() != null) {
						ruleapplied = apply((RuleScheme) this.currentRule);
		    		} 
					else {
						ruleapplied = apply(r);
					}
										
					if (ruleapplied) {
						this.appliedOnce = true;
					} 
	
					System.out.println(r.getName() + " \t applied:  " + ruleapplied);
					
					if (this.os != null)
						writeTransformProtocol(r.getName() + " \t applied:  "
								+ ruleapplied);
	
					if ((i==0 && !ruleapplied && this.eachRuleToApply)
							|| !isGraphConsistent()) {
						this.stopping = true;
						this.trafoSequenceBroken = true;
					}
				}
			}
		}
	}

	private void propagateObjFlowOfRule(
			int ind, 
			final Rule r) {

		Hashtable<GraphObject, GraphObject> 
		matchMap = (r.getRuleScheme() == null)? 
					this.ruleSequence.getMatchSequence().getMatch(ind, r):
					this.ruleSequence.getMatchSequence().getMatch(ind, r.getRuleScheme().getKernelRule());			
		if (r.getMatch() == null) {
			this.currentMatch = (r.getRuleScheme() == null)?
								this.grammar.createMatch(r):
								r.getRuleScheme().getKernelMatch(this.grammar.getGraph());								
			this.currentMatch.setCompletionStrategy(
						(MorphCompletionStrategy) this.strategy.clone(), true);
		} else {
			this.currentMatch = (r.getRuleScheme() == null)?
								r.getMatch():
								r.getRuleScheme().getKernelMatch(this.grammar.getGraph());
		}			
		if (matchMap != null && !matchMap.isEmpty()) {						
			try {
				this.currentMatch.addMapping(matchMap);
				this.currentMatch.setPartialMorphismCompletion(true);						
				this.currentMatch.adaptAttrContextValues(this.currentMatch.getRule().getAttrContext());
				this.currentMatch.adaptAttrContextValuesFromExistingObjMapping();
			} catch (BadMappingException ex) {
				fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
							this.currentMatch, ex.getMessage()));
				this.currentMatch.clear();
				this.stopping = true;	
				this.trafoSequenceBroken = true;
			}
		} 
	}
	
	
	protected boolean apply(final List<Pair<Rule, String>> group, final String iters) {
		if (iters.equals("*")) {
//			System.out.println("\n	apply  *  times");
			this.appliedOnce = true;			
			while (this.appliedOnce && !this.stopping) {
				this.appliedOnce = false;
				long time0 = System.currentTimeMillis();
				for (int j = 0; j < group.size() && !this.stopping; j++) {
					Pair<Rule, String> p = group.get(j);
					if (p.first == null)
						continue;
					
					this.currentRule = p.first;
					if (this.currentRule.isEnabled()) {
						apply(this.currentRule, p.second);
					}

				} 
				this.time = this.time + (System.currentTimeMillis()-time0);
				System.out.println("used time: "+time+"ms");
				if (this.os != null)
					writeUsedTimeToProtocol("used time: ", time);
			}
		} else {
			
			long N = (new Long(iters)).longValue();
//			if (N > 1)
//				System.out.println("\n	apply  " + N + "  time(s)");
			for (long i = 0; i < N && !this.stopping; i++) {
				
				long time0 = System.currentTimeMillis();
				
				for (int j = 0; j < group.size() && !this.stopping; j++) {
					Pair<Rule, String> p = group.get(j);
					if (p.first == null)
						continue;
					
					this.currentRule = p.first;
					if (this.currentRule.isEnabled()) {
						apply(this.currentRule, p.second);
					}
				}
				this.time = this.time + (System.currentTimeMillis()-time0);
				System.out.println("used time: "+time+"ms"); //+"       ###  "+this.hostgraph.getNodesCount());
				if (this.os != null) 
					writeUsedTimeToProtocol("used time: ", time);
			}
		}
		
		if (this.options.hasOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO)) {
			this.checkGraphConsistency();
		}
		
		return this.appliedOnce;
	}

	public boolean apply(Rule r) {
//		System.out.println("RuleSequenceGraTra.apply(Rule) : "+r.getName()+"   "+updateTypeObjectsMapAfterStep);
	
		this.stoppingRule = false;		
		boolean result = false;
		boolean valid = false;

		this.currentMatch = r.getMatch();
		if (this.currentMatch == null) {
			this.currentMatch = this.grammar.createMatch(r);
			this.currentMatch.setCompletionStrategy(
						(MorphCompletionStrategy) this.strategy.clone(), true);
//			this.strategy.showProperties();
		} else if (this.updateTypeObjectsMapAfterStep) {
			this.currentMatch.setTypeObjectsMapChanged(true);
		}
		
		boolean parallelApply = true;
		boolean is_applied = false;
//		int matchCompletions = 0;
		
//		long time0 = System.currentTimeMillis();
		
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
				if (this.ruleSequence.isTrafoByObjFlow()) {
					if (this.currentMatch.isTotal()
							&& this.currentMatch.isAttrConditionSatisfied()
							&& this.currentMatch.arePACsSatisfied()
							&& this.currentMatch.areNACsSatisfied()
							&& this.currentMatch.getRule().evalFormula()
							&& this.currentMatch.isValid()) {
						valid = true;
//						matchCompletions++;
						break;
					}
				}
				
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
						break;
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
								
				try { // check attr context: variables only
					boolean checkVarsOnly = true;
					this.currentMatch.getAttrContext().getVariables()
							.getAttrManager().checkIfReadyToTransform(
									this.currentMatch.getAttrContext(),
									checkVarsOnly);
				} catch (AttrException ex) {
					fireGraTra(new GraTraEvent(this,
							GraTraEvent.NOT_READY_TO_TRANSFORM, r.getName()));				
					return false;
				}
				
				Morphism coMatch = apply(this.currentMatch);				
				if (coMatch != null) {
					result = true;
					this.errorMsg = "";
					is_applied = true;					
					this.currentMatch.clear();
					coMatch.dispose(); coMatch = null;
				} else {
					result = false;	
					valid = false;
					fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
							this.currentMatch, this.errorMsg));					
					this.currentMatch.clear();					
				}
			} else {
				result = false;
				fireGraTra(new GraTraEvent(this, GraTraEvent.NO_COMPLETION,
						this.currentMatch, this.currentMatch.getErrorMsg()));				
				this.currentMatch.clear();
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
	
	public void transform(List<Rule> rules) {
		if (this.grammar == null
				||this.ruleSubsequences == null || this.ruleSubsequences.isEmpty()) {
			return;
		}
		apply();
	}
	
	public void transform() {
		System.out.println("GraTra   by  "+this.getClass().getName()+"  running");
		
		this.stopping = false;
		
		if(!this.grammar.getListOfRules().isEmpty() && this.currentRuleSet.isEmpty())
			setRuleSet();
		
		String ruleSequencesAsText = getRuleSequenceAsText();
		String s2 = "rule sequence: " + ruleSequencesAsText;
		System.out.println(s2);
		
		if (this.writeLogFile) {
			String dirName = this.grammar.getDirName();
			String fileName = this.grammar.getFileName();
			if ((fileName == null) || fileName.equals(""))
				fileName = this.grammar.getName();
			openTransformProtocol(dirName, fileName);
			String version = "Version:  AGG " + Version.getID() + "\n";
			writeTransformProtocol(version);
			String s0 = "Graph transformation by rule sequence of : "
					+ this.grammar.getName();
			String s1 = "on graph : " + this.grammar.getGraph().getName();
			writeTransformProtocol(s0);
			writeTransformProtocol(s1);
			writeTransformProtocol(s2);
		}

		// first check the rules, the graph
		if (!this.grammarChecked) {
			Pair<Object, String> checkpair = this.grammar.isReadyToTransform(true);
			if (checkpair != null) {			
				Object test = checkpair.first;
				if (test != null) {
					String s = checkpair.second + "\nTransformation is stopped.";

					if (test instanceof Type)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.ATTR_TYPE_FAILED, s));
					else if (test instanceof Rule)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.RULE_FAILED, s));
					else if (test instanceof AtomConstraint)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.ATOMIC_GC_FAILED, s));
					transformFailed(s);
					return;
				}
			}
			// now check the host graph
			else if (!this.grammar.isGraphReadyForTransform()) {
				String s = "Graph of the grammar isn't fine."
					+ "\nPlease check attribute settings of the objects."
					+ "\nTransformation is stopped.";
				((GraTra) this).fireGraTra(new GraTraEvent(this,
						GraTraEvent.GRAPH_FAILED, s));
				transformFailed(s);
				return;
			}
			else if (!this.checkGraphConsistency()) {
				String s = "Graph consistency failed."
						+ "\nPlease check the host graph against the graph constraints."
						+ "\nTransformation is stopped.";
					((GraTra) this).fireGraTra(new GraTraEvent(this,
							GraTraEvent.GRAPH_FAILED, s));
					transformFailed(s);
					return;
			}
			this.grammarChecked = true;
		}
		
		// stop start time
		long startTime = System.currentTimeMillis();

		transform(this.grammar.getListOfRules());
		 
		if (this.options.hasOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO)) {
			this.checkGraphConsistency();
		}
		
		if (this.writeLogFile) {
			writeUsedTimeToProtocol("Used time for graph transformation: ", startTime);
			writeTransformProtocol("Graph transformation finished");
			closeTransformProtocol();
		}
		
		fireGraTra(new GraTraEvent(this, GraTraEvent.TRANSFORM_FINISHED,
				this.errorMsg));
	}

	public boolean isTrafoSequenceBroken() {
		return this.trafoSequenceBroken;
	}
	
	protected Vector<Rule> getEnabledRules(Vector<Rule> ruleSet) {
		Vector<Rule> vec = new Vector<Rule>(ruleSet.size());
		for (int j = 0; j < ruleSet.size(); j++) {
			if (ruleSet.elementAt(j).isEnabled())
				vec.add(ruleSet.elementAt(j));
		}
		return vec;
	}

	protected void transformFailed(String text) {
		System.out.println(text);
		writeTransformProtocol(text);
		writeTransformProtocol("\nGraph transformation failed");
		// fireGraTra(new GraTraEvent(this,GraTraEvent.TRANSFORM_FAILED,
		// errorMsg));
		fireGraTra(new GraTraEvent(this, GraTraEvent.TRANSFORM_FINISHED,
				this.errorMsg));
		closeTransformProtocol();
	}

	public boolean transformationDone() {
		return this.appliedOnce;
	}

	public String getProtocolName() {
		return this.protocolFileName;
	}

	protected String getRuleNames(Vector<Rule> rules) {
		String names = "[  ";
		for (int j = 0; j < rules.size(); j++) {
			Rule r = rules.elementAt(j);
			names = names + r.getName() + "  ";
		}
		names = names + "]";
		return names;
	}

	protected String getRuleNamesOfSubsequence(Vector<Pair<Rule, String>> rules) {
		String names = "Rule subsequence: ( ";
		for (int j = 0; j < rules.size(); j++) {
			Pair<Rule, String> p = rules.get(j);
			String rname = p.first.getName();
			names = names + rname;
			String iters = p.second;
			if (!iters.equals("1"))
				names = names + "{" + iters + "}";
			names = names + " ";
		}
		names = names + ")";
		return names;
	}

	protected void openTransformProtocol(String dirName, String fileName) {
		// System.out.println(RuleSequencesGraTra.openTransformProtocol...");
		String dName = dirName;
		String fName = "RuleSequencesGraTra.log";
		if ((fileName != null) && !fileName.equals("")) {
			if (fileName.endsWith(".ggx"))
				fName = fileName.substring(0, fileName.length() - 4)
						+ "_GraTra.log";
			else
				fName = fileName + "_GraTra.log";
		}
		// System.out.println(fName);
		if ((dName != null) && !dName.equals("")) {
			this.f = new File(dirName);
			if (this.f.exists()) {
				if (this.f.isFile()) {
					if (this.f.getParent() != null)
						dName = this.f.getParent() + File.separator;
					else
						dName = "." + File.separator;
				} else if (this.f.isDirectory())
					dName = this.f.getPath() + File.separator;
				else
					dName = "." + File.separator;
			} else
				dName = "." + File.separator;
			this.f = new File(dirName + fName);
		} else
			this.f = new File(fName);

		try {
			this.os = new FileOutputStream(this.f);
			this.protocolFileName = this.f.getName();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		writeTransformProtocol((new Date()).toString());
	}

	protected void writeTransformProtocol(String s) {
		if (this.os == null)
			return;
		if (!this.os.getChannel().isOpen())
			return;

		try {
			if (!s.equals("\n"))
				this.os.write(s.getBytes());
			this.os.write('\n');
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void writeUsedTimeToProtocol(String text, long beginTime) {
		writeTransformProtocol(text + (System.currentTimeMillis()-beginTime) + "ms\n");
	}
	
	protected void closeTransformProtocol() {
		if (this.os == null)
			return;
		try {
			this.os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Pair<Morphism, Morphism> derivation(Match m)
	/** not implemented yet! * */
	{
		return (null);
	}

	public String getRuleSequenceAsText() {
		this.ruleNameSequences = new Vector<String>(this.ruleSubsequences.size());
		String s = "";
		for (int i = 0; i < this.ruleSubsequences.size(); i++) {
			Pair<List<Pair<Rule, String>>, String> grp = this.ruleSubsequences.get(i);
			String grpStr = "";
			List<Pair<Rule, String>> grpRules = grp.first;
			long grpIters = -1;
			String grpItersStr = grp.second;
			if (grpItersStr.equals("*"))
				grpStr = grpStr + "( ";
			else {
				grpIters = (new Long(grp.second)).longValue();
				if (grpRules.size() > 1 || grpIters > 1)
					grpStr = grpStr + "( ";
			}
			for (int j = 0; j < grpRules.size(); j++) {
				Pair<Rule, String> p = grpRules.get(j);
				if (p.first == null) 
					continue;
				String rulename = p.first.getName();
				grpStr = grpStr + rulename;
				long ruleIters = -1;
				String ruleItersStr = p.second;
				if (ruleItersStr.equals("*"))
					grpStr = grpStr + "{" + ruleItersStr + "}";
				else {
					ruleIters = (new Long(p.second)).longValue();
					if (ruleIters > 1)
						grpStr = grpStr + "{" + ruleIters + "}";
				}
				grpStr = grpStr + " ";
			}
			if (grpItersStr.equals("*"))
				grpStr = grpStr + ")";
			else if (grpRules.size() > 1 || grpIters > 1)
				grpStr = grpStr + ")";

			if (grpRules.size() > 0) {
				if (grpItersStr.equals("*"))
					grpStr = grpStr + "{" + grpItersStr + "}";
				else if (grpIters > 1)
					grpStr = grpStr + "{" + grpIters + "}";
			} else
				grpStr = "()";

			this.ruleNameSequences.add(grpStr);

			grpStr = grpStr + "\n";

			s = s + grpStr;
		}
		return s;
	}
	
	protected boolean isInputParameterSet(
			final Graph g, 
			boolean left,
			final Match match) {
		
//		((VarTuple)match.getAttrContext().getVariables()).showVariables();
		if (match != null
				&& left
				&& this.ruleSequence != null 
				&& !this.ruleSequence.getObjectFlow().isEmpty()
				&& ((VarTuple)match.getAttrContext().getVariables()).areInputParametersSet()) {
			return true;
		}
		
		return super.isInputParameterSet(g, left, match);
	}

}
