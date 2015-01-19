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
 * TransitionConnectionRouter.java
 *
 * Created 23.12.2011 - 15:19:45
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author nam
 * 
 */
public class TransitionConnectionRouter extends AbstractRouter {

	/**
     * 
     */
	private ConnectionRouter nextRouter;

	public TransitionConnectionRouter(IFigure figure) {
		ShortestPathConnectionRouter router = new ShortestPathConnectionRouter(
				figure);

		setNextRouter(router);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.ConnectionRouter#route(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void route(Connection connection) {
		nextRouter.route(connection);

		IFigure startFig = connection.getSourceAnchor().getOwner();
		IFigure endFig = connection.getTargetAnchor().getOwner();

		PointList points = connection.getPoints();
		Point p2 = points.getLastPoint();
		Point p1 = points.getFirstPoint();

		if (startFig == endFig) {
			Rectangle r = startFig.getBounds();
			Point bottom = new Point(r.getCenter().x, r.bottom());
			Point right = r.getRight();
			Point p3 = right.getTranslated(0, 50);
			Point p4 = bottom.getTranslated(0, 30);

			points.removeAllPoints();

			points.addPoint(right);
			points.addPoint(p3);
			points.addPoint(p4);
			points.addPoint(bottom);
		}

		int dx = Math.abs(p2.x - p1.x);
		int dy = Math.abs(p2.y - p1.y);

		if (dx > 0 && dy > 0) {

			// points.insertPoint(
			// p1.getTranslated(Math.max(dx, dy) == dx ? 0 : p2.x - p1.x,
			// Math.max(dx, dy) == dy ? 0 : p2.y - p1.y), 1);
			// if (connection.getSourceAnchor().getOwner() !=
			// connection.getTargetAnchor().getOwner()) {
			// nextRouter.route(connection);
			// } else {
			// PointList points = connection.getPoints();
			// points.removeAllPoints();
			// Rectangle rectangle =
			// connection.getSourceAnchor().getOwner().getBounds();
			// int x = rectangle.x + rectangle.width;
			// int y = rectangle.y;
			// points.addPoint(x, y);
			// points.addPoint(x, y - 20);
			// points.addPoint(x + 30, y - 20);
			// points.addPoint(x + 30, y + 10);
			// points.addPoint((new ChopboxAnchor(
			// connection.getSourceAnchor().getOwner())).getLocation(new Point(
			// x + 30, y + 10)));
			// connection.setPoints(points);
		}
		// }
	}

	/**
	 * Sets the next router.
	 * 
	 * @param nextRouter
	 *            the new next router
	 */
	public synchronized void setNextRouter(ConnectionRouter nextRouter) {
		this.nextRouter = nextRouter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.AbstractRouter#getConstraint(org.eclipse.draw2d.Connection
	 * )
	 */
	@Override
	public Object getConstraint(Connection connection) {
		return nextRouter.getConstraint(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.AbstractRouter#invalidate(org.eclipse.draw2d.Connection
	 * )
	 */
	@Override
	public void invalidate(Connection connection) {
		nextRouter.invalidate(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.AbstractRouter#remove(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void remove(Connection connection) {
		nextRouter.remove(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.AbstractRouter#setConstraint(org.eclipse.draw2d.Connection
	 * , java.lang.Object)
	 */
	@Override
	public void setConstraint(Connection connection, Object constraint) {
		nextRouter.setConstraint(connection, constraint);
	}
}
