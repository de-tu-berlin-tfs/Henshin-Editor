package agg.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNode;
import agg.xt_basis.Arc;
import agg.xt_basis.Node;
//import java.awt.Graphics;
//import agg.gui.GraphEditor;
//import agg.gui.RuleEditor;

/**
 * 
 * @author $Author: olga $
 * @version $Id: GraphPanel.java,v 1.7 2010/09/23 08:19:07 olga Exp $
 */
public class GraphPanel extends JPanel {

	private Object itsParent; // RuleEditor or GraphEditor

	protected GraphCanvas canvas;

	protected EdGraph eGraph;

	protected JScrollPane jsp;

	protected Cursor lastEditCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	private boolean mappedObjDeleted = false;

	private boolean attrEditorOn = false;

	private String name = "";

	/**
	 * Create a panel for drawing. The panel contains a view port, vertical and
	 * horizontal scroll bars.
	 */
	public GraphPanel(Object parent) {
		this();
		this.itsParent = parent;
	}

	/**
	 * Create a panel for drawing. The panel contains a view port, vertical and
	 * horizontal scroll bars.
	 */
	public GraphPanel() {
		super(new BorderLayout(), true); // DoubleBuffered
		
		this.setBackground(Color.WHITE);
		this.setForeground(Color.WHITE);
		
		this.canvas = new GraphCanvas();
		try {
			this.jsp = new JScrollPane(this.canvas,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.jsp.setBackground(Color.white);
			this.jsp.setForeground(Color.white);
			this.jsp.getHorizontalScrollBar().setUnitIncrement(30);
			this.jsp.getVerticalScrollBar().setUnitIncrement(30);
			this.jsp.getHorizontalScrollBar().setBlockIncrement(50);
			this.jsp.getVerticalScrollBar().setBlockIncrement(50);
			this.jsp.getHorizontalScrollBar().getModel().setValueIsAdjusting(true);
			this.jsp.getVerticalScrollBar().getModel().setValueIsAdjusting(true);
			add(this.jsp, BorderLayout.CENTER);

			this.canvas.setViewport(this);
		} catch (IllegalArgumentException ex) {
		}
	}
	
	public Object getParentEditor() {
		return this.itsParent;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getName() {
		return this.name;
	}

	public void setBackground(Color c) {
		super.setBackground(c);
		if (this.jsp != null)
			this.jsp.setBackground(c);
		if (this.canvas != null) {
			this.canvas.setBackground(c);
		}
	}

	public JScrollPane getScrollPane() {
		return this.jsp;
	}

	/** Gets my minimum dimension */
	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}

	/** Gets my preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public JScrollBar getHorizontalScrollBar() {
		return this.jsp.getHorizontalScrollBar();
	}

	public JScrollBar getVerticalScrollBar() {
		return this.jsp.getVerticalScrollBar();
	}

	public void setViewportView(GraphCanvas view) {
		this.jsp.setViewportView(view);
	}

	public void updateGraphics() {
		this.canvas.repaint();
	}

	public void updateGraphics(boolean graphDimensionCheck) {
		if (this.eGraph != null) {
			if (graphDimensionCheck) {
				Dimension maxDim = getGraphDimension();
				if ((maxDim.width != 0) || (maxDim.height != 0)) {
					this.canvas.setSize(maxDim);
				}
			}
			this.canvas.repaint();
		}
	}

	public void adjustGraphPanelSize() {
		Dimension maxDim = getGraphDimension();
		if ((maxDim.width != 0) || (maxDim.height != 0))
			this.canvas.setSize(maxDim);
	}

	public void updateGraphicsAfterDelete() {
		if (this.eGraph != null) {
			Dimension maxDim = getGraphDimension();
			if ((maxDim.width != 0) || (maxDim.height != 0)) {
				this.canvas.setSize(maxDim);
			}
			this.canvas.repaint(); 
		}
	}

	private Dimension getGraphDimension() {
		Dimension maxDim = new Dimension(0, 0);
		if (this.eGraph != null) {
			maxDim.setSize(this.canvas.getGraphDimension());
			this.canvas.setRealWidth(maxDim.width);
			this.canvas.setRealHeight(maxDim.height);
			return maxDim;
		} 
		return maxDim;
	}

	public void resizeAfterTransform(boolean val) {
		this.canvas.resizeAfterTransform(val);
	}

	public EdGraph getGraph() {
		return this.eGraph;
	}

	public void setGraph(final EdGraph eg, boolean adjustCanvasSize) {
		this.eGraph = eg;
		this.canvas.setGraph(this.eGraph);
		if (this.eGraph != null) {
			if (adjustCanvasSize) {
				Dimension maxDim = this.canvas.getGraphDimension();			
				this.canvas.setSize(new Dimension(maxDim.width, maxDim.height));
				this.canvas.setRealWidth(maxDim.width);
				this.canvas.setRealHeight(maxDim.height);			
			} else {
				this.canvas.repaint();
			}
		}
		else {
			this.canvas.setSize(new Dimension(getWidth()
					- this.jsp.getVerticalScrollBar().getWidth() - 3, getHeight()
					- this.jsp.getHorizontalScrollBar().getHeight() - 3));
			this.canvas.setRealWidth(this.canvas.getWidth());
			this.canvas.setRealHeight(this.canvas.getHeight());
		}
		this.jsp.getHorizontalScrollBar().setValue(0);
		this.jsp.getVerticalScrollBar().setValue(0);
	}
	
	public void setGraph(final EdGraph eg) {
		this.setGraph(eg, true);
	}

	public GraphCanvas getCanvas() {
		return this.canvas;
	}

	public int getEditMode() {
		return this.canvas.getEditMode();
	}

	public void setEditMode(int m) {
		this.canvas.setEditMode(m);
	}

	private Cursor editCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	public void setEditCursor(Cursor cur) {
		this.editCursor = cur;
	}

	public Cursor getEditCursor() {
		return this.editCursor;
	}

	private int lastEditMode = EditorConstants.DRAW;

	public void setLastEditMode(int m) {
		this.lastEditMode = m;
	}

	public int getLastEditMode() {
		if (this.lastEditMode == EditorConstants.ARC)
			return EditorConstants.DRAW;
		
		return this.lastEditMode;
	}

	public void setLastEditCursor(Cursor cur) {
		this.lastEditCursor = cur;
	}

	public Cursor getLastEditCursor() {
		return this.lastEditCursor;
	}

	public void setVisible(boolean vis) {
		this.canvas.setVisible(vis);
	}

	public void setMappedObjDeleted(boolean b) {
		this.mappedObjDeleted = b;
	}

	public boolean isMappedObjDeleted() {
		return this.mappedObjDeleted;
	}

	public void setAttrEditorActivated(boolean b) {
		this.attrEditorOn = b;
	}

	public boolean isAttrEditorActivated() {
		return this.attrEditorOn;
	}

	/**
	 * Deletes an arc layout for the used object specified by the Arc bArc. The
	 * used object will be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delArc(Arc bArc) {
		this.canvas.delArc(bArc);
	}

	/**
	 * Deletes a node layout for the used object specified by the Node bNode.
	 * The used object will be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delNode(Node bNode) {
		this.canvas.delNode(bNode);
	}

	/**
	 * Deletes a node layout specified by the EdNode eNode. The used object will
	 * be deleted, too.
	 * Undo-Redo is not supported.
	 */
	public void delSelectedNode(EdNode eNode) {
		this.canvas.delSelectedNode(eNode);
	}

	/**
	 * Deletes an arc layout specified by the EdArc eArc. The used object will
	 * be deleted too.
	 * Undo-Redo is not supported.
	 */
	public void delSelectedArc(EdArc eArc) {
		this.canvas.delSelectedArc(eArc);
	}

	/** Deletes all selected nodes 
	 * Undo-Redo is not supported.
	 */
	public void deleteSelectedNodes() {
		this.canvas.deleteSelectedNodes();
	}

	/** Deletes all selected arcs 
	 * Undo-Redo is not supported.
	 */
	public void deleteSelectedArcs() {
		this.canvas.deleteSelectedArcs();
	}

	/** Deletes all selected objects (nodes and arcs) 
	 * Undo-Redo is supported.
	 */
	// called from EditSelPopupMenu, RuleEditor, GraphEditor
	public void deleteSelected() {
		this.canvas.deleteSelected();
	}

	/** Deletes an object on the position specified by the int x, int y 
	 * Undo-Redo is not supported.
	 */
	public boolean deleteObj(int x, int y) {
		return this.canvas.deleteObj(x, y);
	}

	/** Deletes an layout object specified by the EdGraphObject ego 
	 * Undo-Redo is supported.
	 */
	// called from EditPopupMenu
	public void deleteObj(EdGraphObject ego) {
		this.canvas.deleteObj(ego);
	}

	boolean hasSelection() {
		return (this.eGraph != null && this.eGraph.hasSelection())? true: false;
	}
	
	/**
	 * Undo-Redo is not supported.
	 */
	public void deselect(EdGraphObject ego) {
		this.canvas.deselect(ego);
		updateGraphics();
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public EdGraphObject select(int x, int y) {
		return this.canvas.select(x, y);
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public void selectAll() {
		this.canvas.selectAll();
		updateGraphics();
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public void deselectAll() {
		if (this.canvas.deselectAll())
			updateGraphics();
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public void selectNodesOfSelectedNodeType() {
		if (this.canvas.selectNodesOfSelectedNodeType())
			updateGraphics();
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public void selectArcsOfSelectedArcType() {
		if (this.canvas.selectArcsOfSelectedArcType())
			updateGraphics();
	}

	/**
	 * Undo-Redo is not supported.
	 */
	public void straightenSelectedArcs() {
		if (this.canvas.straigthSelectedArcs())
			updateGraphics();
	}

	
}
