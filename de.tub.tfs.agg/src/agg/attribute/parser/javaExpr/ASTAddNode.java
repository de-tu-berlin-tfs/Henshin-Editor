package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */


import agg.attribute.impl.AttrSession;
import agg.attribute.impl.VerboseControl;

/**
 * @version $Id: ASTAddNode.java,v 1.8 2010/09/23 08:15:02 olga Exp $
 * @author $Author: olga $
 */
public class ASTAddNode extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTAddNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTAddNode(id);
	}

	public void interpret() {
		// System.out.println("ASTAddNode: top vor children "+top);
		AttrSession.logPrintln(VerboseControl.logJexParser,
				"ASTAddNode: top vor children " + top);
		jjtGetChild(0).interpret();
		jjtGetChild(1).interpret();
		// System.out.println("ASTAddNode: top nach children "+top);
		AttrSession.logPrintln(VerboseControl.logJexParser,
				"ASTAddNode: top nach children " + top);
		dump("ASTAddNode - interpret: ");
		Class<?> cls = getNodeClass();
		Object op1Result = stack.get(top-1); //stack[top - 1];
		Object op2Result = stack.get(top); //stack[top];
		Object result;

		if (cls == stringClass) {
			result = new String("" + op1Result + op2Result);
		} else {
			if (typeCode() <= typeCode(Integer.TYPE)) {
				result = new Integer(((Number) op1Result).intValue()
						+ ((Number) op2Result).intValue());
			} else {
				result = new Float(((Number) op1Result).floatValue()
						+ ((Number) op2Result).floatValue());
			}
		}
		top--;
		Node obj = ObjectConstNode
				.jjtCreate(this.identifier + " to ObjectConstNode");
		((ObjectConstNode) obj).obj = result;
		obj.jjtSetParent(jjtGetParent());
		((ObjectConstNode) obj).setNodeClass(getNodeClass());
		jjtGetParent().replaceChild(this, obj);
		jjtSetParent(null);
		obj.interpret();
	}

	protected void propagateStringConcatType() {
		SimpleNode child1 = (SimpleNode) jjtGetChild(0);
		SimpleNode child2 = (SimpleNode) jjtGetChild(1);

		setNodeClass(stringClass);

		if (child1.identifier == "AddNode") {
			((ASTAddNode) child1).propagateStringConcatType();
		}
		if (child2.identifier == "AddNode") {
			((ASTAddNode) child2).propagateStringConcatType();
		}
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0);
		Node child2 = jjtGetChild(1);

		child1.checkContext();
		child2.checkContext();

		if (((SimpleNode)child1).hasNumberType() 
				&& ((SimpleNode)child2).hasNumberType()) {
			setNodeClass(commonNumberType((SimpleNode)child1, (SimpleNode)child2));
		} else if (((SimpleNode)child1).getNodeClass() == stringClass
				|| ((SimpleNode)child2).getNodeClass() == stringClass) {
			propagateStringConcatType();
		} else {
			throw new ASTWrongTypeException("[Number x Number -> Number]"
					+ " or [String x String -> String]", ((SimpleNode)child1).getNodeClass()
					.getName()
					+ " x " + ((SimpleNode)child2).getNodeClass().getName());
		}
	}

	public String toString() {
		String result = super.toString();
		result += " " + stack + " " + top;
		return result;
	}

	public String getString() {
		String resultString = "+";
		Node child1 = jjtGetChild(0);
		Node child2 = jjtGetChild(1);
		String left;
		String right;
		left = child1.getString();
		right = child2.getString();
		return left + resultString + right;
	}
}

/*
 * $Log: ASTAddNode.java,v $
 * Revision 1.8  2010/09/23 08:15:02  olga
 * tuning
 *
 * Revision 1.7  2010/07/29 10:09:23  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.6  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.5  2009/10/26 10:03:33  olga
 * tuning and tests
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:46  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:32:58 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:51 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.4 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.3 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.2 2002/09/23 12:23:58 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:40 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:41 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.4 2000/03/03 11:31:55 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */
