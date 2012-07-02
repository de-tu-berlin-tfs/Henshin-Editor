package agg.gui.editor;

import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import agg.xt_basis.TypeError;
import agg.xt_basis.TypeException;
import agg.xt_basis.Type;
import agg.xt_basis.Node;
import agg.xt_basis.Arc;
import agg.xt_basis.TypeGraph;
import agg.xt_basis.TypeSet;
import agg.xt_basis.UndirectedGraph;
import agg.xt_basis.UndirectedTypeGraph;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdRuleScheme;
import agg.editor.impl.EdType;
import agg.editor.impl.EditUndoManager;
import agg.editor.impl.Loop;

/**
 * 
 * @author $Author: olga $
 * @version $Id: GraphCanvas.java,v 1.39 2010/11/13 02:25:42 olga Exp $
 */
public class GraphCanvas extends JPanel {

	public static final int MAX_XWIDTH = 700;

	public static final int MAX_YHEIGHT = 600;

	private static final int DEPEND_ON_LAST_OBJECT = 0;

//	private static final int DEPEND_ON_MOVED_SCROLLBAR = 1;
//	private static final int DEPEND_ON_MOVED_OBJECT = 2;

//	private final GraphCanvasMouseAdapter mouseAdapter;	
//	private final GraphCanvasMouseMotionAdapter mouseMotionAdapter;
	
	
	public GraphCanvas() {
		super(new BorderLayout(), true);
		
		this.canvas = this;
		this.showAnchor = true;
		
//		ToolTipManager.sharedInstance().setReshowDelay(0);
//		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		//mouseAdapter = 
		new GraphCanvasMouseAdapter(this);
		//mouseMotionAdapter = 
		new GraphCanvasMouseMotionAdapter(this);
		
		this.setOpaque(false);
		
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		
		setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
		this.vsbValue = 0;
		this.hsbValue = 0;
		this.scrollbarValueSaved = false;
				
		if (ClassLoader.getSystemResource("agg/lib/icons/sad.png") != null) {
			this.errorImage = new ImageIcon(ClassLoader.getSystemClassLoader()
					.getResource("agg/lib/icons/sad.png"));
		} else
			this.errorImage = new ImageIcon();

		if (ClassLoader.getSystemResource("agg/lib/icons/good.png") != null) {
			this.okImage = new ImageIcon(ClassLoader.getSystemClassLoader()
					.getResource("agg/lib/icons/good.png"));
		} else
			this.okImage = new ImageIcon();

		if (ClassLoader.getSystemResource("agg/lib/icons/smile.png") != null) {
			this.smileImage = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/smile.png"));
		} else
			this.smileImage = new ImageIcon();

		if (ClassLoader.getSystemResource("agg/lib/icons/scroll.png") != null) {
			this.scrollImage = new ImageIcon(ClassLoader
					.getSystemResource("agg/lib/icons/scroll.png"));
		} else
			this.scrollImage = new ImageIcon();		
	}

	public boolean isOpaque() {
		return false;
	}
	
	/** Gets my preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(this.realWidth, this.realHeight);
	}	

	public void setViewport(GraphPanel vp) {
		this.viewport = vp;
		this.hsb = this.viewport.getHorizontalScrollBar();
		this.vsb = this.viewport.getVerticalScrollBar();
	}

	public GraphPanel getViewport() {
		return this.viewport;
	}

	public void setRealWidth(int w) {
		this.realWidth = w;
	}

	public void setRealHeight(int h) {
		this.realHeight = h;
	}

	public void setGraph(EdGraph eg) {
		this.eGraph = eg;
		if (this.eGraph == null || this.eGraph.getBasisGraph().isEmpty()) {
			return;
		}
		this.eGraph.applyScale(this.scale);
		
		this.setName(this.eGraph.getName());
	}

	public void setChanged(boolean b) {
		this.changed = b;
	}
	
	public void setVisible(boolean vis) {
		this.visible = vis;
	}

	// public void setDirected(boolean direct) { directed = direct; }

	public void setFontStyle(int fstyle) {
		this.fontStyle = fstyle;
		setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

	public void setFontSize(int fsize) {
		this.fontSize = fsize;
		if (this.fontSize == 0) // "tiny" has size 6
			setFont(new Font(this.fontName, this.fontStyle, 6)); // 8
		else
			setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setScale(double s) {
		this.scale = s;
		if (this.scale == 0.5) {
			setFont(new Font(this.fontName, this.fontStyle, (int) (this.fontSize * 0.7)));
		} else if (this.scale == 0.2) {
			setFont(new Font(this.fontName, this.fontStyle, (int) (this.fontSize * 0.3)));
		} else if (this.scale == 2.0) {			
			setFont(new Font(this.fontName, this.fontStyle, (int) (this.fontSize * 1.5)));
		} else {
			setFont(new Font(this.fontName, this.fontStyle, (int) (this.fontSize * this.scale)));
		}
		
		if (this.eGraph != null) {
			this.eGraph.applyScale(this.scale);
		}
	}

	public double getScale() {
		return this.scale;
	}

	public void setAttributeVisible(boolean vis) {
		this.attrVisible = vis;
	}
		
	public void resizeAfterTransform(boolean val) {
		this.needResizeAfterStep = val;
	}

	public int getEditMode() {
		return this.mode;
	}

	public void setEditMode(int mode) {
		if (this.pickedObj != null && this.pickedObj.isWeakselected()) {
			this.pickedObj.setWeakselected(false);
			this.repaint();
		}
		
		switch (mode) {
		case EditorConstants.VIEW:
			this.mode = mode;
			break;
		case EditorConstants.DRAW:
			this.mode = mode;
			break;
		case EditorConstants.ARC:
			this.mode = mode;
			break;
		case EditorConstants.COPY:
			this.mode = mode;
			break;
		case EditorConstants.COPY_ARC:
			this.mode = mode;
			break;
		case EditorConstants.MOVE:
			this.mode = mode;
			break;
		case EditorConstants.SELECT:
			this.mode = mode;
			break;
		case EditorConstants.ATTRIBUTES:
			this.mode = mode;
			break;
		case EditorConstants.STRAIGHT:
			this.mode = mode;
			break;
		case EditorConstants.DELETE:
			this.mode = mode;
			break;
		case EditorConstants.TYPECHANGE:
		case EditorConstants.ATTRS:
			this.mode = mode;
			break;
		case EditorConstants.MAP:
		case EditorConstants.UNMAP:
		case EditorConstants.REMOVE_MAP:
		case EditorConstants.MAPSEL:
			this.mode = mode;
			break;
		case EditorConstants.SET_PARENT:
			this.lastMode = this.mode;
			this.mode = mode;
			this.setToolTipText("Click on a node to add inheritance relation.");
			break;
		case EditorConstants.UNSET_PARENT:
			this.lastMode = this.mode;
			this.mode = mode;
			this.setToolTipText("Click on a parent node to remove inheritance relation.");
			break;
		case EditorConstants.UNMAPSEL:
		case EditorConstants.REMOVE_MAPSEL:
			this.mode = mode;
			break;
//		case EditorConstants.INTERACT_RULE:
//		case EditorConstants.INTERACT_NAC:
//		case EditorConstants.INTERACT_PAC:	
//		case EditorConstants.INTERACT_AC:
		case EditorConstants.INTERACT_MATCH:
			this.mode = mode;
			break;
		case EditorConstants.REMOVE_RULE:
		case EditorConstants.REMOVE_NAC:
		case EditorConstants.REMOVE_PAC:
		case EditorConstants.REMOVE_AC:	
		case EditorConstants.REMOVE_MATCH:
			this.mode = mode;
			break;
		case EditorConstants.IDENTIC_RULE:
		case EditorConstants.IDENTIC_NAC:
		case EditorConstants.IDENTIC_PAC:
		case EditorConstants.IDENTIC_AC:
			this.mode = mode;
			break;
		case EditorConstants.INTERFACE_MODE:
			break;
		case EditorConstants.INTERFACE_SELECT:
			break;
		case EditorConstants.INTERFACE_CLOSE:
			break;
		case EditorConstants.HELP:
			this.mode = mode;
			break;
		default:
			throw new IllegalArgumentException("Unexpected mode");
		}
	}

	protected void propagateMoveEditMode() {
		GraGraEditor ggEd = this.getGraGraEditor();
		if (ggEd != null)
			ggEd.resetMoveEditMode();		
	}
	
	public int getLastEditMode() {
		return this.lastMode;
	}
	
	public void setMsg(final String m) {
		this.msg = m;
	}
	
	public String getMsg() {
		return this.msg;
	}

	public boolean hasChanged() {
		return this.changed;
	}
	
//	public void setSize(Dimension dim) {
//		super.setSize(dim);
//		Thread.dumpStack();
//	}
	
	protected void paintComponent(Graphics grs) {		
		if (grs == null) {
			return;
		}		
		synchronized(this) 
		{
		if ((this.eGraph == null) || this.eGraph.isEmpty()) {
			this.realWidth = 0;
			this.realHeight = 0;
			grs.setColor(Color.WHITE); 
			grs.fillRect(0, 0, getWidth(), getHeight());
		} else {
			grs.setFont(getFont());
			Graphics2D graphics2D = (Graphics2D) grs;

			/* get real graph dimension and clear graphics */
			Dimension graphDim = getGraphDimension();			
			this.realWidth = graphDim.width;
			this.realHeight = graphDim.height;
						
