package agg.cons;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

//import com.objectspace.jgl.HashSet;

/**
 * Constraint priority is a set of priorities of a given graph grammar. The set
 * is backed by a hash table.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class ConstraintPriority {

	Hashtable<Object, Object> constraintPriority;

	Enumeration<Formula> constraints;

	Vector<Formula> constraintsVec;

	/**
	 * Creates a new set of constraint priorities for a given priority graph
	 * grammar.
	 * 
	 * @param constraints
	 *            The constraints of a graph grammar.
	 */
	public ConstraintPriority(Enumeration<Formula> constraints) {
		this.constraints = constraints;
		this.constraintsVec = new Vector<Formula>(0);
		while (constraints.hasMoreElements()) {
			this.constraintsVec.addElement(constraints.nextElement());
		}
		initConstraintPriority();
	}

	public ConstraintPriority(List<Formula> constraints) {
		this.constraintsVec = new Vector<Formula>(0);
		for (int i=0; i<constraints.size(); i++) {
			this.constraintsVec.add(constraints.get(i));
		}
		this.constraints = this.constraintsVec.elements();
		initConstraintPriority();
	}
	
	public ConstraintPriority(Vector<Formula> constraints) {
		this.constraintsVec = constraints;
		this.constraints = this.constraintsVec.elements();
		initConstraintPriority();
	}

	/** Sets the Priority of the specified constraint */
	public void addPriority(Formula constraint, int prior) {
		constraint.addPriority(prior);
		this.constraintPriority.put(constraint, constraint.getPriority());
		// System.out.println("constraint prior: "+((Integer)
		// this.constraintPriority.get(rule)).toString());

	}

	private void initConstraintPriority() {
		this.constraintPriority = new Hashtable<Object, Object>();
		for (int i = 0; i < this.constraintsVec.size(); i++) {
			Object constraint = this.constraintsVec.elementAt(i);
			if (constraint instanceof Formula) {
				Vector<Integer> prior = ((Formula) constraint).getPriority();
				if (prior != null) {
					this.constraintPriority.put(constraint, prior);
				}
			} else if (constraint instanceof String)
				this.constraintPriority.put(constraint, Integer.valueOf(0));
		}
	}

	/**
	 * Returns the constraint (formula) priority. The key is a constraint,
	 * priority is a priority.
	 */
	public Hashtable<Object, Object> getConstraintPriority() {
		return this.constraintPriority;
	}

	/**
	 * Returns the smallest priority of the constraint.
	 */
	public Integer getStartPriority() {
		int startPrior = Integer.MAX_VALUE;
		Integer result = null;
		for (Enumeration<?> keys = this.constraintPriority.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Vector<?> prior = (Vector<?>) this.constraintPriority.get(key);
			if (prior != null) {
				if (prior.isEmpty()) {
					startPrior = 1;
					result = Integer.valueOf(1);
				} else {
					Integer p = (Integer) prior.get(0);
					if (p.intValue() < startPrior) {
						startPrior = p.intValue();
						result = p;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Inverts and returns constraint priorities so that the key is a priority
	 * and the value is a set of constraints.
	 */
	public Hashtable<Integer, HashSet<Object>> invertPriority() {
		Hashtable<Integer, HashSet<Object>> inverted = new Hashtable<Integer, HashSet<Object>>();
		for (Enumeration<Object> keys = this.constraintPriority.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Vector<?> prior = (Vector<?>) this.constraintPriority.get(key);
			if (prior != null) {
				Integer p = Integer.valueOf(1);
				if (!prior.isEmpty())
					p = (Integer) prior.get(0);
				HashSet<Object> invertedValue = inverted.get(p);
				if (invertedValue == null) {
					invertedValue = new HashSet<Object>();
					invertedValue.add(key);
					inverted.put(p, invertedValue);
				} else {
					invertedValue.add(key);
				}
			}
		}
		return inverted;
	}

	/**
	 * Returns the constraint priority in a human readable way.
	 */
	public String toString() {
		String resultString = "Formula:\t\tPriority:\n";
		for (Enumeration<Object> keys = this.constraintPriority.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			resultString += ((Formula) key).getName() + "\t\t";
			Vector<?> valueVec = (Vector<?>) this.constraintPriority.get(key);
			for (int i = 0; i < valueVec.size(); i++) {
				Integer value = (Integer) valueVec.get(i);
				resultString += value.toString() + "  ";
			}
			resultString += "\n";
		}
		return resultString;
	}
}
