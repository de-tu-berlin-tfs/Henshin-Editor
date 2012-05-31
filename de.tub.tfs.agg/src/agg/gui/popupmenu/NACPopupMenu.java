// $Id: NACPopupMenu.java,v 1.5 2010/09/23 08:21:33 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdNAC;
import agg.editor.impl.EdNestedApplCond;
import agg.gui.AGGAppl;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.xt_basis.GraphKind;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.OrdinaryMorphism;

public class NACPopupMenu extends JPopupMenu {

	public NACPopupMenu(GraGraTreeView tree) {
		super("NAC");
		this.treeView = tree;

		JMenuItem mi = add(new JMenuItem("Make due to RHS "));
		mi.setActionCommand("makeFromRHS");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeCopyFromRHS();
			}
		});
		addSeparator();
		
		mi = add(new JMenuItem("Copy           "));
		mi.setActionCommand("copyNAC");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});
		
		mi = add(new JMenuItem("Convert to GAC"));
		mi.setActionCommand("copyToGAC");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertToGAC();
			}
		});
		
		addSeparator();
		
		mi = add(new JMenuItem("Delete           Delete"));
		mi.setActionCommand("deleteNAC");
		mi.addActionListener(this.treeView);
		// mi.setMnemonic('D');

		addSeparator();
		
		this.disable = new JCheckBoxMenuItem("disabled");
		this.disable.setActionCommand("disableNAC");
		this.disable.addActionListener(this.treeView);
		add(this.disable);

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		// mi = new JMenuItem("Textual Comments");
		mi.setActionCommand("commentNAC");
		mi.addActionListener(this.treeView);
		// mi.setMnemonic('T');

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null) {
			return false;
		}
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			int pl = this.treeView.getTree().getPathForLocation(x, y).getPath().length;
			if (pl == 4 || pl == 5) {
				path = this.treeView.getTree().getPathForLocation(x, y);
				GraGraTreeNodeData 
				data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) 
								path.getLastPathComponent()).getUserObject();
				if (data != null && data.isNAC()) {
					nac = data.getNAC();
					if (!nac.getMorphism().isEnabled())
						this.disable.setSelected(true);
					else
						this.disable.setSelected(false);
					return true;
				}
			}
		}
		return false;
	}

	void copy() {
		if (nac != null) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
//			GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
//			TreePath rulePath = this.path.getParentPath();
			
			OrdinaryMorphism iso = nac.getMorphism().getTarget().isoCopy();
			OrdinaryMorphism ac = new OrdinaryMorphism(
					nac.getMorphism().getSource(), 
					iso.getTarget(),
					nac.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(nac.getMorphism(), iso)) {
				ac.setName(nac.getName()+"_clone");
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.NAC);
				
				EdNAC cn = nac.getRule().createNAC(ac);	
				nac.getRule().getNACs().remove(cn);
				cn.setLayoutByIndex(nac, true);
				int indx = nac.getRule().getNACs().indexOf(nac) +1;
				if (indx >= 0) {
					nac.getRule().getBasisRule().addNAC(indx, ac);
					nac.getRule().getNACs().add(indx, cn);
				
					treeView.putNACIntoTree(cn, (DefaultMutableTreeNode) node.getParent(),
							((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
				}
			}
		}
	}
	
	void makeCopyFromRHS() {
		if (nac != null) {
			if (treeView.getFrame() instanceof AGGAppl) {
				((AGGAppl)treeView.getFrame()).getGraGraEditor().getRuleEditor().doNACDuetoRHS();
			}
		}
	}
	
	void convertToGAC() {
		if (nac != null) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			
			OrdinaryMorphism iso = nac.getMorphism().getTarget().isoCopy();
			NestedApplCond ac = new NestedApplCond(
					nac.getMorphism().getSource(), 
					iso.getTarget(),
					nac.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(nac.getMorphism(), iso)) {
				ac.setName(nac.getName());
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.AC);
				
				nac.getRule().getBasisRule().addNestedAC(ac);
				EdNestedApplCond cn = (EdNestedApplCond) nac.getRule().createNestedAC(ac);	
				cn.setLayoutByIndex(nac, true);
				
				if (nac.getRule().getBasisRule().getNestedACsList().size() == 1) {
					treeView.putNestedACIntoTree(cn, (DefaultMutableTreeNode) node.getParent(), 0);
				}
				else {
					DefaultMutableTreeNode prev = this.treeView.getTreeNodeOfGrammarElement(
							nac.getRule().getNestedAC(nac.getRule().getNestedACs().size()-2));
					int indx = this.treeView.getTreeModel().getIndexOfChild(node.getParent(), prev);
					treeView.putNestedACIntoTree(cn, (DefaultMutableTreeNode) node.getParent(), indx + 1);
				}				
			}
		}
	}
	
	GraGraTreeView treeView;
	TreePath path;
	EdNAC nac;
	JMenuItem disable;
}
