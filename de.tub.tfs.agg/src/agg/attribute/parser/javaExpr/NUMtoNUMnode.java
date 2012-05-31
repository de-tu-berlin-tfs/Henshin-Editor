package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

/**
 * @version $Id: NUMtoNUMnode.java,v 1.3 2010/03/31 21:10:49 olga Exp $
 * @author $Author: olga $
 */
public class NUMtoNUMnode extends SimpleNode {

	static final long serialVersionUID = 1L;

	NUMtoNUMnode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new NUMtoNUMnode(id);
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0);

		child1.checkContext();

		if (((SimpleNode)child1).hasNumberType()) {
			setNodeClass(((SimpleNode)child1).getNodeClass());
		} else {
			throw new ASTWrongTypeException("[Number -> Number]", "["
					+ ((SimpleNode)child1).getNodeClass().getName() + "]");
		}
	}
}
/*
 * $Log: NUMtoNUMnode.java,v $
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
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:11:01 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 10:59:49 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
