// $Id: Loop.java,v 1.8 2010/09/23 08:15:51 olga Exp $

package agg.editor.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import agg.gui.editor.EditorConstants;

/** A Loop specifies a loop representation of arc. */
public class Loop {

	public int x, y; // upper left corner ( not center!)

	public int w, h;

	public Point anchor; // current anchor

	public Point anch0; // CENTER

	public Point anch1; // UPPER_LEFT

	public Point anch2; // UPPER_RIGHT

	public Point anch3; // BOTTOM_RIGHT

	public Point anch4; // BOTTOM_LEFT

	public int anchorID; // current anchor ID

	public static final int CENTER = 0;

	public static final int UPPER_LEFT = 1;

	public static final int UPPER_RIGHT = 2;

	public static final int BOTTOM_RIGHT = 3;

	public static final int BOTTOM_LEFT = 4;

	public static final int DEFAULT_SIZE = 20; //14;

	private Color col = new Color(0, 0, 0);

	private int min = 14;

	/** Create a loop with upper left corner (x,y), width w and height h */
	public Loop(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.anch1 = new Point(x, y);
		this.anch2 = new Point(x + w, y);
		this.anch3 = new Point(x + w, y + h);
		this.anch4 = new Point(x, y + h);
		this.anch0 = new Point(x + w / 2, y + h / 2);
		this.anchorID = UPPER_LEFT;
		this.anchor = this.anch1;
	}

	/**
	 * Sets the loop to a loop with upper left corner (x,y), width w and height
	 * h
	 */
	public void setLoop(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.anch1 = new Point(x, y);
		this.anch2 = new Point(x + w, y);
		this.anch3 = new Point(x + w, y + h);
		this.anch4 = new Point(x, y + h);
		this.anch0 = new Point(x + w / 2, y + h / 2);
		this.anchorID = UPPER_LEFT;
		this.anchor = this.anch1;
	}

	/** Gets the current anchor */
	public Point getAnchor(int id) {
		Point myAnchor = null;
		switch (id) {
		case CENTER:
			myAnchor = this.anch0;
			break;
		case UPPER_LEFT:
			myAnchor = this.anch1;
			break;
		case UPPER_RIGHT:
			myAnchor = this.anch2;
			break;
		case BOTTOM_RIGHT:
			myAnchor = this.anch3;
			break;
		case BOTTOM_LEFT:
			myAnchor = this.anch4;
			break;
		default:
			myAnchor = this.anch1;
			break;
		}
		return myAnchor;
	}

	/** Checks whether the current anchor is on the upper left corner */
	public boolean isUPPER_LEFT() {
		if ((this.anchor.x == this.anch1.x) && (this.anchor.y == this.anch1.y))
			return true;
		
		return false;
	}

	/** Checks whether the current anchor is on the upper right corner */
	public boolean isUPPER_RIGHT() {
		if ((this.anchor.x == this.anch2.x) && (this.anchor.y == this.anch2.y))
			return true;
		
		return false;
	}

	/** Checks whether the current anchor is on the bottom right corner */
	public boolean isBOTTOM_RIGHT() {
		if ((this.anchor.x == this.anch3.x) && (this.anchor.y == this.anch3.y))
			return true;
		
		return false;
	}

	/** Checks whether the current anchor is on the bottom left corner */
	public boolean isBOTTOM_LEFT() {
		if ((this.anchor.x == this.anch4.x) && (this.anchor.y == this.anch4.y))
			return true;
		
		return false;
	}

	/** Sets a color of the loop */
	public void setColor(Color aColor) {
		this.col = aColor;
	}

	/** Draws a black solid loop */
	private void drawLoop(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(this.anch1.x, this.anch1.y, this.anch2.x, this.anch2.y);
		g.drawLine(this.anch2.x, this.anch2.y, this.anch3.x, this.anch3.y);
		g.drawLine(this.anch3.x, this.anch3.y, this.anch4.x, this.anch4.y);
		g.drawLine(this.anch4.x, this.anch4.y, this.anch1.x, this.anch1.y);
	}

