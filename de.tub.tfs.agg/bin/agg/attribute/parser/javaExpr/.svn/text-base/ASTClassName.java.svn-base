package agg.attribute.parser.javaExpr;


/* JJT: 0.2.2 */

/**
 * @version $Id: ASTClassName.java,v 1.6 2010/11/28 22:12:22 olga Exp $
 * @author $Author: olga $
 */
public class ASTClassName extends SimpleNode {

	static final long serialVersionUID = 1L;

	String name;

	ASTClassName(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTClassName(id);
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		Class<?> clazz = getNodeClass();
		String cname;
		if (clazz == null) {
			cname = "\"" + this.name + "\"";
		} else {
			cname = clazz.toString();
		}
		return this.identifier + " " + cname;
	}

	public void checkContext() {
		Class<?> clazz;

		if (classResolver == null) {
			try {
				clazz = Class.forName(this.name);
				setNodeClass(clazz);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage() + "Class<?> " + this.name
						+ " not found: ");
			}
		} else {
			clazz = classResolver.forName(this.name);
			if (clazz == null) {
				System.out.println("Class " + this.name + " not found: ");
			} else {
				setNodeClass(clazz);
//				System.out.println(this.getClass().getName()+" ::   "+this.name);
			}
		}
	}

	public void interpret() {
		checkContext();
//		stack[++top] = getNodeClass();
		stack.add(++top, getNodeClass());
	}

	public String getString() {
		return this.name;
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTClassName) copy).name = new String(this.name);
		return copy;
	}
}
/*
 * $Log: ASTClassName.java,v $
 * Revision 1.6  2010/11/28 22:12:22  olga
 * tuning
 *
 * Revision 1.5  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.4  2010/07/29 10:09:23  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.3  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
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
 * Revision 1.3 2003/02/20 17:38:25 olga Tests
 * 
 * Revision 1.2 2002/09/23 12:23:59 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:55 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:54 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
