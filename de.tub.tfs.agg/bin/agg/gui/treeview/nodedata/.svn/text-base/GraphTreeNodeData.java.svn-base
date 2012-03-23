// $Id: GraphTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdGraph;

/**
 * The GraphTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: GraphTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $
 */
public class GraphTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdGraph eGraph;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	public GraphTreeNodeData(EdGraph graph) {
		setGraph(graph);
	}


	public GraphTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public GraphTreeNodeData(Object obj) {
		if (obj instanceof EdGraph)
			setGraph((EdGraph) obj);	
		else if (obj instanceof String)
			new GraphTreeNodeData((String) obj);		
	}

	public void dispose() {
		this.eGraph = null;
		this.string = null;
		this.data = null;
		this.treeNode = null;
	}
	
	private void setGraph(EdGraph graph) {
		this.data = graph;
		this.string = graph.getBasisGraph().getName();
		this.eGraph = graph;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdGraph)
			setGraph((EdGraph) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.eGraph = null;
		}
		else {
			this.eGraph = null;
			this.string = null;
			this.data = null;
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
		if (!this.eGraph.getBasisGraph().getName().equals(newString)) {
			this.eGraph.getBasisGraph().setName(newString);
			this.eGraph.getGraGra().setChanged(true);
		} 
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

	public EdGraph getGraph() {
		return this.eGraph;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isGraph()
	 */
	public boolean isGraph() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = " Host graph ";
		if (this.eGraph.getBasisGraph() != null) {
			if (!this.eGraph.getBasisGraph().getTextualComment().equals(""))
				toolTipText = " " + this.eGraph.getBasisGraph().getTextualComment();
		}
		return toolTipText;
	}
	
}