	/** Draws a black solid loop */
	public void drawSolidLoop(Graphics g) {
		drawLoop(g);
		g.setColor(Color.black);
	}

	/** Draws a colored solid loop */
	public void drawColorSolidLoop(Graphics g) {
		g.setColor(this.col);
		new DrawLine(g, this.anch1.x, this.anch1.y, this.anch2.x, this.anch2.y);
		new DrawLine(g, this.anch2.x, this.anch2.y, this.anch3.x, this.anch3.y);
		new DrawLine(g, this.anch3.x, this.anch3.y, this.anch4.x, this.anch4.y);
		new DrawLine(g, this.anch4.x, this.anch4.y, this.anch1.x, this.anch1.y);
	}

	/** Draws a colored dotted loop */
	public void drawColorDotLoop(Graphics g) {
		g.setColor(this.col);
		new DrawLine(g, DrawLine.DOTTED, this.anch1.x, this.anch1.y,
				this.anch2.x, this.anch2.y);
		new DrawLine(g, DrawLine.DOTTED, this.anch2.x, this.anch2.y, this.anch3.x,
				this.anch3.y);
		new DrawLine(g, DrawLine.DOTTED, this.anch3.x, this.anch3.y, this.anch4.x,
				this.anch4.y);
		new DrawLine(g, DrawLine.DOTTED, this.anch4.x, this.anch4.y, this.anch1.x,
				this.anch1.y);
	}

	/** Draws a colored dashed loop */
	public void drawColorDashLoop(Graphics g) {
		g.setColor(this.col);
		new DrawLine(g, DrawLine.DASHED, this.anch1.x, this.anch1.y,
				this.anch2.x, this.anch2.y);
		new DrawLine(g, DrawLine.DASHED, this.anch2.x, this.anch2.y, this.anch3.x,
				this.anch3.y);
		new DrawLine(g, DrawLine.DASHED, this.anch3.x, this.anch3.y, this.anch4.x,
				this.anch4.y);
		new DrawLine(g, DrawLine.DASHED, this.anch4.x, this.anch4.y, this.anch1.x,
				this.anch1.y);
	}

	public void drawWeakselectedLoop(Graphics g) {
		g.setColor(EditorConstants.weakselectColor); //this.col);
		// -1
		new DrawLine(g, this.anch1.x-1, this.anch1.y-1, this.anch2.x+1, this.anch2.y-1);
		new DrawLine(g, this.anch2.x+1, this.anch2.y-1, this.anch3.x+1, this.anch3.y+1);
		new DrawLine(g, this.anch3.x+1, this.anch3.y+1, this.anch4.x-1, this.anch4.y+1);
		new DrawLine(g, this.anch4.x-1, this.anch4.y+1, this.anch1.x-1, this.anch1.y-1);
		// -2
		new DrawLine(g, this.anch1.x-2, this.anch1.y-2, this.anch2.x+2, this.anch2.y-2);
		new DrawLine(g, this.anch2.x+2, this.anch2.y-2, this.anch3.x+2, this.anch3.y+2);
		new DrawLine(g, this.anch3.x+2, this.anch3.y+2, this.anch4.x-2, this.anch4.y+2);
		new DrawLine(g, this.anch4.x-2, this.anch4.y+2, this.anch1.x-2, this.anch1.y-2);
		
		// +1
//		new DrawLine(g, this.anch1.x+1, this.anch1.y+1, this.anch2.x-1, this.anch2.y+1);
//		new DrawLine(g, this.anch2.x-1, this.anch2.y+1, this.anch3.x-1, this.anch3.y-1);
//		new DrawLine(g, this.anch3.x-1, this.anch3.y-1, this.anch4.x+1, this.anch4.y-1);
//		new DrawLine(g, this.anch4.x+1, this.anch4.y-1, this.anch1.x+1, this.anch1.y+1);
		// +2
//		new DrawLine(g, this.anch1.x+2, this.anch1.y+2, this.anch2.x-2, this.anch2.y+2);
//		new DrawLine(g, this.anch2.x-2, this.anch2.y+2, this.anch3.x-2, this.anch3.y-2);
//		new DrawLine(g, this.anch3.x-2, this.anch3.y-2, this.anch4.x+2, this.anch4.y-2);
//		new DrawLine(g, this.anch4.x+2, this.anch4.y-2, this.anch1.x+2, this.anch1.y+2);
	}
	
