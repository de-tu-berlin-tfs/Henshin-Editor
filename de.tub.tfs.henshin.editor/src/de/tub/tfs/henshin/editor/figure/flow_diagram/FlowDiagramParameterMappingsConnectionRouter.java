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
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author nam
 * 
 */
public class FlowDiagramParameterMappingsConnectionRouter extends
		AbstractRouter {

	private static int DEFAULT_SPACING = 10;

	private final Figure owner;

	private HashMap<Connection, Integer> levels;

	/**
	 * @param owner
	 */
	public FlowDiagramParameterMappingsConnectionRouter(Figure owner) {
		super();

		this.owner = owner;
		this.levels = new HashMap<Connection, Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.ConnectionRouter#route(org.eclipse.draw2d.Connection)
	 */
	@Override
	public void route(Connection conn) {
		Iterator<Entry<Connection, Integer>> it = levels.entrySet().iterator();

		while (it.hasNext()) {
			Connection connection = (Connection) it.next().getKey();

			if (connection.getParent() == null) {
				it.remove();
			}
		}

		Point start = getStartPoint(conn);
		Point end = getEndPoint(conn);

		int xMax = Math.max(start.x, end.x) + DEFAULT_SPACING;

		if (!levels.containsKey(conn)) {
			int lvl = 0;

			for (Integer c : levels.values()) {
				while (lvl <= c.intValue()) {
					lvl++;
				}
			}

			levels.put(conn, Integer.valueOf(lvl));
		}

		for (Object f : FlowDiagramParameterMappingsConnectionRouter.this.owner
				.getChildren()) {
			while (collide(start.getTranslated(xMax - start.x, 0),
					end.getTranslated(xMax - end.x, 0),
					((Figure) f).getBounds())) {
				xMax += DEFAULT_SPACING;
			}
		}

		xMax += levels.get(conn).intValue() * DEFAULT_SPACING;

		PointList points = conn.getPoints();
		Point p;

		points.removeAllPoints();
		conn.translateToRelative(start);
		points.addPoint(start);
		conn.translateToRelative(p = new Point(xMax, start.y));
		points.addPoint(p);

		IFigure targetFigure = conn.getTargetAnchor().getOwner();

		if (targetFigure != null) {
			conn.translateToRelative(p = new Point(xMax, end.y));
			points.addPoint(p);
		}

		conn.translateToRelative(end);
		points.addPoint(end);

		conn.setPoints(points);
	}

	/**
	 * @param p0
	 * @param p1
	 * @param rect
	 * @return
	 */
	private boolean collide(Point p0, Point p1, Rectangle rect) {
		int cy = rect.getCenter().y;
		int rx = rect.getRight().x;

		int x0 = p0.x;
		int y0 = p0.y;

		int y1 = p1.y;

		return cy >= Math.min(y0, y1) && cy <= Math.max(y0, y1) && x0 <= rx;
	}
}
