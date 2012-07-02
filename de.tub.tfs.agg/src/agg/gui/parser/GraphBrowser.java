package agg.gui.parser;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JSplitPane;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdMorphism;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.gui.editor.GraphEditor;
import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.ParserGUIListener;
import agg.xt_basis.Graph;


/**
 * This class displays two graphs. Each graph is the left side of a selected
 * rule.
 * 
 * @version $Id: GraphBrowser.java,v 1.9 2010/09/23 08:20:54 olga Exp $
 * @author $Author: olga $
 * 
 * @deprecated not more supported
 */
public class GraphBrowser implements ParserGUIListener {

	JSplitPane graphPane;

	GraphEditor geLeft, geRight;

	EdGraGra layout;

	private boolean attrsVisible = true;

	/**
	 * Creates a new graph browser.
	 * 
	 * @param layout
	 *            The layout is taken from a graph grammar.
	 */
	public GraphBrowser(EdGraGra layout) {
		setLayout(layout);
		this.geLeft = new GraphEditor();
		this.geLeft.setEditMode(agg.gui.editor.EditorConstants.MOVE); // VIEW);
		this.geLeft.setGraph(null);
		this.geRight = new GraphEditor();
		this.geRight.setEditMode(agg.gui.editor.EditorConstants.MOVE); // VIEW);
		this.geRight.setGraph(null);
		this.graphPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.geLeft, this.geRight);
		this.graphPane.setOneTouchExpandable(true);
		this.graphPane.setContinuousLayout(true);
		this.graphPane.setDividerLocation(250);
	}

	/**
	 * Sets a new layout in this graph browser.
	 * 
	 * @param layout
	 *            The new layout.
	 */
	public void setLayout(EdGraGra layout) {
		this.layout = layout;
	}

	/**
	 * The returned component can be displayed in a frame or panel.
	 * 
	 * @return The component is a JSplitPane.
	 */
	public Component getComponent() {
		return getGraphPane();
	}

	public Component getLeftComponent() {
		return this.geLeft.getGraphPanel().getCanvas();
	}

	public Component getRightComponent() {
		return this.geRight.getGraphPanel().getCanvas();
	}

	/**
	 * The returned graph pane holds the two panels for the left hand side of
	 * the rules.
	 * 
	 * @return The JSplitPane
	 */
	public JSplitPane getGraphPane() {
		return this.graphPane;
	}

	/**
	 * Sets the graph of the left side of the graph browser.
	 * 
	 * @param left
	 *            A graph without layout.
	 */
	public void setLeftGraph(Graph left) {
		if (left == null) {
			this.geLeft.setGraph(null);
			this.geLeft.updateGraphics();
		} else if (this.layout != null) {
			EdGraph eg = new EdGraph(left, this.layout.getTypeSet(), this.attrsVisible);
			// eg.updateGraph(this.attrsVisible);
			copyLayout(this.layout, eg);
			eg.updateGraph(this.attrsVisible);
			this.geLeft.setGraph(eg);
			// this.geLeft.setEditMode(agg.editor.impl.EditorConstants.VIEW);
			this.geLeft.setTitle(left.getName());
		}
	}

	public void setLeftGraph(Graph left, String title) {
		setLeftGraph(left);
		if (this.layout != null)
			this.geLeft.setTitle(title);
	}

	/**
	 * Sets the graph of the right side of the graph browser.
	 * 
	 * @param right
	 *            A graph without layout.
	 */
	public void setRightGraph(Graph right) {
		if (right == null) {
			this.geRight.setGraph(null);
			this.geRight.updateGraphics();
		} else if (this.layout != null) {
			EdGraph eg = new EdGraph(right, this.layout.getTypeSet(), this.attrsVisible);
			eg.updateGraph(this.attrsVisible);
			copyLayout(this.layout, eg);
			this.geRight.setGraph(eg);
			// this.geRight.setEditMode(agg.editor.impl.EditorConstants.VIEW);
			this.geRight.setTitle(right.getName());
		}
	}

	public void setRightGraph(Graph right, String title) {
		setRightGraph(right);
		if (this.layout != null)
			this.geRight.setTitle(title);
	}

	/**
	 * The event occured when a window of a overlapping graph is selected.
	 * Therefor display of the overlapping morphisms has to be updated.
	 * 
	 * @param pguie
	 *            The event from the window.
	 */
	public void occured(ParserGUIEvent pguie) {
		// System.out.println("GraphBrowser aktiviert"+pguie.getSource());
		if (pguie.getData() instanceof EdMorphism) {
			EdMorphism numbers = (EdMorphism) pguie.getData();
			HashMap mapL = numbers.getTargetOfMorphism(1);
			GraphEditor linksGege = (GraphEditor) getGraphPane()
					.getLeftComponent();
			linksGege.getGraph().setMorphismMarks(mapL, true);
			HashMap mapR = numbers.getTargetOfMorphism(2);
			GraphEditor rechtsGege = (GraphEditor) getGraphPane()
					.getRightComponent();
			rechtsGege.getGraph().setMorphismMarks(mapR, true);
			linksGege.updateGraphics();
			rechtsGege.updateGraphics();
		}
	}

	public void refresh() {
		this.geLeft.updateGraphics();
		this.geRight.updateGraphics();
	}

	/*
	 * The base graph of EdGraph to has to be a graph (left or right graph of a
	 * rule) of the base gragra of EdGraGra from
	 */
	private void copyLayout(EdGraGra from, EdGraph to) {
		EdRule r = null;
		for (int i = 0; i < from.getRules().size(); i++) {
			r = from.getRules().elementAt(i);
			if (r.getLeft().getBasisGraph().equals(to.getBasisGraph())) {
				break;
			}
		}
		// System.out.println("GraphBrowser: Layout graph : "+r.getLeft());
		if (r == null)
			return;
		EdGraph g = r.getLeft();
		for (int k = 0; k < to.getNodes().size(); k++) {
			EdNode n = to.getNodes().elementAt(k);
			for (int j = 0; j < g.getNodes().size(); j++) {
				EdNode en = g.getNodes().elementAt(j);
				if (en.getBasisNode().equals(n.getBasisNode())) {
					n.setXY(en.getX(), en.getY());
					break;
				}
			}
		}
	}
}
/*
 * $Log: GraphBrowser.java,v $
 * Revision 1.9  2010/09/23 08:20:54  olga
 * tuning
 *
 * Revision 1.8  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.7  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.6  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2006/03/01 09:55:47 olga - new CPA
 * algorithm, new CPA GUI
 * 
 * Revision 1.4 2005/10/10 09:13:30 olga tests
 * 
 * Revision 1.3 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.12 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.11 2004/04/19 11:39:30 olga Graphname als String ohne Blanks
 * 
 * Revision 1.10 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.9 2003/03/05 14:54:47 olga GUI: Morphism anzeigen
 * 
 * Revision 1.8 2003/03/03 17:46:59 olga GUI
 * 
 * Revision 1.7 2003/02/24 17:50:58 olga GUI
 * 
 * Revision 1.6 2003/02/13 17:08:08 olga GUI Anpassung
 * 
 * Revision 1.5 2003/02/05 09:11:48 olga GUI
 * 
 * Revision 1.4 2003/01/15 11:37:01 olga Kleine Aenderung
 * 
 * Revision 1.3 2002/12/09 17:53:26 olga GUI - Verbesserung
 * 
 * Revision 1.2 2002/10/02 18:30:55 olga XXX
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.4 2001/05/14 11:52:57 olga Parser GUI Optimierung
 * 
 * Revision 1.3 2001/04/11 14:56:59 olga Arbeit an der GUI.
 * 
 * Revision 1.2 2001/03/08 11:02:43 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.7 2001/01/28 13:14:43 shultzke API fertig
 * 
 * Revision 1.1.2.6 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.5 2000/07/17 16:12:38 shultzke exlude berechnung verschluckt
 * stdout und rechnet nicht richtig
 * 
 * Revision 1.1.2.4 2000/07/12 14:33:42 shultzke Morphismen koennen jetzt besser
 * gemalt werden
 * 
 * Revision 1.1.2.3 2000/07/10 15:08:07 shultzke additional representtion
 * hinzugefuegt
 * 
 * Revision 1.1.2.2 2000/07/09 17:12:34 shultzke grob die GUI eingebunden
 * 
 */
