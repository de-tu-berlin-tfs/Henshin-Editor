package agg.attribute.impl;

import java.util.HashMap;

import agg.attribute.AttrContext;
import agg.attribute.AttrException;
import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.AttrMapping;
import agg.attribute.AttrType;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AvailableHandlers;
import agg.attribute.view.AttrViewSetting;
import agg.attribute.view.impl.OpenViewSetting;

/**
 * Attribute Tuple Manager - Factory of attribute management; Provides creating
 * services needed by graphical components.
 * 
 * @author $Author: olga $
 * @version $Id: AttrTupleManager.java,v 1.21 2010/11/28 22:11:36 olga Exp $
 */
public class AttrTupleManager extends AttrObject implements
		agg.attribute.AttrManager {

	public HashMap<String,Boolean> classNameLookupMap;

	protected AttrHandler handlers[] = AvailableHandlers.newInstances();

	protected AttrViewSetting defaultOpenView;

	protected AttrViewSetting defaultMaskedView;

	protected AttrViewSetting fixedViewSetting;

	protected ContextView defaultContext;

	protected boolean isCorrectInputEnforced = false;

	private String errorMsg = "";

	private boolean variableContext = false;

	protected static AttrTupleManager myDefaultInstance = new AttrTupleManager();

	
	
	/**
	 * Returns the default instance of AttrManager. Called e.g. by facades.
	 */
	public static AttrManager getDefaultManager() {
		return myDefaultInstance;
	}

	/** Public constructor. */
	public AttrTupleManager() {
		new AttrSession();
		setDebug(true);

		this.classNameLookupMap = new HashMap<String,Boolean>();
		
		this.defaultOpenView = new OpenViewSetting(this);
		this.defaultMaskedView = this.defaultOpenView.getMaskedView();

		this.fixedViewSetting = new OpenViewSetting(this);

		this.defaultContext = makeDefaultContext();
	}

	
	private ContextView makeDefaultContext() {
		ContextView c = new ContextView(this, AttrMapping.PLAIN_MAP);
		c.setAllowVarDeclarations(false);
		c.setAllowComplexExpr(false);
		c.setAllowEmptyValues(false);
		c.setAllowInitialExpr(true);
		return c;
	}
	
	// ///////////////////////////////////////////////
	// Begin of AttrManager interface implementation.

	// ///////////////////
	// Context for variables and parameters:
	public AttrContext newContext(int mapStyle) {
		ContextView c = new ContextView(this, mapStyle, null);
		c.setVariableContext(this.variableContext);
		return c;
	}

	/**
	 * Creating a new attribute context which extends an existing one. In Graph
	 * Transformation, the setting of variables by matching corresponding
	 * graphical objects requires such a construction. It allows for keeping
	 * more that one rule match at a given time;
	 * 
	 * @param mapStyle
	 *            The kind of mapping that is allowed within this context; it is
	 *            one of: - AttrMapping.PLAIN_MAP: In Graph Transformation: rule
	 *            mapping - AttrMapping.MATCH_MAP: In Graph Transformation:
	 *            matching
	 * @param parent
	 *            The context to extend
	 * @return The new attribute context child
	 * @see AttrContext
	 * @see AttrMapping
	 */
	public AttrContext newContext(int mapStyle, AttrContext parent) {
		ContextView c = new ContextView(this, mapStyle, parent);
		c.setVariableContext(this.variableContext);
		return c;
	}

	/*
	public AttrContext makeCopyOf(AttrContext context) {
		ContextView c = new ContextView(this, ((ContextView) context).getMapStyle());
		c.copyAttrContext(context);
		return c;
	}
	*/
	
	
	/**
	 * Creating a full view on an existing attribute context; Through a "full
	 * view" on a context, variables can be declared and values can be assigned
	 * to them by the editor of attribute instances. In Graph Transformation, it
	 * is used for the left-hand-side of a rule.
	 * 
	 * @param context
	 *            The context to generate the view on
	 * @return The new attribute context view
	 * @see AttrContext
	 */
	public AttrContext newLeftContext(AttrContext context) {
		ContextView c = new ContextView(this, context, true);
		c.setVariableContext(this.variableContext);
		return c;
	}

	/**
	 * Creating a view on an existing attribute context, through which variables
	 * can not be declared; they only can be assigned values; In Graph
	 * Transformation, it is used for the right-hand-side of a rule.
	 * 
	 * @param context
	 *            The context to generate the view on
	 * @return The new attribute context view
	 * @see AttrContext
	 */
	public AttrContext newRightContext(AttrContext context) {
		ContextView c = new ContextView(this, context, false);
		c.setVariableContext(this.variableContext);
		return c;
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
	}

	/**
	 * @see agg.attribute.impl#setVariableContext(boolean b)
	 */
	public boolean isVariableContext() {
		return this.variableContext;
	}

	/**
	 * Getting an attribute handler by name.
	 * 
	 * @return The attribute handler with the specified name or 'null'.
	 */
	public AttrHandler getHandler(String name) {
		for (int i = 0; i < this.handlers.length; i++) {
			if (this.handlers[i].getName().equals(name)) {
				return this.handlers[i];
			}
		}
		return null;
	}

	/**
	 * Creating a new attribute type.
	 * 
	 * @return The new attribute type
	 */
	public AttrType newType() {
		return new DeclTuple(this);
	}

	// ///////////////////
	// Instance:

	/**
	 * Creating a new attribute instance of the required type, without a
	 * context. Note that for such attributes, expressions cannot contain
	 * variables. In Graph Transformation, it is used for creating a new
	 * attribute in the host graph.
	 * 
	 * @param type
	 *            The type to use
	 * @return The new attribute instance
	 */
	public AttrInstance newInstance(AttrType type) {
		return newInstance(type, null);
	}

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
	public AttrInstance newInstance(AttrType type, AttrContext context) {
		if (type == null)
			return null;

		if (context == null) {
			return new ValueTuple(this, (DeclTuple) type, this.defaultContext);
//			return new ValueTuple(this, (DeclTuple) type, makeDefaultContext());
		}
		
		return new ValueTuple(this, (DeclTuple) type, (ContextView) context);
	}

	/**
	 * Returns an error message if something gone wrong, otherwise - empty
	 * message.
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void clearErrorMsg() {
		this.errorMsg = "";
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
		if (ruleContext == null) {
			throw new AttrException("Null context supplied.");
		}
		ContextView context = (ContextView) ruleContext;
		String text = "";
		/*
		 * if( ruleContext.getConditions().isFalse() ) { text = "Application
		 * condition [ "+((CondTuple)
		 * ruleContext.getConditions()).getFailedConditionAsString()+" ]
		 * failed."; }
		 */
		CondTuple conds = (CondTuple) context.getConditions();
		for (int k = 0; k < conds.getSize(); k++) {
			CondMember cm = conds.getCondMemberAt(k);
			// System.out.println("AttrTupleManager.checkIfReadyToMatch::
			// condMember: "+cm);
			if (
			// (cm.getMark() != CondMember.NAC)
			// && (cm.getMark() != CondMember.NAC_LHS)
			// && (cm.getMark() != CondMember.PAC)
			// && (cm.getMark() != CondMember.PAC_LHS)
			// && (cm.getMark() != CondMember.NAC_PAC)
			// && (cm.getMark() != CondMember.NAC_PAC_LHS)
					cm.isEnabled()
					&& (cm.getMark() == CondMember.LHS) && cm.isFalse()) {
				text = "Condition:  " + cm.getExprAsText() + "  failed!";
			}
//			System.out.println("AttrTupleManager.checkIfReadyToMatch: error: "+text);
		}
		if (text.length() > 0) {
			this.errorMsg = text;
			throw new AttrException(text);
		}
	}

	// ///////////////////
	// Mapping:

	/**
	 * Mapping between two attribute instances; The mapping is done according to
	 * the context mapping property (total/partial) and is integrated into the
	 * context;
	 * 
	 * @param mappingContext
	 *            The context to include the mapping in
	 * @param source
	 *            Mapping source attribute
	 * @param target
	 *            Mapping target attribute
	 * @return A handle to the mapping; it can be used to undo the mapping
	 *         (remove()) or to proceed to the next possible one (next())
	 * @see agg.attribute.AttrMapping#remove()
	 * @see agg.attribute.AttrMapping#next()
	 */
	public AttrMapping newMapping(AttrContext mappingContext,
			AttrInstance source, AttrInstance target) throws AttrException {
		this.errorMsg = "";
		try {
			return new TupleMapping((ContextView) mappingContext,
					(ValueTuple) source, (ValueTuple) target);
		} catch (AttrImplException ex1) {
			this.errorMsg = "Attributes don't match.";			
			throw new AttrException(this.errorMsg);
		}
	}
	
	public AttrMapping newMappingChild2Parent(AttrContext mappingContext,
			AttrInstance source, AttrInstance target) throws AttrException {
		this.errorMsg = "";
		try {
			return new TupleMapping((ContextView) mappingContext,
					(ValueTuple) source, (ValueTuple) target);
		} catch (AttrImplException ex1) {
			this.errorMsg = "Attributes don't match.";			
			throw new AttrException(this.errorMsg);
		}
	}
	
	// ///////////////////
	// Transformation Check

	/**
	 * Checking if an attributed graph transformation can be performed with
	 * respect to a given context: variables and attribute conditions. If the given
	 * match context is complete and without inconsistencies, this
	 * method remains 'silent'. Otherwise, it throws an exception whose message
	 * text describes the reason.
	 */
	public void checkIfReadyToTransform(AttrContext matchContext)
			throws AttrException {

		if (matchContext == null) {
			this.errorMsg = "Null context supplied.";
			throw new AttrException(this.errorMsg);
		}
		
		this.errorMsg = "";
		VarTuple vars = (VarTuple) matchContext.getVariables();
		for (int k = 0; k < vars.getSize(); k++) {
			VarMember vm = vars.getVarMemberAt(k);
			if (vm.isEnabled()
					&& (vm.getMark() != VarMember.PAC)
					&& (vm.getMark() != VarMember.NAC)
					&& (vm.getMark() != VarMember.GAC)
					&& !vm.isDefinite()) {
				this.errorMsg = "Variable:  " + vm.getName() + "  is not definite!";
			}
		}
		CondTuple conds = (CondTuple) matchContext.getConditions();
		for (int k = 0; k < conds.getSize(); k++) {
			CondMember cm = conds.getCondMemberAt(k);
			if (cm.isEnabled()
					&& (cm.getMark() == CondMember.LHS) 
					&& !cm.isTrue()) {
				this.errorMsg = "Condition:  " + cm.getExprAsText()
						+ "  is not satisfied!";
			}
		}
		if (this.errorMsg.length() > 0) {
			throw new AttrException(this.errorMsg);
		}
	}

	/**
	 * Checking if an attributed graph transformation can be performed with
	 * respect to a given context: if checkVariablesOnly is TRUE then only do check
	 * variables, otherwise - variables and attribute conditions. If the given
	 * match context is complete and without inconsistencies, this
	 * method remains 'silent'. Otherwise, it throws an exception whose message
	 * text describes the reason.
	 */
	public void checkIfReadyToTransform(AttrContext matchContext,
			boolean checkVariablesOnly) throws AttrException {
		if (!checkVariablesOnly) {
			try {
				checkIfReadyToTransform(matchContext);
			} catch (AttrException ex) {
				this.errorMsg = ex.getMessage();
			}
		} else {
			this.errorMsg = "";
			if (matchContext == null) {
				this.errorMsg = "Null context supplied.";
				throw new AttrException(this.errorMsg);
			}

			VarTuple vars = (VarTuple) matchContext.getVariables();
			for (int k = 0; k < vars.getSize(); k++) {
				VarMember vm = vars.getVarMemberAt(k);
				if (vm.isEnabled()
						&& (vm.getMark() != VarMember.PAC)
						&& (vm.getMark() != VarMember.NAC)
						&& (vm.getMark() != VarMember.GAC)
						&& !vm.isDefinite()) {
					this.errorMsg = "Variable:  " + vm.getName() + "  is not definite!";
				}
			}
			if (this.errorMsg.length() > 0) {
				throw new AttrException(this.errorMsg);
			}
		}
	}

	// ///////////////////
	// View Context:

	/**
	 * Creating a new view instance for loose coupling of attribute objects
	 * with their visual representation.
	 */
	public AttrViewSetting newViewSetting() {
		return new OpenViewSetting(this);
	}

	public AttrViewSetting getDefaultOpenView() {
		return this.defaultOpenView;
	}

	public AttrViewSetting getDefaultMaskedView() {
		return this.defaultMaskedView;
	}

	// End of AttrManager interface implementation.
	// ///////////////////////////////////////////////

	public AttrViewSetting getFixedViewSetting() {
		return this.fixedViewSetting;
	}

	public void setDebug(boolean b) {
		VerboseControl.setDebug(b);
	}

	public AttrHandler[] getHandlers() {
		AttrHandler handlersCopy[] = new AttrHandler[this.handlers.length];
		System.arraycopy(this.handlers, 0, handlersCopy, 0, this.handlers.length);
		return handlersCopy;
	}

	public boolean isCorrectInputEnforced() {
		return this.isCorrectInputEnforced;
	}

	public void setCorrectInputEnforced(boolean b) {
		this.isCorrectInputEnforced = b;
	}
	
	/**
	 * Returns the class name if the specified name is a class name,
	 * otherwise - null. 
	 */
	public String isClassName(String name) {
		// System.out.println("AttrTupleManager.isClassName:: "+name);
		String result = null;
		boolean isClass = false;
		try {
//			Class<?> c = 
			Class.forName(name);
			result = name;
			isClass = true;
		} catch (ClassNotFoundException ex) {
		}
		if (!isClass) {
			// construct class name as package+class
			agg.attribute.handler.AttrHandler attrHandlers[] = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().getHandlers();
			for (int h = 0; h < attrHandlers.length; h++) {
				agg.attribute.handler.AttrHandler attrh = attrHandlers[h];
				java.util.Vector<String> packs = ((agg.attribute.handler.impl.javaExpr.JexHandler) attrh)
						.getClassResolver().getPackages();
				for (int pi = 0; pi < packs.size(); pi++) {
					String pack = packs.get(pi);
					// check if class exists
					try {
//						Class<?> c = 
						Class.forName(pack + "." + name);
						result = pack + "." + name;
						isClass = true;
						break;
					} catch (ClassNotFoundException ex) {
					}
				}
				if (isClass)
					break;
			}
		}
		// System.out.println("AttrTupleManager.isClassName:: result: "+result);
		return result;
	}
	
	public String getStaticMethodCall(String aValue) {
		// check the form: $package.class$.static_method
		if (aValue.indexOf("$") == 0) {
			int ind = aValue.substring(1).indexOf("$");
			if (ind > 0) {
				String clstr = aValue.substring(1, ind + 1);
				try {
					Class.forName(clstr);
					String tst = clstr.substring(clstr.indexOf(".") + 1);
					while (tst.indexOf(".") != -1) {
						clstr = tst.concat("");
						tst = clstr.substring(clstr.indexOf(".") + 1);
					}
					clstr = tst.concat("");
					String result = clstr + aValue.substring(ind + 2);
					return result;
				} catch (ClassNotFoundException ex) {}
			}
		} else {
				agg.attribute.handler.AttrHandler 
				attrHandlers[] = agg.attribute.impl.AttrTupleManager.getDefaultManager().getHandlers();
				for (int h = 0; h < attrHandlers.length; h++) {
					agg.attribute.handler.AttrHandler attrh = attrHandlers[h];
					java.util.Vector<String> packs = ((agg.attribute.handler.impl.javaExpr.JexHandler) attrh)
							.getClassResolver().getPackages();
					for (int pi = 0; pi < packs.size(); pi++) {
						String pack = packs.get(pi);
				
						String tst = aValue;
						String pname = null;
						String tmp = "";
						while (tst.indexOf(".") != -1) {
							String next = tst.substring(0, tst.indexOf("."));
							String p = tmp + next;
							if (p.equals(pack)) {
								pname = pack;
								break;
							} else
								tmp = tmp + next;
							tmp = tmp + ".";
							tst = tst.substring(tst.indexOf(".") + 1, tst.length());
						}
						if (pname != null) {
							// cut package name
							String result = aValue.replaceFirst(pname + ".", "");
							// cut method name
							String clstr = result.substring(0, result.indexOf("."));
							try {
								Class.forName(pname + "." + clstr);
								return result;
							} catch (ClassNotFoundException ex) {
							}
						}
					}
				}
		}
		return aValue;
	}
}