			graphics2D.setColor(getBackground()); 
			graphics2D.fillRect(0, 0, getWidth(), getHeight());			
			/* reset graph panel size if needed */
			if (this.needResizeAfterMove) {
				this.needResizeAfterMove = false;
			} else if (this.needResizeAfterStep) {
				/* after transformation step */
				EdNode lastNode = null;
				if (this.eGraph.getNodes().size() != 0) {
					lastNode = this.eGraph.getNodes().lastElement();
					int newHeight = getHeight();
					while (lastNode.getY() > newHeight) {
						newHeight = newHeight + this.viewport.getHeight()/2;
					}
//					if (newHeight > getHeight()) { }
				} else {
					this.realWidth = this.viewport.getWidth() - this.vsb.getWidth() - 3;
					this.realHeight = this.viewport.getHeight() - this.hsb.getHeight() - 3;
				}
				
				resetValueOfScrollbar(DEPEND_ON_LAST_OBJECT);
				this.needResizeAfterStep = false;
			} 
		
			/* update and draw visible graph objects */
			if (this.eGraph.isCPAgraph()) {
				this.eGraph.forceVisibilityUpdate();
			} else {			
				this.eGraph.updateVisibility();
			}
			
			this.eGraph.drawGraphics(graphics2D, 
								this.eGraph.getVisibleNodes(), 
								this.eGraph.getVisibleArcs(), 
								true, 
								this.attrVisible);		

			// when possible
			drawSelectBox(graphics2D, Color.GREEN);
		} 
		}
	}
	
	private void drawSelectBox (Graphics2D grs, Color c) {
		if (this.selBoxOpen) {
			grs.setColor(c);
			grs.drawLine(selBox.x, selBox.y, selBox.x+selBox.width, selBox.y);
			grs.drawLine(selBox.x, selBox.y, selBox.x, selBox.y+selBox.height);
			grs.setColor(Color.BLACK);
		}
	}
	
	public void openAttrEditorForGraphObject(MouseEvent e) {	
		GraGraEditor ggEditor = getGraGraEditor();
		if (ggEditor != null) {
			this.pickedObj = getPickedObject(e.getX(), e.getY(),
					getGraphics().getFontMetrics());
			if (this.pickedObj != null) {
				if (e.getClickCount() == 2
						&& this.mode == EditorConstants.SELECT) {
					ggEditor.deselectAllProc();
					this.eGraph.select(this.pickedObj);
				}
				if (this.viewport.getParentEditor() instanceof GraphEditor)
					ggEditor.setAttrEditorOnTopForGraphObject(this.pickedObj);
				else if (this.viewport.getParentEditor() instanceof RuleEditor)
					ggEditor.setAttrEditorOnBottomForGtaphObject(this.pickedObj);
			} else {
				ggEditor.resetEditor();
			}
		}
	}
	
	public boolean isDragged() {
		return this.dragged;
	}
	
	public boolean isLeftAndRightPressed() {
		return this.leftPressed //this.selBoxOpen 
				&& this.rightPressed;
	}
	
	public void unsetLeftAndRightPressed() {
//		this.selBoxOpen = false;
		this.leftPressed = false;
		this.rightPressed = false;
	}
	
	public boolean isLeftPressed() {
//		return this.selBoxOpen;
		return this.leftPressed;
	}
	
	public void setLeftPressed(boolean b) {
//		this.selBoxOpen = b;
		this.leftPressed = b;
	}
	
	public boolean isRightPressed() {
		return this.rightPressed;
	}
	
	public void setRightPressed(boolean b) {
		this.rightPressed = b;
	}
	
	public boolean isMagicArc() {
		return this.isMagicArc;
	}
	
	public void setMagicArc(boolean b) {
		this.isMagicArc = b;
	}
	
	public boolean isScrolling() {
		return this.scrolling;
	}
	
	public void setScrolling(boolean b) {
		this.scrolling = b;
	}
	
	public boolean isScrollingByDragging() {
		return this.scrollingByDragging;
	}
	
	public void setScrollingByDragging(boolean b) {
		this.scrollingByDragging = b;
	}

	public boolean canCreateNode() {
		return this.canCreateNode;
	}
	
	public boolean canCreateNodeOfType(final Type t, final Type arcType, final Type srcNodeType) {
		if (t == null) {
			this.canCreateNode = false;
			return false;
		}
		
		if (t.isAbstract()) {
			if (this.eGraph.getTypeSet().getBasisTypeSet().getClan(t).size() == 1) {
				if (//!this.eGraph.isTypeGraph()
						this.eGraph == this.eGraph.getGraGra().getGraph()) {
					String mesg = "Node type &nbsp;<i>"
							+ this.eGraph.getGraGra().getSelectedNodeType()
									.getName()
							+ "</i>&nbsp; is abstract <br>"
							+ "and hasn't any descendants. <br>"
							+ "To create a node of an abstract type isn't allowed.";
					cannotCreateErrorMessage(" Create Node ",  " a node",mesg);
					this.canCreateNode = false;
					return false;
				}
			} else if (//!this.eGraph.isTypeGraph()
						this.eGraph == this.eGraph.getGraGra().getGraph()) {
				if (this.eGraph.getTypeSet().getBasisTypeSet()
						.getLevelOfTypeGraphCheck() > 0) {
					String mesg = "Node type &nbsp;<i>"
							+ this.eGraph.getGraGra().getSelectedNodeType()
									.getName()
							+ "</i>&nbsp; is abstract.<br>"
							+ "To create a node of an abstract type inside of the host graph isn't allowed.";
					cannotCreateErrorMessage(" Create Node ", " a node", mesg);
					this.canCreateNode = false;
					return false;
				}
			}
		} else if (this.eGraph == this.eGraph.getGraGra().getGraph()
						&& arcType == null) {
			TypeError error = this.eGraph.getTypeSet().getBasisTypeSet().canCreateNode(
					this.eGraph.getBasisGraph(), t, this.eGraph.getGraGra().getLevelOfTypeGraphCheck());
			if (error != null) {
				String mesg = error.getMessage();
				cannotCreateErrorMessage(" Create Node ", " a node", mesg);
				this.canCreateNode = false;
				return false;
			}
		} else if (this.eGraph == this.eGraph.getGraGra().getGraph()
						&& arcType != null && srcNodeType != null) {
			List<String> arcTypes = this.eGraph.getTypeSet().getBasisTypeSet().nodeTypeRequiresArcType(
					t, arcType, srcNodeType, this.eGraph.getGraGra().getLevelOfTypeGraphCheck());
			if (arcTypes != null && arcTypes.size() > 0) {
				String mesg = "Current node type  " 
								+ "\""+t.getName()+ "\" \n"
								+ "requires edge(s) of type: \n" 
								+ arcTypes.toString();
				cannotCreateErrorMessage(" Create Node ", " a node", mesg);
				this.canCreateNode = false;
				return false;
			}
		}
		
		this.canCreateNode = true;
		return true;
	}
	
	/* 
	 * Undo-Redo is supported.
	 */
	public EdNode addNode(int x, int y) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return null;
		if (this.eGraph.getBasisGraph().isTypeGraph()
				&& ((TypeGraph)this.eGraph.getBasisGraph())
					.getTypeNode(this.eGraph.getTypeSet().getSelectedNodeType().getBasisType()) != null) {
			return null;
		}
