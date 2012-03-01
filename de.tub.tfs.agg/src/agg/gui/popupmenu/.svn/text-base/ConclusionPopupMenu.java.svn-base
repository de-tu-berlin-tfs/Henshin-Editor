package agg.gui.popupmenu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

public class ConclusionPopupMenu extends JPopupMenu {

	public ConclusionPopupMenu(GraGraTreeView tree) {
		super("Conclusion");
		this.treeView = tree;

		JMenuItem mi = add(new JMenuItem("Delete                     Delete"));
		mi.setActionCommand("deleteConclusion");
		mi.addActionListener(this.treeView);
		// mi.setMnemonic('D');

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			TreePath path = this.treeView.getTree().getPathForLocation(x, y);
			if (path.getPath().length == 4) {
				DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode
						.getUserObject();
				if (sd != null && sd.isConclusion()) {
					if (sd.getConclusion().getParent().getConclusions().size() > 1)
						return true;
					
					return false;
				}
			}
		}
		return false;
	}

	private GraGraTreeView treeView;
}
