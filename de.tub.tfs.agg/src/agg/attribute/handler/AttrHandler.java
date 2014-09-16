package agg.attribute.handler;

import java.io.Serializable;

import agg.attribute.parser.javaExpr.ClassResolver;

/**
 * This interface is implemented by Attribute Handlers; It provides services for
 * the Attribute Manager.
 * 
 * @version $Id: AttrHandler.java,v 1.3 2010/11/28 22:10:34 olga Exp $
 * @author $Author: olga $
 */
public interface AttrHandler extends Serializable {
	static final long serialVersionUID = 5710467243044564905L;

	/**
	 * Getting the name of this handler so an attribute manager can display it
	 * in the handlers' menu.
	 * 
	 * @return A (hopefully) meaningful name of the attribute handler.
	 */
	public String getName();

	/**
	 * Getting the type handle for a textual representation, for example "int" ->
	 * int.
	 * 
	 * @param typeString
	 *            The textual representation for the type wanted.
	 * @return The handle for the requested type if such exists, null otherwise.
	 * @exception AttrHandlerException
	 *                When the type cannot be found in the handler.
	 */
	public HandlerType newHandlerType(String typeString)
			throws AttrHandlerException;

	/**
	 * Getting the expression handle by providing the type and a
	 * String-representation of the expression.
	 * 
	 * @param type
	 *            A handle of one of the types created by 'newHandlerType()'.
	 * @return The handle for a newly created expression or...
	 * @exception AttrHandlerException
	 *                When the expression String cannot be a representation of
	 *                an expression of the given type.
	 */
	public HandlerExpr newHandlerExpr(HandlerType type, String expr)
			throws AttrHandlerException;

	/**
	 * Getting the expression handle by providing the type and an appropriate
	 * instance of the type.
	 * 
	 * @param type
	 *            A handle of one of the types created by 'newHandlerType()'.
	 * @return The handle for a newly created expression or...
	 * @exception AttrHandlerException
	 *                When the instance is not of the required type.
	 */
	public HandlerExpr newHandlerValue(HandlerType type, Object value)
			throws AttrHandlerException;

	
	public ClassResolver getClassResolver();
	
	/*
	 * Start an editor, so the user can configure whatever there is to configure
	 * with this handler.
	 * 
	 * @param parent
	 *            The window for which the caller wishes the popping-up dialog
	 *            to be a child.
	 */
	// public void configEdit( Frame parent );
}
/*
 * $Log: AttrHandler.java,v $
 * Revision 1.3  2010/11/28 22:10:34  olga
 * new method
 *
 * Revision 1.2  2007/09/10 13:05:52  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:08:10 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
