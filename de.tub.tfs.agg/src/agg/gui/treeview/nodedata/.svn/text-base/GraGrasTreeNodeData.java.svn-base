// $Id: GraGrasTreeNodeData.java,v 1.3 2010/09/23 08:23:31 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;



/**
 * The GraGraTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: GraGrasTreeNodeData.java,v 1.3 2010/09/23 08:23:31 olga Exp $
 */
public class GraGrasTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	/**
	 * Constructs a new instance of GraGraTreeNodeData with the passed in
	 * arguments.
	 */
	public GraGrasTreeNodeData(final String name) {
		this.string = name;
	}

	public GraGrasTreeNodeData(final Object obj) {
		if (obj instanceof String)
			this.string = (String) obj;
	}

	public void dispose() {
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof String) {
			this.string = (String) obj;
		}
		else {
			this.string = null;		
		}
	}

	public Object getData() {
		return this.string;
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
		return this.string();
	}


	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}


}
