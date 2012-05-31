package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTIntConstNode.java,v 1.4 2010/08/23 07:31:25 olga Exp $
 * @author $Author: olga $
 */
public class ASTIntConstNode extends SimpleNode {

	static final long serialVersionUID = 1L;

	int val;

	ASTIntConstNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTIntConstNode(id);
	}

	public void checkContext() {
		setNodeClass(Integer.TYPE);
	}

	public void interpret() {
		stack.add(++top, new Integer(this.val));
	}

	public String getString() {
		return "" + this.val;
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTIntConstNode) copy).val = this.val;
		return copy;
	}

}

/*
 * $Log: ASTIntConstNode.java,v $
 * Revision 1.4  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.3  2010/07/29 10:09:22  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.2  2007/09/10 13:05:48  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.4 2005/03/21 09:22:57 olga ...
 * 
 * Revision 1.3 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:02 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:15 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:10 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.4 2000/03/03 11:32:23 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */
