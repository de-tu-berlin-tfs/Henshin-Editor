package agg.xt_basis;


import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import agg.cons.AtomConstraint;
import agg.util.Pair;
import agg.xt_basis.agt.RuleScheme;

public class DefaultGraTraImpl extends GraTra {

	@SuppressWarnings("unused")
	private int doneSteps; // only for test

	private int counterMax;
	
	Random ran = new Random();

	private boolean appliedOnce;
	
	private boolean applyContinue = false;
	
	private boolean allRulesEnabled = false;

	File f;

	FileOutputStream os;

	String protocolFileName = "";
	
	private boolean grammarChecked;
	
	public DefaultGraTraImpl() {
	}

	public void dispose() {	
		super.dispose();
	}
	
	/** 
	 * not yet implemented! 
	 */
	public Pair<Morphism, Morphism> derivation(Match m) {
		return (null);
	}
	
	public boolean apply() {
		if (!this.allRulesEnabled) {
			// remove disabled rules from currentRuleSet
			for (int j = 0; j < this.currentRuleSet.size(); j++) {
				if (!this.currentRuleSet.elementAt(j).isEnabled()) {
					this.currentRuleSet.removeElementAt(j);
					j--;
				}
			}
			this.allRulesEnabled = true;
		}

		boolean applied = false;
		while (!this.stopping && (this.currentRuleSet.size() > 0) && !applied) {
			this.pauseRule = false;
			this.stoppingRule = false;
	    		    	
	    	if (!this.applyContinue) {
	    		int i = this.ran.nextInt(this.currentRuleSet.size());
	    		this.currentRule = this.currentRuleSet.elementAt(i);
	    	}
	    	else 
	    		this.applyContinue = false;

	    	if(!this.stoppingRule) {
	    		if (this.currentRule instanceof RuleScheme) {
	    			applied = apply((RuleScheme) this.currentRule);
	    		} 
	    		else {
	    			applied = this.currentRule.canMatch(this.hostgraph, this.strategy) 
									&& 
									apply(this.currentRule);
	    		}
	    	}
	    	else
	    		this.stoppingRule = false; 
	    	
	    	if (this.pauseRule) 
	    		return false;
	    	
//			System.out.println(currentRule.getName() + " \t applied:  "
//					+ applied);
			if (this.os != null)
				writeTransformProtocol(this.currentRule.getName() + " \t applied:  "
						+ applied);

			if (!applied) {
				if (this.os != null)
					writeTransformProtocol(getErrorMsg());

				this.currentRuleSet.remove(this.currentRule);

				if (this.os != null)
					writeTransformProtocol(getRuleNames(this.currentRuleSet));
			} else {
				System.out.println(this.currentRule.getName() + " \t applied");
				this.doneSteps++;
				this.appliedOnce = true;
				if (!isGraphConsistent())
					this.stopping = true;
			}
		}
		return applied;
	}

	public void transform(List<Rule> ruleSet) {		
		this.allRulesEnabled = true;
		boolean applicable = true;
		while (!this.stopping && applicable) {
			if (this.os != null) {
				String ss = getRuleNames(ruleSet);
				writeTransformProtocol(ss);
			}
			this.currentRuleSet = new Vector<Rule>(ruleSet);
			
			applicable = apply();
			
			if(this.pauseRule) return;
    		
    		if(this.waitAfterStep) return;
		}
	}
	
	public void setMaxOfCounter(int nb) {
		this.counterMax = nb;
	}
	
	public void transformByCounter(List<Rule> ruleSet) {		
		this.allRulesEnabled = true;
		boolean applicable = true;
	
		for (int i = 1; i<=this.counterMax && applicable; i++) {
			if (this.os != null) {
				String ss = getRuleNames(ruleSet);
				writeTransformProtocol(ss);
			}
			
			this.currentRuleSet = new Vector<Rule>(ruleSet);	
			
			long t0 = System.currentTimeMillis();
			
			applicable = apply();
			
			System.out.println(i+")  Time: "
					+(System.currentTimeMillis()-t0) + "ms   "
					+Runtime.getRuntime().freeMemory() + "b"
					+"  nodes  ("+this.hostgraph.getNodesCount()+")"
					+"  edges  ("+this.hostgraph.getArcsCount()+")"		
			);
						
//			if(pauseRule) return;
//    		
//    		if(waitAfterStep) return;
		}
	}
	
	public void transformContinue() {  
		this.applyContinue = true;
		this.pauseRule = false;
		  	  	  	  	
		transform(this.currentRuleSet);
		      
//		if(pauseRule) return;		    	
//		if(!stopping && waitAfterStep) return;		    	
	}
	