/*
 * $Log: AttrTupleManager.java,v $
 * Revision 1.21  2010/11/28 22:11:36  olga
 * new method
 *
 * Revision 1.20  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.19  2010/06/09 11:08:43  olga
 * tuning
 *
 * Revision 1.18  2010/03/31 21:08:13  olga
 * tuning
 *
 * Revision 1.17  2010/03/08 15:37:22  olga
 * code optimizing
 *
 * Revision 1.16  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.15  2008/10/07 07:44:45  olga
 * Bug fixed: usage static methods of user own classes in attribute condition
 *
 * Revision 1.14  2008/05/19 09:19:32  olga
 * Applicability of Rule Sequence - reworking
 *
 * Revision 1.13  2007/12/10 08:42:58  olga
 * CPA of grammar with node type inheritance for attributed graphs - bug fixed
 *
 * Revision 1.12  2007/11/29 08:48:40  olga
 * apply match nextCompletion on to already total match, attr mapping part  - bug fixed
 *
 * Revision 1.11  2007/11/28 08:31:42  olga
 * Match next completion : error message tuning
 *
 * Revision 1.10  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.9  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.8  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.7 2007/06/13 08:33:09 olga Update:
 * V161
 * 
 * Revision 1.6 2007/05/07 07:59:30 olga CSP: extentions of CSP variables
 * concept
 * 
 * Revision 1.5 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.4 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.3 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.2 2005/10/24 09:04:49 olga GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.12 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.11 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.10 2004/06/23 08:26:56 olga CPs sind endlich OK.
 * 
 * Revision 1.9 2004/06/14 12:34:19 olga CP Analyse and Transformation
 * 
 * Revision 1.8 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.7 2004/05/06 17:23:26 olga graph matching OK
 * 
 * Revision 1.6 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.5 2004/01/07 09:37:14 olga test
 * 
 * Revision 1.4 2003/12/18 16:25:48 olga Tests.
 * 
 * Revision 1.3 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/02/03 17:46:30 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.14 2001/05/16 12:25:53 olga In checkIfReadyToMatch ist jetzt die
 * pruefung auf AttrMapping.PLAIN_MAP abgeschaltet. So kann man diese Methode
 * auch schon beim Matchen benutzen um die Attribute Condition zu testen.
 * 
 * Revision 1.13 2001/05/14 12:10:42 olga Aenderungen wegen GenGEd: Match
 * Variable auf Variable - als Ergebnis nach Termersaetzung erscheint die
 * Variable in Klemmern: (x). Nur im Fall mit nur eine Variable werden die
 * Klammern weggelassen. Andere Aenderungen: Tests.
 * 
 * Revision 1.12 2000/06/05 14:07:36 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.11 2000/05/24 10:02:28 olga Nur Testausgaben eingebaut bei der
 * Suche nach dem Fehler in DISAGG : Match Konstante auf Konstante
 * 
 * Revision 1.10 2000/04/05 12:09:00 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
