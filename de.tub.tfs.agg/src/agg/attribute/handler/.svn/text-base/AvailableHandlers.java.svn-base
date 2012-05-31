package agg.attribute.handler;

import java.util.Vector;

/**
 * The purpose of this class, being the only class in a package of interfaces,
 * is that an attribute manager knows where to find its handlers. Whenever a new
 * handler is installed, its fully qualified pathname has to be added to the
 * static array 'nameList'. That's all a new Handler has to do besides, of
 * course, implementing those methods. All an attribute manager has to do is
 * (besides implementing the SymbolTable interface) calling 'newInstances()'. It
 * then gets an array of attribute handler instances, one for every handler in
 * the mentioned list.
 * 
 * @version $Id: AvailableHandlers.java,v 1.3 2007/11/01 09:58:20 olga Exp $
 * @author $Author: olga $
 */
public class AvailableHandlers {

	/** This is the list to extend by new Handlers. */
	protected static String nameList[] = { "agg.attribute.handler.impl.javaExpr.JexHandler",
	// "agg.attribute.handler.impl.jex.JexHandler",
	// , AnotherHandler, ...
	};

	/**
	 * This is the method to call by an attribute manager.
	 * 
	 * @return an array of attribute handler instances, one for every handler in
	 *         the mentioned list.
	 */
	static public AttrHandler[] newInstances() {
		Vector<Object> instList = new Vector<Object>(10, 10);
		AttrHandler result[];
		Class<?> handlerClass;

		for (int i = 0; i < nameList.length; i++) {
			try {
				handlerClass = Class.forName(nameList[i]);
				instList.addElement(handlerClass.newInstance());
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException("Class " + nameList[i]
						+ " not found.\n" + ex.getMessage());
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		result = new AttrHandler[instList.size()];
		instList.copyInto(result);
		return result;
	}
}
/*
 * $Log: AvailableHandlers.java,v $
 * Revision 1.3  2007/11/01 09:58:20  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.2  2007/09/10 13:05:52  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:08:14 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
