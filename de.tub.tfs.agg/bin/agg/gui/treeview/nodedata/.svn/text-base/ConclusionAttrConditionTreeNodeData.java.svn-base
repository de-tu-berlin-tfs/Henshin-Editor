// $Id: ConclusionAttrConditionTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdRule;
import agg.attribute.impl.CondMember;
import agg.util.Pair;

/**
 * The ConclusionAttrConditionTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: ConclusionAttrConditionTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $
 */
public class ConclusionAttrConditionTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private Pair<CondMember, EdRule> attrCondition;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public ConclusionAttrConditionTreeNodeData(final CondMember attrCond, final EdAtomic conclusion) {
		setAttrCondition(new Pair<CondMember, EdRule> (attrCond, conclusion));
	}

	private void setAttrCondition(final Pair<CondMember, EdRule> attrCondRulePair) {
		this.data = attrCondRulePair;
		
		if (!attrCondRulePair.first.isEnabled())
			this.string = "[D]" + attrCondRulePair.first.getExprAsText();
		else
			this.string = attrCondRulePair.first.getExprAsText();
		this.attrCondition = attrCondRulePair;
	}
	
	public ConclusionAttrConditionTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public ConclusionAttrConditionTreeNodeData(Object obj) {
		if (obj instanceof Pair) {
			Pair<?,?> p = (Pair) obj;			
			setAttrCondition(new Pair<CondMember, EdRule> ((CondMember) p.first, (EdRule) p.second));
		}
		else if (obj instanceof String)
			new ConclusionAttrConditionTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.attrCondition = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set this.data object of this tree node this.data */
	public void setData(Object obj) {
		if (obj instanceof Pair) {
			Pair<?,?> p = (Pair) obj;	
			setAttrCondition(new Pair<CondMember, EdRule> ((CondMember) p.first, (EdRule) p.second));
		}		
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.attrCondition = null;
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
		if (!this.attrCondition.first.isEnabled())
			this.string = "[D]" + this.attrCondition.first.getExprAsText();
		else
			this.string = this.attrCondition.first.getExprAsText(); 
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		this.string = tag + this.attrCondition.first.getExprAsText();
	}

	/**
	 * Returns the string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public void update() {
		if (!this.attrCondition.first.isEnabled())
			this.string = "[D]" + this.attrCondition.first.getExprAsText();
		else
			this.string = this.attrCondition.first.getExprAsText();
	}

	public String toString() {
		return this.string();
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isAttrCondition()
	 */
	public boolean isAttrCondition() {
		return true;
	}

	public Pair<CondMember, EdRule> getAttrCondition() {
		return this.attrCondition;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	public String getToolTipText() {		
		return " Attribute condition ";
	}
	
	public boolean isTreeTextEditable() {
		return false;
	}
}
