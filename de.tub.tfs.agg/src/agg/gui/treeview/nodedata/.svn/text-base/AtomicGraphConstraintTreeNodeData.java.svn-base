// $Id: AtomicGraphConstraintTreeNodeData.java,v 1.4 2010/08/24 21:35:50 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdAtomic;

/**
 * The AtomicGraphConstraintTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $AtomicGraphConstraintTreeNodeData.java,v 1.1 2008/10/29 09:04:10 olga Exp $
 */
public class AtomicGraphConstraintTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdAtomic eAtomic;

//	private EdAtomic firstConclusion;
	
	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	
	public AtomicGraphConstraintTreeNodeData(final EdAtomic a) {
		setAtomic(a);
	}

	private void setAtomic(final EdAtomic a) {
//		if (a.isParent()) {
//			firstConclusion = a.getConclusion(0);
//		} 
		
		this.data = a;
		this.string = a.getBasisAtomic().getAtomicName();
		this.eAtomic = a;
	}
	
	public AtomicGraphConstraintTreeNodeData(final String s) {
		this.data = s;
		this.string = s;
	}

	public AtomicGraphConstraintTreeNodeData(final Object obj) {
		if (obj instanceof EdAtomic)
			setAtomic((EdAtomic) obj);		
		else if (obj instanceof String)
			new AtomicGraphConstraintTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.eAtomic = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set this.data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdAtomic)
			setAtomic((EdAtomic) obj);		
		else if (obj instanceof String)
			this.string = (String) obj;
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
		if (!this.eAtomic.getBasisAtomic().getAtomicName().equals(newString)) {
			this.eAtomic.getBasisAtomic().setAtomicName(newString);
			for (int i = 0; i < this.eAtomic.getConclusions().size(); i++) {
				this.eAtomic.getConclusions().elementAt(i).getBasisAtomic()
						.setAtomicName(newString);
			}
			this.eAtomic.getGraGra().setChanged(true);
		}
	}

	/**
	 * Returns the string to display for this object.
	 */
	public String string() {
		return this.string;
	}

	public String toString() {
		return this.string();
	}

	public EdAtomic getAtomic() {
		return this.eAtomic;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isAtomic()
	 */
	public boolean isAtomic() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = " Atomic graph constraint ";
		if (!this.eAtomic.getBasisAtomic().getTextualComment().equals(""))
			toolTipText = " "+ this.eAtomic.getBasisAtomic().getTextualComment();
		return toolTipText;
	}
}
