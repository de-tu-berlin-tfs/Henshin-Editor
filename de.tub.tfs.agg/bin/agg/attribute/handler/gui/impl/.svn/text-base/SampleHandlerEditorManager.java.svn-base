package agg.attribute.handler.gui.impl;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.gui.HandlerCustomizingEditor;
import agg.attribute.handler.gui.HandlerEditorManager;
import agg.attribute.handler.gui.HandlerExprEditor;
import agg.attribute.handler.gui.HandlerTypeEditor;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.attribute.handler.impl.javaExpr.gui.JexHandlerEditor;

// import java.util.EventObject;

/**
 * @version $Id: SampleHandlerEditorManager.java,v 1.1 2005/08/25 11:56:58
 *          enrico Exp $
 * @author $Author: olga $
 */
public class SampleHandlerEditorManager extends Object implements
		HandlerEditorManager {

	protected static SampleHandlerEditorManager myOnlyInstance = new SampleHandlerEditorManager();

	public static SampleHandlerEditorManager self() {
		return myOnlyInstance;
	}

	protected SampleHandlerEditorManager() {
		super();
	}

	public HandlerCustomizingEditor getCustomizingEditor(AttrHandler handler) {
		if (handler instanceof JexHandler) {
			return new JexHandlerEditor(handler);
		} 
		return null;
		
	}

	public HandlerTypeEditor getTypeEditor(AttrHandler handler, HandlerType type) {
		return null;
	}

	public HandlerExprEditor getExprEditor(AttrHandler handler,
			HandlerType type, HandlerExpr expr) {
		if (handler instanceof JexHandler
				&& type.getClazz() == java.awt.Color.black.getClass()) {
			return new ColorValueEditor(handler);
		} 
		return null;
		
	}
}
/*
 * $Log: SampleHandlerEditorManager.java,v $
 * Revision 1.3  2010/03/08 15:36:44  olga
 * code optimizing
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
 * Revision 1.3 2000/04/05 12:08:40 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
