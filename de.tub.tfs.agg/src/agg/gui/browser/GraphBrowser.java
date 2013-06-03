package agg.gui.browser;

import javax.swing.JFrame;
import javax.swing.JPanel;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;

/**
 * The interface that defines a graph browser for viewing graphs. The graph can
 * be one of types: <code>editor.impl.EdGraph</code> or
 * <code>agg.xt_basis.Graph</code> ,
 * 
 * @author $Author: olga $
 * @version $Id: GraphBrowser.java,v 1.2 2007/09/10 13:05:53 olga Exp $
 */
public interface GraphBrowser {

	/** Return my <code>JPanel</code>. */
	public abstract JPanel getPanel();

	/** Return my graph. */
	public abstract Object getGraph();

	/** Set <code>EdGraph</code> to show. */
	public abstract void setGraph(EdGraph g);

	/** Set <code>Graph</code> to show. */
	public abstract void setGraph(Graph g);

	/** Show graph. */
	public abstract void showGraph();

	/**
	 * Return gragra. The gragra is of type
	 * <code>agg.editor.impl.EdGraGra</code>.
	 */
	public abstract EdGraGra getGraGra();

	/**
	 * Return gragra. The gragra is of type <code>agg.xt_basis.GraGra</code>.
	 */
	public abstract GraGra getBaseGraGra();

	/**
	 * Set gragra. The gragra is of type <code>agg.editor.impl.EdGraGra</code>.
	 */
	public abstract void setGraGra(EdGraGra gragra);

	/**
	 * Set gragra. The gragra is of type <code>agg.xt_basis.GraGra</code>.
	 */
	public abstract void setGraGra(GraGra gragra);

	/** Load gragra. */
	public abstract EdGraGra loadGraGra(JFrame frame);

	/** Load base gragra. */
	public abstract GraGra loadBaseGraGra(JFrame frame);

	/**
	 * Save gragra. The gragra is of type <code>agg.editor.impl.EdGraGra</code>.
	 */
	public abstract void saveAs(JFrame frame);

	/** Read base graph and update graphics. */
	public abstract void updateGraphics();

}
// $Log: GraphBrowser.java,v $
// Revision 1.2  2007/09/10 13:05:53  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1 2005/08/25 11:57:00 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:04 olga
// Version with Eclipse
//
// Revision 1.2 2003/03/05 18:24:29 komm
// sorted/optimized import statements
//
// Revision 1.1.1.1 2002/07/11 12:17:16 olga
// Imported sources
//
// Revision 1.4 1999/09/09 10:25:04 mich
// Update Shared Source Working Environment
//
// Revision 1.3 1999/08/17 10:51:13 shultzke
// neues Package hinzugefuegt
//
