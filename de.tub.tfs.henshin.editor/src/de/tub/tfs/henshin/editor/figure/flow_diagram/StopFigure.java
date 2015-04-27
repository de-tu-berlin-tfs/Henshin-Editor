/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.SWT;

/**
 * @author nam
 * 
 */
public class StopFigure extends Ellipse {

	/**
	 * 
	 */
	public StopFigure() {
		setSize(30, 30);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		graphics.setAntialias(SWT.ON);

		super.paint(graphics);

		graphics.setBackgroundColor(ColorConstants.black);
		graphics.fillOval(getLocation().x + 5, getLocation().y + 5, 20, 20);
	}
}
