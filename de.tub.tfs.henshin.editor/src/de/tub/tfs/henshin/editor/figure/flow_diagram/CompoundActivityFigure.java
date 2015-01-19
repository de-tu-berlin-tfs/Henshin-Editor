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
 * CompoundActivityFigure.java
 *
 * Created 27.12.2011 - 15:26:21
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class CompoundActivityFigure extends FlowElementFigure {

	private Label empty;

	/**
     * 
     */
	public CompoundActivityFigure() {
		super();

		setCornerDimensions(new Dimension(10, 10));
		setLineWidth(2);
		setForegroundColor(ColorConstants.gray);

		empty = new Label();

		add(empty);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#invalidate()
	 */
	@Override
	public void invalidate() {
		setToolTip("Compound Activity with " + (getChildren().size() - 1)
				+ " children.");

		if (empty != null) {
			if (getChildren().size() > 1) {
				empty.setText("");
			} else {
				empty.setText("<empty>");
			}
		}

		super.invalidate();
	}

	public void setTxtColor(Color c) {
		empty.setForegroundColor(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityFigure#add(org.
	 * eclipse.draw2d.IFigure, java.lang.Object, int)
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (figure instanceof FlowElementFigure) {
			((FlowElementFigure) figure).setCompactMode(true);
		}

		super.add(figure, constraint, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Shape#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		graphics.setBackgroundColor(SWTResourceManager.getColor(230, 230, 250));

		super.paintFigure(graphics);
	}

}
