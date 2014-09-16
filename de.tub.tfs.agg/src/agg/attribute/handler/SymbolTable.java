package agg.attribute.handler;

/**
 * An interface between the Attribute Manager and the Attribute Handlers.
 * Passing of types and values of identifiers. This interface is implemented by
 * the Attribute Manager. A Handler evaluates expressions; the values are
 * assigned by the Manager, never by the Handler; therefore we have no
 * assignment methods in this interface.
 * 
 * @version $Id: SymbolTable.java,v 1.2 2007/09/10 13:05:52 olga Exp $
 * @author $Author: olga $
 */
public interface SymbolTable {

	/**
	 * Getting the type of an identifier.
	 * 
	 * @param name
	 *            Identifier's name
	 * @return Identifier's type
	 */
	public HandlerType getType(String name);

	/**
	 * Getting the value of an identifier.
	 * 
	 * @param name
	 *            Identifier's name
	 * @return Identifier's value as expression
	 */
	public HandlerExpr getExpr(String name);
}
/*
 * $Log: SymbolTable.java,v $
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
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:08:18 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
