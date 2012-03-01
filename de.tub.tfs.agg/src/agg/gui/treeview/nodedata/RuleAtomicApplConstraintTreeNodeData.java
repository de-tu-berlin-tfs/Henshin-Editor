// $Id: RuleAtomicApplConstraintTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdAtomApplCond;


/**
 * The RuleAtomicApplConstraintTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: RuleAtomicApplConstraintTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $
 */
public class RuleAtomicApplConstraintTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdAtomApplCond eAtomApplCond;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public RuleAtomicApplConstraintTreeNodeData(final EdAtomApplCond cond) {
		setConstraint(cond);
	}

	private void setConstraint(final EdAtomApplCond cond) {
		this.data = cond;
		this.string = "AtomApplCond";
		this.eAtomApplCond = cond;
	}
	
	public RuleAtomicApplConstraintTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public RuleAtomicApplConstraintTreeNodeData(final Object obj) {
		if (obj instanceof EdAtomApplCond)
			setConstraint((EdAtomApplCond) obj);
		else if (obj instanceof String)
			new RuleAtomicApplConstraintTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.string = null;
		this.eAtomApplCond = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdAtomApplCond)
			setConstraint((EdAtomApplCond) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.string = null;
			this.eAtomApplCond = null;		
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

	public EdAtomApplCond getAtomApplCond() {
		return this.eAtomApplCond;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isAtomApplCond()
	 */
	public boolean isAtomApplCond() {
		return true;
	}

	public String getToolTipText() {
		return " Atomic post application condition ";
	}
	
	public boolean isTreeTextEditable() {
		return false;
	}
}
