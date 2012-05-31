/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Olga Runge<p>
 * Company:      TU Berlin<p>
 * @author Olga Runge
 * @version 1.0
 */
package agg.editor.impl;

import agg.cons.EvalSet;

public class EdRuleConstraint {

	private EvalSet evalset;

	private EdRule rule;

	private String name;

	public EdRuleConstraint(String n, EdRule rule, EvalSet constraint) {
		this.name = n;
		this.rule = rule;
		this.evalset = constraint;
	}

	public void dispose() {
		this.rule = null;
		this.evalset = null;
//		System.out.println("EdRuleConstraint.dispose()  DONE  "+this.hashCode());
	}
	
	public void finalize() {
//		System.out.println("EdRuleConstraint.finalize()  called  "+this.hashCode());
	}
	
	public String getName() {
		return this.name;
	}

	public EdRule getRule() {
		return this.rule;
	}

	public EvalSet getConstraint() {
		return this.evalset;
	}

}
