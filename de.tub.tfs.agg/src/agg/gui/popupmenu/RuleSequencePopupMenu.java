/**
 * 
 */
package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdRule;
import agg.gui.AGGAppl;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.nodedata.RuleSequenceTreeNodeData;
import agg.ruleappl.RuleSequence;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Rule;

/**
 * @author olga
 *
 */
public class RuleSequencePopupMenu extends JPopupMenu {

	
	public RuleSequencePopupMenu(GraGraTreeView tree) {
		
		super("RuleSequence");
		this.treeView = tree;
				
		this.mi = add(new JMenuItem("Show / Edit"));
		this.mi.setActionCommand("editRuleSequence");
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (RuleSequencePopupMenu.this.ruleSeq != null) {
					RuleSequencePopupMenu.this.treeView.selectPath(RuleSequencePopupMenu.this.locationRow);
					if (RuleSequencePopupMenu.this.ruleSeq.getGraGra() 
							== RuleSequencePopupMenu.this.treeView.getCurrentGraGra().getBasisGraGra()) {
						if (!RuleSequencePopupMenu.this.ruleSeq.isValid()) {
							JOptionPane.showMessageDialog(RuleSequencePopupMenu.this.treeView.getFrame(), 							
									"Currently selected rule sequence is not valid anymore.\n"						
									+"At least one rule of it is not available.\n"
									+"Please delete this sequence and create a new one. ",	
									"Rule Sequence failed",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						((AGGAppl)RuleSequencePopupMenu.this.treeView.getFrame()).getGraGraEditor()
									.showRuleSequenceGUI(RuleSequencePopupMenu.this.ruleSeq.getName());
					}
					else {
						int indx = RuleSequencePopupMenu.this.locationRow;						
						TreePath p = RuleSequencePopupMenu.this.treeView.getTreePathOfGrammar(RuleSequencePopupMenu.this.ruleSeq.getGraGra());
						if (p != null) {
							RuleSequencePopupMenu.this.treeView.selectPath(p);
							RuleSequencePopupMenu.this.treeView.selectPath(indx);
							if (!RuleSequencePopupMenu.this.ruleSeq.isValid()) {
								JOptionPane.showMessageDialog(RuleSequencePopupMenu.this.treeView.getFrame(), 							
										"Currently selected rule sequence is not valid anymore.\n"						
										+"At least one rule of it is not available.\n"
										+"Please delete this sequence and create a new one. ",	
										"Rule Sequence failed",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							((AGGAppl)RuleSequencePopupMenu.this.treeView.getFrame()).getGraGraEditor()
									.showRuleSequenceGUI(RuleSequencePopupMenu.this.ruleSeq.getName());
						}
					}
				}
			}
		});
		
		addSeparator();
		
		this.miConcurDisJointRule = new JMenuItem("Make Concurrent Rule by disjoint Union");
//		this.add(this.miConcurDisJointRule);
		this.miConcurDisJointRule.setActionCommand("disjointConcurRuleOfSeq");
		this.miConcurDisJointRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeDisjointConcurRuleOfSeq();
			}
		});

		this.miConcurJointRule = new JMenuItem("Make (max) Concurrent Rule jointly by Dependency");
		this.add(this.miConcurJointRule);
		this.miConcurJointRule.setActionCommand("jointConcurRuleOfSeq");
//		this.miConcurJointRule.addActionListener(treeView);
		this.miConcurJointRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMaxJointlyConcurRuleOfSeq();
			}
		});

		this.miAllConcurJointRule = new JMenuItem("Make Concurrent Rule(s) jointly by Dependency");
		this.add(this.miAllConcurJointRule);
		this.miAllConcurJointRule.setActionCommand("jointAllConcurRuleOfSeq");
