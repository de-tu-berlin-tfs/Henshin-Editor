/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.editor.impl;

import java.util.List;
import java.util.Vector;

import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.util.XMLHelper;
import agg.util.XMLObject;

public class EdConstraint implements XMLObject {

	private Formula formula;

	private String fstrByIndx, fstrByNames;

	transient protected EdGraGra eGra; // my EdGraGra

	public EdConstraint(String n) {
		this(new Formula(true), n);
		this.fstrByNames = "true";
		this.fstrByIndx = "true";
	}

	public EdConstraint(Formula f, String n) {
		this.formula = f;
		this.formula.setName(n);
		this.fstrByNames = "";
		this.fstrByIndx = "";
	}

	public void dispose() {
		this.fstrByNames = "";
		this.fstrByIndx = "";
		this.formula = null;
		this.eGra = null;
	}
	
	public void finalize() {
	}
	
	public Formula getBasisConstraint() {
		return this.formula;
	}

	public void update() {
		/* We'll see if we need this method. */
	}

	public String getName() {
		return this.formula.getName();
	}

	public void setName(String n) {
		this.formula.setName(n);
	}

	public void setVarSet(List<Evaluable> v) {
		this.fstrByNames = this.formula.getAsString(v);
		this.fstrByIndx = this.fstrByNames;
	}

	public void setVarSet(List<Evaluable> v, List<String> names) {
		this.fstrByNames = this.formula.getAsString(v, names);
		this.fstrByIndx = this.formula.getAsString(v, new Vector<String>());
	}

	public EdGraGra getGraGra() {
		return this.eGra;
	}

	public void setGraGra(EdGraGra egra) {
		this.eGra = egra;
	}

	public String getAsString() {
		return this.fstrByNames;
	}

	public String getAsIndxString() {
		return this.fstrByIndx;
	}
	
	public void XreadObject(XMLHelper h) {
		if (h.peekObject(this.formula, this)) {
			String n = h.readAttr("name");
			this.formula.setName(n);
			h.close();
		}
	}

	public void XwriteObject(XMLHelper h) {
		h.openObject(this.formula, this);
		h.close();
	}

}
