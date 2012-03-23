// $Id: RuleSequenceTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;


import javax.swing.tree.DefaultMutableTreeNode;

import agg.ruleappl.RuleSequence;


/**
 * The RuleTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: RuleSequenceTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $
 */
public class RuleSequenceTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private RuleSequence ruleSequence;

	/** Value to display. */
	private String string = "RuleSequence";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public RuleSequenceTreeNodeData(RuleSequence sequence) {
		setRuleSequence(sequence);
	}

	public RuleSequenceTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public RuleSequenceTreeNodeData(Object obj) {
		if (obj instanceof RuleSequence)
			setRuleSequence((RuleSequence) obj);		
		else if (obj instanceof String) 
			new RuleSequenceTreeNodeData((String) obj);
	}

	private void setRuleSequence(RuleSequence sequence) {
		this.data = sequence;	
		this.ruleSequence = sequence;
		this.string = this.ruleSequence.getName();	
	}
	
	public void dispose() {
		this.data = null;
		this.ruleSequence = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof RuleSequence)
			setRuleSequence((RuleSequence) obj);	
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.ruleSequence = null;
		}
		else {
			this.data = null;
			this.ruleSequence = null;
			this.string = null;		
		}
	}

	public Object getData() {
		return this.data;
	}
		
	/**
	 * Sets the string to display for this object.
	 */
	public void setString(String str) {
		if (str == null) {
			return;
		}
		String newString = str.replaceAll(" ", "");
		this.string = newString;
		if (!this.ruleSequence.getName().equals(this.string))
			this.ruleSequence.setName(this.string);
	}

	public void setString(String tag, String str) {
		if (str == null) {
			return;
		}
		String newString = str.replaceAll(" ", "");
		this.string = newString;
		if (!this.ruleSequence.getName().equals(this.string))
			this.ruleSequence.setName(this.string);
	}

	public void setString(String tag, String tag1, String str) {
		if (str == null) {
			return;
		}
		String newString = str.replaceAll(" ", "");
		this.string = newString;
		if (!this.ruleSequence.getName().equals(this.string))
			this.ruleSequence.setName(this.string);
	}

	/**
	 * Returns the string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public String toString() {
		return this.string;
	}

	
 	public void setTreeNode(DefaultMutableTreeNode node) {
 		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isRuleSequence()
	 */
	public boolean isRuleSequence() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#getRuleSequence()
	 */
	public RuleSequence getRuleSequence() {
		return this.ruleSequence;
	}
	
	
	public String getToolTipText() {
		if (this.ruleSequence.isChecked())
			return this.ruleSequence.getText();
		else
			return this.ruleSequence.getToolTipText();
	}
}
