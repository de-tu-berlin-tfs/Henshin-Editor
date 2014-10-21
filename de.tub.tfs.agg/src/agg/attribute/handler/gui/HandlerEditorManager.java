package agg.attribute.handler.gui;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;

/**
 * Returning the appropriate editors/renderers. All the return valus can be
 * null, meaning the client should use his default editors and change the edited
 * objects by using the usual interface.
 * 
 * @version $Id: HandlerEditorManager.java,v 1.2 2007/09/10 13:05:51 olga Exp $
 * @author $Author: olga $
 */
public interface HandlerEditorManager {

	public HandlerCustomizingEditor getCustomizingEditor(AttrHandler handler);

	public HandlerTypeEditor getTypeEditor(AttrHandler handler, HandlerType type);

	public HandlerExprEditor getExprEditor(AttrHandler handler,
			HandlerType type, HandlerExpr expr);
}
/*
 * $Log: HandlerEditorManager.java,v $
 * Revision 1.2  2007/09/10 13:05:51  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:27 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:25 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
