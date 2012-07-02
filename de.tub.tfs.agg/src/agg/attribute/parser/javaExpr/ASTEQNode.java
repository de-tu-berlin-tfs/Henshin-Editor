package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTEQNode.java,v 1.8 2010/07/29 10:09:20 olga Exp $
 * @author $Author: olga $
 */
public class ASTEQNode extends TYPE1xTYPE1toBOOL {

	static final long serialVersionUID = 1L;

	ASTEQNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTEQNode(id);
	}

	public void interpret() {
		// System.out.println("ASTEQNode.interpret()... ");

		Node child1 = jjtGetChild(0);
		Node child2 = jjtGetChild(1);

		// child1.interpret();
		try {
			// System.out.println("ASTEQNode.interpret() child1: "+child1);
			child1.interpret();
		} catch (Exception e) {
			// System.out.println("ASTEQNode.interpret() ... ex: "+child1+" ::
			// "+e);
			if (e instanceof ASTMissingValueException) {

			} else
				throw (RuntimeException) e;
		}

		// child2.interpret();
		try {
			// System.out.println("ASTEQNode.interpret() child2: "+child2);
			child2.interpret();
		} catch (Exception e) {
			// System.out.println("ASTEQNode.interpret() ... ex: "+child2+" ::
			// "+e);
			if (e instanceof ASTMissingValueException) {

			} else
				throw (RuntimeException) e;
		}

		// System.out.println("top: "+top);
		// System.out.println("ASTEQNode.interpret() child1: "+child1);
		// System.out.println("ASTEQNode.interpret() child2: "+child2);

		Object op1Result = null;
		Object op2Result = null;
		Object result;

		if (top > 0) {
			op1Result = stack.get(top-1);
			op2Result = stack.get(top);
		} else
			op1Result = stack.get(top);

		// System.out.println("ASTEQNode.interpret() stack[top] is Boolean: "+
		// (stack[top] instanceof Boolean));
		// System.out.println("ASTEQNode.interpret() op1Result: "+op1Result);
		// System.out.println("ASTEQNode.interpret() op2Result: "+op2Result);

		if (stack.get(top) instanceof Boolean) {
			if (op1Result instanceof Boolean && op2Result instanceof Boolean)
				result = new Boolean(
						((Boolean) op1Result).booleanValue() == ((Boolean) op2Result)
								.booleanValue());
			else
				result = stack.get(top); // new Boolean(false);
		} else if (((SimpleNode)child1).hasNumberType()) {
			// System.out.println("ASTEQNode.interpret()
			// child1.hasNumberType()");
			Class<?> commonType = commonNumberType((SimpleNode)child1, (SimpleNode)child2);
			if (op1Result != null && op2Result != null) {
				if (typeCode(commonType) <= typeCode(Integer.TYPE)) {
					// System.out.println("ASTEQNode.interpret() int == int");
					result = new Boolean(
						((Number) op1Result).intValue() == ((Number) op2Result)
								.intValue());
				} else {
					result = new Boolean(
						((Number) op1Result).floatValue() == ((Number) op2Result)
								.floatValue());
				}
			} else if (op1Result == null && op2Result == null)
				result = new Boolean(true);
			else
				result = new Boolean(false);
		}
		// TEST strings
		else if (((SimpleNode)child1).hasStringType() 
					&& ((SimpleNode)child2).hasStringType()) {
			// System.out.println("child1 && child2 :: hasStringType");
			if (op1Result == null && op2Result == null)
				result = new Boolean(true);
			else if (op1Result != null && op2Result != null)
				result = new Boolean(((String) op1Result).equals(op2Result));
			else
				result = new Boolean(false);
		}

		// TEST == null
		else if (((SimpleNode)child1).getNodeClass() != null
				&& ((SimpleNode)child2).getNodeClass().getName().equals("java.lang.Object")
				&& child2.getString().equals("null")) {
			// System.out.println("ASTEQNode TEST:: child1.getString():
			// "+child1.getString()+" op1Result = "+op1Result);
			// System.out.println("ASTEQNode TEST:: child2.getString():
			// "+child2.getString()+" op2Result = "+op2Result);

			if (((SimpleNode)child1).hasStringType()) {
				// System.out.println("ASTNENode TEST:: child1.hasStringType:
				// "+child1.hasStringType());
				if (op1Result != null && child1.getString().equals(op1Result))
					result = new Boolean(false);
				else
					result = new Boolean(true);
			} else if (op1Result instanceof Boolean) {
				// System.out.println("ASTEQNode TEST:: op1Result Boolean:
				// "+op1Result);
				result = new Boolean(true);
			} else {
				// System.out.println("ASTEQNode TEST:: else: op1Result:
				// "+op1Result);
				result = new Boolean(op1Result == op2Result);
			}

//			stack[top] = result;
//			stack.set(top, result);
			// System.out.println("ASTEQNode TEST:: stack[top] = result:
			// "+result);
		} else {
			result = null;
		}

//		stack[top] = result;
		stack.set(top, result);
		if (top > 0) {
//			stack[--top] = result;
			stack.set(--top, result);
		}
		// System.out.println("ASTEQNode.interpret() stack[top] = result:
		// "+result);
		// System.out.println("ASTEQNode.interpret() stack[--top] = result:
		// "+result);
	}

	public String getString() {
		Node left = jjtGetChild(0);
		Node right = jjtGetChild(1);
		return left.getString() + "==" + right.getString();
	}
}
/*
 * $Log: ASTEQNode.java,v $
 * Revision 1.8  2010/07/29 10:09:20  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.7  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.6  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.5  2007/09/24 09:42:38  olga
 * AGG transformation engine tuning
 *
 * Revision 1.4  2007/09/10 13:05:48  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/03/15 09:25:03 olga Some bugs of
 * attr. conditions fixed
 * 
 * Revision 1.2 2006/01/16 09:37:01 olga Extended attr. setting
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
 * Revision 1.6 2000/04/05 12:09:59 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:57 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
