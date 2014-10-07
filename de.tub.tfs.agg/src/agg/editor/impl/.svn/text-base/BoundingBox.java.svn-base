// $Id: BoundingBox.java,v 1.8 2010/08/23 07:32:23 olga Exp $

package agg.editor.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import agg.gui.editor.GraphCanvas;

/**
 * A BoundingBox specifies a bounding rectangle around the objects of the class
 * agg.editor.impl.EdGraphObject
 */
@SuppressWarnings("serial")
public class BoundingBox extends Rectangle {

	private Vector<EdGraphObject> objs;

	private int space = 50;

	private Point min, max; // min, max points of a bounding box

	private Point upper_left = new Point(0, 0);

//	private Point upper_right = new Point(0, 0);

	private Rectangle anchorUL, // UPPER_LEFT
			anchorUR, // UPPER_RIGHT
			anchorBR, // BOTTOM_RIGHT
			anchorBL; // BOTTOM_LEFT

	private Point anchor; // current anchor

	private int dx, dy; // only >= 0

	private int maxX; //, maxY, minX, minY; // min, max size of the graphical area

	/**
	 * Creates a bounding box with graphobjects within it. The elements of the
	 * objs are of class agg.editor.impl.EdGraphObject.
	 */
	public BoundingBox(Vector<EdGraphObject> objs) {
		// System.out.println(">>> BoundingBox");
		super();
		this.objs = objs;
		/* set a space area */
		// space = 10;
		/* initialize min and this.max points of the bounding box */
		this.min = new Point(10000, 10000);
		this.max = new Point(0, 0);
		init();
	}

	public BoundingBox(Vector<EdGraphObject> objs, Point upper_left) {
		// System.out.println(">>> BoundingBox");
		super();
		this.objs = objs;
		/* set a space area */
		// space = 10;
		/* initialize this.min and this.max points of the bounding box */
		this.min = new Point(10000, 10000);
		this.max = new Point(0, 0);
		this.upper_left = upper_left;
		init();
	}

	private void init() {
		/*
		 * set this.min and this.max points of a bounding box using the size of
		 * graphobjects and a space area
		 */
		for (int i = 0; i < this.objs.size(); i++) {
			EdGraphObject elem = this.objs.elementAt(i);
			if (elem.isNode() || (elem.isArc() && !((EdArc) elem).isLine())) {
				if ((elem.getX() - elem.getWidth() / 2) < this.min.x)
					this.min.x = elem.getX() - elem.getWidth() / 2;
				if ((elem.getY() - elem.getHeight() / 2) < this.min.y)
					this.min.y = elem.getY() - elem.getHeight() / 2;
				if ((elem.getX() + elem.getWidth() / 2) > this.max.x)
					this.max.x = elem.getX() + elem.getWidth() / 2;
				if ((elem.getY() + elem.getHeight() / 2) > this.max.y)
					this.max.y = elem.getY() + elem.getHeight() / 2;
			}
		}
		/* add a space area to this.max points of a bounding box */
		this.max.x = this.max.x + this.space;
		this.max.y = this.max.y + this.space / 2;

		/* set bounds of a bounding box */
		setBounds(this.min.x, this.min.y, this.max.x - this.min.x, this.max.y - this.min.y);

//		minX = 0; // 10;
//		minY = 0;
		this.maxX = GraphCanvas.MAX_XWIDTH;
//		maxY = GraphCanvas.MAX_YHEIGHT;

		/* set an anchor on each corner of a bounding box */
		this.anchorUL = new Rectangle(this.min.x, this.min.y, 2, 2);
		this.anchorUR = new Rectangle(this.max.x, this.min.y, 2, 2);
		this.anchorBR = new Rectangle(this.max.x, this.max.y, 2, 2);
		this.anchorBL = new Rectangle(this.min.x, this.max.y, 2, 2);

		/* initialize a current anchor */
		this.anchor = new Point(-1, -1);

		/* initialize the overlapping of X and Y */
		this.dx = 0;
		this.dy = 0;
		// System.out.println("BB: "+this.min.x+" , "+this.min.y +" "+this.max.x+" , "+this.max.y);
	}

	/** Checks whether the point(x,y) is contained within the bounding box */
	public boolean contains(int ax, int ay) {
		if (super.contains(ax, ay)) {
			this.anchor.x = ax;
			this.anchor.y = ay;
			return true;
		} 
			
		this.anchor.x = -1;
		this.anchor.y = -1;
		return false;
	}

