package agg.gui.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import agg.attribute.impl.ContextView;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.ruleappl.ObjectFlow;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;

/**
 * This class specifies a graph morphism editor which allows to set mappings
 * of an morphism.
 * 
 * @author $Author: olga $
 */
public class GraphMorphismEditor extends JPanel {

	private final GraphMorphismEditorMouseAdapter mouseAdapter;
	private final GraphMorphismEditorMouseMotionAdapter mouseMotionAdapter;
	
	/**
	 * Creates a graph morphism editor.
	 */
	public GraphMorphismEditor(final JFrame parentFrame) {
		super(new BorderLayout());
		
		this.applFrame = parentFrame;
		
		this.mainPanel = this;
		
		this.mouseAdapter = new GraphMorphismEditorMouseAdapter(this);
		this.mouseMotionAdapter = new GraphMorphismEditorMouseMotionAdapter(this);
		
		this.leftPanel = new GraphPanel(this);
		this.leftPanel.setName("Source Graph");
		final JPanel lPanel = new JPanel(new BorderLayout());
		lPanel.setPreferredSize(new Dimension(250, 150));
		lPanel.add(this.leftPanel, BorderLayout.CENTER);

		this.rightPanel = new GraphPanel(this);
		this.rightPanel.setName("Target Graph");
		final JPanel rPanel = new JPanel(new BorderLayout());
		rPanel.setPreferredSize(new Dimension(500, 150));
		rPanel.add(this.rightPanel, BorderLayout.CENTER);

		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lPanel,
				rPanel);
		this.splitPane.setDividerSize(10);
		this.splitPane.setContinuousLayout(true);
		this.splitPane.setOneTouchExpandable(true);
		this.dividerLocation = 250;
		this.splitPane.setDividerLocation(this.dividerLocation);

		final JPanel morphPanel = new JPanel(new BorderLayout());
		morphPanel.setPreferredSize(new Dimension(500, 150));
		this.title = new JLabel("    ");

		this.exportJPEGButton = createExportJPEGButton();
		final JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.add(this.title, BorderLayout.CENTER);
		if (this.exportJPEGButton != null)
			titlePanel.add(this.exportJPEGButton, BorderLayout.EAST);
		morphPanel.add(titlePanel, BorderLayout.NORTH);

		morphPanel.add(this.splitPane, BorderLayout.CENTER);

		add(morphPanel, BorderLayout.CENTER);
		
		this.leftPanel.getCanvas().addMouseListener(this.mouseAdapter);
		this.rightPanel.getCanvas().addMouseListener(this.mouseAdapter);

		this.leftPanel.getCanvas().addMouseMotionListener(this.mouseMotionAdapter);
		this.rightPanel.getCanvas().addMouseMotionListener(this.mouseMotionAdapter);
		
		String tooltipLeft = "Connect: click object left and object right.";
		String tooltipRight = "Disconnect: click object left and background right.";
		
		this.leftPanel.getCanvas().setToolTipText(tooltipLeft);
		this.rightPanel.getCanvas().setToolTipText(tooltipRight);
	}
	