	/**
	 * Draws a move anchor of this loop.
	 * 
	 * @param anchorKey
	 *            can be: Loop.UPPER_LEFT / Loop.UPPER_RIGHT / Loop.BOTTOM_RIGHT /
	 *            Loop.BOTTOM_LEFT / Loop.CENTER. Only Loop.UPPER_LEFT is
	 *            implemented.
	 */
	public void drawMoveAnchor(Graphics grs, int anchorKey) {
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Line.MOVE_ANCHOR_COLOR);
		if (anchorKey == Loop.UPPER_LEFT)
			g.fill(new Rectangle(this.anch1.x - Line.MOVE_ANCHOR_OFFSET, 
					this.anch1.y - Line.MOVE_ANCHOR_OFFSET, 
					Line.MOVE_ANCHOR_SIZE,
					Line.MOVE_ANCHOR_SIZE));

		/*
		 * else if (anchorKey == Loop.UPPER_RIGHT) g.fill(new Rectangle(this.anch2.x -
		 * 4, this.anch2.y - 4, MOVE_ANCHOR_SIZE, MOVE_ANCHOR_SIZE)); else if
		 * (anchorKey == Loop.BOTTOM_RIGHT) g.fill(new Rectangle(this.anch3.x - 4,
		 * this.anch3.y - 4, MOVE_ANCHOR_SIZE, MOVE_ANCHOR_SIZE)); else if (anchorKey ==
		 * Loop.BOTTOM_LEFT) g.fill(new Rectangle(this.anch4.x - 4, this.anch4.y - 4,
		 * MOVE_ANCHOR_SIZE, MOVE_ANCHOR_SIZE));
		 */
		g.setPaint(lastColor);
	}

	/**
	 * Moves the loop with source and target specified by the argument Rectangle
	 * r around x, y specified by the arguments int dx, int dy
	 */
	public void move(Rectangle r, int anchor_id, int dx, int dy) {
		// System.out.println("Loop.move : "+dx+","+dy);
		this.anchorID = anchor_id;
		if (anchor_id == CENTER)
			this.anchor = this.anch0;
		else if (anchor_id == UPPER_LEFT)
			this.anchor = this.anch1;
		else if (anchor_id == UPPER_RIGHT)
			this.anchor = this.anch2;
		else if (anchor_id == BOTTOM_RIGHT)
			this.anchor = this.anch3;
		else if (anchor_id == BOTTOM_LEFT)
			this.anchor = this.anch4;

		if (anchor_id == CENTER)
			moveMe(r, dx, dy);
		else
			resizeMe(r, dx, dy);
	}

	private void moveMe(Rectangle r, int dx, int dy) {
		if (testAnchors(r, dx, dy)) {
			// System.out.println(">>> Loop.moveMe CENTER");
			this.x = this.x + dx;
			this.y = this.y + dy;
		}
	}

