// $Id: PACPopupMenu.java,v 1.5 2010/09/23 08:21:33 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdPAC;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.xt_basis.GraphKind;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.OrdinaryMorphism;

public class PACPopupMenu extends JPopupMenu {

	public PACPopupMenu(GraGraTreeView tree) {
		super("PAC");
		this.treeView = tree;
		/*
		 * JMenuItem mi = (JMenuItem) add(new JMenuItem("Show"));
		 * mi.setActionCommand("showPAC"); mi.addActionListener(treeView);
		 * 
		 * mi = (JMenuItem) add(new JMenuItem("Hide"));
		 * mi.setActionCommand("hidePAC"); mi.addActionListener(treeView);
		 */

		JMenuItem mi = add(new JMenuItem("Copy           "));
		mi.setActionCommand("copyPAC");
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
		mi.setActionCommand("deletePAC");
		mi.addActionListener(this.treeView);
		// mi.setMnemonic('D');

		this.disable = new JCheckBoxMenuItem("disabled");
		this.disable.setActionCommand("disablePAC");
		this.disable.addActionListener(this.treeView);
		add(this.disable);

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		// mi = new JMenuItem("Textual Comments");
		mi.setActionCommand("commentPAC");
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
				if (data != null && data.isPAC()) {
					pac = data.getPAC();
					if (!pac.getMorphism().isEnabled())
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
		if (pac != null) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
//			GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
//			TreePath rulePath = this.path.getParentPath();
			
			OrdinaryMorphism iso = pac.getMorphism().getTarget().isoCopy();
			OrdinaryMorphism ac = new OrdinaryMorphism(
					pac.getMorphism().getSource(), 
					iso.getTarget(),
					pac.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(pac.getMorphism(), iso)) {
				ac.setName(pac.getName()+"_clone");
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.PAC);
				
				EdPAC cp = pac.getRule().createPAC(ac);	
				pac.getRule().getPACs().remove(cp);
				cp.setLayoutByIndex(pac, true);
				int indx = pac.getRule().getPACs().indexOf(pac) +1;
				if (indx >= 0) {
					pac.getRule().getBasisRule().addPAC(indx, ac);
					pac.getRule().getPACs().add(indx, cp);
				
					treeView.putPACIntoTree(cp, (DefaultMutableTreeNode) node.getParent(),
							((DefaultMutableTreeNode) node.getParent()).getIndex(node) + 1);
				}
			}
		}
	}
	
	void convertToGAC() {
		if (pac != null) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
//			GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
//			TreePath rulePath = this.path.getParentPath();
			
			OrdinaryMorphism iso = pac.getMorphism().getTarget().isoCopy();
			NestedApplCond ac = new NestedApplCond(
					pac.getMorphism().getSource(), 
					iso.getTarget(),
					pac.getRule().getBasisRule().getRight().getAttrContext());
			if (ac.completeDiagram3(pac.getMorphism(), iso)) {
				ac.setName(pac.getName());
				ac.getImage().setAttrContext(ac.getAttrContext());
				ac.getImage().setKind(GraphKind.AC);
				
				pac.getRule().getBasisRule().addNestedAC(ac);
				EdNestedApplCond cn = (EdNestedApplCond) pac.getRule().createNestedAC(ac);	
				cn.setLayoutByIndex(pac, true);
				
				if (pac.getRule().getBasisRule().getNestedACsList().size() == 1) {
					treeView.putNestedACIntoTree(cn, (DefaultMutableTreeNode) node.getParent(), 0);
				}
				else {
					DefaultMutableTreeNode prev = this.treeView.getTreeNodeOfGrammarElement(
							pac.getRule().getNestedAC(pac.getRule().getNestedACs().size()-2));
					int indx = this.treeView.getTreeModel().getIndexOfChild(node.getParent(), prev);
					treeView.putNestedACIntoTree(cn, (DefaultMutableTreeNode) node.getParent(),indx + 1);
				}
			}
		}
	}
	
	GraGraTreeView treeView;
	TreePath path;
	EdPAC pac;
	JMenuItem disable;
}
