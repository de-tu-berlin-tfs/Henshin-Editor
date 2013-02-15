/**
 * NoteFigure.java
 * created on 21.07.2012 23:11:16
 */
package de.tub.tfs.henshin.editor.figure.graph;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author huuloi
 *
 */
public class ToolTipFigure extends Label {

	public ToolTipFigure(String note) {
		super(note);
		setBorder(new ToolTipBorder());
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		graphics.setBackgroundColor(ColorConstants.white);
		Rectangle bound = getBounds();
		graphics.fillRectangle(bound.x, bound.y, bound.width, bound.height);
		super.paintFigure(graphics);
	}
}
