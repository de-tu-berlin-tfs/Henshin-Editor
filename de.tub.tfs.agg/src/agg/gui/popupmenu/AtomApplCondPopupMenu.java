package agg.gui.popupmenu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

@SuppressWarnings("serial")
public class AtomApplCondPopupMenu extends JPopupMenu {

	public AtomApplCondPopupMenu(GraGraTreeView tree) {
		super("RuleConstraint");
		this.treeView = tree;
		JMenuItem mi = add(new JMenuItem("Delete               Delete"));
		mi.setActionCommand("deleteAtomApplCond");
		mi.addActionListener(this.treeView.getActionAdapter());
		setBorderPainted(true);
		pack();
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			TreePath path = this.treeView.getTree().getPathForLocation(x, y);
			if (path.getPath().length == 5) {
				DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode
						.getUserObject();
				if (sd != null && sd.isAtomApplCond())
					return true;
			}
		}
		return false;
	}

	private GraGraTreeView treeView;
}
