package agg.xt_basis;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.Vector;

import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * This class defines the options for graph transformation, backed by a vector
 * of names. Possible options are: for completion strategy (it is always the
 * first element of the vector): "CSP", "CSP w/o BJ" for match : "injective",
 * "dangling", "identification", "NACs", other general options: "consistency",
 * "checkRuleApplicability", "showGraphAfterStep", "waitAfterStep",
 * "selectNewAfterStep", for kind of graph transformation: "layered",
 * "priority", "ruleSequence", other options of layered graph transformation:
 * "layeredLoop", "stopLayerAndWait", "breakLayer", "breakAllLayer".
 * 
 * Please node: Options: "checkRuleApplicability", "showGraphAfterStep",
 * "waitAfterStep", "selectNewAfterStep", "stopLayerAndWait", "breakLayer",
 * "breakAllLayer" can be used with AGG GUI, only.
 */
public class GraTraOptions {

	public final static String CSP = "CSP";

	public final static String CSP_WO_BJ = "CSP w/o BJ";

	public final static String INJECTIVE = "injective";

	public final static String DANGLING = "dangling";

	public final static String IDENTIFICATION = "identification";

	public final static String NACS = "NACs";

	public final static String PACS = "PACs";
	
	public final static String GACS = "GACs";
	
	public final static String RANDOM_CSP_DOMAIN = "randomCSPDomain";
	
	public final static String DETERMINED_CSP_DOMAIN = "determinedCSPDomain";
	
	public final static String CONSISTENT_ONLY = "consistency";

	public final static String CONSISTENCY_CHECK_AFTER_GRAPH_TRAFO = "consistencyCheckAfterGraphTrafo";
	
	public final static String STOP_INCONSISTENT = "stopInconsistency";

	public final static String CHECK_RULE_APPLICABILITY = "checkRuleApplicability";

	public final static String SHOW_GRAPH_AFTER_STEP = "showGraphAfterStep";

	public final static String WAIT_AFTER_STEP = "waitAfterStep";
	
	public final static String SELECT_NEW_AFTER_STEP = "selectNewAfterStep";

	public final static String NONDETERMINISTICALLY = "nondeterministically";
	
	public final static String PRIORITY = "priority";

	public final static String RULE_SEQUENCE = "ruleSequence";
	
	public final static String EACH_RULE_TO_APPLY = "eachRuleToApply";

	public final static String LAYERED = "layered";

	public final static String LOOP_OVER_LAYER = "layeredLoop";

	public final static String RESET_GRAPH = "resetGraph";
	
	public final static String STOP_LAYER_AND_WAIT = "stopLayerAndWait";

	public final static String BREAK_LAYER = "breakLayer";

	public final static String BREAK_ALL_LAYER = "breakAllLayer";

	public final static String PARALLEL_MATCHING = "parallelMatching";
	
	public final static String XY_POS_ATTRIBUTE = "xyPosAttribute";
	
	final private Vector<String> options;

	private MorphCompletionStrategy strategy;

	/** Creates an new empty list */
	public GraTraOptions() {
		this.options = new Vector<String>();
	}

	/** Adds the specified option name to this list */
	public void addOption(String name) {
		if (!this.options.contains(name))
			this.options.addElement(name);
	}

	/** Removes the specified option name from this list */
	public void removeOption(String name) {
		if (this.options.contains(name))
			this.options.removeElement(name);
	}

	/** Returns true if this list contains the specified option name */
	public boolean hasOption(String name) {
		if (this.options.contains(name))
			return true;
		
		return false;
	}

	/** Returns current option names */
	public Vector<String> getOptions() {
		return this.options;
	}

	/** Returns current morphism completion strategy */
	public MorphCompletionStrategy getCompletionStrategy() {
		updateMorphismCompletionStrategy();
		return this.strategy;
	}

	public String toString() {
		int l = this.options.size();
		String result = this.options.elementAt(0);
		result += " [ ";
		for (int i = 1; i < l - 1; i++) {
			result += this.options.elementAt(i);
			result += ", ";
		}
		result += this.options.elementAt(l - 1);
		result += " ]";
		return result;
	}

	/**
	 * Updates morphism completion strategy and its properties (match
	 * conditions) using current list of option names.
	 */
	public void updateMorphismCompletionStrategy() {
		// set strategy
		String stratName = "";
		if (this.options.contains("CSP"))
			stratName = "CSP";
		else if (this.options.contains("CSP w/o BJ"))
			stratName = "CSP w/o BJ";

		Enumeration<MorphCompletionStrategy> strats = CompletionStrategySelector.getStrategies();
		while (strats.hasMoreElements()) {
			MorphCompletionStrategy mcs = strats.nextElement();
			String name = CompletionStrategySelector.getName(mcs);
			if (stratName.equals(name)) {
				this.strategy = mcs;
			}
		}

		if (this.strategy == null) {
			return;
		}

		// set match conditions
		// BitSet supportbits =
		this.strategy.getSupportedProperties();
		BitSet activebits = this.strategy.getProperties();
		for (int j = 0; j < CompletionPropertyBits.BITNAME.length; j++) {
			String bitName = CompletionPropertyBits.BITNAME[j];
			if (this.options.contains(bitName))
				activebits.set(j);
			else
				activebits.clear(j);
		}
		
		if (this.options.contains(GraTraOptions.DETERMINED_CSP_DOMAIN))
				this.strategy.setRandomisedDomain(false);	
	}
}
