// $Id: ConstraintTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $

package agg.gui.treeview.nodedata;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import agg.editor.impl.EdConstraint;

/**
 * The ConstraintTreeNodeData implements an user object of a tree node in the
 * GraGraTreeModel.
 * 
 * @author $Author: olga $
 * @version $Id: ConstraintTreeNodeData.java,v 1.3 2010/08/24 21:35:50 olga Exp $
 */
public class ConstraintTreeNodeData extends GraGraTreeNodeDataAdapter {

	
	private Object data;
	
	private EdConstraint eConstraint;

	/** Value to display. */
	private String string = "";

	/** My tree node in a tree */
	private DefaultMutableTreeNode treeNode;

	public ConstraintTreeNodeData(final EdConstraint c) {
		setConstraint(c);
	}

	private void setConstraint(final EdConstraint c) {
		this.data = c;
		if (!c.getBasisConstraint().isEnabled())
			this.string = "[D]" + c.getBasisConstraint().getName();
		else
			this.string = c.getName();
		this.eConstraint = c;
	}
	
	public ConstraintTreeNodeData(final String s) {
		this.data = s;
		this.string = s;
	}

	public ConstraintTreeNodeData(final Object obj) {
		if (obj instanceof EdConstraint)
			setConstraint((EdConstraint) obj);		
		else if (obj instanceof String)
			new ConstraintTreeNodeData((String) obj);
	}

	public void dispose() {
		this.data = null;
		this.eConstraint = null;
		this.string = null;
		this.treeNode = null;
	}
	
	/* Set this.data object of this tree node this.data */
	public void setData(final Object obj) {
		if (obj instanceof EdConstraint)
			setConstraint((EdConstraint) obj);
		else if (obj instanceof String) {
			this.string = (String) obj;
			this.data = obj;
		}
		else {
			this.data = null;
			this.eConstraint = null;
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
		
			String s = newString;
			String sD = "";
			if (!this.eConstraint.getBasisConstraint().isEnabled())
				sD = "[D]";
			if (newString.indexOf("[D]") != -1)
				newString = newString.substring(3, newString.length());			
			if ((newString.indexOf("[") == 0) && (newString.indexOf("]") != -1))
				s = newString.substring(newString.indexOf("]") + 1, (newString
						.length()));			
			String sL = "";
			Vector<Integer> layer = this.eConstraint.getBasisConstraint().getLayer();
			if (this.eConstraint.getGraGra().getBasisGraGra().isLayered()
					&& !layer.isEmpty()) {				
				sL = "[";
				for (int k = 0; k < layer.size(); k++) {
					int l = layer.get(k).intValue();
					sL = sL + String.valueOf(l);
					if (k < layer.size() - 1)
						sL = sL + ",";
				}				
				sL = sL + "]";				
			}
			if (!this.eConstraint.getName().equals(s)) {
				this.eConstraint.setName(s);
				this.eConstraint.getGraGra().setChanged(true);
			}
			this.string = sD + sL + s;
	}

	public void setString(String tag, String newString) {
		if (tag.equals("[]"))
			tag = "";
		this.string = tag + newString;
		if (!this.eConstraint.getBasisConstraint().getName()
						.equals(newString)) {
			this.eConstraint.getBasisConstraint().setName(newString);
			this.eConstraint.getGraGra().setChanged(true);
		}
	}

	public void setString(String tag, String tag1, String newString) {
		if (tag.equals("[]"))
			tag = "";
		if (tag1.equals("[]"))
			tag1 = "";
		this.string = tag + tag1 + newString;
		if (!this.eConstraint.getBasisConstraint().getName().equals(newString))
			this.eConstraint.getGraGra().setChanged(true);
		this.eConstraint.getBasisConstraint().setName(newString);
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

	public EdConstraint getConstraint() {
		return this.eConstraint;
	}

	public void setTreeNode(DefaultMutableTreeNode node) {
		this.treeNode = node;
	}

	public DefaultMutableTreeNode getTreeNode() {
		return this.treeNode;
	}


	/* (non-Javadoc)
	 * @see agg.gui.treeview.GraGraTreeNodeData#isConstraint()
	 */
	public boolean isConstraint() {
		return true;
	}

	public String getToolTipText() {
		String toolTipText = "Graph constraint : " + this.eConstraint.getAsString() + " ";
		if (!this.eConstraint.getBasisConstraint().getTextualComment().equals(""))
			toolTipText = " "+ this.eConstraint.getBasisConstraint().getTextualComment();
		return toolTipText;
	}
}
