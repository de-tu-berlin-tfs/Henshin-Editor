package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

/**
 * @version $Id: ASTExpression.java,v 1.8 2010/11/28 22:12:22 olga Exp $
 * @author $Author: olga $
 */
public class ASTExpression extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTExpression(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTExpression(id);
	}

	/*
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 */

	public void dump(String prefix) {
		if (this.children != null) {
			for (java.util.Enumeration<Node> e = this.children.elements(); e
					.hasMoreElements();) {
				SimpleNode n = (SimpleNode) e.nextElement();
				n.dump(prefix);
			}
		}
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0);
		child1.checkContext();
		takeNodeClassFrom((SimpleNode)child1);
	}

	public void interpret() {
		jjtGetChild(0).interpret();
	}

	public String getString() {
		Node nparent = jjtGetParent();
		String result = jjtGetChild(0).getString();
		if (nparent != null) {
			if (nparent instanceof ASTPrimaryExpression)
				result = "(" + result + ")";
		}
		return result;
	}
}
/*
 * $Log: ASTExpression.java,v $
 * Revision 1.8  2010/11/28 22:12:22  olga
 * tuning
 *
 * Revision 1.7  2010/09/23 08:15:02  olga
 * tuning
 *
 * Revision 1.6  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.5  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/24 09:42:38  olga
 * AGG transformation engine tuning
 *
 * Revision 1.3  2007/09/10 13:05:46  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:37:01 olga Extended
 * attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:00 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:02 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:00 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.4 2000/03/03 11:32:13 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */
