/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.cons;

import java.text.StringCharacterIterator;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.io.Serializable;

import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.NestedApplCond;

@SuppressWarnings("serial")
public class Formula implements Evaluable, XMLObject, Serializable {

	public static final int NOP = 0;

	public static final int TRUE = 1;

	public static final int FALSE = 2;

	public static final int NOT = 3;

	public static final int AND = 4;

	public static final int OR = 5;

	public static final int EXISTS = 6;
	
	public static final int FORALL = 7;
	
	public static final int UNDEF = -1;

	private Evaluable sub1, sub2;

	private int op;

	public String str = "";
	
	private int running_tick;

	private String name = "";

	private boolean evaluable = true;

	private boolean enabled = true;

	private String comment = "";

	private Vector<Integer> layer = new Vector<Integer>(5);

	private Vector<Integer> priority = new Vector<Integer>(5);

	private void init(int _op, Evaluable e1, Evaluable e2) {
		this.op = _op;
		this.sub1 = e1;
		this.sub2 = e2;
	}

	public Formula(boolean v) {
		init(v ? TRUE : FALSE, null, null);
		/*
		 * _Don't_ initialize running_tick in init(). It does not belong to the
		 * Formula itself, but rather to it's sub parts. Resetting it after the
		 * constructor might make subparts return a cached result, although
		 * called with a different object.
		 */
		this.running_tick = 0;
		this.name = "Formula";
		this.evaluable = true;
		this.enabled = true;
		this.str = String.valueOf(v);
	}

	public Formula(List<Evaluable> vars, String s) {
		setFormula(vars, s);
		this.running_tick = 0;
		this.name = "Formula";
		this.evaluable = true;
		this.enabled = true;
		this.str = s;
	}

	/* Creates a formula connecting all vars with operator op. */
	public Formula(List<Evaluable> vars, int op) {
		this.running_tick = 0;
		if (op != AND && op != OR) {
			init(UNDEF, null, null);
			return;
		}
		if (vars.isEmpty()) {
			init(TRUE, null, null);
			this.str = String.valueOf(true);
		}
		else {
			String s = "1";
			for (int i = 1; i < vars.size(); i++) {
				s += (op == AND) ? " && " : " || ";
				s += Integer.toString(i + 1);
			}
			setFormula(vars, s);
			this.str = s;
		}
		this.name = "Formula";
		this.evaluable = true;
		this.enabled = true;
	}

	public void setName(String str) {
		this.name = str;
	}

	public String getName() {
		return this.name;
	}

	/** Set textual comments for this formula. */
	public void setTextualComment(String text) {
		this.comment = text;
	}

	/** Return textual comments of this formula. */
	public String getTextualComment() {
		return this.comment;
	}

