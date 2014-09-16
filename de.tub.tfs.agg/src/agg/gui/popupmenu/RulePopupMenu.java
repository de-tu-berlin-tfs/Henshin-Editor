// $Id: RulePopupMenu.java,v 1.18 2010/09/19 16:25:01 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.dialog.FormulaGraphGUI;
import agg.gui.treeview.nodedata.ApplFormulaTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.path.GrammarTreeNode;
import agg.xt_basis.NestedApplCond;


public class RulePopupMenu extends JPopupMenu {

	public RulePopupMenu(GraGraTreeView tree) {
		super("Rule");
		this.treeView = tree;

		this.miAC = new JMenuItem("New GAC (General Application Condition)");
		this.add(miAC);
		this.miAC.setActionCommand("newNestedAC");
		this.miAC.addActionListener(this.treeView);

		this.miAC1 = new JMenuItem("Make GAC due to RHS");
		this.add(miAC1);
		this.miAC1.setActionCommand("makeGACFromRHS");
		this.miAC1.addActionListener(this.treeView);
		
		this.miFormula = new JMenuItem("Set Formula above GACs");
		this.add(miFormula);
		this.miFormula.setActionCommand("setFormulaAboveACs");
		this.miFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFormula();
			}
		});
		
		addSeparator();
		
		this.miNAC = add(new JMenuItem(
				"New NAC                                 Shift+Alt+N"));
		this.miNAC.setActionCommand("newNAC");
		this.miNAC.addActionListener(this.treeView);
		// miNAC.setMnemonic('N');
		
		this.miNAC1 = add(new JMenuItem(
				"Make NAC due to RHS               "));
		this.miNAC1.setActionCommand("makeNACFromRHS");
		this.miNAC1.addActionListener(this.treeView);
		
		addSeparator();
		
		this.miPAC = add(new JMenuItem("New PAC                                 "));// Shift+Alt+A
		this.miPAC.setActionCommand("newPAC");
		this.miPAC.addActionListener(this.treeView);
		// miPAC.setMnemonic('P');
		addSeparator();

		this.miPostAC = add(new JMenuItem("Create Post Conditions"));
		this.miPostAC.setActionCommand("convertAtomicsOfRule");
		this.miPostAC.addActionListener(this.treeView);

		this.miPostACdel = add(new JMenuItem("Delete Post Conditions"));
		this.miPostACdel.setActionCommand("deleteRuleConstraints");
		this.miPostACdel.addActionListener(this.treeView);

		addSeparator();

		this.miLayer = add(new JMenuItem(
				"Set Layer                                 Shift+Alt+L"));
		this.miLayer.setActionCommand("setRuleLayer");
		this.miLayer.addActionListener(this.treeView);
		// miLayer.setMnemonic('L');

		this.miPriority = add(new JMenuItem(
				"Set Priority                              Shift+Alt+P"));
		this.miPriority.setActionCommand("setRulePriority");
		this.miPriority.addActionListener(this.treeView);
		// miPriority.setMnemonic('P');

		addSeparator();
		this.miParallelApply = new JCheckBoxMenuItem("Parallel Matching");
		this.miParallelApply.setActionCommand("allowParallelApply");
		this.miParallelApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBoxMenuItem) e.getSource()).isSelected())
					RulePopupMenu.this.rule.getBasisRule().setParallelMatchingEnabled(true);
				else
					RulePopupMenu.this.rule.getBasisRule().setParallelMatchingEnabled(false);
			}
		});
		add(this.miParallelApply);

		addSeparator();

		this.miMove = add(new JMenuItem("Move"));
		this.miMove.setActionCommand("moveRule");
		this.miMove.addActionListener(this.treeView);
		// miMove.setMnemonic('M');

		addSeparator();
		
		this.miCopy = add(new JMenuItem(
				"Copy                                       Shift+Alt+D"));
		this.miCopy.setActionCommand("copyRule");
		this.miCopy.addActionListener(this.treeView);
		
		this.miInverse = add(new JMenuItem("Make Inverse Rule"));
		this.miInverse.setActionCommand("reverseRule");
		this.miInverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reverseRule();
			}
		});

		this.miMinimal = add(new JMenuItem("Make Minimal Rule"));
		this.miMinimal.setActionCommand("minimalRule");
		this.miMinimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				minimalRule();
			}
		});
		
		this.miMakeRS = add(new JMenuItem("Make Rule Scheme"));
		this.miMakeRS.setActionCommand("makeRuleScheme");
//		this.miMakeRS.addActionListener(this.treeView);
		this.miMakeRS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeRuleScheme();
			}
		});
		
