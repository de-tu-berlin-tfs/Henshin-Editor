package agg.editor.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import agg.attribute.view.AttrViewSetting;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphPanel;
import agg.xt_basis.GraphObject;

/**
 * An EdGraphObject specifies the common layout interface and implementations
 * for nodes and arcs. This abstract class is the superclass of EdNode and
 * EdArc.
 * 
 * @author $Author: olga $
 * @version $Id: EdGraphObject.java,v 1.35 2010/11/13 02:25:16 olga Exp $
 */
public abstract class EdGraphObject {

//	public final static int CRITICAL_GREEN = 0;	
//	public final static int CRITICAL_BLACK_BOLD = 1;
	
	protected int criticalStyle = 0;
	
	protected Integer itsUndoReprDataHC;
	
	protected EdType eType;

	protected EdGraph eGraph;

	protected int x;

	protected int y;

	protected int w;

	protected int h;

	double itsScale = 1.0;
	
	// contextUsage is used for undo and stores its hashcode(s)
	// as string like this - ":hashcode:hashcode:hashcode:"
	// it used from NodeReprData and ArcReprDate to store/restore node, edge
	// DO NOT REWRITE THIS!
	protected String contextUsage = "";

	protected boolean hasDefaultLayout;

	protected boolean elemOfTG; // is element of a TypeGraph

	/**
	 * true, if the graph object has (type) errors. if true the object will be
	 * marked.
	 */
	protected boolean errorMode;

	protected boolean visible = true;
	
	protected boolean attrVisible = true;

	protected Color backgroundColor = null; //Color.white;
	
	protected int myKey = 0;

	transient protected final Vector<String> marks;

	transient protected EdGraphObject myCopy;

	transient protected boolean selected, weakselected;
	
	transient protected boolean moved;

	transient protected GraphPanel myGraphPanel;

	transient protected boolean attrObserver;

	transient protected boolean attrChanged;

	protected AttrViewSetting view;

	transient protected boolean init;

	
	/** Creates a layout specified by the EdType eType */
	public EdGraphObject(EdType eType) {
		this.contextUsage = "";
		this.x = 0;
		this.y = 0;
		this.eType = eType;
//		if (this.eType != null) {
//			this.eType.addUser(this);
//		}
		this.marks = new Vector<String>();
	}

	public abstract void dispose();
	
	public EdGraph getContext() {
		return this.eGraph;
	}

	public void setContext(EdGraph g) {
		this.eGraph = g;
	}

	/** Returns the used object */
	public abstract GraphObject getBasisObject();

	/** Returns TRUE if the used object is a node */
	public abstract boolean isNode();

	/** Returns TRUE if the used object is a n arc */
	public abstract boolean isArc();

	/** Returns the parent object */
	public abstract EdNode getNode();

	/** Returns the parent object */
	public abstract EdArc getArc();

	public abstract void setCritical(boolean b);
	
	public abstract void setDrawingStyleOfCriticalObject(int criticalStyle);
	
	public abstract boolean isCritical();

	/** Returns the attributes */
	public abstract Vector<Vector<String>> getAttributes();

	public abstract void refreshAttributeInstance();

	public abstract void removeFromAttributeViewObserver();
	
	/** Sets a graph panel */
	public abstract void setGraphPanel(GraphPanel gp);

	/** Sets the attributes */
	public abstract Vector<Vector<String>> setAttributes(GraphObject obj);

	public abstract void drawGraphic(Graphics grs);
	
	
	/** Returns TRUE if this is attribute observer */
	public boolean isAttrObserver() {
		return this.attrObserver;
	}

	/** Sets this to attribute observer */
	public void setAttrObserver(boolean obs) {
		this.attrObserver = obs;
		this.init = true;
	}

	/** Returns my copy */
	public EdGraphObject getCopy() {
		return this.myCopy;
	}

	/** Returns my X position */
	public int getX() {
		return this.x;
	}

	/** Returns my Y position */
	public int getY() {
		return this.y;
	}

	/** Returns my width */
	public int getWidth() {
		return this.w;
	}

	/** Returns my height */
	public int getHeight() {
		return this.h;
	}

	/** Returns my layout type */
	public EdType getType() {
		return this.eType;
	}

	/** Returns the name of my layout type */
	public String getTypeName() {
		return this.eType.getTypeName();
	}

