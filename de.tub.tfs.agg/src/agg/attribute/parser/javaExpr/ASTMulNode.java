package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTMulNode.java,v 1.3 2010/07/29 10:09:21 olga Exp $
 * @author $Author: olga $
 */
public class ASTMulNode extends NUMxNUMtoNUMnode {

	static final long serialVersionUID = 1L;

	ASTMulNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTMulNode(id);
	}

	public void interpret() {
		jjtGetChild(0).interpret();
		jjtGetChild(1).interpret();

		Object op1Result = stack.get(top-1);
		Object op2Result = stack.get(top);
		Object result;

		if (typeCode() <= typeCode(Integer.TYPE)) {
			result = new Integer(((Number) op1Result).intValue()
					* ((Number) op2Result).intValue());
		} else {
			result = new Float(((Number) op1Result).floatValue()
					* ((Number) op2Result).floatValue());
		}
//		stack[--top] = result;
		stack.set(--top, result);
	}

	public String getString() {
		Node left = jjtGetChild(0);
		Node right = jjtGetChild(1);
		return left.getString() + "*" + right.getString();
	}
}
/*
 * $Log: ASTMulNode.java,v $
 * Revision 1.3  2010/07/29 10:09:21  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.2  2007/09/10 13:05:49  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:24:03 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:27 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:20 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
