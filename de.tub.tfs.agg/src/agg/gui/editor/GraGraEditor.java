package agg.gui.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter; 
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.border.TitledBorder;

//import agg.attribute.AttrContext;
import agg.attribute.AttrTuple;
import agg.attribute.AttrVariableTuple;
import agg.attribute.facade.impl.DefaultEditorFacade;
import agg.attribute.gui.AttrTopEditor;
import agg.attribute.gui.AttrTupleEditor;
import agg.attribute.gui.impl.BasicTupleEditor;
import agg.attribute.gui.impl.TopEditor;
import agg.attribute.gui.impl.TupleTableModel;
import agg.attribute.view.impl.MaskedViewSetting;
import agg.attribute.view.impl.OpenViewSetting;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.CondMember;
import agg.cons.AtomApplCond;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdAtomApplCond;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdRuleScheme;
import agg.editor.impl.EdType;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdPAC;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.editor.impl.EditUndoManager;
import agg.gui.AGGAppl;
import agg.gui.AGGToolBar;
import agg.gui.animation.NodeAnimation;
import agg.gui.cons.TwoMorphs;
import agg.gui.event.EditEvent;
import agg.gui.event.EditEventListener;
import agg.gui.event.TransformEvent;
import agg.gui.event.TransformEventListener;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.event.TypeEvent;
import agg.gui.event.TypeEventListener;
import agg.gui.icons.CompletionIcon;
import agg.gui.icons.DeselectAllIcon;
import agg.gui.icons.IdenticNestedACIcon;
import agg.gui.icons.MatchIcon;
import agg.gui.icons.SelectAllIcon;
import agg.gui.icons.SelectArcTypeIcon;
import agg.gui.icons.SelectNodeTypeIcon;
import agg.gui.icons.StartIcon;
import agg.gui.icons.StepIcon;
import agg.gui.icons.StopIcon;
import agg.gui.icons.StepBackIcon;
import agg.gui.icons.TextIcon;
import agg.gui.options.GraTraMatchOptionGUI;
import agg.gui.options.GraTraOptionGUI;
import agg.gui.options.GraphLayouterOptionGUI;
import agg.gui.options.OptionGUI;
import agg.gui.popupmenu.EditPopupMenu;
import agg.gui.popupmenu.EditSelPopupMenu;
import agg.gui.popupmenu.ModePopupMenu;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.gui.trafo.GraGraTransform;
import agg.gui.trafo.TransformInterpret;
import agg.gui.trafo.TransformLayered;
import agg.gui.trafo.TransformRuleSequences;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.gui.typeeditor.ArcTypePropertyEditor;
import agg.gui.typeeditor.NodeTypePropertyEditor;
import agg.gui.typeeditor.TypeEditor;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Graph;
import agg.xt_basis.Type;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.agt.AmalgamatedRule;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.xt_basis.agt.RuleScheme;
import agg.cons.AtomConstraint;
import agg.layout.GraphLayouts;
import agg.layout.evolutionary.LayoutMetrics;
import agg.layout.evolutionary.EvolutionaryGraphLayout;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;

/**
 * This class defines an editor for editing grammars. A grammar is an instance
 * of the class EdGraGra. The editor contains: type editor, rule editor and
 * graph editor. It provides menus: Edit, Mode, Transform.
 * 
 * @author $Author: olga $
 * @version $ID:$
 */
