// $Id: TypeGraphPopupMenu.java,v 1.2 2010/09/23 08:21:34 olga Exp $

package agg.gui.popupmenu;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTextualComment;
import agg.xt_basis.TypeSet;

/**
 * This context menu displayed on top of the type graph. Within this menu one can
 * select which level of type graph check should be applied to all other graphs.
 * The menu actions will be processed in {@link GraGraTreeView}.
 * 
 * @author $Author: olga $
 * @version $Id: TypeGraphPopupMenu.java,v 1.2 2010/09/23 08:21:34 olga Exp $
 */
public class TypeGraphPopupMenu extends JPopupMenu {


	public TypeGraphPopupMenu(GraGraTreeView tree) {
		super("Type Graph");

		this.treeView = tree;

		ButtonGroup states = new ButtonGroup();

		this.disabled = new JRadioButtonMenuItem("disabled");
		this.disabled.setActionCommand("checkTypeGraph.DISABLED");
//		this.disabled.addActionListener(this.treeView);
		this.disabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTypeGraphLevel(TypeSet.DISABLED);
			}
		});
		states.add(this.disabled);
		this.add(this.disabled);

//		enabledInheritance = new JRadioButtonMenuItem("Inheritance only");
//		enabledInheritance.setActionCommand("checkTypeGraph.ENABLED_INHERITANCE");
//		enabledInheritance.addActionListener(this.treeView);
//		enabledInheritance.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setTypeGraphLevel(TypeSet.ENABLED_INHERITANCE);
//			}
//		});
//		states.add(enabledInheritance);
//		this.add(enabledInheritance);
		
		this.enabled = new JRadioButtonMenuItem("enabled");
		this.enabled.setActionCommand("checkTypeGraph.ENABLED");
//		this.enabled.addActionListener(this.treeView);
		this.enabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTypeGraphLevel(TypeSet.ENABLED);
			}
		});
		states.add(this.enabled);
		this.add(this.enabled);

		this.enabledMax = new JRadioButtonMenuItem("enabled with max");
		this.enabledMax.setActionCommand("checkTypeGraph.ENABLED_MAX");
//		this.enabledMax.addActionListener(this.treeView);
		this.enabledMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTypeGraphLevel(TypeSet.ENABLED_MAX);
			}
		});
		states.add(this.enabledMax);
		this.add(this.enabledMax);

		this.enabledMaxMin = new JRadioButtonMenuItem("enabled with min and max");
		this.enabledMaxMin.setActionCommand("checkTypeGraph.ENABLED_MAX_MIN");
//		this.enabledMaxMin.addActionListener(this.treeView);
		this.enabledMaxMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTypeGraphLevel(TypeSet.ENABLED_MAX_MIN);
			}
		});
		states.add(this.enabledMaxMin);
		this.add(this.enabledMaxMin);

		this.addSeparator();

		JMenuItem mi = add(new JMenuItem("Delete                  Delete"));
		mi.setActionCommand("deleteTypeGraph");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (treeView.hasMultipleSelection())
					treeView.delete("selected");
				else
					treeView.deleteTypeGraph(node, path, true);
			}
		});
		
		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		mi.setActionCommand("commentTypeGraph");
//		mi.addActionListener(this.treeView);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editComments();
			}
		});
		this.pack();
		this.setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		// no Tree?
		if (this.treeView == null) {
			return false;
		}

		// not an object in the Tree
		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			// is it a level 3 object;
			if (this.treeView.getTree().getPathForLocation(x, y).getPath().length == 3) {
				// get the type graph there, if there is one
				this.path = this.treeView.getTree().getPathForLocation(x, y);
				this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();
				this.graph = this.treeView.getGraph(this.node);
				// get the gragra for the graph
				EdGraGra gragra = this.treeView
						.getGraGra((DefaultMutableTreeNode) this.path.getPathComponent(1));

				// is this the type graph?
				if ((graph != null) && (graph.isTypeGraph())
						&& (gragra != null)) {
					// select the actual level
					int level = gragra.getLevelOfTypeGraphCheck();
					if (level == TypeSet.DISABLED) {
						this.disabled.setSelected(true);
					} else if (level == TypeSet.ENABLED_INHERITANCE) {
						this.enabledInheritance.setSelected(true);
					} else if (level == TypeSet.ENABLED) {
						this.enabled.setSelected(true);
					} else if (level == TypeSet.ENABLED_MAX) {
						this.enabledMax.setSelected(true);
					} else if (level == TypeSet.ENABLED_MAX_MIN) {
						this.enabledMaxMin.setSelected(true);
					} else {
						// unknown level?
						this.disabled.setSelected(false);
						this.enabledInheritance.setSelected(false);
						this.enabled.setSelected(false);
						this.enabledMax.setSelected(false);
						this.enabledMaxMin.setSelected(false);
					}
					// this.treeView.selectPath(x,y);
					return true;
				}// if graph!=null
			}// if length!=3
		}// if row != -1
		return false;
	}// invoked
	
	
	public void setTypeGraphLevel(final int level) {
		// try get the gragra of the selected TypeGraph
		if (this.path != null) {
			DefaultMutableTreeNode 
			parent = (DefaultMutableTreeNode) this.path.getPathComponent(1);
			EdGraGra gragra = this.treeView.getGraGra(parent);
	
			// if no type graph exists
			if (gragra.getTypeSet().getBasisTypeSet().getTypeGraph() == null) {
				return;
			}
			
			if (this.treeView.setLevelOfTypeGraphCheck(gragra, level, true)) {				
				this.treeView.updateTypeGraphTreeNode(node, gragra);
			}
		}
	}
	
	void editComments() {
		if (graph != null) {
			this.treeView.cancelCommentsEdit();
			Point p = this.treeView.getPopupMenuLocation();
			if (p == null) 
				p = new Point(200,200);
			GraGraTextualComment 
			comments = new GraGraTextualComment(this.treeView.getFrame(), p.x,
						p.y, graph.getBasisGraph());

			if (comments != null)
				comments.setVisible(true);
		}
	}

	private JMenuItem disabled, enabledInheritance, enabled, 
						enabledMax, enabledMaxMin;

	GraGraTreeView treeView;
	TreePath path;
	DefaultMutableTreeNode node;
	EdGraph graph;
	
}
