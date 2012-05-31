// $Id: RuleSchemePopupMenu.java,v 1.17 2010/10/16 22:44:43 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;
import agg.gui.event.TreeViewEvent;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.AmalgamatedRuleTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.nodedata.MultiRuleTreeNodeData;
import agg.xt_basis.agt.AmalgamatedRule;
import agg.xt_basis.agt.Covering;
import agg.xt_basis.agt.RuleScheme;

public class RuleSchemePopupMenu extends JPopupMenu {

	JMenuItem mi;
	
	public RuleSchemePopupMenu(GraGraTreeView tree) {
		super("RuleScheme");
		this.treeView = tree;
		
		this.mi = add(new JMenuItem("New Multi Rule"));
		this.mi.setActionCommand("newMultiRule");
//		this.mi.addActionListener(treeView);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMultiRule();
			}
		});
		
		addSeparator();
		
		this.miParallelKernelMatch =  add(new JCheckBoxMenuItem("parallel Match of Kernel Rule"));
		this.miParallelKernelMatch.setActionCommand("parallelKernelMatch");		
//		this.miParallelKernelMatches.addActionListener(treeView);
		this.miParallelKernelMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setParallelKernelMatch(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		
		this.miDisjointMultiMatch =  add(new JCheckBoxMenuItem("disjoint Match of Multi Rule"));
		this.miDisjointMultiMatch.setActionCommand("disjointMultiMatch");
//		this.miDisjointMultiMatches.addActionListener(treeView);
		this.miDisjointMultiMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDisjointMultiMatch(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});

		this.miConflictFreeMultiMatch = new JCheckBoxMenuItem("conflict free Match of Multi Rule");
		add(this.miConflictFreeMultiMatch);
		this.miConflictFreeMultiMatch.setActionCommand("conflictFreeMultiMatch");
//		this.miConflictFreeMultiMatches.addActionListener(treeView);
		this.miConflictFreeMultiMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setConflictFreeMultiMatch(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		
		this.miAtLeastOneMultiMatch =  add(new JCheckBoxMenuItem("apply at least One Multi Rule"));
		this.miAtLeastOneMultiMatch.setActionCommand("atLeastOneMultiMatch");
//		miAtLeastOneMultiMatches.addActionListener(treeView);
		this.miAtLeastOneMultiMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAtLeastOneMultiMatchRequired(((JCheckBoxMenuItem)e.getSource()).isSelected());
			}
		});
		
		addSeparator();
		
		this.mi = add(new JMenuItem("Create Amalgamated Rule"));
		this.mi.setActionCommand("createAmalgamatedRule");
//		this.mi.addActionListener(treeView);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAmalgamatedRule();
			}
		});
		
		addSeparator();
		
		this.mi = add(new JMenuItem(
				"Set Layer                                 "));
		this.mi.setActionCommand("setRuleLayer");
		this.mi.addActionListener(this.treeView);
		// mi.setMnemonic('L');
		this.mi.setEnabled(true);

		this.mi = add(new JMenuItem(
				"Set Priority                              "));
		this.mi.setActionCommand("setRulePriority");
		this.mi.addActionListener(this.treeView);
		// mi.setMnemonic('P');
		this.mi.setEnabled(true);
		
		addSeparator();
		
//		miParallelApply = new JRadioButtonMenuItem("Parallel Matching");
//		miParallelApply.setActionCommand("allowParallelApplyRuleScheme");
//		miParallelApply.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (((JRadioButtonMenuItem) e.getSource()).isSelected())
//					testRule.getBasisRule().setParallelMatchingEnabled(true);
//				else
//					testRule.getBasisRule().setParallelMatchingEnabled(false);
//			}
//		});
//		add(miParallelApply);
//		miParallelApply.setEnabled(false);
		
//		addSeparator();

		this.mi = add(new JMenuItem("Move"));
		this.mi.setActionCommand("moveRuleScheme");
		this.mi.addActionListener(this.treeView);
		// mi.setMnemonic('M');
		this.mi.setEnabled(true);
		
		addSeparator();
		
		this.mi = add(new JMenuItem("Copy"));
		this.mi.setActionCommand("copyRuleScheme");
