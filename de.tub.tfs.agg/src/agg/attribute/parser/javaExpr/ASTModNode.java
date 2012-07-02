package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTModNode.java,v 1.4 2010/07/29 10:09:20 olga Exp $
 * @author $Author: olga $
 */
public class ASTModNode extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTModNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTModNode(id);
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0);
		Node child2 = jjtGetChild(1);

		child1.checkContext();
		child2.checkContext();

		if (((SimpleNode)child1).hasNumberType() 
				&& ((SimpleNode)child2).hasNumberType()) {
			setNodeClass(commonNumberType((SimpleNode)child1, (SimpleNode)child2));
		}
		if (typeCode() >= typeCode(Float.TYPE)) {
			throw new ASTWrongTypeException("[int x int -> int]", 
					((SimpleNode)child1).getNodeClass().getName()
					+ " x " + ((SimpleNode)child2).getNodeClass().getName());
		}
	}

	public void interpret() {
		jjtGetChild(0).interpret();
		jjtGetChild(1).interpret();

		stack.set(--top, new Integer(((Integer) stack.get(top)).intValue()
				% ((Integer) stack.get(top+1)).intValue()));
	}

	public String getString() {
		Node left = jjtGetChild(0);
		Node right = jjtGetChild(1);
		return left.getString() + "%" + right.getString();
	}
}
/*
 * $Log: ASTModNode.java,v $
 * Revision 1.4  2010/07/29 10:09:20  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.3  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:48  olga
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
 * Revision 1.6 2000/04/05 12:10:25 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:19 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
