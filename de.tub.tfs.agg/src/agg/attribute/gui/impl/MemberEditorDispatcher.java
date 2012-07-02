package agg.attribute.gui.impl;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import agg.attribute.AttrInstance;
import agg.attribute.AttrInstanceMember;
import agg.attribute.AttrTuple;
import agg.attribute.AttrTypeMember;
//import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.gui.HandlerExprEditor;

/**
 * Calls the appropriate editors for table cells.
 * 
 * @version $Id: MemberEditorDispatcher.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class MemberEditorDispatcher extends DefaultCellEditor implements
		TableCellEditor, TupleTableModelConstants {

	static final long serialVersionUID = -8476624634642847534L;

	/**
	 * Owning editor, serves e.g. for accessing of the edited tuple, the view
	 * setting and the table.
	 */
	protected BasicTupleEditor editor = null;

	public MemberEditorDispatcher(BasicTupleEditor editor) {
		super(new JTextField("test", 10));
		// Temporarily disabled.
		// this.editor = editor;
		// editor.getTableView().setCellEditor( this );
	}

	/** Dispatches to the appropriate cell editor. */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		TupleTableModel tableModel = this.editor.getTableModel();
		int key = tableModel.getItemKeyAt(column);
		TableCellEditor defaultEditor = this.editor.getTableView().getDefaultEditor(
				tableModel.getItemClass(key));

		if (key == HANDLER) {
			return (this.editor.getHandlerSelectionEditor().getComponent());
		} else if (key == EXPR) {
			AttrTuple tuple = this.editor.getTuple();
			if ((tuple != null && row >= tuple.getNumberOfEntries())
					|| !(tuple instanceof AttrInstance)) {
				return defaultEditor.getTableCellEditorComponent(table, value,
						isSelected, row, column);
			}
			AttrInstance inst = (AttrInstance) tuple;
			AttrInstanceMember member = (AttrInstanceMember) inst.getMemberAt(
					this.editor.getViewSetting(), row);
			AttrTypeMember decl = member.getDeclaration();
//			HandlerExpr expr = member.getExpr();
			HandlerExprEditor hee = this.editor.getHandlerEditorManager()
					.getExprEditor(decl.getHandler(), decl.getType(),
							member.getExpr());
			if (hee == null) {
				return defaultEditor.getTableCellEditorComponent(table, value,
						isSelected, row, column);
			}
			return hee.getEditorComponent(decl.getType(), member.getExpr(),
					new Dimension(100, 10));
		} else {
			return defaultEditor.getTableCellEditorComponent(table, value,
					isSelected, row, column);
		}
	}

}
/*
 * $Log: MemberEditorDispatcher.java,v $
 * Revision 1.4  2010/09/23 08:13:17  olga
 * tuning
 *
 * Revision 1.3  2007/11/05 09:18:19  olga
 * code tuning
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
 * Revision 1.5 2000/04/05 12:07:56 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
