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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.BinaryFormula;

import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author angel
 * 
 */
public class BinaryFormulaFigure extends FormulaFigure<BinaryFormula> {

	/**
	 * @param binaryFormula
	 *            The binary formula whose figure is built.
	 */
	public BinaryFormulaFigure(final BinaryFormula binaryFormula) {
		super(binaryFormula);

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
		if (getFormula().getLeft() == null && getFormula().getRight() == null) {
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

	/**
	 * @return The formula figure children: left and right figures.
	 */
	private List<FormulaFigure<?>> getChildFigures() {
		List<FormulaFigure<?>> childFigures = new ArrayList<FormulaFigure<?>>();
		for (Object child : getChildren()) {
			if (child instanceof FormulaFigure<?>) {
				childFigures.add((FormulaFigure<?>) child);
			}
		}
		return childFigures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.figure.condition.FormulaFigure#getPreferredDimension()
	 */
	@Override
	protected Dimension getPreferredDimension() {
		Dimension dimension = super.getPreferredDimension();

		List<FormulaFigure<?>> childFigures = getChildFigures();
		if (!childFigures.isEmpty()) {
			Dimension leftDimension = childFigures.get(0)
					.getPreferredDimension();
			Dimension rightDimension = new Dimension(1, 1);

			if (childFigures.size() == 2) {
				rightDimension = childFigures.get(1).getPreferredDimension();
			}

			dimension.height += Math.max(leftDimension.height,
					rightDimension.height);
			dimension.width = 2 * Math.max(leftDimension.width,
					rightDimension.width);
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

		List<FormulaFigure<?>> childFigures = getChildFigures();
		for (FormulaFigure<?> childFigure : childFigures) {
			childFigure.setSize(width / 2, height - 1);

			if (FormulaUtil.isLeftChild(getFormula(), childFigure.getFormula())) {
				childFigure.setLocation(new Point(getLocation().x,
						getLocation().y + HEIGHT_DEFAULT));
			} else if (FormulaUtil.isRightChild(getFormula(),
					childFigure.getFormula())) {
				childFigure.setLocation(new Point(getLocation().x + this.width
						/ 2, getLocation().y + HEIGHT_DEFAULT));
			}
		}
	}
}