//		this.miAllConcurJointRule.addActionListener(treeView);
		this.miAllConcurJointRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeJointlyConcurRuleOfSeq();
			}
		});

		this.miConcurRuleByObjFlow = add(new JMenuItem("Make Concurrent Rule jointly by Object Flow"));
		this.miConcurRuleByObjFlow.setActionCommand("concurRuleOfSeqByOF");
		this.miConcurRuleByObjFlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeConcurRuleOfSeqByOF();
			}
		});

		
		this.miParallelRule = new JMenuItem("Make Parallel Rule by disjoint Union");
		this.add(this.miParallelRule);
		this.miParallelRule.setActionCommand("makeParallelRule");
		this.miParallelRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeParallelRuleOfSeq();
			}
		});
		
		addSeparator();
		
		this.mi = add(new JMenuItem("Delete"));
		this.mi.setActionCommand("deleteRuleSequence");
//		this.mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (treeView.hasMultipleSelection())
					treeView.delete("selected");
				else
					treeView.deleteRuleSequence(node, path, true);
			}
		});
		
		addSeparator();
		
		this.miRuleSeqValidated = add(new JCheckBoxMenuItem("Graph Transformation by validated Rule Sequence"));
//		group.add(miRuleSeq);
		this.miRuleSeqValidated.setActionCommand("validatedRuleSequence");
		this.miRuleSeqValidated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (RuleSequencePopupMenu.this.ruleSeq != null) {
					if (!RuleSequencePopupMenu.this.ruleSeq.isChecked()) {
						// warning							
							JOptionPane.showMessageDialog(null, 
										"<html><body>"
										+"Currently selected rule sequence isn't checked!\n ",
										"Cannot select this action", JOptionPane.WARNING_MESSAGE);	
							((JCheckBoxMenuItem) e.getSource()).setSelected(false);
							return;
					}
					else if (((JCheckBoxMenuItem) e.getSource()).isSelected()) {
						if (RuleSequencePopupMenu.this.ruleSeq.isChecked()) {
							RuleSequencePopupMenu.this.miRuleSeqObjFlow.setSelected(false);
							
							RuleSequencePopupMenu.this.ruleSeq.getGraGra().setTrafoByApplicableRuleSequence(true);
							if (RuleSequencePopupMenu.this.ruleSeq.getGraph() != null) {
								((JCheckBoxMenuItem) e.getSource()).setText(
										"Graph: "
										+RuleSequencePopupMenu.this.ruleSeq.getGraph().getName()
										+"Transformation by validated Rule Sequence");
							} else {
								((JCheckBoxMenuItem) e.getSource()).setText("Transformation by validated Rule Sequence");
							}
						}
					} else {
						RuleSequencePopupMenu.this.ruleSeq.getGraGra().setTrafoByApplicableRuleSequence(false);
						((JCheckBoxMenuItem) e.getSource()).setText("Transformation by validated Rule Sequence");
					}
				}
			}
		});
		
		this.miRuleSeqObjFlow = new JCheckBoxMenuItem("Graph Transformation by defined Object Flow");
		add(this.miRuleSeqObjFlow);
