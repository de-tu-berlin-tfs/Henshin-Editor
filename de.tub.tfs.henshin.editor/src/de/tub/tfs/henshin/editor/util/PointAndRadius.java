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
package de.tub.tfs.henshin.editor.util;

import org.eclipse.draw2d.geometry.Point;

/**
 * The Class PointAndRadius.
 */
public class PointAndRadius {

	/** The point. */
	private Point point;

	/** The radius. */
	private double radius;

	/**
	 * Instantiates a new point and radius.
	 * 
	 * @param point
	 *            the point
	 * @param radius
	 *            the radius
	 */
	public PointAndRadius(Point point, double radius) {
		super();
		this.point = point;
		this.radius = radius;
	}

	/**
	 * Gets the point.
	 * 
	 * @return the point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

}