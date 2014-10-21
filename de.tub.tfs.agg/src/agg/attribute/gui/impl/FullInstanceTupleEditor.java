package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditorManager;

/**
 * Editor for all data of an attribute instance tuple.
 * 
 * @author $Author: olga $
 * @version $Id: FullInstanceTupleEditor.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 */
public class FullInstanceTupleEditor extends TabMesTool_TupleEditor {

	public FullInstanceTupleEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
		setViewSetting(getViewSetting().getOpenView()); // all entries visible;
	}

	protected void arrangeMainPanel() {
	}

	protected void genericCustomizeMainLayout() {
		super.genericCustomizeMainLayout();
		this.mainPanel.setPreferredSize(new Dimension(400, 300));
	}

	//
	// Overriding...

	/**
	 * The heart of the matter. Columns are: [ VISIBILITY, HANDLER, TYPE, NAME,
	 * EXPR, CORRECTNESS ]. Extendable: true. Titles: default. Editable: default
	 * (all except for CORRECTNESS).
	 */
	protected TupleTableModel createTableModel() {
		int columns[] = { VISIBILITY, HANDLER, TYPE, NAME, EXPR, CORRECTNESS };
		TupleTableModel tm = new TupleTableModel(this);
		tm.setColumnArray(columns);
		tm.setExtensible(true);
		return tm;
	}

	protected void createToolBar() {
		JLabel label1 = new JLabel("Tuple:");
		JLabel label2 = new JLabel("Member:");

		JToolBar toolBar1 = new JToolBar();
		toolBar1.setFloatable(false);
		toolBar1.add(getResetAction());
		toolBar1.addSeparator();
		toolBar1.add(getShowAllAction());
		toolBar1.addSeparator();
		toolBar1.add(getHideAllAction());

		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFloatable(false);
		toolBar2.add(getDeleteAction());
		toolBar2.addSeparator();
		toolBar2.add(getEvaluateAction());

		Box hBox = Box.createHorizontalBox();
		hBox.add(label1);
		hBox.add(toolBar1);
		hBox.add(Box.createGlue());
		hBox.add(label2);
		hBox.add(toolBar2);
		this.toolBarPanel = new JPanel(new BorderLayout());
		this.toolBarPanel.add(hBox, BorderLayout.CENTER);
	}
}
/*
 * $Log: FullInstanceTupleEditor.java,v $
 * Revision 1.3  2010/08/18 09:24:52  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:50 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.8 2000/04/05 12:07:50 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.7 1999/12/22 12:36:41 shultzke The user cannot edit the context of
 * graphs. Only in rules it is possible.
 * 
 * Revision 1.6 1999/09/13 10:01:03 shultzke ContextEditor dezent eingefaerbt
 * 
 * Revision 1.5 1999/08/17 07:32:27 shultzke GUI leicht geaendert
 */
