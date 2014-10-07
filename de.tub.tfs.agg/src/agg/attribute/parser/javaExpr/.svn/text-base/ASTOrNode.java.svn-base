package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTOrNode.java,v 1.5 2010/07/29 10:09:23 olga Exp $
 * @author $Author: olga $
 */
public class ASTOrNode extends BOOLxBOOLtoBOOLnode {

	static final long serialVersionUID = 1L;

	ASTOrNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTOrNode(id);
	}

	public void interpret() {
		// System.out.println("ASTOrNode.interpret() ...");
		Object result;
		// jjtGetChild(0).interpret();
		try {
			jjtGetChild(0).interpret();
		} catch (Exception e) {
			// System.out.println("ASTOrNode.interpret() ... ex:
			// "+jjtGetChild(0)+" :: "+e);
			if (e instanceof ASTMissingValueException) {
			} else
				throw (RuntimeException) e;
		}

		if (((Boolean) stack.get(top)).booleanValue()) {
			stack.set(top, new Boolean(true));
			return;
		}

		// jjtGetChild(1).interpret();
		try {
			jjtGetChild(1).interpret();
		} catch (Exception e) {
			// System.out.println("ASTOrNode.interpret() ... ex:
			// "+jjtGetChild(1)+" :: "+e);
			if (e instanceof ASTMissingValueException) {
			} else
				throw (RuntimeException) e;
		}

		// System.out.println("ASTOrNode.interpret() stack[top]: "+stack[top]);
		// System.out.println("ASTOrNode.interpret() stack[top+1]:
		// "+stack[top+1]);

		if (stack.get(top+1) instanceof Boolean)
			result = new Boolean(((Boolean) stack.get(top)).booleanValue()
					|| ((Boolean) stack.get(top+1)).booleanValue());
		else if ((top > 0) && (stack.get(top-1) instanceof Boolean))
			result = new Boolean(((Boolean) stack.get(top)).booleanValue()
					|| ((Boolean) stack.get(top-1)).booleanValue());
		else
			result = new Boolean(((Boolean) stack.get(top)).booleanValue());

		/*
		 * result = new Boolean(((Boolean)stack[top]).booleanValue() ||
		 * ((Boolean)stack[top - 1]).booleanValue()); //((Boolean)stack[top +
		 * 1]).booleanValue());
		 */

		if (top > 0)
			stack.set(--top, result);
		else
			stack.set(top, result);

		// System.out.println("ASTOrNode.interpret() result: "+result);
	}

	public String getString() {
		Node left = jjtGetChild(0);
		Node right = jjtGetChild(1);
		return left.getString() + "||" + right.getString();
	}
}
/*
 * $Log: ASTOrNode.java,v $
 * Revision 1.5  2010/07/29 10:09:23  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.4  2007/09/10 13:05:47  olga
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
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:34 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:26 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
