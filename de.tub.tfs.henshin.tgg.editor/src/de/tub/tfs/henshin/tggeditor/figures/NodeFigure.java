package de.tub.tfs.henshin.tggeditor.figures;


import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
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

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


public class NodeFigure extends Figure {

	private static final MarginBorder BORDER2 = new MarginBorder(1, 1, 1, 1);

	/** The Constant Display. */
	static final Device Display = null;

	private static final Font SANSSERIFNORMAL = new Font(Display, "SansSerif", 8, SWT.NORMAL);

	private static final Font SANSSERIF = new Font(null, "SansSerif", 8, SWT.BOLD);

	/** The anchor for incoming Edges (target anchor)*/
	protected ChopboxAnchor incomingConnectionAnchor;

	/** The anchor for outgoing Edges (source anchor)*/
	protected ChopboxAnchor outgoingConnectionAnchor;
	
	/** The background color of node figure if it's selected */
	protected static final Color selectedColor = new Color(null,100,255,100);
	
	/** The background color of node figure if it's primary selected */
	protected static final Color selectedPrimaryColor = new Color(null,255,100,100);
	
	/** The standard background color of node figure (no selection) */
	protected Color standardColor;
	
	/** The current background color of node figure */
	protected Color currentColor;

	private TNode node;
	
	/** The figure which holds whole content of node figure */
	protected Figure content;
	
	/** The figure which holds the title line of node figure */
	protected Figure title;
	
	/** The figure which holds all labels of attributes */
	protected Figure attributes;
	
	/** The label which holds name and type of node */
	protected Label nameLabel;
	
	/** The label which holds <++> marker */
	protected Label marker;
	
	/** The label which holds <tr> marker */
	protected Label translatedMarker;

	/** The border of the node rectangle figure */
	LineBorder border;
	
	protected static Color sourceColor= new Color(null,252,239,226);
	protected static Color correspondenceColor= new Color(null,226,240,252);
	protected static Color targetColor= new Color(null,255,255,235);
	
	public NodeFigure(TNode node) {
		super();
		setLayoutManager(new FlowLayout());

		content = new Figure();
		add(content);
		content.setLayoutManager(new ToolbarLayout());

		title=new Figure();
		title.setLayoutManager(new FlowLayout());
		content.add(title);
		// Underline
		RectangleFigure r = new RectangleFigure();
		r.setSize(title.getBounds().width, 2);
		LineBorder b = new LineBorder();
		b.setColor(ColorConstants.gray);
		r.setBorder(b);
		content.add(r);

		
		this.node = node;

		Color borderColor = ColorConstants.buttonDarkest;
//		Color[] shadow = {ColorConstants.black, ColorConstants.black};
//		Color[] highlight = {ColorConstants.black, ColorConstants.black};

		//org.eclipse.draw2d.
		content.setBorder(BORDER2);
		border = new LineBorder();
		border.setColor(borderColor); 
		setBorder(border);
				//new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));
		setOpaque(true);

		
		nameLabel = new Label(getNodeName());
		//nameLabel.setLabelAlignment(Label.LEFT);
		nameLabel.setLabelAlignment(Label.CENTER);
		nameLabel.setFont(SANSSERIFNORMAL);
//		nameLabel.setBorder(new MarginBorder(0, 0, 0, 0));
		title.add(nameLabel);

		
		attributes = new Figure();
		GridLayout layout = new GridLayout();
		layout.marginHeight=0;
		layout.marginWidth=0;
		layout.verticalSpacing=0;
		attributes.setLayoutManager(layout);
//		attributes.setBorder(new MarginBorder(-3, 0, -3, 0));

		content.add(attributes);

		
		marker = new Label(RuleUtil.NEW);
		marker.setForegroundColor(ColorConstants.darkGreen);
//		marker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD));
		marker.setFont(SANSSERIF);
		marker.setBackgroundColor(targetColor);
		marker.setVisible(true);

		
		
		translatedMarker = new Label(RuleUtil.Translated);
		translatedMarker.setForegroundColor(ColorConstants.blue);
//		translatedMarker.setFont(new Font(Display, "SansSerif", 12, SWT.BOLD)); 
		translatedMarker.setFont(SANSSERIF);
		translatedMarker.setBackgroundColor(targetColor);
		translatedMarker.setVisible(true);

		
		updateMarker();
		
		NodeUtil.correctNodeFigurePosition(this);
		
		switch(NodeTypes.getNodeGraphType(node)){
		case SOURCE: standardColor = sourceColor;break;
		case CORRESPONDENCE: standardColor = correspondenceColor;break;
		case TARGET: standardColor = targetColor;break;
		}
		currentColor = standardColor;
		this.setBackgroundColor(currentColor);
	}

	public void updateMarker() {
		// add marker according to marker type
		if (node.getMarkerType() != null) {
			// marker is available

			// instance graph after executing a translation
			if (node.getMarkerType().equals(RuleUtil.Translated_Graph)) {

				border.setWidth(2);
				border.setColor(ColorConstants.darkGreen);

			} else
			if (node.getMarkerType().equals(RuleUtil.Not_Translated_Graph)) {

				border.setWidth(2);
				border.setColor(ColorConstants.red);

			} else
			
			// other marker types -> rules
			if (node.getMarkerType() != null) {
				if (node.getMarkerType().equals(RuleUtil.NEW)) 
					title.add(marker, 1);
				if (node.getMarkerType().equals(RuleUtil.Translated))
					title.add(translatedMarker, 1);
			}
		} else {
			if (marker.getParent() == title)
				title.remove(marker);
			if (translatedMarker.getParent() == title)
				title.remove(translatedMarker);
		}
		
	}

	@Override
	public void repaint() {
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
			nameLabel.setText( name);
		else
			nameLabel.setText( getNodeName());
	}
	
	/**
	 * Set marked. 
	 * add or remove marker label from content
	 *
	 * @param isMarked
	 */
	public void setMarked(boolean isMarked){
		if(isMarked && !title.getChildren().contains(marker)) {
			title.add(marker, 1);
		}
		if(!isMarked && title.getChildren().contains(marker)) {
			title.remove(marker);
		}
	}
	
	/**
	 * Set translated.
	 * add or remove translated label from content
	 *
	 * @param isTranslated
	 */
	public void setTranslated(boolean isTranslated){
		if(isTranslated && !title.getChildren().contains(translatedMarker)) {
			title.add(translatedMarker, 1);
		}
		if(!isTranslated && title.getChildren().contains(translatedMarker)) {
			title.remove(translatedMarker);
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
		case EditPart.SELECTED: currentColor = selectedColor;break;
		case EditPart.SELECTED_NONE:currentColor = standardColor;break;
		case EditPart.SELECTED_PRIMARY:currentColor = selectedPrimaryColor;break;
		}
		this.setBackgroundColor(currentColor);
	}

	public void updatePos() {
		NodeUtil.correctNodeFigurePosition(this);
		this.invalidate();
	}

}
