package agg.attribute.handler;

import java.util.Vector;

import agg.attribute.parser.javaExpr.Node;

/**
 * This interface is implemented by Attribute Handlers; provides services for
 * the Attribute Manager. It is used in the "SymbolTable".
 * 
 * @see SymbolTable
 * @version $Id: HandlerExpr.java,v 1.2 2007/09/10 13:05:52 olga Exp $
 * @author $Author: olga $
 */
public interface HandlerExpr extends java.io.Serializable, Cloneable {

	static final long serialVersionUID = -3331713981402257236L;

	public String toString();

	/**
	 * Obtaining the value.
	 * 
	 * @return The value as an Object instance.
	 */
	public Object getValue();

	/**
	 * Obtaining a copy of the message receiving expression.
	 * 
	 * @return The copy.
	 */
	public HandlerExpr getCopy();

	/**
	 * Type-check the expression under a given symbol table with declarations.
	 * 
	 * @param symTab
	 *            the declaration Table to use for the checking
	 * @exception AttrHandlerException
	 *                if the checking yields an inconsitency. An exception is
	 *                preferred over a return value as it is a ready-to-use
	 *                propagation mechanism with specific information easily
	 *                attached.
	 */
	public void check(SymbolTable symTab) throws AttrHandlerException;

	/**
	 * Type-check the constant expression under a given symbol table with declarations.
	 * 
	 * @param symtab
	 *            the declaration Table to use for the checking
	 * @exception AttrHandlerException
	 *                if the checking yields an inconsistency. An exception is
	 *                preferred over a return value as it is a ready-to-use
	 *                propagation mechanism with specific information easily
	 *                attached.
	 */
	public void checkConstant(SymbolTable symtab) throws AttrHandlerException;
	
	/**
	 * Evaluate the expression under a given symbol table containing variable
	 * declarations and (hopefully) also the assignments.
	 * 
	 * @param symTab
	 *            the declaration Table to use for the evaluation
	 * @exception AttrHandlerException
	 *                if the evaluation yields an error (a missing value for a
	 *                variable etc.)
	 */
	public void evaluate(SymbolTable symTab) throws AttrHandlerException;

	/** returns the string representation of an expression */
	public String getString();

	/**
	 * Checks if the expression is constant. Needed for keeping users from
	 * giving expressions that are not allowed in a context.
	 * 
	 * @return 'true' if constant, 'false' sonst.
	 */
	public boolean isConstant();

	/**
	 * Checks if the expression is a single Variable. Needed for keeping users
	 * from giving expressions that are not allowed in a context.
	 * 
	 * @return 'true' if a variable, 'false' sonst.
	 */
	public boolean isVariable();

	/**
	 * Checks if the expression is a complex one (like x+1). Needed for keeping
	 * users from giving expressions that are not allowed in a context.
	 * 
	 * @return 'true' if is complex, 'false' sonst.
	 */
	public boolean isComplex();

	public boolean equals(HandlerExpr testObject);

	/**
	 * Checks if the recipient can be "matched", "unified" with the first
	 * parameter under a certain variable assignment.
	 * 
	 * @param expr
	 *            The expression to check if unifiable with;
	 * @param symTab
	 *            Contains the variable assignments under which to perform the
	 *            test.
	 * @return 'true' if the two expressions ar matching, 'false' sonst.
	 */
	public boolean isUnifiableWith(HandlerExpr expr, SymbolTable symTab);

	/** Returns the abstract syntax tree which represents the expression */
	public Node getAST();

	/**
	 * fills the vector with the names of all variables which occur in this
	 * expression
	 */
	public void getAllVariables(Vector<String> v);
}
/*
 * $Log: HandlerExpr.java,v $
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
 * Revision 1.7 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.6 2000/04/05 12:08:15 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
