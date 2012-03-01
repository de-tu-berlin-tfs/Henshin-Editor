/* JJT: 0.2.2 */
package agg.attribute.parser.javaExpr;

import java.lang.reflect.Array;

/**
 * @version $Id: ASTArrayAllocation.java,v 1.7 2010/08/23 07:31:25 olga Exp $
 * @author $Author: olga $
 */
public class ASTArrayAllocation extends SimpleNode {

	static final long serialVersionUID = 1L;

	Class<?> componentClass = null;

	int nDimensions = 0;

	ASTArrayAllocation(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTArrayAllocation(id);
	}

	protected boolean isConstantExpr() {
		return false;
	}

	public void checkContext() {
		int nChildren = jjtGetNumChildren();
		Node componentNode = jjtGetChild(0);
		Node lengthNode;
		Object arrayInst;
		Class<?> resultClass;
		int dimArray[];

		componentNode.checkContext();
		this.componentClass = ((SimpleNode)componentNode).getNodeClass();
		this.nDimensions = nChildren - 1;

		for (int i = 1; i < nChildren; i++) {
			lengthNode = jjtGetChild(i);
			lengthNode.checkContext();
			if (((SimpleNode)lengthNode).getNodeClass() != Integer.TYPE) {
				String reqSig = "An array length must be of type integer (int).";
				String foundSig = "Tried to pass an object of type\n'"
						+ ((SimpleNode)lengthNode).getNodeClass() + "' as array length.";
				throw new ASTWrongTypeException(reqSig, foundSig);
			}
		}

		dimArray = new int[this.nDimensions];
		dimArray[0] = 1;
		for (int i = 1; i < this.nDimensions; i++) {
			dimArray[i] = 0;
		}
		arrayInst = Array.newInstance(this.componentClass, dimArray);
		resultClass = arrayInst.getClass();
		setNodeClass(resultClass);
	}

	public void interpret() {
		int nChildren = jjtGetNumChildren();
		Node componentNode = jjtGetChild(0);
		Node lengthNode;
		boolean isLengthAllowed = true;
		int length;
		int lengthList[] = new int[nChildren - 1];

		componentNode.interpret();
		this.componentClass = (Class<?>) stack.get(top--);

		for (int i = 1; i < nChildren; i++) {
			lengthNode = jjtGetChild(i);
			lengthNode.interpret();
			length = ((Integer) stack.get(top--)).intValue();
			lengthList[i - 1] = length;
			if (length < 0) {
				throw new RuntimeException(
						"An array length must be a non-negative integer number (int)"
								+ " or empty." + "\nTried to pass a value of "
								+ length + " as length.");
			}
			if (length == 0) {
				if (i == 1) {
					throw new RuntimeException(
							"A positive array length value is required\n"
									+ "at least for the first dimension.");
				} 
				isLengthAllowed = false;
			
			} else if (!isLengthAllowed) {
				throw new RuntimeException(
						"A positive array length value is illegal after a previous\n"
								+ "dimension length was empty.");
			}
		}
		stack.add(++top, Array.newInstance(this.componentClass, lengthList));
	}

	public String getString() {
		String dimString = "";
		Node constructorName = jjtGetChild(0);
		int nChildren = jjtGetNumChildren();
		for (int i = 1; i < nChildren; i++) {
			Node dimension = jjtGetChild(i);
			if (dimension instanceof ASTEmptyDimension)
				dimString += "[]";
			else
				dimString += "[" + dimension.getString() + "]";
		}
		return "new " + constructorName.getString() + dimString;
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTArrayAllocation) copy).componentClass = this.componentClass;
		((ASTArrayAllocation) copy).nDimensions = this.nDimensions;
		return copy;
	}
}
/*
 * $Log: ASTArrayAllocation.java,v $
 * Revision 1.7  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.6  2010/07/29 10:09:20  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.5  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.4  2010/03/08 15:38:02  olga
 * code optimizing
 *
 * Revision 1.3  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
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
 * Revision 1.2 2002/09/23 12:23:58 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:44 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:45 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */
