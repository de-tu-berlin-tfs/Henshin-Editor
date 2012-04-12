// $Id: NACTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdNAC;

/**
 * The NACTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: NACTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $
 */
public class NACTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdNAC eNAC;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;


	public NACTreeNodeData(final EdNAC nac) {
		setNAC(nac);
	}

	private void setNAC(final EdNAC nac) {
		this.data = nac;
		if (!nac.getMorphism().isEnabled())
			this.string = "[D]" + nac.getName();
		else
			this.string = nac.getName();
		this.eNAC = nac;
	}
	
	public NACTreeNodeData(final String s) {
		this.data = s;
		this.string = s;
	}

	public NACTreeNodeData(final Object obj) {
		if (obj instanceof EdNAC)
			setNAC((EdNAC) obj);		
		else if (obj instanceof String)
			new NACTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.eNAC = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdNAC)
			setNAC((EdNAC) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.eNAC = null;
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

			String newNacName = "";
			String sD = "";
			if (!this.eNAC.getMorphism().isEnabled())
				sD = "[D]";
			if (newString.indexOf("[D]") != -1)
				newString = newString.substring(3, newString.length());
			newNacName = newString;
			if (!this.eNAC.getName().equals(newNacName)) {
				this.eNAC.setName(newNacName);
				this.eNAC.getGraGra().setChanged(true);
			}
			this.string = sD + this.eNAC.getBasisGraph().getName();
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		if (!this.eNAC.getName().equals(newString)) {
			this.eNAC.setName(newString);
			this.eNAC.getGraGra().setChanged(true);
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

	public EdNAC getNAC() {
		return this.eNAC;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isNAC()
	 */
	public boolean isNAC() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = " Negative application condition ";
		if (!this.eNAC.getMorphism().getTextualComment().equals(""))
			toolTipText = " "+ this.eNAC.getMorphism().getTextualComment();
		return toolTipText;
	}
}