	/** Returns my key used by the morphism marking */
	public int getMyKey() {
		return this.myKey;
	}

	/** Returns my morphism mark */
	public String getMorphismMark() {
		StringBuffer markBuf = new StringBuffer();
		for (int i = 0; i < this.marks.size(); i++) {
			if (i > 0)
				markBuf.append(',');
			markBuf.append(this.marks.elementAt(i));
		}
		return markBuf.toString();
	}

	/** Returns the text height */
	public int getTextHeight(FontMetrics fm) {
		Vector<Vector<String>> attrs = getAttributes();
		int nn = 0;
		int h1 = 0;
		// die Hoehe einer Zeile
		if (fm == null)
			h1 = 17; // default
		else
			h1 = fm.getHeight();

		if ((fm != null && fm.getFont().getSize() < 8)
				|| !this.attrVisible)
			return h1;

		if (attrs != null) {
			nn = attrs.size();
			for (int i = 0; i < attrs.size(); i++) {
				Vector<String> attr = attrs.elementAt(i);
				if (!this.elemOfTG && (attr.elementAt(2).length() == 0))
					nn--;
				else if (this.elemOfTG && (attr.elementAt(1) == null))
					nn--;
			}
		}
		// die Hoehe aller Attribute
		int hght = h1 * nn;
		// gesamte Hoehe
		// if((getTypeString().length() != 0) || (isNode() && this.elemOfTG))
		hght = hght + h1;
		return hght;
	}

	/** Returns the text width */
	public int getTextWidth(FontMetrics fm) {
		int nn = 6; // default char width
		String typeStr = "";
		typeStr = getTypeString();
		if (isNode()) {
			if (getType().getBasisType().isAbstract()) {
				if (!typeStr.equals(""))
					typeStr = "{" + typeStr + "}";
				else
					typeStr = "{ }";
			}
			if (this.elemOfTG) 
				typeStr = typeStr + " " + ((EdNode) this).getMultiplicityString();
		}
		int wdth = 0;
		if (fm == null)
			wdth = nn * typeStr.length();
		else
			wdth = fm.stringWidth(typeStr);

		if ((fm != null && fm.getFont().getSize() < 8)
				|| !this.attrVisible)
			return wdth;

		Vector<Vector<String>> attrs = getAttributes();
		if (attrs != null) {
			for (int i = 0; i < attrs.size(); i++) {
				Vector<String> attr = attrs.elementAt(i);
				if (!this.elemOfTG && (attr.elementAt(2).length() != 0)) {
					String tstStr = attr.elementAt(1) + "="
							+ attr.elementAt(2);
					if (fm == null) {
						if ((nn * tstStr.length()) > wdth)
							wdth = nn * tstStr.length();
					} else if (fm.stringWidth(tstStr) > wdth)
						wdth = fm.stringWidth(tstStr);
				} else if (this.elemOfTG && (attr.elementAt(1) != null)) {										
					String tstStr = attr.elementAt(0) + "  "
							+ attr.elementAt(1);
					
//					 Type graph: default attr value 
					if (attr.elementAt(2).length() != 0) {
						tstStr = tstStr + "="  + attr.elementAt(2);
					}
					
					if (fm == null) {
						if ((nn * tstStr.length()) > wdth)
							wdth = nn * tstStr.length();
					} else if (fm.stringWidth(tstStr) > wdth)
						wdth = fm.stringWidth(tstStr);
				}
			}
		}
		return wdth;
	}

	/** Returns the type name with mapping mark if it exists. */
	protected String getTypeString() {
		String typeStr = this.eType.getBasisType().getStringRepr();
		if (!this.getBasisObject().getObjectName().equals("")) {
			typeStr = this.getBasisObject().getObjectName().concat(":").concat(typeStr);
		}
		
		if (getMorphismMark().length() != 0) {
			typeStr = getMorphismMark().concat(":").concat(typeStr);
		}
		return typeStr;
	}

	/** Returns my shape */
	public int getShape() {
		return this.eType.shape;
	}

	/** Returns my color */
	public Color getColor() {
		return this.eType.color;
	}

	/** Returns the color used by selecting */
	public Color getSelectColor() {
		return EditorConstants.selectColor;
	}

	/** Returns TRUE if i am selected */
	public boolean isSelected() {
		return this.selected;
	}
	
