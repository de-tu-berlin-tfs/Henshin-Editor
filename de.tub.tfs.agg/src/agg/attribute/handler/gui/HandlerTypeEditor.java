package agg.attribute.handler.gui;

import java.awt.Component;
import java.awt.Dimension;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerType;

/**
 * This interface is implemented by attribute handler type editors; it provides
 * services for the Attribute instance (tuple) editors.
 * 
 * @version $Id: HandlerTypeEditor.java,v 1.2 2007/09/10 13:05:51 olga Exp $
 * @author $Author: olga $
 */
public interface HandlerTypeEditor extends HandlerEditor {

	/**
	 * Returns a graphical component for displaying the specified type. The
	 * 'availableSpace' limit should be honoured, since this is a service for
	 * displaying the type in a table cell. However, the renderer can contain
	 * tools (e.g. buttons) for invoking its larger custom renderer. Either
	 * 'handler or 'typeToRender' cannot be null.
	 */
	public Component getRendererComponent(AttrHandler handler,
			HandlerType typeToRender, Dimension availableSpace);

	/**
	 * Returns a graphical component for editing the specified type. The
	 * 'availableSpace' is a recommendation when the editor wishes to be
	 * operatable in a compact table cell and needs not be taken into account.
	 * Either 'handler or 'typeToRender' cannot be null.
	 */
	public Component getEditorComponent(AttrHandler handler,
			HandlerType typeToEdit, Dimension availableSpace);

	/** Returns the edited type. */
	public HandlerType getEditedType();
}
/*
 * $Log: HandlerTypeEditor.java,v $
 * Revision 1.2  2007/09/10 13:05:51  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:27 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:30 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
