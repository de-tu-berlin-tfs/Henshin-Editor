// $Id: GrammarTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdGraGra;


/**
 * The GrammarTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: GrammarTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $
 */
public class GrammarTreeNodeData extends GraGraTreeNodeDataAdapter {

	private Object data;

	private EdGraGra eGra;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public GrammarTreeNodeData(final EdGraGra gra) {
		setGrammar(gra);
	}

	public GrammarTreeNodeData(final String s) {
		this.data = s;
		this.string = s;
	}

	public GrammarTreeNodeData(final Object obj) {
		this.data = obj;
		if (obj instanceof EdGraGra)
			new GrammarTreeNodeData((EdGraGra) obj);
		else if (obj instanceof String)
			new GrammarTreeNodeData((String) obj);
	}

	private void setGrammar(final EdGraGra gra) {
		this.data = gra;
		this.string = gra.getName();
		this.eGra = gra;
	}
	
	public void dispose() {
		this.data = null;
		this.string = null;
		this.eGra = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(Object obj) {
		if (obj instanceof EdGraGra)
			setGrammar((EdGraGra) obj);			
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.eGra = null;
		}
		else {
			this.string = null;
			this.eGra = null;
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
		if (!this.eGra.getName().equals(newString)) {
			this.eGra.setName(newString);
			this.eGra.setChanged(true);
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

	public EdGraGra getGraGra() {
		return this.eGra;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isGraGra()
	 */
	public boolean isGraGra() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = "";
		if (!this.eGra.getFileName().equals("")) {
			if (this.eGra.getDirName().indexOf(File.separator) != -1)
				toolTipText = "( " + this.eGra.getDirName()
						+ this.eGra.getFileName() + " )";
			else
				toolTipText = "( " + this.eGra.getDirName()
						+ File.separator
						+ this.eGra.getFileName() + " )";
			if (!this.eGra.getBasisGraGra()
					.getTextualComment().equals(""))
				toolTipText = toolTipText
						+ "  "
						+ this.eGra.getBasisGraGra()
								.getTextualComment();
		} else {
			toolTipText = " Graph grammar ";
			if (!this.eGra.getBasisGraGra()
					.getTextualComment().equals(""))
				toolTipText = " "
						+ this.eGra.getBasisGraGra()
								.getTextualComment();
		}
		return toolTipText;
	}
}
