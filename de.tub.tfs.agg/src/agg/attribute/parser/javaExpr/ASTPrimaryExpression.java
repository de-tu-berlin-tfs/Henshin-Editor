/* JJT: 0.2.2 */

package agg.attribute.parser.javaExpr;


/**
 * @version $Id: ASTPrimaryExpression.java,v 1.11 2010/12/07 16:36:45 olga Exp $
 * @author $Author: olga $
 */
public class ASTPrimaryExpression extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTPrimaryExpression(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTPrimaryExpression(id);
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
		int nChildren = jjtGetNumChildren();
		Node prefix, suffix = null;

		try {
			prefix = jjtGetChild(0);
			prefix.checkContext();
			for (int i = 1; i < nChildren; i++) {
				suffix = jjtGetChild(i);
				if (((SimpleNode)suffix).isMember()) {
					((MemberNode) suffix).checkContext((SimpleNode)prefix);
				} else if (((SimpleNode)suffix).isArrayIndex()) {
					((ASTArrayIndex) suffix).checkContext((SimpleNode)prefix);
				}
				if (!((SimpleNode)suffix).isAction()) {
					prefix = suffix;
				}
			}
			if (suffix == null || ((SimpleNode)suffix).isAction()) {
				takeNodeClassFrom((SimpleNode)prefix);
			} else {
				takeNodeClassFrom((SimpleNode)suffix);
			}
		} catch (Exception e) {
			throw (RuntimeException) e;
		}
	}

	public void interpret() {
		int nChildren = jjtGetNumChildren();
		Node prefix, suffix;

		try {
			prefix = jjtGetChild(0);
			prefix.interpret();
			
			for (int i = 1; i < nChildren; i++) {
				suffix = jjtGetChild(i);
				if (((SimpleNode)suffix).isMember()) {
					((MemberNode) suffix).interpret((SimpleNode)prefix);
				} else if (((SimpleNode)suffix).isArrayIndex()) {
					((ASTArrayIndex) suffix).interpret((SimpleNode)prefix);
				}
				top--;
				if (!((SimpleNode)suffix).isAction()) {
					stack.set(top, stack.get(top+1));
					prefix = suffix;
				}
			}
		} catch (Exception e) {
			throw (RuntimeException) e;
		}
	}

	public String getString() {
		int nChildren = jjtGetNumChildren();
		String result = "";
		for (int i = 0; i < nChildren; i++) {
			Node child = jjtGetChild(i);
			result += child.getString();
		}
		return result;
	}
}
/*
 * $Log: ASTPrimaryExpression.java,v $
 * Revision 1.11  2010/12/07 16:36:45  olga
 * bug fixed when a static member of a static class is used in attr expression
 *
 * Revision 1.10  2010/11/28 22:12:22  olga
 * tuning
 *
 * Revision 1.9  2010/09/23 08:15:01  olga
 * tuning
 *
 * Revision 1.8  2010/07/29 10:09:19  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.7  2010/05/05 16:16:28  olga
 * tuning and tests
 *
 * Revision 1.6  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.5  2007/11/05 09:18:23  olga
 * code tuning
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:37:01 olga
 * Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.7 2000/04/05 12:10:36 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.6 2000/03/14 10:59:27 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
