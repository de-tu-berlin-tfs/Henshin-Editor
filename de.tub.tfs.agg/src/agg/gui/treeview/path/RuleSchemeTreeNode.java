/**
 * 
 */
package agg.gui.treeview.path;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;
import agg.gui.event.TreeViewEvent;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;

/**
 * @author olga
 *
 */
public class RuleSchemeTreeNode extends DefaultMutableTreeNode {

	
	public RuleSchemeTreeNode() {}
	
	public RuleSchemeTreeNode(final EdRuleScheme rs) {
		
		
	}
	
	public TreePath deleteMultiRule(
			final GraGraTreeView treeView,
			final DefaultMutableTreeNode delNode,
			final TreePath selPath,
			boolean withWarning) {
		
		final GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		final TreePath ruleschemePath = selPath.getParentPath();
		if (ruleschemePath != null) {
			final DefaultMutableTreeNode ruleschemeNode = (DefaultMutableTreeNode) ruleschemePath
					.getLastPathComponent();
			final GraGraTreeNodeData ruleschemeData = (GraGraTreeNodeData) ruleschemeNode
					.getUserObject();
			if (data.getRule() != treeView.getCurrentRule()) {
				int answer = withWarning? treeView.removeWarning("Multi Rule"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					int row = treeView.getTree().getRowForPath(selPath);
					treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, selPath));
					treeView.getTreeModel().removeNodeFromParent(delNode);
					final EdRule r = data.getRule();
					treeView.getGraGraStore().storeMultiRule(ruleschemeData.getRuleScheme(), r);
					ruleschemeData.getRuleScheme().removeMultiRule(r);
					row--;
					treeView.setEditPath(row);					
					treeView.setFlagForNew();
					treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, treeView.getEditorPath()));
					return treeView.getSelectedPath();	
				}
			} 
			else {
				int answer = withWarning? treeView.removeCurrentObjectWarning("Multi Rule"):0;
				if (answer == JOptionPane.YES_OPTION) {
					int row = treeView.getTree().getRowForPath(selPath);
					treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, selPath));
					treeView.getTreeModel().removeNodeFromParent(delNode);
					EdRule r = data.getRule();
					treeView.getGraGraStore().storeMultiRule(ruleschemeData.getRuleScheme(), r);
					ruleschemeData.getRuleScheme().removeMultiRule(r);	
					row--;
					treeView.setEditPath(row);					
					treeView.setFlagForNew();
					treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, treeView.getEditorPath()));
					return treeView.getSelectedPath();	
				}
			}
		}
		return selPath;
	}
	
	public TreePath deleteAmalgamatedRule(
			final GraGraTreeView treeView,
			final DefaultMutableTreeNode delNode,
			final TreePath selPath,
			boolean withWarning) {
		
		final TreePath ruleschemePath = selPath.getParentPath();
		if (ruleschemePath != null) {
			final DefaultMutableTreeNode ruleschemeNode = (DefaultMutableTreeNode) ruleschemePath
					.getLastPathComponent();
			int rsRow = treeView.getTree().getRowForPath(ruleschemePath);
			final GraGraTreeNodeData 
			ruleschemeData = (GraGraTreeNodeData) ruleschemeNode.getUserObject();
			treeView.fireTreeViewEvent(new TreeViewEvent(this,TreeViewEvent.DELETED, selPath));
			treeView.getTreeModel().removeNodeFromParent(delNode);
				
			if (ruleschemeData.getRuleScheme() != null)
				ruleschemeData.getRuleScheme().removeAmalgamatedRule();

			treeView.setEditPath(rsRow);
			treeView.setFlagForNew();
			treeView.fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SELECTED, treeView.getEditorPath()));
			return treeView.getSelectedPath();				
		}
		return selPath;
	}
}
