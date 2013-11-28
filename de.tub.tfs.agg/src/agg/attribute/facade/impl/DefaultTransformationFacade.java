package agg.attribute.facade.impl;

import agg.attribute.AttrContext;
import agg.attribute.AttrException;
import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.AttrMapping;
import agg.attribute.facade.TransformationFacade;
import agg.attribute.impl.AttrTupleManager;

/**
 * Collection of methods for storing and retrieving information in attribute
 * tuples and members.
 * 
 * @version $Id: DefaultTransformationFacade.java,v 1.1 2005/08/25 11:57:00
 *          enrico Exp $
 * @author $Author: olga $
 */
public class DefaultTransformationFacade implements TransformationFacade {

	protected static DefaultTransformationFacade myOnlyInstance = new DefaultTransformationFacade();

	protected DefaultTransformationFacade() {
		super();
	}

	public static TransformationFacade self() {
		return myOnlyInstance;
	}

	// //////////////////////////////////////////////////////
	// Attribute Manager

	/**
	 * Returns the default attribute manager which can be used for advanced
	 * operations not provided by this facade.
	 */
	public AttrManager getAttrManager() {
		return AttrTupleManager.getDefaultManager();
	}

	/**
	 * Creates and returns a new rule context. Typically calls newContext(
	 * AttrMapping.PLAIN_MAP ) of the default AttrManager.
	 */
	public AttrContext newRuleContext() {
		return getAttrManager().newContext(AttrMapping.PLAIN_MAP);
	}

	/**
	 * Returns the left side context to a rule context. Typically calls
	 * newLeftContext( ruleContext ) of the default AttrManager.
	 */
	public AttrContext getLeftContext(AttrContext ruleContext) {
		return getAttrManager().newLeftContext(ruleContext);
	}

	/**
	 * Returns the right side context to a rule context. Typically calls
	 * newRightContext( ruleContext ) of the default AttrManager.
	 */
	public AttrContext getRightContext(AttrContext ruleContext) {
		return getAttrManager().newRightContext(ruleContext);
	}

	// ///////////////////
	// Pre-Match Check

	/**
	 * Checking if matching can be performed with respect to a given rule
	 * context. If the rule context in question is without inconsistencies, this
	 * method remains 'silent'. Otherwise, it throws an exception whose message
	 * text describes the reason.
	 */
	public void checkIfReadyToMatch(AttrContext ruleContext)
			throws AttrException {
		getAttrManager().checkIfReadyToMatch(ruleContext);
	}

	/**
	 * Creates and returns a new match context to a rule context. Typically
	 * calls newContext( AttrMapping.MATCH_MAP, ruleContext ) of the default
	 * AttrManager.
	 */
	public AttrContext newMatchContext(AttrContext ruleContext) {
		return getAttrManager().newContext(AttrMapping.MATCH_MAP, ruleContext);
	}

	// ///////////////////
	// Mapping:

	/**
	 * Mapping between two attribute instances; The mapping is done according to
	 * the context property (rule/match) and is integrated into the context;
	 * 
	 * @param mappingContext
	 *            The context to include the mapping in
	 * @param source
	 *            Mapping source attribute
	 * @param target
	 *            Mapping target attribute
	 * @return A handle to the mapping; it can be used to undo the mapping
	 *         (remove()) or to proceed to the next possible one (next()). If
	 *         the mapping context is that of a match, a match on the attributes
	 *         is tried. If this fails, "null" is returned.
	 */
	public AttrMapping newMapping(AttrContext mappingContext,
			AttrInstance source, AttrInstance target) throws AttrException {
		return getAttrManager().newMapping(mappingContext, source, target);
	}

	/**
	 * Use the next possible attribute mapping;
	 * 
	 * @return "true" if more subsequent mappings exist, "false" otherwise.
	 */
	public boolean nextMapping(AttrMapping mapping) {
		return mapping.next();
	}

	/**
	 * Discard mapping; Removes variable assignments made by a mapping from its
	 * context and dissolves the connection between the attribute instances.
	 */
	public void removeMapping(AttrMapping mapping) {
		mapping.remove();
	}

	// ///////////////////
	// Pre-Transformation Check

	/**
	 * Checking if a transformation can be performed with the attributes with
	 * respect to a given context. If the match context in question is complete
	 * and without inconsistencies, this method remains 'silent'. Otherwise, it
	 * throws an exception whose message text describes the reason.
	 */
	public void checkIfReadyToTransform(AttrContext matchContext)
			throws AttrException {
		getAttrManager().checkIfReadyToTransform(matchContext);
	}

	/**
	 * Getting the number of variables declared by an instance which have no
	 * value assigned to them yet. Each variable name is counted only once, even
	 * if it is used more than once in this tuple.
	 * 
	 * @return The number of free variables.
	 */
	public int getNumberOfFreeVariables(AttrInstance tuple, AttrContext context) {
		return tuple.getNumberOfFreeVariables(context);
	}

	/**
	 * Applying a rule; the substitutions occur "in-place" (in the recipient);
	 * In Graph Transformation, this method is applied to attributes of host
	 * graph objects, "rightSide" being an attribute of the right side of the
	 * rule and "context" being the "match"-context built up by subsequently
	 * matching the attributes of corresponding graphical objects.
	 */
	public void apply(AttrInstance workGraphInst, AttrInstance rightSideInst,
			AttrContext context) {
		workGraphInst.apply(rightSideInst, context);
	}
}

/*
 * $Log: DefaultTransformationFacade.java,v $
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:25 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:56 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:26 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
