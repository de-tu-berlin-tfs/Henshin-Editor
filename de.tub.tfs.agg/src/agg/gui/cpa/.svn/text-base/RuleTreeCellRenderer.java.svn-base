package agg.gui.cpa;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import agg.gui.IconResource;

/**
 * The renderer supports some user friendly items for a tree. So this class
 * tells a rule tree to display the AGG typical items.
 * 
 * @version $Id: RuleTreeCellRenderer.java,v 1.4 2010/09/23 08:18:49 olga Exp $
 * @author $Author: olga $
 */
@SuppressWarnings("serial")
public class RuleTreeCellRenderer extends DefaultTreeCellRenderer {

	ImageIcon gragra, rule, nac;

	/**
	 * Creates a new renderer and sets all the important images for the tree.
	 */
	public RuleTreeCellRenderer() {
		this.gragra = IconResource.getIconFromURL(IconResource.getURLGraGra());
		this.rule = IconResource.getIconFromURL(IconResource.getURLRule());
		this.nac = IconResource.getIconFromURL(IconResource.getURLNAC());
	}

	/**
	 * Returns a little picture for the rule tree.
	 * 
	 * @param tree
	 *            The customized tree.
	 * @param value
	 *            The value of a node of the tree.
	 * @param sel
	 *            true if the node is selected.
	 * @param expanded
	 *            true if the node is expanded.
	 * @param leaf
	 *            true if the node is a leaf.
	 * @param row
	 *            The index of the row of the node.
	 * @param focus
	 *            true if the node has the focus.
	 * @return The little picture for the node.
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean focus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded,
				leaf, row, focus);

		if (isRoot(value)) {
			setIcon(this.gragra);
		} else if (isRule(value)) {
			setIcon(this.rule);
		} else if (isNAC(value)) {
			setIcon(this.nac);
		}
		return this;
	}

	private boolean isRoot(Object o) {
		if (o instanceof RuleModel.TreeData)
			return ((RuleModel.TreeData) o).isRoot();
		
		return false;
	}

	private boolean isRule(Object o) {
		if (o instanceof RuleModel.TreeData)
			return ((RuleModel.TreeData) o).isRule();
		
		return false;
	}

	private boolean isNAC(Object o) {
		if (o instanceof RuleModel.TreeData)
			return ((RuleModel.TreeData) o).isNAC();
		
		return false;
	}

}
/*
 * $Log: RuleTreeCellRenderer.java,v $
 * Revision 1.4  2010/09/23 08:18:49  olga
 * tuning
 *
 * Revision 1.3  2010/03/08 15:41:21  olga
 * code optimizing
 *
 * Revision 1.2  2008/11/13 08:26:21  olga
 * some tests
 *
 * Revision 1.1  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.3  2007/11/05 09:18:21  olga
 * code tuning
 *
 * Revision 1.2  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:50 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:48 shultzke API fertig
 * 
 * Revision 1.1.2.2 2000/07/09 17:12:42 shultzke grob die GUI eingebunden
 * 
 */