public class GraGraEditor extends JPanel implements TreeModelListener,
		TableModelListener, TreeViewEventListener, TransformEventListener,
		TypeEventListener {

	protected GraphLayouts graphLayouts;

	protected final EvolutionaryGraphLayout evolutionaryLayouter = new EvolutionaryGraphLayout(
			100, null);

	private final GraphLayouterOptionGUI evolutionaryLayouterOptionGUI = new GraphLayouterOptionGUI(
			this.evolutionaryLayouter);

	private final Hashtable<Object, Integer> dividerLocationSet = new Hashtable<Object, Integer>();

	private final TwoMorphs pacMorphs = new TwoMorphs();

	private final ModePopupMenu modePopupMenu = new ModePopupMenu();

	private final EditPopupMenu editPopupMenu = new EditPopupMenu();

	private final EditSelPopupMenu editSelPopupMenu = new EditSelPopupMenu();

	private final Vector<JMenu> mainMenus = new Vector<JMenu>();

	private final JMenu edit = new JMenu("Edit", true);

	private final JMenu mode = new JMenu("Mode", true);

	private final JMenu transform = new JMenu("Transform", true);;

	private final Vector<JButton> editModeButtons = new Vector<JButton>(6);

	private final Vector<JButton> transformButtons = new Vector<JButton>(6);

	private final Vector<EditEventListener> editEventListeners = new Vector<EditEventListener>();

	private final AGGToolBar toolBar = new AGGToolBar(0); // horizontal

	protected final GraGraEditorActionAdapter actionAdapter;

	protected final MouseListener ml;

	private final GraGraEditorKeyAdapter keyAdapter;

	private boolean scaleGraphOnly = true;

	private final JButton buttonIdenticNAC, buttonIdenticPAC, buttonIdenticNestedAC;
	private final JButton buttonMatch, buttonCompletion;
	private final JButton buttonStep, buttonStart, buttonStop, buttonUndoStep, buttonT;
	protected final JButton buttonUndo, buttonRedo;
	protected final JButton buttonLayout, buttonLayoutMenu;
	protected final Dimension freeSpace = new Dimension(20, 20);
    private int indxIdenticNestedAC;
    
	private final Color bgc = new Color(238, 238, 238); // JButton Background

	/**
	 * Creates a gragra editor containing type, rule, graph, attribute, input
	 * parameter editors and transformation component
	 */
	public GraGraEditor(JFrame aggappl) {
		super(new BorderLayout());

		this.applFrame = aggappl;

		this.keyAdapter = new GraGraEditorKeyAdapter(this);

		this.ml = new GraGraEditorMouseAdapter(this);

		this.actionAdapter = new GraGraEditorActionAdapter(this);

		/* create Edit, Mode, Transform menus */
		createMainMenus();

		this.buttonIdenticNAC = this.toolBar.createTool("textable", "IN",
				"Identic NAC (Negative Application Condition)", "identicNAC",
				this.actionAdapter, false);

		this.buttonIdenticPAC = this.toolBar.createTool("textable", "IP",
				"Identic PAC (Positive Application Condition)", "identicPAC",
				this.actionAdapter, false);

		this.buttonIdenticNestedAC = this.toolBar.createButton(new IdenticNestedACIcon("IGAC", false),
				"Identic GAC (General Application Condition)", "identicAC",
				this.actionAdapter, false);
		
//		buttonIdenticNestedAC = toolBar.createTool("textable", "IAC",
//				"Identic GAC (General Application Condition)", "identicAC",
//				this.actionAdapter, false);
		
		this.buttonMatch = this.toolBar.createTool("iconable", "MatchIcon",
				"Interactive match mode", "match", this.actionAdapter, false);

		this.buttonCompletion = this.toolBar.createTool("iconable", "CompletionIcon",
				"Next Completion", "completion", this.actionAdapter, false);

		this.buttonStep = this.toolBar.createTool("iconable", "StepIcon",
				"Transformation Step", "step", this.actionAdapter, false);

		this.buttonStart = this.toolBar.createTool("iconable", "StartIcon",
				"Start Transformation", "start", this.actionAdapter, false);

		this.buttonStop = this.toolBar.createTool("iconable", "StopIcon",
				"Stop Transformation", "stop", this.actionAdapter, false);

		this.buttonUndoStep = this.toolBar.createTool("iconable", "StepBackIcon",
				"Undo Step", "undoStep", this.actionAdapter, false);

		this.buttonT = this.toolBar
				.createTool(
						"textable",
						"NT",
						"Transformation by applying rules non-deterministically (default)",
						"", this.actionAdapter, false);

		this.buttonUndo = this.toolBar.createTool("imageable", "undo16x16",
				"Undo Last Edit", "undo", this.actionAdapter, false);

		this.buttonRedo = this.toolBar.createTool("imageable", "redo16x16",
				"Redo Last Edit", "redo", this.actionAdapter, false);

		this.buttonLayout = this.toolBar.createTool("imageable", "layout",
				" repeatedly graph layout ", "graphlayout", this.actionAdapter,
				true);
		this.buttonLayoutMenu = this.toolBar.createTool("imageable", "layoutmenu16x16",
				" Graph Layout Algorithm menu ", "graphlayoutmenu",
				this.actionAdapter, true);
		this.buttonLayoutMenu.addMouseListener(this.ml);

		/* create tool bar */
		fillToolBar();

		/* create editors */
		this.typeEditor = new TypeEditor(aggappl, this);
		this.typeEditor.addTypeEventListener(this);

		this.graphEditor = new GraphEditor(this);

		this.ruleEditor = new RuleEditor(this);

		/* create split pane with rule and graph editor */
		this.splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.ruleEditor,
				this.graphEditor);
		this.splitPane.setDividerSize(15);
		this.splitPane.setContinuousLayout(true);
		this.splitPane.setOneTouchExpandable(true);

		/* create split pane with type editor and editor split pane */
		this.splitPane0 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, null, this.splitPane);
		this.splitPane0.setDividerSize(0);
		this.splitPane0.setContinuousLayout(true);
		this.splitPane0.setOneTouchExpandable(true);

		this.splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.splitPane0,
				null);
		this.splitPane1.setContinuousLayout(true);
		this.splitPane1.setOneTouchExpandable(true);
		this.splitPane1.setDividerSize(15);
		add(this.splitPane1, BorderLayout.CENTER);

		/* pop-up menus */
		this.modePopupMenu.setLabel("Edit Mode");
		this.modePopupMenu.setEditor(this);
		this.modePopupMenu.setMainModeMenu(this.mode);

		this.editPopupMenu.setLabel("Edit");
		this.editPopupMenu.setEditor(this);
		this.editPopupMenu.setParentFrame(this.applFrame);
		this.editPopupMenu.setGraphLayouter(this.evolutionaryLayouter);
		this.editPopupMenu.setParentFrame(this.applFrame);

		this.editSelPopupMenu.setLabel("Edit");
		this.editSelPopupMenu.setEditor(this);
		this.editSelPopupMenu.setParentFrame(this.applFrame);

		this.ruleEditor.setModePopupMenu(this.modePopupMenu);
		this.ruleEditor.setEditPopupMenu(this.editPopupMenu);
		this.ruleEditor.setEditSelPopupMenu(this.editSelPopupMenu);

		this.graphEditor.setModePopupMenu(this.modePopupMenu);
		this.graphEditor.setEditPopupMenu(this.editPopupMenu);
		this.graphEditor.setEditSelPopupMenu(this.editSelPopupMenu);

		this.ruleEditor.setEditCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.graphEditor.setEditCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		/* create transform module */
		this.gragraTransform = new GraGraTransform(this);
		this.gragraTransform.addTransformEventListener(this);
		this.gragraTransform.getOptionGUI().addActionListener(this.actionAdapter);
		this.gragraTransform.getGeneralOptionGUI().addActionListener(
				GraTraOptions.WAIT_AFTER_STEP, this.actionAdapter);

		resetEnabledOfMenus(null, false);
		resetEnabledOfToolBarItems(null, false);

		this.ruleEditor.getLeftPanel().getCanvas().addMouseListener(this.ml);
		this.ruleEditor.getRightPanel().getCanvas().addMouseListener(this.ml);
		this.ruleEditor.getNACPanel().getCanvas().addMouseListener(this.ml);

		this.graphEditor.getGraphPanel().getCanvas().addMouseListener(this.ml);

		this.typeEditor.getTypePalette().getEditNodeTypeButton()
				.addMouseListener(this.ml);
		this.typeEditor.getTypePalette().getNewNodeTypeButton().addMouseListener(this.ml);
		this.typeEditor.getTypePalette().getDeleteNodeTypeButton().addMouseListener(
				this.ml);
		this.typeEditor.getTypePalette().getEditArcTypeButton().addMouseListener(this.ml);
		this.typeEditor.getTypePalette().getNewArcTypeButton().addMouseListener(this.ml);
		this.typeEditor.getTypePalette().getDeleteArcTypeButton().addMouseListener(
				this.ml);

		this.nodeAnimation = new NodeAnimation();

		final ComponentListener cl = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if (GraGraEditor.this.typesAlwaysOn) {
					GraGraEditor.this.splitPane1.setDividerLocation(
								GraGraEditor.this.splitPane1.getWidth() - 200);
				}
				else {
					GraGraEditor.this.splitPane1.setDividerLocation(GraGraEditor.this.splitPane1.getWidth());
				}
			}			
		};
		addComponentListener(cl);

		createGraphLayouters();

		// test
		// this.activateObjectNameMenuItem(true);
	}

	private void createGraphLayouters() {
		/*
		 * testen ob this thread //Thread.currentThread().getClass().getName();
		 * Thread.currentThread().getClass().getName() instanceof AWT then
		 * direct ausfuehren graphLayouts = new GraphLayouts();
		 * graphLayouts.addActionListener(actionAdapter);
		 * setGraphLayoutAlgorithmName(GraphLayouts.DEFAULT_LAYOUT);
		 * 
		 * when not: SwingUtilities.invokeAndWait(new Runnable() {...
		 */
		// try {
		// SwingUtilities.invokeAndWait(new Runnable() {
		// public void run() {
		// graphLayouts = new GraphLayouts();
		// graphLayouts.addActionListener(actionAdapter);
		// setGraphLayoutAlgorithmName(GraphLayouts.DEFAULT_LAYOUT);
		// }
		// });
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// } catch (InvocationTargetException e1) {
		// e1.printStackTrace();
		// }
		// new
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GraGraEditor.this.graphLayouts = new GraphLayouts();
				GraGraEditor.this.graphLayouts.addActionListener(GraGraEditor.this.actionAdapter);
				setGraphLayoutAlgorithmName(GraphLayouts.DEFAULT_LAYOUT);
			}
		});
	}

	public ActionListener getActionListener() {
		return this.actionAdapter;
	}

	public int getDividerLocationOfEditorAndTypeEditor() {
		return this.dividerLocation2;
	}

	public void setDividerLocationOfEditorAndTypeEditor(int location) {
		this.dividerLocation2 = location;
	}

	public void setDividerLocation(int location) {
		this.dividerLocation = location;
		this.splitPane.setDividerLocation(this.dividerLocation);
	}

	public void showOptionGUI() {
		if (this.applFrame instanceof AGGAppl) {
			((AGGAppl) this.applFrame).getPreferences().showOptionGUI(
					OptionGUI.TRANSFORMATION);
		}
	}

	public void showRuleSequenceGUI(String seqName) {
		this.gragraTransform.getOptionGUI().showRuleSequenceGUI(seqName);	
	}
	
	public JSplitPane getMainSplitPane() {
		return this.splitPane1;
	}

	public boolean typesAlwaysOn() {
		return this.typesAlwaysOn;
	}

	public void setTypesAlwaysOn(boolean b) {
		this.typesAlwaysOn = b;
	}

	private void setAttrEditor() {
		/* get attribute editor and put to rule and graph editor */
		this.attrEditor = DefaultEditorFacade.self().getTopEditor();
		/* get input parameter editor */
		this.inputParameterEditor = DefaultEditorFacade.self()
				.getInputParameterEditor();

		/* add this to be attribute TableModelListener */
		((BasicTupleEditor) ((TopEditor) this.attrEditor)
				.getAttrInstanceEditor()).getTableModel()
				.addTableModelListener(this);
		((TopEditor) this.attrEditor).getContextEditor().getConditionEditor()
				.getTableModel().addTableModelListener(this);
		((BasicTupleEditor) this.inputParameterEditor).getTableModel()
				.addTableModelListener(this);

//		this.graphEditor.setAttrEditor(this.attrEditor);
		this.ruleEditor.setAttrEditor(this.attrEditor);

		if (this.exportJPEG != null) {
			((TopEditor) this.attrEditor).setExportJPEG(this.exportJPEG);
		}

		this.attrEditorExists = true;
	}

	public boolean isTransformationRunning() {
		return (this.interpreting || this.layering || this.sequences);
	}

	public boolean isLayeredTransformationRunning() {
		return this.layering;
	}

	public boolean isDefaultTransformationRunning() {
		return this.interpreting;
	}

	public boolean isRuleSequencesTransformationRunning() {
		return this.sequences;
	}

	/** Adds a new edit event listener. */
	public synchronized void addEditEventListener(EditEventListener l) {
		if (!this.editEventListeners.contains(l))
			this.editEventListeners.addElement(l);
	}

	/** Removes the edit event listener. */
	public synchronized void removeEditEventListener(EditEventListener l) {
		if (this.editEventListeners.contains(l))
			this.editEventListeners.removeElement(l);
	}

	/** Sends an edit event to all my listeners. */
	public synchronized void fireEditEvent(EditEvent e) {
		for (int i = 0; i < this.editEventListeners.size(); i++) {
			this.editEventListeners.elementAt(i).editEventOccurred(e);
		}
	}

	/** ********************************************************* */

	/** Implements TreeViewEventListener.treeViewEventOccurred */
	public void treeViewEventOccurred(TreeViewEvent e) {
		// System.out.println("GraGraEditor.treeViewEventOccurred "+e.getMsg());
		if (e.getMsg() == TreeViewEvent.EXIT) {
			setGraGra(null);
			return;
		}

		if (e.getMsg() == TreeViewEvent.TRANSFER_SHORTKEY) {
			if (e.getObject() instanceof KeyEvent) {
				this.keyAdapter.performShortKeyEvent((KeyEvent) e.getObject());
			}
		} else if (e.getMsg() == TreeViewEvent.GRAPH_CHANGED) {
			this.graphEditor.updateGraphics();
		} else if (e.getMsg() == TreeViewEvent.BACKGROUND_CLICK) {
			if (this.hasAttrEditorOnTop()) {
				if (this.isLastAttrDeclValid()) {	
					refreshTypeUser();
					if (this.activePanel != null) {
						updateUndoButtonAfterAttrEdit(this.activePanel);
					}
					this.resetRuleEditor();
					if (getEditMode() == EditorConstants.ATTRIBUTES) {
						this.lasteditmode = EditorConstants.MOVE;
						setEditMode(this.lasteditmode);
						this.forwardModeCommand("Move");
					}
				}
			} else if (this.hasAttrEditorOnBottom()) {
				if (this.isLastAttrDeclValid()) {							
					refreshTypeUser();
					if (this.activePanel != null) {
						updateUndoButtonAfterAttrEdit(this.activePanel);
					}
					this.resetGraphEditor();	
					if (getEditMode() == EditorConstants.ATTRIBUTES) {
						this.lasteditmode = EditorConstants.MOVE;
						setEditMode(this.lasteditmode);
						this.forwardModeCommand("Move");
					}
				}
			}
		} else if (e.getMsg() == TreeViewEvent.SELECT) {
			if (this.editmode == EditorConstants.INTERACT_MATCH) {
				resetEditModeAfterMapping(this.lasteditmode);
			} else if (this.graphEditor.getGraph() != null
					&& this.graphEditor.getGraph().isTypeGraph()) {
				int multiplicityError = getGraGra().getBasisGraGra()
						.getMultiplicityErrorKind();
				if (multiplicityError != -1) {
					fireEditEvent(new EditEvent(this,
							EditEvent.SET_TYPE_GRAPH_ENABLED, getGraGra(),
							String.valueOf(multiplicityError)));
				}
			}
		} else if (e.getMsg() == TreeViewEvent.SELECTED) {
			if (this.interpreting || this.layering || this.sequences) {
				return;
			}
			this.treeNodeData = e.getData();
			// store used class packages of current gragra
			if (this.treeNodeData.isGraGra() && (getGraGra() != null))
				getGraGra().getBasisGraGra().storeUsedClassPackages();

			if (hasAttrEditorOnTop() || hasAttrEditorOnBottom()) {
				if (!this.isLastAttrDeclValid()) 
					return;
				
				refreshTypeUser();
				if (this.undoManager != null && this.undoManager.isEnabled()) {
					if (!this.attrChanged) {
						if (this.activePanel != null)
							this.activePanel.getGraph().undoManagerLastEditDie();
					} else {
						if (this.activePanel != null)
							this.activePanel.getGraph().undoManagerEndEdit();
					}
					this.attrChanged = false;
					updateUndoButton();
				}
				if (this.activePanel != null)
					updateUndoButtonAfterAttrEdit(this.activePanel);
				if (hasAttrEditorOnTop()) {
					if (this.editmode != EditorConstants.ATTRIBUTES) {
						resetRuleEditor();
					}
				} else if (hasAttrEditorOnBottom()) {
					if (this.editmode != EditorConstants.ATTRIBUTES) {
						resetGraphEditor();
					}
				}	
			}

			if (this.treeNodeData.isRuleSequence()) {
				if (getGraGra() != null
						&& getGraGra().getBasisGraGra() == this.treeNodeData
								.getRuleSequence().getGraGra()) {
					getGraGra().getBasisGraGra().setCurrentRuleSequence(
							this.treeNodeData.getRuleSequence());
					if (getGraGra().getBasisGraGra().getCurrentRuleSequence() != null
							&& getGraGra().getBasisGraGra().getCurrentRuleSequence().isEmpty()) {
						getGraGra().getBasisGraGra().getCurrentRuleSequence().setGraph(getGraGra().getBasisGraGra().getGraph());
					}
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							this.treeNodeData.getRuleSequence()
									.getName().concat(" :   ")
									.concat(this.treeNodeData.getRuleSequence().getToolTipText())));
					getGraGraTransform().getOptionGUI().doClick(
							GraTraOptions.RULE_SEQUENCE);
					
					this.gragraTransform.getOptionGUI().setRulesOfRuleSequenceGUI(getGraGra().getEnabledRules());
					this.gragraTransform.getOptionGUI()
							.setRuleSequences(
									this.treeNodeData.getRuleSequence()
											.getSubSequenceList());
					
					 if (this.treeNodeData.getRuleSequence().getSubSequenceList().isEmpty()) {						
						 this.gragraTransform.getOptionGUI().showRuleSequenceGUI(
								 this.treeNodeData.getRuleSequence().getName());
					 }
					return;
				} 
				return;
			} 
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
			
			loadDataInEditor(this.treeNodeData);

			if (getGraGra() != null) {
				if (this.treeNodeData.isGraGra()) {
					this.gragraTransform.getOptionGUI().closeRuleSequenceGUI();
					this.nacExists = false;
					resetButtonIdenticNAC(this.nacExists);
					this.pacExists = false;
					resetButtonIdenticPAC(this.pacExists);
					this.acExists = false;
					resetButtonIdenticNestedAC(this.acExists);
					getGraGra().getBasisGraGra().setUsedClassPackages();
				} else if (this.treeNodeData.isRule()) {
					this.nacExists = false; // treeNodeData.getRule().hasNACs();
					resetButtonIdenticNAC(this.nacExists);
					this.pacExists = false; // treeNodeData.getRule().hasPACs();
					resetButtonIdenticPAC(this.pacExists);
					this.acExists = false;
					resetButtonIdenticNestedAC(this.acExists);
					
					if (this.treeNodeData.getRule().getMatch() != null) {
						this.treeNodeData.getRule().update();
						this.graphEditor.getGraph().update();
						updateGraphics();
					}

				} else if (this.treeNodeData.isNAC()) {
					this.nacExists = true;
					resetButtonIdenticNAC(this.nacExists);
				} else if (this.treeNodeData.isPAC()) {
					this.pacExists = true;
					resetButtonIdenticPAC(this.pacExists);
				} else if (this.treeNodeData.isNestedAC()) {
					this.acExists = true;
					resetButtonIdenticNestedAC(this.acExists);
				}
			}
			
		} else if (e.getMsg() == TreeViewEvent.DELETED) {
			this.treeNodeData = e.getData();
			if (this.treeNodeData != null) {
				deleteDataFromEditor(this.treeNodeData);
				if (this.gragra == null) {
					this.nacExists = false;
					this.pacExists = false;
					this.splitPane1.setRightComponent(null); // Type palette
					resetEnabledOfToolBarItems(null, false);
				}
				resetEnabledOfMenus(null, false);
				if (hasAttrEditorOnTop())
					resetRuleEditor();
				else if (hasAttrEditorOnBottom())
					resetGraphEditor();
			}
		} else if (e.getMsg() == TreeViewEvent.RULE_DELETED) {
			if (this.gragra != null) {
				this.gragraTransform.setRuleSequences(//gragra.getRules(), 
						this.gragra.getEnabledRules(),
						this.gragra.getBasisGraGra().getRuleSequenceList());
				// test no more rules
				if (this.gragra.getRules().isEmpty()) 
					this.ruleEditor.setRule(null);
			}
		} else if (e.getMsg() == TreeViewEvent.RULE_ADDED) {
			if (this.gragra != null) {
				this.gragraTransform.setRuleSequences(//gragra.getRules(), 
						this.gragra.getEnabledRules(),
						this.gragra.getBasisGraGra().getRuleSequenceList());
			}
		} else if (e.getMsg() == TreeViewEvent.RULE_SEQUENCE_DELETED) {
			if (this.gragra != null 
					&& this.gragra.getBasisGraGra().getRuleSequences().isEmpty()) {
				this.gragraTransform.setRuleSequences(//gragra.getRules(), 
						this.gragra.getEnabledRules(),
						null);
			}
		}
		else if (e.getMsg() == TreeViewEvent.REFRESH_GRAGRA) {
			resetGraTraGUI(this.gragra);
		} else if (e.getMsg() == TreeViewEvent.LOAD) {
			resetEditModeAfterMapping(this.lasteditmode);
		} else if (e.getMsg() == TreeViewEvent.LOADED) {			
			setEditMode(EditorConstants.MOVE);
			forwardModeCommand("Move");
		} else if (e.getMsg() == TreeViewEvent.SAVE) {
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
			updateGraphics();

			if (this.gragra != null)
				this.gragra.getBasisGraGra().setGraTraOptions(
						this.gragraTransform.getGraTraOptionsList());
		} else if (e.getMsg() == TreeViewEvent.TYPE_ERROR) {
			updateGraphics();
		} else if (e.getMsg() == TreeViewEvent.NO_TYPE_ERROR) {
			updateGraphics();
		} else if (e.getMsg() == TreeViewEvent.TRY_RESET) {
			this.gragraTransform.destroyMatch();
			this.graphEditor.updateGraphics();
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"        "));
		} else if (e.getMsg() == TreeViewEvent.RESET_GRAPH) {
			this.treeNodeData = e.getData();
			if ((this.treeNodeData.isGraGra() && (this.treeNodeData.getGraGra() == this.gragra))
					|| (this.treeNodeData.isGraph() && (this.treeNodeData.getGraph()
							.getGraGra() == this.gragra))) {

				this.graphEditor.setGraph(this.gragra.getGraph());
				this.graphEditor.updateGraphics();
				if (this.gragraTransform.checkRuleApplicabilityEnabled())
					this.gragraTransform.getApplicableRules(this.gragra);

				if (this.continueLayeredTransformLoop) {
					this.continueLayeredTransformLoop = false;
					this.buttonStart.doClick();
				} else {
					this.noMoreStopBeforeResetGraph = false;
				}
			}
		} else if (e.getMsg() == TreeViewEvent.TRY_IMPORT) {
			this.gragraTransform.destroyMatch();
			this.graphEditor.updateGraphics();
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"        "));
		} else if (e.getMsg() == TreeViewEvent.IMPORT_TYPE_GRAPH) {
			this.typeEditor.refreshTypes();
			this.evolutionaryLayouterOptionGUI.setGraGra(this.gragra);

			if (this.graphEditor.getGraph().isTypeGraph()) {
				this.graphEditor.setGraph(this.gragra.getTypeGraph());
				this.graphEditor.updateGraphics();
			}
			if (getGraGra().getTypeSet().getTypeGraph() != null
					&& !getGraGra().getTypeSet().getTypeGraph()
							.getSelectedObjs().isEmpty()) {
				forwardModeCommand("Move");
				setEditMode(agg.gui.editor.EditorConstants.MOVE);
			}
		} else if (e.getMsg() == TreeViewEvent.IMPORT_GRAPH) {
			this.typeEditor.refreshTypes();
			this.evolutionaryLayouterOptionGUI.setGraGra(this.gragra);

			if (!this.graphEditor.getGraph().isTypeGraph()) {
				this.graphEditor.setGraph(this.gragra.getGraph());
				this.graphEditor.updateGraphics();
			}
			if (getGraGra().getTypeSet().getTypeGraph() != null
					&& !getGraGra().getTypeSet().getTypeGraph()
							.getSelectedObjs().isEmpty()) {
				forwardModeCommand("Move");
				setEditMode(agg.gui.editor.EditorConstants.MOVE);
			}
			if (this.gragraTransform.checkRuleApplicabilityEnabled())
				this.gragraTransform.getApplicableRules(this.gragra);
		} else if (e.getMsg() == TreeViewEvent.ADD_IMPORT_GRAPH) {
			this.typeEditor.refreshTypes();
			this.evolutionaryLayouterOptionGUI.setGraGra(this.gragra);
			// ?? should the added import graph be set into graph editor?
			// graphEditor.setGraph(gragra.getGraph());
			// graphEditor.updateGraphics();
			// if(gragraTransform.checkRuleApplicabilityEnabled())
			// gragraTransform.getApplicableRules(gragra);
		} else if (e.getMsg() == TreeViewEvent.IMPORT_RULE) {
			this.typeEditor.refreshTypes();
			this.evolutionaryLayouterOptionGUI.setGraGra(this.gragra);
			if (this.graphEditor.getGraph().isTypeGraph()) {
				this.graphEditor.setGraph(this.gragra.getTypeGraph());
				this.graphEditor.updateGraphics();
			}
			if (getGraGra().getTypeSet().getTypeGraph() != null
					&& !getGraGra().getTypeSet().getTypeGraph()
							.getSelectedObjs().isEmpty()) {
				forwardModeCommand("Move");
				setEditMode(agg.gui.editor.EditorConstants.MOVE);
			}
		} else if (e.getMsg() == TreeViewEvent.CHECK_RULE_APPLICABILITY) {
			getGraGra().getApplicableRules(this.gragraTransform.getStrategy());
			if (e.getSource() instanceof GraGraTreeView
					|| e.getSource() instanceof agg.gui.popupmenu.GraGraPopupMenu)
				((GraGraTreeView) e.getSource()).getTree().treeDidChange();
		} else if (e.getMsg() == TreeViewEvent.DISMISS_RULE_APPLICABILITY) {
			getGraGra().dismissRuleApplicability();
			if (e.getSource() instanceof GraGraTreeView
					|| e.getSource() instanceof agg.gui.popupmenu.GraGraPopupMenu)
				((GraGraTreeView) e.getSource()).getTree().treeDidChange();
		} else if (e.getMsg() == TreeViewEvent.UNDO_DELETE) {
			this.typeEditor.refreshTypes();
		} else if (e.getMsg() == TreeViewEvent.EXPORT_JPEG) {
			saveGraphJPEG();
		} else if (e.getMsg() == TreeViewEvent.INPUT_PARAMETER_NOT_SET) {
			// System.out.println("GraGraEditor: TreeViewEvent:
			// INPUT_PARAMETER_NOT_SET");
			if (e.getObject() instanceof Rule) {
				setInputParameter((Rule) e.getObject());
			}
		} else if (e.getMsg() == TreeViewEvent.RULE_SEQUENCE) {
			getGraGraTransform().getOptionGUI().doClick(
					GraTraOptions.RULE_SEQUENCE);
		}
	}

	/** Implements TreeModelListener.treeNodesChanged */
	public void treeNodesChanged(TreeModelEvent e) {
		// System.out.println("GraGraEditor.treeNodesChanged ");
		final TreePath path = e.getTreePath();
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
				.getLastPathComponent();
		GraGraTreeNodeData data = (GraGraTreeNodeData) node.getUserObject();
		String name = data.string();

		if (this.treeNodeData != null && getGraGra() != null) {
			data = this.treeNodeData;
			name = data.string();

			if (data.isGraGra() && data.getGraGra() != null
					&& (data.getGraGra().equals(getGraGra()))
					&& (data.getGraGra() == getGraGra())
					) {
				if (this.ruleEditor.getAtomic() != null) {
					this.ruleEditor.setAtomicTitle(this.ruleEditor.getAtomic()
							.getBasisAtomic().getName(), this.ruleEditor.getAtomic()
							.getBasisAtomic().getAtomicName());
				} else if (this.ruleEditor.getRule() != null) {
					this.ruleEditor.setRuleTitle(this.ruleEditor.getRule().getBasisRule()
							.getName(), name);
				} else {
					this.ruleEditor.setRuleTitle("", name);
				}
				this.graphEditor.setTitle(data.getGraGra().getGraph()
						.getBasisGraph().getName(), name);
			} else if (data.isTypeGraph() && data.getGraph() != null
					&& data.getGraph().equals(this.graphEditor.getGraph())) {
				this.graphEditor.setTitle(data.getGraph().getBasisGraph().getName(),
						getGraGra().getName());
			} else if (data.isGraph() && data.getGraph() != null
					&& data.getGraph().equals(this.graphEditor.getGraph())) {
				this.graphEditor.setTitle(data.getGraph().getBasisGraph().getName(),
						getGraGra().getName());
			} else if (data.isRule() && data.getRule() != null
					&& data.getRule().equals(this.ruleEditor.getRule())) {
				this.ruleEditor.setRuleTitle(name, getGraGra().getName());
			} else if (data.isNAC() && data.getNAC() != null
					&& data.getNAC().equals(this.ruleEditor.getNAC())) {
				this.ruleEditor.setNACTitle(name);
			} else if (data.isAtomic()
					&& data.getAtomic() != null
					&& this.ruleEditor.getAtomic() != null
					&& (data.getAtomic().getParent() == this.ruleEditor.getAtomic()
							.getParent())) {
				// if (ruleEditor.getAtomic().getParent() ==
				// ruleEditor.getAtomic()
				// && ruleEditor.getAtomic().getConclusions().isEmpty())
				// ruleEditor.setAtomicTitle("", name);
				// else
				this.ruleEditor.setAtomicTitle(this.ruleEditor.getAtomic().getMorphism()
						.getName(), name);
			} else if (data.isConclusion() && data.getConclusion() != null
					&& (data.getConclusion().equals(this.ruleEditor.getAtomic()))) {
				this.ruleEditor.setAtomicTitle(name, data.getConclusion()
						.getBasisAtomic().getAtomicName());
			}
		}
	}

	public void treeNodesInserted(TreeModelEvent e) {
	}

	public void treeNodesRemoved(TreeModelEvent e) {
	}

	public void treeStructureChanged(TreeModelEvent e) {
	}

	/** *********************************************************************** */

	
	int trafoEventKey;
	
	/** Implements TransformEventListener.transformEventOccurred */
	public void transformEventOccurred(TransformEvent e) {
		this.trafoEventKey = e.getMsg();
		switch (this.trafoEventKey) {
		case TransformEvent.INHERITANCE:
			requestFocusInWindow();
			JOptionPane.showMessageDialog(
							this.applFrame,
							"Sorry."
									+ "\nConsistency check will be ignored, because it isn`t yet"
									+ "\nimplemented for grammars with node type inheritance.",
							"Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
			break;
		case TransformEvent.START:
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transformation of  <" + getGraGra().getName() + ">  ..."));
			break;
		case TransformEvent.RULE:
			if (e.getRule() != null && this.gragraTransform.waitAfterStepEnabled()) {
				EdRule er = this.gragra.getRule(e.getRule().getName());
				if (er != null) {
					this.ruleEditor.setRule(er); 
				}
			}
			break;
		case TransformEvent.NEW_MATCH:
			if (this.interpreting || this.layering || this.sequences) {
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ... " + e.getMessage()));
			}
			break;
		case TransformEvent.MATCH_VALID:
			if (this.interpreting || this.layering || this.sequences) {
				if (this.undoManager != null && this.undoManager.isEnabled())
					this.undoManager.setUndoEndOfTransformStepAllowed(true);

				 if (e.getMatch().getRule().isWaitBeforeApplyEnabled()) {
					 this.sleep = true;
					 requestFocusInWindow();
					 
					 if (e.getMatch().getRule().getRuleScheme() == null) {
						 if (this.getRuleEditor().getRule().getBasisRule() != e.getMatch().getRule()) {
							 this.setRule(this.getGraGra().getRule(e.getMatch().getRule()));
						 }
						 this.getRuleEditor().getRule().updateMatch();
						 updateGraphics();
					 } else {
						 EdRuleScheme rs = this.getGraGra().getRuleScheme(e.getMatch().getRule());
						 if (rs != null) {
//							 EdRule amalgRule = rs.getAmalgamatedRule();
							 Rule amalgRule = rs.getBasisRuleScheme().getAmalgamatedRule();
							 if (amalgRule != null) {
//								 amalgRule.updateRule();
//								 this.setRule(amalgRule);								 
//								 amalgRule.updateMatch();
//								 updateGraphics();
								 
								 this.setRule(rs.getKernelRule());
								 rs.updateMatch(amalgRule.getMatch(), this.graphEditor.getGraph());
								 this.graphEditor.getGraphPanel().updateGraphics(false);								 
							 }
						 }
					 }
					 
					 fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							 e.getMessage()
							 +"     <<<   PRESS ANY KEY TO CONTINUE   >>>"));
					 try{
						 while (this.sleep) {
							 Thread.sleep(100);
						 }
					 } catch(InterruptedException ex1) {}
				 } else {
					 
				 }
			} else {
				updateGraphics();
			}
			break;
		case TransformEvent.LAYER_FINISHED:
			if (this.layering) {
				if (this.gragraTransform.stopLayerAndWaitEnabled()) {
					this.sleep = true;
					requestFocusInWindow();

					fireEditEvent(new EditEvent(
							this,
							EditEvent.EDIT_PROCEDURE,
							e.getMessage()
									+ "     <<<   PRESS ANY KEY TO CONTINUE   >>>"));
					try {
						while (this.sleep) {
							Thread.sleep(100);
						}
					} catch (InterruptedException ex1) {}

					this.layeredTransform.nextLayer();
				} else {
					if (!this.gragraTransform.showGraphAfterStepEnabled()) {
						this.graphEditor.updateGraphics();
					}

					this.layeredTransform.nextLayer();
				}
			}
			break;
		case TransformEvent.STOP:
			getGraGra().getGraph().setTransformChangeEnabled(false);
			if (this.interpreting || this.layering || this.sequences) {
				this.sleep = false;

				resetIconsIfTransformInterpret(true);
				this.lasteditmode = EditorConstants.MOVE;
				setEditMode(this.lasteditmode);
				this.forwardModeCommand("Move");

				disableStopMenuItem();
				this.buttonT.setEnabled(true);
				this.buttonLayout.setEnabled(true);

				if (this.layering) {
					removeEditEventListener(this.layeredTransform);
					this.layeredTransform.dispose();
					this.layering = false;
				} else if (this.interpreting) {
					removeEditEventListener(this.interpreter);
					this.interpreter.dispose();
					this.interpreting = false;
				} else if (this.sequences) {
					removeEditEventListener(this.transformRuleSequences);
					this.transformRuleSequences.dispose();
					this.sequences = false;
				}

				addEditEventListener(this.gragraTransform.getTransformDebugger());

				if (this.gragraTransform.checkRuleApplicabilityEnabled())
					this.gragraTransform.getApplicableRules(getGraGra());

				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transformation of  <" + getGraGra().getName() + ">  "
								+ e.getMessage()));
			}

			selectToolBarTransformItem("");

			this.typeEditor.setEnabled(true);

			if (this.undoManager != null && this.undoManager.isEnabled()
					&& this.undoManager.canUndo()) {
				this.buttonUndo.setEnabled(true);
				if (this.undoManager.canRedo())
					this.buttonRedo.setEnabled(true);
				setUndoStepButtonEnabled(true);
			}
			if (getGraGra().isAnimated()) {
				this.undoManager.setEnabled(true);
			}
			if (this.applFrame instanceof AGGAppl) {
				((AGGAppl) this.applFrame).getGraGraTreeView().getTree()
						.treeDidChange();
			}
			if (this.evolutionaryLayouter.getWriteMetricValues()) {
				if (this.jpgPath != null && !this.jpgPath.equals(""))
					writeMetricValues(this.jpgPath + File.separator
							+ this.getGraGra().getFileName() + "_metrics.log");
			}
			setActivePanel(this.graphEditor.getGraphPanel());
			break;
		case TransformEvent.MATCH_DEF:
			if (this.ruleEditor.isObjMapping()) {
				this.ruleEditor.resetEditModeAfterMapping();
			}
			setEditMode(EditorConstants.INTERACT_MATCH);
			break;
		case TransformEvent.MATCH_PARTIAL:
			// graphEditor.updateGraphics();
			// fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
			// "Transform ... Partial match is set. Try to complete it. "));
			break;
		case TransformEvent.NEXT:
			if (this.lasteditmode != EditorConstants.MOVE) {
				this.lasteditmode = EditorConstants.MOVE;
				setEditMode(this.lasteditmode);
				this.forwardModeCommand("Move");
			}
			this.graphEditor.updateGraphics();
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transform ...  Next Completion ... Please wait. "));
			break;
		case TransformEvent.CANNOT_MATCH:
			if (!this.interpreting && !this.layering && !this.sequences) {
				getGraGra().getGraph().setTransformChangeEnabled(false);
				setEditMode(this.lasteditmode);
				JOptionPane.showMessageDialog(this.applFrame, "Cannot match" + "\n"
						+ e.getMessage(), "Cannot match",
						JOptionPane.ERROR_MESSAGE);
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ...  Match failed. "));
			}
			break;
		case TransformEvent.STEP:
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transform ...  Step ... Please wait. "));
			break;
		case TransformEvent.INPUT_PARAMETER_NOT_SET:
			if (setInputParameter(e))
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ... Please set value of the input parameter(s)."));
			break;
		case TransformEvent.NOT_READY_TO_TRANSFORM:
			getGraGra().getGraph().setTransformChangeEnabled(false);
			JOptionPane.showMessageDialog(this.applFrame, "Not ready to transform!"
					+ "\n" + e.getMessage(), "Cannot transform",
					JOptionPane.ERROR_MESSAGE);
			setEditMode(this.lasteditmode);
			selectToolBarTransformItem("");
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transform ...  Not ready to transform. "));
			break;
		case TransformEvent.CANNOT_TRANSFORM:
			if (!this.interpreting && !this.layering && !this.sequences) {
				getGraGra().getGraph().setTransformChangeEnabled(false);
				if (e.getMessage().length() > 0) {
					JOptionPane.showMessageDialog(this.applFrame,
							"Cannot transform." + "\n" + e.getMessage(),
							"Cannot transform", JOptionPane.ERROR_MESSAGE);
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							"Transform ...  failed. "));
				}
			} else { // if (interpreting || layering || sequences)
				if (e.getMessage().length() > 0) {
					JOptionPane.showMessageDialog(this.applFrame,
							"Cannot transform." + "\n" + e.getMessage(),
							"Cannot transform", JOptionPane.ERROR_MESSAGE);
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							"Transform ... " + e.getMessage()));
				}
			}
			setEditMode(this.lasteditmode);
			selectToolBarTransformItem("");
			break;
		case TransformEvent.CLEAR_MATCH:
			updateGraphics();
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Please define a new match."));
			break;
		case TransformEvent.KEEP_MATCH:
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
			break;
		case TransformEvent.MATCH_COMPLETED:
			getGraph().unsetNodeNumberChanged();

			if (this.gragraTransform.selectMatchObjectsEnabled()) {
				getGraph().updateAlongMorph(e.getMatch());
			}

			updateGraphics();

			this.updateUndoButton();
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transform ... Match is complete."));
			selectToolBarTransformItem("");
			setActivePanel(this.graphEditor.getGraphPanel());
			break;
		case TransformEvent.ANIMATED_NODE:
			// match and co-match are used to get position of node to animate
			setNodeAnimation(e.getMatch());
			break;
		case TransformEvent.STEP_COMPLETED:
			this.stepCounter++;
			if (this.interpreting || this.layering || this.sequences) {
				if (this.gragraTransform.showGraphAfterStepEnabled()) {
					if (this.stepCounter == 1) {
						this.getGraph().addMovedToUndo(
								this.getGraph().getGraphObjects());
					}

					runAnimationThread();

					this.graphEditor.getGraphPanel().updateGraphics();
				}

				if (this.gragraTransform.checkRuleApplicabilityEnabled()) {
					this.gragraTransform.getApplicableRules(this.gragra);
				}

				if (this.gragraTransform.waitAfterStepEnabled()) {
					requestFocusInWindow();
					this.sleep = true;
					fireEditEvent(new EditEvent(
							this,
							EditEvent.EDIT_PROCEDURE,
							e.getMessage()
									+ "     <<<   PRESS ANY KEY TO CONTINUE   >>>"));
					try {
						while (this.sleep) {
							Thread.sleep(100);
						}
					} catch (InterruptedException ex1) {}

					if (this.gragraTransform.checkRuleApplicabilityEnabled()) {
						this.gragraTransform.getApplicableRules(this.gragra, true);
					}
				} else {
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							"Transform ... " + e.getMessage()));
				}

			} else { // step done in debug transformation
				getGraGra().getGraph().setTransformChangeEnabled(false);

				runAnimationThread();

				this.graphEditor.getGraphPanel().updateGraphics();

				if (this.ruleEditor.getRule().getBasisRule() instanceof AmalgamatedRule
						|| this.ruleEditor.getRule().getBasisRule() instanceof KernelRule
						|| this.ruleEditor.getRule().getBasisRule() instanceof MultiRule) {
					fireEditEvent(new EditEvent(this,
							EditEvent.DELETE_RULE_REQUEST, this.ruleEditor.getRule()));
				}
				if (e.getMatch() == null) {
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							"Transform ... Step is completed."));

					if (this.gragraTransform.checkRuleApplicabilityEnabled()) {
						fireEditEvent(new EditEvent(this,
								EditEvent.EDIT_PROCEDURE,
								" Checking applicability of rules ... Please wait. "));

						this.gragraTransform.getApplicableRules(getGraGra());
						fireEditEvent(new EditEvent(this,
								EditEvent.EDIT_PROCEDURE,
								" Step is done.     Checking applicability of rules is done."));
					} else {
						this.gragraTransform.getApplicableRules(getGraGra(), true);
						fireEditEvent(new EditEvent(this,
								EditEvent.EDIT_PROCEDURE, " Step is done. "));
					}
				}

				selectToolBarTransformItem("");

				if (this.undoManager != null && this.undoManager.isEnabled()
						&& this.undoManager.canUndo()) {
					setUndoButtonEnabled(true);
				}
				setActivePanel(this.graphEditor.getGraphPanel());
			}
			break;
		case TransformEvent.NO_COMPLETION:
			if (!this.interpreting && !this.layering && !this.sequences) {
				getGraGra().getGraph().setTransformChangeEnabled(false);

				updateGraphics();
				JOptionPane.showMessageDialog(this.applFrame,
						"No match completion found." + "\n" + e.getMessage(),
						"No completion", JOptionPane.ERROR_MESSAGE);

				selectToolBarTransformItem("");
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ... No match completion found."));
			}
			break;
		case TransformEvent.NOT_VALID:
			if (!this.interpreting && !this.layering && !this.sequences) {
				getGraGra().getGraph().setTransformChangeEnabled(false);

				updateGraphics();
				// setEditMode(this.lasteditmode);
				JOptionPane.showMessageDialog(this.applFrame, "Match is not valid.",
						"Not valid", JOptionPane.ERROR_MESSAGE);
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ... Match was not valid."));
			}
			break;
		case TransformEvent.CANCEL:
			if (this.interpreting || this.layering || this.sequences) {
				// graphEditor.updateGraphics();
				// updateGraphics();
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ...  was cancelled. "));
			} else {
				getGraGra().getGraph().setTransformChangeEnabled(false);
				// updateGraphics();
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ... Last action was cancelled. "));
			}
			setEditMode(this.lasteditmode);
			selectToolBarTransformItem("");
			break;
		case TransformEvent.INCONSISTENT:
			if (!this.interpreting && !this.layering && !this.sequences) {
				getGraGra().getGraph().setTransformChangeEnabled(false);
				setEditMode(this.lasteditmode);
				selectToolBarTransformItem("");
			}

			if (this.layering && this.noMoreStopBeforeResetGraph) {
			} else {
				JOptionPane.showMessageDialog(this.applFrame, e.getMessage(),
						"Graph Inconsistency", JOptionPane.ERROR_MESSAGE);

				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"Transform ...  Graph inconsistency "));
			}
			break;
		case TransformEvent.RULE_SEQUENCE_DEFINE:
			if (this.gragra != null) {
				this.gragra.getBasisGraGra().setSubsequencesOfCurrentRuleSequence(
						this.gragraTransform.getRuleSequences());
			}
			break;
		case TransformEvent.RESET_GRAPH:
			boolean showDialog = true;
			if (this.noMoreStopBeforeResetGraph) {
				this.layeredRuns++;
				if (this.layeredRuns > TransformLayered.MAX_LAYERED_RUNS) {
					this.layeredRuns = 0;
				} else {
					this.continueLayeredTransformLoop = true;
					fireEditEvent(new EditEvent(this, EditEvent.RESET_GRAPH,
							this.graphEditor.getGraph()));
					return;
				}
			}

			if (showDialog) {
				Object[] options = { "OK", "Cancel" };
				final DialogWithCheckBoxAndTwoButtons dialog = new DialogWithCheckBoxAndTwoButtons(
						this.applFrame,
						"Reset Graph",
						" Do you want to reset graph for next loop of layered graph transformation?",
						options,
						options[0],
						" loop ( max "
								+ String
										.valueOf(TransformLayered.MAX_LAYERED_RUNS)
								+ " times ) over all layers without asking for confirmation");
				dialog.setVisible(true);
				int answer = dialog.getAnswer();
				if (answer == DialogWithCheckBoxAndTwoButtons.YES_OPTION) {
					this.continueLayeredTransformLoop = true;
					this.noMoreStopBeforeResetGraph = dialog.isCheckSelected();
					fireEditEvent(new EditEvent(this, EditEvent.RESET_GRAPH,
							this.graphEditor.getGraph()));
				}
			}
			break;
		default:
		}
	}

	protected boolean setInputParameter(final TransformEvent e) {
		if (e.getMatch() != null) {
			return setInputParameter(e.getMatch());
		} else if (e.getRule() != null) {
			return setInputParameter(e.getRule());
		} else
			return false;
	}

	protected boolean setInputParameter(final Match m) {
		if (m != null) {
			agg.attribute.AttrVariableTuple avt = m.getAttrContext()
					.getVariables();
			this.inputParameterEditor.setTuple(avt);
			((OpenViewSetting) ((MaskedViewSetting) this.inputParameterEditor
					.getViewSetting()).getOpenView()).removeFormat(avt
					.getType());
			setInputParameterEditorOnTop(this.inputParameterEditor.getComponent(), m
					.getRule());
			if (this.ruleEditor.getRule().getBasisRule() != m.getRule()) {
				this.setRule(this.gragra.getRule(m.getRule()));
			}
			return true;
		}
		return false;
	}

	protected boolean setInputParameter(final Rule r) {
		if (r != null) {
			agg.attribute.AttrVariableTuple avt = r.getAttrContext()
					.getVariables();
			this.inputParameterEditor.setTuple(avt);
			((OpenViewSetting) ((MaskedViewSetting) this.inputParameterEditor
					.getViewSetting()).getOpenView()).removeFormat(avt
					.getType());
			setInputParameterEditorOnTop(this.inputParameterEditor.getComponent(), r);
			return true;
		}
		return false;
	}

	/** ******************************************************* */

	/** Implements TableModelListener.tableChanged */
	public void tableChanged(TableModelEvent e) {
//		System.out.println("##########   GraGraEditor.tableChanged of attributes:: ");

		// Attr Editor und Input Parameter Editor events
		final AttrVariableTuple avt = (AttrVariableTuple) ((BasicTupleEditor) this.inputParameterEditor)
				.getTuple();
		if ((e.getColumn() == TableModelEvent.ALL_COLUMNS) && (avt != null)
				&& avt.areInputParametersSet()) {
			unsetInputParameterEditor();
			this.inputParameterEditor.setTuple(null);
			fireEditEvent(new EditEvent(this, EditEvent.INPUT_PARAMETER_OK));
			return;
		}

		if (!hasAttrEditorOnTop() && !hasAttrEditorOnBottom()) {
			return;
		}

		boolean needFullUpdate = false;
		this.attrTypeChanged = false;
		this.attrChanged = false;
		this.attrTypeCreated = false;
		if (((TopEditor) this.attrEditor).getTitleOfSelectedEditor().equals(
				"Current Attribute")
				&& this.attrMemberCount != (((TupleTableModel) e.getSource())
						.getRowCount() - 1)) {
			// System.out.println(attrMemberCount+" "+(((TupleTableModel)
			// e.getSource())
			// .getRowCount() - 1));
			this.attrChanged = true;
			needFullUpdate = true;
			this.attrTypeCreated = true;
			if (this.graphObjectOfAttrInstance != null) {
				this.gragra.getTypeSet().setAttrTypeChanged(this.graphObjectOfAttrInstance.getType(), true);
			}
		} else if (((TupleTableModel) e.getSource()).getColumnName(
				((TupleTableModel) e.getSource()).getChangedColumn()).equals(
				"Shown")) {
			if (((TupleTableModel) e.getSource()).isColumnValueChanged()) {
				needFullUpdate = true;
				// gragra.setChanged(true);
			}
		} else if (((TupleTableModel) e.getSource()).getColumnName(
				((TupleTableModel) e.getSource()).getChangedColumn()).equals(
				"Expression")) {
			if (((TopEditor) this.attrEditor).getTitleOfSelectedEditor().equals(
					"Attribute Context")) {
				if (this.ruleEditor.getAtomic() != null) {
					fireEditEvent(new EditEvent(this,
							EditEvent.ATTR_CONDITION_CHANGED, this.ruleEditor
									.getAtomic(), ""));
				} else if (this.ruleEditor.getRule() != null) {
					fireEditEvent(new EditEvent(this,
							EditEvent.ATTR_CONDITION_CHANGED, this.ruleEditor
									.getRule(), ""));
				}
			} else if (((TopEditor) this.attrEditor).getTitleOfSelectedEditor()
					.equals("Current Attribute")) {
				this.attrChanged = true;
				if (this.graphObjectOfAttrInstance != null) {
					if (this.graphObjectOfAttrInstance.getContext().getKind()
							.equals("LHS")) {
						if (!compareLHSAttrsWithTarObjs(this.graphObjectOfAttrInstance)) {
							needFullUpdate = true;
						}
					}
					else if (this.graphObjectOfAttrInstance.getContext().getKind()
							.equals("NAC")) {
						compareAttrsWithSourceObjectOfNAC(this.graphObjectOfAttrInstance);
					} else if (this.graphObjectOfAttrInstance.getContext().getKind()
							.equals("PAC")) {
						compareAttrsWithSourceObjectOfPAC(this.graphObjectOfAttrInstance);
					} else if (this.graphObjectOfAttrInstance.getContext().getKind()
							.equals("GAC")) {
						compareAttrsWithSourceObjectOfGAC(this.graphObjectOfAttrInstance);
					}
				}
			}
		} else if (((TupleTableModel) e.getSource()).getColumnName(
				((TupleTableModel) e.getSource()).getChangedColumn()).equals(
				"Type")) {
			if (((TupleTableModel) e.getSource()).isColumnValueChanged()) {
				this.attrTypeChanged = true;
				this.attrChanged = true;
				needFullUpdate = true;
			}
		} else if (((TupleTableModel) e.getSource()).getColumnName(
				((TupleTableModel) e.getSource()).getChangedColumn()).equals(
				"Name")) {
			if (((TupleTableModel) e.getSource()).isColumnValueChanged()) {
				this.attrChanged = true;
				needFullUpdate = true;
			}
		}

		if (this.attrChanged) {
			if (needFullUpdate) {
				if (hasAttrEditorOnTop()) {
					this.graphEditor.updateGraphics();
				} else {
					this.ruleEditor.updateGraphics();
				}
			} else {
				this.activePanel.updateGraphics();
			}
		}
	}

	private void attrTypeChangedWarning() {
		if (this.attrTypeChanged) {
			JOptionPane
					.showMessageDialog(
							this.applFrame,
							"Attribute type declaration has changed."
									+ "\nAlready set attribute values may be not more valid."
									+ "\nPlease check these values.",
							"Attribute Type changed",
							JOptionPane.WARNING_MESSAGE);
			this.attrTypeChanged = false;
		}
	}

	protected boolean isLastAttrDeclValid() {
		if (this.attrEditor.getTuple() != null && !this.attrEditor.getTuple().getTupleType().isDefined()) {										
			JOptionPane.showMessageDialog(this.applFrame, 
					"An attribute member declaration failed! \nPlease correct or delete it." , 
					"Attribute failed",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	protected boolean compareAttrsWithSourceObjectOfNAC(final GraphObject nacObj) {
		if (this.activePanel != null
				&& this.activePanel.getParentEditor() instanceof RuleEditor
				&& nacObj.getAttribute() != null) {
			if (this.ruleEditor.getNAC() != null) {
				final Enumeration<GraphObject> inverse = this.ruleEditor.getNAC()
						.getMorphism().getInverseImage(nacObj);
				while (inverse.hasMoreElements()) {
					final GraphObject lhsObj = inverse.nextElement();

					if (!this.ruleEditor.getRule().getBasisRule()
							.compareConstAttrValueOfMapObjs(
									lhsObj, nacObj)) {
						JOptionPane
								.showMessageDialog(
										this.applFrame,
										"NAC attribute value failed!"
												+ "\nThe value of an attribute of a NAC"
												+ "\nhas to be equal to the correspondent "
												+ "\nattribute value of the LHS of a rule.",
										"Attribute value changed",
										JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
		}
		return true;
	}

	protected boolean compareAttrsWithSourceObjectOfPAC(final GraphObject pacObj) {
		if (this.activePanel != null
				&& this.activePanel.getParentEditor() instanceof RuleEditor
				&& pacObj.getAttribute() != null) {
			if (this.ruleEditor.getPAC() != null
					&& this.ruleEditor.getPAC().getMorphism()
							.getInverseImage(pacObj).hasMoreElements()) {

				final Enumeration<GraphObject> inverse = this.ruleEditor.getPAC()
						.getMorphism().getInverseImage(pacObj);
				while (inverse.hasMoreElements()) {
					final GraphObject lhsObj = inverse.nextElement();

					if (!this.ruleEditor.getRule().getBasisRule()
							.compareConstAttrValueOfMapObjs(
									lhsObj, pacObj)) {
						JOptionPane
								.showMessageDialog(
										this.applFrame,
										"PAC attribute value failed!"
												+ "\nThe value of an attribute of a PAC "
												+ "\nhas to be equal to the correspondent "
												+ "\nattribute value of the LHS of a rule.",
										"Attribute value changed",
										JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
		}
		return true;
	}

	protected boolean compareAttrsWithSourceObjectOfGAC(final GraphObject acObj) {
		if (this.activePanel != null
				&& this.activePanel.getParentEditor() instanceof RuleEditor
				&& acObj.getAttribute() != null) {
			if (this.ruleEditor.getNestedAC() != null
					&& this.ruleEditor.getNestedAC().getMorphism()
							.getInverseImage(acObj).hasMoreElements()) {

				final Enumeration<GraphObject> inverse = this.ruleEditor.getNestedAC()
						.getMorphism().getInverseImage(acObj);
				while (inverse.hasMoreElements()) {
					final GraphObject lhsObj = inverse.nextElement();

					if (!this.ruleEditor.getRule().getBasisRule()
							.compareConstAttrValueOfMapObjs(
									lhsObj, acObj)) {
						JOptionPane
								.showMessageDialog(
										this.applFrame,
										"GAC attribute value failed!"
												+ "\nThe value of an attribute of a GAC "
												+ "\nhas to be equal to the correspondent "
												+ "\nattribute value of the LHS of a rule.",
										"Attribute value changed",
										JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
		}
		return true;
	}
	
	protected boolean compareLHSAttrsWithTarObjs(final GraphObject lhsObj) {
		boolean result = true;
		if (this.activePanel != null
				&& this.activePanel.getParentEditor() instanceof RuleEditor
				&& lhsObj.getAttribute() != null) {

			final Rule brule = this.ruleEditor.getRule().getBasisRule();

			final List<OrdinaryMorphism> nacs = brule.getNACsList();
			result = compareLHSAttrsWithTarObjOfCond(brule, lhsObj, nacs, "NAC");

			final List<OrdinaryMorphism> pacs = brule.getPACsList();
			result = compareLHSAttrsWithTarObjOfCond(brule, lhsObj, pacs, "PAC");
			
			final List<OrdinaryMorphism> acs = brule.getNestedACsList();
			result = compareLHSAttrsWithTarObjOfCond(brule, lhsObj, acs, "GAC");
		}
		return result;
	}

	protected boolean compareLHSAttrsWithTarObjOfCond(
			final Rule r,
			final GraphObject lhsObj,
			List<OrdinaryMorphism> conds,
			String what) {
		boolean result = true;
		for (int l = 0; l < conds.size(); l++) {
			final OrdinaryMorphism cond = conds.get(l);
			final GraphObject go = cond.getImage(lhsObj);
			if (go != null
					&& !r.compareConstAttrValueOfMapObjs(
								lhsObj, go)) {
				result = false;
				JOptionPane.showMessageDialog(
						this.applFrame,
								what+" attribute value failed!"
									+ "\nThe value of the correspondent attribute "
									+ "\nof the "+what+" ( "
									+ cond.getName()
									+ " )  failed."
									+ "\nIt has to be equal to the attribute value  "
									+ "\nof the LHS of a rule."
									+ "\n"+what+" attribute value will be unset.",
								"Attribute value changed",
								JOptionPane.ERROR_MESSAGE);
			}
		}
		return result;
	}	
	
	/** Implements TypeEventListener.typeEventOccurred */
	public void typeEventOccurred(TypeEvent e) {
		// System.out.println("GraGraEditor.typeEventOccurred:: "+e.getMsg()+"
		// "+e.getSource());
		int typeMsg = e.getMsg();
		switch (typeMsg) {
		case TypeEvent.MODIFIED_CHANGED: {
			if (e.getSource() instanceof ArcTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateEdgeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			} else if (e.getSource() instanceof NodeTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateNodeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			}
			// type changed name|shape|color|filled , to do: update
			// itsTypeObjectsMap of graphs
			this.gragra.update();
			updateGraphics();
			break;
		}
		case TypeEvent.MODIFIED_CREATED: {
			if (e.getSource() instanceof ArcTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateEdgeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			} else if (e.getSource() instanceof NodeTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateNodeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			}
			break;
		}
		case TypeEvent.MODIFIED_DELETED: {
			if (e.getSource() instanceof ArcTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateEdgeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			} else if (e.getSource() instanceof NodeTypePropertyEditor) {
				this.evolutionaryLayouterOptionGUI.updateNodeTypeComboBox((EdType) e
						.getUsedObject(), e.getIndexOfObject(), e.getMsg());
			}
			this.gragra.update();
			updateGraphics();
			break;
		}
		case TypeEvent.ERROR:
			updateGraphics();
			break;
		case TypeEvent.UPDATE:
			updateGraphics();
			break;
		case TypeEvent.REFRESH:
			this.typeEditor.refreshTypes();
			break;
		case TypeEvent.TYPE_ANIMATED_CHANGED:
			this.gragra.resetAnimated(true);
			break;
		}
	}

	/** ************************************************************* */

	/** Returns my minimum dimension */
	public Dimension getMinimumSize() {
		return new Dimension(100, 100);
	}

	/** Returns my preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(600, 550);
	}

	/** Returns my main menus */
	public Enumeration<JMenu> getMenus() {
		return this.mainMenus.elements();
	}

	/** Returns the AGG main frame. */
	public JFrame getParentFrame() {
		return this.applFrame;
	}

	/** Gets my tool bar */
	public JToolBar getToolBar() {
		return this.toolBar;
	}

	public JMenu getEditMenu() {
		return this.edit;
	}

	public JMenu getModeMenu() {
		return this.mode;
	}

	public JMenu getTransformMenu() {
		return this.transform;
	}

	public EvolutionaryGraphLayout getGraphLayouter() {
		return this.evolutionaryLayouter;
	}

	public GraphLayouterOptionGUI getGraphLayouterOptionGUI() {
		return this.evolutionaryLayouterOptionGUI;
	}

	/** Sets input parameter editor */
	public void setInputParameterEditorOnTop(Component paramEditor, Rule r) {
		if (this.splitPane0.getTopComponent() == null) {
			// ruleEditor.saveScrollBarValue();
			// graphEditor.saveScrollBarValue();
			// int location = splitPane0.getDividerLocation();

			final JPanel p = new JPanel(new BorderLayout());
			p.setBorder(new TitledBorder("  Rule: " + r.getName()
					+ " -  Please set value of the input parameter(s)  "));
			p.setBackground(new Color(255, 204, 102)); // like Color.ORANGE);
			p.add(paramEditor, BorderLayout.CENTER);
			p.add(new JLabel("   "), BorderLayout.SOUTH);
			final int h = paramEditor.getHeight() + 50;
			p.setPreferredSize(new Dimension(500, h));
			this.splitPane0.setTopComponent(p);
			this.splitPane0.setDividerSize(15);
		}
	}

	/*
	private void setInputParameterEditorOnTop(Rule r) {
		if (ruleEditor.getRule().getBasisRule() != r) {
			return;
		}
		AttrContext ac = r.getMatch().getAttrContext();
		agg.attribute.AttrVariableTuple avt = ac.getVariables();
		inputParameterEditor.setTuple(avt);
		((OpenViewSetting) ((MaskedViewSetting) inputParameterEditor
				.getViewSetting()).getOpenView()).removeFormat(avt.getType());
		setInputParameterEditorOnTop(inputParameterEditor.getComponent(), r);

		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
				"Transform ... Please set value of the input parameter(s)."));
	}
*/
	
	/** Sets my type editor on the top */
	public void unsetInputParameterEditor() {
		if (this.splitPane0.getTopComponent() != null) {
			this.splitPane0.setTopComponent(null);
			this.splitPane0.setDividerSize(0);
		}
	}

	/** Sets attribute editor on the top */
	public void setAttrEditorOnTop(Component attrEditorComponent) {
		if (!(this.splitPane.getTopComponent() instanceof AttrTupleEditor)) {
			((TopEditor) this.attrEditor).selectAttributeEditor(true);
			this.dividerLocation = this.splitPane.getDividerLocation();
			this.splitPane.setTopComponent(attrEditorComponent);
			this.splitPane.setDividerLocation(this.dividerLocation);

			if (this.editmode == EditorConstants.DRAW) {
				setEditMode(EditorConstants.MOVE);
				this.forwardModeCommand("Move");
			}
		}
	}

	/** Sets attribute editor on the bottom */
	public void setAttrEditorOnBottom(Component attrEditorComponent) {
		if (!(this.splitPane.getBottomComponent() instanceof AttrTupleEditor)) {
			((TopEditor) this.attrEditor).selectAttributeEditor(true);
			// graphEditor.saveScrollBarValue();
			this.dividerLocation = this.splitPane.getDividerLocation();
			this.splitPane.setBottomComponent(attrEditorComponent);
			this.splitPane.setDividerLocation(this.dividerLocation);

			this.ruleEditor.getLeftPanel().setAttrEditorActivated(true);
			this.ruleEditor.getRightPanel().setAttrEditorActivated(true);
			if (this.ruleEditor.getNACPanel() != null)
				this.ruleEditor.getNACPanel().setAttrEditorActivated(true);

			if (this.editmode == EditorConstants.DRAW) {
				setEditMode(EditorConstants.MOVE);
				this.forwardModeCommand("Move");
			}
		}
	}

	/** Test whether the attribute editor is on the top */
	public boolean hasAttrEditorOnTop() {
		if (this.splitPane.getTopComponent() instanceof RuleEditor
				&& this.splitPane.getBottomComponent() instanceof GraphEditor)
			return false;
		else if (this.splitPane.getBottomComponent() instanceof GraphEditor) 
			return true;
		else
			return false;
	}

	/** Test whether the attribute editor is on the bottom */
	public boolean hasAttrEditorOnBottom() {
		if (this.splitPane.getTopComponent() instanceof RuleEditor
				&& this.splitPane.getBottomComponent() instanceof GraphEditor)
			return false;
		else if (this.splitPane.getTopComponent() instanceof RuleEditor) 
			return true;
		else
			return false;
	}

	/** Sets my rule editor on the top */
	public void resetRuleEditor() {
		this.dividerLocation = this.splitPane.getDividerLocation();
		if (this.isPostAtomApplCond) {
			this.splitPane.setTopComponent(this.pacMorphs);
			this.splitPane.setDividerLocation(this.dividerLocation);
		} else {
			this.splitPane.setTopComponent(this.ruleEditor);
			this.splitPane.setDividerLocation(this.dividerLocation);
		}
	}

	/** Sets my graph editor on the top */
	public void resetGraphEditor() {
		if (!(this.splitPane.getBottomComponent() instanceof GraphEditor)) {
			this.splitPane.setBottomComponent(this.graphEditor);
			this.splitPane.setDividerLocation(this.dividerLocation);

			this.ruleEditor.getLeftPanel().setAttrEditorActivated(false);
			this.ruleEditor.getRightPanel().setAttrEditorActivated(false);
			if (this.ruleEditor.getNACPanel() != null)
				this.ruleEditor.getNACPanel().setAttrEditorActivated(false);
		}
	}

	public void resetEditor() {
		if (hasAttrEditorOnTop()) {
			resetRuleEditor();
		}
		else if (hasAttrEditorOnBottom()) {
			resetGraphEditor();
		}
	}

	private void refreshTypeUser() {
		if (this.attrTypeCreated) {
			this.gragra.getTypeSet().refreshTypeUsersAfterAttrTypeChanged();
			this.attrTypeCreated = false;
		}
	}
	
	public final EditUndoManager getUndoManager() {
		return this.undoManager;
	}

	public final JButton getUndoButton() {
		return this.buttonUndo;
	}

	public final JButton getUndoStepButton() {
		return this.buttonUndoStep;
	}

	public final JButton getRedoButton() {
		return this.buttonRedo;
	}

	public final JButton getStepButton() {
		return this.buttonStep;
	}

	public final JButton getMatchButton() {
		return this.buttonMatch;
	}

	public final JButton getMatchCompletionButton() {
		return this.buttonCompletion;
	}

	public final JButton getStartButton() {
		return this.buttonStart;
	}

	public final JButton getStopButton() {
		return this.buttonStop;
	}

	public void setSleep(boolean b) {
		this.sleep = b;
	}

	public boolean isSleeping() {
		return this.sleep;
	}

	public JButton getTransformationKindButton() {
		return this.buttonT;
	}

	/** Returns my type editor */
	public final TypeEditor getTypeEditor() {
		return this.typeEditor;
	}

	/** Returns my rule editor */
	public final RuleEditor getRuleEditor() {
		return this.ruleEditor;
	}

	/** Returns my graph editor */
	public final GraphEditor getGraphEditor() {
		return this.graphEditor;
	}

	public int getTypePanelDividerLocation() {
		return this.typeEditor.getTypePalette().getWidthOfPalette();
	}

	public int getTypePanelLastDividerLocation() {
		return this.splitPane1.getLastDividerLocation();
	}

	public void setTypePanelDividerLocation(int l) {
		this.splitPane1.setDividerLocation(this.splitPane1.getWidth() - l);
	}

	public void resetTypePanelWidth() {
		if (this.typesAlwaysOn) {
			this.splitPane1.setDividerLocation(this.splitPane1.getWidth() - 200);
		}
		else {
			this.splitPane1.setDividerLocation(this.splitPane1.getWidth());
		}
	}

	/** Returns options GUI of the gragra transformation. */
	public GraTraOptionGUI getTransformOptionGUI() {
		return this.gragraTransform.getOptionGUI();
	}

	/** Returns options GUI of the gragra transformation. */
	public GraTraMatchOptionGUI getGeneralTransformOptionGUI() {
		return this.gragraTransform.getGeneralOptionGUI();
	}

	/**
	 * Returns my active panel. There are four panels: graph, left rule side,
	 * right rule side, NAC
	 */
	public GraphPanel getActivePanel() {
		return this.activePanel;
	}

	public GraphPanel setActivePanel(final GraphPanel gp) {
		this.activePanel = gp;
		return this.activePanel;
	}

	/** Returns panel of the graph g. */
	public GraphPanel getPanelOfGraph(final EdGraph g) {
		if (g == null)
			return null;
		if (g.equals(this.ruleEditor.getLeftPanel().getGraph()))
			return this.ruleEditor.getLeftPanel();
		else if (g.equals(this.ruleEditor.getRightPanel().getGraph()))
			return this.ruleEditor.getRightPanel();
		else if (g.equals(this.ruleEditor.getNACPanel().getGraph()))
			return this.ruleEditor.getNACPanel();
		else if (g.equals(this.graphEditor.getGraphPanel().getGraph()))
			return this.graphEditor.getGraphPanel();
		else
			return null;
	}

	/** Returns current gragra */
	public final EdGraGra getGraGra() {
		return this.gragra;
	}

	public final EdRule getRule() {
		return this.ruleEditor.getRule();
	}

	public final EdGraph getGraph() {
		return this.graphEditor.getGraph();
	}

	public final EdNAC getNAC() {
		return this.ruleEditor.getNAC();
	}

	public final EdPAC getPAC() {
		return this.ruleEditor.getPAC();
	}

	private void resetUndoManager(EdGraGra gg) {
		if (gg.getUndoManager() != null) {
			this.undoManager = (EditUndoManager) gg.getUndoManager();
		} else {
			/* create common undo manager */
			this.undoManager = new EditUndoManager("Undo last edit");
			this.undoManager.setLimit(1000);
			this.typeEditor.setUndoManager(this.undoManager);
			gg.setUndoManager(this.undoManager);

			this.undoManager.setEnabled(gg.isUndoManagerEnabled());
		}
	}

	public void enableUndoManager(boolean enable) {
		this.undoManager.setEnabled(enable);
		if (!enable) {
			this.buttonUndo.setEnabled(false);
			this.buttonRedo.setEnabled(false);
		}
	}

	/** Sets current gragra specified by the EdGraGra gragra */
	public void setGraGra(EdGraGra gra) {
		if (this.gragra != null) { // old gragra
			this.gragra.getTypeSet().removeTypeEventListener(this);
			if (this.graphEditor.getGraphPanel().getCanvas().hasChanged()
					|| this.ruleEditor.getLeftPanel().getCanvas().hasChanged()
					|| this.ruleEditor.getRightPanel().getCanvas().hasChanged()
					|| this.ruleEditor.getNACPanel().getCanvas().hasChanged())
				this.gragra.setChanged(true);
		}

		if (gra == null) {
			this.gragra = null;
			// update type editor
			this.typeEditor.setGraGra(null);
			// update options GUI for graph layouter
			this.evolutionaryLayouterOptionGUI.setGraGra(null);
			// update options GUI for rule sequences
			this.gragraTransform.setRuleSequences(null, null);
			this.gragraTransform.unsetTransformDebug();
			return;
		}

		this.gragra = gra; // new gragra

		if (!this.attrEditorExists) {
			this.setAttrEditor();
		}
		resetUndoManager(this.gragra);

		this.gragra.getTypeSet().setNodeIconable(this.iconable);
		this.gragra.getTypeSet().addTypeEventListener(this);

		resetGraTraGUI(this.gragra);

		this.noMoreStopBeforeResetGraph = false;

		// update options GUI for graph layouter
		this.evolutionaryLayouterOptionGUI.setGraGra(this.gragra);

		this.nodeToAnimateOnfront = false;

		if (this.applFrame instanceof AGGAppl)
			((AGGAppl) this.applFrame).addToFrameTitle(this.gragra.getDirName(), this.gragra
					.getFileName());

		if (this.modePopupMenu.miUndoManager.getText().equals(
				"Disable Undo Manager")
				&& !((EditUndoManager) this.gragra.getUndoManager()).isEnabled()) {
			this.modePopupMenu.miUndoManager.doClick();
		}
		if (//this.gragra.getGraph().isStaticNodePositionEnabled() &&
				!this.modePopupMenu.miStaticNodePosition.isSelected()) {
			this.modePopupMenu.miStaticNodePosition.doClick();
		}
	}

	protected void resetGraTraGUI(final EdGraGra gg) {
		if (gg.getBasisGraGra().getGraTraOptions().isEmpty()) {
			Vector<String> defaultOpts = new Vector<String>();
			defaultOpts.addElement(GraTraOptions.CSP);
			defaultOpts.addElement(GraTraOptions.INJECTIVE);
			defaultOpts.addElement(GraTraOptions.DANGLING);
			defaultOpts.addElement(GraTraOptions.IDENTIFICATION);
			defaultOpts.addElement(GraTraOptions.NACS);
			defaultOpts.addElement(GraTraOptions.PACS);
			defaultOpts.addElement(GraTraOptions.GACS);
			this.gragraTransform.updateGraTraOptionGUI(defaultOpts);
			gg.getBasisGraGra()
					.setGraTraOptions(this.gragraTransform.getStrategy());

			this.gragraTransform.setRuleSequences(null, null);
		} else {
			this.gragraTransform.updateGraTraOptionGUI(gg.getBasisGraGra()
					.getGraTraOptions());

			this.gragraTransform.setRuleSequences(//gg.getRules(),
					gg.getEnabledRules(),
					gg.getBasisGraGra().getRuleSequenceList());

			if (gg.getBasisGraGra().getMorphismCompletionStrategy() == null) {
				gg.getBasisGraGra().setGraTraOptions(
						this.gragraTransform.getStrategy());
			}

			if (this.gragraTransform.layeredEnabled())
				resetTransformationKindIcon(this.gragraTransform.layeredEnabled(),
						"LT");
			else if (this.gragraTransform.priorityEnabled())
				resetTransformationKindIcon(this.gragraTransform.priorityEnabled(),
						"PT");
			else if (this.gragraTransform.ruleSequenceEnabled())
				resetTransformationKindIcon(this.gragraTransform
						.ruleSequenceEnabled(), "ST");
			else
				resetTransformationKindIcon(false, "NT");
		}
	}

	public void setRule(EdRule r) {
		if (r != null && this.gragra == r.getGraGra()) {
			this.ruleEditor.setRule(r);
		}
	}

	public void setGraph(EdGraph g) {
		if (this.gragra == g.getGraGra()) {
			this.graphEditor.setGraph(g);
			this.nodeToAnimateOnfront = false;
		}
	}

	public void setNAC(EdNAC nac) {
		if (this.ruleEditor.getRule() == nac.getRule()) {
			this.ruleEditor.setNAC(nac);
		}
	}

	public void setPAC(EdPAC pac) {
		if (this.ruleEditor.getRule() == pac.getRule()) {
			this.ruleEditor.setPAC(pac);
		}
	}

	public void setAtomic(EdAtomic a) {
		if (this.gragra == a.getGraGra()) {
			this.ruleEditor.setAtomic(a);
			this.ruleEditor.updateGraphics();
		}
	}

	public void setAtomApplCond(AtomApplCond c) {
		if (this.gragra == this.pacMorphs.getGraGra()) {
			this.pacMorphs.setAtomApplCond(c);
		}
	}

	/*
	 * ************************************* Mode menu procedures
	 * *************************************
	 */

	/** Returns my current mode */
	public final int getEditMode() {
		return this.editmode;
	}

	public final int getLastEditMode() {
		return this.lasteditmode;
	}

	/**
	 * Sets my current mode.
	 */
	public void setEditMode(int mode) {
		if (this.gragra == null || getGraGra().getTypeSet().isEmpty())
			return;

		this.editmode = mode;
		switch (this.editmode) {
		case agg.gui.editor.EditorConstants.DRAW:
			drawModeProc();
			break;
		case agg.gui.editor.EditorConstants.SELECT:
			selectModeProc();
			break;
		case agg.gui.editor.EditorConstants.MOVE:
			moveModeProc();
			break;
		case agg.gui.editor.EditorConstants.ATTRIBUTES:
			attributesModeProc();
			break;
		case agg.gui.editor.EditorConstants.INTERACT_RULE:
			ruleDefModeProc();
			break;
		case agg.gui.editor.EditorConstants.INTERACT_NAC:
			nacDefModeProc();
			break;
		case agg.gui.editor.EditorConstants.INTERACT_PAC:
			pacDefModeProc();
			break;
		case agg.gui.editor.EditorConstants.INTERACT_AC:
			acDefModeProc();
			break;	
		case agg.gui.editor.EditorConstants.INTERACT_MATCH:
			matchDefModeProc();
			break;
		case agg.gui.editor.EditorConstants.MAP:
			mapModeProc();
			break;
		case agg.gui.editor.EditorConstants.UNMAP:
			unmapModeProc();
			break;
		case agg.gui.editor.EditorConstants.MAPSEL:
			mapselModeProc();
			break;
		case agg.gui.editor.EditorConstants.UNMAPSEL:
			unmapselModeProc();
			break;
		case agg.gui.editor.EditorConstants.VIEW:
			viewModeProc();
			break;
		default:
			drawModeProc();
			break;
		}
		fireEditEvent(new EditEvent(this, mode));
	}

	public void enableMagicEdgeSupport(boolean b) {
		this.ruleEditor.getLeftPanel().getCanvas().setMagicEdgeSupportEnabled(b);
		this.ruleEditor.getRightPanel().getCanvas().setMagicEdgeSupportEnabled(b);
		this.ruleEditor.getLeftCondPanel().getCanvas().setMagicEdgeSupportEnabled(b);
		this.graphEditor.getGraphPanel().getCanvas().setMagicEdgeSupportEnabled(b);
	}

	private void drawModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.DRAW);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.DRAW);
		this.lasteditmode = EditorConstants.DRAW;
	}

	private void selectModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.SELECT);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.SELECT);
		this.lasteditmode = EditorConstants.SELECT;
	}

	private void moveModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.MOVE);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.MOVE);
		this.lasteditmode = EditorConstants.MOVE;
	}

	private void attributesModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.ATTRIBUTES);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.ATTRIBUTES);
		this.lasteditmode = EditorConstants.ATTRIBUTES;
	}

	private void ruleDefModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_RULE);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_RULE);
	}

	private void nacDefModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_NAC);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_NAC);
	}

	private void pacDefModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_PAC);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_PAC);
	}

	private void acDefModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_AC);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_AC);
	}
	
	private void matchDefModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.INTERACT_MATCH);
	}

	private void mapModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.MAP);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.MAP);
		this.lasteditmode = EditorConstants.MAP;
	}

	private void unmapModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.UNMAP);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.UNMAP);
		this.lasteditmode = EditorConstants.UNMAP;
	}

	private void mapselModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.MAPSEL);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.MAPSEL);
	}

	private void unmapselModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.UNMAPSEL);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.UNMAPSEL);
	}

	private void viewModeProc() {
		this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.VIEW);
		this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.VIEW);
	}

	/*
	 * ******************************************* Edit menu procedures
	 * *******************************************
	 */

	/** Shows attribute editor */
	public void attrsProc() {
		if (this.editmode == EditorConstants.VIEW
				|| (this.ruleEditor.getRule() == null && this.graphEditor.getGraph() == null)
				|| !getGraGra().getGraph().isEditable())
			return;

		if (!this.graphEditor.hasSelection() && !this.ruleEditor.hasSelection()) {
			JOptionPane.showMessageDialog(this.applFrame, "No object is selected.");
			// fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, "No
			// object is selected."));
			this.errMsg = true;
			return;
		}
		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;
		if (this.ruleEditor.hasOneSelection() && !this.graphEditor.hasSelection()) {
			// show attribute editor within the graph panel
			EdGraphObject ego = null;
			if (this.ruleEditor.getRule().getLeft().hasOneSelection())
				ego = this.ruleEditor.getRule().getLeft().getSelectedObjs()
						.firstElement();
			else if (this.ruleEditor.getRule().getRight().hasOneSelection())
				ego = this.ruleEditor.getRule().getRight().getSelectedObjs()
						.firstElement();
			else if (this.ruleEditor.getNAC().hasOneSelection())
				ego = this.ruleEditor.getNAC().getSelectedObjs().firstElement();
			else if (this.ruleEditor.getPAC().hasOneSelection())
				ego = this.ruleEditor.getPAC().getSelectedObjs().firstElement();
			else if (this.ruleEditor.getNestedAC().hasOneSelection())
				ego = this.ruleEditor.getNestedAC().getSelectedObjs().firstElement();
			if (ego != null) {
				if (getAttrEditor(ego) != null) {
					this.attrEditor.enableContextEditor(true);
					setAttrEditorOnBottom(this.attrEditor.getComponent());
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							"To get the graph editor again click on the graph in the tree view."));
				}
			} else {
				JOptionPane.showMessageDialog(this.applFrame,
						"No object is selected.");
				// fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
				// "No object is selected."));
				this.errMsg = true;
			}
		} else if (this.graphEditor.hasOneSelection() && !this.ruleEditor.hasSelection()) {
			// show attribute editor within the rule panel
			if (getAttrEditor(this.graphEditor.getGraph().getSelectedObjs()
					.firstElement()) != null) {
				this.attrEditor.enableContextEditor(false);
				setAttrEditorOnTop(this.attrEditor.getComponent());
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"To get the rule editor again click on the rule in the tree view."));
			}
		} else {
			JOptionPane.showMessageDialog(this.applFrame,
					"Too many selections.\nPlease select only one object.");
			// fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, "Too
			// many selections."));
			this.errMsg = true;
		}
	}

	/** Deletes selected nodes and edges */
	public boolean deleteProc() {
		if (this.editmode == EditorConstants.VIEW
				|| !getGraGra().isEditable()
				|| (!this.graphEditor.hasSelection() && !this.ruleEditor.hasSelection()))
			return false;

		this.errMsg = false;
		int answer = removeWarning("Do you want really to delete \n selected object(s) ?");
		if (answer == JOptionPane.YES_OPTION) {
			this.ruleEditor.deleteProc();
			if (this.graphEditor.deleteProc())
				this.ruleEditor.getRule().update();
		}
		return true;
	}

	/** Copies selected nodes and edges */
	public void copyProc() {
		if ((this.editmode == EditorConstants.VIEW)
				|| (this.ruleEditor.getRule() == null && this.graphEditor.getGraph() == null)
				|| !getGraGra().isEditable())
			return;

		this.errMsg = false;
		if ((this.splitPane.getTopComponent() instanceof RuleEditor)
				&& (this.splitPane.getBottomComponent() instanceof GraphEditor)) {

			if (!this.graphEditor.hasSelection() && !this.ruleEditor.hasSelection()) {
				JOptionPane.showMessageDialog(this.applFrame,
						"No object to copy was selected.");
				this.errMsg = true;
				return;
			} else if (this.ruleEditor.hasSelection()
					&& !this.ruleEditor.hasOneSelection()) {
				JOptionPane
						.showMessageDialog(
								this.applFrame,
								"Too many selections. \nPlease select objects within the same graph panel only.");
				this.errMsg = true;
				return;
			} else if (this.ruleEditor.hasSelection() && this.graphEditor.hasSelection()) {
				JOptionPane
						.showMessageDialog(
								this.applFrame,
								"Too many selections. \nPlease select objects within the same graph panel only.");
				this.errMsg = true;
				return;
			}

			// Selektierte Objekte werden zu einem Graph gemacht,
			// um evtl. in einen anderen Graphen zu kopieren
			EdGraph gCopy = this.ruleEditor.getSelectedAsGraph();
			if (gCopy != null) {
				this.ruleEditor.setGraphToCopy(gCopy);
				this.graphEditor.setGraphToCopy(gCopy);
				this.graphEditor.setSourceOfCopy(this.ruleEditor.getSourceOfCopy());
			} else {
				gCopy = this.graphEditor.getSelectedAsGraph();
				if (gCopy != null) {
					this.ruleEditor.setGraphToCopy(gCopy);
					this.ruleEditor.setSourceOfCopy(this.graphEditor.getGraph());
					this.graphEditor.setGraphToCopy(gCopy);
				}
			}
			// System.out.println("GraGraEditor.copy: "+gCopy);
			if (gCopy != null) {
				this.graphToCopy = gCopy;
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"To get a copy please click on the background of a panel."));
				this.ruleEditor.setEditMode(agg.gui.editor.EditorConstants.COPY);
				this.graphEditor.setEditMode(agg.gui.editor.EditorConstants.COPY);
			} else {
				JOptionPane.showMessageDialog(this.applFrame, "Bad selection. "
						+ "\nPlease check selected edges."
						+ "\nSource and target nodes should be selected, too.");
				this.errMsg = true;
			}
		}
	}

	public void pasteProc() {
		if (this.editmode == EditorConstants.VIEW
				|| this.ruleEditor.getRule() == null && this.graphEditor.getGraph() == null
				|| !getGraGra().isEditable())
			return;

		this.errMsg = false;
		if ((this.splitPane.getTopComponent() instanceof RuleEditor)
				&& (this.splitPane.getBottomComponent() instanceof GraphEditor)) {
			// System.out.println("GraGraEditor.paste: "+graphToCopy);
			if (this.graphToCopy != null) {
				this.ruleEditor.setGraphToCopy(this.graphToCopy);
				this.ruleEditor.setSourceOfCopy(null);
				this.graphEditor.setGraphToCopy(this.graphToCopy);
				this.graphEditor.setSourceOfCopy(null);
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						"To paste a copy click on the background of the appropriate graph panel."));
				this.ruleEditor.setEditMode(EditorConstants.COPY);
				this.graphEditor.setEditMode(EditorConstants.COPY);
			} else {
				JOptionPane
						.showMessageDialog(
								this.applFrame,
								"Nothing was copied.\n"
										+ "To copy and paste graph objects into an other graph:\n"
										+ "- select nodes and edges and click the Copy icon \n"
										+ "- choose the target graph panel \n"
										+ "- click the Paste icon and finally \n"
										+ "- click on the background of the panel to place the copy.");
			}
		}
	}

	public void resetAfterCopy() {
		this.ruleEditor.setGraphToCopy(null);
		this.graphEditor.setGraphToCopy(null);
	}

	/** Selects all nodes and edges */
	public void selectAllProc() {
		if(editmode != EditorConstants.MOVE) {
			this.forwardModeCommand("Move");
			this.setEditMode(EditorConstants.MOVE);
		}
		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;
		if (this.activePanel != null) {
			this.activePanel.selectAll();
		} else {
			if (this.splitPane.getTopComponent() instanceof RuleEditor)
				this.ruleEditor.selectAllProc();
			if (this.splitPane.getBottomComponent() instanceof GraphEditor)
				this.graphEditor.selectAllProc();
		}
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
	}

	/** Selects nodes of selected node type */
	public void selectNodeTypeProc() {
		if(editmode != EditorConstants.MOVE) {
			this.forwardModeCommand("Move");
			this.setEditMode(EditorConstants.MOVE);
		}
		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;
		if (this.activePanel != null) {
			this.activePanel.selectNodesOfSelectedNodeType();
		} else {
			if (this.splitPane.getTopComponent() instanceof RuleEditor)
				this.ruleEditor.selectNodeTypeProc();
			if (this.splitPane.getBottomComponent() instanceof GraphEditor)
				this.graphEditor.selectNodeTypeProc();
		}
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
	}

	/** Selects edges of selected edge type */
	public void selectArcTypeProc() {
		if(editmode != EditorConstants.MOVE) {
			this.forwardModeCommand("Move");
			this.setEditMode(EditorConstants.MOVE);
		}
		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;
		if (this.activePanel != null) {
			this.activePanel.selectArcsOfSelectedArcType();
		} else {
			if (this.splitPane.getTopComponent() instanceof RuleEditor)
				this.ruleEditor.selectArcTypeProc();
			if (this.splitPane.getBottomComponent() instanceof GraphEditor)
				this.graphEditor.selectArcTypeProc();
		}
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
	}

	/** Deselects all nodes and edges */
	public void deselectAllProc() {
		// if(editmode == EditorConstants.VIEW) return;
		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;
		if (this.activePanel != null) {
			this.activePanel.deselectAll();
		} else {
			if (this.splitPane.getTopComponent() instanceof RuleEditor)
				this.ruleEditor.deselectAllProc();
			if (this.splitPane.getBottomComponent() instanceof GraphEditor)
				this.graphEditor.deselectAllProc();
		}
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
	}

	/** Straight all selected arcs */
	public void doStraightenArcsProc() {
		if (this.editmode == EditorConstants.VIEW)
			return;
		if (this.ruleEditor.getRule() == null && this.graphEditor.getGraph() == null)
			return;
		if (!getGraGra().getGraph().isEditable())
			return;

		if (!this.graphEditor.hasSelection() && !this.ruleEditor.hasSelection()) {
			int answer = 0;
//			Object[] options = { "OK", "Cancel" };
			// JOptionPane.showOptionDialog(applFrame,
			// "<html><body>"
			// +"Sorry! Any edge isn't selected.<br>"
			// +"Do you want to straighten all edges of the host graph?<br>"
			// +"(To disable this setting, please select an edge and call <br>"
			// +"the menu item again. Next graph update will take it in
			// account.)"
			// +"</body></html>",
			// "Straighten edge", JOptionPane.DEFAULT_OPTION,
			// JOptionPane.QUESTION_MESSAGE, null, options,
			// options[0]);
			if (answer == 0) {
				// if (splitPane.getTopComponent() instanceof RuleEditorImpl) {
				// ruleEditor.setStraightenArcs(true);
				// ruleEditor.updateGraphics();
				// }
				if (this.splitPane.getBottomComponent() instanceof GraphEditor) {
					this.graphEditor.setStraightenArcs(true);
					this.graphEditor.updateGraphics();
				}
			}
			return;
		}

		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));

		this.errMsg = false;
		if (this.splitPane.getTopComponent() instanceof RuleEditor) {
			this.ruleEditor.setStraightenArcs(false);
			this.ruleEditor.straightenArcsProc();
		}
		if (this.splitPane.getBottomComponent() instanceof GraphEditor) {
			this.graphEditor.setStraightenArcs(false);
			this.graphEditor.straightenArcsProc();
		}
	}

	/** Create an identical rule */
	public void doIdenticRuleProc() {
		if ((this.editmode == EditorConstants.VIEW)
				|| (this.ruleEditor.getRule() == null)
				|| !getGraGra().getGraph().isEditable())
			return;

		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;

		this.ruleEditor.doIdenticRule();
		if (!this.ruleEditor.getMsg().equals(""))
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					this.ruleEditor.getMsg()));
	}

	/** Create an identical NAC (Negative Application Condition) */
	public void doIdenticNacProc() {
		if ((this.editmode == EditorConstants.VIEW)
				|| (this.ruleEditor.getRule() == null)
				|| !getGraGra().getGraph().isEditable())
			return;

		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;

		this.ruleEditor.doIdenticNAC();
		if (!this.ruleEditor.getMsg().equals(""))
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					this.ruleEditor.getMsg()));
	}

	/** Create an identical PAC (Positive Application Condition) */
	public void doIdenticPacProc() {
		if ((this.editmode == EditorConstants.VIEW)
				|| (this.ruleEditor.getRule() == null)
				|| !getGraGra().getGraph().isEditable())
			return;

		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;

		this.ruleEditor.doIdenticPAC();
		if (!this.ruleEditor.getMsg().equals(""))
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					this.ruleEditor.getMsg()));
	}

	/** Create an identical GAC (General Application Condition) */
	public void doIdenticGACProc() {
		if ((this.editmode == EditorConstants.VIEW)
				|| (this.ruleEditor.getRule() == null)
				|| !getGraGra().getGraph().isEditable())
			return;

		if (this.errMsg)
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, ""));
		this.errMsg = false;

		this.ruleEditor.doIdenticGAC();
		if (!this.ruleEditor.getMsg().equals(""))
			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					this.ruleEditor.getMsg()));
	}
	
	public void setInterpreting(boolean enable) {
		this.interpreting = enable;
	}

	public void setLayering(boolean enable) {
		this.layering = enable;
	}

	public void setTransformRuleSequences(boolean enable) {
		this.sequences = enable;
	}

	public void doView(String command) {
		setEditMode(EditorConstants.VIEW);
		resetModeMenu(null);
		this.modePopupMenu.deselectAll();
		fireEditEvent(new EditEvent(this, EditorConstants.VIEW));
	}

	public void doPreferencesProc(String command) {
		if (command.equals("bold"))
			setFontStyle(Font.BOLD);
		else if (command.equals("italic"))
			setFontStyle(Font.ITALIC);
		else if (command.equals("plain"))
			setFontStyle(Font.PLAIN);
		else if (command.equals("tiny"))
			setFontSize(0);
		else if (command.equals("small"))
			setFontSize(8);
		else if (command.equals("large"))
			setFontSize(10);
		else if (command.equals("LARGE"))
			setFontSize(12);
		else if (command.equals("0.2"))
			setScale(0.2);
		else if (command.equals("0.3"))
			setScale(0.3);
		else if (command.equals("0.5"))
			setScale(0.5);
		else if (command.equals("0.7"))
			setScale(0.7);
		else if (command.equals("1.0"))
			setScale(1.0);
		else if (command.equals("1.5"))
			setScale(1.5);
		else if (command.equals("2.0"))
			setScale(2.0);
	}

	public void setScalingGraphOnly(boolean b) {
		this.scaleGraphOnly = b;
	}

	private void setFontStyle(int fstyle) {
		this.ruleEditor.getLeftPanel().getCanvas().setFontStyle(fstyle);
		this.ruleEditor.getRightPanel().getCanvas().setFontStyle(fstyle);
		this.ruleEditor.getNACPanel().getCanvas().setFontStyle(fstyle);
		this.graphEditor.getGraphPanel().getCanvas().setFontStyle(fstyle);
		updateGraphics();
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_FONT_STYLE, fstyle));
	}

	private void setFontSize(int fsize) {
		this.ruleEditor.getLeftPanel().getCanvas().setFontSize(fsize);
		this.ruleEditor.getRightPanel().getCanvas().setFontSize(fsize);
		this.ruleEditor.getNACPanel().getCanvas().setFontSize(fsize);
		this.graphEditor.getGraphPanel().getCanvas().setFontSize(fsize);
		updateGraphics();
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_FONT_SIZE, fsize));
	}

	public void setScale(double scale) {
		if (!this.scaleGraphOnly) {
			this.ruleEditor.getLeftPanel().getCanvas().setScale(scale);
			this.ruleEditor.getRightPanel().getCanvas().setScale(scale);
			this.ruleEditor.getNACPanel().getCanvas().setScale(scale);
			this.graphEditor.getGraphPanel().getCanvas().setScale(scale);
			updateGraphics();
		} else {
			this.graphEditor.getGraphPanel().getCanvas().setScale(scale);
			this.graphEditor.updateGraphics();
		}

		fireEditEvent(new EditEvent(this, EditEvent.EDIT_SCALE, scale));
	}

	public final AttrTopEditor setAttrEditorOnTopForGraphObject(
			EdGraphObject ego) {
		this.attrEditor.enableContextEditor(false);
		if (getAttrEditor(ego) != null) {
			setAttrEditorOnTop(this.attrEditor.getComponent());
			return this.attrEditor;
		} 
		return null;
	}

	public final AttrTopEditor setAttrEditorOnBottomForGtaphObject(
			EdGraphObject ego) {
		this.attrEditor.enableContextEditor(true);
		if (getAttrEditor(ego) != null) {
			setAttrEditorOnBottom(this.attrEditor.getComponent());
			return this.attrEditor;
		} 
		return null;
	}

	public final AttrTopEditor setAttrEditorOnTopForType(Type aType) {
		AttrTuple anAttrTuple = aType.getAttrType();
		if (anAttrTuple == null) {
			aType.createAttributeType();
			anAttrTuple = aType.getAttrType();
		}
		this.attrEditor.enableContextEditor(false);
		this.attrEditor.setTuple(anAttrTuple);

		((TopEditor) this.attrEditor).setTitleText("   Type Name :   "
				+ aType.getName());

		if (!(this.splitPane.getBottomComponent() instanceof GraphEditor)) {
			this.resetGraphEditor();
		}

		setAttrEditorOnTop(this.attrEditor.getComponent());
		return this.attrEditor;
	}

	private boolean canChangeAttr(EdGraphObject ego) {
		Graph g =  ego.getContext().getBasisGraph();
		EdRule er = this.ruleEditor.getRule();
		if (er != null 
				&& er.getBasisRule() != null 
				&& er.getBasisRule().getRuleScheme() != null
				&& er.getBasisRule().isElement(g)) {
			RuleScheme rs = er.getBasisRule().getRuleScheme();		
			Rule r = rs.getMultiRule(g);
			if (r != null) {
				if (((MultiRule) r).getEmbeddingLeft().getCodomainObjects().contains(ego.getBasisObject())
						|| ((MultiRule) r).getEmbeddingRight().getCodomainObjects().contains(ego.getBasisObject())) {
					// warning msg
					String what = ego.isNode()? "node": "edge";
					
					JOptionPane.showMessageDialog(this.applFrame, 
							"Cannot modify this "+what+" . It should be changed from its kernel rule.", 
							"Attribute of Graph Object", 
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}
	
	/** Returns my attribute editor for the specified graph object */
	private final AttrTopEditor getAttrEditor(EdGraphObject ego) {
		if (!canChangeAttr(ego)) {
			return null;			
		}
		
		if (ego.isNode()) {
			EdNode en = (EdNode) ego;
			if (en.getBasisNode() != null) {

				if (en.getBasisNode().getAttribute() == null) {
					en.createAttributeInstance();
				}

				this.graphObjectOfAttrInstance = en.getBasisNode();

				this.attrEditor.setTuple(en.getBasisNode().getAttribute());
				((TopEditor) this.attrEditor).setTitleText("   Node Type Name :   "
						+ en.getBasisNode().getType().getName());
				en.setAttrViewSetting(this.attrEditor.getViewSetting());

				en.setGraphPanel(this.activePanel);

				this.attrMemberCount = en.getBasisObject().getNumberOfAttributes();

				if (this.undoManager != null && this.undoManager.isEnabled()) {
					Vector<EdGraphObject> vec = getGraGra()
							.getGraphObjectsOfType(en.getType(), true);
					// System.out.println(attrMemberCount+" objs: "+vec.size());
					if (!vec.isEmpty()) {
						this.getActivePanel().getGraph()
								.addChangedAttributeToUndo(vec);
					}
				}
			}
		} else if (ego.isArc()) {
			EdArc ea = (EdArc) ego;
			if (ea.getBasisArc() != null) {
				if (ea.getBasisArc().getAttribute() == null) {
					ea.createAttributeInstance();
				}
				this.graphObjectOfAttrInstance = ea.getBasisArc();

				this.attrEditor.setTuple(ea.getBasisArc().getAttribute());
				((TopEditor) this.attrEditor).setTitleText("   Edge Type Name :   "
						+ ea.getBasisArc().getType().getName());
				ea.setAttrViewSetting(this.attrEditor.getViewSetting());

				ea.setGraphPanel(this.activePanel);

				if (this.undoManager != null && this.undoManager.isEnabled()) {
					Vector<EdGraphObject> vec = new Vector<EdGraphObject>(
							this.getGraGra().getTypeSet().getTypeUsers(ea.getType()));
					if (!vec.isEmpty()) {
						for (int i=0; i<vec.size(); i++) {
							EdGraphObject go = vec.get(i);
							if (go.getContext().getGraGra() == null) {								
								System.out.println(go.getContext().getName()+"   "+go.getBasisObject()+"       GraGra NULL");
//								vec.remove(i);
//								i--;
							}
						}
						this.activePanel.getGraph().addChangedAttributeToUndo(vec);
					}
				}
			}
		}
		return this.attrEditor;
	}

	public GraGraTransform getGraGraTransform() {
		return this.gragraTransform;
	}

	/** Updates all my graph panels. */
	public void updateGraphics() {
		synchronized (this) {
			this.ruleEditor.getLeftPanel().updateGraphics();
			this.ruleEditor.getRightPanel().updateGraphics();
			this.ruleEditor.getLeftCondPanel().updateGraphics();
			this.graphEditor.getGraphPanel().updateGraphics(false);
		}
	}

	protected void updateGraphics(boolean resizeGraphPanelIfNeeded) {
		synchronized (this) {
			this.ruleEditor.getLeftPanel().updateGraphics();
			this.ruleEditor.getRightPanel().updateGraphics();
			this.ruleEditor.getLeftCondPanel().updateGraphics();
			this.graphEditor.getGraphPanel()
					.updateGraphics(resizeGraphPanelIfNeeded);
		}
	}

	protected void refreshAfterWaitBeforeApplyRule() {
		if (this.trafoEventKey == TransformEvent.MATCH_VALID
				&& this.ruleEditor.getRule().getBasisRule().isWaitBeforeApplyEnabled()) {
			this.graphEditor.getGraph().clearMarks();
		}
	}
	
	protected void ruleEditorUpdateGraphics() {
		synchronized (this) {
			this.ruleEditor.getLeftPanel().updateGraphics();
			this.ruleEditor.getRightPanel().updateGraphics();
			this.ruleEditor.getNACPanel().updateGraphics();
		}
	}

	protected void graphEditorUpdateGraphics(boolean resizeGraphPanelIfNeeded) {
		this.graphEditor.getGraphPanel().updateGraphics(resizeGraphPanelIfNeeded);
	}

	/** Clears my type, rule and graph editors. */
	public void clear() {
		this.typeEditor.removeAll();
		setGraGra(null);
		this.graphEditor.setGraph(null);
		this.ruleEditor.setNAC(null);
		this.ruleEditor.setPAC(null);
		this.ruleEditor.setRule(null);
		this.pacMorphs.setGraGra(null);
		this.pacMorphs.setAtomApplCond(null);
		this.nodeAnimation.dispose();
		updateGraphics();
		fireEditEvent(new EditEvent(this, EditEvent.NO_EDIT_PROCEDURE, ""));
	}

	/**
	 * Allows replace nodes by icons, if the node type has an icon
	 * representation.
	 */
	public void setNodeIconable(boolean iconable) {
		this.iconable = iconable;
		if (this.gragra != null) {
			this.gragra.getTypeSet().setNodeIconable(iconable);
			updateGraphics();
		}
	}

	/** Activates or deactivates this editor, menus and tool bar */
	public void resetEnabled(boolean enable) {
		this.setEnabled(enable);
		this.edit.setEnabled(enable);
		this.mode.setEnabled(enable);
		this.transform.setEnabled(enable);
	}

	/** The message s will be fired to all EditEventListener */
	public void setMsg(String s) {
		fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE, s));
	}

	public void setExportJPEG(GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
		this.modePopupMenu.setExportJPEG(jpg);
		this.ruleEditor.setExportJPEG(jpg);
		this.graphEditor.setExportJPEG(jpg);
		this.pacMorphs.setExportJPEG(jpg);
		if (this.attrEditorExists) {
			((TopEditor) this.attrEditor).setExportJPEG(jpg);
		}
	}

	/** Returns the current completion strategy */
	public MorphCompletionStrategy getMorphCompletionStrategy() {
		return this.gragraTransform.getStrategy();
	}

	public void startLayeredTransform() {
		this.layeredTransform = this.gragraTransform.createRuleLayerTransform();
		this.layering = true;
		this.gragraTransform.startTransformLayered(getGraGra());
	}

	public void startInterpreterTransform() {
		if (this.gragraTransform.priorityEnabled()) {
			this.interpreter = this.gragraTransform.createRulePriorityTransform();
			this.interpreting = true;
			this.gragraTransform.startRulePriorityTransformInterpreter(getGraGra());
		} else {
			this.interpreter = this.gragraTransform.createInterpreterTransform();
			this.interpreting = true;
			this.gragraTransform.startTransformInterpreter(getGraGra());
		}
	}

	public void startRuleSequenceTransform() {
		if (this.gragra.getBasisGraGra().getCurrentRuleSequence() != null) {
			if (this.gragra.getBasisGraGra().getCurrentRuleSequence().isGraphUsedInObjFlow()
					 && this.gragra.getBasisGraGra().getGraph() != this.gragra.getBasisGraGra().getCurrentRuleSequence().getGraph()) {
				JOptionPane.showMessageDialog(this.applFrame,
						 "The current graph and the graph of output objects \n"
						+"of the object flow of the current sequence must be the same.\n"
						+"Please select the graph : "+this.gragra.getBasisGraGra().getCurrentRuleSequence().getGraph().getName(),
						"Cannot transform!", JOptionPane.ERROR_MESSAGE);
				this.brakeTrafoRS();
				return;				
			}
			if (!this.gragra.getBasisGraGra().getCurrentRuleSequence()
					.isObjFlowValid()) {
				String error = "";
				if (this.gragra.getBasisGraGra().getCurrentRuleSequence().getMessageOfInvalidObjectFlow() 
						== RuleSequence.OBJECT_FLOW_TRANSITIVE_CLOSURE_FAILED)
					error = "\n( transitive closure failed )";
				else if (this.gragra.getBasisGraGra().getCurrentRuleSequence().getMessageOfInvalidObjectFlow() 
						== RuleSequence.OBJECT_FLOW_PERSISTENT_FAILED)
					error = "\n( persistent object flow failed )";
					
				JOptionPane.showMessageDialog(this.applFrame,
						"Object flow of the current rule sequence is not valid!"
						+error,
						"Cannot transform!", JOptionPane.ERROR_MESSAGE);
				
				return;
			}
	
			if (this.gragra.getBasisGraGra().trafoByApplicableRuleSequence()) {
				this.transformRuleSequences = this.gragraTransform
						.createRuleSequenceTransform(true);
			} else {
				this.transformRuleSequences = this.gragraTransform
						.createRuleSequenceTransform(false);
			}
	
			this.sequences = true;
			this.gragraTransform.startTransformByRuleSequence(this.gragra, this.gragra
					.getBasisGraGra().getCurrentRuleSequence());
			// System.out.println(gragraTransform.getRuleSequencesAsText());
		}
	}

	private void brakeTrafoRS() {
		getGraGra().getGraph().setTransformChangeEnabled(false);

		if (this.sequences) {
			this.sleep = false;

			resetIconsIfTransformInterpret(true);
			this.lasteditmode = EditorConstants.MOVE;
			setEditMode(this.lasteditmode);
			this.forwardModeCommand("Move");

			disableStopMenuItem();
			this.buttonT.setEnabled(true);
			this.buttonLayout.setEnabled(true);

			if (this.transformRuleSequences != null) {
				removeEditEventListener(this.transformRuleSequences);
				this.transformRuleSequences.dispose();
			}
			this.sequences = false;

			addEditEventListener(this.gragraTransform.getTransformDebugger());

			if (this.gragraTransform.checkRuleApplicabilityEnabled())
				this.gragraTransform.getApplicableRules(getGraGra());

			fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
					"Transformation of  <" + getGraGra().getName() + "  broken"));
		}

		selectToolBarTransformItem("");

		this.typeEditor.setEnabled(true);

		if (this.undoManager != null && this.undoManager.isEnabled()
				&& this.undoManager.canUndo()) {
			this.buttonUndo.setEnabled(true);
			if (this.undoManager.canRedo())
				this.buttonRedo.setEnabled(true);
			setUndoStepButtonEnabled(true);
		}
		if (getGraGra().isAnimated()) {
			this.undoManager.setEnabled(true);
		}

		if (this.applFrame instanceof AGGAppl)
			((AGGAppl) this.applFrame).getGraGraTreeView().getTree().treeDidChange();

		if (this.evolutionaryLayouter.getWriteMetricValues()) {
			if (this.jpgPath != null && !this.jpgPath.equals(""))
				writeMetricValues(this.jpgPath + File.separator
						+ this.getGraGra().getFileName() + "_metrics.log");
		}

		setActivePanel(this.graphEditor.getGraphPanel());
	}
	
	
	public boolean isEditable() {
		if (!getGraGra().getGraph().isEditable()) {
			updateGraphics();
			JOptionPane
					.showMessageDialog(
							this.applFrame,
							"Grammar is locked by Critical Pair Analysis \nor transformation is running.",
							"Cannot transform", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean isImageViewModeSelected() {
		return this.mode.getItem(7).isSelected();
	}

	public boolean isAnimationRunning() {
		return this.animationThread;
	}

	public NodeAnimation getNodeAnimation() {
		return this.nodeAnimation;
	}

	public boolean isGraGraReadyToTransform(EdGraGra gg) {
		String msgstr = "";
		if (!gg.getBasisGraGra().isGraphReadyForTransform()) {
			msgstr = "The host graph of this grammar is not fine.\n"
					+ "Please check attribute settings of the objects.";
		} else {
			Object obj = gg.getBasisGraGra().isReadyToTransform(true);
			if (obj != null) {
				if (obj instanceof Rule) {
					msgstr = "The rule  <" + ((Rule) obj).getName()
							+ ">  of this grammar is not fine.\n"
							+ "Please check attribute settings of the objects.";
				} else if (obj instanceof AtomConstraint) {
					msgstr = "The graph constraint  <"
							+ ((AtomConstraint) obj).getName()
							+ ">  of this grammar is not fine.\n"
							+ "Please check attribute settings of the objects.";
				}
			} else if (this.gragraTransform.ruleSequenceEnabled()
						&& gg.getBasisGraGra().getCurrentRuleSequence() == null) {
				JOptionPane.showMessageDialog(this.applFrame, 
						"Graph transformation by Rule Sequence failed.\n"
						+"There isn't any rule sequence defined.",
						"Cannot transform!", JOptionPane.WARNING_MESSAGE);
				selectToolBarTransformItem("");
				setEditMode(this.lasteditmode);
				return false;
			}				
		}
		if (!msgstr.equals("")) {
			updateGraphics();
			JOptionPane.showMessageDialog(this.applFrame, "Not ready to transform!"
					+ "\n" + msgstr, "Cannot transform",
					JOptionPane.ERROR_MESSAGE);
			selectToolBarTransformItem("");
			setEditMode(this.lasteditmode);
			return false;
		}
		return true;
	}

	public void doPrepareTransformProc() {
		resetIconsIfTransformInterpret(false);
		this.lasteditmode = getEditMode();
		setEditMode(EditorConstants.VIEW);
		this.gragraTransform.getApplicableRules(getGraGra(), true);

		this.buttonT.setEnabled(false);
		this.buttonUndo.setEnabled(false);
		this.buttonUndoStep.setEnabled(false);
		this.buttonRedo.setEnabled(false);
		this.buttonLayout.setEnabled(false);
	}

	public void doStopTransformLayeredProc() {
		this.sleep = false;
		this.gragraTransform.stopTransformLayered();
		if (!this.gragraTransform.breakAllLayerEnabled()) {
			this.selectToolBarTransformItem("start");
		}
	}

	public void doStopTransformInterpreterProc() {
		this.sleep = false;
		if (this.interpreter == this.gragraTransform.getInterpreterTransform())
			this.gragraTransform.stopTransformInterpreter();
		else
			this.gragraTransform.stopRulePriorityTransformInterpreter();
	}

	public void doStopTransformRuleSequencesProc() {
		this.sleep = false;
		this.gragraTransform.stopTransformRuleSequences();
	}

	public void doPrepareInteractiveMatchProc() {
		this.lasteditmode = getEditMode();
		if (getGraGra() != null && this.ruleEditor.getRule() != null) {
			if (!getGraGra().getGraph().isEditable()) {
				JOptionPane.showMessageDialog(this.applFrame, "<html><body>"
						+ "Grammar is locked by critical pair analysis<br> "
						+ "or transformation is already running."
						+ "</body<</html>", "Cannot transform",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!this.ruleEditor.getRule().isEditable()) {
				return;
			}
			if (!this.interpreting && !this.layering && !this.sequences) {
				
				((AttrTupleManager) AttrTupleManager.getDefaultManager()).setVariableContext(false);
				
				this.forwardModeCommand("Move");
				selectToolBarTransformItem("match");

				if (this.graphEditor.getGraph().adjustTypeObjectsMap()) {
					getGraGra().destroyAllMatches();
				}

				if (this.gragraTransform.checkRuleApplicabilityEnabled()) {
					// check if the rule applicable
					if (!this.gragraTransform.getApplicableRules(getGraGra())
							.contains(this.ruleEditor.getRule().getBasisRule())) {
						JOptionPane.showMessageDialog(
								this.applFrame,
								"Current rule isn't applicable. \nPlease select an applicable rule.",
								"Cannot match",
								JOptionPane.WARNING_MESSAGE);
						selectToolBarTransformItem("");
						return;
					}
				}
				this.gragraTransform.matchDef(this.ruleEditor.getRule());
			}
		}
	}

	public void doPrepareCompletionMatchProc() {
		if (getGraGra() != null && this.ruleEditor.getRule() != null) {
			if (!getGraGra().getGraph().isEditable()) {
				JOptionPane
						.showMessageDialog(
								this.applFrame,
								"Grammar is locked by critical pair analysis \nor transformation is already running.",
								"Cannot transform", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!this.ruleEditor.getRule().isEditable()) {
				return;
			}
			// test Graph.getPartialMorphismIntoSet(Vector, TypeSet)
			// if(getGraGra().getGraph().hasSelection()){
			// makePartialMatch();
			// }
			//      
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(false);
			if (!this.interpreting && !this.layering && !this.sequences) {
				if ((this.tmpTransformThread == null)
						|| !this.tmpTransformThread.isAlive()) {
					selectToolBarTransformItem("completion");

					if (this.graphEditor.getGraph().adjustTypeObjectsMap()) {
						getGraGra().destroyAllMatches();
					}

					if (this.ruleEditor.isObjMapping())
						this.ruleEditor.resetEditModeAfterMapping();
					if (this.editmode == EditorConstants.INTERACT_MATCH) {
						setEditMode(this.lasteditmode);
//						setEditMode(EditorConstants.MOVE);
					}

					this.gragraTransform.nextCompletion(this.ruleEditor.getRule());

					// tmpTransformThread = new Thread(){
					// public void run(){
					// gragraTransform.nextCompletion(ruleEditor.getRule());
					// }};
					// tmpTransformThread.start();
				} else
					fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
							" next completion ... Please wait. "));
			}
		}
	}

	public void doExecuteStepProc() {
		if (getGraGra() == null || this.ruleEditor.getRule() == null
				|| this.graphEditor.getGraph() == null
				|| this.graphEditor.getGraph().isTypeGraph() || !isEditable()) {
			return;
		}

		// fuer das spaetere fortlaufende layout...
		if (this.evolutionaryLayouter.isEnabled()
				&& this.getGraph().getGraphGen() == 0) {
			this.getGraph().incGraphGen();
		} else
			getGraGra().getGraph().enableDefaultGraphLayout(true);

		((AttrTupleManager) AttrTupleManager.getDefaultManager())
				.setVariableContext(false);

		if (!this.interpreting && !this.layering && !this.sequences) {
			// here for kernel and multi step warning if amalgamated exists!!!

			this.getGraph().setTransformChangeEnabled(true);
			if (this.undoManager != null && this.undoManager.isEnabled()) {
				this.undoManager.setUndoEndOfTransformStepAllowed(true);
			}
			if ((this.tmpTransformThread == null) || !this.tmpTransformThread.isAlive()) {
				selectToolBarTransformItem("step");

				this.lasteditmode = EditorConstants.MOVE;
				setEditMode(this.lasteditmode);
				this.forwardModeCommand("Move");

				this.graphEditor.getGraph().adjustTypeObjectsMap();
				this.gragraTransform.step(this.ruleEditor.getRule());

				// tmpTransformThread = new Thread(){
				// public void run(){
				// gragraTransform.step(ruleEditor.getRule());
				// }
				// };
				// tmpTransformThread.start();
			} else
				fireEditEvent(new EditEvent(this, EditEvent.EDIT_PROCEDURE,
						" transformation step ... Please wait. "));
		}
	}

	protected void setNodeAnimation(final Match m) {
		if (m != null) {
			EdRule r = this.getGraGra().getRule(m.getRule());
			// System.out.println("GraGraEditor.setAnimation of rule:
			// "+m.getRule().getName()+" "+r.isAnimated()+"
			// "+r.getAnimationKind());
			if (r.isAnimated()) {
				this.animationThread = this.nodeAnimation.prepareAnimation(r, this
						.getGraph(), (Graphics2D) this.graphEditor.getGraphPanel()
						.getCanvas().getGraphics());
			}
		}
	}

	protected void runAnimationThread() {
		if (this.animationThread) {
			this.getGraph().enableStaticNodePosition();
			this.getGraph().setStraightenArcs(true);

			if (!this.nodeToAnimateOnfront) {
				this.getGraph().setNodeOfAnimatedTypeToFront();
				this.nodeToAnimateOnfront = true;
			}
			// start
			this.nodeAnimation.animate();
			this.animationThread = false;
		}
	}

	private EdGraGra gra;
	private EdGraph graph;
	private EdRule rule;
	private EdNAC nac;
	private EdPAC pac;
	private EdNestedApplCond ac;
	private EdAtomic atomicConstr;
	private EdAtomApplCond postApplCond;
	private boolean resetGraGra;
	private boolean wasPostAtomApplCond;

	private void loadDataInEditor(final GraGraTreeNodeData data) {
		this.gra = null;
		this.graph = null;
		this.rule = null;
		this.nac = null;
		this.pac = null;
		this.ac = null;
		this.atomicConstr = null;
		this.postApplCond = null;
		this.resetGraGra = false;
		this.wasPostAtomApplCond = this.isPostAtomApplCond;
		this.isPostAtomApplCond = false;

		if (data.isGraGra() 
				&& (data.getGraGra() != null)
				&& (data.getGraGra().getBasisGraGra() != null)) {
			this.gra = loadGrammarInEditor(data);
		} 
		else if (data.isGraph() 
				&& (data.getGraph() != null)
				&& (data.getGraph().getBasisGraph() != null)) {
			this.graph = loadGraphInEditor(data);
		} 
		else if (data.isAtomic() 
				&& (data.getAtomic() != null)
				&& (data.getAtomic().getBasisAtomic() != null)) {
			this.atomicConstr = loadAtomicConstraintInEditor(data);
		} 
		else if (data.isConclusion() && (data.getConclusion() != null)
				&& (data.getConclusion().getBasisAtomic() != null)) {
			this.atomicConstr = loadConclusionOfAtomicInEditor(data);
		} 
		else if (data.isRuleScheme() 
				&& (data.getRuleScheme() != null)
				&& (data.getRuleScheme().getBasisRule() != null)) {
			this.rule = loadRuleInEditor(data);
		}
		else if (data.isRule() 
				&& (data.getRule() != null)
				&& (data.getRule().getBasisRule() != null)) {
			this.rule = loadRuleInEditor(data);
		} 
		else if (data.isConstraint()) { // formula
			loadFormulaInEditor(data);
		} 
		else if (data.isRuleConstraint()
				&& data.getTreeNode() != null) {
			this.postApplCond = loadPostApplCondOfRuleInEditor(data);
		} 
		else if (data.isAtomApplCond() 
				&& data.getAtomApplCond() != null
				&& data.getTreeNode() != null) {
			this.postApplCond = loadAtomicOfPostAplCondOfRuleInEditor(data);
		} 
		else if (data.isNAC() 
				&& (data.getNAC() != null)
				&& (data.getNAC().getBasisGraph() != null)) {
			this.nac = loadNACInEditor(data);
		} 
		else if (data.isPAC() 
				&& (data.getPAC() != null)
				&& (data.getPAC().getBasisGraph() != null)) {
			this.pac = loadPACInEditor(data);			
		}
		else if (data.isNestedAC() 
				&& (data.getNestedAC() != null)
				&& (data.getNestedAC().getBasisGraph() != null)) {
			this.ac = loadNestedACInEditor(data);
		} 
		else if (data.isAttrCondition()) {
			loadAttrConditionInEditor(data);
		} 

		if (hasAttrEditorOnTop()) {
			attrTypeChangedWarning();
			if (this.rule != null && this.rule != this.ruleEditor.getRule())
				this.ruleEditor.setRule(this.rule);
			resetRuleEditor();
		} else if (hasAttrEditorOnBottom() && !data.isAttrCondition()) {
			attrTypeChangedWarning();
			resetGraphEditor();
		}

		if (this.wasPostAtomApplCond != this.isPostAtomApplCond)
			resetRuleEditor();
		
		if (this.resetGraGra) {
			loadDataInEditorResetGraGra(data);
		}
		
		fireEditEvent(new EditEvent(this, EditEvent.DATA_LOADED, ""));
	}

	private EdGraGra loadGrammarInEditor(final GraGraTreeNodeData data) {
		if (!this.attrEditorExists) {
			this.setAttrEditor();
		}
		if (data.getGraGra() != this.gragra) {
			this.resetGraGra = true;
			this.dividerLocation = this.splitPane.getDividerLocation();
			this.dividerLocation1 = this.typeEditor.getTypePalette()
					.getDividerLocation();

			this.graph = data.getGraGra().getGraph();
			if (!data.getGraGra().getRules().isEmpty()) {
				this.rule = data.getGraGra().getRules().firstElement();
				if (this.rule.getMatch() != null) {
					this.rule.update();
					this.graph.update();
				}
				if (!this.rule.getNACs().isEmpty())
					this.nac = this.rule.getNACs().firstElement();
				else if (!this.rule.getPACs().isEmpty())
					this.pac = this.rule.getPACs().firstElement();
			} 
			
			((AGGAppl)this.applFrame).setPreferenceNoArcParallel(
					!data.getGraGra().getTypeSet().getBasisTypeSet().isArcParallel());
		}
		return data.getGraGra();
	}
	
	private EdGraph loadGraphInEditor(final GraGraTreeNodeData data) {
		if ((data.getGraph() != this.graphEditor.getGraph()) 
				|| this.graphEditor.isEmpty()) {
			if ((this.gragra == null) || (data.getGraph().getGraGra() != this.gragra)) {
				this.resetGraGra = true;
				this.gra = data.getGraph().getGraGra();
				if (!data.getGraph().isTypeGraph())
					this.gra.resetGraph(data.getGraph());
				// das unten in resetGrara methode
				if (!this.gra.getRules().isEmpty())
					this.rule = this.gra.getRules().firstElement();
				if (this.rule != null && !this.rule.getNACs().isEmpty())
					this.nac = this.rule.getNACs().firstElement();
				else if (this.rule != null && !this.rule.getPACs().isEmpty())
					this.pac = this.rule.getPACs().firstElement();
			} else if (!data.getGraph().isTypeGraph()) { // can be after undo step
				if (this.gragra.getGraph() != data.getGraph())
					this.gragra.resetGraph(data.getGraph());

//				graph = gragra.getGraph();
				data.setData(data.getGraph()); // ???
				this.graphEditor.setGraph(data.getGraph());
				this.graphEditor.updateGraphics();
			} else {
				this.graphEditor.setGraph(data.getGraph());
				this.graphEditor.updateGraphics();
			}
		}
		setMsg(this.graphEditor.getMsg());
		return data.getGraph();
	}
	
	private EdAtomic loadAtomicConstraintInEditor(final GraGraTreeNodeData data) {
		this.atomicConstr = data.getAtomic();
		if (data.getAtomic() != this.ruleEditor.getAtomic()) {
			this.dividerLocation = this.splitPane.getDividerLocation();
			if (this.ruleEditor.getAtomic() != null) {
				this.dividerLocationSet.put(this.ruleEditor.getAtomic(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			}
			if ((this.gragra == null) || (data.getAtomic().getGraGra() != this.gragra)) {
				this.resetGraGra = true;
				this.gra = data.getAtomic().getGraGra();
				this.graph = this.gra.getGraph();
				if (data.getAtomic().isParent()) {
					this.atomicConstr = data.getAtomic().getConclusion(0);
				}
			} else {
				if (this.ruleEditor.getAtomic() != null) {
					if (this.atomicConstr.isParent()) {
						if ((this.ruleEditor.getAtomic().getParent() != data.getAtomic())) {
							this.atomicConstr = data.getAtomic().getConclusion(0);
						} else {
							this.atomicConstr = null;
						}
					}
				} else if (data.getAtomic().isParent()) {
					this.atomicConstr = data.getAtomic().getConclusion(0);
				}

				if (this.atomicConstr != null) {
					this.atomicConstr.update();
					this.ruleEditor.setAtomic(this.atomicConstr);
					if (this.dividerLocationSet.get(this.atomicConstr) != null)
						this.splitPane.setDividerLocation(this.dividerLocationSet
								.get(this.atomicConstr).intValue());
					this.ruleEditor.updateGraphics();
				}
			}
		}
		resetEnabledOfMenus(data, false);
		resetEnabledOfToolBarItems(data, false);
		return this.atomicConstr;
	}
	
	private EdAtomic loadConclusionOfAtomicInEditor(final GraGraTreeNodeData data) {
		if (data.getConclusion() != this.ruleEditor.getAtomic()) {
			if ((this.gragra == null) || (data.getConclusion().getGraGra() != this.gragra)) {
				this.resetGraGra = true;
				this.gra = data.getConclusion().getGraGra();
				this.graph = this.gra.getGraph();
			} else {
				data.getConclusion().update();
				this.ruleEditor.setAtomic(data.getConclusion());
				this.ruleEditor.updateGraphics();
				if (this.ruleEditor.isObjMapping())
					this.ruleEditor.resetEditModeAfterMapping();
			}
		} else {
			String s = data.getConclusion().getBasisAtomic().getAtomicName();
			this.ruleEditor.setAtomicTitle(data.getConclusion().getBasisAtomic().getName(), s);
		}
		resetEnabledOfMenus(data, false);
		resetEnabledOfToolBarItems(data, false);
		return data.getConclusion();
	}
	
	private EdRule loadRuleInEditor(final GraGraTreeNodeData data) {
		boolean done = false;
		this.dividerLocation = this.splitPane.getDividerLocation();
		if (data.getRule() != this.ruleEditor.getRule()) {
			if (this.ruleEditor.getRule() != null)
				this.dividerLocationSet.put(this.ruleEditor.getRule(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			if ((this.gragra == null) || (data.getRule().getGraGra() != this.gragra)) {
				this.resetGraGra = true;
				this.gra = data.getRule().getGraGra();
				this.graph = this.gra.getGraph();
				this.rule = data.getRule();
				if (!data.getRule().getNACs().isEmpty())
					this.nac = data.getRule().getNACs().firstElement();
				else if (!data.getRule().getPACs().isEmpty())
					this.pac = data.getRule().getPACs().firstElement();
//				else if (!data.getRule().getNestedACs().isEmpty()) 
//					this.ac = (EdNestedApplCond) data.getRule().getNestedACs().firstElement();
			}
			if (!this.resetGraGra) {
				this.ruleEditor.setRule(data.getRule());
				done = true;
				if (!data.getRule().getNACs().isEmpty()) {
					this.nac = data.getRule().getNACs().firstElement();
					this.ruleEditor.setNAC(this.nac);
				}
				else if (!data.getRule().getPACs().isEmpty()) {
					this.pac = data.getRule().getPACs().firstElement();
					this.ruleEditor.setPAC(this.pac);
				}
//				else if (!data.getRule().getNestedACs().isEmpty()) {
//					this.ac = (EdNestedApplCond) data.getRule().getNestedACs().firstElement();
//					ruleEditor.setNestedAC(this.ac);
//				}
				else {
					this.ruleEditor.setNestedAC(null);
				}
			}
		} else if (data.getRule().getLeft() != this.ruleEditor.getLeftPanel().getGraph()
				|| data.getRule().getRight() != this.ruleEditor.getRightPanel().getGraph()) {
			this.ruleEditor.resetRule();
			done = true;
		} else if (!this.ruleEditor.isRightPanelVisible()) {
			this.ruleEditor.resetRule();
			done = true;
		}
		
		if (done) {
			if (this.dividerLocationSet.get(this.ruleEditor.getRule()) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(
						this.ruleEditor.getRule()).intValue());
			else
				this.splitPane.setDividerLocation(this.dividerLocation);
		}
		if (!this.interpreting && !this.layering && !this.sequences) {
			resetEnabledOfMenus(data, false);
			resetEnabledOfToolBarItems(data, false);
		}
		return data.getRule();
	}
	
	private void loadFormulaInEditor(final GraGraTreeNodeData data) {
		this.gra = data.getConstraint().getGraGra();
		if ((this.gra != null) && (this.gra != this.gragra)) {
			this.resetGraGra = true;
			this.dividerLocation = this.splitPane.getDividerLocation();
			// da unten in resetGrammar
			this.graph = this.gra.getGraph();
			if (!this.gra.getRules().isEmpty()) {
				this.rule = this.gra.getRules().firstElement();
				if (this.rule.getMatch() != null) {
					this.rule.update();
					this.graph.update();
				}
			}
			if (this.rule != null && !this.rule.getNACs().isEmpty())
				this.nac = this.rule.getNACs().firstElement();
			else if (this.rule != null && !this.rule.getPACs().isEmpty())
				this.pac = this.rule.getPACs().firstElement();
		}	
	}
	
	private EdAtomApplCond loadPostApplCondOfRuleInEditor(final GraGraTreeNodeData data) {
		DefaultMutableTreeNode node = data.getTreeNode();
		if (node.getChildCount() != 0) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) 
						node.getChildAt(0);
			GraGraTreeNodeData c = (GraGraTreeNodeData) child
						.getUserObject();
			this.postApplCond = c.getAtomApplCond();
			if (this.gragra != null) {
				this.gra = ((GraGraTreeNodeData) ((DefaultMutableTreeNode) 
							node.getParent().getParent()).getUserObject())
							.getGraGra();
				this.rule = ((GraGraTreeNodeData) ((DefaultMutableTreeNode) 
							node.getParent()).getUserObject()).getRule();
				if (this.gra != this.gragra) {
					this.resetGraGra = true;
					this.graph = this.gra.getGraph();
				}
				this.pacMorphs.setGraGra(this.gra);
				this.pacMorphs.setRule(this.rule);
				this.pacMorphs.setAtomApplCond(this.postApplCond.getAtomApplCond());
				resetEnabledOfMenus(c, false);
				resetEnabledOfToolBarItems(c, false);	
				this.isPostAtomApplCond = true;
			}
			return this.postApplCond;
		}
		return null;
	}
	
	private EdAtomApplCond loadAtomicOfPostAplCondOfRuleInEditor(final GraGraTreeNodeData data) {
		DefaultMutableTreeNode node = data.getTreeNode();
		this.postApplCond = data.getAtomApplCond();
		if (this.gragra != null) {
			this.gra = ((GraGraTreeNodeData) ((DefaultMutableTreeNode) 
					node.getParent().getParent().getParent())
					.getUserObject()).getGraGra();
			this.rule = ((GraGraTreeNodeData) ((DefaultMutableTreeNode) 
					node.getParent().getParent()).getUserObject())
					.getRule();
			if (this.gra != this.gragra) {
				this.resetGraGra = true;
				this.graph = this.gra.getGraph();
			}
			this.pacMorphs.setGraGra(this.gra);
			this.pacMorphs.setRule(this.rule);
			this.pacMorphs.setAtomApplCond(this.postApplCond.getAtomApplCond());
			resetEnabledOfMenus(data, false);
			resetEnabledOfToolBarItems(data, false);
			this.isPostAtomApplCond = true;
		}	
		return this.postApplCond;
	}
	
	private EdNAC loadNACInEditor(final GraGraTreeNodeData data) {
		if ((this.gragra == null) || (data.getNAC().getRule().getGraGra() != this.gragra)) {
			this.resetGraGra = true;
			this.rule = data.getNAC().getRule();
			this.gra = this.rule.getGraGra();
			this.graph = this.gra.getGraph();
			
		} else if (data.getNAC().getRule() != this.ruleEditor.getRule()) {
			this.rule = data.getNAC().getRule();
			this.ruleEditor.setRule(this.rule);
			this.ruleEditor.setNAC(data.getNAC());
			if (this.dividerLocationSet.get(this.rule) != null) {
				int divloc = this.dividerLocationSet.get(this.rule).intValue();
				if (this.dividerLocationSet.get(data.getNAC()) != null) {
					int divloc1 = this.dividerLocationSet.get(data.getNAC()).intValue();
					if (divloc >= divloc1)
						this.splitPane.setDividerLocation(divloc);
					else
						this.splitPane.setDividerLocation(divloc1);
				} else
					this.splitPane.setDividerLocation(this.dividerLocationSet.get(
							this.rule).intValue());
			} else
				this.splitPane.setDividerLocation(this.dividerLocation);
			this.ruleEditor.updateGraphics();
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		} else if (data.getNAC() != this.ruleEditor.getNAC()) {
			this.dividerLocation = this.splitPane.getDividerLocation();
			if (this.ruleEditor.getNAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getPAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getPAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getNestedAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNestedAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			
			this.ruleEditor.setNAC(data.getNAC());
			if (this.dividerLocationSet.get(data.getNAC()) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(data.getNAC())
						.intValue());
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		}
		resetEnabledOfMenus(data, false);
		resetEnabledOfToolBarItems(data, false);	
		return data.getNAC();
	}
	
	private EdPAC loadPACInEditor(final GraGraTreeNodeData data) {
		if ((this.gragra == null) || (data.getPAC().getRule().getGraGra() != this.gragra)) {
			this.resetGraGra = true;
			this.rule = data.getPAC().getRule();
			this.gra = this.rule.getGraGra();
			this.graph = this.gra.getGraph();
		} else if (data.getPAC().getRule() != this.ruleEditor.getRule()) {
			this.rule = data.getPAC().getRule();
			this.ruleEditor.setRule(this.rule);
			this.ruleEditor.setPAC(data.getPAC());
			if (this.dividerLocationSet.get(this.rule) != null) {
				int divloc = this.dividerLocationSet.get(this.rule).intValue();
				if (this.dividerLocationSet.get(data.getPAC()) != null) {
					int divloc1 = this.dividerLocationSet.get(data.getPAC()).intValue();
					if (divloc >= divloc1)
						this.splitPane.setDividerLocation(divloc);
					else
						this.splitPane.setDividerLocation(divloc1);
				} else
					this.splitPane.setDividerLocation(this.dividerLocationSet.get(
							this.rule).intValue());
			} else
				this.splitPane.setDividerLocation(this.dividerLocation);
			this.ruleEditor.updateGraphics();
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		} else if (data.getPAC() != this.ruleEditor.getPAC()) {
			this.dividerLocation = this.splitPane.getDividerLocation();
			if (this.ruleEditor.getPAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getPAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getNAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getNestedAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNestedAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			
			this.ruleEditor.setPAC(data.getPAC());
			if (this.dividerLocationSet.get(data.getPAC()) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(data.getPAC())
						.intValue());
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		}
		resetEnabledOfMenus(data, false);
		resetEnabledOfToolBarItems(data, false);
		return data.getPAC();
	}
	
	private EdNestedApplCond loadNestedACInEditor(final GraGraTreeNodeData data) {
		if ((this.gragra == null) || (data.getNestedAC().getRule().getGraGra() != this.gragra)) {
			this.resetGraGra = true;
			this.rule = data.getNestedAC().getRule();
			this.gra = this.rule.getGraGra();
			this.graph = this.gra.getGraph();
		} else if (data.getNestedAC().getRule() != this.ruleEditor.getRule()) {
			this.dividerLocation = this.splitPane.getDividerLocation();
			if (this.ruleEditor.getNestedAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNestedAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getNAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getPAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getPAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			
			this.rule = data.getNestedAC().getRule();
			this.ruleEditor.setRule(this.rule);
			if (this.dividerLocationSet.get(this.rule) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(this.rule).intValue());
			
			this.ruleEditor.setNestedAC(null); //data.getNestedAC());						
//			if (dividerLocationSet.get(data.getNestedAC()) != null)
//				splitPane.setDividerLocation(dividerLocationSet.get(data.getNestedAC())
//						.intValue());
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		} else if (data.getNestedAC() != this.ruleEditor.getNestedAC()) {
			this.dividerLocation = this.splitPane.getDividerLocation();
			if (this.ruleEditor.getNestedAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNestedAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getNAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getNAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			else if (this.ruleEditor.getPAC() != null)
				this.dividerLocationSet.put(this.ruleEditor.getPAC(), Integer
						.valueOf(this.splitPane.getDividerLocation()));
			
			this.ruleEditor.setNestedAC(data.getNestedAC());
			if (this.dividerLocationSet.get(data.getNestedAC()) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(data.getNestedAC())
						.intValue());
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
		} else if (this.ruleEditor.isRightPanelVisible()) {
			this.ruleEditor.setNestedAC(data.getNestedAC());
		}
		resetEnabledOfMenus(data, false);
		resetEnabledOfToolBarItems(data, false);	
		return data.getNestedAC();
	}
	
	private void loadAttrConditionInEditor(final GraGraTreeNodeData data) {
		if (this.ruleEditor.getAtomic() != null) {
			Pair<CondMember, EdRule> attrCond = data.getAttrCondition();
			if (attrCond.second instanceof EdAtomic) {
				this.atomicConstr = (EdAtomic) attrCond.second;
				if (this.atomicConstr.getParent().getGraGra() != this.gragra) {
					this.resetGraGra = true;
					this.gra = this.atomicConstr.getParent().getGraGra();
					this.graph = this.gra.getGraph();
				} else if (this.atomicConstr != this.ruleEditor.getAtomic()) {
					this.ruleEditor.setAtomic(this.atomicConstr);
				}
				setAttrEditorOnBottom(this.attrEditor.getComponent());
				this.attrEditor.setContext(this.atomicConstr.getMorphism().getAttrContext());
				((TopEditor) this.attrEditor).selectContextEditor(true);
				((TopEditor) this.attrEditor).setTuple(null);
			}
		} else if (this.ruleEditor.getRule() != null) {
			Pair<CondMember, EdRule> attrCond = data.getAttrCondition();
			if (attrCond.second instanceof EdAtomic) {
				this.atomicConstr = (EdAtomic) attrCond.second;
				if (this.atomicConstr.getParent().getGraGra() != this.gragra) {
					this.resetGraGra = true;
					this.gra = this.atomicConstr.getParent().getGraGra();
					this.graph = this.gra.getGraph();
				} else if (this.atomicConstr != this.ruleEditor.getAtomic()) {
					this.ruleEditor.setAtomic(this.atomicConstr);
				}
				setAttrEditorOnBottom(this.attrEditor.getComponent());
				this.attrEditor.setContext(this.atomicConstr.getMorphism().getAttrContext());
				((TopEditor)this. attrEditor).selectContextEditor(true);
				((TopEditor) this.attrEditor).setTuple(null);
			} else {
				this.rule = attrCond.second;
				if (this.rule.getGraGra() != this.gragra) {
					this.resetGraGra = true;
					this.gra = this.rule.getGraGra();
					this.graph = this.gra.getGraph();
				} else if (this.rule != this.ruleEditor.getRule()) {
					this.ruleEditor.setRule(this.rule);
				}
				if (hasAttrEditorOnTop()) {
					resetRuleEditor();
				}
				setAttrEditorOnBottom(this.attrEditor.getComponent());
				this.attrEditor.setContext(this.rule.getBasisRule().getAttrContext());
				((TopEditor) this.attrEditor).selectContextEditor(true);
				((TopEditor) this.attrEditor).setTuple(null);
			}
		}
	}
	
	private void loadDataInEditorResetGraGra(final GraGraTreeNodeData data) {
		setGraGra(this.gra);

		this.typeEditor.setGraGra(this.gra);
		if (this.dividerLocation1 == 0) {
			this.typeEditor.getTypePalette().setDividerLocation(
					this.splitPane.getHeight() / 2);
		} else {
			this.typeEditor.getTypePalette()
					.setDividerLocation(this.dividerLocation1);
		}
		if (this.splitPane1.getRightComponent()
				!= this.typeEditor.getTypePalette().getTypePaletteComponent()) {
			this.splitPane1.setRightComponent(this.typeEditor.getTypePalette()
					.getTypePaletteComponent());
		}
		this.splitPane1.setDividerLocation(this.getWidth()
					- this.typeEditor.getTypePalette().getWidthOfPalette());
		
		this.graphEditor.setGraph(this.graph);

		resetEnabledOfMenus(data, true);
		resetEnabledOfToolBarItems(data, true);

		if (this.atomicConstr != null) {
			this.ruleEditor.setAtomic(this.atomicConstr);
			if (this.dividerLocationSet.get(this.atomicConstr) != null)
				this.splitPane.setDividerLocation(this.dividerLocationSet.get(this.atomicConstr)
						.intValue());
			else {
				this.splitPane.setDividerLocation(this.dividerLocation);
			}
		} else if (this.rule != null) {
			this.ruleEditor.setRule(this.rule);
			if (this.nac != null)
				this.ruleEditor.setNAC(this.nac);
			else if (this.pac != null)
				this.ruleEditor.setPAC(this.pac);
//			else if (this.ac != null)
//				ruleEditor.setNestedAC(this.ac);
			else {
				this.ac = null;
				this.ruleEditor.setNestedAC(null);
			}
			
			if ((this.rule != null) && (this.dividerLocationSet.get(this.rule) != null)) {
				int divloc = this.dividerLocationSet.get(this.rule).intValue();
				if (this.nac != null && (this.dividerLocationSet.get(this.nac) != null)) {
					int divloc1 = this.dividerLocationSet.get(this.nac).intValue();
					if (divloc >= divloc1)
						this.splitPane.setDividerLocation(divloc);
					else
						this.splitPane.setDividerLocation(divloc1);
				} else if (this.pac != null && (this.dividerLocationSet.get(this.pac) != null)) {
					int divloc1 = this.dividerLocationSet.get(this.pac).intValue();
					if (divloc >= divloc1)
						this.splitPane.setDividerLocation(divloc);
					else
						this.splitPane.setDividerLocation(divloc1);
				} else if (this.ac != null && (this.dividerLocationSet.get(this.ac) != null)) {
					int divloc1 = this.dividerLocationSet.get(this.ac).intValue();
					if (divloc >= divloc1)
						this.splitPane.setDividerLocation(divloc);
					else
						this.splitPane.setDividerLocation(divloc1);
				} else {
					this.splitPane.setDividerLocation(this.dividerLocationSet.get(
							this.rule).intValue());
				}
			} else
				this.splitPane.setDividerLocation(this.dividerLocation);
		}
		updateGraphics();

		if (getEditMode() == -1) {
			setEditMode(EditorConstants.DRAW);
			forwardModeCommand("Draw");
		} else if (getGraGra().getTypeSet().isEmpty()) {
			setEditMode(EditorConstants.DRAW);
			forwardModeCommand("Draw");
		} else {
			if (this.ruleEditor.isObjMapping())
				this.ruleEditor.resetEditModeAfterMapping();
			setEditMode(this.lasteditmode);
			selectToolBarModeItem(this.lasteditmode);
		}

		if (this.gragraTransform.checkRuleApplicabilityEnabled())
			this.gragraTransform.getApplicableRules(getGraGra());
	}
	
	private void deleteDataFromEditor(final GraGraTreeNodeData data) {
		if (data.isGraGra()) {
			for (int i = 0; i < data.getGraGra().getRules().size(); i++)
				removeRuleContextFromAttrEditor(data.getGraGra().getRules()
						.get(i));
			if (data.getGraGra() == this.gragra) {
				this.clear();
			}
		} else if (data.isTypeGraph()) {
			if (data.getGraph().equals(this.graphEditor.getGraph()))
				this.graphEditor.clear();
		} else if (data.isGraph() && !data.isTypeGraph()) {
			if (data.getGraph().equals(this.graphEditor.getGraph()))
				this.graphEditor.clear();
		} else if (data.isRule()) {
			removeRuleContextFromAttrEditor(data.getRule());
			if (data.getRule().equals(this.ruleEditor.getRule()))
				this.ruleEditor.clear();
		} else if (data.isNAC()) {
			if (data.getNAC().equals(this.ruleEditor.getNAC()))
				this.ruleEditor.clearNAC();
		} else if (data.isPAC()) {
			if (data.getPAC().equals(this.ruleEditor.getPAC()))
				this.ruleEditor.clearPAC();
		} else if (data.isNestedAC()) {
			if (data.getNestedAC().equals(this.ruleEditor.getNestedAC()))
				this.ruleEditor.clearNestedAC();
		} else if (data.isAtomic()) {
			if (data.getAtomic().equals(this.ruleEditor.getAtomic()))
				this.ruleEditor.clear();
		} else if (data.isConclusion()) {
			if (data.getConclusion().equals(this.ruleEditor.getAtomic()))
				this.ruleEditor.clear();
		} else if (data.isAtomApplCond()) {
			this.pacMorphs.setAtomApplCond(null);
		}
	}

	private void removeRuleContextFromAttrEditor(EdRule r) {
		if (this.attrEditorExists) {
			((OpenViewSetting) this.attrEditor.getViewSetting().getOpenView())
					.removeFormat(r.getBasisRule().getAttrContext()
							.getVariables().getType());
			((OpenViewSetting) this.attrEditor.getViewSetting().getOpenView())
					.removeFormat(r.getBasisRule().getAttrContext()
							.getConditions().getType());
		}
	}

	public JPopupMenu getGraphLayoutMenu() {
		return this.graphLayouts.getMenu();
	}

	private void createMainMenus() {
		createEditMenu();
		createModeMenu();
		createTransformMenu();
	}

	private void createEditMenu() {
		final String[] editLabels = new String[] {
				"Undo Edit                                      Ctrl+Z",
				"Redo Edit                                      Ctrl+Y",
				"Discard All Edits                              ",
				"Attributes                                     Ctrl+A",
				"Delete                                           Ctrl+D",
				"Copy                                              Ctrl+C",
				"Paste                                            Ctrl+V",
				"Select Nodes of Type               Ctrl+Alt+N",
				"Select Edges of Type               Ctrl+Alt+E",
				"Select All                                     Ctrl+Alt+S",
				"Deselect All                                Ctrl+Alt+U",
				"Straighten Edges                           Ctrl+E",
				"Identic Rule                         Ctrl+Shift+R",
				"Identic NAC                          Ctrl+Shift+N",
				"Identic PAC                          Ctrl+Shift+P",
				"Identic General AC             Ctrl+Shift+A"};
		final String[] editCommands = new String[] {
				"undo", "redo", "discardAllEdits", "attributes", 
				"delete", "copy", "paste", "selectNodeType", 
				"selectArcType", "selectAll", "deselectAll", "straighten", 
				"identicRule", "identicNAC", "identicPAC", "identicAC" };
		final char[] editMnemonics = new char[] { 
				'U', 'R', ' ', 'B',
				'L', 'C',' ', 'N', 
				'G', 'S', 'D', 'H', 
				'I', ' ', ' ', ' ' };

		this.edit.setMnemonic('E');
		for (int i = 0; i < editLabels.length; i++) {
			final JMenuItem mi = new JMenuItem(editLabels[i]);
			if (editMnemonics[i] != ' ')
				mi.setMnemonic(editMnemonics[i]);
			mi.setEnabled(true);
			mi.setActionCommand(editCommands[i]);
			mi.addActionListener(this.actionAdapter);
			this.edit.add(mi);
			if (editCommands[i].equals("discardAllEdits")
					|| editCommands[i].equals("attributes")
					|| editCommands[i].equals("paste")
					|| editCommands[i].equals("deselectAll")
					|| editCommands[i].equals("straighten")) {
				this.edit.addSeparator();
			}
		}
		this.mainMenus.addElement(this.edit);
	}

	private void createModeMenu() {
		final String[] modeLabels = new String[] { 
				"Draw              Shift+D",
				"Select            Shift+S", 
				"Move              Shift+M",
				"Attributes        Shift+A", 
				"Map                Ctrl+M",
				"Unmap              Ctrl+U", 
				"Image_view" };
		final String[] modeCommands = new String[] { "drawMode", "selectMode",
				"moveMode", "attributesMode", "mapMode", "unmapMode",
				"imageMode" };
		final char[] modeMnemonics = new char[] { 'D', 'S', 'M', 'B', 'P', 'U',
				'I' };

		this.mode.setMnemonic('M');
		for (int i = 0; i < modeLabels.length; i++) {
			final JCheckBoxMenuItem mi = new JCheckBoxMenuItem(modeLabels[i]);
			mi.setMnemonic(modeMnemonics[i]);
			mi.setEnabled(true);
			mi.setActionCommand(modeCommands[i]);
			mi.addActionListener(this.actionAdapter);
			this.mode.add(mi);
			if (modeCommands[i].equals("unmapMode")) {
				this.mode.addSeparator();
			}
		}
		this.mode.getItem(0).setSelected(true);
		this.mainMenus.addElement(this.mode);
	}

	private void createTransformMenu() {
		final String[] transLabels = new String[] {
				"Start                           Shift+Ctrl+T",
				"Stop                           Shift+Ctrl+Q",
				"Match                         Shift+Ctrl+M",
				"Next Completion        Shift+Ctrl+C",
				"Step                            Shift+Ctrl+S",
				"Undo Step                   Shift+Ctrl+U", "Options..." };
		final String[] transCommands = new String[] { "start", "stop", "match",
				"completion", "step", "undoStep", "options" };
		final char[] transMnemonics = new char[] { 't', 'o', 'c', 'N', 'S',
				'U', ' ' };

		this.transform.setMnemonic('T');
		for (int i = 0; i < transLabels.length; i++) {
			final JMenuItem mi = new JMenuItem(transLabels[i]);
			mi.setMnemonic(transMnemonics[i]);
			mi.setEnabled(true);
			mi.setActionCommand(transCommands[i]);
			mi.addActionListener(this.actionAdapter);
			if (mi.getActionCommand().equals("stop")
					|| mi.getActionCommand().equals("undoStep")) {
				mi.setEnabled(false);
			}
			this.transform.add(mi);
			if (transCommands[i].equals("stop")
					|| transCommands[i].equals("step")
					|| transCommands[i].equals("undoStep")) {
				this.transform.addSeparator();
			}
		}
		this.mainMenus.addElement(this.transform);
	}

	private void resetEnabledOfMenus(GraGraTreeNodeData selNode,
			boolean gragraIsChanged) {
		if (this.gragra != null) {
			if (gragraIsChanged) {
				if (selNode != null) {
					if (selNode.isAtomic() || selNode.isConclusion()) {
						this.edit.setEnabled(true);
						this.mode.setEnabled(true);
						this.transform.setEnabled(false);
						// view.setEnabled(true);
						setEditMode(this.lasteditmode);

					} else if (selNode.isRuleConstraint()
							|| selNode.isConstraint()
							|| selNode.isAtomApplCond()) {
						if (this.editmode != EditorConstants.VIEW) {
							this.lasteditmode = this.editmode;
						}
						setEditMode(EditorConstants.VIEW);
						this.edit.setEnabled(false);
						this.mode.setEnabled(false);
						this.transform.setEnabled(false);
						// view.setEnabled(false);
					} else { // isGraGra || isGraph || isRule || isNAC
						this.edit.setEnabled(true);
						this.mode.setEnabled(true);
						this.transform.setEnabled(true);
						// view.setEnabled(true);
						setEditMode(this.lasteditmode);
					}
				}
			} else { // !gragraIsChanged
				if (selNode != null) {
					if (selNode.isAtomic() || selNode.isConclusion()) {
						this.edit.setEnabled(true);
						this.mode.setEnabled(true);
						this.transform.setEnabled(false);
						// view.setEnabled(true);
						setEditMode(this.lasteditmode);
					} else if (selNode.isRuleConstraint()
							|| selNode.isConstraint()
							|| selNode.isAtomApplCond()) {
						if (this.editmode != EditorConstants.VIEW) {
							this.lasteditmode = this.editmode;
						}
						setEditMode(EditorConstants.VIEW);
						this.edit.setEnabled(false);
						this.mode.setEnabled(false);
						this.transform.setEnabled(false);
						// view.setEnabled(false);
					} else { // isGraGra || isGraph || isRule || isNAC
						this.edit.setEnabled(true);
						this.mode.setEnabled(true);
						this.transform.setEnabled(true);
						// view.setEnabled(true);
						setEditMode(this.lasteditmode);
					}
				}
			}
		} else { // gragra == null
			this.edit.setEnabled(false);
			this.mode.setEnabled(false);
			this.transform.setEnabled(false);
			// view.setEnabled(false);
		}
	}

	private void resetModeMenu(JCheckBoxMenuItem selItem) {
		for (int i = 0; i < this.mode.getItemCount(); i++) {
			if (this.mode.getMenuComponent(i) instanceof JMenuItem) {
				JMenuItem mi = this.mode.getItem(i);
				if (selItem == null)
					mi.setSelected(false);
				else if (selItem.getText().equals("Image_view"))
					;
				else if (mi.getText().equals("Image_view"))
					;
				else if (selItem.getState() && (!mi.equals(selItem)))
					mi.setSelected(!selItem.getState());
			}
		}
	}

	private JCheckBoxMenuItem getModeMenuItem(String itemname) {
		String name = itemname.replaceAll(" ", "");
		for (int i = 0; i < this.mode.getItemCount(); i++) {
			if (this.mode.getMenuComponent(i) instanceof JMenuItem) {
				JMenuItem mi = this.mode.getItem(i);
				String miname = mi.getText().replaceAll(" ", "");
				if (miname.indexOf(name) != -1)
					return (JCheckBoxMenuItem) mi;
			}
		}
		return null;
	}

	public void forwardModeCommand(JCheckBoxMenuItem selItem) {
		if (!selItem.getText().equals("Image_view"))
			selItem.setSelected(true);
		resetModeMenu(selItem);
		this.modePopupMenu.selectEditModeMenuItem(selItem.getText());
		selectToolBarModeItem(selItem.getText());
	}

	public void forwardModeCommand(String selName) {
		final JCheckBoxMenuItem selItem = getModeMenuItem(selName);
		if (selItem != null)
			forwardModeCommand(selItem);
		else {
			this.modePopupMenu.selectEditModeMenuItem(selName);
			selectToolBarModeItem(selName);
		}
	}

	public void resetTransformMenu(String selName) {
		if (selName.equals("Start")) {
			resetTransformMenu(this.transform.getItem(0));
		} else if (selName.equals("Stop")) {
			resetTransformMenu(this.transform.getItem(1));
		}
	}

	public void resetTransformMenu(JMenuItem selItem) {
		if (selItem.getActionCommand().equals("start")) {
			for (int i = 0; i < this.transform.getItemCount(); i++) {
				if (this.transform.getMenuComponent(i) instanceof JMenuItem) {
					final JMenuItem mi = this.transform.getItem(i);
					if (mi.getActionCommand().equals("stop")
							|| mi.getActionCommand().equals("undoStep")
							|| mi.getActionCommand().equals("options")
							|| mi.getText().equals("Transform"))
						mi.setEnabled(true);
					else
						mi.setEnabled(false);
				}
			}
		} else if (selItem.getActionCommand().equals("stop")) {
			for (int i = 0; i < this.transform.getItemCount(); i++) {
				if (this.transform.getMenuComponent(i) instanceof JMenuItem) {
					final JMenuItem mi = this.transform.getItem(i);
					if (mi.getActionCommand().equals("stop"))
						mi.setEnabled(false);
					else
						mi.setEnabled(true);
				}
			}
		}
	}

	private void disableStopMenuItem() {
		for (int i = 0; i < this.transform.getItemCount(); i++) {
			if (this.transform.getItem(i) != null) {
				if (this.transform.getItem(i).getActionCommand().equals("stop"))
					this.transform.getItem(i).setEnabled(false);
				else
					this.transform.getItem(i).setEnabled(true);
			}
		}
	}

	private void fillToolBar() {
		// edit
		this.toolBar.addTool(this.toolBar.createTool("imageable", "attributes",
				"Attributes of a selected object", "attributes",
				this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("imageable", "delete",
				"Delete selected object(s)", "delete", this.actionAdapter,
				false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("imageable", "copy",
				"Copy selected object(s)", "copy", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("imageable", "paste", "Paste",
				"paste", this.actionAdapter, false));
		this.toolBar.addSeparator();// new Dimension(3,3) );
		this.toolBar.addTool(this.toolBar.createTool("iconable", "SelectNodeTypeIcon",
				"Select nodes of current type", "selectNodeType",
				this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "SelectArcTypeIcon",
				"Select arcs of current type", "selectArcType",
				this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "SelectAllIcon",
				"Select all objects", "selectAll", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.toolBar.addTool(this.toolBar.createTool("iconable", "DeselectAllIcon",
				"Deselect all objects / Redraw", "deselectAll",
				this.actionAdapter, false));
		this.toolBar.addSeparator(this.freeSpace);
		this.toolBar.addTool(this.toolBar.createTool("textable", "IR", "Identic Rule",
				"identicRule", this.actionAdapter, false));
		this.toolBar.addSeparator(new Dimension(3, 3));

		this.toolBar.addTool(this.buttonIdenticNAC);
		this.toolBar.addSeparator(new Dimension(3, 3));

		this.toolBar.addTool(this.buttonIdenticPAC); 
		
		this.indxIdenticNestedAC = this.toolBar.getComponentCount();
//		toolBar.addSeparator(new Dimension(3, 3));
//		toolBar.addTool(buttonIdenticNestedAC); 
		
		this.toolBar.addSeparator(this.freeSpace);
		
		// mode
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar
				.createTool("imageable", "draw_mode", "Draw mode", "drawMode",
						this.actionAdapter, false)));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar.createTool("imageable",
				"select_mode", "Select mode", "selectMode", this.actionAdapter,
				false)));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar
				.createTool("imageable", "move_mode", "Move mode", "moveMode",
						this.actionAdapter, false)));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar.createTool("imageable",
				"attributes_mode", "Attributes mode", "attributesMode",
				this.actionAdapter, false)));
		this.toolBar.addSeparator(new Dimension(3, 3));
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar.createTool("imageable",
				"map_mode", "Map mode", "mapMode", this.actionAdapter, false))); // 22
		this.toolBar.addSeparator(new Dimension(3, 3)); // 23
		this.editModeButtons.add(this.toolBar.addTool(this.toolBar.createTool("imageable",
				"unmap_mode", "Unmap mode", "unmapMode", this.actionAdapter,
				false))); // 24
		this.toolBar.addSeparator(this.freeSpace); // 25

		// transform
		// buttonMatch = toolBar.createTool("iconable", "MatchIcon",
		// "Interactive match mode", "match", this, false);
		this.toolBar.addTool(this.buttonMatch);
		this.transformButtons.add(this.buttonMatch);
		this.toolBar.addSeparator(new Dimension(3, 3));
		// buttonCompletion = toolBar.createTool("iconable", "CompletionIcon",
		// "Next Completion", "completion", this, false);
		this.toolBar.addTool(this.buttonCompletion);
		this.transformButtons.add(this.buttonCompletion);
		this.toolBar.addSeparator(new Dimension(3, 3));
		// buttonStep = toolBar.createTool("iconable", "StepIcon",
		// "Transformation Step", "step", this, false);
		this.toolBar.addTool(this.buttonStep);
		this.transformButtons.add(this.buttonStep);
		this.toolBar.addSeparator(this.freeSpace);
		// buttonStart = toolBar.createTool("iconable", "StartIcon", "Start
		// Transformation", "start", this, false);
		this.toolBar.addTool(this.buttonStart);
		this.transformButtons.add(this.buttonStart);
		this.toolBar.addSeparator(new Dimension(3, 3));
		// buttonStop = toolBar.createTool("iconable", "StopIcon", "Stop
		// Transformation", "stop", this, false);
		this.toolBar.addTool(this.buttonStop);
		this.transformButtons.add(this.buttonStop);
		this.toolBar.addSeparator(new Dimension(3, 3));
		// buttonUndoStep = toolBar.createTool("iconable", "StepBackIcon", "Undo
		// Step", "undoStep", this, false);
		this.toolBar.addTool(this.buttonUndoStep);
		this.transformButtons.add(this.buttonUndoStep);
		this.toolBar.addSeparator(this.freeSpace);
		// buttonT = toolBar.createTool("textable", "NT", "Transformation by
		// applying rules non-deterministically (default)", "", this, false);
		this.toolBar.addTool(this.buttonT);
		this.buttonT.setForeground(Color.red);
		((TextIcon) this.buttonT.getIcon()).setColor(Color.red);

		// test undo
		this.toolBar.addSeparator(this.freeSpace);
		// buttonUndo = toolBar.createTool("imageable", "undo16x16", "Undo Last
		// Edit", "undo", this, false);
		this.toolBar.addTool(this.buttonUndo);
		// buttonRedo = toolBar.createTool("imageable", "redo16x16", "Redo Last
		// Edit", "redo", this, false);
		this.toolBar.addTool(this.buttonRedo);

		this.toolBar.addSeparator(this.freeSpace);
		// toolBar.addTool("imageable", "layout", "layout", "layout", this,
		// true);
		// toolBar.addTool("imageable","layout","repeatedly arc length
		// layout","arclayout",this,true);
		// toolBar.addTool("imageable","layout","combined1","combinedlayout1",this,true);
		// buttonELayout = toolBar.createTool("imageable","layout","repeatedly
		// graph layout","graphlayout",this,true);
		this.toolBar.addTool(this.buttonLayout);
		this.toolBar.addTool(this.buttonLayoutMenu);
		// TODO spaeter den button deaktivieren und erst aktivieren, wenn er
		// sinnvoll ist.
		// toolBar.addTool("imageable", "lmetric", "Show layout metrics",
		// "lmetric", this, true);
		// toolBar.addTool("imageable","lmetric","jpgoutput","painttojpg",this,true);
	}

	private void selectToolBarModeItem(int editMode) {
		if (editMode == EditorConstants.DRAW)
			selectToolBarModeItem("Draw");
		else if (editMode == EditorConstants.SELECT)
			selectToolBarModeItem("Select");
		else if (editMode == EditorConstants.MOVE)
			selectToolBarModeItem("Move");
		else if (editMode == EditorConstants.ATTRIBUTES)
			selectToolBarModeItem("Attributes");
		else if (editMode == EditorConstants.MAP)
			selectToolBarModeItem("Map");
		else if (editMode == EditorConstants.UNMAP)
			selectToolBarModeItem("Unmap");
		else
			selectToolBarModeItem("Draw");
	}

	public void selectToolBarModeItem(String editMode) {
		String actionComand = "drawMode";
		String emode = editMode.replaceAll(" ", "");
		if (emode.indexOf("Select") != -1)
			actionComand = "selectMode";
		else if (emode.indexOf("Move") != -1)
			actionComand = "moveMode";
		else if (emode.indexOf("Attributes") != -1)
			actionComand = "attributesMode";
		else if (emode.indexOf("Map") != -1)
			actionComand = "mapMode";
		else if (emode.indexOf("Unmap") != -1)
			actionComand = "unmapMode";

		for (int i = 0; i < this.editModeButtons.size(); i++) {
			final JButton b = this.editModeButtons.get(i);
			// System.out.println(((JButton) it).getBackground());
			if (b.getActionCommand().equals(actionComand)) {
				if (!b.isSelected()) {
					b.setBackground(new Color(153, 153, 255));
					b.setSelected(true);

					if (this.ruleEditor.isObjMapping())
						this.ruleEditor.resetEditModeAfterMapping();
					
//					if (this.editmode == EditorConstants.INTERACT_MATCH) 
//						setEditMode(this.lasteditmode);
						
					selectToolBarTransformItem("");
				}
			} else {
				b.setBackground(this.bgc);
				b.setSelected(false);
			}
		}
	}

	public void selectToolBarTransformItem(String actionComand) {
		for (int i = 0; i < this.transformButtons.size(); i++) {
			final JButton b = this.transformButtons.get(i);
			if (b.getActionCommand().equals(actionComand)) {
				if (!b.isSelected()) {
					b.setBackground(Color.yellow);
					b.setSelected(true);
				}
			} else {
				b.setBackground(this.bgc);
				b.setSelected(false);
			}
		}
	}

	private void resetEnabledOfToolBarItems(GraGraTreeNodeData nodeData,
			boolean gragraIsChanged) {
		if (this.gragra != null) {
			if (nodeData != null) {
				if (gragraIsChanged) {
					if (nodeData.isAtomic() || nodeData.isConclusion()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									if (((TextIcon) b.getIcon()).getText()
											.equals("IR")) {
										b.setIcon(new TextIcon("IA", true));
										b.setToolTipText("Identic Atomic");
									}
									if (((TextIcon) b.getIcon()).getText()
											.equals("IN")
											|| ((TextIcon) b.getIcon())
													.getText().equals("IP")
											|| ((TextIcon) b.getIcon())
													.getText().equals("IGAC")	) {
										((TextIcon) b.getIcon())
												.setEnabled(false);
										b.setEnabled(false);
									} else
										((TextIcon) b.getIcon())
												.setEnabled(true); 
								}
							}
						}
						resetSelectIcons(true);
						resetTransformIcons(false);
					} else if (nodeData.isRuleConstraint()
							|| nodeData.isConstraint()
							|| nodeData.isAtomApplCond()) {
						for (int i = 0; i <this. toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								if (b == this.buttonLayout
										|| b == this.buttonLayoutMenu) {
									b.setEnabled(true);
								} else
									b.setEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									((TextIcon) b.getIcon()).setEnabled(false);
								}
							}
						}
						resetSelectIcons(false);
						resetTransformIcons(false);
					} else if (nodeData.isGraGra() || nodeData.isGraph()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									((TextIcon) b.getIcon()).setEnabled(true);
								}
							}
						}
						resetSelectIcons(true);
						resetTransformIcons(true);
					} else if (nodeData.isRule() || nodeData.isNAC()
							|| nodeData.isPAC()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									if (((TextIcon) b.getIcon()).getText()
											.equals("IA")) {
										b.setIcon(new TextIcon("IR", true));
										b.setToolTipText("Identic Rule");
									}
									((TextIcon) b.getIcon()).setEnabled(true);
								}
							}
						}
						if (nodeData.isRule()) {
							resetButtonIdenticNAC(false);// nacExists);
							resetButtonIdenticPAC(false);// pacExists);
						} else if (nodeData.isNAC()) {
							resetButtonIdenticPAC(false);// pacExists);
						} else if (nodeData.isPAC()) {
							resetButtonIdenticNAC(false);// nacExists);
						}
						resetSelectIcons(true);
						resetTransformIcons(true);
					}
				} else { // !gragraIsChanged
					if (nodeData.isAtomic() || nodeData.isConclusion()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									if (((TextIcon) b.getIcon()).getText()
											.equals("IR")) {
										b.setIcon(new TextIcon("IA", true));
										b.setToolTipText("Identic Atomic");
									}
									((TextIcon) b.getIcon()).setEnabled(true);
								}
							}
						}
						resetButtonIdenticNAC(false);
						resetButtonIdenticPAC(false);
						resetButtonIdenticNestedAC(false);

						resetSelectIcons(true);
						resetTransformIcons(false);
					} else if (nodeData.isRuleConstraint()
							|| nodeData.isConstraint()
							|| nodeData.isAtomApplCond()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								if (b == this.buttonLayout
										|| b == this.buttonLayoutMenu) {
									b.setEnabled(true);
								} else
									b.setEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									((TextIcon) b.getIcon()).setEnabled(false);
								}
							}
						}
						resetSelectIcons(false);
						resetTransformIcons(false);
					} else if (nodeData.isGraGra() || nodeData.isGraph()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									((TextIcon) b.getIcon()).setEnabled(true);
								}
							}
						}
						resetSelectIcons(true);
						resetTransformIcons(true);
					} else if (nodeData.isRule() || nodeData.isNAC()
							|| nodeData.isPAC() || nodeData.isNestedAC()) {
						for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
							if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
								JButton b = (JButton) this.toolBar
										.getComponentAtIndex(i);
								b.setEnabled(true);
								if (b == this.buttonRedo)
									updateRedoButton();
								if (b == this.buttonUndo)
									updateUndoButton();
								if (b == this.buttonUndoStep)
									setUndoStepButtonEnabled(false);
								if (b.getIcon() instanceof TextIcon) {
									if (((TextIcon) b.getIcon()).getText()
											.equals("IA")) {
										b.setIcon(new TextIcon("IR", true));
										b.setToolTipText("Identic Rule");
									}
									((TextIcon) b.getIcon()).setEnabled(true);
								}
							}
						}
						if (nodeData.isRule()) {
							this.resetButtonIdenticNAC(false);
							this.resetButtonIdenticPAC(false);
							this.resetButtonIdenticNestedAC(false);
						} else if (nodeData.isNAC()) {
							this.resetButtonIdenticPAC(false);
							this.resetButtonIdenticNestedAC(false);
						} else if (nodeData.isPAC()) {
							this.resetButtonIdenticNAC(false);
							this.resetButtonIdenticNestedAC(false);
						} else if (nodeData.isNestedAC()) {
							this.resetButtonIdenticNAC(false);
							this.resetButtonIdenticPAC(false);
						}
						resetSelectIcons(true);
						resetTransformIcons(true);
					}
				}
			} else { // extraNode == null
				for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
					if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
						JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
						b.setEnabled(true);
						if (b == this.buttonRedo)
							updateRedoButton();
						if (b == this.buttonUndo)
							updateUndoButton();
						if (b == this.buttonUndoStep)
							setUndoStepButtonEnabled(false);
						if (b.getIcon() instanceof TextIcon) {
							((TextIcon) b.getIcon()).setEnabled(true);
						}
					}
				}
				resetSelectIcons(true);
				resetTransformIcons(true);
			}
		} else { // gragra == null
			for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
				if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
					JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
					b.setEnabled(false);
					if (b.getIcon() instanceof TextIcon)
						((TextIcon) b.getIcon()).setEnabled(false);
				}
			}
			resetSelectIcons(false);
			resetTransformIcons(false);
			resetStartTrafoIcon(false);
		}
		this.toolBar.revalidate();
	}

	private void resetSelectIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getIcon() instanceof DeselectAllIcon) {
					((DeselectAllIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof SelectAllIcon) {
					((SelectAllIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof SelectNodeTypeIcon) {
					((SelectNodeTypeIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof SelectArcTypeIcon) {
					((SelectArcTypeIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				}
			}
		}
	}

	private void resetEditIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				// System.out.println(b.getActionCommand());
				if (b.getActionCommand().equals("attributes")
						|| b.getActionCommand().equals("delete")
						|| b.getActionCommand().equals("copy")
						|| b.getActionCommand().equals("paste")) {
					b.setEnabled(enable);
				}
				if (b.getIcon() instanceof TextIcon) {
					if (((TextIcon) b.getIcon()).getText().equals("IR")
							|| ((TextIcon) b.getIcon()).getText().equals("IA")) {
						((TextIcon) b.getIcon()).setEnabled(enable);
						b.setEnabled(enable);
					}
					if (((TextIcon) b.getIcon()).getText().equals("IN")) {
						if (this.nacExists) {
							resetButtonIdenticNAC(enable);
						} else {
							resetButtonIdenticNAC(false);
						}
					}
					if (((TextIcon) b.getIcon()).getText().equals("IP")) {
						if (this.pacExists) {
							resetButtonIdenticPAC(enable);
						} else {
							resetButtonIdenticPAC(false);
						}
					}
					if (((TextIcon) b.getIcon()).getText().equals("IGAC")) {
						if (this.acExists) {
							resetButtonIdenticNestedAC(enable);
						} else {
							resetButtonIdenticNestedAC(false);
						}
					}
				} else if (b.getIcon() instanceof SelectNodeTypeIcon) {
					((SelectNodeTypeIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof SelectArcTypeIcon) {
					((SelectArcTypeIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof SelectAllIcon) {
					((SelectAllIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof DeselectAllIcon) {
					((DeselectAllIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				}
			}
		}
	}

	private void resetButtonIdenticNAC(boolean b) {
		((TextIcon) this.buttonIdenticNAC.getIcon()).setEnabled(b);
		this.buttonIdenticNAC.setEnabled(b);
	}

	private void resetButtonIdenticPAC(boolean b) {
		((TextIcon) this.buttonIdenticPAC.getIcon()).setEnabled(b);
		this.buttonIdenticPAC.setEnabled(b);
	}

	private void resetButtonIdenticNestedAC(boolean b) {
		((TextIcon) this.buttonIdenticNestedAC.getIcon()).setEnabled(b);
		this.buttonIdenticNestedAC.setEnabled(b);
	}
	
	private void resetEditModeIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getActionCommand().equals("drawMode")
						|| b.getActionCommand().equals("selectMode")
						// || b.getActionCommand().equals("moveMode")
						|| b.getActionCommand().equals("attributesMode")
						|| b.getActionCommand().equals("mapMode")
						|| b.getActionCommand().equals("unmapMode")) {
					b.setEnabled(enable);
				}
			}
		}
	}

	public void resetEditModeAfterMapping(int editMode) {
		// System.out.println("GraGraEditor.resetEditModeAfterMapping ");
		if (this.editmode == EditorConstants.INTERACT_MATCH
				|| this.editmode == EditorConstants.MAP
				|| this.editmode == EditorConstants.UNMAP) {
			if (this.editmode == EditorConstants.INTERACT_MATCH)
				selectToolBarTransformItem("");
			this.ruleEditor.resetEditModeAfterMapping();
			this.lasteditmode = editMode;
			setEditMode(editMode);
			this.forwardModeCommand(EditorConstants.getModeOfID(editMode));
		}
	}

	protected void resetMoveEditMode() {
		setEditMode(EditorConstants.MOVE);
		this.forwardModeCommand(EditorConstants.getModeOfID(EditorConstants.MOVE));
	}
	
	private void resetTransformIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getIcon() instanceof MatchIcon) {
					((MatchIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof CompletionIcon) {
					((CompletionIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof StepIcon) {
					((StepIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof StartIcon) {
					((StartIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(true);
				} else if (b.getIcon() instanceof StopIcon) {
					((StopIcon) b.getIcon()).setEnabled(false);
					b.setEnabled(false);
				} else if (b.getIcon() instanceof StepBackIcon) {
					((StepBackIcon) b.getIcon()).setEnabled(false);
					b.setEnabled(false);
				}
			}
		}
	}

	private void resetStartTrafoIcon(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getIcon() instanceof StartIcon) {
					((StartIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} 
			}
		}
	}
	
	public void enableNestedApplCond(boolean b) {
		if (b) {
			this.toolBar.addSeparator(new Dimension(3, 3), this.indxIdenticNestedAC);
			this.toolBar.addTool(this.buttonIdenticNestedAC, this.indxIdenticNestedAC+1); 
		}
	}
	
	public void enableStopButton(boolean b) {
		((StopIcon) this.buttonStop.getIcon()).setEnabled(b);
		this.buttonStop.setEnabled(b);
	}

	public void setUndoStepButtonEnabled(boolean b) {
		((StepBackIcon) this.buttonUndoStep.getIcon()).setEnabled(b);
		this.buttonUndoStep.setEnabled(b);
	}

	public void updateUndoButtonAfterAttrEdit(final GraphPanel p) {
		if (this.attrChanged) {
			p.getGraph().undoManagerEndEdit();
			updateUndoButton();
			this.attrChanged = false;
		} else {
			p.getGraph().undoManagerLastEditDie();
		}
	}

	public void updateUndoButton() {
		if (this.undoManager != null && this.undoManager.isEnabled()) {
			if (this.undoManager.canUndo())
				this.buttonUndo.setEnabled(true);
			else 
				this.buttonUndo.setEnabled(false);
		} else {
			this.buttonUndo.setEnabled(false);
		}
	}

	public void updateRedoButton() {
		if (this.undoManager != null && this.undoManager.isEnabled()) {
			if (this.undoManager.canRedo())
				this.buttonRedo.setEnabled(true);
			else
				this.buttonRedo.setEnabled(false);
		} else {
			this.buttonRedo.setEnabled(false);
		}
	}

	public void setUndoButtonEnabled(boolean b) {
		if (this.undoManager != null && this.undoManager.isEnabled()) {
			if (b && this.undoManager.canUndo())
				this.buttonUndo.setEnabled(true);
			else if (!b && !this.undoManager.canUndo())
				this.buttonUndo.setEnabled(false);
		} else {
			this.buttonUndo.setEnabled(false);
		}
	}

	public void undoEdit() {
		if (getGraGra() == null || !getGraGra().getGraph().isEditable())
			return;
		if (!this.interpreting && !this.layering && !this.sequences) {
			if (this.undoManager != null && this.undoManager.isEnabled()
					&& this.undoManager.canUndo()) {
				getGraGra().getGraph().setTransformChangeEnabled(false);
				this.undoManager.undo();
				updateGraphics();
				if (!this.undoManager.canUndo()) {
					this.buttonUndo.setEnabled(false);
					setUndoStepButtonEnabled(false);
				}
				if (this.undoManager.canRedo())
					this.buttonRedo.setEnabled(true);
			}
		}
	}

	public void undoTransformStep() {
		if (getGraGra() != null && this.ruleEditor.getRule() != null) {
			if (!getGraGra().getGraph().isEditable())
				return;
			if (!this.interpreting && !this.layering && !this.sequences) {
				// undoTransformStep
				if (this.undoManager != null && this.undoManager.isEnabled()
						&& this.undoManager.canUndo()) {
					selectToolBarTransformItem("undoStep");

					int undoID = this.undoManager.getUndoStateID();
					int lastEndOfStepUndoID = this.undoManager
							.getUndoEndOfTransformStep();
					while (lastEndOfStepUndoID >= 0
							&& undoID >= lastEndOfStepUndoID) {
						if (this.undoManager.canUndo()) {
							this.undoManager.undo();
							undoID = this.undoManager.getUndoStateID();
						} else
							break;
					}
					if (lastEndOfStepUndoID == -1)
						this.setUndoStepButtonEnabled(false);
					if (!this.undoManager.canUndo())
						this.setUndoButtonEnabled(false);
					if (this.undoManager.canRedo())
						this.buttonRedo.setEnabled(true);

					this.graphEditor.updateGraphics();
				}
				selectToolBarTransformItem("");
			}
		}
	}

	public void redoEdit() {
		if (getGraGra() != null || !getGraGra().getGraph().isEditable()) {
			if (!this.interpreting && !this.layering && !this.sequences) {
				if (this.undoManager != null && this.undoManager.isEnabled()
						&& this.undoManager.canRedo()) {
					// if(undoManager.getGraGra() != getGraGra()) {
					// if(undoManager.getGraGra() != null){
					// JOptionPane.showMessageDialog(applFrame, "The following
					// Undo edits are not applicable to the current grammar!"
					// +"\nThe name of corresponding grammar is
					// \""+undoManager.getGraGra().getName()+"\".");
					// return;
					// }
					// }
					this.undoManager.redo();
					updateGraphics();
					// if(!undoManager.canUndo())
					this.buttonRedo.setEnabled(false);

					if (this.undoManager.canUndo())
						this.buttonUndo.setEnabled(true);
				}
			}
		}
	}

	public void discardAllEdits() {
		if (this.undoManager != null && this.undoManager.isEnabled()) {
			this.undoManager.discardAllEdits();
			this.buttonUndo.setEnabled(false);
			this.buttonRedo.setEnabled(false);
			setUndoStepButtonEnabled(false);
		}
	}

	public void resetStepCounter() {
		this.stepCounter = 0;
	}

	public void resetTransformationKindIcon(boolean enable, String text) {
		if (enable) {
			if (text.equals("NT")) {
				((TextIcon) this.buttonT.getIcon()).setText("NT");
				this.buttonT
						.setToolTipText(" Transformation by applying rules non-deterministically (default) ");
			} else if (text.equals("LT")) {
				((TextIcon) this.buttonT.getIcon()).setText("LT");
				this.buttonT
						.setToolTipText(" Transformation by applying rule layers ");
			} else if (text.equals("PT")) {
				((TextIcon) this.buttonT.getIcon()).setText("PT");
				this.buttonT
						.setToolTipText(" Transformation by applyin grule priorities ");
			} else if (text.equals("ST")) {
				((TextIcon) this.buttonT.getIcon()).setText("ST");
				this.buttonT
						.setToolTipText(" Transformation by applying rule sequences ");
			}
			((TextIcon) this.buttonT.getIcon()).setEnabled(true);
			this.buttonT.setEnabled(true);
		} else {
			// ((TextIcon) buttonT.getIcon()).setText("NT");
			// buttonT.setToolTipText(" Transformation by applying rules
			// non-deterministically (default) ");
		}
		if (this.buttonT.getGraphics() != null)
			this.buttonT.update(this.buttonT.getGraphics());
	}

	private void resetTransformDebugIcons(boolean enable) {
		for (int i = 0; i < this.toolBar.getComponentCount(); i++) {
			if (this.toolBar.getComponentAtIndex(i) instanceof JButton) {
				JButton b = (JButton) this.toolBar.getComponentAtIndex(i);
				if (b.getIcon() instanceof MatchIcon) {
					((MatchIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof CompletionIcon) {
					((CompletionIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				} else if (b.getIcon() instanceof StepIcon) {
					((StepIcon) b.getIcon()).setEnabled(enable);
					b.setEnabled(enable);
				}
			}
		}
	}

	private void resetIconsIfTransformInterpret(boolean enable) {
		if (this.applFrame instanceof AGGAppl)
			((AGGAppl) this.applFrame).getGraGraTreeView().resetFileIcons(enable);
		resetEditIcons(enable);
		resetEditModeIcons(enable);
		resetTransformDebugIcons(enable);
		this.toolBar.revalidate();
	}

	private int removeWarning(String msgStr) {
		Object[] options = { "YES", "NO" };
		int answer = JOptionPane.showOptionDialog(null, msgStr, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}

	public void doPrepareLayouterProc() {
		this.graphEditor.getGraph().enableDefaultGraphLayout(false);
		// this.graphEditor.getGraph().straightAllArcs();
		if (this.evolutionaryLayouter.getJpgOutput()) {
			this.jpgPath = this.exportJPEG.getDirectoryForJPEGs(this);
			if (this.jpgPath != null && !this.jpgPath.equals("")) {
				String filename = this.getGraGra().getFileName() + "_0.jpg";
				// System.out.println("GraGraEditor:: dir for JPEGs: "+jpgPath+"
				// start with file: "+filename);
				this.exportJPEG.save(this.graphEditor.getGraphPanel().getCanvas(),
						this.jpgPath + File.separator + filename);
			}
		}
		if (this.evolutionaryLayouter.getWriteMetricValues()) {
			this.graphEditor.getGraph().updateVisibility();
			if (this.metricvalues == null)
				this.metricvalues = createMetricValues(this.graphEditor.getGraph()
						.getVisibleNodes(), this.graphEditor.getGraph()
						.getVisibleArcs());
		}
	}

	public void doPrepareDefaultGraphLayout() {
		this.graphEditor.getGraph().enableDefaultGraphLayout(true);
		if (this.staticNodePositionForGraphLayouter) {
			this.graphEditor.getGraph().enableStaticNodePosition();
		}
	}

	private void saveGraphJPEG() {
		if (this.gragra == null)
			return;

		this.exportJPEG.setDirectory(this.gragra.getDirName());
		this.exportJPEG.save(this.graphEditor.getGraphPanel().getCanvas());
	}

//	private void saveApplJPEG() {
//		exportJPEG.setDirectory(gragra.getDirName());
//		exportJPEG.save(graphEditor.getGraphPanel().getCanvas());
//	}

	private void setNodeIds(EdGraph g) {
		final Vector<EdNode> nodes = g.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			final EdNode n = nodes.get(i);
			if (n.getNodeID() == -1) {
				// g.incLastNodeID();
				// n.setNodeID(g.getLastNodeID());
				n.setNodeID(n.hashCode());
			}
		}
	}

	private Vector<String> createMetricValues(final List<EdNode> nodes,
			final List<EdArc> arcs) {
		final Vector<String> metricValues = new Vector<String>();
		// old title
		// String s = new
		// String("ggen\tn_ov\tn_e_ov\te_xing\te_dif\tp_miss\t\tn_mov\tclust\t\t#n\t#e");
		// new title
		String s = new String(
				"A  \tB \tC \tD   \tE    \tF     \tG    \tH    \tI          J          K       ");
		metricValues.add(s);
		String s1 = "";
		for (int i = 0; i < 94; i++) {
			s1 = s1.concat("-");
		}
		metricValues.add(s1);

		s = new String(
				"age\t#n\t#e\tn_ov\tn_eov\te_xing\te_dif\tn_mov\td_single   d_mental   d_layout");
		metricValues.add(s);
		s1 = "";
		for (int i = 0; i < 94; i++) {
			s1 = s1.concat("-");
		}
		metricValues.add(s1);

		LayoutMetrics lmetric = this.evolutionaryLayouter.getLayoutMetrics();
		int age = 0;
		s = new String(age + "\t");
		int nodesNm = nodes.size();
		s += nodesNm + "\t";
		int arcsNm = arcs.size();
		s += arcsNm + "\t";

		int nodeOverlapping = lmetric.getNodeIntersect(nodes, false);
		s += nodeOverlapping + "\t";
		int arcNodeOverlapping = lmetric.getArcNodeIntersect(nodes, arcs);
		s += arcNodeOverlapping + "\t";
		int arcArcOverlapping = lmetric.getArcArcIntersect(arcs);
		s += arcArcOverlapping + "\t";

		s += lmetric.getAverageArcLengthDeviation(arcs) + "\t";
		s += "N/A\t";

		float singleDistance = lmetric.getSingleDistance(nodesNm, arcsNm,
				nodeOverlapping, arcNodeOverlapping, arcArcOverlapping);
		String str_singleDistance = String.valueOf(singleDistance);
		s += str_singleDistance.substring(0,
				str_singleDistance.indexOf('.') + 2)
				+ "\t   ";

		s += "N/A\t      ";

		s += "N/A";

		// s += lmetric.getPatternMistakes(this.getGraph())+"\t\t";
		// s += "N/A";

		metricValues.add(s);
		return metricValues;
	}

	private void writeMetricValues(String filename) {
		try {
			final BufferedWriter buf = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filename)));
			for (int i = 0; i < this.metricvalues.size(); i++) {
				buf.write(this.metricvalues.get(i) + "\n");
			}
			buf.flush();
			buf.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void enableStaticNodePositionForGraphLayouter(boolean enable) {
		this.staticNodePositionForGraphLayouter = enable;
		this.graphEditor.enableStaticNodePositionForGraphLayouter(enable);
	}

	/*
	private void setStaticNodePositionForGraphLayouter() {
		this.getGraph().setStaticNodePositionForGraphLayouter();
	}

	
	private void doDefaultELayoutProc() {
		// System.out.println("GraGraEditor.doDefaultELayoutProc()...");
		this.getGraph().updateVisibility();
		final List<EdNode> visiblenodes = this.getGraph().getVisibleNodes();
		final List<EdArc> visiblearcs = this.getGraph().getVisibleArcs();

		this.getGraph().updateNodePosEtoL(visiblenodes);
		// Layouter l = new Layouter(300,
		// this.getPanelOfGraph(this.getGraph()).getSize());
		final Dimension neededpanel = evolutionaryLayouter
				.getNeededPanelSize(visiblenodes);
		final int panelx = this.getPanelOfGraph(this.getGraph()).getWidth();
		final int panely = this.getPanelOfGraph(this.getGraph()).getHeight();
		neededpanel.setSize(Math.max(neededpanel.width, panelx), Math.max(
				neededpanel.height, panely));// mindestens so gross wie die
		// vorhandene zeichenflaeche
		evolutionaryLayouter.setPanelSize(neededpanel);
		this.getPanelOfGraph(this.getGraph()).getCanvas().setSize(neededpanel);
		// layouter.layout(this.getGraph(), 100);
		evolutionaryLayouter.layout(this.getGraph(), visiblenodes, visiblearcs);
	}

	
	private void doByArcLengthELayoutProc() {
		this.getGraph().updateVisibility();
		final List<EdNode> visiblenodes = this.getGraph().getVisibleNodes();
		final List<EdArc> visiblearcs = this.getGraph().getVisibleArcs();

		this.getGraph().updateNodePosEtoL(visiblenodes);
		// Layouter l = new
		// Layouter(300,this.getPanelOfGraph(this.getGraph()).getSize());
		final Dimension neededpanel = evolutionaryLayouter
				.getNeededPanelSize(visiblenodes);
		final int panelx = this.getPanelOfGraph(this.getGraph()).getWidth();
		final int panely = this.getPanelOfGraph(this.getGraph()).getHeight();
		neededpanel.setSize(Math.max(neededpanel.width, panelx), Math.max(
				neededpanel.height, panely));// mindestens so gross wie die
		// vorhandene zeichenflaeche
		evolutionaryLayouter.setPanelSize(neededpanel);
		this.getPanelOfGraph(this.getGraph()).getCanvas().setSize(neededpanel);
		// layouter.layoutByArcLength(this.getGraph(), 1,
		// layouter.getBeginTemperature());
		evolutionaryLayouter.layoutByArcLength(this.getGraph(), visiblenodes,
				visiblearcs);
	}

	
	private void doCombinedELayoutProc() {
		this.getGraph().updateVisibility();
		final List<EdNode> visiblenodes = this.getGraph().getVisibleNodes();
		final List<EdArc> visiblearcs = this.getGraph().getVisibleArcs();

		this.getGraph().updateNodePosEtoL(visiblenodes);
		// Layouter l = new
		// Layouter(300,this.getPanelOfGraph(this.getGraph()).getSize());
		final Dimension neededpanel = evolutionaryLayouter
				.getNeededPanelSize(visiblenodes);
		final int panelx = this.getPanelOfGraph(this.getGraph()).getWidth();
		final int panely = this.getPanelOfGraph(this.getGraph()).getHeight();
		neededpanel.setSize(Math.max(neededpanel.width, panelx), Math.max(
				neededpanel.height, panely));
		// mindestens so gross wie die vorhandene zeichenflaeche
		evolutionaryLayouter.setPanelSize(neededpanel);
		this.getPanelOfGraph(this.getGraph()).getCanvas().setSize(neededpanel);
		evolutionaryLayouter.combinedLayout(this.getGraph(), visiblenodes,
				visiblearcs, evolutionaryLayouter.getIterationCount(), 10, 50);
	}
*/
	
	public void doStandardLayoutProc() {
		if (this.staticNodePositionForGraphLayouter) {
			this.getGraph().enableStaticNodePosition();
		}

		if (this.evolutionaryLayouter.isEnabled()) {
			this.doStandardELayoutProc(this.getGraph());
		} else {
			this.getGraph().enableDefaultGraphLayout(true);
			this.getGraph().update();
		}
	}

	public void doStandardELayoutProc(EdGraph g) {
		// System.out.println("\nGraGraEditor.doStandardELayoutProc()... graph:
		// "+g.getName());
		g.updateVisibility();
		final List<EdNode> visiblenodes = g.getVisibleNodes();
		final List<EdArc> visiblearcs = g.getVisibleArcs();

		if (g.getNodes().size() <= 1) {
			return;
		}

		g.enableDefaultGraphLayout(false);

		g.straightAllArcs();

		// if (g.getBasisGraph().isCompleteGraph()) {
		// layouter.setOldEdGraph(g.copy());
		// g.incGraphGen();
		// }

		g.updateNodePosEtoL(visiblenodes);
		g.updateLengthOfLayoutEdge(visiblearcs, this.evolutionaryLayouter
				.getGeneralEdgeLength());
		this.setNodeIds(g);
		LayoutMetrics lmetrics = this.evolutionaryLayouter.getLayoutMetrics();
		// neue Cluster der nodes berechnen
		for (int i = 0; i < g.getNodes().size(); i++) {
			g.getNodes().get(i).calculateCluster(lmetrics.getEpsilon(),
					g.getNodes());
		}
		Dimension neededpanel = this.evolutionaryLayouter
				.getNeededPanelSize(visiblenodes);
		int panelx = this.getPanelOfGraph(g).getWidth();
		int panely = this.getPanelOfGraph(g).getHeight();
		neededpanel.setSize(Math.max(neededpanel.width, panelx), Math.max(
				neededpanel.height, panely));// mindestens so gross wie die
		// vorhandene zeichenflaeche
		this.getPanelOfGraph(g).getCanvas().setSize(neededpanel);
		this.evolutionaryLayouter.setPanelSize(neededpanel);

		int intersect = this.evolutionaryLayouter.getNodeIntersect(visiblenodes,
				true);
		// System.out.println("NodeIntersect: "+intersect);
		if (intersect > 0) {
			this.evolutionaryLayouter.setFrozenByDefault(false);
			// if(!layouter.isEnabled())
			// layouter.setUseFrozenAsDefault(true);
			this.evolutionaryLayouter.makeRandomLayoutOfNodes(g, visiblenodes);
		}
		// else if(layouter.isEnabled())
		// layouter.setUseFrozenAsDefault(false);
		else
			this.evolutionaryLayouter.setFrozenByDefault(true);

		// layouter.layoutGraph(g,100,1,50);
		this.evolutionaryLayouter.layoutGraph(g, visiblenodes, visiblearcs);
		// spater per option abfragen:
		// funktioniert noch nicht
		if (this.evolutionaryLayouter.isCentre())
			this.evolutionaryLayouter.centreLayout(g, visiblenodes);

		g.resolveArcOverlappings(15);

		g.setCurrentLayoutToDefault(true);
	}

	public void doStepLayoutProc() {
		if (this.evolutionaryLayouter.isEnabled()) {
			this.doStepELayoutProc();
		} else {
			this.getGraph().update();
		}
	}

	private void doStepELayoutProc() {
		// System.out.println("GraGraEditor.doStepELayoutProc()... ");
		this.getGraph().updateVisibility();
		final List<EdNode> visiblenodes = this.getGraph().getVisibleNodes();
		final List<EdArc> visiblearcs = new Vector<EdArc>(); //this.getGraph().getVisibleArcs();

		if (visiblenodes.size() <= 1) {
			return;
		}

		this.getGraph().enableDefaultGraphLayout(false);

		this.evolutionaryLayouter.setOldEdGraph(this.getGraph().copy());
		this.getGraph().incGraphGen();

		this.getGraph().updateNodePosEtoL(visiblenodes);
		
		// do nothing with edges!
//		this.getGraph().updateLengthOfLayoutEdge(visiblearcs,
//				this.evolutionaryLayouter.getGeneralEdgeLength());
		
		this.setNodeIds(this.getGraph());
		LayoutMetrics lmetrics = this.evolutionaryLayouter.getLayoutMetrics();
		// neue Cluster der nodes berechnen
		for (int i = 0; i < this.getGraph().getNodes().size(); i++) {
			this.getGraph().getNodes().get(i).calculateCluster(
					lmetrics.getEpsilon(), this.getGraph().getNodes());
		}
		// blitzalterung fuer nachbarn geloeschter knoten
		if (this.getGraph().isNodeRemoved())
			this.evolutionaryLayouter.shockAging(this.getGraph());
		Dimension neededpanel = this.evolutionaryLayouter
				.getNeededPanelSize(visiblenodes);
		int panelx = this.getPanelOfGraph(this.getGraph()).getWidth();
		int panely = this.getPanelOfGraph(this.getGraph()).getHeight();
		neededpanel.setSize(Math.max(neededpanel.width, panelx), Math.max(
				neededpanel.height, panely));
		// mindestens so gross wie die vorhandene zeichenflaeche
		this.getPanelOfGraph(this.getGraph()).getCanvas().setSize(neededpanel);
		this.evolutionaryLayouter.setPanelSize(neededpanel);
		
		this.evolutionaryLayouter.setFrozenByDefault(false);

		int intersect = this.evolutionaryLayouter.getNodeIntersect(visiblenodes, true);
		if (intersect > 0) {
			this.evolutionaryLayouter.makeRandomLayoutOfNodes(this.getGraph(),
					visiblenodes);
		}

		// boolean didLayout =
		this.evolutionaryLayouter.layoutGraph(this.getGraph(), visiblenodes, visiblearcs);
		
		// spaeter per option abfragen:
		// funtioniert noch nicht richtig
		if (this.evolutionaryLayouter.isCentre())
			this.evolutionaryLayouter.centreLayout(this.getGraph(), visiblenodes);

		this.getGraph().resolveArcOverlappings(15);

		if (this.evolutionaryLayouter.getJpgOutput()) {
			if (this.jpgPath == null && !this.exportJPEG.isCancelled())
				this.jpgPath = this.exportJPEG.getDirectoryForJPEGs(this);
			if (this.jpgPath != null && !this.jpgPath.equals("")) {
				String filename = this.getGraGra().getFileName() + "_"
						+ this.getGraph().getGraphGen() + ".jpg";
				this.exportJPEG.save(this.graphEditor.getGraphPanel().getCanvas(),
						this.jpgPath + File.separator + filename);
			}
		}

		if (this.evolutionaryLayouter.getWriteMetricValues()) {
			if (this.metricvalues == null)
				this.metricvalues = createMetricValues(visiblenodes, visiblearcs);
			String metrics = getMetricValuesString(this.getGraph()
					.getGraphGen(), visiblenodes, visiblearcs,
					this.evolutionaryLayouter.getOldEdGraph().getVisibleNodes(),
					lmetrics);
			this.metricvalues.addElement(metrics);
		}
		// System.out.println("GraGraEditor.doStepELayoutProc()... done ");
	}

	private String getMetricValuesString(int graphAgeGeneration,
			final List<EdNode> nodes, final List<EdArc> arcs,
			final List<EdNode> oldnodes, LayoutMetrics lmetrics) {

		int age = graphAgeGeneration;
		String metrics = new String(age + "\t"); // A
		int nodesNm = nodes.size();
		metrics += nodesNm + "\t"; // B
		int arcsNm = arcs.size();
		metrics += arcsNm + "\t"; // C

		int nodeOverlapping = lmetrics.getNodeIntersect(nodes, false);
		metrics += nodeOverlapping + "\t"; // D
		int arcNodeOverlapping = lmetrics.getArcNodeIntersect(nodes, arcs);
		metrics += arcNodeOverlapping + "\t"; // E
		int arcArcOverlapping = lmetrics.getArcArcIntersect(arcs);
		metrics += arcArcOverlapping + "\t"; // F

		int movementsOfArcs = lmetrics.getAverageArcLengthDeviation(arcs);
		metrics += movementsOfArcs + "\t"; // G
		int movementsOfNodes = lmetrics.getAverageNodeMove(oldnodes, nodes);
		metrics += movementsOfNodes + "\t"; // H

		float singleDist = lmetrics.getSingleDistance(nodesNm, arcsNm,
				nodeOverlapping, arcNodeOverlapping, arcArcOverlapping);
		String str = String.valueOf(singleDist);
		String singleDistance = str;
		if (str.length() > (str.indexOf('.') + 2))
			singleDistance = str.substring(0, str.indexOf('.') + 2);
		metrics += singleDistance + "\t   "; // I

		float mentalDist = lmetrics.getMentalDistance(nodesNm, arcsNm,
				movementsOfNodes, movementsOfArcs);
		str = String.valueOf(mentalDist);
		String mentalDistance = str;
		if (str.length() > (str.indexOf('.') + 2))
			mentalDistance = str.substring(0, str.indexOf('.') + 2);
		metrics += mentalDistance + "\t      "; // J

		float layoutQual = lmetrics.getLayoutQuality(singleDist, mentalDist);
		str = String.valueOf(layoutQual);
		String layoutQuality = str;
		if (str.length() > (str.indexOf('.') + 2))
			layoutQuality = str.substring(0, str.indexOf('.') + 2);
		metrics += layoutQuality; // K

		return metrics;
	}

	/*
	private void doDisplayELayoutMetricsProc() {
		this.getGraph().updateVisibility();
		final List<EdNode> visiblenodes = this.getGraph().getVisibleNodes();
		final List<EdArc> visiblearcs = this.getGraph().getVisibleArcs();

		this.getGraph().updateNodePosEtoL(visiblenodes);
		final LayoutMetrics lm = new LayoutMetrics();
		int aaintersect = lm.getArcArcIntersect(visiblearcs);
		int anintersect = lm.getArcNodeIntersect(visiblenodes, visiblearcs);
		int nnintersect = lm.getNodeIntersect(visiblenodes, false);
		int ogintersect = lm.getOverallIntersect(visiblenodes, visiblearcs);
		// int space = lm.getspaceusage(this.getGraph());
		final String erg = "Layout metrics:\n( the smaller the value the better )\n"
				+ "weighted overall-result: "
				+ ogintersect
				+ "\nedge-edge overlappings: "
				+ aaintersect
				+ "\nedge-node overlappings: "
				+ anintersect
				+ "\nnode-node overlappings: " + nnintersect
//															  +"\n\nRaumausnutzung:
//															  "+space
				;
		JOptionPane.showMessageDialog(this, erg);
	}
*/
	
	public agg.layout.ZestGraphLayout getZestGraphLayouter() {
		 return (this.graphLayouts.getZestLayouter() != null)? this.graphLayouts.getZestLayouter(): null;
	}
	
	public void setGraphLayoutAlgorithmName(final String name) {
		this.graphLayoutAlgorithmName = name;
		this.buttonLayout.setToolTipText(name);
	}

	public boolean doZestLayoutProc(final EdGraph g, GraphPanel gp, 
			final String algorithmname) {
		boolean result = false;
		if (this.graphLayouts.getZestLayouter() != null) {
			this.graphLayouts.getZestLayouter().setGraph(g);
			this.graphLayouts.getZestLayouter().setAlgorithm(algorithmname);
//			this.graphLayouts.getZestLayouter().setGraphDimension(gp.getSize());
			result = this.graphLayouts.getZestLayouter().applyLayout();
		}
		return result;
	}

	public void doGraphLayout() {
		EdGraph g = (this.activePanel == null)? 
							getGraph(): this.activePanel.getGraph();
		GraphPanel gp = (this.activePanel == null)? 
				this.getGraphEditor().getGraphPanel(): this.activePanel;
		
		if (this.graphLayoutAlgorithmName.equals(GraphLayouts.DEFAULT_LAYOUT)) {
			this.doStandardELayoutProc(g);
			gp.updateGraphics(true);
		} else {
			if (doZestLayoutProc(g, gp, this.graphLayoutAlgorithmName)) {
				gp.updateGraphics(true);
			}
		}
	}

	public void activateObjectNameMenuItem(boolean b) {
		this.editPopupMenu.activateObjectNameMenuItem(b);
	}

	// test fuer selektierte Objekte im Hostgraphen als partieller Match
	/*
	private void makePartialMatch() {
		System.out.println("GraGraEditor.makePartialMatch...");
		Vector<EdGraphObject> sel = getGraGra().getGraph().getSelectedObjs();
		Vector<GraphObject> set = new Vector<GraphObject>();
		for (int i = 0; i < sel.size(); i++) {
			set.add(sel.get(i).getBasisObject());
		}
		Graph lhs = ruleEditor.getRule().getBasisRule().getLeft();
		Vector<Hashtable<GraphObject, GraphObject>> maps = lhs
				.getPartialMorphismIntoSet(set);
		if (maps != null && !maps.isEmpty()) {
			// create match
			if (ruleEditor.getRule().getBasisRule().getMatch() == null)
				getGraGra().getBasisGraGra().createMatch(
						ruleEditor.getRule().getBasisRule());

			Hashtable<GraphObject, GraphObject> table = maps.get(0);
			Enumeration<GraphObject> en = table.keys();
			while (en.hasMoreElements()) {
				GraphObject obj = en.nextElement();
				GraphObject img = table.get(obj);
				ruleEditor.getRule().getBasisRule().getMatch().addMapping(obj,
						img);
				if (ruleEditor.getRule().getBasisRule().getMatch()
						.getImage(obj) != null)
					System.out.println("add mapping to match - done");
				else
					System.out.println("add mapping to match - failed");
			}
		}
	}
*/
	
	/*
	 * ************************************************
	 */
	// double scale = 1.0;
	protected final JFrame applFrame;

	protected final JSplitPane splitPane0, splitPane, splitPane1;

	protected int editmode = -1;

	private int lasteditmode = EditorConstants.DRAW;

	protected final TypeEditor typeEditor;

	protected boolean typesAlwaysOn;

	protected final RuleEditor ruleEditor;

	protected final GraphEditor graphEditor;

	protected AttrTopEditor attrEditor;

	private AttrTupleEditor inputParameterEditor;

	private boolean attrEditorExists;

	protected GraphObject graphObjectOfAttrInstance;

	protected boolean attrTypeChanged, attrChanged, attrTypeCreated;

	private int attrMemberCount;

	protected int dividerLocation, dividerLocation1, dividerLocation2;

	protected GraphPanel activePanel;

	private boolean isPostAtomApplCond;

	private boolean iconable;

	private GraGraTreeNodeData treeNodeData;

	protected EdGraGra gragra;

	private EdGraph graphToCopy;

	private boolean nacExists, pacExists, acExists;

	private final GraGraTransform gragraTransform;

	private TransformInterpret interpreter;

	private boolean interpreting;

	protected TransformLayered layeredTransform;

	private boolean layering, continueLayeredTransformLoop;

	protected int layeredRuns;
	protected boolean noMoreStopBeforeResetGraph;

	private TransformRuleSequences transformRuleSequences;

	private boolean sequences;

	private boolean errMsg;

	private Thread tmpTransformThread;

	private boolean animationThread;

	private boolean nodeToAnimateOnfront;

	private boolean sleep;

	private GraphicsExportJPEG exportJPEG;

	private String jpgPath;

	private Vector<String> metricvalues;

	private boolean staticNodePositionForGraphLayouter;

	private int stepCounter;

	protected final NodeAnimation nodeAnimation;

	protected EditUndoManager undoManager;

	// protected JMenu graphLayoutMenu;
	protected String graphLayoutAlgorithmName;
}
