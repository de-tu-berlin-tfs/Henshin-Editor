// $Id: RuleApplConstraintTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdRuleConstraint;


/**
 * The RuleApplConstraintTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: RuleApplConstraintTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $
 */
public class RuleApplConstraintTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdRuleConstraint eRuleConstraint;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public RuleApplConstraintTreeNodeData(final EdRuleConstraint ruleConstr) {
		setConstraint(ruleConstr);
	}

	private void setConstraint(final EdRuleConstraint ruleConstr) {
		this.data = ruleConstr;
		this.string = "RuleConstraint";
		this.eRuleConstraint = ruleConstr;
	}
	
	public RuleApplConstraintTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public RuleApplConstraintTreeNodeData(Object obj) {
		if (obj instanceof EdRuleConstraint)
			setConstraint((EdRuleConstraint) obj);	
		else if (obj instanceof String)
			new RuleApplConstraintTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.string = null;
		this.eRuleConstraint = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdRuleConstraint)
			setConstraint((EdRuleConstraint) obj);		
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.string = null;
			this.eRuleConstraint = null;			
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

	public EdRuleConstraint getRuleConstraint() {
		return this.eRuleConstraint;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isRuleConstraint()
	 */
	public boolean isRuleConstraint() {
		return true;
	}

	public String getToolTipText() {
		return " Post application condition ";
	}
	
	public boolean isTreeTextEditable() {
		return false;
	}
}
