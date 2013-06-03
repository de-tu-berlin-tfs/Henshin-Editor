// $Id: CenterOfPoints.java,v 1.6 2010/08/23 07:32:23 olga Exp $

package agg.editor.impl;

import java.awt.Point;
import java.util.Vector;

/**
 * A CenterOfPoints specifies a center of the polygon defined as a vector of
 * points
 */
public class CenterOfPoints {

	/**
	 * Creates a new CenterOfPoints whose center is (0,0) and whose points are
	 * specified by the Vector argument.
	 */
	public CenterOfPoints(Vector<Point> v) {
		this.vec = v;
	}

	public void setPoints(Vector<Point> v) {
		this.vec = v;
	}

	public Point getCenter() {
		// System.out.println(">>> CenterOfPoints.getCenter");
		Point c = getCenterOfPoints(this.vec);
		return c;
	}

	private Point getCenterOfPoints(Vector<Point> v) {
		int sumx = 0;
		int sumy = 0;
		for (int i = 0; i < v.size(); i++) {
			sumx = sumx + v.elementAt(i).x;
			sumy = sumy + v.elementAt(i).y;
		}
		if (v.size() != 0)
			return new Point(sumx / v.size(), sumy / v.size());
		
		return new Point(0, 0);
	}

	private Vector<Point> vec;
}
