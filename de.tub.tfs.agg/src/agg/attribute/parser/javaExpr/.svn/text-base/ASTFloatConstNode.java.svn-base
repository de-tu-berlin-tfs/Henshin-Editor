/* JJT: 0.2.2 */

package agg.attribute.parser.javaExpr;


/**
 * @version $Id: ASTFloatConstNode.java,v 1.4 2010/08/23 07:31:26 olga Exp $
 * @author $Author: olga $
 */
public class ASTFloatConstNode extends SimpleNode {

	static final long serialVersionUID = 1L;

	double val;

	ASTFloatConstNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTFloatConstNode(id);
	}

	public void checkContext() {
		setNodeClass(Double.TYPE);
	}

	public void interpret() {
//		stack[++top] = new Double(val);
		stack.add(++top, new Double(this.val));
	}

	public String getString() {
		return "" + this.val;
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTFloatConstNode) copy).val = this.val;
		return copy;
	}
}
/*
 * $Log: ASTFloatConstNode.java,v $
 * Revision 1.4  2010/08/23 07:31:26  olga
 * tuning
 *
 * Revision 1.3  2010/07/29 10:09:24  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.2  2007/09/10 13:05:46  olga
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
 * Revision 1.3 2003/07/21 13:18:10 olga Attrs testen
 * 
 * Revision 1.2 2002/09/23 12:24:01 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:10:07 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:03 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