	/** Computes new positions of the graphobjects within the bounding box */
	public void compute() {
		// System.out.println(">>> BoundingBox.compute");
		// System.out.println(this.maxX);
		int newX = 0, newY = 0;
		/* set relative position of graph objects into a bounding box */
		for (int i = 0; i < this.objs.size(); i++) {
			EdGraphObject elem = this.objs.elementAt(i);
			// System.out.println(">>> old Position: x,y "+elem.getX()+" ,
			// "+elem.getY()+" w,h "+elem.getWidth()+" , "+elem.getHeight());
			// System.out.println(">>> old abs Position: "+elem.getX()+"
			// "+elem.getY());
			elem.setXY(elem.getX() - this.min.x, elem.getY() - this.min.y);
			// System.out.println(">>> rel Position: "+elem.getX()+"
			// "+elem.getY());
			if (elem.isArc() && elem.getArc().isLine()) {
				if (elem.getArc().hasAnchor())
					elem.getArc().setAnchor(
							new Point(elem.getArc().getAnchor().x - this.min.x, elem
									.getArc().getAnchor().y
									- this.min.y));
			} else if (elem.isArc() && !elem.getArc().isLine()) { // Loop
				elem.getArc().setAnchor(
						Loop.UPPER_LEFT,
						new Point(elem.getArc().getAnchor(Loop.UPPER_LEFT).x
								- this.min.x, elem.getArc().getAnchor(
								Loop.UPPER_LEFT).y
								- this.min.y));
			}
		}
		/* compute newX, newY */
		// correct this.min / this.max points to upper_left point
		if (this.upper_left.x != 0 && this.upper_left.y != 0) {
			newX = this.upper_left.x;
			newY = this.upper_left.y;
		} else {
			newX = this.min.x + this.dx;
			newY = this.min.y + this.dy;
		}

		// System.out.println("BoundBox: "+newX+" "+this.maxX);
		if ((newX + getSize().width) > this.maxX) {
			newX = 0;
			newY = newY + getSize().height + 10;
		}
		// else newY = this.min.y; //this.max.y;
		// System.out.println("newX newY "+ newX +" "+ newY);
		/* set this.max (x,y) and this.min (x,y) of a bounding box */
		this.min.x = newX;
		this.min.y = newY;
		this.max.x = this.min.x + getSize().width;
		this.max.y = this.min.y + getSize().height;
		setBounds(this.min.x, this.min.y, getSize().width, getSize().height);
		// System.out.println("BB: "+this.min.x+" , "+this.min.y +" "+this.max.x+" , "+this.max.y);

		/* set absolute Position of graph objects */
		// System.out.println(">>> set absolute Position: ");
		for (int i = 0; i < this.objs.size(); i++) {
			EdGraphObject elem = this.objs.elementAt(i);
			if (elem.isNode()) {
				elem.setXY(elem.getX() + this.min.x, elem.getY() + this.min.y);
				if ((elem.getX() - elem.getWidth() / 2) < 0)
					elem.setX(elem.getX() + elem.getWidth() / 2 + 10);
				// System.out.println(">>> new abs Position: x,y "+elem.getX()+"
				// , "+elem.getY()+" w,h "+elem.getWidth()+" ,
				// "+elem.getHeight());
			} else if (elem.isArc() && elem.getArc().isLine()) {
				if (elem.getArc().hasAnchor())
					elem.getArc().setAnchor(
							new Point(elem.getArc().getAnchor().x + this.min.x, elem
									.getArc().getAnchor().y
									+ this.min.y));
			} else if (elem.isArc() && !elem.getArc().isLine()) { // Loop
				elem.getArc().setAnchor(
						Loop.UPPER_LEFT,
						new Point(elem.getArc().getAnchor(Loop.UPPER_LEFT).x
								+ this.min.x, elem.getArc().getAnchor(
								Loop.UPPER_LEFT).y
								+ this.min.y));
			}
		}
		// System.out.println("BoundingBox.compute:: upper_right : "+new
		// Point(this.max.x, this.min.y));
		return;
	}

	/** Gets the current this.anchor of the bounding box */
	public Point getAnchor() {
		return this.anchor;
	}

