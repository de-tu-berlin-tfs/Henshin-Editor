// $Id: NestedACPopupMenu.java,v 1.7 2010/09/23 08:21:33 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.attribute.AttrContext;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdPAC;
import agg.editor.impl.EdRule;
import agg.gui.AGGAppl;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.dialog.FormulaGraphGUI;
import agg.gui.treeview.nodedata.ApplFormulaTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.path.GrammarTreeNode;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.OrdinaryMorphism;



public class NestedACPopupMenu extends JPopupMenu {

	public NestedACPopupMenu(GraGraTreeView tree) {
		super("General Application Condition");
		this.treeView = tree;

		miRHS = add(new JMenuItem("Make due to RHS"));
		miRHS.setEnabled(false);
		miRHS.setActionCommand("makeFromRHS");
//		miRHS.addActionListener(this.treeView);
		miRHS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeCopyFromRHS();
			}
		});	
		addSeparator();
		
		JMenuItem mi = add(new JMenuItem("New GAC"));
		mi.setActionCommand("newNestedAC");
		mi.addActionListener(this.treeView);
				
		mi = new JMenuItem("Set Formula above GACs");
		this.add(mi);
		mi.setActionCommand("setFormulaAboveACs");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFormula();
			}
		});
		
		addSeparator();
		
		mi = add(new JMenuItem("Copy           "));
		mi.setActionCommand("copyGAC");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});
		
		addSeparator();
		
		mi = add(new JMenuItem("Convert to NAC"));
		mi.setActionCommand("convertToNAC");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertToNAC();
			}
		});
		mi = add(new JMenuItem("Convert to PAC"));
		mi.setActionCommand("convertToPAC");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertToPAC();
			}
		});
		
		addSeparator();
		
		mi = add(new JMenuItem("Delete           Delete"));
		mi.setActionCommand("deleteNestedAC");
		mi.addActionListener(this.treeView);

		addSeparator();
		
		this.miDisable = add(new JCheckBoxMenuItem("disabled"));
		this.miDisable.setActionCommand("disableNestedAC");
		this.miDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBoxMenuItem)e.getSource()).isSelected()) {
					NestedACPopupMenu.this.cond.getMorphism().setEnabled(false);
					if (NestedACPopupMenu.this.rule != null 
							&& !((NestedApplCond)NestedACPopupMenu.this.cond.getMorphism()).getVarTagInFormula().isEmpty()) {
						((NestedApplCond)NestedACPopupMenu.this.cond.getMorphism()).setVarTagInFormula("");
						NestedACPopupMenu.this.rule.getBasisRule().setFormula("");
						if (NestedACPopupMenu.this.formula != null) {
							NestedACPopupMenu.this.treeView.getTreeModel().removeNodeFromParent(NestedACPopupMenu.this.formula.getTreeNode());
							NestedACPopupMenu.this.formula = null;
						}
					} else if (NestedACPopupMenu.this.parCond != null 
							&& !((NestedApplCond)NestedACPopupMenu.this.cond.getMorphism()).getVarTagInFormula().isEmpty()) {
						((NestedApplCond)NestedACPopupMenu.this.cond.getMorphism()).setVarTagInFormula("");
						((NestedApplCond)NestedACPopupMenu.this.parCond.getMorphism()).setFormula("");
						if (NestedACPopupMenu.this.formula != null) {
							NestedACPopupMenu.this.treeView.getTreeModel().removeNodeFromParent(NestedACPopupMenu.this.formula.getTreeNode());
							NestedACPopupMenu.this.formula = null;
						}
					}
					NestedACPopupMenu.this.data.update();
				} else {
					NestedACPopupMenu.this.cond.getMorphism().setEnabled(true);
					NestedACPopupMenu.this.data.update();
				}
				NestedACPopupMenu.this.treeView.getTree().treeDidChange();
			}
		});

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		mi.setActionCommand("commentNestedAC");
		mi.addActionListener(this.treeView);

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {			
			this.path = this.treeView.getTree().getPathForLocation(x, y);
			this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			this.data = (GraGraTreeNodeData) this.node.getUserObject();
							
			if (this.data != null && this.data.isNestedAC()) {
				GrammarTreeNode.expand(this.treeView, this.node, this.path);
				
				this.parData = (GraGraTreeNodeData) 
						((DefaultMutableTreeNode)this.node.getParent()).getUserObject();
				if (this.parData.isRule()) {
					this.rule = this.parData.getRule();
					this.miRHS.setEnabled(true);
				}
				else if (this.parData.isNestedAC()) {
					this.parCond = this.parData.getNestedAC();
					this.miRHS.setEnabled(false);	
				}
				GraGraTreeNodeData firstChildData = (GraGraTreeNodeData) 
							((DefaultMutableTreeNode)((DefaultMutableTreeNode)this.node.getParent())
									.getFirstChild()).getUserObject();
				if (firstChildData.isApplFormula()) 
					this.formula = firstChildData;
				
				this.posX = x; this.posY = y;
				this.cond = this.data.getNestedAC();
				if (!this.cond.getMorphism().isEnabled())
					this.miDisable.setSelected(true);
				else
					this.miDisable.setSelected(false);
				return true;
			}
		}
		return false;
	}

	void setFormula() {
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
		d.setLocation(this.posX+20, this.posY+20);
		while (true) {
			d.setVisible(true);
			if (!d.isCanceled()) {
				boolean formulaChanged = d.isChanged();
				String f = d.getFormula();
				if (!this.cond.getNestedMorphism().setFormula(f, list)) {
					JOptionPane
					.showMessageDialog(
							this.treeView.getFrame(),
							"The formula definition failed. Please correct.",
							" Formula failed ", JOptionPane.WARNING_MESSAGE);
				}
				else if (formulaChanged) {	
					insertFormulaIntoACTreeNode(f, allNestedACs);
					this.cond.getGraGra().setChanged(true);	
					break;
				} else
					break;
			} else break;
		}	
	}
	
	void insertFormulaIntoACTreeNode(
			final String f, 
			final List<EdNestedApplCond> allVarsObj) {
		if (f.length() > 0) {			
			Object child = (this.node.getChildCount() > 0)? this.treeView.getTreeModel().getChild(this.node, 0): null;
			if (child != null) {
				// remove existing formula from rule subtree
				if (((GraGraTreeNodeData) ((DefaultMutableTreeNode) child)
						.getUserObject()).isApplFormula()) {
					this.treeView.getTreeModel().removeNodeFromParent((DefaultMutableTreeNode)child);
				}
			}	
			// add formula tree node into nestedAC subtree
			if (!"true".equals(f)) {
				final GraGraTreeNodeData conddata = new ApplFormulaTreeNodeData(
						f, true, this.cond);
				conddata.setString(f);
				final DefaultMutableTreeNode condnode = new DefaultMutableTreeNode(
								conddata);
				conddata.setTreeNode(condnode);
				this.treeView.getTreeModel().insertNodeInto(condnode, this.node, 0);
			}			
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
	
	void copy() {		
		if (cond != null) {
			AttrContext attrContxt = cond.getRule().getBasisRule().getRight().getAttrContext();
			if (cond.getParent() != null)
				attrContxt = cond.getMorphism().getImage().getAttrContext();
			
			NestedApplCond ac = new NestedApplCond(			
					cond.getMorphism().getSource(), 
					BaseFactory.theFactory().createGraph(cond.getMorphism().getSource().getTypeSet()),
					attrContxt);
			ac.getImage().setAttrContext(attrContxt);
			
			Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
			Enumeration<GraphObject> gos = cond.getMorphism().getSource().getElements();
			while (gos.hasMoreElements()) {
				GraphObject go = gos.nextElement();
				table.put(go, go);
			}
			BaseFactory.theFactory().copyGraph(cond.getBasisGraph(), ac.getImage(), table);
			BaseFactory.theFactory().copyMorph(cond.getMorphism(), ac, table);			
			BaseFactory.theFactory().copyNestedAC(cond.getNestedMorphism(), ac, table);

			ac.setName(cond.getName()+"_clone");
				
			if (cond.getParent() == null) {
				EdNestedApplCond cp = (EdNestedApplCond) cond.getRule().createNestedAC(ac);	
				cond.getRule().getNestedACs().remove(cp);
				int indx = cond.getRule().getNestedACs().indexOf(cond) +1;
				if (indx >= 0) {						
					cond.getRule().getBasisRule().addNestedAC(indx, ac);
					cond.getRule().getNestedACs().add(indx, cp);	
					cp.setLayoutByIndex(cond, true);
					treeView.putNestedACIntoTree(cp, (DefaultMutableTreeNode) node.getParent(),
							((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
				}					
			}
			else {
				EdNestedApplCond parAC = cond.getParent();
				EdNestedApplCond cp = (EdNestedApplCond) parAC.createNestedAC(ac);
				parAC.getNestedACs().remove(cp);
				int indx = parAC.getNestedACs().indexOf(cond) +1;
				if (indx >= 0) {
					parAC.getNestedMorphism().addNestedAC(indx, ac);
					parAC.getNestedACs().add(indx, cp);	
					cp.setLayoutByIndex(cond, true);
					treeView.putNestedACIntoTree(cp, (DefaultMutableTreeNode) node.getParent(),
							((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
				}
			}
		}
	}
	
	void convertToNAC() {
		if (cond != null) {
			OrdinaryMorphism iso = cond.getMorphism().getTarget().isoCopy();
			OrdinaryMorphism ac = new OrdinaryMorphism(
					cond.getMorphism().getSource(), 
					iso.getTarget(),
					cond.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(cond.getMorphism(), iso)) {
				ac.setName(cond.getName());
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.NAC);
				
				cond.getRule().getBasisRule().addNAC(ac);
				EdNAC cn = cond.getRule().createNAC(ac);	
				cn.setLayoutByIndex(cond, true);
				
				DefaultMutableTreeNode rn = this.treeView.getTreeNodeOfGrammarElement(
						cond.getRule());
				
				int indx = cond.getRule().getNestedACs().size() 
							+ cond.getRule().getNACs().size()-1;
				if (rn.getChildCount() > 0) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode)rn.getFirstChild();		
					if (child != null
						&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
						indx++;
					}
				}
				treeView.putNACIntoTree(cn, rn, indx);
			}
		}
	}
	
	void convertToPAC() {
		if (cond != null) {			
			OrdinaryMorphism iso = cond.getMorphism().getTarget().isoCopy();
			OrdinaryMorphism ac = new OrdinaryMorphism(
					cond.getMorphism().getSource(), 
					iso.getTarget(),
					cond.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(cond.getMorphism(), iso)) {
				ac.setName(cond.getName());
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.PAC);
				
				cond.getRule().getBasisRule().addPAC(ac);
				EdPAC cn = cond.getRule().createPAC(ac);	
				cn.setLayoutByIndex(cond, true);
				
				DefaultMutableTreeNode rn = this.treeView.getTreeNodeOfGrammarElement(
						cond.getRule());
				
				int indx = cond.getRule().getNestedACs().size() 
							+ cond.getRule().getNACs().size() 
							+ cond.getRule().getPACs().size()-1;
				if (rn.getChildCount() > 0) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode)rn.getFirstChild();		
					if (child != null
						&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
						indx++;
					}
				}			
				treeView.putPACIntoTree(cn, rn, indx);
			}
		}
	}
	
	void makeCopyFromRHS() {
		if (this.parCond == null) {
			if (treeView.getFrame() instanceof AGGAppl) {
				((AGGAppl)treeView.getFrame()).getGraGraEditor().getRuleEditor().doGACDuetoRHS();
			}
		}
	}
	
	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node;
	GraGraTreeNodeData data, parData, formula;
	EdNestedApplCond cond, parCond;
	EdRule rule;
	
	int posX, posY; 
	private JMenuItem miDisable, miRHS;
}
