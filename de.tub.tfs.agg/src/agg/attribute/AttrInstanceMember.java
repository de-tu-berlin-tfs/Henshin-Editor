package agg.attribute;

import agg.attribute.handler.HandlerExpr;

/**
 * The interface for an instance tuple member.
 * 
 * @version $Id: AttrInstanceMember.java,v 1.2 2007/09/10 13:05:31 olga Exp $
 * @author $Author: olga $
 */
public interface AttrInstanceMember extends AttrMember {
	static final long serialVersionUID = 8245278580252379537L;

	/** Retrieving the type. */
	public AttrTypeMember getDeclaration();

	/** Test, if the value is set or not. */
	public boolean isSet();

	/**
	 * Retrieving the expression (value) contained in this member. The result
	 * can be queried and set according to the agg.attribute.handler.HandlerExpr
	 * interface.
	 */
	public HandlerExpr getExpr();

	/** Setting the expression (value) contained in this member. */
	public void setExpr(HandlerExpr expr);

	/**
	 * Retrieving the value of an entry. If the result is 'null', the reason can
	 * be: 1. The value is set as 'null'; 2. The value is not set at all. For
	 * testing if the value was set as 'null' or not set at all, use 'isSet()'
	 * of this interface.
	 */
	public Object getExprAsObject();

	/** Returns the textual representation of the expression. */
	public String getExprAsText();

	/**
	 * Setting the value of an instance member directly.
	 * 
	 * @param value
	 *            Any object instance.
	 */
	public void setExprAsObject(Object value);

	/**
	 * Evaluating an expression and setting its value as this member's entry.
	 * 
	 * @param expr
	 *            textual expression representation;
	 */
	public void setExprAsEvaluatedText(String expr);

	/**
	 * Setting an expression for this member without immediate evaluation.
	 * Syntax and type checking are performed.
	 * 
	 * @param expr
	 *            textual expression representation;
	 */
	public void setExprAsText(String expr);
}
/*
 * $Log: AttrInstanceMember.java,v $
 * Revision 1.2  2007/09/10 13:05:31  olga
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
 * Revision 1.3 2003/03/05 18:24:07 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:46 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:06:50 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
