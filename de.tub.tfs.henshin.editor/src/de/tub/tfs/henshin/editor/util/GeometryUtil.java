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
 * GeometryUtil.java
 *
 * Created 18.12.2011 - 19:29:33
 */
package de.tub.tfs.henshin.editor.util;

import org.eclipse.draw2d.geometry.Point;

/**
 * A utility class providing functions for geometric calculations.
 * 
 * @author nam
 * 
 */
public class GeometryUtil {
	/**
	 * The singleton instance.
	 */
	public static GeometryUtil INSTANCE = new GeometryUtil();

	/**
	 * An epsilon to handle float precision errors.
	 */
	private static final double EPS = 0.0000789d;

	/**
	 * Private, since singleton.
	 */
	private GeometryUtil() {
	}

	/**
	 * Calculates the intersection {@link Point point} of two lines defined by
	 * the given parameters.
	 * 
	 * <p>
	 * The two lines need to be defined as follow: (x1, y1) -> (x2, y2) and
	 * (x3,y3) -> (x4,y4).
	 * </p>
	 * 
	 * @param x1
	 *            x coordinate of starting point of line segment 1
	 * @param y1
	 *            y coordinate of starting point of line segment 1
	 * @param x2
	 *            x coordinate of ending point of line segment 1
	 * @param y2
	 *            y coordinate of ending point of line segment 1
	 * @param x3
	 *            x coordinate of the starting point of line segment 2
	 * @param y3
	 *            y coordinate of the starting point of line segment 2
	 * @param x4
	 *            x coordinate of the ending point of line segment 2
	 * @param y4
	 *            y coordinate of the ending point of line segment 2
	 * 
	 * @return the intersection {@link Point}, if exists or <code>null</code>,
	 *         otherwise.
	 */
	public Point getIntersection(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4, boolean checkInside) {

		Point intersect = new Point();

		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		double nume1 = (x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3);
		double nume2 = (x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3);

		// lines are coincident
		if (Math.abs(denom) <= EPS && Math.abs(nume1) <= EPS
				&& Math.abs(nume2) <= EPS) {
			intersect.x = (int) Math.round((x1 + x2) / 2.d);
			intersect.y = (int) Math.round((y1 + y2) / 2.d);

			return intersect;
		}

		// lines are parallel, no intersection
		if (Math.abs(denom) <= EPS) {
			return null;
		}

		double mua = nume1 / denom;
		double mub = nume2 / denom;

		if ((mua < 0 || mua > 1 || mub < 0 || mub > 1) && checkInside) {
			return null;
		}

		intersect.x = (int) Math.round(x1 + mua * (x2 - x1));
		intersect.y = (int) Math.round(y1 + mua * (y2 - y1));

		return intersect;
	}

	/**
	 * Convenient method to calculate intersection point dealing with
	 * {@link Point point}s as parameters.
	 * 
	 * @param p1
	 *            starting point of line segment 1
	 * @param p2
	 *            ending point of line segment 1
	 * @param p3
	 *            starting point of line segment 2
	 * @param p4
	 *            ending point of line segment 2
	 * 
	 * @return the intersection {@link Point}, if exists or <code>null</code>,
	 *         otherwise.
	 * 
	 * @see GeometryUtil#getIntersection(double, double, double, double, double,
	 *      double, double, double)
	 */
	public Point getIntersection(Point p1, Point p2, Point p3, Point p4,
			boolean checkInside) {
		return getIntersection(p1.preciseX(), p1.preciseY(), p2.preciseX(),
				p2.preciseY(), p3.preciseX(), p3.preciseY(), p4.preciseX(),
				p4.preciseY(), checkInside);
	}
}
