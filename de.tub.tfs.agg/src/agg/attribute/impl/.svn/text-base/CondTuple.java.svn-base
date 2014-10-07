package agg.attribute.impl;

import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.AttrConditionMember;
import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrInstance;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.util.XMLHelper;

//import agg.util.Debug;

/**
 * Application conditions. Every instance of ContextCore has exactly one
 * instance of this class.
 * 
 * @author $Author: olga $
 * @version $Id: CondTuple.java,v 1.16 2010/11/28 22:11:36 olga Exp $
 */
@SuppressWarnings("serial")
public class CondTuple extends LoneTuple implements AttrConditionTuple,
		AttrMsgCode {

	// Protected variables

	/** Name of the handler for the boolean type. */
	protected static String boolHandlerName = JexHandler.getLabelName();

	/** Constant for the boolean type name. */
	final protected static String boolTypeName = "boolean";

	/** Constant for the true value. */
	final protected static String trueVal = "true";

	/** Constant for the false value. */
	final protected static String falseVal = "false";

	/** Constant prefix for the condition entry names. */
	final protected static String namePrefix = "c";

	/** Attribute handler for condition expressions. */
	protected AttrHandler condHandler = null;

	/** Boolean type. */
	protected HandlerType boolType = null;

	/**
	 * Current condition number, is used to compose unique names within one
	 * condition tuple.
	 */
	protected int condNum = 0;

	// Public Constructors.
	public CondTuple(AttrTupleManager manager, ContextView context,
			CondTuple parent) {
		super(manager, context, parent);
		getContextView().setAllowVarDeclarations(true);
		getContextView().setAllowComplexExpr(true);
		initClass();

		if (parent != null) {
			if (this.getSize() == 0 && this.parent.getSize() > 0) {
				for (int i = 0; i < this.parent.getSize(); i++) {
					CondMember m = (CondMember) this.parent.getMemberAt(i);
					CondMember mem = (CondMember) this.addCondition(m
							.getExprAsText());
					if (mem != null) {
						mem.setEnabled(m.isEnabled());
						mem.setMark(m.getMark());
					}
				}
			}
			getContextView().setVariableContext(
					parent.getContextView().isVariableContext());
		}
	}

	protected void initClass() {
		if (this.boolType == null) {
			String errMsg = "";
			this.condHandler = this.manager.getHandler(boolHandlerName);
			try {
				this.boolType = this.condHandler.newHandlerType(boolTypeName);
				if (this.manager.classNameLookupMap.get(boolTypeName) == null)
					this.manager.classNameLookupMap.put(boolTypeName, Boolean.TRUE);
			} catch (AttrHandlerException ex) {
				errMsg = ex.getMessage();
			}
			if (this.boolType == null) {
				errMsg += "\nFinding a boolean type for condition expressions failed!\n";
				throw new AttrImplException(NO_SUCH_TYPE, errMsg);
			}
		}
	}

	public void clear() {
		for (int i = 0; i < getSize(); i++) {
			this.deleteMemberAt(i);
		}
		this.condNum = 0;
		this.errorMsg = "";
	}

	public void dispose() {
		super.dispose();
		// type.dispose();
		// type = null;
	}

	public void makeCopyOf(CondTuple tuple) {
		if (this.getSize() == 0 && tuple.getSize() > 0) {
			for (int i = 0; i < tuple.getSize(); i++) {
				CondMember m = (CondMember) tuple.getMemberAt(i);
				CondMember mem = (CondMember) this.addCondition(m
						.getExprAsText());
				if (mem != null) {
					mem.setEnabled(m.isEnabled());
					mem.setMark(m.getMark());
				}
			}
		}
	}
	
	// /////////////////////////////////////////////
	// Protected Methods.

	// Naming of conditions.

	protected String getNextName() {
		return ("" + namePrefix + this.condNum++);
	}

	protected String getNameFor(int index) {
		return ("" + namePrefix + index);
	}

	//

	/** Generic component creation. */
	protected ValueMember newMember(DeclMember decl) {
		return new CondMember(this, decl);
	}

	// /////////////////////////////////////////////
	// Public Methods.

	public CondMember getCondMemberAt(int index) {
		return (CondMember) getMemberAt(index);
	}

	public AttrConditionMember addCondition(String expr) {
		if (expr.equals(""))
			return null;

		getTupleType().addMember(this.condHandler, boolTypeName, getNextName());
		CondMember cm = getCondMemberAt(getSize() - 1);
		cm.setExprAsText(expr);
		if (cm.getErrorMsg().indexOf("Parsing error") != -1) {
			this.errorMsg = cm.getErrorMsg();
		}
		return cm;
	}

	public AttrConditionMember addCondition(int indx, String expr) {
		if (expr.equals(""))
			return null;

		getTupleType().addMember(indx, this.condHandler, boolTypeName, getNextName());
		CondMember cm = getCondMemberAt(indx);
		cm.setExprAsText(expr);
		if (cm.getErrorMsg().indexOf("Parsing error") != -1) {
			this.errorMsg = cm.getErrorMsg();
		}
		return cm;
	}
	
	/** Test, if all members can yield true or false. */
	public boolean isDefinite() {
		for (int i = 0; i < getSize(); i++) {
			if (!getCondMemberAt(i).isDefinite())
				return false;
		}
		return true;
	}

	/** Test, if name can yield true or false. */
	public boolean isDefinite(String name) {
		for (int i = 0; i < getSize(); i++) {
			CondMember cm = getCondMemberAt(i);
			Vector<String> v = cm.getAllVariables();
			if (v.contains(name) && !cm.isDefinite())
				return false;
		}
		return true;
	}

	/** Test, if expr is member of a condition tuple. */
	public boolean contains(String expr) {
		for (int i = 0; i < getSize(); i++) {
			CondMember cm = getCondMemberAt(i);
			if (cm.getExprAsText().equals(expr))
				return true;
		}
		return false;
	}

	public boolean isEvaluable(VarTuple vars) {
		for (int i = 0; i < getSize(); i++) {
			CondMember cm = getCondMemberAt(i);
			if (!cm.isEvaluable(vars)) {
				return false;
			}
		}
		return true;
	}

	String failedCondAsString;

	/** Test, if ANDing of all members yields true. */
	public boolean isTrue() {
		this.failedCondAsString = "";
		for (int i = 0; i < getSize(); i++) {
			CondMember cm = getCondMemberAt(i);
			if (!cm.isEnabled())
				continue;
			else if (!cm.isTrue()) {
				this.failedCondAsString = cm.getExprAsText();
				return false;
			}
		}
		return true;
	}

	/** Test, if ANDing of all members yields true. */
	public boolean isTrue(String name) {
		this.failedCondAsString = "";
		for (int i = 0; i < getSize(); i++) {
			CondMember cm = getCondMemberAt(i);
			Vector<String> v = cm.getAllVariables();
			if (v.contains(name) && !cm.isTrue()) {
				this.failedCondAsString = cm.getExprAsText();
				// System.out.println("CondTuple.isTrue(name) :: failed: "+
				// this.failedCondAsString);
				return false;
			}
		}
		return true;
	}

	/**
	 * Test, if the tuple contains members which can be evaluated and yield
	 * 'false'.
	 */
	public boolean isFalse() {
		this.failedCondAsString = "";
		CondMember member;
		for (int i = 0; i < getSize(); i++) {
			member = getCondMemberAt(i);
			if (!member.isEnabled())
				continue;
			else if (member.isDefinite() && !member.isTrue()) {
				this.failedCondAsString = member.getExprAsText();
				return true;
			}
		}
		return false;
	}

	/**
	 * Test, if the tuple contains members which can be evaluated and yield
	 * 'false'.
	 */
	public boolean isFalse(String name) {
		this.failedCondAsString = "";
		CondMember member;
		for (int i = 0; i < getSize(); i++) {
			member = getCondMemberAt(i);
			Vector<String> v = member.getAllVariables();
			if (v.contains(name) && member.isDefinite() && !member.isTrue()) {
				this.failedCondAsString = member.getExprAsText();
				return true;
			}
		}
		return false;
	}

	public String getFailedConditionAsString() {
		String s = this.failedCondAsString;
		this.failedCondAsString = "";
		return s;
	}

	public void setVariableContext(boolean b) {
		getContextView().setVariableContext(b);
	}

	public Vector<String> getAllVariables() {
		Vector<String> result = new Vector<String>();
		for (int i = 0; i < getSize(); i++) {
			CondMember member = getCondMemberAt(i);
			Vector<String> names = member.getAllVariables();
			for (int j = 0; j < names.size(); j++) {
				String name = names.elementAt(j);
				if (!result.contains(name))
					result.addElement(name);
			}
		}
		return result;
	}

	public boolean usesVariable(String var) {
		for (int i = 0; i < getSize(); i++) {
			CondMember member = getCondMemberAt(i);
			Vector<String> names = member.getAllVariables();
			if (names.contains(var))
				return true;
		}
		return false;
	}

	public boolean compareTo(AttrInstance another) {
		CondTuple vt = (CondTuple) another;
		// compare tuple type
		if (!this.type.compareTo(vt.getTupleType()))
			return false;
		// compare member value
		int length = getSize();
		for (int i = 0; i < length; i++) {
			CondMember v = getCondMemberAt(i);
			CondMember v1 = vt.getCondMemberAt(i);
			if ((v.getExpr() == null) && (v1.getExpr() == null))
				;
			else if ((v.getExpr() == null) && (v1.getExpr() != null))
				return false;
			else if ((v.getExpr() != null) && (v1.getExpr() == null))
				return false;
			else if (!v.getExprAsText().equals(v1.getExprAsText()))
				return false;
		}
		return true;
	}

	public void showConditions() {
		System.out.println("Attr. context conditions: ");
		for (int i = 0; i < getSize(); i++) {
			CondMember c = (CondMember) getMemberAt(i);
			if (c != null)
				System.out.println(c.getExprAsText() + "      (" + c.getMark()+")");
		}
		System.out.println("================================");
	}

	public void XwriteObject(XMLHelper h) {
		int num = getSize();
		for (int i = 0; i < num; i++) {
			CondMember cm = getCondMemberAt(i);
			h.openSubTag("Condition");
			if (!cm.isEnabled())
				h.addAttr("enabled", "false");
			h.openSubTag("Value");
			h.addAttrValue("string", cm.getExprAsText()); //h.addAttrValue("String", cm.getExprAsText());
			h.close();
			h.close();
		}
	}

	public void XreadObject(XMLHelper h) {
		Enumeration<?> en = h.getEnumeration("", null, true, "Condition");
		while (en.hasMoreElements()) {
			h.peekElement(en.nextElement());
			if (h.getDocumentVersion().equals("1.0")) {
				boolean enabled = true;
				Object attr_enabled = h.readAttr("enabled");
				if ((attr_enabled != null)
						&& ((String) attr_enabled).equals("false"))
					enabled = false;
				if (h.readSubTag("Value")) {
					Object obj = null;
					String javaTag = h.readSubTag();
					if (javaTag.equals("java")) {							
						obj = h.getAttrValue("String");
						if (obj == null)
							obj = h.getAttrValue("string");
					}
					if (javaTag.equals("string") || javaTag.equals("String")) {								
						obj = h.getElementData(h.top());
					}
					h.close();
//					obj = h.getAttrValue("String"); // old code
					if (obj instanceof String) {
						// loesche '\n' und mehrere Leerzeichen aus dem String
						String objStr = (String) obj;
						String test = objStr.replaceAll("\n", "");
						while (test.indexOf("  ") != -1) {
							objStr = test.replaceAll("  ", " ");
							test = objStr.toString();
						}
						CondMember cond = (CondMember) addCondition(objStr);
						if (cond != null)
							cond.setEnabled(enabled);
					}
					h.close();
				}
			} else
				addCondition(h.readAttr("value"));
			h.close();
		}
	}

}
/*
 * $Log: CondTuple.java,v $
 * Revision 1.16  2010/11/28 22:11:36  olga
 * new method
 *
 * Revision 1.15  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.14  2010/01/31 16:41:37  olga
 * new method addMember(int, String)
 *
 * Revision 1.13  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.12  2009/01/29 14:30:34  olga
 * CPA - bug fixed
 *
 * Revision 1.11  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.10  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.9 2007/04/19 18:02:02 olga Gluing nodes
 * bug fixed
 * 
 * Revision 1.8 2007/04/19 14:50:04 olga Loading grammar - tuning
 * 
 * Revision 1.7 2007/03/28 10:00:29 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.6 2007/01/31 09:19:29 olga Bug fixed in case of transformating
 * attributed grammar with inheritance and non-injective match
 * 
 * Revision 1.5 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.4 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.3 2006/04/06 09:28:53 olga Tuning of Import Type Graph and Import
 * Graph
 * 
 * Revision 1.2 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.20 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.19 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.18 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.17 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.16 2004/09/23 08:26:43 olga Fehler bei CPs weg, Debug output in
 * file
 * 
 * Revision 1.15 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.14 2004/06/23 08:26:56 olga CPs sind endlich OK.
 * 
 * Revision 1.13 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.12 2004/05/13 17:54:09 olga Fehlerbehandlung
 * 
 * Revision 1.11 2004/04/28 12:46:38 olga test CSP
 * 
 * Revision 1.10 2004/02/05 17:53:38 olga Auswertung von Attr.Conditions
 * optimiert
 * 
 * Revision 1.9 2004/01/15 16:43:38 olga ...
 * 
 * Revision 1.8 2003/12/18 16:25:49 olga Tests.
 * 
 * Revision 1.7 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.6 2003/02/03 17:46:30 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.5 2002/12/19 14:24:28 olga XML Ausgabe korregiert
 * 
 * Revision 1.4 2002/12/05 13:30:50 olga AttrCondition aus alten .ggx wieder
 * einlesbar.
 * 
 * Revision 1.3 2002/11/25 14:56:27 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/09/23 12:23:56 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.8 2001/11/15 16:37:59 olga Nur Tests wegen DIS_AGG
 * 
 * Revision 1.7 2001/02/21 15:49:14 olga Neue Methoden fuer den Parser.
 * 
 * Revision 1.6 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.5 2000/04/05 12:09:05 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
