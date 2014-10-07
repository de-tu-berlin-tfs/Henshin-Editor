package agg.attribute.handler.gui.impl;

import java.awt.Component;
import java.awt.Dimension;

import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.gui.HandlerExprEditor;

// import java.util.EventObject;

/**
 * @version $Id: DefaultHandlerExprEditor.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class DefaultHandlerExprEditor extends DefaultHandlerEditorSupport
		implements HandlerExprEditor {

	public DefaultHandlerExprEditor() {
		super();
	}

	/**
	 * Returns a graphical component for displaying the specified expr. The
	 * 'availableSpace' limit should be honoured, since this is a service for
	 * displaying the expr in a table cell. However, the renderer can contain
	 * tools (e.g. buttons) for invoking its larger custom renderer. Either
	 * 'type' or 'exprToRender' cannot be null.
	 */
	public Component getRendererComponent(HandlerType type,
			HandlerExpr exprToRender, Dimension availableSpace) {
		return getRendererComponent(exprToRender, availableSpace);
	}

	/**
	 * Returns a graphical component for editing the specified expr. The
	 * 'availableSpace' is a recommendation when the editor wishes to be
	 * operatable in a compact table cell and needs not be taken into account.
	 * Either 'type' or 'exprToEdit' cannot be null.
	 */
	public Component getEditorComponent(HandlerType type,
			HandlerExpr exprToEdit, Dimension availableSpace) {
		return getEditorComponent(exprToEdit, availableSpace);
	}

	/** Returns the edited expression. */
	public HandlerExpr getEditedExpr() {
		return (HandlerExpr) this.editedObject;
	}
}
/*
 * $Log: DefaultHandlerExprEditor.java,v $
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
 * Revision 1.3 2000/04/05 12:08:37 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
