package agg.gui.popupmenu;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.attribute.impl.CondMember;
import agg.editor.impl.EdRule;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.util.Pair;

public class AttrConditionPopupMenu extends JPopupMenu {

	public AttrConditionPopupMenu(GraGraTreeView tree) {
		super("AttributeCondition");
		this.treeView = tree;

		// JMenuItem mi = (JMenuItem) add(new JMenuItem("Delete "));
		// mi.setEnabled(false);
		// mi.setActionCommand("deleteAttrCondition");
		// mi.addActionListener(this.treeView);
		// mi.setMnemonic('D');

		this.disable = new JCheckBoxMenuItem("disabled");
		this.disable.setActionCommand("disableAttrCondition");
		this.disable.addActionListener(this.treeView);
		add(this.disable);

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
				// this.treeView.selectPath(x,y);
				DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode
						.getUserObject();
				if (sd != null && sd.isAttrCondition()) {
					Pair<CondMember, EdRule> p = sd.getAttrCondition();
					CondMember cm = p.first;
					if (!cm.isEnabled())
						this.disable.setSelected(true);
					else
						this.disable.setSelected(false);
					return true;
				}
			}
		}
		return false;
	}

	private GraGraTreeView treeView;

	private JMenuItem disable;
}