//		miConcurrent = add(new JMenuItem("Make Disjoint Concurrent Rule With Next Rule"));
//		miConcurrent.setActionCommand("concurrentRule");
//		miConcurrent.addActionListener(treeView);

		addSeparator();

		this.miDelete = add(new JMenuItem(
				"Delete                                              Delete"));
		this.miDelete.setActionCommand("deleteRule");
		this.miDelete.addActionListener(this.treeView);
		// miDelete.setMnemonic('D');

		addSeparator();

		this.miDisabled = new JCheckBoxMenuItem("disabled");
		this.miDisabled.setActionCommand("disableRule");
		this.miDisabled.addActionListener(this.treeView);
		add(this.miDisabled);

		addSeparator();

		this.miAnimated = new JCheckBoxMenuItem("animated");
		this.miAnimated.setActionCommand("animatedRule");
//		this.miAnimated.addActionListener(treeView);
		this.miAnimated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RulePopupMenu.this.rule.setAnimated(((JCheckBoxMenuItem) e.getSource()).isSelected());
			}
		});
		add(this.miAnimated);
		
		addSeparator();
		
		this.miWait = new JCheckBoxMenuItem("Wait Before Applying Rule");
		this.miWait.setActionCommand("waitBeforeApplyRule");
//		this.miWait.addActionListener(treeView);
		this.miWait.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RulePopupMenu.this.rule.getBasisRule().setWaitBeforeApplyEnabled(((JCheckBoxMenuItem) e.getSource()).isSelected());
			}
		});
		add(this.miWait);
		
		addSeparator();
		
		this.miComment = add(new JMenuItem("Textual Comments"));
		// miComment = new JMenuItem("Textual Comments");
		this.miComment.setActionCommand("commentRule");
		this.miComment.addActionListener(this.treeView);
		// miComment.setMnemonic('T');

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			int pl = this.treeView.getTree().getPathForLocation(x, y).getPath().length;
			if (pl == 3) {
				this.path = this.treeView.getTree().getPathForLocation(x, y);
				this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
				this.data = (GraGraTreeNodeData) this.node.getUserObject();
				this.rule = this.treeView.getRule((DefaultMutableTreeNode) this.path
						.getLastPathComponent());
				if (this.rule != null) {					
					GrammarTreeNode.expand(this.treeView, this.node, this.path);
					this.posX = x; this.posY = y;
					
					setAllEnabled(true);
					this.miDisabled.setSelected(!this.rule.getBasisRule().isEnabled());	
					this.miParallelApply.setSelected(this.rule.getBasisRule().isParallelApplyEnabled());
					
					this.miAnimated.setEnabled(this.rule.getTypeSet().getTypeGraph() != null
							&& !this.rule.getTypeSet().getTypeGraph().getArcs().isEmpty());
					if (this.miAnimated.isEnabled())
						this.miAnimated.setSelected(this.rule.isAnimated());
					
					this.miWait.setSelected(this.rule.getBasisRule().isWaitBeforeApplyEnabled());
					
					return true;
				} 
			} 
		} 
		return false;
	}

	private void setAllEnabled(boolean b) {
		this.miDelete.setEnabled(b);
		this.miAC.setEnabled(b);
		this.miAC1.setEnabled(b);
		this.miFormula.setEnabled(b);
		this.miNAC.setEnabled(b);
		this.miNAC1.setEnabled(b);
		this.miPAC.setEnabled(b);
		this.miDisabled.setEnabled(b);
		this.miPostAC.setEnabled(b);
		this.miPostACdel.setEnabled(b);
		this.miLayer.setEnabled(b);
		this.miPriority.setEnabled(b);
		this.miCopy.setEnabled(b);
		this.miInverse.setEnabled(b);
		this.miMakeRS.setEnabled(b);
//		this.miConcurrent.setEnabled(b);
		this.miMove.setEnabled(b);
		this.miParallelApply.setEnabled(b);
		this.miAnimated.setEnabled(b);
		this.miWait.setEnabled(b);
	}
	
	void setFormula() {	
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
		d.setLocation(this.posX+20, this.posY+20);
		
		while (true) {
			d.setVisible(true);
			if (!d.isCanceled()) {
				boolean formulaChanged = d.isChanged();
				String f = d.getFormula();
				if (!this.rule.getBasisRule().setFormula(f, list)) {
					JOptionPane
					.showMessageDialog(
							this.treeView.getFrame(),
							"The formula definition failed. Please correct.",
							" Formula failed ", JOptionPane.WARNING_MESSAGE);
					continue;
				} else {
					f = this.rule.getBasisRule().getFormulaStr();
				}
				if (formulaChanged) {
					insertFormulaIntoRuleTreeNode(f, allNestedACs);
					this.rule.getGraGra().setChanged(true);	
					break;
				} else
					break;
			} else break;
		}	
	}
	

	private List<NestedApplCond> makeFrom(
			List<EdNestedApplCond> list) {
		final List<NestedApplCond> result = new Vector<NestedApplCond>(list.size(), 0);
		for (int i=0; i<list.size(); i++) {
			result.add(list.get(i).getNestedMorphism());
		}
		return result;
	}

	void insertFormulaIntoRuleTreeNode(
			final String f, 
			final List<EdNestedApplCond> allVarsObj) {
		if (f.length() > 0) {			
			Object child = this.treeView.getTreeModel().getChild(this.node, 0);
			if (child != null) {
				// remove existing formula from rule subtree
				if (((GraGraTreeNodeData) ((DefaultMutableTreeNode) child)
						.getUserObject()).isApplFormula()) {
					this.treeView.getTreeModel().removeNodeFromParent((DefaultMutableTreeNode)child);
				}
				
				// add formula tree node into rule subtree
				if (!"true".equals(f)) {
					final GraGraTreeNodeData conddata = new ApplFormulaTreeNodeData(f, true, this.rule);
					conddata.setString(f);
					final DefaultMutableTreeNode condnode = new DefaultMutableTreeNode(
								conddata);
					conddata.setTreeNode(condnode);
					this.treeView.getTreeModel().insertNodeInto(condnode, this.node, 0);
				}
			}
		}	
	}	
	
	public void enableNestedApplCond(boolean b) {
		this.enableNestedAC = b;
	}
	
	void reverseRule() {
		if (this.path == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(null, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = this.path.getParentPath();
		DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
		GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
		EdRule inverseRule = graData.getGraGra().reverseRule(data.getRule(), true);
		if (inverseRule != null) {
			treeView.putRuleIntoTree(inverseRule, 
						(DefaultMutableTreeNode) node.getParent(),
						((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
								
			if (!inverseRule.getBasisRule().getErrorMsg().equals("")) {
				String warnMsg = inverseRule.getBasisRule().getErrorMsg().replaceAll(";", "<br>");
				warnMsg = warnMsg.replaceAll("\n", "<br>");
				JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"							
							+"During reverting the following occurred:<br><br>"
							+"<font color=\"#FF0000\">"
							+warnMsg	
							+"</font>"
							+"<br>The new inverse rule:  "
							+inverseRule.getName()
							+"<br>is added after its original rule into the rule set.<br><br>",						
							"Inverse Rule:  "+inverseRule.getName(),
							JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"							
							+"Inverting was successful.<br><br>"						
							+"The new inverse rule:  "
							+inverseRule.getName()+"<br>"
							+"is added after its original rule into the rule set.<br><br>",	
							"Inverse Rule:  "+inverseRule.getName(),
							JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(treeView.getFrame(), 
						"<html><body>"						
						+"It isn't possible to invert the rule:<br>    "+data.getRule().getName()+".",					
						"Inverse Rule",
						JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void minimalRule() {
		if (this.path == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(null, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = this.path.getParentPath();
		DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
		GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
		EdRule minRule = graData.getGraGra().makeMinimalRule(data.getRule(), true);
		if (minRule != null) {
			treeView.putRuleIntoTree(minRule, 
						(DefaultMutableTreeNode) node.getParent(),
						((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
								
			if (minRule.getBasisRule().getErrorMsg().indexOf("identical") >= 0) {
				String warnMsg = minRule.getBasisRule().getErrorMsg();
				JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"	
							+warnMsg+"<br>"								
							+"It is added after its original rule into the rule set.<br><br>",						
							"Minimal Rule :  "+minRule.getName(),
							JOptionPane.WARNING_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"													
							+"The minimal rule is added after its original rule.<br><br>",	
							"Minimal Rule :  "+minRule.getName(),
							JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(treeView.getFrame(), 
						"<html><body>"						
						+"It isn't possible to make a minimal rule from:<br>    "
						+data.getRule().getName()+"."+"<br>",					
						"Minimal Rule Failed",
						JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void makeRuleScheme() {
		if (this.path == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(null, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = this.path.getParentPath();
		DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
		GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
		EdRuleScheme rs = graData.getGraGra().createRuleScheme(data.getRule());
		if (rs != null) {
			treeView.putRuleSchemeIntoTree(rs, 
						(DefaultMutableTreeNode) node.getParent(),
						((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
								
			JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"							
							+"The new rule scheme:  "
							+rs.getName()+"<br>"
							+"is added after its original rule into the rule set.<br><br>",	
							"Rule Scheme:  "+rs.getName(),
							JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(treeView.getFrame(), 
						"<html><body>"						
						+"It isn't possible to invert the rule:<br>    "+data.getRule().getName()+".",					
						"Inverse Rule",
						JOptionPane.ERROR_MESSAGE);
		}
	}
	
	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node;
	GraGraTreeNodeData data;
	EdRule rule;

	int posX, posY; 
	
	private JMenuItem miDelete, miDisabled, miAC, miAC1, miFormula,  miNAC, miNAC1, miPAC, 
	miParallelApply, miAnimated, miPostAC, miPostACdel, 
	miLayer, miPriority, miCopy, miInverse, miMinimal, miMakeRS, //miConcurrent, 
	miMove, miWait, miComment;
	
	boolean enableNestedAC;
	
}
