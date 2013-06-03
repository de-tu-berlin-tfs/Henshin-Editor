package agg.cons;

import java.util.Vector;


public class EvalSet implements Evaluable {
	private Vector<java.lang.Object> set;

	private int old_tick;

	private boolean old_val;

	private boolean negation;

	public EvalSet() {
		this(null);
	}

	public EvalSet(Vector<java.lang.Object> s) {
		setSet(s);
		this.old_tick = -1;
		this.old_val = false;
	}

	public void setSet(Vector<java.lang.Object> s) {
		if (s == null)
			this.set = new Vector<java.lang.Object>();
		else
			this.set = s;
	}

	public Vector<java.lang.Object> getSet() {
		return this.set;
	}

	public boolean eval(java.lang.Object o) {
		return eval(o, -1);
	}

	/* set.eval(o) := \forall s\in set. s.eval(o) */
	public boolean eval(java.lang.Object o, int tick) {
		if (tick != -1 && tick == this.old_tick)
			return this.old_val;
		this.old_tick = tick;
		this.old_val = false;
		boolean result = true;
		for (int i = 0; i < this.set.size(); i++) {
			Evaluable e = (Evaluable) this.set.get(i);
			result = ((AtomApplConstraint) e).eval(o, tick);
			if (!result)
				return false;
		}
		this.old_val = true;
		return true;
	}

	public boolean eval(java.lang.Object o, boolean negate) {
		return eval(o, -1, negate);
	}

	/* set.eval(o) := \forall s\in set. s.eval(o) */
	public boolean eval(java.lang.Object o, int tick, boolean negate) {
		if (tick != -1 && tick == this.old_tick)
			return this.old_val;
		this.old_tick = tick;
		this.old_val = false;
		boolean result = true;
		for (int i = 0; i < this.set.size(); i++) {
			Evaluable e = (Evaluable) this.set.get(i);
			result = ((AtomApplConstraint) e).eval(o, tick, negate);
			if (!result)
				return false;
		}
		this.old_val = true;
		return true;
	}

	public String getAsString() {
		return new String("");
	}

	public void setNegation(boolean b) {
		this.negation = b;
	}

	public boolean getNegation() {
		return this.negation;
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
	 */
	public boolean evalForall(Object o, int tick) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		return "EvalSet";
	}

}