//	private void moveMe(int dx, int dy) {
//		this.x = this.x + dx;
//		this.y = this.y + dy;
//	}

	private void resizeMe(Rectangle r, int dx, int dy) {
		// System.out.println(">>> Loop.resizeMe : "+this.anchorID+" :
		// ("+dx+","+dy+")" );
		if (this.anchorID == UPPER_LEFT) {
			if (testActiveAnchor(r, dx, dy)) {
				if (dx < 0 && dy < 0) {
					this.x = this.x + dx;
					this.y = this.y + dy;
					this.w = this.w + Math.abs(dx);
					this.h = this.h + Math.abs(dy);
				} else if (dx >= 0 && dy >= 0) {
					this.x = this.x + dx;
					this.y = this.y + dy;
					this.w = this.w - dx;
					this.h = this.h - dy;
				}
			}
		} else if (this.anchorID == UPPER_RIGHT) {
			if (testActiveAnchor(r, dx, dy)) {
				if (dx > 0 && dy < 0) {
					this.y = this.y + dy;
					this.w = this.w + dx;
					this.h = this.h + Math.abs(dy);
				} else if (dx < 0 && dy > 0) {
					this.y = this.y + dy;
					this.w = this.w + dx;
					this.h = this.h - dy;
				}
			}
		} else if (this.anchorID == BOTTOM_RIGHT) {
			if (testActiveAnchor(r, dx, dy)) {
				if ((dx > 0 && dy > 0) || (dx < 0 && dy < 0)) {
					this.w = this.w + dx;
					this.h = this.h + dy;
				}
			}
		} else if (this.anchorID == BOTTOM_LEFT) {
			if (testActiveAnchor(r, dx, dy)) {
				if (dx < 0 && dy > 0) {
					this.x = this.x + dx;
					this.w = this.w + Math.abs(dx);
					this.h = this.h + dy;
				} else if (dx > 0 && dy < 0) {
					this.x = this.x + dx;
					this.w = this.w - dx;
					this.h = this.h + dy;
				}
			}
		}
		if ((this.x + this.w) >= (r.x + r.width))
			this.w = this.w - ((this.x + this.w) - (r.x + r.width)) * 2;
		if ((this.y + this.h) >= (r.y + r.height))
			this.h = this.h - ((this.y + this.h) - (r.y + r.height)) * 2;
	}

	/** Checks whether the Point p is contained within the loop */
	public boolean contains(Point p) {
		Rectangle r = new Rectangle(this.anch1.x - 5, this.anch1.y - 5,
				Line.MOVE_ANCHOR_SIZE + 2, Line.MOVE_ANCHOR_SIZE + 2);
		// Rectangle r = new Rectangle(this.x-2, y-2, this.w+4, this.h+4);
		if (r.contains(p.x, p.y)) {
			return true;
		} 
		return false;
	}

	private boolean testActiveAnchor(Rectangle r, int dx, int dy) {
		Point a1 = new Point(this.anch1.x, this.anch1.y);
		Point a2 = new Point(this.anch2.x, this.anch2.y);
		Point a3 = new Point(this.anch3.x, this.anch3.y);
		Point a4 = new Point(this.anch4.x, this.anch4.y);
		int wl = 0, hl = 0;
		if (this.anchorID == UPPER_LEFT) {
			a1.x = a1.x + dx;
			a1.y = a1.y + dy;
			a4.x = a4.x + dx;
			a2.y = a2.y + dy;
			if (dx < 0 && dy < 0) {
				wl = this.w + Math.abs(dx);
				hl = this.h + Math.abs(dy);
				if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
						&& minWidth(wl) && minHeight(hl))
					return true;
				
				return false;
			} 
			wl = this.w - dx;
			hl = this.h - dy;
			r.setSize((int) (r.getWidth() + 1), (int) (r.getHeight() + 1));
			if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
					&& minWidth(wl) && minHeight(hl))
				return true;
			
			return false;		
		} else if (this.anchorID == UPPER_RIGHT) {
			a2.x = a2.x + dx;
			a2.y = a2.y + dy;
			a1.y = a1.y + dy;
			a3.x = a3.x + dx;
			if (dx > 0 && dy < 0) {
				wl = this.w + dx;
				hl = this.h + Math.abs(dy);
				if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
						&& minWidth(wl) && minHeight(hl))
					return true;
				
				return false;
			} 
			wl = this.w + dx;
			hl = this.h - dy;
			r.setSize((int) (r.getWidth() + 1), (int) (r.getHeight() + 1));
			if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
					&& minWidth(wl) && minHeight(hl))
				return true;
			
			return false;	
		} else if (this.anchorID == BOTTOM_RIGHT) {
			a3.x = a3.x + dx;
			a3.y = a3.y + dy;
			a4.x = a4.x + dx;
			a2.y = a2.y + dy;
			if ((dx > 0 && dy > 0) || (dx < 0 && dy < 0)) {
				wl = this.w + dx;
				hl = this.h + dy;
			}
			if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
					&& minWidth(wl) && minHeight(hl))
				return true;
			
			return false;
		} else if (this.anchorID == BOTTOM_LEFT) {
			a4.x = a4.x + dx;
			a4.y = a4.y + dy;
			a3.y = a3.y + dy;
			a1.x = a1.x + dx;
			if (dx < 0 && dy > 0) {
				wl = this.w + Math.abs(dx);
				hl = this.h + dy;
				if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
						&& minWidth(wl) && minHeight(hl))
					return true;
				
				return false;
			} 
			wl = this.w - dx;
			hl = this.h + dy;
			r.setSize((int) (r.getWidth() + 1), (int) (r.getHeight() + 1));
			if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4)
					&& minWidth(wl) && minHeight(hl))
				return true;
				
			return false;		
		} else
			return false;
	}

	private boolean inside(Rectangle r, Point a1, Point a2, Point a3, Point a4) {
		if (r.contains(a1.x, a1.y) || r.contains(a2.x, a2.y)
				|| r.contains(a3.x, a3.y) || r.contains(a4.x, a4.y)) {
			return true;
		} 
		return false;
	}

	/**
	 * Checks whether the loop ( as a rectangle) specified by the arguments
	 * Point a1, Point a2, Point a3, Point a4 is outside of the source specified
	 * by the argument Rectangle r
	 */
	public boolean outside(Rectangle r, Point a1, Point a2, Point a3, Point a4) {
		if (!r.contains(a1.x, a1.y) || !r.contains(a2.x, a2.y)
				|| !r.contains(a3.x, a3.y) || !r.contains(a4.x, a4.y))
			return true;
		
		return false;
	}

	private boolean testAnchors(Rectangle r, int dx, int dy) {
		Point a1 = new Point(this.anch1.x, this.anch1.y);
		Point a2 = new Point(this.anch2.x, this.anch2.y);
		Point a3 = new Point(this.anch3.x, this.anch3.y);
		Point a4 = new Point(this.anch4.x, this.anch4.y);

		a1.x = a1.x + dx;
		a1.y = a1.y + dy;
		a2.x = a2.x + dx;
		a2.y = a2.y + dy;
		a3.x = a3.x + dx;
		a3.y = a3.y + dy;
		a4.x = a4.x + dx;
		a4.y = a4.y + dy;

		if (inside(r, a1, a2, a3, a4) && outside(r, a1, a2, a3, a4))
			return true;
		
		return false;
	}