//		TypeError error = this.eGraph.getTypeSet().getSelectedNodeType().getBasisType()
//					.checkIfNodeCreatable(this.eGraph.getBasisGraph(), 
//							this.eGraph.getTypeSet().getBasisTypeSet().getLevelOfTypeGraphCheck());
//		if (error != null) {
//			JOptionPane.showMessageDialog(null, error.getMessage(),
//						"Type Graph Error", JOptionPane.ERROR_MESSAGE);
//			return null;
//		}

		try {
			int X = (int) (x / this.scale);
			int Y = (int) (y / this.scale);
			EdNode en = null;
			en = this.eGraph.addNode(X, Y, this.visible);
			en.applyScale(this.scale);
			en.getLNode().setFrozenByDefault(true);
			
			this.eGraph.addCreatedToUndo(en);
			this.eGraph.undoManagerEndEdit();			
			
			en.drawGraphic(getGraphics());
			
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
			
			this.addToGraphEmbedding(en);
			
			return en;
		} catch (TypeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Create Node Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/*
	 * Undo-Redo is supported.
	 */
	private EdNode addNode(EdType nodeType, int x, int y) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return null;
//		TypeError error = nodeType.getBasisType().checkIfNodeCreatable(
//						this.eGraph.getBasisGraph(), 
//						this.eGraph.getTypeSet().getBasisTypeSet().getLevelOfTypeGraphCheck());
//		if (error != null) {
//			JOptionPane.showMessageDialog(null, error.getMessage(),
//						"Type Graph Error", JOptionPane.ERROR_MESSAGE);
//			return null;
//		}
		
		try {
			int X = (int) (x / this.scale);
			int Y = (int) (y / this.scale);
			EdNode en = null;
			en = this.eGraph.addNode(X, Y, nodeType, true);
			en.applyScale(this.scale);
			en.getLNode().setFrozenByDefault(true);

			this.eGraph.addCreatedToUndo(en);
			this.eGraph.undoManagerEndEdit();
			
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
			
			this.addToGraphEmbedding(en);
			
			return en;
		} catch (TypeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Create Node Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	private void addToGraphEmbedding(final EdGraphObject sourceObject) {
		if (this.eGraph.isSourceGraphOfGraphEmbedding()) {
			final EdRule kernelRule = this.eGraph.getGraGra().getRule(this.eGraph.getBasisGraph());
			if (kernelRule != null) {
				final EdRuleScheme rs = this.eGraph.getGraGra().getRuleScheme(kernelRule.getBasisRule());
				if (rs != null) {
					rs.propagateAddGraphObjectToMultiRule(sourceObject);
				}
			}
		}
	}
	
	private void removeFromGraphEmbedding(final EdGraphObject sourceObject) {
		if (this.eGraph.isSourceGraphOfGraphEmbedding()) {
			final EdRule kernelRule = this.eGraph.getGraGra().getRule(this.eGraph.getBasisGraph());
			if (kernelRule != null) {
				final EdRuleScheme rs = this.eGraph.getGraGra().getRuleScheme(kernelRule.getBasisRule());
				if (rs != null) {
					rs.propagateRemoveGraphObjectToMultiRule(sourceObject);
				}
			}
		}
	}
	
	/**
	 * Undo/Redo is supported.
	 */
	public EdArc addArc(EdGraphObject s, EdGraphObject t, Point anch) throws TypeException {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return null;
		boolean directed = !(this.eGraph.getBasisGraph() instanceof UndirectedGraph
								|| this.eGraph.getBasisGraph() instanceof UndirectedTypeGraph);
		
		boolean doAddArc = (!this.eGraph.isTypeGraph() 
							|| addSimilarParentArc(this.eGraph.getTypeSet().getSelectedArcType().getBasisType(),
														s.getType().getBasisType(), 
														t.getType().getBasisType())) ? true : false;
		if (doAddArc) {
			try {
				EdArc ea = null;
				if (anch != null) {
					anch.x = (int) (anch.x / this.scale);
					anch.y = (int) (anch.y / this.scale);
				}
				ea = this.eGraph.addArc(s, t, anch, directed);
				ea.applyScale(this.scale);
				if (anch != null) {
					ea.getLArc().setFrozenByDefault(true);
				}
	
				this.eGraph.addCreatedToUndo(ea);
				this.eGraph.undoManagerEndEdit();
				
				this.changed = true;
				if (this.eGraph.getGraGra() != null) {
					this.eGraph.getGraGra().setChanged(true);
				}
				
				this.addToGraphEmbedding(ea);
				
				return ea;
				
			} catch (TypeException e) {
				// possible errors: type failed, no parallel edges
				// see Graph.checkConnectValid(Type, Node, Node)
				if (!this.eGraph.isTypeGraph())
					JOptionPane.showMessageDialog(null, e.getMessage(),
						"Create Edge Error", JOptionPane.ERROR_MESSAGE);
				throw e;
			}
		}
		else
			throw new TypeException(new TypeError(TypeError.TYPE_ALREADY_DEFINED, 
					"The similar type edge is already defined for a child node types."));
	}

	private boolean addSimilarParentArc(final Type t, final Type source, final Type target) {
		Arc a = ((TypeGraph)this.eGraph.getBasisGraph()).getTypeGraphChildArc(t, source, target);
		if (a != null) {
			Object[] options = { "Continue", "Cancel" };
			int answer = JOptionPane.showOptionDialog(null, 
					"The similar type edge: <"+t.getName()+"> is already defined between parent node types.\n"
					+"Do you want continue to create this type edge?",
					"Similar Parent and Child Type Graph Edge ", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[1]);
			return (answer == 0);
		}
		return true;
	}
	
	/** Deletes an object on the position specified by the int x, int y 
	 * Undo-Redo is not supported.
	 */
	public boolean deleteObj(int x, int y) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return false;
		
		// hier noch testen wegen undo!!!
		try {
			if (this.eGraph.deleteObj(x, y)) {
				return true;
			} 
			return false;
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " this object",e.getMessage());
			return false;
		}
	}
			
	/** Deletes an layout object specified by the EdGraphObject ego 
	 * Undo-Redo is supported.
	 */
	public void deleteObj(EdGraphObject go) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		if (this.eGraph.getBasisGraph().isTypeGraph()) {
			deleteTypeGraphObject(go, new Vector<EdGraphObject>(), true);
		} 
		else if (this.eGraph.isTargetObjOfGraphEmbedding(go)) {
			// warning this.msg
			if (this.getGraGraEditor() != null) {
				JOptionPane.showMessageDialog(this.getGraGraEditor().applFrame, 
						"Cannot delete this graph object. It should be deleted from the kernel rule.", 
						"Delete Rule Object", 
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, 
					"Cannot delete this graph object. It should be deleted from the kernel rule.", 
					"Delete Rule Object", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (go.isNode()) {
			this.deleteNodeObject((EdNode) go);
			this.unsetPicked();
			this.src = null;
		}
		else if (go.isArc()) {
			this.deleteArcObject((EdArc) go, false, false);
			this.unsetPicked();
			this.src = null;
		}
	}

	/*
	 * Undo-Redo is supported.
	 */
	private void deleteNodeObject(EdNode go) {
		if (this.eGraph.isTargetObjOfGraphEmbedding(go)) {
			return;
		} 	
		
		try {
			TypeError typeError = null;
			// handle incoming arcs
			Vector<EdArc> vIn = this.eGraph.getIncomingArcs(go);
			for (int i = 0; i < vIn.size(); i++) {
				EdArc a = vIn.get(i);
				
				if (this.getViewport().getParentEditor() instanceof GraphEditor) {	
					typeError = this.eGraph.getTypeSet().getBasisTypeSet().checkIfRemovable(a.getBasisArc(), false, true);
					if (typeError != null) {
						cannotDeleteErrorMessage("Graph", " an arc", typeError.getMessage());
						return;
					}	
				}
				
				this.eGraph.addDeletedToUndo(a);
			}
			// handle outgoing arcs	
			Vector<EdArc> vOut = this.eGraph.getOutgoingArcs(go);
			for (int i = 0; i < vOut.size(); i++) {
				EdArc a = vOut.get(i);
				
				if (this.getViewport().getParentEditor() instanceof GraphEditor) {
					typeError = this.eGraph.getTypeSet().getBasisTypeSet().checkIfRemovable(a.getBasisArc(), true, false);
					if (typeError != null) {
						cannotDeleteErrorMessage("Graph", " an arc", typeError.getMessage());
						return;
					}	
				}
				
				if (!vIn.contains(a))
					this.eGraph.addDeletedToUndo(a);
			}
				
			this.eGraph.addDeletedToUndo(go);
			
			this.removeFromGraphEmbedding(go);
			
			this.eGraph.deleteObj(go, false);

			this.eGraph.undoManagerEndEdit();				
			updateUndoButton();	
				
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Graph", " the object",e.getMessage());
		}
	}

	/*
	 * Undo-Redo is supported.
	 */
	private void deleteArcObject(final EdArc go, boolean deleteSrc, boolean deleteTar) {
		if (this.eGraph.isTargetObjOfGraphEmbedding(go)) {
			return;
		}
		
		boolean forceRemoveArc = false;
		if (this.getViewport().getParentEditor() instanceof GraphEditor) {
			TypeError typeError = this.eGraph.getTypeSet().getBasisTypeSet().checkIfRemovable(
												go.getBasisArc(), deleteSrc, deleteTar);
			if (typeError != null) {
				cannotDeleteErrorMessage("Graph", " an arc", typeError.getMessage());
				return;
			}
			forceRemoveArc = true;
		}
		
		try {
			this.eGraph.addDeletedToUndo(go);
			
			this.removeFromGraphEmbedding(go);
			
			this.eGraph.delSelectedArc(go, (deleteSrc && deleteTar) || forceRemoveArc);
			
			this.eGraph.undoManagerEndEdit();				
			updateUndoButton();	
					
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " an arc",e.getMessage());
		}
	}
	
	public void updateUndoButton() {
		if (this.viewport.getParentEditor() instanceof GraphEditor) {
			if (((GraphEditor) this.viewport.getParentEditor()).getGraGraEditor() != null) {
				((GraphEditor) this.viewport.getParentEditor()).getGraGraEditor()
						.updateUndoButton();
			}
		} else if (this.viewport.getParentEditor() instanceof RuleEditor) {
			if (((RuleEditor) this.viewport.getParentEditor()).getGraGraEditor() != null) {
				((RuleEditor) this.viewport.getParentEditor()).getGraGraEditor()
						.updateUndoButton();
			}
		}
	}

	/**
	 * Deletes an arc layout for the used object specified by the Arc bArc. The
	 * used object will be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delArc(Arc bArc) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		try {
			
//			this.removeFromGraphEmbedding(sourceObject);
			
			this.eGraph.delArc(bArc);
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " an edge",e.getMessage());
		}
	}

	/**
	 * Deletes a node layout for the used object specified by the Node bNode.
	 * The used object will be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delNode(Node bNode) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		try {
			
			this.eGraph.delNode(bNode);
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " a node",e.getMessage());
		}
	}

	/**
	 * Deletes a node layout specified by the EdNode eNode. The used object will
	 * be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delSelectedNode(EdNode eNode) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		try {
			this.eGraph.delSelectedNode(eNode);
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " a node",e.getMessage());
		}
	}

	/**
	 * Deletes an arc layout specified by the EdArc eArc. The used object will
	 * be deleted, too.
	 */
	public void delSelectedArc(EdArc eArc) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		try {
			this.eGraph.delSelectedArc(eArc);
			this.changed = true;
			if (this.eGraph.getGraGra() != null) {
				this.eGraph.getGraGra().setChanged(true);
			}
		} catch (TypeException e) {
			cannotDeleteErrorMessage("Type Graph", " an edge",e.getMessage());
		}
	}

	/** Deletes all selected nodes */
	public void deleteSelectedNodes() {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		if (this.eGraph.getBasisGraph().isTypeGraph()) {
			deleteTypeGraphObject((List<?>) this.eGraph.getSelectedObjs().clone());
		} else {
			try {
				this.eGraph.deleteSelectedNodes();
				this.changed = true;
				if (this.eGraph.getGraGra() != null) {
					this.eGraph.getGraGra().setChanged(true);
				}
			} catch (TypeException e) {
				cannotDeleteErrorMessage("Type Graph", " a node",e.getMessage());
			}
		}
	}

	/** Deletes all selected arcs */
	public void deleteSelectedArcs() {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		if (this.eGraph.getBasisGraph().isTypeGraph()) {
			deleteTypeGraphObject((List<?>) this.eGraph.getSelectedObjs().clone());
		} else {
			try {
				this.eGraph.deleteSelectedArcs();
				this.changed = true;
				if (this.eGraph.getGraGra() != null) {
					this.eGraph.getGraGra().setChanged(true);
				}
			} catch (TypeException e) {
				cannotDeleteErrorMessage("Type Graph", " an edge",e.getMessage());
			}
		}
	}
	
	/** Deletes all selected objects (nodes and arcs) */
	public void deleteSelected() {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;
		
		if (this.eGraph.getBasisGraph().isTypeGraph()) {
			deleteTypeGraphObject((List<?>) this.eGraph.getSelectedObjs().clone());
		} 
		else {				
			List<?> list = (List<?>) this.eGraph.getSelectedArcs().clone();
			for (int i=0; i<list.size(); i++) {
				final EdArc obj = (EdArc) list.get(i);
				this.deleteArcObject(obj, obj.getSource().isSelected(), obj.getTarget().isSelected());
			}
			
			list = (List<?>) this.eGraph.getSelectedNodes().clone();
			for (int i=0; i<list.size(); i++) {
				final EdNode obj = (EdNode) list.get(i);
				this.deleteNodeObject(obj);
			}
		}
	}


	private void deleteTypeGraphObject(final List<?> gos) {
		final Vector<EdGraphObject> deletedObjs = new Vector<EdGraphObject>();
		final boolean showWarning = true;
		for (int i = 0; i < gos.size(); i++) {
			EdGraphObject go = (EdGraphObject) gos.get(i);
			if (go.isNode())
				continue;
			
			// first delete edges
			deleteTypeGraphObject(go, deletedObjs, !showWarning);		
		}
		for (int i = 0; i < gos.size(); i++) {
			EdGraphObject go = (EdGraphObject) gos.get(i);
			if (go.isNode()) {
				// now delete nodes
				deleteTypeGraphObject(go, deletedObjs, !showWarning);
			} else
				continue;
		}
	}

	private void deleteTypeGraphObject(
			final EdGraphObject go,
			final List<EdGraphObject> deletedObjs, boolean showWarning) {
		
		if (deletedObjs.contains(go)) {
			// is already deleted
			return;
		}

		boolean canDelete = false;
		boolean used = true;
		String objstr = "";
		// check, if type graph node/edge was used
		if (go.isNode()) {
			objstr = "node";
			if (!this.eGraph.getTypeSet().isTypeGraphNodeUsed(go.getType())
					&& !this.eGraph.getTypeSet().isChildTypeGraphNodeUsed(go.getType())) {
				used = false;
			}
		} else if (go.isArc()) {
			objstr = "edge";
			if (!this.eGraph.getTypeSet().isTypeGraphArcUsed(
										go.getType(),
										((EdArc) go).getSource().getType(),
										((EdArc) go).getTarget().getType())) {
				used = false;
			}
		}
		if (used) {
			if (this.eGraph.getTypeSet().getBasisTypeSet()
					.getLevelOfTypeGraphCheck() != agg.xt_basis.TypeSet.DISABLED) {
				JOptionPane
						.showMessageDialog(
								null,
								"<html><body>Cannot delete type "
										+ objstr
										+ ".<br> "
										+ "There are objects of the type "
										+ objstr
										+ " &nbsp;<i>"
										+ go.getType().getName()
										+ "</i><br>"
										+ "Please disable the type graph before delete this type "
										+ objstr, // + ".</body></html>",
								" Delete type ", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (showWarning) {
				Object[] options = { "Delete", "Cancel" };
				int answer = JOptionPane.showOptionDialog(null,
						"<html><body>Are you sure you want to delete the type "
								+ objstr + " &nbsp;<i>"
								+ go.getType().getName() + "</i> ?<br> "
								+ "There are objects of it.", //</body></html>",
						" Delete type ", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if (answer == JOptionPane.YES_OPTION) {
					canDelete = true;
				}
			} else
				canDelete = true;
		} else
			canDelete = true;

		if (canDelete) {
			Vector<EdGraphObject> objs = new Vector<EdGraphObject>();
			objs.add(go);
			deletedObjs.add(go);

			if (go.isNode()) {
				// // inheritance : TEST!
				// Vector<EdNode> parents = this.eGraph.getParentsOf((EdNode) go);
				// if(parents.size() > 0)
				// this.eGraph.addChangedParentToUndo(go);
				// Vector<EdNode> childrens = this.eGraph.getChildrenOf((EdNode) go);
				// for(int j=0; j<childrens.size(); j++){
				// EdNode ch = (EdNode) childrens.get(j);
				// this.eGraph.addChangedParentToUndo(ch);
				// }

				Vector<EdArc> vIn = this.eGraph.getIncomingArcs((EdNode) go);
				for (int i = 0; i < vIn.size(); i++) {
					EdArc a = vIn.get(i);
					if (!deletedObjs.contains(a)) {
						objs.add(a);
						deletedObjs.add(a);
					}
				}
				Vector<EdArc> vOut = this.eGraph.getOutgoingArcs((EdNode) go);
				for (int i = 0; i < vOut.size(); i++) {
					EdArc a = vOut.get(i);
					if (!deletedObjs.contains(a)) {
						objs.add(a);
						deletedObjs.add(a);
					}
				}
			}
			this.eGraph.addDeletedToUndo(objs);

//			boolean canRemoveTypeGraphObject = false;
//			if (go.isNode())
//				canRemoveTypeGraphObject = this.eGraph.getTypeSet().getBasisTypeSet().canRemoveTypeGraphNode((Node)go.getBasisObject());
//			else 
//				canRemoveTypeGraphObject = this.eGraph.getTypeSet().getBasisTypeSet().canRemoveTypeGraphArc((Arc)go.getBasisObject());
//			if (canRemoveTypeGraphObject) {
				
				try {
//					this.eGraph.deleteObj(go);
					
					this.eGraph.forceDeleteObj(go);
					this.eGraph.undoManagerEndEdit();
					updateUndoButton();
					
					this.eGraph.refreshInheritanceArcs();
					this.changed = true;
					if (this.eGraph.getGraGra() != null) {
						this.eGraph.getGraGra().setChanged(true);
					}
				} catch (TypeException e) {
					cannotDeleteErrorMessage("Type Graph", " this object",e.getMessage());

					if (this.eGraph.getUndoManager() != null) {
						this.eGraph.getUndoManager().undo();
						this.eGraph.getUndoManager().redo();
					}
				}
				
			}
//		}
	}

	public EdGraph getGraph() {
		return this.eGraph;
	}
	
	private EdGraGra getGraGra() {
		if (this.viewport.getParentEditor() instanceof GraphEditor)
			return ((GraphEditor) this.viewport.getParentEditor()).getGraGra();
		else if (this.viewport.getParentEditor() instanceof RuleEditor)
			return ((RuleEditor) this.viewport.getParentEditor()).getGraGra();
		else
			return null;
	}

	private GraGraEditor getGraGraEditor() {
		if (this.viewport.getParentEditor() instanceof GraphEditor)
			return ((GraphEditor) this.viewport.getParentEditor()).getGraGraEditor();
		else if (this.viewport.getParentEditor() instanceof RuleEditor)
			return ((RuleEditor) this.viewport.getParentEditor()).getGraGraEditor();
		else
			return null;
	}

	public boolean isGraphEditor() {
		return (this.viewport.getParentEditor() instanceof GraphEditor);
	}
	
	public boolean isRuleEditor() {
		return (this.viewport.getParentEditor() instanceof RuleEditor);
	}
	
	public void performDeleteInheritanceRel(EdNode srcNode) {
		if ((srcNode != null)
				&&
				(this.eGraph.getTypeSet().getBasisTypeSet().getLevelOfTypeGraphCheck() <= TypeSet.DISABLED
						|| !this.eGraph.getTypeSet().isTypeUsed(srcNode.getType()))
				) {
						
			Vector<EdNode> parents = this.eGraph.getParentsOf(srcNode);
			if (!parents.isEmpty()) {				
				EdNode tarNode = parents.get(0);
				
				boolean canRemoveIR = !this.eGraph.getTypeSet().hasTypeUser(srcNode.getType());			
				if (!canRemoveIR) {				
					Vector<EdArc> usedArcTypes = this.eGraph.getTypeSet().
						getTypeArcOfInheritedArcsInUse(srcNode.getType().getBasisType(), tarNode.getType().getBasisType());
					
					if (!usedArcTypes.isEmpty()) {
						Vector<String> names = new Vector<String>(usedArcTypes.size());
						for (int i = 0; i < usedArcTypes.size(); i++) {
							names.add(usedArcTypes.get(i).getType().getName());			
						}
						Object[] options = { "YES", "CANCEL" };
						int answer = JOptionPane
							.showOptionDialog(
									null,
									"<html><body>Please note:<br>"
											+ "After removing this inheritance relation there is at least <br>"
											+ "one node object of a child node with an edge <br>"
											+ "of no more existent inheritance. <br>"
											+ "Edge type name:  "
											+ names
											+ " .<br><br>"
											+ "Do you want to delete appropriate edges inside of all graphs?",
											//+ "</body></html>",
									" Remove Inheritance Relation ",
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, options,
									options[0]);
						if (answer == JOptionPane.YES_OPTION) {
							// destroy all appropriate edges
							EdGraGra gragra = getGraGra();
							if (gragra != null) {
								for (int i = 0; i < usedArcTypes.size(); i++) {
									EdGraphObject tgo = usedArcTypes.get(i);
									gragra.deleteGraphObjectsOfType(tgo, false, true);
								}
							}
						} else
							return;
	
						canRemoveIR = true;
					} else
						canRemoveIR = true;
				}			
	
				if (canRemoveIR) {
					this.eGraph.addChangedParentToUndo(srcNode);
					if (this.eGraph.deleteInheritanceRelation(srcNode, tarNode)) {
						this.eGraph.undoManagerEndEdit();
	
						this.eGraph.update();
						this.canvas.repaint();
						this.changed = true;
						if (this.eGraph.getGraGra() != null) {
							this.eGraph.getGraGra().setChanged(true);
						}
					} else {
						this.eGraph.undoManagerLastEditDie();
						errSound();
						JOptionPane
								.showMessageDialog(
										null,
										"<html><body>This inheritance relation could not be removed.", //</body></html>",
										" Remove Inheritance Relation ",
										JOptionPane.ERROR_MESSAGE);
					}
				}
				
			} else {
				// in case when a parent node type exists
				// but any inheritance arc does not exist 
				// inside of the (EdGraph) type graph,
				// do remove this inheritance relation.
				Type ptype = srcNode.getType().getBasisType().getParent();
				if (ptype != null) {
					this.eGraph.getTypeSet().getBasisTypeSet().removeInheritanceRelation(srcNode.getType().getBasisType(), ptype);
				}
			}
		} 
	}
	
	public void performDeleteInheritanceRel(EdNode srcNode, EdNode tarNode) {
		if ((srcNode != null)
				&&
				(this.eGraph.getTypeSet().getBasisTypeSet().getLevelOfTypeGraphCheck() <= TypeSet.DISABLED
						|| !this.eGraph.getTypeSet().isTypeUsed(srcNode.getType()))
				) {
						
			boolean canRemoveIR = !this.eGraph.getTypeSet().hasTypeUser(srcNode.getType());			
			if (!canRemoveIR) {
				Vector<EdArc> usedArcTypes = this.eGraph.getTypeSet().
				getTypeArcOfInheritedArcsInUse(srcNode.getType().getBasisType(), tarNode.getType().getBasisType());

				if (!usedArcTypes.isEmpty()) {
				Vector<String> names = new Vector<String>(usedArcTypes.size());
				for (int i = 0; i < usedArcTypes.size(); i++)
					names.add(usedArcTypes.get(i).getType().getName());
				Object[] options = { "YES", "CANCEL" };
				int answer = JOptionPane
						.showOptionDialog(
								null,
								"<html><body>Please note:<br>"
										+ "After removing this inheritance relation there are at least <br>"
										+ "one node object of a child node with an edge <br>"
										+ "of no more existent inheritance. <br>"
										+ "Edge type name:  "
										+ names
										+ " .<br><br>"
										+ "Do you want to delete appropriate edges inside of all graphs?",
										//+ "</body></html>",
								" Remove Inheritance Relation ",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (answer == JOptionPane.YES_OPTION) {
					// destroy all appropriate edges
					EdGraGra gragra = getGraGra();
					if (gragra != null) {
						for (int i = 0; i < usedArcTypes.size(); i++) {
							EdArc tgo = usedArcTypes.get(i);
							gragra.deleteGraphObjectsOfType(tgo, false, true);
						}
					}
				} else
					return;

				canRemoveIR = true;
			} else
				canRemoveIR = true;
			}			

			if (canRemoveIR) {
				this.eGraph.addChangedParentToUndo(srcNode);
				if (this.eGraph.deleteInheritanceRelation(srcNode, tarNode)) {
					this.eGraph.undoManagerEndEdit();

					this.eGraph.update();
					this.canvas.repaint();
					this.changed = true;
					if (this.eGraph.getGraGra() != null) {
						this.eGraph.getGraGra().setChanged(true);
					}
				} else {
					this.eGraph.undoManagerLastEditDie();
					errSound();
					JOptionPane
							.showMessageDialog(
									null,
									"<html><body>This inheritance relation could not be removed.", //</body></html>",
									" Remove Inheritance Relation ",
									JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	}

	public void makeSelectionAt(int X, int Y) {
		EdGraphObject go = select(X, Y);
		if (go != null) {
//			this.eGraph.addSelectedToUndo(go);
//			this.eGraph.undoManagerEndEdit();

			if (go.isNode())
				this.eGraph.drawNode(getGraphics(), (EdNode) go);
			else
				this.eGraph.drawArc(getGraphics(), (EdArc) go);

//			this.updateUndoButton();
		}
	}

	public void makeSelectionAt(EdGraphObject go) {
		if (go != null) {
			this.eGraph.select(go);
			
//			this.eGraph.addSelectedToUndo(go);
//			this.eGraph.undoManagerEndEdit();

			if (go.isNode())
				this.eGraph.drawNode(getGraphics(), (EdNode) go);
			else
				this.eGraph.drawArc(getGraphics(), (EdArc) go);

//			this.updateUndoButton();
		}
	}
	
	public EdGraphObject select(int x, int y) {
		if (this.eGraph == null
//				|| !this.eGraph.isEditable()
				)
			return null;

		int X = (int) (x / this.scale);
		int Y = (int) (y / this.scale);
		EdGraphObject go = this.eGraph.select(X, Y);
//		if (go != null) {
//			this.eGraph.addSelectedToUndo(go);
//			this.eGraph.undoManagerEndEdit();
//		}
		return go;
	}

	/*
	 * Undo-Redo is supported.
	 */
	public void copySelected(int x, int y) {
		if (this.eGraph == null || !this.eGraph.isEditable())
			return;

		int X = (int) (x / this.scale);
		int Y = (int) (y / this.scale);

		this.eGraph.copySelected(X, Y);

		if (!this.eGraph.getSelectedObjs().isEmpty()) {
			Vector<EdGraphObject> vec = new Vector<EdGraphObject>();
			vec.addAll(this.eGraph.getSelectedObjs());

			this.eGraph.addCreatedToUndo(vec);
			this.eGraph.undoManagerEndEdit();
		}
	}

	/*
	 * Undo-Redo is supported.
	 */
	public void copySelected(final EdGraph targetGraph, int x, int y) {
		if (this.eGraph == null || !this.eGraph.isEditable()
				|| targetGraph == null)
			return;

		int X = (int) (x / this.scale);
		int Y = (int) (y / this.scale);

		targetGraph.setGraphToCopy(this.eGraph.getSelectedAsGraph());
		targetGraph.copySelected(X, Y);

		if (!targetGraph.getSelectedObjs().isEmpty()) {
			Vector<EdGraphObject> vec = new Vector<EdGraphObject>();
			vec.addAll(targetGraph.getSelectedObjs());

			targetGraph.addCreatedToUndo(vec);
			targetGraph.undoManagerEndEdit();
		}
	}
	
	public void selectAll() {
		if (this.eGraph == null 
//				|| mode == EditorConstants.VIEW
//				|| !this.eGraph.isEditable()
				)
			return;
		this.eGraph.selectAll();
//		this.eGraph.addSelectedToUndo();
//		this.eGraph.undoManagerEndEdit();
//		updateUndoButton();
	}

	public void deselect(EdGraphObject ego) {
		if (this.eGraph == null)
			return;
		
//		this.eGraph.addDeselectedToUndo();
		this.eGraph.deselect(ego);
//		this.eGraph.undoManagerEndEdit();
//		updateUndoButton();
	}

	public boolean deselectAll() {
		if (this.eGraph == null
//				|| mode == EditorConstants.VIEW
				)
			return false;
		boolean hadSelection = false;
		if (this.eGraph.hasSelection()) {
			hadSelection = true;
//			this.eGraph.addDeselectedToUndo();
			this.eGraph.deselectAll();
//			this.eGraph.undoManagerEndEdit();
//			updateUndoButton();
		}
				
		if (hadSelection 
//				|| this.eGraph.unsetCriticalGraphObjects()
				 )
			return true;
		
		return false;
	}

	public boolean selectNodesOfSelectedNodeType() {
		if (this.eGraph == null
//				|| mode == EditorConstants.VIEW 
//				|| !this.eGraph.isEditable()
				)
			return false;
		boolean hasSelection = false;
		if (this.eGraph.hasSelection()) {
			hasSelection = true;
//			this.eGraph.addDeselectedToUndo();
			this.eGraph.deselectAll();
//			this.eGraph.undoManagerEndEdit();
		}
		this.eGraph.selectObjectsOfSelectedNodeType();
		if (this.eGraph.hasSelection()) {
			hasSelection = true;
//			this.eGraph.addSelectedToUndo();
//			this.eGraph.undoManagerEndEdit();
		}
//		updateUndoButton();
		if (hasSelection)
			return true;
		
		return false;
	}

	public boolean selectArcsOfSelectedArcType() {
		if (this.eGraph == null
//				|| mode == EditorConstants.VIEW 
//				|| !this.eGraph.isEditable()
				)
			return false;
		boolean hasSelection = false;
		if (this.eGraph.hasSelection()) {
			hasSelection = true;
//			this.eGraph.addDeselectedToUndo();
			this.eGraph.deselectAll();
//			this.eGraph.undoManagerEndEdit();
		}
		this.eGraph.selectObjectsOfSelectedArcType();
		if (this.eGraph.hasSelection()) {
			hasSelection = true;
//			this.eGraph.addSelectedToUndo();
//			this.eGraph.undoManagerEndEdit();
		}
//		updateUndoButton();
		if (hasSelection)
			return true;
		
		return false;
	}

	public boolean straigthSelectedArcs() {
		if (this.eGraph == null
//				|| mode == EditorConstants.VIEW
				)
			return false;
		if (this.eGraph.hasSelection()) {
//			Vector<EdGraphObject> vec = new Vector<EdGraphObject>();
//			vec.addAll(this.eGraph.getSelectedArcs());
			if (this.eGraph.straightSelectedArcs()) {
//				this.eGraph.undoManagerEndEdit();
//				this.eGraph.undoManagerEndEdit();
//				updateUndoButton();
				this.changed = true;
				return true;
			} 
//			else {
//				this.eGraph.undoManagerLastEditDie();
//			}
		}
		return false;
	}

	public void setAnchorPoint(final Point p) {
		this.anchor = p;
	}
	
	public Point getAnchorPoint() {
		return this.anchor;
	}
	
	public void setPickedPoint(int startx, int starty) {
		this.x0 = startx;
		this.y0 = starty;
	}
	
	public Point getPickedPoint() {
		return new Point(this.x0, this.y0);
	}
	
	public void setPickedObject(final EdGraphObject go) {
		this.pickedObj = go;
	}
	
	public EdGraphObject getPickedObject() {
		return this.pickedObj;
	}
	
	public void setSourceObject(final EdGraphObject go) {
		this.src = go;
	}
	
	public EdGraphObject getSourceObject() {
		return this.src;
	}
	
	public void setTargetObject(final EdGraphObject go) {
		this.tar = go;
	}
	
	public EdGraphObject getTargetObject() {
		return this.tar;
	}
	
	public EdGraphObject getPickedObject(int X, int Y, FontMetrics Fm) {
		this.pickedNode = null;
		this.pickedArc = null;
		this.pickedTextOfArc = null;
		this.pickedNode = this.eGraph.getPickedNode(X, Y);
		if (this.pickedNode != null) {
			this.pickedObj = this.pickedNode;
			return this.pickedNode;
		}
	
//		this.pickedTextOfArc = this.eGraph.getPickedTextOfArc(X, Y, Fm);
//		if (this.pickedTextOfArc != null) {
//			this.pickedObj = this.pickedTextOfArc;
//			return this.pickedTextOfArc;
//		}
		
		this.pickedArc = this.eGraph.getPickedArc(X, Y);
		if (this.pickedArc != null) {
			this.pickedObj = this.pickedArc;
			return this.pickedArc;
		}
		
		this.pickedTextOfArc = this.eGraph.getPickedTextOfArc(X, Y, Fm);
		if (this.pickedTextOfArc != null) {
			this.pickedObj = this.pickedTextOfArc;
			return this.pickedTextOfArc;
		}
		
		return null;	
	}

	public EdNode getPickedNode(int X, int Y) {
//		if (this.eGraph == null) {
//			return null;
//		}
		return this.eGraph.getPickedNode(X, Y);
	}

	public EdArc getPickedArc(int X, int Y) {
		return this.eGraph.getPickedArc(X, Y);
	}
	
	public void draggingOfObject(MouseEvent e) {
		this.dx = 0;
		this.dy = 0;
		int x = e.getX();
		int y = e.getY(); 
		if (e.getX() <= 0) {
			x = 5;
		}
		if (e.getY() <= 0) {
			y = 5;
		}
		if (!this.startDragging) {
			this.startDragging = true;
			if (!this.pickedObj.isSelected()) {
				this.eGraph.addMovedToUndo(this.pickedObj);
			} else {
				this.eGraph.addMovedToUndo(this.eGraph.getSelectedObjs());
			}
		}
		this.dx = x - this.x0;
		this.dy = y - this.y0;
		if ((Math.abs(this.dx) > 0) || (Math.abs(this.dy) > 0)) {
			this.dragged = true;
			this.x0 = x;
			this.y0 = y;
			if (!this.pickedObj.isSelected() || this.pickedObj.isArc()) {
				movePicked(x, y, this.dx, this.dy);
//				update(getGraphics());
			} else if (this.pickedObj.isSelected() && !this.pickedObj.isArc()) {
				moveSelected(this.dx, this.dy);
				((EdNode)this.pickedObj).drawShadowGraphic(this.getGraphics());
//				update(getGraphics());
			}
		} else {
			this.dx = 0;
			this.dy = 0;
		}
	}
	
	public void endDraggingOfObject() {
		if (this.pickedObj != null && this.pickedObj.isNode()) {
			this.eGraph.undoManagerEndEdit();
			this.updateUndoButton();
//			if (this.isGraphEditor())
//				nodeToFront(this.pickedObj);			
		} 
		else if (this.pickedTextOfArc != null) {				
			this.eGraph.undoManagerEndEdit();
			this.updateUndoButton();				
		} 
		else if (this.pickedArc != null) {				
			this.eGraph.undoManagerEndEdit();
			this.updateUndoButton();
		}
		
		updateAfterDraggingOfObject();	
		
		if (this.eGraph.getGraGra() != null) {
			this.eGraph.getGraGra().setChanged(true);
		}
		this.startDragging = false;
		this.dragged = false;
		this.x0 = 0;
		this.y0 = 0;
	}
	
	private void updateAfterDraggingOfObject() {
		Dimension graphDim = getGraphDimension();
		if ((graphDim.width != this.getWidth()) || (graphDim.height != this.getHeight())) {
			this.setSize(graphDim);
		}
		
		if (this.viewport.getParentEditor() instanceof RuleEditor
					&& ((RuleEditor) this.viewport.getParentEditor())
							.isSynchronMoveOfMappedObjectsEnabled()) {
			if (this.viewport == ((RuleEditor) this.viewport
						.getParentEditor()).getRightPanel()) {
				((RuleEditor) this.viewport.getParentEditor())
							.getLeftPanel().updateGraphics(true);
				((RuleEditor) this.viewport.getParentEditor())
							.getNACPanel().updateGraphics(true);
			} else if (this.viewport == ((RuleEditor) this.viewport
						.getParentEditor()).getLeftPanel()) {
				((RuleEditor) this.viewport.getParentEditor())
							.getRightPanel().updateGraphics(true);
				((RuleEditor) this.viewport.getParentEditor())
							.getNACPanel().updateGraphics(true);
			} else if (this.viewport == ((RuleEditor) this.viewport
						.getParentEditor()).getNACPanel()) {
				((RuleEditor) this.viewport.getParentEditor())
							.getRightPanel().updateGraphics(true);
				((RuleEditor) this.viewport.getParentEditor())
							.getLeftPanel().updateGraphics(true);
			}
		}
	}
	
	
	public EdGraphObject getDraggedObject() {
		if (this.dragged)
			return this.pickedObj;
		
		return null;
	}

	public Dimension getDraggedDimension() {
		if (this.dragged)
			return new Dimension(this.dx, this.dy);
		
		return new Dimension(0, 0);
	}
	
	public void unsetPicked() {
		this.pickedObj = null;
		this.pickedNode = null;
		this.pickedArc = null;
		this.pickedTextOfArc = null;
	}
	
	
	/**
	 * @deprecated
	 * 
	 */
	public void resetScrollBars() {
		if (this.eGraph == null)
			return;
		if (this.scrollbarValueSaved) {
			this.hsb.setValue(this.hsbValue);
			this.vsb.setValue(this.vsbValue);
			this.scrollbarValueSaved = false;
			this.hsbValue = 0;
			this.vsbValue = 0;
		}
	}

	public void nodeToFront(int X, int Y) {
		this.pickedNode = this.eGraph.getPickedNode(X, Y);
		if (this.pickedNode != null) {
			this.eGraph.nodeToFront(this.pickedNode);
		}
	}

	public void nodeToFront(EdGraphObject go) {
		if (go != null && go.isNode()) {
			this.eGraph.nodeToFront((EdNode) go);			
		}
	}

	public Dimension getGraphDimension() {
		if (this.eGraph != null) 
			return  this.eGraph.getGraphDimension();
		
		return new Dimension(0,0);
	}

	
	private void movePicked(int pX, int pY, int dX, int dY) {
		if (this.pickedNode != null) {
			this.eGraph.moveNode(this.pickedNode, dX, dY);
			this.pickedNode.getLNode().setFrozenByDefault(true);
			this.pickedNode.drawShadowGraphic(this.getGraphics());
		} 
		else if (this.pickedTextOfArc != null) {
			this.eGraph.moveTextOfArc(this.pickedTextOfArc, dX, dY);
			this.pickedTextOfArc.drawTextShadowGraphic(this.getGraphics(), pX, pY);
		} 
		else if (this.pickedArc != null) { // Anchor of an edge
			this.eGraph.moveArc(this.pickedArc, dX, dY);
			this.pickedArc.drawShadowGraphic(this.getGraphics());
		}
	}

	public void setEdgeAnchorVisible(boolean b) {
		this.showAnchor = b;
	}
	
	public boolean isEdgeAnchorVisible() {
		return this.showAnchor;
	}

	public void setMagicEdgeSupportEnabled(boolean b) {
		this.magicArcEnabled = b;
	}

	public boolean isMagicEdgeSupportEnabled() {
		return this.magicArcEnabled;
	}

	public void startMagicArc(int X, int Y) {
		if (this.src != null)
			this.magicArcStart = new Point(this.src.getX(), this.src.getY());
	}

	public void setMagicArcStart(final Point p) {
		this.magicArcStart = p;
	}
	
	
	public void drawMagicArc(EdNode from, int x, int y) {
		if (this.magicArcStart != null) {
			Graphics2D g = (Graphics2D) getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);	
			g.setPaint(Color.GREEN);
			g.setStroke(new BasicStroke(2.0f));
			g.draw(new Line2D.Double(x, y, x, y));
			
			if (!checkSourceOfMagicArc((EdNode) this.src, this.src.getX(), this.src.getY()))
				drawErrorImage(this.src.getX(), this.src.getY());

			EdNode to = getPickedNode(x, y);
			if (to != null) {
				if (checkTargetOfMagicArc(from, to)) {
					drawOKImage(1, x, y);
					this.arcError = false;
				}
				else {
					drawErrorImage(x, y);
					this.arcError = true;
				}
			} else {
				if (checkTargetOfMagicArc(this.src.getType(), this.eGraph.getTypeSet()
						.getSelectedNodeType())) {
					drawOKImage(1, x, y);
					this.arcError = false;
				}
				else {
					EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
					if (arcType != null) {
						Vector<Type> v = arcType.getBasisType()
								.getTargetsOfArc(this.src.getType().getBasisType());
						if (!this.eGraph.isTypeGraph() && v.size() == 1) {
							drawOKImage(0, x, y);
							this.arcError = false;
						}
						else {
							drawErrorImage(x, y);
							this.arcError = true;
						}
					}
				}
			}
		}
	}

	public void drawErrorImage(int x, int y) {
		((Graphics2D) getGraphics()).drawImage(this.errorImage.getImage(),
//				this.magicArcStart.x, this.magicArcStart.y,
				this.src.getX(), this.src.getY(),
				null);
	}

	public void drawOKImage(int kind, int x, int y) {
		if (kind == 0) {
			((Graphics2D) getGraphics()).drawImage(this.okImage.getImage(),
//					this.magicArcStart.x, this.magicArcStart.y,
					this.src.getX(), this.src.getY(),
					null);
		} else if (kind == 1) {
			((Graphics2D) getGraphics()).drawImage(this.smileImage.getImage(),
//					this.magicArcStart.x, this.magicArcStart.y,
					this.src.getX(), this.src.getY(),
					null);
		}
	}
		
	public boolean checkSourceOfMagicArc(EdNode from, int x, int y) {
		if (this.eGraph.getTypeSet().getTypeGraph() != null) {
			int tgl = this.eGraph.getTypeSet().getBasisTypeSet()
					.getLevelOfTypeGraphCheck();
			if (tgl > 0) {
				EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
				boolean error = false;
				if (arcType != null) {
					if (!this.eGraph.isTypeGraph())			
						error = !arcType.getBasisType()
							.hasTypeGraphArc(from.getType().getBasisType());
					if (error)
						return false;
				} else
					return false;
			}
		}
		if (this.eGraph.getTypeSet().getSelectedArcType() == null) {
			return false;
		}
		return true;
	}

	public boolean checkTargetOfArc(EdNode from, EdNode to) {
		return checkTargetOfMagicArc(from, to);
	}
	
	private boolean checkTargetOfMagicArc(EdNode from, EdNode to) {
		if (this.eGraph.getTypeSet().getTypeGraph() != null) {
			int tgl = this.eGraph.getTypeSet().getBasisTypeSet()
					.getLevelOfTypeGraphCheck();
			if (tgl > 0) {
				EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
				boolean error = false;
				if (arcType != null) {
					if (this.eGraph.isTypeGraph())	
						error = (this.eGraph.getTypeSet().getBasisTypeSet()
								.getTypeGraphArc(arcType.getBasisType(),
										from.getType().getBasisType(),
										to.getType().getBasisType()) != null);
					else
						error = !arcType.getBasisType()
							.isEdgeCreatable(from.getType().getBasisType(), to
									.getType().getBasisType(), tgl);
					if (error)
						return false;
				} else
					return false;
			}
		}
		if (this.eGraph.getTypeSet().getSelectedArcType() == null) {
			return false;
		}
		return true;
	}

	private boolean checkTargetOfMagicArc(EdType from, EdType to) {
		if (this.eGraph.getTypeSet().getTypeGraph() != null) {
			int tgl = this.eGraph.getTypeSet().getBasisTypeSet()
					.getLevelOfTypeGraphCheck();
			boolean error = false;
			EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
			if (tgl > 0) {
				if (!this.eGraph.isTypeGraph()) {
					error = !arcType.getBasisType()
							.isEdgeCreatable(from.getBasisType(), to
									.getBasisType(), tgl);
				} else {
					error = (this.eGraph.getTypeSet().getBasisTypeSet()
							.getTypeGraphNode(to.getBasisType()) != null);
					if (!error)
						error = (this.eGraph.getTypeSet().getBasisTypeSet()
								.getTypeGraphArc(arcType.getBasisType(),
										from.getBasisType(), to.getBasisType()) != null);
				}
				if (error)
					return false;
			} else if (this.eGraph.isTypeGraph()) {
				error = (this.eGraph.getTypeSet().getBasisTypeSet()
						.getTypeGraphNode(to.getBasisType()) != null);
				if (!error)
					error = (this.eGraph.getTypeSet().getBasisTypeSet()
							.getTypeGraphArc(arcType.getBasisType(),
									from.getBasisType(), to.getBasisType()) != null);
				if (error)
					return false;
			}
		}
		if (this.eGraph.getTypeSet().getSelectedArcType() == null) {
			return false;
		}
		return true;
	}

	private void makeArcWithTargetAt(int X, int Y) {
		if (this.mode == EditorConstants.ARC) {
			return;
		}
		boolean tarWasNull = false;
		this.tar = getPickedNode(X, Y);
		if (this.tar == null) {
			tarWasNull = true;
			EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
			if (arcType != null) {
				Vector<Type> v = arcType.getBasisType()
						.getTargetsOfArc(this.src.getType().getBasisType());
				
				if (checkTargetOfMagicArc(this.src.getType(), this.eGraph.getTypeSet()
						.getSelectedNodeType())) {
					
					EdType nodeType = this.eGraph.getTypeSet()
					.getSelectedNodeType();
					if (v.size() == 1) 
						nodeType = this.eGraph.getTypeSet().getNodeType(v.firstElement());

					if (this.canCreateNodeOfType(nodeType.getBasisType(), 
								arcType.getBasisType(), this.src.getType().getBasisType())) {
						this.tar = addNode(nodeType, X, Y);
					}
				} else {
					if (!this.eGraph.isTypeGraph()) {
						if (v.size() == 1) {
							EdType nodeType = this.eGraph.getTypeSet().getNodeType(
									v.firstElement());

							if (this.canCreateNodeOfType(nodeType.getBasisType(),
									arcType.getBasisType(), this.src.getType().getBasisType())) {
								this.tar = addNode(nodeType, X, Y);
							}
						} else if (v.size() == 0) {
							if (this.eGraph.getTypeSet().getBasisTypeSet()
									.getLevelOfTypeGraphCheck() > 0) {
								if (this.eGraph.getTypeSet().getTypeGraph().getArcs(
										this.eGraph.getTypeSet()
												.getSelectedArcType())
										.isEmpty())
									cannotCreateErrorMessage(
											" Create edge "," an edge",
											"Edge type &nbsp;<i>"
													+ this.eGraph
															.getTypeSet()
															.getSelectedArcType()
															.getBasisType().getName()
													+ "</i>&nbsp; isn't defined in the type graph.");
							}
						}
					} 
				}
			}
		}
		if (this.tar != null) {
			if (this.eGraph.getTypeSet().getSelectedArcType() != null) {		
				try {
					EdArc ea = addArc(this.src, this.tar, this.anchor);
					if (ea != null) {
						this.eGraph.drawArc(getGraphics(), ea);
					} else if (tarWasNull) {
						((EditUndoManager) this.eGraph.getUndoManager()).undo();
					}
				} catch (TypeException ex) {}
			}
			
			((EdNode) this.src).applyScale(this.scale);
			((EdNode) this.src).getLNode().setFrozenByDefault(true);
			((EdNode) this.src).drawGraphic(getGraphics());
			
			((EdNode) this.tar).applyScale(this.scale);
			((EdNode) this.tar).getLNode().setFrozenByDefault(true);
			((EdNode) this.tar).drawGraphic(getGraphics());
		}
		this.mode = EditorConstants.DRAW;
	}
	
	public void makeArcByMagicArc(int X, int Y) {
		boolean tarWasNull = false;
		this.tar = getPickedNode(X, Y);
		if (this.tar == null) {
			tarWasNull = true;
			EdType arcType = this.eGraph.getTypeSet().getSelectedArcType();
			if (arcType == null) {
				cannotCreateErrorMessage(" Create edge "," an edge",
						"There isn't any edge type selected.");
			} else {
				Vector<Type> v = arcType.getBasisType()
						.getTargetsOfArc(this.src.getType().getBasisType());
				
				if (checkTargetOfMagicArc(this.src.getType(), this.eGraph.getTypeSet()
						.getSelectedNodeType())) {
					
					if (v.size() == 1) {
						EdType nodeType = this.eGraph.getTypeSet().getNodeType(
								v.firstElement());

						if (this.canCreateNodeOfType(nodeType.getBasisType(), 
								arcType.getBasisType(), this.src.getType().getBasisType())) {
							this.tar = addNode(nodeType, X, Y);
							if (this.tar != null) {
								((EdNode) this.tar).applyScale(this.scale);
								((EdNode) this.tar).getLNode().setFrozenByDefault(true);
								((EdNode) this.tar).drawGraphic(getGraphics());
							}
						}
					} else {
						EdType nodeType = this.eGraph.getTypeSet()
								.getSelectedNodeType();

						if (this.canCreateNodeOfType(nodeType.getBasisType(),
								arcType.getBasisType(), this.src.getType().getBasisType())) {
							this.tar = addNode(nodeType, X, Y);
							if (this.tar != null) {							
								((EdNode) this.tar).applyScale(this.scale);
								((EdNode) this.tar).getLNode().setFrozenByDefault(true);
								((EdNode) this.tar).drawGraphic(getGraphics());
							}
						}
					}
				} else {
					if (!this.eGraph.isTypeGraph()) {
						if (v.size() == 1) {
							EdType nodeType = this.eGraph.getTypeSet().getNodeType(
									v.firstElement());

							if (this.canCreateNodeOfType(nodeType.getBasisType(),
									arcType.getBasisType(), this.src.getType().getBasisType())) {
								this.tar = addNode(nodeType, X, Y);
								if (this.tar != null) {
									((EdNode) this.tar).applyScale(this.scale);
									((EdNode) this.tar).getLNode().setFrozenByDefault(
											true);								
									((EdNode) this.tar)
											.drawGraphic(getGraphics());
								}
							}
						} else if (v.size() == 0) {
							if (this.eGraph.getTypeSet().getBasisTypeSet()
									.getLevelOfTypeGraphCheck() > 0) {
								if (this.eGraph.getTypeSet().getTypeGraph().getArcs(
										this.eGraph.getTypeSet()
												.getSelectedArcType())
										.isEmpty())
									cannotCreateErrorMessage(
											" Create edge "," an edge",
											"Edge type &nbsp;<i>"
													+ this.eGraph
															.getTypeSet()
															.getSelectedArcType()
															.getBasisType().getName()
													+ "</i>&nbsp; isn't defined in the type graph.");
							}
						}
					} 
				}
			}
		}
		if (this.tar != null) {
			if (this.eGraph.getTypeSet().getSelectedArcType() == null) {
				cannotCreateErrorMessage(" Create edge "," an edge",
						"There isn't any edge type selected.");
			} else if (!this.arcError) {
				try {
					EdArc ea = addArc(this.src, this.tar, this.anchor);
					if (ea != null) {
						this.eGraph.drawArc(getGraphics(), ea);
						this.mode = EditorConstants.DRAW;
					} else if (tarWasNull) {
						((EditUndoManager) this.eGraph.getUndoManager()).undo();
					}
				} catch (TypeException ex) {}
			}
		}
		if (this.tar == null)
			this.canvas.propagateMoveEditMode();
		removeMagicArc();
		update(getGraphics());
//		this.mode = EditorConstants.DRAW;
	}

	public void removeMagicArc() {
		this.src = null;
		this.tar = null;
		this.anchor = null;
		this.magicArcStart = null;
		this.isMagicArc = false;
	}

	/*
	private JFrame getApplFrame() {
		if (this.viewport.getParentEditor() instanceof GraphEditor)
			return ((GraphEditor)this.viewport.getParentEditor()).getApplFrame();
		else if (this.viewport.getParentEditor() instanceof RuleEditor)
			return ((RuleEditor)this.viewport.getParentEditor()).getApplFrame();
		else return null;
	}
*/
	
	public void startSelectBox(int X, int Y) {
		selBox.setLocation(X, Y);
		this.selBoxOpen = true;
	}

	public boolean isSelectBoxOpen() {
		return this.selBoxOpen;
	}
	
	public int getSelectBoxSize() {
		return selBox.width;
	}
	
	public void resizeSelectBox(int X, int Y) {
		if (this.selBoxOpen
				&& (X-this.x0) > 0 
				&& (Y-this.y0) > 0 ) {
			selBox.setSize(X - this.x0, Y - this.y0);
			drawSelectBox((Graphics2D)this.getGraphics(), Color.GREEN);
		}
	}

	
	public void selectObjectsInsideOfSelectBoxAndClose() {
		selectObjectsInside(selBox);
		closeSelectBox();
	}
	
	private void closeSelectBox() {
		drawSelectBox((Graphics2D)this.getGraphics(), Color.WHITE);
		selBox.setLocation(0, 0);
		selBox.setSize(0, 0);
		this.selBoxOpen = false;		
	}
		
	public boolean startScrolling(int X, int Y) {
		if (this.hsb.isShowing() || this.vsb.isShowing()) {									
			((Graphics2D) getGraphics()).drawImage(this.scrollImage.getImage(), X,
					Y, null);
			return true;	
		} 
		return false;
	}

	public void endScrolling() {
		this.scrolling = false;
		this.scrollingByDragging = false;
		update(getGraphics());
	}

	public void scrollGraph(int X0, int Y0, int X, int Y) {
		int dX = X - X0;
		int dY = Y - Y0;

		if (dX > 0) {
			if (this.hsb.getValue() < (this.hsb.getMaximum() - this.hsb.getVisibleAmount()))
				this.hsb.setValue(this.hsb.getValue() + dX);
		} else if (dX < 0) {
			if (this.hsb.getValue() > 0)
				this.hsb.setValue(this.hsb.getValue() + dX);
		}

		if (dY > 0) {
			if (this.vsb.getValue() < (this.vsb.getMaximum() - this.vsb.getVisibleAmount()))
				this.vsb.setValue(this.vsb.getValue() + dY);
		} else if (dY < 0) {
			if (this.vsb.getValue() > 0)
				this.vsb.setValue(this.vsb.getValue() + dY);
		}
	}

	private void selectObjectsInside(Rectangle rect) {
		Vector<EdGraphObject> selSet = new Vector<EdGraphObject>();
		int k = -1;
		boolean deselectDone = false;
		for (int i = 0; i < this.eGraph.getNodes().size(); i++) {
			EdNode n = this.eGraph.getNodes().get(i);
			if (rect.contains(n.getX(), n.getY())) {
				if (!deselectDone) {
					this.eGraph.deselectAll();
					deselectDone = true;
				}
				this.eGraph.select(n);
				selSet.add(n);
				k = i + 1;
				break;
			}
		}
		if (k == -1)
			return;

		for (int i = k; i < this.eGraph.getNodes().size(); i++) {
			EdNode n = this.eGraph.getNodes().get(i);
			if (rect.contains(n.getX(), n.getY())) {
				this.eGraph.select(n);
				selSet.add(n);
			}
		}
		for (int i = 0; i < this.eGraph.getArcs().size(); i++) {
			EdArc ea = this.eGraph.getArcs().elementAt(i);
			if (selSet.contains(ea.getSource())
					&& selSet.contains(ea.getTarget())) {
				this.eGraph.select(ea);
				selSet.add(ea);
			}
		}
		if (selSet.isEmpty()) {
			setForeground(Color.WHITE);
			((Graphics2D) getGraphics()).draw(selBox);
			setForeground(Color.BLACK);
		}
//		else {
//			for (int i=selSet.size()-1; i>=0; i--) 
//				selSet.get(i).drawGraphic(this.getGraphics());
//		}
		
		this.closeSelectBox();
	}

	private void moveSelected(int DX, int DY) {
		for (int i = 0; i < this.eGraph.getSelectedNodes().size(); i++) {
			EdNode en = this.eGraph.getSelectedNodes().elementAt(i);
			this.eGraph.moveNodeAndNotSelectedInOutArcs(en, DX, DY);
		}
		
		for (int i = 0; i < this.eGraph.getArcs().size(); i++) {
			EdArc ea = this.eGraph.getArcs().elementAt(i);
			if (ea.isLine() && ea.hasAnchor()) {
				if (ea.isSelected()) {
					if (ea.getSource().isSelected() 
								&& ea.getTarget().isSelected())
						ea.setAnchor(new Point(ea.getAnchor().x + DX, 
											ea.getAnchor().y + DY));
					else 
						ea.setAnchor(new Point(ea.getAnchor().x + DX/2, 
											ea.getAnchor().y + DY/2));
				}
			} else if (!ea.isLine() && (ea == this.pickedArc)
					&& !ea.getSource().isSelected()) {
				// edge-loop selected & edge-loop picked & source not selected
				Loop loop = ea.toLoop();
				loop.move(((EdNode) ea.getSource()).toRectangle(), ea
						.getAnchorID(), DX, DY);
				ea.setAnchor(Loop.UPPER_LEFT, new Point(loop.x, loop.y));
				ea.setWidth(loop.w);
				ea.setHeight(loop.h);
			}
		}		
	}


	/* reset value of scrollbar */
	private void resetValueOfScrollbar(int dependOn) {
		if (this.eGraph == null)
			return;
		if ((dependOn == DEPEND_ON_LAST_OBJECT)
				&& (this.eGraph.getNodes().size() != 0)) {
			EdGraphObject go = this.eGraph.getNodes().lastElement();
			if (go.getX() >= (this.hsb.getValue() + this.hsb.getVisibleAmount())) {
				this.hsbValue = go.getX() + go.getWidth() - this.hsb.getVisibleAmount();
				this.hsb.setValue(this.hsbValue);
				this.hsbValue = 0;
			}
			if (go.getY() >= (this.vsb.getValue() + this.vsb.getVisibleAmount())) {
				this.vsbValue = go.getY() + go.getHeight() - this.vsb.getVisibleAmount();
				this.vsb.setValue(this.vsbValue);
				this.vsbValue = 0;
			}
		}
	}

	private void cannotDeleteErrorMessage(String title, String what, String mesg) {
		String str = "<html><body>Cannot delete "+what+".<br>"+ mesg; // + "</body></html>";
		JOptionPane.showMessageDialog(null, str, title, JOptionPane.ERROR_MESSAGE);
	}

	public void cannotCreateErrorMessage(String title, String what, String mesg) {
		String str = "<html><body>Cannot create "+what+".<br>"+ mesg; // + "</body></html>";
		JOptionPane.showMessageDialog(null, str, title, JOptionPane.ERROR_MESSAGE);
	}

	private Toolkit tk;

	private void errSound() {
		Frame f = new Frame("Test");
		f.pack();
		this.tk = ((Window) f).getToolkit();
		this.tk.beep();
	}

	
	/****************************************************************/

	protected GraphCanvas canvas;
	
	private boolean leftPressed, rightPressed;
	
	private EdGraph eGraph;

	private boolean visible = true;

	private int mode = EditorConstants.DRAW;

	private int lastMode = EditorConstants.DRAW;

	private String fontName = EditorConstants.FONT_NAME;

	private int fontStyle = EditorConstants.FONT_STYLE;

	private int fontSize = EditorConstants.FONT_SIZE;

	private double scale = 1.0;

	private JScrollBar hsb, vsb;

	private int hsbValue;

	private int vsbValue;

	private boolean scrollbarValueSaved;

	private GraphPanel viewport;
	
//	private JFrame applFrame;
	
	private int realWidth = 100;

	private int realHeight = 100;

	private boolean needResizeAfterMove = false;

	private boolean needResizeAfterStep = false;
	
	private boolean attrVisible = true;

	private EdNode pickedNode;

	private EdArc pickedArc;

	private EdArc pickedTextOfArc;

	private EdGraphObject pickedObj;

	private EdGraphObject src;

	private EdGraphObject tar;

	private Point anchor;

	private int x0 = 0, y0 = 0, dx = 0, dy = 0;

	private final static Rectangle selBox = new Rectangle(0, 0, 0, 0);
	
	private boolean selBoxOpen, changed, dragged, startDragging;
	
	boolean canCreateNode;
	
	private String msg;

	private Point magicArcStart;

	private boolean isMagicArc, showAnchor;

	private boolean magicArcEnabled = true;
	 
	private boolean arcError;

	private boolean scrolling, scrollingByDragging;
	
	final private ImageIcon errorImage, okImage, smileImage, scrollImage;
}
