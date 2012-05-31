package agg.attribute.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import agg.attribute.AttrManager;
import agg.attribute.AttrTuple;
import agg.attribute.AttrVariableTuple;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.impl.VarMember;

/**
 * This class provides an editor for input parameter
 * 
 * @version $Id: InputParameterEditor.java,v 1.4 2010/08/18 09:24:53 olga Exp $
 * @author $Author: olga $
 */
public class InputParameterEditor extends LightInstanceEditor {

	/** create an editor */
	public InputParameterEditor(AttrManager m, AttrEditorManager em) {
		super(m, em);
		// System.out.println("input editor 1306");
	}

	/** create the table of this editor */
	protected TupleTableModel createTableModel() {
		int[] columns = { IS_INPUT_PARAMETER, TYPE, NAME, EXPR, CORRECTNESS };

		TupleTableModel tm = new TupleTableModel(this);
		tm.setColumnArray(columns);
		tm.setExtensible(false);

		tm.setColumnTitle(IS_INPUT_PARAMETER, " In ");
		tm.setColumnTitle(TYPE, "Type");
		tm.setColumnTitle(NAME, "Parameter Name");
		tm.setColumnTitle(EXPR, "Value");
		tm.setColumnTitle(CORRECTNESS, "OK");

		tm.setColumnEditable(IS_INPUT_PARAMETER, false);
		tm.setColumnEditable(TYPE, false);
		tm.setColumnEditable(NAME, false);
		tm.setColumnEditable(EXPR, true);
		tm.setColumnEditable(CORRECTNESS, false);

		return tm;
	}

	/** simply put the table into my main panel */
	protected void genericCustomizeMainLayout() {
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.tableScrollPane, BorderLayout.CENTER);
		this.mainPanel.setPreferredSize(new Dimension(200, 200));
	}

	/** now add all input parameter */
	public void setTuple(AttrTuple anAttrTuple) {
		super.setTuple(anAttrTuple);
		if (anAttrTuple == null)
			return;
		int size = ((AttrVariableTuple) anAttrTuple).getNumberOfEntries();
		// System.out.println(anAttrTuple.getClass().getName()+"Size: "+size);
		for (int i = 0; i < size; i++) {
			AttrVariableTuple avt = (AttrVariableTuple) anAttrTuple;
			VarMember vm = (VarMember) avt.getMemberAt(i);
			// System.out.println(vm);
			if (vm.isInputParameter()) {
				// System.out.println("attr nr."+i+" ist input parameter und
				// visible");
				getViewSetting().setVisibleAt(anAttrTuple, true, i);
			} else {
				getViewSetting().setVisibleAt(anAttrTuple, false, i);
				// System.out.println("attr nr."+i+" ist nicht sichtbar");
			}
		}
	}
}
/*
 * $Id: InputParameterEditor.java,v 1.4 2010/08/18 09:24:53 olga Exp $
 * 
 * $Log: InputParameterEditor.java,v $
 * Revision 1.4  2010/08/18 09:24:53  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2005/10/19 08:58:45 olga GUI
 * tuning
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/03/01 15:47:55 olga Tests
 * 
 * Revision 1.3 2003/12/18 16:25:33 olga .
 * 
 * Revision 1.2 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:52 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.2 1999/09/09 10:25:15 mich Update Shared Source Working
 * Environment
 * 
 * Revision 1.1 1999/07/21 12:12:48 shultzke ParameterEditor hizugefuegt und in
 * dazugehoerigen Klassen durchgestellt.
 */

