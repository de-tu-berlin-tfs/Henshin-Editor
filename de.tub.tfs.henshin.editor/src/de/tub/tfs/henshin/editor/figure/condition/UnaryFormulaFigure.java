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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.UnaryFormula;

import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author angel
 * 
 */
public class UnaryFormulaFigure extends FormulaFigure<UnaryFormula> {

	/**
	 * @param unaryFormula
	 *            The unary formula whose figure is built.
	 */
	public UnaryFormulaFigure(final UnaryFormula unaryFormula) {
		super(unaryFormula);

		label.setBounds(new Rectangle(getLocation().x, getLocation().y, width,
				HEIGHT_DEFAULT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.condition.FormulaFigure#refreshLabel()
	 */
	@Override
	protected void refreshLabel() {
		if (getFormula().getChild() == null) {
			label.setBounds(new Rectangle(getLocation().x, getLocation().y,
					width, height));
		} else {
			label.setBounds(new Rectangle(getLocation().x, getLocation().y,
					width, HEIGHT_DEFAULT));
		}

		label.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));
		if (!FormulaUtil.isValid(getFormula())) {
			label.setForegroundColor(SWTResourceManager.getColor(255, 0, 0));
		}
	}

	private FormulaFigure<?> getChildFigure() {
		for (final Object child : getChildren()) {
			if (child instanceof FormulaFigure<?>) {
				return (FormulaFigure<?>) child;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.condition.FormulaFigure#getPreferredDimension()
	 */
	@Override
	protected Dimension getPreferredDimension() {
		Dimension dimension = super.getPreferredDimension();

		FormulaFigure<?> childFigure = getChildFigure();
		if (childFigure != null) {
			Dimension childDimension = childFigure.getPreferredDimension();
			dimension.height += childDimension.height;
			dimension.width = childDimension.width;
		}

		return dimension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.condition.FormulaFigure#setSize(int, int)
	 */
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);

		FormulaFigure<?> childFigure = getChildFigure();
		if (childFigure != null) {
			childFigure.setSize(width, height - 1);
			childFigure.setLocation(new Point(getLocation().x, getLocation().y
					+ HEIGHT_DEFAULT));
		}
	}
}