/*
	private void setActiveAnchor(Point p) {
		Rectangle r1 = new Rectangle(this.anch1.x - 4, this.anch1.y - 4, 8, 8);
		Rectangle r2 = new Rectangle(this.anch2.x - 4, this.anch2.y - 4, 8, 8);
		Rectangle r3 = new Rectangle(this.anch3.x - 4, this.anch3.y - 4, 8, 8);
		Rectangle r4 = new Rectangle(this.anch4.x - 4, this.anch4.y - 4, 8, 8);
		Rectangle r0 = new Rectangle(x, y, this.w, this.h);

		if (r1.contains(p.x, p.y)) {
			this.anchor = this.anch1;
			this.anchorID = UPPER_LEFT;
		} else if (r2.contains(p.x, p.y)) {
			this.anchor = this.anch2;
			this.anchorID = UPPER_RIGHT;
		} else if (r3.contains(p.x, p.y)) {
			this.anchor = this.anch3;
			this.anchorID = BOTTOM_RIGHT;
		} else if (r4.contains(p.x, p.y)) {
			this.anchor = this.anch4;
			this.anchorID = BOTTOM_LEFT;
		} else if (r0.contains(p.x, p.y)) {
			this.anchor = this.anch0;
			this.anchorID = CENTER;
			// System.out.println(">>> Loop.setActiveAnchor on CENTER");
		} else {
			this.anchor = null;
			this.anchorID = -1;
		}
	}
*/
	
	private boolean minWidth(int wl) {
		if (wl >= this.min)
			return true;
		
		return false;
	}

	private boolean minHeight(int hl) {
		if (hl >= this.min)
			return true;
		
		return false;
	}

}