	/** Returns TRUE if i'm visible */
	public boolean isVisible() {
		return this.visible;
	}

	/** Returns TRUE if my attribute is visible */
	public boolean isAttributeVisible() {
		return this.visible;
	}
	
	/** Returns TRUE if i am an element of a type graph */
	public boolean isElementOfTypeGraph() {
		return this.elemOfTG;
	}

	/**
	 * Returns TRUE if my type is the same as the type specified by the
	 * EdGraphObject eObj
	 */
	public boolean hasSimilarType(EdGraphObject eObj) {
		if (this.eType.isParentOf(eObj.getType()))
			return true;
		
		return false;
	}

	/**
	 * Returns TRUE if the point specified by the int X, int Y is inside of
	 * myself
	 */
	public abstract boolean inside(int X, int Y);

	/**
	 * Returns the dimension of the overlapping from another layout specified by
	 * the EdGraphObject eObj
	 */
	public Dimension ifOverlapFrom(EdGraphObject eObj) {
		Point p1 = new Point(this.x - this.w / 2, this.y - this.h / 2);
		Point p2 = new Point(this.x + this.w / 2, this.y - this.h / 2);
		Point p3 = new Point(this.x + this.w / 2, this.y + this.h / 2);
		Point p4 = new Point(this.x - this.w / 2, this.y + this.h / 2);

		Point p11 = new Point(eObj.getX() - eObj.getWidth() / 2, eObj.getY()
				- eObj.getHeight() / 2);
		Point p12 = new Point(eObj.getX() + eObj.getWidth() / 2, eObj.getY()
				- eObj.getHeight() / 2);
		Point p13 = new Point(eObj.getX() + eObj.getWidth() / 2, eObj.getY()
				+ eObj.getHeight() / 2);
		Point p14 = new Point(eObj.getX() - eObj.getWidth() / 2, eObj.getY()
				+ eObj.getHeight() / 2);

		int minDist = 10;
		Dimension overlapSize = new Dimension(
				((p3.x + eObj.getWidth() / 2 + minDist) - this.x), ((p3.y
						+ eObj.getHeight() / 2 + minDist) - this.y));
		boolean overlap = false;
		if (p1.equals(p11) && p2.equals(p12) && p3.equals(p13)
				&& p4.equals(p14)) {
			overlap = true;
		} else if (inside(eObj.getX(), eObj.getY()) || inside(p11.x, p11.y)
				|| inside(p12.x, p12.y) || inside(p13.x, p13.y)
				|| inside(p14.x, p14.y) || eObj.inside(getX(), getY())
				|| eObj.inside(p1.x, p1.y) || eObj.inside(p2.x, p2.y)
				|| eObj.inside(p3.x, p3.y) || eObj.inside(p4.x, p4.y)) {
			overlap = true;
		}

		if (overlap) {
			return overlapSize;
		} 

		return new Dimension(0, 0);		
	}

	/** Marks this as element of a type graph */
	public void markElementOfTypeGraph(boolean val) {
		this.elemOfTG = val;
	}

	/** Sets x, y positions */
	public void setXY(int X, int Y) {
		this.x = X;
		this.y = Y;
	}

	/** Sets x positions */
	public void setX(int X) {
		this.x = X;
	}

	/** Sets y positions */
	public void setY(int Y) {
		this.y = Y;
	}

	/** Sets the width */
	public void setWidth(int W) {
		this.w = W;
	}

	/** Sets the height */
	public void setHeight(int H) {
		this.h = H;
	}

	/** Selects/deselects this object */
	public void setSelected(boolean sel) {
		if (!this.selected && sel) {
			this.selected = true;
		} else {
			this.selected = false;
		}
		this.weakselected = false;
	}

	/** Selects this object */
	public void select() {
		this.selected = true;
	}

	/** Deselects this object */
	public void deselect() {
		this.selected = false;
	}

	/** Selects/deselects this object as weak selected (using gray color)*/
	public void setWeakselected(boolean weaksel) {
		this.weakselected = weaksel;
	}
	
	public boolean isWeakselected() {
		return this.weakselected;
	}
	
	public void setBackground(Color c) {
		this.backgroundColor = c;
	}

	/** Sets my layout type to the EdType t */
	public void setType(EdType t) {
		this.eType = t;
	}

