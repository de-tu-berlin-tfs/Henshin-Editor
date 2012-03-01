/**
 * ConditionalActivityFigure.java
 *
 * Created 18.12.2011 - 16:37:01
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tub.tfs.henshin.editor.figure.BoxModel;

/**
 * @author nam
 * 
 */
public class ConditionalActivityFigure extends ActivityFigure {
	/**
     * 
     */
	public ConditionalActivityFigure() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Shape#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		graphics.setLineWidth(2);

		Rectangle r = getBounds();

		// Define the points of a diamond
		Point p1 = new Point(r.x, r.y + r.height / 2);
		Point p2 = new Point(r.x + r.width / 2, r.y);
		Point p3 = new Point(r.x + r.width, r.y + r.height / 2);
		Point p4 = new Point(r.x + r.width / 2, r.y + r.height - 1);

		PointList pointList = new PointList();

		pointList.addPoint(p1);
		pointList.addPoint(p2);
		pointList.addPoint(p3);
		pointList.addPoint(p4);

		// Fill the shape
		graphics.fillPolygon(pointList);

		// Draw the outline
		graphics.drawLine(p1, p2);
		graphics.drawLine(p2, p3);
		graphics.drawLine(p3, p4);
		graphics.drawLine(p4, p1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityFigure#setUpBoxModel
	 * (de.tub.tfs.henshin.editor.figure.BoxModel)
	 */
	@Override
	protected void setUpBoxModel(BoxModel boxModel) {
		boxModel.sethPadding(25);
		boxModel.setvPadding(25);
	}
}
