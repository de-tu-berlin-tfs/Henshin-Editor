package agg.attribute.handler.gui.impl;

import java.awt.Component;
import java.awt.Dimension;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.gui.HandlerTypeEditor;

// import java.util.EventObject;

/**
 * @version $Id: DefaultHandlerTypeEditor.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class DefaultHandlerTypeEditor extends DefaultHandlerEditorSupport
		implements HandlerTypeEditor {

	public DefaultHandlerTypeEditor() {
		super();
	}

	/**
	 * Returns a graphical component for displaying the specified type. The
	 * 'availableSpace' limit should be honoured, since this is a service for
	 * displaying the type in a table cell. However, the renderer can contain
	 * tools (e.g. buttons) for invoking its larger custom renderer. Either
	 * 'handler or 'typeToRender' cannot be null.
	 */
	public Component getRendererComponent(AttrHandler handler,
			HandlerType typeToRender, Dimension availableSpace) {
		return getRendererComponent(typeToRender, availableSpace);
	}

	/**
	 * Returns a graphical component for editing the specified type. The
	 * 'availableSpace' is a recommendation when the editor wishes to be
	 * operatable in a compact table cell and needs not be taken into account.
	 * Either 'handler or 'typeToRender' cannot be null.
	 */
	public Component getEditorComponent(AttrHandler handler,
			HandlerType typeToEdit, Dimension availableSpace) {
		return getEditorComponent(typeToEdit, availableSpace);
	}

	/** Returns the edited type. */
	public HandlerType getEditedType() {
		return (HandlerType) this.editedObject;
	}
}
/*
 * $Log: DefaultHandlerTypeEditor.java,v $
 * Revision 1.3  2010/08/25 00:31:54  olga
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
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:53 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:59 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:39 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
