package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTCondExpr.java,v 1.5 2010/07/29 10:09:24 olga Exp $
 * @author $Author: olga $
 */
public class ASTCondExpr extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTCondExpr(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTCondExpr(id);
	}

	public void checkContext() throws ASTWrongTypeException {
		// System.out.println("ASTCondExpr.checkContext()...");
		Node condition = jjtGetChild(0);
		Node expr1 = jjtGetChild(1);
		Node expr2 = jjtGetChild(2);
		/*
		 * System.out.println("condition: "+condition.toString());
		 * System.out.println("expr1: "+expr1.toString());
		 * System.out.println("expr2: "+expr2.toString());
		 */
		condition.checkContext();

		if (((SimpleNode)condition).getNodeClass() != Boolean.TYPE) {
			throw new ASTWrongTypeException("[boolean ? TYPE_1 : TYPE_1]", "["
					+ ((SimpleNode)condition).getNodeClass().getName() + " ? ... : ...]");
		}
		expr1.checkContext();
		expr2.checkContext();

		if (((SimpleNode)expr1).getNodeClass() == ((SimpleNode)expr2).getNodeClass()) {
			setNodeClass(((SimpleNode)expr1).getNodeClass());
		} else {
			throw new ASTWrongTypeException("[boolean ? TYPE_1 : TYPE_1]", "["
					+ ((SimpleNode)condition).getNodeClass().getName() + " ? "
					+ ((SimpleNode)expr1).getNodeClass().getName() + " : "
					+ ((SimpleNode)expr2).getNodeClass().getName() + "]");
		}
	}

	public void interpret() {
		jjtGetChild(0).interpret();

		if (((Boolean) stack.get(top--)).booleanValue()) {
			jjtGetChild(1).interpret();
		} else {
			jjtGetChild(2).interpret();
		}
	}

	public String getString() {
		String result;
		Node cond = jjtGetChild(0);
		Node iff = jjtGetChild(1);
		Node thenn = jjtGetChild(2);
		result = cond.getString() + "?" + iff.getString() + ":"
				+ thenn.getString();
		return result;
	}
}
/*
 * $Log: ASTCondExpr.java,v $
 * Revision 1.5  2010/07/29 10:09:24  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.4  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:37:01 olga Extended
 * attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:51 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:24:00 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:56 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:55 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
