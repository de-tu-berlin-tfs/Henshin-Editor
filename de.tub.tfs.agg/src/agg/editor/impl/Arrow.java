// $Id: Arrow.java,v 1.8 2010/08/19 09:07:25 olga Exp $

package agg.editor.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * The Arrow specifies an arrow head on the target side of the arc.
 */
public class Arrow extends Line {

	private double angle;

	private Point top;

	private Point leftEnd;

	private Point rightEnd;

	private Polygon head;

	private int headLength = 12;

	private double headAngle = 0.45; // 0.5

	private double itsScale = 1.0;
	
	private int tarH;

	private int tarW;

	/** Creates an arrow head */
	public Arrow( Point begin, Point end, int targetWidth,
			int targetHeight) {
		super(begin.x, begin.y, end.x, end.y);
		this.tarW = targetWidth;
		this.tarH = targetHeight;
		this.head = getHeadPolygon();
	}
	
	/** Creates an arrow head */
	public Arrow(int x1, int y1, int x2, int y2,
			int targetHeight, int targetWidth) {
		this(new Point(x1, y1), new Point(x2, y2), targetHeight,
				targetWidth);
	}
	
	/** Creates an arrow head */
	public Arrow(int x1, int y1, int x2, int y2) {
		this(new Point(x1, y1), new Point(x2, y2), 0, 0);
	}
	
	public Arrow(double scale, Point begin, Point end, int targetWidth,
			int targetHeight, int headSize) {
		super(begin.x, begin.y, end.x, end.y);
	
		this.tarW = targetWidth;
		this.tarH = targetHeight;
		
		if (headSize > 0)
			this.headLength = headSize;
			
		this.headLength = (int) ((this.headLength / this.itsScale) * scale);	
		
		this.head = getHeadPolygon();
		
		this.itsScale = scale;
	}

	public Arrow(double scale, int x1, int y1, int x2, int y2,
			int targetHeight, int targetWidth, int headSize) {		
		this(scale, new Point(x1, y1), new Point(x2, y2), targetHeight,
				targetWidth, headSize);
	}

	public Arrow(double scale, int x1, int y1, int x2, int y2) {
		this(scale, new Point(x1, y1), new Point(x2, y2), 0, 0, 0);
	}

	
	/** Draws the arrow head */
	public void draw(Graphics g) {
		if (g == null || this.head == null)
			return;
		g.fillPolygon(this.head);
		// g.drawPolygon(this.head);
	}

	/** Draws the arrow head */
	public void draw(Graphics g, boolean filled) {
		if (g == null || this.head == null)
			return;
		if (filled)
			g.fillPolygon(this.head);
		else {
			Color col = g.getColor();
			g.setColor(Color.WHITE);
			g.fillPolygon(this.head);
			g.setColor(col);
			g.drawPolygon(this.head);
		}
	}

	/** Returns the head end point of an arrow */
	public Point getHeadEnd() {
		return this.top;
	}

	/** Returns the left end point of an arrow */
	public Point getLeftEnd() {
		return this.leftEnd;
	}

	/** Returns the right end point of an arrow */
	public Point getRightEnd() {
		return this.rightEnd;
	}

	private double getAngle() {
		double dX = Math.abs(this.x2 - this.x1);
		double dY = Math.abs(this.y2 - this.y1);
		double winkel = Math.atan(dY / dX);
		// System.out.println("angle : "+winkel);
		return winkel;
	}

	private Point getTopOfHead(int factorX, int factorY) {
		this.angle = getAngle();
		int halfW = this.tarW / 2;
		int halfH = this.tarH / 2;
		int d = (int) (Math.tan(this.angle) * halfW);
		int topX = this.x2 + halfW * factorX;
		int topY = this.y2 + d * factorY;

		int ext = 3;
		Rectangle tarRect = new Rectangle((this.x2 - halfW - ext),
				(this.y2 - halfH - ext), this.tarW + ext * 2, this.tarH + ext * 2);
		if (!tarRect.contains(new Point(topX, topY))) {
			d = (int) (halfH / Math.tan(this.angle));
			topX = this.x2 + d * factorX;
			topY = this.y2 + halfH * factorY;
			if (!tarRect.contains(new Point(topX, topY)))
				return null;
		}
		// System.out.println("top : "+topX+" , "+topY);
		return new Point(topX, topY);
	}

