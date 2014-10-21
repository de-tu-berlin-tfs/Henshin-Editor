// $Id: PACTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdPAC;


/**
 * The PACTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: PACTreeNodeData.java,v 1.3 2010/09/23 08:23:33 olga Exp $
 */
public class PACTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdPAC ePAC;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	public PACTreeNodeData(final EdPAC pac) {
		setPAC(pac);
	}

	private void setPAC(final EdPAC pac) {
		this.data = pac;
		if (!pac.getMorphism().isEnabled())
			this.string = "[D]" + pac.getName();
		else
			this.string = pac.getName();
		this.ePAC = pac;
	}
	
	public PACTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public PACTreeNodeData(Object obj) {
		if (obj instanceof EdPAC)
			setPAC((EdPAC) obj);
		else if (obj instanceof String)
			new PACTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.ePAC = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdPAC)
			setPAC((EdPAC) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.ePAC = null;
		}
		else {
			this.data = null;
			this.ePAC = null;
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
			String newPacName = "";
			String sD = "";
			if (!this.ePAC.getMorphism().isEnabled())
				sD = "[D]";
			if (newString.indexOf("[D]") != -1)
				newString = newString.substring(3, newString.length());
			newPacName = newString;
			if (!this.ePAC.getName().equals(newPacName)) {
				this.ePAC.setName(newPacName);
				this.ePAC.getGraGra().setChanged(true);
			}
			this.string = sD + this.ePAC.getBasisGraph().getName();
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		if (!this.ePAC.getName().equals(newString)) {
			this.ePAC.setName(newString);
			this.ePAC.getGraGra().setChanged(true);
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

	public EdPAC getPAC() {
		return this.ePAC;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isPAC()
	 */
	public boolean isPAC() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = " Positive application condition ";
		if (!this.ePAC.getMorphism().getTextualComment().equals(""))
			toolTipText = " "+ this.ePAC.getMorphism().getTextualComment();
		return toolTipText;
	}
}