	public void transformContinueWithNextStep() { 
		this.pauseRule = false;
		    	
		transform(this.currentRuleSet);
		     	
//		if(pauseRule) return;		    	
//		if(!stopping && waitAfterStep) return;
		    	
//		writeTransformProtocol("\nGraph transformation is finished");
//		fireGraTra(new GraTraEvent(this,GraTraEvent.TRANSFORM_FINISHED));
//		closeTransformProtocol();     
	}
	
	
	public void transform() {
		this.stopping = false;
		
		if(!this.grammar.getListOfRules().isEmpty() && this.currentRuleSet.isEmpty())
			setRuleSet();
		
		if (this.writeLogFile) {
			String dirName = this.grammar.getDirName();
			String fileName = this.grammar.getFileName();
			if ((fileName == null) || fileName.equals(""))
				fileName = this.grammar.getName();
			openTransformProtocol(dirName, fileName);
			String version = "Version:  AGG " + Version.getID() + "\n";
			writeTransformProtocol(version);
			String s0 = "Graph transformation of : " + this.grammar.getName();
			String s1 = "on graph : " + this.grammar.getGraph().getName();
			String s2 = getRuleNames(this.currentRuleSet);
			writeTransformProtocol(s0);
			writeTransformProtocol(s1);
			writeTransformProtocol(s2);
			writeTransformProtocol("\n");
		}
		// first check the rules, the graph
		if (!this.grammarChecked) {
			Pair<Object, String> pair = this.grammar.isReadyToTransform(true);
			if (pair != null) {
				Object test = pair.first;
				if (test != null) {
					String s0 = pair.second + "\nTransformation is stopped.";

					if (test instanceof Type)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.ATTR_TYPE_FAILED, s0));
					else if (test instanceof Rule)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.RULE_FAILED, s0));
					else if (test instanceof AtomConstraint)
						((GraTra) this).fireGraTra(new GraTraEvent(this,
								GraTraEvent.ATOMIC_GC_FAILED, s0));
					transformFailed(s0);
					return;
				}
			} else if (!this.grammar.isGraphReadyForTransform()) {
				String s0 = "Graph of the grammar isn't fine."
						+ "\nPlease check attribute settings of the objects. \nTransformation is stopped.";
				((GraTra) this).fireGraTra(new GraTraEvent(this,
						GraTraEvent.GRAPH_FAILED, s0));
				transformFailed(s0);
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

		Vector<Rule> ruleSet = getEnabledRules(this.currentRuleSet);
		
		// set start time
		long startTime = System.currentTimeMillis();
		
		if (this.counterMax == 0)
			transform(ruleSet);
		else
			this.transformByCounter(ruleSet);

		// stop end time
		System.out.println("Used time for graph transformation: "
				+ (System.currentTimeMillis()-startTime) + "ms");
		
		if (this.options.hasOption(GraTraOptions.CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO)) {
			this.checkGraphConsistency();
		}
		
		
		if (this.writeLogFile) {
			writeTransformProtocol("\nUsed time for graph transformation: "
					+ (System.currentTimeMillis()-startTime) + "ms");
			writeTransformProtocol("\nGraph transformation finished");
			closeTransformProtocol();
		}
		
		fireGraTra(new GraTraEvent(this, GraTraEvent.TRANSFORM_FINISHED,
				this.errorMsg));
	}

	private Vector<Rule> getEnabledRules(Vector<Rule> ruleSet) {
		Vector<Rule> vec = new Vector<Rule>(ruleSet.size());
		for (int j = 0; j < ruleSet.size(); j++) {
			if (ruleSet.elementAt(j).isEnabled())
				vec.add(ruleSet.elementAt(j));
		}
		return vec;
	}

	private void transformFailed(String text) {
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

	private String getRuleNames(List<Rule> rules) {
		String names = "[ ";
		for (int j = 0; j < rules.size(); j++) {
			Rule r = rules.get(j);
			names = names + r.getName() + " ";
		}
		names = names + "]";
		return names;
	}

	private void openTransformProtocol(String dirName, String fileName) {
		String dName = dirName;
		String fName = "DefaultGraTra.log";
		// System.out.println("DefaultGraTraImpl.openTransformProtocol: dirName:
		// "+dirName);
		// System.out.println("DefaultGraTraImpl.openTransformProtocol:
		// fileName: "+fileName);
		if ((fileName != null) && !fileName.equals("")) {
			if (fileName.endsWith(".ggx"))
				fName = fileName.substring(0, fileName.length() - 4)
						+ "_GraTra.log";
			else
				fName = fileName + "_GraTra.log";
		}
		// System.out.println(fName);

		if ((dName != null) && !dName.equals("")) {
			this.f = new File(dName);
			if (this.f.exists()) {
				if (this.f.isFile()) {
					if (this.f.getParent() != null)
						dName = this.f.getParent() + File.separator;
					else
						dName = "." + File.separator;
				} else if (this.f.isDirectory()) {
					// System.out.println(dirName);
					dName = this.f.getPath() + File.separator;
				} else
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

	private void writeTransformProtocol(String s) {
		if (this.os == null)
			return;
		try {
			if (!s.equals("\n"))
				this.os.write(s.getBytes());
			this.os.write('\n');
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void closeTransformProtocol() {
		if (this.os == null)
			return;
		try {
			this.os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
