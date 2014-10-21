// $Id: GraGraPopupMenu.java,v 1.11 2010/09/23 08:21:33 olga Exp $

package agg.gui.popupmenu;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdConstraint;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;
import agg.editor.impl.EdType;
import agg.gui.AGGAppl;
import agg.gui.event.TreeViewEvent;
import agg.gui.treeview.GraGraTreeModel;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.dialog.GraGraConstraintLayerDialog;
import agg.gui.treeview.dialog.GraGraConstraintPriorityDialog;
import agg.gui.treeview.dialog.GraGraDisableLayerDialog;
import agg.gui.treeview.dialog.GraGraLayerDialog;
import agg.gui.treeview.dialog.GraGraPriorityDialog;
import agg.gui.treeview.dialog.GraGraTriggerRuleOfLayerDialog;
import agg.gui.treeview.dialog.NodeEdgeTypeSelectionDialog;
import agg.gui.treeview.dialog.SelectRulesDialog;
import agg.gui.treeview.nodedata.AtomicGraphConstraintTreeNodeData;
import agg.gui.treeview.nodedata.ConclusionTreeNodeData;
import agg.gui.treeview.nodedata.ConstraintTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTextualComment;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.nodedata.GrammarTreeNodeData;
import agg.gui.treeview.nodedata.GraphTreeNodeData;
import agg.gui.treeview.nodedata.KernelRuleTreeNodeData;
import agg.gui.treeview.nodedata.RuleSchemeTreeNodeData;
import agg.gui.treeview.nodedata.RuleSequenceTreeNodeData;
import agg.gui.treeview.nodedata.RuleTreeNodeData;
import agg.gui.treeview.nodedata.TypeGraphTreeNodeData;
import agg.gui.treeview.path.GrammarTreeNode;
import agg.ruleappl.RuleSequence;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;
import agg.xt_basis.RulePriority;

@SuppressWarnings("serial")
public class GraGraPopupMenu extends JPopupMenu {

	public final static String EXPORT_BY_TYPE_TO_COLOR_GRAPH = "EXPORT_BY_TYPE_TO_COLOR_GRAPH";
	public final static String EXPORT_TO_COLOR_GRAPH = "EXPORT_TO_COLOR_GRAPH";
	public final static String IMPORT_BY_TYPE_FROM_COLOR_GRAPH = "IMPORT_BY_TYPE_FROM_COLOR_GRAPH";
	public final static String IMPORT_FROM_COLOR_GRAPH = "IMPORT_FROM_COLOR_GRAPH";
	
	
	public GraGraPopupMenu(GraGraTreeView tree) {
		super("GraGra");
		this.treeView = tree;

		this.typeSelectionDialog = new NodeEdgeTypeSelectionDialog(this.treeView.getFrame());
		
		this.menuNew = new JMenu("New");
		add(this.menuNew);

		JMenuItem mi = this.menuNew.add(new JMenuItem(
				"Type Graph                    Ctrl+Alt+T"));
		mi.setActionCommand("newTypeGraph");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTypeGraph();
			}
		});
		
		mi = this.menuNew.add(new JMenuItem(
						"Graph                            Ctrl+Alt+G"));
		mi.setActionCommand("newGraph");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGraph();
			}
		});
		
		this.menuNew.addSeparator();
		
		mi = this.menuNew.add(new JMenuItem(
				"Rule                               Ctrl+Alt+R"));
		mi.setActionCommand("newRule");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRule();
			}
		});

		this.miRuleScheme = new JMenuItem("RuleScheme                                ");
		this.menuNew.add(this.miRuleScheme);
		this.miRuleScheme.setActionCommand("newRuleScheme");
		this.miRuleScheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRuleScheme();
			}
		});
		
		this.miParallelRule = new JMenuItem("Make Parallel Rule by disjoint Union");
