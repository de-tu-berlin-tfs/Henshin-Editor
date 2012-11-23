package tggeditor.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.internal.dnd.SwtUtil;
import org.eclipse.ui.themes.ColorUtil;

import tggeditor.util.NodeTypes;
import tggeditor.util.NodeUtil;

public class NodeFigure extends Figure {

	/** The Constant Display. */
	static final Device Display = null;

	/** The anchor for incoming Edges (target anchor)*/
	protected ChopboxAnchor incomingConnectionAnchor;

	/** The anchor for outgoing Edges (source anchor)*/
	protected ChopboxAnchor outgoingConnectionAnchor;
	
	/** The background color of node figure if it's selected */
	protected Color selectedColor = new Color(null,100,255,100);
	
	/** The background color of node figure if it's primary selected */
	protected Color selectedPrimaryColor = new Color(null,255,100,100);
	
	/** The standard background color of node figure (no selection) */
	protected Color standardColor;
	
	/** The current background color of node figure */
	protected Color currentColor;

	private Node node;
	
	/** The figure which holds whole content of node figure */
	protected Figure content;
	
	/** The figure which holds all labels of attributes */
	protected Figure attributes;
	
	/** The label which holds name and type of node */
	protected Label nameLabel;
	
	/** The label which holds <++> marker */
	protected Label marker;
	
	/** The label which holds <tr> marker */
	protected Label translatedMarker;
	
	protected boolean critical;

	public NodeFigure(Node node, boolean isMarked, boolean isTranslated) {
		super();
		content = new Figure();
		setLayoutManager(new FlowLayout());
		add(content);
		content.setLayoutManager(new ToolbarLayout());
		
		this.node = node;
		Color[] shadow = {ColorConstants.black, ColorConstants.black};
		Color[] highlight = {ColorConstants.black, ColorConstants.black};

		nameLabel = new Label(getNodeName());
		nameLabel.setLabelAlignment(Label.LEFT);
		attributes = new Figure();
		GridLayout layout = new GridLayout(1,true);
		attributes.setLayoutManager(layout);
		
		marker = new Label("<++>");
		marker.setForegroundColor(ColorConstants.darkGreen);
		marker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		marker.setVisible(true);
		
		if(isMarked) {
			content.add(marker);
		}
		
		translatedMarker = new Label("<tr>");
		translatedMarker.setForegroundColor(ColorConstants.darkGreen);
		translatedMarker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		translatedMarker.setVisible(true);
		
		if(isTranslated) {
			content.add(translatedMarker);
		}
		
		content.add(nameLabel);
		content.add(attributes);
		content.setBorder(new MarginBorder(5, 5, 5, 5));
		setBorder(new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));
		setOpaque(true);
		
		
		switch(NodeTypes.getNodeGraphType(node)){
		case SOURCE: standardColor = new Color(null,255,204,152);break;
		case CORRESPONDENCE: standardColor = new Color(null,152,204,255);break;
		case TARGET: standardColor = new Color(null,255,255,152);break;
		}
		currentColor = standardColor;
		this.setBackgroundColor(currentColor);
		
		critical = NodeUtil.getNodeLayout(node).isCritical();
		if (critical){
			standardColor = new Color(standardColor.getDevice(),ColorUtil.blend(standardColor.getRGB(), ColorConstants.red.getRGB()));
			selectedColor = new Color(selectedColor.getDevice(),ColorUtil.blend(selectedColor.getRGB(), ColorConstants.red.getRGB()));
			selectedPrimaryColor = new Color(selectedPrimaryColor.getDevice(),ColorUtil.blend(selectedPrimaryColor.getRGB(), ColorConstants.red.getRGB()));
			
		}
		
	}

	@Override
	public void repaint() {
		super.repaint();
	}
	
	@Override
	public void validate() {
		super.validate();
		NodeUtil.correctNodeFigurePosition(this);
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		if (name.indexOf("[") != -1 && name.indexOf("]") != -1)
			nameLabel.setText(name);
		else
			nameLabel.setText(getNodeName());
	}
	
	/**
	 * Set marked. 
	 * add or remove marker label from content
	 *
	 * @param isMarked
	 */
	public void setMarked(boolean isMarked){
		if(isMarked && !content.getChildren().contains(marker)) {
			content.add(marker, 0);
		}
		if(!isMarked && content.getChildren().contains(marker)) {
			content.remove(marker);
		}
	}
	
	/**
	 * Set translated.
	 * add or remove translated label from content
	 *
	 * @param isTranslated
	 */
	public void setTranslated(boolean isTranslated){
		if(isTranslated && !content.getChildren().contains(translatedMarker)) {
			content.add(translatedMarker, 0);
		}
		if(!isTranslated && content.getChildren().contains(translatedMarker)) {
			content.remove(translatedMarker);
		}
	}

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
	}

	/**
	 * the paint method
	 */
	public void paint(Graphics graphics) {
		graphics.setAlpha(255);
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
		return nameLabel.getBounds();
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
	public Node getNode() {
		return node;
	}
	
	/**
	 * Sets if figure is selected, primary selected or not selected. Automatically sets the right
	 * color for selection mode.
	 * @param selectionMode (as constant of EditPart)
	 */
	public void setSelectionMode(int selectionMode) {
		switch(selectionMode) {
		case EditPart.SELECTED: currentColor = selectedColor;break;
		case EditPart.SELECTED_NONE:currentColor = standardColor;break;
		case EditPart.SELECTED_PRIMARY:currentColor = selectedPrimaryColor;break;
		}
		this.setBackgroundColor(currentColor);
	}

}
