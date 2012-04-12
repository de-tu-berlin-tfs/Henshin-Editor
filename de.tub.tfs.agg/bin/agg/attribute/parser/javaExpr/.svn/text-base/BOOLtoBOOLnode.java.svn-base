package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

/**
 * @version $Id: BOOLtoBOOLnode.java,v 1.3 2010/03/31 21:10:49 olga Exp $
 * @author $Author: olga $
 */
public class BOOLtoBOOLnode extends SimpleNode {

	static final long serialVersionUID = 1L;

	BOOLtoBOOLnode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new BOOLtoBOOLnode(id);
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0);

		child1.checkContext();

		if (((SimpleNode)child1).getNodeClass() == Boolean.TYPE) {
			setNodeClass(Boolean.TYPE);
		} else {
			throw new ASTWrongTypeException("[boolean -> boolean]", "["
					+ ((SimpleNode)child1).getNodeClass().getName() + "]");
		}
	}
}
/*
 * $Log: BOOLtoBOOLnode.java,v $
 * Revision 1.3  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:47  olga
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
 * Revision 1.2 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:10:44 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 10:59:34 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