	/** Sets my key to int m */
	public void setMorphismMark(int m) {
		this.myKey = m;
	}

	/**
	 * Adds the morphism mark specified by the int m to my morphism mark
	 */
	public void addMorphismMark(int m) {
		this.marks.addElement(String.valueOf(m));
	}

	/**
	 * Adds the morphism mark specified by the String m to my morphism mark
	 */
	public void addMorphismMark(String m) {
		this.marks.addElement(m);
	}

	/** Returns TRUE if my morphism mark is empty */
	public boolean isMorphismMarkEmpty() {
		if (this.marks.isEmpty())
			return true;
		
		return false;
	}

	/* Makes my morphism mark empty */
	public void clearMorphismMark() {
		this.marks.clear();
	}

	protected void setContextUsage(String context) {
		this.contextUsage = context;
	}

	protected void addContextUsage(String context) {
		this.contextUsage = this.contextUsage.concat(":");
		this.contextUsage = this.contextUsage.concat(context);
	}
	
	protected String getContextUsage() {
		return this.contextUsage;
	}

	/** Sets my visibility to value specified by the boolean vis */
	public void setVisible(boolean vis) {
		this.visible = vis;
	}

	/** Sets visibility of my attribute to value specified by the boolean vis */
	public void setAttributeVisible(boolean vis) {
		this.attrVisible = vis;
	}
	
	/** Sets my copy to the object specified by the EdGraphObject ec */
	public void setCopy(EdGraphObject ec) {
		this.myCopy = ec;
	}

	/** Sets my attribute view to the view specified by the AttrViewSetting aView */
	public void setAttrViewSetting(AttrViewSetting aView) {
		this.view = aView;
		this.init = true;
	}

	/** Returns an open view of my attribute */
	protected AttrViewSetting getView() {
		return this.view;
	}

