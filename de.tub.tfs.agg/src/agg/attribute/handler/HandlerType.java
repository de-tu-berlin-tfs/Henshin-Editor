package agg.attribute.handler;

/**
 * This interface is implemented by Attribute Handlers; provides services for
 * the Attribute Manager. It is used in the "SymbolTable".
 * 
 * @see SymbolTable
 * @version $Id: HandlerType.java,v 1.3 2007/11/01 09:58:20 olga Exp $
 * @author $Author: olga $
 */
public interface HandlerType extends java.io.Serializable {
	static final long serialVersionUID = 247608297125283454L;

	/**
	 * Getting the string representation of this type. Overrides the
	 * "toString()" method of the "Object" class.
	 */
	public String toString();

	/**
	 * Obtaining the actual class rather than just its textual representation.
	 * The name is funny because getClass() is already defined in Object as a
	 * 'final' method.
	 * 
	 * @return A class handle.
	 */
	public Class<?> getClazz();
}
/*
 * $Log: HandlerType.java,v $
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
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:08:17 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
