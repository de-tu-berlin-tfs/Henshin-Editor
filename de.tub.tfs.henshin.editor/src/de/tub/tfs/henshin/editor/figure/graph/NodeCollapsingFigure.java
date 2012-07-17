/**
 * NodeCollapsingFigure.java
 * created on 08.07.2012 16:55:23
 */
package de.tub.tfs.henshin.editor.figure.graph;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.swt.SWT;

import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * @author huuloi
 *
 */
public class NodeCollapsingFigure extends NodeFigure {

	private Label toolTip;
	
	private Node node;

	public NodeCollapsingFigure(Node node) {
		super();
		this.node = node;
		toolTip = new Label(node.getName());
	 	setToolTip(toolTip);
	 	this.width = Constants.SIZE_20;
	 	this.height = Constants.SIZE_20;
	 	repaint();
	 	setLayoutManager(new XYLayout());
	}
	
	@Override
	protected void layout() {
		super.layout();
//		setSize(Constants.SIZE_20, Constants.SIZE_20);
	}
	
	@Override
	public void paint(Graphics graphics) {
		graphics.setAntialias(SWT.ON);
		graphics.setBackgroundColor(ColorConstants.black);
		if (node != null) {
			NodeLayout layout = HenshinLayoutUtil.INSTANCE.getLayout(node);
			graphics.fillRectangle(layout.getX(), layout.getY(), Constants.SIZE_20, Constants.SIZE_20);
		}
		super.paint(graphics);
	}

	@Override
	public boolean isHide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHide(boolean hide) {
		// TODO Auto-generated method stub
	}

	@Override
	public Rectangle getValueLabelTextBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
}
