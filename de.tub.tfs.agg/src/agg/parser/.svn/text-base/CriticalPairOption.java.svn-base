package agg.parser;

import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import agg.util.Pair;

/**
 * This option configures the algorithm of the critical pairs.
 * 
 * @version $Id: CriticalPairOption.java,v 1.22 2010/11/16 23:33:08 olga Exp $
 * @author $Author: olga $
 */
public class CriticalPairOption {

	public final static String COMPLETE = "complete";
	public final static String CONSISTENT = "consistent";
	public final static String STRONG_ATTR_CHECK = "strongAttrCheck";
	public final static String IGNORE_SAME_MATCH = "ignoreSameMatch";
	public final static String IGNORE_SAME_RULE = "ignoreSameRule";
	public final static String DIRECTLY_STRICT_CONFLUENT = "directlyStrictConfluent";
	public final static String DIRECTLY_STRICT_CONFLUENT_UPTOISO = "directlyStrictConfluentUpToIso";
	public final static String ESSENTIAL = "essential";
	
	/**
	 * The algorithm of the critical pairs.
	 */
	public static final int EXCLUDEONLY = 0; // == CONFLICT

	/**
	 * The additional algorithm of the critical pairs.
	 */
	public static final int TRIGGER_DEPEND = 1;
	
	public static final int TRIGGER_SWITCH_DEPEND = 2;
	
	/**
	 * @deprecated  replaced by TRIGGER_DEPEND
	 */
	public static final int DEPENDONLY = TRIGGER_DEPEND;

	private boolean switchDependency;
	
	private boolean priority;

	private boolean layered;

	private int layer;

	private int algorithm;

	private boolean complete;

	private boolean reduce;

	private boolean reduceSameMatch;
	
	private boolean withNACs, withPACs;

	private boolean consistent;
	
	private boolean strongAttrCheck;

	private boolean equalVariableNameOfAttrMapping;
	
	private boolean ignoreIdenticalRules;

	private boolean directStrctCnfl, directStrctCnflUpToIso;
	
	private Vector<OptionEventListener> listener;

	/**
	 * Creates new option with default settings.
	 */
	public CriticalPairOption() {
		this.algorithm = EXCLUDEONLY;
		this.priority = false;
		this.layered = false;
		this.layer = -1;
		this.complete = true;
		this.reduce = false; // now it is essential
		this.reduceSameMatch = false;
		this.withNACs = true;
		this.withPACs = true;
		this.consistent = false; 
		this.strongAttrCheck = false; //true;
		this.equalVariableNameOfAttrMapping = false;
		this.ignoreIdenticalRules = false;
		this.directStrctCnfl = false;
		this.directStrctCnflUpToIso = false;
		this.listener = new Vector<OptionEventListener>(2);
	}

	/**
	 * Returns the algorithm of the critical pair analysis.
	 * 
	 * @return The algorithm
	 */
	public int getCriticalPairAlgorithm() {
		return this.algorithm;
	}

	/**
	 * Sets the algorithm of the critical pair algorithm.
	 * 
	 * @param algorithm
	 *            The algorithm.
	 */
	public void setCriticalPairAlgorithm(int algorithm) {
		// System.out.println("CP_Option.setCriticalPairAlgorithm "+algorithm);
		this.algorithm = algorithm;
		if (algorithm == TRIGGER_SWITCH_DEPEND)
			this.switchDependency = true;
		else
			this.switchDependency = false;
		
		fireOptionEvent(new EventObject(this));
	}

	public boolean switchDependencyEnabled() {
		return this.switchDependency;
	}
	
	public void enableSwitchDependency(boolean enable) {
		this.switchDependency = enable;
	}
	
	public boolean priorityEnabled() {
		return this.priority;
	}