//		this.mi.addActionListener(treeView);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyRuleScheme();
			}
		});
		this.mi.setEnabled(true);
		
		this.mi = add(new JMenuItem("Make Inverse RuleScheme"));
		this.mi.setActionCommand("reverseRuleScheme");
//		this.mi.addActionListener(this.treeView);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reverseRuleScheme();
			}
		});
		this.mi.setEnabled(true);
		
		addSeparator();

		this.mi = add(new JMenuItem("Delete                "));
		this.mi.setActionCommand("deleteRuleScheme");
		this.mi.addActionListener(this.treeView);
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (treeView.hasMultipleSelection())
					treeView.delete("selected");
				else
					deleteRuleScheme();
			}
		});
		// this.mi.setMnemonic('D');
		this.mi.setEnabled(true);
		
		addSeparator();

		this.miDisabled = new JCheckBoxMenuItem("disabled");
		this.miDisabled.setActionCommand("disableRuleScheme");
		this.miDisabled.addActionListener(this.treeView);
		add(this.miDisabled);
//		miDisabled.setEnabled(true);
		
		addSeparator();

//		miAnimated = new JRadioButtonMenuItem("animated");
//		miAnimated.setActionCommand("animatedRule");
////		miAnimated.addActionListener(treeView);
//		miAnimated.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				testRule.setAnimated(((JRadioButtonMenuItem) e.getSource()).isSelected());
//			}
//		});
//		add(miAnimated);
		
//		addSeparator();
		
		this.miWait = new JCheckBoxMenuItem("Wait Before Applying Rule");
		this.miWait.setActionCommand("waitBeforeApplyRule");
