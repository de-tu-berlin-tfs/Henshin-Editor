// $Id: AtomicPopupMenu.java,v 1.4 2010/08/23 07:34:12 olga Exp $

package agg.gui.popupmenu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

@SuppressWarnings("serial")
public class AtomicPopupMenu extends JPopupMenu {

	public AtomicPopupMenu(GraGraTreeView tree) {
		super("Atomic");
		this.treeView = tree;

		JMenuItem mi;

		mi = add(new JMenuItem("New Conclusion                Shift+Alt+C"));
		mi.setActionCommand("newConclusion");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('N');

		mi = add(new JMenuItem("Check Graph"));
		mi.setActionCommand("checkOneAtomic");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('G');

		/*
		 * mi = (JMenuItem) add(new JMenuItem("Create Post Condition"));
		 * mi.setActionCommand("convertOne"); mi.addActionListener(this.treeView);
		 */
		mi = add(new JMenuItem("Delete                      Delete"));
		mi.setActionCommand("deleteAtomic");
		mi.addActionListener(this.treeView.getActionAdapter());
		// mi.setMnemonic('D');

		// addSeparator();
		mi = new JMenuItem("Undo Delete Conclusion");
		// add(mi);
		mi.setActionCommand("undoDeleteAtomicConclusion");
		mi.addActionListener(this.treeView.getActionAdapter());

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		// mi = new JMenuItem("Textual Comments");
		mi.setActionCommand("commentAtomConstraint");
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
				if (sd != null && sd.isAtomic())
					return true;
			}
		}
		return false;
	}

	private GraGraTreeView treeView;
}
