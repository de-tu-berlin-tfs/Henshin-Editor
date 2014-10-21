// $Id: ConstraintPopupMenu.java,v 1.5 2010/08/23 07:34:12 olga Exp $

package agg.gui.popupmenu;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdConstraint;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

@SuppressWarnings("serial")
public class ConstraintPopupMenu extends JPopupMenu {

	public ConstraintPopupMenu(GraGraTreeView tree) {
		super("Constraint");
		this.treeView = tree;

		JMenuItem mi;

		mi = add(new JMenuItem("Select Rule Layer"));
		mi.setActionCommand("setConstraintLayer");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('L');

		addSeparator();

		mi = add(new JMenuItem("Select Rule Priority"));
		mi.setActionCommand("setConstraintPriority");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('P');

		addSeparator();

		this.checkGraph = add(new JMenuItem("Check Graph"));
		this.checkGraph.setActionCommand("checkOneConstraint");
		this.checkGraph.addActionListener(this.treeView.getActionAdapter());
		// this.checkGraph.setMnemonic('G');
		addSeparator();
		
		mi = add(new JMenuItem("Edit"));
		mi.setActionCommand("editConstraint");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('E');

		mi = add(new JMenuItem("Delete               Delete"));
		mi.setActionCommand("deleteConstraint");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('D');
		addSeparator();
		
		this.disable = new JCheckBoxMenuItem("disabled");
		this.disable.setActionCommand("disableConstraint");
		this.disable.addActionListener(this.treeView.getActionAdapter());
		add(this.disable);

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		// mi = new JMenuItem("Textual Comments");
		mi.setActionCommand("commentConstraint");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('T');

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			TreePath path = this.treeView.getTree().getPathForLocation(x, y);
			if (path.getPath().length == 3) {
				DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode
						.getUserObject();
				if (sd != null && sd.isConstraint()) {
					EdConstraint constr = sd.getConstraint();
					if (!constr.getBasisConstraint().isEnabled()) {
						this.disable.setSelected(true);
						this.checkGraph.setEnabled(false);
					} else {
						this.disable.setSelected(false);
						this.checkGraph.setEnabled(true);
					}
					return true;
				}
			}
		}
		return false;
	}

	private GraGraTreeView treeView;

	private JMenuItem disable, checkGraph;
}
