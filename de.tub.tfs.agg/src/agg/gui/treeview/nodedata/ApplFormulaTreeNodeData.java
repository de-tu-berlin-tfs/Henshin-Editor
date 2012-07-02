// $Id: ApplFormulaTreeNodeData.java,v 1.8 2010/11/11 17:19:52 olga Exp $

package agg.gui.treeview.nodedata;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;

import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdRule;
import agg.util.Pair;
import agg.xt_basis.NestedApplCond;

/**
 * The RuleAttrConditionTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: ApplFormulaTreeNodeData.java,v 1.8 2010/11/11 17:19:52 olga Exp $
 */
public class ApplFormulaTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	JLabel treeCell;
	private Object data; 
	
	private Formula f;
	private List<String> names;
	private List<Evaluable> acs;

	private boolean enabled=true;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	public ApplFormulaTreeNodeData(final String formula, 
			boolean enabled, final EdRule rule) {
		setRuleData(formula, enabled, rule);
		setFormula(formula);
	}

	public ApplFormulaTreeNodeData(final String formula, 
			boolean enabled, final EdNestedApplCond nestedAC) {
		setApplCondData(formula, enabled, nestedAC);
		setFormula(formula);
	}
	
	private void setRuleData(final String formula, 
			boolean enabled, final EdRule rule) {
		this.data = new Pair<String, Object>(formula, rule);
		this.f = rule.getBasisRule().getFormula();
		this.names = rule.getBasisRule().getNameOfEnabledACs();
		this.acs = new Vector<Evaluable>(rule.getBasisRule().getEnabledACs());
		this.enabled = enabled;
	}
	
	private void setApplCondData(final String formula, 
			boolean enabled, final EdNestedApplCond nestedAC) {
		this.data = new Pair<String, Object>(formula, nestedAC);
		this.f = nestedAC.getNestedMorphism().getFormula();
		this.names = nestedAC.getNestedMorphism().getNameOfEnabledACs();
		this.acs = new Vector<Evaluable>(nestedAC.getNestedMorphism().getEnabledACs());
		this.enabled = enabled;
	}
	
	private void setFormula(final String formula) {
		if (!this.enabled)
			this.string = "[D]" + formula;
		else
			this.string = formula;
		
		replace();
	}
	
	public ApplFormulaTreeNodeData(final String s) {
		this.data = s;
		setFormula(s);
	}

	public ApplFormulaTreeNodeData(final Object obj) {
		this.data = obj;
		if (obj instanceof String)
			setFormula((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			replace();
		}
		else {
			this.data = null;
			this.string = null;		
		}
	}

	public Object getData() {
		return this.data;
	}
		
	/**
	 * Do not use this method to set the string to display for this object,
	 * do use <code>update()<\code> instead.
	 */
	public void setString(String str) {
	}

	/**
	 * Do not use this method to set the string to display for this object,
	 * do use <code>update()<\code> instead.
	 */
	public void setString(String tag, String newString) {
	}

	/**
	 * Returns the string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public void update() {
		if (this.data instanceof Pair<?, ?>) {
			String fstr = (String) ((Pair<?,?>)this.data).first;
			if (((Pair<?,?>)this.data).second instanceof EdRule) {
				EdRule r = (EdRule) ((Pair<?,?>)this.data).second;
				this.names = r.getBasisRule().getNameOfEnabledACs();
				this.acs = new Vector<Evaluable>(r.getBasisRule().getEnabledACs());
				if (((EdRule)((Pair<?,?>)this.data).second).getBasisRule().refreshFormula(this.acs)) {
					fstr = ((EdRule)((Pair<?,?>)this.data).second).getBasisRule().getFormulaStr();
					this.data = new Pair<String, Object>(fstr, r);
					this.enabled = r.getBasisRule().isEnabled();
				}
			} else if (((Pair<?,?>)this.data).second instanceof EdNestedApplCond) {
				EdNestedApplCond ac = (EdNestedApplCond) ((Pair<?,?>)this.data).second;
				this.names = ac.getNestedMorphism().getNameOfEnabledACs();
				this.acs = new Vector<Evaluable>(ac.getNestedMorphism().getEnabledACs());
				if (((NestedApplCond)((EdNestedApplCond)((Pair<?,?>)this.data).second).getMorphism()).refreshFormula(this.acs)) {
					fstr = ((NestedApplCond)((EdNestedApplCond)((Pair<?,?>)this.data).second).getMorphism()).getFormulaText();
					this.data = new Pair<String, Object>(fstr, ac);
					this.enabled = ac.getNestedMorphism().isEnabled();
				}
			}
		}
		
		if (!this.enabled)
			this.string = "[D]" + (String)((Pair<?,?>)this.data).first;
		else
			this.string = (String)((Pair<?,?>)this.data).first;
				
		replace();
	}

	public String toString() {
		return this.string;
	}

	public EdRule getRule() {
		if (((Pair<?,?>)this.data).second instanceof EdRule)
			return (EdRule)((Pair<?,?>)this.data).second;
		else
			return null;
	}

	public EdNestedApplCond getNestedAC() {
		if (((Pair<?,?>)this.data).second instanceof EdNestedApplCond)
			return (EdNestedApplCond)((Pair<?,?>)this.data).second;
		else
			return null;
	}
	
	public Formula getFormula() {
		return this.f;
	}
	
	public boolean isApplFormula() {
		return true;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	private void replace() {		
		if (this.string != null) {
			String outStr = this.f.getAsString(this.acs, this.names);
			this.string = outStr;
		}		
	}
	
	public String getToolTipText() {
		return " Formula above General Application Conditions ";
	}
	
	public boolean isTreeTextEditable() {
		return false;
	}
	
}