//		this.menuNew.add(this.miParallelRule);
		this.miParallelRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeParallelRule();
			}
		});
		
		this.menuNew.addSeparator();
		
		mi = this.menuNew.add(new JMenuItem("Atomic Constraint         Ctrl+Alt+A"));
		mi.setActionCommand("newAtomic");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAtomic();
			}
		});

		mi = this.menuNew.add(new JMenuItem("Constraint                     Ctrl+Alt+C"));
		mi.setActionCommand("newConstraint");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGraphConstraint();
			}
		});

		this.menuNew.addSeparator();

		mi = this.menuNew.add(new JMenuItem("Rule Sequence        "));
		mi.setActionCommand("newRuleSequence");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRuleSequence();
			}
		});
		
		
		mi = add(new JMenuItem("Add Copy of Current Host Graph"));
		mi.setActionCommand("addGraph");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHostGraph();
			}
		});

		addSeparator();

		JMenu m = new JMenu("Layering");
		add(m);
		mi = m.add(new JMenuItem("Set Rule Layer"));
		mi.setActionCommand("setLayerOfRules");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLayerOfRules();
			}
		});

		mi = m.add(new JMenuItem("Set Trigger Rule for Layer"));
		mi.setActionCommand("setTriggerRuleOfLayer");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTriggerRuleOfLayer();
			}
		});

		mi = m.add(new JMenuItem("Select Rule Layer for Constraint"));
		mi.setActionCommand("setLayerOfConstraints");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLayerOfConstraints();
			}
		});

		m.addSeparator();

		mi = m.add(new JMenuItem("Sort Rules by Layer"));
		mi.setActionCommand("sortRulesByLayer");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortRulesByLayer();
			}
		});

		mi = m.add(new JMenuItem("Sort Constraints by Layer"));
		mi.setActionCommand("sortConstraintsByLayer");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortConstraintsByLayer();
			}
		});

		m.addSeparator();

		mi = m.add(new JMenuItem("Disable Rule Layer"));
		mi.setActionCommand("disableLayerOfRules");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableLayerOfRules();
			}
		});

		addSeparator();

		m = new JMenu("Priority");
		add(m);
		mi = m.add(new JMenuItem("Set Rule Priority"));
		mi.setActionCommand("setPriorityOfRules");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPriorityOfRules();
			}
		});

		mi = m.add(new JMenuItem("Select Rule Priority for Constraint"));
		mi.setActionCommand("setPriorityOfConstraints");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPriorityOfConstraints();
			}
		});

		m.addSeparator();

		mi = m.add(new JMenuItem("Sort Rules by Priority"));
		mi.setActionCommand("sortRulesByPriority");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortRulesByPriority();
			}
		});

		mi = m.add(new JMenuItem("Sort Constraints by Priority"));
		mi.setActionCommand("sortConstraintsByPriority");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortConstraintsByPriority();
			}
		});

		addSeparator();

		mi = add(new JMenuItem("Check Rule Applicability"));
		mi.setActionCommand("checkRuleApplicability");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRuleApplicability();
			}
		});

		mi = add(new JMenuItem("Dismiss Rule Applicability"));
		mi.setActionCommand("dismissRuleApplicability");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dismissRuleApplicability();
			}
		});
		addSeparator();

		m = new JMenu("Consistency");
		add(m);

		mi = m.add(new JMenuItem("Check Atomics"));
		mi.setActionCommand("checkAtomics");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAtomics(1);
			}
		});

		mi = m.add(new JMenuItem("Check Constraints"));
		mi.setActionCommand("checkConstraints");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAtomics(2);
			}
		});

		m.addSeparator();
		
		mi = m.add(new JMenuItem("Create Post Application Conditions"));
		mi.setActionCommand("convertConstraints");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAtomics(0);
			}
		});

		addSeparator();

		mi = add(new JMenuItem("Close                               Ctrl+W"));
		mi.setActionCommand("deleteGraGra");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (treeView.hasMultipleSelection())
					treeView.delete("selected");
				else
					treeView.delete("GraGra");
			}
		});

		addSeparator();

		mi = add(new JMenuItem("Save                                Ctrl+S"));
		mi.setActionCommand("save");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeView.saveGraGra();
			}
		});

		mi = add(new JMenuItem("Save As                            Alt+S"));
		mi.setActionCommand("saveAs");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeView.saveAsGraGra();
			}
		});
		
		addSeparator();

		JMenu submExport = (JMenu) add(new JMenu("Export"));

		mi = submExport.add(new JMenuItem("JPEG         Shift+J"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGraphJPEG");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = submExport.add(new JMenuItem("GXL          Shift+X"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGXL");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = submExport.add(new JMenuItem("GTXL        Shift+T"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGTXL");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = createMenuExportToColorGraph("COLOR GRAPH        ");
		submExport.add(mi);		
		mi.setEnabled(true);

		
		JMenu submImport = (JMenu) add(new JMenu("Import"));
		submImport.setEnabled(true);
		mi = submImport.add(new JMenuItem(
				"GGX                         Shift+Alt+G"));
		mi.setEnabled(true);
		mi.setActionCommand("importGGX");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = submImport.add(new JMenuItem(
				"GXL                          Shift+Alt+X"));
		mi.setEnabled(true);
		mi.setActionCommand("importGXL");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = new JMenuItem("GTXL");
		mi.setEnabled(false);
		mi.setActionCommand("importGTXL");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = submImport.add(new JMenuItem("OMONDO XMI          Shift+Alt+O"));
		mi.setEnabled(true);
		mi.setActionCommand("importOMONDOXMI");
		mi.addActionListener(this.treeView.getActionAdapter());

		mi = createMenuImportFromColorGraph("COLOR GRAPH        ");
		submImport.add(mi);	
		mi.setEnabled(true);
		
		addSeparator();

		mi = add(new JMenuItem("Refresh Attributes"));
		mi.setActionCommand("refresh");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAttrs();
			}
		});
		
		addSeparator();
		
		miReload = add(new JMenuItem("Reload                     Shift+Alt+R"));
		miReload.setActionCommand("reload");
		miReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeView.reloadGraGra();
			}
		});

		addSeparator();

		mi = add(new JMenuItem("Textual Comments"));
		mi.setActionCommand("commentGraGra");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editComments();
			}
		});

		pack();
		setBorderPainted(true);
	}

	public boolean invoked(int x, int y) {
		if (this.treeView == null)
			return false;

		if (this.treeView.getTree().getRowForLocation(x, y) != -1) {
			if (this.treeView.getTree().getPathForLocation(x, y).getPath().length == 2) {
				this.path = this.treeView.getTree().getPathForLocation(x, y);
				this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();				
				this.data = (GraGraTreeNodeData) this.node.getUserObject();
				if (this.data.isGraGra()) {
					this.gra = this.data.getGraGra();
					this.typeSelectionDialog.setGraGra(this.gra);
					
					if (this.treeView.getGraGra().getRules().size() > 1)
						this.miParallelRule.setEnabled(true);
					else
						this.miParallelRule.setEnabled(false);
					
					return true;
				}
			}
		}
		return false;
	}

	boolean invoke() {
		if (this.treeView == null)
			return false;

		this.path = this.treeView.getSelectedPath();
		if (this.path != null) {
			this.node = (DefaultMutableTreeNode) this.path.getLastPathComponent();				
			this.data = (GraGraTreeNodeData) this.node.getUserObject();
			if (this.data.isGraGra()) {
				this.gra = this.data.getGraGra();
				this.typeSelectionDialog.setGraGra(this.gra);					
				if (this.treeView.getGraGra().getRules().size() > 1)
					this.miParallelRule.setEnabled(true);
				else
					this.miParallelRule.setEnabled(false);					
				return true;
			}	
			else {
				this.path = null;
			}
		}
		return false;
	}
	
	public void show(Component invoker, int x, int y) {
		super.show(invoker, x, y);
		
		this.typeSelectionDialog.setNodeType(this.treeView.getNodeTypeForColorGraph());
		this.typeSelectionDialog.setEdgeType(this.treeView.getEdgeTypeForColorGraph());
		this.typeSelectionDialog.setLocation(
				this.getLocationOnScreen().x+150,
				this.getLocationOnScreen().y+50);
		
	}
	
	private JMenu createMenuExportToColorGraph(final String title) {
		final JMenu expMenu = new JMenu(title);
		
		final JMenuItem miAll = expMenu.add(new JMenuItem("Export Complete Current Host Graph"));
		miAll.setSelected(true);	
		miAll.setActionCommand(EXPORT_TO_COLOR_GRAPH);
		miAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(null);
				GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(null);
				
				GraGraPopupMenu.this.treeView.getActionAdapter().actionPerformed(e);
			}
		});
		
		final JMenuItem miSpecial = expMenu.add(new JMenuItem("Export specific Nodes and Edges of Current Host Graph"));		
		miSpecial.setSelected(false);	
		miSpecial.setActionCommand(EXPORT_BY_TYPE_TO_COLOR_GRAPH);
		miSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				GraGraPopupMenu.this.typeSelectionDialog.setVisible(true);
				if (!GraGraPopupMenu.this.typeSelectionDialog.isCancelled()) {
					final EdType nodeType = GraGraPopupMenu.this.typeSelectionDialog.getNodeType();
					final EdType edgeType = GraGraPopupMenu.this.typeSelectionDialog.getEdgeType();
					if (nodeType != null) {
						GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(nodeType);
					} else {
						GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(null);
					}
					if (edgeType != null) {
						GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(edgeType);
					} else {
						GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(null);
					}

					GraGraPopupMenu.this.treeView.getActionAdapter().actionPerformed(e);
				}
			}
		});		
		
		return expMenu;
	}
	
	private JMenu createMenuImportFromColorGraph(final String title) {
		final JMenu impMenu = new JMenu(title);
		
		final JMenuItem miAll = impMenu.add(new JMenuItem("Import into Current Host Graph"));
		miAll.setSelected(true);
		miAll.setActionCommand(IMPORT_FROM_COLOR_GRAPH);
		miAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(null);
				GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(null);
				
				GraGraPopupMenu.this.treeView.getActionAdapter().actionPerformed(e);
			}
		});
		
		final JMenuItem miSpecial = impMenu.add(new JMenuItem("Import specific Nodes into Current Host Graph"));		
		miSpecial.setSelected(false);	
		miSpecial.setActionCommand(IMPORT_BY_TYPE_FROM_COLOR_GRAPH);
		miSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraGraPopupMenu.this.typeSelectionDialog.setVisible(true);				
				if (!GraGraPopupMenu.this.typeSelectionDialog.isCancelled()) {
					final EdType nodeType = GraGraPopupMenu.this.typeSelectionDialog.getNodeType();
					final EdType edgeType = GraGraPopupMenu.this.typeSelectionDialog.getEdgeType();

					if (nodeType != null) {
						GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(nodeType);
					} else {
						GraGraPopupMenu.this.treeView.setNodeTypeForColorGraph(null);
					}
					
					if (edgeType != null) {
						GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(edgeType);
					} else {
						GraGraPopupMenu.this.treeView.setEdgeTypeForColorGraph(null);
					}
					
					GraGraPopupMenu.this.treeView.getActionAdapter().actionPerformed(e);
				}
			}
		});
		
		return impMenu;
	}
	
	public void enableRuleScheme() {
		for (int i=0; i<this.menuNew.getItemCount(); i++) {
			if (this.menuNew.getItem(i).getActionCommand().equals("newRule")
					&& ((i+1) < this.menuNew.getItemCount())
					&& !this.menuNew.getItem(i+1).getActionCommand().equals("newRuleScheme")) {
				this.menuNew.insert(this.miRuleScheme, i+1);
				break;
			}
		}
	}
	
	void createTypeGraph() {
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		if (gra.getTypeSet().getTypeGraph() != null) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(),
					"<html><body>"
					+" The type graph already exists."
					+"</body></html>",
					"",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (gra.isEditable()) {
			EdGraph typeGraph = gra.createTypeGraph();		
	
			GraGraTreeNodeData sdTypeGraph = new TypeGraphTreeNodeData(typeGraph);
			sdTypeGraph.setString("[D]TypeGraph");
	
			DefaultMutableTreeNode newTypeGraphNode = new DefaultMutableTreeNode(
					sdTypeGraph);
			sdTypeGraph.setTreeNode(newTypeGraphNode);
			this.treeView.getTreeModel().insertNodeInto(newTypeGraphNode, parent, 0);
		} 
		else
			treeView.lockWarning();
	} 
	
	void createGraph() {
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		if (gra.isEditable()) {
			EdGraph graph = new EdGraph(
					BaseFactory.theFactory().createGraph(gra.getTypeSet().getBasisTypeSet(), true), 
					gra.getTypeSet());
			graph.getBasisGraph().setName(
						graph.getBasisGraph().getName() + gra.getGraphs().size());
			gra.addGraph(graph);
			graph.setGraGra(gra);
			GraGraTreeNodeData sdGraph = new GraphTreeNodeData(graph);
			DefaultMutableTreeNode newGraphNode = new DefaultMutableTreeNode(sdGraph);
			sdGraph.setTreeNode(newGraphNode);
			int indx = gra.getGraphs().size() - 1;
			if (gra.getTypeGraph() != null)
				indx++;
			this.treeView.getTreeModel().insertNodeInto(newGraphNode, parent, indx);
		}
		else
			treeView.lockWarning();
	}
	
	boolean addHostGraph() {
		if (this.path == null) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.", 
						"", JOptionPane.WARNING_MESSAGE);
			return false;
		} 
		
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
		if (gra != this.treeView.getCurrentGraGra()) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(),
					"<html><body>"
					+"Bad selection.<br> Please select an appropriate grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (gra.isEditable()) {
			EdGraph g = gra.cloneGraph(true);
			if (g != null) {
				int indx = gra.getGraphs().size() - 1;
				if (gra.getTypeGraph() != null)
					indx++;
				g.getBasisGraph().setName(
						gra.getGraph().getBasisGraph().getName()
								+ (gra.getGraphs().size() - 1));
				GraGraTreeNodeData sdGraph = new GraphTreeNodeData(g);
				DefaultMutableTreeNode 
				newGraphNode = new DefaultMutableTreeNode(sdGraph);
				sdGraph.setTreeNode(newGraphNode);
		
				this.treeView.getTreeModel().insertNodeInto(newGraphNode, parent, indx);
				
				return true;
			}
		}
		else {
			treeView.lockWarning();
		}
		return false;
	}
	
	void createRule() {
		if (gra.isEditable()) {
			DefaultMutableTreeNode
			parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			int newIndex = gra.getGraphs().size() + gra.getRules().size();
			if (gra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Rule" + gra.getRules().size();
			name = ((GraGraTreeModel) this.treeView.getTree().getModel()).makeNewName(gra, name);
			EdRule newRule = gra.createRule(name);
			
			GraGraTreeNodeData sdRule = new RuleTreeNodeData(newRule);
			if (gra.getBasisGraGra().isLayered()) {
				String tag = "[" + newRule.getBasisRule().getLayer() + "]";
				sdRule.setString(tag, name);
			} else if (gra.getBasisGraGra().trafoByPriority()) {
				String tag = "[" + newRule.getBasisRule().getPriority() + "]";
				sdRule.setString(tag, name);
			}
			DefaultMutableTreeNode newRuleNode = new DefaultMutableTreeNode(sdRule);
			sdRule.setTreeNode(newRuleNode);
			this.treeView.getTreeModel().insertNodeInto(newRuleNode, parent, newIndex);
			this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
					TreeViewEvent.RULE_ADDED, this.path));
		}
		else
			treeView.lockWarning();
	}
	
	void createRuleScheme() {
		if (gra.isEditable()) {
			DefaultMutableTreeNode
			parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			int newIndex = gra.getGraphs().size() + gra.getRules().size();
			if (gra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "RuleScheme" + gra.getRules().size();
			name = ((GraGraTreeModel) this.treeView.getTree().getModel()).makeNewName(gra, name);
			EdRuleScheme newRuleScheme = gra.createRuleScheme(name);
				
			GraGraTreeNodeData sdRuleScheme = new RuleSchemeTreeNodeData(newRuleScheme);
	        DefaultMutableTreeNode newRuleSchemeNode = new DefaultMutableTreeNode(sdRuleScheme);
	        sdRuleScheme.setTreeNode(newRuleSchemeNode);
	        this.treeView.getTreeModel().insertNodeInto(newRuleSchemeNode, parent, newIndex);
		
	        // make kernel rule tree node of rule scheme
	        parent = newRuleSchemeNode;
	        GraGraTreeNodeData sdKernelRule = new KernelRuleTreeNodeData(newRuleScheme.getKernelRule());
	        DefaultMutableTreeNode newKernelRuleNode = new DefaultMutableTreeNode(sdKernelRule);
	        sdKernelRule.setTreeNode(newKernelRuleNode);
	        newIndex = 0;
	        this.treeView.getTreeModel().insertNodeInto(newKernelRuleNode, parent, newIndex);	
		}
		else
			treeView.lockWarning();
    }
	
	/** Adds a new atomic constraint node for the selected gragra node */
	void createAtomic() {
		if (gra.isEditable()) {
			DefaultMutableTreeNode 
			parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			int newIndex = gra.getGraphs().size() + gra.getRules().size()
					+ gra.getAtomics().size();
			if (gra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Atomic";
			if (gra.getAtomics().size() > 0)
				name = name + gra.getAtomics().size();
			name = ((GraGraTreeModel) this.treeView.getTree().getModel()).makeNewName(gra, name);
			EdAtomic newAtomic = gra.createAtomic(name);
	
			GraGraTreeNodeData sd = new AtomicGraphConstraintTreeNodeData(newAtomic);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeView.getTreeModel().insertNodeInto(newNode, parent, newIndex);
	
			/* add first conclusion */
			newIndex = newAtomic.getConclusions().size() - 1;
			EdAtomic aConclusion = newAtomic.getConclusion(0);
			name = aConclusion.getMorphism().getName();
	
			sd = new ConclusionTreeNodeData(aConclusion);
			DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(aNode);
			this.treeView.getTreeModel().insertNodeInto(aNode, newNode, newIndex);
		}
		else
			treeView.lockWarning();
	}
	
	/** Adds a new constraint (formula) node for the selected gragra node */
	void createGraphConstraint() {
		if (gra.isEditable()) {
			DefaultMutableTreeNode 
			parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			int newIndex = gra.getGraphs().size() + gra.getRules().size()
					+ gra.getAtomics().size() + gra.getConstraints().size();
			if (gra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Constraint";
			if (gra.getConstraints().size() > 0)
				name = name + gra.getConstraints().size();
			name = ((GraGraTreeModel) this.treeView.getTree().getModel()).makeNewName(gra, name);
			EdConstraint newConstraint = gra.createConstraint(name);
	
			handleRuleConstraints(parent, false, newConstraint);
	
			GraGraTreeNodeData sd = new ConstraintTreeNodeData(newConstraint);
			if (gra.getBasisGraGra().isLayered() 
					&& !newConstraint.getBasisConstraint().getLayer().isEmpty()) {
				String tag = "["
						+ newConstraint.getBasisConstraint().getLayerAsString()
						+ "]";
				sd.setString(tag, name);
			}
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeView.getTreeModel().insertNodeInto(newNode, parent, newIndex);
		}
		else
			treeView.lockWarning();
	}

	void createRuleSequence() {
		if (gra.isEditable()) {
	 		DefaultMutableTreeNode
			parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();
			int indx = gra.getGraphs().size() 
								+ gra.getRules().size()
								+ gra.getAtomics().size()
								+ gra.getConstraints().size();
							
			if (gra.getTypeSet().getTypeGraph() != null)
				indx++;
						
			this.treeView.fireTreeViewEvent(new TreeViewEvent(
					this.treeView, TreeViewEvent.RULE_SEQUENCE,
						this.path));			
			if ((indx + gra.getBasisGraGra().getRuleSequences().size())
					> parent.getChildCount()) {
				// show already existing rule sequences
				for (int i=0; i<gra.getBasisGraGra().getRuleSequences().size(); i++) {
					RuleSequence rseq = gra.getBasisGraGra().getRuleSequences().get(i);
					rseq.setCriticalPairOption(((AGGAppl)this.treeView.getFrame()).getCPA().getCriticalPairOption());
						
					GraGraTreeNodeData sdRuleSequence = new RuleSequenceTreeNodeData(rseq);
				       DefaultMutableTreeNode newRuleSequenceNode = new DefaultMutableTreeNode(sdRuleSequence);
				       sdRuleSequence.setTreeNode(newRuleSequenceNode);
				       this.treeView.getTreeModel().insertNodeInto(newRuleSequenceNode, parent, indx+i);
				}
			}
				
			indx = indx + gra.getBasisGraGra().getRuleSequences().size();
				
			String name = "RuleSequence";
			if (!gra.getBasisGraGra().getRuleSequences().isEmpty()) 
				name = "RuleSequence"+gra.getBasisGraGra().getRuleSequences().size();
			RuleSequence rseq = new RuleSequence(gra.getBasisGraGra(), name);
			gra.getBasisGraGra().addRuleSequence(rseq);
			rseq.setCriticalPairOption(((AGGAppl)this.treeView.getFrame()).getCPA().getCriticalPairOption());
				
			GraGraTreeNodeData sdRuleSequence = new RuleSequenceTreeNodeData(rseq);
		    DefaultMutableTreeNode newRuleSequenceNode = new DefaultMutableTreeNode(sdRuleSequence);
		    sdRuleSequence.setTreeNode(newRuleSequenceNode);
		    this.treeView.getTreeModel().insertNodeInto(newRuleSequenceNode, parent, indx);
		} 
		else 
			treeView.lockWarning();
    }
	
	void checkRuleApplicability() {
		if (this.path == null) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 
					"<html><body>"
					+"Bad selection.<br> Please select a gragra.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (gra != null) {
			this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
					TreeViewEvent.CHECK_RULE_APPLICABILITY));
		}
	}
	
	void dismissRuleApplicability() {
		if (this.path == null) {
			JOptionPane.showMessageDialog(this.treeView.getFrame(), 
					"<html><body>"
					+"Bad selection.<br> Please select a gragra.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (gra != null) {
			this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
					TreeViewEvent.DISMISS_RULE_APPLICABILITY));
		}
	}
	
	/**
	 * When <code>what</code> is <br>
	 * 0: create Post Application Conditions for all rules<br>
	 * 1: check all atomic graph constraints at the current host graph<br>
	 * 2: check all graph consistency constraints (formulae) at the current host graph.
	 * @param what
	 */
	public void doAtomics(final int what) {
		if (this.path == null) {
			invoke();
			if (this.path == null) {
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"<html><body>"
						+"Bad selection.<br> Please select a gragra.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		DefaultMutableTreeNode
		parent = (DefaultMutableTreeNode) this.path.getLastPathComponent();

		switch (what) {
		case 0:
			handleRuleConstraints(parent, false, null);
			if (this.gra.getBasisGraGra().getAtomics().hasMoreElements()
					&& this.gra.getBasisGraGra().getConstraints()
							.hasMoreElements()) {
				
				JOptionPane pane = new JOptionPane(
						"Creating post application conditions ... \n Please wait ... ",
						JOptionPane.WARNING_MESSAGE);
				final JDialog d = pane.createDialog("Creating ...");
				Thread thread = new Thread() {
					public void run() {
						msg = gra.getBasisGraGra().convertConstraints();
						gra.setChanged(true);
						d.setVisible(false);
					}
				};
				thread.start();
//				this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
//						TreeViewEvent.CONVERT_STEP,
//						"Creating post application condition. Please wait ..."));
				d.setVisible(true);
				while (thread.isAlive()) {}
				
				if (!this.msg.equals("")) {
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
							TreeViewEvent.CONVERT_STEP,
							"Cannot convert to post application condition.  "
									+ this.msg));
					JOptionPane.showMessageDialog(this.treeView.getFrame(),
							"<html><body>"
							+"Cannot convert to post application condition.<br"
							+ this.msg,
							"",
							JOptionPane.ERROR_MESSAGE);
				} else {
					handleRuleConstraints(parent, true, null);
					this.treeView.fireTreeViewEvent(new TreeViewEvent(this.treeView,
							TreeViewEvent.CONVERT_STEP,
							"Creating post application condition ... done."));
				}
			} else {
				JOptionPane
						.showMessageDialog(
								this.treeView.getFrame(),
								"<html><body>"
								+"Cannot convert to post application condition.",
								"",
								JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 1: {
			if (this.gra.getAtomics().isEmpty()) {
				JOptionPane
						.showMessageDialog(this.treeView.getFrame(),
								"<html><body>"
								+"Nothing to check!<br>"
								+"The current grammar doesn't contain any graph constraints.",
								"",
								JOptionPane.WARNING_MESSAGE);
				break;
			}
			if (!this.gra.getBasisGraGra().isGraphReadyForTransform()) {
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"<html><body>"
						+"The host graph isn't ready.<br>"
						+"Please check its attributes.",
						"",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
			
			boolean changed = this.gra.isChanged();
			boolean all_good = false;
			TreeViewEvent e = new TreeViewEvent(this.treeView, TreeViewEvent.CHECK);
			this.treeView.fireTreeViewEvent(e);
			boolean all_valid = this.gra.getBasisGraGra().checkAtomics(true);
			if (all_valid) {
				all_good = this.gra.getBasisGraGra().checkAtomics(false);
			}
			this.msg = "";
			if (!all_valid) {
				this.msg = "<html><body>"
						+"Not all atomic constraints are valid <br>"
						+"(i.e. atomic morphism : total & injective,<br>"
						+"attribute value, attribute condition ...).<br>"
						+"Please check :<br>"
						+"<font color=\"#FF0000\">"
						+this.gra.getBasisGraGra().getConsistencyErrorMsg() 
						+"</font>"
						+"</body></html>";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg, "", JOptionPane.ERROR_MESSAGE);
			}
			else if (!all_good) {
				this.msg = "<html><body>"
						+"All atomics are valid, <br>"
						+"but the graph doesn't fulfill all of them :<br>"
						+"<font color=\"#FF0000\">"
						+this.gra.getBasisGraGra().getConsistencyErrorMsg() 
						+"</font>"
						+"</body></html>";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg, "", JOptionPane.ERROR_MESSAGE);
			}
			else {
				this.msg = "The graph fulfills all atomic graph constraints.";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg);
			}
			
			this.gra.setChanged(changed);
			break;
		}
		case 2: {
			if (this.gra.getAtomics().isEmpty()) {
				JOptionPane
						.showMessageDialog(this.treeView.getFrame(),
								"<html><body"
								+"Nothing to check!<br>"
								+"The current grammar doesn't contain any graph constraints.",
								"",
								JOptionPane.WARNING_MESSAGE);
				break;
			}
			if (!this.gra.getBasisGraGra().isGraphReadyForTransform()) {
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
						"<html><body"
						+"The host graph is not ready!<br>"
						+"Please check its attributes.",
						"",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
						
			boolean changed = this.gra.isChanged();
			boolean all_good = false;
			TreeViewEvent e = new TreeViewEvent(this.treeView, TreeViewEvent.CHECK);
			this.treeView.fireTreeViewEvent(e);
			boolean all_valid = this.gra.getBasisGraGra().checkGraphConstraints(true);
			if (all_valid) {
				all_good = this.gra.getBasisGraGra().checkGraphConstraints(false);
			}
			if (!all_valid) {
				this.msg = "<html><body>"
						+"Not all constraints were valid.<br>"
						+"Please check :<br>"
						+"<font color=\"#FF0000\">"
						+ this.gra.getBasisGraGra().getConsistencyErrorMsg()
						+"</font>"
						+"</body></html>";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg, "", JOptionPane.ERROR_MESSAGE);
			}
			else if (!all_good) {
				this.msg = "<html><body>"
						+"All constraints were valid, <br>"
						+"but the graph doesn't fulfill all of them :<br>"
						+"<font color=\"#FF0000\">"
						+ this.gra.getBasisGraGra().getConsistencyErrorMsg() 
						+"</font>"								
						+"</body></html>";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg, "", JOptionPane.ERROR_MESSAGE);
			}
			else {
				this.msg = "The graph fulfills all constraints.";
				JOptionPane.showMessageDialog(this.treeView.getFrame(), this.msg);
			}
			
			this.gra.setChanged(changed);
			break;
		}
		}
		this.path = null;
	}
	
	private void handleRuleConstraints(DefaultMutableTreeNode node,
			boolean insert, Object objToCheck) {
		GraGraTreeNodeData sd = (GraGraTreeNodeData) node.getUserObject();
		if (sd.isGraGra()) {
			Enumeration<?> en = node.children();
			while (en.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) en
						.nextElement();
				GraGraTreeNodeData csd = (GraGraTreeNodeData) child
						.getUserObject();
				if (csd.isRule()) {
					if (insert)
						treeView.addRuleConstraints(child, objToCheck);
					else
						treeView.removeRuleConstraints(child, objToCheck);
				}
			}
			
		} else if (sd.isRule()) {
			if (insert)
				treeView.addRuleConstraints(node, objToCheck);
			else {
				treeView.removeRuleConstraints(node, objToCheck);
			}
		}
	}
	
	/** Sets rule layer of the selected grammar */
	void setLayerOfRules() {
		if (gra != null) {
			RuleLayer rl = new RuleLayer(gra.getBasisGraGra().getListOfRules());
			GraGraLayerDialog lg = new GraGraLayerDialog(this.treeView.getFrame(), rl);
			Point p = this.treeView.getPopupMenuLocation();
			if (p != null)
				lg.setLocation(p);
			lg.showGUI();
			if (!lg.isCancelled()) {
				if (lg.hasChanged()) {
					this.gra.setChanged(true);
					this.gra.getBasisGraGra().oneRuleHasChangedLayer();
					this.gra.getBasisGraGra().refreshConstraintsForLayer();
					if (gra.getBasisGraGra().isLayered()) {
						this.treeView.getTreeModel().ruleNameChanged(gra, true);
						this.treeView.getTreeModel().constraintNameChanged(gra, true);
						this.treeView.getTree().treeDidChange();
						this.treeView.fireTreeViewEvent(
								new TreeViewEvent(this.treeView, TreeViewEvent.RULE_LAYER, this.path));
					}
				}
			}
		}
	}
	
	/** Sets constraint layer of the selected grammar */
	@SuppressWarnings("deprecation")
	void setLayerOfConstraints() {
		if (gra != null) {			
			if (!gra.getBasisGraGra().getConstraints().hasMoreElements()) {
				JOptionPane.showMessageDialog(this.treeView.getFrame(),
						"There isn't any graph constraints (formulae) available.");
			} else {
				GraGraConstraintLayerDialog lg = new GraGraConstraintLayerDialog(
						this.treeView.getFrame(), gra.getBasisGraGra().getConstraintsVec(),
						gra.getBasisGraGra().getLayers());
				Point p = this.treeView.getPopupMenuLocation();
				if (p != null)
					lg.setLocation(p);
				lg.showGUI();
				if (!lg.isCancelled()) {
					gra.setChanged(true);
					if (gra.getBasisGraGra().isLayered()) {
						this.treeView.getTreeModel().constraintNameChanged(gra, true);
						this.treeView.getTree().treeDidChange();
					}
				}
			}
		}
	}
	
	/** Sets trigger rule of layer of the selected grammar */
	void setTriggerRuleOfLayer() {
		if (gra != null) {
			RuleLayer rl = new RuleLayer(gra.getBasisGraGra().getEnabledRules()); //getListOfRules());
			GraGraTriggerRuleOfLayerDialog trlGUI = new GraGraTriggerRuleOfLayerDialog(
					this.treeView.getFrame(), rl);
			trlGUI.setGraGra(gra);
			Point p = this.treeView.getPopupMenuLocation();
			if (p != null)
				trlGUI.setLocation(p);
			trlGUI.showGUI();
			if (!trlGUI.isCancelled()) {
				gra.setChanged(true);
				if (gra.getBasisGraGra().isLayered()) {
					this.treeView.getTreeModel().ruleNameChanged(gra, true);
					this.treeView.getTree().treeDidChange();
				}
			}
		}
	}
	
	void sortRulesByLayer() {
		EdRule currRule = this.treeView.getCurrentRule();
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.treeView.getTreeModel().getRoot();
		if (gra != null) {
			int row = 0;
			for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
				TreePath path = this.treeView.getTree().getPathForRow(i);
				DefaultMutableTreeNode inode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if (inode == node) {
					if (this.treeView.getTree().isExpanded(row))
						this.treeView.getTree().collapseRow(i);
					row = i;
					break;
				}
			}
			int indx = this.treeView.getTreeModel().getIndexOfChild(parent, node);
			
			node.removeAllChildren();
			this.treeView.getTreeModel().removeNodeFromParent(node);
			
			gra.sortRulesByLayer();
			
			GraGraTreeNodeData sdGra = new GrammarTreeNodeData(gra);
			node = new DefaultMutableTreeNode(sdGra);
			sdGra.setTreeNode(node);
			this.treeView.getTreeModel().insertNodeInto(node, parent, indx);
			
			GrammarTreeNode tmpTreeNode = new GrammarTreeNode();
			tmpTreeNode.updateTreeNodeData(this.treeView, node, gra);

			this.treeView.getTreeModel().nodeChanged(node);
			if (indx == 0)
				this.treeView.getTree().expandRow(indx);
			this.treeView.getTree().expandRow(row);
			
			if (currRule != null) {
				DefaultMutableTreeNode ruleNode = this.treeView.getTreeModel()
						.getTreeNodeOfGraGraRule(node, currRule);
				if (ruleNode != null) {
					for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
						TreePath ipath = this.treeView.getTree().getPathForRow(i);
						DefaultMutableTreeNode inode = (DefaultMutableTreeNode) ipath
								.getLastPathComponent();
						if (inode.equals(ruleNode))
							this.treeView.getTree().setSelectionPath(ipath);
					}
				}
			}
			if (gra.getBasisGraGra().isLayered()) {
				this.treeView.getTreeModel().ruleNameChanged(gra, true);
				this.treeView.getTreeModel().constraintNameChanged(gra, true);
			}
			this.treeView.getTree().treeDidChange();	
		}
	}
	
	void sortConstraintsByLayer() {
		EdConstraint currConstraint = this.treeView.getCurrentConstraint();
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.treeView.getTreeModel().getRoot();
		if (gra != null) {
			int row = 0;
			for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
				TreePath path = this.treeView.getTree().getPathForRow(i);
				DefaultMutableTreeNode inode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if (inode == node) {
					if (this.treeView.getTree().isExpanded(row))
						this.treeView.getTree().collapseRow(i);
					row = i;
					break;
				}
			}
			int indx = this.treeView.getTreeModel().getIndexOfChild(parent, node);
			
			node.removeAllChildren();
			this.treeView.getTreeModel().removeNodeFromParent(node);
			
			gra.sortConstraintsByLayer();
			
			GraGraTreeNodeData sdGra = new GrammarTreeNodeData(gra);
			node = new DefaultMutableTreeNode(sdGra);
			sdGra.setTreeNode(node);
			this.treeView.getTreeModel().insertNodeInto(node, parent, indx);
			
			GrammarTreeNode tmpTreeNode = new GrammarTreeNode();
			tmpTreeNode.updateTreeNodeData(this.treeView, node, gra);

			this.treeView.getTreeModel().nodeChanged(node);
			if (indx == 0)
				this.treeView.getTree().expandRow(indx);
			this.treeView.getTree().expandRow(row);
			
			DefaultMutableTreeNode constraintNode = this.treeView.getTreeModel()
					.getTreeNodeOfGraGraRule(node, currConstraint);
			if (constraintNode != null) {
				for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
					TreePath path = this.treeView.getTree().getPathForRow(i);
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					if (node.equals(constraintNode))
						this.treeView.getTree().setSelectionPath(path);
				}
			}
			if (gra.getBasisGraGra().isLayered()) {
				this.treeView.getTreeModel().ruleNameChanged(gra, true);
				this.treeView.getTreeModel().constraintNameChanged(gra, true);
			}
			this.treeView.getTree().treeDidChange();
		}
	}
	
	void disableLayerOfRules() {
		if (gra != null) {
			GraGraDisableLayerDialog lg = new GraGraDisableLayerDialog(
					this.treeView.getFrame(), gra.getBasisGraGra().getLayers());
			lg.setGraGra(gra);
			Point p = this.treeView.getPopupMenuLocation();
			if (p != null)
				lg.setLocation(p);
			lg.showGUI();
			if (!lg.isCancelled()) {
				if (lg.hasChanged()) {
					gra.setChanged(true);
					gra.getBasisGraGra().oneRuleHasChangedEvailability();
					if (gra.getBasisGraGra().isLayered()) 
					this.treeView.getTreeModel().ruleNameChanged(gra, true);
					this.treeView.getTree().treeDidChange();
				}
			}
		}
	}
	
	/** Sets rule priority of the selected grammar */
	void setPriorityOfRules() {
		if (gra != null) {
			RulePriority rp = new RulePriority(gra.getBasisGraGra().getListOfRules());
			GraGraPriorityDialog pg = new GraGraPriorityDialog(this.treeView.getFrame(), rp);
			Point p = this.treeView.getPopupMenuLocation();
			if (p != null)
				pg.setLocation(p);
			pg.showGUI();
			if (!pg.isCancelled()) {
				if (pg.hasChanged()) {
					gra.setChanged(true);
					gra.getBasisGraGra().oneRuleHasChangedPriority();
					if (gra.getBasisGraGra().trafoByPriority()) {
						this.treeView.getTreeModel().ruleNameChanged(gra, false, true);
						this.treeView.getTree().treeDidChange();
						this.treeView.fireTreeViewEvent(
								new TreeViewEvent(this.treeView, TreeViewEvent.RULE_PRIORITY, this.path));
					}
				}
			}
		}
	}
	
	/** Sets constraint priority of the selected grammar */
	@SuppressWarnings("deprecation")
	void setPriorityOfConstraints() {
		if (gra != null) {
			if (!gra.getBasisGraGra().getConstraints().hasMoreElements()) {
				JOptionPane.showMessageDialog(this.treeView.getFrame(),
				"There isn't any graph constraints (formulae) available.");
			} else {
				GraGraConstraintPriorityDialog lg = new GraGraConstraintPriorityDialog(
						this.treeView.getFrame(), gra.getBasisGraGra().getConstraintsVec(),
						gra.getBasisGraGra().getPriorities());
				Point p = this.treeView.getPopupMenuLocation();
				if (p != null)
					lg.setLocation(p);
				lg.showGUI();
				if (!lg.isCancelled()) {
					gra.setChanged(true);
					if (gra.getBasisGraGra().trafoByPriority()) {
						this.treeView.getTreeModel().constraintNameChanged(gra, false, true);
						this.treeView.getTree().treeDidChange();
					}
				}
			}
		}
	}
	
	/** Sort rules by rule priority */
	void sortRulesByPriority() {
		EdRule currRule = this.treeView.getCurrentRule();
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.treeView.getTreeModel().getRoot();
		if (gra != null) {
			int row = 0;
			for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
				TreePath path = this.treeView.getTree().getPathForRow(i);
				DefaultMutableTreeNode inode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if (inode == node) {
					if (this.treeView.getTree().isExpanded(row))
						this.treeView.getTree().collapseRow(i);
					row = i;
					break;
				}
			}
			int indx = this.treeView.getTreeModel().getIndexOfChild(parent, node);
			
			node.removeAllChildren();
			this.treeView.getTreeModel().removeNodeFromParent(node);
			
			gra.sortRulesByPriority();
			
			GraGraTreeNodeData sdGra = new GrammarTreeNodeData(gra);
			node = new DefaultMutableTreeNode(sdGra);
			sdGra.setTreeNode(node);
			this.treeView.getTreeModel().insertNodeInto(node, parent, indx);
			
			GrammarTreeNode tmpTreeNode = new GrammarTreeNode();
			tmpTreeNode.updateTreeNodeData(this.treeView, node, gra);

			this.treeView.getTreeModel().nodeChanged(node);
			if (indx == 0)
				this.treeView.getTree().expandRow(indx);
			this.treeView.getTree().expandRow(row);
			
			if (currRule != null) {
				DefaultMutableTreeNode ruleNode = this.treeView.getTreeModel()
						.getTreeNodeOfGraGraRule(node, currRule);
				if (ruleNode != null) {
					for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
						TreePath ipath = this.treeView.getTree().getPathForRow(i);
						DefaultMutableTreeNode inode = (DefaultMutableTreeNode) ipath
								.getLastPathComponent();
						if (inode.equals(ruleNode))
							this.treeView.getTree().setSelectionPath(ipath);
					}
				}
			}
			if (gra.getBasisGraGra().trafoByPriority())
				this.treeView.getTreeModel().ruleNameChanged(gra, false, true);
			this.treeView.getTree().treeDidChange();
		}
	}
	
	/** Sort constraints by rule priority */
	void sortConstraintsByPriority() {
		EdConstraint currConstraint = this.treeView.getCurrentConstraint();
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.treeView.getTreeModel().getRoot();
		if (gra != null) {
			int row = 0;
			for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
				TreePath path = this.treeView.getTree().getPathForRow(i);
				DefaultMutableTreeNode inode = (DefaultMutableTreeNode) path
						.getLastPathComponent();
				if (inode == node) {
					if (this.treeView.getTree().isExpanded(row))
						this.treeView.getTree().collapseRow(i);
					row = i;
					break;
				}
			}
			int indx = this.treeView.getTreeModel().getIndexOfChild(parent, node);
			
			node.removeAllChildren();
			this.treeView.getTreeModel().removeNodeFromParent(node);
			
			gra.sortConstraintsByPriority();
			
			GraGraTreeNodeData sdGra = new GrammarTreeNodeData(gra);
			node = new DefaultMutableTreeNode(sdGra);
			sdGra.setTreeNode(node);
			this.treeView.getTreeModel().insertNodeInto(node, parent, indx);
			
			GrammarTreeNode tmpTreeNode = new GrammarTreeNode();
			tmpTreeNode.updateTreeNodeData(this.treeView, node, gra);

			this.treeView.getTreeModel().nodeChanged(node);
			if (indx == 0)
				this.treeView.getTree().expandRow(indx);
			this.treeView.getTree().expandRow(row);
			
			DefaultMutableTreeNode constraintNode = this.treeView.getTreeModel()
					.getTreeNodeOfGraGraRule(node, currConstraint);
			if (constraintNode != null) {
				for (int i = 0; i < this.treeView.getTree().getRowCount(); i++) {
					TreePath path = this.treeView.getTree().getPathForRow(i);
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					if (node.equals(constraintNode))
						this.treeView.getTree().setSelectionPath(path);
				}
			}
			if (gra.getBasisGraGra().trafoByPriority()) {
				this.treeView.getTreeModel().ruleNameChanged(gra, false, true);
				this.treeView.getTreeModel().constraintNameChanged(gra, false, true);			
				this.treeView.getTree().treeDidChange();
			}
		}
	}

	void refreshAttrs() {
		if (gra != null)
			gra.getTypeSet().refreshAttrInstances();
	}
	
	void editComments() {
		if (gra != null) {
			this.treeView.cancelCommentsEdit();
			Point p = this.treeView.getPopupMenuLocation();
			if (p == null) 
				p = new Point(200,200);
			GraGraTextualComment 
			comments = new GraGraTextualComment(this.treeView.getFrame(), p.x,
						p.y, gra.getBasisGraGra());

			if (comments != null)
				comments.setVisible(true);
		}
	}
	
	void makeParallelRule() {		
		if (gra != null) {
			int indxOfLastRule = node.getIndex(this.treeView.getTreeNodeOfRule(
												data.getGraGra().getRules().lastElement()));
				
			final SelectRulesDialog dialog = new SelectRulesDialog(this.treeView.getFrame(), 
														data.getGraGra().getBasisGraGra(),
														null, new Point(300, 200));
			dialog.setVisible(true);
			if (dialog.getRuleList() != null && !dialog.getRuleList().isEmpty()) {
				if (dialog.rulesContainsRuleScheme()) {
					JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
							"Currently selected rule list contains at least one Rule Scheme.\n"						
							+"Building a parallel rule is not available in this case.\n"
							+"Only plain rules will be supported. ",	
							"Feature not available",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				Rule failedRule = BaseFactory.theFactory().checkApplCondsOfRules(dialog.getRuleList());
				if (failedRule != null) {
					JOptionPane.showMessageDialog(this.treeView.getFrame(), 							
							"Currently selected rule list contains at least one invalid rule.\n"						
							+failedRule.getName()+":    "+failedRule.getErrorMsg(),	
							"Parallel Rule Failed",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				makeParallelRuleOfRules(data.getGraGra(), 
							dialog.getRuleList(), 
							node,
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
						+"Building a parallel rule was successful."
						+"\n\n"
						+"The rule:  "
						+parallelRule.getName()+"\n"
						+"is added at the end of the rule set.\n\n",	
						"Parallel Rule:  "+parallelRule.getName(),
						JOptionPane.INFORMATION_MESSAGE);
			} 					
			else {
				JOptionPane.showMessageDialog(this.treeView.getFrame(), 
							"Building a parallel rule failed!",	
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
	
	JMenuItem miRuleScheme, miParallelRule, miReload;
	JMenu menuNew;
	
	GraGraTreeView treeView;	
	TreePath path;
	DefaultMutableTreeNode node;
	GraGraTreeNodeData data;
	EdGraGra gra;
	String msg;
	final protected NodeEdgeTypeSelectionDialog typeSelectionDialog;

}
