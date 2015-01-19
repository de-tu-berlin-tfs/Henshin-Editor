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
package de.tub.tfs.henshin.editor.figure.graph;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class TraceFreeformLayer.
 */
public class TraceFreeformLayer extends FreeformLayer {

	/** The name label. */
	private Label nameLabel = new Label();

	/**
	 * Instantiates a new trace freeform layer.
	 * 
	 * @param text
	 *            the text
	 */
	public TraceFreeformLayer(String text) {
		super();
		setLayoutManager(new XYLayout());
		nameLabel.setText(text);
		nameLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.BOLD));
		nameLabel.setForegroundColor(Display.getCurrent().getSystemColor(
				SWT.COLOR_GRAY));
		add(nameLabel, new Rectangle(0, 0, -1, -1));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.FreeformLayer#add(org.eclipse.draw2d.IFigure,
	 * java.lang.Object, int)
	 */
	@Override
	public void add(IFigure child, Object constraint, int index) {
		if (constraint == null) {
			constraint = new Rectangle(5, 30 + index * 25, -1, -1);
		}
		super.add(child, constraint, index);
	}

}
