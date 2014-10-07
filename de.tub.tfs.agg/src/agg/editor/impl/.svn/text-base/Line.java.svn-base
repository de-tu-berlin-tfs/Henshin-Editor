// $Id: Line.java,v 1.6 2010/09/23 08:15:51 olga Exp $

package agg.editor.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import agg.gui.editor.EditorConstants;

/** A Line specifies a line representation of arc. */
public class Line {

	// final static BasicStroke stroke = new BasicStroke(1.0f);
	public final static int MOVE_ANCHOR_SIZE = 20; //width == height   // 20 // 10
	public final static int MOVE_ANCHOR_SIZE_W = 30;
	public final static int MOVE_ANCHOR_SIZE_W_2 = 15;
	public final static int MOVE_ANCHOR_OFFSET = 10; // 10  // 4
//	public final static Color MOVE_ANCHOR_COLOR = new Color(185, 180, 180);
    public final static Color MOVE_ANCHOR_COLOR = new Color(0, 200, 0);
//    public final static Color MOVE_ANCHOR_COLOR = new Color(130, 255, 150);
    
	public int x1, y1; // begin of line

	public int x2, y2; // end of line

	public int xh, yh; // center of line

	private Point anchor;

	private Color col = new Color(0, 0, 0);


	/** Creates a line between (x1,y1) and (x2,y2) */
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		this.xh = x1 - ((x1 - x2) / 2);
		this.yh = y2 + ((y1 - y2) / 2);
		this.anchor = new Point(this.xh, this.yh);
	}

	/** Sets an anchor of the line to the Point newAnchor */
	public void setAnchor(Point newAnchor) {
		this.anchor = newAnchor;
	}

	/** Sets a color of the line to the Color newColor */
	public void setColor(Color newColor) {
		this.col = newColor;
	}

	/** Gets the anchor of the line */
	public Point getAnchor() {
		return this.anchor;
	}

	/** Draws a black solid line */
	public void drawSolidLine(Graphics g) {
		drawLine(g);
	}

	/** Draws a colored solid line */
	public void drawColorSolidLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		// g.setStroke(stroke);
		Point anch = myAnchor();
		g.setPaint(this.col);
		new DrawLine(g, DrawLine.SOLID, this.x1, this.y1, anch.x, anch.y);
		new DrawLine(g, DrawLine.SOLID, anch.x, anch.y, this.x2, this.y2);
	}

	/** Draws a colored dotted line */
	public void drawColorDotLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		g.setPaint(this.col);
		new DrawLine(g, DrawLine.DOTTED, this.x1, this.y1, anch.x, anch.y);
		new DrawLine(g, DrawLine.DOTTED, anch.x, anch.y, this.x2, this.y2);
	}

	/** Draws a colored dashed line */
	public void drawColorDashLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		g.setPaint(this.col);
		new DrawLine(g, DrawLine.DASHED, this.x1, this.y1, anch.x, anch.y);
		new DrawLine(g, DrawLine.DASHED, anch.x, anch.y, this.x2, this.y2);
	}

	/**
	 * Draws a colored dotted line : dot and space are specified by the
	 * arguments int dots, int space
	 */
	public void drawColorDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		if (this.x1 == this.x2)
			drawColorVDotLine(g, dots, space);
		else if (this.y1 == this.y2)
			drawColorHDotLine(g, dots, space);
		else {
			g.setPaint(this.col);
			new DrawLine(g, DrawLine.DOTTED, this.x1, this.y1, anch.x, anch.y);
			new DrawLine(g, DrawLine.DOTTED, anch.x, anch.y, this.x2, this.y2);
		}
	}

	/**
	 * Draws a colored dashed line : dash and space are specified by the
	 * arguments int dots, int space
	 */
	public void drawColorDashLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		if (this.x1 == this.x2)
			drawColorVDotLine(g, dots, space);
		else if (this.y1 == this.y2)
			drawColorHDotLine(g, dots, space);
		else {
			g.setPaint(this.col);
			new DrawLine(g, DrawLine.DASHED, this.x1, this.y1, anch.x, anch.y);
			new DrawLine(g, DrawLine.DASHED, anch.x, anch.y, this.x2, this.y2);
		}
	}

	/**
	 * Draws a black dotted line : dot and space are specified by the arguments
	 * int dots, int space
	 */
	public void drawDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		if (this.x1 == this.x2)
			drawVDotLine(g, dots, space);
		else if (this.y1 == this.y2)
			drawHDotLine(g, dots, space);
		else {
			// System.out.println(">>> dx, dy: "+(this.x2-this.x1)+" "+(this.y2-this.y1));
			int anf = this.x1;
			int end = this.x2;
			if (this.x2 < this.x1) {
				anf = this.x2;
				end = this.x1;
			}
			int a = (this.y1 - this.y2) / (this.x1 - this.x2);
			int b = this.y1 - this.x1 * (this.y1 - this.y2) / (this.x1 - this.x2);
			g.setPaint(Color.black);
			for (int i = anf; i < anch.x;) {
				for (int j = 0; j < dots; j++) {
					int y = a * (i + j) + b;
					g.drawLine(i + j, y, i + j, y);
				}
				i = i + (dots + space);
			}
			for (int i = anch.x; i < end;) {
				for (int j = 0; j < dots; j++) {
					int y = a * (i + j) + b;
					g.drawLine(i + j, y, i + j, y);
				}
				i = i + (dots + space);
			}
		}
	}

	/** Draws a colored solid line */
	protected void drawWeakselectedLine(Graphics g) {
		g.setColor(EditorConstants.weakselectColor);
		// -1
		new DrawLine(g, DrawLine.SOLID, this.x1-1, this.y1-1, this.anchor.x-1, this.anchor.y-1);		
		new DrawLine(g, DrawLine.SOLID, this.anchor.x-1, this.anchor.y-1, this.x2-1, this.y2-1);
		// -2
		new DrawLine(g, DrawLine.SOLID, this.x1-2, this.y1-2, this.anchor.x-2, this.anchor.y-2);	
		new DrawLine(g, DrawLine.SOLID, this.anchor.x-2, this.anchor.y-2, this.x2-2, this.y2-2);
	}
	
	/**
	 * Draws a move anchor of this line.
	 */
	public void drawMoveAnchor(Graphics grs) {
		if (this.anchor == null)
			return;
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Line.MOVE_ANCHOR_COLOR);
		g.fill(new Rectangle(this.anchor.x - MOVE_ANCHOR_OFFSET, 
				this.anchor.y - MOVE_ANCHOR_OFFSET, 
				MOVE_ANCHOR_SIZE,
				MOVE_ANCHOR_SIZE));
		g.setPaint(lastColor);
	}

	private Point myAnchor() {
		if (this.anchor == null)
			return new Point(this.xh, this.yh);
		
		return this.anchor;
	}

	private void drawLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		g.setColor(Color.black);
		g.setPaint(Color.black);
		g.drawLine(this.x1, this.y1, anch.x, anch.y);
		g.drawLine(anch.x, anch.y, this.x2, this.y2);
	}

	private void drawHDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		int anf = this.x1;
		int end = this.x2;
		if (this.x2 < this.x1) {
			anf = this.x2;
			end = this.x1;
		}
		g.setColor(Color.black);
		g.setPaint(Color.black);
		for (int i = anf; i < anch.x;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(i + j, this.y1, i + j, this.y2);
			i = i + dots + space;
		}
		for (int i = anch.x; i < end;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(i + j, this.y1, i + j, this.y2);
			i = i + (dots + space);
		}
	}

	private void drawVDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		int anf = this.y1;
		int end = this.y2;
		if (this.y2 < this.y1) {
			anf = this.y2;
			end = this.y1;
		}
		g.setColor(Color.black);
		g.setPaint(Color.black);
		for (int i = anf; i < anch.y;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(this.x1, i + j, this.x2, i + j);
			i = i + (dots + space);
		}
		for (int i = anch.y; i < end;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(this.x1, i + j, this.x2, i + j);
			i = i + (dots + space);
		}
	}

	private void drawColorHDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		int anf = this.x1;
		int end = this.x2;
		if (this.x2 < this.x1) {
			anf = this.x2;
			end = this.x1;
		}
		g.setColor(this.col);
		g.setPaint(this.col);
		for (int i = anf; i < anch.x;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(i + j, this.y1, i + j, this.y1);
			i = i + (dots + space);
		}
		for (int i = anch.x; i < end;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(i + j, this.y1, i + j, this.y1);
			i = i + (dots + space);
		}
	}

	private void drawColorVDotLine(Graphics grs, int dots, int space) {
		Graphics2D g = (Graphics2D) grs;
		Point anch = myAnchor();
		int anf = this.y1;
		int end = this.y2;
		if (this.y2 < this.y1) {
			anf = this.y2;
			end = this.y1;
		}
		g.setColor(this.col);
		g.setPaint(this.col);
		for (int i = anf; i < anch.y;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(this.x1, i + j, this.x1, i + j);
			i = i + (dots + space);
		}
		for (int i = anch.y; i < end;) {
			for (int j = 0; j < dots; j++)
				g.drawLine(this.x1, i + j, this.x1, i + j);
			i = i + (dots + space);
		}
	}

	
	public static boolean inside(int X, int Y, Point p1, Point p2, int size) {
		Rectangle r = null;
		int xh_2 = (p2.x - p1.x)/2;
		int yh_2 = (p2.y - p1.y)/2;
		int xt_2 = p1.x + xh_2;
		int yt_2 = p1.y + yh_2;
		r = new Rectangle(xt_2 - size/2, yt_2 - size/2, size, size);
		if (r.contains(X, Y)) 
			return true;
		if (xh_2 > size && yh_2 > size/2) {
			if (inside(X, Y, p1, new Point(xt_2, yt_2), size)) {
				return true;
			}
			if (inside(X, Y, new Point(xt_2, yt_2), p2, size)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean inside(int X, int Y, int x0, int y0, int w, int h) {
		if (w > 0 && h > 0) {
			Rectangle r = new Rectangle(x0, y0, w, h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w < 0 && h < 0) {
			Rectangle r = new Rectangle(x0+w, y0+h, -w, -h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w > 0 && h < 0) {
			Rectangle r = new Rectangle(x0, y0+h, w, -h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w < 0 && h > 0) {
			Rectangle r = new Rectangle(x0+w, y0, -w, h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w == 0 && h > 0) {
			Rectangle r = new Rectangle(x0-5, y0, 10, h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w == 0 && h < 0) {
			Rectangle r = new Rectangle(x0-5, y0+h, 10, -h);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w > 0 && h == 0) {
			Rectangle r = new Rectangle(x0, y0-5, w, 10);
			if (r.contains(X, Y)) 
				return true;
		}
		if (w < 0 && h == 0) {
			Rectangle r = new Rectangle(x0+w, y0-5, -w, 10);
			if (r.contains(X, Y)) 
				return true;
		}
		return false;
	}
	
	public static boolean insideLFunct(int X, int Y, double x1, double y1, double x2, double y2) {
		if ((int)(x2-x1) != 0) {			
			double resY = ((y2-y1)/(x2-x1))*((double)X-x1) + y1;
			if (((int)resY == Y) 
				|| (Math.abs((int)resY-Y) <= 5))
				return true;
		}
//		if ((int)(y2-y1) == 0) {	
//			if ((x2 > x1) && (X > x1 && X < x2)) 
//				return true;		
//			if ((x1 > x2) && (X > x2 && X < x1)) 
//				return true;
//		}
//		if ((int)(x2-x1) == 0) {
			if ((y2 > y1) && (Y > y1 && Y < y2)) 
				return true;		
			if ((y1 > y2) && (Y > y2 && Y < y1)) 
				return true;
//		}
		return false;
	}
}
