// $Id: MultiRuleTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdRule;

/**
 * The RuleTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: MultiRuleTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $
 */
public class MultiRuleTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdRule eRule;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public MultiRuleTreeNodeData(final EdRule rule) {
		setRule(rule);
	}

	public MultiRuleTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public MultiRuleTreeNodeData(Object obj) {
		if (obj instanceof EdRule)
			setRule((EdRule) obj);		
		else if (obj instanceof String) 
			new MultiRuleTreeNodeData((String) obj);
	}

	private void setRule(final EdRule rule) {
		this.data = rule;
		if (!rule.getBasisRule().isEnabled())
			this.string = "[D]" + rule.getBasisRule().getName();
		else
			this.string = rule.getBasisRule().getName();
		this.eRule = rule;		
	}
	
	public void dispose() {
		this.data = null;
		this.eRule = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdRule)
			setRule((EdRule) obj);	
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.eRule = null;
		}
		else {
			this.data = null;
			this.eRule = null;
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
		
			String newRuleName = "";
			String sD = "";
			if (!this.eRule.getBasisRule().isEnabled())
				sD = "[D]";			
			if (newString.indexOf("[D]") != -1)
				newString = newString.substring(3, newString.length());			
			newRuleName = newString;
			if (!this.eRule.getBasisRule().getName().equals(newRuleName)) {
				this.eRule.getBasisRule().setName(newRuleName);
				this.eRule.getGraGra().setChanged(true);
			}
			this.string = sD + this.eRule.getBasisRule().getName();
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		if (!this.eRule.getBasisRule().getName().equals(newString)) {
			this.eRule.getBasisRule().setName(newString);
			this.eRule.getGraGra().setChanged(true);
		}
	}

	public void setString(String tag, String tag1, String newString) {
		if (tag.equals("[]"))
			tag = "";
		if (tag1.equals("[]"))
			tag1 = "";
		this.string = tag + tag1 + newString;
		if (!this.eRule.getBasisRule().getName().equals(newString))
			this.eRule.getGraGra().setChanged(true);
		this.eRule.getBasisRule().setName(newString);
	}

	/**
	 * Returns the string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public String toString() {
		return string();
	}

	public EdRule getRule() {
		return this.eRule;
	}
	
	public EdRule getMultiRule() {
		return this.eRule;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isMultiRule()
	 */
	public boolean isMultiRule() {
		return true;
	}

	public boolean isRule() {
		return true;
	}
	
	public String getToolTipText() {
		String toolTipText = "Multi rule of rule scheme";
		if (!this.eRule.getBasisRule().getTextualComment().equals(""))
			toolTipText = " "+ this.eRule.getBasisRule().getTextualComment();
		return toolTipText;
	}
}
