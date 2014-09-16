package agg.attribute.handler.gui.impl;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @version $Id: DefaultHandlerEditorSupport.java,v 1.1 2005/08/25 11:56:58
 *          enrico Exp $
 * @author $Author: olga $
 */
public abstract class DefaultHandlerEditorSupport extends AbstractHandlerEditor
		implements CellEditorListener {

	protected transient DefaultCellEditor cellEditor;

	protected transient DefaultTableCellRenderer cellRenderer;

	protected transient JTextField editorField;

	protected Object editedObject = null;

	public DefaultHandlerEditorSupport() {
		super();
		JTextField editorF = new JTextField();
		this.cellEditor = new DefaultCellEditor(editorF);
		this.cellEditor.addCellEditorListener(this);
		this.cellRenderer = new DefaultTableCellRenderer();
	}

	/** This method is for overriding with additional behaviour. */
	protected void updateEditedObject(Object newObject) {
		this.editedObject = newObject;
	}

	public void editingStopped(ChangeEvent e) {
		updateEditedObject(this.cellEditor.getCellEditorValue());
		fireEditingStopped();
	}

	public void editingCanceled(ChangeEvent e) {
		fireEditingCancelled();
	}

	/**
	 * Returns a graphical component for displaying the specified type. The
	 * 'availableSpace' limit should be honoured, since this is a service for
	 * displaying the type in a table cell. However, the renderer can contain
	 * tools (e.g. buttons) for invoking its larger custom renderer. Either
	 * 'handler or 'typeToRender' cannot be null.
	 */
	public Component getRendererComponent(Object obj, Dimension availableSpace) {
		return this.cellRenderer.getTableCellRendererComponent(null, // JTable table,
				obj.toString(), // Object value,
				true, // boolean isSelected,
				true, // boolean hasFocus,
				0, // int row,
				0); // int column)
	}

	/**
	 * Returns a graphical component for editing the specified type. The
	 * 'availableSpace' is a recommendation when the editor wishes to be
	 * operatable in a compact table cell and needs not be taken into account.
	 * Either 'handler or 'typeToRender' cannot be null.
	 */
	public Component getEditorComponent(Object obj, Dimension availableSpace) {
		this.editedObject = obj;
		return this.cellEditor.getTableCellEditorComponent(null, // JTable table,
				obj.toString(), // Object value,
				true, // boolean isSelected,
				0, // int row,
				0); // int column)
	}
}
/*
 * $Log: DefaultHandlerEditorSupport.java,v $
 * Revision 1.4  2010/08/25 00:31:54  olga
 * tuning
 *
 * Revision 1.3  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
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
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:52 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:59 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:08:35 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
