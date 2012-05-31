package agg.attribute;

import java.io.Serializable;

import agg.attribute.handler.AttrHandler;
import agg.attribute.view.AttrViewSetting;

/**
 * Factory class interface for attribute-related objects; Provides creating
 * services needed by graphical components.
 * 
 * @version $Id: AttrManager.java,v 1.4 2010/11/28 22:09:07 olga Exp $
 * @author $Author: olga $
 */

public interface AttrManager extends Serializable {

	static final long serialVersionUID = -8497856472856070842L;

	// ///////////////////
	// Context for variables and parameters:

	/**
	 * Creating a new attribute context which is the root of a context tree;
	 * 
	 * @param mapStyle
	 *            The kind of mapping that is done within this context; it is
	 *            one of:
	 *            <dl>
	 *            <dt> -
	 *            <dd> 'AttrMapping.PLAIN_MAP': In Graph Transform.: rule
	 *            mapping
	 *            <dt> -
	 *            <dd> 'AttrMapping.MATCH_MAP': In Graph Transformation:
	 *            matching
	 *            </dl>
	 * @return The new attribute root context
	 * @see AttrContext
	 * @see AttrMapping
	 */
	public AttrContext newContext(int mapStyle);

	/**
	 * Creating a new attribute context which extends an existing one. In Graph
	 * Transformation, the setting of variables by matching corresponding
	 * graphical objects requires such a construction. It allows for keeping
	 * more that one rule match at a given time;
	 * 
	 * @param mapStyle
	 *            The kind of mapping that is done within this context; it is
	 *            one of:
	 *            <dl>
	 *            <dt> -
	 *            <dd> 'AttrMapping.PLAIN_MAP': In Graph Transform.: rule
	 *            mapping
	 *            <dt> -
	 *            <dd> 'AttrMapping.MATCH_MAP': In Graph Transformation:
	 *            matching
	 *            </dl>
	 * @param parent
	 *            The context to extend
	 * @return The new attribute context child
	 * @see AttrContext
	 * @see AttrMapping
	 */
	public AttrContext newContext(int mapStyle, AttrContext parent);

	/**
	 * Creating a left rule side view for an existing rule context; Here,
	 * variables can be declared, but the assignment of complex expressions to
	 * single attribute values is forbidden.
	 * 
	 * @param context
	 *            The context to generate the view on
	 * @return The new attribute context view
	 * @see AttrContext
	 */
	public AttrContext newLeftContext(AttrContext context);

	/**
	 * Creating a right rule side view for an existing rule context, through
	 * which variables cannot be declared; complex expressions as attribute
	 * values are allowed, but only declared variables may be used.
	 * 
	 * @param context
	 *            The context to generate the view on
	 * @return The new attribute context view
	 * @see AttrContext
	 */
	public AttrContext newRightContext(AttrContext context);

	// ///////////////////
	// Attribute handler:

	/**
	 * Getting an attribute handler by name.
	 * 
	 * @return The attribute handler.
	 */
	public AttrHandler getHandler(String name);

	/**
	 * Getting all attribute handlers that have been registered.
	 */
	public AttrHandler[] getHandlers();

	// ///////////////////
	// Attribute type:

	/**
	 * Creating a new attribute type.
	 * 
	 * @return The new attribute type
	 */
	public AttrType newType();

	// ///////////////////
	// Instance:

	/**
	 * Creating a new attribute instance of the required type, without a
	 * context. Note that for such attributes, expressions must be constants. In
	 * Graph Transformation, it is used for creating a new attribute in the host
	 * graph.
	 * 
	 * @param type
	 *            The type to use
	 * @return The new attribute instance
	 */
	public AttrInstance newInstance(AttrType type);

	/**
	 * Creating a new attribute instance of the required type and in the given
	 * context or a context view. In Graph Transformation, it is used for
	 * creating a new attribute in a rule.
	 * 
	 * @param type
	 *            The type to use
	 * @param context
	 *            The context to use
	 * @return The new attribute instance
	 */
	public AttrInstance newInstance(AttrType type, AttrContext context);

	// ///////////////////
	// Pre-Match Check

	/**
	 * Checking if matching can be performed with respect to a given rule
	 * context. If the rule context in question is without inconsistencies, this
	 * method remains 'silent'. Otherwise, it throws an exception whose message
	 * text describes the reason.
	 */
	public void checkIfReadyToMatch(AttrContext ruleContext)
			throws AttrException;

	// ///////////////////
	// Mapping:

	/**
	 * Mapping between two attribute instances; The mapping is done according to
	 * the context mapping property (match/plain) and is integrated into the
	 * context;
	 * 
	 * @param mappingContext
	 *            The context to include the mapping in
	 * @param source
	 *            Mapping source attribute
	 * @param target
	 *            Mapping target attribute
	 * @return A handle to the mapping; it can be used to undo the mapping
	 *         (remove()) or to proceed to the next possible one (next()). If
	 *         the mapping style for mappingContext is "MATCH_MAP", a match is
	 *         tried and necessary checks concerning non-injectiveness are
	 *         performed. If this fails, "null" is returned.
	 */
	public AttrMapping newMapping(AttrContext mappingContext,
			AttrInstance source, AttrInstance target) throws AttrException;

	// ///////////////////
	// Pre-Transformation Check

	/**
	 * Checking if a transformation can be performed with the attributes with
	 * respect to a given context. If the match context in question is complete
	 * and without inconsistencies, this method remains 'silent'. Otherwise, it
	 * throws an exception whose message text describes the reason.
	 */
	public void checkIfReadyToTransform(AttrContext matchContext)
			throws AttrException;

	public void checkIfReadyToTransform(AttrContext matchContext,
			boolean checkOnlyVariables) throws AttrException;

	// ///////////////////
	// View Context:

	/**
	 * Creating a new mediator instance for loose coupling of attribute objects
	 * with their visual representation.
	 */
	public AttrViewSetting newViewSetting();

	/**
	 * Obtaining the open view of the default view setting ('open' meaning: it
	 * considers permutations, but not hiding of members;).
	 */
	public AttrViewSetting getDefaultOpenView();

	/**
	 * Obtaining the masked view of the default view setting ('masked' meaning:
	 * it considers permutations as well as hiding of members;).
	 */
	public AttrViewSetting getDefaultMaskedView();

	/**
	 * Returns an error message if something gone wrong, otherwise - empty
	 * message.
	 */
	public String getErrorMsg();

	/**
	 * Returns a class name if the specified name is a class name,
	 * otherwise - null. 
	 */
	public String isClassName(String name);
	
	public String getStaticMethodCall(String aValue);
	
}

/*
 * $Log: AttrManager.java,v $
 * Revision 1.4  2010/11/28 22:09:07  olga
 * tuning
 *
 * Revision 1.3  2008/10/07 07:44:46  olga
 * Bug fixed: usage static methods of user own classes in attribute condition
 *
 * Revision 1.2  2007/09/10 13:05:31  olga
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
 * Revision 1.6 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.5 2004/01/07 09:37:10 olga test
 * 
 * Revision 1.4 2003/12/18 16:25:20 olga Add error msgs
 * 
 * Revision 1.3 2003/03/05 18:24:07 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:46 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:06:52 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
