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
 * ConditionalActivityFigureAnchor.java
 *
 * Created 18.12.2011 - 22:00:36
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tub.tfs.henshin.editor.util.GeometryUtil;

/**
 * @author nam
 * 
 */
public class ConditionalActivityFigureAnchor extends AbstractConnectionAnchor {

	private boolean isSource;

	/**
	 * Constructs an AbstractConnectionAnchor with an owner.
	 * 
	 * @param owner
	 *            an {@link IFigure} as owner of this anchor.
	 */
	public ConditionalActivityFigureAnchor(IFigure owner) {
		this(owner, false);
	}

	/**
     * 
     */
	public ConditionalActivityFigureAnchor(IFigure owner, boolean isSource) {
		super(owner);

		this.isSource = isSource;
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
		if (isSource) {
			return getSourceLocation(reference);
		} else {
			return getTargetLocation(reference);
		}
	}

	/**
	 * @param reference
	 * @return
	 */
	private Point getTargetLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON.setBounds(getOwner().getBounds());

		r.translate(-1, -1);
		r.resize(2, 2);

		getOwner().translateToAbsolute(r);

		Point ref = r.getCenter().negate().translate(reference);

		if (ref.x == 0) {
			return new Point(reference.x, (ref.y > 0) ? r.bottom() : r.y);
		}

		if (ref.y == 0) {
			return new Point((ref.x > 0) ? r.right() : r.x, reference.y);
		}

		Point c = r.getCenter();

		int relX = reference.x - c.x;
		int relY = reference.y - c.y;

		Point q1 = new Point(r.x, r.y + r.height / 2);
		Point q2 = new Point(r.x + r.width / 2, r.y);
		Point q3 = new Point(r.x + r.width, r.y + r.height / 2);
		Point q4 = new Point(r.x + r.width / 2, r.y + r.height - 1);

		Point targetLocation;

		if (relX >= 0 && relY >= 0) {
			targetLocation = (GeometryUtil.INSTANCE.getIntersection(c,
					reference, q3, q4, false));
		}

		else if (relX < 0 && relY >= 0) {
			targetLocation = (GeometryUtil.INSTANCE.getIntersection(c,
					reference, q1, q4, false));
		}

		else if (relX < 0 && relY < 0) {
			targetLocation = (GeometryUtil.INSTANCE.getIntersection(c,
					reference, q1, q2, false));
		} else {
			targetLocation = (GeometryUtil.INSTANCE.getIntersection(c,
					reference, q2, q3, false));
		}

		return targetLocation;
	}

	/**
	 * @param reference
	 * @return
	 */
	private Point getSourceLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON.setBounds(getOwner().getBounds());

		r.resize(1, 1);

		getOwner().translateToAbsolute(r);

		Point p1 = new Point(r.x, r.y + r.height / 2);
		Point p2 = new Point(r.x + r.width / 2, r.y);
		Point p3 = new Point(r.x + r.width, r.y + r.height / 2);
		Point p4 = new Point(r.x + r.width / 2, r.y + r.height - 1);

		double dist1 = p1.getDistance(reference);
		double dist2 = p2.getDistance(reference);
		double dist3 = p3.getDistance(reference);
		double dist4 = p4.getDistance(reference);

		double nearest = Math.min(dist1,
				Math.min(dist3, Math.min(dist4, dist2)));

		if (nearest == dist1) {
			return p1;
		}

		if (nearest == dist2) {
			return p2;
		}

		if (nearest == dist3) {
			return p3;
		}

		return p4;
	}

}