//		group.add(miRuleSeqObjFlow);
		this.miRuleSeqObjFlow.setActionCommand("objectflowRuleSequence");
		this.miRuleSeqObjFlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (RuleSequencePopupMenu.this.ruleSeq != null) {
					if (((JCheckBoxMenuItem) e.getSource()).isSelected()) {
						if (RuleSequencePopupMenu.this.ruleSeq.getObjectFlow().isEmpty()) {
							JOptionPane.showMessageDialog(null, 
								"<html><body>"
								+"There isn't any Object Flow defined.",
								"Cannot select this action", JOptionPane.WARNING_MESSAGE);	
							((JCheckBoxMenuItem) e.getSource()).setSelected(false);
							return;
						}
						RuleSequencePopupMenu.this.miRuleSeqValidated.setSelected(false);
						RuleSequencePopupMenu.this.ruleSeq.getGraGra().setTrafoByRuleSequenceWithObjectFlow(true);						
					}
					else {
						RuleSequencePopupMenu.this.ruleSeq.getGraGra().setTrafoByRuleSequenceWithObjectFlow(false);
//						((JCheckBoxMenuItem) e.getSource()).setText("Graph Transformation by validated Rule Sequence");
					}
				}
			}
		});
			
	}
	
	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		this.locationRow = this.treeView.getTree().getRowForLocation(x, y);
		if (this.locationRow != -1) {
			if (this.treeView.getTree().getPathForLocation(x, y).getPath().length == 3) {
				this.path = this.treeView.getTree().getPathForLocation(x, y);
				this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();				
				this.data = (RuleSequenceTreeNodeData) this.node.getUserObject();
				this.ruleSeq = this.treeView.getRuleSequence(node);
				if (this.ruleSeq != null) {
					this.miConcurJointRule.setEnabled(!this.ruleSeq.isEmpty());
					this.miAllConcurJointRule.setEnabled(!this.ruleSeq.isEmpty());
					this.miConcurRuleByObjFlow.setEnabled(!this.ruleSeq.isEmpty());
					this.miParallelRule.setEnabled(!this.ruleSeq.isEmpty());
					this.miRuleSeqValidated.setEnabled(!this.ruleSeq.isEmpty());
					this.miRuleSeqObjFlow.setEnabled(!this.ruleSeq.isEmpty());
					
					((JCheckBoxMenuItem) this.miRuleSeqValidated).setText("Graph Transformation by validated Rule Sequence");
					((JCheckBoxMenuItem) this.miRuleSeqValidated).setSelected(this.ruleSeq.isTrafoByARS());
					
					((JCheckBoxMenuItem) this.miRuleSeqObjFlow).setSelected(this.ruleSeq.isTrafoByObjFlow());
					
					return true;
				}
			}
		}
		return false;
	}
	
	
	void makeDisjointConcurRuleOfSeq() {		
		TreePath graPath = this.path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			final GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			final int indxOfLastRule = ((DefaultMutableTreeNode) node.getParent())
					.getIndex(this.treeView.getTreeNodeOfRule(
								graData.getGraGra().getRules().lastElement()));
			if (warningOK()) {
				if (data.getRuleSequence().getRules().size() == 1) {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"The concurrent rule for the single rule is the rule itself.",					
							"Concurrent Rule",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JOptionPane pane = new JOptionPane(
						"Generating concurrent rule ... \n Please wait ... ",
						JOptionPane.WARNING_MESSAGE);
				final JDialog d = pane.createDialog("Generating ...");
				Thread thread = new Thread() {
					public void run() {											
						EdRule concurrentRule = graData.getGraGra()
							.makeConcurrentRuleOfRuleSeq(data.getRuleSequence(), false, true);

						if (concurrentRule != null) {				
							treeView.putRuleIntoTree(concurrentRule, 
									(DefaultMutableTreeNode) node.getParent(),
									indxOfLastRule+1);
							treeView.concurrentRuleWarning(concurrentRule);
						}
						else {
							JOptionPane.showMessageDialog(treeView.getFrame(), 
									"<html><body>"						
									+"It wasn't possible to create a concurrent rule.",					
									"Concurrent Rule",
									JOptionPane.ERROR_MESSAGE);
						}
						d.setVisible(false);
					}
				};
				thread.start();				
				d.setVisible(true);
				while (thread.isAlive()) {}
			}
		}
	}
	
	void makeMaxJointlyConcurRuleOfSeq() {		
		TreePath graPath = this.path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			final GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			final int indxOfLastRule = ((DefaultMutableTreeNode) node.getParent())
									.getIndex(this.treeView.getTreeNodeOfRule(
											graData.getGraGra().getRules().lastElement()));
			if (warningOK()) {
				if (data.getRuleSequence().getRules().size() == 1) {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"The concurrent rule for the single rule is the rule itself.",					
							"Concurrent Rule",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JOptionPane pane = new JOptionPane(
						"Generating concurrent rule ... \n Please wait ... ",
						JOptionPane.WARNING_MESSAGE);
				final JDialog d = pane.createDialog("Generating ...");
				Thread thread = new Thread() {
					public void run() {
						concurrentRules = graData.getGraGra()
								.makeConcurrentRuleOfRuleSeqForward(data.getRuleSequence(), false, true);						
						d.setVisible(false);
					}
				};
				thread.start();				
				d.setVisible(true);
				while (thread.isAlive()) {}
				
				if (concurrentRules != null && !concurrentRules.isEmpty()) {
					for (int i=0; i<concurrentRules.size(); i++) {
						EdRule concurrentRule = concurrentRules.get(i);
						treeView.putRuleIntoTree(concurrentRule, 
								(DefaultMutableTreeNode) node.getParent(),
								indxOfLastRule+1+i);
					}
					treeView.concurrentRuleWarning(concurrentRules.get(0));
				}
				else {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"It wasn't possible to create a concurrent rule.<br>"
							+"(Reason may be: Dependency of rules doesn't exist)",					
							"Concurrent Rule",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	void makeJointlyConcurRuleOfSeq() {		
		TreePath graPath = this.path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			final GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			final int indxOfLastRule = ((DefaultMutableTreeNode) node.getParent())
									.getIndex(this.treeView.getTreeNodeOfRule(
											graData.getGraGra().getRules().lastElement()));
			if (warningOK()) {
				if (data.getRuleSequence().getRules().size() == 1) {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"The concurrent rule for the single rule is the rule itself.",					
							"Concurrent Rule",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JOptionPane pane = new JOptionPane(
						"Generating concurrent rule ... \n Please wait ... ",
						JOptionPane.WARNING_MESSAGE);
				final JDialog d = pane.createDialog("Generating ...");
				Thread thread = new Thread() {
					public void run() {
						concurrentRules = graData.getGraGra()
							.makeConcurrentRuleOfRuleSeqForward(data.getRuleSequence(), true, true);
						
						d.setVisible(false);
					}
				};
				thread.start();				
				d.setVisible(true);
				while (thread.isAlive()) {}
				
				if (concurrentRules != null && !concurrentRules.isEmpty()) {
					for (int i=0; i<concurrentRules.size(); i++) {
						EdRule concurrentRule = concurrentRules.get(i);
						treeView.putRuleIntoTree(concurrentRule, 
								(DefaultMutableTreeNode) node.getParent(),
								indxOfLastRule+1+i);
					}
					treeView.concurrentRuleWarning(concurrentRules.get(0));
				}
				else {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"It isn't possible to create a concurrent rule.",					
							"Concurrent Rule",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	void makeConcurRuleOfSeqByOF() {		
		TreePath graPath = this.path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			final GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			final int indxOfLastRule = ((DefaultMutableTreeNode) node.getParent())
			.getIndex(this.treeView.getTreeNodeOfRule(
					graData.getGraGra().getRules().lastElement()));

			if (warningOK()) {
				if (data.getRuleSequence().getRules().size() == 1) {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"The concurrent rule for the single rule is the rule itself.",					
							"Concurrent Rule",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (data.getRuleSequence().isObjFlowDefined()) {				
				
					JOptionPane pane = new JOptionPane(
							"Generating concurrent rule ... \n Please wait ... ",
							JOptionPane.WARNING_MESSAGE);
					final JDialog d = pane.createDialog("Generating ...");
					Thread thread = new Thread() {
						public void run() {
					
								EdRule concurrentRule = graData.getGraGra()
									.makeConcurrentRuleOfRuleSeq(data.getRuleSequence(), true, true);
								if (concurrentRule != null) {				
									treeView.putRuleIntoTree(concurrentRule, 
											(DefaultMutableTreeNode) node.getParent(),
											indxOfLastRule+1);
									treeView.concurrentRuleWarning(concurrentRule);
								}
								else {
									JOptionPane.showMessageDialog(treeView.getFrame(), 
											"<html><body>"						
											+"It isn't possible to create a concurrent rule.",					
											"Concurrent Rule Failed",
											JOptionPane.ERROR_MESSAGE);
								}
							
							d.setVisible(false);
						}
					};
					thread.start();				
					d.setVisible(true);
					while (thread.isAlive()) {}
				}
				else {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"						
							+"Any Object Flow does not exist.<br>"
							+"It should be defined before.",					
							"Concurrent Rule Failed",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private boolean warningOK() {
		if (data.getRuleSequence().containsRuleScheme()) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
					"Currently selected rule sequence contains at least one Rule Scheme.\n"						
					+"Building of a concurrent rule is not available in this case.\n"
					+"Only plain rules will be supported. ",	
					"Feature not available",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		Rule failedRule = BaseFactory.theFactory().checkApplCondsOfRules(data.getRuleSequence().getRules());
		if (failedRule != null) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
					"Currently selected rule list contains at least one invalid rule.\n"						
					+failedRule.getName()+":    "+failedRule.getErrorMsg(),	
					"Concurrent Rule Failed",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (data.getRuleSequence().containsRuleLoop()) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(),
					"Please note:\n"
					+"The (*) iterations of a rule will be converted to 2 times.",						
					"Concurrent Rule",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;
	}
	
	void makeParallelRuleOfSeq() {
		TreePath graPath = this.path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			if (graData.getGraGra() != null) {
				int indxOfLastRule = ((DefaultMutableTreeNode) node.getParent())
										.getIndex(this.treeView.getTreeNodeOfRule(
													graData.getGraGra().getRules().lastElement()));
					
				if (data.getRuleSequence().containsRuleScheme()) {
					JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
								"Currently selected rule sequence contains at least one Rule Scheme.\n"						
								+"Building of a parallel rule is not available in this case.\n"
								+"Only plain rules will be supported. ",	
								"Feature not available",
								JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				Rule failedRule = BaseFactory.theFactory().checkApplCondsOfRules(data.getRuleSequence().getRules());
				if (failedRule != null) {
					JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
								"Currently selected rule list contains at least one invalid rule.\n"						
								+failedRule.getName()+":    "+failedRule.getErrorMsg(),	
								"Parallel Rule Failed",
								JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				if (data.getRuleSequence().containsRuleLoop()) {
					JOptionPane.showMessageDialog(this.treeView.getFrame(),
								"Please note:\n"
								+"The (*) iterations of a rule will be converted to 2 times.",						
								"Parallel Rule",
								JOptionPane.INFORMATION_MESSAGE);
				}
					
				makeParallelRuleOfRules(graData.getGraGra(), 
							data.getRuleSequence().getRules(), 
							graNode,
							indxOfLastRule);
			}
		}
	}
	
	private void makeParallelRuleOfRules(
			final EdGraGra gra,
			final List<Rule> rules, 
			final DefaultMutableTreeNode node,
			int indxOfLastRule) {
						
		EdRule parallelRule = gra.makeParallelRuleOfRules(rules, true);	
		if (parallelRule != null) {
			if (parallelRule.getBasisRule().isApplicable()) {
				this.treeView.putRuleIntoTree(parallelRule, 
						node,
						indxOfLastRule+1);
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"<html><body>"							
						+"Building of a parallel rule was successful."
						+"\n\n"
						+"The rule:  "
						+parallelRule.getName()+"\n"
						+"is added at the end of the rule set.\n\n",	
						"Parallel Rule:  "+parallelRule.getName(),
						JOptionPane.INFORMATION_MESSAGE);
			} 					
			else {
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
							"Building of a parallel rule failed!",	
							"Parallel Rule:  "+parallelRule.getName(),
							JOptionPane.ERROR_MESSAGE);
			} 
		} else {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 
					"<html><body>"						
					+"It was not possible to build a parallel rule.",					
					"Parallel Rule",
					JOptionPane.ERROR_MESSAGE);
		}
	}
		
	JMenuItem mi;
	
	JMenuItem miRuleSeqValidated, 
				miConcurDisJointRule, 
				miConcurJointRule, miAllConcurJointRule,
				miConcurRuleByObjFlow,
				miEvalRuleSeq, miRuleSeqObjFlow, miParallelRule;
		
	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node;
	RuleSequenceTreeNodeData data;
	int locationRow;
	
	RuleSequence ruleSeq;
	List<EdRule> concurrentRules;
}
