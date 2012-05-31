/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Olga Runge<p>
 * Company:      TU Berlin<p>
 * @author Olga Runge
 * @version 1.0
 */
package agg.editor.impl;

import agg.cons.AtomApplCond;

public class EdAtomApplCond {

	private AtomApplCond atomApplCond;

	private EdRule rule;

	private String name;

	public EdAtomApplCond(String n, EdRule rule, AtomApplCond atomCond) {
		this.name = n;
		this.rule = rule;
		this.atomApplCond = atomCond;
	}

	public void dispose() {
		this.rule = null;
		this.atomApplCond = null;
//		System.out.println("EdAtomApplCond.dispose()  DONE  "+this.hashCode());
	}
	
	public void finalize() {
//		System.out.println("EdAtomApplCond.finalize()  called  "+this.hashCode());
	}
	
	public String getName() {
		return this.name;
	}

	public EdRule getRule() {
		return this.rule;
	}

	public AtomApplCond getAtomApplCond() {
		return this.atomApplCond;
	}

}
