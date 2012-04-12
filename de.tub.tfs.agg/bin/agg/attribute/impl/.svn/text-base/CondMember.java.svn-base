package agg.attribute.impl;

import java.util.Vector;

import agg.attribute.AttrConditionMember;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.AttrEvent;
import agg.util.XMLHelper;

/**
 * Class for members of condition tuples that are used as application conditions
 * in a context. This is an extension of ValueMember.
 * 
 * @author Boris Melamed (bm) + $Author: olga $
 * @version $Id: CondMember.java,v 1.22 2010/08/23 07:30:49 olga Exp $
 * 
 */
public class CondMember extends ValueMember implements AttrConditionMember {

	static final long serialVersionUID = 1922931197917082378L;

	/* possible mark of a condition */
	public static final int LHS = 0;

	public static final int RHS = 1;
	
	public static final int NAC = 20;
	
	public static final int NAC_LHS = 21;
	
	public static final int NAC_PAC = 22;

	public static final int NAC_PAC_LHS = 23;
	
	public static final int PAC = 30;

	public static final int PAC_LHS = 31;


	private int mark;

	private boolean enabled = true;

	private boolean shifted;
	
	//
	// Public Constructors:
	//

	/**
	 * Creating a new instance with the specified type.
	 * 
	 * @param tuple
	 *            Instance tuple that this value is a member of.
	 * @param decl
	 *            Declaration for this member.
	 */
	public CondMember(CondTuple tuple, DeclMember decl) {
		super(tuple, decl);
		this.enabled = true;
	}

	//
	// Public Methods:
	//

	/** copy the contents of a single entry instance into another. */
	public void copy(ValueMember fromInstance) {
		super.copy(fromInstance);
		setMark(((CondMember) fromInstance).getMark());
	}

	/** Removes this member from its tuple. */
	public void delete() {
		getDeclaration().delete();
	}

	public boolean areVariablesSet() {
		if (getContext() != null) {
			VarTuple varTuple = (VarTuple) getContext().getVariables();
			
			HandlerExpr ex = getExpr();
			Vector<String> v = new Vector<String>();
			ex.getAllVariables(v);
			for (int i = 0; i < v.size(); i++) {
				String s = v.elementAt(i);
				HandlerExpr he = getContext().getExpr(s);
				if (he == null) {
					VarMember var = (VarMember) varTuple.getEntryAt(s);
					if ((var != null) 
							&& (var.getExprAsText() != null)
							&& var.getExprAsText().equals("null")) {
						continue;
					} 
					return false;
				}
			}			
			return true;
		}
		
		return false;		
	}

	/** Test, if the expression can yield true or false. */
	public boolean isDefinite() {
//		System.out.println("CondMember.isDefinite:: "+ getExpr()+" mark: "
//				+mark+" this.enabled "+this.enabled);
		if (!isValid() || getExpr() == null)// || !this.enabled)
			return false;

		boolean result = true;
		try {
			HandlerExpr ex = getExpr().getCopy();
			if ((this.mark == LHS) && !getContext().isVariableContext()) {
				// in this case the condition is fully defined by LHS
				// so it has to be evaluable
				ex.evaluate(getContext());
			}
			else if(getContext().isVariableContext()) {
				// here is VARIABLE context graph (CPA overlap.graph),
				// so it is always defined
				return true;
			}
			else if (areVariablesSet()) {
				// here it is of NAC||NAC_LHS||PAC||PAC_LHS
				// and all used variables are set for exmpl.
				// by NACStar or PACStar next completion
				return true;
			}
			else {
				// otherwise it is not defined
				result = false;
			}
		} catch (AttrHandlerException ex1) {
			if (areVariablesSet())
				return true;
			
			result = false;
		}
		return result;
	}

	/** Test, if the expression yields true. */
	public boolean isTrue() {
		if (!isDefinite()) {
			return false;
		}

		HandlerExpr ex = getExpr().getCopy();
		/*
		 * Falls in der Expression eine Variable vorkommt, die ersetzt werden
		 * muss, ist jegliche Bedingung erfuellt.
		 */
		Vector<String> v = new Vector<String>();
		ex.getAllVariables(v);
		for (int i = 0; i < v.size(); i++) {
			String s = v.elementAt(i);
			HandlerExpr he = getContext().getExpr(s);
			if (he == null) {
				// a variable has no value
//				if (getContext().isVariableContext()) {
//					return false; //true;
//				}
			} else if (!he.isConstant()) {
//				 a value is a variable
				return true;
			} 			
			// a value is a constant
		}
		try {
			ex.evaluate(getContext());
			return ex.toString().equals("true");
		} catch (AttrHandlerException ex1) {
			return false;
		}
	}

