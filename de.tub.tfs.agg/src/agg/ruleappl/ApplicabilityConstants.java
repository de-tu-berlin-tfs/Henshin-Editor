/**
 * 
 */
package agg.ruleappl;

/**
 * @author olga
 *
 */
public class ApplicabilityConstants {
	
	public final static String UNDEFINED = "undefined";
	
	/** 
	 * Rule applicability criterion 1...4
	 */
	/** 1 */
	public final static String INITIALIZATION = "initialization";
	
	/** 2 */
	public final static String NO_NODE_DELETING = "no-node-deleting-rules";
	
	/** 3 */
	public final static String NO_IMPEDING_PREDECESSORS = "no-impeding-predecessors";
	
	/** 4a */
	public final static String PURE_ENABLING_PREDECESSOR = "pure-enabling-predecessor";
	/** 4b */
	public final static String PARTIAL_ENABLING_PREDECESSOR = "partial-enabling-predecessor";
	/** 4c */
	public final static String DIRECT_ENABLING_PREDECESSOR = "direct-enabling-predecessor";
	/** 4d */
	public final static String PREDECESSOR_NOT_NEEDED = "predecessor-not-needed";

	
	public final static String NO_ENABLING_PREDECESSOR_NAC = "no-enabling-predecessor-nac";
	
	public final static String ENABLING_PREDECESSOR = "enabling-predecessor";
	
	/** 
	 * Rule non-applicability criterion 
	 */
	public final static String INITIALIZATION_ERROR = "initialization-error";
	public final static String NO_ENABLING_PREDECESSOR = "no-enabling-predecessor";
	

}
