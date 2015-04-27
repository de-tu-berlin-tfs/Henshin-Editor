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
