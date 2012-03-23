package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTBitwiseOrNode.java,v 1.3 2010/07/29 10:09:22 olga Exp $
 * @author $Author: olga $
 */
public class ASTBitwiseOrNode extends NUMxNUMtoNUMnode {

	static final long serialVersionUID = 1L;

	ASTBitwiseOrNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTBitwiseOrNode(id);
	}

	public void interpret() {
		jjtGetChild(0).interpret();
		jjtGetChild(1).interpret();

		if (stack.get(top) instanceof Boolean) {
//			stack[--top] = new Boolean(((Boolean) stack[top]).booleanValue()
//					| ((Boolean) stack[+1]).booleanValue());
			stack.set(--top, new Boolean(((Boolean) stack.get(top)).booleanValue()
					| ((Boolean) stack.get(+1)).booleanValue()));
		}
		else if (stack.get(top) instanceof Integer) {
//			stack[--top] = new Integer(((Integer) stack[top]).intValue()
//					| ((Integer) stack[+1]).intValue());
			stack.set(--top, new Integer(((Integer) stack.get(top)).intValue()
					| ((Integer) stack.get(+1)).intValue()));
		}
	}

	public String getString() {
		Node left = jjtGetChild(0);
		Node right = jjtGetChild(1);
		return left.getString() + "|" + right.getString();
	}
}
/*
 * $Log: ASTBitwiseOrNode.java,v $
 * Revision 1.3  2010/07/29 10:09:22  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.2  2007/09/10 13:05:49  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:51 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:59 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:50 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:50 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
