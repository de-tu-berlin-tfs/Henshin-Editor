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
 * FlowElementFigure.java
 *
 * Created 18.12.2011 - 16:11:40
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import de.tub.tfs.henshin.editor.figure.BoxModel;
import de.tub.tfs.muvitor.gef.editparts.policies.IGhostFigureProvider;

/**
 * @author nam
 * 
 */
public abstract class FlowElementFigure extends RoundedRectangle implements
		IGhostFigureProvider {

	private static final int DEFAULT_H_PADDING = 10;

	private static final int DEFAULT_V_PADDING = 10;

	private static final int DEFAULT_H_SPACING = 5;

	private boolean compactMode = false;

	private BoxModel boxModel;

	/**
	 * An tool tip {@link Label} for this figure.
	 */
	private Label toolTip;

	public FlowElementFigure() {
		toolTip = new Label();

		boxModel = new BoxModel();

		boxModel.sethPadding(DEFAULT_H_PADDING);
		boxModel.setvPadding(DEFAULT_V_PADDING);
		boxModel.sethSpacing(DEFAULT_H_SPACING);

		setToolTip(toolTip);
		setAntialias(SWT.ON);
		setLayoutManager(new XYLayout());

		setUpBoxModel(boxModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#add(org.eclipse.draw2d.IFigure,
	 * java.lang.Object, int)
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		if (figure instanceof FlowElementFigure) {
			FlowElementFigure child = (FlowElementFigure) figure;

			child.setCompactMode(isCompactMode() || child.isCompactMode());
		}

		super.add(figure, constraint, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
	 */
	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		layout();

		return getSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.policies.IGhostFigureProvider#getGhostFigure
	 * ()
	 */
	@Override
	public IFigure getGhostFigure() {
		RoundedRectangle rect = new RoundedRectangle();

		rect.setSize(this.getSize());
		rect.setAlpha(100);
		rect.setBackgroundColor(ColorConstants.lightBlue);
		rect.setLineStyle(SWT.LINE_DOT);
		rect.setLineWidth(1);
		rect.setCornerDimensions(new Dimension(20, 20));

		return rect;
	}

	/**
	 * @param toolTip
	 *            the toolTip to set
	 */
	public void setToolTip(String toolTip) {
		this.toolTip.setText(toolTip);
	}

	/**
	 * @return
	 */
	public Rectangle getTextBounds() {
		return Rectangle.SINGLETON.setSize(0, 0);
	}

	/**
	 * @return the compactMode
	 */
	public boolean isCompactMode() {
		return compactMode;
	}

	/**
	 * @param compact
	 */
	public void setCompactMode(boolean compact) {
		this.compactMode = compact;

		if (compact) {
			for (Object child : getChildren()) {
				if (child instanceof FlowElementFigure) {
					((FlowElementFigure) child).setCompactMode(compact);
				}
			}
		}

		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#layout()
	 */
	@Override
	protected void layout() {
		int vPadding = boxModel.getvPadding();
		int hPadding = boxModel.gethPadding();
		int hSpacing = boxModel.gethSpacing();

		int y = getLocation().y + vPadding;
		int x = getLocation().x + hPadding;

		int width = 0;

		int maxHeight = 0;
		for (Object o : getChildren()) {
			IFigure child = (IFigure) o;
			Dimension childSize = child.getPreferredSize(-1, -1);

			child.setBounds(new Rectangle(x + width, y, childSize.width,
					childSize.height));

			width += childSize.width + hSpacing;
			maxHeight = Math.max(childSize.height, maxHeight);
		}

		int height = maxHeight + 2 * vPadding;

		setSize(width + 2 * hPadding - hSpacing, height);

		// vertical centering of children
		for (Object o : getChildren()) {
			Figure child = (Figure) o;

			int dy = getBounds().getCenter().y
					- child.getBounds().getCenter().y;

			child.setLocation(child.getLocation().getTranslated(0, dy));
		}

		super.layout();
	}

	/**
	 * @return
	 */
	protected void setUpBoxModel(BoxModel boxModel) {
	}
}