	public void enablePriority(boolean enable) {
		if (this.priority != enable) {
			this.priority = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	/**
	 * Checks if the graph grammar is layered.
	 * 
	 * @return true if the graph grammar is layered
	 */
	public boolean layeredEnabled() {
		return this.layered;
	}

	/**
	 * Enable if layered graph grammar is used.
	 * 
	 * @param enable
	 *            true for layered graph grammar
	 */
	public void enableLayered(boolean enable) {
		if (this.layered != enable) {
			this.layered = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public void setLayer(int l) {
		this.layer = l;
		fireOptionEvent(new EventObject(this));
	}

	public int getLayer() {
		return this.layer;
	}

	public void setOptionsFromList(final List<Pair<String,String>> optionList) {
		for (int i=0; i<optionList.size(); i++) {
			final Pair<String,String> opVal = optionList.get(i);
			if (opVal.first.equals(CriticalPairOption.COMPLETE)) {
				this.enableComplete(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.CONSISTENT)) {
				this.enableConsistent(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.STRONG_ATTR_CHECK)) {
				this.enableStrongAttrCheck(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.IGNORE_SAME_MATCH)) {
				this.enableReduceSameMatch(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.IGNORE_SAME_RULE)) {
				this.enableIgnoreIdenticalRules(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT)) {
				this.enableDirectlyStrictConfl(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT_UPTOISO)) {
				this.enableDirectlyStrictConflUpToIso(Boolean.valueOf(opVal.second).booleanValue());
			}
			else if (opVal.first.equals(CriticalPairOption.ESSENTIAL)) {
				this.enableReduce(Boolean.valueOf(opVal.second).booleanValue());
			}
		}
	}
	
	public boolean completeEnabled() {
		return this.complete;
	}

	public void enableComplete(boolean enable) {
		if (this.complete != enable) {
			this.complete = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public boolean reduceEnabled() {
		return this.reduce;
	}

	public void enableReduce(boolean enable) {
		if (this.reduce != enable) {
			this.reduce = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public boolean reduceSameMatchEnabled() {
		return this.reduceSameMatch;
	}
	
	public void enableDirectlyStrictConfl(boolean enable) {
		if (this.directStrctCnfl != enable) {
			this.directStrctCnfl = enable;
			fireOptionEvent(new EventObject(this));
		}
	}
	
	public boolean directlyStrictConflEnabled() {
		return this.directStrctCnfl;
	}
	
	public void enableDirectlyStrictConflUpToIso(boolean enable) {
		if (this.directStrctCnflUpToIso != enable) {
			this.directStrctCnflUpToIso = enable;
			fireOptionEvent(new EventObject(this));
		}
	}
	
	public boolean directlyStrictConflUpToIsoEnabled() {
		return this.directStrctCnflUpToIso;
	}
	
	public void enableReduceSameMatch(boolean enable) {
		if (this.reduceSameMatch != enable) {
			this.reduceSameMatch = enable;
			fireOptionEvent(new EventObject(this));
		}
	}
	
	public void enableNacs(boolean enable) {
		if (this.withNACs != enable) {
			this.withNACs = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public boolean nacsEnabled() {
		return this.withNACs;
	}

	public void enablePacs(boolean enable) {
		if (this.withPACs != enable) {
			this.withPACs = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public boolean pacsEnabled() {
		return this.withPACs;
	}
	
	public boolean consistentEnabled() {
		return this.consistent;
	}

	public void enableConsistent(boolean enable) {
		// System.out.println("CriticalPairOption:: consistent: "+enable);
		if (this.consistent != enable) {
			this.consistent = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	public boolean strongAttrCheckEnabled() {
		return this.strongAttrCheck;
	}

	public void enableStrongAttrCheck(boolean enable) {
		if (this.strongAttrCheck != enable) {
			this.strongAttrCheck = enable;
			fireOptionEvent(new EventObject(this));
		}
	}
	
	public boolean equalVariableNameOfAttrMappingEnabled() {
		return this.equalVariableNameOfAttrMapping;
	}
	
	public void enableEqualVariableNameOfAttrMapping(boolean enable) {
		if (this.equalVariableNameOfAttrMapping != enable) {
			this.equalVariableNameOfAttrMapping = enable;
			fireOptionEvent(new EventObject(this));
		}
	}
	
	public boolean ignoreIdenticalRulesEnabled() {
		return this.ignoreIdenticalRules;
	}

	public void enableIgnoreIdenticalRules(boolean enable) {
		if (this.ignoreIdenticalRules != enable) {
			this.ignoreIdenticalRules = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	/**
	 * Adds an option listener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addOptionListener(OptionEventListener l) {
		if (!this.listener.contains(l))
			this.listener.addElement(l);
	}

	/**
	 * Removes an option listener
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeOptionListener(OptionEventListener l) {
		if (this.listener.contains(l))
			this.listener.removeElement(l);
	}

	/**
	 * Sends a event to all its listeners.
	 * 
	 * @param event
	 *            The event which will be sent
	 */
	private synchronized void fireOptionEvent(EventObject event) {
		for (int i = 0; i < this.listener.size(); i++) {
			this.listener.elementAt(i).optionEventOccurred(event);
		}
	}

	/**
	 * Returns the option in human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		return super.toString() + " " + getCriticalPairAlgorithm();
	}
}
/*
 * $Log: CriticalPairOption.java,v $
 * Revision 1.22  2010/11/16 23:33:08  olga
 * tuning
 *
 * Revision 1.21  2010/11/07 20:48:10  olga
 * tuning
 *
 * Revision 1.20  2010/11/06 18:33:50  olga
 * extended and improved
 *
 * Revision 1.19  2010/11/04 11:01:31  olga
 * tuning
 *
 * Revision 1.18  2010/08/23 07:34:51  olga
 * tuning
 *
 * Revision 1.17  2009/03/19 09:31:06  olga
 * CPE: attr check improved
 *
 * Revision 1.16  2009/03/12 12:27:40  olga
 * Consistency check of critical graphs in CPA by default OFF
 *
 * Revision 1.15  2009/03/12 10:57:46  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.14  2008/05/19 09:19:33  olga
 * Applicability of Rule Sequence - reworking
 *
 * Revision 1.13  2008/05/07 08:37:55  olga
 * Applicability of Rule Sequences with NACs
 *
 * Revision 1.12  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.11  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.10  2007/09/27 08:42:46  olga
 * CPA: new option  -ignore pairs with same rules and same matches-
 *
 * Revision 1.9  2007/09/10 13:05:39  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.8 2007/01/11 10:21:16 olga
 * Optimized Version 1.5.1beta , free for tests
 * 
 * Revision 1.7 2006/12/13 13:32:59 enrico reimplemented code
 * 
 * Revision 1.6 2006/03/01 09:55:46 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.5 2005/12/21 14:45:37 olga Event bug fixed
 * 
 * Revision 1.4 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.3 2005/09/26 16:41:20 olga CPA graph, CPs - visualization
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.5 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.4 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.3 2004/06/21 08:35:33 olga immer noch CPs
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.4 2001/06/18 13:37:46 olga Bei Critical Pair ein neuer Menuitem:
 * Debug, wo man einzelne Regelpaare testen kann. System.gc() eingefuegt.
 * 
 * Revision 1.3 2001/06/13 16:49:34 olga Parser Classen Optimierung.
 * 
 * Revision 1.2 2001/03/08 10:44:50 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.4 2001/01/28 13:14:51 shultzke API fertig
 * 
 * Revision 1.1.2.3 2000/12/21 13:46:03 shultzke optionen weiter veraendert
 * 
 * Revision 1.1.2.2 2000/12/19 12:11:43 shultzke Parseroptiongui und
 * criticalpairoptionGUI getrennt
 * 
 * Revision 1.1.2.1 2000/12/18 13:33:39 shultzke Optionen veraendert
 * 
 */
