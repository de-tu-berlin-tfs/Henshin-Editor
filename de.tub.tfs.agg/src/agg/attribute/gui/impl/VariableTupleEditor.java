package agg.attribute.gui.impl;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditorManager;

/**
 * Editor for a variable-tuple.
 * 
 * @version $Id: VariableTupleEditor.java,v 1.3 2010/09/20 14:27:59 olga Exp $
 * @author $Author: olga $
 */
public class VariableTupleEditor extends TabMesTool_TupleEditor {

	public VariableTupleEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
	}

	//
	// Overriding...

	/**
	 * The heart of the matter. Columns are: [ IS_INPUT_PARAMETER,
	 * IS_OUTPUT_PARAMETER, HANDLER, TYPE, NAME, VALUE, CORRECTNESS ]
	 * Extensible: true. Titles: default. Editable: Only EXPR.
	 */
	protected TupleTableModel createTableModel() {
		int columns[] = { IS_INPUT_PARAMETER, IS_OUTPUT_PARAMETER, HANDLER,
				TYPE, NAME, EXPR, CORRECTNESS };
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
 * $Log: VariableTupleEditor.java,v $
 * Revision 1.3  2010/09/20 14:27:59  olga
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
 * Revision 1.2 2002/09/23 12:23:52 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:08:04 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
