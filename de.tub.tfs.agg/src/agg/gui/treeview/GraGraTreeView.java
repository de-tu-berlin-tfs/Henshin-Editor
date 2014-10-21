package agg.gui.treeview;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JFileChooser;

import agg.cons.AtomApplCond;
import agg.cons.EvalSet;
import agg.cons.Evaluable;
import agg.editor.impl.EdAtomApplCond;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdConstraint;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdPAC;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleConstraint;
import agg.editor.impl.EdRuleScheme;
import agg.editor.impl.EdType;
import agg.gui.AGGAppl;
import agg.gui.AGGToolBar;
import agg.gui.event.LoadEventListener;
import agg.gui.event.SaveEventListener;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.event.TransformEventListener;
import agg.gui.event.TransformEvent;
import agg.gui.event.EditEventListener;
import agg.gui.event.EditEvent;
import agg.gui.event.LoadEvent;
import agg.gui.icons.DeleteNestedACIcon;
import agg.gui.icons.NewGraGraIcon;
import agg.gui.icons.NewNestedACIcon;
import agg.gui.icons.NewTypeGraphIcon;
import agg.gui.icons.NewGraphIcon;
import agg.gui.icons.NewNACIcon;
import agg.gui.icons.NewPACIcon;
import agg.gui.icons.NewRuleIcon;
import agg.gui.icons.NewAtomicIcon;
import agg.gui.icons.NewConclusionIcon;
import agg.gui.icons.NewConstraintIcon;
import agg.gui.icons.DeleteGraGraIcon;
import agg.gui.icons.DeleteNACIcon;
import agg.gui.icons.DeletePACIcon;
import agg.gui.icons.DeleteRuleIcon;
import agg.gui.icons.DeleteTypeGraphIcon;
import agg.gui.icons.DeleteGraphIcon;
import agg.gui.icons.DeleteAtomicIcon;
import agg.gui.icons.DeleteConclusionIcon;
import agg.gui.icons.DeleteConstraintIcon;
import agg.gui.popupmenu.AmalgamRulePopupMenu;
import agg.gui.popupmenu.ApplFormulaPopupMenu;
import agg.gui.popupmenu.AtomApplCondPopupMenu;
import agg.gui.popupmenu.AtomicPopupMenu;
import agg.gui.popupmenu.AttrConditionPopupMenu;
import agg.gui.popupmenu.ConclusionPopupMenu;
import agg.gui.popupmenu.ConstraintPopupMenu;
import agg.gui.popupmenu.FilePopupMenu;
import agg.gui.popupmenu.GraGraPopupMenu;
import agg.gui.popupmenu.GraphPopupMenu;
import agg.gui.popupmenu.KernelRulePopupMenu;
import agg.gui.popupmenu.MultiRulePopupMenu;
import agg.gui.popupmenu.NACPopupMenu;
import agg.gui.popupmenu.NestedACPopupMenu;
import agg.gui.popupmenu.PACPopupMenu;
import agg.gui.popupmenu.RuleConstraintPopupMenu;
import agg.gui.popupmenu.RulePopupMenu;
import agg.gui.popupmenu.RuleSchemePopupMenu;
import agg.gui.popupmenu.RuleSequencePopupMenu;
import agg.gui.popupmenu.TypeGraphPopupMenu;
import agg.gui.saveload.AGGFileFilter;
import agg.gui.saveload.GraGraElementsStore;
import agg.gui.saveload.GraGraLoad;
import agg.gui.saveload.GraGraSave;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.gui.treeview.dialog.ConstraintPriorityDialog;
import agg.gui.treeview.dialog.FormulaGraphGUI;
import agg.gui.treeview.dialog.GraGraConstraintLayerDialog;
import agg.gui.treeview.dialog.GraphImportDialog;
import agg.gui.treeview.dialog.ItemImportDialog;
import agg.gui.treeview.dialog.RuleConstraintsDialog;
import agg.gui.treeview.nodedata.ApplFormulaTreeNodeData;
import agg.gui.treeview.nodedata.AtomicGraphConstraintTreeNodeData;
import agg.gui.treeview.nodedata.ConclusionAttrConditionTreeNodeData;
import agg.gui.treeview.nodedata.ConclusionTreeNodeData;
import agg.gui.treeview.nodedata.ConstraintTreeNodeData;
import agg.gui.treeview.nodedata.GraGraTextualComment;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.treeview.nodedata.GraGrasTreeNodeData;
import agg.gui.treeview.nodedata.GrammarTreeNodeData;
import agg.gui.treeview.nodedata.GraphTreeNodeData;
import agg.gui.treeview.nodedata.KernelRuleTreeNodeData;
import agg.gui.treeview.nodedata.MultiRuleTreeNodeData;
import agg.gui.treeview.nodedata.NACTreeNodeData;
import agg.gui.treeview.nodedata.NestedACTreeNodeData;
import agg.gui.treeview.nodedata.PACTreeNodeData;
import agg.gui.treeview.nodedata.RuleApplConstraintTreeNodeData;
import agg.gui.treeview.nodedata.RuleAtomicApplConstraintTreeNodeData;
import agg.gui.treeview.nodedata.RuleAttrCondTreeNodeData;
import agg.gui.treeview.nodedata.RuleSchemeTreeNodeData;
import agg.gui.treeview.nodedata.RuleSequenceTreeNodeData;
import agg.gui.treeview.nodedata.RuleTreeNodeData;
import agg.gui.treeview.nodedata.TypeGraphTreeNodeData;
import agg.gui.treeview.path.GrammarTreeNode;
import agg.gui.treeview.path.RuleSchemeTreeNode;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.Completion_NAC;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeError;
import agg.xt_basis.TypeSet;
import agg.xt_basis.agt.AmalgamatedRule;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.xt_basis.agt.RuleScheme;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.CondMember;
import agg.convert.AGG2ColorGraph;
import agg.convert.ConverterXML;
import agg.ruleappl.RuleSequence;
import agg.util.XMLHelper;
import agg.cons.AtomConstraint;
import agg.cons.Formula;
import agg.util.Pair;

/**
 * The GraGraTreeView displays a tree of graph grammar data. 
 * 
 * @author $Author: olga $
 * @version $Id: GraGraTreeView.java,v 1.72 2010/11/11 17:19:16 olga Exp $
 */
@SuppressWarnings("serial")
public class GraGraTreeView extends JPanel implements
		TransformEventListener, EditEventListener, LoadEventListener {

	/** Creates a tree view of graph grammars */
	public GraGraTreeView(JFrame aggappl) {
		super(new BorderLayout());

		this.applFrame = aggappl;

		setBorder(BorderFactory.createRaisedBevelBorder());

		/* create an empty tree of gragras with top node <GraGras> */
		this.top = new DefaultMutableTreeNode(new GraGrasTreeNodeData("GraGras"));
		this.treeModel = new GraGraTreeModel(this.top);
		this.tree = new JTree(this.treeModel);
		this.tree.setEditable(false);
		this.tree.setDoubleBuffered(true);
		this.tree.putClientProperty("JTree.lineStyle", "Angled");

		/* implement my MouseListener */
		mouseAdapter = new GraGraTreeViewMouseAdapter(this);
		this.tree.addMouseListener(mouseAdapter);
		
		/* implements my ActionListener */
		actionAdapter = new TreeViewActionAdapter(this);
		
		/* implement my KeyListener */
		keyAdapter = new GraGraTreeViewKeyAdapter(this);
		this.tree.addKeyListener(keyAdapter);
		
		this.gragraStore = new GraGraElementsStore(this);

		/* create file menu */
		this.file = new JMenu("File", true);
		createMenus();

		/* create tool bar */
		this.toolBar = new AGGToolBar(0);
		this.trash = this.toolBar.createTool("imageable", "trash",
				"Trash of grammar elements", "undoDelete", this.actionAdapter, false);
		createToolBar();
		
		/* Enable tool tips for the tree */
		ToolTipManager.sharedInstance().registerComponent(this.tree);

		/*
		 * Set the cell renderer of the tree to GraGraTreeCellRenderer for
		 * drawing
		 */
		this.tree.setCellRenderer(new GraGraTreeCellRenderer());

		/** Add the tree within the JScrollPane to the GraGraTreeView panel */
		add(new JScrollPane(this.tree), BorderLayout.CENTER);

		/* create file, gragra, rule, constraint, atomic, nac ...*/
		this.filePopupMenu = new FilePopupMenu(this.actionAdapter);
		this.gragraPopupMenu = new GraGraPopupMenu(this);
		this.rulePopupMenu = new RulePopupMenu(this);
		this.amalgamRulePopupMenu = new AmalgamRulePopupMenu(this);
		this.ruleSchemePopupMenu = new RuleSchemePopupMenu(this);
		this.kernRulePopupMenu = new KernelRulePopupMenu(this);
		this.multiRulePopupMenu = new MultiRulePopupMenu(this);
		this.ruleSequencePopupMenu = new RuleSequencePopupMenu(this);
		this.nacPopupMenu = new NACPopupMenu(this);
		this.pacPopupMenu = new PACPopupMenu(this);
		this.acPopupMenu = new NestedACPopupMenu(this);
		this.constraintPopupMenu = new ConstraintPopupMenu(this);
		this.atomicPopupMenu = new AtomicPopupMenu(this);
		this.ruleConstraintPopupMenu = new RuleConstraintPopupMenu(this);
		this.atomApplCondPopupMenu = new AtomApplCondPopupMenu(this);
		this.conclusionPopupMenu = new ConclusionPopupMenu(this);
		this.typeGraphPopupMenu = new TypeGraphPopupMenu(this);
		this.graphPopupMenu = new GraphPopupMenu(this);
		this.attrConditionPopupMenu = new AttrConditionPopupMenu(this);
		this.applFormulaPopupMenu = new ApplFormulaPopupMenu(this);
		
		/* create save and load instances */
		this.gragraSave = new GraGraSave(this.applFrame);
		this.gragraLoad = new GraGraLoad(this.applFrame);
		this.gragraLoad.addLoadEventListener(this);
		
		this.trash.addMouseListener(mouseAdapter);
		
		// multiple selection
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	}

	void propagateSelectedTreeItem() {
		this.tree.setEditable(false);
		if (!this.wasMoved) {
			if (!this.selPath.equals(this.editorPath)) {
//				int row = this.tree.getRowForPath(this.selPath);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath
													.getLastPathComponent();
				if (node.getUserObject() != null && ((GraGraTreeNodeData)node.getUserObject()).isNestedAC()) {
					if (this.currentRule 
							!= ((GraGraTreeNodeData)node.getUserObject()).getNestedAC().getRule()) {
						this.selectPath(this.getTreePathOfGrammarElement(
								((GraGraTreeNodeData)node.getUserObject()).getNestedAC().getRule()));	
						
						this.tree.getCellRenderer().getTreeCellRendererComponent(
								tree, node, true, true, false, this.tree.getRowForPath(this.selPath), true);
						return;
					}
				}
	
				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.SELECT, this.selPath));
				setFlagForNew();
				this.editorPath = this.selPath;
				if ((DefaultMutableTreeNode) this.editorPath.getLastPathComponent() 
							!= (DefaultMutableTreeNode) this.treeModel.getRoot()) {
					setCurrentData(this.editorPath);
					fireTreeViewEvent(new TreeViewEvent(
								this, TreeViewEvent.SELECTED,
								this.editorPath));
				}
			} else {
				fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.BACKGROUND_CLICK, this.selPath));
			}
		}
	}

	public TreePath getSelectedPath() {
		return this.selPath;
	}
	
	public TreePath getEditorPath() {
		return this.editorPath;
	}
	
	public TreeViewActionAdapter getActionAdapter() {
		return this.actionAdapter;
	}
	
	protected void executeCommand(final String command) {
		if (command.equals("newGraGra")) {
			addGraGra();
			resetEnabledOfFileMenuItems(command);
			this.filePopupMenu.resetEnabledOfFileMenuItems(command);
			resetEnabledOfToolBarItems(command);
		} else if (command.equals("newTypeGraph"))
			addTypeGraph();
		else if (command.equals("newGraph"))
			addGraph(null, null);
		else if (command.equals("newRule"))
			addRule();
		else if (command.equals("newNestedAC"))
			addNestedAC(false);
		else if (command.equals("makeGACFromRHS"))
			addNestedAC(true);
		else if (command.equals("newNAC"))
			addNAC(false);
		else if (command.equals("makeNACFromRHS"))
			addNAC(true);
		else if (command.equals("newPAC"))
			addPAC();
		else if (command.equals("newAtomic"))
			addAtomic();
		else if (command.equals("newConclusion"))
			addConclusion();
		else if (command.equals("newConstraint"))
			addConstraint();		
		else if (command.equals("newAtomic"))
			addAtomic();
		else if (command.equals("newConclusion"))
			addConclusion();
		else if (command.equals("newConstraint"))
			addConstraint();
		else if (command.equals("convertAtomicsOfRule"))
			doPostApplicationConditionOfRule();
		else if (command.equals("checkOneAtomic"))
			checkOne();
		else if (command.equals("checkOneConstraint"))
			checkOne();		
		else if (command.equals("checkAtomics"))
			gragraPopupMenu.doAtomics(1);
		else if (command.equals("checkConstraints"))
			gragraPopupMenu.doAtomics(2);		
		else if (command.equals("editConstraint"))
			editConstraint();
		
		else if (command.equals("open"))
			loadGraGra();
		else if (command.equals("save"))
			saveGraGra();
		else if (command.equals("saveAs"))
			saveAsGraGra();
		else if (command.equals("exportGraphJPEG"))
			exportGraphJPEG();
		else if (command.equals("exportJPEG"))
			exportJPEG();
		else if (command.equals("openBase"))
			loadBaseGraGra();
		else if (command.equals("saveAsBase"))
			saveAsBaseGraGra();
		
		else if (command.equals("exportGXL"))
			exportGraGra("GXL");
		else if (command.equals("exportGTXL"))
			exportGraGra("GTXL");
		
		else if (command.equals(GraGraPopupMenu.EXPORT_BY_TYPE_TO_COLOR_GRAPH))
			exportGraGra(GraGraPopupMenu.EXPORT_BY_TYPE_TO_COLOR_GRAPH);
		else if (command.equals(GraGraPopupMenu.EXPORT_TO_COLOR_GRAPH))
			exportGraGra(GraGraPopupMenu.EXPORT_TO_COLOR_GRAPH);
		
		else if (command.equals("importGGX"))
			importGraGra("GGX");
		else if (command.equals("importGXL"))
			importGraGra("GXL");
		else if (command.equals("importGTXL"))
			importGraGra("GTXL");
		else if (command.equals("importOMONDOXMI"))
			importGraGra("OMONDOXMI");
		
		else if (command.equals(GraGraPopupMenu.IMPORT_BY_TYPE_FROM_COLOR_GRAPH))
			importGraGra(GraGraPopupMenu.IMPORT_BY_TYPE_FROM_COLOR_GRAPH);
		else if (command.equals(GraGraPopupMenu.IMPORT_FROM_COLOR_GRAPH))
			importGraGra(GraGraPopupMenu.IMPORT_FROM_COLOR_GRAPH);
		
		else if (command.equals("delete")) {
			delete("");
			resetEnabledOfFileMenuItems(command);
			this.filePopupMenu.resetEnabledOfFileMenuItems(command);
			resetEnabledOfToolBarItems(command);
			if (this.tree.getRowCount() == 1)
				fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.EMPTY));
		} else if (command.equals("deleteGraGra"))
			delete("GraGra");
		else if (command.equals("deleteGraph"))
			delete("Graph");
		else if (command.equals("deleteTypeGraph"))
			delete("TypeGraph");
		else if (command.equals("deleteRule"))
			delete("Rule");
		else if (command.equals("deleteRuleSequence"))
			delete("RuleSequence");
		else if (command.equals("deleteNAC"))
			delete("NAC");
		else if (command.equals("deletePAC"))
			delete("PAC");
		else if (command.equals("deleteNestedAC"))
			delete("NestedAC");
		else if (command.equals("deleteAtomic"))
			delete("Atomic");
		else if (command.equals("deleteConclusion"))
			delete("Conclusion");
		else if (command.equals("deleteConstraint"))
			delete("Constraint");
		else if (command.equals("deleteRuleConstraints"))
			delete("RuleConstraints");
		else if (command.equals("deleteRuleConstraint"))
			delete("RuleConstraint");
		else if (command.equals("deleteAtomApplCond"))
			delete("AtomApplCond");
		else if (command.equals("moveRule")
				|| command.equals("moveRuleScheme"))
			moveRule();
		else if (command.equals("copyRule"))
			copy("Rule");
		else if (command.equals("copyRuleScheme"))
			copy("RuleScheme");
		else if (command.equals("concurrentRule"))
			makeConcurrentRule(true);
		else if (command.equals("disableRuleScheme")) {
			disable("RuleScheme", true);
		}else if (command.equals("enableRuleScheme")) {
			disable("RuleScheme", false);
		} else if (command.equals("disableRule")) {
			disable("Rule", true);
		} else if (command.equals("enableRule")) {
			disable("Rule", false);
		} else if (command.equals("disableNAC")) {
			disable("NAC", true);
		} else if (command.equals("enableNAC")) {
			disable("NAC", false);
		} else if (command.equals("disablePAC")) {
			disable("PAC", true);
		} else if (command.equals("enablePAC")) {
			disable("PAC", false);
		} else if (command.equals("disableNestedAC")) {
			disable("NestedAC", true);
		} else if (command.equals("enableNestedAC")) {
			disable("NestedAC", false);
		}else if (command.equals("disableAttrCondition")) {
			disable("AttrCondition", true);
		} else if (command.equals("enableAttrCondition")) {
			disable("AttrCondition", false);
		} else if (command.equals("disableConstraint")) {
			disable("Constraint", true);
		} else if (command.equals("enableConstraint")) {
			disable("Constraint", false);
		} else if (command.equals("setRuleLayer"))
			setRuleLayer();
		else if (command.equals("setConstraintLayer"))
			setConstraintLayer();
		else if (command.equals("setConstraintPriority"))
			setConstraintPriority();
		else if (command.equals("showNAC"))
			showNAC();
		else if (command.equals("hideNAC"))
			hideNAC();
		else if (command.equals("showAttrConditions"))
			showRuleAttrConditions(null);
		else if (command.equals("setRulePriority"))
			setRulePriority();
		else if (command.equals("undoDelete"))
			undoDelete();
		else if (command.equals("undoDeleteTypeGraph"))
			undoDeleteTypeGraph();
		else if (command.equals("undoDeleteRule"))
			undoDeleteRule();
		else if (command.equals("undoDeleteNAC"))
			undoDeleteNAC();
		else if (command.equals("undoDeletePAC"))
			undoDeletePAC();
		else if (command.equals("undoDeleteNestedAC"))
			undoDeleteNestedAC();
		else if (command.equals("undoDeleteAtomicConstraint"))
			undoDeleteAtomicConstraint();
		else if (command.equals("undoDeleteConstraint"))
			undoDeleteConstraint();
		else if (command.equals("undoDeleteAtomicConclusion"))
			undoDeleteAtomicConclusion();
		else if (command.equals("commentGraph"))
			editTextualComments("commentGraph");
		else if (command.equals("commentRule"))
			editTextualComments("commentRule");
		else if (command.equals("commentNAC"))
			editTextualComments("commentNAC");
		else if (command.equals("commentPAC"))
			editTextualComments("commentPAC");
		else if (command.equals("commentNestedAC"))
			editTextualComments("commentNestedAC");
		else if (command.equals("commentAtomConstraint"))
			editTextualComments("commentAtomConstraint");
		else if (command.equals("commentConstraint"))
			editTextualComments("commentConstraint");
		else if (command.equals("exit")) {
			final Object[] options = { "SAVE", "EXIT", "CANCEL" };
			exitAppl(options);
		}
	}

	public void exitAppl(final Object[] options) {
//		Object[] options = { "SAVE", "EXIT", "CANCEL" };
//		Object[] options = { "SAVE", "EXIT" };

		int answ = 1;
		Vector<EdGraGra> gragras = getGraGras();
		for (int i=0; i<gragras.size(); i++) {
			EdGraGra gra = gragras.get(i);
			if (gra.isChanged()) {
				answ = exitWarning(options, "grammar", gra.getName());
				if (answ == 0) { // SAVE
					saveGraGra(gra);
					answ = 1;
				} else if (answ == 1) { // EXIT
					fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.EXIT));
					System.exit(0);
				} 
			}
		}
		if (answ == 1)
			System.exit(0);
	}
	
	public synchronized void addSaveEventListener(SaveEventListener l) {
		this.gragraSave.addSaveEventListener(l);
	}

	public synchronized void removeSaveEventListener(SaveEventListener l) {
		this.gragraSave.removeSaveEventListener(l);
	}

	public synchronized void addLoadEventListener(LoadEventListener l) {
		this.gragraLoad.addLoadEventListener(l);
	}

	public synchronized void removeLoadEventListener(LoadEventListener l) {
		this.gragraLoad.removeLoadEventListener(l);
	}

	public synchronized void addTreeViewEventListener(TreeViewEventListener l) {
		if (!this.treeEventListeners.contains(l))
			this.treeEventListeners.addElement(l);
	}

	public synchronized void removeTreeViewEventListener(TreeViewEventListener l) {
		if (this.treeEventListeners.contains(l))
			this.treeEventListeners.removeElement(l);
	}

	public synchronized void addTreeModelListener(TreeModelListener l) {
		getTree().getModel().addTreeModelListener(l);
	}

	public synchronized void removeTreeModelListener(TreeModelListener l) {
		getTree().getModel().removeTreeModelListener(l);

	}

	public ActionListener getActionListener() {
		return this.actionAdapter;
	}
	
	public synchronized void fireTreeViewEvent(final TreeViewEvent e) {
		for (int i = 0; i < this.treeEventListeners.size(); i++)
			this.treeEventListeners.elementAt(i).treeViewEventOccurred(e);
	}

	/** Gets my main menus */
	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	public JMenu getFileMenu() {
		return this.file;
	}
	
	/** Gets my tool bar */
	public JToolBar getToolBar() {
		return this.toolBar;
	}

	/** Returns my main application frame. */
	public JFrame getFrame() {
		return this.applFrame;
	}

	/** Gets my minimum dimension */
	public Dimension getMinimumSize() {
		return new Dimension(10, 10);
	}

	/** Gets my preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}

	/** Returns my tree */
	public JTree getTree() {
		return this.tree;
	}

	public TreeNode getTopTreeNode() {
		return this.top;
	}
	
	/** Returns my tree model */
	public GraGraTreeModel getTreeModel() {
		return this.treeModel;
	}

	public GraGraElementsStore getGraGraStore() {
		return this.gragraStore;
	}
	
	public EdGraGra getCurrentGraGra() {
		return this.currentGraGra;
	}

	public EdGraph getCurrentGraph() {
		return this.currentGraph;
	}
	
	public EdRule getCurrentRule() {
		return this.currentRule;
	}
	
	public EdConstraint getCurrentConstraint() {
		return this.currentConstraint;
	}
	
	public EdRule getCurrentRuleScheme() {
		return this.currentRuleScheme;
	}
	
	public RuleSequence getCurrentRuleSequence() {
		return this.currentRuleSequence;
	}
	
	public void graphDidChange() {
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.GRAPH_CHANGED));
	}

	/** Selects the tree node at the specified position */
	public void selectPath(final int x, final int y) {
		this.selPath = this.tree.getPathForLocation(x, y);
		fireTreeViewEvent(new TreeViewEvent(this,
				TreeViewEvent.SELECT, this.selPath));
		this.tree.setSelectionPath(this.selPath);
		this.tree.treeDidChange();
		this.editorPath = this.selPath;
		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		setFlagForNewData(sd);
		if ((DefaultMutableTreeNode) this.editorPath
				.getLastPathComponent() != (DefaultMutableTreeNode) this.treeModel
				.getRoot()) {
			setCurrentData(this.editorPath);
			fireTreeViewEvent(new TreeViewEvent(
						this, TreeViewEvent.SELECTED,
					this.editorPath));
		}
	}

	/** Selects the tree node at the specified row */
	public void selectPath(final int row) {
		selectPath(this.tree.getPathForRow(row));
	}

	public void selectPath(TreePath path) {
		this.selPath = path;
		fireTreeViewEvent(new TreeViewEvent(this,
				TreeViewEvent.SELECT, this.selPath));
		this.tree.setSelectionPath(this.selPath);
		this.tree.treeDidChange();
		this.editorPath = this.selPath;
		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		setFlagForNewData(sd);
		if ((DefaultMutableTreeNode) this.editorPath
				.getLastPathComponent() != (DefaultMutableTreeNode) this.treeModel
				.getRoot()) {
			setCurrentData(this.editorPath);
			fireTreeViewEvent(new TreeViewEvent(
						this, TreeViewEvent.SELECTED,
					this.editorPath));
		}
	}
	
	void setUndirectedArcsOfGraphs(final ActionEvent e) {
		this.undirectedArcs = ((JCheckBoxMenuItem) e.getSource()).isSelected();
	}
	
	void resetUndirectedArcProperty(boolean b) {
		if (this.applFrame instanceof AGGAppl) {
			((AGGAppl)this.applFrame).getPreferences().selectArcUndirected(b);
			this.undirectedArcs = b;
		}
	}
	
	void setNoParallelArcsOfGraphs(final ActionEvent e) {
		this.nonparallelArcs = ((JCheckBoxMenuItem) e.getSource()).isSelected();
	}
	
	void setCheckEmptyAttrs(final ActionEvent e) {
		this.checkEmptyAttrs = ((JCheckBoxMenuItem) e.getSource()).isSelected();
		this.resetAllowEmptyAttrs(!this.checkEmptyAttrs);
	}
	
	void setGraTraOption_layered(final ActionEvent e) {
		if (this.currentGraGra == null) {
			return;
		}
		this.layered = ((JRadioButton) e.getSource()).isSelected();
		if (this.ruleSequence) {
			this.ruleSequence = false;
			this.hideRuleSequence();
		}
		if (this.layered) {
			this.currentGraGra.getBasisGraGra().addGraTraOption(GraTraOptions.LAYERED);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.PRIORITY);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.RULE_SEQUENCE);
			this.priority = false;			
			this.ruleSequence = false;
		} else {
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.LAYERED);
		}
		
		this.treeModel.ruleNameChanged(this.currentGraGra, this.layered);
		this.treeModel.constraintNameChanged(this.currentGraGra, this.layered);
	} 
	
	void setGraTraOption_priority(final ActionEvent e) {
		if (this.currentGraGra == null)
			return;
		this.priority = ((JRadioButton) e.getSource()).isSelected();
		if (this.ruleSequence) {
			this.ruleSequence = false;
			this.hideRuleSequence();
		}
		if (this.priority) {
			this.currentGraGra.getBasisGraGra().addGraTraOption(GraTraOptions.PRIORITY);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.LAYERED);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.RULE_SEQUENCE);
			this.layered = false;
			this.ruleSequence = false;
		} else {
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.PRIORITY);
		}

		this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
		this.treeModel.constraintNameChanged(this.currentGraGra, this.layered, this.priority);
	} 
	
	void setGraTraOption_ruleSequence(final ActionEvent e) {
		if (this.currentGraGra == null)
			return;

		boolean ruleSeqShown = this.ruleSequence;
		
		this.ruleSequence = ((JRadioButton) e.getSource()).isSelected();
		if (this.ruleSequence) {
			this.currentGraGra.getBasisGraGra().addGraTraOption(GraTraOptions.RULE_SEQUENCE);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.LAYERED);
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.PRIORITY);
			this.priority = false;
			this.layered = false;
			if (!ruleSeqShown) {	
				this.showRuleSequence();
			}
		} else {
			this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.RULE_SEQUENCE);
		}
		
		this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
		this.treeModel.constraintNameChanged(this.currentGraGra, this.layered, this.priority);
	}
	
	void setGraTraOption_nondeterministically() {
		if (this.currentGraGra == null) {
			return;
		}
		this.layered = false;
		this.priority = false;
		if (this.ruleSequence) {
			this.ruleSequence = false;
			this.hideRuleSequence();
		}
		
		this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.LAYERED);
		this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.PRIORITY);
		this.currentGraGra.getBasisGraGra().removeGraTraOption(GraTraOptions.RULE_SEQUENCE);
		
		this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
		this.treeModel.constraintNameChanged(this.currentGraGra, this.layered, this.priority);
	}

	
	/** Loads a gragra */
	public boolean addGraGra(final EdGraGra grammar) {
		if (grammar != null) {
			BaseFactory.theFactory().notify(grammar.getBasisGraGra());
			grammar.update();
			
			GrammarTreeNode grammarTreeNode = new GrammarTreeNode(grammar);
			int indx = grammarTreeNode.insertIntoTree(this);
			
			this.tree.treeDidChange();
			
			/* put gragra in editor */
			propagateGraGraToEditor(indx);
			
			if (this.currentGraGra.getGraTraOptions().contains("layered")) {
				this.layered = true;
				this.priority = false;
				this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
				this.treeModel.constraintNameChanged(this.currentGraGra, this.layered,
						this.priority);
			} else if (this.currentGraGra.getGraTraOptions().contains("priority")) {
				this.priority = true;
				this.layered = false;
				this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
				this.treeModel.constraintNameChanged(this.currentGraGra, this.layered,
						this.priority);
			}
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
			
			this.directory = this.gragraLoad.getDirName();
			resetEnabledOfFileMenuItems("open");
			this.filePopupMenu.resetEnabledOfFileMenuItems("open");
			resetEnabledOfToolBarItems("open");
						
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
			
//			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.NEW));
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SELECTED,
					this.editorPath));

			return true;
		}
		return false;
	}

	/** Adds a new gragra node to the tree */
	public EdGraGra addGraGra() {
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.treeModel.getRoot();
		int newIndex = parent.getChildCount();
		String name = "GraGra";
		if (parent.getChildCount() > 0) {
			name = "GraGra" + newIndex;
		}
		if (!((GraGraTreeModel) this.tree.getModel()).isValid(
				((GraGraTreeModel) this.tree.getModel()).getGraGraNames(), name, "")) {
			name = name + "_";
		}
		// test undirected
		final EdGraGra newGraGra = new EdGraGra(name, !undirectedArcs, !nonparallelArcs);
		newGraGra.getTypeSet().getBasisTypeSet().setAllowEmptyAttr(!this.checkEmptyAttrs);
		
		final GraGraTreeNodeData sdGraGra = new GrammarTreeNodeData(newGraGra);
		final DefaultMutableTreeNode 
		newGraGraNode = new DefaultMutableTreeNode(sdGraGra);
		sdGraGra.setTreeNode(newGraGraNode);
		this.treeModel.insertNodeInto(newGraGraNode, parent, newIndex);
		if (!this.tree.isExpanded(this.tree.getPathForRow(0))) {
			this.tree.expandPath(this.tree.getPathForRow(0));
		}
		int graIndex = this.tree.getRowCount() - 1;

		final GraGraTreeNodeData 
		sdGraph = new GraphTreeNodeData(newGraGra.getGraph());
		final DefaultMutableTreeNode 
		newGraphNode = new DefaultMutableTreeNode(sdGraph);
		sdGraph.setTreeNode(newGraphNode);
		parent = newGraGraNode;
		newIndex = parent.getChildCount();
		this.treeModel.insertNodeInto(newGraphNode, parent, newIndex);

		final GraGraTreeNodeData 
		sdRule = new RuleTreeNodeData(newGraGra.getRules().firstElement());
		final DefaultMutableTreeNode newRuleNode = new DefaultMutableTreeNode(sdRule);
		sdRule.setTreeNode(newRuleNode);
		newIndex = parent.getChildCount();
		this.treeModel.insertNodeInto(newRuleNode, parent, newIndex);

		// here add RuleScheme
		
		if (!this.tree.isExpanded(this.tree.getPathForRow(graIndex))) {
			this.tree.expandPath(this.tree.getPathForRow(graIndex));
		}
		// load GraGra in editor
		this.tree.setSelectionRow(graIndex);
		this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
		this.editorPath = this.selPath;
		setFlagForNew();
		
//		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.NEW));
		
		setCurrentData(this.editorPath);
		
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SELECTED,
				this.editorPath));

		this.layered = false;
		this.priority = false;
		this.treeModel.ruleNameChanged(newGraGra, this.layered, this.priority);
		this.treeModel.constraintNameChanged(newGraGra, this.layered, this.priority);

		newGraGra.setChanged(false);
		
		return newGraGra;
	}

	
	/* 
	 * If the specified EdGraGra gra is null and the EdGraph g is null, then take the
	 * current host graph of the current grammar and add the copy of it to the
	 * grammar, otherwise adds the specified graph to the specified grammar.
	 */
	public boolean addGraph(final EdGraGra gra, EdGraph graph) {
		String gname = (graph != null) ? graph.getName() : "";
		String ggname = (gra != null) ? gra.getName() : "";
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
								"<html><body>"
								+"Bad selection to add a graph  " + gname+"."
								+ "\n Please select suitable grammar  " + ggname+".",
								"",
								JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add a graph  " + gname+"."
							+ "\n Please select suitable grammar  " + ggname+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (gra != null && gra != eGra && graph != null) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add graph: " + graph.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eGra.isEditable()) {
			if (graph == null) {
				graph = new EdGraph(BaseFactory.theFactory().createGraph(eGra.getTypeSet()
						.getBasisTypeSet(), true), eGra.getTypeSet());
				graph.getBasisGraph().setName(
						graph.getBasisGraph().getName() + eGra.getGraphs().size());
			}
			if (!eGra.getGraphs().contains(graph))
				eGra.addGraph(graph);
			graph.setGraGra(eGra);
			GraGraTreeNodeData sdGraph = new GraphTreeNodeData(graph);
			DefaultMutableTreeNode newGraphNode = new DefaultMutableTreeNode(
					sdGraph);
			sdGraph.setTreeNode(newGraphNode);
			int indx = eGra.getGraphs().size() - 1;
			if (eGra.getTypeGraph() != null)
				indx++;
			this.treeModel.insertNodeInto(newGraphNode, parent, indx);
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	} 

	/** Adds a new type graph node for the selected gragra. */
	public EdGraph addTypeGraph() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select a grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra.getTypeSet().getTypeGraph() != null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+" The type graph already exists."
					+"</body></html>",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		EdGraph typeGraph = eGra.createTypeGraph();		

