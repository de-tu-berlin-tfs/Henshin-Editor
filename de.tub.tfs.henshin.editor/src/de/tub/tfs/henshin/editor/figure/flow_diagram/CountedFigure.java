/**
 * CountedFigure.java
 *
 * Created 18.12.2011 - 23:24:12
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class CountedFigure extends FlowElementFigure {

	/**
     * 
     */
	private Label limitLabel;

	/**
     * 
     */
	public CountedFigure() {
		super();

		setAntialias(SWT.ON);
		setLineWidth(2);

		setFont(SWTResourceManager.getFont("Sans", 12, SWT.BOLD));

		limitLabel = new Label();

		limitLabel.setForegroundColor(ColorConstants.gray);

		add(limitLabel);

		setLimit("0");
	}

	/**
	 * @param limit
	 */
	public void setLimit(String limit) {
		limitLabel.setText(limit);

		setToolTip("Counted Element with counter '" + limit + "'.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.FlowElementFigure#getTextBounds
	 * ()
	 */
	@Override
	public Rectangle getTextBounds() {
		return limitLabel.getTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.setAntialias(SWT.ON);
		graphics.setLineWidth(1);
		graphics.setForegroundColor(ColorConstants.gray);

		super.paint(graphics);

		graphics.setForegroundColor(ColorConstants.black);
		graphics.setLineWidth(2);

		Rectangle r = Rectangle.SINGLETON;

		r.setBounds(getBounds());

		// Define the points of a diamond
		Point p1 = new Point(r.x, r.y + r.height / 2);
		Point p2 = new Point(r.x + r.width / 2, r.y);
		Point p3 = new Point(r.x + r.width, r.y + r.height / 2);
		Point p4 = new Point(r.x + r.width / 2, r.y + r.height - 1);

		// Draw the outline
		graphics.drawLine(p1, p2);
		graphics.drawLine(p2, p3);
		graphics.drawLine(p3, p4);
		graphics.drawLine(p4, p1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#layout()
	 */
	@Override
	protected void layout() {
		limitLabel.setBounds(new Rectangle(new Point(getLocation().x + 15,
				getLocation().y + 10), limitLabel.getTextBounds().getSize()));

		setSize(limitLabel.getSize().expand(30, 20));
	}
}
