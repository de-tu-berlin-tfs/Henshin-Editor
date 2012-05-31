package agg.gui.cpa;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.ParserGUIListener;
import agg.xt_basis.GraGra;

/**
 * This class provides a tree that displays all rules from a selected graph
 * grammar.
 * 
 * @version $Id: RuleTree.java,v 1.2 2010/09/23 08:18:50 olga Exp $
 * @author $Author: olga $
 */
public class RuleTree implements TreeSelectionListener {
	JTree treeView;

	Vector<ParserGUIListener> listeners;

	boolean showAtomics, withNACs;

	/**
	 * Creates a new tree with the selected graph grammar. The graph grammar
	 * provides the set of rules which are displayed.
	 * 
	 * @param grammar
	 *            The selected grammar.
	 */
	public RuleTree(GraGra grammar) {
		this(grammar, false, true);
	}

	/**
	 * Creates a new tree with the selected graph grammar. The graph grammar
	 * provides the set of rules which are displayed.
	 * 
	 * @param grammar
	 *            The selected grammar.
	 */
	public RuleTree(GraGra grammar, boolean atomics, boolean nacs) {
		this.treeView = new JTree(new RuleModel(grammar, atomics, nacs));
		this.treeView.addTreeSelectionListener(this);
		this.treeView.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.treeView.setEditable(false);
		this.treeView.setShowsRootHandles(false);
		this.treeView.setMinimumSize(new Dimension(100, 100));
		this.treeView.setCellRenderer(new RuleTreeCellRenderer());
		this.listeners = new Vector<ParserGUIListener>();
		this.showAtomics = atomics;
		this.withNACs = nacs;
	}

	/**
	 * Sets a grammar for the tree.
	 * 
	 * @param gragra
	 *            The new grammar.
	 */
	public void setGrammar(GraGra gragra) {
		this.treeView.setModel(new RuleModel(gragra, this.showAtomics, this.withNACs));
	}

	/**
	 * Returns the tree for the display in a frame or panel.
	 * 
	 * @return The returned graph is a JTree.
	 */
	public JTree getTree() {
		return this.treeView;
	}

	/**
	 * Register here a new listener to receive events.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addParserGUIListener(ParserGUIListener listener) {
		this.listeners.addElement(listener);
	}

	/**
	 * Remove a listener here and stop getting messages.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void removeParserGUIListener(ParserGUIListener listener) {
		this.listeners.removeElement(listener);
	}

	private void fireParserGUIEvent(Object data) {
		ParserGUIEvent event = new ParserGUIEvent(this, data);
		for (int i = 0; i < this.listeners.size(); i++) {
			ParserGUIListener l = this.listeners.elementAt(i);
			l.occured(event);
		}
	}

	// Implementierung des TreeSelectionListener
	/**
	 * If a value of a tree changes this method has to handle this change.
	 * 
	 * @param e
	 *            The event from the changing object.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		RuleModel.TreeData node = (RuleModel.TreeData) getTree()
				.getLastSelectedPathComponent();

		if (node == null)
			return;
		fireParserGUIEvent(node);
	}
}
/*
 * $Log: RuleTree.java,v $
 * Revision 1.2  2010/09/23 08:18:50  olga
 * tuning
 *
 * Revision 1.1  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.2  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.3 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/03/03 17:46:59 olga GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:49 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.4 2001/01/28 13:14:48 shultzke API fertig
 * 
 * Revision 1.1.2.3 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.2 2000/07/09 17:12:40 shultzke grob die GUI eingebunden
 * 
 */
