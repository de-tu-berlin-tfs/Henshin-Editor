package agg.xt_basis;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

//import com.objectspace.jgl.HashSet;

/**
 * Rule priority is a set of rule priority of a given graph grammar. The set is
 * backed by a hash table.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class RulePriority {

	private Hashtable<Rule, Integer> rulePriority;

//	private Enumeration<Rule> rules;

	private Vector<Rule> rulesVec;

	/**
	 * Creates a new set of rule priorities for a given graph grammar.
	 * 
	 * @param rules
	 *            The rules of a graph grammar.
	 */
	public RulePriority(Enumeration<Rule> rules) {
//		this.rules = rules;
		this.rulesVec = new Vector<Rule>(0);
		while (rules.hasMoreElements())
			this.rulesVec.addElement(rules.nextElement());
		initRulePriority();
	}

	public RulePriority(List<Rule> rules) {
		this.rulesVec = new Vector<Rule>(0);
		for (int i=0; i<rules.size(); i++) {
			this.rulesVec.add(rules.get(i));
		}
//		this.rules = rulesVec.elements();
		initRulePriority();
	}
	
	public RulePriority(Vector<Rule> rules) {
		this.rulesVec = rules;
//		this.rules = rulesVec.elements();
		initRulePriority();
	}

	/** Sets the priority of the specified rule */
	public void setPriority(Rule rule, int p) {
		rule.setPriority(p);
		this.rulePriority.put(rule, Integer.valueOf(p));
		// System.out.println("rule priority: "+((Integer)
		// rulePriority.get(rule)).toString());
	}

	private void initRulePriority() {
		this.rulePriority = new Hashtable<Rule, Integer>();
		for (int i = 0; i < this.rulesVec.size(); i++) {
			Rule rule = this.rulesVec.elementAt(i);
			this.rulePriority.put(rule, Integer.valueOf(rule.getPriority()));

			// Object rule = rulesVec.elementAt(i);
			// if(rule instanceof Rule)
			// rulePriority.put(rule, Integer.valueOf(((Rule) rule).getPriority()));
			// else if(rule instanceof String)
			// rulePriority.put(rule, Integer.valueOf(0));
		}
	}

	/**
	 * Returns the rule Priorities. A rule is a key, a priority is a value.
	 * 
	 * @return The rule priority.
	 */
	public Hashtable<Rule, Integer> getRulePriority() {
		return this.rulePriority;
	}

	/**
	 * Returns the highest priority of the rule priority. The highest priority
	 * means the smallest number > 0.
	 * 
	 * @return The highest priority.
	 */
	public Integer getStartPriority() {
		int startPriority = Integer.MAX_VALUE;
		Integer result = null;
		for (Enumeration<Rule> keys = this.rulePriority.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer p = this.rulePriority.get(key);
			if (p.intValue() < startPriority) {
				startPriority = p.intValue();
				result = p;
			}
		}
		return result;
	}

	/**
	 * Inverts a rule priority so that the priority is the key and the value is
	 * a set.
	 * 
	 * @return The inverted set.
	 */
	public Hashtable<Integer, HashSet<Rule>> invertPriority() {
		Hashtable<Integer, HashSet<Rule>> inverted = new Hashtable<Integer, HashSet<Rule>>();
		for (Enumeration<Rule> keys = this.rulePriority.keys(); keys.hasMoreElements();) {
			Rule key = keys.nextElement();
			Integer value = this.rulePriority.get(key);
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
	 * Returns the rule priority in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		String resultString = "Rule:\t\tPriority:\n";
		for (Enumeration<Rule> keys = this.rulePriority.keys(); keys.hasMoreElements();) {
			Rule key = keys.nextElement();
			Integer value = this.rulePriority.get(key);
			resultString += key.getName() + "\t\t" + value.toString()
					+ "    " + key.getPriority() + "\n";
		}
		return resultString;
	}
}