//		this.miWait.addActionListener(treeView);
		this.miWait.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuleSchemePopupMenu.this.ruleScheme.getBasisRule().setWaitBeforeApplyEnabled(((JCheckBoxMenuItem) e.getSource()).isSelected());
			}
		});
		add(this.miWait);
		
		addSeparator();
		
		this.mi = add(new JMenuItem("Textual Comments"));
		// this.mi = new JMenuItem("Textual Comments");
		this.mi.setActionCommand("commentRuleScheme");
		this.mi.addActionListener(this.treeView);
		// mi.setMnemonic('T');

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		this.locationRow = this.treeView.getTree().getRowForLocation(x, y);
		if (this.locationRow != -1
				&& this.treeView.getTree().getPathForLocation(x, y).getPath().length == 3) {
			this.path = this.treeView.getTree().getPathForLocation(x, y);
			this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			this.ruleScheme = this.treeView.getRuleScheme((DefaultMutableTreeNode) path.getLastPathComponent());
			if (this.ruleScheme != null) {
				// treeView.selectPath(x,y);
					
				this.miParallelKernelMatch.setSelected(this.ruleScheme.getBasisRuleScheme().parallelKernelMatch());
				this.miDisjointMultiMatch.setSelected(this.ruleScheme.getBasisRuleScheme().disjointMultiMatches());
				this.miConflictFreeMultiMatch.setSelected(this.ruleScheme.getBasisRuleScheme().checkDeleteUseConflictRequired());
				this.miAtLeastOneMultiMatch.setSelected(this.ruleScheme.getBasisRuleScheme().atLeastOneMultiMatchRequired());
					
				this.miDisabled.setSelected(!this.ruleScheme.getBasisRule().isEnabled());
				this.miWait.setSelected(this.ruleScheme.getBasisRule().isWaitBeforeApplyEnabled());
				return true;
			} 
		} 
		return false;
	}

	protected void addMultiRule() {
		if (this.treeView.getSelectedPath() == null) {
			String s = "Bad selection.\n Please select a rule scheme.";
			JOptionPane.showMessageDialog(this.treeView.getFrame(), s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 			
//		TreePath graPath = this.treeView.getSelectedPath().getParentPath();
		TreePath rsPath = this.treeView.getSelectedPath();
		DefaultMutableTreeNode rsNode = (DefaultMutableTreeNode) rsPath.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) rsNode.getUserObject();
		
		if (data != null && data.isRuleScheme()) {
			EdRuleScheme rs = data.getRuleScheme();		
			EdRule multiRule = rs.addMultiRule("MultiRule"+String.valueOf(rs.getBasisRuleScheme().getCountOfMultiRules()));
			multiRule.update();
			
			GraGraTreeNodeData sdMultiRule = new MultiRuleTreeNodeData(multiRule);
			DefaultMutableTreeNode newMultiRuleNode = new DefaultMutableTreeNode(sdMultiRule);
			sdMultiRule.setTreeNode(newMultiRuleNode);
			
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.treeView.selPath
																.getLastPathComponent();
			int newIndex = rs.getBasisRuleScheme().getCountOfMultiRules();
			this.treeView.getTreeModel().insertNodeInto(newMultiRuleNode, parent, newIndex);
		}
		
	}
	
	protected void deleteRuleScheme() {
		TreePath graPath = null;
		TreePath rsPath = null;
		DefaultMutableTreeNode rsNode = null;
		GraGraTreeNodeData data = null;
		if (this.treeView.getSelectedPath() == null) {
			String s = "Bad selection.\n Please select a rule scheme.";
			JOptionPane.showMessageDialog(this.treeView.getFrame(), s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 		
		graPath = this.treeView.getSelectedPath().getParentPath();
		rsPath = this.treeView.getSelectedPath();
		rsNode = (DefaultMutableTreeNode) rsPath.getLastPathComponent();
		data = (GraGraTreeNodeData) rsNode.getUserObject();
		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			if (data.getRuleScheme() != this.treeView.getCurrentRuleScheme()) {
				int answer = this.treeView.removeWarning("RuleScheme");
				if (answer == JOptionPane.YES_OPTION) {
					int row = this.treeView.getTree().getRowForPath(rsPath);
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, rsPath));
					this.treeView.getTreeModel().removeNodeFromParent(rsNode);
					EdRule rs = data.getRuleScheme();
	//				treeView.getGraGraStore().storeRuleScheme(rs.getGraGra(), (EdRuleScheme)rs);
					graData.getGraGra().removeRule(rs);
	
					row--;
					this.treeView.setEditPath(row);					
					this.treeView.setFlagForNew();
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.treeView.editorPath));
				}
			} else {
				int answer = this.treeView.removeCurrentObjectWarning("RuleScheme");
				if (answer == JOptionPane.YES_OPTION) {
					int row = this.treeView.getTree().getRowForPath(rsPath);
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, rsPath));
					this.treeView.getTreeModel().removeNodeFromParent(rsNode);
					EdRule rs = data.getRule();
					treeView.getGraGraStore().storeRuleScheme(rs.getGraGra(), (EdRuleScheme)rs);
					graData.getGraGra().removeRule(rs);
	
					row--;
					this.treeView.setEditPath(row);					
					this.treeView.setFlagForNew();
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.treeView.editorPath));
					
					if ((((GraGraTreeNodeData)((DefaultMutableTreeNode)
							this.treeView.selPath.getLastPathComponent())
												.getUserObject()).isGraph()
							&& !this.treeView.getCurrentGraGra().getRules().isEmpty())) {						
						row++;
						this.treeView.setEditPath(row);					
						this.treeView.setFlagForNew();
						this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.SELECTED, this.treeView.editorPath));
					}
				}
			}
		}
	}
	
	protected void createAmalgamatedRule() {     
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.treeView.selPath.getLastPathComponent();          
		EdRuleScheme rs = this.treeView.getRuleScheme(node);
      	if(rs == null) {
      		JOptionPane.showMessageDialog(null, "Bad selection. \nPlease select a rule scheme.");
      		return;
      	}
      	if (rs.getGraGra().isEditable()) {
	      	final RuleScheme baseRS = rs.getBasisRuleScheme();
	      	if (baseRS.getAmalgamatedRule() == null) {
		      	if (baseRS.isValid()) {
		      		baseRS.clearMatchesOfMultiRules();
		      		
		      		if (!baseRS.isInputParameterSet(true)) {
		      			this.treeView.fireTreeViewEvent(
		      					new TreeViewEvent(this.treeView, TreeViewEvent.INPUT_PARAMETER_NOT_SET, baseRS));
		
		      			 JOptionPane.showMessageDialog(null, 
										"Please set Input Parameter of the rule scheme\n"
						      					 +"and call <Create Amalgamated Rule> again.",
										"Input Parameter Not Set",
										JOptionPane.WARNING_MESSAGE);	  
		      			return;	
		      		} 
		      		baseRS.applyValueOfInputParameter();
		      		
					final Covering cov = new Covering(
										baseRS, 
										rs.getGraGra().getBasisGraGra().getGraph(), 
										rs.getGraGra().getBasisGraGra().getMorphismCompletionStrategy());      
					if (cov.amalgamate()
							&& cov.getAmalgamatedRule() != null) {
						
						final AmalgamatedRule amalgamatedRule =  cov.getAmalgamatedRule();	
						baseRS.setAmalgamatedRule(amalgamatedRule);	
						
						final EdRule edAmalgamatedRule = new EdRule(amalgamatedRule);
			       
						amalgamatedRule.setName("Amalgamation");
						edAmalgamatedRule.update();
						rs.setAmalgamatedRule(edAmalgamatedRule);	       
						
						//add amalgamated Rule to the tree       
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.treeView.selPath.getLastPathComponent();
						int newIndex = parent.getChildCount(); 				
						GraGraTreeNodeData sdRule = new AmalgamatedRuleTreeNodeData(edAmalgamatedRule); 				
						DefaultMutableTreeNode newRuleNode = new DefaultMutableTreeNode(sdRule); 					
						sdRule.setTreeNode(newRuleNode);   
						this.treeView.getTreeModel().insertNodeInto(newRuleNode, parent, newIndex);
							
						this.treeView.getTree().expandPath(this.treeView.selPath);
						this.treeView.getTree().treeDidChange();
						
						// add NACs,  for test only
		//				for (int j = 0; j < edAmalgamatedRule.getNACs().size(); j++) {
		//					final EdNAC nac = edAmalgamatedRule.getNACs().elementAt(j);
		//					nac.layoutBasisGraph(new Dimension(150, 150));
		//					final GraGraTreeNodeData nacsd = new NACTreeNodeData(nac);
		//					final DefaultMutableTreeNode nacNode = new DefaultMutableTreeNode(nacsd);
		//					nacsd.setTreeNode(nacNode);
		//					treeView.getTreeModel().insertNodeInto(nacNode, newRuleNode, 
		//							newRuleNode.getChildCount());
		//				}
										
						TreePath path = this.treeView.getTreePathOfGrammarElement(edAmalgamatedRule);
						if (path != null) {
							int row = this.treeView.getTree().getRowForPath(path);
							this.treeView.selectPath(row);
						}						
					}
					else {        
						JOptionPane.showMessageDialog( 
								this.treeView.getFrame(),
									"Amalgamation failed.\n"
									+cov.getErrorMessage(),
									"Rule Scheme: "+rs.getBasisRuleScheme().getName(),
									JOptionPane.ERROR_MESSAGE);
					} 
				}     
				else {        
					JOptionPane.showMessageDialog( 
							this.treeView.getFrame(),
								"Amalgamation is not possible.\n"
								+"Please check the rule scheme: \n"
								+"embedding of the kernel rule into the multi rules,\n"
								+"but also attribute settings.",
								"Invalid Rule Scheme",
								JOptionPane.ERROR_MESSAGE);
				}
	      	} else {
	      		JOptionPane.showMessageDialog( 
	      				this.treeView.getFrame(),
						"An amalgamated rule does already exist.\n"
						+"Please delete it first.",
						"Rule Scheme: "+rs.getBasisRuleScheme().getName(),
						JOptionPane.WARNING_MESSAGE);
	      	}
      	}
      	else
      		this.treeView.lockWarning();
	}  

	protected void setDisjointMultiMatch(boolean b) {	
		if (this.ruleScheme != null) {				
			this.ruleScheme.getBasisRuleScheme().setDisjointMultiMatches(b);
		}			
	}
	
	protected void setConflictFreeMultiMatch(boolean b) {	
		if (this.ruleScheme != null) {				
			this.ruleScheme.getBasisRuleScheme().setCheckDeleteUseConflictRequired(b);
		}			
	}
	
	protected void setParallelKernelMatch(boolean b) {	
		if (this.ruleScheme != null) {				
			this.ruleScheme.getBasisRuleScheme().setParallelKernelMatch(b);
		}			
	}
	
	protected void setAtLeastOneMultiMatchRequired(boolean b) {	
		if (this.ruleScheme != null) {				
			this.ruleScheme.getBasisRuleScheme().setAtLeastOneMultiMatchRequired(b);
		}			
	}
	
	void copyRuleScheme() {
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = null;
		if (this.path == null) {
			String s = "Bad selection.\n Please select a rule scheme.";
			JOptionPane.showMessageDialog(null, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		graPath = this.path.getParentPath();
		
		if (graPath != null) {
			DefaultMutableTreeNode 
			graNode = (DefaultMutableTreeNode) graPath.getLastPathComponent();
			GraGraTreeNodeData 
			graData = (GraGraTreeNodeData) graNode.getUserObject();
			EdRuleScheme rsClone = graData.getGraGra().cloneRuleScheme(data.getRuleScheme(), true);
			this.treeView.putRuleSchemeIntoTree(rsClone, 
					(DefaultMutableTreeNode) node.getParent(),
					((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
			this.treeView.getTree().treeDidChange();
			
			this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.RULE_ADDED, this.path));
		}
	}
	
	void reverseRuleScheme() {
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = null;
		if (this.path == null) {
			String s = "Bad selection.\n Please select a rule scheme.";
			JOptionPane.showMessageDialog(null, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		graPath = this.path.getParentPath();
		
		if (graPath != null) {
			DefaultMutableTreeNode 
			graNode = (DefaultMutableTreeNode) graPath.getLastPathComponent();
			GraGraTreeNodeData 
			graData = (GraGraTreeNodeData) graNode.getUserObject();
			EdRuleScheme invRS = graData.getGraGra().reverseRuleScheme(data.getRuleScheme(), true);
			if (invRS != null) {
				this.treeView.putRuleSchemeIntoTree(invRS, 
						(DefaultMutableTreeNode) node.getParent(),
						((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
				
				this.treeView.getTree().treeDidChange();
				
				if (!invRS.getBasisRule().getErrorMsg().equals("")) {
					String warnMsg = invRS.getBasisRule().getErrorMsg().replaceAll(";", "<br>");
					warnMsg = warnMsg.replaceAll("\n", "<br>");
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"							
							+"During reverting the following occurred:<br><br>"
							+"<font color=\"#FF0000\">"
							+warnMsg	
							+"</font>"
							+"<br>The new inverse rule sheme:  "
							+invRS.getName()
							+"<br>is added after its original rule scheme into the rule set.<br><br>",						
							"Inverse Rule Scheme:  "+invRS.getName(),
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(treeView.getFrame(), 
							"<html><body>"							
							+"Reverting was successful.<br><br>"						
							+"The new inverse rule scheme:  "
							+invRS.getName()+"<br>"
							+"is added after its original rule scheme into the rule set.<br><br>",	
							"Inverse Rule Scheme:  "+invRS.getName(),
							JOptionPane.INFORMATION_MESSAGE);
				}
				this.treeView.fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.RULE_ADDED, this.path));
			}
		}
	}

	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node;

	private int locationRow;
	
	protected EdRuleScheme ruleScheme;

	private JMenuItem miDisabled, 
//					miPAC, miParallelApply, miAnimated, 
						miDisjointMultiMatch, miConflictFreeMultiMatch,
						miParallelKernelMatch, miAtLeastOneMultiMatch, miWait;
}
