package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

/**
 * @version $Id: TYPE1xTYPE1toBOOL.java,v 1.4 2010/03/31 21:10:49 olga Exp $
 * @author $Author: olga $
 */
public class TYPE1xTYPE1toBOOL extends SimpleNode {

	static final long serialVersionUID = 1L;

	TYPE1xTYPE1toBOOL(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new TYPE1xTYPE1toBOOL(id);
	}

	public void checkContext() throws ASTWrongTypeException {
		Node child1 = jjtGetChild(0), child2 = jjtGetChild(1);

		// System.out.println("TYPE1xTYPE1toBOOL.checkContext:: child1:
		// "+child1);
		// System.out.println("TYPE1xTYPE1toBOOL.checkContext:: child2:
		// "+child2);

		child1.checkContext();
		child2.checkContext();

		if (((SimpleNode)child1).hasNumberType() 
				&& ((SimpleNode)child2).hasNumberType()) {
			setNodeClass(Boolean.TYPE);

		} else if (((SimpleNode)child1).getNodeClass() == ((SimpleNode)child2).getNodeClass()) {
			setNodeClass(Boolean.TYPE);
		} else {
			// xxx!=null or xxx==null
			// System.out.println("TYPE1xTYPE1toBOOL.checkContext::
			// "+child1.getNodeClass());
			// System.out.println("TYPE1xTYPE1toBOOL.checkContext::
			// "+child2.getNodeClass());
			// System.out.println("TYPE1xTYPE1toBOOL.checkContext::
			// "+child2.toString()+" "+child2.getString());
			if (((SimpleNode)child1).getNodeClass() != null
					&& ((SimpleNode)child2).getNodeClass().getName().equals(
							"java.lang.Object")
					&& child2.getString().equals("null")) {
				// System.out.println("TYPE1xTYPE1toBOOL.checkContext::
				// "+child1.getString()+ " ?= "+child2.getString()+" DONE");
				setNodeClass(Boolean.TYPE);
				return;
			}

			throw new ASTWrongTypeException("[TYPE_1 x TYPE_1 -> boolean]", "["
					+ ((SimpleNode)child1).getNodeClass().getName() + " x "
					+ ((SimpleNode)child2).getNodeClass().getName() + "]");
		}
	}
}
/*
 * $Log: TYPE1xTYPE1toBOOL.java,v $
 * Revision 1.4  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:37:01 olga
 * Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:15 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:11:13 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 11:00:00 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