	private Point getLeftEndOfHead() {
		if (this.top == null)
			return null;
		int xLE = this.top.x - (int) (Math.cos(this.angle - this.headAngle) * this.headLength);
		int yLE = this.top.y - (int) (Math.sin(this.angle - this.headAngle) * this.headLength);
		return new Point(xLE, yLE);
	}

	private Point getRightEndOfHead() {
		if (this.top == null)
			return null;
		int xRE = this.top.x - (int) (Math.cos(this.angle + this.headAngle) * this.headLength);
		int yRE = this.top.y - (int) (Math.sin(this.angle + this.headAngle) * this.headLength);
		return new Point(xRE, yRE);
	}

	private Polygon getHeadPolygon() {
		if ((this.x2 > this.x1) && (this.y2 == this.y1)) // left
		{
			// System.out.println("left");
			this.angle = .0;
			this.top = new Point(this.x2 - this.tarW / 2, this.y2);
		} else if ((this.x2 < this.x1) && (this.y2 == this.y1)) // right
		{
			// System.out.println("right");
			this.angle = Math.PI;
			this.top = new Point(this.x2 + this.tarW / 2, this.y2);
		} else if ((this.x2 == this.x1) && (this.y2 > this.y1)) // top
		{
			// System.out.println("top");
			this.angle = Math.PI / 2;
			this.top = new Point(this.x2, this.y2 - this.tarH / 2);
		} else if ((this.x2 == this.x1) && (this.y2 < this.y1)) // bottom
		{
			// System.out.println("bottom");
			this.angle = Math.PI / 2 + Math.PI;
			this.top = new Point(this.x2, this.y2 + this.tarH / 2);
		} else if ((this.x2 > this.x1) && (this.y2 > this.y1)) // top left
		{
			// System.out.println("top left");
			this.top = getTopOfHead(-1, -1);
		} else if ((this.x2 < this.x1) && (this.y2 < this.y1)) // bottom right
		{
			// System.out.println("bottom right");
			this.top = getTopOfHead(+1, +1);
			this.angle = Math.PI + this.angle;
		} else if ((this.x2 < this.x1) && (this.y2 > this.y1)) // this.top right
		{
			// System.out.println("top right");
			this.top = getTopOfHead(+1, -1);
			this.angle = Math.PI - this.angle;
		} else if ((this.x2 > this.x1) && (this.y2 < this.y1)) // bottom left
		{
			// System.out.println("bootom left");
			this.top = getTopOfHead(-1, +1);
			this.angle = 2 * Math.PI - this.angle;
		} else if ((this.x2 == this.x1) && (this.y2 == this.y1)) 
		{
			this.angle = .0;
			this.top = new Point(0, 0);
		}

		// calc left end of head
		this.leftEnd = getLeftEndOfHead();

		// calc right end of head
		this.rightEnd = getRightEndOfHead();

		// System.out.println("this.top.x : "+this.top.x);
		// System.out.println("xLE : "+this.leftEnd.x+" xRE : "+this.rightEnd.x);

		if (this.top == null || this.leftEnd == null || this.rightEnd == null)
			return null;

		// do polygon
		int xArray[] = new int[3];
		xArray[0] = this.top.x;
		xArray[1] = this.leftEnd.x;
		xArray[2] = this.rightEnd.x;

		int yArray[] = new int[3];
		yArray[0] = this.top.y;
		yArray[1] = this.leftEnd.y;
		yArray[2] = this.rightEnd.y;

		Polygon polygon = new Polygon(xArray, yArray, 3);

		return polygon;
	}

}
