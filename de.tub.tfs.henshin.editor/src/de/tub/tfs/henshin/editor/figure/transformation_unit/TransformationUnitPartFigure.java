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
package de.tub.tfs.henshin.editor.figure.transformation_unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

/**
 * The Class ConditionalUnitPartFigure.
 */
public class TransformationUnitPartFigure extends RoundedRectangle {

	/** The Constant Display. */
	protected static final Device Display = null;

	/** The text. */
	private Label text;

	/** The width. */
	private int width;

	/** The sub unit figure. */
	private List<SubUnitFigure> subUnitFigures;

	/** The text container. */
	private Rectangle textContainer;

	/**
	 * Instantiates a new conditional unit part figure.
	 * 
	 * @param name
	 *            the name
	 * @param width
	 *            the width
	 */
	public TransformationUnitPartFigure(String name, int width) {
		super();
		this.subUnitFigures = new ArrayList<SubUnitFigure>();

		text = new Label(name);
		text.setFont(new Font(Display, "Arial", 11, SWT.BOLD | SWT.ITALIC));
		text.setTextAlignment(PositionConstants.LEFT);
		text.setForegroundColor(ColorConstants.darkGray);

		setLayoutManager(new XYLayout());
		textContainer = new Rectangle(3, 3, width - 3, 25);
		add(text, textContainer);
		setSize(width, 80);

	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		text.setText(name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#repaint()
	 */
	@Override
	public void repaint() {
		if (subUnitFigures != null) {
			int x = getLocation().x;
			int y = getLocation().y;

			for (int i = 0, n = subUnitFigures.size(); i < n; i++) {
				IFigure figure = subUnitFigures.get(i);
				figure.setSize(width - 6, 50);
				figure.setLocation(new Point(x + 3, y + 27 + 52 * i));
			}
		}
		super.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		this.width = w;
		textContainer.setSize(w, 25);
		super.setSize(w, h);
		if (!subUnitFigures.isEmpty()) {
			for (SubUnitFigure figure : subUnitFigures) {
				figure.setSize(w - 6, 50);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setLocation(org.eclipse.draw2d.geometry.Point)
	 */
	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure,
	 * java.lang.Object, int)
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (figure instanceof SubUnitFigure) {
			subUnitFigures.add(index, (SubUnitFigure) figure);
		}
		super.add(figure, constraint, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#remove(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void remove(IFigure figure) {
		if (figure instanceof SubUnitFigure) {
			subUnitFigures.remove(figure);
		}
		super.remove(figure);
	}

	public int calculateHight() {
		if (subUnitFigures.size() <= 1) {
			return 80;
		} else {
			return 30 + subUnitFigures.size() * 52;
		}
	}
}
