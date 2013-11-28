package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditorManager;

/**
 * Editor for all data of an attribute instance tuple. However, it does not
 * complain if a type tuple is edited instead.
 * 
 * @version $Id: LightInstanceEditor.java,v 1.3 2010/08/18 09:24:53 olga Exp $
 * @author $Author: olga $
 */
public class LightInstanceEditor extends BasicTupleEditor {

	public LightInstanceEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
		setViewSetting(getViewSetting().getMaskedView()); // Apply tuple mask;
	}

	// Overriding...

	/**
	 * The heart of the matter. Columns are: [NAME, EXPR, CORRECTNESS]
	 * Extendable: false Titles: None. Editable: Only EXPR.
	 */
	protected TupleTableModel createTableModel() {
		int columns[] = { NAME, EXPR, CORRECTNESS };

		TupleTableModel tm = new TupleTableModel(this);
		tm.setColumnArray(columns);
		tm.setExtensible(false);

		tm.setColumnTitle(NAME, null);
		tm.setColumnTitle(EXPR, null);
		tm.setColumnTitle(CORRECTNESS, null);

		tm.setColumnEditable(NAME, false);
		tm.setColumnEditable(EXPR, true);
		tm.setColumnEditable(CORRECTNESS, false);

		return tm;
	}

	// Taking genericCreateAllViews() from parent class.

	/** Simply put the table onto my main panel. */
	protected void genericCustomizeMainLayout() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.tableScrollPane, BorderLayout.CENTER);
		this.mainPanel.setPreferredSize(new Dimension(100, 100));
	}

	/** @override AbstractTupleEditor */
	protected void createTableView() {
		super.createTableView();

		// No reordering, since header titles are not shown,
		// the user might be confused.
		this.tableView.getTableHeader().setReorderingAllowed(false);
		this.tableView.setRowSelectionAllowed(false);
		this.tableView.setColumnSelectionAllowed(false);
	}
}
/*
 * $Log: LightInstanceEditor.java,v $
 * Revision 1.3  2010/08/18 09:24:53  olga
 * tuning
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
 * Revision 1.2 2002/09/23 12:23:50 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:07:54 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