//		GraGraTreeNodeData sdTypeGraph = new GraGraTreeNodeData(typeGraph, true);
		GraGraTreeNodeData sdTypeGraph = new TypeGraphTreeNodeData(typeGraph);
		sdTypeGraph.setString("[D]TypeGraph");

		DefaultMutableTreeNode newTypeGraphNode = new DefaultMutableTreeNode(
				sdTypeGraph);
		sdTypeGraph.setTreeNode(newTypeGraphNode);
		this.treeModel.insertNodeInto(newTypeGraphNode, parent, 0);

		// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);

		return typeGraph;
	} // addTypeGraph

	private boolean addTypeGraph(final EdGraGra gra, final EdGraph g) {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select a grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra.getTypeSet().getTypeGraph() != null && eGra.getTypeSet().getTypeGraph() != g) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+" The type graph already exists."
					+"</body></html>",
					"",
					JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (eGra != gra) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select the apropriate grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		eGra.setTypeGraph(g);
		
//		GraGraTreeNodeData sdTypeGraph = new GraGraTreeNodeData(g, true);
		GraGraTreeNodeData sdTypeGraph = new TypeGraphTreeNodeData(g);
		sdTypeGraph.setString("[D]TypeGraph");
		DefaultMutableTreeNode newTypeGraphNode = new DefaultMutableTreeNode(sdTypeGraph);
		sdTypeGraph.setTreeNode(newTypeGraphNode);
		this.treeModel.insertNodeInto(newTypeGraphNode, parent, 0);
		// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
		return true;
	} // addTypeGraph

    
    /** Adds a new rule scheme node of the selected gragra node */   
    public boolean addRuleScheme(final EdGraGra gra, final EdRuleScheme newRS) {
    	return addRuleSchemeAt(gra, newRS, -1);
    }
    
    private boolean addRuleSchemeAt(final EdGraGra gra, final EdRuleScheme newRS, int index) {
    	if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select a grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra != gra) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add a rule scheme: " + newRS.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eGra.isEditable()) {
			eGra.addRule(newRS);
			int indx = index;
			if (indx < 0) {
				indx = eGra.getGraphs().size() + eGra.getRules().size()-1;
				if (eGra.getTypeSet().getTypeGraph() != null)
					indx++;
			}	
			
			putRuleSchemeIntoTree(newRS, parent, indx);
			
	//      if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
	        
	        return true;
		} else {
			lockWarning();
			return false;
		}
    }
   
    public void putRuleSchemeIntoTree(final EdRuleScheme newRS,
			final DefaultMutableTreeNode parentNode, int index) {
			
		GraGraTreeNodeData sdRuleScheme = new RuleSchemeTreeNodeData(newRS);
        DefaultMutableTreeNode newRuleSchemeNode = new DefaultMutableTreeNode(sdRuleScheme);
        sdRuleScheme.setTreeNode(newRuleSchemeNode);
        this.treeModel.insertNodeInto(newRuleSchemeNode, parentNode, index);
        this.treeModel.ruleNameChanged(newRuleSchemeNode, this.layered);
        // make kernel rule tree node
        DefaultMutableTreeNode parent = newRuleSchemeNode;
        GraGraTreeNodeData sdKernelRule = new KernelRuleTreeNodeData(newRS.getKernelRule());
        DefaultMutableTreeNode newKernelRuleNode = new DefaultMutableTreeNode(sdKernelRule);
        sdKernelRule.setTreeNode(newKernelRuleNode);
        int indx = 0;
        this.treeModel.insertNodeInto(newKernelRuleNode, parent, indx);	
        int nn = 0;
		// add GACs tree nodes 
        nn = this.addGACsToRuleTreeNode(newRS.getKernelRule(), newKernelRuleNode, nn);
        // add NACs tree nodes
		nn = nn+newRS.getKernelRule().getNestedACs().size();
		this.addNACsToRuleTreeNode(newRS.getKernelRule(), newKernelRuleNode, nn);
		// add PACs tree nodes
        nn = nn+newRS.getKernelRule().getNACs().size();
        this.addPACsToRuleTreeNode(newRS.getKernelRule(), newKernelRuleNode, nn);
        // add attr condition tree node
        this.addAttrCondToRuleTreeNode(newRS.getKernelRule(), newKernelRuleNode);
        
		for (int j=0; j < newRS.getMultiRules().size(); j++) {
			EdRule mr = newRS.getMultiRules().get(j);
			GraGraTreeNodeData sdMultiRule = new MultiRuleTreeNodeData(mr);
	        DefaultMutableTreeNode newMultiRuleNode = new DefaultMutableTreeNode(sdMultiRule);
	        sdMultiRule.setTreeNode(newMultiRuleNode);
	        this.treeModel.insertNodeInto(newMultiRuleNode, parent, j+1);	
	        
			// add GACs tree nodes 
	        nn = 0;
	        nn = this.addGACsToRuleTreeNode(mr, newMultiRuleNode, nn);
	        // add NACs tree nodes
			nn = nn+mr.getNestedACs().size();
			this.addNACsToRuleTreeNode(mr, newMultiRuleNode, nn);
			// add PACs tree nodes
	        nn = nn+mr.getNACs().size();
	        this.addPACsToRuleTreeNode(mr, newMultiRuleNode, nn);
	     // add attr condition tree node
	        this.addAttrCondToRuleTreeNode(mr, newMultiRuleNode);
		}
    }
    
    
    private void addNestedACs(List<EdNestedApplCond> acs, DefaultMutableTreeNode parent) {
    	for (int i = 0; i < acs.size(); i++) {
			EdNestedApplCond ac = acs.get(i);
			GraGraTreeNodeData sd = new NestedACTreeNodeData(ac);
			DefaultMutableTreeNode acNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(acNode);
			this.treeModel.insertNodeInto(acNode, parent, parent.getChildCount());
			
			String f = ac.getNestedMorphism().getFormulaText();
			if (!"true".equals(f)) {
				final GraGraTreeNodeData fdata = new ApplFormulaTreeNodeData(f, true, ac);
				fdata.setString(f);
				final DefaultMutableTreeNode fnode = new DefaultMutableTreeNode(fdata);
				fdata.setTreeNode(fnode);
				this.treeModel.insertNodeInto(fnode, acNode, 0);
			}
			
			addNestedACs(ac.getEnabledACs(), acNode); 
		}	
    }
    
	/** Adds a new rule node of the selected gragra node */
	public EdRule addRule() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select a grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra.isEditable()) {
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size();
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Rule" + eGra.getRules().size();
			name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eGra, name);
			EdRule newRule = eGra.createRule(name);
			
			GraGraTreeNodeData sdRule = new RuleTreeNodeData(newRule);
			if (this.layered) {
				String tag = "[" + newRule.getBasisRule().getLayer() + "]";
				sdRule.setString(tag, name);
			} else if (this.priority) {
				String tag = "[" + newRule.getBasisRule().getPriority() + "]";
				sdRule.setString(tag, name);
			}
			DefaultMutableTreeNode newRuleNode = new DefaultMutableTreeNode(sdRule);
			sdRule.setTreeNode(newRuleNode);
			this.treeModel.insertNodeInto(newRuleNode, parent, newIndex);
			this.fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.RULE_ADDED, this.selPath));
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
			return newRule;
		} else {
			lockWarning();
			return null;
		}
	}

	/** Adds a new rule node of the selected gragra node */
	private boolean addRule(final EdGraGra gra, final EdRule newRule) {
//		System.out.println(this.selPath);
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
								"<html><body>"
								+"Bad selection to add a rule: " + newRule.getName()+"."
								+ "\n Please select the grammar: "
								+ gra.getName()+".",
								"",
								JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add a rule: " + newRule.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode 
		parent = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra != gra) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add rule: " + newRule.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eGra.isEditable()) {
			eGra.addRule(newRule);
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size() - 1;
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = newRule.getBasisRule().getName();
			GraGraTreeNodeData sdRule = new RuleTreeNodeData(newRule);
			String tagD = "";
			if (!newRule.getBasisRule().isEnabled()) {
				tagD = "[D]";
			}
			if (this.layered) {
				String tagL = "[" + newRule.getBasisRule().getLayer() + "]";
				sdRule.setString(tagD, tagL, name);
			} else if (this.priority) {
				String tagP = "[" + newRule.getBasisRule().getPriority() + "]";
				sdRule.setString(tagD, tagP, name);
			} else {
				sdRule.setString(tagD, name);
			}
			DefaultMutableTreeNode newRuleNode = new DefaultMutableTreeNode(sdRule);
			sdRule.setTreeNode(newRuleNode);
			this.treeModel.insertNodeInto(newRuleNode, parent, newIndex);
			int nn = 0;
			// add formula and GACs tree nodes 
			nn = this.addGACsToRuleTreeNode(newRule, newRuleNode, nn);
			
			nn = nn+newRule.getNestedACs().size();
			// add NAGs tree nodes 
			this.addNACsToRuleTreeNode(newRule, newRuleNode, nn);
	
			nn = nn + newRule.getNACs().size();
			// add PAGs tree nodes 
			this.addPACsToRuleTreeNode(newRule, newRuleNode, nn);
			
			// add rule attr conditions
			addAttrCondToRuleTreeNode(newRule, newRuleNode);
	
			this.fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.RULE_ADDED, this.selPath));
			
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	private int addGACsToRuleTreeNode(
			final EdRule rule, 
			final DefaultMutableTreeNode ruleNode, 
			int nn) {
		int indx = 0;
//		// add formula tree node 
		String f = rule.getBasisRule().getFormulaStr();
		if (!"true".equals(f)) {
			indx++;
			final GraGraTreeNodeData fdata = new ApplFormulaTreeNodeData(f, true, rule);
			fdata.setString(f);
			final DefaultMutableTreeNode fnode = new DefaultMutableTreeNode(fdata);
			fdata.setTreeNode(fnode);
			this.treeModel.insertNodeInto(fnode, ruleNode, 0);
			nn++;
		}
		// add GAGs tree nodes 
		for (int i = 0; i < rule.getNestedACs().size(); i++) {
			EdNestedApplCond ac = (EdNestedApplCond)rule.getNestedACs().get(i);
			GraGraTreeNodeData sd = new NestedACTreeNodeData(ac);
			DefaultMutableTreeNode acNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(acNode);
			this.treeModel.insertNodeInto(acNode, ruleNode, nn);
			nn++;
			
			f = ac.getNestedMorphism().getFormulaText();
			if (!"true".equals(f)) {
				final GraGraTreeNodeData fdata = new ApplFormulaTreeNodeData(f, true, ac);
				fdata.setString(f);
				final DefaultMutableTreeNode fnode = new DefaultMutableTreeNode(fdata);
				fdata.setTreeNode(fnode);
				this.treeModel.insertNodeInto(fnode, acNode, 0);
			}
			addNestedACs(ac.getEnabledACs(), acNode); 
		}
		return indx;
	}
	
	private void addNACsToRuleTreeNode(
			final EdRule rule, 
			final DefaultMutableTreeNode ruleNode, 
			int nn) {
		// add NAGs tree nodes 
		for (int i = 0; i < rule.getNACs().size(); i++) {
			EdNAC nac = rule.getNACs().get(i);
			GraGraTreeNodeData sd = new NACTreeNodeData(nac);
			DefaultMutableTreeNode nacNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(nacNode);
			this.treeModel.insertNodeInto(nacNode, ruleNode, nn+i);
		}
	}
	
	private void addPACsToRuleTreeNode(
			final EdRule rule, 
			final DefaultMutableTreeNode ruleNode, 
			int nn) {
		// add PAGs tree nodes 
		for (int i = 0; i < rule.getPACs().size(); i++) {
			EdPAC pac = rule.getPACs().get(i);
			GraGraTreeNodeData sd = new PACTreeNodeData(pac);
			DefaultMutableTreeNode pacNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(pacNode);
			this.treeModel.insertNodeInto(pacNode, ruleNode, nn+i);
		}
	}
	
	private void addAttrCondToRuleTreeNode(
			final EdRule rule, 
			final DefaultMutableTreeNode ruleNode) {
		CondTuple conds = (CondTuple) rule.getBasisRule().getAttrContext().getConditions();
		for (int i = 0; i < conds.getSize(); i++) {
			CondMember c = (CondMember) conds.getMemberAt(i);			
			GraGraTreeNodeData cdata = new RuleAttrCondTreeNodeData(
											c, rule);
			cdata.setString(c.getExprAsText());
			DefaultMutableTreeNode cchild = new DefaultMutableTreeNode(cdata);
			cdata.setTreeNode(cchild);
			this.treeModel.insertNodeInto(cchild, ruleNode, ruleNode.getChildCount());			
		}
	}
	
	protected void inheritanceWarning() {
		javax.swing.JOptionPane
				.showMessageDialog(
						this.applFrame,
						"<html><body>"
						+"Sorry!<br>This item is not available for the graph grammar <br>"
						+"with node type inheritance.",
						"", javax.swing.JOptionPane.WARNING_MESSAGE);
	}

	/** Shows existing rule sequences.*/
    private void addRuleSequences() {
    	if (this.selPath != null) {
    		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
			EdGraGra eGra = getGraGra(node);
			if (eGra == null && this.currentGraGra != null ) {
				eGra = this.currentGraGra;
				node = this.getTreeNodeOfGrammar(eGra.getBasisGraGra());
			}
			if (eGra != null) {
				int indx = eGra.getGraphs().size() 
								+ eGra.getRules().size()
								+ eGra.getAtomics().size()
								+ eGra.getConstraints().size();
							
				if (eGra.getTypeSet().getTypeGraph() != null)
					indx++;
				
				for (int i=0; i<eGra.getBasisGraGra().getRuleSequences().size(); i++) {
					RuleSequence rseq = eGra.getBasisGraGra().getRuleSequences().get(i);
					rseq.setCriticalPairOption(((AGGAppl)this.getFrame()).getCPA().getCriticalPairOption());				
						
					if (this.getTreeNodeOfGrammarElement(rseq) == null) {
						GraGraTreeNodeData sdRuleSequence = new RuleSequenceTreeNodeData(rseq);
					    DefaultMutableTreeNode newRuleSequenceNode = new DefaultMutableTreeNode(sdRuleSequence);
					    sdRuleSequence.setTreeNode(newRuleSequenceNode);
					    this.treeModel.insertNodeInto(newRuleSequenceNode, node, indx+i);
					}
				}
			}
    	}
    }
    
    public void showRuleSequence() {
    	if (!this.ruleSequenceHidden)
    		this.hideRuleSequence();
    	
    	this.addRuleSequences();
    	this.ruleSequenceHidden = false;
    }
    
    public void hideRuleSequence() {
	    if (this.currentGraGra != null) {
//	    	TreePath path = this.getTreePathOfGrammarElement(this.currentGraGra);
	    	for (int i = 0; i < this.currentGraGra.getBasisGraGra().getRuleSequences().size(); i++) {
	    		TreePath rsPath = this.getTreePathOfGrammarElement(this.currentGraGra.getBasisGraGra().getRuleSequences().get(i));
	    		if (rsPath != null) {
	    			this.treeModel.removeNodeFromParent((DefaultMutableTreeNode)rsPath.getLastPathComponent());
	    			this.ruleSequenceHidden = true;
	    		} 			
	   		}
    	}
    }
    
    public void lockWarning() {
		JOptionPane.showMessageDialog(this.applFrame,
				"Cannot execute this action. This grammar is locked.",
				"Edit Formula", JOptionPane.ERROR_MESSAGE);
	}
    
	/** Adds a new atomic constraint node for the selected gragra node */
	public EdAtomic addAtomic() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"
						+"Bad selection.<br> Please select a grammar.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		/* New atomics are OK, when new rules are. */
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select a grammar.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);

		 if (eGra.isEditable()) {	
			// handleRuleConstraints(parent, false);
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size()
					+ eGra.getAtomics().size();
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Atomic";
			if (eGra.getAtomics().size() > 0)
				name = name + eGra.getAtomics().size();
			name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eGra, name);
			EdAtomic newAtomic = eGra.createAtomic(name);
	
			GraGraTreeNodeData sd = new AtomicGraphConstraintTreeNodeData(newAtomic);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex);
	
			/* add first conclusion */
			newIndex = newAtomic.getConclusions().size() - 1;
			EdAtomic aConclusion = newAtomic.getConclusion(0);
			name = aConclusion.getMorphism().getName();
	
			sd = new ConclusionTreeNodeData(aConclusion);
			DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(aNode);
			this.treeModel.insertNodeInto(aNode, newNode, newIndex);
	
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
	
			return newAtomic;
		 }
		 else {
			 lockWarning();
			 return null;
		 }
	}

	public boolean addAtomic(final EdGraGra gra, final EdAtomic newAtomic) {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
								"<html><body>"
								+"Bad selection to add a graph constraint: "
								+ newAtomic.getName()+"."
								+ "\n Please select suitable grammar: "
								+ gra.getName()+".",
								"",
								JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add a graph constraint: "
							+ newAtomic.getName()+"."
							+ "\n Please select suitable grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra != gra) {
			JOptionPane.showMessageDialog(this.applFrame,
							"<html><body>"
							+"Bad selection to add a graph constraint: "
							+ newAtomic.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eGra.isEditable()) {
			// handleRuleConstraints(parent, false);
			eGra.addAtomic(newAtomic);
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size()
					+ eGra.getAtomics().size() - 1;
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			GraGraTreeNodeData sd = new AtomicGraphConstraintTreeNodeData(newAtomic);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex);
			/* add conclusions */
			for (int i = 0; i < newAtomic.getConclusions().size(); i++) {
				EdAtomic aConclusion = newAtomic.getConclusion(i);
				sd = new ConclusionTreeNodeData(aConclusion);
				DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(sd);
				sd.setTreeNode(aNode);
				this.treeModel.insertNodeInto(aNode, newNode, i);
			}
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	/** Adds a new conclusion to the selected atomic node */
	public EdAtomic addConclusion() {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select an atomic.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) parent.getUserObject();
		if (!data.isAtomic()) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection.<br> Please select an atomic.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}

		if (data.getAtomic().getGraGra().isEditable()) {
			EdAtomic parentAtomic = data.getAtomic();
			TreePath graPath = this.selPath.getParentPath();
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			handleRuleConstraints(graNode, false, parentAtomic);
	
			int newIndex = parentAtomic.getConclusions().size();
			String name = "Conclusion" + parentAtomic.getConclusions().size();
			EdAtomic newConclusion = parentAtomic.createNextConclusion(name);
			// System.out.println("newConclusion:
			// "+newConclusion.getBasisAtomic().getAtomicName()+" . "+
			// newConclusion.getBasisAtomic().getName());
			GraGraTreeNodeData sd = new ConclusionTreeNodeData(newConclusion);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return newConclusion;
		}
		else {
			lockWarning();
			return null;
		}
	}

	public boolean addConclusion(final EdAtomic atomic, final EdAtomic newConclusion) {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add conclusion: "
							+ newConclusion.getName()+"."
							+ "\n Please select suitable graph constraint: "
							+ atomic.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) parent.getUserObject();
		if (!data.isAtomic()) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add conclusion: "
							+ newConclusion.getName()+"."
							+ "\n Please select suitable graph constraint: "
							+ atomic.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		EdAtomic parentAtomic = data.getAtomic();
		if (parentAtomic != atomic) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add conclusion: "
							+ newConclusion.getName()+"."
							+ "\n Please select suitable graph constraint: "
							+ atomic.getName()+".",
							"",
							JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (parentAtomic.getGraGra().isEditable()) {
			TreePath graPath = this.selPath.getParentPath();
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			handleRuleConstraints(graNode, false, parentAtomic);
			parentAtomic.addConclusion(newConclusion);
			// System.out.println(parentAtomic.getConclusions().size());
			int newIndex = parentAtomic.getConclusions().size();
			GraGraTreeNodeData sd = new ConclusionTreeNodeData(newConclusion);
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex - 1);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	public boolean setLevelOfTypeGraphCheck(final EdGraGra gragra, int level, boolean showErrorMsg) {
		// change level and collect errors
		Collection<TypeError> errors = gragra.setLevelOfTypeGraphCheck(level);
		if (errors != null && errors.size() > 0) {			
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TYPE_ERROR, ""));			
			if (showErrorMsg) {
				String message = "<html><body>";
				message = message.concat(
						"Cannot change the type graph mode.<br>Some type mismatches found.");
				Iterator<TypeError> iter = errors.iterator();
				int i = 0;
				while ((iter.hasNext()) && (i < 20)) {
					message = message.concat("\n");
					message = message.concat(iter.next().getMessage());
					i++;
				}
				if ((i >= 20) && (iter.hasNext())) {
					message = message.concat("\n ... more undisplayed mismatches");
				}
				JOptionPane.showMessageDialog(this.applFrame,
						message,
						"Type Graph Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		} 
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.NO_TYPE_ERROR, ""));
		return true;
	}
	
	public void setTypeGraphLevel(final int level) {
		this.typeGraphPopupMenu.setTypeGraphLevel(level);
	}

	public void updateTypeGraphTreeNode(final DefaultMutableTreeNode node, final EdGraGra gragra) {		
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		if (data.isTypeGraph()) {
			String mode = "";
			switch (gragra.getBasisGraGra().getTypeSet()
					.getLevelOfTypeGraphCheck()) {
			case TypeSet.DISABLED:
				mode = "[D]";
				break;
			case TypeSet.ENABLED_INHERITANCE:
				mode = "[Inh]";
				break;	
			case TypeSet.ENABLED:
				mode = "[E]";
				break;
			case TypeSet.ENABLED_MAX:
				mode = "[Em]";
				break;
			case TypeSet.ENABLED_MAX_MIN:
				mode = "[Emm]";
				break;
			default:
				mode = "[?]";
			}
			String str = mode
					+ gragra.getTypeGraph().getBasisGraph().getName();
			data.setString(str);
			gragra.setChanged(true);
			this.repaint();
		}
	}


	/**
	 * Checks currently selected graph consistency constraint at the current host graph.
	 */	
	public void checkOne() {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame, 
					"<html><body>"
					+"Bad selection."
					+"<br> Please select a graph constraint.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		EdGraGra gra = getGraGra((DefaultMutableTreeNode) node.getParent());
		if (gra == null) {
			String s = "Yikes.  Internal brokeness.";
			JOptionPane.showMessageDialog(this.applFrame, s);
			return;
		}
		/*
		 * if(gra.getBasisGraGra().getTypeSet().hasInheritance()) {
		 * inheritanceWarning(); return; }
		 */

		TreeViewEvent e = new TreeViewEvent(this, TreeViewEvent.CHECK);
		fireTreeViewEvent(e);
		boolean valid = false;
		boolean good = false;
		String thing = "";
		String mesg;
		boolean changed = gra.isChanged();
		if (data.isAtomic()) {
			thing = "atomic";
			EdAtomic eatom = data.getAtomic();
			valid = eatom.getBasisAtomic().isValid();
			if (valid) {
				if (gra.getBasisGraGra().isGraphReadyForTransform()) {
									
//					if (gra.getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX_MIN
//							&& this.setLevelOfTypeGraphCheck(gra, gra.getLevelOfTypeGraphCheck())) 
					{						
					
						eatom.getBasisAtomic().setMorphismCompletionStrategy(
							gra.getBasisGraGra().getMorphismCompletionStrategy());
						good = eatom.getBasisAtomic().eval(
							gra.getBasisGraGra().getGraph());
					}
				} else {
					mesg = "The host graph isn't ready! Please check its attributes.";
					JOptionPane.showMessageDialog(this.applFrame, 
							mesg, 
							"Graph not ready", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} else if (data.isConstraint()) {
			thing = "constraint (formula)";
			EdConstraint econs = data.getConstraint();
			valid = econs.getBasisConstraint().isValid();
			if (valid) {
				if (gra.getBasisGraGra().isGraphReadyForTransform()) {
					
//					if (gra.getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX_MIN
//							&& this.setLevelOfTypeGraphCheck(gra, gra.getLevelOfTypeGraphCheck())) 
					{
						
						gra.getBasisGraGra()
							.setMorphismCompletionStrategyOfGraphConstraints();
						good = econs.getBasisConstraint().eval(
							gra.getBasisGraGra().getGraph());
					}
				} else {
					mesg = "The Host graph is not ready! Please check its attributes.";
					JOptionPane.showMessageDialog(this.applFrame, 
							mesg,
							"Graph not ready", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		
		if (!valid) {
			mesg = "This " + thing + " isn't valid.  No checking done.";
			JOptionPane.showMessageDialog(this.applFrame, mesg, "Not valid", JOptionPane.ERROR_MESSAGE);
		}
		else if (!good) {
			mesg = "The graph doesn't fulfill this " + thing + ".";
			JOptionPane.showMessageDialog(this.applFrame, mesg, "Not fulfilled", JOptionPane.ERROR_MESSAGE);
		}
		else {
			mesg = "The graph fulfills this " + thing + ".";
			JOptionPane.showMessageDialog(this.applFrame, mesg, "Fulfilled", JOptionPane.INFORMATION_MESSAGE);
		}
		
//		e = new TreeViewEvent(this, TreeViewEvent.CHECK_DONE);
//		e.setMessage(mesg);
//		fireTreeViewEvent(e);
		
		gra.setChanged(changed);
	}


	/**
	 * Creates post condition of the selected rule from atomics of its gragra
	 */
	public void doPostApplicationConditionOfRule() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1
					&& ((DefaultMutableTreeNode) this.top.getChildAt(0))
							.getChildCount() == 2) {
				this.tree.setSelectionRow(3);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Bad selection.<br> Please select a rule.",
						"",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		} else if (this.top.getChildCount() == 1
				&& ((DefaultMutableTreeNode) this.top.getChildAt(0)).getChildCount() == 2) {
			this.tree.setSelectionRow(3);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		}

		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>Bad selection.<br> Please select a rule.",
					"",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		final EdRule eRule = getRule(parent);

//		if (eRule.getGraGra().getBasisGraGra().getTypeSet().usesInheritance()) {
//			inheritanceWarning();
//			return;
//		}

		// formulas dialog
		RuleConstraintsDialog ruleConstraintsDialog = new RuleConstraintsDialog(
				this.applFrame, eRule);
		ruleConstraintsDialog.showGUI();
		if (!ruleConstraintsDialog.isCancelled()) {
			if (!ruleConstraintsDialog.getFormulas().isEmpty()) {
				handleRuleConstraints(parent, false, null);
				eRule.getBasisRule().setUsedFormulas(
						ruleConstraintsDialog.getFormulas());
				Thread thread = new Thread() {
					public void run() {
						GraGraTreeView.this.msg = eRule.getBasisRule().convertUsedFormulas();
						eRule.getGraGra().setChanged(true);
					}
				};
				thread.start();

				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.CONVERT_STEP,
						"Creating Post Application Condition. Please wait ..."));
				JOptionPane.showMessageDialog(this.applFrame,
						"Creating Post Application Condition ... ");
				while (thread.isAlive()) {}

				if (!this.msg.equals("")) {
					if (eRule.getBasisRule().getConstraints().size() == 0) {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.CONVERT_STEP,
								"Cannot convert Graph Atomics to Post Application Condition of rule.  "
									+ this.msg));
					}
					JOptionPane.showMessageDialog(this.applFrame,
								"<html><body>"						
								+ this.msg,
								"Create Post Application Condition",
								JOptionPane.ERROR_MESSAGE);
					
				} 
				
				if (eRule.getBasisRule().getConstraints().size() > 0) {
					handleRuleConstraints(parent, true, null);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.CONVERT_STEP,
							"Creating Post Application Condition ... done."));
				}
			} 
//			else
//				JOptionPane.showMessageDialog(this.applFrame,
//						"<html><body>Bad selection.<br> Please select a rule.",
//						"", JOptionPane.WARNING_MESSAGE);
		}
	}

	/** Adds a new constraint (formula) node for the selected gragra node */
	public EdConstraint addConstraint() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				String s = "<html><body>Bad selection.<br> Please select a grammar.";
				JOptionPane.showMessageDialog(this.applFrame, s, "", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		/* New constraints are OK, when new rules are. */
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>Bad selection.<br> Please select a grammar.</body></html>",
					"", JOptionPane.WARNING_MESSAGE);
			return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra.isEditable()) {
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size()
					+ eGra.getAtomics().size() + eGra.getConstraints().size();
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = "Constraint";
			if (eGra.getConstraints().size() > 0)
				name = name + eGra.getConstraints().size();
			name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eGra, name);
			EdConstraint newConstraint = eGra.createConstraint(name);
	
			handleRuleConstraints(parent, false, newConstraint);
	
			GraGraTreeNodeData sd = new ConstraintTreeNodeData(newConstraint);
			if (this.layered && !newConstraint.getBasisConstraint().getLayer().isEmpty()) {
				String tag = "["
						+ newConstraint.getBasisConstraint().getLayerAsString()
						+ "]";
				sd.setString(tag, name);
			}
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex);
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
	
			return newConstraint;
		} else {
			lockWarning();
			return null;
		}
	}

	public boolean addConstraint(final EdGraGra gra, final EdConstraint newConstraint) {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>"
						+"Bad selection to add formula: "
								+ newConstraint.getName()+"."
								+ "\n Please select the grammar: "
								+ gra.getName()+".",
								"", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newRuleOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add formula: "
							+ newConstraint.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		EdGraGra eGra = getGraGra(parent);
		if (eGra != gra) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add formula: "
							+ newConstraint.getName()+"."
							+ "\n Please select the grammar: " + gra.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (eGra.isEditable()) {
			handleRuleConstraints(parent, false, newConstraint);
			eGra.addConstraint(newConstraint);
			int newIndex = eGra.getGraphs().size() + eGra.getRules().size()
					+ eGra.getAtomics().size() + eGra.getConstraints().size() - 1;
			if (eGra.getTypeSet().getTypeGraph() != null)
				newIndex++;
			String name = newConstraint.getName();
			GraGraTreeNodeData sd = new ConstraintTreeNodeData(newConstraint);
			String tagD = "";
			if (!newConstraint.getBasisConstraint().isEnabled())
				tagD = "[D]";
			if (this.layered && !newConstraint.getBasisConstraint().getLayer().isEmpty()) {
				String tagL = "["
						+ newConstraint.getBasisConstraint().getLayerAsString()
						+ "]";
				sd.setString(tagD, tagL, name);
			} else if (this.priority
					&& !newConstraint.getBasisConstraint().getPriority().isEmpty()) {
				String tagP = "["
						+ newConstraint.getBasisConstraint().getPriorityAsString()
						+ "]";
				sd.setString(tagD, tagP, name);
			}
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newNode);
			this.treeModel.insertNodeInto(newNode, parent, newIndex);
			// if (!this.tree.isExpanded(this.selPath)) this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	public void editConstraint() {
		if (this.selPath == null) {
			String s = "<html><body>Bad selection.<br> Please select a constraint to edit.</body></html>";
			JOptionPane.showMessageDialog(this.applFrame, s);
			return;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		DefaultMutableTreeNode granode = (DefaultMutableTreeNode) parent
				.getParent();

		EdGraGra eGra = (granode != null) ? getGraGra(granode) : null;
		EdConstraint con = getConstraint(parent);
		if (eGra == null || con == null) {
			return;
		}
//		FormulaDialog d = new FormulaDialog(this.applFrame,
//				" Editor of Graph Constraint (Formula) ", true);

		String ownerName = "graph constraint : "+con.getName();
		FormulaGraphGUI d = new FormulaGraphGUI(this.applFrame, ownerName,
				" Graph editor of Formula above Atomic Graph Constraints.", 
				" An empty graph is the TRUE case.",
				true);
		d.setExportJPEG(this.exportJPEG);
		d.disableFORALL(true);
		
		List<Evaluable> atomics = eGra.getBasisGraGra().getListOfAtomicObjects();

		String oldf = con.getBasisConstraint().getAsString(atomics);

		d.setVarsAsObjs(eGra.getAtomics(), oldf);
		d.setLocation(200, 100);
		while (true) {
			d.setVisible(true);
			if (!d.isCanceled()) {
				boolean formulaChanged = d.isChanged();
				String f = d.getFormula();
				if (!con.getBasisConstraint().setFormula(atomics, f))
					JOptionPane
							.showMessageDialog(
									this.applFrame,
									"The formula definition failed. Please correct.",
									" Formula failed ", JOptionPane.WARNING_MESSAGE);
				else if (formulaChanged && !f.equals(oldf)) {
					handleRuleConstraints(granode, false, con);
					con.setVarSet(atomics, eGra.getAtomicNames());
					con.update();
					eGra.setChanged(true);
					break;
				} else
					break;
			}
			else break;
		}
	}

	/** Adds a new NAC node to the selected rule node */
	public EdNAC addNAC(boolean copyRHS) {
		if (this.selPath == null) {			
			if (this.top.getChildCount() == 1
					&& ((DefaultMutableTreeNode) this.top.getChildAt(0))
							.getChildCount() == 2) {
				this.tree.setSelectionRow(3);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Bad selection.<br> Please select a rule.</body></html>",
						"", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1
				&& ((DefaultMutableTreeNode) this.top.getChildAt(0)).getChildCount() == 2) {
			this.tree.setSelectionRow(3);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>Bad selection.<br> Please select a rule.</body></html>",
					"", JOptionPane.WARNING_MESSAGE);
			return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
					.getLastPathComponent();
		final EdRule eRule = getRule(parent);
		if (eRule.getGraGra().isEditable()) {
			int newIndex = eRule.getNestedACs().size() + eRule.getNACs().size(); 
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			String name = "Nac";
			if (eRule.getNACs().size() > 0)
				name = name + eRule.getNACs().size();
			name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eRule, name);			
			EdNAC newNAC = copyRHS? eRule.createNACDuetoRHS(name) : eRule.createNAC(name, false);
			putNACIntoTree(newNAC, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return newNAC;
		} else {
			lockWarning();
			return null;
		}
	}

	public void putNACIntoTree(final EdNAC nac,
			final DefaultMutableTreeNode parentNode, int index) {
		GraGraTreeNodeData sd = new NACTreeNodeData(nac);
		String name = nac.getName();
		if (!nac.getMorphism().isEnabled()) {
			String tag = "[D]";
			sd.setString(tag, name);
		}
		DefaultMutableTreeNode newACNode = new DefaultMutableTreeNode(sd);
		sd.setTreeNode(newACNode);
		this.treeModel.insertNodeInto(newACNode, parentNode, index);
	}
	
	
	public boolean addNAC(final EdRule r, final EdNAC newNAC) {
		// System.out.println("addNAC(r, newNAC)");
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1
					&& ((DefaultMutableTreeNode) this.top.getChildAt(0))
							.getChildCount() == 2) {
				this.tree.setSelectionRow(3);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>"
						+"Bad selection to add a NAC: " + newNAC.getName()+"."
								+ "\n Please select the rule: " + r.getName()+".",
								"", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1
				&& ((DefaultMutableTreeNode) this.top.getChildAt(0)).getChildCount() == 2) {
			this.tree.setSelectionRow(3);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a NAC: " + newNAC.getName()+"."
							+ "\n Please select the rule: " + r.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		final EdRule eRule = getRule(parent);
		// System.out.println(eRule.hashCode()+" "+r.hashCode());
		if (eRule != r) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a NAC: " + newNAC.getName()+"."
							+ "\n Please select the rule: " + r.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eRule.getGraGra().isEditable()) {
			int newIndex = eRule.getNestedACs().size() + eRule.getNACs().size();
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			eRule.addNAC(newNAC);
			putNACIntoTree(newNAC, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	/** Adds a new PAC node for the selected rule node */
	public EdPAC addPAC() {
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1
					&& ((DefaultMutableTreeNode) this.top.getChildAt(0))
							.getChildCount() == 2) {
				this.tree.setSelectionRow(3);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"Bad selection.\n Please select a rule.",
						"", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1
				&& ((DefaultMutableTreeNode) this.top.getChildAt(0)).getChildCount() == 2) {
			this.tree.setSelectionRow(3);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return null;
		}

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		final EdRule eRule = getRule(parent);
		if (eRule.getGraGra().isEditable()) {
			int newIndex = eRule.getNestedACs().size() + eRule.getNACs().size() + eRule.getPACs().size();
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			String name = "Pac";
			if (eRule.getPACs().size() > 0)
				name = name + eRule.getPACs().size();
			name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eRule, name);
			final EdPAC newPAC = eRule.createPAC(name, false);
			putPACIntoTree(newPAC, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return newPAC;
		} else {
			lockWarning();
			return null;
		}
	}

	public void putPACIntoTree(final EdPAC pac,
			final DefaultMutableTreeNode parentNode, int index) {
		GraGraTreeNodeData sd = new PACTreeNodeData(pac);
		String name = pac.getName();
		if (!pac.getMorphism().isEnabled()) {
			String tag = "[D]";
			sd.setString(tag, name);
		}
		DefaultMutableTreeNode newACNode = new DefaultMutableTreeNode(sd);
		sd.setTreeNode(newACNode);
		this.treeModel.insertNodeInto(newACNode, parentNode, index);
	}
	
	public boolean addPAC(final EdRule r, final EdPAC newPAC) {
		// System.out.println("addPAC(r, newPAC)");
		if (this.selPath == null) {
			if (this.top.getChildCount() == 1
					&& ((DefaultMutableTreeNode) this.top.getChildAt(0))
							.getChildCount() == 2) {
				this.tree.setSelectionRow(3);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>"
						+"Bad selection to add a PAC: " + newPAC.getName()+"."
								+ "\n Please select the rule: " + r.getName()+".",
								"", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else if (this.top.getChildCount() == 1
				&& ((DefaultMutableTreeNode) this.top.getChildAt(0)).getChildCount() == 2) {
			this.tree.setSelectionRow(3);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a PAC: " + newPAC.getName()+"."
							+ "\n Please select the rule: " + r.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		final EdRule eRule = getRule(parent);
		if (eRule != r) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a PAC: " + newPAC.getName()+"."
							+ "\n Please select the rule: " + r.getName()+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (eRule.getGraGra().isEditable()) {
			int newIndex = eRule.getNestedACs().size() + eRule.getNACs().size() + eRule.getPACs().size();
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			eRule.addPAC(newPAC);
			putPACIntoTree(newPAC, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return true;
		} else {
			lockWarning();
			return false;
		}
	}

	/** Adds a new nested AC node for the selected rule node */
	public EdPAC addNestedAC(boolean copyRHS) {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame,
				"Bad selection.\n Please select a rule or a general application condition.",
				"", JOptionPane.WARNING_MESSAGE);
			return null;
		} 
		else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a rule or a general application condition.",
					"", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		EdPAC newAC = null;
		String name = "ApplCond";
		
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		int newIndex = 0;
		if (parent.getChildCount() > 0) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
			if (child != null
				&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
				newIndex = 1;
			}
		}
		Object parObj = getRule(parent);
		if (parObj != null) {
			final EdRule eRule = (EdRule) parObj;
			if (eRule.getGraGra().isEditable()) {
				newIndex = newIndex + eRule.getNestedACs().size();
				String nb = (eRule.getNestedACs().size() > 0)? String.valueOf(eRule.getNestedACs().size()): "";
				name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(eRule, "ApplCond".concat(nb));
				newAC = copyRHS? eRule.createGACDuetoRHS(name) : eRule.createNestedAC(name, false);	
			} else
				lockWarning();
		}
		else {
			parObj = this.getNestedAC(parent);
			if (parObj != null) {
				final EdNestedApplCond parAC = (EdNestedApplCond) parObj;	
				if (parAC.getGraGra().isEditable()) {
					newIndex = newIndex + parAC.getSizeOfNestedACs();
					String nb = (newIndex > 0)? String.valueOf(newIndex): "";
					name = ((GraGraTreeModel) this.tree.getModel()).makeNewName(parAC, "NestCond".concat(nb));
					newAC = parAC.createNestedAC(name, false);
				} else
					lockWarning();
			}
		}
		if (newAC != null) {
			GraGraTreeNodeData sd = new NestedACTreeNodeData(newAC);
			DefaultMutableTreeNode newACNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(newACNode);
			this.treeModel.insertNodeInto(newACNode, parent, newIndex);
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
		}
		return newAC;
	}

	
	
	public boolean addNestedAC(final EdRule r, final EdNestedApplCond newAC) {		
		return this.addNestedCond(r, "rule", r.getName(), newAC);
	}
	
	public boolean addNestedAC(final EdNestedApplCond ac, final EdNestedApplCond newAC) {
		return this.addNestedCond(ac, "condition", ac.getName(), newAC);
	}
	
	private boolean addNestedCond(final Object parentObj, String parentKind, String parentName, final EdNestedApplCond newAC) {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a (Nested) Application Condition: " + newAC.getName()+"."
							+ "\n Please select a "+parentKind+" : " + parentName+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (this.selPath != this.tree.getSelectionPath()) {
			this.selPath = this.tree.getSelectionPath();
			setFlagForNew();
		}
		if (!this.newApplCondOK) {
			JOptionPane.showMessageDialog(this.applFrame,
					"<html><body>"
					+"Bad selection to add a (Nested) Application Condition: " + newAC.getName()+"."
							+ "\n Please select a "+parentKind+" : "  + parentName+".",
							"", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		int newIndex = -1;
		if (parentObj instanceof EdRule) {
			final EdRule eRule = getRule(parent);
			if (eRule != parentObj) {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>"
						+"Bad selection to add a (Nested) Application Condition: " + newAC.getName()+"."
								+ "\n Please select a "+parentKind+" : "  + parentName+".",
								"", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			newIndex = eRule.getNestedACs().size();
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			eRule.addNestedAC(newAC);
		}
		else if (parentObj instanceof EdNestedApplCond) {
			final EdNestedApplCond ac = getNestedAC(parent);
			if (ac != parentObj) {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>"
						+"Bad selection to add a (Nested) Application Condition: " + newAC.getName()+"."
								+ "\n Please select a "+parentKind+" : "  + parentName+".",
								"", JOptionPane.WARNING_MESSAGE);
				return false;
			}	
			newIndex = ac.getSizeOfNestedACs();
			if (parent.getChildCount() > 0) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)parent.getFirstChild();		
				if (child != null
					&& ((GraGraTreeNodeData) child.getUserObject()).isApplFormula()) {
					newIndex++;
				}
			}
			ac.addNestedAC(newAC);
		}
		if (newIndex != -1) {
			GraGraTreeNodeData sd = new NestedACTreeNodeData(newAC);
			if (!newAC.getMorphism().isEnabled()) {
				String tag = "[D]";
				sd.setString(tag, newAC.getName());
			}
			putNestedACIntoTree(newAC, parent, newIndex);
						
			if (!this.tree.isExpanded(this.selPath))
				this.tree.expandPath(this.selPath);
			return true;
		}
		
		return false;
	}
	
	public void putNestedACIntoTree(final EdNestedApplCond ac,
			final DefaultMutableTreeNode parentNode, int index) {
		GraGraTreeNodeData sd = new NestedACTreeNodeData(ac);
		String name = ac.getName();
		if (!ac.getMorphism().isEnabled()) {
			String tag = "[D]";
			sd.setString(tag, name);
		}
		DefaultMutableTreeNode newACNode = new DefaultMutableTreeNode(sd);
		sd.setTreeNode(newACNode);
		this.treeModel.insertNodeInto(newACNode, parentNode, index);
		
		// add subtree of newAC
		addSubTreeOfNestedAC(ac, newACNode);
	}
	
	@SuppressWarnings("unused")
	private void addSubTreeOfNestedAC(final EdNestedApplCond ac, DefaultMutableTreeNode acNode) {
		int indx = 0;
		// add formula
		if (!"true".equals(ac.getNestedMorphism().getFormulaText())) {
			List<String> allVars = ac.getNestedMorphism().getNameOfEnabledACs();
			List<Integer> vars = Formula.getFromStringAboveList(
					ac.getNestedMorphism().getFormulaText(), allVars);
			
			final GraGraTreeNodeData conddata = new ApplFormulaTreeNodeData(
					ac.getNestedMorphism().getFormulaText(), true, ac);
			final DefaultMutableTreeNode condchild = new DefaultMutableTreeNode(
					conddata);
			conddata.setTreeNode(condchild);
			this.treeModel.insertNodeInto(condchild, acNode, indx); //acNode.getChildCount());	
			indx++;
		}
		for (int i=0; i<ac.getNestedACs().size(); i++) {
			final EdNestedApplCond cond = ac.getNestedACs().get(i);
			GraGraTreeNodeData sd = new NestedACTreeNodeData(cond);
			if (!cond.getMorphism().isEnabled()) {
				String tag = "[D]";
				sd.setString(tag, cond.getName());
			}
			DefaultMutableTreeNode condNode = new DefaultMutableTreeNode(sd);
			sd.setTreeNode(condNode);
			this.treeModel.insertNodeInto(condNode, acNode, indx);
			indx++;
			
			// add subtree of newAC
			addSubTreeOfNestedAC(cond, condNode);
		}
	}
	
	/** Activate resp. deactivate this object, file menu and file tool bar */
	public void resetEnabled(boolean enable) {
		this.setEnabled(enable);
		this.file.setEnabled(enable);
		// resetEnabledOfToolBarItems(enable);
	}

	/** The selected rule node will be set for moving. */
	public void moveRule() {
		if (this.tree.isExpanded(this.selPath))
			this.tree.collapsePath(this.selPath);
		this.movedNode = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		this.tmpSelPath = this.tree.getSelectionPath();
		if (this.tmpSelPath == null) {
			this.movedNode = null;
			this.wasMoved = false;
			return;
		}
		this.tmpSelNode = (DefaultMutableTreeNode) this.tmpSelPath.getLastPathComponent();
		this.wasMoved = true;
		this.applFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}

	/** Set layer of the selected rule node . */
	public void setRuleLayer() {
		DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) ruleNode.getUserObject();
		if (sd.isRule()) {
			EdRule r = sd.getRule();
			String initLayer = String.valueOf(r.getBasisRule().getLayer());
			String layer = new String("-1");
			// System.out.println((Integer.valueOf(layer)).intValue());
			Integer i = Integer.valueOf(layer);
			while ((i == null) || !(i.intValue() >= 0)) {
				layer = JOptionPane.showInputDialog(this.applFrame,
						"Please set the rule layer >= 0.",
						initLayer);
				if (layer == null) {
					i = Integer.valueOf(initLayer);
					break;
				}
				try {
					i = Integer.valueOf(layer);
				} catch (NumberFormatException ex) {
					i = null;
				}
			}
			if (r.getBasisRule().getLayer() != i.intValue()) {
				r.getBasisRule().setLayer(i.intValue());
				r.getGraGra().setChanged(true);
				r.getGraGra().getBasisGraGra().oneRuleHasChangedLayer();
				r.getGraGra().getBasisGraGra().refreshConstraintsForLayer();
				if (this.layered) {
					this.treeModel.ruleNameChanged(ruleNode, this.layered);
					this.treeModel.nodeChanged(ruleNode);
					this.treeModel.constraintNameChanged(r.getGraGra(), this.layered);
					this.tree.treeDidChange();
					fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.RULE_LAYER, this.selPath));
				}
			}
		}
	}

	/** Set rule layer of the selected constraint (Formula) node . */
	public void setConstraintLayer() {
		DefaultMutableTreeNode formulaNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) formulaNode
				.getUserObject();
		if (sd.isConstraint()) {
			EdConstraint c = sd.getConstraint();
			Vector<Formula> v = new Vector<Formula>(1);
			v.add(c.getBasisConstraint());
			GraGraConstraintLayerDialog lg = new GraGraConstraintLayerDialog(
					this.applFrame, v, c.getGraGra().getBasisGraGra().getLayers());
//			lg.setGraGra(c.getGraGra());
			lg.showGUI();
			if (!lg.isCancelled()) {
				c.getGraGra().setChanged(true);
				if (this.layered) {
					this.treeModel.constraintNameChanged(c.getGraGra(), this.layered);
					this.treeModel.nodeChanged(formulaNode);
					this.tree.treeDidChange();
				}
			}
			// System.out.println(c.getBasisConstraint().getName() +"
			// "+c.getBasisConstraint().getLayer());
		}
	}

	/** Set rule priority of the selected constraint (Formula) node . */
	public void setConstraintPriority() {
		DefaultMutableTreeNode formulaNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) formulaNode
				.getUserObject();
		if (sd.isConstraint()) {
			EdConstraint c = sd.getConstraint();
			Vector<Formula> v = new Vector<Formula>(1);
			v.add(c.getBasisConstraint());
			ConstraintPriorityDialog lg = new ConstraintPriorityDialog(this.applFrame, v,
					c.getGraGra().getBasisGraGra().getPriorities());
			lg.showGUI();
			if (!lg.isCancelled()) {
				c.getGraGra().setChanged(true);
				if (this.priority) {
					this.treeModel.constraintNameChanged(c.getGraGra(), false,
							this.priority);
					this.treeModel.nodeChanged(formulaNode);
					this.tree.treeDidChange();
				}
			}
			// System.out.println(c.getBasisConstraint().getName() +"
			// "+c.getBasisConstraint().getPriority());
		}
	}

	public void showRuleAttrConditions(final DefaultMutableTreeNode aNode) {
		// System.out.println("GraGraTreeView.showRuleAttrConditions...");
		DefaultMutableTreeNode node = aNode;
		if (node == null)
			node = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) node.getUserObject();
		if (sd.isRule()) {
			EdRule r = sd.getRule();
			// first delete
			int i = 0;
			while (i < node.getChildCount()) {
				DefaultMutableTreeNode delNode = (DefaultMutableTreeNode) node
						.getChildAt(i);
				GraGraTreeNodeData delData = (GraGraTreeNodeData) delNode
						.getUserObject();
				if (delData.isAttrCondition()) {
					this.treeModel.removeNodeFromParent(delNode);
				} else
					i++;
			}
			// now insert
			CondTuple conds = (CondTuple) r.getBasisRule().getAttrContext()
					.getConditions();
			for (i = 0; i < conds.getSize(); i++) {
				CondMember cond = (CondMember) conds.getMemberAt(i);
				String condStr = cond.getExprAsText();
				// System.out.println("condStr: "+condStr);
				if ((condStr != null) && !condStr.equals("")) {
					GraGraTreeNodeData conddata = new RuleAttrCondTreeNodeData(cond,r);
					conddata.setString(condStr);
					DefaultMutableTreeNode condchild = new DefaultMutableTreeNode(
							conddata);
					conddata.setTreeNode(condchild);
					this.treeModel.insertNodeInto(condchild, node, node
							.getChildCount());
				}
			}
			this.treeModel.nodeChanged(node);
		}
	}

	public void showAtomicAttrConditions(final DefaultMutableTreeNode aNode) {
		// System.out.println("GraGraTreeView.showAtomicAttrConditions...");
		DefaultMutableTreeNode node = aNode;
		if (node == null)
			node = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) node.getUserObject();
		if (sd.isConclusion()) {
			EdAtomic concl = sd.getConclusion();
			// first delete
			int i = 0;
			while (i < node.getChildCount()) {
				DefaultMutableTreeNode delNode = (DefaultMutableTreeNode) node
						.getChildAt(i);
				GraGraTreeNodeData delData = (GraGraTreeNodeData) delNode
						.getUserObject();
				if (delData.isAttrCondition()) {
					this.treeModel.removeNodeFromParent(delNode);
				} else
					i++;
			}
			// now insert
			CondTuple conds = (CondTuple) concl.getBasisAtomic().getAttrContext()
					.getConditions();
			for (i = 0; i < conds.getSize(); i++) {
				CondMember cond = (CondMember) conds.getMemberAt(i);
				String condStr = cond.getExprAsText();
				// System.out.println("condStr: "+condStr);
				if ((condStr != null) && !condStr.equals("")) {
					GraGraTreeNodeData conddata = new ConclusionAttrConditionTreeNodeData(cond, concl);
					conddata.setString(condStr);
					DefaultMutableTreeNode condchild = new DefaultMutableTreeNode(
							conddata);
					conddata.setTreeNode(condchild);
					this.treeModel.insertNodeInto(condchild, node, node
							.getChildCount());
				}
			}
			this.treeModel.nodeChanged(node);
		}
	}

	public Point getPopupMenuLocation() {
		return this.popupLocation;
	}

	/** Set priority of the selected rule. */
	public void setRulePriority() {
		DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		GraGraTreeNodeData sd = (GraGraTreeNodeData) ruleNode.getUserObject();
		if (sd.isRule()) {
			EdRule r = sd.getRule();
			String initPriority = String
					.valueOf(r.getBasisRule().getPriority());
			String priorityStr = new String("-1");
			Integer i = Integer.valueOf(priorityStr);
			while ((i == null) || !(i.intValue() > 0)) {
				priorityStr = JOptionPane
						.showInputDialog(
								this.applFrame,
								"Please set the rule priority > 0.",
								initPriority);
				if (priorityStr == null) {
					i = Integer.valueOf(initPriority);
					break;
				}
				try {
					i = Integer.valueOf(priorityStr);
				} catch (NumberFormatException ex) {
					i = null;
				}
			}
			if (r.getBasisRule().getPriority() != i.intValue()) {
				r.getBasisRule().setPriority(i.intValue());
				r.getGraGra().setChanged(true);
				r.getGraGra().getBasisGraGra().oneRuleHasChangedPriority();
				if (this.priority) {
					this.treeModel.ruleNameChanged(ruleNode, this.layered, this.priority);
					this.treeModel.nodeChanged(ruleNode);
					this.tree.treeDidChange();
					fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.RULE_PRIORITY, this.selPath));
				}
			}
		}
	}

	/** Shows the NAC at the selected path in editor */
	public void showNAC() {
		this.editorPath = this.selPath;
		setCurrentData(this.editorPath);
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SHOW,
				this.editorPath));
	}

	/** Hides the NAC at the selected path in editor */
	public void hideNAC() {
//		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
//				.getLastPathComponent();
//		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.HIDE,
				this.editorPath));
	}

	/** Returns all gragra names of the this.tree */
	public Vector<String> getGraGraNames() {
		return ((GraGraTreeModel) this.tree.getModel()).getGraGraNames();
	}

	/**
	 * Returns an EdGraGra as the used object of the DefaultMutableTreeNode n
	 */
	public EdGraGra getGraGra(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isGraGra())
			return data.getGraGra();
		
		return null;
	}

	/**
	 * Returns an EdGraph as the used object of the DefaultMutableTreeNode n
	 */
	public EdGraph getGraph(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isGraph())
			return data.getGraph();
		
		return null;
	}

	/**
	 * Returns an EdAtomic as the used object of the DefaultMutableTreeNode n
	 */
	public EdAtomic getAtomic(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isAtomic())
			return data.getAtomic();
		
		return null;
	}

	/**
	 * Returns an EdAtomic as the used object of the DefaultMutableTreeNode n
	 */
	public EdAtomic getConclusion(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isConclusion())
			return data.getConclusion();
		
		return null;
	}

	/**
	 * Returns an EdConstraint as the used object of the DefaultMutableTreeNode
	 * n
	 */
	public EdConstraint getConstraint(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isConstraint())
			return data.getConstraint();
		
		return null;
	}

	/**
	 * Returns an EdRule as the used object of the DefaultMutableTreeNode n
	 */
	public EdRule getRule(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isRule())
			return data.getRule();		
		
		return null;
	}

	public EdRule getKernelRule(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isKernelRule())
			return data.getKernelRule();		
		
		return null;
	}
	
	public EdRule getMultiRule(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isMultiRule())
			return data.getMultiRule();		
		
		return null;
	}
	
	/**
	 * Returns an EdRuleScheme as the used object of the DefaultMutableTreeNode n
	 */
	public EdRuleScheme getRuleScheme(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isRuleScheme())
			return data.getRuleScheme();
		
		return null;
	}
	
	/**
	 * Returns a RuleSequence as the used object of the DefaultMutableTreeNode n
	 */
	public RuleSequence getRuleSequence(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isRuleSequence())
			return data.getRuleSequence();
		
		return null;
	}
	
	/**
	 * Returns an EdNAC as the used object of the DefaultMutableTreeNode n
	 */
	public EdNAC getNAC(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isNAC())
			return data.getNAC();
		
		return null;
	}

	/**
	 * Returns an EdPAC as the used object of the DefaultMutableTreeNode n
	 */
	public EdPAC getPAC(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isPAC())
			return data.getPAC();
		
		return null;
	}

	/**
	 * Returns an nested AC as the used object of the DefaultMutableTreeNode n
	 */
	public EdNestedApplCond getNestedAC(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isNestedAC())
			return data.getNestedAC();
		
		return null;
	}
	
	public Pair<EdRule, Vector<String>> getRuleContext(final DefaultMutableTreeNode n) {
		Pair<EdRule, Vector<String>> context = null;
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isAttrCondition()) {
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) n
					.getParent();
			if (parent == null)
				return null;
			GraGraTreeNodeData sd = (GraGraTreeNodeData) parent.getUserObject();
			if (sd.isRule()) {
				Vector<String> attrconds = sd.getRule().getAttrConditions();
				context = new Pair<EdRule, Vector<String>>(sd.getRule(),
						attrconds);
			}
		}
		return context;
	}

	public Pair<EdAtomic, Vector<String>> getConclusionContext(
			final DefaultMutableTreeNode n) {
		Pair<EdAtomic, Vector<String>> context = null;
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isAttrCondition()) {
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) n
					.getParent();
			if (parent == null)
				return null;
			GraGraTreeNodeData sd = (GraGraTreeNodeData) parent.getUserObject();
			if (sd.isConclusion()) {
				Vector<String> attrconds = sd.getConclusion()
						.getAttrConditions();
				context = new Pair<EdAtomic, Vector<String>>(
						sd.getConclusion(), attrconds);
			}
		}
		return context;
	}

	/**
	 * Returns an EdRuleConstraint as the used object of the
	 * DefaultMutableTreeNode n
	 */
	public EdRuleConstraint getRuleConstraint(DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isRuleConstraint())
			return data.getRuleConstraint();
		
		return null;
	}

	/**
	 * Returns an EdAtomApplCond as the used object of the
	 * DefaultMutableTreeNode n
	 */
	public EdAtomApplCond getAtomApplCond(final DefaultMutableTreeNode n) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) n.getUserObject();
		if (data.isAtomApplCond())
			return data.getAtomApplCond();
		
		return null;
	}

	/** Returns a vector with elements of the type agg.gui.EdGraGra */
	public Vector<EdGraGra> getGraGras() {
		Vector<EdGraGra> gragras = new Vector<EdGraGra>();
		DefaultMutableTreeNode aNode;
		GraGraTreeNodeData data;
		for (int i = 0; i < this.top.getChildCount(); i++) {
			aNode = (DefaultMutableTreeNode) this.top.getChildAt(i);
			data = (GraGraTreeNodeData) aNode.getUserObject();
			if (data.isGraGra())
				gragras.addElement(data.getGraGra());
		}
		return gragras;
	}

	void resetAllowEmptyAttrs(boolean b) {
		DefaultMutableTreeNode aNode;
		GraGraTreeNodeData data;
		for (int i = 0; i < this.top.getChildCount(); i++) {
			aNode = (DefaultMutableTreeNode) this.top.getChildAt(i);
			data = (GraGraTreeNodeData) aNode.getUserObject();
			if (data.isGraGra())
				data.getGraGra().getTypeSet().getBasisTypeSet().setAllowEmptyAttr(b);
		}
	}
	
	
	/*
	 * ******************************************************************************
	 * Copy Rule
	 * ******************************************************************************
	 */

	/**
	 * Copies the selected rule node into the same gragra tree. The rule node is
	 * specified by the String "Rule".
	 */
	public void copy(final String menuItemString) {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\nPlease select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}

		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		if (aNode == null)
			return;
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		if (sd == null)
			return;
		if (menuItemString.equals("Rule")) {
			if (sd.isRule())
				copyRule(aNode);
			else
				JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a rule.",
							"", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void copyRule(final DefaultMutableTreeNode node) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = null;
		if (this.selPath == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		graPath = this.selPath.getParentPath();
		
		if (graPath != null) {
			DefaultMutableTreeNode 
			graNode = (DefaultMutableTreeNode) graPath.getLastPathComponent();
			if (graNode.getUserObject() instanceof GrammarTreeNodeData) {
				// copy a simple rule
				GraGraTreeNodeData 
				graData = (GraGraTreeNodeData) graNode.getUserObject();
				if (graData.getGraGra().isEditable()) {
					EdRule ruleClone = graData.getGraGra().cloneRule(data.getRule(), true);
					putRuleIntoTree(ruleClone, graNode, graNode.getIndex(node) + 1);
					this.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.RULE_ADDED, this.selPath));
				}
			}
			// copy an amalgamated rule
			else if (graNode.getUserObject() instanceof RuleSchemeTreeNodeData) {
				TreePath rsPath = graPath;
				DefaultMutableTreeNode rsNode = graNode;
				RuleSchemeTreeNodeData 
				rsData = (RuleSchemeTreeNodeData) rsNode.getUserObject();
				graPath = rsPath.getParentPath();
				graNode = (DefaultMutableTreeNode) graPath.getLastPathComponent();
				GraGraTreeNodeData 
				graData = (GraGraTreeNodeData) graNode.getUserObject();
				if (graData.getGraGra().isEditable()) {
					EdRule ruleClone = graData.getGraGra().cloneAmalgamatedRule(data.getRule(), rsData.getRuleScheme(), true);
					this.tree.collapsePath(rsPath);
					putRuleIntoTree(ruleClone, graNode, graNode.getIndex(rsNode) + 1);
										
					this.fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.RULE_ADDED, this.selPath));
				}
			}
		}
	}

	protected void copyRule() {
		if (this.selPath == null) {
			JOptionPane.showMessageDialog(this.applFrame, 
					"Bad selection.\n Please select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		copyRule(node);	
	}	
	
	private void makeConcurrentRule(boolean disjoint) {
		if (this.selPath == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 			
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		makeConcurrentRule(node, disjoint);
	}	
	
	private void makeConcurrentRule(final DefaultMutableTreeNode node, boolean disjoint) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		TreePath graPath = null;
		if (this.selPath == null) {
			String s = "Bad selection.\n Please select a rule sequence.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		graPath = this.selPath.getParentPath();
		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
						
			EdRule rule1 = data.getRule();
			int rule2indx = graData.getGraGra().getIndexOfRule(rule1);
			int nn = 1;
			if (rule2indx < (graData.getGraGra().getRules().size()-1)) {
				rule2indx++;
				nn++;
			}
			EdRule rule2 = graData.getGraGra().getRule(rule2indx);
			if (rule1.getBasisRule().getRuleScheme() != null 
					|| rule2.getBasisRule().getRuleScheme() != null) {
				JOptionPane.showMessageDialog(this.applFrame, 
						"One of the rules is a Rule Scheme.\n"
						+"Building of a concurrent rule is not available in this case.\n"
						+"Only plain rules will be supported.",					
						"Feature not available",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			EdRule concurrentRule = graData.getGraGra().makeConcurrentRule(rule1, rule2, true, true);
			
			if (concurrentRule != null) {
				this.putRuleIntoTree(concurrentRule, 
						(DefaultMutableTreeNode) node.getParent(),
						((DefaultMutableTreeNode) node.getParent()).getIndex(node) + nn);
								
				if (!concurrentRule.getBasisRule().getErrorMsg().equals("")) {
					String warnMsg = concurrentRule.getBasisRule().getErrorMsg().replaceAll(";", "<br>");
					warnMsg = warnMsg.replaceAll("\n", "<br>");
					JOptionPane.showMessageDialog(this.applFrame, 
							"<html><body>"							
							+"During creating concurrent rule the following change(s) done:<br><br>"
							+"<font color=\"#FF0000\">"
							+warnMsg	
							+"</font>"
							+"<br>(It would be advisable to check the change(s).)"
							+"<br><br>The new concurrent rule:  "
							+concurrentRule.getName()+"<br>"
							+"is added after its original rules into the rule set.<br><br>",						
							"Concurrent Rule:  "+concurrentRule.getName(),
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this.applFrame, 
							"<html><body>"							
							+"Creating concurrent rule was successful.<br><br>"						
							+"The new concurrent rule:  "
							+concurrentRule.getName()+"<br>"
							+"is added after its original rules into the rule set.<br><br>",	
							"Concurrent Rule:  "+concurrentRule.getName(),
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"						
						+"It isn't possible to create concurrent rule based on :<br>    "
						+data.getRule().getName()+".",					
						"Concurrent Rule",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	public void concurrentRuleWarning(EdRule concurrentRule) {
		if (!concurrentRule.getBasisRule().getErrorMsg().equals("")) {
			String warnMsg = concurrentRule.getBasisRule().getErrorMsg().replaceAll(";", "\n");
//			warnMsg = warnMsg.replaceAll("\n", "<br>");
			JOptionPane.showMessageDialog(this.applFrame, 															
					"During creating concurrent rule some warnings arised.\n\n"
					+warnMsg	
					+"\n(It would be advisable to check this rule.)"
					+"\n\nThe new rule: \n"
					+concurrentRule.getName()+"\n"
					+"is added at the end of the rule set.\n\n",						
					"Concurrent Rule:  "+concurrentRule.getName(),
					JOptionPane.INFORMATION_MESSAGE);
			
//			String warnMsg = concurrentRule.getBasisRule().getErrorMsg().replaceAll(";", "<br>");
//			warnMsg = warnMsg.replaceAll("\n", "<br>");
//			JOptionPane.showMessageDialog(this.applFrame, 				
//					"<html><body>"							
//					+"During creating concurrent rule some changes done:<br><br>"
//					+"<font color=\"#FF0000\">"
//					+warnMsg	
//					+"</font>"
//					+"<br>(It would be advisable to check the change(s).)"
//					+"<br><br>The new concurrent rule: <br> "
//					+concurrentRule.getName()+"<br>"
//					+"is added at the end of the rule set.<br><br>",						
//					"Concurrent Rule:  "+concurrentRule.getName(),
//					JOptionPane.INFORMATION_MESSAGE);
		} else {
			if (concurrentRule.getBasisRule().getMatch() != null
					&& !concurrentRule.getBasisRule().getMatch().isEmpty()) {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"							
						+"Creating concurrent rule was successful.<br><br>"						
						+"The new concurrent rule:<br>  "
						+concurrentRule.getName()+"<br>"
						+"is added at the end of the rule set.<br><br>"
						+"The match of this rule is partially set due to object flow.<br><br>",	
						"Concurrent Rule:  "+concurrentRule.getName(),
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this.applFrame, 
						"<html><body>"							
						+"Creating concurrent rule was successful.<br><br>"						
						+"The new concurrent rule: <br> "
						+concurrentRule.getName()+"<br>"
						+"is added at the end of the rule set.<br><br>",	
						"Concurrent Rule:  "+concurrentRule.getName(),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void putRuleIntoTree(final EdRule rule,
								final DefaultMutableTreeNode parentNode, int index) {
		
		GraGraTreeNodeData sd = new RuleTreeNodeData(rule);
		DefaultMutableTreeNode ruleNode = new DefaultMutableTreeNode(sd);
		sd.setTreeNode(ruleNode);
		this.treeModel.insertNodeInto(ruleNode,
									parentNode, index);

		int indx = 0;
		// GACs
		indx = this.addGACsToRuleTreeNode(rule, ruleNode, indx);		
		// NACs
		indx = indx+rule.getNestedACs().size();
		this.addNACsToRuleTreeNode(rule, ruleNode, indx);
		// PACs
		indx = indx+rule.getNACs().size();
		this.addPACsToRuleTreeNode(rule, ruleNode, indx);
		// attribute conditions
		addAttrCondToRuleTreeNode(rule, ruleNode);
		 
		this.treeModel.ruleNameChanged(ruleNode, this.layered);
		this.tree.treeDidChange();
		
		this.fireTreeViewEvent(new TreeViewEvent(this,
				TreeViewEvent.RULE_ADDED, this.selPath));
	}
	
	/*
	 * ******************************************************************************
	 * Disable Rule
	 * ******************************************************************************
	 */

	/**
	 * Disable the selected rule node. The disabled rule is not used by graph
	 * transformation. The specified parameter key should get the value "Rule".
	 */
	public void disable(final String key, final boolean disable) {
		if (this.selPath == null)
			return;

		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		if (aNode == null)
			return;

		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		if (sd == null)
			return;

		if (key.equals("Rule")) {
			if (sd.isRule()) {
				if (disable) {
					if (this.layered && !sd.isMultiRule())
						sd.setString("[D]", "["
								+ sd.getRule().getBasisRule().getLayer() + "]",
								sd.getRule().getBasisRule().getName());
					else if (this.priority && !sd.isMultiRule())
						sd.setString("[D]", "["
								+ sd.getRule().getBasisRule().getPriority()
								+ "]", sd.getRule().getBasisRule().getName());
					else
						sd.setString("[D]", sd.getRule().getBasisRule()
								.getName());
				} else {
					if (this.layered && !sd.isMultiRule())
						sd.setString("", "["
								+ sd.getRule().getBasisRule().getLayer() + "]",
								sd.getRule().getBasisRule().getName());
					else if (this.priority && !sd.isMultiRule())
						sd.setString("", "["
								+ sd.getRule().getBasisRule().getPriority()
								+ "]", sd.getRule().getBasisRule().getName());
					else
						sd.setString("", sd.getRule().getBasisRule().getName());
				}
				sd.getRule().getBasisRule().setEnabled(!disable);
				sd.getRule().getGraGra().getBasisGraGra().oneRuleHasChangedEvailability();
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		} else if (key.equals("RuleScheme")) {
			if (sd.isRuleScheme()) {
				if (disable) {
					if (this.layered)
						sd.setString("[D]", "["
								+ sd.getRuleScheme().getBasisRule().getLayer() + "]",
								sd.getRuleScheme().getBasisRule().getName());
					else if (this.priority)
						sd.setString("[D]", "["
								+ sd.getRuleScheme().getBasisRule().getPriority()
								+ "]", sd.getRuleScheme().getBasisRule().getName());
					else
						sd.setString("[D]", sd.getRuleScheme().getBasisRule()
								.getName());
				} else {
					if (this.layered)
						sd.setString("", "["
								+ sd.getRuleScheme().getBasisRule().getLayer() + "]",
								sd.getRuleScheme().getBasisRule().getName());
					else if (this.priority)
						sd.setString("", "["
								+ sd.getRuleScheme().getBasisRule().getPriority()
								+ "]", sd.getRuleScheme().getBasisRule().getName());
					else
						sd.setString("", sd.getRuleScheme().getBasisRule().getName());
				}
				sd.getRuleScheme().getBasisRule().setEnabled(!disable);
				sd.getRuleScheme().getGraGra().getBasisGraGra().oneRuleHasChangedEvailability();
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		}
		else if (key.equals("NAC")) {
			if (sd.isNAC()) {
				if (disable) {
					sd.setString("[D]", sd.getNAC().getMorphism().getName());
				} else {
					sd.setString("", sd.getNAC().getMorphism().getName());
				}
				sd.getNAC().getMorphism().setEnabled(!disable);
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		} else if (key.equals("PAC")) {
			if (sd.isPAC()) {
				if (disable) {
					sd.setString("[D]", sd.getPAC().getMorphism().getName());
				} else {
					sd.setString("", sd.getPAC().getMorphism().getName());
				}
				sd.getPAC().getMorphism().setEnabled(!disable);
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		} else if (key.equals("NestedAC")) {
			if (sd.isNestedAC()) {
				if (disable) {
					sd.setString("[D]", sd.getNestedAC().getMorphism().getName());
				} else {
					sd.setString("", sd.getNestedAC().getMorphism().getName());
				}
				sd.getNestedAC().getMorphism().setEnabled(!disable);
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		}
		else if (key.equals("AttrCondition")) {
			if (sd.isAttrCondition()) {
				String condstr = sd.getAttrCondition().first.getExprAsText();
				if (disable) {
					sd.setString("[D]", condstr);
				} else {
					sd.setString("", condstr);
				}
				sd.getAttrCondition().first.setEnabled(!disable);
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		} else if (key.equals("Constraint")) {
			if (sd.isConstraint()) {
				if (disable) {
					if (this.layered)
						sd.setString("[D]", "["
								+ sd.getConstraint().getBasisConstraint()
										.getLayerAsString() + "]", sd
								.getConstraint().getName());
					else if (this.priority)
						sd.setString("[D]", "["
								+ sd.getConstraint().getBasisConstraint()
										.getPriorityAsString() + "]", sd
								.getConstraint().getName());
					else
						sd.setString("[D]", sd.getConstraint().getName());
				} else {
					if (this.layered)
						sd.setString("", "["
								+ sd.getConstraint().getBasisConstraint()
										.getLayerAsString() + "]", sd
								.getConstraint().getName());
					else if (this.priority)
						sd.setString("", "["
								+ sd.getConstraint().getBasisConstraint()
										.getPriorityAsString() + "]", sd
								.getConstraint().getName());
					else
						sd.setString("", sd.getConstraint().getName());
				}
				sd.getConstraint().getBasisConstraint().setEnabled(!disable);
				this.treeModel.nodeChanged(aNode);
				this.tree.treeDidChange();
			}
		}
	}

	/** **** textual comments ************ */

	public void cancelCommentsEdit() {
		if (this.comments != null) {
			this.comments.cancel();
			this.comments = null;
		}
	}
	
	private void editTextualComments(final String command) {
		if (this.selPath == null || command.equals("")) {
			return;
		}
		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) this.selPath
				.getLastPathComponent();
		if (aNode == null)
			return;
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		if (sd == null)
			return;

		cancelCommentsEdit();

		if (command.equals("commentGraph")) {
			if (sd.isGraph() && !sd.getGraph().isTypeGraph())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getGraph().getBasisGraph());
		} else if (command.equals("commentRule")) {
			if (sd.isRule())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getRule().getBasisRule());
		} else if (command.equals("commentNAC")) {
			if (sd.isNAC())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getNAC().getMorphism());
		} else if (command.equals("commentPAC")) {
			if (sd.isPAC())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getPAC().getMorphism());
		} else if (command.equals("commentNestedAC")) {
			if (sd.isNestedAC())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getNestedAC().getMorphism());
		} else if (command.equals("commentAtomConstraint")) {
			if (sd.isAtomic())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getAtomic().getBasisAtomic());
		} else if (command.equals("commentConstraint")) {
			if (sd.isConstraint())
				this.comments = new GraGraTextualComment(this.applFrame, this.popupLocation.x,
						this.popupLocation.y, sd.getConstraint()
								.getBasisConstraint());
		}
		if (this.comments != null)
			this.comments.setVisible(true);
	}

	/*
	 * ******************************************************************************
	 * Delete GraGra , Rule, NAC
	 * ******************************************************************************
	 */

	public void deleteCurrentGraGra(final EdGraGra gra) {
		TreePath path = this.getTreePathOfGrammarElement(gra);
		if (path != null) {	
			if (path != this.selPath) {
				this.tree.setSelectionPath(path);
				this.selPath = path;
				this.editorPath = this.selPath;
				fireTreeViewEvent(new TreeViewEvent(
							this, TreeViewEvent.SELECTED,
							this.editorPath));
			}
			DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) path.getLastPathComponent();
			this.deleteGraGra(treenode, path, true);	
		}
	}
	
	public boolean hasMultipleSelection() {
		return (this.tree.getSelectionPaths() != null && this.tree.getSelectionPaths().length > 1);
	}
	
	private void deleteMultipleSelElems() {
		int row = this.tree.getSelectionModel().getMinSelectionRow();
		TreePath[] elems = this.tree.getSelectionPaths();
		for (int i=0; i<elems.length; i++) {
			TreePath p = elems[i];
			deleteTreeNode((DefaultMutableTreeNode) p.getLastPathComponent(), p, false);
		}
		row--;
		this.setEditPath(row);					
		this.setFlagForNew();
		this.fireTreeViewEvent(new TreeViewEvent(this,
				TreeViewEvent.SELECTED, this.getEditorPath()));
			
		if ((((GraGraTreeNodeData)((DefaultMutableTreeNode)selPath
					.getLastPathComponent()).getUserObject()).isGraph())) {
			if (!this.getCurrentGraGra().getRules().isEmpty()) {						
				row++;
				this.setEditPath(row);					
				this.setFlagForNew();
				this.fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.SELECTED, this.getEditorPath()));
			}
		}
	}
	
	/**
	 * Deletes the selected node from the gragra tree. The node can be a gragra |
	 * rule | NAC and is specified by the String "GraGra" | "Rule" | "NAC".
	 */
	public void delete(final String treeItemString) {
		if (this.selPath == null) {
			if (this.currentGraGra != null)
				JOptionPane.showMessageDialog(this.applFrame,
						"Bad selection for deletion.",
						"", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (this.hasMultipleSelection()) {
			Object[] options = { "Delete", "Cancel" };
			int op = JOptionPane.showOptionDialog(this.applFrame,
					"Are you sure you want to delete all selected grammar elements?",
					"Multiple Delete", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			if (op == 0) {
				deleteMultipleSelElems();
			}
			return;
		}
		
		if (!treeItemString.equals("GraGra")
				&& (this.currentGraGra != null
						&& !this.currentGraGra.getGraph().isEditable())) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Cannot delete this grammar. It is locked.",
					"Delete", JOptionPane.ERROR_MESSAGE);
			return;
		}

		DefaultMutableTreeNode 
		aNode = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		if (aNode == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Cannot delete. Data is not defined.",
					"", JOptionPane.ERROR_MESSAGE);
			return;
		}
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		if (sd == null) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Cannot delete. Tree node data is not defined.",
					"", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!treeItemString.equals("")) {
			if (treeItemString.equals("GraGra")) {
				if (sd.isGraGra()) {
					deleteGraGra(aNode, this.selPath, true);
					resetEnabledOfFileMenuItems("deleteGraGra");
					this.filePopupMenu.resetEnabledOfFileMenuItems("deleteGraGra");
					resetEnabledOfToolBarItems("deleteGraGra");
					if (this.tree.getRowCount() == 1)
						fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.EMPTY));
				}
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a grammar.",
							"", JOptionPane.WARNING_MESSAGE);
			}
			else if (treeItemString.equals("TypeGraph")) {
				if (sd.isTypeGraph())
					deleteTypeGraph(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a type graph.",
							"", JOptionPane.WARNING_MESSAGE);
			}
			else if (treeItemString.equals("Graph")) {
				if (sd.isGraph() && !sd.isTypeGraph())
					deleteGraph(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a graph.",
							"", JOptionPane.WARNING_MESSAGE);
			} 
			else if (treeItemString.equals("Rule")) {
				if (sd.isRule()) {
//					if (sd.isKernelRule()) {						
//					} else 
					if (sd.isMultiRule()) {
						deleteMultiRule(aNode, true);
					} else if (sd.isAmalgamatedRule()) {
						deleteAmalgamatedRule(aNode, false);
					} else {
						deleteRule(aNode, this.selPath, true);
					}
				}
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a rule.",
							"", JOptionPane.WARNING_MESSAGE);
			} 
			else if (treeItemString.equals("NAC")) {
				if (sd.isNAC())
					deleteNAC(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a NAC.",
							"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("PAC")) {
				if (sd.isPAC())
					deletePAC(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a PAC.",
							"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("NestedAC")) {
				if (sd.isNestedAC())
					deleteNestedAC(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a (Nested) Application Condition.",
							"", JOptionPane.WARNING_MESSAGE);
			} 
			else if (treeItemString.equals("Atomic")) {
				if (sd.isAtomic())
					deleteAtomic(aNode, this.selPath, true);
				else
					JOptionPane
							.showMessageDialog(this.applFrame,
									"Bad selection.\nPlase select an atomic graph constraint.",
									"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("Conclusion")) {
				if (sd.isConclusion())
					deleteConclusion(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection,\nPlease select a conclusion.",
							"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("Constraint")) {
				if (sd.isConstraint())
					deleteConstraint(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a constraint.",
							"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("RuleConstraint")) {
				if (sd.isRuleConstraint())
					deleteRuleConstraint(aNode, this.selPath, true);
				else
					JOptionPane
							.showMessageDialog(this.applFrame,
									"Bad selection.\nPlease select a rule post application constraint.",
									"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("AtomApplCond")) {
				if (sd.isAtomApplCond())
					deleteAtomApplCond(aNode, this.selPath, true);
				else
					JOptionPane
							.showMessageDialog(this.applFrame,
									"Bad selection.\nPlease select a rule atomic post application condition.",
									"", JOptionPane.WARNING_MESSAGE);
			} else if (treeItemString.equals("RuleConstraints")) {
				if (sd.isRule())
					deleteRuleConstraints(aNode, this.selPath, true);
				else
					JOptionPane.showMessageDialog(this.applFrame,
							"Bad selection.\nPlease select a rule.",
							"", JOptionPane.WARNING_MESSAGE);
				
			} 
			else
				JOptionPane.showMessageDialog(this.applFrame,
						"Bad selection for deletion.",
						"", JOptionPane.WARNING_MESSAGE);
		} else {
			deleteTreeNode(aNode, this.selPath, false);
		}
	}

	protected void deleteTreeNode(DefaultMutableTreeNode aNode, TreePath path, boolean withWarning) {
		GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
		if (sd != null) {
			if (sd.isGraGra())
				deleteGraGra(aNode, path, withWarning);
			else if (sd.isTypeGraph())
				deleteTypeGraph(aNode, path, withWarning);
			else if (sd.isGraph() && !sd.isTypeGraph())
				deleteGraph(aNode, path, withWarning);
			else if (sd.isRuleScheme())
				(new GrammarTreeNode()).deleteRuleScheme(this, aNode, path, withWarning);
			else if (sd.isRule())
				deleteRule(aNode, path, withWarning);
			else if (sd.isNAC())
				deleteNAC(aNode, path, withWarning);
			else if (sd.isPAC())
				deletePAC(aNode, path, withWarning);
			else if (sd.isNestedAC())
				deleteNestedAC(aNode, path, withWarning);
			else if (sd.isAtomic())
				deleteAtomic(aNode, path, withWarning);
			else if (sd.isConclusion())
				deleteConclusion(aNode, path, withWarning);
			else if (sd.isConstraint())
				deleteConstraint(aNode, path, withWarning);
			else if (sd.isRuleSequence())
				deleteRuleSequence(aNode, path, withWarning);
			else if (sd.isRuleConstraint())
				deleteRuleConstraint(aNode, path, withWarning);
			else if (sd.isAtomApplCond())
				deleteAtomApplCond(aNode, path, withWarning);
			else
				JOptionPane.showMessageDialog(this.applFrame,
						"Bad selection for deletion.",
						"", JOptionPane.WARNING_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection for deletion.",
					"", JOptionPane.WARNING_MESSAGE);
	}
	
	private int cpaGraGraWarning(final String action) {
		Object[] options = { action, "CANCEL" };
		String act = action.toLowerCase();		
		int answer = JOptionPane.showOptionDialog(this.applFrame,
				"This grammar is currently used by CPA."
				+"\nAlready computed critical pairs will be lost."
				+"\nDo you want to " + act
						+ " the grammar really?", "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}
	
	private void deleteGraGra(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		GraGraTreeNodeData sd = (GraGraTreeNodeData) delNode.getUserObject();
		if (sd.getGraGra() != this.currentGraGra) {
			if (sd.getGraGra().isChanged()
					&& (changedGraGraWarning("CLOSE") != JOptionPane.YES_OPTION)) {
				return;				
			}
			if (((AGGAppl) this.applFrame).getCPA().getGraGra() == sd.getGraGra()
					&& !((AGGAppl) this.applFrame).getCPA().isEmpty()
					&& withWarning
					&& cpaGraGraWarning("CLOSE") != JOptionPane.YES_OPTION) {
				return;	
			}
			
//			int answer = withWarning? this.removeWarning("GraGra"): 0;
//			if (answer == JOptionPane.YES_OPTION) 
			{
				this.gragraStore.removeGraGra(sd.getGraGra());
				this.treeModel.removeNodeFromParent(delNode);
				
				fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.DELETED, path));
				
				EdGraGra gra = sd.getGraGra();			
				// remove gragra subtree
				deleteGraGraSubtree(delNode);			
				gra.dispose();
				path = null;
			}
		} 
		else if (!((AGGAppl) this.applFrame).getGraGraEditor().isTransformationRunning()) {
			if (this.currentGraGra.isChanged()
					&& withWarning
					&& (changedGraGraWarning("CLOSE") != JOptionPane.YES_OPTION)) {
				return;			
			}
			if (((AGGAppl) this.applFrame).getCPA().getGraGra() == this.currentGraGra) {
				if (((AGGAppl) this.applFrame).getCPA().getGraGra() == sd.getGraGra()
						&& !((AGGAppl) this.applFrame).getCPA().isEmpty()
						&& withWarning
						&& cpaGraGraWarning("CLOSE") != JOptionPane.YES_OPTION) {
					return;	
				}
			}
			
			this.gragraStore.removeGraGra(this.currentGraGra);
			this.treeModel.removeNodeFromParent(delNode);
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.DELETED, path));
			
			// remove gragra subtree
			deleteGraGraSubtree(delNode);									
			this.currentGraGra.dispose();
			
			this.selPath = null;
			this.currentGraGra = null;
			this.currentGraph = null;
			this.currentRule = null;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
			this.currentAtomic = null;
			this.currentConstraint = null;
			this.currentRuleConstraint = null;
			this.currentAtomApplCond = null;
		} else if (withWarning){
			JOptionPane.showMessageDialog(this.applFrame, 
					"Please complete the graph transformation before to close the grammar.", 
					"", 
					JOptionPane.ERROR_MESSAGE);
		}		
		System.gc();
	}

	private void deleteGraGraSubtree(final DefaultMutableTreeNode delNode) {
		((GraGraTreeNodeData) delNode.getUserObject()).dispose();
		delNode.setUserObject(null);
		for (int i=0; i<delNode.getChildCount(); i++) {
			DefaultMutableTreeNode ch = (DefaultMutableTreeNode) delNode.getChildAt(i);
			GraGraTreeNodeData sdch = (GraGraTreeNodeData) ch.getUserObject();
			ch.setUserObject(null);
			sdch.dispose();
		}
		delNode.removeAllChildren();
	}
	
	public void deleteGraph(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		this.selPath = (new GrammarTreeNode()).deleteGraph(this, delNode, path, withWarning);
	}

	public void setEditPath(final int aRow) {
		int row = aRow;
		if (row < 0)
			row = 0;
		// else if(this.tree.getPathForRow(this.tree.getMinSelectionRow()) == null) row--;
		this.tree.setSelectionRow(row);
		this.tree.treeDidChange();
		this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
		if (this.selPath != null) {
			this.editorPath = this.selPath;
			setCurrentData(this.editorPath);
		}
	}

	public void deleteTypeGraph(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		this.selPath = (new GrammarTreeNode()).deleteTypeGraph(this, delNode, path, withWarning);
	}

	private void deleteRule(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		this.selPath = (new GrammarTreeNode()).deleteRule(this, delNode, path, withWarning);
	}

	private void deleteMultiRule(final DefaultMutableTreeNode delNode, boolean withWarning) {
		this.selPath = (new RuleSchemeTreeNode()).deleteMultiRule(this, delNode, this.selPath, withWarning);
	}
	
	private void deleteAmalgamatedRule(final DefaultMutableTreeNode delNode, boolean withWarning) {
		this.selPath = (new RuleSchemeTreeNode()).deleteAmalgamatedRule(this, delNode, this.selPath, withWarning);
	}
	
	public void deleteRuleSequence(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		this.selPath = (new GrammarTreeNode()).deleteRuleSequence(this, delNode, path, withWarning);
	}
	
	private void deleteAtomic(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select an Atomic Graph Constraint.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath graPath = path.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			if (data.getAtomic() == this.currentAtomic
					&& path == this.tree.getSelectionPath()) {
				int row = this.tree.getRowForPath(path);
				int answer = withWarning? removeCurrentObjectWarning("Atomic"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					handleRuleConstraints(graNode, false, this.currentAtomic);
					this.treeModel.removeNodeFromParent(delNode);
					if (graData.isGraGra()) {
						this.gragraStore.storeAtomConstraint(graData.getGraGra(),
								data.getAtomic());
						graData.getGraGra().removeAtomic(data.getAtomic());
					}
					row--;
					setEditPath(row);
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			} else {
				int answer = withWarning? removeWarning("Atomic"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					int row = this.tree.getRowForPath(path);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					handleRuleConstraints(graNode, false, this.currentAtomic);
					this.treeModel.removeNodeFromParent(delNode);
					if (graData.isGraGra()) {
						this.gragraStore.storeAtomConstraint(graData.getGraGra(), data
								.getAtomic());
						graData.getGraGra().removeAtomic(data.getAtomic());
					}
					row--;
					setEditPath(row);
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			}
		}
	}

	private void deleteConclusion(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a conclusion.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath atomicPath = this.selPath.getParentPath();		
		if (atomicPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) atomicPath
					.getParentPath().getLastPathComponent();
			DefaultMutableTreeNode atomicNode = (DefaultMutableTreeNode) atomicPath
					.getLastPathComponent();
			GraGraTreeNodeData atomicData = (GraGraTreeNodeData) atomicNode
					.getUserObject();
			if (data.getConclusion().getParent().getConclusions().size() > 1) {
				if (withWarning && data.getConclusion().isParent()) {
					JOptionPane.showMessageDialog(this.applFrame,
					"Sorry, cannot delete the first conclusion.",
					"", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (data.getConclusion() == this.currentConclusion
						&& path == this.tree.getSelectionPath()) {
					int row = this.tree.getRowForPath(path);
					int answer = withWarning? removeCurrentObjectWarning("Conclusion"): 0;
					if (answer == JOptionPane.YES_OPTION) {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.DELETED, path));
						handleRuleConstraints(graNode, false, atomicData
								.getAtomic());
						this.treeModel.removeNodeFromParent(delNode);
						this.gragraStore.storeAtomConclusion(data.getConclusion()
								.getParent(), data.getConclusion());
						atomicData.getAtomic().removeConclusion(
								data.getConclusion());
						row--;
						setEditPath(row);
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.SELECTED, this.editorPath));
					}
				} else {
					int answer = withWarning? removeWarning("Conclusion"): 0;
					if (answer == JOptionPane.YES_OPTION) {
						int row = this.tree.getRowForPath(path);
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.DELETED, path));
						handleRuleConstraints(graNode, false, atomicData
								.getAtomic());
						this.treeModel.removeNodeFromParent(delNode);
						this.gragraStore.storeAtomConclusion(data.getConclusion()
								.getParent(), data.getConclusion());
						atomicData.getAtomic().removeConclusion(data.getConclusion());
						row--;
						setEditPath(row);
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.SELECTED, this.editorPath));
					}
				}
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"Cannot delete. At least one conclusion should exist.",
						"", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	private void deleteConstraint(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a consistency constraint.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath graPath = this.selPath.getParentPath();		
		if (graPath != null) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) graPath
					.getLastPathComponent();
			GraGraTreeNodeData graData = (GraGraTreeNodeData) graNode
					.getUserObject();
			if (data.getConstraint() != this.currentConstraint) {
				int answer = withWarning? removeWarning("Constraint"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					int row = this.tree.getRowForPath(path);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					handleRuleConstraints(graNode, false, data.getConstraint());
					this.treeModel.removeNodeFromParent(delNode);
					this.gragraStore.storeConstraint(graData.getGraGra(), data
							.getConstraint());
					graData.getGraGra().removeConstraint(data.getConstraint());
					row--;
					setEditPath(row);
	//				setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			} else {
				int row = this.tree.getRowForPath(path);
				int answer = withWarning? removeCurrentObjectWarning("Constraint"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					handleRuleConstraints(graNode, false, data.getConstraint());
					this.treeModel.removeNodeFromParent(delNode);
					this.gragraStore.storeConstraint(graData.getGraGra(), data
							.getConstraint());
					graData.getGraGra().removeConstraint(data.getConstraint());
					row--;
					setEditPath(row);
//					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			}
		}
	}

	public void deleteRuleConstraint(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (this.selPath == null) {
			String s = "Bad selection.\n Please select a rule constraint.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath rulePath = path.getParentPath();		
		if (rulePath != null) {
			if (!data.isRuleConstraint()) {
				String s = "Bad selection.\n Please select a rule constraint.";
				JOptionPane.showMessageDialog(this.applFrame, s,
						"", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (data.getRuleConstraint() != null) {
				EdRuleConstraint ruleConstr = data.getRuleConstraint();
				DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) rulePath
						.getLastPathComponent();
				GraGraTreeNodeData ruleData = (GraGraTreeNodeData) ruleNode
						.getUserObject();
				if (ruleConstr != this.currentRuleConstraint) {
					int row = this.tree.getRowForPath(path);
					this.treeModel.removeNodeFromParent(delNode);
					ruleData.getRule().getBasisRule().removeConstraint(
							ruleConstr.getConstraint());
					ruleData.getRule().getGraGra().setChanged(true);
					row--;
					setEditPath(row);
					setFlagForNew();
				} else {
					int row = this.tree.getRowForPath(path);
					this.treeModel.removeNodeFromParent(delNode);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					ruleData.getRule().getBasisRule().removeConstraint(
							ruleConstr.getConstraint());
					ruleData.getRule().getGraGra().setChanged(true);
					row--;
					setEditPath(row);
					setFlagForNew();					
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			}
		}
	}

	public void deleteAtomApplCond(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select an atomic application condition.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		if (!data.isAtomApplCond()) {
			String s = "Bad selection.\n Please select an atomic application condition.";
			JOptionPane.showMessageDialog(this.applFrame, s,
						"", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (data.getAtomApplCond() != null) {
			EdAtomApplCond aac = data.getAtomApplCond();
			TreeNode parent = delNode.getParent();
			if (parent.getChildCount() == 1)
				return;
			TreePath rulePath = path.getParentPath().getParentPath();
			DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) rulePath
					.getLastPathComponent();
			GraGraTreeNodeData ruleData = (GraGraTreeNodeData) ruleNode
					.getUserObject();
			if (aac != this.currentAtomApplCond) {
				int row = this.tree.getRowForPath(path);
				this.treeModel.removeNodeFromParent(delNode);
				ruleData.getRule().getBasisRule().removeAtomApplCond(
						aac.getAtomApplCond());
				row--;
				setEditPath(row);
			} else {
				// int answer = removeWarning("Atomic Application
				// Condition");
				// if (answer == JOptionPane.YES_OPTION)
				{
					int row = this.tree.getRowForPath(path);
					this.treeModel.removeNodeFromParent(delNode);
					fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.DELETED, path));
					ruleData.getRule().getBasisRule().removeAtomApplCond(
								aac.getAtomApplCond());
					this.currentAtomApplCond = null;
					row--;
					setEditPath(row);
				}
			}
		}
	}

	public void deleteRuleConstraints(final DefaultMutableTreeNode ruleNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a rule.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		TreePath rulePath = path.getParentPath();		
		if (rulePath != null) {
			GraGraTreeNodeData ruleData = (GraGraTreeNodeData) ruleNode
					.getUserObject();
			if (!ruleData.isRule()) {
				String s = "Bad selection.\n Please select a rule.";
				JOptionPane.showMessageDialog(this.applFrame, s,
						"", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (ruleData.getRule().getBasisRule().getAtomApplConds().size() != 0) {
				// System.out.println(ruleData.getRule().getBasisRule().getAtomApplConds().size()+"
				// ApplConstraints to delete ");
				int i = 0;
				while (i < ruleNode.getChildCount()) {
					DefaultMutableTreeNode delNode = (DefaultMutableTreeNode) ruleNode
							.getChildAt(i);
					GraGraTreeNodeData delData = (GraGraTreeNodeData) delNode
							.getUserObject();
					if (delData.isRuleConstraint())
						this.treeModel.removeNodeFromParent(delNode);
					else
						i++;
				}
				ruleData.getRule().getBasisRule().removeApplConditions();
				ruleData.getRule().getGraGra().setChanged(true);
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;

				int row = this.tree.getRowForPath(path);
				setEditPath(row);
				setFlagForNew();
				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.SELECTED, this.editorPath));
			}
		}
	}

	private void deleteNAC(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a NAC.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath rulePath = path.getParentPath();		
		if (rulePath != null) {
			DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) rulePath
					.getLastPathComponent();
			GraGraTreeNodeData ruleData = (GraGraTreeNodeData) ruleNode
					.getUserObject();
			if (data.getNAC() != this.currentNAC) {
				int answer = withWarning? removeWarning("NAC"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					this.treeModel.removeNodeFromParent(delNode);
					EdNAC nac = data.getNAC();
					this.gragraStore.storeNAC(nac.getRule(), nac);
					ruleData.getRule().removeNAC(nac);
					// ruleData.getRule().destroyNAC(nac);
					path = this.tree.getSelectionPath();
					this.tree.setSelectionPath(path);
					setEditPath(this.tree.getRowForPath(path));
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			} else {
				int row = this.tree.getRowForPath(path);
				int answer = withWarning? removeCurrentObjectWarning("NAC"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					this.treeModel.removeNodeFromParent(delNode);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					EdNAC nac = data.getNAC();
					this.gragraStore.storeNAC(nac.getRule(), nac);
					ruleData.getRule().removeNAC(nac);					
					// ruleData.getRule().destroyNAC(nac);
					row--;
					setEditPath(row);					
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			}
		}
	}

	private void deletePAC(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a PAC.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath rulePath = path.getParentPath();		
		if (rulePath != null) {
			DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) rulePath
					.getLastPathComponent();
			GraGraTreeNodeData ruleData = (GraGraTreeNodeData) ruleNode
					.getUserObject();
			if (data.getPAC() != this.currentPAC) {		
				int answer = withWarning? removeWarning("PAC"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					this.treeModel.removeNodeFromParent(delNode);
					EdPAC pac = data.getPAC();
					this.gragraStore.storePAC(pac.getRule(), pac);
					ruleData.getRule().removePAC(pac);
					// ruleData.getRule().destroyPAC(pac);
					path = this.tree.getSelectionPath();
					setEditPath(this.tree.getRowForPath(path));
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			} else {
				int row = this.tree.getRowForPath(path);
				int answer = withWarning? removeCurrentObjectWarning("PAC"): 0;
				if (answer == JOptionPane.YES_OPTION) {
					this.treeModel.removeNodeFromParent(delNode);
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.DELETED, path));
					EdPAC pac = data.getPAC();
					this.gragraStore.storePAC(pac.getRule(), pac);
					ruleData.getRule().removePAC(pac);
					// ruleData.getRule().destroyPAC(pac);
					row--;
					setEditPath(row);
					setFlagForNew();
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.SELECTED, this.editorPath));
				}
			}
		}
	}

	private void deleteNestedAC(final DefaultMutableTreeNode delNode, TreePath path, boolean withWarning) {
		if (path == null) {
			String s = "Bad selection.\n Please select a (Nested) Application Condition.";
			JOptionPane.showMessageDialog(this.applFrame, s,
					"", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		GraGraTreeNodeData data = (GraGraTreeNodeData) delNode.getUserObject();
		TreePath parentPath = this.selPath.getParentPath();
		
		if (parentPath != null) {
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentPath
					.getLastPathComponent();
			GraGraTreeNodeData parentData = (GraGraTreeNodeData) parentNode
					.getUserObject();
			
//			int row = -1;
			boolean candelete = false;
			if (data.getNestedAC() == this.currentNestedAC) {
				int answer = withWarning? removeCurrentObjectWarning("(Nested) Application Condition"): 0;
				if (answer == JOptionPane.YES_OPTION) {
//					row = this.tree.getRowForPath(path);
					if (delNode.getParent() != null) {
						this.treeModel.removeNodeFromParent(delNode);
						candelete = true;
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.DELETED, this.selPath));
					}
				} 
					
			} 
			if (candelete) {				
				EdNestedApplCond ac = data.getNestedAC();
				
				if (parentData.isRule()) 
					this.gragraStore.storeNestedAC(ac.getRule(), ac);
				else if (parentData.isNestedAC())
					this.gragraStore.storeNestedAC(ac.getParent(), ac);
				
				GraGraTreeNodeData firstChildData = null;
				if (parentData.getTreeNode().getChildCount() > 0)
					firstChildData = (GraGraTreeNodeData)((DefaultMutableTreeNode)parentData.
											getTreeNode().getFirstChild()).getUserObject();						
						
				if (parentData.isRule()) 
					parentData.getRule().removeNestedAC(ac);
				else if (parentData.isNestedAC())
					parentData.getNestedAC().removeNestedAC(ac);
						
				if (firstChildData != null && firstChildData.isApplFormula()) 
					this.treeModel.removeNodeFromParent(firstChildData.getTreeNode());
						
				if (parentData.isRule()) {
					parentData.getRule().getBasisRule().setFormula("true");
				}
				else if (parentData.isNestedAC()) {
					parentData.getNestedAC().getNestedMorphism().setFormula("true");
				}
						
				this.tree.treeDidChange();						
				// update this.tree path of NestedApplConds of rule
				this.selectPath(parentPath);
			}
		}
	}
	
	/**
	 * Either inserts or deletes the constraints for the given rule R from the
	 * tree view.
	 */
	public void removeRuleConstraints(DefaultMutableTreeNode rnode,
			Object objToCheck) {
		GraGraTreeNodeData sd = (GraGraTreeNodeData) rnode.getUserObject();
		if ( !sd.isRule()
				|| (sd.getRule().getBasisRule().getAtomApplConds().size() == 0) ) {
			return;
		}
		
		if (objToCheck != null) {
			if (objToCheck instanceof EdAtomic) {
				EdAtomic parAtom = ((EdAtomic) objToCheck).getParent();
				if (parAtom != null) {
					// Conclusion
					if (!sd.getRule().getBasisRule().getUsedAtomics().contains(
							parAtom.getBasisAtomic()))
						return;
				} else {
					AtomConstraint atomic = ((EdAtomic) objToCheck)
							.getBasisAtomic();
					// System.out.println(sd.getRule().getBasisRule().getUsedAtomics());
					if (!sd.getRule().getBasisRule().getUsedAtomics().contains(
							atomic)) {
						return;
					}
				}
			} else if (objToCheck instanceof EdConstraint) {
				boolean used = false;
				Vector<Formula> formulas = sd.getRule().getBasisRule().getUsedFormulas();
				for (int i = 0; i < formulas.size(); i++) {
					Formula f = formulas.get(i);
					// System.out.println(f +"
					// "+((EdConstraint)objToCheck).getBasisConstraint());
					if (f.compareTo(((EdConstraint) objToCheck)
							.getBasisConstraint())) {
						used = true;
						break;
					}
				}
				if (!used) {
					return;
				}
			}
		}

		TreePath path = new TreePath(rnode.getPath());
		this.tree.collapsePath(path);
		while (true) {
			Enumeration<?> en = rnode.children();
			boolean again = false;
			while (en.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) en
						.nextElement();
				GraGraTreeNodeData cdata = (GraGraTreeNodeData) child
						.getUserObject();
				if (cdata.isRuleConstraint()) {
					again = true;
					child.removeAllChildren();
					this.treeModel.removeNodeFromParent(child);
					break;
				}
			}
			if (!again)
				break;
		}
		this.tree.expandPath(path);
	}

	public void addRuleConstraints(final DefaultMutableTreeNode rnode,
			Object objToCheck) {
		// System.out.println("GraGraTreeView.addRuleConstraints ");
		GraGraTreeNodeData sd = (GraGraTreeNodeData) rnode.getUserObject();
		if (!sd.isRule())
			return;
		EdRule er = sd.getRule();
		if (er.getGraGra().isEditable()) {
			TreePath path = new TreePath(rnode.getPath());
			if (sd.getRule().getBasisRule().getAtomApplConds().size() == 0) {
				return;
			}
			if (objToCheck != null) {
				if (objToCheck instanceof EdAtomic) {
					EdAtomic parAtom = (EdAtomic) objToCheck;
					// new Conclusion was added to parAtom, check its rule
					if (!sd.getRule().getBasisRule().getUsedAtomics().contains(
							parAtom.getBasisAtomic()))
						return;
				}
			}
			this.tree.collapsePath(path);
			Vector<EvalSet> atoms = er.getBasisRule().getAtomApplConds();
			// System.out.println("Atomics: "+atoms.size());
			Vector<String> names = er.getBasisRule().getConstraintNames();
			String name;
			for (int i = 0; i < atoms.size(); i++) {
				EvalSet es = atoms.get(i);
				// System.out.println("s = R x P : "+es.getSet().size());
				name = "PAC_" + names.get(i);
				EdRuleConstraint rc = new EdRuleConstraint(name, er, es);
				GraGraTreeNodeData subsd = new RuleApplConstraintTreeNodeData(rc);
				subsd.setString(name);
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(subsd);
				subsd.setTreeNode(child);
	//			this.treeModel.insertNodeInto(child, rnode, rnode.getChildCount());
				// System.out.println("Conclusions: "+es.getSet().size());
				boolean parentAdded = false;
				for (int j = 0; j < es.getSet().size(); j++) {
					// AtomApplCond cond = (AtomApplCond) es.getSet().get(j);
					Vector<?> set = ((EvalSet) es.getSet().get(j)).getSet();
					// System.out.println("Conclusions: "+set.size());
					if (set.size() == 0) {
	//					System.out.println("GragRaTreeView.addRuleConstraints: "
	//							+ er.getBasisRule().getName()
	//							+ ":  No  AtomApplCond  found!");
						break;
					}
					if (!parentAdded) {
						this.treeModel.insertNodeInto(child, rnode, rnode.getChildCount());
						parentAdded = true;
					}
					for (int k = 0; k < set.size(); k++) {
						AtomApplCond cond = (AtomApplCond) set.elementAt(k);
						String condName = cond.getSourceAtomConstraint().getName();
						String n = (k + (j * set.size()) + 1) + "_" + condName;
						EdAtomApplCond aac = new EdAtomApplCond(n, er, cond);
						GraGraTreeNodeData conddata = new RuleAtomicApplConstraintTreeNodeData(aac);
						conddata.setString(n);
						DefaultMutableTreeNode condchild = new DefaultMutableTreeNode(
								conddata);
						conddata.setTreeNode(condchild);
						this.treeModel.insertNodeInto(condchild, child, child
								.getChildCount());
						
						for (int l = 0; l < cond.getEquivalents().size(); l++) {
							AtomApplCond eq = cond.getEquivalents().elementAt(l);
							String eqn = n + "_Eq" + l;
							EdAtomApplCond aacEq = new EdAtomApplCond(eqn, er, eq);
							GraGraTreeNodeData eqdata = new RuleAtomicApplConstraintTreeNodeData(
									aacEq);
							eqdata.setString(eqn);
							DefaultMutableTreeNode eqchild = new DefaultMutableTreeNode(
									eqdata);
							eqdata.setTreeNode(eqchild);
							this.treeModel.insertNodeInto(eqchild, child, child
									.getChildCount());
						}
						
					}
				}
			}
			this.tree.expandPath(path);
		} else {
			lockWarning();
		}
	}
	
	public void handleRuleConstraints(DefaultMutableTreeNode node,
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
						addRuleConstraints(child, objToCheck);
					else
						removeRuleConstraints(child, objToCheck);
				}
			}
			
		} else if (sd.isRule()) {
			if (insert)
				addRuleConstraints(node, objToCheck);
			else {
				removeRuleConstraints(node, objToCheck);
			}
		}
	}

	/*
	 * ******************************************************************** Save
	 * and Load GraGra
	 * ********************************************************************
	 */

	/**
	 * LoadEvent occurred
	 */
	public void loadEventOccurred(LoadEvent e) {
		// if (e.getMsg() == LoadEvent.PROGRESS_BEGIN);
	}

	/** Loads a gragra */
	public void loadGraGra() {
		loadGraGra(null);
		AGGAppl.hideFileLoadLogo();
	}

	@SuppressWarnings("deprecation")
	public void loadGraGra(String fullFileName) {
		synchronized (this.tree) {
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOAD));
		EdGraGra loadedGraGra = null;
		if (fullFileName != null) {			
			AGGAppl.showFileLoadLogo();
			File f = new File(fullFileName);
			if (f.exists()) {		
				long time0 = System.currentTimeMillis();
				
				XMLHelper h = new XMLHelper();
				if (h.read_from_xml(fullFileName)) {
					GraGra bgra = BaseFactory.theFactory().createGraGra();
					h.getTopObject(bgra);
	
					System.out.println("(Base) Grammar  <" + bgra.getName()
							+ ">  loaded in  "
							+ (System.currentTimeMillis() - time0) + "ms");
						
					loadedGraGra = new EdGraGra(bgra);
					if (f.getParent() != null)
						loadedGraGra.setDirName(f.getParent());
					else
						loadedGraGra.setDirName(System.getProperty("user.dir")
								+ File.separator);
					loadedGraGra.setFileName(f.getName());
					loadedGraGra.getTypeSet().setResourcesPath(
							loadedGraGra.getDirName());
					h.enrichObject(loadedGraGra);
					
					System.out.println("(Layouted) Grammar loaded in  "
							+ (System.currentTimeMillis() - time0) + "ms");
					if (bgra.getRulesVec().size() > 20)
						System.out.println("Grammar contains Rules: "+bgra.getRulesVec().size());
				}
			}
		} else {
			long time0 = System.currentTimeMillis();
			/* open load dialog and get gragra */
			this.gragraLoad.setDirName(this.directory);
			this.gragraLoad.load();
			if (this.gragraLoad.getGraGra() != null) {
				loadedGraGra = this.gragraLoad.getGraGra();
				System.out.println("(Layouted) Grammar loaded in  "
						+ (System.currentTimeMillis() - time0) + "ms");
				if (loadedGraGra.getRules().size() > 100)
					System.out.println("Grammar contains Rules: "+loadedGraGra.getRules().size());
			}			
		}
		// long time0 = System.currentTimeMillis();
		
		if (loadedGraGra != null) {
			// notify BaseFactory
			BaseFactory.theFactory().notify(loadedGraGra.getBasisGraGra());
			/* put gragra into tree */
			GrammarTreeNode grammarTreeNode = new GrammarTreeNode(loadedGraGra);
			int graIndex = grammarTreeNode.insertIntoTree(this);
//			int graIndex = putGraGraInTree(loadedGraGra);	
			
			/* put gragra in editor */
			propagateGraGraToEditor(graIndex);
			loadedGraGra.setChanged(false);
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
			
			if (this.currentGraGra.getGraTraOptions().contains(GraTraOptions.LAYERED)) {
				this.layered = true;
				this.priority = false;
				this.ruleSequence = false;
				this.treeModel.ruleNameChanged(this.currentGraGra, this.layered);
				this.treeModel.constraintNameChanged(this.currentGraGra, this.layered);
			} else if (this.currentGraGra.getGraTraOptions().contains(GraTraOptions.PRIORITY)) {
				this.layered = false;
				this.priority = true;
				this.ruleSequence = false;
				this.treeModel.ruleNameChanged(this.currentGraGra, this.layered, this.priority);
				this.treeModel.constraintNameChanged(this.currentGraGra, this.layered,
						this.priority);
			} else if (this.currentGraGra.getGraTraOptions().contains(GraTraOptions.RULE_SEQUENCE)) {
				this.ruleSequence = true;
				this.ruleSequenceHidden = false;
				this.layered = false;
				this.priority = false;
			} else {
				this.layered = false;
				this.priority = false;
				this.ruleSequence = false;
			}

			this.directory = this.gragraLoad.getDirName();
			resetEnabledOfFileMenuItems("open");
			this.filePopupMenu.resetEnabledOfFileMenuItems("open");
			resetEnabledOfToolBarItems("open");						
		} 
		else if (!this.gragraLoad.isCanceled()) {
			JOptionPane.showMessageDialog(this.applFrame, 
					"<html><body>Cannot load the grammar.<br>Please check the .ggx file.</body></html>",
					"  GraGra load failed",
					JOptionPane.ERROR_MESSAGE);
		}
		}
	}

	/** Loads a base gragra */
	public void loadBaseGraGra() {
		loadBaseGrammar();
		AGGAppl.hideFileLoadLogo();
	}
	
	/** Loads a base gragra */
	protected void loadBaseGrammar() {
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOAD));

		/* open load dialog and get gragra */
		this.gragraLoad.loadBase();
		if (this.gragraLoad.getBaseGraGra() != null) {
			GraGra loadedBasis = this.gragraLoad.getBaseGraGra();
			BaseFactory.theFactory().notify(loadedBasis);

			EdGraGra loadedGraGra = new EdGraGra(loadedBasis);
			loadedGraGra.setDirName(this.gragraLoad.getDirName());
			loadedGraGra.setFileName(this.gragraLoad.getFileName());
			loadedGraGra.makeLayoutOfBasisGraphs();
			
			GrammarTreeNode grammarTreeNode = new GrammarTreeNode(loadedGraGra);
			int graIndex = grammarTreeNode.insertIntoTree(this);
//			int graIndex = putGraGraInTree(loadedGraGra);
			
			/* put gragra in editor */
			propagateGraGraToEditor(graIndex);
			if (this.currentGraGra.getGraTraOptions().contains("layered"))
				this.treeModel.ruleNameChanged(this.currentGraGra, true);

			resetEnabledOfFileMenuItems("open");
			this.filePopupMenu.resetEnabledOfFileMenuItems("open");
			resetEnabledOfToolBarItems("open");

			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
		} else
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
	}

	public void doExpand(final EdGraGra gragra, final int graIndex) {
		if (!this.tree.isExpanded(this.tree.getPathForRow(graIndex)))
			this.tree.expandPath(this.tree.getPathForRow(graIndex));
	}

	/* put gragra in editor */
	private void propagateGraGraToEditor(int graIndex) {
		this.tree.setSelectionRow(graIndex);
		this.tree.treeDidChange();
		this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
		this.editorPath = this.selPath;
		setCurrentData(this.editorPath);
		setFlagForNew();
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SELECTED,
				this.editorPath));
	}
	
	/** Reloads the gragra */
	public void reloadGraGra() { // this.currentGraGra
		if(this.applFrame instanceof AGGAppl
				&& ((AGGAppl)this.applFrame).getGraGraEditor().isTransformationRunning()
				&& ((AGGAppl)this.applFrame).getGraGraEditor().getGraGra() == this.currentGraGra) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Graph Transformation running!\n"
					+"Cannot reload the currently used grammar.",
					"Cannot reload", JOptionPane.ERROR_MESSAGE);
		}
		else {
			reloadCurrentGraGra();
			AGGAppl.hideFileLoadLogo();
		}
	}
	
	/** Reloads the gragra */
	protected void reloadCurrentGraGra() {	
		EdGraGra gra = this.currentGraGra;
		
		final DefaultMutableTreeNode 
		aNode = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
		if (((GraGraTreeNodeData) aNode.getUserObject()).isGraGra()
				&& gra != ((GraGraTreeNodeData) aNode.getUserObject()).getGraGra())
			gra = ((GraGraTreeNodeData) aNode.getUserObject()).getGraGra();			

		if (gra.getFileName().equals("")) {
			JOptionPane.showMessageDialog(this.applFrame,
					"Please save the grammar first.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (gra == this.currentGraGra) {
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOAD));
		}

		// Warning
		if (gra.isChanged()) {
			int answer = changedGraGraWarning("RELOAD");
			if (answer == JOptionPane.NO_OPTION)
				return;
		}
		
		EdGraGra gragra = (new GrammarTreeNode()).reloadCurrentGraGra(this, this.selPath, this.gragraLoad, gra);
		if (gragra != null) {		
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
			
			this.selectPath(this.selPath);
			
			/* put gragra in editor */
//			this.editorPath = this.selPath;
//			setCurrentData(this.editorPath);		
//			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.SELECTED,
//					this.editorPath));
			
			gra.dispose();
		}
	}

	public void synchronizeGraGraRuleView(final GraGra grammar) {
		if (this.currentGraGra != null && this.currentGraGra.getBasisGraGra() == grammar) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.selPath
					.getLastPathComponent();
			GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
			if (data.isGraGra()) {
				updateGraGraRuleData(node);
				this.tree.treeDidChange();
			} else {
				node = (DefaultMutableTreeNode) this.selPath.getParentPath()
						.getLastPathComponent();
				data = (GraGraTreeNodeData) node.getUserObject();
				if (data.isGraGra()) {
					updateGraGraRuleData(node);
					this.tree.treeDidChange();
				}
			}
		} else {
			DefaultMutableTreeNode node = getTreeNodeOfGrammar(grammar);
			if (node != null) {
				updateGraGraRuleData(node);
				this.tree.treeDidChange();
			}
		}
	}

	private void updateGraGraRuleData(final DefaultMutableTreeNode graNode) {
		EdGraGra gragra = ((GraGraTreeNodeData) graNode.getUserObject())
				.getGraGra();
		Vector<Rule> preservedRules = new Vector<Rule>();
		// first check deleted rules
		for (int i = 0; i < graNode.getChildCount(); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) graNode
					.getChildAt(i);
			GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
			if (data.isRule()) {
				if (gragra.getBasisGraGra().getRule(
						data.getRule().getBasisRule().getName()) == null) {
					// rule not found, delete its this.tree node
					graNode.remove(i);
					i--;
				} else
					preservedRules.add(data.getRule().getBasisRule());
			}
		}
		// now add created rules
		GrammarTreeNode gtn = new GrammarTreeNode();		
		for (int i = 0; i < gragra.getBasisGraGra().getListOfRules().size(); i++) {
			Rule r = gragra.getBasisGraGra().getListOfRules().get(i);
			if (!preservedRules.contains(r)) {
				if (gragra.addRule(new EdRule(r))) {
					EdRule er = gragra.getRules().lastElement();
					gtn.insertRuleIntoTree(this, graNode, er);
				}
			}
		}
	}

	public TreePath getTreePathOfGrammar(final Object elem) {
		for (int i = 0; i<this.tree.getRowCount(); i++) {
			final TreePath path = this.tree.getPathForRow(i);
			final GraGraTreeNodeData data = (GraGraTreeNodeData) 
							((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
			if (data.isGraGra() 
					&& (elem == data.getData()
							|| elem == ((EdGraGra)data.getData()).getBasisGraGra())) {
				return path;
			}
		}
		return null;
	}
	
	public TreePath getTreePathOfGrammarElement(final Object elem) {
		for (int i = 0; i<this.tree.getRowCount(); i++) {
			final TreePath path = this.tree.getPathForRow(i);
			final GraGraTreeNodeData data = (GraGraTreeNodeData) 
							((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
			if (elem == data.getData()) {
				return path;
			}
		}
		return null;
	}
	
	public DefaultMutableTreeNode getTreeNodeOfGrammarElement(final Object elem) {
		for (int i = 0; i<this.tree.getRowCount(); i++) {
			final TreePath path = this.tree.getPathForRow(i);
			final DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) path.getLastPathComponent();
			final GraGraTreeNodeData data = (GraGraTreeNodeData) treenode.getUserObject();
			if (elem == data.getData()) {
				return treenode;
			}		
		}
		return null;
	}
	
	public DefaultMutableTreeNode getTreeNodeOfGrammar(final GraGra grammar) {
		for (int i = 0; i < this.top.getChildCount(); i++) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) this.top
					.getChildAt(i);
			GraGraTreeNodeData sd = (GraGraTreeNodeData) graNode
					.getUserObject();
			if (sd.getGraGra().getBasisGraGra() == grammar)
				return graNode;
		}
		return null;
	}

	public DefaultMutableTreeNode getTreeNodeOfRule(final EdRule rule) {
		for (int i = 0; i < this.top.getChildCount(); i++) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) this.top
					.getChildAt(i);
			for (int j = 0; j < graNode.getChildCount(); j++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) graNode
						.getChildAt(j);
				GraGraTreeNodeData sd = (GraGraTreeNodeData) child
						.getUserObject();
				if (sd.isRule() && sd.getRule() == rule)
					return child;
			}
		}
		return null;
	}

	private DefaultMutableTreeNode getTreeNodeOfAtomicConclusion(final EdAtomic conclusion) {
		for (int i = 0; i < this.top.getChildCount(); i++) {
			DefaultMutableTreeNode graNode = (DefaultMutableTreeNode) this.top
					.getChildAt(i);
			for (int j = 0; j < graNode.getChildCount(); j++) {
				DefaultMutableTreeNode grachild = (DefaultMutableTreeNode) graNode
						.getChildAt(j);
				GraGraTreeNodeData sdj = (GraGraTreeNodeData) grachild
						.getUserObject();
				if (sdj.isAtomic()) {
					for (int k = 0; k < grachild.getChildCount(); k++) {
						DefaultMutableTreeNode child = (DefaultMutableTreeNode) grachild
								.getChildAt(k);
						GraGraTreeNodeData sdk = (GraGraTreeNodeData) child
								.getUserObject();
						if (sdk.isConclusion() && sdk.getConclusion() == conclusion) {
							return child;
						}
					}
				}
			}
		}
		return null;
	}
	
	/** Saves the specified gragra */
	private void saveGraGra(final EdGraGra gragra) {
		if (gragra != null) {
			gragra.refreshAttrInstances();
			if (gragra.getDirName().equals(""))
				this.gragraSave.setDirName(this.directory);
			
			this.gragraSave.setGraGra(gragra, gragra.getDirName(), gragra.getFileName());
			this.gragraSave.save();
		}
	}
	
	/** Saves the selected gragra */
	public void saveGraGra() {
		/* get gragra to save */
		EdGraGra selGraGra = getGraGra();
		if (selGraGra != null) {
			selGraGra.refreshAttrInstances();
			
			(new GrammarTreeNode()).saveGraGra(this, this.gragraSave,
					this.directory, selGraGra);			
		}
	}

	
	/** Saves the selected gragra */
	public void saveAsGraGra() {
		/* get gragra to save */
		EdGraGra selGraGra = getGraGra();
		if (selGraGra != null) {
			selGraGra.refreshAttrInstances();
			
			(new GrammarTreeNode()).saveAsGraGra(this, this.gragraSave,
					this.directory, selGraGra);			
		}
	}

	/** Saves the base gragra of the selected gragra */
	public void saveAsBaseGraGra() {
		/* get gragra to save */
		EdGraGra selGraGra = getGraGra();
		if (selGraGra != null) {
			selGraGra.refreshAttrInstances();
			
			/* deselect all selections and remove all matches */
			selGraGra.clear();
			
			(new GrammarTreeNode()).saveAsBaseGraGra(this, this.gragraSave,
					this.directory, selGraGra);			
		}
	}

	public String getFileDirectory() {
		return this.directory;
	}

	public void setFileDirectory(final String dir) {
		this.directory = dir;
	}
	
	/** Reset the working host graph of the currently selected gragra
	 * by the copy of its start graph.
	 */
	protected synchronized void resetGraph() {
		resetGraph(null);
	}
	
	/** Reset the specified graph of the currently selected gragra
	 * by the copy of its start graph.
	 */
	public synchronized void resetGraph(final EdGraph graph) {
		/* get selected gragra */
		EdGraGra selGraGra = getGraGra();
		if (selGraGra != null) {
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_RESET));
			
			GraGraTreeNodeData ndataEdit = (GraGraTreeNodeData)
					((DefaultMutableTreeNode) this.editorPath.getLastPathComponent()).getUserObject();
			GraGraTreeNodeData ndataSel = (GraGraTreeNodeData)
				((DefaultMutableTreeNode) this.selPath.getLastPathComponent()).getUserObject();
			
			if (ndataEdit.isGraph() && ndataSel.isGraph()
					&& (ndataEdit.getGraph() == ndataSel.getGraph())) {
				if (selGraGra.resetGraph()) {					
					ndataEdit.setData(selGraGra.getGraph());
					ndataEdit.setString(selGraGra.getGraph().getName());
					this.tree.treeDidChange();
					
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.RESET_GRAPH, this.editorPath));
				}
			} else if (ndataSel.isGraph()) {
				if (ndataSel.getGraph() == selGraGra.getGraph()) {
					if (selGraGra.resetGraph()) {		
						ndataSel.setData(selGraGra.getGraph());
						ndataSel.setString(selGraGra.getGraph().getName());
						this.tree.treeDidChange();
						
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.RESET_GRAPH, this.selPath));
					}
				} else {
					int indx = selGraGra.getIndexOfGraph(ndataSel.getGraph());
					if (selGraGra.resetGraph(indx)) {
						EdGraph g = selGraGra.getGraph(indx);
						if (g != null) {
							ndataSel.setData(g);
							ndataSel.setString(g.getName());
							this.tree.treeDidChange();
						} 
					}
				}
			} else if (this.currentGraph == selGraGra.getGraph()) {
				final TreePath path = this.getTreePathOfGrammarElement(this.currentGraph);
				if (path != null) {
					GraGraTreeNodeData ndata = (GraGraTreeNodeData)
						((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
					if (ndata != null && selGraGra.resetGraph()) {	
						ndata.setData(selGraGra.getGraph());
						ndata.setString(selGraGra.getGraph().getName());
						this.currentGraph = selGraGra.getGraph();
						this.tree.treeDidChange();
						
						fireTreeViewEvent(new TreeViewEvent(this,
												TreeViewEvent.RESET_GRAPH, path));
					}
				}
			} else if (graph != null 
					&& graph == selGraGra.getGraph()) {
				final TreePath path = this.getTreePathOfGrammarElement(graph);
				if (path != null) {
					GraGraTreeNodeData ndata = (GraGraTreeNodeData)
						((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
					if (ndata != null && selGraGra.resetGraph()) {	
						ndata.setData(selGraGra.getGraph());
						ndata.setString(selGraGra.getGraph().getName());
						this.currentGraph = selGraGra.getGraph();
						this.tree.treeDidChange();
						
						fireTreeViewEvent(new TreeViewEvent(this,
												TreeViewEvent.RESET_GRAPH, path));
					}
				}
			}
		}
	}

	/** Exports the host graph of the selected gragra to JPEG format */
	public void exportGraphJPEG() {
		if (getGraGra() == null)
			return;
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.EXPORT_JPEG));
	}

	/** Exports the graphics of the AGG window to JPEG format */
	public void exportJPEG() {
		saveJPEG();
	}

	/**
	 * Converts gragra to GXL : XML-based File Exchange Format for Graphs and
	 * Graph Transformation Systems
	 */
	public void exportGraGra(final String format) {
		/* get gragra to save */
		EdGraGra selGraGra = getGraGra();
		if (selGraGra == null)
			return;

		/* deselect all selections and remove all matches */
		selGraGra.clear();
		String oldDir = selGraGra.getDirName();
		String oldFile = selGraGra.getFileName();

		/* set gragra to save */
		String tmpn = "tmp_" + selGraGra.getName() + ".ggx";
		String tmpName = tmpn.replaceAll(" ", "");
		this.gragraSave.setGraGra(selGraGra, selGraGra.getDirName(), tmpName);
		this.gragraSave.save();

		File tmpf = new File(selGraGra.getDirName() + tmpName);
		tmpf.deleteOnExit();

		ConverterXML converter = new ConverterXML();
		String fn = "";
		String fd = "";
		String filter = "";
		if (format.equals("GXL"))
			filter = ".gxl";
		else if (format.equals("GTXL"))
			filter = ".gtxl";
		else if (format.endsWith("COLOR_GRAPH"))
			filter = ".col";
		
		if (!filter.equals("")) {
			// get name of gxl or gtxl file
			JFileChooser chooser = null;
			if (this.directory.equals(""))
				chooser = new JFileChooser(System.getProperty("user.dir"));
			else
				chooser = new JFileChooser(this.directory);

			AGGFileFilter fileFilter = null;
			if (format.endsWith("COLOR_GRAPH")) 
				fileFilter = new AGGFileFilter(filter, "COLOR_GRAPH Files ("
						+ filter + ")");
			else
				fileFilter = new AGGFileFilter(filter, "XML Files ("
					+ filter + ")");
			
			chooser.addChoosableFileFilter(fileFilter);
			chooser.setFileFilter(fileFilter);
			int value = chooser.showSaveDialog(this.applFrame);
			if (value == JFileChooser.APPROVE_OPTION) {
				fd = chooser.getCurrentDirectory().toString();
				if (chooser.getSelectedFile() != null
						&& !chooser.getSelectedFile().getName().equals("")) {
					fn = chooser.getSelectedFile().getName();
//					if (!fn.endsWith(filter))
//						fn = fn.concat(filter);
				}
				if (!fd.endsWith(File.separator))
					fd += File.separator;
			}
			
			if (!fn.equals("")) {
				File source = null;
				if (format.equals("GXL")) {
					source = converter.copyFile(fd, "ggx2gxl.xsl");
					if (source != null
							&& converter.ggx2gxl(selGraGra.getDirName()
									+ selGraGra.getFileName(), fd + fn, fd
									+ "ggx2gxl.xsl")) {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.CONVERTED));
					} else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						JOptionPane.showMessageDialog(this.applFrame,
								"Export has failed!",
								"", JOptionPane.WARNING_MESSAGE);
					}
				} 
				else if (format.equals("GTXL")) {
					source = converter.copyFile(fd, "gts2gtxl.xsl");
					if (source != null) {
						source = converter.copyFile(fd, "ggx2gxl.xsl");
					}
					if (source != null
							&& converter.gts2gtxl(selGraGra.getDirName()
									+ selGraGra.getFileName(), fd + fn, fd
									+ "gts2gtxl.xsl")) {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.CONVERTED));
					} else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						JOptionPane.showMessageDialog(this.applFrame,
								"Export has failed!",
								"", JOptionPane.WARNING_MESSAGE);
					}
				} 
				else if (format.endsWith("COLOR_GRAPH")) {
					Type ntype = null;
					if (this.nodeTypeOfColorGraph != null)
						ntype = this.nodeTypeOfColorGraph.getBasisType();
					Type etype = null;
					if (this.edgeTypeOfColorGraph != null)
						etype = this.edgeTypeOfColorGraph.getBasisType();
					AGG2ColorGraph.exportAGG2ColorGraph(selGraGra.getBasisGraGra(), fd + fn,
							ntype, 
							etype); 						
				}
				else {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.ERROR));
					JOptionPane.showMessageDialog(this.applFrame,
							"Export has failed!",
							"", JOptionPane.WARNING_MESSAGE);
				}
			}
		}		
		
		selGraGra.setDirName(oldDir);
		selGraGra.setFileName(oldFile);
	}

	/**
	 * Converts gragra from GXL : XML-based File Exchange Format for Graphs and
	 * Graph Transformation Systems
	 */
	public void importGraGra(final String format) {
		doImportGraGra(format);
		AGGAppl.hideFileLoadLogo();
	}
	
	/**
	 * Converts gragra from GXL : XML-based File Exchange Format for Graphs and
	 * Graph Transformation Systems
	 */
	@SuppressWarnings("unused")
	protected synchronized void doImportGraGra(String format) {
		boolean importAsGrammar = true;
		TreePath path = this.selPath;
		GraGraTreeNodeData data = null;
		if (path != null) {
			data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while ((data != null) && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else
					data = null;
			}
			if (data == null) {
				importAsGrammar = true;
//				JOptionPane.showMessageDialog(this.applFrame,
//						"Cannot import!\nPlease select a grammar first.",
//						"", JOptionPane.ERROR_MESSAGE);
//				return;
			} 
			else if (format.endsWith("COLOR_GRAPH")) {
				importAsGrammar = false;
			}
			else {
				Object[] options = { "Grammar", "Grammar Component" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease choose what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (answer == 0) 
					importAsGrammar = true;
				else
					importAsGrammar = false;
			}
			
			if (!importAsGrammar && !format.endsWith("COLOR_GRAPH")) {
				EdGraGra selGraGra = data.getGraGra();
					
					if (this.editorPath != null && !(path == this.editorPath)) {
						JOptionPane.showMessageDialog(this.applFrame,
									"<html><body>"
									+"Bad selection to import a grammar component."
									+"\nPlease select the grammar "+this.currentGraGra.getName()+"."
									+"\nThe Type Graph should be disabled before.",
									"",
									JOptionPane.WARNING_MESSAGE);
						return;
					}
					else if (selGraGra.getTypeSet() != null				
								&& selGraGra.getTypeSet().getBasisTypeSet()
											.getLevelOfTypeGraphCheck() != 0) {
						JOptionPane.showMessageDialog(this.applFrame,
									"Cannot import! "
									+ "\nPlease disable the Type Graph first.",
									"Import Warning", JOptionPane.ERROR_MESSAGE);
						return;
					}
			}
		}

		ConverterXML converter = new ConverterXML();
		String fn = "";
		String fd = "";
		String filter = "";
		if (format.equals("GGX"))
			filter = ".ggx";
		else if (format.equals("GXL"))
			filter = ".gxl";
		else if (format.equals("GTXL"))
			filter = ".gtxl";
		else if (format.equals("OMONDOXMI"))
			filter = ".ecore";
		else if (format.endsWith("COLOR_GRAPH"))
			filter = ".res";
		
		if (!filter.equals("")) {
			// get name of gxl or gtxl file
			JFileChooser chooser = null;
			if (this.directory.equals(""))
				chooser = new JFileChooser(System.getProperty("user.dir"));
			else
				chooser = new JFileChooser(this.directory);
			
			AGGFileFilter filterXML = null;
			if (format.endsWith("COLOR_GRAPH")) {
				filterXML = new AGGFileFilter(filter, "COLOR_GRAPH Files ("+ filter + ")");
			}
			else {
				filterXML = new AGGFileFilter(filter, "XML Files ("+ filter + ")");
			}
			
			chooser.addChoosableFileFilter(filterXML);
			chooser.setFileFilter(filterXML);
			int value = chooser.showOpenDialog(this.applFrame);
			if (value == JFileChooser.APPROVE_OPTION) {
				if (chooser.getSelectedFile() != null
						&& !chooser.getSelectedFile().getName().equals("")) {
					this.directory = chooser.getCurrentDirectory().toString();
					fn = chooser.getSelectedFile().getName();
					fd = chooser.getCurrentDirectory().toString();
					if (!fd.endsWith(File.separator))
						fd += File.separator;
				}
			}

			if (!fn.equals("")) {
				String fnOut = "";
				File gxldtd = null;
				File gtsdtd = null;
				File source = null;
				if (format.equals("GGX")) {
					// System.out.println("GraGraTreeView.importGraGra: format
					// is GGX :: "+fn);
				} else if (format.equals("GXL")) {
					fnOut = fn.substring(0, fn.length() - 4) + "_gxl.ggx";
					source = converter.copyFile(fd, "gxl2ggx.xsl");
					gxldtd = converter.copyFile(fd, "gxl.dtd");
					String fd1 = System.getProperty("user.dir"); // test
					if (!fd1.endsWith(File.separator))
						fd1 += File.separator;
					gtsdtd = converter.copyFile(fd, "gts.dtd");
					gtsdtd = converter.copyFile(fd1, "gts.dtd");
					converter.copyFile(fd, "agglayout.dtd");
					converter.copyFile(fd1, "agglayout.dtd");
				} else if (format.equals("GTXL")) {
					fnOut = fn.substring(0, fn.length() - 4) + "_gtxl.ggx";
					// source = converter.copyFile(fd, "gtxl2gts.xsl");
					// gxldtd = converter.copyDTD(fd, "gtxl.dtd");
					// gtsdtd = converter.copyDTD(fd, "gts.dtd");
					// converter.copyFile(fd, "agglayout.dtd");
				} else if (format.equals("OMONDOXMI")) {
					// System.out.println("GraGraTreeView.importGraGra: format
					// is OMONDOXMI ");
					fnOut = fn.substring(0, fn.length() - 6) + "_ecore.ggx";
					source = converter.copyFile(fd, "gxl2ggx.xsl");
					gxldtd = converter.copyFile(fd, "gxl.dtd");
					String fd1 = System.getProperty("user.dir"); 
					if (!fd1.endsWith(File.separator))
						fd1 += File.separator;
					gtsdtd = converter.copyFile(fd, "gts.dtd");
					gtsdtd = converter.copyFile(fd1, "gts.dtd");
					converter.copyFile(fd, "agglayout.dtd");
					converter.copyFile(fd1, "agglayout.dtd");
					converter.copyFile(fd, "omondoxmi2gxl.xsl");
				} 
				else if (data != null && format.endsWith("COLOR_GRAPH")) {					
					EdGraGra selGraGra = data.getGraGra();
					JOptionPane.showMessageDialog(this.applFrame,
							"Please note:\n"
							+"Color values from result ColorGraph will be used \n"
							+"as value of color attributes of the nodes of the current host graph.\n"
							+"The color attribute should be of type <int> or <String>",
							"Import result of ColorGraph", JOptionPane.INFORMATION_MESSAGE);

					Type ntype = null;
					if (this.nodeTypeOfColorGraph != null)
						ntype = this.nodeTypeOfColorGraph.getBasisType();
					Type etype = null;
					if (this.edgeTypeOfColorGraph != null)
						etype = this.edgeTypeOfColorGraph.getBasisType();
					if (AGG2ColorGraph.importColorGraph2AGG(selGraGra.getBasisGraGra(),
										selGraGra.getBasisGraGra().getGraph(), 
										fd+fn, 
										ntype, 
										etype)) {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.GRAPH_CHANGED));
					}
				}

				if (!format.equals("GGX")
						&& !format.endsWith("COLOR_GRAPH")
						&& ((gxldtd == null) || (gtsdtd == null))) {
					JOptionPane
							.showMessageDialog(this.applFrame,
									"Import failed!\n File gxl.dtd resp. gts.dtd is not found.",
									"", JOptionPane.WARNING_MESSAGE);
				}
				
				if (format.equals("GGX")) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.LOAD));
					/* open and get gragra */
					this.gragraLoad.reload(fd, fn);
					if (this.gragraLoad.getGraGra() != null) {
						handleLoadedImportGraGra(this.gragraLoad.getGraGra(),
								this.selPath, importAsGrammar);
					}
					else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						this.applFrame.getRootPane().revalidate();
						JOptionPane.showMessageDialog(this.applFrame,
								"Import GGX file failed!",
								"", JOptionPane.ERROR_MESSAGE);
					}
				} else if (format.equals("GXL")
						&& converter.gxl2ggx(fd + fn, fd + fnOut, fd
								+ "gxl2ggx.xsl")) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.LOAD));
					/* open and get gragra */
					this.gragraLoad.reload(fd, fnOut);
					if (this.gragraLoad.getGraGra() != null) {
						EdGraGra test = this.gragraLoad.getGraGra();
//						System.out.println(test.getDirName()+""+test.getFileName());
//						this.gragraSave.setGraGra(test, test.getDirName(), test
//								.getFileName());
//						this.gragraSave.save();
						
//						//test
//						if (converter.getFileExtOfImport().equals(".ecore")) {
//							test.makeTypeGraphFromPlainGraph(test.getGraph());
//							handleLoadedImportGraGra(test,
//									this.selPath, importAsGrammar);
//						}
//						else	
							handleLoadedImportGraGra(this.gragraLoad.getGraGra(),
									this.selPath, importAsGrammar);
					} else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						this.applFrame.getRootPane().revalidate();
						JOptionPane.showMessageDialog(this.applFrame,
								"Import GXL file failed!",
								"", JOptionPane.ERROR_MESSAGE);
					}
				} else if (format.equals("GTXL")
						&& converter.gtxl2gts(fd + fn, fd + fnOut, fd
								+ "gtxl2gts.xsl")) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.LOAD));
					/* open and get gragra */
					this.gragraLoad.reload(fd, fnOut);
					if (this.gragraLoad.getGraGra() != null) {
						EdGraGra loadedGraGra = this.gragraLoad.getGraGra();
						this.gragraSave.setGraGra(loadedGraGra, loadedGraGra
								.getDirName(), loadedGraGra.getFileName());
						this.gragraSave.save();
						loadedGraGra.getBasisGraGra().setGraTraOptions(
								new Completion_NAC(new Completion_InjCSP()));
						BaseFactory.theFactory().notify(
								loadedGraGra.getBasisGraGra());
						loadedGraGra.update();
						
//						putGraGraInTree(loadedGraGra);						
						GrammarTreeNode grammarTreeNode = new GrammarTreeNode(loadedGraGra);
						int indx = grammarTreeNode.insertIntoTree(this);
						
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.LOADED));
					} else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						this.applFrame.getRootPane().revalidate();
						JOptionPane.showMessageDialog(this.applFrame,
								"Import GTXL file failed!",
								"", JOptionPane.ERROR_MESSAGE);
					}
				} else if (format.equals("OMONDOXMI")
						&& converter.omondoxmi2ggx(fd + fn, fd + fnOut, fd
								+ "omondoxmi2gxl.xsl", fd + "gxl2ggx.xsl")) {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.LOAD));
					/* open and get gragra */
					this.gragraLoad.reload(fd, fnOut);
					if (this.gragraLoad.getGraGra() != null) {
						EdGraGra loadedGraGra = this.gragraLoad.getGraGra();
//						//test
						if (converter.getFileExtOfImport().equals(".ecore")) {
							loadedGraGra.createTypeGraphFrom(loadedGraGra.getGraph());							
						}
						this.gragraSave.setGraGra(loadedGraGra, loadedGraGra
								.getDirName(), loadedGraGra.getFileName());
						this.gragraSave.save();
						handleLoadedImportGraGra(loadedGraGra, this.selPath, importAsGrammar);
					} else {
						fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
						this.applFrame.getRootPane().revalidate();
						JOptionPane.showMessageDialog(this.applFrame,
								"Import OMONDO XMI ( .ecore ) file failed!",
								"", JOptionPane.ERROR_MESSAGE);
					}
				} 
				else if (format.endsWith("COLOR_GRAPH")) {	
					
				}
				else {
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.ERROR));
					JOptionPane.showMessageDialog(this.applFrame,
							"Import has failed!",
							"", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}


	private synchronized void handleLoadedImportGraGra(
			final EdGraGra importGraGra,
			final TreePath path, 
			boolean importAsGrammar) {
		if (importGraGra != null) {
			if (this.currentGraGra == null || importAsGrammar) { // selected gragra
				importAsGrammar(importGraGra);
				return;
			} 			
						
			boolean impAsTG = false;
			boolean impAsG = false;
			boolean impAsR = false;
			boolean impAsGC = false;
			
			if ((importGraGra.getTypeSet().getTypeGraph() != null)
					&& !importGraGra.getTypeSet().getTypeGraph()
							.getBasisGraph().isEmpty()) {
				impAsTG = true;
			}
			if (importGraGra.getGraph() != null) {
				impAsG = true;
			}
			if (!importGraGra.getRules().isEmpty()) {
				impAsR = true;
			}
			if (!importGraGra.getAtomics().isEmpty()) {
				impAsGC = true;
			}
			if ((impAsTG && impAsG && impAsR && impAsGC)) {
				Object[] options = { "Type Graph", "Host Graph", "Rule", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[4]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsGraph(importGraGra, path); break;
				case 2: importAsRule(importGraGra, path); break;
				case 3: importAsGraphConstraints(importGraGra, path); break;
				case 4: importAsGrammarItems(importGraGra, path); break;
				}								
			}
			else if ((impAsTG && impAsG && impAsR)) {
				Object[] options = { "Type Graph", "Host Graph", "Rule", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[3]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsGraph(importGraGra, path); break;
				case 2: importAsRule(importGraGra, path); break;
				case 3: importAsGrammarItems(importGraGra, path); break;
				}								
			} 
			else if ((impAsTG && impAsG && impAsGC)) {
				Object[] options = { "Type Graph", "Host Graph", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[3]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsGraph(importGraGra, path); break;
				case 2: importAsGraphConstraints(importGraGra, path); break;
				case 3: importAsGrammarItems(importGraGra, path); break;
				}				
			} 
			else if ((impAsTG && impAsG)) {
				Object[] options = { "Type Graph", "Host Graph", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[2]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsGraph(importGraGra, path); break;
				case 2: importAsGrammarItems(importGraGra, path); break;
				}				
			} 
			else if ((impAsTG && impAsR && impAsGC)) {
				Object[] options = { "Type Graph", "Rule", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[3]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsRule(importGraGra, path); break;
				case 2: importAsGraphConstraints(importGraGra, path); break;
				case 3: importAsGrammarItems(importGraGra, path); break;
				}								
			} 
			else if ((impAsTG && impAsR)) {
				Object[] options = { "Type Graph", "Rule", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[2]);
				switch (answer) {
				case 0: importAsTypeGraph(importGraGra, path); break;
				case 1: importAsRule(importGraGra, path); break;
				case 2: importAsGrammarItems(importGraGra, path); break;
				}								
			} 
			else if (impAsG && impAsR && impAsGC) {
				Object[] options = { "Host Graph", "Rule", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[3]);
				switch (answer) {
				case 0: importAsGraph(importGraGra, path); break;
				case 1: importAsRule(importGraGra, path); break;
				case 2: importAsGraphConstraints(importGraGra, path); break;
				case 3: importAsGrammarItems(importGraGra, path); break;
				}				
			} 
			else if (impAsG && impAsR) {
				Object[] options = { "Host Graph", "Rule", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select what do you want to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[2]);
				switch (answer) {
				case 0: importAsGraph(importGraGra, path); break;
				case 1: importAsRule(importGraGra, path); break;
				case 2: importAsGrammarItems(importGraGra, path); break;
				}				
			}
			else if (impAsR && impAsGC) {
				Object[] options = { "Rule", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				switch (answer) {
				case 0: importAsRule(importGraGra, path); break;
				case 1: importAsGraphConstraints(importGraGra, path); break;
				case 2: importAsGrammarItems(importGraGra, path); break;
				}
			} 
			else if (impAsR) {
				Object[] options = { "Rule", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (answer == 0) {					
					importAsRule(importGraGra, path);
				}				
			} 
			else if (impAsG && impAsGC) {
				Object[] options = { "Host Graph", "Constraints", "All", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				switch (answer) {
				case 0: importAsGraph(importGraGra, path); break;
				case 1: importAsGraphConstraints(importGraGra, path); break;
				case 2: importAsGrammarItems(importGraGra, path); break;
				} 
			} 
			else if (impAsG) {
				Object[] options = { "Host Graph", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (answer == 0) {	
					importAsGraph(importGraGra, path);
				}
			} 
			else if (impAsTG) {
				Object[] options = { "Type Graph", "Cancel" };
				int answer = JOptionPane
						.showOptionDialog(this.applFrame,
								"\nPlease select to import.",
								"Import", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (answer == 0) {	
					importAsTypeGraph(importGraGra, path);
				}
			}
		}
	}

	private void importAsTypeGraph(final EdGraGra imp, final TreePath treepath) {
		TreePath path = treepath;
		if (path != null) {
			GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while ((data != null) && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else
					data = null;
			}
			if (data == null) {
				JOptionPane.showMessageDialog(this.applFrame,
						"Cannot import!\n Please select a grammar first.",
						"", JOptionPane.ERROR_MESSAGE);
				return;
			}
			EdGraGra selGraGra = data.getGraGra();
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));
			if (selGraGra.getBasisGraGra().getTypeSet()
					.getLevelOfTypeGraphCheck() != 0) {
				JOptionPane
						.showMessageDialog(this.applFrame,
								"Cannot import!\n Please disable the current type graph first.",
								"", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean typeGraphImported = importTypeGraph(imp, selGraGra, path);
			if (!typeGraphImported && !this.rewriteTypeGraph) {
				fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
				this.applFrame.getRootPane().revalidate();
			}
		} 
		else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Cannot import!\nPlease select a grammar first.",					
					"", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void importAsGraph(final EdGraGra imp, final TreePath treepath) {
		TreePath path = treepath;
		if (path != null) {
			GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while ((data != null) && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else
					data = null;
			}
			if (data == null) {
				JOptionPane.showMessageDialog(this.applFrame,
						"Cannot import!\nPlease select a grammar first.",
						"", JOptionPane.ERROR_MESSAGE);
				return;
			}
			EdGraGra selGraGra = data.getGraGra();
			Object[] options = { "Use as current graph", "Add to graphs" };
			int answer = JOptionPane
					.showOptionDialog(
							this.applFrame,
							"Do you want to use the import graph as the current graph?"
									+ "\n( Please note: The type graph of the current grammar should be disabled. )",
							"Import Graph", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (answer == 0) {				
				if (!importGraphAsHostGraph(imp, selGraGra, path, true)) {
					fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
					this.applFrame.getRootPane().revalidate();
				}
			} else if (answer == 1) {
				if (!importGraph(imp, selGraGra, path, true)) {
					fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
					this.applFrame.getRootPane().revalidate();
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Cannot import!\nPlease select a grammar first.",
					"", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void importAsGrammar(final EdGraGra imp) {
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOAD));
		if (imp.getBasisGraGra().getGraTraOptions().isEmpty())
			imp.getBasisGraGra().setGraTraOptions(
					new Completion_NAC(new Completion_InjCSP()));
		BaseFactory.theFactory().notify(imp.getBasisGraGra());
		imp.update();
		
		GrammarTreeNode grammarTreeNode = new GrammarTreeNode(imp);
		int graIndex = grammarTreeNode.insertIntoTree(this);
		
		/* put gragra in editor */
		propagateGraGraToEditor(graIndex);
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.LOADED));
		imp.setChanged(false);
		resetEnabledOfFileMenuItems("open");
		this.filePopupMenu.resetEnabledOfFileMenuItems("open");
		resetEnabledOfToolBarItems("open");
	}
	
	private boolean importAsGrammarItems(final EdGraGra importGraGra, final TreePath treepath) {
		TreePath path = treepath;
		if (path != null) {
			GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while (data != null && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else {
					data = null;					
				}
			}
			if (data == null) {
				JOptionPane.showMessageDialog(this.applFrame,
						"Cannot import!\nPlease select a grammar first.",
						"", JOptionPane.ERROR_MESSAGE);
				return false;
			}

			EdGraGra selGraGra = data.getGraGra();
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));
			importGrammarItems(importGraGra, selGraGra, path);
			return true;
		}
		return false;
	}

	private boolean importGrammarItems(
			final EdGraGra importGraGra, final EdGraGra selGraGra, final TreePath path) {
		// import type graph
		if (selGraGra.getTypeGraph() == null) {
			if (selGraGra.getTypeSet().isEmpty()
					&& !importGraGra.getTypeSet().isEmpty()) {
				selGraGra.getTypeSet().getBasisTypeSet().setHelpInfo(String.valueOf(
							importGraGra.getTypeSet().getBasisTypeSet().hashCode()));
			}
			if (importGraGra.getTypeGraph() == null) {					
				selGraGra.getTypeSet().getBasisTypeSet().adaptTypes(
							importGraGra.getBasisGraGra().getTypeSet(), true);
			} else {
				EdGraph tgraph = selGraGra.getTypeSet().createTypeGraph();
				if (!importGraGra.getTypeGraph().getBasisGraph().isEmpty()) {
					tgraph.getBasisGraph().setHelpInfo(String.valueOf(
							importGraGra.getTypeGraph().getBasisGraph().hashCode()));
				}	
				this.addTypeGraph(selGraGra, tgraph);						
				this.importTypeGraph(importGraGra, selGraGra, path);
			}				
		}
		else if (selGraGra.getLevelOfTypeGraphCheck() == TypeSet.DISABLED) {
			if (importGraGra.getTypeGraph() != null) {
				if (selGraGra.getTypeGraph().getBasisGraph().isEmpty()
							&& !importGraGra.getTypeGraph().getBasisGraph().isEmpty()) {
					selGraGra.getTypeGraph().getBasisGraph().setHelpInfo(String.valueOf(
							importGraGra.getTypeGraph().getBasisGraph().hashCode()));
				}
				this.importTypeGraph(importGraGra, selGraGra, path);
			}
			else {
				if (selGraGra.getTypeSet().getBasisTypeSet().isEmpty()
						&& !importGraGra.getBasisGraGra().getTypeSet().isEmpty()) {
					selGraGra.getTypeSet().getBasisTypeSet().setHelpInfo(String.valueOf(
								importGraGra.getTypeSet().getBasisTypeSet().hashCode()));
				}
				selGraGra.getTypeSet().getBasisTypeSet().adaptTypes(
							importGraGra.getBasisGraGra().getTypeSet(), true);
			}
		}
		// import graphs
		for (int i = 0; i < importGraGra.getGraphs().size(); i++) {
			EdGraph eg = importGraGra.getGraphs().get(i);
			importGraph(eg, selGraGra);
		}
		// import rules
		Vector<String> failed = new Vector<String>();
		for (int i = 0; i < importGraGra.getRules().size(); i++) {
			EdRule er = importGraGra.getRules().get(i);
			EdRule newRule = importRule(er, selGraGra);
			if (newRule == null)
				failed.add(er.getBasisRule().getName());
		}
		// import graph constraints
		importGraphConstraints(importGraGra, selGraGra);
		
		selGraGra.getTypeSet().getBasisTypeSet().setHelpInfo("");
		selGraGra.getTypeSet().getBasisTypeSet().getTypeGraph().setHelpInfo("");
			
		if (failed.size() > 0) {
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
			this.applFrame.getRootPane().revalidate();
			JOptionPane.showMessageDialog(this.applFrame,
						"Import has failed for rule(s): " + "\n"
								+ failed.toString(),
								"", JOptionPane.WARNING_MESSAGE);
		}			
		return true;
	}

	private boolean importTypeGraph(final EdGraGra importGraGra, final EdGraGra selGraGra, final TreePath path) {
		boolean imported = false;
		if (!selGraGra.getTypeSet().getBasisTypeSet().compareTypes(
					importGraGra.getTypeSet().getBasisTypeSet())) {
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
			this.applFrame.getRootPane().revalidate();
			Object[] options = { "OK" };
			int answer = JOptionPane.showOptionDialog(
						this.applFrame,
						"There are mismatches of types. "
						+ "\nDo you want to rewrite the current type graph \nby the import type graph?",
						"Import Type Graph",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options,
						options[0]);
			if (answer == JOptionPane.YES_OPTION)
				this.rewriteTypeGraph = true;
		} 
		else {
			boolean selectAll = false;
			if (selGraGra.getTypeSet().getTypeGraph() == null) {
				addTypeGraph();
				selectAll = true;
				this.tree.treeDidChange();
			}
			else if (selGraGra.importTypeGraph(importGraGra.getTypeGraph(), false)) {
				imported = true;
				if (selectAll)
					selGraGra.getTypeSet().getTypeGraph().selectAll();
			}
		}

		if (this.rewriteTypeGraph) {
			if (selGraGra.importTypeGraph(importGraGra.getTypeGraph(), true))
				imported = true;
		} 
		else if (!imported) {
			if (!selGraGra.getTypeSet().getBasisTypeSet().compareTypes(
						importGraGra.getTypeSet().getBasisTypeSet())) {
				Object[] options = { "OK" };
				int answer = JOptionPane.showOptionDialog(
									this.applFrame,
									"There are mismatches of types. "
									+ "\nDo you want to rewrite the current type graph "
									+ "\nby the import type graph?",
									"Import Type Graph",
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.WARNING_MESSAGE, null, options,
									options[0]);
				if (answer == JOptionPane.YES_OPTION) {
					if (selGraGra.importTypeGraph(importGraGra
								.getTypeGraph(), true))
						imported = true;
				}
			} else {
				boolean selectAll = false;
				if (selGraGra.getTypeSet().getTypeGraph() == null) {
					addTypeGraph();
					selectAll = true;
					this.tree.treeDidChange();
				}
				else if (selGraGra.importTypeGraph(importGraGra.getTypeGraph(), true)) {
					imported = true;
					if (selectAll)
						selGraGra.getTypeSet().getTypeGraph().selectAll();
				}
			}
		}
		if (imported) {
			this.rewriteTypeGraph = false;
			DefaultMutableTreeNode 
			pathComp = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) 
						path.getLastPathComponent()).getChildAt(0);
			if (selGraGra.getBasisGraGra().getTypeGraph() != null)
				pathComp = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) 
							path.getLastPathComponent()).getChildAt(1);
			((GraGraTreeNodeData) pathComp.getUserObject()).setData(selGraGra.getGraph());
			JOptionPane.showMessageDialog(
						this.applFrame,
						"<html><body>"
						+"Import Type Graph was successful."
						+ "<br>( Please note: New type graph objects are selected."
						+ "<br>You may move them to the right position, if you want. )"
						+"</body></html>");
			fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.IMPORT_TYPE_GRAPH));
			return true;
		} 
		
		this.rewriteTypeGraph = false;
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
		this.applFrame.getRootPane().revalidate();
		JOptionPane.showMessageDialog(this.applFrame, "Import has failed!",
					"", JOptionPane.WARNING_MESSAGE);
		return false;		
	}
	
	private boolean importGraphAsHostGraph(
			final EdGraGra importGraGra, 
			final EdGraGra selGraGra,
			final TreePath path,
			boolean adapt) {
		boolean doadapt = adapt;
		GraGraTreeNodeData graphData = null;
		if (this.currentGraGra == selGraGra) {
			DefaultMutableTreeNode 
			gragraTreeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			for (int i = 0; i < gragraTreeNode.getChildCount(); i++) {
				DefaultMutableTreeNode 
				child = (DefaultMutableTreeNode) gragraTreeNode.getChildAt(i);
				GraGraTreeNodeData 
				childData = (GraGraTreeNodeData) child.getUserObject();
				if (childData.isGraph()
						&& childData.getGraph() == selGraGra.getGraph()) {
					graphData = childData;
					break;
				}
			}
		}
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));
			
		EdGraph graphToImport = null;
		Vector<String> graphNames = new Vector<String>();
		if (importGraGra.getGraphs().size() > 1) {
			Vector<EdGraph> graphs = importGraGra.getGraphs();
			for (int i = 0; i < graphs.size(); i++)
				graphNames.add(graphs.get(i).getName());
	
			GraphImportDialog gid = new GraphImportDialog(this.applFrame,
						"Graph to import", graphNames, true);
			gid.setVisible(true);
			graphNames = gid.getSelectedItemNames();
			if (!graphNames.isEmpty()) {
				graphToImport = importGraGra.getGraph(graphNames.get(0));
			}
		} else {
			graphToImport = importGraGra.getGraph();
		}			
			
		if (graphToImport != null && !graphToImport.getBasisGraph().isEmpty()) {			
			if ((importGraGra.getTypeGraph() != null)
					&& ((selGraGra.getTypeGraph() == null) 
							|| (selGraGra.getTypeGraph().getBasisGraph().isEmpty() 
									&& (selGraGra.getLevelOfTypeGraphCheck() == TypeSet.DISABLED)))) {
				if (importTypeGraph(importGraGra, selGraGra, path))
					doadapt = false;
			}	
			if (doadapt) {
				if (selGraGra.importGraph(graphToImport, doadapt)) {
					if (graphData != null) {
						graphData.setData(selGraGra.getGraph());
						if (!this.currentGraph.isTypeGraph()) {
							this.currentGraph = selGraGra.getGraph();
							this.currentGraph.getBasisGraph().setName(graphData.string());
						}
						fireTreeViewEvent(new TreeViewEvent(this,
									TreeViewEvent.IMPORT_GRAPH));
						return true;
					} 
					return false;
				} 
				fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ERROR));
				this.applFrame.getRootPane().revalidate();
				JOptionPane.showMessageDialog(this.applFrame,
								"Import has failed!",
										"", JOptionPane.ERROR_MESSAGE);
				return false;		
			} 
			else if (selGraGra.importGraph(importGraGra.getGraph())) {
				if (graphData != null) {
					graphData.setData(selGraGra.getGraph());
					if (!this.currentGraph.isTypeGraph()) {
						this.currentGraph = selGraGra.getGraph();
						this.currentGraph.getBasisGraph()
									.setName(graphData.string());
					}
					fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.IMPORT_GRAPH));
					return true;
				} 
				return false;
			} 
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
			this.applFrame.getRootPane().revalidate();
			JOptionPane.showMessageDialog(this.applFrame, 
							"<html><body>"
							+"Import has failed!"
							+ "\nPlease check the types of the import graph."
							+ "\nThe current type graph should be disabled before."
							+"</body></html>",
							"", JOptionPane.ERROR_MESSAGE);
			return false;		
		} 
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
		this.applFrame.getRootPane().revalidate();
		return false;			
	}

	private boolean importGraph(
			final EdGraph importGraph,
			final EdGraGra selGraGra) {
				
		if (selGraGra.addImportGraph(importGraph)) {
			EdGraph eg = selGraGra.getGraphs().lastElement();
			if (addGraph(selGraGra, eg)) {
				this.tree.treeDidChange();
				return true;
			}
		} 

		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
		this.applFrame.getRootPane().revalidate();
		return false;			
	}

	private boolean importAsRule(final EdGraGra importGraGra, final TreePath treepath) {
		TreePath path = treepath;
		if (path != null) {
			GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while ((data != null) && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else
					data = null;
			}
			if (data == null) {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Cannot import!<br>Please select a grammar first.",
						"", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			EdGraGra selGraGra = data.getGraGra();

			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));

			Vector<String> ruleNames = new Vector<String>();
			Vector<EdRule> rules = importGraGra.getRules();
			for (int i = 0; i < rules.size(); i++)
				ruleNames.add(rules.get(i).getName());

			ItemImportDialog rid = new ItemImportDialog(this.applFrame,
					"Rule to import", ruleNames);
			rid.setVisible(true);
			ruleNames = rid.getSelectedItemNames();
			Vector<String> failed = new Vector<String>();
			if (ruleNames.size() > 0) {
				selGraGra.getTypeSet().getBasisTypeSet().adaptTypes(
						importGraGra.getBasisGraGra().getTypes(), true);
				selGraGra.getTypeSet().refreshTypes(true);
				
//				List<Rule> set = new Vector<Rule>(); // test
				
				for (int i = 0; i < ruleNames.size(); i++) {
					String rname = ruleNames.get(i);
					EdRule er = importGraGra.getRule(importGraGra
							.getBasisGraGra().getRule(rname));
					EdRule newRule = importRule(er, selGraGra);
					if (newRule == null)
						failed.add(rname);
//					else
//						set.add(newRule.getBasisRule()); // test
				}
				
//				selGraGra.getBasisGraGra().addRuleSubset(set); // test
				
				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.IMPORT_RULE));
			}
			if (failed.size() > 0) {
				fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
				this.applFrame.getRootPane().revalidate();
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Import failed for rules:<br>" + failed.toString()+"</body></html>",
						"", JOptionPane.WARNING_MESSAGE);
			}
			return true;
		}
		return false;
	}

	private EdRule importRule(
			final EdRule er,
			final EdGraGra selGraGra) {
		if (er instanceof EdRuleScheme) {
			RuleScheme rs = BaseFactory.theFactory().cloneRuleScheme(
									(RuleScheme) er.getBasisRule(),
									selGraGra.getTypeSet().getBasisTypeSet());
			if (rs != null) {
				EdRuleScheme newRS = new EdRuleScheme(rs, selGraGra.getTypeSet());
				newRS.setLayoutByIndexFrom((EdRuleScheme) er);
				this.addRuleScheme(selGraGra, newRS);
				this.tree.treeDidChange();
				return newRS;
			}
		} 
		else {
			Rule r = BaseFactory.theFactory().cloneRule(er.getBasisRule(),
								selGraGra.getTypeSet().getBasisTypeSet(), true);
			if (r != null) {
				EdRule newRule = new EdRule(r, selGraGra.getTypeSet());
				if (this.addRule(selGraGra, newRule)) {
					newRule.setLayoutByIndexFrom(er);
					this.tree.treeDidChange();
					return newRule;
				}
			}
		}
		return null;
	}
	
	private boolean importAsGraphConstraints(final EdGraGra importGraGra, final TreePath treepath) {
		TreePath path = treepath;
		if (path != null) {
			GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
					.getLastPathComponent()).getUserObject();
			while ((data != null) && !data.isGraGra()) {
				TreePath parentPath = path.getParentPath();
				if (parentPath != null) {
					data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) parentPath
							.getLastPathComponent()).getUserObject();
					path = parentPath;
				} else
					data = null;
			}
			if (data == null) {
				JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Cannot import!<br>Please select a grammar first.",
						"", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			EdGraGra selGraGra = data.getGraGra();

			Object[] options = { "All Constraints", "Atomic Graph Constraints", "Cancel" };
			int answer = JOptionPane
					.showOptionDialog(this.applFrame,
							"\nPlease select what do you want to import.",
							"Import", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
			switch (answer) {
				case 0: importAllGraphConstraints(importGraGra, selGraGra); break;
				case 1: importAsAtomicGraphConstraints(importGraGra, path); break;			
			}
			return true;
		}
		return false;
	}
	
	private void importAllGraphConstraints(final EdGraGra importGraGra, final EdGraGra selGraGra) {
		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));

		selGraGra.getTypeSet().getBasisTypeSet().adaptTypes(
				importGraGra.getBasisGraGra().getTypeSet(), true);
		selGraGra.getTypeSet().refreshTypes(true);
		
		importGraphConstraints(importGraGra, selGraGra);
	}
	
	private void importAsAtomicGraphConstraints(final EdGraGra importGraGra, final TreePath path) {
		GraGraTreeNodeData data = (GraGraTreeNodeData) ((DefaultMutableTreeNode) path
				.getLastPathComponent()).getUserObject();	
		EdGraGra selGraGra = data.getGraGra();

		fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.TRY_IMPORT));

		Vector<String> names = importGraGra.getAtomicNames();
		ItemImportDialog rid = new ItemImportDialog(this.applFrame,
					"Atomic Graph Constraint to import", names);
		rid.setVisible(true);
		names = rid.getSelectedItemNames();
		Vector<String> failed = new Vector<String>();
		if (names.size() > 0) {
			selGraGra.getTypeSet().getBasisTypeSet().adaptTypes(
						importGraGra.getBasisGraGra().getTypes(), true);
			selGraGra.getTypeSet().refreshTypes(true);
				
			for (int i = 0; i < names.size(); i++) {
				String name = names.get(i);
				EdAtomic ac = importGraGra.getAtomic(name);
				EdAtomic newac = importGraphConstraint(ac, selGraGra);
				if (newac == null)
					failed.add(name);
			}
								
			fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.IMPORT_ATOMIC));
		}
		if (failed.size() > 0) {
			fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.ERROR));
			this.applFrame.getRootPane().revalidate();
			JOptionPane.showMessageDialog(this.applFrame,
						"<html><body>Import failed for atomic graph constraints:<br>" + failed.toString()+"</body></html>",
						"Import Failed", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private EdAtomic importGraphConstraint(final EdAtomic atom, final EdGraGra selGraGra) {
		AtomConstraint ac = BaseFactory.theFactory().cloneAtomConstraint(
											atom.getBasisAtomic(),
											selGraGra.getTypeSet().getBasisTypeSet());
		if (ac != null) {
			EdAtomic newAtom = new EdAtomic(ac, selGraGra.getTypeSet(), ac.getAtomicName());
			for (int j=0; j<atom.getBasisAtomic().getConclusionsSize(); j++) {
				newAtom.getConclusion(j).setLayoutByIndexFrom(atom.getConclusion(j)); 
			}
			if (this.addAtomic(selGraGra, newAtom)) {
				this.tree.treeDidChange();
//				fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.IMPORT_ATOMIC));
			}
			return newAtom;
		}
		return null;
	}
	
	private void importGraphConstraints(final EdGraGra importGraGra, final EdGraGra selGraGra) {
		List<EdAtomic> atoms = importGraGra.getAtomics();
		for (int i = 0; i < atoms.size(); i++) {
			EdAtomic atom = atoms.get(i);
//			EdAtomic newAtom = 
			importGraphConstraint(atom, selGraGra);
		}
		// import formulas
		List<EdConstraint> constraints = importGraGra.getConstraints();
		for (int i = 0; i < constraints.size(); i++) {
			EdConstraint co = constraints.get(i);
			Formula f = new Formula(selGraGra.getAtomicsAsEvaluable(), co.getAsIndxString());
			if (f.isValid()) {
				EdConstraint newco = new EdConstraint(f, co.getName());
				if (this.addConstraint(selGraGra, newco)) {
					this.tree.treeDidChange();
//				fireTreeViewEvent(new TreeViewEvent(this,
//						TreeViewEvent.IMPORT_CONSTRAINT));
				}
			}
		}	
	}
	
	private boolean importGraph(
			final EdGraGra importGraGra, 
			final EdGraGra selGraGra, 
			final TreePath path,
			final boolean adapt) {
		
		boolean result = false;	
		List<EdGraph> graphsToImport = new Vector<EdGraph>();
		Vector<String> graphNames = new Vector<String>();
		if (importGraGra.getGraphs().size() > 1) {
			Vector<EdGraph> graphs = importGraGra.getGraphs();
			for (int i = 0; i < graphs.size(); i++) {
				graphNames.add(graphs.get(i).getName());
			}
			GraphImportDialog gid = new GraphImportDialog(this.applFrame,
						"Graph to import", graphNames, false);
			gid.setVisible(true);
			graphNames = gid.getSelectedItemNames();
			if (!graphNames.isEmpty()) {
				for (int i=0; i<graphNames.size(); i++) {
					graphsToImport.add(importGraGra.getGraph(graphNames.get(i)));
				}
			}
		} else {
			graphsToImport.add(importGraGra.getGraph());
		}			
			
		for (int i=0; i<graphsToImport.size(); i++) {
			EdGraph graphToImport = graphsToImport.get(i);
			if (adapt) {
				if (selGraGra.addImportGraph(graphToImport, adapt)
						&& addGraph(selGraGra, selGraGra.getGraphs()
								.lastElement())) {
					int indx = selGraGra.getGraphs().size() - 1;
					if (selGraGra.getTypeGraph() != null) {
						indx++;
					}
					DefaultMutableTreeNode 
					pathComp = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) 
								path.getLastPathComponent()).getChildAt(indx);					
					((GraGraTreeNodeData) pathComp.getUserObject())
								.setData(selGraGra.getGraphs().lastElement());
					fireTreeViewEvent(new TreeViewEvent(this,
								TreeViewEvent.ADD_IMPORT_GRAPH));
					result = true;
				} 					
			} else if (selGraGra.addImportGraph(graphToImport)
					&& addGraph(selGraGra, selGraGra.getGraphs()
							.lastElement())) {
				int indx = selGraGra.getGraphs().size() - 1;
				if (selGraGra.getTypeGraph() != null) {
					indx++;
				}
				DefaultMutableTreeNode 
				pathComp = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) 
							path.getLastPathComponent()).getChildAt(indx);
				((GraGraTreeNodeData) pathComp.getUserObject())
							.setData(selGraGra.getGraphs().lastElement());
				fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.ADD_IMPORT_GRAPH));
				result = true;
			}
		}
		if (result) {
			if (selGraGra.getBasisGraGra().getTypeGraph() != null
					&& selGraGra.getTypeGraph() == null) {
				JOptionPane
					.showMessageDialog(this.applFrame,
							"<html><body> A new Type Graph of the current grammar is created."
							+" \n To make it available, please add a new Type Graph path "
							+"\n into tree view of the current grammar.",
							"New Type Graph", 
							JOptionPane.WARNING_MESSAGE);
					
			}
			return true;
		} 
			
		fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.ERROR));
		this.applFrame.getRootPane().revalidate();
		return false;	
	}

	/** Implements TransformEventListener.transformEventOccurred */
	public void transformEventOccurred(final TransformEvent e) {
		if (e.getMsg() == TransformEvent.APPLICABLE_RULES) {
			this.tree.treeDidChange();
		}
	}

	public void editEventOccurred(EditEvent e) {
		if (e.getMsg() == EditEvent.TRANSFER_SHORTKEY) {
			this.keyAdapter.performShortKeyEvent((KeyEvent) e.getObject(), false);
		} else if (e.getMsg() == EditEvent.MENU_KEY) {
			if (e.getMessage().equals("File"))
				this.file.doClick();
			else if (e.getMessage().equals("New GraGra"))
				executeCommand("newGraGra");
			else if (e.getMessage().equals("Open"))
				executeCommand("open");
			else if (e.getMessage().equals("Save"))
				executeCommand("save");
			else if (e.getMessage().equals("Save As"))
				executeCommand("saveAs");
			else if (e.getMessage().equals("exportGraphJPEG"))
				executeCommand("exportGraphJPEG");
			else if (e.getMessage().equals("exportGXL"))
				executeCommand("exportGXL");
			else if (e.getMessage().equals("exportGTXL"))
				executeCommand("exportGTXL");
			else if (e.getMessage().equals("importGGX"))
				executeCommand("importGGX");
			else if (e.getMessage().equals("importGXL"))
				executeCommand("importGXL");
			else if (e.getMessage().equals("importOMONDOXMI"))
				executeCommand("importOMONDOXMI");
			else if (e.getMessage().equals("Delete"))
				executeCommand("delete");
			else if (e.getMessage().equals("Type Graph"))
				executeCommand("newTypeGraph");
			else if (e.getMessage().equals("Graph"))
				executeCommand("newGraph");
			else if (e.getMessage().equals("Rule"))
				executeCommand("newRule");
			else if (e.getMessage().equals("New NAC"))
				executeCommand("newNAC");
			else if (e.getMessage().equals("New PAC"))
				executeCommand("newPAC");
			else if (e.getMessage().equals("Atomic Constraint"))
				executeCommand("newAtomic");
			else if (e.getMessage().equals("New Conclusion"))
				executeCommand("newConclusion");
			else if (e.getMessage().equals("Constraint"))
				executeCommand("newConstraint");
			else if (e.getMessage().equals("Set Layer"))
				setRuleLayer();
			else if (e.getMessage().equals("Reload"))
				reloadGraGra();
			else if (e.getMessage().equals("Quit"))
				executeCommand("exit");

		} else if (e.getMsg() == EditEvent.DATA_LOADED) {
			this.applFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (e.getMsg() == EditEvent.ATTR_CONDITION_CHANGED) {
			if (this.selPath == null) {
				return;
			}
			// EditEvent.ATTR_CONDITION_CHANGED "+e.getObject());
			if (e.getObject() instanceof EdAtomic) {
				DefaultMutableTreeNode atomicNode = (DefaultMutableTreeNode) this.selPath
						.getLastPathComponent();
				GraGraTreeNodeData sd = (GraGraTreeNodeData) atomicNode
						.getUserObject();
				if (sd.isAtomic()) {
					DefaultMutableTreeNode conclNode = (DefaultMutableTreeNode) atomicNode
							.getChildAt(0);
//					int row = this.tree.getRowForPath(this.selPath) + 1;
					// this.tree.collapseRow(row);
					showAtomicAttrConditions(conclNode);
					// this.tree.expandRow(row);
				} else if (sd.isConclusion()
						&& (sd.getConclusion() == (EdAtomic) e.getObject())) {
					// this.tree.collapsePath(this.selPath);
					showAtomicAttrConditions(atomicNode);
					// this.tree.expandPath(this.selPath);
				} else if (sd.isAttrCondition()) {
					TreePath atomicPath = this.selPath.getParentPath();
					atomicNode = (DefaultMutableTreeNode) atomicPath
							.getLastPathComponent();
					sd = (GraGraTreeNodeData) atomicNode.getUserObject();
					// this.tree.collapsePath(atomicPath);
					showAtomicAttrConditions(atomicNode);
					// this.tree.expandPath(atomicPath);
				} else {
					DefaultMutableTreeNode conclNode = this
							.getTreeNodeOfAtomicConclusion((EdAtomic) e
									.getObject());
					showAtomicAttrConditions(conclNode);
				}
			} else if (e.getObject() instanceof EdRule) {
				DefaultMutableTreeNode ruleNode = (DefaultMutableTreeNode) this.selPath
						.getLastPathComponent();
				TreePath rulePath = this.selPath;
				GraGraTreeNodeData sd = (GraGraTreeNodeData) ruleNode
						.getUserObject();
				if (sd.isRule()) {
					if (sd.getRule() == (EdRule) e.getObject()) {
						// this.tree.collapsePath(rulePath);
						showRuleAttrConditions(ruleNode);
						// this.tree.expandPath(rulePath);
					}
				} else if (sd.isAttrCondition()) {
					rulePath = this.selPath.getParentPath();
					ruleNode = (DefaultMutableTreeNode) rulePath
							.getLastPathComponent();
					sd = (GraGraTreeNodeData) ruleNode.getUserObject();
					// this.tree.collapsePath(rulePath);
					showRuleAttrConditions(ruleNode);
					// this.tree.expandPath(rulePath);
				} else if (sd.isNAC() || sd.isPAC() || sd.isNestedAC()) {
					rulePath = this.selPath.getParentPath();
					ruleNode = (DefaultMutableTreeNode) rulePath
							.getLastPathComponent();
					// this.tree.collapsePath(rulePath);
					showRuleAttrConditions(ruleNode);
					// this.tree.expandPath(rulePath);
				} else {
					ruleNode = this.getTreeNodeOfRule((EdRule) e.getObject());
					showRuleAttrConditions(ruleNode);
				}
			}
		} else if (e.getMsg() == EditEvent.RESET_GRAPH) {
			if (e.getObject() instanceof EdGraph)
				resetGraph((EdGraph) e.getObject());
			else
				resetGraph();
		} else if (e.getMsg() == EditEvent.SET_TYPE_GRAPH_ENABLED) {
			if (e.getObject() instanceof EdGraGra) {
				if (((EdGraGra) e.getObject()).getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX) {
					if (setLevelOfTypeGraphCheck(this.currentGraGra, TypeSet.ENABLED, false)) {					
						DefaultMutableTreeNode node = getTreeNodeOfGrammarElement(
								((EdGraGra) e.getObject()).getTypeGraph());
						updateTypeGraphTreeNode(node, (EdGraGra) e.getObject());
					}
				}
				else if (((EdGraGra) e.getObject()).getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX_MIN) {
					if (e.getMessage().equals(String.valueOf(TypeError.TO_MUCH_NODES))
							|| e.getMessage().equals(String.valueOf(TypeError.TO_MUCH_ARCS))) {
						if (setLevelOfTypeGraphCheck(this.currentGraGra, TypeSet.ENABLED, false)) {					
							DefaultMutableTreeNode node = getTreeNodeOfGrammarElement(
									((EdGraGra) e.getObject()).getTypeGraph());
							updateTypeGraphTreeNode(node, (EdGraGra) e.getObject());
						}
					}
					else if (e.getMessage().equals(String.valueOf(TypeError.TO_LESS_NODES))
							|| e.getMessage().equals(String.valueOf(TypeError.TO_LESS_ARCS))) {											
						if (setLevelOfTypeGraphCheck(this.currentGraGra, TypeSet.ENABLED_MAX, false)) {					
							DefaultMutableTreeNode node = getTreeNodeOfGrammarElement(
									((EdGraGra) e.getObject()).getTypeGraph());
							updateTypeGraphTreeNode(node, (EdGraGra) e.getObject());
						} else { // should not be happen
							if (setLevelOfTypeGraphCheck(this.currentGraGra, TypeSet.ENABLED, false)) {					
								DefaultMutableTreeNode node = getTreeNodeOfGrammarElement(
										((EdGraGra) e.getObject()).getTypeGraph());
								updateTypeGraphTreeNode(node, (EdGraGra) e.getObject());
							}
						}
					}
				} 
			}
		} else if (e.getMsg() == EditEvent.DELETE_RULE_REQUEST) {
			if (e.getObject() instanceof EdRule) {
				EdRule r = (EdRule) e.getObject();
			
				if (r.getBasisRule() instanceof AmalgamatedRule) {
					deleteAmalgamatedRule(getTreeNodeOfGrammarElement(r), false);
				} else if (r.getBasisRule() instanceof KernelRule
						|| r.getBasisRule() instanceof MultiRule) {
					EdRuleScheme rs = this.currentGraGra.getRuleScheme(r.getBasisRule());
					if (rs != null) {						
						this.tree.expandPath(this.getTreePathOfGrammarElement(rs));
						this.tree.treeDidChange();
						EdRule ru = rs.getAmalgamatedRule();
						if (ru != null) {
							DefaultMutableTreeNode delNode = getTreeNodeOfGrammarElement(ru);
							if (delNode != null)
								deleteAmalgamatedRule(getTreeNodeOfGrammarElement(ru), false);
						}
					}
				}
			}
		} else if (e.getMsg() == EditEvent.SHOW_RULE_SEQUENCE) {
			showRuleSequence();
		} else if (e.getMsg() == EditEvent.HIDE_RULE_SEQUENCE) {
			hideRuleSequence();
		}
		
	}
	
	public void setFlagForNew() {
		if (this.selPath != null) {
			final DefaultMutableTreeNode 
			aNode = (DefaultMutableTreeNode) this.selPath.getLastPathComponent();
			final GraGraTreeNodeData sd = (GraGraTreeNodeData) aNode.getUserObject();
			setFlagForNewData(sd);
		}
	}

	void setFlagForNewData(final GraGraTreeNodeData sd) {
		if (sd.isGraGra()) {
			this.newRuleOK = true;
			this.newApplCondOK = false;
		} else if (sd.isGraph()) {
			this.newRuleOK = false;
			this.newApplCondOK = false;
		} else if (sd.isRule()) {
			this.newApplCondOK = true;
			this.newRuleOK = false;
		} else if (sd.isNAC()) {
			this.newRuleOK = false;
			this.newApplCondOK = false;
		} else if (sd.isPAC()) {
			this.newRuleOK = false;
			this.newApplCondOK = false;
		} else if (sd.isNestedAC()) {
			this.newRuleOK = false;
			this.newApplCondOK = true;
		} else {
			this.newRuleOK = false;
			this.newApplCondOK = false;
		}
	}

	void setCurrentData(final TreePath path) {	
		Object graObj = this.currentGraGra;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();				
		int nn = path.getPath().length;
		if (nn == 2) { // GraGra
			setCurrentGraGra(node);
		}
		else if (nn == 3) { 
			// Graph || Rule || RuleScheme|| Atomic (AtomicGC) || Constraint(Formula || RuleSequence)
			if (setCurrentGraph(node)
					|| setCurrentRuleScheme(node)
					|| setCurrentRule(node)
					|| setCurrentAtomicGC(node)
					|| setCurrentFormula(node) // Constraint(Formula
					|| setCurrentRuleSequence(node))
				;//return;
		} else if (nn == 4) { // NAC || NestedApplCond || PAC || AttrCondition || AtomicGC Conclusion
			if (setCurrentNAC(node)
					|| setCurrentPAC(node)
					|| setCurrentNestedAC(node)
					|| setCurrentAttrCondition(node)
					|| setCurrentRulePostApplConstraint(node)
					|| setCurrentConclusionOfAtomicGC(node)
					
					|| setCurrentKernelRule(node)
					|| setCurrentMultiRule(node))
				;//return;
		} else if (nn == 5) {
			if (setCurrentNestedAC(node)
					|| setCurrentConclusionAttrCondition(node)
					// for kernel, multi rule
					|| setCurrentNAC(node)
					|| setCurrentPAC(node)
					|| setCurrentNestedAC(node)
					// Post Appl Cond of Rule
					|| setCurrentRuleAtomicPostApplCondition(node))
				;//return;
		} else if (nn > 5) {
			if (setCurrentNestedAC(node))
				;//return;
		}
		if (this.currentGraGra != graObj && this.currentGraGra != null)
			this.resetUndirectedArcProperty(!this.currentGraGra.getTypeSet().isArcDirected());
	}

	private void setCurrentGraGra(final DefaultMutableTreeNode node) { 
		this.currentGraGra = getGraGra(node);
		this.currentGraph = this.currentGraGra.getGraph();
		if (!this.currentGraGra.getRules().isEmpty()) {
			this.currentRule = this.currentGraGra.getRules().firstElement();
			DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
			if (rnode != null) {
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
				}
			}
		} else {
			this.currentRule = null;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
		}
		if (this.currentGraGra.getAtomics().size() != 0) {
			this.currentAtomic = this.currentGraGra.getAtomics().firstElement();
			if (this.currentAtomic.getConclusions().size() != 0) {
				this.currentConclusion = this.currentAtomic.getConclusions()
						.firstElement();
			} else
				this.currentConclusion = null; 
		} else {
			this.currentAtomic = null;
			this.currentConclusion = null;
		}
		this.currentConstraint = null;
		this.currentRuleConstraint = null;
		this.currentAtomApplCond = null;

//		isGraGra = true;

		this.layered = false;
		this.priority = false;
		if (this.currentGraGra.getBasisGraGra().isLayered()) {
			this.layered = true;
		}
		else if (this.currentGraGra.getBasisGraGra().trafoByPriority()) {
			this.priority = true;
		}
		this.treeModel.ruleNameChanged(getGraGra(), this.layered, this.priority);
		this.treeModel.constraintNameChanged(getGraGra(), this.layered, this.priority);
		this.tree.treeDidChange();
	}
	
	private boolean setCurrentGraph(final DefaultMutableTreeNode node) {
		EdGraph test = getGraph(node);
		if (test != null) {
			this.currentGraph = test;
			if (this.currentGraph.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentGraph.getGraGra();
				if (this.currentGraGra.getRules().size() != 0) {
					this.currentRule = this.currentGraGra.getRules().firstElement();
					DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
					if (rnode != null) {
						this.currentNAC = null;
						this.currentPAC = null;
						this.currentNestedAC = null;
						Enumeration<?> children = rnode.children();
						if (children.hasMoreElements()) {
							DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
									.nextElement();
							GraGraTreeNodeData csd = (GraGraTreeNodeData) child
									.getUserObject();
							if (csd.isNAC())
								this.currentNAC = csd.getNAC();
							else if (csd.isPAC())
								this.currentPAC = csd.getPAC();
						}
					}
				} else {
					this.currentRule = null;
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
				}
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null; 
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentRuleSequence(final DefaultMutableTreeNode node) {
		final RuleSequence testRuleSequence = getRuleSequence(node);
		if (testRuleSequence != null && this.currentGraGra != null) {
			if (testRuleSequence.getGraGra() == this.currentGraGra.getBasisGraGra()) {
				this.currentRuleSequence = testRuleSequence;	
				return true;
			}
		}
		return false;
	}
	
	private boolean setCurrentRule(final DefaultMutableTreeNode node) {
		EdRule testRule = getRule(node);
		if (testRule != null) {
			this.currentRule = testRule;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
			Enumeration<?> children = node.children();
			if (children.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
						.nextElement();
				GraGraTreeNodeData csd = (GraGraTreeNodeData) child
						.getUserObject();
				if (csd.isNAC())
					this.currentNAC = csd.getNAC();
				else if (csd.isPAC())
					this.currentPAC = csd.getPAC();
				else if (csd.isNestedAC())
					this.currentNestedAC = csd.getPAC();
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentRuleScheme(final DefaultMutableTreeNode node) {
		final EdRuleScheme testRuleScheme = getRuleScheme(node);
		if (testRuleScheme != null) {
			this.currentRuleScheme = testRuleScheme;	
			this.currentRule = null;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
			Enumeration<?> children = node.children();
			if (children.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
						.nextElement();
				GraGraTreeNodeData csd = (GraGraTreeNodeData) child
						.getUserObject();
				if (csd.isKernelRule())
					this.currentRule = csd.getKernelRule();
				else if (csd.isMultiRule())
					this.currentRule = csd.getMultiRule();
				else if (csd.isNAC())
					this.currentNAC = csd.getNAC();
				else if (csd.isPAC())
					this.currentPAC = csd.getPAC();
				else if (csd.isNestedAC())
					this.currentNestedAC = csd.getNestedAC();
			}
			if (this.currentRuleScheme.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRuleScheme.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null; 
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentKernelRule(final DefaultMutableTreeNode node) {
		EdRule testRule = getKernelRule(node);
		if (testRule != null) {
			this.currentRule = testRule;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
			Enumeration<?> children = node.children();
			if (children.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
						.nextElement();
				GraGraTreeNodeData csd = (GraGraTreeNodeData) child
						.getUserObject();
				if (csd.isNAC())
					this.currentNAC = csd.getNAC();
				else if (csd.isPAC())
					this.currentPAC = csd.getPAC();
				else if (csd.isNestedAC())
					this.currentNestedAC = csd.getNestedAC();
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}

	private boolean setCurrentMultiRule(final DefaultMutableTreeNode node) {
		EdRule testRule = getMultiRule(node);
		if (testRule != null) {
			this.currentRule = testRule;
			this.currentNAC = null;
			this.currentPAC = null;
			this.currentNestedAC = null;
			Enumeration<?> children = node.children();
			if (children.hasMoreElements()) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
						.nextElement();
				GraGraTreeNodeData csd = (GraGraTreeNodeData) child
						.getUserObject();
				if (csd.isNAC())
					this.currentNAC = csd.getNAC();
				else if (csd.isPAC())
					this.currentPAC = csd.getPAC();
				else if (csd.isNestedAC())
					this.currentNestedAC = csd.getNestedAC();
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
//			if (((MultiRule)this.currentRule.getBasisRule()).getRuleScheme().getKernelRule().hasChanged()) {				
//			}
			return true;
		}
		return false;
	}
	private boolean setCurrentAtomicGC(final DefaultMutableTreeNode node) {
		// test atomic graph constraint (AGC)
		EdAtomic testAtomic = getAtomic(node);
		if (testAtomic != null) {
			this.currentAtomic = testAtomic;
			if (this.currentAtomic.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentAtomic.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getRules().size() != 0) {
					this.currentRule = this.currentGraGra.getRules().firstElement();
					DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
					Enumeration<?> children = rnode.children();
					if (children.hasMoreElements()) {
						DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
								.nextElement();
						GraGraTreeNodeData csd = (GraGraTreeNodeData) child
								.getUserObject();
						if (csd.isNAC())
							this.currentNAC = csd.getNAC();
						else if (csd.isPAC())
							this.currentPAC = csd.getPAC();
						else if (csd.isNestedAC())
							this.currentNestedAC = csd.getNestedAC();
					}
				} else {
					this.currentRule = null;
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentConclusionOfAtomicGC(final DefaultMutableTreeNode node) {
		EdAtomic testConclusion = getConclusion(node);
		if (testConclusion != null) {
			this.currentConclusion = testConclusion;
			if (this.currentConclusion.getParent() != this.currentAtomic) {
				this.currentAtomic = this.currentConclusion.getParent();
			}
			if (this.currentConclusion.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentConclusion.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getRules().size() != 0) {
					this.currentRule = this.currentGraGra.getRules().firstElement();
					DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
					Enumeration<?> children = rnode.children();
					if (children.hasMoreElements()) {
						DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
								.nextElement();
						GraGraTreeNodeData csd = (GraGraTreeNodeData) child
								.getUserObject();
						if (csd.isNAC())
							this.currentNAC = csd.getNAC();
						else if (csd.isPAC())
							this.currentPAC = csd.getPAC();
						else if (csd.isNestedAC())
							this.currentNestedAC = csd.getNestedAC();
					}
				} else {
					this.currentRule = null;
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
//			isConclusion = true;
			// checkCurrentData();
			return true;
		}
		return false;
	}
	
	private boolean setCurrentConclusionAttrCondition(final DefaultMutableTreeNode node) {
		// test conclusion attr condition
		this.currentConclusionContext = getConclusionContext(node);
		if (this.currentConclusionContext != null) {
			if (this.currentConclusionContext.first != this.currentConclusion) {
				this.currentConclusion = this.currentConclusionContext.first;
			}
			if (this.currentConclusion.getParent() != this.currentAtomic) {
				this.currentConclusion = this.currentConclusionContext.first;
				this.currentAtomic = this.currentConclusion.getParent();
			}
			if (this.currentAtomic.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentAtomic.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getRules().size() != 0)
					this.currentRule = this.currentGraGra.getRules().firstElement();
				DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
					else if (csd.isNestedAC())
						this.currentNestedAC = csd.getNestedAC();
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentFormula(final DefaultMutableTreeNode node) {
		// test constraint (formula)
		EdConstraint testConstraint = getConstraint(node);
		if (testConstraint != null) {
			this.currentConstraint = testConstraint;
			if (this.currentConstraint.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentConstraint.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getRules().size() != 0) {
					this.currentRule = this.currentGraGra.getRules().firstElement();
					DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
					Enumeration<?> children = rnode.children();
					if (children.hasMoreElements()) {
						DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
								.nextElement();
						GraGraTreeNodeData csd = (GraGraTreeNodeData) child
								.getUserObject();
						if (csd.isNAC())
							this.currentNAC = csd.getNAC();
						else if (csd.isPAC())
							this.currentPAC = csd.getPAC();
						else if (csd.isNestedAC())
							this.currentNestedAC = csd.getNestedAC();
					}
				} else {
					this.currentRule = null;
					this.currentNAC = null;
					this.currentPAC = null;
					this.currentNestedAC = null;
				}
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null; 
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
//			isConstraint = true;
			// checkCurrentData();
			return true;
		}
		return false;
	}
	
	private boolean setCurrentNestedAC(final DefaultMutableTreeNode node) {
		EdPAC testAC = getNestedAC(node);
		if (testAC != null) {
			this.currentNestedAC = testAC;
			if (this.currentNestedAC.getRule() != this.currentRule) {
				this.currentRule = this.currentNestedAC.getRule();
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				this.currentNAC = null;
				this.currentPAC = null;
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}	
		return false;
	}
	
	private boolean setCurrentPAC(final DefaultMutableTreeNode node) {
		EdPAC testPAC = getPAC(node);
		if (testPAC != null) {
			this.currentPAC = testPAC;
			if (this.currentPAC.getRule() != this.currentRule) {
				this.currentRule = this.currentPAC.getRule();
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				this.currentNAC = null;
				this.currentNestedAC = null;
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}	
		return false;
	}
	
	private boolean setCurrentNAC(final DefaultMutableTreeNode node) {
		EdNAC testNAC = getNAC(node);
		if (testNAC != null) {
			this.currentNAC = testNAC;
			if (this.currentNAC.getRule() != this.currentRule)
				this.currentRule = this.currentNAC.getRule();
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				this.currentPAC = null;
				this.currentNestedAC = null;
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;
			}
			return true;
		}
		return false;
	}
	
	private boolean setCurrentAttrCondition(final DefaultMutableTreeNode node) {
		// test rule attr condition
		this.currentRuleContext = getRuleContext(node);
		if (this.currentRuleContext != null) {
			if (this.currentRuleContext.first != this.currentRule) {
				this.currentRule = this.currentRuleContext.first;
				DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
					else if (csd.isNestedAC())
						this.currentNestedAC = csd.getNestedAC();
				}
			}
			if (this.currentRuleContext.first.getGraGra() != this.currentGraGra) {
				this.currentRule = this.currentRuleContext.first;
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
					else if (csd.isNestedAC())
						this.currentNestedAC = csd.getNestedAC();
				}
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}

				this.currentConstraint = null;
				this.currentRuleConstraint = null;
				this.currentAtomApplCond = null;

			}
//			isAttrCondition = true;
			// checkCurrentData();
			return true;
		}
		return false;
	}
	
	private boolean setCurrentRulePostApplConstraint(final DefaultMutableTreeNode node) {
		// test rule post application constraint (PostAC)
		EdRuleConstraint testRuleConstraint = getRuleConstraint(node);
		if (testRuleConstraint != null) {
			this.currentRuleConstraint = testRuleConstraint;
			if (this.currentRuleConstraint.getRule() != this.currentRule) {
				this.currentRule = this.currentRuleConstraint.getRule();
				DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
					else if (csd.isNestedAC())
						this.currentNestedAC = csd.getNestedAC();
				}
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null; 
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
				this.currentAtomApplCond = null;
			}
//			isRuleConstraint = true;
			// checkCurrentData();
			return true;
		}
		return false;
	}
	
	private boolean setCurrentRuleAtomicPostApplCondition(final DefaultMutableTreeNode node) {
		// Atomic application condition ( child of PAC)
		this.currentAtomApplCond = getAtomApplCond(node);
		if (this.currentAtomApplCond != null) {
			if (this.currentAtomApplCond.getRule() != this.currentRule) {
				this.currentRule = this.currentAtomApplCond.getRule();
				DefaultMutableTreeNode rnode = getTreeNodeOfRule(this.currentRule);
				this.currentNAC = null;
				this.currentPAC = null;
				this.currentNestedAC = null;
				Enumeration<?> children = rnode.children();
				if (children.hasMoreElements()) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) children
							.nextElement();
					GraGraTreeNodeData csd = (GraGraTreeNodeData) child
							.getUserObject();
					if (csd.isNAC())
						this.currentNAC = csd.getNAC();
					else if (csd.isPAC())
						this.currentPAC = csd.getPAC();
					else if (csd.isNestedAC())
						this.currentNestedAC = csd.getNestedAC();
				}
				this.currentRuleConstraint = null;
			}
			if (this.currentRule.getGraGra() != this.currentGraGra) {
				this.currentGraGra = this.currentRule.getGraGra();
				this.currentGraph = this.currentGraGra.getGraph();
				if (this.currentGraGra.getAtomics().size() != 0) {
					this.currentAtomic = this.currentGraGra.getAtomics()
							.firstElement();
					if (this.currentAtomic.getConclusions().size() != 0) {
						this.currentConclusion = this.currentAtomic.getConclusions()
								.firstElement();
					} else
						this.currentConclusion = null;
				} else {
					this.currentAtomic = null;
					this.currentConclusion = null;
				}
				this.currentConstraint = null;
			}
			return true;
		}
		return false;
	}
	
	public EdGraGra getGraGra() {
		if (this.currentGraGra != null)
			return this.currentGraGra;
		else if (this.selPath == null) {
			if (this.top.getChildCount() == 1) {
				this.tree.setSelectionRow(1);
				this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
				setFlagForNew();
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"Please select a grammar.",
						"", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else if (this.top.getChildCount() == 1) {
			this.tree.setSelectionRow(1);
			this.selPath = this.tree.getPathForRow(this.tree.getMinSelectionRow());
			setFlagForNew();
		} else if (this.selPath.getPath().length != 2) {
			JOptionPane
					.showMessageDialog(this.applFrame, "Please select a grammar.",
							"", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return getGraGra((DefaultMutableTreeNode) this.selPath
				.getLastPathComponent());
	}

	void refreshGraGraRules(DefaultMutableTreeNode graNode) {
		GraGraTreeNodeData sd = (GraGraTreeNodeData) graNode.getUserObject();
		sd.getGraGra().getRules().removeAllElements();
		sd.getGraGra().getBasisGraGra().getListOfRules().clear();
		for (int i = 0; i < graNode.getChildCount(); i++) {
			DefaultMutableTreeNode elem = (DefaultMutableTreeNode) graNode
					.getChildAt(i);
			GraGraTreeNodeData sdElem = (GraGraTreeNodeData) elem
					.getUserObject();
			if (sdElem.isRule()) {
				sd.getGraGra().getRules().addElement(sdElem.getRule());
				sd.getGraGra().getBasisGraGra().getListOfRules().add(
						sdElem.getRule().getBasisRule());				
				sd.getGraGra().setChanged(true);
			}
		}
	}

	private void undoDelete() {
		this.gragraStore.showStorePalette();
	}

	public void undoDelete(final Object obj) {
		if (obj == null)
			return;
		
		if (obj instanceof EdNestedApplCond) {
			if (((EdNestedApplCond) obj).getParent() == null) {
				TreePath path =  getTreePathOfGrammarElement(((EdNestedApplCond) obj).getRule());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addNestedAC(((EdNestedApplCond) obj).getRule(), (EdNestedApplCond) obj)) {
					this.gragraStore.storeNestedAC(((EdNestedApplCond) obj).getRule(), (EdNestedApplCond) obj);
					return;
				}
			} else {
				TreePath path =  getTreePathOfGrammarElement(((EdNestedApplCond) obj).getParent());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addNestedAC(((EdNestedApplCond) obj).getParent(), (EdNestedApplCond) obj)) {			
					this.gragraStore.storeNestedAC(((EdNestedApplCond) obj).getParent(), (EdNestedApplCond) obj);
					return;
				}
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdNAC) {
			TreePath path =  getTreePathOfGrammarElement(((EdNAC) obj).getRule());
			if (path != null && this.selPath != path) {
				resetSelection(path);
			}
			if (!addNAC(((EdNAC) obj).getRule(), (EdNAC) obj)) {
				this.gragraStore.storeNAC(((EdNAC) obj).getRule(), (EdNAC) obj);
				return;
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdPAC) {
			TreePath path =  getTreePathOfGrammarElement(((EdPAC) obj).getRule());
			if (path != null && this.selPath != path) {
				resetSelection(path);
			}
			if (!addPAC(((EdPAC) obj).getRule(), (EdPAC) obj)) {
				this.gragraStore.storePAC(((EdPAC) obj).getRule(), (EdPAC) obj);
				return;
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdGraph) {
			TreePath path =  getTreePathOfGrammarElement(((EdGraph) obj).getGraGra());
			if (path != null && this.selPath != path) {
				resetSelection(path);
			}
			if (((EdGraph) obj).isTypeGraph()) {
				if (!addTypeGraph(((EdGraph) obj).getGraGra(), (EdGraph) obj)) {
					this.gragraStore.storeTypeGraph(((EdGraph) obj).getGraGra(),
							(EdGraph) obj);
					return;
				}
			} else {
				if (!addGraph(((EdGraph) obj).getGraGra(), (EdGraph) obj)) {
					this.gragraStore.storeGraph(((EdGraph) obj).getGraGra(),
							(EdGraph) obj);
					return;
				}
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdAtomic) {
			if (((EdAtomic) obj).getParent() == (EdAtomic) obj) {
				TreePath path =  getTreePathOfGrammarElement(((EdAtomic) obj).getGraGra());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addAtomic(((EdAtomic) obj).getGraGra(), (EdAtomic) obj)) {
					this.gragraStore.storeAtomConstraint(((EdAtomic) obj)
							.getGraGra(), (EdAtomic) obj);
					return;
				}
			} else {
				TreePath path =  getTreePathOfGrammarElement(((EdAtomic) obj).getParent());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addConclusion(((EdAtomic) obj).getParent(), (EdAtomic) obj)) {
					this.gragraStore.storeAtomConclusion(((EdAtomic) obj)
							.getParent(), (EdAtomic) obj);
					return;
				}
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdRule) {
			if (obj instanceof EdRuleScheme) {
				TreePath path =  getTreePathOfGrammarElement(((EdRuleScheme) obj).getGraGra());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addRuleScheme(((EdRuleScheme) obj).getGraGra(), (EdRuleScheme) obj)) {
					this.gragraStore.storeRuleScheme(((EdRuleScheme) obj).getGraGra(), (EdRuleScheme) obj);
					return;
				}
			} else {
				TreePath path =  getTreePathOfGrammarElement(((EdRule) obj).getGraGra());
				if (path != null && this.selPath != path) {
					resetSelection(path);
				}
				if (!addRule(((EdRule) obj).getGraGra(), (EdRule) obj)) {
					this.gragraStore.storeRule(((EdRule) obj).getGraGra(), (EdRule) obj);
					return;
				}
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		} else if (obj instanceof EdConstraint) {
			TreePath path =  getTreePathOfGrammarElement(((EdConstraint) obj).getGraGra());
			if (path != null && this.selPath != path) {
				resetSelection(path);
			}
			if (!addConstraint(((EdConstraint) obj).getGraGra(), (EdConstraint) obj)) {
				this.gragraStore.storeConstraint(((EdConstraint) obj).getGraGra(), (EdConstraint) obj);
				return;
			}
			fireTreeViewEvent(new TreeViewEvent(this,
					TreeViewEvent.UNDO_DELETE, ""));
		}
		this.tree.treeDidChange();
		if (this.gragraStore.isEmpty())
			this.trash.setEnabled(false);
	}

	private void resetSelection(final TreePath path) {
		this.tree.setSelectionPath(path);
		this.selPath = this.tree.getSelectionPath();
		setFlagForNew();
		this.editorPath = this.selPath;
		setCurrentData(this.editorPath);
		fireTreeViewEvent(new TreeViewEvent(
					this, TreeViewEvent.SELECTED,
					this.editorPath));
	}
	
	private void undoDeleteTypeGraph() {
		if (this.currentGraGra != null) {
			EdGraph g = this.gragraStore.getTypeGraph(this.currentGraGra);
			if (g != null) {
				addTypeGraph(this.currentGraGra, g);
				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	/*
	private void undoDeleteGraph() {
		if (this.currentGraGra != null) {
			EdGraph g = this.gragraStore.getGraph(this.currentGraGra);
			if (g != null) {
				addGraph(this.currentGraGra, g);
				fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
*/
	
	private void undoDeleteRule() {
		if (this.currentGraGra != null) {
			EdRule r = this.gragraStore.getRule(this.currentGraGra);
			if (r != null) {
				if (addRule(this.currentGraGra, r))
					fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void undoDeleteAtomicConstraint() {
		if (this.currentGraGra != null) {
			EdAtomic c = this.gragraStore.getAtomConstraint(this.currentGraGra);
			if (c != null) {
				if (addAtomic(this.currentGraGra, c))
					fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void undoDeleteConstraint() {
		if (this.currentGraGra != null) {
			EdConstraint c = this.gragraStore.getConstraint(this.currentGraGra);
			if (c != null) {
				if (addConstraint(this.currentGraGra, c))
					fireTreeViewEvent(new TreeViewEvent(this,
						TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a grammar.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void undoDeleteNAC() {
		if (this.currentRule != null) {
			EdNAC n = this.gragraStore.getNAC(this.currentRule);
			if (n != null) {
				addNAC(this.currentRule, n);
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void undoDeletePAC() {
		if (this.currentRule != null) {
			EdPAC p = this.gragraStore.getPAC(this.currentRule);
			if (p != null) {
				addPAC(this.currentRule, p);
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void undoDeleteNestedAC() {
		if (this.currentRule != null) {
			EdNestedApplCond p = this.gragraStore.getNestedAC(this.currentRule);
			if (p != null) {
				addNestedAC(this.currentRule, p);
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select a rule.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	
	private void undoDeleteAtomicConclusion() {
		if (this.currentAtomic != null) {
			EdAtomic c = this.gragraStore.getAtomConclusion(this.currentAtomic);
			if (c != null) {
				if (addConclusion(this.currentAtomic, c)) 
					fireTreeViewEvent(new TreeViewEvent(this,
							TreeViewEvent.UNDO_DELETE, ""));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Bad selection.\n Please select an atomic graph constraint.",
					"", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	public int removeCurrentObjectWarning(String obj) {
		String msgStr = "Do you want to delete the current " + obj + " ?";
		if (obj.equals("GraGra"))
			msgStr = "Do you want to close the current " + obj + " ?";
		Object[] options = { "YES", "NO" };
		int answer = JOptionPane.showOptionDialog(this.applFrame, msgStr, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}

	public int removeWarning(String obj) {
		String msgStr = "Do you want really to delete this  " + obj + " ?";
		Object[] options = { "YES", "NO" };
		int answer = JOptionPane.showOptionDialog(this.applFrame, msgStr, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}
	
	/*
	private int saveWarning(String obj) {
		Object[] options = { "SAVE", "CANCEL" };
		int answer = JOptionPane.showOptionDialog(null, "This " + obj
				+ " has been changed. \nDo you want to save it ?", "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}

	private int saveWarning(String obj, String name) {
		Object[] options = { "SAVE", "CANCEL" };
		int answer = JOptionPane.showOptionDialog(this.applFrame, 
				"The " + obj +"<"+name+">"
				+ " has been changed. \nDo you want to save it ?", "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}
	*/
	private int exitWarning(Object[] options, String obj, String name) {
//		Object[] options = { "SAVE", "EXIT", "CANCEL" };
		int answer = JOptionPane.showOptionDialog(this.applFrame, 
				"The " + obj +"<"+name+">"
				+ " has been changed. \nDo you want to save it before?", " Quit AGG ",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[0]);
		return answer;
	}
	
	private int changedGraGraWarning(String action) {
		Object[] options = { action, "CANCEL" };
		String act = action.toLowerCase();
		if (action.equals("RELOAD"))
			act = "rewrite";
		int answer = JOptionPane.showOptionDialog(this.applFrame,
				"This grammar has been changed.\nDo you want to " + act
						+ " the grammar really?", "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}

	private void createMenus() {
		this.file.setMnemonic('F');

		JMenuItem mi = this.file.add(new JMenuItem("New GraGra            Ctrl+N"));
		mi.setEnabled(true);
		mi.setMnemonic('N');
		mi.setActionCommand("newGraGra");
		mi.addActionListener(this.actionAdapter);

		this.file.addSeparator();

		mi = this.file.add(new JMenuItem("Open                      Ctrl+O"));
		mi.setEnabled(true);
		mi.setMnemonic('O');
		mi.setActionCommand("open");
		mi.addActionListener(this.actionAdapter);

		mi = this.file.add(new JMenuItem("Save                       Ctrl+S"));
		mi.setEnabled(false);
		mi.setMnemonic('S');
		mi.setActionCommand("save");
		mi.addActionListener(this.actionAdapter);

		mi = this.file.add(new JMenuItem("Save As                   Alt+S"));
		mi.setEnabled(false);
		mi.setMnemonic(KeyEvent.VK_A);
		mi.setDisplayedMnemonicIndex(5);
		mi.setActionCommand("saveAs");
		mi.addActionListener(this.actionAdapter);

		this.file.addSeparator();

		mi = this.file.add(new JMenuItem("Open (Base)"));
		mi.setActionCommand("openBase");
		mi.addActionListener(this.actionAdapter);

		mi = this.file.add(new JMenuItem("Save As (Base)"));
		mi.setEnabled(false);
		mi.setActionCommand("saveAsBase");
		mi.addActionListener(this.actionAdapter);

		this.file.addSeparator();

		JMenu subm = (JMenu) this.file.add(new JMenu("Export"));
		subm.setEnabled(false);
		subm.setMnemonic('x');

		mi = subm.add(new JMenuItem("JPEG         Shift+J"));
		mi.setEnabled(true);
		mi.setMnemonic('J');
		mi.setActionCommand("exportGraphJPEG");
		mi.addActionListener(this.actionAdapter);

		mi = subm.add(new JMenuItem("GXL          Shift+X"));
		mi.setEnabled(true);
		mi.setMnemonic('X');
		mi.setActionCommand("exportGXL");
		mi.addActionListener(this.actionAdapter);

		mi = subm.add(new JMenuItem("GTXL        Shift+T"));
		mi.setEnabled(true);
		mi.setMnemonic('T');
		mi.setActionCommand("exportGTXL");
		mi.addActionListener(this.actionAdapter);

		subm = (JMenu) this.file.add(new JMenu("Import"));
		subm.setEnabled(true);
		subm.setMnemonic('I');

		mi = subm.add(new JMenuItem("GGX                         Shift+Alt+G"));
		mi.setEnabled(true);
		mi.setMnemonic('G');
		mi.setActionCommand("importGGX");
		mi.addActionListener(this.actionAdapter);

		mi = subm
				.add(new JMenuItem("GXL                          Shift+Alt+X"));
		mi.setEnabled(true);
		mi.setMnemonic('X');
		mi.setActionCommand("importGXL");
		mi.addActionListener(this.actionAdapter);

		// mi = (JMenuItem) subm.add(new JMenuItem("GTXL"));
		mi = new JMenuItem("GTXL");
		mi.setEnabled(false);
		mi.setMnemonic('T');
		mi.setActionCommand("importGTXL");
		mi.addActionListener(this.actionAdapter);

		mi = subm.add(new JMenuItem("OMONDO XMI ( .ecore )   Shift+Alt+O"));
		mi.setEnabled(true);
		mi.setMnemonic('O');
		mi.setActionCommand("importOMONDOXMI");
		mi.addActionListener(this.actionAdapter);

		this.file.addSeparator();

		mi = this.file.add(new JMenuItem("Close GraGra           Ctrl+W"));
		mi.setEnabled(false);
		mi.setMnemonic('C');
		mi.setActionCommand("deleteGraGra");
		mi.addActionListener(this.actionAdapter);

		this.file.addSeparator();

		mi = this.file.add(new JMenuItem("AGG View to JPEG"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((AGGAppl) GraGraTreeView.this.applFrame).exportAppl2JPEG();
			}
		});

		this.file.addSeparator();
		
		mi = this.file.add(new JMenuItem("Quit                        Ctrl+Q"));
		mi.setActionCommand("exit");
		mi.setMnemonic('Q');
		mi.addActionListener(this.actionAdapter);

		this.menus.addElement(this.file);
	}
	
	private void resetEnabledOfFileMenuItems(String command) {
		if (command.equals("newGraGra") || command.equals("open")
				|| command.equals("importGXL")) {
			for (int i = 1; i < this.file.getItemCount() - 1; i++) {
				if (this.file.getItem(i) != null) {
					JMenuItem mi = this.file.getItem(i);
					mi.setEnabled(true);
				}
			}
		} else if (command.equals("delete") || command.equals("deleteGraGra")) {
			if (this.tree.getRowCount() == 1) {
				for (int i = 1; i < this.file.getItemCount() - 1; i++) {
					if (this.file.getItem(i) instanceof JMenu) {
						JMenu m = (JMenu) this.file.getItem(i);
						if (m.getText().startsWith("Import"))
							m.setEnabled(true);
						else
							m.setEnabled(false);

					} else if (this.file.getItem(i) != null) {
						JMenuItem mi = this.file.getItem(i);
//						System.out.println(mi.getText());
						if (mi.getText().startsWith("Open")
								|| mi.getText().startsWith("Open (Base)"))
							mi.setEnabled(true);
						else
							mi.setEnabled(false);
					}
				}
			}
		} else if (command.equals("print")) {
		}
	}

	private void createToolBar() {
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewGraGraIcon",
				"New GraGra", "newGraGra", this.actionAdapter, true));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("imageable", "open", "Open", "open",
				this.actionAdapter, true));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("imageable", "save", "Save", "save",
				this.actionAdapter, false));
		this.toolBar.addSeparator(); this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewTypeGraphIcon",
				"New Type Graph", "newTypeGraph", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewGraphIcon",
				"New Graph", "newGraph", this.actionAdapter, false));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewRuleIcon",
				"New Rule", "newRule", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		
		this.newNestedAC = this.toolBar.createTool("iconable", "NewNestedACIcon",
				"New General Application Condition", "newNestedAC", this.actionAdapter, false);
		this.indxNewNestedAC = this.toolBar.getComponentCount();		
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewNACIcon", 
				"New Negative Application Condition",
				"newNAC", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewPACIcon", 
				"New Positive Application Condition",
				"newPAC", this.actionAdapter, false));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewAtomicIcon",
				"New Atomic Graph Constraint", "newAtomic", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewConclusionIcon",
				"New Conclusion of Atomic Graph Constraint", "newConclusion", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "NewConstraintIcon",
				"New Graph Constraint", "newConstraint", this.actionAdapter, false));
		this.toolBar.addSeparator(); this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteGraGraIcon",
				"Delete GraGra", "deleteGraGra", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteTypeGraphIcon",
				"Delete Type Graph", "deleteTypeGraph", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteGraphIcon",
				"Delete Graph", "deleteGraph", this.actionAdapter, false));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteRuleIcon",
				"Delete Rule", "deleteRule", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.delNestedAC = this.toolBar.createTool("iconable", "DeleteNestedACIcon",
				"Delete General Application Condition", "deleteNestedAC", this.actionAdapter, false);
		this.indxDelNestedAC = this.toolBar.getComponentCount();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteNACIcon",
				"Delete Negative Application Condition", "deleteNAC", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeletePACIcon",
				"Delete Positive Application Condition", "deletePAC", this.actionAdapter, false));
		this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteAtomicIcon",
				"Delete Atomic Graph Constraint", "deleteAtomic", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteConclusionIcon",
				"Delete Conclusion of Atomic Graph Constraint", "deleteConclusion", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeleteConstraintIcon",
				"Delete Graph Constraint", "deleteConstraint", this.actionAdapter, false));
		this.toolBar.addSeparator();this.toolBar.addSeparator();
		this.toolBar.addTool(this.trash);
		this.gragraStore.setTrash(this.trash);
		this.toolBar.addSeparator(); this.toolBar.addSeparator();
		this.toolBar.addTool(this.toolBar.createTool("imageable", "print",
						"Export GraGra Editor View to JPEG", "exportJPEG",
						this.actionAdapter, false));
		this.toolBar.addSeparator();
	}

	public void extendToolBar(final JButton b) {
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(b);
	}
	
	private void resetEnabledOfToolBarItems(String command) {
		if (command.equals("newGraGra")
				|| (command.equals("open") && (this.tree.getRowCount() > 1))) {
			for (int i = 4; i < this.toolBar.getComponentCount(); i++) {
				if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
					JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
					b.setEnabled(true);

					if (b.getIcon() instanceof NewTypeGraphIcon)
						((NewTypeGraphIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewGraphIcon)
						((NewGraphIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewRuleIcon)
						((NewRuleIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewNestedACIcon)
						((NewNestedACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewNACIcon)
						((NewNACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewPACIcon)
						((NewPACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewAtomicIcon)
						((NewAtomicIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewConclusionIcon)
						((NewConclusionIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof NewConstraintIcon)
						((NewConstraintIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteGraGraIcon)
						((DeleteGraGraIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteTypeGraphIcon)
						((DeleteTypeGraphIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteGraphIcon)
						((DeleteGraphIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteRuleIcon)
						((DeleteRuleIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteNestedACIcon)
						((DeleteNestedACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteNACIcon)
						((DeleteNACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeletePACIcon)
						((DeletePACIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteAtomicIcon)
						((DeleteAtomicIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteConclusionIcon)
						((DeleteConclusionIcon) b.getIcon()).setEnabled(true);
					else if (b.getIcon() instanceof DeleteConstraintIcon)
						((DeleteConstraintIcon) b.getIcon()).setEnabled(true);
				}
			}
		} else if (// command.equals("delete") ||
		command.equals("deleteGraGra")) {
			if (this.tree.getRowCount() == 1) {
				for (int i = 4; i < this.toolBar.getComponentCount(); i++) {
					if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
						JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
						b.setEnabled(false);

						if (b.getIcon() instanceof NewTypeGraphIcon)
							((NewTypeGraphIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewGraphIcon)
							((NewGraphIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewRuleIcon)
							((NewRuleIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewNestedACIcon)
							((NewNestedACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewNACIcon)
							((NewNACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewPACIcon)
							((NewPACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewAtomicIcon)
							((NewAtomicIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewConclusionIcon)
							((NewConclusionIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof NewConstraintIcon)
							((NewConstraintIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteGraGraIcon)
							((DeleteGraGraIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteTypeGraphIcon)
							((DeleteTypeGraphIcon) b.getIcon())
									.setEnabled(false);
						else if (b.getIcon() instanceof DeleteGraphIcon)
							((DeleteGraphIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteRuleIcon)
							((DeleteRuleIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteNestedACIcon)
							((DeleteNestedACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteNACIcon)
							((DeleteNACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeletePACIcon)
							((DeletePACIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteAtomicIcon)
							((DeleteAtomicIcon) b.getIcon()).setEnabled(false);
						else if (b.getIcon() instanceof DeleteConclusionIcon)
							((DeleteConclusionIcon) b.getIcon())
									.setEnabled(false);
						else if (b.getIcon() instanceof DeleteConstraintIcon)
							((DeleteConstraintIcon) b.getIcon())
									.setEnabled(false);
					}
				}
			}
		}
		if (this.gragraStore.isEmpty())
			this.trash.setEnabled(false);
		else
			this.trash.setEnabled(true);
	}

	public void resetFileIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getActionCommand().equals("open")
						|| b.getActionCommand().equals("save")) {
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewGraGraIcon) {
					((NewGraGraIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewTypeGraphIcon) {
					((NewTypeGraphIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewGraphIcon) {
					((NewGraphIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewRuleIcon) {
					((NewRuleIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewNestedACIcon) {
					((NewNestedACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewNACIcon) {
					((NewNACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewPACIcon) {
					((NewPACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewAtomicIcon) {
					((NewAtomicIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewConclusionIcon) {
					((NewConclusionIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof NewConstraintIcon) {
					((NewConstraintIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteGraGraIcon) {
					((DeleteGraGraIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteTypeGraphIcon) {
					((DeleteTypeGraphIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteGraphIcon) {
					((DeleteGraphIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteRuleIcon) {
					((DeleteRuleIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteNestedACIcon) {
					((DeleteNestedACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteNACIcon) {
					((DeleteNACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeletePACIcon) {
					((DeletePACIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteAtomicIcon) {
					((DeleteAtomicIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteConclusionIcon) {
					((DeleteConclusionIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeleteConstraintIcon) {
					((DeleteConstraintIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				}
			}
		}
		if (this.gragraStore.isEmpty())
			this.trash.setEnabled(false);
		else
			this.trash.setEnabled(true);
	}

	public void setExportJPEG(final GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
	}

	public GraphicsExportJPEG getGraphicsExportJPEG() {
		return this.exportJPEG;
	}
	
	private void saveJPEG() {
		this.exportJPEG.setDirectory(this.directory);
		((AGGAppl) this.applFrame).exportJPEG();
	}

	public void setNodeTypeForColorGraph(final EdType type) {
		this.nodeTypeOfColorGraph = type;
	}
	
	public void setEdgeTypeForColorGraph(final EdType type) {
		this.edgeTypeOfColorGraph = type;
	}
	
	public EdType getNodeTypeForColorGraph() {
		return this.nodeTypeOfColorGraph;
	}
	
	public EdType getEdgeTypeForColorGraph() {
		return this.edgeTypeOfColorGraph;
	}
		
	public void enableNestedApplCond(boolean b) {
		if (b) {
			this.rulePopupMenu.enableNestedApplCond(b);
			
			this.toolBar.addTool(this.newNestedAC, this.indxNewNestedAC);
			this.toolBar.addSeparator(new Dimension(3, 3), this.indxNewNestedAC+1);
			this.toolBar.addTool(this.delNestedAC, this.indxDelNestedAC+2);
			this.toolBar.addSeparator(new Dimension(3, 3), this.indxDelNestedAC+3);
		}
	}
	
	protected final JFrame applFrame;

	private final GraGraTreeViewKeyAdapter keyAdapter;
	
	private final GraGraTreeViewMouseAdapter mouseAdapter;
	
	private final TreeViewActionAdapter actionAdapter;
	
	protected String msg;

	private final Vector<JMenu> menus = new Vector<JMenu>();

	private final JMenu file;

	private final AGGToolBar toolBar;

	private final GraGraSave gragraSave;

	private final GraGraLoad gragraLoad;

	private final Vector<TreeViewEventListener> 
	treeEventListeners = new Vector<TreeViewEventListener>();

	private String directory = "";

	protected EdGraGra currentGraGra;

	protected RuleSequence currentRuleSequence;
	
	private EdGraph currentGraph;

	protected EdRule currentRule;

	protected EdRuleScheme currentRuleScheme;
	
	private EdNAC currentNAC;

	private EdPAC currentPAC, currentNestedAC;

	private EdAtomic currentAtomic;

	private EdAtomic currentConclusion;

	private EdConstraint currentConstraint;

	private EdRuleConstraint currentRuleConstraint;

	private EdAtomApplCond currentAtomApplCond;

	private Pair<EdRule, Vector<String>> currentRuleContext;
	
	private Pair<EdAtomic, Vector<String>> currentConclusionContext;

	protected final FilePopupMenu filePopupMenu;

	protected final ConstraintPopupMenu constraintPopupMenu;

	protected final AtomicPopupMenu atomicPopupMenu;

	protected final ConclusionPopupMenu conclusionPopupMenu;

	protected final RuleConstraintPopupMenu ruleConstraintPopupMenu;

	protected final AtomApplCondPopupMenu atomApplCondPopupMenu;

	protected final GraGraPopupMenu gragraPopupMenu;

	protected final RulePopupMenu rulePopupMenu;
	protected final AmalgamRulePopupMenu amalgamRulePopupMenu;
	
	protected final RuleSchemePopupMenu ruleSchemePopupMenu;
	protected final KernelRulePopupMenu kernRulePopupMenu;
	protected final MultiRulePopupMenu multiRulePopupMenu;
	
	protected final RuleSequencePopupMenu ruleSequencePopupMenu;
	
	protected final NACPopupMenu nacPopupMenu;

	protected final PACPopupMenu pacPopupMenu;

	protected final NestedACPopupMenu acPopupMenu;
	
	protected final TypeGraphPopupMenu typeGraphPopupMenu;

	protected final GraphPopupMenu graphPopupMenu;

	protected final AttrConditionPopupMenu attrConditionPopupMenu;

	protected final ApplFormulaPopupMenu applFormulaPopupMenu;
	
	protected Point popupLocation;

	protected final JTree tree;

	protected final GraGraTreeModel treeModel;

	private final DefaultMutableTreeNode top;

	public TreePath selPath; // Path clicked with the mouse

	public TreePath editorPath; // Path underlayed with selection color

	protected Rectangle movedRect;

	protected DefaultMutableTreeNode movedNode;

	protected Point movedPoint = new Point(0, 0);

	private boolean newRuleOK = false;
	private boolean newApplCondOK = false;

	protected boolean pressedMouseLeft = false;
    	
	protected boolean wasMoved = false;

	protected DefaultMutableTreeNode tmpSelNode;

	protected TreePath tmpSelPath;

	protected boolean isSelected = false;

	public boolean layered = false, priority = false;
	
	boolean ruleSequence = false, ruleSequenceHidden = false;

	private boolean rewriteTypeGraph = false;
	private boolean undirectedArcs, nonparallelArcs, checkEmptyAttrs;
	
	protected final GraGraElementsStore gragraStore;

	protected final JButton trash;
	protected JButton newNestedAC, delNestedAC;
	protected int indxNewNestedAC, indxDelNestedAC;
	
	private GraGraTextualComment comments;

	private GraphicsExportJPEG exportJPEG;
	
	private EdType nodeTypeOfColorGraph;
	private EdType edgeTypeOfColorGraph;
	
}
