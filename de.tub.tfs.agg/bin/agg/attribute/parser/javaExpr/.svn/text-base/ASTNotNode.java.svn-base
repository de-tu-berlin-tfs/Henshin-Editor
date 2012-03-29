package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTNotNode.java,v 1.3 2010/07/29 10:09:23 olga Exp $
 * @author $Author: olga $
 */
public class ASTNotNode extends BOOLtoBOOLnode {

	static final long serialVersionUID = 1L;

	ASTNotNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTNotNode(id);
	}

	public void interpret() {
		jjtGetChild(0).interpret();
//		stack[top] = new Boolean(!((Boolean) stack[top]).booleanValue());
		stack.set(top, new Boolean(!((Boolean) stack.get(top)).booleanValue()));
	}

	public String getString() {
		Node left = jjtGetChild(0);
		return "!" + left.getString();
	}
}
/*
 * $Log: ASTNotNode.java,v $
 * Revision 1.3  2010/07/29 10:09:23  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.2  2007/09/10 13:05:47  olga
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
 * Revision 1.2 2002/09/23 12:24:04 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:31 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:24 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