	/**
	 * sets if the current graph object has an error or not. If called with
	 * true, the object will be marked in the display and unmarked, if called
	 * with false as parameter.
	 * 
	 * @author Joerg <komm>
	 */
	public void setErrorMode(boolean errorMode) {
		this.errorMode = errorMode;
	}// setErrorMode

}
// $Log: EdGraphObject.java,v $
// Revision 1.35  2010/11/13 02:25:16  olga
// tuning
//
// Revision 1.34  2010/08/25 00:33:06  olga
// tuning
//
// Revision 1.33  2010/03/08 15:40:02  olga
// code optimizing
//
// Revision 1.32  2010/02/22 15:07:57  olga
// code optimizing
//
// Revision 1.31  2009/10/14 07:52:53  olga
// GUI bug fixed
//
// Revision 1.30  2009/06/30 09:50:18  olga
// agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
// agg.xt_basis.Node, Arc: changed: save, load the object name
// agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
//
// workaround of Applicability of Rule Sequences and Object Flow
//
// Revision 1.29  2009/03/25 15:19:14  olga
// code tuning
//
// Revision 1.28  2008/10/29 09:04:04  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.27  2008/09/04 07:48:42  olga
// GUI extension: hide nodes, edges
//
// Revision 1.26  2008/07/21 10:03:28  olga
// Code tuning
//
// Revision 1.25  2008/07/17 15:51:50  olga
// GraphEditor - graph scaling tuning
//
// Revision 1.24  2008/07/14 07:35:47  olga
// Applicability of RS - new option added, more tuning
// Node animation - new animation parameter added,
// Undo edit manager - possibility to disable it when graph transformation
// because it costs much more time and memory
//
// Revision 1.23  2008/07/02 17:14:36  olga
// Code tuning
//
// Revision 1.22  2008/04/21 09:32:19  olga
// Visualization of inheritance edge - bugs fixed
// Graph layout tuning
//
// Revision 1.21  2008/04/17 10:11:07  olga
// Undo, redo edit and graph layout tuning,
//
// Revision 1.20  2008/04/07 09:36:49  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.19  2008/01/23 15:03:18  olga
// Tuning of usability of the gragra editor.
//
// Revision 1.18  2007/12/05 08:57:00  olga
// Delete a conclusion of an Atomic graph constraint : bug fixed
// Graph visualization update after the marking "Abstract" of a type node in the type graph : bug fixed
// CPA : some bug fixed; code tuning
//
// Revision 1.17  2007/12/03 08:35:12  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.16  2007/11/19 08:48:38  olga
// Some GUI usability mistakes fixed.
// Default values in node/edge of a type graph implemented.
// Code tuning.
//
// Revision 1.15  2007/11/01 09:58:11  olga
// Code refactoring: generic types- done
//
// Revision 1.14  2007/10/11 08:05:04  olga
// Enumeration typing
//
// Revision 1.13  2007/09/24 09:42:33  olga
// AGG transformation engine tuning
//
// Revision 1.12  2007/09/10 13:05:15  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.11 2007/04/19 07:52:32 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.10 2007/04/11 10:03:32 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.9 2007/03/28 10:00:22 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.8 2007/01/22 08:28:28 olga
// GUI bugs fixed
//
// Revision 1.7 2006/11/01 11:17:29 olga
// Optimized agg sources of CSP algorithm, match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.6 2006/08/02 09:00:57 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.5 2006/04/06 09:28:53 olga
// Tuning of Import Type Graph and Import Graph
//
// Revision 1.4 2006/03/01 09:55:46 olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.3 2005/12/21 14:49:20 olga
// GUI tuning
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
// Revision 1.2 2005/06/20 13:37:04 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:01 olga
// Version with Eclipse
//
// Revision 1.14 2005/01/28 14:02:32 olga
// -Fehlerbehandlung beim Typgraph check
// -Erweiterung CP GUI / CP Menu
// -Fehlerbehandlung mit identification option
// -Fehlerbehandlung bei Rule PAC
//
// Revision 1.13 2004/11/15 11:24:45 olga
// Neue Optionen fuer Transformation;
// verbesserter default Graphlayout;
// Close GraGra mit Abfrage wenn was geaendert wurde statt Delete GraGra
//
// Revision 1.12 2004/06/14 12:34:19 olga
// CP Analyse and Transformation
//
// Revision 1.11 2003/12/18 16:26:23 olga
// Copy method and Layout
//
// Revision 1.10 2003/04/10 09:05:24 olga
// Aenderungen wegen serializable Ausgabe
//
// Revision 1.9 2003/04/10 08:50:33 olga
// Tests mit serializable Ausgabe
//
// Revision 1.8 2003/03/05 18:24:24 komm
// sorted/optimized import statements
//
// Revision 1.7 2002/11/25 15:03:51 olga
// Arbeit an den Typen.
//
// Revision 1.6 2002/11/14 14:29:39 olga
// Anzeige von Multiplicity -- ein Versuch.
//
// Revision 1.5 2002/09/30 10:08:10 komm
// insert TypeException and type error marks
//
// Revision 1.4 2002/09/30 10:02:29 olga
// Layout
//
// Revision 1.3 2002/09/23 12:24:06 komm
// added type graph in xt_basis, editor and GUI
//
// Revision 1.2 2002/09/19 16:21:23 olga
// Layout von Knoten geaendert.
//
// Revision 1.1.1.1 2002/07/11 12:17:07 olga
// Imported sources
//
// Revision 1.15 2001/05/14 12:00:41 olga
// Graph Layout und Graphobject Layout optimiert.
//
// Revision 1.14 2001/03/08 10:53:20 olga
// Das ist Stand nach der AGG GUI Reimplementierung.
//
// Revision 1.13 2000/12/21 09:48:52 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.12.6.2 2000/11/09 17:54:41 olga
// Fehlerbeseitigt im TypeEditor und bei den Kanten.
//
// Revision 1.12.6.1 2000/11/06 09:32:32 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.12 2000/06/07 09:54:20 olga
// Der Layoutvergleich wurde erweitert durch Color Vergleich.
//
// Revision 1.11 1999/09/23 14:04:29 olga
// *** empty log message ***
//
// Revision 1.10 1999/09/23 14:00:39 olga
// Aenderung betreff.AttrTopEditor.
// AttrViewSetting view nicht mehr transient.
//
// Revision 1.9 1999/09/16 13:56:57 olga
// *** empty log message ***
//
// Revision 1.8 1999/09/15 15:59:34 olga
// *** empty log message ***
//
// Revision 1.7 1999/09/15 12:52:32 olga
// *** empty log message ***
//
// Revision 1.6 1999/09/06 13:59:47 shultzke
// Editoren sind statisch samt serialVersionUID
//
