/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * @author nam
 * 
 */
public class StartFigure extends FlowElementFigure {

	/**
     * 
     */
	public StartFigure() {
		super();

		setToolTip("Start Activity.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.FlowElementFigure#layout()
	 */
	@Override
	protected void layout() {
		super.layout();

		setSize(20, 20);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.setAntialias(SWT.ON);
		graphics.setBackgroundColor(ColorConstants.black);
		graphics.fillOval(Rectangle.SINGLETON.setBounds(getBounds()).resize(-1,
				-1));
	}
}