	public void setEnabled(boolean b) {
		this.enabled = b;
		fireChanged(AttrEvent.MEMBER_DISABLED);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Test, if this condition can be evaluated and yield 'false'.
	 */
	public boolean isFalse() {
		if (isDefinite() && !isTrue())
			return true;
		
		return false;
	}

	public boolean isEvaluable(VarTuple vars) {
		// System.out.println("CondMember.isEvaluable :: ...");
		String testVarName = null;
		String testVarType = null;
		Vector<String> names = getAllVariables();
		for (int k = 0; k < names.size(); k++) {
			String name = names.elementAt(k);
			VarMember var = vars.getVarMemberAt(name);
			if (var != null) {
				// System.out.println("CondMember.isEvaluable :: Variable :
				// "+var.getName()+" : "+var);
				if (var.getExpr() == null) {
					if ((var.getExprAsText() != null)
							&& var.getExprAsText().equals("null"))
						;// System.out.println("CondMember.isEvaluable ::
					// Variable : "+var.getName()+" exprAsText:
					// "+var.getExprAsText());
					else
						return false;
				} else if (var.getExpr().isVariable()) {
					testVarType = var.getDeclaration().getTypeName();
					if (testVarName == null) {
						// System.out.println(var.getExprAsText());
						if (var.getExprAsText().length() > 1)
							testVarName = var.getExprAsText().substring(1,
									(var.getExprAsText().length() - 1));
						else if (var.getExprAsText().length() == 1)
							testVarName = var.getExprAsText();
						// System.out.println("testVar name: "+testVarName+"
						// type: "+testVarType);
					} else if (testVarName.equals(var.getExprAsText())
							&& testVarType.equals(var.getDeclaration()
									.getTypeName())) {
						// System.out.println("CondMember.isEvaluable ::
						// Variable : "+testVarName+" -- OK");
					} else {
						// System.out.println("CondMember.isEvaluable :: name or
						// type failed! : name: "+var.getExprAsText()+" type:
						// "+var.getDeclaration().getTypeName());
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Return a vector with names of used declared variables.
	 */
	public Vector<String> getAllVariables() {
		Vector<String> resultVector = new Vector<String>();
		if (getExpr() != null)
			getExpr().getAllVariables(resultVector);
		// remove static class name from result vector
		for (int i=0; i<resultVector.size(); i++) {
			String n = resultVector.get(i);
			if (AttrTupleManager.getDefaultManager().isClassName(n) != null) {				
				resultVector.remove(i);
				i--;
			}
		}
		return resultVector;
	}

//	private Class<?> getClass(String name) {
//		ClassResolver classResolver = new ClassResolver();
//		Class<?> c = classResolver.forName(name);
//		return c;
//	}
	
	/**
	 * The mark m marks that this condition member, for example, is a condition of a NAC.
	 * The possible marks are: CondMember.LHS, CondMember.NAC, CondMember.PAC, 
	 * CondMember.NAC_LHS, CondMember.PAC_LHS, CondMember.NAC_PAC, CondMember.NAC_PAC_LHS.
	 * @see CondMember 
	 */
	public void setMark(int m) {
		this.mark = m;
		fireChanged(AttrEvent.MEMBER_MARK);
	}

	public int getMark() {
		return this.mark;
	}

	public void setName(String n) {
		getDeclaration().setName(n);
	}

	public void setShifted(boolean b) {
		this.shifted = b;
	}
	
	public boolean isShifted() {
		return this.shifted;
	}
	
	
	public void XwriteObject(XMLHelper h) {
		throw new RuntimeException(
				"CondMember.XwriteObject called, but it shouldn't");
	}

	public void XreadObject(XMLHelper h) {
		throw new RuntimeException(
				"CondMember.XreadObject called, but it shouldn't");
	}
}
/*
 * $Log: CondMember.java,v $
 * Revision 1.22  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.21  2010/04/29 15:14:07  olga
 * tuning and tests
 *
 * Revision 1.20  2010/03/08 15:37:22  olga
 * code optimizing
 *
 * Revision 1.19  2010/01/31 16:41:37  olga
 * new method addMember(int, String)
 *
 * Revision 1.18  2009/09/03 15:54:51  olga
 * code tuning
 *
 * Revision 1.17  2009/01/29 14:30:34  olga
 * CPA - bug fixed
 *
 * Revision 1.16  2009/01/19 12:34:01  olga
 * AGG tuning
 *
 * Revision 1.15  2008/10/07 07:44:45  olga
 * Bug fixed: usage static methods of user own classes in attribute condition
 *
 * Revision 1.14  2008/05/07 08:37:55  olga
 * Applicability of Rule Sequences with NACs
 *
 * Revision 1.13  2008/02/25 08:44:48  olga
 * Extending of CPA: new class CriticalRulePairAtGraph to get critical
 * matches of two rules at a concret graph.
 *
 * Revision 1.12  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.11  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.10  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.9  2007/09/17 10:50:00  olga
 * Bug fixed in graph transformation by rules with NACs and PACs .
 * AGG help docus extended by new implemented features.
 *
 * Revision 1.8  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.7 2007/06/13 08:33:09 olga Update: V161
 * 
 * Revision 1.6 2007/03/28 10:00:31 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.5 2007/01/31 09:19:29 olga Bug fixed in case of transformating
 * attributed grammar with inheritance and non-injective match
 * 
 * Revision 1.4 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.3 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.2 2005/12/21 14:50:09 olga tests
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.20 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.19 2005/02/09 11:10:32 olga Fehler bei Post Application
 * Constraints behoben. Import/Export Fehler mit aggXXX.jar behoben. CPs
 * berechnung : Variablenumbenennung in Overlapgraphen
 * 
 * Revision 1.18 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.17 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.16 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.15 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.14 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.13 2004/06/23 08:26:56 olga CPs sind endlich OK.
 * 
 * Revision 1.12 2004/06/17 10:21:50 olga Start-Transformation mit Anhalten nach
 * einer Ableitung; CPs Korrektur und Optimierung
 * 
 * Revision 1.11 2004/06/14 12:34:19 olga CP Analyse and Transformation
 * 
 * Revision 1.10 2004/06/09 12:43:31 olga ...
 * 
 * Revision 1.9 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.8 2004/05/13 17:54:09 olga Fehlerbehandlung
 * 
 * Revision 1.7 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.6 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.5 2003/01/15 11:49:17 olga Testausgaben aus
 * 
 * Revision 1.4 2003/01/15 11:35:44 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.3 2002/12/20 11:25:06 olga Tests
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
 * Revision 1.6 2001/02/15 16:00:02 olga Einige Aenderungen wegen XML
 * 
 * Revision 1.5 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.4 2000/04/05 12:09:04 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.3 2000/03/14 10:57:30 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren
 * 
 */
