package agg.xt_basis;


/**
 * 
 * @author $Author: olga $
 * @version $Id: TypeGraphArc.java,v 1.14 2010/10/08 08:48:02 olga Exp $
 */
public class TypeGraphArc {
	/**
	 * true, if this combination of source, traget and edge type is contained in
	 * the type graph
	 */
	private boolean typeGraphObjectDefined = false;

	/**
	 * its edge object inside of a type graph
	 */
	private Arc itsArc;
	
	private boolean visible = true;
	
	/**
	 * minimum number of nodes arcs of this type starts from.
	 */
	private short sourceMin = Type.UNDEFINED;

	/**
	 * maximum number of nodes arcs of this type starts from.
	 */
	private short sourceMax = Type.UNDEFINED;

	/**
	 * minimum number of nodes arcs of this type goes to.
	 */
	private short targetMin = Type.UNDEFINED;

	/**
	 * maximum number of nodes arcs of this type goes to.
	 */
	private short targetMax = Type.UNDEFINED;

	
	TypeGraphArc() {}

	public void dispose() {
		this.itsArc = null;
	}
		
	Arc addTypeGraphObject(Arc a) {		
		this.itsArc = a;
		this.typeGraphObjectDefined = (a != null);
		return this.itsArc;
	}

	public Arc getArc() {
		return this.itsArc;
	}
	
	public void setVisible(boolean vis) {
		this.visible = vis;
	}
	
	public boolean isVisible() {
		return this.visible;		
	}
		
	boolean doesTypeGraphObjectExist() {
		return this.typeGraphObjectDefined;
	}

	boolean removeTypeGraphObject() {
		this.typeGraphObjectDefined = false;
		this.itsArc = null;
		return true;
	}

	void forceRemoveTypeGraphObject() {
		this.typeGraphObjectDefined = false;
		this.itsArc = null;
	}

	public void setSourceMin(int value) {
		this.sourceMin = (short) value;
	}

	public void setSourceMax(int value) {
		this.sourceMax = (short) value;
	}

	public void setTargetMin(int value) {
		this.targetMin = (short) value;
	}

	public void setTargetMax(int value) {
		this.targetMax = (short) value;
	}

	public int getSourceMin() {
		return this.sourceMin;
	}

	public int getSourceMax() {
		return this.sourceMax;
	}

	public int getTargetMin() {
		return this.targetMin;
	}

	public int getTargetMax() {
		return this.targetMax;
	}

}

// $Log: TypeGraphArc.java,v $
// Revision 1.14  2010/10/08 08:48:02  olga
// tuning
//
// Revision 1.13  2010/03/08 15:51:39  olga
// code optimizing
//
// Revision 1.12  2010/02/22 14:41:39  olga
// code optimizing
//
// Revision 1.11  2009/07/23 14:31:16  olga
// code tuning
//
// Revision 1.10  2008/09/04 08:06:42  olga
// Extension: visibility of nodes, edges
//
// Revision 1.9  2008/04/07 09:36:54  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.8  2008/02/18 09:37:11  olga
// - an extention of rule dependency check is implemented;
// - some bugs fixed;
// - editing of graphs improved
//
// Revision 1.7  2007/11/19 08:48:40  olga
// Some GUI usability mistakes fixed.
// Default values in node/edge of a type graph implemented.
// Code tuning.
//
// Revision 1.6  2007/10/04 07:44:28  olga
// Code tuning
//
// Revision 1.5  2007/09/10 13:05:38  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.4 2007/03/28 10:01:10 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.3 2006/04/03 08:57:50 olga
// New: Import Type Graph
// and some bugs fixed
//
// Revision 1.2 2005/09/08 16:25:02 olga
// Improved: editing attr. condition, importing graph, sorting node/edge types
//
// Revision 1.1 2005/08/25 11:56:54 enrico
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
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.8 2005/01/28 14:02:32 olga
// -Fehlerbehandlung beim Typgraph check
// -Erweiterung CP GUI / CP Menu
// -Fehlerbehandlung mit identification option
// -Fehlerbehandlung bei Rule PAC
//
// Revision 1.7 2004/12/20 14:53:49 olga
// Changes because of matching optimisation.
//
// Revision 1.6 2003/05/28 11:50:41 olga
// Min/Max Multiplicity check Test und Aenderung
//
// Revision 1.5 2003/04/10 08:51:23 olga
// Tests mit serializable Ausgabe
//
// Revision 1.4 2002/12/16 10:42:36 komm
// multiplicity moved to TypeGraphArc
//
// Revision 1.3 2002/12/04 14:02:21 komm
// multiplicity works now, new isUseless()
//
// Revision 1.2 2002/12/02 09:59:52 komm
// each source/target combination has now its own multiplicity constraints
//
// Revision 1.1 2002/11/11 10:39:02 komm
// multiplicity check added
//
