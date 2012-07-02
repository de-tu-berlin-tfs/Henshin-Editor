
package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdRule;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.dialog.FormulaGraphGUI;
import agg.gui.treeview.nodedata.ApplFormulaTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.xt_basis.NestedApplCond;

public class ApplFormulaPopupMenu extends JPopupMenu {

	public ApplFormulaPopupMenu(GraGraTreeView tree) {
		super("Formula above GACs");
		this.treeView = tree;

		JMenuItem mi = add(new JMenuItem("Refresh"));
		mi.setActionCommand("refreshApplFormula");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshFormula();
			}
		});
		
		mi = new JMenuItem("Edit");
		this.add(mi);
		mi.setActionCommand("editApplFormula");
//		mi.addActionListener(treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editFormula();
			}
		});
		
//		addSeparator();
		
//		mi = new JMenuItem("Delete           Delete");
//		add(disable);		
//		mi.setActionCommand("deleteApplFormula");
//		mi.addActionListener(treeView);
//		mi.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {				
//			}
//		});
			
		
//		disable = new JCheckBoxMenuItem("disabled");
//		add(disable);
//		disable.setActionCommand("disableApplFormula");
//		disable.addActionListener(treeView);
//		disable.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {				
//			}
//		});

//		addSeparator();

//		mi = new JMenuItem("Textual Comments");
//		add(disable);
//		mi.setActionCommand("commentApplFormula");
//		mi.addActionListener(treeView);

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		this.rule = null;
		this.cond = null;
		
		if (this.treeView == null) {
			return false;
		}
		this.row = this.treeView.getTree().getRowForLocation(x, y);
		if (this.row != -1) {
			this.path = this.treeView.getTree().getPathForLocation(x, y);
			this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			this.data = (GraGraTreeNodeData) this.node.getUserObject();
							
			if (this.data != null && this.data.isApplFormula()) {
				this.formula = this.data;
				this.parNode = (DefaultMutableTreeNode)this.node.getParent();
				this.parData = (GraGraTreeNodeData)this.parNode.getUserObject();
				if (this.parData.isRule())
					this.rule = this.parData.getRule();
				else if (this.parData.isNestedAC())
					this.cond = this.parData.getNestedAC();
				
				this.posX = x; this.posY = x;

				return true;
			}
		}
		return false;
	}

	void refreshFormula() {
		this.data.update();
		
		if (this.rule != null) {
			if (!this.rule.getBasisRule().getFormula().isValid()) {	
				this.treeView.getTreeModel().removeNodeFromParent(this.data.getTreeNode());
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"This formula could not be reproduced.\n",
						"Formula corrupted",
						JOptionPane.ERROR_MESSAGE
						);
			}
		}
		else if (this.cond != null) {
			if (!this.cond.getNestedMorphism().getFormula().isValid()) {
				this.treeView.getTreeModel().removeNodeFromParent(this.data.getTreeNode());
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"This formula could not be reproduced.\n",
						"Formula corrupted",
						JOptionPane.ERROR_MESSAGE
						);
			}
		}
		this.treeView.getTree().treeDidChange();
	}
	
	void editFormula() {
		if (this.cond != null)
			editApplCondFormula();
		else if (this.rule != null)
			editRuleFormula();
	}
	
	void editApplCondFormula() {
		String ownerName = "application condition : "+this.cond.getMorphism().getName();
		FormulaGraphGUI d = new FormulaGraphGUI(this.treeView.getFrame(), ownerName,
				" Graph editor of Formula above General Application Conditions ", 
				" An empty graph is the case where all nodes are connected by AND.",
				true);
		d.setExportJPEG(this.treeView.getGraphicsExportJPEG());
		
		String oldformula = ((NestedApplCond)this.cond.getMorphism()).getFormulaText();
//		List<String> allVars = ((NestedApplCond)this.cond.getMorphism()).getNameOfEnabledACs();
		List<EdNestedApplCond> allNestedACs = this.cond.getEnabledACs();
		List<NestedApplCond> list = makeFrom(allNestedACs);
		d.setVarsAsObjs(allNestedACs, oldformula);
		d.setLocation(this.posX, this.posY);
		while (true) {
			d.setVisible(true);
			if (!d.isCanceled()) {
				if (this.cond.getGraGra().isEditable()) {
					boolean formulaChanged = d.isChanged();
					String f = d.getFormula();
					if (!this.cond.getNestedMorphism().setFormula(f, list)) {
						JOptionPane
						.showMessageDialog(
								this.treeView.getFrame(),
								"This formula definition failed. Please correct.",
								" Formula failed ", JOptionPane.WARNING_MESSAGE);
					}
					else if (formulaChanged) {	
						insertFormulaIntoACTreeNode(f, allNestedACs);
						this.cond.getGraGra().setChanged(true);	
	//					data.update();
	//					treeView.getTree().treeDidChange();
						break;
					} else
						break;
				} else
					treeView.lockWarning();
			} else break;
		}	
	}
	
	void editRuleFormula() {
		String ownerName = "rule : "+this.rule.getBasisRule().getName();
		FormulaGraphGUI d = new FormulaGraphGUI(this.treeView.getFrame(), ownerName,
				" Graph editor of Formula above General Application Conditions ", 
				" An empty graph is the case where all nodes are connected by AND.",
				true);
		d.setExportJPEG(this.treeView.getGraphicsExportJPEG());
		
		String oldformula = this.rule.getBasisRule().getFormulaStr();
//		List<String> allVars = this.rule.getBasisRule().getNameOfEnabledACs();
		List<EdNestedApplCond> allNestedACs = this.rule.getEnabledACs();
		List<NestedApplCond> list = makeFrom(allNestedACs);
		d.setVarsAsObjs(allNestedACs, oldformula);
		d.setLocation(this.posX, this.posY);
		while (true) {
			d.setVisible(true);
			if (!d.isCanceled()) {
				if (this.rule.getGraGra().isEditable()) {
					boolean formulaChanged = d.isChanged();
					String f = d.getFormula();
					if (!this.rule.getBasisRule().setFormula(f, list)) {
						JOptionPane
						.showMessageDialog(
								this.treeView.getFrame(),
								"The formula definition failed. Please correct.",
								" Formula failed ", JOptionPane.WARNING_MESSAGE);
					}
					else if (formulaChanged) {	
						insertFormulaIntoACTreeNode(f, allNestedACs);
						this.rule.getGraGra().setChanged(true);
	//					data.update();					
	//					treeView.getTree().treeDidChange();
						break;
					} else
						break;
				}
				else
					treeView.lockWarning();
			} else break;
		}	
	}
	
	void insertFormulaIntoACTreeNode(
			final String f, 
			final List<EdNestedApplCond> allVarsObj) {
		if (f.length() > 0) {	
			this.treeView.getTreeModel().removeNodeFromParent(this.node);
				
			// add formula tree node into nestedAC subtree
			if (!"true".equals(f)) {
				GraGraTreeNodeData conddata = null;
				if (this.rule != null)
					conddata = new ApplFormulaTreeNodeData(
							f, true, this.rule);
				else if (this.cond != null)
					conddata = new ApplFormulaTreeNodeData(
						f, true, this.cond);
				if (conddata != null) {
					conddata.setString(f);
					final DefaultMutableTreeNode condnode = new DefaultMutableTreeNode(
									conddata);
					conddata.setTreeNode(condnode);
					this.treeView.getTreeModel().insertNodeInto(condnode, this.parNode, 0);
				}
			}			
		}	
	}
	
	private List<NestedApplCond> makeFrom(final List<EdNestedApplCond> list) {
		final List<NestedApplCond> result = new Vector<NestedApplCond>(list.size(), 0);
		for (int i=0; i<list.size(); i++) {
			result.add(list.get(i).getNestedMorphism());
		}
		return result;
	}
	
	
	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node, parNode;
	GraGraTreeNodeData data, parData, formula;
	EdNestedApplCond cond;
	EdRule rule;
	
	int row, posX, posY; 
//	private JMenuItem disable;
}