//	public JFrame getApplFrame() {
//		return this.applFrame;
//	}
		
	private JButton createExportJPEGButton() {
		java.net.URL url = ClassLoader.getSystemClassLoader()
								.getResource("agg/lib/icons/print.gif");
		if (url != null) {
		ImageIcon image = new ImageIcon(url);
		// System.out.println(image);
			JButton b = new JButton(image);
			b.setToolTipText("Export JPEG");
			b.setMargin(new Insets(-5, 0, -5, 0));
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (GraphMorphismEditor.this.exportJPEG != null)
						GraphMorphismEditor.this.exportJPEG.save(GraphMorphismEditor.this.mainPanel);
				}
			});
			b.setEnabled(false);
			return b;
		} 
		return null;
	}

	public void setMorphism(final OrdinaryMorphism morph) {
		this.morphism = morph;
	}
	
	public void setIsoMorphismLeft(final OrdinaryMorphism isoLeft) {
		this.isoLeft = isoLeft;
	}
	
	public void setIsoMorphismRight(final OrdinaryMorphism isoRight) {
		this.isoRight = isoRight;
	}
	
	public void setObjectFlow(final ObjectFlow objFlow) {
		this.objFlow = objFlow;
	}
	
	public ObjectFlow getObjectFlow() {
		return this.objFlow;
	}
	
	
	public boolean addMapping(final GraphObject leftobj, final GraphObject rightobj) {		
		if (this.morphism.getInverseImage(rightobj).hasMoreElements()) {
			if (this.morphism.getInverseImage(rightobj).nextElement() != leftobj)
				removeMapping(this.morphism.getInverseImage(rightobj).nextElement());	
			else
				return true;
		}
		boolean mapOK = false;
		if (leftobj != null && rightobj != null) {
			int currentlyAllowedMapping = ((ContextView) this.morphism.getAttrContext()).getAllowedMapping();
//			System.out.println(leftobj+"    "+rightobj+"   "+leftobj.getType().isParentOf(rightobj.getType()));
			try {
				if (leftobj.getType().isParentOf(rightobj.getType())
						&& !rightobj.isAttrMemConstantValDifferent(leftobj)) {
					this.morphism.addMapping(leftobj, rightobj);
					mapOK = true;
				}
				else if (leftobj.getType().isChildOf(rightobj.getType())
						&& !rightobj.isAttrMemConstantValDifferent(leftobj)){
					this.morphism.addChild2ParentMapping(leftobj, rightobj);
					mapOK = true;
				}				
			} catch (BadMappingException ex) {
				System.out.println("add mapping of object flow:  FAILED!  "+ex.getMessage());
				mapOK = false;
			}
			
			if (mapOK) {
				if (this.isoLeft != null && this.isoRight != null) {
					this.objFlow.addMapping(
							this.isoLeft.getInverseImage(leftobj).nextElement(), 
							this.isoRight.getInverseImage(rightobj).nextElement());
				} else {
					this.objFlow.addMapping(leftobj, rightobj);
				}
				
				if (((ContextView) this.morphism.getAttrContext()).getAllowedMapping()
						!= currentlyAllowedMapping)
					((ContextView) this.morphism.getAttrContext()).changeAllowedMapping(currentlyAllowedMapping);	
			}
		} 
		
		return mapOK;
	}
	
	private void removeNodeMapping(GraphObject obj) {
		// remove mappings of arcs if needed
		Iterator<Arc> arcs = ((Node)obj).getOutgoingArcs();
		while (arcs.hasNext()) {
			this.removeArcMapping(arcs.next());
		}
		arcs = ((Node)obj).getIncomingArcs();
		while (arcs.hasNext()) {
			this.removeArcMapping(arcs.next());
		}
		// remove node mapping
		this.morphism.removeMapping(obj);			
		if (this.isoLeft != null && this.isoRight != null) {
			this.objFlow.removeMapping(
						this.isoLeft.getInverseImage(obj).nextElement());
		} else {
			this.objFlow.removeMapping(obj);	
		}
	}
	
	private void removeArcMapping(GraphObject obj) {
		this.morphism.removeMapping(obj);	
		if (this.isoLeft != null && this.isoRight != null) {
			this.objFlow.removeMapping(
					this.isoLeft.getInverseImage(obj).nextElement());
		} else {
			this.objFlow.removeMapping(obj);	
		}
	}
	
	public boolean removeMapping(GraphObject obj) {
		if (obj != null) {
			if (obj.isNode())
				removeNodeMapping(obj);
			else
				removeArcMapping(obj);;
			return true;			
		} 
		return false;
	}

	public void removeAllMappings() {		
		this.morphism.removeAllMappings();
		this.objFlow.getMapping().clear();	
	}
	
	/** Get minimum dimension */
	public Dimension getMinimumSize() {
		return new Dimension(100, 100);
	}

	/** Get preferred dimension */
	public Dimension getPreferredSize() {
		return new Dimension(500, 200);
	}

	public String getTitle() {
		return this.title.getText();
	}

	/** Set title */
	public void setTitle(String str) {
		this.title.setText("  " + str);
	}

	public void setLeftTitle(String str) {
		this.leftPanel.setName("  " + str);
		this.leftPanel.getCanvas().setName("  " + str);
	}
	
	public void setRightTitle(String str) {
		this.rightPanel.setName("  " + str);
	}
	
	
	/** Return left panel */
	public GraphPanel getLeftPanel() {
		return this.leftPanel;
	}

	/** Return right panel */
	public GraphPanel getRightPanel() {
		return this.rightPanel;
	}

	
	/** Return active panel */
	public GraphPanel getActivePanel() {
		return this.activePanel;
	}

	/** Returns my mode */
	public int getEditMode() {
		return this.leftPanel.getEditMode();
	}


	/** Returns left graph */
	public EdGraph getLeftGraph() {
		return this.leftGraph;
	}

	/** Return right graph */
	public EdGraph getRightGraph() {
		return this.rightGraph;
	}


	public void setLeftGraph(EdGraph g) {
		this.leftGraph = g;
		
		if (this.leftGraph == null) {
			setLeftTitle("    ");
			this.leftPanel.setGraph(null);
//			this.rightPanel.setGraph(null);

//			if (this.exportJPEGButton != null)
//				this.exportJPEGButton.setEnabled(false);
			
			return;
		}
				
		this.leftPanel.setGraph(g, true);

//		if (this.exportJPEGButton != null
//				&& this.exportJPEG != null) {
//			this.exportJPEGButton.setEnabled(true);
//		}
	}
	
	public void setRightGraph(EdGraph g) {
		this.rightGraph = g;
		
		if (this.rightGraph == null) {
			setRightTitle("    ");
//			this.leftPanel.setGraph(null);
			this.rightPanel.setGraph(null);

//			if (this.exportJPEGButton != null)
//				this.exportJPEGButton.setEnabled(false);
			
			return;
		}
				
		this.rightPanel.setGraph(g, true);

//		if (this.exportJPEGButton != null
//				&& this.exportJPEG != null) {
//			this.exportJPEGButton.setEnabled(true);
//		}
	}
	
	public int getDividerLocation() {
		return this.dividerLocation;
	}

	public void setDividerLocation(int i) {
		this.splitPane.setDividerLocation(i);
	}


	/**
	 * Updates layout of the LHS and RHS along the morphism.
	 */
	public void updateGraphs() {
		this.getLeftGraph().clearMarks();
		this.getRightGraph().clearMarks();

		EdNode enL = null;
		EdNode enR = null;
		EdArc eaL = null;
		EdArc eaR = null;

		if (this.morphism == null)
			return;
		
		Enumeration<GraphObject> domain = this.morphism.getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = this.morphism.getImage(bOrig);

			enL = this.getLeftGraph().findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enR = this.getRightGraph().findNode(bImage);
				if (enR != null)
					enR.addMorphismMark(enL.getMorphismMark());
				else
					enL.clearMorphismMark();
			}

			eaL = this.getLeftGraph().findArc(bOrig);
			if (eaL != null) {
				if (eaL.isMorphismMarkEmpty())
					eaL.addMorphismMark(eaL.getMyKey());

				eaR = this.getRightGraph().findArc(bImage);
				if (eaR != null)
					eaR.addMorphismMark(eaL.getMorphismMark());
				else
					eaL.clearMorphismMark();
			}
		}
	}
	
	/** Update graphics of my left and right panels */
	public void updateGraphics() {
		synchronized(this) {
			this.leftPanel.updateGraphics();
			this.rightPanel.updateGraphics();
		}
	}

	/** Update graphics of my left and right graph panels */
	public void updateGraphics(boolean graphDimensionCheck) {
		synchronized(this) {
			this.leftPanel.updateGraphics(graphDimensionCheck);
			this.rightPanel.updateGraphics(graphDimensionCheck);
		}
	}


	/** Clear graph panels. */
	public void clear() {
		setLeftGraph(null);
		setRightGraph(null);
		updateGraphics();
	}


	/** Set current mode. */
	public void setEditMode(int mode) {
		switch (mode) {
		case EditorConstants.MAP:
			mapModeProc();
			break;
		case EditorConstants.UNMAP:
			unmapModeProc();
			break;
		case EditorConstants.VIEW:
			viewModeProc();
			break;
		default:
			break;
		}
	}

	/** Return current mode */
	public int getMode() {
		return this.leftPanel.getEditMode();
	}


	/** Set cursor specified by the Cursor cur */
	public void setEditCursor(Cursor cur) {
		this.leftPanel.setEditCursor(cur);
		this.rightPanel.setEditCursor(cur);
	}
	

	private void mapModeProc() {		
		setPanelEditMode(EditorConstants.MAP);		
		setEditCursor(new Cursor(Cursor.HAND_CURSOR));
		if (this.applFrame != null)
			this.applFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		msg = "Click on a source object and a target object to get a mapping.";
	}

	private void unmapModeProc() {
		setPanelEditMode(EditorConstants.UNMAP);
		setEditCursor(new Cursor(Cursor.HAND_CURSOR));
		if (this.applFrame != null)
			this.applFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		this.msg = "Click on the source of the mapping to destroy it.";
	}
	
	private void viewModeProc() {
		this.leftPanel.setEditMode(EditorConstants.VIEW);
		this.rightPanel.setEditMode(EditorConstants.VIEW);
	}

	public void setExportJPEG(GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
	}
	
	public GraphPanel setActivePanel(Object src) {
		if (src instanceof GraphCanvas) 
			this.activePanel = ((GraphCanvas) src).getViewport();
		else
			this.activePanel = null;
		
		return this.activePanel;
	}

	private void setPanelEditMode(int mode) {
		this.leftPanel.setEditMode(mode);
		this.rightPanel.setEditMode(mode);
	}

	
	private final JFrame applFrame;
	final JPanel mainPanel;
	private final JLabel title;
	
	private final JSplitPane splitPane;
	private int dividerLocation;

	private final GraphPanel leftPanel;
	private final GraphPanel rightPanel;
	private GraphPanel activePanel;

//	private String msg = "";
	
	private EdGraph leftGraph, rightGraph;
		
	private OrdinaryMorphism morphism;
	private OrdinaryMorphism isoLeft, isoRight;
	private ObjectFlow objFlow;
	

	GraphicsExportJPEG exportJPEG;

	private final JButton exportJPEGButton;



}
