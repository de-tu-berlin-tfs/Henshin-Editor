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
package de.tub.tfs.henshin.editor.figure.condition;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.SchemeBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;

import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author angel
 * 
 */
public abstract class FormulaFigure<T extends Formula> extends RectangleFigure {

	protected static int WIDTH_DEFAULT = 80;

	protected static int HEIGHT_DEFAULT = 25;

	protected static int X_DEFAULT = 30;

	protected static int Y_DEFAULT = 40;

	private static Color[] shadow = { ColorConstants.black,
			ColorConstants.black };

	private static Color[] highlight = { ColorConstants.gray,
			ColorConstants.gray };

	protected Label label;

	protected int width = WIDTH_DEFAULT;

	protected int height = HEIGHT_DEFAULT;

	private T formula;

	public FormulaFigure(final T formula) {
		this.formula = formula;

		if (formula != null) {
			setBounds(new Rectangle(X_DEFAULT, Y_DEFAULT, WIDTH_DEFAULT,
					HEIGHT_DEFAULT));

			final String formulaText = FormulaUtil.getText(formula);

			label = new Label(formulaText);
			label.setBorder(new LineBorder(ColorConstants.black));
			label.setLabelAlignment(PositionConstants.CENTER);
			label.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));

			if (!FormulaUtil.isValid(formula)) {
				label.setForegroundColor(SWTResourceManager.getColor(255, 0, 0));
			}
			add(label);
		}

		setBorder(new SchemeBorder(new SchemeBorder.Scheme(highlight, shadow)));
	}

	public T getFormula() {
		return formula;
	}

	/**
	 * Refresh figure size and location.
	 */
	public void refresh() {
		Dimension preferredDimension = getPreferredDimension();
		setSize(preferredDimension);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		width = w * WIDTH_DEFAULT;
		height = h * HEIGHT_DEFAULT;

		super.setSize(width, height);

		refreshLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setBounds(org.eclipse.draw2d.geometry.Rectangle
	 * )
	 */
	@Override
	public void setBounds(Rectangle rect) {
		width = rect.width;
		height = rect.height;

		super.setBounds(rect);
	}

	/**
	 * Refreshes label size and location on current figure.
	 */
	protected abstract void refreshLabel();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		int x = Math.round(getLocation().x + getSize().width / 2.0f);
		graphics.setBackgroundPattern(new Pattern(null, x, getLocation().y, x,
				getLocation().y + getSize().height, SWTResourceManager
						.getColor(230, 230, 230), ColorConstants.white));
		super.paint(graphics);
	}

	protected Dimension getPreferredDimension() {
		return new Dimension(1, 1);
	}
}
