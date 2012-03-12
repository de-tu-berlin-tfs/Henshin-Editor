package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditorManager;

/**
 * Abstract editor, providing a general layout: From top to bottom: 1. The table
 * view. 2. Message text area. 3. Button panel.
 * 
 * @version $Id: TabMesTool_TupleEditor.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public abstract class TabMesTool_TupleEditor extends ExtendedTupleEditorSupport {

	public TabMesTool_TupleEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
	}

	//
	// Overriding...

	protected void genericCreateAllViews() {
		createTableView();
		createOutputTextArea();
		createToolBar();
	}

	/**
	 * From top to bottom: 1. The table view with the whole tuple, all entries
	 * visible. 2. Message text area. 3. Button panel.
	 */
	protected void genericCustomizeMainLayout() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.tableAndOutputSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				this.tableScrollPane, this.outputScrollPane);
		this.tableAndOutputSplitPane.setMinimumSize(new Dimension(80, 50));
		this.mainPanel.add(this.tableAndOutputSplitPane, BorderLayout.CENTER);
		this.mainPanel.add(this.toolBarPanel, BorderLayout.SOUTH);
		this.mainPanel.setMinimumSize(new Dimension(80, 70));
		this.mainPanel.addComponentListener(this);
		this.tableScrollPane.setPreferredSize(new Dimension(300, 100));
		resize();
		// mainPanel.setPreferredSize( getMainDim());
	}

	protected void resize() {
		this.tableAndOutputSplitPane.setDividerLocation(0.9);
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}

	public void componentShown(ComponentEvent e) {
		resize();
	}

	public void componentResized(ComponentEvent e) {
		resize();
	}

	protected abstract void createToolBar();
}
/*
 * $Log: TabMesTool_TupleEditor.java,v $
 * Revision 1.4  2010/08/18 09:24:53  olga
 * tuning
 *
 * Revision 1.3  2008/07/09 13:34:26  olga
 * Applicability of RS - bug fixed
 * Delete not used node/edge type - bug fixed
 * AGG help - extended
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:51 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:07:58 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
