// $Id: ConclusionTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdAtomic;


/**
 * The ConclusionTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: ConclusionTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $
 */
public class ConclusionTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdAtomic eAtomic;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public ConclusionTreeNodeData(final EdAtomic a) {
		setConclusion(a);
	}

	private void setConclusion(final EdAtomic a) {
		this.data = a;
		this.string = a.getBasisAtomic().getName();
		this.eAtomic = a;
	}
	
	public ConclusionTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public ConclusionTreeNodeData(Object obj) {
		if (obj instanceof EdAtomic)
			setConclusion((EdAtomic) obj);
		else if (obj instanceof String)
			new ConclusionTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.eAtomic = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set this.data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdAtomic)
			setConclusion((EdAtomic) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.eAtomic = null;
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
		if (!this.eAtomic.getBasisAtomic().getName().equals(newString)) {
			this.eAtomic.getBasisAtomic().setName(newString);
			this.eAtomic.getGraGra().setChanged(true);
		} 
	}

	/**
	 * Returns the this.string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public String toString() {
		return this.string();
	}

	public EdAtomic getConclusion() {
		return this.eAtomic;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isConclusion()
	 */
	public boolean isConclusion() {
		return true;
	}

	public String getToolTipText() {
		return " Conclusion of atomic graph constraint ";
	}
}
