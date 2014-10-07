// $Id: TypeGraphTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdGraph;
import agg.xt_basis.TypeSet;

/**
 * The GraGraTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: TypeGraphTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $
 */
public class TypeGraphTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdGraph eGraph;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	/**
	 * Creates a new type graph tree node data.
	 * 
	 * @param graph
	 *            the graph to show, if the entry is selected
	 * @param isTypeGraph
	 *            if true the graph is treted as a type graph. So another icon
	 *            will be shown for example.
	 */
	public TypeGraphTreeNodeData(EdGraph typegraph) {
		setTypeGraph(typegraph);
	}

	public TypeGraphTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public TypeGraphTreeNodeData(Object obj) {
		if (obj instanceof EdGraph)
			setTypeGraph((EdGraph) obj);
		else if (obj instanceof String)
			new TypeGraphTreeNodeData((String) obj);
	}

	public void dispose() {
		this.eGraph = null;
		this.data = null;
		this.treeNode = null;
	}
	
	private void setTypeGraph(EdGraph graph) {
		if (graph.getBasisGraph().isTypeGraph()) {
			this.data = graph;
			this.string = graph.getBasisGraph().getName();
			this.eGraph = graph;
		}
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdGraph)
			setTypeGraph((EdGraph) obj);
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

		
			String s = newString;
			String mode = "";
			if ((newString.length() >= 3)
					&& newString.substring(0, 3).equals("[D]")) {
				s = newString.substring(newString.indexOf("]") + 1, (newString
						.length()));
			} else if ((newString.length() >= 3)
					&& newString.substring(0, 3).equals("[E]")) {
				s = newString.substring(newString.indexOf("]") + 1, (newString
						.length()));
			} else if ((newString.length() >= 4)
					&& newString.substring(0, 4).equals("[Em]")) {
				s = newString.substring(newString.indexOf("]") + 1, (newString
						.length()));
			} else if ((newString.length() >= 5)) {
				if (newString.substring(0, 5).equals("[Emm]")) {
					s = newString.substring(newString.indexOf("]") + 1, (newString
						.length()));
				} else if (newString.substring(0, 5).equals("[Inh]")) {
					s = newString.substring(newString.indexOf("]") + 1, (newString
							.length()));
				}
			} 
			else {
				switch (this.eGraph.getGraGra().getBasisGraGra().getTypeSet()
						.getLevelOfTypeGraphCheck()) {
				case TypeSet.DISABLED:
					mode = "[D]";
					break;
				case TypeSet.ENABLED_INHERITANCE:
					mode = "[Inh]";
					break;	
				case TypeSet.ENABLED:
					mode = "[E]";
					break;
				case TypeSet.ENABLED_MAX:
					mode = "[Em]";
					break;
				case TypeSet.ENABLED_MAX_MIN:
					mode = "[Emm]";
					break;
				default:
					mode = "[D]";
				}
			}
			this.string = mode + newString + "    ";
			if (!this.eGraph.getBasisGraph().getName().equals(s)) {
				this.eGraph.getBasisGraph().setName(s);
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
		return this.string;
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
	
	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isTypeGraph()
	 */
	public boolean isTypeGraph() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = " Type graph with node and edge types ";
		if (!this.eGraph.getBasisGraph().getTextualComment()
				.equals(""))
			toolTipText = " "
					+ this.eGraph.getBasisGraph()
							.getTextualComment();
		return toolTipText;
	}
}
