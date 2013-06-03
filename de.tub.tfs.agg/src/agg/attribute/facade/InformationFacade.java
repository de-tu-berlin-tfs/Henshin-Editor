package agg.attribute.facade;

import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrInstanceMember;
import agg.attribute.AttrManager;
import agg.attribute.AttrObserver;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;

// import agg.attribute.view.*;

/**
 * Collection of methods for storing and retrieving information in attribute
 * tuples and members.
 * 
 * @version $Id: InformationFacade.java,v 1.2 2007/09/10 13:05:50 olga Exp $
 * @author $Author: olga $
 */
public interface InformationFacade {

	// //////////////////////////////////////////////////////
	// Attribute Manager

	/**
	 * Returns the default attribute manager which can be used for advanced
	 * operations not provided by this facade.
	 */
	public AttrManager getAttrManager();

	// //////////////////////////////////////////////////////
	// Java Expression Handler:
	//

	/**
	 * Returns the java expression handler. This can then be used for creating a
	 * tuple type member.
	 */
	public AttrHandler getJavaHandler();

	// //////////////////////////////////////////////////////
	// Observable Pattern:
	//

	/**
	 * Adding a new attribute observer.
	 * 
	 * @param tuple
	 *            The attribute tuple (type or instance) to observe.
	 * @param attrObs
	 *            The attribute observer to be registered.
	 */
	public void addObserver(AttrTuple tuple, AttrObserver attrObs);

	/**
	 * Removing an attribute observer from the list of observers.
	 * 
	 * @param tuple
	 *            The attribute tuple (type or instance) observed.
	 * @param attrObs
	 *            The attribute observer to be registered.
	 */
	public void removeObserver(AttrTuple tuple, AttrObserver attrObs);

	// //////////////////////////////////////////////////////
	// Tuple Type

	/**
	 * Returns a new attribute tuple type, using the default attribute manager.
	 */
	public AttrType createTupleType();

	/**
	 * Adding a member declaration to a tuple type.
	 * 
	 * @param tupleType
	 *            the tuple type to be extended.
	 * @param handler
	 *            attribute handler for the entry type;
	 * @param memberType
	 *            textual representation of the member type;
	 * @param name
	 *            name (selector) of the entry within the attribute tuple. The
	 *            new declaration member is returned and can be extended by
	 *            calling the respective AttrTypeMember methods.
	 */
	public AttrTypeMember addMember(AttrType tupleType, AttrHandler handler,
			String memberType, String name);

	/**
	 * Adding an empty member declaration to a tuple type.
	 * 
	 * @param tupleType
	 *            the tuple type to be extended. The new declaration member is
	 *            returned and can be extended by calling the respective
	 *            AttrTypeMember methods.
	 */
	public AttrTypeMember addMember(AttrType tupleType);

	/**
	 * Delete a member declaration from a tuple type.
	 * 
	 * @param tupleType
	 *            the tuple type.
	 * @param name
	 *            name (selector) of the entry within the attribute tuple.
	 */
	public void deleteMemberAt(AttrType tupleType, String name);

	/**
	 * Delete a member declaration from a tuple type.
	 * 
	 * @param tupleType
	 *            the tuple type.
	 * @param index
	 *            index of the member within the attribute tuple.
	 */
	public void deleteMemberAt(AttrType tupleType, int index);

	/**
	 * Getting a tuple type member by its absolute (view-independent) index.
	 */
	public AttrTypeMember getTypeMemberAt(AttrType tupleType, int index);

	/**
	 * Getting a tuple type member by its declaration name.
	 */
	public AttrTypeMember getTypeMemberAt(AttrType tupleType, String name);

	// //////////////////////////////////////////////////////
	// Type Tuple Member

	/** Retrieving the member name. */
	public String getName(AttrTypeMember memberDecl);

	/** Setting a member type name. */
	public void setName(AttrTypeMember memberDecl, String memberName);

	/**
	 * Retrieving the type. Returns null if no type is set or if the type is not
	 * valid.
	 */
	public HandlerType getType(AttrTypeMember memberDecl);

	/** Retrieving the member type name as string. */
	public String getTypeName(AttrTypeMember memberDecl);

	/** Setting the member type. */
	public void setType(AttrTypeMember memberDecl, String typeName);

	/** Retrieving the member attribute handler. */
	public AttrHandler getHandler(AttrTypeMember memberDecl);

	/** Setting the member attribute handler. */
	public void setHandler(AttrTypeMember memberDecl, AttrHandler h);

	// //////////////////////////////////////////////////////
	// Tuple Instance

	/**
	 * Creating a new attribute instance of the required type and in the given
	 * context or a context view. In Graph Transformation, it is used for
	 * creating a new attribute in a rule.
	 * 
	 * @param type
	 *            The type to use
	 * @param context
	 *            The context to use, can be null
	 * @return The new attribute instance
	 */
	public AttrInstance createTupleInstance(AttrType type, AttrContext context);

	/**
	 * Getting a tuple instance member by its absolute (view-independent) index.
	 */
	public AttrInstanceMember getInstanceMemberAt(AttrInstance tupleInstance,
			int index);

	/**
	 * Getting a tuple instance member by its declaration name.
	 */
	public AttrInstanceMember getInstanceMemberAt(AttrInstance tupleInstance,
			String name);

	/** Retrieving an instance member's type. */
	public AttrTypeMember getDeclaration(AttrInstanceMember instanceMember);

	/** Test, if the member value is set or not. */
	public boolean isSet(AttrInstanceMember instanceMember);

	/**
	 * Retrieving the expression (value) contained in a member. The result can
	 * be queried and set according to the agg.attribute.handler.HandlerExpr
	 * interface.
	 */
	public HandlerExpr getExpr(AttrInstanceMember instanceMember);

	/** Setting the expression (value) contained in this member. */
	public void setExpr(AttrInstanceMember instanceMember, HandlerExpr expr);

	/**
	 * Retrieving the value of a member. If the result is 'null', the reason can
	 * be: 1. The value is set as 'null'; 2. The value is not set at all. For
	 * testing if the value was set as 'null' or not set at all, use 'isSet()'
	 * of this interface.
	 */
	public Object getExprAsObject(AttrInstanceMember instanceMember);

	/**
	 * Setting the value of an instance member directly.
	 * 
	 * @param instanceMember
	 *            The member of an attribute tuple instance.
	 * @param value
	 *            Any object instance.
	 */
	public void setExprAsObject(AttrInstanceMember instanceMember, Object value);

	/** Returns the textual representation of a member's expression. */
	public String getExprAsText(AttrInstanceMember instanceMember);

	/**
	 * Evaluating an expression and setting its value as a member's entry.
	 * 
	 * @param instanceMember
	 *            The member of an attribute tuple instance.
	 * @param expr
	 *            textual expression representation;
	 */
	public void setExprAsEvaluatedText(AttrInstanceMember instanceMember,
			String expr);

	/**
	 * Setting an expression for a member without immediate evaluation. Syntax
	 * and type checking are performed.
	 * 
	 * @param instanceMember
	 *            The member of an attribute tuple instance.
	 * @param expr
	 *            textual expression representation;
	 */
	public void setExprAsText(AttrInstanceMember instanceMember, String expr);

}

/*
 * $Log: InformationFacade.java,v $
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:56 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:20 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