	/** Sets the current this.anchor of the bounding box */
	public void setAnchor(int x, int y) {
		this.anchor.x = x;
		this.anchor.y = y;
	}

	/**
	 * Returns TRUE if the current this.anchor is on the upper left corner of the
	 * bounding box
	 */
	public boolean isUpperLeft() {
		return this.anchorUL.contains(this.anchor.x, this.anchor.y);
	}

	/**
	 * Returns TRUE if the current this.anchor is on the upper right corner of the
	 * bounding box
	 */
	public boolean isUpperRight() {
		return this.anchorUR.contains(this.anchor.x, this.anchor.y);
	}

	/**
	 * Returns TRUE if the current this.anchor is on the bottom right corner of the
	 * bounding box
	 */
	public boolean isBottomRight() {
		return this.anchorBR.contains(this.anchor.x, this.anchor.y);
	}

	/**
	 * Returns TRUE if the current this.anchor is on the bottom left corner of the
	 * bounding box
	 */
	public boolean isBottomLeft() {
		return this.anchorBL.contains(this.anchor.x, this.anchor.y);
	}

	public Point getUpperLeft() {
		return new Point(this.min.x, this.min.y);
	}

	public Point getUpperRight() {
		return new Point(this.max.x, this.min.y);
	}

	public void setUpperLeft(Point p) {
		this.min = p;
	}

	/**
	 * Moves the bounding box dragging the current this.anchor to the specified x,y
	 */
	public void moveTo(int ax, int ay) {
		if (this.anchor.x == -1 || this.anchor.y == -1)
			return;

		int diffX = ax - this.anchor.x;
		int diffY = ay - this.anchor.y;
		this.min.x = this.min.x + diffX;
		this.min.y = this.min.y + diffY;
		this.max.x = this.max.x + diffX;
		this.max.y = this.max.y + diffY;
		makeAnchors();
	}

	/**
	 * Resizes the one selected corner of the bounding box to the specified x,y
	 */
	public void resizeTo(int ax, int ay) {
		if (this.anchor.x == -1 || this.anchor.y == -1)
			return;

		if (isUpperLeft()) {
			this.min.x = ax;
			this.min.y = ay;
			setBounds(this.min.x, this.min.y, this.max.x - this.min.x, this.max.y - this.min.y);
			makeAnchors();
		} else if (isUpperRight()) {
			this.max.x = ax;
			this.min.y = ay;
			setBounds(this.min.x, this.min.y, this.max.x - this.min.x, this.max.y - this.min.y);
			makeAnchors();
		} else if (isBottomLeft()) {
			this.min.x = ax;
			this.max.y = ay;
			setBounds(this.min.x, this.min.y, this.max.x - this.min.x, this.max.y - this.min.y);
			makeAnchors();
		} else if (isBottomRight()) {
			this.max.x = ax;
			this.max.y = ay;
			setBounds(this.min.x, this.min.y, this.max.x - this.min.x, this.max.y - this.min.y);
			makeAnchors();
		}
	}

	/** Sets the this.max x of the bounding box */
	public void setMaxX(int aMax) {
		this.maxX = aMax;
	}

	/* Sets the this.max y of the bounding box */
//	public void setMaxY(int this.max) {
//		maxY = this.max;
//	}

	/* Sets the this.min x of the bounding box */
//	public void setMinX(int this.min) {
//		minX = this.min;
//	}

	/* Sets the this.min y of the bounding box */
//	public void setMinY(int this.min) {
//		minY = this.min;
//	}

	/** Sets the this.max overlapping of the graphobjects within the bounding box */
	public void setOverlap(int overX, int overY) {
		this.dx = overX;
		this.dy = overY;
	}

	public void setOverlap(Rectangle rect) {

	}

	/** Shows the bounding box */
	public void show(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(this.min.x, this.min.y, getSize().width, getSize().height);
	}

	/** Hides the bounding box */
	public void hide(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(this.min.x, this.min.y, getSize().width, getSize().height);
	}

	/** Resets the corner anchors of the bounding box */
	private void makeAnchors() {
		this.anchorUL = new Rectangle(this.min.x, this.min.y, 2, 2);
		this.anchorUR = new Rectangle(this.max.x, this.min.y, 2, 2);
		this.anchorBR = new Rectangle(this.max.x, this.max.y, 2, 2);
		this.anchorBL = new Rectangle(this.min.x, this.max.y, 2, 2);
	}
}
