package agg.attribute.impl;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.util.Disposable;

/**
 * Contains declarations and values of context variables; Note that this class
 * does NOT implement the AttrContext interface; The actual implementation for
 * it is the wrapper class ContextView, which allows to restrict access to a
 * context while sharing actual state (variables, conditions, mappings). An
 * example are the different access rights of the left and the right rule side
 * in graph transformation.
 * 
 * @see ContextView
 * @author $Author: olga $
 * @version $Id: ContextCore.java,v 1.23 2010/08/23 07:30:49 olga Exp $
 */
public class ContextCore extends ManagedObject implements Serializable,
		Disposable {

	static final long serialVersionUID = 4267479295340570839L;


	/** Kind of mapping in this context, PLAIN_MAP or MATCH_MAP. */
	protected int mapStyle;

	/** Parent context of this context, chain of inheritance. */
	protected ContextCore parent = null;

	/**
	 * Table of mappings. (Hashtable of Vectors of TupleMapping, key is the
	 * target object)
	 */
	protected Hashtable<ValueTuple, Vector<TupleMapping>> mappings = new Hashtable<ValueTuple, Vector<TupleMapping>>();

	/** Conditions of this context. */
	protected CondTuple conditions;

	/** Variables and parameters of this context. */
	protected VarTuple variables;

	/** Flag; when set, mapping removals are deferred. */
	transient protected boolean isFrozen = false;

	/** Container with deferred mapping removals. */
	transient protected Vector<TupleMapping> delayedMappingRemovals = null;

	transient private String errorMsg;

	transient private boolean variableContext = false;

//	/** Zaehlt die Objekte */
	// transient private static int COUNTER = 0;
	
	/**
	 * Creating a new root context.
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
	 */
	public ContextCore(AttrTupleManager manager, int mapStyle) {
		this(manager, mapStyle, null);
	}

	/**
	 * Creating a new child context.
	 * 
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
	 *            The parent of the new context
	 */
	public ContextCore(AttrTupleManager manager, int mapStyle,
			ContextCore parent) {
		super(manager);
		this.mapStyle = mapStyle;
		this.parent = parent;
		this.errorMsg = "";
		VarTuple parentVars = null;
		CondTuple parentCond = null;
		if (parent != null) {
			this.variableContext = parent.isVariableContext();
			parentVars = parent.getVariables();
			parentCond = parent.getConditions();
		}
				
		this.variables = new VarTuple(manager, new ContextView(manager, this),
				parentVars);
		this.conditions = new CondTuple(manager, new ContextView(manager, this),
				parentCond);
		// COUNTER++;
	}

	/** 
	 * Creates a new VarTuple instance and rewrites the already existing VarTuple instance.
	 */
	public void resetVariableTuple() {
		VarTuple parentVars = this.parent != null? this.parent.getVariables(): null;
		this.variables = new VarTuple(this.manager, new ContextView(this.manager, this), parentVars);
	}
	
	/**
	 * Creates a new CondTuple instance and rewrites the already existing CondTuple instance.
	 */
	public void resetConditionTuple() {
		CondTuple parentCond = this.parent != null? this.parent.getConditions(): null;
		this.conditions = new CondTuple(this.manager, new ContextView(this.manager, this), parentCond);
	}

	public void makeCopyOf(ContextCore context) {
		this.mapStyle = context.mapStyle;
		this.errorMsg = "";
		this.variableContext = context.isVariableContext();
				
		this.variables = new VarTuple(this.manager, new ContextView(this.manager, this), null);
		this.variables.makeCopyOf(context.getVariables());
		
		this.conditions = new CondTuple(this.manager, new ContextView(this.manager, this), null);
		this.conditions.makeCopyOf(context.getConditions());
	}
	
	public void dispose() {
		if (this.delayedMappingRemovals != null)
			this.delayedMappingRemovals.removeAllElements();
		this.delayedMappingRemovals = null;
		this.manager = null;
		this.parent = null;
		if (this.conditions != null) {
			this.conditions.dispose();
			this.conditions = null;
		}
		if (this.variables != null) {
			this.variables.dispose();
			this.variables = null;
		}
	}

	protected final void finalize() {
		if (this.delayedMappingRemovals != null || this.manager != null || this.parent != null
				|| this.conditions != null || this.variables != null)
			dispose();
		// COUNTER--;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void clearErrorMsg() {
		this.errorMsg = "";
	}

	public CondTuple getConditions() {
		return this.conditions;
	}

	public VarTuple getVariables() {
		return this.variables;
	}

	/** Checking if this context is "frozen". */
	public boolean isFrozen() {
		return this.isFrozen;
	}

	/**
	 * Switching on of the freeze mode; mapping removals are deferred until
	 * 'defreeze()' is called.
	 */
	public void freeze() {
		if (this.delayedMappingRemovals == null) {
			this.delayedMappingRemovals = new Vector<TupleMapping>(30);
		}
		this.isFrozen = true;
	}

	/** Perform mapping removals which were delayed during the freeze mode. */
	public void defreeze() {
		this.isFrozen = false;
		performDelayedRemove();
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
		this.variableContext = b;
		if (this.parent != null) {
			this.parent.setVariableContext(b);
		}
	}

	/**
	 * @see agg.attribute.impl#setVariableContext(boolean b)
	 */
	public boolean isVariableContext() {
		return this.variableContext;
	}

	/** Getting the mapping style (match or plain). */
	public int getAllowedMapping() {
		return this.mapStyle;
	}

	/** Adding a new mapping to this context. */
	public void addMapping(TupleMapping mapping) {
		// Thread.dumpStack();
		ValueTuple target = mapping.getTarget();
		Vector<TupleMapping> mappingsToTarget = this.mappings.get(target);
		if (mappingsToTarget == null) {
			mappingsToTarget = new Vector<TupleMapping>();
			this.mappings.put(target, mappingsToTarget);
		}
		mappingsToTarget.addElement(mapping);
	}

	/** returns all mappings */
	protected Hashtable<ValueTuple, Vector<TupleMapping>> getMapping() {
		return this.mappings;
	}

	/**
	 * Returns Vector of mappings (TupleMapping) to a target object.
	 */
	public Vector<TupleMapping> getMappingsToTarget(ValueTuple target) {
		Vector<TupleMapping> mappingsToValue = this.mappings.get(target);
		return mappingsToValue;
	}
	
	/**
	 * Removing a mapping.
	 * 
	 * @return 'true' if the mapping was contained in this context at all,
	 *         'false' otherwise.
	 */
	public boolean removeMapping(TupleMapping mapping) {
		ValueTuple target = mapping.getTarget();
		Vector<TupleMapping> mappingsToTarget = this.mappings.get(target);
		if (mappingsToTarget == null) {
			return false;
		}
		if (this.isFrozen()) {
			if (!mappingsToTarget.contains(mapping)) {
				return false;
			} 
			this.delayedMappingRemovals.addElement(mapping);
			
		} else {
			mapping.removeNow();
		}
		return mappingsToTarget.removeElement(mapping);
	}

	public void removeAllMappings() {
		final Enumeration<ValueTuple> keys = this.mappings.keys();
		while (keys.hasMoreElements()) {
			ValueTuple key = keys.nextElement();
			Vector<TupleMapping> mappingsToTarget = this.mappings.get(key);
			for (int i=0; i<mappingsToTarget.size(); i++) {
				TupleMapping mapping = mappingsToTarget.get(i);
				mapping.removeNow();
			}
			mappingsToTarget.clear();
		}
		this.mappings.clear();
	}

	/** Removing the mappings in the 'delayedMappingRemovals' container. */
	private void performDelayedRemove() {
		if (this.delayedMappingRemovals != null) {
			int size = this.delayedMappingRemovals.size();
			for (int i = 0; i < size; i++) {
				this.delayedMappingRemovals.elementAt(i).removeNow();
			}
			this.delayedMappingRemovals.removeAllElements();
		}
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
		return (getVariables() != null && getVariables().isDeclared(name));
	}

	/**
	 * Adding a new declaration; "name" is a key and must not have been
	 * previously used for a declaration in this context or any of its parents.
	 * 
	 * @param name
	 *            The name of the variable to declare
	 * @param type
	 *            The type of the variable
	 * @return 'true' on success, 'false' otherwise
	 */
	public boolean addDecl(AttrHandler handler, String type, String name) {
		if (this.variables != null) {
			try {
				this.variables.declare(handler, type, name);
					return true;
			} catch (AttrImplException ex) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Removing a declaration from this context; Parent contextes are NOT
	 * considered; Does nothing if the variable "name" is not declared;
	 * 
	 * @param name
	 *            The name of the variable to remove
	 */
	public void removeDecl(String name) {
		getVariables().deleteLeafDeclaration(name);
	}

	/**
	 * Getting the declaration (type) of a variable;
	 * 
	 * @param name
	 *            The name of the variable
	 * @return Type of the specified variable
	 * @exception NoSuchVariableException
	 *                If no variable 'name' is declared
	 */
	public DeclMember getDecl(String name) throws NoSuchVariableException {
		DeclMember t = getVariables().getTupleType().getDeclMemberAt(name);
		return t;
	}

	/**
	 * Checking if a variable can be set to a given value without violating the
	 * application conditions. Note: if the conditions are violated already,
	 * this method returns 'true' for any 'value', unless 'value' contradicts a
	 * previously set non-null value for the variable.
	 */
	public boolean canSetValue(String name, ValueMember value) {
		ValueMember prevValue;
		boolean doesBreakCondition = false;
		this.errorMsg = "";
		prevValue = getVariables().getVarMemberAt(name);
		if (prevValue == null) {
			this.errorMsg = "No such variable: " + name;
			return false;
		}
		if (prevValue.getExpr() == null) {
			// CONDITION CHECK WIRD HIER NICHT MEHR GEMACHT,
			// SONDERN IN Completion_CSP
			/*
			 * if(!getConditions().isDefinite(name) ||
			 * getConditions().isTrue(name)){ setValue( name, value );
			 * if(getConditions().isDefinite(name) &&
			 * !getConditions().isTrue(name)){ this.errorMsg = "Attribute condition [
			 * "+getConditions().getFailedConditionAsString()+ " ] failed.";
			 * doesBreakCondition = true; } removeValue( name ); }
			 */
			return !doesBreakCondition;
		} else if (prevValue.equals(value)) {
			return true;
		} else {
			this.errorMsg = "Cannot set attribute value.";
			return false;
		}
	}

	/**
	 * Setting a variable value.
	 * 
	 * @param name
	 *            Name of the variable to assign to
	 * @param value
	 *            Value to assign
	 * @exception NoSuchVariableException
	 *                If the name is not declared in this context.
	 */
	public void setValue(String name, ValueMember value)
			throws NoSuchVariableException {
		
		VarMember vm = getVariables().getVarMemberAt(name);
		if (vm == null)
			throw new NoSuchVariableException(name);
		
		
		HandlerExpr he = value.getExpr();
		vm.unifyWith(he);
		if (value.getExprAsText().equals("null")) {
			vm.setExprAsText("null");
		}
		
	}

	/**
	 * Removing a variable
	 * 
	 * @param name
	 *            Name of the variable to remove
	 * @exception NoSuchVariableException
	 *                If the name is not declared in this context.
	 */
	public void removeValue(String name) throws NoSuchVariableException {
		if (isDeclared(name))
			getVariables().getVarMemberAt(name).undoUnification();
		else
			; // throw new NoSuchVariableException( name );
	}

	/**
	 * Getting the value of a variable.
	 * 
	 * @param name
	 *            Name of the variable to get the value from
	 * @exception NoSuchVariableException
	 *                If the name is not declared in this context.
	 */
	public ValueMember getValue(String name) {
		ValueMember value;
		value = getVariables().getVarMemberAt(name);
		if (value == null)
			throw new NoSuchVariableException(name);
		return value;
	}

}
/*
 * $Log: ContextCore.java,v $
 * Revision 1.23  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.22  2010/04/28 15:15:39  olga
 * tuning
 *
 * Revision 1.21  2010/04/27 12:20:07  olga
 * addDecl: null pointer bug fixed
 *
 * Revision 1.20  2010/03/31 22:47:22  olga
 * tuning
 *
 * Revision 1.19  2010/03/31 21:08:13  olga
 * tuning
 *
 * Revision 1.18  2010/03/08 15:37:22  olga
 * code optimizing
 *
 * Revision 1.17  2009/11/26 17:37:15  olga
 * tests
 *
 * Revision 1.16  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.15  2008/07/14 07:35:46  olga
 * Applicability of RS - new option added, more tuning
 * Node animation - new animation parameter added,
 * Undo edit manager - possibility to disable it when graph transformation
 * because it costs much more time and memory
 *
 * Revision 1.14  2008/05/19 09:19:32  olga
 * Applicability of Rule Sequence - reworking
 *
 * Revision 1.13  2007/12/10 08:42:58  olga
 * CPA of grammar with node type inheritance for attributed graphs - bug fixed
 *
 * Revision 1.12  2007/12/03 08:35:13  olga
 * - Some bugs fixed in visualization of morphism mappings after deleting and creating
 * nodes, edges
 * - implemented: matching with non-injective NAC and Match morphism
 *
 * Revision 1.11  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.10  2007/09/17 10:50:00  olga
 * Bug fixed in graph transformation by rules with NACs and PACs .
 * AGG help docus extended by new implemented features.
 *
 * Revision 1.9  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.8 2007/05/07 07:59:30 olga CSP:
 * extentions of CSP variables concept
 * 
 * Revision 1.7 2007/01/11 10:21:19 olga Optimized Version 1.5.1beta , free for
 * tests
 * 
 * Revision 1.6 2006/12/13 13:32:58 enrico reimplemented code
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
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.14 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.13 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.12 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.11 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.10 2004/06/23 08:26:56 olga CPs sind endlich OK.
 * 
 * Revision 1.9 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.8 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.7 2004/03/18 17:41:53 olga
 * 
 * rrektur an CPs und XML converter
 * 
 * Revision 1.6 2004/02/12 17:44:02 olga Post Application Condition und alles
 * was damit zusammen haengt wird erstmal zurueckgestellt.
 * 
 * Revision 1.5 2004/02/05 17:53:38 olga Auswertung von Attr.Conditions
 * optimiert
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
 * Revision 1.11 2000/03/14 10:57:32 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren
 * 
 */