	public void setEnabled(boolean b) {
		this.enabled = b;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean setFormula(List<Evaluable> vars, String s) {
//		System.out.println(s);
		Formula that = get_multiple(vars, new StringCharacterIterator(s));
		if (that != null) {
			init(that.op, that.sub1, that.sub2);
			return true;
		} 
		init(UNDEF, null, null);
		return false;
	}
	
	public String getAsString(List<Evaluable> v) {
		HashMap<Evaluable, String> m = new HashMap<Evaluable, String>();
		for (int i = 0; i < v.size(); i++) {
			m.put(v.get(i), Integer.toString(i + 1, 10));
		}
		return getAsString(v, m);
	}

	public String getAsString(List<Evaluable> v, List<String> names) {
		if (names.size() != v.size()) {
			return getAsString(v);
		}
		
		final HashMap<Evaluable, String> map = new HashMap<Evaluable, String>();
		for (int i = 0; i < v.size(); i++) {
			map.put(v.get(i), names.get(i));
		}	
		return getAsReadableString(v, map);
	}

	public String getAsReadableString(List<Evaluable> v) {
		String s1 = "";
		String s2 = "";
		switch (this.op) {
		case UNDEF:
			return "";
		case NOT:
//			return "(" + "!" + ((Formula) this.sub1).getAsReadableString(v, map) + ")";
		case FORALL:
//			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
			return "FORALL" + "( " + s1 + " )";
		case EXISTS:
//			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
			return "EXISTS" + "( " + s1 + " )";
		case FALSE:
			return "false";
		case TRUE:
			return "true";
		case AND:
		case OR:
//			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
//			s2 = ((Formula) this.sub2).getAsReadableString(v, map);
			return "(" + s1 + ((this.op == AND) ? " & " : " | ") + s2 + ")";
		case NOP:
//			if (this.sub1 instanceof Formula)
//				return "(" + ((Formula) this.sub1).getAsString(v, map) + ")";
//			String n;
//			if (!map.containsKey(this.sub1)) {
//				int i = v.size();
//				v.add(this.sub1);
//				n = Integer.toString(i + 1, 10);
//				map.put(this.sub1, n);
//			} else
//				n = map.get(this.sub1);
//			return n;
		}
		return "true";
	}
	
	private String getAsReadableString(List<Evaluable> v,
			HashMap<Evaluable, String> map) {
		String s1 = "";
		String s2 = "";
		switch (this.op) {
		case UNDEF:
			return "";
		case NOT:
			return "(" + "!" + ((Formula) this.sub1).getAsReadableString(v, map) + ")";
		case FORALL:
			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
			return "FORALL" + "( " + s1 + " )";
		case EXISTS:
			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
			return "EXISTS" + "( " + s1 + " )";
		case FALSE:
			return "false";
		case TRUE:
			return "true";
		case AND:
		case OR:
			s1 = ((Formula) this.sub1).getAsReadableString(v, map);
			s2 = ((Formula) this.sub2).getAsReadableString(v, map);
			return "(" + s1 + ((this.op == AND) ? " & " : " | ") + s2 + ")";
		case NOP:
			if (this.sub1 instanceof Formula)
				return "(" + ((Formula) this.sub1).getAsString(v, map) + ")";
			String n;
			if (!map.containsKey(this.sub1)) {
				int i = v.size();
				v.add(this.sub1);
				n = Integer.toString(i + 1, 10);
				map.put(this.sub1, n);
			} else
				n = map.get(this.sub1);
			return n;
		}
		return "true";
	}
	
	public boolean compareTo(Formula f) {
		if (!getName().equals(f.getName()))
			return false;
		Vector<Evaluable> vec = new Vector<Evaluable>();
		String form = getAsString(vec);
		String form1 = f.getAsString(vec);
		if (!form.equals(form1))
			return false;
		return true;
	}

	private boolean isValid(Evaluable e) {
		if (e == null)
			return false;
		if (!(e instanceof Formula))
			return true;
		return ((Formula) e).isValid();
	}

	public boolean isValid() {
		this.evaluable = true;
		switch (this.op) {
		case UNDEF:
			return false;
		case TRUE:
		case FALSE:
			return true;
		case NOP:
		case NOT:
		case FORALL:
		case EXISTS:
			return isValid(this.sub1);
		case AND:
		case OR:
			return (isValid(this.sub1) && isValid(this.sub2));
		}
		return false;
	}

	private void setEvaluable(Evaluable e) {
		if (e instanceof AtomConstraint) {
			this.evaluable = this.evaluable && ((AtomConstraint) e).isEvaluable();
		} else if (e instanceof Formula) {
			this.evaluable = this.evaluable && ((Formula) e).isEvaluable();
		}
		else if (e instanceof NestedApplCond) {
			this.evaluable = this.evaluable && ((NestedApplCond) e).isEvaluable();
		}
	}

	public boolean isEvaluable() {
		return this.evaluable;
	}

	public boolean eval(java.lang.Object o) {
		return eval(o, -1);
	}

	public boolean eval(java.lang.Object o, int t) {
		int tick = t;
		if (tick == -1) {
			if (this.running_tick < 0)
				this.running_tick = 0;
			tick = this.running_tick++;
		}

		switch (this.op) {
		case NOP:
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;
		case NOT: 
			if (this.sub1 != null) {
				boolean result = !this.sub1.eval(o, tick, true);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;
		case AND: 
			if ((this.sub1 != null) && (this.sub2 != null)) {
				boolean result = this.sub1.eval(o, tick) && this.sub2.eval(o, tick);
				setEvaluable(this.sub1);
				setEvaluable(this.sub2);
				return result;
			} 
			return false;
		case OR: 
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick);
				setEvaluable(this.sub1);
				if (result)
					return result;
			}
			if (this.sub2 != null) {
				boolean result = this.sub2.eval(o, tick);
				setEvaluable(this.sub2);
				if (result)
					return result;
			}
			return false;
		case FORALL: 
			if (this.sub1 != null) {				
				boolean result = this.sub1.evalForall(o, tick);
				setEvaluable(this.sub1);				
				return result;
			} 
			return false;
		case EXISTS: 
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick);
				setEvaluable(this.sub1);
				return result;
			}			
			return false;
		case TRUE:
			return true;
		case FALSE:
			return false;
		}
		return false;
	}

	public boolean eval(java.lang.Object o, boolean negation) {
		return eval(o, -1, negation);
	}

	public boolean eval(java.lang.Object o, int t, boolean negation) {
		int tick = t;
		if (tick == -1) {
			if (this.running_tick < 0)
				this.running_tick = 0;
			tick = this.running_tick++;
		}
		switch (this.op) {
		case NOP:
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick, negation);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;

		case NOT: 
			if (this.sub1 != null) {
				boolean result = !this.sub1.eval(o, tick, true);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;

		case AND:
			if ((this.sub1 != null) && (this.sub2 != null)) {
				boolean result = this.sub1.eval(o, tick, false)
						&& this.sub2.eval(o, tick, false);
				setEvaluable(this.sub1);
				setEvaluable(this.sub2);
				return result;
			} 
			return false;

		case OR:
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick, false);
				setEvaluable(this.sub1);
				if (result)
					return result;
			}
			if (this.sub2 != null) {
				boolean result = this.sub2.eval(o, tick, false);
				setEvaluable(this.sub2);
				if (result)
					return result;
			}
			return false;
		case FORALL: 
			if (this.sub1 != null) {
				boolean result = this.sub1.evalForall(o, tick);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;
		case EXISTS: 
			if (this.sub1 != null) {
				boolean result = this.sub1.eval(o, tick);
				setEvaluable(this.sub1);
				return result;
			} 
			return false;
		case TRUE:
			return true;
		case FALSE:
			return false;
		}
		return false;
	}

	/*
	 * This is used, when an Atomic is deleted, to make the formulas valid
	 * again. All occurrences of Evaluable e in the formula are patched out by a constant
	 * formula, which according to the second argument is either true or false.
	 */
	public void patchOutEvaluable(Evaluable e, boolean subst) {
		switch (this.op) {
		case AND:
		case OR:
			if (this.sub1 == e)
				this.sub1 = new Formula(subst);
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchOutEvaluable(e, subst);
			if (this.sub2 == e)
				this.sub2 = new Formula(subst);
			else if (this.sub2 != null && this.sub2 instanceof Formula)
				((Formula) this.sub2).patchOutEvaluable(e, subst);
			/* Fallthrough */
		case NOT:
			if (this.sub1 == e)
				this.sub1 = new Formula(!subst);
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchOutEvaluable(e, !subst);
		case NOP:	
		case FORALL:
		case EXISTS:
			if (this.sub1 == e)
				this.sub1 = new Formula(subst);
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchOutEvaluable(e, subst);
		}
	}

	public void patchInEvaluableAsDisjunction(Evaluable e, List<Evaluable> subst) {
		String s = "";
		if (subst.size() > 0)
			s = "1";
		for (int i=1; i<subst.size(); i++) {
			s = s + "|" + String.valueOf(i+1);
		}
//		System.out.println("Formula:  "+s);
		Formula f = new Formula(subst, s);
		switch (this.op) {
		case AND:
		case OR:
			if (this.sub1 == e)
				this.sub1 = f;
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchInEvaluableAsDisjunction(e, subst);
			if (this.sub2 == e)
				this.sub2 = f;
			else if (this.sub2 != null && this.sub2 instanceof Formula)
				((Formula) this.sub2).patchInEvaluableAsDisjunction(e, subst);
		case NOT:
			if (this.sub1 == e)
				this.sub1 = f;
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchInEvaluableAsDisjunction(e, subst);
		case NOP:	
		case FORALL:
		case EXISTS:
			if (this.sub1 == e)
				this.sub1 = f;
			else if (this.sub1 != null && this.sub1 instanceof Formula)
				((Formula) this.sub1).patchInEvaluableAsDisjunction(e, subst);
		}
	}
	
	public Formula(int _op, Evaluable e1, Evaluable e2) {
		init(_op, e1, e2);
	}

	private String getAsString(List<Evaluable> v,
			HashMap<Evaluable, String> map) {
		String s1 = "";
		String s2 = "";
		switch (this.op) {
		case UNDEF:
			return "";
		case NOT:
			return "(" + "!" + ((Formula) this.sub1).getAsString(v, map) + ")";
		case FORALL:
			s1 = ((Formula) this.sub1).getAsString(v, map);
			return "A" + "( " + s1 + " )";
		case EXISTS:
			s1 = ((Formula) this.sub1).getAsString(v, map);
			return "E" + "( " + s1 + " )";
		case FALSE:
			return "F"; //"false";
		case TRUE:
			return "T"; //"true";
		case AND:
			s1 = ((Formula) this.sub1).getAsString(v, map);
			s2 = ((Formula) this.sub2).getAsString(v, map);
			return "( " + s1 + " & " + s2 + " )";
		case OR:
			s1 = ((Formula) this.sub1).getAsString(v, map);
			s2 = ((Formula) this.sub2).getAsString(v, map);
			return "( " + s1 + " | " + s2 + " )";
		case NOP:
			if (this.sub1 instanceof Formula)
				return "(" + ((Formula) this.sub1).getAsString(v, map) + ")";
			String n;
			if (!map.containsKey(this.sub1)) {
				int i = v.size();
				v.add(this.sub1);
				n = Integer.toString(i + 1, 10);
				map.put(this.sub1, n);
			} else
				n = map.get(this.sub1);
			return n;
		}
		return "true";
	}

	private void skipWS(StringCharacterIterator i) {
		char c = i.current();
		while (c == ' ' || c == '\t' || c == '\n' || c == '\r')
			c = i.next();
	}

	private Formula get_multiple(List<Evaluable> vars,
			StringCharacterIterator i) {
		Formula ret = get_one(vars, i);
		while (ret != null) {
			char c = i.current();
			if (c == CharacterIterator.DONE) {
				break;
			} 
			else if (c == '&' || c == '|') {
				while (i.current() == c)
					i.next();
				Formula f2 = get_one(vars, i);
				if (f2 == null)
					ret = null;
				else
					ret = new Formula(c == '&' ? AND : OR, ret, f2);
			} 
			else if (c == 'A') {
				i.next(); // "("
//				i.next(); // "$"
//				i.next(); // ","
				c = i.next(); 
				Formula f = get_one(vars, i);
				if (f == null)
					return null;
				return new Formula(FORALL, f, null);
			} 
			else if (c == 'E') {
				i.next(); // "("
//				i.next(); // "$"
//				i.next(); // ","
				c = i.next();
				Formula f = get_one(vars, i);
				if (f == null)
					return null;
				return new Formula(EXISTS, f, null);
			} 
			else if (c == 'T') {
				return get_one(vars, i);
			} 
			else if (c == 'F') {
				return get_one(vars, i);
			} 
			else if (c == ')') {
				c = i.next();
				skipWS(i);
				break;
			} else {
				skipWS(i);
			}
		}
		return ret;
	}

	private Formula get_one(List<Evaluable> vars, StringCharacterIterator i) {
		skipWS(i);
		char c = i.current();
		if (c == '(') {
			c = i.next();
			if (c == CharacterIterator.DONE)
				return null;
			Formula f = get_multiple(vars, i);
			if (i.current() == ')') 
				c = i.next();
			return f;
		} else if (c == '!') {
			c = i.next();
			Formula f = get_one(vars, i);
			if (f == null)
				return null;
			return new Formula(NOT, f, null);
		} 
		else if (c == 'A') {
			i.next(); // "("
//			skipWS(i);
//			i.next(); // "$"
//			skipWS(i);
//			i.next(); // ","
//			skipWS(i);
			c = i.next(); 
			Formula f1 = get_one(vars, i);
			if (f1 == null)
				return null;
			return new Formula(FORALL, f1, null);
		} 
		else if (c == 'E') {
			i.next(); // "("
//			skipWS(i);
//			i.next(); // "$"
//			skipWS(i);
//			i.next(); // ","
//			skipWS(i);
			c = i.next();
			Formula f1 = get_one(vars, i);
			if (f1 == null)
				return null;
			return new Formula(EXISTS, f1, null);
		}
		else if (c == 'T') {
			c = i.next(); 
			return new Formula(true);
		} 
		else if (c == 'F') {
			c = i.next(); 
			return new Formula(false);
		}
		else if (c >= '0' && c <= '9') {
			int v = 0;
			while (c >= '0' && c <= '9') {
				v = v * 10 + (c - '0');
				c = i.next();
			}
			skipWS(i);
			v--;
			if (v < 0 || v >= vars.size())
				return null;
			return new Formula(NOP, vars.get(v), null);
		} else if (c == 'f' || c == 't') {
			while (i.current() >= 'a' && i.current() <= 'z')
				i.next();
			skipWS(i);
			return new Formula(c == 't');
		} else
			return null;
	}

	public static List<Integer> getFromStringAboveList(String s,  final List<String> list) {
		final List<Integer> result = new Vector<Integer>(2);
		final List<String> edit = new Vector<String>(5,2);
		StringCharacterIterator i = new StringCharacterIterator(s);
		char c = i.current();
		while (c != CharacterIterator.DONE) {
			if (c == '&' || c == '|' 
					|| c == '!' || c == '$'
					|| c == 'A' || c == 'E' 
					|| c == ' ' || c == ',' 
					|| c == '(' || c == ')') {
				edit.add(String.valueOf(c));
				i.next();
			} else if (c >= '0' && c <= '9') {
				String cs = "";
				int v = 0;
				while (c >= '0' && c <= '9') {
					cs = cs.concat(String.valueOf(c));
					v = v * 10 + (c - '0');
					c = i.next();
				}
				v--;
				if (v < 0 || v >= list.size())
					return result;
				edit.add(String.valueOf(cs));
			} else if (c == 'f' || c == 't') {
				String cs = "";
				while (i.current() >= 'a' && i.current() <= 'z') {
					cs = cs.concat(String.valueOf(c));
					i.next();
				}
				edit.add(String.valueOf(cs));
			}
			c = i.current();			
		}
		for (int j=0; j<edit.size(); j++) {
			try {
				result.add(Integer.valueOf(edit.get(j)));
			} catch (Exception ex) {}
		}
		return result;
	}
	
	public int getOperation() {
		return this.op;
	}

	public Evaluable getFirst() {
		return this.sub1;
	}
	
	public Evaluable getSecond() {
		return this.sub2;
	}
	
	public boolean isNOT(Evaluable var, Vector<Evaluable> vars) {
		boolean b = false;
		switch (this.op) {
		case UNDEF:
			return false;
		case FALSE:
			return false;
		case TRUE:
			return false;
		case NOT:
		case FORALL:
		case EXISTS:
			b = ((Formula) this.sub1).isNOT(var, vars);
			return b;
		case AND:
		case OR:
			b = ((Formula) this.sub1).isNOT(var, vars);
			if (!b)
				b = ((Formula) this.sub2).isNOT(var, vars);
			return b;
		case NOP:
			if (this.sub1 instanceof Formula)
				b = ((Formula) this.sub1).isNOT(var, vars);
			else if (vars.contains(this.sub1) && this.sub1 == var) {
				b = true;
			}
			return b;
		}
		return b;
	}

	/**
	 * Returns my layer. The layer is used by layered grammar.
	 */
	public Vector<Integer> getLayer() {
		return this.layer;
	}

	public String getLayerAsString() {
		String str = "";
		for (int k = 0; k < this.layer.size(); k++) {
			int l = this.layer.get(k).intValue();
			str = str + String.valueOf(l);
			if (k < this.layer.size() - 1)
				str = str + ",";
		}
		return str;
	}

	/**
	 * Add the specified layer l to its layer container.
	 */
	public void addLayer(int l) {
		boolean added = false;
		for (int i = 0; i < this.layer.size(); i++) {
			if (l <= this.layer.get(i).intValue()) {
				this.layer.add(i, new Integer(l));
				added = true;
				break;
			}
		}
		if (!added)
			this.layer.add(new Integer(l));
	}

	/**
	 * Set the specified Vector l to its layer container. An element of this
	 * Vector is an Integer..
	 */
	public void setLayer(Vector<Integer> l) {
		this.layer = l;
	}

	/**
	 * Returns my priority. The layer is used by grammar with rule priority.
	 */
	public Vector<Integer> getPriority() {
		return this.priority;
	}

	public String getPriorityAsString() {
		String str = "";
		for (int k = 0; k < this.priority.size(); k++) {
			int l = this.priority.get(k).intValue();
			str = str + String.valueOf(l);
			if (k < this.priority.size() - 1)
				str = str + ",";
		}
		return str;
	}

	/**
	 * Add the specified priority p to its priority container.
	 */
	public void addPriority(int p) {
		boolean added = false;
		for (int i = 0; i < this.priority.size(); i++) {
			if (p <= this.priority.get(i).intValue()) {
				this.priority.add(i, new Integer(p));
				added = true;
				break;
			}
		}
		if (!added)
			this.priority.add(new Integer(p));
	}

	/**
	 * Set the specified Vector p to its priority container. An element of this
	 * Vector is an Integer.
	 */
	public void setPriority(Vector<Integer> p) {
		this.priority = p;
	}

	@SuppressWarnings("unused")
	public void XreadObject(XMLHelper h) {
		if (h.isTag("Formula", this)) {
			
			this.name = h.readAttr("name");
			String str = h.readAttr("comment");
			if (!str.equals(""))
				this.comment = str;
			Object attr_enabled = h.readAttr("enabled");
			if ((attr_enabled != null)
					&& ((String) attr_enabled).equals("false"))
				this.enabled = false;
			else
				this.enabled = true;
			
			if (this.layer == null)
				this.layer = new Vector<Integer>(5);
			else
				this.layer.clear();
			// read layer
			if (h.readSubTag("Layer")) {
				String l = h.readAttr("Layer");	
				String size = h.readAttr("Size");
				if (!"".equals(l)) {
					l = l.replaceAll(" ", "");
					if (l.indexOf("[") >= 0)
						l = l.substring(1);
					if (l.indexOf("]") >= 0)
						l = l.substring(0, l.length()-1);
					String[] array = l.split(",");
					
					for (int i = 0; i < array.length; i++) {
						try {
							this.layer.add(new Integer(array[i]));
						} catch(java.lang.NumberFormatException ex) {}
					}
				}
				h.close();
			}

			if (this.priority == null)
				this.priority = new Vector<Integer>(5);
			else
				this.priority.clear();
			// read priority
			if (h.readSubTag("Priority")) {
				String p = h.readAttr("Priority");
				String size = h.readAttr("Size");
				if (!"".equals(p)) {
					p = p.replaceAll(" ", "");
					if (p.indexOf("[") >= 0)
						p = p.substring(1);
					if (p.indexOf("]") >= 0)
						p = p.substring(0, p.length()-1);
					String[] array = p.split(",");
					
					for (int i = 0; i < array.length; i++) {
						try {
							this.priority.add(new Integer(array[i]));
						} catch(java.lang.NumberFormatException ex) {}
					}
				}
				h.close();
			}

			h.close();
		}
	}

	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Formula", this);

		h.addAttr("name", getName());
		h.addAttr("comment", this.comment);		
		h.addAttr("enabled", String.valueOf(this.enabled));
		
		// write layer
		h.openSubTag("Layer");
		if (this.layer.size() == 0) {
			h.addAttr("Layer", "");
		}
		else if (this.layer.size() == 1) {
			String l = this.layer.get(0).toString();
			h.addAttr("Layer", l);
		} 
		else {
			h.addAttr("Layer", this.layer.toString());
		}
		h.addAttr("Size", String.valueOf(this.layer.size()));
		h.close();

		// write priority
		h.openSubTag("Priority");
		if (this.priority.size() == 0) {
			h.addAttr("Priority", "");
		}
		else if (this.priority.size() == 1) {
			String p = this.priority.get(0).toString();
			h.addAttr("Priority", p);
		}
		else {
			h.addAttr("Priority", this.priority.toString());
		}
		h.addAttr("Size", String.valueOf(this.priority.size()));		
		h.close();

		h.close();
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
	 */
	public boolean evalForall(Object o, int t) {
		int tick = t;
		if (tick == -1) {
			if (this.running_tick < 0)
				this.running_tick = 0;
			tick = this.running_tick++;
		}
		switch (this.op) {
		case NOP: 
			if (this.sub1 != null) {
				if (this.sub1 instanceof NestedApplCond) {
					((NestedApplCond) this.sub1).forall = true;
				}
				
				boolean result = this.sub1.eval(o, tick);
				setEvaluable(this.sub1);
				
				if (this.sub1 instanceof NestedApplCond) {
					((NestedApplCond) this.sub1).forall = false;
				}
				
				return result;
			} 
			return false;
		}
		return false;
	}

	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.layer.trimToSize();
		this.priority.trimToSize();
	}
}
