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
 * ParameterFigureConnectionAnchor.java
 *
 * Created 26.12.2011 - 16:20:56
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

/**
 * @author nam
 * 
 */
public class ParameterFigureConnectionAnchor extends AbstractConnectionAnchor {

	/**
	 * @param owner
	 */
	public ParameterFigureConnectionAnchor(IFigure owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry
	 * .Point)
	 */
	@Override
	public Point getLocation(Point reference) {
		return getOwner().getBounds().getRight();
	}

}
