package agg.attribute.impl;

import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.attribute.AttrVariableTuple;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.SymbolTable;

/**
 * This is a view onto an underlying ContextCore class object; By this
 * delegation, views with different access rights can share the same context; At
 * this stage, just two access modes are implemented: "LeftRuleSide" and
 * "RightRuleSide"; "RightRuleSide" access does not allow adding or removing of
 * variable declarations;
 * 
 * @see ContextCore
 * @author $Author: olga $
 * @version $Id: ContextView.java,v 1.14 2010/08/23 07:30:49 olga Exp $
 */
public class ContextView extends ManagedObject implements AttrContext,
		SymbolTable {

	static final long serialVersionUID = 6106321395444330038L;


	/** Handle to the actual context core. */
	protected ContextCore core;

	/** Describes the access mode. */
	protected boolean canDeclareVar = true;

	/** Describes the access mode. */
	protected boolean canUseComplexExpr = true;

	/** Describes the access mode. */
	protected boolean canHaveEmptyValues = true;

	/** Describes the access mode. */
	protected boolean canUseInitialExpr = true;

	
	/**
	 * Creates a new root context and returns a full access view for it;
	 * 
	 * @param manager
	 *            The calling Attribute Manager
	 * @param mapStyle
	 *            The kind of mapping that is allowed within this context; it is
	 *            one of:
	 *            <dl>
	 *            <dt> -
	 *            <dd> 'AttrMapping.PLAIN_MAP': In Graph Transform.: rule
	 *            mapping
	 *            <dt> -
	 *            <dd> 'AttrMapping.MATCH_MAP': In Graph Transformation:
	 *            matching
	 *            </dl>
	 */
	public ContextView(AttrTupleManager manager, int mapStyle) {
		this(manager, mapStyle, null);
	}

	/**
	 * Creates a new child context and returns a full access view for it;
	 * 
	 * @param manager
	 *            The calling Attribute Manager
	 * @param mapStyle
	 *            The kind of mapping that is allowed within this context; it is
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
	 *            The view whose context is to be the parent for this view's
	 *            context
	 */
	public ContextView(AttrTupleManager manager, int mapStyle,
			AttrContext parent) {
		super(manager);
		ContextCore parentCore = null;
		if (parent != null) {
			parentCore = ((ContextView) parent).core;
		}
		
		this.core = new ContextCore(manager, mapStyle, parentCore);
	}


	
	/**
	 * Returns a new view which shares another view's context and has the
	 * specified access mode;
	 * 
	 * @param manager
	 *            The calling Attribute Manager
	 * @param source
	 *            The view to share the context with
	 * @param leftRuleSide
	 *            Convenience parameter, sets access control appropriately.
	 */
	public ContextView(AttrTupleManager manager, AttrContext source,
			boolean leftRuleSide) {
		this(manager, ((ContextView) source).core);
		if (leftRuleSide) {
			this.canDeclareVar = true;
			this.canUseComplexExpr = false;
			this.canUseInitialExpr = true;
		} else {
			this.canDeclareVar = true;
			this.canUseComplexExpr = true;
			this.canUseInitialExpr = true;
		}
	}

	/**
	 * Returns a new view for the specified context.
	 * 
	 * @param manager
	 *            The calling Attribute Manager
	 * @param source
	 *            The context to view at.
	 */
	public ContextView(AttrTupleManager manager, ContextCore source) {
		super(manager);
		this.core = source;
	}


	public void dispose() {
		if (this.core != null)
			this.core.dispose();
		this.core = null;
	}

	protected final void finalize() {
	}

	/** 
	 * Creates a new VarTuple instance and rewrites the already existing VarTuple instance.
	 */
	public void resetVariableTuple() {
		this.core.resetVariableTuple();
	}
	
	/**
	 * Creates a new CondTuple instance and rewrites the already existing CondTuple instance.
	 */
	public void resetConditionTuple() {
		this.core.resetConditionTuple();
	}
	
	/*
	 * public boolean isCorrectInputEnforced(){ return
	 * getManager().isCorrectInputEnforced(); }
	 */

	public String getErrorMsg() {
		return this.core.getErrorMsg();
	}

	public void clearErrorMsg() {
		this.core.clearErrorMsg();
	}

	/**
	 * A variable context mins that mainly variables will be used as values of
	 * the graph objects of a graph, so if a rule / match attribute context has
	 * an attribute condition, it cannot be evaluated and will get TRUE as
	 * result. This feature is mainly used for critical pair analysis, where the
	 * attribute conditions will be handled especially. Do not use this setting
	 * for common transformation.
	 */
	public void setVariableContext(boolean b) {
		this.core.setVariableContext(b);
	}

	/**
	 * @see agg.attribute.impl#setVariableContext(boolean b)
	 */
	public boolean isVariableContext() {
		return this.core.isVariableContext();
	}

	// ////////////////////////////////////
	// Begin of AttrContext implementation:

	public AttrConditionTuple getConditions() {
		return this.core.getConditions();
	}

	public AttrVariableTuple getVariables() {
		return this.core.getVariables();
	}

	/**
	 * Returns Vector of mappings (TupleMapping) to a target object.
	 */
	public Vector<TupleMapping> getMappingsToTarget(ValueTuple target) {
		return this.core.getMappingsToTarget(target);
	}
	
	/** Query if we're on the right rule side. */
	public boolean doesAllowComplexExpressions() {
		return (this.canUseComplexExpr);
	}

	/** Query if we're on the left or right rule side. */
	public boolean doesAllowInitialExpressions() {
		return (this.canUseInitialExpr);
	}

	/** Query if we're on the left rule side. */
	public boolean doesAllowNewVariables() {
		return (this.canDeclareVar);
	}

	/** Query if we're on the left rule side. */
	public boolean doesAllowEmptyValues() {
		return (this.canHaveEmptyValues);
	}

	public void setAllowVarDeclarations(boolean isAllowed) {
		this.canDeclareVar = isAllowed;
	}

	public void setAllowComplexExpr(boolean isAllowed) {
		this.canUseComplexExpr = isAllowed;
	}

	public void setAllowInitialExpr(boolean isAllowed) {
		this.canUseInitialExpr = isAllowed;
	}

	public void setAllowEmptyValues(boolean isAllowed) {
		this.canHaveEmptyValues = isAllowed;
	}

	/**
	 * Maybe a distributed graph wants to set an attribute context
	 * 
	 * @param source
	 *            the object to take the information from
	 */
	public void setAttrContext(AttrContext source) {
		ContextView cv = (ContextView) source;
		this.core = cv.core;
		this.canDeclareVar = cv.canDeclareVar;
		this.canUseComplexExpr = cv.canUseComplexExpr;
		this.canHaveEmptyValues = cv.canHaveEmptyValues;
		this.canUseInitialExpr = cv.canUseInitialExpr;
	}

	public void copyAttrContext(AttrContext context) {
		ContextView cv = (ContextView) context;
		this.core.makeCopyOf(((ContextView) context).core);
		
		this.canDeclareVar = cv.canDeclareVar;
		this.canUseComplexExpr = cv.canUseComplexExpr;
		this.canHaveEmptyValues = cv.canHaveEmptyValues;
		this.canUseInitialExpr = cv.canUseInitialExpr;
	}
	
	/**
	 * Switching on of the freeze mode; mapping removals are deferred until
	 * 'defreeze()' is called.
	 */
	public void freeze() {
		this.core.freeze();
	}

	/** Perform mapping removals which were delayed during the freeze mode. */
	public void defreeze() {
		this.core.defreeze();
	}

	// End of AttrContext interface implementation.
	// ////////////////////////////////////////////////

	// ////////////////////////////////////
	// Begin of SymbolTable implementation:

	/**
	 * Implementing the SymbolTable interface for type retrieval.
	 * 
	 * @see SymbolTable
	 */
	public HandlerType getType(String name) {
		if (!isDeclared(name))
			return null;
		return this.core.getDecl(name).getType();
	}

	/**
	 * Implementing the SymbolTable interface for value retrieval.
	 * 
	 * @see SymbolTable
	 */
	public HandlerExpr getExpr(String name) {
		if (!isDeclared(name))
			return null;
		return this.core.getValue(name).getExpr();
	}

	// End of SymbolTable interface implementation.
	// ////////////////////////////////////////////////

	// /////////////////////////////////////////////
	// Wrapping methods

	/** Getting the mapping style (match or plain). */
	public int getAllowedMapping() {
		return this.core.getAllowedMapping();
	}

	public void changeAllowedMapping(int otherMapStyle) {
		this.core.mapStyle = otherMapStyle;
	}
	
	/** Adding another Mapped pair to the actual context. */
	public void addMapping(TupleMapping mapping) {
		this.core.addMapping(mapping);
	}

	/** returns all Mappings */
	public Hashtable<ValueTuple, Vector<TupleMapping>> getMapping() {
		return this.core.getMapping();
	}

	/** Removing a Mapped pair from the actual context. */
	public boolean removeMapping(TupleMapping mapping) {
		return this.core.removeMapping(mapping);
	}

	public void removeAllMappings() {
		this.core.removeAllMappings();
	}

	public int getMapStyle() {
		return this.core.mapStyle;
	}
	
	/**
	 * Tests if a variable has already been declared in this context or in any
	 * of its parents;
	 * 
	 * @param name
	 *            The name of the variable
	 * @return 'true' if "name" is declared, 'false' otherwise
	 */
	public boolean isDeclared(String name) {
		return this.core.isDeclared(name);
	}

	/**
	 * Adding a new declaration; "name" is a key and must not have been
	 * previously used for a declaration in this context or any of its parents
	 * before;
	 * 
	 * @param name
	 *            The name of the variable to declare
	 * @param type
	 *            The type of the variable
	 * @return 'true' on success, 'false' otherwise
	 * @exception ContextRestrictedException
	 *                If this view is restricted
	 */
	public boolean addDecl(AttrHandler handler, String type, String name)
			throws ContextRestrictedException {
		if (!this.canDeclareVar)
			throw new ContextRestrictedException();
		boolean result = this.core.addDecl(handler, type, name);
		/*
		 * if(result) fireAttrChanged( new TupleViewEvent(getVariables(),
		 * AttrEvent.MEMBER_VALUE_AS_VARIABLE,
		 * getVariables().getIndexForMember(getVariables().getVarMemberAt(name))) );
		 */
		return result;
	}

	/**
	 * Removing a declaration from this context; Parent contextes are NOT
	 * considered; Does nothing if the variable "name" is not declared;
	 * 
	 * @param name
	 *            The name of the variable to remove
	 * @exception ContextRestrictedException
	 *                If this view is restricted
	 */
	public void removeDecl(String name) throws ContextRestrictedException {
		if (!this.canDeclareVar)
			throw new ContextRestrictedException();
		this.core.removeDecl(name);
	}

	/**
	 * Checking if a variable can be set to a given value without violating the
	 * application conditions. Note: if the conditions are violated already,
	 * this method returns 'true' for any 'value', unless 'value' contradicts a
	 * previously set non-null value for the variable.
	 */
	public boolean canSetValue(String name, ValueMember value) {
		return this.core.canSetValue(name, value);
	}

	/**
	 * Appending a value to a variable; This will be the current value until a
	 * new value will be appended or this one removed
	 * 
	 * @param name
	 *            The name of the variable
	 * @param value
	 *            The value to append to the variable
	 * @exception NoSuchVariableException
	 *                If no variable 'name' is declared
	 */
	public void setValue(String name, ValueMember value)
			throws NoSuchVariableException {
		this.core.setValue(name, value);
	}

	/**
	 * Removing the value of the specified variable name from this context;
	 * 
	 * @param name
	 *            The name of the variable
	 * @exception NoSuchVariableException
	 *                If no variable 'name' is declared
	 * @exception WrongContextException
	 *                If the value was not assigned in this context
	 */
	public void removeValue(String name) throws NoSuchVariableException {
		this.core.removeValue(name);
	}

}
/*
 * $Log: ContextView.java,v $
 * Revision 1.14  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.13  2010/03/31 22:47:23  olga
 * tuning
 *
 * Revision 1.12  2010/03/31 21:08:13  olga
 * tuning
 *
 * Revision 1.11  2009/07/21 09:38:28  olga
 * ApplRS tuning
 * ObjectFlow inheritance mapping - bug fixed
 *
 * Revision 1.10  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.9  2007/12/10 08:42:58  olga
 * CPA of grammar with node type inheritance for attributed graphs - bug fixed
 *
 * Revision 1.8  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/05/07 07:59:30 olga CSP:
 * extentions of CSP variables concept
 * 
 * Revision 1.5 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.4 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.3 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.2 2005/10/24 09:04:49 olga GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.10 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.9 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.8 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.7 2004/06/23 08:26:56 olga CPs sind endlich OK.
 * 
 * Revision 1.6 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.5 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.4 2003/12/18 16:25:49 olga Tests.
 * 
 * Revision 1.3 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:56 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.15 2000/05/17 11:56:56 olga Testversion an Gabi mit diversen
 * Aenderungen. Fehler sind moeglich!!
 * 
 * Revision 1.14 2000/04/05 12:09:09 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
