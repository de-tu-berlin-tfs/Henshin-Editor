// $Id: NestedACTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $

package agg.gui.treeview.nodedata;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdNestedApplCond;


/**
 * The PACTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: NestedACTreeNodeData.java,v 1.3 2010/09/23 08:23:32 olga Exp $
 */
public class NestedACTreeNodeData extends GraGraTreeNodeDataAdapter {
	
	private Object data;
	
	private EdNestedApplCond eAC;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	public NestedACTreeNodeData(final EdNestedApplCond ac) {
		setAC(ac);
	}

	private void setAC(final EdNestedApplCond ac) {
		this.data = ac;
		this.eAC = ac;
		if (!ac.getMorphism().isEnabled())
			this.string = "[D]" + ac.getName();
		else
			this.string = ac.getName();
	}
	
	public NestedACTreeNodeData(String s) {
		this.data = s;
		this.string = s;
	}

	public NestedACTreeNodeData(Object obj) {
		if (obj instanceof EdNestedApplCond)
			setAC((EdNestedApplCond) obj);
		else if (obj instanceof String)
			new NestedACTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.eAC = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set data object of this tree node data */
	public void setData(final Object obj) {
		if (obj instanceof EdNestedApplCond)
			setAC((EdNestedApplCond) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
			this.eAC = null;
		}
		else {
			this.data = null;
			this.eAC = null;
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
		String newName = "";
		String sD = "";
		if (!this.eAC.getMorphism().isEnabled())
			sD = "[D]";
		if (newString.indexOf("[D]") != -1)
			newString = newString.substring(3, newString.length());
		newName = newString;
		if (!this.eAC.getName().equals(newName)) {
			this.eAC.setName(newName);
			this.eAC.getGraGra().setChanged(true);
		}
		this.string = sD + this.eAC.getBasisGraph().getName();
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;		
		if (!this.eAC.getName().equals(newString)) {
			this.eAC.setName(newString);
			this.eAC.getGraGra().setChanged(true);
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

	public EdNestedApplCond getNestedAC() {
		return this.eAC;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}

	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isNestedAC()
	 */
	public boolean isNestedAC() {
		return true;
	}

	public void update() {
		if (this.eAC != null) {
			if (!this.eAC.getMorphism().isEnabled())
				this.string = "[D]" + this.eAC.getName();
			else {
				this.string = this.eAC.getName();
			}
		}
	}
	
	public String getToolTipText() {
		String toolTipText = " General Application condition ";
		if (!this.eAC.getMorphism().getTextualComment().equals(""))
			toolTipText = " "+ this.eAC.getMorphism().getTextualComment();
		return toolTipText;
	}
	
}
