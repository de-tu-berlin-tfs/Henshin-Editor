// $Id: RuleSchemeTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;

/**
 * The RuleTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: RuleSchemeTreeNodeData.java,v 1.4 2010/09/23 08:23:32 olga Exp $
 */
public class RuleSchemeTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdRuleScheme eRuleScheme;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public RuleSchemeTreeNodeData(final EdRuleScheme ruleScheme) {
		setRuleScheme(ruleScheme);
	}

	public RuleSchemeTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public RuleSchemeTreeNodeData(Object obj) {
		if (obj instanceof EdRuleScheme)
			setRuleScheme((EdRuleScheme) obj);		
		else if (obj instanceof String) 
			new RuleSchemeTreeNodeData((String) obj);
	}

	private void setRuleScheme(final EdRuleScheme ruleScheme) {
		this.data = ruleScheme;
		if (!ruleScheme.getBasisRule().isEnabled())
			this.string = "[D]" + ruleScheme.getBasisRuleScheme().getSchemeName();
		else
			this.string = ruleScheme.getBasisRuleScheme().getSchemeName();
		this.eRuleScheme = ruleScheme;		
	}
	
	public void dispose() {
		this.data = null;
		this.eRuleScheme = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdRuleScheme)
			setRuleScheme((EdRuleScheme) obj);	
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.eRuleScheme = null;
		}
		else {
			this.data = null;
			this.eRuleScheme = null;
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
		String oldString = this.string;
		String newString = str.replaceAll(" ", "");
		this.string = newString;
		
			String newRuleName = "";
			String sD = "";
			if (!this.eRuleScheme.getBasisRule().isEnabled())
				sD = "[D]";
			String sL = "[" + this.eRuleScheme.getBasisRule().getLayer() + "]";
			if (oldString.indexOf(sL) == -1)
				sL = "";
			if (newString.indexOf("[D]") != -1)
				newString = newString.substring(3, newString.length());
			String testL = "[" + this.eRuleScheme.getBasisRule().getLayer() + "]";
			if (newString.indexOf(testL) != -1)
				newString = newString.substring(testL.length(), newString
						.length());
			newRuleName = newString;
			if (!this.eRuleScheme.getBasisRuleScheme().getSchemeName().equals(newRuleName)) {
				this.eRuleScheme.getBasisRuleScheme().setName(newRuleName);
				this.eRuleScheme.getGraGra().setChanged(true);
			}
			this.string = sD + sL + this.eRuleScheme.getBasisRuleScheme().getSchemeName();
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		if (!this.eRuleScheme.getBasisRuleScheme().getSchemeName().equals(newString)) {
			this.eRuleScheme.getBasisRuleScheme().setName(newString);
			this.eRuleScheme.getGraGra().setChanged(true);
		}
	}

	public void setString(String tag, String tag1, String newString) {
		if (tag.equals("[]"))
			tag = "";
		if (tag1.equals("[]"))
			tag1 = "";
		this.string = tag + tag1 + newString;
		if (!this.eRuleScheme.getBasisRuleScheme().getSchemeName().equals(newString))
			this.eRuleScheme.getGraGra().setChanged(true);
		this.eRuleScheme.getBasisRuleScheme().setName(newString);
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

	public EdRuleScheme getRuleScheme() {
		return this.eRuleScheme;
	}

	public EdRule getRule() {
		return this.eRuleScheme;
	}
	
 	public void setTreeNode(DefaultMutableTreeNode node) {
 		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isRuleScheme()
	 */
	public boolean isRuleScheme() {
		return true;
	}
	
	public boolean isRule() {
		return true;
	}
	
	public String getToolTipText() {
		String toolTipText = "";
		if (!this.eRuleScheme.getKernelRule().isApplicable())
			toolTipText = " Rule scheme isn't applicable because of non-applicable kernel rule";
		else
			toolTipText = " Rule scheme ";

		if (!this.eRuleScheme.getBasisRule().getTextualComment().equals("")) {
			toolTipText = " " + this.eRuleScheme.getBasisRule().getTextualComment();
		}
		return toolTipText;
	}
	
}
