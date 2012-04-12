package agg.editor.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.undo.*;

import agg.attribute.AttrEvent;
import agg.attribute.AttrInstance;
import agg.attribute.view.AttrViewEvent;
import agg.attribute.view.AttrViewObserver;
import agg.attribute.view.AttrViewSetting;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.ContextView;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.TypeException;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphPanel;
import agg.layout.evolutionary.LayoutArc;

/**
 * EdArc specifies an arc layout of an agg.xt_basis.Arc object
 * 
 * @author $Author: olga $
 * @version $Id: EdArc.java,v 1.60 2010/11/14 12:59:11 olga Exp $
 */

public class EdArc extends EdGraphObject implements AttrViewObserver,
		XMLObject, StateEditable {

	
	private Arc bArc;

	private EdGraphObject from;

	private EdGraphObject to;

	private boolean directed = true;

	private Point anchor;

	transient private int anchorID = 0; // only for loop

	transient private boolean hasDefaultAnchor;

	transient private Point textLocation;

	protected Point textOffset, origTextOffset;

	transient private Dimension textSize;

	transient private Point srcMultiplicityLocation;

	transient private Dimension srcMultiplicitySize;

	transient private Point srcMultiplicityOffset;

	transient private Point trgMultiplicityLocation;

	transient private Dimension trgMultiplicitySize;

	transient private Point trgMultiplicityOffset;

	transient private int partOfText; // 0 attrText, 1/2 src/trg

	private LayoutArc lArc;

	
	/**
	 * Creates an arc layout specified by the EdType eType for an used object
	 * specified by the Arc bArc, EdGraphObject from, EdGraphObject to
	 */
	public EdArc(Arc bArc, EdType eType, EdGraphObject from, EdGraphObject to) throws TypeException {
		super(eType);
		
		if (bArc == null || bArc.getSource() == null || bArc.getTarget() == null) {
			throw new TypeException("Basic node is null");
		}
		
		this.bArc = bArc;
		this.from = from;
		this.to = to;
		this.directed = bArc.isDirected();
		this.anchor = null;
		this.hasDefaultAnchor = true;
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		this.textOffset = new Point(0, -22);
		this.textLocation = new Point();
		this.textSize = new Dimension();
		
		if (this.bArc != null) {
			this.contextUsage = String.valueOf(this.hashCode());
			if (this.bArc.getAttribute() != null) {
				addToAttributeViewObserver();
			}
		}

		this.lArc = new LayoutArc(this);
	}

	/**
	 * Creates an arc layout specified by the EdType eType, EdGraphObject from,
	 * EdGraphObject to for an used object of the class agg.xt_basis.Arc that
	 * would be created from the graph specified by the Graph bGraph
	 */
	public EdArc(Graph bGraph, EdType eType, EdGraphObject from,
			EdGraphObject to) throws TypeException {
		this(bGraph, eType, from, to, null);
	}

	/**
	 * Creates an arc layout specified by the EdType eType, EdGraphObject from,
	 * EdGraphObject to, Point anchor ( bend ) for an used object of the class
	 * agg.xt_basis.Arc that would be created from the graph specified by the
	 * Graph bGraph
	 */
	public EdArc(Graph bGraph, EdType eType, EdGraphObject from,
			EdGraphObject to, Point anchor) throws TypeException {

		this((bGraph != null) ? bGraph.createArc(eType.bType, (Node) from
				.getBasisObject(), (Node) to.getBasisObject()) : null, eType,
				from, to);
		setAnchor(anchor);
	}

	/** Marks this as element of a type graph */
	public void markElementOfTypeGraph(boolean val) {
		this.elemOfTG = val;
		if (this.elemOfTG && !this.getContext().isInheritanceType(this.eType)) {
			createMultiplicityVars();
		}
	}
	
	private void createMultiplicityVars() {
		if (this.srcMultiplicityLocation == null)
			this.srcMultiplicityLocation = new Point();	
		if (this.srcMultiplicitySize == null)
			this.srcMultiplicitySize = new Dimension();
		if (this.srcMultiplicityOffset == null)
			this.srcMultiplicityOffset = new Point();
		if (this.trgMultiplicityLocation == null)
			this.trgMultiplicityLocation = new Point();
		if (this.trgMultiplicitySize == null)
			this.trgMultiplicitySize = new Dimension();
		if (this.trgMultiplicityOffset == null)
			this.trgMultiplicityOffset = new Point();
	}
	
	/** Disposes myself */
	public void dispose() {
		if (this.attrObserver) {
			removeFromAttributeViewObserver();
		}
		this.view = null;
		if (this.lArc != null)
			this.lArc.dispose(); 
		this.lArc = null;
		this.eGraph = null;
		this.eType = null;
		this.from = null;
		this.to = null;
		this.bArc = null;
		this.myGraphPanel = null;
	}

	public void finalize() { }
	
	public void storeState(Hashtable<Object, Object> state) {
		ArcReprData data = new ArcReprData(this);
		state.put(Integer.valueOf(this.hashCode()), data);
		state.put(Integer.valueOf(data.hashCode()), data);
		this.itsUndoReprDataHC = Integer.valueOf(data.hashCode());
	}

	public void restoreState(Hashtable<?, ?> state) {	
//		System.out.println("EdArc.restoreState:: "+state.get(Integer.valueOf(this.hashCode()))+"   "+state.get(this.itsUndoReprDataHC));

		ArcReprData data = (ArcReprData) state.get(Integer.valueOf(this.hashCode()));
		if (data == null) {
			data = (ArcReprData) state.get(this.itsUndoReprDataHC);	
		}
		if (data != null) {
			data.restoreArcFromArcRepr(this);
			this.attrChanged = false;
		}
	}

	public void restoreState(Hashtable<?, ?> state, String hashCode) {
//		System.out.println("### EdArc.restoreState:: "+state.get(Integer.valueOf(hashCode))+"   "+state.get(this.itsUndoReprDataHC));
		
		ArcReprData data = (ArcReprData) state.get(Integer.valueOf(hashCode));
		if (data == null) {
			data = (ArcReprData) state.get(this.itsUndoReprDataHC);
		}
		if (data == null) {
			data = (ArcReprData) state.get(Integer.valueOf(this.hashCode()));
		}
		
		if (data != null) {
			data.restoreArcFromArcRepr(this);
			this.attrChanged = false;
		}
	}
	
	public void restoreState(ArcReprData data) {
		data.restoreArcFromArcRepr(this);
		this.attrChanged = false;
	}
	
	/** Returns an open view of my attribute */
	protected AttrViewSetting getView() {
		if (!this.init || this.view == null) {
//			this.view = new OpenViewSetting((AttrTupleManager)AttrTupleManager.getDefaultManager());
			
			this.view = ((AttrTupleManager)AttrTupleManager.getDefaultManager()).getDefaultOpenView();
			
			AttrInstance attr = this.bArc.getAttribute();
			DeclTuple decl = attr.getTupleType();			
			for (int i = 0; i < decl.getNumberOfEntries(); i++) {
				DeclMember mem = (DeclMember) decl.getMemberAt(i);
				this.view.setVisibleAt(attr, mem.isVisible(), i);			
			}
			
			this.init = true;
		}
		return this.view;
	}
	
	public void setAttrViewSetting(AttrViewSetting aView) {
		this.view = aView;
		if (!this.attrObserver) {
			this.view.addObserver(this, this.bArc.getAttribute());
			this.attrObserver = true;
		}
		this.init = true;
	}
	
	public void addToAttributeViewObserver() {
		getView().addObserver(this, this.bArc.getAttribute());
		this.attrObserver = true;
	}
	
	
	public void removeFromAttributeViewObserver() {
		if (this.bArc != null 
				&& this.bArc.getAttribute() != null
				&& this.view != null) {
			this.view.removeObserver(this, this.bArc.getAttribute());
			this.view.getOpenView().removeObserver(this, this.bArc.getAttribute());
			this.view.getMaskedView()
					.removeObserver(this, this.bArc.getAttribute());
		}
	}
	public void createAttributeInstance() {
		if (this.bArc != null && this.bArc.getAttribute() == null) {
			this.bArc.createAttributeInstance();
			addToAttributeViewObserver();
		}
	}

	public void refreshAttributeInstance() {
		if (this.bArc != null && this.bArc.getAttribute() != null) {
			((ValueTuple) this.bArc.getAttribute()).getTupleType().refreshParents();
			addToAttributeViewObserver();
		}
	}

	/** Returns the layout arc of this arc. */
	public LayoutArc getLArc() {
		return this.lArc;
	}

	/** Returns the used object */
	public final Arc getBasisArc() {
		return this.bArc;
	}

	/** Returns the used object of this arc. */
	public final GraphObject getBasisObject() {
		return this.bArc;
	}

	/** Returns FALSE */
	public final boolean isNode() {
		return false;
	}

	/** Returns TRUE */
	public final boolean isArc() {
		return true;
	}

	/** Returns NULL */
	public final EdNode getNode() {
		return null;
	}

	/** Returns this arc */
	public final EdArc getArc() {
		return this;
	}
	
	public void setCritical(boolean b) {
		this.bArc.setCritical(b);
	}

	public boolean isCritical() {
		return this.bArc.isCritical();
	}

	/**
	 * States how to draw critical objects of CPA critical overlapping graphs:
	 * <code>EdGraphObject.CRITICAL_GREEN</code> or
	 * <code>EdGraphObject.CRITICAL_BLACK_BOLD</code>.
	 */
	public void setDrawingStyleOfCriticalObject(int criticalStyle) {
		this.criticalStyle = criticalStyle;
	}
	
	/** Returns TRUE if this uses a line for the graphic */
	public final boolean isLine() {
		if (!this.from.equals(this.to))
			return true;
		
		return false;
	}

	/** Returns TRUE if <code>this</code> has a bend */
	public boolean hasAnchor() {
		return (((this.anchor != null) && !this.hasDefaultAnchor) ? true : false);
	}

	/** Returns the point of my bend (I am a line) */
	public Point getAnchor() {
		if (this.anchor != null)
			return this.anchor;
		if (isLine())
			return new Point(getX(), getY());
		
		return null;
	}

	/** Returns the point of my bend specified by the int id (I am a loop) */
	public Point getAnchor(int id) {
		if (this.anchor != null)
			return this.anchor;
		if (!isLine()) {
			Loop loop = toLoop();
			return loop.getAnchor(id);
		} 
		return null;
	}

	/** Returns the id of my bend */
	public int getAnchorID() {
		return this.anchorID;
	}

	/** Returns TRUE if <code>this</code> has only one direction */
	public boolean isDirected() {
		if (this.bArc != null) {
			this.directed = this.bArc.isDirected();
		} 
		return this.directed;

	}

	public boolean isVisible() {
		if (this.bArc != null) {
			this.visible = this.bArc.isVisible() && this.from.isVisible() && this.to.isVisible();
			
			if (this.getContext().getBasisGraph().isCompleteGraph()) {
				this.visible = this.visible
					&& this.getType().getBasisType().isObjectOfTypeGraphArcVisible(
											this.getSource().getType().getBasisType(), 
											this.getTarget().getType().getBasisType());
			}
		} 
		return this.visible;
	}

	/**
	 * Returns the attributes which are shown
	 */
	public Vector<Vector<String>> getAttributes() {
		// maybe this method can be moved to EdGraphObjec
		Vector<Vector<String>> attrs = new Vector<Vector<String>>();
		if (this.bArc != null && this.bArc.getAttribute() != null) {
			AttrInstance attributes = this.bArc.getAttribute();

			if (attributes != null && getView() != null) {
				AttrViewSetting mvs = this.view.getMaskedView();

				int number = mvs.getSize(attributes);
				for (int i = 0; i < number; i++) {
					Vector<String> tmpAttrVector = new Vector<String>();
					int index = mvs.convertSlotToIndex(attributes, i);
					tmpAttrVector.addElement(attributes.getTypeAsString(index));
					tmpAttrVector.addElement(attributes.getNameAsString(index));
					tmpAttrVector
							.addElement(attributes.getValueAsString(index));
					attrs.addElement(tmpAttrVector);
				}
			} else
				attrs = setAttributes(this.bArc);
		}
		return attrs;
	}

	/** Sets my attributes to the attributes specified by the Arc bArc */
	public Vector<Vector<String>> setAttributes(Arc bArc) {
		Vector<Vector<String>> attrs = new Vector<Vector<String>>();
		if (bArc == null)
			return attrs;
		if (bArc.getAttribute() == null)
			return attrs;

		int nattrs = bArc.getAttribute().getNumberOfEntries();
		if (nattrs != 0) {
			for (int i = 0; i < nattrs; i++) {
				Vector<String> attr = new Vector<String>();
				attr.addElement(bArc.getAttribute().getTypeAsString(i));
				attr.addElement(bArc.getAttribute().getNameAsString(i));
				attr.addElement(bArc.getAttribute().getValueAsString(i));
				attrs.addElement(attr);
			}
		}
		return attrs;
	}

	/** Sets my attributes to the attributes specified by the GraphObject obj */
	public Vector<Vector<String>> setAttributes(GraphObject obj) {
		return setAttributes((Arc) obj);
	}

	/** Sets my used object specified by the Arc bArc */
	public void setBasisArc(Arc bArc) {
		this.bArc = bArc;
	}

	/** Gets my source object */
	public EdGraphObject getSource() {
		return this.from;
	}

	/** Sets my source object */
	public void setSource(EdGraphObject en) {
		this.from = en;
	}

	/** Gets my target object */
	public EdGraphObject getTarget() {
		return this.to;
	}

	/** Sets my target object */
	public void setTarget(EdGraphObject en) {
		this.to = en;
	}

	/** Sets my direction */
	public void setDirected(boolean direct) {
		this.directed = direct;
		if (this.bArc != null)
			this.bArc.setDirected(direct);
	}

	/**
	 * Sets my bend (I am a line) to the position specified by the Point
	 * newAnchor
	 */
	public void setAnchor(Point newAnchor) {
		this.anchor = newAnchor;
		if (this.anchor == null) {
			this.hasDefaultAnchor = true;
		} else if (isLine()) {
			setXY(this.anchor.x, this.anchor.y);
			this.hasDefaultAnchor = false;
		} else { // Loop
			setAnchor(Loop.UPPER_LEFT, newAnchor);
		}
	}

	/**
	 * Sets my bend (I am a loop) specified by the int id to the position
	 * specified by the Point newAnchor
	 */
	public void setAnchor(int id, Point newAnchor) {
		this.anchor = newAnchor;
		if (this.anchor == null) {
			this.hasDefaultAnchor = true;
		} else if (!isLine() && id == Loop.UPPER_LEFT) { /*
															 * 1 : anchor of
															 * loop
															 */
			setXY(this.anchor.x, this.anchor.y);
			this.hasDefaultAnchor = false;
		}
	}

	/** Sets my representation features: directed, visible, selected */
	public void setReps(boolean direct, boolean vis, boolean sel) {
		setDirected(direct);
		setVisible(vis);
		setSelected(sel);
	}

	/**
	 * Makes an exact copy. Only the basis arc is the same.
	 */
	public EdArc copy() {
		try {
			EdArc newArc = new EdArc(this.bArc, this.eType, this.from, this.to);
			newArc.setAnchor(getAnchor());
			return newArc;
		} catch (TypeException ex) {
			return null;
		}
	}

	/** Gets myself as a line representation */
	public final Line toLine() {
		Line line = new Line(this.from.getX(), this.from.getY(), this.to.getX(), this.to.getY());
		if ((this.anchor != null))
			line.setAnchor(new Point(this.anchor.x, this.anchor.y));
		return line;
	}

	/** Gets myself as a loop (rectangle) representation */
	public final Loop toLoop() {
		Loop loop = new Loop(this.x, this.y, this.w, this.h);
		return loop;
	}

	/** Returns the size of the text */
	public Dimension getTextSize(FontMetrics fm) {
		this.textSize.setSize(new Dimension(super.getTextWidth(fm), super
				.getTextHeight(fm)));
		return this.textSize;
	}

	/** Returns the text offset */
	public Point getTextOffset() {
		return this.textOffset;
	}

	/** Sets the text offset */
	public void setTextOffset(int xOffset, int yOffset) {
		this.textOffset.x = xOffset;
		this.textOffset.y = yOffset;
	}

	/**
	 * Translates offset of an edge text to a new value specified by the dx and
	 * dy, if the text will be moved. The text can be an attribute text or a
	 * source multiplicity text or a target multiplicity text.
	 */
	public void translateTextOffset(int dx, int dy) {
		if (this.partOfText == 0) {
			this.textOffset.translate(dx, dy);
		} else if (this.partOfText == 1) {
			this.srcMultiplicityOffset.translate(dx, dy);
		} else if (this.partOfText == 2) {
			this.trgMultiplicityOffset.translate(dx, dy);
		}
	}

	/**
	 * Returns TRUE if the point specified by the int X, int Y is inside of
	 * myself
	 */
	public boolean inside(int X, int Y) {
		this.anchorID = 0;
		if (isLine()) {
			Rectangle r = new Rectangle(this.x - this.w/2, this.y - this.h/2, this.w, this.h);
			if (r.contains(X, Y)) {
				// System.out.println(X+" , "+Y+" arc: "+this.x+" , "+this.y+" :
				// "+w);
				return true;
			} 
			return false;
		} 
		/* Loop */
		Loop loop = toLoop();
		if (loop.contains(new Point(X, Y))) {
			if (loop.anchorID == Loop.UPPER_LEFT) {
				this.anchor = loop.anchor;
				this.anchorID = loop.anchorID;
			}
			return true;
		} 
		return false;		
	}

	/**
	 * Returns TRUE if the point specified by the int X, int Y is inside of my
	 * text part
	 */
	public boolean insideTextOfArc(int X, int Y, FontMetrics fm) {
		Rectangle r = getTextRectangle(fm);
		if ((r != null) && (r.contains(X, Y))) {
			this.partOfText = 0;
			return true;
		} else if (this.elemOfTG && !this.getContext().isInheritanceType(this.eType)) {
			if (insideTextOfMultiplicity(X, Y, "source")) {
				this.partOfText = 1;
				return true;
			} else if (insideTextOfMultiplicity(X, Y, "target")) {
				this.partOfText = 2;
				return true;
			} else
				return false;
		} else
			return false;
	}

	private Rectangle getTextRectangle(FontMetrics fm) {
		Dimension d = getTextSize(fm);
		// System.out.println(d);
		int tw = (int) d.getWidth();
		int th = (int) d.getHeight();
		if (isLine()) {
			int tx = 0, ty = 0;
//			int x1 = from.getX();
//			int y1 = from.getY();
//			int x2 = to.getX();
//			int y2 = to.getY();
			if (this.anchor != null) {
				tx = this.anchor.x;
				ty = this.anchor.y;
			} else {
				tx = this.x;
				ty = this.y;
			}
			tx = tx - tw/2;
			this.textLocation.x = tx + this.textOffset.x;
			this.textLocation.y = ty + this.textOffset.y;
			return new Rectangle(this.textLocation.x, this.textLocation.y, tw, th);
		} 
		// loop
		this.textLocation.x = this.x + this.textOffset.x;
		this.textLocation.y = this.y + this.textOffset.y;
		return new Rectangle(this.textLocation.x, this.textLocation.y, tw, th);
	}

	/**
	 * Returns TRUE if the point specified by the int X, int Y is inside of my
	 * source or target multiplicity part
	 */
	private boolean insideTextOfMultiplicity(int X, int Y, String key) {
		if (key.equals("target")) {
			Rectangle r = new Rectangle(
					this.trgMultiplicityLocation.x + this.trgMultiplicityOffset.x, 
					this.trgMultiplicityLocation.y + this.trgMultiplicityOffset.y - (int) this.trgMultiplicitySize.getHeight(),
					(int) this.trgMultiplicitySize.getWidth(),
					(int) this.trgMultiplicitySize.getHeight());
			if (r.contains(X, Y)) {
				return true;
			} 
			return false;
		} else if (key.equals("source")) {
			Rectangle r = new Rectangle(
					this.srcMultiplicityLocation.x + this.srcMultiplicityOffset.x, 
					this.srcMultiplicityLocation.y + this.srcMultiplicityOffset.y - (int) this.srcMultiplicitySize.getHeight(),
					(int) this.srcMultiplicitySize.getWidth(),
					(int) this.srcMultiplicitySize.getHeight());
			if (r.contains(X, Y)) {
				return true;
			} 
			return false;
		} else
			return false;
	}


	public void applyScale(double scale) {
		if (scale != this.itsScale) {
			setX((int) (this.x * (scale / this.itsScale)));
			setY((int) (this.x * (scale / this.itsScale)));
			if (this.anchor != null) {
				this.anchor.x = (int) (this.anchor.x * (scale / this.itsScale));
				this.anchor.y = (int) (this.anchor.y * (scale / this.itsScale));
			}
			if (this.textOffset != null) {
				this.textOffset.x = (int) (this.textOffset.x * (scale / this.itsScale));
				this.textOffset.y = (int) (this.textOffset.y * (scale / this.itsScale));
			}
			if (!this.isLine()) {
				this.w = (int) (this.w * (scale / this.itsScale));
				this.h = (int) (this.h * (scale / this.itsScale));
			}
			this.itsScale = scale;
		}
	}

	public void drawShadowGraphic(Graphics grs) {
		if (this.visible) {
			Graphics2D g = (Graphics2D) grs;
			// save color, font style
			Color lastColor = g.getColor();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);			
			g.setPaint(Color.LIGHT_GRAY);
			g.setStroke(EditorConstants.defaultStroke);
			
			if (this.isLine()) {
				g.draw(new Rectangle2D.Double(this.anchor.x-10, this.anchor.y-10, 20, 20));
			}
			else {
				g.draw(new Rectangle2D.Double(this.anchor.x-10, this.anchor.y-10, 20, 20));
			}
			// reset font style, color
			g.setFont(EditorConstants.defaultFont);
			g.setPaint(lastColor);
		}
	}
	
	public void drawTextShadowGraphic(Graphics grs, int px, int py) {
		if (this.visible) {
			Graphics2D g = (Graphics2D) grs;
			// save color, font style
			Color lastColor = g.getColor();
			int fontstyle = g.getFont().getStyle();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);			
			g.setPaint(Color.LIGHT_GRAY);
			g.setStroke(EditorConstants.defaultStroke);			
			g.draw(new Rectangle2D.Double(px-10, py-10, 20, 20));
			
			// reset font style, color
			g.setFont(new Font("Dialog", fontstyle, g.getFont().getSize()));
			g.setPaint(lastColor);
		}
	}
	
	/** Draws myself in the graphics specified by the Graphics g */
	public void drawGraphic(Graphics grs) {	
//		synchronized (this) 
		{
		if (!this.visible) {
			return;
		}
		
		this.criticalStyle = this.eGraph.criticalStyle;
		
		Graphics2D g = (Graphics2D) grs;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (this.eType.filled) {
			g.setStroke(EditorConstants.boldStroke);
		} else {
			g.setStroke(EditorConstants.defaultStroke);
		}

		// save the old color
		Color lastColor = g.getColor();
				
		if (this.backgroundColor != null && this.backgroundColor != Color.white) {
			g.setPaint(this.backgroundColor);
			if (this.from != this.to)
				drawBackgroundLine(g);
			else
				drawBackgroundLoop(g);
		}

		boolean hiddenObjectsOfType = this.eGraph.isTypeGraph() 
						&& !this.eType.getBasisType().isObjectOfTypeGraphArcVisible(
								this.from.getType().getBasisType(),
								this.to.getType().getBasisType());
		
		if (isSelected()) {
			g.setPaint(getSelectColor());
		} else if (hiddenObjectsOfType) {
			g.setPaint(EditorConstants.hideColor);
		} else if (isCritical()) {
			if (this.criticalStyle == 0) {
				g.setStroke(EditorConstants.criticalColorStroke);
				g.setFont(EditorConstants.criticalFont);
				g.setPaint(EditorConstants.criticalColor);
			}
			else {//if (this.criticalStyle == 1) {
				g.setStroke(EditorConstants.criticalStroke);
				g.setPaint(Color.BLACK);
			} 
		} else {			
			g.setPaint(this.getColor());
		}
		
		if (this.from != this.to) {
			drawArcAsLine(g, true);
		} else {
			drawArcAsLoop(g, true);
		}
		
		if (this.errorMode) {
			showErrorAnchor(g);
		} 		
		g.setStroke(EditorConstants.defaultStroke);
		g.setFont(EditorConstants.defaultFont);
		g.setPaint(lastColor);
		}
	}

	/** Draws text of my graphics */
	public void drawText(Graphics grs, double scale) {
		Graphics2D g = (Graphics2D) grs;		
		g.setPaint(this.getColor());
		int tx, ty;
		if (isLine()) {
			int tw = (int) getTextSize(g.getFontMetrics()).getWidth();
			// int tw = getTextWidth(g.getFontMetrics());
			if (this.anchor != null) {
				tx = this.anchor.x;
				ty = this.anchor.y;
			} else {
				tx = getX();
				ty = getY();
			}
			tx = tx - tw/2;
			this.textLocation.x = tx + this.textOffset.x;
			this.textLocation.y = ty + this.textOffset.y;
		} else {
			this.textLocation.x = this.x + this.textOffset.x;
			this.textLocation.y = this.y + this.textOffset.y;
		}
		drawText(g, this.textLocation.x, this.textLocation.y);
	}

	/** Erases text of my graphics */
	public void eraseText(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		g.setPaint(Color.white);
		Rectangle r;
		if (isLine()) {
			r = getTextRectangle(g.getFontMetrics());
			g.fillRect(r.x, r.y, r.width, r.height);
		} else {
			r = getTextRectangle(g.getFontMetrics());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}

	/** Erases my graphic */
	public void eraseGraphic(Graphics grs) {
		// System.out.println("EdArc.eraseGraphic");
		Graphics2D g = (Graphics2D) grs;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g.setStroke(EditorConstants.basicStroke);
		g.setPaint(Color.white);
		eraseMoveAnchor(g);
		if (isLine()) {
			eraseArcAsLine(g, true);
			/*
			 * Rectangle r = getTextRectangle(g.getFontMetrics());
			 * g.fillRect(r.x, r.y, r.width, r.height); drawArcAsLine(g, scale,
			 * false);
			 */
		} else {
			eraseArcAsLoop(g, true);
			/*
			 * Rectangle r = getTextRectangle(g.getFontMetrics());
			 * g.fillRect(r.x, r.y, r.width, r.height); drawArcAsLoop(g, scale,
			 * false);
			 */
		}
		// g.setStroke(stroke);
	}

	/** Gets the width of my representation like loop */
	public int getWidthOfLoop() {
		if (getWidth() == 0)
			return Loop.DEFAULT_SIZE;
		
		return getWidth();
	}

	/** Gets the height of my representation like loop */
	public int getHeightOfLoop() {
		if (getHeight() == 0)
			return Loop.DEFAULT_SIZE;
		
		return getHeight();
	}

	/** Shows the bend which will be moved */
	public void showMoveAnchor(Graphics g) {
		if (!this.from.equals(this.to))
			showMoveAnchorOfLine(g);
		else
			showMoveAnchorOfLoop(g);
	}

	/** Erases the anchor marking of the bend */
	public void eraseMoveAnchor(Graphics g) {
		if (!this.from.equals(this.to))
			eraseMoveAnchorOfLine(g);
		else
			eraseMoveAnchorOfLoop(g);
	}

	/**
	 * Implements the AttrViewObserver. Makes update graphics if the attributes
	 * of my used object are changed.
	 */
	public void attributeChanged(AttrViewEvent ev) {
		if (ev.getID() == AttrEvent.GENERAL_CHANGE // 0
				// || ev.getID() == AttrEvent.MEMBER_ADDED // 10
				|| ev.getID() == AttrEvent.MEMBER_DELETED // 20
				|| ev.getID() == AttrEvent.MEMBER_RETYPED // 60
				|| ev.getID() == AttrEvent.MEMBER_RENAMED // 50
				|| ev.getID() == AttrViewEvent.MEMBER_VISIBILITY // 220
				|| ev.getID() == AttrViewEvent.MEMBER_MOVED ) { // 210
		
			if (ev.getSource().getTupleType().isValid()) {
				this.attrChanged = true;
			}
			
		} else if (ev.getID() == AttrEvent.MEMBER_VALUE_CORRECTNESS // 70
				|| ev.getID() == AttrEvent.MEMBER_VALUE_MODIFIED) { // 80
		
			if (ev.getSource().isValid()) {
				this.attrChanged = true;
				if (this.myGraphPanel != null) {
					if (this.myGraphPanel.isAttrEditorActivated()) {
						if (this.bArc.getContext().getAttrContext() != null) {
							ValueMember val = ((ValueTuple) this.bArc.getAttribute())
									.getValueMemberAt(ev.getIndex());
							if (val.isSet() && val.getExpr().isVariable()) {
								ContextView viewContext = (ContextView) ((ValueTuple) val
										.getHoldingTuple()).getContext();
								VarTuple variable = (VarTuple) viewContext
										.getVariables();
								VarMember var = variable.getVarMemberAt(val
										.getExprAsText());
								if (var == null)
									return;
							
								if (this.bArc.getContext().isNacGraph())
									var.setMark(VarMember.NAC);
								else if (this.bArc.getContext().isPacGraph())
									var.setMark(VarMember.PAC);
								else if (viewContext
										.doesAllowComplexExpressions())
									var.setMark(VarMember.RHS);
								else
									var.setMark(VarMember.LHS);
							}
						}
					}
				}
			} else {
				ValueTuple attr = (ValueTuple) this.bArc.getAttribute();
				for (int i = 0; i < attr.getSize(); i++) {
					ValueMember am = (ValueMember) attr.getMemberAt(i);
					if (!am.isValid())
						break;
				}
			}
		}
	}

	public void setGraphPanel(GraphPanel gp) {
		this.myGraphPanel = gp;
	}
	
	private void showMoveAnchorOfLine(Graphics grs) {
		// System.out.println("showMoveAnchorOfLine");
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Line.MOVE_ANCHOR_COLOR); 
		g.fill(new Rectangle(getX() - Line.MOVE_ANCHOR_OFFSET, 
								getY() - Line.MOVE_ANCHOR_OFFSET, 
								Line.MOVE_ANCHOR_SIZE,
								Line.MOVE_ANCHOR_SIZE));
		g.setPaint(lastColor);
	}

	private void showMoveAnchorOfLoop(Graphics grs) {
		Loop loop = new Loop(getX(), getY(), getWidth(), getHeight());
		loop.drawMoveAnchor(grs, this.anchorID);
	}

	/**
	 * Draws a sign to mark wrong arcs. If {@link EdGraphObject#errorMode} is
	 * true, a green box will be shown.
	 */
	protected void showErrorAnchor(Graphics g) {
		if (!this.from.equals(this.to))
			showErrorAnchorOfLine(g);
		else
			showErrorAnchorOfLoop(g);
	}// showErrorAnchor

	/**
	 * Draws a sign to mark wrong arcs between different nodes. If
	 * {@link EdGraphObject#errorMode} is true, a green box will be shown.
	 */
	private void showErrorAnchorOfLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Color.green);
		g.fill(new Rectangle2D.Double(this.x - 6, this.y - 6, 12, 12));
		g.setPaint(lastColor);
	}// showErrorAnchorOfLine

	/**
	 * Draws a sign to mark wrong arcs between the same node. If
	 * {@link EdGraphObject#errorMode} is true, a green box will be shown.
	 */
	private void showErrorAnchorOfLoop(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Color.green);
		Loop loop = new Loop(getX(), getY(), getWidth(), getHeight());
		if (this.anchorID == Loop.UPPER_LEFT)
			g.fill(new Rectangle2D.Double(loop.anch1.x - 6, loop.anch1.y - 6,
					12, 12));
		g.setPaint(lastColor);
	}// showErrorAnchorOfLoop

	private void eraseMoveAnchorOfLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Color.white);
		g.fill(new Rectangle2D.Double(this.x - 5, this.y - 5, 10, 10));
		g.setPaint(lastColor);
	}

	private void eraseMoveAnchorOfLoop(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		Color lastColor = g.getColor();
		g.setPaint(Color.white);
		Loop loop = new Loop(getX(), getY(), getWidth(), getHeight());
		if (this.anchorID == Loop.UPPER_LEFT)
			g.fill(new Rectangle2D.Double(loop.anch1.x - 5, loop.anch1.y - 5,
					10, 10));
		g.setPaint(lastColor);
	}

	private void drawArcAsLine(Graphics grs, boolean withText) {
		Graphics2D g = (Graphics2D) grs;
		boolean needAnchorTuning = true;
		/* set the edge data */
		int x1 = this.from.getX();
		int y1 = this.from.getY();
		int x2 = this.to.getX();
		int y2 = this.to.getY();
		int srcW = this.to.getWidth();
		int srcH = this.to.getHeight();
		int tarW = this.from.getWidth();
		int tarH = this.from.getHeight();
		
		Line line = this.toLine();
		if (this.anchor != null) {
			line.setAnchor(new Point(this.anchor.x, this.anchor.y));
			needAnchorTuning = false;
		}
		/* set XY of move position of arc */
		setXY(line.getAnchor().x, line.getAnchor().y);
		/* set width, height */
		if (getWidth() == getHeight() && getHeight() == 0) {
			setWidth(14);
			setHeight(14);
		}
		/* show arc */
		line.setColor(g.getColor());
		int sh = getShape();
		switch (sh) {
		case EditorConstants.SOLID:
			line.drawColorSolidLine(g);
			break;
		case EditorConstants.DOT:
			line.drawColorDotLine(g);
			break;
		case EditorConstants.DASH:
			line.drawColorDashLine(g);
			break;
		default:
			break;
		}

		if (this.elemOfTG) {
			// Edges arrow and Multiplicity of edge target
			// Head of edge
			Arrow arrow = new Arrow(this.itsScale, 
					line.getAnchor().x, line.getAnchor().y,
					x2, y2, srcW, srcH, 0);					

			if (this.bArc.isInheritance())
				arrow.draw(g, false);
			else if (this.directed)
				arrow.draw(g);
					
			Arrow backArrow = new Arrow(this.itsScale, 
					line.getAnchor().x, line.getAnchor().y, 
					x1, y1, tarW, tarH, 0);
			
			if (this.directed && needAnchorTuning) {
				Point beg = backArrow.getHeadEnd();
				Point end = arrow.getHeadEnd();
				if (beg != null && end != null) {
					int anchX = beg.x + (end.x - beg.x) / 2;
					int anchY = beg.y + (end.y - beg.y) / 2;
					line.setAnchor(new Point(anchX, anchY));
					setXY(anchX, anchY);
				}
			}
						
			if (!this.bArc.isInheritance()) {
				Point p = new Point();
				// draw target multiplicity
				if ((arrow.getRightEnd() != null) && (y2 > line.getAnchor().y)) {
					p.y = arrow.getRightEnd().y - 5;
					p.x = arrow.getRightEnd().x;
				} else if (arrow.getLeftEnd() != null) {
					p.y = arrow.getLeftEnd().y + 5;
					p.x = arrow.getLeftEnd().x;
				}
				drawMultiplicity(g, "target", p, this.eType.getBasisType()
						.getTargetMin(this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()), 
								this.eType.getBasisType().getTargetMax(
								this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()));
				
				// draw source multiplicity
				if ((backArrow.getRightEnd() != null)
						&& (y1 > line.getAnchor().y)) {
					p.y = backArrow.getRightEnd().y - 5;
					p.x = backArrow.getRightEnd().x;
				} else if (backArrow.getLeftEnd() != null) {
					p.y = backArrow.getLeftEnd().y + 5;
					p.x = backArrow.getLeftEnd().x;
				}
				drawMultiplicity(g, "source", p, this.eType.getBasisType()
						.getSourceMin(this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()), 
								this.eType.getBasisType().getSourceMax(
								this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()));
			}
		}
		else { // plain graph
			if (this.directed) {
				int headsize = (isCritical() && (this.criticalStyle == 1)) ? 17: 0;
				Arrow arrow = new Arrow(this.itsScale, line.getAnchor().x, line.getAnchor().y,
						x2, y2, srcW, srcH, headsize);
				arrow.draw(g);
					
				if (needAnchorTuning) {
					Arrow backArrow = new Arrow(this.itsScale, line.getAnchor().x, line
							.getAnchor().y, x1, y1, tarW, tarH, headsize);
					Point beg = backArrow.getHeadEnd();
					Point end = arrow.getHeadEnd();
					if (beg != null && end != null) {
						int anchX = beg.x + (end.x - beg.x) / 2;
						int anchY = beg.y + (end.y - beg.y) / 2;
						line.setAnchor(new Point(anchX, anchY));
						setXY(anchX, anchY);
					}
				}
			}
		}
		
		/* Text */
		if (withText) {
			g.setStroke(EditorConstants.defaultStroke);
			// save the old color
//			Color lastColor = g.getColor();
			
			this.textLocation.x = getX() + this.textOffset.x;
			this.textLocation.y = getY() + this.textOffset.y;
			drawText(g, this.textLocation.x, this.textLocation.y);

//			g.setPaint(lastColor);
		}
	}

	private void drawBackgroundLine(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		g.setStroke(new BasicStroke(5.0f));
		Line line = toLine();
		if ((this.anchor != null)) {
			line.setAnchor(new Point(this.anchor.x, this.anchor.y));
		}
		/* set width, height */
		if (getWidth() == getHeight() && getHeight() == 0) {
			setWidth(14);
			setHeight(14);
		}
		/* draw arc */
		line.setColor(g.getColor());
		line.drawColorSolidLine(g);
		g.setStroke(EditorConstants.defaultStroke);
	}

	private void eraseArcAsLine(Graphics grs, boolean withText) {
		Graphics2D g = (Graphics2D) grs;
		int nX[] = new int[6];
		int nY[] = new int[6];
		int nP, n = 5, n1 = 5;
		nX[0] = this.from.getX();
		nY[0] = this.from.getY() - n;
		nX[1] = getX();
		nY[1] = getY() - n;
		nX[2] = this.to.getX();
		nY[2] = this.to.getY() - (n + n1);
		nX[3] = this.to.getX();
		nY[3] = this.to.getY() + (n + n1);
		nX[4] = getX();
		nY[4] = getY() + n;
		nX[5] = this.from.getX();
		nY[5] = this.from.getY() + n;
		nP = 6;
		g.fillPolygon(nX, nY, nP);
		if (withText) {
			Rectangle r = getTextRectangle(g.getFontMetrics());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}

	private void drawArcAsLoop(Graphics grs, boolean withText) {		
		Graphics2D g = (Graphics2D) grs;
		
		int fromX = this.from.getX();
		int fromY = this.from.getY();
		int fromW = this.from.getWidth();
		int fromH = this.from.getHeight();
		
		// rechne width und height um, wenn source node ist CIRCLE
		if (((EdNode) this.from).getShape() == EditorConstants.CIRCLE) {
			fromW = (int) (Math.acos(1/2) * (this.from.getWidth()/2));
			fromH = fromW;
		}
		// rechne width und height um, wenn source node ist OVAL
		else if (((EdNode) this.from).getShape() == EditorConstants.OVAL) {
			int nn = 0;
			if (fromW < fromH)
				nn = fromW - (fromH - fromW)/2;
			else
				nn = fromH - (fromW - fromH)/2;
			fromW = nn;
			fromH = nn;
		}
		/* set the edge data for first time */
		int w1 = 0, h1 = 0, x1 = 0, y1 = 0;
//		int w2 = 0, h2 = 0, x2 = 0, y2 = 0;
		int offsetX = 0, offsetY = 0;
		if (getWidth() == getHeight() && getHeight() == 0) {
			w1 = getWidthOfLoop();
			h1 = w1;
			offsetX = fromW/2 + w1/2 + w1/4;
			offsetY = fromH/2 + h1/2 + h1/4;
			x1 = fromX - offsetX;
			y1 = fromY - offsetY;
			/* set position of arc */
			setXY(x1, y1);
			/* set width, height */
			setWidth(w1);
			setHeight(h1);
		} else {
			/* use the edge data */
			w1 = getWidth();
			h1 = getHeight();
			offsetX = fromW/2 + w1/2 + w1/4;
			offsetY = fromH/2 + h1/2 + h1/4;
			x1 = fromX - offsetX;
			y1 = fromY - offsetY;
			/* Teste ob die Loop haengt */
			int difX = 0;
			int difY = 0;
			if ((x1 + w1) <= (fromX - fromW/2)) {
				difX = (fromX - fromW/2) - (x1 + w1);
				x1 = x1 + difX + 5;
			} else if (x1 >= (fromX + fromW/2)) {
				difX = x1 - (fromX + fromW/2);
				x1 = x1 - difX - 5;
			}
			if ((y1 + h1) <= (fromY - fromH/2)) {
				difY = (fromY - fromH/2) - (y1 + h1);
				y1 = y1 + difY + 5;
			} else if (y1 >= (fromY + fromH/2)) {
				difY = y1 - (fromY + fromH/2);
				y1 = y1 - difY - 5;
			}
			/* Teste ob die Loop ueberdeckt ist */
			Loop tLoop = new Loop(x1, y1, w1, h1);
			if (!tLoop.outside(((EdNode) this.from).toRectangle(), tLoop.anch1,
					tLoop.anch2, tLoop.anch3, tLoop.anch4)) {
				x1 = fromX - fromW/2 - w1/2 - w1/4;
				y1 = fromY - fromH/2 - h1/2 - h1/4;
			}
		}
		/* create a new loop of this */
		Loop loop = new Loop(x1, y1, w1, h1);
		/* set position of arc */
		setXY(x1, y1);
		/* set width, height */
		setWidth(w1);
		setHeight(h1);
		loop.setColor(g.getColor());
		int sh = getShape();
		switch (sh) {
		case EditorConstants.SOLID:
			loop.drawColorSolidLoop(g);
			break;
		case EditorConstants.DOT:
			loop.drawColorDotLoop(g);
			break;
		case EditorConstants.DASH:
			loop.drawColorDashLoop(g);
			break;
		default:
			break;
		}

		if (this.elemOfTG) {
			// Edges arrow and Multiplicity of edge target
			Arrow arrow = new Arrow(this.itsScale, loop.anch4.x, loop.anch4.y,
						loop.anch3.x, loop.anch3.y,
						(loop.anch3.x - (fromX - fromW/2)) * 2,
						(loop.anch3.y - (fromY - fromH/2)) * 2,
						0);				
			if (this.directed) { 
				arrow.draw(g);
			}
			if (arrow.getRightEnd() != null) {
				drawMultiplicity(g, "target", arrow.getRightEnd(), this.eType
						.getBasisType().getTargetMin(
								this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()), 
								this.eType.getBasisType().getTargetMax(
										this.bArc.getSource().getType(),
										this.bArc.getTarget().getType()));
			}
			Arrow backArrow = new Arrow(this.itsScale, loop.anch2.x, loop.anch2.y,
					loop.anch3.x, loop.anch3.y,
					(loop.anch3.x - (fromX - fromW/2)) * 2,
					(loop.anch3.y - (fromY - fromH/2)) * 2,
					0);
			if (backArrow.getLeftEnd() != null) {
				Point p = new Point(backArrow.getLeftEnd().x, backArrow.getLeftEnd().y - 10);
				drawMultiplicity(g, "source", p, this.eType.getBasisType()
						.getSourceMin(this.bArc.getSource().getType(),
								this.bArc.getTarget().getType()), 
								this.eType.getBasisType().getSourceMax(
										this.bArc.getSource().getType(),
										this.bArc.getTarget().getType()));
			}
		}
		else { // plain graph
			if (this.directed) {
				int headsize = (isCritical() && (this.criticalStyle == 1)) ? 17: 0;
				Arrow arrow = new Arrow(this.itsScale, loop.anch4.x, loop.anch4.y,
							loop.anch3.x, loop.anch3.y,
							(loop.anch3.x - (fromX - fromW/2)) * 2,
							(loop.anch3.y - (fromY - fromH/2)) * 2,
							headsize);
				arrow.draw(g);
			}
		}

		/* Attribute Text */
		if (withText) {
			g.setStroke(EditorConstants.defaultStroke);
			// save the old color
//			Color lastColor = g.getColor();
			
			this.textLocation.x = x1 + this.textOffset.x;
			this.textLocation.y = y1 + this.textOffset.y;
			drawText(g, this.textLocation.x, this.textLocation.y);

//			g.setPaint(lastColor);
		}
	}

	private void drawBackgroundLoop(Graphics grs) {
		Graphics2D g = (Graphics2D) grs;
		g.setStroke(new BasicStroke(5.0f));
		int fromWidth = this.from.getWidth();
		int fromHeight = this.from.getHeight();
		/* set the edge data for first time */
		int w1 = 0, h1 = 0, x1 = 0, y1 = 0;
//		int w2 = 0, h2 = 0, x2 = 0, y2 = 0;
//		int offsetX = 0, offsetY = 0;
		if (getWidth() == getHeight() && getHeight() == 0) {
			w1 = getWidthOfLoop();
			h1 = w1;
			x1 = this.from.getX() - (fromWidth/2 + w1);
			y1 = this.from.getY() - (fromHeight/2 + h1);
		} else {
			/* use the edge data */
			w1 = getWidth();
			h1 = getHeight();
			x1 = this.from.getX() - (fromWidth/2 + w1);
			y1 = this.from.getY() - (fromHeight/2 + h1);
		}
		/* create a new loop of this */
		Loop loop = new Loop(x1, y1, w1, h1);
		loop.setColor(g.getColor());
		loop.drawColorSolidLoop(g);
		g.setStroke(EditorConstants.defaultStroke);
	}

	private void eraseArcAsLoop(Graphics grs, boolean withText) {
		// bilde Poligon mit Color.white
		Graphics2D g = (Graphics2D) grs;
		g.fillRect(getX(), getY(), getWidth() + 2, getHeight() + 5);
		if (withText) {
			Rectangle r = getTextRectangle(g.getFontMetrics());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}

	private void drawText(Graphics grs, int X, int Y) {
		Graphics2D g = (Graphics2D) grs;
		boolean underlined = false;
		int tx = X;
		if (tx <= 0)
			tx = 2;
		int ty = Y;
		if (ty <= 0)
			ty = 2;

		FontMetrics fm = g.getFontMetrics();
		/* Typnamen anzeigen */
		int tw = getTextWidth(fm); // (int)textSize.getWidth();
		String typeStr = getTypeString();
		int ty1 = ty + fm.getAscent();
		g.drawString(typeStr, tx, ty1);

		if ((g.getFont().getSize() < 8)
				|| !this.attrVisible)
			return;

		/* Attribute anzeigen */
		Vector<Vector<String>> attrs = getAttributes();
		if (attrs != null && !attrs.isEmpty()) {
			for (int i = 0; i < attrs.size(); i++) {
				Vector<String> attr = attrs.elementAt(i);
				if (!this.elemOfTG && (attr.elementAt(2).length() != 0)) {
					String attrStr = attr.elementAt(1);
					attrStr = attr.elementAt(1) + "=";
					attrStr = attrStr + attr.elementAt(2);
					if (!underlined) {
						ty = ty + fm.getHeight();
						g.drawLine(tx, ty, tx + tw, ty);
						ty = ty + 2;
						underlined = true;
					}
					ty1 = ty + fm.getAscent();
					g.drawString(attrStr, tx, ty1);
					ty = ty + fm.getHeight();
				} else if (this.elemOfTG && (attr.elementAt(1) != null)) {					
					String attrStr = attr.elementAt(0);
					attrStr = attrStr + "  ";
					attrStr = attrStr + attr.elementAt(1);
//					 Type graph: default attr value 
					if (attr.elementAt(2).length() != 0) {
						attrStr = attrStr + "=" + attr.elementAt(2);
					}
					if (!underlined) {
						ty = ty + fm.getHeight();
						g.drawLine(tx, ty, tx + tw, ty);
						ty = ty + 2;
						underlined = true;
					}
					ty1 = ty + fm.getAscent();
					g.drawString(attrStr, tx, ty1);
					ty = ty + fm.getHeight();
				}
			}
		}
	}

	private void drawMultiplicity(Graphics grs, String key, Point p, int min,
			int max) {
		Graphics2D g = (Graphics2D) grs;
		String s = "";
		if (min != -1) {
			s = s.concat(String.valueOf(min));
			s = s.concat("..");
			if (max == -1)
				s = s.concat("*");
		} else { // min == -1
			if (max != -1)
				s = s.concat("0..");
			else
				s = "*";
		}
		if (max != -1) {
			if (min != max)
				s = s.concat(String.valueOf(max));
			else
				s = String.valueOf(max);
		}
		int w1 = g.getFontMetrics().stringWidth(s);
		int h1 = g.getFontMetrics().getHeight();
		if (key.equals("source")) {
			if (this.srcMultiplicityOffset.x == 0 && this.srcMultiplicityOffset.y == 0) {
				if (isLine())
					this.srcMultiplicityOffset.x = -w1;
				else
					this.srcMultiplicityOffset.x = +5;
				this.srcMultiplicityOffset.y = h1;
			}
			this.srcMultiplicityLocation.x = p.x;
			this.srcMultiplicityLocation.y = p.y;
			this.srcMultiplicitySize.setSize(new Dimension(w1, h1));
			g.drawString(s,
					this.srcMultiplicityLocation.x + this.srcMultiplicityOffset.x,
					this.srcMultiplicityLocation.y + this.srcMultiplicityOffset.y);
		} else if (key.equals("target")) {
			if (this.trgMultiplicityOffset.x == 0 && this.trgMultiplicityOffset.y == 0) {
				this.trgMultiplicityOffset.x = -w1;
				this.trgMultiplicityOffset.y = h1/2;
			}
			this.trgMultiplicityLocation.x = p.x;
			this.trgMultiplicityLocation.y = p.y;
			this.trgMultiplicitySize.setSize(new Dimension(w1, h1));
			g.drawString(s,
					this.trgMultiplicityLocation.x + this.trgMultiplicityOffset.x,
					this.trgMultiplicityLocation.y + this.trgMultiplicityOffset.y);
		}

	}

	public void drawNameAttrOnly(Graphics grs) {
//		if (!this.isVisible()) {
		if (!this.visible) {
			return;
		}
		
		this.criticalStyle = this.eGraph.criticalStyle;
		
		Graphics2D g = (Graphics2D) grs;
		
		if (this.eType.filled) {
			g.setStroke(EditorConstants.boldStroke);
		} else {
			g.setStroke(EditorConstants.defaultStroke);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(this.getColor());
		if (!this.from.equals(this.to)) {
			// drawArcAsLine(g, scale, true);
			boolean needAnchorTuning = true;
			/* set the edge data */
			int x1 = this.from.getX();
			int y1 = this.from.getY();
			int x2 = this.to.getX();
			int y2 = this.to.getY();
			Line line = toLine();
			if ((this.anchor != null)) {
				line.setAnchor(new Point(this.anchor.x, this.anchor.y));
				needAnchorTuning = false;
			}
			/* set XY of move position of arc */
			setXY(line.getAnchor().x, line.getAnchor().y);
			/* set width, height */
			if (getWidth() == getHeight() && getHeight() == 0) {
				setWidth(14);
				setHeight(14);
			}
			/*
			 * if(getType().getBasisType().getName().equals("c"))
			 * line.setColor(conflictColor); else
			 * if(getType().getBasisType().getName().equals("d"))
			 * line.setColor(dependencyColor);
			 */
			line.setColor(this.getColor());

			int sh = getShape();
			switch (sh) {
			case EditorConstants.SOLID:
				line.drawColorSolidLine(g);
				break;
			case EditorConstants.DOT:
				line.drawColorDotLine(g);
				break;
			case EditorConstants.DASH:
				line.drawColorDashLine(g);
				break;
			default:
				break;
			}
			/* Head of edge */
			int headsize = (isCritical() && (this.criticalStyle == 1)) ? 17: 0;
			Arrow arrow = new Arrow(this.itsScale, line.getAnchor().x,
					line.getAnchor().y, x2, y2, this.to.getWidth(), this.to.getHeight(), headsize);
			if (isDirected())
				arrow.draw(g);

			Arrow tmpBackArrow = new Arrow(this.itsScale, line.getAnchor().x, line
					.getAnchor().y, x1, y1, this.from.getWidth(), this.from.getHeight(), headsize);
			if (needAnchorTuning) {
				// System.out.println("EdArc: do arc tuning");
				Point beg = tmpBackArrow.getHeadEnd();
				Point end = arrow.getHeadEnd();
				if (beg != null && end != null) {
					int anchX = beg.x + (end.x - beg.x)/2;
					int anchY = beg.y + (end.y - beg.y)/2;
					line.setAnchor(new Point(anchX, anchY));
					setXY(anchX, anchY);
				}
			}

			// g.setPaint(this.getColor());
			this.textLocation.x = getX() + this.textOffset.x;
			this.textLocation.y = getY() + this.textOffset.y;
			showNameAttrOnly(g, this.textLocation.x, this.textLocation.y);
		} else {
			// drawArcAsLoop(g, scale, true);
			int fromWidth = this.from.getWidth();
			int fromHeight = this.from.getHeight();
			/* set the edge data for first time */
			int w1 = 0, h1 = 0, x1 = 0, y1 = 0;
//			int w2 = 0, h2 = 0, x2 = 0, y2 = 0;
			int offsetX = 0, offsetY = 0;
			if (getWidth() == getHeight() && getHeight() == 0) {
				w1 = getWidthOfLoop();
				h1 = w1;
				offsetX = fromWidth/2 + w1/2 + w1/4;
				offsetY = fromHeight/2 + h1/2 + h1/4;
				x1 = this.from.getX() - offsetX;
				y1 = this.from.getY() - offsetY;
				/* set position of arc */
				setXY(x1, y1);
				/* set width, height */
				setWidth(w1);
				setHeight(h1);
			} else {
				/* use the edge data */
				w1 = getWidth();
				h1 = getHeight();
				offsetX = fromWidth/2 + w1/2 + w1/4;
				offsetY = fromHeight/2 + h1/2 + h1/4;
				x1 = this.from.getX() - offsetX;
				y1 = this.from.getY() - offsetY;
				/* Teste ob die Schlinge haengt */
				int difX = 0;
				int difY = 0;
				if ((x1 + w1) <= (this.from.getX() - fromWidth/2)) {
					difX = (this.from.getX() - fromWidth/2) - (x1 + w1);
					x1 = x1 + difX + 5;
				} else if (x1 >= (this.from.getX() + fromWidth/2)) {
					difX = x1 - (this.from.getX() + fromWidth/2);
					x1 = x1 - difX - 5;
				}
				if ((y1 + h1) <= (this.from.getY() - fromHeight/2)) {
					difY = (this.from.getY() - fromHeight/2) - (y1 + h1);
					y1 = y1 + difY + 5;
				} else if (y1 >= (this.from.getY() + fromHeight/2)) {
					difY = y1 - (this.from.getY() + fromHeight/2);
					y1 = y1 - difY - 5;
				}
				/* Teste ob die Schlinge ueberdeckt ist */
				Loop tLoop = new Loop(x1, y1, w1, h1);
				if (!tLoop.outside(((EdNode) this.from).toRectangle(), tLoop.anch1,
						tLoop.anch2, tLoop.anch3, tLoop.anch4)) {
					x1 = this.from.getX() - fromWidth/2 - w1/2 - w1/4;
					y1 = this.from.getY() - fromHeight/2 - h1/2 - h1/4;
				}
			}
			/* create a new loop of this */
			Loop loop = new Loop(x1, y1, w1, h1);
			/* set position of arc */
			setXY(x1, y1);
			/* set width, height */
			setWidth(w1);
			setHeight(h1);
			/*
			 * if(getType().getBasisType().getName().equals("c"))
			 * loop.setColor(conflictColor); else
			 * if(getType().getBasisType().getName().equals("d"))
			 * loop.setColor(dependencyColor);
			 */
			loop.setColor(this.getColor());
			int sh = getShape();
			switch (sh) {
			case EditorConstants.SOLID:
				loop.drawColorSolidLoop(g);
				break;
			case EditorConstants.DOT:
				loop.drawColorDotLoop(g);
				break;
			case EditorConstants.DASH:
				loop.drawColorDashLoop(g);
				break;
			default:
				break;
			}

			/* Head of edge */
			int headsize = (isCritical() && (this.criticalStyle == 1)) ? 17: 0;
			Arrow arrow = new Arrow(this.itsScale, loop.anch4.x, loop.anch4.y,
					loop.anch3.x, loop.anch3.y,
					(loop.anch3.x - (this.from.getX() - fromWidth/2)) * 2,
					(loop.anch3.y - (this.from.getY() - fromHeight/2)) * 2,
					headsize);
			arrow.draw(g);

			// g.setPaint(this.getColor());
			this.textLocation.x = x1 + this.textOffset.x;
			this.textLocation.y = y1 + this.textOffset.y;
			showNameAttrOnly(g, this.textLocation.x, this.textLocation.y);
		}
		g.setPaint(this.getColor());
		g.setStroke(EditorConstants.defaultStroke);
	}

	private void showNameAttrOnly(Graphics grs, int X, int Y) {
		Graphics2D g = (Graphics2D) grs;
		int tx = X;
		if (tx <= 0)
			tx = 2;
		int ty = Y;
		if (ty <= 0)
			ty = 2;
		FontMetrics fm = g.getFontMetrics();
		/* Attribute anzeigen */
		Vector<Vector<String>> attrs = getAttributes();
		if (attrs != null && !attrs.isEmpty()) {
			for (int i = 0; i < attrs.size(); i++) {
				Vector<String> attr =attrs.elementAt(i);
				if (attr.elementAt(2).length() != 0) {
					if (attr.elementAt(1).equals("name")) {
						String attrStr = attr.elementAt(2);
						if (!attrStr.equals("\"\"")) {
							int ty1 = ty + fm.getAscent();
							g.drawString(attrStr, tx, ty1);
						}
						return;
					}
				}

			}
		}
	}

	public void XwriteObject(XMLHelper xmlh) {
		// wegen zooming statt x,y, textOffset origX ... nehmen
		// was ist mit Loop???
		if (xmlh.openObject(this.bArc, this)) {
			xmlh.openSubTag("EdgeLayout");
			
			int outX = (int) (this.textOffset.x / this.itsScale);
			int outY = (int) (this.textOffset.y / this.itsScale);
			xmlh.addAttr("textOffsetX", outX);
			xmlh.addAttr("textOffsetY", outY);
			if (isLine()) {
				if (this.hasDefaultAnchor) {
					xmlh.addAttr("bendX", 0);
					xmlh.addAttr("bendY", 0);
				} else {
					outX = (int) (this.x / this.itsScale);
					outY = (int) (this.y / this.itsScale);
					xmlh.addAttr("bendX", outX);
					xmlh.addAttr("bendY", outY);
				}
			} else { // is Loop
				outX = (int) (this.x / this.itsScale);
				outY = (int) (this.y / this.itsScale);
				xmlh.addAttr("bendX", outX);
				xmlh.addAttr("bendY", outY);
				outX = (int) (this.w / this.itsScale);
				outY = (int) (this.h / this.itsScale);
				xmlh.addAttr("loopW", outX);
				xmlh.addAttr("loopH", outY);
			}
			if (isElementOfTypeGraph()) {
				outX = (int) (this.srcMultiplicityOffset.x / this.itsScale);
				outY = (int) (this.srcMultiplicityOffset.y / this.itsScale);
				xmlh.addAttr("sourceMultiplicityOffsetX", outX);
				xmlh.addAttr("sourceMultiplicityOffsetY", outY);
				outX = (int) (this.trgMultiplicityOffset.x / this.itsScale);
				outY = (int) (this.trgMultiplicityOffset.y / this.itsScale);
				xmlh.addAttr("targetMultiplicityOffsetX", outX);
				xmlh.addAttr("targetMultiplicityOffsetY", outY);
			}
			
			xmlh.close();
			// LayoutArc speichern:
			if (this.lArc != null) {
				xmlh.addObject("", this.lArc, true);
			}

			xmlh.close();
		}
	}

	public void XreadObject(XMLHelper xmlh) {
		int loopW = 0;
		int loopH = 0;
		xmlh.peekObject(this.bArc, this);
		if (xmlh.readSubTag("EdgeLayout")) {
			this.textOffset = new Point();
			String s = xmlh.readAttr("textOffsetX");
			if (s.length() == 0)
				this.textOffset.x = 0;
			else
				this.textOffset.x = (new Integer(s)).intValue();

			s = xmlh.readAttr("textOffsetY");
			if (s.length() == 0)
				this.textOffset.y = 0;
			else
				this.textOffset.y = (new Integer(s)).intValue();

			s = xmlh.readAttr("bendX");
			if ((s.length() == 0) || (s.equals("0"))) {
				this.x = 0;
			}
			else {
				this.x = (new Integer(s)).intValue();
				if (this.x < 0) {
					this.x = 0;
				}
//				else if (x > 1600) {
//					x = (int) (Math.random()*1000);
//				} 
			}

			s = xmlh.readAttr("bendY");
			if ((s.length() == 0) || (s.equals("0"))) {
				this.y = 0;
			}
			else {
				this.y = (new Integer(s)).intValue();
				if (this.y < 0) {
					this.y = 0;
				}
//				else if (y > 1000) {
//					y = (int) (Math.random()*1000);
//				} 
			}

			if (!isLine()) { // is Loop
				s = xmlh.readAttr("loopW");
				if (s.length() == 0)
					loopW = 0;
				else
					loopW = (new Integer(s)).intValue();

				s = xmlh.readAttr("loopH");
				if (s.length() == 0)
					loopH = 0;
				else
					loopH = (new Integer(s)).intValue();
			}

			if (this.elemOfTG) {
				createMultiplicityVars();
				s = xmlh.readAttr("sourceMultiplicityOffsetX");
				if (s.length() == 0) 
					this.srcMultiplicityOffset.x = 0;
				else
					this.srcMultiplicityOffset.x = (new Integer(s)).intValue();
	
				s = xmlh.readAttr("sourceMultiplicityOffsetY");
				if (s.length() == 0)
					this.srcMultiplicityOffset.y = 0;
				else
					this.srcMultiplicityOffset.y = (new Integer(s)).intValue();
	
				s = xmlh.readAttr("targetMultiplicityOffsetX");
				if (s.length() == 0)
					this.trgMultiplicityOffset.x = 0;
				else
					this.trgMultiplicityOffset.x = (new Integer(s)).intValue();
	
				s = xmlh.readAttr("targetMultiplicityOffsetY");
				if (s.length() == 0)
					this.trgMultiplicityOffset.y = 0;
				else
					this.trgMultiplicityOffset.y = (new Integer(s)).intValue();
			}
			
			xmlh.close();

			// set anchor of edge
			if (isLine()) {
				Line line = toLine();
				Point p = line.getAnchor();
				if (((this.x == 0) && (this.y == 0)) 
						|| ((p.x == this.x) && (p.y == this.y))) {
					this.hasDefaultAnchor = true;
				} else {
					this.hasDefaultAnchor = false;
					setAnchor(new Point(this.x, this.y));
				}
			} else { /* a loop */
				if (xmlh.getDocumentVersion().equals("1.0")) {
					if ((loopW != 0) && (loopH != 0)) {
						this.anchor = new Point(this.x, this.y);
						this.anchorID = Loop.UPPER_LEFT;
						this.hasDefaultAnchor = false;
						setXY(this.x, this.y);
						setWidth(loopW);
						setHeight(loopH);
					} else {
						setAnchor(new Point(this.x, this.y));
						this.hasDefaultAnchor = true;
					}
				} else {
					setAnchor(new Point(this.x, this.y));
					this.hasDefaultAnchor = true;
				}
			}
		}
		// layoutArc einlesen:
		xmlh.enrichObject(this.lArc);

		xmlh.close();

		this.attrVisible = true;
		this.attrChanged = false;
	}

}
// $Log: EdArc.java,v $
// Revision 1.60  2010/11/14 12:59:11  olga
// tuning
//
// Revision 1.59  2010/11/12 15:14:53  olga
// tuning
//
// Revision 1.58  2010/11/10 01:14:49  olga
// tuning
//
// Revision 1.57  2010/11/09 16:41:25  olga
// tuning
//
// Revision 1.56  2010/08/25 00:33:06  olga
// tuning
//
// Revision 1.55  2010/03/19 14:46:49  olga
// tuning
//
// Revision 1.54  2010/03/18 18:17:28  olga
// tuning
//
// Revision 1.53  2010/03/17 21:38:36  olga
// tuning
//
// Revision 1.52  2010/03/10 14:44:49  olga
// make identical rule - bug fixed
//
// Revision 1.51  2010/03/08 15:40:04  olga
// code optimizing
//
// Revision 1.50  2010/02/22 15:07:44  olga
// code optimizing
//
// Revision 1.49  2010/01/31 16:42:35  olga
// tuning
//
// Revision 1.48  2009/10/14 07:52:29  olga
// GUI bug fixed
//
// Revision 1.47  2009/06/30 09:50:19  olga
// agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
// agg.xt_basis.Node, Arc: changed: save, load the object name
// agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
//
// workaround of Applicability of Rule Sequences and Object Flow
//
// Revision 1.46  2009/03/30 13:50:49  olga
// some tests
//
// Revision 1.45  2009/03/25 15:19:15  olga
// code tuning
//
// Revision 1.44  2008/12/17 09:37:42  olga
// Import of TypeGraph from  grammar (.ggx) - bug fixed
//
// Revision 1.43  2008/11/24 11:35:12  olga
// GUI tuning
//
// Revision 1.42  2008/11/06 08:45:36  olga
// Graph layout is extended by Zest Graph Layout ( eclipse zest plugin)
//
// Revision 1.41  2008/10/29 09:04:05  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.40  2008/09/11 15:10:53  olga
// Tests only
//
// Revision 1.39  2008/09/11 09:22:25  olga
// Some changes in CPA: new computing of conflicts after an option changed,
// Graph layout of overlapping graphs
//
// Revision 1.38  2008/09/04 07:48:42  olga
// GUI extension: hide nodes, edges
//
// Revision 1.37  2008/07/21 10:03:28  olga
// Code tuning
//
// Revision 1.36  2008/07/17 15:51:50  olga
// GraphEditor - graph scaling tuning
//
// Revision 1.35  2008/07/14 07:35:48  olga
// Applicability of RS - new option added, more tuning
// Node animation - new animation parameter added,
// Undo edit manager - possibility to disable it when graph transformation
// because it costs much more time and memory
//
// Revision 1.34  2008/07/02 17:14:36  olga
// Code tuning
//
// Revision 1.33  2008/06/26 14:18:47  olga
// Graph visualization tuning
//
// Revision 1.32  2008/04/21 09:32:19  olga
// Visualization of inheritance edge - bugs fixed
// Graph layout tuning
//
// Revision 1.31  2008/04/17 10:11:07  olga
// Undo, redo edit and graph layout tuning,
//
// Revision 1.30  2008/04/11 13:29:05  olga
// Memory usage - tuning
//
// Revision 1.29  2008/04/10 10:53:14  olga
// Draw graphics tuning
//
// Revision 1.28  2008/04/07 09:36:50  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.27  2008/01/23 15:03:18  olga
// Tuning of usability of the gragra editor.
//
// Revision 1.26  2007/11/19 08:48:39  olga
// Some GUI usability mistakes fixed.
// Default values in node/edge of a type graph implemented.
// Code tuning.
//
// Revision 1.25  2007/11/05 09:18:16  olga
// code tuning
//
// Revision 1.24  2007/11/01 09:58:11  olga
// Code refactoring: generic types- done
//
// Revision 1.23  2007/10/11 08:05:04  olga
// Enumeration typing
//
// Revision 1.22  2007/09/24 09:42:33  olga
// AGG transformation engine tuning
//
// Revision 1.21  2007/09/10 13:05:16  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.20 2007/06/18 08:15:59 olga
// New extentions by drawing edge.
//
// Revision 1.19 2007/06/13 08:32:44 olga
// Update: V161
//
// Revision 1.18 2007/04/19 07:52:35 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.17 2007/04/11 10:03:35 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.16 2007/03/28 10:00:24 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.15 2007/02/19 09:11:00 olga
// Bug during loading file fixed.
// Type editor tuning
//
// Revision 1.14 2007/01/22 08:28:29 olga
// GUI bugs fixed
//
// Revision 1.13 2006/11/01 11:17:29 olga
// Optimized agg sources of CSP algorithm, match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.12 2006/08/09 16:09:09 olga
// Tuning evolutionary layouter
// API docu
//
// Revision 1.11 2006/08/09 07:42:18 olga
// API docu
//
// Revision 1.10 2006/08/02 09:00:57 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.9 2006/05/29 07:59:41 olga
// GUI, undo delete - tuning.
//
// Revision 1.8 2006/05/17 06:57:16 olga
// CPA graph: set conflict/dependency edge style
//
// Revision 1.7 2006/04/03 08:57:50 olga
// New: Import Type Graph
// and some bugs fixed
//
// Revision 1.6 2005/12/21 14:49:20 olga
// GUI tuning
//
// Revision 1.5 2005/11/07 09:38:08 olga
// Null pointer during retype attr. member fixed.
//
// Revision 1.4 2005/10/10 08:05:16 olga
// Critical Pair GUI and CPA graph
//
// Revision 1.3 2005/09/26 16:41:20 olga
// CPA graph, CPs - visualization
//
// Revision 1.2 2005/09/19 09:12:14 olga
// CPA GUI tuning
//
// Revision 1.1 2005/08/25 11:56:56 enrico
// *** empty log message ***
//
// Revision 1.3 2005/07/11 09:30:20 olga
// This is test version AGG V1.2.8alfa .
// What is new:
// - saving rule option <disabled>
// - setting trigger rule for layer
// - display attr. conditions in gragra tree view
// - CPA algorithm <dependencies>
// - creating and display CPA graph with conflicts and/or dependencies
// based on (.cpx) file
//
// Revision 1.2.2.1 2005/07/04 11:41:37 enrico
// basic support for inheritance
//
// Revision 1.2 2005/06/20 13:37:04 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.38 2005/03/03 13:48:42 olga
// - Match with NACs and attr. conditions with mixed variables - error corrected
// - save/load class packages written by user
// - PACs : creating T-equivalents - improved
// - save/load matches of the rules (only one match of a rule)
// - more friendly graph/rule editor GUI
// - more syntactical checks in attr. editor
//
// Revision 1.37 2005/02/14 09:27:01 olga
// -PAC;
// -GUI, layered graph transformation anzeigen;
// -CPs.
//
// Revision 1.36 2005/01/28 14:02:32 olga
// -Fehlerbehandlung beim Typgraph check
// -Erweiterung CP GUI / CP Menu
// -Fehlerbehandlung mit identification option
// -Fehlerbehandlung bei Rule PAC
//
// Revision 1.35 2004/12/20 14:53:48 olga
// Changes because of matching optimisation.
//
// Revision 1.34 2004/11/15 11:24:45 olga
// Neue Optionen fuer Transformation;
// verbesserter default Graphlayout;
// Close GraGra mit Abfrage wenn was geaendert wurde statt Delete GraGra
//
// Revision 1.33 2004/10/25 14:24:37 olga
// Fehlerbehandlung bei CPs und Aenderungen im zusammenhang mit
// termination-Modul
// in AGG
//
// Revision 1.32 2004/07/16 13:02:02 olga
// TypeGraph OK
//
// Revision 1.31 2004/07/15 11:13:10 olga
// CPs letzter Schliff
//
// Revision 1.30 2004/06/14 12:34:19 olga
// CP Analyse and Transformation
//
// Revision 1.29 2004/06/09 11:32:54 olga
// Attribute-Eingebe/Bedingungen : NAC kann jetzt eigene Variablen und
// Bedingungen
// haben.
// CP Berechnung korregiert.
//
// Revision 1.28 2004/05/26 16:17:40 olga
// Observer / observable
//
// Revision 1.27 2004/05/13 17:54:09 olga
// Fehlerbehandlung
//
// Revision 1.26 2004/04/28 12:46:38 olga
// test CSP
//
// Revision 1.25 2004/04/15 10:49:47 olga
// Kommentare
//
// Revision 1.24 2004/02/25 11:45:04 olga
// Rule Post Appl Conditions getestet.
//
// Revision 1.23 2004/01/29 13:38:18 olga
// Layout und Types
//
// Revision 1.22 2003/10/16 08:25:12 olga
// Copy rule implementiert
//
// Revision 1.21 2003/08/04 14:40:43 olga
// Loop- behandlung
//
// Revision 1.20 2003/06/12 16:03:50 olga
// Layout
//
// Revision 1.19 2003/04/10 09:05:23 olga
// Aenderungen wegen serializable Ausgabe
//
// Revision 1.18 2003/04/10 08:50:33 olga
// Tests mit serializable Ausgabe
//
// Revision 1.17 2003/03/27 09:28:53 olga
// Layout- anpassung
//
// Revision 1.16 2003/03/17 15:32:59 olga
// Kleine Korrektur von Xread, Xwrite
//
// Revision 1.15 2003/03/05 18:24:23 komm
// sorted/optimized import statements
//
// Revision 1.14 2002/12/18 14:15:14 olga
//
// Layout
//
// Revision 1.13 2002/12/18 09:15:54 olga
// Layout - Anpassung
//
// Revision 1.12 2002/12/12 09:22:05 olga
// Kantengraphik verbessert (Loops)
//
// Revision 1.11 2002/12/09 14:32:32 olga
// Loop-Anzeige verbessert.
//
// Revision 1.10 2002/12/05 14:31:19 olga
// Speichern von Multiplicity offset
//
// Revision 1.9 2002/12/05 13:34:50 olga
// Speichern / Lesen und Anzeigen von Multiplicity.
//
// Revision 1.8 2002/12/02 09:59:44 komm
// each source/target combination has now its own multiplicity constraints
//
// Revision 1.7 2002/11/25 15:03:51 olga
// Arbeit an den Typen.
//
// Revision 1.6 2002/11/14 14:29:39 olga
// Anzeige von Multiplicity -- ein Versuch.
//
// Revision 1.5 2002/11/07 15:58:26 olga
// Fehlerbehandlung
//
// Revision 1.4 2002/10/02 18:30:42 olga
// XXX
//
// Revision 1.3 2002/09/30 10:06:01 komm
// ErrorMode moved to EdGraphObject
//
// Revision 1.2 2002/09/23 12:24:05 komm
// added type graph in xt_basis, editor and GUI
//
// Revision 1.1.1.1 2002/07/11 12:17:07 olga
// Imported sources
//
// Revision 1.26 2001/07/04 10:44:16 olga
// Neue Methoden in EdGraph um eine Selektion als EdGraph kopieren
// und zu einem EdGraph addieren zu koennen.
//
// Revision 1.25 2001/05/14 15:45:15 olga
// Anzeige von Loop am Kreis oder Oval verbessert.
//
// Revision 1.24 2001/05/14 12:00:39 olga
// Graph Layout und Graphobject Layout optimiert.
//
// Revision 1.23 2001/04/11 14:57:36 olga
// Arbeit an dem Layout.
//
// Revision 1.22 2001/03/15 17:52:31 olga
// *** empty log message ***
//
// Revision 1.21 2001/03/15 17:03:19 olga
// Layout korrektur.
//
// Revision 1.20 2001/03/08 10:53:18 olga
// Das ist Stand nach der AGG GUI Reimplementierung.
//
// Revision 1.19 2000/12/21 09:48:50 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.18 2000/12/07 14:23:35 matzmich
// XML-Kram
// Man beachte: xerces (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird
// jetzt im CLASSPATH benoetigt.
//
// Revision 1.17.6.3 2000/12/06 10:13:20 olga
// Rendering hints in Graphics2D gesetzt.
//
// Revision 1.17.6.2 2000/11/09 17:54:41 olga
// Fehlerbeseitigt im TypeEditor und bei den Kanten.
//
// Revision 1.17.6.1 2000/11/06 09:32:31 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.17 2000/03/03 11:41:16 shultzke
// *** empty log message ***
//
// Revision 1.14 1999/10/07 14:20:19 olga
// *** empty log message ***
//
// Revision 1.13 1999/10/06 14:28:29 olga
// *** empty log message ***
//
// Revision 1.12 1999/09/23 13:58:37 olga
// *** empty log message ***
//
// Revision 1.11 1999/09/23 09:18:04 olga
// Angepasst auf eifarbige Line/Loop.
//
// Revision 1.10 1999/09/20 10:58:45 olga
// *** empty log message ***
//
// Revision 1.9 1999/09/20 10:43:20 olga
// *** empty log message ***
//
// Revision 1.8 1999/09/16 13:56:16 olga
// *** empty log message ***
//
// Revision 1.7 1999/09/15 12:34:43 olga
// *** empty log message ***
//
// Revision 1.6 1999/08/03 13:22:44 shultzke
// das Laden und Speichern der Views der Attribute
// wurde in read- und writeObject von EdNode
// und EdArc gelegt. Es ist im Moment noch etwas
// unsauber. Aber es funktioniert.
//
// Revision 1.5 1999/07/28 11:23:26 shultzke
// Events werden richtig benutzt
// Konstruktoren verschoenert
//
// Revision 1.4 1999/07/26 10:24:55 shultzke
// Views koennen zwar benutzt werden. Sie werden
// aber noch nicht nach dem Laden rekonstruiert.
//
