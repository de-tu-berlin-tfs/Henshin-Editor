package agg.attribute.handler.impl.javaExpr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import agg.attribute.AttrVariableMember;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.SymbolTable;
import agg.attribute.impl.AttrSession;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.VerboseControl;
import agg.attribute.parser.javaExpr.Jex;
import agg.attribute.parser.javaExpr.Node;


/**
 * @version $Id: JexExpr.java,v 1.16 2010/09/23 08:13:35 olga Exp $
 * @author $Author: olga $
 */
public class JexExpr extends Object implements HandlerExpr {

	static protected Jex parser = new Jex();

	protected JexHandler handler;

	protected JexType type;

//	protected String text;

	/** The value of an attribute */
	protected Object value;

	/** Represents the abstract syntax tree of an expression */
	protected Node ast;

	protected int property = Jex.PARSE_ERROR;

	public static final long serialVersionUID = 268212822469784946L;

	public JexExpr() {
	}

	public JexExpr(String exprString, boolean asValue, JexType type)
			throws AttrHandlerException {
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"JexExpr:\n->Constructor");

		if (asValue) {
			this.assignValue(exprString, type);
		} else {
			this.type = type;
//			this.text = exprString.trim();
			this.property = parser.parse(exprString.trim());
			this.ast = parser.getAST();
			
//			if (VerboseControl.logTrace) {
//				AttrSession.logPrintln(VerboseControl.logTrace, "konstant: "
//						+ isConstant() + "\nvariable: " + isVariable()
//						+ "\nkomplex: " + isComplex());
//				this.ast.dump("JexExpr: ");
//			}
			
			if (isConstant()) {
				evaluate(null);
			}
//			else value = new String(text);
		}
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"JexExpr:\n<-Construktor");
	}

	public JexExpr(Object value, JexType type) throws AttrHandlerException {
		this.assignValue(value, type);
	}

	protected void assignValue(Object avalue, JexType atype)
			throws AttrHandlerException {
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"JexExpr:\n->assignValue");
		
		Class<?> clazz = atype.getClazz();
		
		if( this.value != null && !clazz.isInstance( this.value )) { 
//			System.out.println("JexExpr.assignValue:  "
//					+this.value.toString() 
//					+ " is not an instance of " 
//					+ clazz.toString());
			throw new AttrHandlerException( 
					this.value.toString() 
					+ " is not an instance of " 
					+ clazz.toString()); 
		}
		 
		this.type = atype;
		this.value = avalue;
		
		// this.text = (this.value == null ? "null" : this.value.toString());
		// if( this.value != null && this.value instanceof String ) {
		// 		this.text = "\"" + this.text + "\"";
		// }
		
		this.property = Jex.IS_CONSTANT;
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"JexExpr:\n<-assignValue");
	}

	static public Jex getParser() {
		return parser;
	}

	public JexHandler getHandler() {
		if (this.type != null)
			return this.type.handler;
		
		return null;
	}

	/**
	 * Getting the string representation of this value. Overrides the
	 * "toString()" method of the "Object" class.
	 */
	public String toString() {
		return getString();
	}

	public boolean equals(HandlerExpr expr) {
		JexExpr testObject = (JexExpr) expr;
		boolean result = true;
		if (testObject == null || getHandler() != testObject.getHandler()
				|| !this.type.equals(testObject.type)) {
			result = false;
		} else if (this.value != null && testObject.value != null) {
			result = this.value.equals(testObject.value);
		} else {
			if (this.value == null && testObject.value == null) {
				// result = text.equals( testObject.text );

				/* Dieser Fall tritt ein wenn null als Value eingegeben wird. */
				if (this.ast == null && testObject.ast == null)
					return true;
				if (this.ast != null && testObject.ast != null) {
					result = this.ast.equals(testObject.ast);
					// TO DO: die equals Methode soll in Abstr Baum rein!!
					result = this.ast.toString().equals(testObject.ast.toString());
				} else
					result = false;
			} else
				result = false;
		}
		return result;
	}

	public Object getValue() {
		return this.value;
	}

	public JexType getType() {
		return this.type;
	}

	protected void copyFrom(HandlerExpr expr) {
		JexExpr from = (JexExpr) expr;
		this.type = from.type;
		this.value = from.value;
		this.property = from.property;
		if (from.ast != null) {
			// try{
			// from.ast.getString() kopiert keine Objecte
			// TODO
			// this.property = parser.parse( from.ast.getString() );
			// ast = parser.getAST();
			this.ast = from.ast.copy();
			// }
			// catch(AttrHandlerException ahe){
			// ast = from.ast;
			// }
		} else {
			this.ast = null;
			this.property = from.property;
		}
		// text = new String( from.text );
	}

	public HandlerExpr getCopy() {
		JexExpr copy = new JexExpr();
		copy.copyFrom(this);
		return copy;
	}

	public void check(SymbolTable symtab) throws AttrHandlerException {
		if (this.property == Jex.IS_CONSTANT)
			return;
		if (getHandler() != null && this.type != null) {
			getHandler().adaptParser();
			parser.check(getString(), this.type.getClazz(), symtab);
		}
	}

	public void evaluate(SymbolTable symtab) throws AttrHandlerException {
		if (VerboseControl.logTrace) {
			AttrSession.logPrintln(VerboseControl.logTrace,
					"JexExpr:\n->evaluate()");
			AttrSession.logPrintln(VerboseControl.logTrace, "JexExpr: text "
					+ getString() + " vor interpret aufruf");
			AttrSession.logPrintln(VerboseControl.logTrace, "JexExpr: value "
					+ this.value + " vor interpret aufruf");
			AttrSession.logPrintln(VerboseControl.logTrace, "JexExpr: symtab "
					+ symtab + " vor interpret aufruf");
		
			if (symtab instanceof ContextView) {
				ContextView context = (ContextView) symtab;
				AttrSession.logAttrInstance(context.getVariables(),
						"JexExpr: Variablen");
				AttrSession.logPrintln("JexExpr: Variablen:"
						+ context.getVariables().getNumberOfEntries());
				for (int i = 0; i < context.getVariables().getNumberOfEntries(); i++) {
					AttrSession.logPrintln("JexExpr: Variablen bei "
							+ i
							+ ": "
							+ ((AttrVariableMember) context.getVariables()
									.getMemberAt(i)).getExprAsText());
				}
			} else
				AttrSession.logPrintln("JexExpr: symtab ist kein ContextView");
		}
		
		// falls ein matchmapping existiert, nur dann soll ausgewertet werden.
		// boolean matchMapping = true;
		// ContextView contextView = null;
		// if(symtab instanceof ContextView)
		// contextView = (ContextView)symtab;
		// if(contextView != null){
		// matchMapping = contextView.getAllowedMapping() ==
		// AttrMapping.MATCH_MAP;
		// AttrSession.logPrintln(VerboseControl.logTrace,"werte ausdruck aus
		// mit mapping: "+contextView.getAllowedMapping());
		// }

		if (this.value == null) {
			// wir gehen erstmal davon aus, dass die Expression zu einer
			// Konstanten ausgewertet wird.
			this.property = Jex.IS_CONSTANT;
			if (getHandler() != null && this.type != null) {
				getHandler().adaptParser();
				if (symtab != null && mustRewrite(symtab)) {
					this.value = null;
					try {
//						AttrSession.logPrintln(VerboseControl.logJexParser,
//								"JexExpr.evaluate: rewrite");
						parser.rewrite(getAST(), this.type.getClazz(), symtab);
						this.property = Jex.IS_COMPLEX;
					} catch (AttrHandlerException ex) {
//						AttrSession.logPrintln(VerboseControl.logJexParser,
//								"JexExpr.evaluate:  rewriting failed. "
//										+ ex.getMessage());
				
						throw new AttrHandlerException(
								"JexExpr.evaluate::  required type: "
										+ this.type.toString()
										+ "  - rewriting failed. "
										+ ex.getMessage() + "  - value failed.");
					}
				} else if (this.ast != null) {
					try {
//						AttrSession.logPrintln(VerboseControl.logJexParser,
//								"JexExpr.evaluate: interpret");
						this.value = parser.interpret(this.ast, this.type.getClazz(), symtab);
					} catch (AttrHandlerException ex) {
//						AttrSession.logPrintln(VerboseControl.logJexParser,
//								"JexExpr.evaluate:  interpretting failed. "
//										+ ex.getMessage());
						if (ex.getMessage() != null) {
						throw new AttrHandlerException(
								"JexExpr.evaluate::  required type: "
										+ this.type.toString()
										+ "  - interpretting failed. ");
						}
					}
				}
			} else
				this.value = null;
		}
		if (VerboseControl.logTrace) {
			AttrSession.logPrintln(VerboseControl.logTrace, "JexExpr: value "
					+ this.value + " nach evaluate");
			AttrSession.logPrintln(VerboseControl.logTrace,
					"JexExpr:\n<-evaluate()");
		}
	}

	/** Checks the expression if there is any variable which must be rewritten */
	protected boolean mustRewrite(SymbolTable symtab) {
		Vector<String> v = new Vector<String>();
		getAllVariables(v);
		boolean result = false;
		for (int i = 0; i < v.size() && !result; i++) {
			String s = v.elementAt(i);
			HandlerExpr he = symtab.getExpr(s);
			if (he != null) {
				result = !he.isConstant();
			}
		}
		return result;
	}

	public boolean isConstant() {
		return (this.property == Jex.IS_CONSTANT);
	}

	public boolean isVariable() {
		return (this.property == Jex.IS_VARIABLE);
	}

	public boolean isComplex() {
		return (this.property == Jex.IS_COMPLEX);
	}

	protected boolean isAssignableTo(JexExpr expr, SymbolTable symtab) {
		if (expr == null || symtab == null) {
			return false;
		}
		
		if (this.isConstant() && expr.isVariable()) {
			JexExpr currentAssignment = (JexExpr) symtab.getExpr(expr
					.getString());
			if (currentAssignment == null
					|| ((this.value != null) && this.value
							.equals(currentAssignment.value))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if both expression fit together
	 */
	public boolean isUnifiableWith(HandlerExpr expr, SymbolTable symtab) {
		if (this.type == null) {
			return false;
		}
		
		JexExpr testObject = (JexExpr) expr;
		if (testObject == null 
				|| getHandler() != testObject.getHandler()
				|| !this.type.equals(testObject.type)) {
			return false;
		}
//		AttrSession.logPrintln(VerboseControl.logMapping,
//				"JexExpr: Expr not null, Handler are equal, Type are equal");
		boolean result = true;
		/*
		 * es gibt 3 Moeglichkeiten: 
		 * 1. beide Ausdruecke sind konstant und sie sind gleich (die ersten drei Zeilen) 
		 * 2. der eine kann dem anderen zugewiesen werden. 
		 * 3. der andere kann dem einen zugewiesen werden (Umkehrung)
		 */
		result = result && isConstant();
		result = result && expr.isConstant();
		result = result && equals(expr);
		
		result = result || isAssignableTo(testObject, symtab);
		result = result || testObject.isAssignableTo(this, symtab);
		
		return result;
	}

	/** represents the expression as a string */
	public String getString() {
		String result;
		if (this.value != null) {
			result = this.value.toString();
			if (this.value instanceof String)
				result = "\"" + result + "\"";
		} else {
			if (this.ast != null)
				result = this.ast.getString();
			else
				result = "null";
		}
		return result;
	}

	/** Returns the abstract syntax tree which represents the expression */
	public Node getAST() {
		return this.ast;
	}

	/**
	 * fills the vector with the names of all variables which occur in this
	 * abstract syntax tree
	 */
	public void getAllVariables(Vector<String> v) {
		if (getAST() != null)
			getAST().getAllVariablesinExpression(v);
	}

	/***************************************************************************
	 * LOAD - SAVE
	 * *********************************************************************
	 */
	private void readObject(ObjectInputStream ois) throws IOException,
			ClassNotFoundException {
		ObjectInputStream.GetField gf = ois.readFields();
		/* reading fields */
		this.handler = (JexHandler) gf.get("handler", null);

		this.type = (JexType) gf.get("type", null);

		/** The value of an attribute */
		this.value = gf.get("value", null);

		this.property = gf.get("property", Jex.PARSE_ERROR);
		/** Represents the abstract syntax tree of an expression */
		this.ast = (Node) gf.get("ast", null);
		if (this.ast == null && this.value == null) {
			String text = (String) gf.get("text", null);
			if (text != null) {
				JexExpr newExpr = null;
				try {
					newExpr = new JexExpr(text, false, this.type);
				} catch (AttrHandlerException ahe) {
					throw new RuntimeException(ahe.getMessage());
				}
				this.ast = newExpr.ast;
				this.property = newExpr.property;
			}
		}

	}
}

/*
 * $Log: JexExpr.java,v $
 * Revision 1.16  2010/09/23 08:13:35  olga
 * tuning
 *
 * Revision 1.15  2010/03/31 21:07:29  olga
 * tuning
 *
 * Revision 1.14  2010/03/18 18:14:04  olga
 * null pointer bug fixed
 *
 * Revision 1.13  2010/03/08 15:37:01  olga
 * code optimizing
 *
 * Revision 1.12  2009/11/26 10:56:55  olga
 * tuning
 *
 * Revision 1.11  2009/03/19 09:31:06  olga
 * CPE: attr check improved
 *
 * Revision 1.10  2008/12/04 14:30:02  olga
 * Node Type Inheritance: init of parent attributes - bug fixed
 *
 * Revision 1.9  2007/11/05 09:18:19  olga
 * code tuning
 *
 * Revision 1.8  2007/11/01 09:58:19  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/10 13:05:46  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/03/28 10:00:51 olga - extensive
 * changes of Node/Edge Type Editor, - first Undo implementation for graphs and
 * Node/edge Type editing and transformation, - new / reimplemented options for
 * layered transformation, for graph layouter - enable / disable for NACs, attr
 * conditions, formula - GUI tuning
 * 
 * Revision 1.5 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.4 2006/11/02 10:41:26 enrico interface extension for AGG
 * 
 * Revision 1.3 2006/04/10 09:19:30 olga Import Type Graph, Import Graph -
 * tuning. Attr. member type check: if class does not exist. Graph constraints
 * for a layer of layered grammar.
 * 
 * Revision 1.2 2006/01/16 09:36:24 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:57:00 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.11 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.10 2004/01/15 16:43:38 olga ...
 * 
 * Revision 1.9 2003/07/10 14:02:39 olga Tests
 * 
 * Revision 1.8 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.7 2003/01/15 11:33:55 olga System.out Umleitung abgeschaltet
 * 
 * Revision 1.6 2002/12/20 11:36:16 olga Tests
 * 
 * Revision 1.5 2002/12/20 11:25:00 olga Tests
 * 
 * Revision 1.4 2002/12/18 09:15:19 olga Testausgaben raus
 * 
 * Revision 1.3 2002/11/25 14:56:24 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/11/11 09:41:07 olga Nur Testausgaben
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:00 olga Imported sources
 * 
 * Revision 1.19 2001/05/14 12:05:36 olga Unwesentlich. Testausgaben wieder
 * raus.
 * 
 * Revision 1.18 2001/02/15 15:59:55 olga Einige Aenderungen wegen XML
 * 
 * Revision 1.17 2000/12/21 09:48:47 olga In dieser Version wurden XML und GUI
 * Reimplementierung zusammen gefuehrt.
 * 
 * Revision 1.16.6.1 2000/12/04 13:25:50 olga Erste Stufe der GUI
 * Reimplementierung abgeschlossen: - AGGAppl.java optimiert - Print eingebaut
 * (GraGraPrint.java) - GraGraTreeView.java, GraGraEditor.java optimiert - Event
 * eingebaut - GraTra umgestellt
 * 
 * Revision 1.16 2000/06/15 06:53:12 shultzke equals fuer JexType anstatt
 * Objectvergleich
 * 
 * Revision 1.15 2000/06/05 14:07:20 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.14 2000/05/24 16:17:28 olga Error bei Transformation mit Condotion
 * beseitigt. Error bei Variablen in der rechten Regelseite auch beseitigt.
 * 
 * Revision 1.13 2000/05/24 10:01:05 olga Aenderungen wegen dem Fehler in DISAGG :
 * Match Konstante auf Konstante
 * 
 * Revision 1.12 2000/05/17 11:33:32 shultzke diverse Aenderungen. Version von
 * Olga wird erwartet
 * 
 * Revision 1.11 2000/04/05 12:08:43 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.10 2000/03/15 08:17:58 olga Die Aenderungen betraffen nur
 * serialVersionUID in einigen Files, um alte Beispiele zu laden. Noch zu
 * klaeren od wir die alte Beispiele am Leben erhalten wollen.
 * 
 * Revision 1.9 2000/03/14 10:57:14 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren
 * 
 * Revision 1.8 2000/03/03 11:39:41 shultzke Aus der Expression den String durch
 * abstrakten Syntaxbaum ersetzt
 */
