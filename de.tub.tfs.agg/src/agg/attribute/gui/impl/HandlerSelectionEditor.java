package agg.attribute.gui.impl;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

import agg.attribute.AttrManager;
import agg.attribute.handler.AttrHandler;

//import javax.swing.table.*;
//import java.awt.*;

/**
 * The default editor for selecting an attribute handler.
 * 
 * @version $Id: HandlerSelectionEditor.java,v 1.1 2005/08/25 11:56:59 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class HandlerSelectionEditor extends DefaultCellEditor {

	static final long serialVersionUID = -4218688348462932834L;

	protected static HandlerSelectionEditor myOnlyInstance;

	protected HandlerListModel listModel;

	protected HandlerSelectionEditor(JComboBox cb) {
		super(cb);
	}

	@SuppressWarnings("unchecked")
	public static HandlerSelectionEditor getHandlerSelectionEditor(AttrManager m) {
		if (myOnlyInstance == null) {
			HandlerListModel aListModel = new HandlerListModel();
			@SuppressWarnings("rawtypes")
			JComboBox handlerComboBox = new JComboBox(aListModel);
			handlerComboBox.setEditable(false);
			myOnlyInstance = new HandlerSelectionEditor(handlerComboBox);
			myOnlyInstance.listModel = aListModel;
		}
		myOnlyInstance.listModel.setHandlers(m.getHandlers());
		return myOnlyInstance;
	}
}

@SuppressWarnings({ "serial", "rawtypes" })
class HandlerListModel extends AbstractListModel implements ComboBoxModel {

	protected AttrHandler handlers[];

	protected int selIndx = -1; // 0;

	public void setHandlers(AttrHandler h[]) {
		this.handlers = h;
		fireContentsChanged(this, 0, this.handlers.length);
	}

	public int getSize() {
		if (this.handlers != null)
			return this.handlers.length;
		
		return 0;
	}

	public Object getElementAt(int i) {
		return this.handlers[i].getName();
	}

	public Object getSelectedItem() {
		if (this.selIndx == -1)
			return null;
	
		return this.handlers[this.selIndx].getName();
	}

	public void setSelectedItem(Object selectedHandlerName) {
		String tmpHandlerName;
		for (int i = 0; i < this.handlers.length; i++) {
			tmpHandlerName = this.handlers[i].getName();
			if (tmpHandlerName.equals(selectedHandlerName)) {
				this.selIndx = i;
			}
		}
	}
}
/*
 * $Log: HandlerSelectionEditor.java,v $
 * Revision 1.4  2010/09/23 08:13:18  olga
 * tuning
 *
 * Revision 1.3  2010/03/08 15:36:09  olga
 * code optimizing
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:59 enrico
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
 * Revision 1.5 2000/04/05 12:07:51 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
