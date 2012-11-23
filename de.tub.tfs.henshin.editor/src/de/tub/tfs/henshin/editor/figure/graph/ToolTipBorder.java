/**
 * NoteBorder.java
 * created on 21.07.2012 23:36:03
 */
package de.tub.tfs.henshin.editor.figure.graph;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * @author huuloi
 *
 */
public class ToolTipBorder extends AbstractBorder {

	public static final int FOLD = 10;
	
	@Override
	public Insets getInsets(IFigure figure) {
		return new Insets(1, 2 + FOLD, 2, 2);
	}

	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		Rectangle rectangle = figure.getBounds().getCopy();
		
		rectangle.shrink(insets);
		graphics.setLineWidth(1);
		graphics.drawLine(rectangle.x + FOLD, rectangle.y, rectangle.x + rectangle.width - 1, rectangle.y);
		graphics.drawLine(rectangle.x, rectangle.y + FOLD, rectangle.x, rectangle.y + rectangle.height - 1);
		graphics.drawLine(rectangle.x + rectangle.width - 1, rectangle.y, rectangle.x + rectangle.width - 1, rectangle.y + rectangle.height - 1);
		graphics.drawLine(rectangle.x, rectangle.y + rectangle.height - 1, rectangle.x + rectangle.width - 1, rectangle.y + rectangle.height - 1);
		graphics.drawLine(rectangle.x + FOLD, rectangle.y, rectangle.x + FOLD, rectangle.y + FOLD);
		graphics.drawLine(rectangle.x, rectangle.y + FOLD, rectangle.x + FOLD, rectangle.y + FOLD);
		graphics.setBackgroundColor(ColorConstants.lightGray);
		graphics.fillPolygon(new int[] { rectangle.x, rectangle.y + FOLD, rectangle.x + FOLD, rectangle.y,
				rectangle.x + FOLD, rectangle.y + FOLD });
		graphics.setLineStyle(SWT.LINE_DOT);
		graphics.drawLine(rectangle.x, rectangle.y + FOLD, rectangle.x + FOLD, rectangle.y);
	}

}
