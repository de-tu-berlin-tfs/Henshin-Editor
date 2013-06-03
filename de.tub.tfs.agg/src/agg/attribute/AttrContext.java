package agg.attribute;

import java.io.Serializable;

import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.SymbolTable;
import agg.util.Disposable;

/**
 * Framework for allocation of variables, administration of attribute mappings
 * and application conditions in rules.
 * 
 * @author $Author: olga $
 * @version $Id: AttrContext.java,v 1.3 2007/09/10 13:05:31 olga Exp $
 */
public interface AttrContext extends Serializable, SymbolTable, Disposable {
	static final long serialVersionUID = 4786408901979106117L;

	public AttrConditionTuple getConditions();

	public AttrVariableTuple getVariables();

	public boolean doesAllowComplexExpressions();

	public boolean doesAllowNewVariables();

	public boolean doesAllowEmptyValues();

	public void setAllowVarDeclarations(boolean isAllowed);

	public void setAllowComplexExpr(boolean isAllowed);

	public void setAllowEmptyValues(boolean isAllowed);

	public void setAttrContext(AttrContext source);

	/**
	 * Switching on the freeze mode; mapping removals are deferred until
	 * 'defreeze()' is called.
	 */
	public void freeze();

	/** Perform mapping removals which were delayed during the freeze mode. */
	public void defreeze();

	/**
	 * @return If a match is not possible, the source (left side) attribute
	 *         instance whose match first assigned a value to a variable which
	 *         prevents 'source' from being matched to 'target'; null otherwise.
	 */
	/*
	 * public AttrInstance getMatchObstacle( AttrInstance source, AttrInstance
	 * target );
	 */

	/**
	 * Getting the type of an identifier. getType( String ) and getExpr( String )
	 * allow to use an AttrContext as a SymbolTable when using an AttrHandler.
	 * 
	 * @param name
	 *            Identifier's name
	 * @return Identifier's type
	 */
	public HandlerType getType(String name);

	/**
	 * Getting the value of an identifier. getType( String ) and getExpr( String )
	 * allow to use an AttrContext as a SymbolTable when using an AttrHandler.
	 * 
	 * @param name
	 *            Identifier's name
	 * @return Identifier's value as expression
	 */
	public HandlerExpr getExpr(String name);

	public void removeAllMappings();

}

/*
 * $Log: AttrContext.java,v $
 * Revision 1.3  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/11/09 10:31:05 olga Matching
 * error fixed
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.3 2003/03/05 18:24:06 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:45 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.7 2000/04/05 12:06:42 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
