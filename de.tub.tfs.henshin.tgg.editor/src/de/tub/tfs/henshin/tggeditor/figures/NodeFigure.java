package de.tub.tfs.henshin.tggeditor.figures;


import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Device;



import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.RuleObjectTextWithMarker;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TextWithMarker;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;


public class NodeFigure extends Figure {

	private static final MarginBorder BORDER2 = new MarginBorder(1, 1, 1, 1);

	/** The anchor for incoming Edges (target anchor)*/
	protected ChopboxAnchor incomingConnectionAnchor;

	/** The anchor for outgoing Edges (source anchor)*/
	protected ChopboxAnchor outgoingConnectionAnchor;
	
	/** The standard background color of node figure (no selection) */
	protected Color standardNodeBG_Color;
	
	/** The current background color of node figure */
	protected Color currentNodeBG_Color;

	private TNode node;
	
	/** The figure which holds whole content of node figure */
	protected Figure content;
	
	
	/**
	 * The outer rectangle figure
	 */
	protected RectangleFigure r;
	
	/** The figure which holds all labels of attributes */
	protected Figure attributes;
	
	/** The label which holds name and type of node */
	protected TextWithMarker labelWithMarker;
	
	

	/** The border of the node rectangle figure */
	protected LineBorder border;
	
	public NodeFigure(TNode node) {
		super();
		setLayoutManager(new FlowLayout());

		content = new Figure();
		add(content);
		content.setLayoutManager(new ToolbarLayout());
		createMarker();
		
		content.add(labelWithMarker);
		// Underline
		r = new RectangleFigure();
		r.setSize(labelWithMarker.getBounds().width, 2);
		LineBorder b = new LineBorder();
		b.setColor(ColorConstants.gray);
		r.setBorder(b);
		content.add(r);

		
		this.node = node;

		content.setBorder(BORDER2);
		border = new LineBorder();
		border.setColor(TGGEditorConstants.BORDER_DEFAULT_COLOR); 
		setBorder(border);
		setOpaque(true);
		
		attributes = new Figure();
		GridLayout layout = new GridLayout();
		layout.marginHeight=0;
		layout.marginWidth=0;
		layout.verticalSpacing=0;
		attributes.setLayoutManager(layout);

		content.add(attributes);

		

		
		updateMarker();
		
		//NodeUtil.correctNodeFigurePosition(this);
		
		updateBG();
	}

	public void updateBG() {
		switch(node.getComponent()){
		case SOURCE: standardNodeBG_Color = TGGEditorConstants.SOURCE_COLOR;break;
		case CORRESPONDENCE: standardNodeBG_Color = TGGEditorConstants.CORR_COLOR;break;
		case TARGET: standardNodeBG_Color = TGGEditorConstants.TARGET_COLOR;break;
		default:
			break;
		}
		currentNodeBG_Color = standardNodeBG_Color;
		this.setBackgroundColor(currentNodeBG_Color);
	}

	protected void createMarker() {
		labelWithMarker=new RuleObjectTextWithMarker(TGGEditorConstants.FG_BLACK_COLOR);
	}
	public void updateMarker() {
		// add marker according to marker type
		labelWithMarker.setMarker(node.getMarkerType());
		// shrink rectangle to best fit
		labelWithMarker.setSize(labelWithMarker.getPreferredSize());
		r.setSize(labelWithMarker.getBounds().width, 2);
		
		if (node.getMarkerType() == null){ // no marker is available
			border.setColor(TGGEditorConstants.BORDER_DEFAULT_COLOR);
		}
		else {
			// marker is available

			// instance graph after executing a translation
			if (node.getMarkerType().equals(RuleUtil.Translated_Graph)) {
				border.setColor(TGGEditorConstants.BORDER_TRANSLATED_COLOR);
			} else
			if (node.getMarkerType().equals(RuleUtil.Not_Translated_Graph)) {
				border.setColor(TGGEditorConstants.BORDER_NOT_TRANSLATED_COLOR);
			}
		}
		
	}

	@Override
	public void repaint() {
		//updateBG();
		super.repaint();
	}
	
	@Override
	public void validate() {
		super.validate();
		
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		if (name.indexOf("[") != -1 && name.indexOf("]") != -1)
			labelWithMarker.setText( name);
		else
			labelWithMarker.setText( getNodeName());
	}
	

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
	}

	/**
	 * the paint method
	 */
	public void paint(Graphics graphics) {
		
		super.paint(graphics);
	}

	/**
	 * Gets the outgoing connection anchor
	 * 
	 * @return the outgoing connection anchor
	 */
	public ChopboxAnchor getSourceConnectionAnchor() {
		return outgoingConnectionAnchor;
	}

	/**
	 * Gets the incoming connection anchor
	 * 
	 * @return the incoming connection anchor
	 */
	public ChopboxAnchor getTargetConnectionAnchor() {
		return incomingConnectionAnchor;
	}
	
	/**
	 * Gets the bounds of nameLabel
	 * @return bounds of nameLabel
	 */
	public Rectangle getValueLabelTextBounds() {
		return labelWithMarker.getBounds();
	}

	/**
	 * Gets the name of node.
	 *
	 * @return the name of node
	 */
	private String getNodeName() {
		String name = new String();
		if (node != null) {
			if (node.getName() != null) {
				name = new String(node.getName());
			}
			name += ":";
			if (node.getType() != null) {
				name += node.getType().getName();
			}
		}
		return name;
	}
	
	/**
	 * Gets the attribute pane.
	 *
	 * @return attribute figure which holds all attribute labels
	 */
	public Figure getAttributePane() {
		return attributes;
	}
	
	/**
	 * Gets the node
	 * @return the node which belongs to node figure
	 */
	public TNode getNode() {
		return node;
	}
	
	/**
	 * Sets if figure is selected, primary selected or not selected. Automatically sets the right
	 * color for selection mode.
	 * @param selectionMode (as constant of EditPart)
	 */
	public void setSelectionMode(int selectionMode) {
		switch(selectionMode) {
		case EditPart.SELECTED: currentNodeBG_Color = TGGEditorConstants.SECLECTED_COLOR;break;
		case EditPart.SELECTED_NONE:currentNodeBG_Color = standardNodeBG_Color;break;
		case EditPart.SELECTED_PRIMARY:currentNodeBG_Color = TGGEditorConstants.SELECTED_PRIMARY_COLOR;break;
		}
		this.setBackgroundColor(currentNodeBG_Color);
	}

	public void updatePos() {
		//NodeUtil.correctNodeFigurePosition(this);
		this.invalidate();
	}

}
