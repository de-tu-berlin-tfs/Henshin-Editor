package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

import java.util.Vector;

import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;

/**
 * @version $Id: ASTId.java,v 1.9 2010/08/23 07:31:25 olga Exp $
 * @author $Author: olga $
 */
public class ASTId extends SimpleNode {

	static final long serialVersionUID = 1L;

	String name;

	boolean isClass = false;

	ASTId(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTId(id);
	}

	protected boolean isConstantExpr() {
		return false;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		Class<?> clazz = getNodeClass();
		String cname = "";

		if (this.isClass) {
			cname += "(Class name) ";
		}
		cname += "\"" + this.name + "\"";
		if (clazz != null) {
			cname += " [" + clazz.toString() + "]";
		}
		return this.identifier + " " + cname;
	}

	public void checkContext() {
		Class<?> clazz = null;
		HandlerType tabEntry = null;

		if (classResolver != null) {
			clazz = classResolver.forName(this.name);
		} else {
			try {
				clazz = Class.forName(this.name);
			} catch (ClassNotFoundException e) {
				/*
				 * Do nothing, it's just a sign that the name belongs to a
				 * variable, not a class.
				 */
			}
		}

		if (clazz == null) {
			this.isClass = false;
			if (symtab == null) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"ASTId: symtab is null.");
				throw new ASTIdNotDeclaredException(this.name);
			}
			
			if ((tabEntry = symtab.getType(this.name)) == null) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"ASTId: symtab.getType(" + this.name + ") is null.");
				throw new ASTIdNotDeclaredException(this.name);
			}
			if ((clazz = tabEntry.getClazz()) == null) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"ASTId: symtab.getType(" + this.name
//								+ ").getClazz() is null.");
				throw new ASTIdNotDeclaredException(this.name);
			}
		} else {
			this.isClass = true;
		}
		setNodeClass(clazz);
	}

	public void interpret() {
		Object value = null;
		HandlerExpr tabEntry = null;

		checkContext();

		if (this.isClass) {
			value = getNodeClass();
		} 
		else {
			if (symtab == null
					|| ((tabEntry = symtab.getExpr(this.name)) == null)
					|| ((value = tabEntry.getValue()) == null)) {
				
//				if (symtab == null) {
//						AttrSession.logPrintln(VerboseControl.logJexParser,
//								"ASTId: symtab is null.");
//				} else if ((tabEntry = symtab.getExpr(this.name)) == null) {
//							AttrSession.logPrintln(VerboseControl.logJexParser,
//									"ASTId: symtab.getExpr(" + this.name + ") is null.");
//				} else if ((value = tabEntry.getValue()) == null) {
//								AttrSession.logPrintln(VerboseControl.logJexParser,
//										"ASTId: symtab.getExpr(" + this.name
//												+ ").getValue() is null.");
//				} 
				
				throw new ASTMissingValueException("Missing value exception for: "+this.name);
			}
		}
				
		top++;
		Node obj = ObjectConstNode
				.jjtCreate(this.identifier + " to ObjectConstNode");				
		((ObjectConstNode) obj).obj = value;
		obj.jjtSetParent(jjtGetParent());
		((ObjectConstNode) obj).setNodeClass(getNodeClass());
		jjtGetParent().replaceChild(this, obj);
		jjtSetParent(null);
		if (value != null)
			obj.interpret();
	}

	/**
	 * Rewrites a single id.
	 */
	public void rewrite() {
		// System.out.println( "ASTId.rewrite(): ...");
		checkContext();
		HandlerExpr newExpr = symtab.getExpr(this.name);
		if (newExpr == null) {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"ASTId: symtab.getExpr(" + this.name + ") is null.");
			throw new ASTMissingValueException("Missing value exception for: "+this.name);
		}
		Node newTree = newExpr.getAST();
		/*
		 * Falls im AST zwei Expression aufeinander folgen, kann einen geloescht
		 * werden. beide auf selben parent zeigen, im parent child ersetzen im
		 * alten knoten parent abhaengen
		 */
		if ((newTree instanceof ASTExpression)
				&& (jjtGetParent() instanceof ASTExpression)) {
			Node tmp = newTree.jjtGetChild(0);
			((ASTExpression) newTree).children.removeElement(tmp);
			tmp.jjtSetParent(null);
			newTree = tmp;
		}
		newTree.jjtSetParent(jjtGetParent());
		jjtGetParent().replaceChild(this, newTree);
		jjtSetParent(null);
	}

	public String getString() {
		return this.name;
	}

	/**
	 * fills the vector with the names of all variables which occur in this
	 * abstract syntax tree
	 */
	public void getAllVariablesinExpression(Vector<String> v) {
		v.addElement(this.name);
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTId) copy).name = new String(this.name);
		((ASTId) copy).isClass = this.isClass;
		return copy;
	}
}
/*
 * $Log: ASTId.java,v $
 * Revision 1.9  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.8  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.7  2010/03/18 18:16:14  olga
 * loop bug fixed
 *
 * Revision 1.6  2009/10/26 10:04:26  olga
 * tuning and tests
 *
 * Revision 1.5  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/12/13 13:32:58 enrico reimplemented
 * code
 * 
 * Revision 1.2 2006/01/16 09:37:01 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.5 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/02/06 16:53:17 olga Test
 * 
 * Revision 1.3 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.2 2002/09/23 12:24:01 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.8 2000/05/17 11:57:08 olga Testversion an Gabi mit diversen
 * Aenderungen. Fehler sind moeglich!!
 * 
 * Revision 1.7 2000/04/05 12:10:12 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.6 2000/03/14 10:59:07 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
