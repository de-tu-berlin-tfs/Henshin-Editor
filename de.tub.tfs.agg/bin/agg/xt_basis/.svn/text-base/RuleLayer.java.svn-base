package agg.xt_basis;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;


/**
 * Rule layer is a set of rule layer of a given layered graph grammar. The set
 * is backed by a hash table.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class RuleLayer {

	private Hashtable<Rule, Integer> ruleLayer;

//	private Enumeration<Rule> rules;

	private Vector<Rule> rulesVec;

	/**
	 * Creates a new set of rule layers for a given layered graph grammar.
	 * 
	 * @param rules
	 *            The rules of a graph grammar.
	 */
	public RuleLayer(Enumeration<Rule> rules) {
//		this.rules = rules;
		this.rulesVec = new Vector<Rule>(0);
		while (rules.hasMoreElements()) {
			this.rulesVec.addElement(rules.nextElement());
		}
		initRuleLayer();
	}

	public RuleLayer(List<Rule> rules) {
		this.rulesVec = new Vector<Rule>(0);
		for (int i=0; i<rules.size(); i++) {
			this.rulesVec.add(rules.get(i));
		}
//		this.rules = this.rulesVec.elements();
		initRuleLayer();
	}
	
	public RuleLayer(Vector<Rule> rules) {
		this.rulesVec = rules;
//		this.rules = this.rulesVec.elements();
		initRuleLayer();
	}

	public void dispose() {
		this.ruleLayer.clear();
		this.rulesVec = null;
//		this.rules = null;
	}
	
	/** Sets the layer of the specified rule */
	public void setLayer(Rule rule, int layer) {
		rule.setLayer(layer);
		this.ruleLayer.put(rule, Integer.valueOf(layer));
	}

	private void initRuleLayer() {
		this.ruleLayer = new Hashtable<Rule, Integer>();
		for (int i = 0; i < this.rulesVec.size(); i++) {
			Rule rule = this.rulesVec.elementAt(i);
			this.ruleLayer.put(rule, Integer.valueOf(rule.getLayer()));
		}
	}

	public boolean compareTo(RuleLayer rl) {
		return this.ruleLayer.equals(rl.getRuleLayer());
	}
	
	/**
	 * Returns the rule layer. A rule is a key, a layer is a value.
	 * 
	 * @return The rule layer.
	 */
	public Hashtable<Rule, Integer> getRuleLayer() {
		return this.ruleLayer;
	}

	/**
	 * Returns the smallest layer of the rule layer.
	 * 
	 * @return The smallest layer.
	 */
	public Integer getStartLayer() {
		int startLayer = Integer.MAX_VALUE;
		Integer result = null;
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer layer = getRuleLayer().get(key);
			if (layer.intValue() < startLayer) {
				startLayer = layer.intValue();
				result = layer;
			}
		}
		return result;
	}

	/**
	 * Inverts a rule layer so that the layer is the key and the value is a set.
	 * 
	 * @return The inverted layer function.
	 */
	public Hashtable<Integer, HashSet<Rule>> invertLayer() {
		Hashtable<Integer, HashSet<Rule>> inverted = new Hashtable<Integer, HashSet<Rule>>();
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Rule key = keys.nextElement();
			Integer value = this.ruleLayer.get(key);
			HashSet<Rule> invertedValue = inverted.get(value);
			if (invertedValue == null) {
				invertedValue = new HashSet<Rule>();
				invertedValue.add(key);
				inverted.put(value, invertedValue);
			} else {
				invertedValue.add(key);
			}
		}
		return inverted;
	}

	/**
	 * Returns the rule layer in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		String resultString = "Rule:\t\tLayer:\n";
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Rule key = keys.nextElement();
			Integer value = this.ruleLayer.get(key);
			resultString += key.getName() + "\t\t" + value.toString() + "    "
					+ key.getLayer() + "\n";
		}
		return resultString;
	}
}
/*
 * $Log: RuleLayer.java,v $
 * Revision 1.11  2010/09/23 08:27:31  olga
 * tuning
 *
 * Revision 1.10  2010/03/08 15:51:38  olga
 * code optimizing
 *
 * Revision 1.9  2008/07/21 13:41:40  olga
 * Code tuning
 *
 * Revision 1.8  2008/04/07 09:36:53  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.7  2007/11/19 08:48:40  olga
 * Some GUI usability mistakes fixed.
 * Default values in node/edge of a type graph implemented.
 * Code tuning.
 *
 * Revision 1.6  2007/11/01 09:58:15  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.5  2007/09/10 13:05:35  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.4 2007/06/13 08:33:01 olga Update: V161
 * 
 * Revision 1.3 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.2 2005/10/24 09:04:49 olga GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:54 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:02 olga Version with Eclipse
 * 
 * Revision 1.6 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.5 2004/12/20 14:53:49 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.4 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.3 2003/05/14 17:56:46 komm Added minimum multiplicity and removed
 * TODOs
 * 
 * Revision 1.2 2003/03/05 18:24:04 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:28 olga Imported sources
 * 
 * Revision 1.1 2001/08/16 14:16:53 olga Aenderungen wegen layered graph
 * transformation. Rule Layer wird in XML gespeichert und geladen. GraTra
 * Optionen werden in XML gespeichert und geladen.
 * 
 * Revision 1.4 2001/08/02 15:22:16 olga Error-Meldungen eingebaut in
 * LayerFunction und die Anzeige dieser Meldungen in GUI.
 * 
 * Revision 1.3 2001/07/19 15:20:09 olga Fehlermeldung bei Layer Function.
 * 
 * Revision 1.2 2001/03/08 10:44:52 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.6 2001/01/28 13:14:54 shultzke API fertig
 * 
 * Revision 1.1.2.5 2001/01/11 11:36:08 shultzke Laden und speichern der
 * kritischen Paare geht, es fehlt nur noch das Laden fuer den Parser.
 * 
 * Revision 1.1.2.4 2000/12/26 10:00:04 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.3 2000/12/18 13:33:39 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.2 2000/12/10 20:26:08 shultzke Parser kann XML
 * 
 * Revision 1.1.2.1 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */
